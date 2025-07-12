import express from 'express';
import connection from './config/data_connect.js';
import cors from 'cors';
import dotenv from "dotenv";
import cookieParser from "cookie-parser";
import authRoutes from './modules/Auth/authRoutes.js';
import bcrypt from "bcryptjs";
import jwt from "jsonwebtoken";

const app = express();
const PORT = 3000;
const SECRET_KEY = "yoursecretkey@123";

dotenv.config();

app.use(express.json());

// Kiểm tra kết nối DB
connection.connect((err) => {
  if (err) {
    console.error('Kết nối MySQL thất bại:', err);
  } else {
    console.log('Đã kết nối MySQL thành công');
  }
});
app.use(cookieParser());
// (Nếu bạn có xử lý form URL-encoded)
app.use(express.urlencoded({ extended: true }));

app.use(cors({
  origin: "http://10.0.2.2:3000" || "*", // 👈 Cho phép mọi domain gọi được
  methods: ["GET", "POST", "PUT", "DELETE"],
  credentials: true,
}));
// Route test
app.get('/', (req, res) => {
  res.send('Hello from Express!');
});
app.post('/api/auth/register', (req, res) => {
const { username, email, password } = req.body;

  try {
    if (!username || !email || !password) {
      return res.status(400).json({
        success: false,
        message: "All fields are required"
      });
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{6,}$/;

    if (!emailRegex.test(email)) {
      return res.status(400).json({
        success: false,
        message: "Invalid email format"
      });
    }

    if (!passwordRegex.test(password)) {
      return res.status(400).json({
        success: false,
        message: "Password must include upper, lower case, number, min 6 characters"
      });
    }

    // Kiểm tra email đã tồn tại chưa
    connection.query("SELECT * FROM users WHERE email = ?", [email], async (err, results) => {
      if (err) return res.status(500).json({ success: false, message: "Database error" });

      if (results.length > 0) {
        return res.status(400).json({
          success: false,
          message: "Email already exists"
        });
      }

      const hashedPassword = await bcrypt.hash(password, 10); // Mã hóa password

      // Thêm user
      connection.query(
        "INSERT INTO users (full_name, email, password) VALUES (?, ?, ?)",
        [username, email, hashedPassword],
        (insertErr, insertResult) => {
          if (insertErr) {
            console.error("Lỗi khi thêm user:", insertErr);
            return res.status(500).json({
              success: false,
              message: "Error inserting user into database"
            });
          }

          const userId = insertResult.insertId;

          res.status(201).json({
            success: true,
            message: "User registered successfully",
            userId: userId
          });
        }
      );
    });
  } catch (error) {
    console.error("Register error:", error);
    res.status(500).json({
      success: false,
      message: "Internal Server Error"
    });
  }
});
app.post('/api/auth/login', (req, res) => {
  console.log("Received registration request:", req.body);
  const { email, password } = req.body;
  try {
        if (!email || !password) {
          return res.status(400).json({
            success: false,
            message: "All fields are required",
            token: ""
          });
        }
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
          return res.status(400).json({
            success: false,
            message: "Invalid email format",
            token: ""
          });
        }
        connection.query(
          "SELECT * FROM users WHERE email = ?",
          [email],
          async (err, results) => {
            if (err) {
              return res.status(500).json({
                success: false,
                message: "Database error",
                token: ""
              });
            }

            if (results.length === 0) {
              return res.status(400).json({
                success: false,
                message: "Email not found",
                token: ""
              });
            }

            const user = results[0];
            const isPasswordValid = await bcrypt.compare(password, user.password);

            if (!isPasswordValid) {
              return res.status(400).json({
                success: false,
                message: "Invalid password",
                token: ""
              });
            }

            const token = jwt.sign(
              {
                id: user.user_id,
                email: user.email,
                fullName: user.full_name
              },
              SECRET_KEY,
              { expiresIn: "7d" }
            );

            res.status(200).json({
              success: true,
              message: "Login successful",
              token: token
            });
            console.log(token)
          }
        );
      } catch (error) {
        console.error("Login error:", error);
        res.status(500).json({
          success: false,
          message: "Internal Server Error",
          token: ""
        });
      }
});
app.get('/api/rooms', (req, res) => {
    connection.query("SELECT * FROM rooms", (err, results) => {
        if (err) {
            console.error("Lỗi truy vấn:", err);
            return res.status(500).json({ success: false, message: "Database error" });
        }
        res.json({ success: true, data: results });
    });
});

//app.use("/api/auth", authRoutes);
app.post('/api/rooms/search', (req, res) => {
    const {
        hotelName,
        checkInDate,
        checkOutDate,
        checkInTime,
        checkOutTime,
        numberOfGuests
    } = req.body;

    if (!hotelName) {
        return res.status(400).json({ success: false, message: "Hotel name is required" });
    }

    const sql = `
        SELECT rooms.*
        FROM rooms
        JOIN hotels ON rooms.hotel_id = hotels.hotel_id
        WHERE hotels.hotel_name = ?
        AND rooms.room_flag != true
    `;

    connection.query(sql, [hotelName], (err, results) => {
        if (err) {
            console.error("Lỗi truy vấn:", err);
            return res.status(500).json({ success: false, message: "Database error" });
        }

        res.json({ success: true, data: results });
    });
});


// Middleware xác thực token
const authenticateToken = async (req, res, next) => {
  const authHeader = req.headers['authorization'];
  if (!authHeader?.startsWith('Bearer ')) {
    return res.status(401).json({ success: false, message: 'Token thiếu hoặc sai' });
  }

  const token = authHeader.split(' ')[1];
  try {
    const decoded = jwt.verify(token, SECRET_KEY);
    req.user = decoded; // Gắn user vào req
    next();
  } catch (err) {
    return res.status(403).json({ success: false, message: 'Token không hợp lệ' });
  }
};


app.post('/api/user/update/user', authenticateToken, async (req, res) => {
  const userId = req.user.id;
  const { userName, gender, birth_date, phone, identity_number } = req.body;

  const sql = `
    UPDATE users
    SET
      full_name = COALESCE(?, full_name),
      gender = COALESCE(?, gender),
      birth_date = COALESCE(?, birth_date),
      phone = COALESCE(?, phone),
      identity_number = COALESCE(?, identity_number)
    WHERE user_id = ?
  `;

  const values = [userName, gender, birth_date, phone, identity_number, userId];

  connection.query(sql, values, (err, result) => {
    if (err) {
      console.error(err);
      return res.status(500).json({ success: false, message: 'Lỗi máy chủ' });
    }

    if (result.affectedRows > 0) {
      res.json({ success: true, message: 'Cập nhật thành công' });
    } else {
      res.json({ success: false, message: 'Không tìm thấy người dùng' });
    }
  });
});

app.post('/api/book-room', authenticateToken, (req, res) => {
    const userId = req.user.id;
    const { room_id, check_in, check_out, total_price } = req.body;
    // 1. Cập nhật room_flag = true
    const updateRoomSql = "UPDATE rooms SET room_flag = TRUE WHERE room_id = ?";
    connection.query(updateRoomSql, [room_id], (err, updateResult) => {
        if (err) return res.status(500).json({ success: false, message: "Lỗi cập nhật phòng" });

        // 2. Chèn vào bảng booking
        const insertBookingSql = `
            INSERT INTO booking (user_id, room_id, check_in, check_out, total_price, booking_status, booking_date)
            VALUES (?, ?, ?, ?, ?, ?, NOW())
        `;
        connection.query(
            insertBookingSql,
            [userId, room_id, check_in, check_out, total_price, 'Đã đặt'],
            (err, result) => {
                if (err) return res.status(500).json({ success: false, message: "Lỗi đặt phòng" });
                res.json({ success: true, message: "Đặt phòng thành công" });
            }
        );
    });
});

app.get('/api/user/bookings', authenticateToken, (req, res) => {
    const userId = req.user.id;

    const sql = `
        SELECT
            b.booking_id,
            r.room_type,
            r.image_url,
            b.check_in,
            b.check_out,
            b.total_price,
            b.booking_status
        FROM booking b
        JOIN rooms r ON b.room_id = r.room_id
        WHERE b.user_id = ?
        ORDER BY b.booking_date DESC
    `;

    connection.query(sql, [userId], (err, results) => {
        if (err) {
            console.error("Lỗi khi truy vấn booking:", err);
            return res.status(500).json({ success: false, message: "Lỗi server" });
        }

        res.json(results);
    });
});

app.post('/api/bookings/cancel', authenticateToken, (req, res) => {
    const userId = req.user.id;
    const bookingId = req.body.booking_id;

    if (!bookingId) {
        return res.status(400).json({ success: false, message: 'Thiếu booking_id' });
    }

    // B1: Cập nhật trạng thái của booking sang CANCELLED
    const cancelSql = `UPDATE booking SET booking_status = 'CANCELLED' WHERE booking_id = ? AND user_id = ?`;

    connection.query(cancelSql, [bookingId, userId], (err, result) => {
        if (err) {
            console.error('❌ Lỗi khi hủy booking:', err);
            return res.status(500).json({ success: false, message: 'Lỗi server', error: err });
        }

        if (result.affectedRows === 0) {
            return res.status(404).json({ success: false, message: 'Không tìm thấy booking hoặc không thuộc về bạn' });
        }

        console.log('✅ Hủy booking thành công');

        // B2: Truy vấn room_id tương ứng với booking đã hủy
        const getRoomIdSql = `SELECT room_id FROM booking WHERE booking_id = ?`;

        connection.query(getRoomIdSql, [bookingId], (err, rows) => {
            if (err) {
                console.error('❌ Lỗi khi lấy room_id:', err);
                return res.status(500).json({ success: false, message: 'Lỗi server khi lấy room_id' });
            }

            if (rows.length === 0) {
                return res.status(404).json({ success: false, message: 'Không tìm thấy phòng liên quan' });
            }

            const roomId = rows[0].room_id;

            // B3: Cập nhật room_flag = 0 trong bảng room
            const updateRoomSql = `UPDATE rooms SET room_flag = 0 WHERE room_id = ?`;


            connection.query(updateRoomSql, [roomId], (err, result) => {
                if (err) {
                    console.error('❌ Lỗi khi cập nhật room_flag:', err);
                    return res.status(500).json({ success: false, message: 'Lỗi khi cập nhật room_flag' });
                }
                console.log('✅ Đã cập nhật room_flag = 0 cho room_id:', roomId);
                return res.status(200).json({ success: true, message: 'Đã huỷ phòng và cập nhật room_flag' });
            });
        });
    });
});

app.get('/api/rooms/available', (req, res) => {
    const sql = `SELECT * FROM rooms WHERE room_flag = 0 LIMIT 3`;

    connection.query(sql, (err, results) => {
        if (err) {
            console.error("❌ Lỗi truy vấn:", err);
            return res.status(500).json({ success: false, message: 'Lỗi server', error: err });
        }

        console.log("✅ Lấy danh sách phòng thành công"); // Đặt console.log đúng
        return res.status(200).json(results);
    });
});



app.listen(PORT, () => {
  console.log(`🚀 Server is running at http://localhost:${PORT}`);
});

export default app;

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
//app.use("/api/auth", authRoutes);

app.listen(PORT, () => {
  console.log(`🚀 Server is running at http://localhost:${PORT}`);
});

export default app;

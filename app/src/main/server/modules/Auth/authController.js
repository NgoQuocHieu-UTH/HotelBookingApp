import bcrypt from "bcryptjs";
import jwt from "jsonwebtoken";
import connection from "../../config/data_connect.js";

const SECRET_KEY = "yoursecretkey@123"; // Nên để trong .env

// LOGIN
const login = async (req, res) => {
  const { email, password } = req.body;
  return res.status(400).json({
            success: false,
            message: "All fields are required",
            token: ""
          });
  /*try {
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
        }
      );
    } catch (error) {
      console.error("Login error:", error);
      res.status(500).json({
        success: false,
        message: "Internal Server Error",
        token: ""
      });
    }*/
};

// REGISTER
const register = async (req, res) => {
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
};

export { login, register };

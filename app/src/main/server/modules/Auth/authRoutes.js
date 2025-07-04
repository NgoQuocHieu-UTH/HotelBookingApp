import express from "express";
import { login, register } from "./authController.js";


const authRoutes = express.Router();

//authRoutes.post("/signup", signup);
authRoutes.post("/login", login);
authRoutes.post("/register", register);

export default authRoutes;
CREATE DATABASE IF NOT EXISTS hotelbookingapp;
USE hotelbookingapp;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100),
    gender ENUM('Nam', 'Nữ', 'Khác'),
    birth_date DATE,
    identity_number VARCHAR(20),
    phone VARCHAR(15),
    linked_account VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE hotels (
    hotel_id INT AUTO_INCREMENT PRIMARY KEY,
    hotel_name VARCHAR(150) NOT NULL,
    address TEXT,
    city VARCHAR(100),
    rating FLOAT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rooms (
    room_id INT AUTO_INCREMENT PRIMARY KEY,
    hotel_id INT NOT NULL,
    room_type VARCHAR(100),         -- Ví dụ: Phòng đơn, phòng đôi, suite...
    description TEXT,
    price_per_night DECIMAL(10,2),
    availability BOOLEAN DEFAULT TRUE, -- TRUE = còn trống, FALSE = đã đặt
    capacity INT,                      -- Sức chứa (số người)
    image_url TEXT,                   -- Link ảnh
    FOREIGN KEY (hotel_id) REFERENCES hotels(hotel_id) ON DELETE CASCADE
);

CREATE TABLE bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    room_id INT NOT NULL,
    check_in DATE NOT NULL,
    check_out DATE NOT NULL,
    total_price DECIMAL(10,2),
    booking_status ENUM('pending', 'confirmed', 'cancelled') DEFAULT 'pending',
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (room_id) REFERENCES rooms(room_id)
);
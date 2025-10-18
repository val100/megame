-- SQL Server DDL for Room Reservation App

CREATE TABLE buildings (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(200) NOT NULL,
    address NVARCHAR(400) NULL,
    created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME()
);

CREATE TABLE apartments (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    building_id BIGINT NOT NULL,
    floor INT NOT NULL,
    apartment_number NVARCHAR(50) NOT NULL,
    owner_name NVARCHAR(200) NULL,
    contact_email NVARCHAR(200) NULL,
    contact_phone NVARCHAR(50) NULL,
    is_active BIT NOT NULL DEFAULT 1,
    CONSTRAINT uq_apartment UNIQUE (building_id, apartment_number),
    CONSTRAINT fk_apartment_building FOREIGN KEY (building_id) REFERENCES buildings(id)
);

CREATE TABLE users (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    email NVARCHAR(255) NOT NULL UNIQUE,
    password_hash NVARCHAR(255) NOT NULL,
    role NVARCHAR(32) NOT NULL,
    apartment_id BIGINT NULL,
    full_name NVARCHAR(200) NULL,
    phone NVARCHAR(50) NULL,
    created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT fk_user_apartment FOREIGN KEY (apartment_id) REFERENCES apartments(id)
);

CREATE TABLE rooms (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(200) NOT NULL,
    capacity INT NOT NULL,
    description NVARCHAR(1000) NULL,
    is_active BIT NOT NULL DEFAULT 1,
    rules_json NVARCHAR(MAX) NULL
);

CREATE TYPE booking_status AS TABLE (
    status NVARCHAR(16)
);
-- Note: Using plain NVARCHAR for enums in SQL Server; enforce via check constraint

CREATE TABLE bookings (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    booking_number BIGINT NULL,
    room_id BIGINT NOT NULL,
    apartment_id BIGINT NOT NULL,
    requested_start DATETIME2 NOT NULL,
    requested_end DATETIME2 NOT NULL,
    status NVARCHAR(16) NOT NULL,
    event_type NVARCHAR(100) NULL,
    num_guests INT NULL,
    notes NVARCHAR(2000) NULL,
    created_by BIGINT NOT NULL,
    created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 NULL,
    approved_by BIGINT NULL,
    approved_at DATETIME2 NULL,
    payment_status NVARCHAR(16) NOT NULL DEFAULT 'NOT_PAID',
    CONSTRAINT fk_booking_room FOREIGN KEY (room_id) REFERENCES rooms(id),
    CONSTRAINT fk_booking_apartment FOREIGN KEY (apartment_id) REFERENCES apartments(id),
    CONSTRAINT fk_booking_created_by FOREIGN KEY (created_by) REFERENCES users(id),
    CONSTRAINT fk_booking_approved_by FOREIGN KEY (approved_by) REFERENCES users(id),
    CONSTRAINT ck_booking_time CHECK (requested_end > requested_start),
    CONSTRAINT ck_booking_status CHECK (status IN ('PENDING','APPROVED','REJECTED','CANCELLED','COMPLETED')),
    CONSTRAINT ck_booking_payment CHECK (payment_status IN ('NOT_PAID','PAID','REFUNDED'))
);

-- Indexes for overlap queries
CREATE INDEX ix_bookings_room_time ON bookings (room_id, requested_start, requested_end);
CREATE INDEX ix_bookings_status ON bookings (status);
CREATE INDEX ix_bookings_apartment ON bookings (apartment_id);

CREATE TABLE payments (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    booking_id BIGINT NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    currency NVARCHAR(8) NOT NULL,
    status NVARCHAR(16) NOT NULL,
    provider NVARCHAR(50) NOT NULL,
    transaction_id NVARCHAR(128) NULL,
    created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    processed_at DATETIME2 NULL,
    CONSTRAINT fk_payment_booking FOREIGN KEY (booking_id) REFERENCES bookings(id),
    CONSTRAINT ck_payment_status CHECK (status IN ('INIT','SUCCESS','FAILED','REFUNDED'))
);

CREATE TABLE audit_logs (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    entity_type NVARCHAR(64) NOT NULL,
    entity_id BIGINT NOT NULL,
    action NVARCHAR(64) NOT NULL,
    actor_user_id BIGINT NULL,
    details NVARCHAR(MAX) NULL,
    created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME()
);

-- Optional sequences
CREATE SEQUENCE booking_number_seq AS BIGINT START WITH 1000 INCREMENT BY 1;

# SmartLocker System

A scalable locker management backend system built in **Java** using **Object-Oriented Programming (OOP)** principles. This project simulates how e-commerce and logistics companies manage smart lockers for parcel delivery and pickup.

---

## 📌 Project Overview

SmartLocker is designed to manage:

- Locker allocation based on package size
- Package delivery to lockers
- OTP-based package pickup
- Locker release after successful pickup
- Tracking locker and package status
- Modular and clean object-oriented architecture

This project demonstrates strong **Low-Level Design (LLD)** and **Java backend development** skills.

---

## 🚀 Features

### 👤 User Features

- Deliver package to available locker
- Receive OTP for secure pickup
- Pick up parcel using OTP
- Locker auto-frees after pickup

### 🏢 Admin Features

- View locker availability
- Track occupied/free lockers
- Manage packages efficiently

---

## 🧠 OOP Concepts Used

- **Encapsulation** – Private fields with controlled access
- **Abstraction** – Clear service-layer responsibilities
- **Inheritance** – Extendable user/package models
- **Polymorphism** – Flexible locker/package behaviors

---

## 🏗️ Project Structure

```text
src/
 ├── Main.java
 ├── Locker.java
 ├── LockerSize.java
 ├── LockerStatus.java
 ├── Package.java
 ├── PackageStatus.java
 ├── User.java
 ├── SmartLockerService.java

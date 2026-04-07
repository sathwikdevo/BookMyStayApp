# Book My Stay App 🏨

## 📌 Project Overview

**Book My Stay App** is a Java-based Hotel Booking Management System demonstrating real-world use of **OOP and data structures**.

This use case focuses on implementing **safe, read-only room search functionality**.

---

## 🚀 Use Case 4: Room Search & Availability Check

### 🎯 Goal

Enable users to **view available rooms without modifying system state**, ensuring safe and consistent data access.

---

## 👤 Actors

* **Guest** – Searches for available rooms
* **Search Service** – Handles read-only operations

---

## 🔄 Application Flow

1. Guest initiates search
2. Inventory provides availability data
3. Room objects provide details
4. Unavailable rooms are filtered
5. Available rooms are displayed
6. No changes are made to inventory

---

## 🧠 Key Concepts Used

### 🔹 Read-Only Access

* Inventory is accessed without modification
* Ensures system stability

### 🔹 Defensive Programming

* Only rooms with availability > 0 are shown

### 🔹 Separation of Concerns

* Search logic separated from booking and inventory updates

### 🔹 Inventory as State Holder

* Inventory stores availability
* Search only reads from it

### 🔹 Domain Model Usage

* Room classes provide details like price and beds

### 🔹 Validation Logic

* Filters out unavailable rooms

---

## ✅ Requirements Implemented

* Centralized inventory access
* Read-only search logic
* Filtering unavailable rooms
* Display room details + availability
* No state modification

---

## 💡 Key Benefits

* Prevents accidental data modification
* Ensures accurate availability display
* Clean separation of read/write operations

---

## ⚠️ Limitations

* No booking functionality yet
* No user input
* Static data

---

## 🔄 Improvement Over Use Case 3

* Introduces clear separation between read and write operations
* Prevents accidental inventory updates during search

---

## 🛠️ How to Compile and Run

### Step 1: Compile

```bash id="cmd41"
javac UseCase4RoomSearch.java
```

### Step 2: Run

```bash id="cmd42"
java UseCase4RoomSearch
```

---

## 📤 Sample Output

```id="out41"
Welcome to Book My Stay App!
Version: v4.1

Available Rooms:

Room Type: Single Room
Beds: 1
Price: 1000.0
Available: 5

Room Type: Double Room
Beds: 2
Price: 1800.0
Available: 3

Search completed. No changes made to inventory.
```

---

## 📁 Project Structure

```id="struct41"
BookMyStayApp/
│── UseCase4RoomSearch.java
│── README.md
```

---

## 🔮 Future Enhancements

* Add booking functionality
* Prevent overbooking
* Add filters (price, beds, etc.)
* Integrate user input

---

## 👨‍💻 Author

Your Name

---

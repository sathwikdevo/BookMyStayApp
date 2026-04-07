# Book My Stay App 🏨

## 📌 Project Overview

**Book My Stay App** is a Hotel Booking Management System built using Core Java to demonstrate real-world applications of **data structures and object-oriented design**.

This use case focuses on improving system design by introducing **centralized inventory management**.

---

## 🚀 Use Case 3: Centralized Room Inventory Management

### 🎯 Goal

To replace scattered availability variables with a **centralized data structure (HashMap)** and demonstrate efficient state management.

---

## 👤 Actor

* **RoomInventory** – Manages and controls room availability across the system

---

## 🔄 Application Flow

1. Inventory system is initialized
2. Room types and availability are stored in a HashMap
3. Availability is accessed using keys (room types)
4. Updates are performed through controlled methods
5. Inventory state is displayed

---

## 🧠 Key Concepts Used

### 🔹 Problem of Scattered State

* Previous use case used multiple variables
* Difficult to maintain and scale

### 🔹 HashMap

* Stores room type → availability
* Example:

  ```java
  Map<String, Integer>
  ```

### 🔹 O(1) Lookup

* Fast access using keys
* Efficient for frequent operations

### 🔹 Single Source of Truth

* All data stored in one place
* Prevents inconsistency

### 🔹 Encapsulation

* Inventory logic hidden inside `RoomInventory` class
* Access only through methods

### 🔹 Separation of Concerns

* Inventory → manages availability
* Room → defines room details

### 🔹 Scalability

* New room type = just add a new entry
* No code changes required elsewhere

---

## ✅ Requirements Implemented

* HashMap used for storage
* Inventory initialized via constructor
* Methods for get/update/display
* Centralized availability management

---

## 💡 Key Benefits

* Consistent system state
* Fast lookups and updates
* Easy to scale and extend

---

## ⚠️ Limitations

* No validation for negative updates
* No booking workflow yet
* Console-based only

---

## 🔄 Improvement Over Use Case 2

* Eliminates multiple availability variables
* Introduces centralized control
* Improves maintainability and scalability

---

## 🛠️ How to Compile and Run

### Step 1: Compile

```bash id="cmd31"
javac UseCase3InventorySetup.java
```

### Step 2: Run

```bash id="cmd32"
java UseCase3InventorySetup
```

---

## 📤 Sample Output

```id="out31"
Welcome to Book My Stay App!
Version: v3.1

Current Room Inventory:
Single Room -> Available: 5
Double Room -> Available: 3
Suite Room -> Available: 2

Updating Single Room availability...

Current Room Inventory:
Single Room -> Available: 4
Double Room -> Available: 3
Suite Room -> Available: 2

Application finished.
```

---

## 📁 Project Structure

```id="struct31"
BookMyStayApp/
│── UseCase3InventorySetup.java
│── README.md
```

---

## 🔮 Future Enhancements

* Integrate booking system
* Prevent overbooking
* Add user input
* Use advanced data structures (Queue, Priority Queue)
* Persist data using files or database

---

## 👨‍💻 Author

Your Name

---

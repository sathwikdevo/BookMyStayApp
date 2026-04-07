# Book My Stay App 🏨

## 📌 Project Overview

**Book My Stay App** is a Java-based Hotel Booking Management System designed to demonstrate core **Object-Oriented Programming (OOP)** concepts in real-world scenarios.

This version (**Use Case 2**) focuses on modeling room types using abstraction and inheritance, while keeping availability static.

---

## 🚀 Use Case 2: Basic Room Types & Static Availability (Single File)

### 🎯 Goal

To introduce **object-oriented design** using:

* Abstract classes
* Inheritance
* Polymorphism

while keeping the implementation simple using a **single Java file**.

---

## 👤 Actor

* User (runs the program to view room details and availability)

---

## 🔄 Application Flow

1. User runs the application
2. Room objects are created (Single, Double, Suite)
3. Availability is stored in variables
4. Room details and availability are displayed
5. Application exits

---

## 🧠 Key Concepts Used

### 🔹 Abstract Class

* `Room` is an abstract class
* Defines common properties like room type, beds, and price

### 🔹 Inheritance

* `SingleRoom`, `DoubleRoom`, `SuiteRoom` extend `Room`
* Reuse common logic and specialize behavior

### 🔹 Polymorphism

* Objects are handled using `Room` reference
* Enables flexibility and scalability

### 🔹 Encapsulation

* Fields are private
* Accessed via getter methods

### 🔹 Static Availability

* Availability stored in simple variables
* Demonstrates limitations of hardcoded data

---

## ✅ Requirements Implemented

* Abstract `Room` class
* Concrete room classes
* Object initialization in `main()`
* Static availability variables
* Console output for room details

---

## 💡 Key Benefits

* Clear understanding of OOP fundamentals
* Simple and beginner-friendly structure
* Strong base for future enhancements

---

## ⚠️ Limitations

* Availability is hardcoded
* No dynamic updates or booking logic
* Not scalable for real systems

---

## 🛠️ How to Compile and Run

### Step 1: Compile

```bash id="m9q2x1"
javac UseCase2RoomInitialization.java
```

### Step 2: Run

```bash id="k4d8zp"
java UseCase2RoomInitialization
```

---

## 📤 Sample Output

```id="c7wq9a"
Welcome to Book My Stay App!
Version: v2.1

Room Type: Single Room
Beds: 1
Price: 1000.0
Available: 5

Room Type: Double Room
Beds: 2
Price: 1800.0
Available: 3

Room Type: Suite Room
Beds: 3
Price: 3000.0
Available: 2

Application finished.
```

---

## 📁 Project Structure

```id="v3p8sn"
BookMyStayApp/
│── UseCase2RoomInitialization.java
│── README.md
```

---

## 🔮 Future Enhancements

* Use arrays or collections for room storage
* Implement booking functionality
* Add user input handling
* Introduce inventory management system

---

## 👨‍💻 Author

Your Name

---

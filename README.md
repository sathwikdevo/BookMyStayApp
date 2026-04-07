Book My Stay – Use Case 5: Booking Request (First-Come-First-Served)
Overview

This project demonstrates a Hotel Booking Management System that handles booking requests in a fair, first-come-first-served manner. It focuses on core Java concepts and fundamental data structures, showing how queues can manage simultaneous requests while preserving order and fairness.

Use Case 5: Booking Request

Goal:
Handle multiple booking requests fairly by preserving the arrival order, without allocating rooms immediately.

Actors:

Reservation – Represents a guest’s booking intent.
Booking Request Queue – Stores incoming booking requests in FIFO order.

Flow:

Guest submits a booking request.
Request is added to the booking queue.
Requests are stored in arrival order.
Requests wait for processing; no inventory mutation occurs at this stage.
Key Concepts
Queue Data Structure: Queue<Reservation> using LinkedList ensures FIFO processing.
FIFO Principle: Guarantees fairness; earliest requests are processed first.
Decoupling Intake from Allocation: Requests are collected first and processed later, enabling controlled allocation during peak demand.
Key Requirements
Accept booking requests from guests.
Store requests in a queue structure.
Preserve request arrival order.
Avoid any room allocation at this stage.
Prepare requests for subsequent processing.
Benefits
Fair and deterministic booking request handling.
Predictable system behavior under peak load.
Simplified request coordination before allocation.
How to Compile and Run
javac UseCase5BookingRequestQueue.java
java UseCase5BookingRequestQueue
Sample Output
=== Welcome to Book My Stay (Booking Request Queue) ===

Options:
1. Submit Booking Request
2. View Booking Queue
3. Exit
   Enter choice: 1
   Enter guest name: Alice
   Enter number of rooms: 2
   Booking request added for Alice

Options:
1. Submit Booking Request
2. View Booking Queue
3. Exit
   Enter choice: 1
   Enter guest name: Bob
   Enter number of rooms: 1
   Booking request added for Bob

Options:
1. Submit Booking Request
2. View Booking Queue
3. Exit
   Enter choice: 2

Current Booking Queue (FIFO order):
Reservation{Guest='Alice', Rooms=2}
Reservation{Guest='Bob', Rooms=1}
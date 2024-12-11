# TicketingSystemCLI
 Command Line Interface of Real Time Ticketing System      

 ## Table of Contents
1. [Introduction](#introduction)
2. [Prerequisites](#prerequisites)
4. [Setup Instructions](#setup-instructions)
5. [Usage](#usage)

## Introduction
The Real-Time Ticketing System (CLI) is a multi-threaded console-based application that simulates ticket transactions between vendors and customers. The system ensures efficient ticket management by allowing:

1. Vendors to add tickets to a shared pool.
2. Customers to purchase tickets from the pool concurrently.
3. Proper synchronization for thread-safe operations.
   
The system also includes:
1. Configurable ticketing parameters through a JSON-based configuration file.
2. Logging of all transactions (ticket additions and purchases) in a structured format.

## Prerequisites
Java Development Kit (JDK): Version 8 or higher (tested on JDK 21).
Google Gson Library: Ensure the Gson JAR file is included in the project's lib folder.

## Setup Instructions
Download the Project:
Clone the repository to your local machine: https://github.com/RanindiFernando/TicketingSystemCLI.git

## Usage 
**Configuring the System**
1. On running the application, you'll be prompted to load the previous configuration or enter a new one:
  If you choose yes, the system will read configuration values from config.json and proceed.
  If you choose no, the system will prompt you to input:
  Total Tickets: Initial ticket count in the pool.
  Ticket Release Rate: Time interval for vendors to release tickets.
  Customer Retrieval Rate: Time interval for customers to retrieve tickets.
  Max Ticket Capacity: Maximum tickets the system can hold.

2. Configuration values are validated and saved to config.json for future use.

**Running the Simulation**
1. Once the configuration is set, the system offers the following menu:
Start Simulation: Starts the concurrent ticketing simulation with vendors adding tickets and customers purchasing tickets.
Exit Simulation: Stops all threads, saves transaction logs to transactions.json, and exits.

2. During the simulation:
Vendors and customers operate concurrently.
All transactions (additions and purchases) are logged in real-time.
The user can terminate the simulation by choosing the "Exit Simulation" option.

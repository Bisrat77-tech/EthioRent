# 🏠 EthioRent - House Rental Management System

<div align="center">

![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=java)
![OOP](https://img.shields.io/badge/OOP-Complete-blue)
![Version](https://img.shields.io/badge/Version-1.0.0-green)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

**A complete Object-Oriented House Rental Management System built with Java**

</div>

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [OOP Concepts Demonstrated](#oop-concepts-demonstrated)
- [Project Structure](#project-structure)
- [Installation & Setup](#installation--setup)
- [How to Run](#how-to-run)
- [Usage Guide](#usage-guide)
- [File Handling](#file-handling)
- [Team Members](#team-members)
- [Technologies Used](#technologies-used)
- [License](#license)

---

## 🎯 Overview

**EthioRent** is a comprehensive house rental management system developed in Java. It allows property owners to manage properties, tenants, rental agreements, and track payments efficiently. The system demonstrates core Object-Oriented Programming concepts including inheritance, polymorphism, encapsulation, abstraction, exception handling, and file persistence.

---

## ✨ Features

### 🏢 Property Management
- Add new properties (House, Apartment, Studio)
- View all properties in the system
- Search properties by ID
- Update property status (Available/Rented/Maintenance)
- Remove properties from the system

### 👤 Tenant Management
- Register new tenants
- View all registered tenants
- Search tenants by ID
- Update tenant contact information
- Remove tenants from the system

### 📄 Rental Management
- Create rental agreements between tenants and properties
- View all active rentals
- Record monthly rent payments
- Terminate rental agreements
- Automatic property status updates

### 📊 Reports & Analytics
- System summary (total properties, tenants, active rentals)
- View available properties
- Monthly revenue report
- Real-time revenue calculation

### 💾 Data Persistence
- Automatic data saving to text files
- Data loading on application startup
- Creates backup files automatically

---

## 🎓 OOP Concepts Demonstrated

| Concept | Implementation | Location |
|---------|---------------|----------|
| **Encapsulation** | Private fields with public getters/setters | All model classes |
| **Inheritance** | House, Apartment, Studio extend Property | `model/` package |
| **Polymorphism** | Overridden `displayInfo()` method | Property subclasses |
| **Abstraction** | Abstract `Property` class with abstract methods | `Property.java` |
| **Exception Handling** | Try-catch blocks for input validation & file I/O | `InputHelper.java`, `RentalSystem.java` |
| **File Handling** | Save/load data to/from text files | `RentalSystem.java` |
| **Collections Framework** | HashMap, ArrayList, Stream API | `RentalSystem.java` |

---

## 📁 Project Structure

```
EthioRent/
│
├── src/
│   ├── Main.java                 # Entry point & menu system
│   │
│   ├── model/                    # Data models (Package)
│   │   ├── PropertyStatus.java   # Enum for property states
│   │   ├── Property.java         # Abstract base class
│   │   ├── House.java            # House subclass
│   │   ├── Apartment.java        # Apartment subclass
│   │   ├── Studio.java           # Studio subclass
│   │   ├── Tenant.java           # Tenant class
│   │   └── RentalAgreement.java  # Rental agreement class
│   │
│   ├── service/                  # Business logic (Package)
│   │   └── RentalSystem.java     # Core system & file handling
│   │
│   └── utils/                    # Utilities (Package)
│       └── InputHelper.java      # Input validation helper
│
├── data/                         # Auto-created data directory
│   ├── properties.txt            # Stored property data
│   ├── tenants.txt               # Stored tenant data
│   └── rentals.txt               # Stored rental data
│
└── README.md                     # Project documentation
```

---

## 🛠 Installation & Setup

### Prerequisites

- **Java Development Kit (JDK)** 17 or higher
- **IntelliJ IDEA** (recommended) or any Java IDE
- Minimum 2GB RAM

### Step 1: Clone or Download

```bash
git clone https://github.com/Bisrat77-tech/EthioRent.git
```

### Step 2: Open in IntelliJ IDEA

1. Open IntelliJ IDEA
2. Click `File` → `Open`
3. Select the `EthioRent` folder
4. Wait for IntelliJ to index the files

### Step 3: Build the Project

- IntelliJ automatically compiles the project
- Or press `Ctrl + F9` (Windows/Linux) or `Cmd + F9` (Mac)

---

## 🚀 How to Run

### Method 1: Using IntelliJ IDEA (Recommended)

1. Navigate to `src/Main.java`
2. Right-click on the file
3. Select `Run 'Main.main()'`
4. Or click the green play button ▶️

### Method 2: Using Command Line

```bash
# Compile all Java files
javac -d ./classes -sourcepath ./src ./src/Main.java

# Run the application
java -cp ./classes Main
```

---

## 📖 Usage Guide

### Main Menu

```
============================================================
     🏠 ETHIORENT - HOUSE RENTAL MANAGEMENT SYSTEM 🏠
============================================================
1. 🏢 Manage Properties
2. 👤 Manage Tenants
3. 📄 Manage Rentals
4. 📊 View Reports
5. 🚪 Exit
============================================================
```

### Quick Start Example

#### 1. Add a Property
```
--- ADD NEW PROPERTY ---
Property Types:
1. Apartment
2. House
3. Studio
Select property type: 2
Enter Property ID: H001
Enter Address: Bole, Addis Ababa
Enter Monthly Rent (ETB): 15000
Enter Bedrooms: 3
Enter Bathrooms: 2
Enter Lot Size (sqm): 250
Has Garden? (true/false): true
✅ Property added successfully!
```

#### 2. Register a Tenant
```
--- REGISTER NEW TENANT ---
Enter Tenant ID: T001
Enter Full Name: Bisrat Zenebe
Enter Phone Number: 0912345678
Enter Email: bisrat@example.com
✅ Tenant registered successfully!
```

#### 3. Create Rental Agreement
```
--- CREATE RENTAL AGREEMENT ---
Enter Property ID: H001
Enter Tenant ID: T001
Enter Rental ID: R001
Enter Deposit Amount (ETB): 30000
Enter Lease Duration (months): 12
✅ Rental agreement created successfully!
```

#### 4. Record Payment
```
Enter Rental ID: R001
Enter Payment Amount (ETB): 15000
✅ Payment of 15000.00 ETB recorded for R001
```

---

## 💾 File Handling

The system automatically creates a `data/` folder with three text files:

### properties.txt Format
```
ID|Address|Rent|Bedrooms|Bathrooms|Status|Type|ExtraData
H001|Bole, Addis Ababa|15000|3|2|RENTED|HOUSE|250|true
```

### tenants.txt Format
```
ID|Name|Phone|Email
T001|Bisrat Zenebe|0912345678|bisrat@example.com
```

### rentals.txt Format
```
RentalID|PropertyID|TenantID|StartDate|EndDate|Deposit|IsActive
R001|H001|T001|1700000000000|1731536000000|30000|true
```

### System Summary Report
```
--- SYSTEM SUMMARY ---
🏢 Total Properties: 5
👤 Total Tenants: 3
📄 Active Rentals: 2
💰 Monthly Revenue: 35000.00 ETB
```

---

## 👥 Team Members

| # | Name              | ID               | Role      |
|---|-------------------|------------------|-----------|
| 1 | Bisrat Zenebe     | BITS/UGR/0022/26 | Developer |
| 2 | Christina Solomon | BITS/UGR/0025/26 | Developer |
| 3 | Aya Anwar         | BITS/UGR/0012/26 | Developer
| 4 | Danawit Melkamu   | BITS/UGR/00229/26| Developer | 


**Course:** Java / Object-Oriented Programming 

**Institution:** BITS College  

**Year:** 2026

---

## 🛠 Technologies Used

- **Java 25** - Core programming language
- **IntelliJ IDEA** - Primary IDE
- **Java Collections Framework** - Data storage (HashMap, ArrayList)
- **Java I/O** - File handling for data persistence
- **Java Date/Time** - Rental date management
- **Java Stream API** - Data processing for reports

---

## 🔮 Future Enhancements

- [ ] Add GUI using JavaFX or Swing
- [ ] Implement database connectivity (MySQL/PostgreSQL)
- [ ] Add payment receipt generation (PDF)
- [ ] Email notifications for rent reminders
- [ ] Mobile app integration
- [ ] Multi-user login with roles (Admin/Staff/Tenant)
- [ ] Export reports to Excel/PDF

---

## 📞 Contact

- **GitHub:** [github.com/Bisrat77-tech](https://github.com/Bisrat77-tech) 
             ,[github.com/Christina-2525](https://github.com/Christina-2525),[github.com/itzayaa](https://github.com/itzayaa)

- **Repository:** [github.com/Bisrat77-tech/EthioRent](https://github.com/Bisrat77-tech/EthioRent)

---

## 📄 License

```
MIT License

Copyright (c) 2026 BITS College - EthioRent Team

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

<div align="center">

**Developed by the EthioRent Team**

*Happy Coding! 🚀*

</div>

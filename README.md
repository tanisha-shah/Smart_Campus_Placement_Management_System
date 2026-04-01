# Smart Campus Placement Management System
### A Menu-Driven CLI Application in Java

---

## Overview

The Smart Campus Placement Management System is a fully menu-driven Command Line Interface
(CLI) application built using core Java concepts. It simulates a real-world college placement
cell where Students, Companies, and an Admin interact through structured terminal menus.

---

## Project Structure

```
SmartCampusPlacement/
│
├── src/
│   ├── Main.java                        ← Entry point
│   │
│   ├── model/                           ← Data classes (POJOs)
│   │   ├── User.java                    ← Base class (parent)
│   │   ├── Student.java                 ← Extends User
│   │   ├── Company.java                 ← Extends User
│   │   ├── Admin.java                   ← Extends User
│   │   ├── Drive.java                   ← Campus drive details
│   │   ├── Application.java             ← Student's application
│   │   └── Notification.java            ← System notifications
│   │
│   ├── service/                         ← Business logic
│   │   ├── UserService.java             ← Register, login, fetch users
│   │   ├── DriveService.java            ← Post, fetch, filter drives
│   │   ├── ApplicationService.java      ← Apply, update status
│   │   ├── NotificationService.java     ← Send, read notifications
│   │   └── ReportService.java           ← Generate reports (with threads)
│   │
│   ├── cli/                             ← Menu-driven interface
│   │   ├── AuthMenu.java                ← Register/Login for all roles
│   │   ├── StudentMenu.java             ← Student functionalities
│   │   ├── CompanyMenu.java             ← Company functionalities
│   │   └── AdminMenu.java               ← Admin functionalities
│   │
│   ├── utils/                           ← Helper classes
│   │   ├── FileHelper.java              ← All file read/write operations
│   │   └── UIHelper.java                ← Terminal formatting & IDs
│   │
│   └── exceptions/                      ← Custom exceptions
│       ├── InvalidLoginException.java
│       ├── UserAlreadyExistsException.java
│       ├── DriveNotFoundException.java
│       └── AlreadyAppliedException.java
│
├── out/                                 ← Compiled .class files (auto-created)
├── data/                                ← Data storage text files (auto-created)
│   ├── students.txt
│   ├── companies.txt
│   ├── admins.txt
│   ├── drives.txt
│   ├── applications.txt
│   └── notifications.txt
│
├── compile_and_run.sh                   ← Linux/Mac compile + run script
├── compile.bat                          ← Windows compile script
└── README.md
```

---

## How to Compile and Run

### Prerequisites
- Java JDK 8 or higher installed
- A terminal / command prompt

### On Linux or macOS:
```bash
# Give execute permission
chmod +x compile_and_run.sh

# Compile and run in one step
./compile_and_run.sh
```

### On Windows:
```batch
# Step 1: Compile
compile.bat

# Step 2: Run (from the out/ directory)
cd out
java Main
```

### Manual Compilation (any OS):
```bash
# 1. Create output folder
mkdir out

# 2. Compile all source files
javac -d out -sourcepath src src/Main.java src/model/*.java src/service/*.java src/cli/*.java src/utils/*.java src/exceptions/*.java

# 3. Run (from out/ directory so data/ is created there)
cd out
java Main
```

---

## Default Admin Login

The system creates a default admin account automatically on first launch:
- **Email:** admin@campus.edu
- **Password:** admin123

---

## Features by Role

### Student
| Feature | Description |
|---|---|
| Register | Create a new account with CGPA, branch, skills |
| Login | Authenticate with email + password |
| View All Drives | See all active campus drives |
| View Eligible Drives | Only shows drives matching your CGPA, branch, backlogs |
| Apply for Drive | Apply to any eligible drive |
| Track Applications | See status: APPLIED / SHORTLISTED / REJECTED |
| Skill Gap Analysis | Compare your skills with job requirements |
| Resume Score | Score out of 100 based on CGPA + skills + backlogs |
| Notifications | Receive alerts for new drives and status updates |
| Manage Skills | Add or remove skills from your profile |

### Company
| Feature | Description |
|---|---|
| Register | Create a company account |
| Login | Authenticate |
| Post Drive | Create a new drive with eligibility criteria & skills |
| View My Drives | See all drives posted by your company |
| View Applicants | See who applied, with full profile details |
| Shortlist / Reject | Update application status, student gets notified |

### Admin
| Feature | Description |
|---|---|
| Login | Admin-only authentication |
| View All Students | See every registered student |
| View All Companies | See every registered company |
| View All Drives | Monitor all drives across all companies |
| View All Applications | System-wide applications with counts |
| View Student Profile | Deep-dive into any student's profile |
| Summary Report | System statistics with multithreading simulation |

---

## Java Concepts Used

| Concept | Where Used |
|---|---|
| Classes & Objects | All model, service, cli, utils classes |
| Constructors | All model classes have parameterized constructors |
| Encapsulation | Private fields + getters/setters in all models |
| Inheritance | Student, Company, Admin all extend User |
| Dynamic Method Dispatch | getDisplayInfo() overridden in each subclass |
| Exception Handling | try-catch in all menus; 4 custom exception classes |
| File Handling | FileHelper.java reads/writes all data to .txt files |
| Multithreading | ReportService uses Thread for async report generation |
| ArrayList | Used throughout for storing lists of users, drives, etc. |
| Scanner | Used in all CLI menus for user input |

---

## Data Storage Format

Data is stored as plain text CSV files in the `data/` directory.

**students.txt**
```
userId,name,email,password,STUDENT,branch,cgpa,backlogs,skill1;skill2
```

**drives.txt**
```
driveId,companyId,companyName,jobRole,ctc,minCgpa,maxBacklogs,date,location,jobType,status,branches,skills
```

**applications.txt**
```
appId,studentId,studentName,driveId,companyName,jobRole,status,date
```

---

## Sample Walkthrough

1. Run the application
2. Choose **Student** → **Register**
   - Enter: Name, Email, Password, Branch (CSE), CGPA (8.5), Backlogs (0), Skills (java, python)
3. You're now logged in as a student
4. Choose **View Eligible Drives** to see matching drives
5. Choose **Apply for a Drive** and enter the Drive ID
6. Log out, then log in as **Admin** (admin@campus.edu / admin123)
7. View all students, drives, and applications
8. Log in as a **Company**, post a drive
9. Log back in as the student — you'll see a notification about the new drive!

---

## Resume Score Formula

```
Score = (CGPA / 10.0 × 50)       → Up to 50 points
      + (number of skills × 5)    → Up to 40 points  
      + (10 if zero backlogs)     → 10 bonus points
                                  = Max 100 points
```

---

*Built as a beginner-friendly Java project demonstrating OOP principles.*

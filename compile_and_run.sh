#!/bin/bash
# ============================================================
#  Smart Campus Placement System - Compile & Run (Linux/Mac)
# ============================================================

echo "Compiling Smart Campus Placement System..."
echo ""

# Create output directory
mkdir -p out

# Compile all Java source files
javac -d out -sourcepath src \
    src/Main.java \
    src/model/User.java \
    src/model/Student.java \
    src/model/Company.java \
    src/model/Admin.java \
    src/model/Drive.java \
    src/model/Application.java \
    src/model/Notification.java \
    src/service/UserService.java \
    src/service/DriveService.java \
    src/service/ApplicationService.java \
    src/service/NotificationService.java \
    src/service/ReportService.java \
    src/cli/AuthMenu.java \
    src/cli/StudentMenu.java \
    src/cli/CompanyMenu.java \
    src/cli/AdminMenu.java \
    src/utils/FileHelper.java \
    src/utils/UIHelper.java \
    src/exceptions/InvalidLoginException.java \
    src/exceptions/UserAlreadyExistsException.java \
    src/exceptions/DriveNotFoundException.java \
    src/exceptions/AlreadyAppliedException.java

if [ $? -eq 0 ]; then
    echo ""
    echo "[SUCCESS] Compilation successful!"
    echo ""
    echo "Starting the application..."
    echo ""
    # Run from out/ directory so data/ folder is created there
    cd out && java Main
else
    echo ""
    echo "[ERROR] Compilation failed. Check errors above."
    exit 1
fi

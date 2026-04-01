@echo off
REM ============================================================
REM  Smart Campus Placement System - Compile Script (Windows)
REM ============================================================

echo Compiling Smart Campus Placement System...
echo.

REM Create output directory
if not exist "out" mkdir out

REM Compile all Java files
javac -d out -sourcepath src ^
    src\Main.java ^
    src\model\User.java ^
    src\model\Student.java ^
    src\model\Company.java ^
    src\model\Admin.java ^
    src\model\Drive.java ^
    src\model\Application.java ^
    src\model\Notification.java ^
    src\service\UserService.java ^
    src\service\DriveService.java ^
    src\service\ApplicationService.java ^
    src\service\NotificationService.java ^
    src\service\ReportService.java ^
    src\cli\AuthMenu.java ^
    src\cli\StudentMenu.java ^
    src\cli\CompanyMenu.java ^
    src\cli\AdminMenu.java ^
    src\utils\FileHelper.java ^
    src\utils\UIHelper.java ^
    src\exceptions\InvalidLoginException.java ^
    src\exceptions\UserAlreadyExistsException.java ^
    src\exceptions\DriveNotFoundException.java ^
    src\exceptions\AlreadyAppliedException.java

if %errorlevel% == 0 (
    echo.
    echo [SUCCESS] Compilation successful!
    echo Run:  java -cp out Main
) else (
    echo.
    echo [ERROR] Compilation failed. Check errors above.
)
pause

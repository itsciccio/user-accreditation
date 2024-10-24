:: run.bat for Windows
@echo off
call mvnw clean package -DskipTests
java -jar target/user_accreditation-1.0.0.jar
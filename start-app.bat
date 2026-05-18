@echo off
echo Starting Backend Server...
start cmd /k "cd backend && mvn spring-boot:run"

echo Starting Frontend Server...
start cmd /k "cd frontend && npm run dev"

echo Both servers are starting in new windows!

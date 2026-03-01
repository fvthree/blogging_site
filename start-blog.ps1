# PostgreSQL Blog Setup - Quick Start Script
# Run this script in PowerShell to start your blog with PostgreSQL

Write-Host "=====================================" -ForegroundColor Cyan
Write-Host "  FVTHREE Blog - PostgreSQL Setup  " -ForegroundColor Cyan
Write-Host "=====================================" -ForegroundColor Cyan
Write-Host ""

# Check if Docker is running
Write-Host "Checking Docker..." -ForegroundColor Yellow
$dockerRunning = docker info 2>&1 | Out-Null
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Docker is not running!" -ForegroundColor Red
    Write-Host "Please start Docker Desktop and try again." -ForegroundColor Red
    exit 1
}
Write-Host "✓ Docker is running" -ForegroundColor Green
Write-Host ""

# Stop any existing containers
Write-Host "Stopping existing containers..." -ForegroundColor Yellow
docker-compose down 2>&1 | Out-Null
Write-Host "✓ Cleaned up" -ForegroundColor Green
Write-Host ""

# Build and start services
Write-Host "Building and starting services..." -ForegroundColor Yellow
Write-Host "This may take a few minutes on first run..." -ForegroundColor Cyan
docker-compose up --build -d

if ($LASTEXITCODE -eq 0) {
    Write-Host "✓ Services started successfully!" -ForegroundColor Green
    Write-Host ""

    # Wait for services to be healthy
    Write-Host "Waiting for services to be healthy..." -ForegroundColor Yellow
    Start-Sleep -Seconds 10

    # Check status
    Write-Host ""
    Write-Host "Service Status:" -ForegroundColor Cyan
    docker-compose ps

    Write-Host ""
    Write-Host "=====================================" -ForegroundColor Green
    Write-Host "  🎉 Blog is ready!  " -ForegroundColor Green
    Write-Host "=====================================" -ForegroundColor Green
    Write-Host ""
    Write-Host "Blog URL: http://localhost:8086/" -ForegroundColor Cyan
    Write-Host "Health Check: http://localhost:8086/actuator/health" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Database Info:" -ForegroundColor Yellow
    Write-Host "  Host: localhost" -ForegroundColor White
    Write-Host "  Port: 5432" -ForegroundColor White
    Write-Host "  Database: velzon_blog" -ForegroundColor White
    Write-Host "  User: velzon_user" -ForegroundColor White
    Write-Host "  Password: velzon_secure_password_123" -ForegroundColor White
    Write-Host ""
    Write-Host "Useful Commands:" -ForegroundColor Yellow
    Write-Host "  View logs: docker-compose logs -f velzon-app" -ForegroundColor White
    Write-Host "  Stop services: docker-compose down" -ForegroundColor White
    Write-Host "  Restart: docker-compose restart" -ForegroundColor White
    Write-Host ""

    # Try to open browser
    Write-Host "Opening blog in browser..." -ForegroundColor Yellow
    Start-Sleep -Seconds 5
    Start-Process "http://localhost:8086/"

} else {
    Write-Host "ERROR: Failed to start services!" -ForegroundColor Red
    Write-Host "Check logs with: docker-compose logs" -ForegroundColor Yellow
    exit 1
}


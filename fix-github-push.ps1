# Quick Fix for GitHub Push Protection
# This script removes the Mapbox token from git history

Write-Host "=====================================" -ForegroundColor Cyan
Write-Host "  GitHub Push Protection Fix        " -ForegroundColor Cyan
Write-Host "  Removing Mapbox Tokens from Git   " -ForegroundColor Cyan
Write-Host "=====================================" -ForegroundColor Cyan
Write-Host ""

# Check if we're in a git repository
if (-not (Test-Path .git)) {
    Write-Host "ERROR: Not in a git repository!" -ForegroundColor Red
    exit 1
}

Write-Host "Current Status:" -ForegroundColor Yellow
git status --short
Write-Host ""

# Ask user to confirm
Write-Host "This will:" -ForegroundColor Yellow
Write-Host "  1. Stage all changes" -ForegroundColor White
Write-Host "  2. Amend the last commit (removing secrets)" -ForegroundColor White
Write-Host "  3. Force push to remove secrets from GitHub" -ForegroundColor White
Write-Host ""
Write-Host "WARNING: This will rewrite git history!" -ForegroundColor Red
Write-Host ""

$confirm = Read-Host "Continue? (yes/no)"
if ($confirm -ne "yes") {
    Write-Host "Aborted." -ForegroundColor Yellow
    exit 0
}

Write-Host ""
Write-Host "Step 1: Staging changes..." -ForegroundColor Yellow
git add .

if ($LASTEXITCODE -eq 0) {
    Write-Host "✓ Changes staged" -ForegroundColor Green
} else {
    Write-Host "ERROR: Failed to stage changes" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Step 2: Amending last commit..." -ForegroundColor Yellow
git commit --amend --no-edit

if ($LASTEXITCODE -eq 0) {
    Write-Host "✓ Commit amended" -ForegroundColor Green
} else {
    Write-Host "ERROR: Failed to amend commit" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Step 3: Force pushing..." -ForegroundColor Yellow
Write-Host "Note: If this fails, the secret might be in older commits" -ForegroundColor Cyan

$branch = git branch --show-current
Write-Host "Pushing to branch: $branch" -ForegroundColor Cyan

git push --force-with-lease

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "=====================================" -ForegroundColor Green
    Write-Host "  ✓ SUCCESS!                       " -ForegroundColor Green
    Write-Host "=====================================" -ForegroundColor Green
    Write-Host ""
    Write-Host "Secrets removed from git history!" -ForegroundColor Green
    Write-Host ""
    Write-Host "Next steps:" -ForegroundColor Yellow
    Write-Host "  1. Create config: cp src/main/resources/static/assets/js/pages/leaflet-map-config.example.js src/main/resources/static/assets/js/pages/leaflet-map-config.js" -ForegroundColor White
    Write-Host "  2. Edit config with your Mapbox token" -ForegroundColor White
    Write-Host "  3. The config file is in .gitignore (won't be committed)" -ForegroundColor White
    Write-Host ""
} else {
    Write-Host ""
    Write-Host "=====================================" -ForegroundColor Red
    Write-Host "  ✗ Push Failed                    " -ForegroundColor Red
    Write-Host "=====================================" -ForegroundColor Red
    Write-Host ""
    Write-Host "The secret might be in older commits." -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Options:" -ForegroundColor Yellow
    Write-Host "  1. See GITHUB_PUSH_PROTECTION_FIX.md for advanced cleaning" -ForegroundColor White
    Write-Host "  2. Use GitHub's allow URL (if token is not sensitive)" -ForegroundColor White
    Write-Host "  3. Revoke the token and create a new one" -ForegroundColor White
    Write-Host ""
    exit 1
}


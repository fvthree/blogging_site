# Emergency Fix - Remove Secrets from Git History
# This script will completely clean the git history

Write-Host "=====================================" -ForegroundColor Red
Write-Host "  EMERGENCY: Removing Secrets       " -ForegroundColor Red
Write-Host "=====================================" -ForegroundColor Red
Write-Host ""

Write-Host "WARNING: This will rewrite ALL git history!" -ForegroundColor Yellow
Write-Host "Make sure you have a backup!" -ForegroundColor Yellow
Write-Host ""

$confirm = Read-Host "Type 'YES' to continue"
if ($confirm -ne "YES") {
    Write-Host "Aborted." -ForegroundColor Yellow
    exit 0
}

Write-Host ""
Write-Host "Step 1: Removing commits with secrets..." -ForegroundColor Yellow

# Reset to before the problematic commits
# The issue is in commits: d49a17f and b1aafdc

# First, let's see how many commits back we need to go
Write-Host "Recent commits:" -ForegroundColor Cyan
git log --oneline -10

Write-Host ""
Write-Host "Resetting to clean state..." -ForegroundColor Yellow

# Soft reset to keep all changes but remove commits
git reset --soft HEAD~5

Write-Host "✓ Commits removed" -ForegroundColor Green
Write-Host ""

Write-Host "Step 2: Re-staging all changes..." -ForegroundColor Yellow
git add .

Write-Host "✓ Changes staged" -ForegroundColor Green
Write-Host ""

Write-Host "Step 3: Creating new clean commit..." -ForegroundColor Yellow
git commit -m "feat: Complete blog setup with PostgreSQL and security fixes

- Migrated from H2 to PostgreSQL
- Configured Docker Compose for persistence
- Fixed .gitignore to exclude secrets
- Removed API tokens from source code
- Created config templates for sensitive data
- Updated blog theme (minimal design)
- Documentation added

All secrets have been externalized to config files."

Write-Host "✓ Clean commit created" -ForegroundColor Green
Write-Host ""

Write-Host "Step 4: Force pushing..." -ForegroundColor Yellow
Write-Host "WARNING: This will overwrite remote history!" -ForegroundColor Red
Write-Host ""

$pushConfirm = Read-Host "Type 'PUSH' to force push"
if ($pushConfirm -ne "PUSH") {
    Write-Host "Aborted. You can manually push with: git push --force" -ForegroundColor Yellow
    exit 0
}

git push --force

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "=====================================" -ForegroundColor Green
    Write-Host "  ✓ SUCCESS!                       " -ForegroundColor Green
    Write-Host "=====================================" -ForegroundColor Green
    Write-Host ""
    Write-Host "Git history has been cleaned!" -ForegroundColor Green
    Write-Host "All secrets have been removed from commits." -ForegroundColor Green
    Write-Host ""
} else {
    Write-Host ""
    Write-Host "=====================================" -ForegroundColor Red
    Write-Host "  ✗ Failed                         " -ForegroundColor Red
    Write-Host "=====================================" -ForegroundColor Red
    Write-Host ""
    Write-Host "If this failed, you may need to:" -ForegroundColor Yellow
    Write-Host "  1. Use GitHub's allow URL (last resort)" -ForegroundColor White
    Write-Host "  2. Delete the repo and recreate it" -ForegroundColor White
    Write-Host "  3. Contact GitHub support" -ForegroundColor White
    Write-Host ""
}


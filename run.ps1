Write-Host "Compiling ATM Interface..." -ForegroundColor Cyan
javac -sourcepath src -d out src/app/Main.java
if ($LASTEXITCODE -eq 0) {
    Write-Host "Launching ATM Application..." -ForegroundColor Green
    java -cp out app.Main
} else {
    Write-Host "Compilation failed." -ForegroundColor Red
}

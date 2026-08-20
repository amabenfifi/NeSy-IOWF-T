Write-Host "=== NeSy-IOWF++_T Deployment Script ===" -ForegroundColor Cyan

# 1. Construction de la nouvelle image Docker
Write-Host "Building Docker image: nesy-iowf-t..."
docker build --no-cache -t nesy-iowf-t:latest .
if ($LASTEXITCODE -ne 0) {
    Write-Error "Failed to build Docker image."
    exit $LASTEXITCODE
}

# 2. Nettoyage des anciens conteneurs pour éviter les conflits de nom et de port
Write-Host "Nettoyage des anciens conteneurs..."
docker stop nesy-iowf-t-container 2>$null
docker rm nesy-iowf-t-container 2>$null

# 3. Création du dossier local pour les logs (s'il n'existe pas déjà)
if (-Not (Test-Path -Path "D:\VDCROBDD\logs")) {
    New-Item -ItemType Directory -Force -Path "D:\VDCROBDD\logs" | Out-Null
}
# Nettoyage automatique des anciens résultats de simulation
if (Test-Path -Path "D:\VDCROBDD\logs\nesy_metrics.csv") {
    Remove-Item -Path "D:\VDCROBDD\logs\nesy_metrics.csv" -Force
}

# 4. Démarrage du conteneur avec le Volume Partagé (Bind Mount)
Write-Host "Démarrage du conteneur nesy-iowf-t-container sur le port 8080 avec volume partagé..."
docker run -d -p 8080:8080 -v "D:\VDCROBDD\logs:/app/logs" --name nesy-iowf-t-container nesy-iowf-t:latest

if ($LASTEXITCODE -eq 0) {
    Write-Host "Succès ! Le conteneur est en cours d'exécution." -ForegroundColor Green
    
    # 5. Affichage des logs en direct
    Write-Host "Affichage des logs en direct (Faites Ctrl+C pour quitter les logs) :" -ForegroundColor Yellow
    docker logs -f nesy-iowf-t-container
}
else {
    Write-Error "Échec lors du démarrage du conteneur."
    exit $LASTEXITCODE
}
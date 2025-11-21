#!/bin/bash
set -e

APP_DIR="/opt/app"
BACKUP_DIR="/opt/app/backups"
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
if [ -f "$APP_DIR/app.jar" ]; then
  echo "[INFO SSH] Generated backup of app.jar"
  mv $APP_DIR/app.jar $BACKUP_DIR/app_$TIMESTAMP.jar
  if [ $? -ne 0 ]; then
    echo "[ERROR SSH] Error to create backup of app.jar"
    exit 1
  elif [ -f "/tmp/app.jar" ]; then
    echo "[INFO SSH] Backup created at $BACKUP_DIR/app_$TIMESTAMP.jar"
    echo "[INFO SSH] Deploying new app.jar"
    mv /tmp/app.jar $APP_DIR/app.jar
    supervisorctl restart appspringboot
    if [ $? -ne 0 ]; then
      echo "[ERROR SSH] Error to deploy and restart the application."
      exit 1
    else
      echo "[INFO SSH] Application deployed and restarted successfully."
    fi
  else
    echo "[ERROR SSH] New app.jar file not found in /tmp directory."
    exit 1
  fi
fi
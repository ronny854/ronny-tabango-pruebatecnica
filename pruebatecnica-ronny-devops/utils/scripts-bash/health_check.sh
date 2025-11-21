#!/bin/bash

HEALTH_URL="http://localhost:8080/actuator/health"

echo "[INFO SSH] Verifying application health status at $HEALTH_URL"

for i in {1..10}; do
    RESPONSE=$(curl -s $HEALTH_URL)

    echo "Intento $i → $RESPONSE"

    if echo "$RESPONSE" | grep -q "\"status\":\"UP\""; then
        echo "✔ App is healthy"
        exit 0
    fi

    sleep 3
done

echo "❌ App is not healthy after multiple attempts."
exit 1

cd /root/srv
trap 'kill $(jobs -p)' EXIT; until ./env/bin/fastapi run server.py & wait; do
    echo "fastapi server crashed with exit code $?. Respawning.." >&2
    sleep 1
done

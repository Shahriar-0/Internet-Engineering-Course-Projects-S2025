@echo off
setlocal enabledelayedexpansion
set CLUSTER_NAME=dev-cluster

:: Create kind cluster
echo Creating kind cluster...
kind create cluster --name %CLUSTER_NAME% --config cluster-config.yaml
if errorlevel 1 (
    echo ERROR: Failed to create kind cluster
    exit /b 1
)

echo Waiting for cluster to be ready...
kubectl wait --for=condition=Ready nodes --all --timeout=300s
if errorlevel 1 (
    echo ERROR: Cluster did not become ready
    exit /b 1
)
echo Kind cluster created successfully

:: Create ConfigMap and Secrets
echo Creating ConfigMap and Secrets...
kubectl create secret generic db-secret-env --from-env-file=mysql.env
kubectl create secret generic server-secret-env --from-env-file=server.env
kubectl create configmap webclient-config --from-env-file=webclient.env
if errorlevel 1 (
    echo ERROR: Failed to create configmap or secrets
    exit /b 1
)
echo ConfigMap and Secrets created successfully

:: Create PV and PVC
echo Creating PersistentVolume and PersistentVolumeClaim...
kubectl apply -f db-pv.yaml
kubectl apply -f db-pvc.yaml
if errorlevel 1 (
    echo ERROR: Failed to apply PV or PVC
    exit /b 1
)
echo PV and PVC created successfully

:: Deploy MySQL
echo Deploying MySQL...
kubectl apply -f db-deployment.yaml
kubectl apply -f mysql-service.yaml
if errorlevel 1 (
    echo ERROR: Failed to deploy MySQL
    exit /b 1
)
echo MySQL deployed successfully

:: Deploy backend server
echo Deploying backend server...
kubectl apply -f server-deployment.yaml
kubectl apply -f miobook-service.yaml
if errorlevel 1 (
    echo ERROR: Failed to deploy backend server
    exit /b 1
)
echo Backend server deployed successfully

:: Deploy web client
echo Deploying web client...
kubectl apply -f webclient-deploy.yaml
kubectl apply -f nginx-service.yaml
if errorlevel 1 (
    echo ERROR: Failed to deploy web client
    exit /b 1
)
echo Web client deployed successfully

echo All components deployed successfully.
exit /b 0

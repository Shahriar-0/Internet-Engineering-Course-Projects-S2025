#!/bin/bash

set -e

CLUSTER_NAME="dev-cluster"


function create_cluster() {
  echo "Creating kind cluster..."
  kind create cluster --name $CLUSTER_NAME --config cluster-config.yaml
  if [ $? -ne 0 ]; then
    echo "Failed to create kind cluster"
    exit 1
  fi
  echo "Waiting for cluster to be ready..."
  kubectl wait --for=condition=Ready nodes --all --timeout=300s
  echo "Kind cluster created successfully"
}

function create_configmap_and_secrets() {
  echo "Creating ConfigMap and Secret..."
  kubectl create secret generic db-secret-env --from-env-file=mysql.env
  kubectl create secret generic server-secret-env --from-env-file=server.env
  kubectl create configmap webclient-config --from-env-file=webclient.env
  echo "ConfigMap and Secret created successfully"
}

function create_pv_and_pvc() {
  echo "Creating PersistentVolume and PersistentVolumeClaim..."
  kubectl apply -f db-pv.yaml
  kubectl apply -f db-pvc.yaml
  echo "PersistentVolume and PersistentVolumeClaim created successfully"
}

function deploy_mysql() {
  echo "Deploying MySQL..."
  kubectl apply -f db-deployment.yaml
  kubectl apply -f mysql-service.yaml
  echo "MySQL deployed successfully"
}

function deploy_server() {
  echo "Deploying Server..."
  kubectl apply -f server-deployment.yaml
  kubectl apply -f miobook-service.yaml
  echo "Server deployed successfully"
}

function deploy_webclient() {
  echo "Deploying Web Client..."
  kubectl apply -f webclient-deploy.yaml
  kubectl apply -f nginx-service.yaml
  echo "Web Client deployed successfully"
}

function main() {
  create_cluster
  create_configmap_and_secrets
  create_pv_and_pvc
  deploy_mysql
  deploy_server
  deploy_webclient
  exit 0
}

main

# Setup Blackbox exporter 

## Prerequisites 

  * prometheus setup with helm

## Walkthrough 

```
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm install my-prometheus-blackbox-exporter prometheus-community/prometheus-blackbox-exporter --version 8.17.0

```

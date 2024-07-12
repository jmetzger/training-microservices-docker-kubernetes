# Setup Blackbox exporter 

## Prerequisites 

  * prometheus setup with helm

## Step 1: Setup

```
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm install my-prometheus-blackbox-exporter prometheus-community/prometheus-blackbox-exporter --version 8.17.0 --namespace monitoring --create-namespace

```

## Step 2: Find SVC 

```
kubectl -n monitoring get svc | grep blackbox
```

```
my-prometheus-blackbox-exporter   ClusterIP   10.245.183.66    <none>        9115/TCP              
```


## Step 3: Test with Curl 

```
kubectl run -it --rm curltest --image=curlimages/curl -- sh 
```

```
# Testen nach google in shell von curl
curl http://my-prometheus-blackbox-exporter.monitoring:9115/probe?target=google.com&module=http_2xx
```

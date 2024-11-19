# Welchen Weg der Installation (Welches System) ? 

# Überblick der Systeme 

## General 

```
kubernetes itself has not convenient way of doing specific stuff like 
creating the kubernetes cluster.

So there are other tools/distri around helping you with that.

```

## Kubeadm

### General 

  * The official CNCF (https://www.cncf.io/) tool for provisioning Kubernetes clusters
    (variety of shapes and forms (e.g. single-node, multi-node, HA, self-hosted))
  * Most manual way to create and manage a cluster 

### Disadvantages 

  * Plugins sind oftmals etwas schwierig zu aktivieren

## microk8s 

### General

  * Created by Canonical (Ubuntu)
  * Runs on Linux
  * Runs only as snap
  * In the meantime it is also available for Windows/Mac
  * HA-Cluster 

### Production-Ready ? 

  * Short answer: YES 

```
Quote canonical (2020):

MicroK8s is a powerful, lightweight, reliable production-ready Kubernetes distribution. It is an enterprise-grade Kubernetes distribution that has a small disk and memory footprint while offering carefully selected add-ons out-the-box, such as Istio, Knative, Grafana, Cilium and more. Whether you are running a production environment or interested in exploring K8s, MicroK8s serves your needs.

Ref: https://ubuntu.com/blog/introduction-to-microk8s-part-1-2

```

### Advantages

  * Easy to setup HA-Cluster (multi-node control plane)
  * Easy to manage 

## minikube 

### Disadvantages
  
  * Not usable / intended for production 

### Advantages 

  * Easy to set up on local systems for testing/development (Laptop, PC) 
  * Multi-Node cluster is possible 
  * Runs und Linux/Windows/Mac
  * Supports plugin (Different name ?)


## k3s

  * Sehr schlanker Footprint
  * Sehr einfach und schnell zu installieren (ein binary)
  * Auch für die Produktion geeignet.
  * Sinnvoll für den Einsatz auf sehr ressourcenarmen Systemen 

## kind (Kubernetes-In-Docker)

### General 

  * Runs in docker container 


### For Production ?

```
Having a footprint, where kubernetes runs within docker 
and the applikations run within docker as docker containers
it is not suitable for production.
```



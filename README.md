# Microservices mit Docker und Kubernetes 

## Agenda 

 1. Einführung 
    * [Über Microservices](/microservices/monolith-vs-microservice.md)
    * [Praxisbeispiele](/microservices/praxisbeispiele.md) 
    * Abgrenzung 
 
 1. Praxis Microservices ohne Docker und Kubernetes 
    * [Schritt 1: Nodejs aufsetzen](microservices/exercise/01-node.md)
    * [Schritt 2: Codebasis bereitsstellen](microservices/exercise/02-retrieve-code.md)
    * [Schritt 3: Posts - Service testen](microservices/exercise/03-posts-test-unser-erster-service.md)
 
 1. Grundwissen Microservices (Teil 1)
    * Architektur und Schichten
    * Teamentwicklung 
    * Devops
    * REST Api 
    * Messaging
    
  1. Docker-Grundlagen 
     * [Übersicht Architektur](architektur.md)
     * [Was ist ein Container ?](container.md)
     * [Was sind container images](container-images.md) 
     * [Container vs. Virtuelle Maschine](container-vs-vm.md)
     * [Was ist ein Dockerfile](dockerfile.md) 
  
  1. Docker-Installation
     * [BEST for Ubuntu : Install Docker from Docker Repo](install-docker-ubuntu-apt.md) 
  
  1. Docker-Befehle 
     * [Die wichtigsten Befehle](docker-befehle.md)
     * [Logs anschauen - docker logs - mit Beispiel nginx](docker-logs-nginx.md)
     * [docker run](docker-run.md)
     * [Docker container/image stoppen/löschen](container-image-delete.md)
     * [Docker containerliste anzeigen](container-liste.md)
     * [Docker nicht verwendete Images/Container löschen](delete-everything.md)
     * [Docker container analysieren](docker-inspect.md)
     * [Docker container in den Vordergrund bringen - attach](/docker/docker-attach.md) 
     * [Aufräumen - container und images löschen](prune-container-images.md)
     * [Nginx mit portfreigabe laufen lassen](docker-example-nginx.md)    
  
 1. Dockerfile - Examples 
    * [Ubuntu mit hello world](ubuntu-hello-world.md)
    * [Ubuntu mit ping](ubuntu-ping.md) 
    * [Nginx mit content aus html-ordner](nginx-html-content.md)
  
 1. Docker - Projekt blog
    * [posts in blog dockerisieren](microservices/exercise/dockerize-posts.md)
  
 1. Kubernetes - Überblick
    * [Warum Kubernetes, was macht Kubernetes](warum-kubernetes.md) 
    * [Aufbau Allgemein](/kubernetes/architecture.md)
    * [Aufbau mit helm,OpenShift,Rancher(RKE),microk8s](/kubernetes/aufbau-helm-microk8s-kubernetes.md)
    * [Welches System ? (minikube, micro8ks etc.)](welches-system.md)
  
 1. Kubernetes mit microk8s (Installation und Management)
    * [Installation Ubuntu - snap](microk8s/installation-ubuntu-snap.md)
    * [Create a cluster with microk8s](microk8s/cluster.md)
    * [Remote-Verbindung zu Kubernetes (microk8s) einrichten](microk8s/connect-from-remote.md)
    * [Bash completion installieren](kubectl/bash-completion.md)
    * [vim support for yaml](vim/vim-yaml.md)
    
 1. Kubernetes Praxis API-Objekte 
    * [Das Tool kubectl (Devs/Ops) - Spickzettel](/kubectl/spickzettel.md)
    * [kubectl example with run](/kubectl/run-with-example.md)
    * [Bauen einer Applikation mit Resource Objekten](bauen-einer-webanwendung.md)
    * [kubectl/manifest/pod](/kubectl-examples/01-pod-nginx.md)
    * ReplicaSets (Theorie) - (Devs/Ops)
    * [kubectl/manifest/replicaset](/kubectl-examples/01a-replicaset-nginx.md)
    * Deployments (Devs/Ops)
    * [kubectl/manifest/deployments](/kubectl-examples/03-nginx-deployment.md)
    * [Services - Aufbau](/kubernetes/services-aufbau.md)
    * [kubectl/manifest/service](/kubectl-examples/03b-service.md)
    * DaemonSets (Devs/Ops)
    * [Hintergrund Ingress](/kubernetes/ingress.md) 
    * [Ingress Controller auf Digitalocean (doks) mit helm installieren](/digitalocean/ingress-auf-digitalocean-mit-helm.md)
    * [Documentation for default ingress nginx](https://kubernetes.github.io/ingress-nginx/user-guide/nginx-configuration/configmap/)
    * [Beispiel Ingress](/kubectl-examples/04-ingress-nginx.md)
    * [Install Ingress On Digitalocean DOKS](/digitalocean/install-ingress-helm.md)
    * [Beispiel mit Hostnamen](/kubectl-examples/04-ingress-nginx-with-hostnames.md)
    * [Achtung: Ingress mit Helm - annotations](/ingress-mit-helm-class-achtung.md)
    * [Permanente Weiterleitung mit Ingress](/kubectl-examples/05-ingress-permanent-redirect.md)
    * [ConfigMap Example](/kubectl-examples/06-configmap.md)
    * [Configmap MariaDB - Example](kubectl-examples/06a-configmap-mariadb.md)
    * [Configmap MariaDB my.cnf](kubectl-examples/06b-mariadb-configmap-configfile.md)
 
 1. Kubernetes (Debugging)
     * [Netzwerkverbindung zu pod testen](/tipps-tricks/verbindung-zu-pod-testen.md)
 
 1. Grundwissen Microservices (Teil 2)
    * [Brainstorming Domäne](microservices/brainstorming-domaene.md)
    * [Datenbank - Patterns - Teil 1](microservices/database-patterns-teil1.md)
    * [Datenbank - Patterns - Teil 2](microservices/database-patterns-teil2.md)
    * [Strategische Patterns](microservices/strategic-patterns.md)
    * Tests
    * Skalierbarkeit 
    * Log Aggregation 
    * Performance Monitoring 
    * Demos 
    * Intergrationsstrategie  
    * [Monolith schneiden microservices](microservices/monolith-schneiden.md)

 1. Docker
    * Einsatz und Abgrenzung
    * Docker im Microservice Context
    * DockerHub
    * build
    * tag
    * Sicherheitsaspekte
    * CI / CD

 1. Kubernetes (Teil 1) 
    * Orchestierung mit K8s
    * Architektur und Komponenten
    * Master, Worker
    * kubectl

 1. Kubernetes Resourcen / Objekte 
    * Pods
    * Deployment
    * Replicas
    * Labels und Selektoren
    * Scaling
    
 1. Kubernetes (Teil 2) 
    * Kubernetes Cloud Provider

 1. Ausblick

## Backlog  

  1. Grundlagen microservices 
     * [Monolithische Architektur vs. Microservices Architektur](/microservices/monolithic-vs-microservices.md)
     * [EventBus Implementierungen/Überblick](/microservices/eventbus/overview.md)

  1. Kubernetes GUI
     * [OpenLens](/kubernetes/gui/openlens.md) 

  1. Docker-Grundlagen 
     * [Übersicht Architektur](architektur.md)
     * [Was ist ein Container ?](container.md)
     * [Was sind container images](container-images.md) 
     * [Container vs. Virtuelle Maschine](container-vs-vm.md)
     * [Was ist ein Dockerfile](dockerfile.md) 
  
  1. Docker-Installation
     * [BEST for Ubuntu : Install Docker from Docker Repo](install-docker-ubuntu-apt.md) 
  
  1. Docker-Befehle 
     * [Die wichtigsten Befehle](docker-befehle.md)
     * [Logs anschauen - docker logs - mit Beispiel nginx](docker-logs-nginx.md)
     * [docker run](docker-run.md)
     * [Docker container/image stoppen/löschen](container-image-delete.md)
     * [Docker containerliste anzeigen](container-liste.md)
     * [Docker nicht verwendete Images/Container löschen](delete-everything.md)
     * [Docker container analysieren](docker-inspect.md)
     * [Docker container in den Vordergrund bringen - attach](/docker/docker-attach.md) 
     * [Aufräumen - container und images löschen](prune-container-images.md)
     * [Nginx mit portfreigabe laufen lassen](docker-example-nginx.md)    
  
  1. Dockerfile - Examples 
     * [Ubuntu mit hello world](ubuntu-hello-world.md)
     * [Ubuntu mit ping](ubuntu-ping.md) 
     * [Nginx mit content aus html-ordner](nginx-html-content.md)
  
  1. Docker-Netzwerk 
     * [Netzwerk](network.md)
  
  1. Docker-Container Examples 
     * [2 Container mit Netzwerk anpingen](2-containers-with-network-ping.md)
     * [Container mit eigenem privatem Netz erstellen](container-with-own-bridge.md)  
  
  1. Docker-Daten persistent machen / Shared Volumes 
     * [Überblick](storage-overview.md) 
     * [Volumes](storage-volumes.md) 
     * [bind-mounts](docker-bind-mount.md)
     
  1. Docker Compose
     * [yaml-format](yaml-format.md)
     * [Ist docker-compose installiert?](docker-compose-installed.md) 
     * [Example with Wordpress / MySQL](example-wordpress-mysql.md)
     * [Example with Wordpress / Nginx / MariadB](example-wnm-docker-compose.md)
     * [Example with Ubuntu and Dockerfile](example-docker-compose-ubuntu-build.md)
     * [Logs in docker - compose](docker-compose-logs.md)
     * [docker-compose und replicas](docker-compose-replicas.md)
     * [docker compose Reference](https://docs.docker.com/compose/compose-file/compose-file-v3/)
  
  1. Docker Security 
     * [Docker Security](docker/security/overview.md)
     * [Scanning docker image with docker scan/snyx](docker/security/docker-scan-snyk.md) 

  1. Docker - Dokumentation 
     * [Vulnerability Scanner with docker](https://docs.docker.com/engine/scan/#prerequisites)
     * [Vulnerability Scanner mit snyk](https://snyk.io/plans/)
     * [Parent/Base - Image bauen für Docker](https://docs.docker.com/develop/develop-images/baseimages/)

  1. Kubernetes - microk8s (Installation und Management) 
     * [Ingress controller in microk8s aktivieren](microk8s/ingress.md) 
         
  1. Helm (Kubernetes Paketmanager) 
     * [Helm Grundlagen](/helm/grundlagen.md)
     * [Helm Warum ?](/helm/warum.md)
     * [Helm Example](/helm/example.md)

  1. Kubernetes - RBAC 
     * [Nutzer einrichten microk8s ab kubernetes 1.25](/kubernetes/rbac-create-user-kubernetes-1-25.md) 
 
  1. Kubernetes - Netzwerk (CNI's) / Mesh
     * [Netzwerk Interna](/kubernetes-networks/networking-internal-overview.md)
     * [Übersicht Netzwerke](/kubernetes-networks/overview.md) 
     * [Calico - nginx example NetworkPolicy](/kubernetes-network/callico/00-simple-example.md)
     * [Beispiele Ingress Egress NetworkPolicy](kubernetes-networks/examples-ingress-egress.md)
     * [Mesh / istio](sammlung-istio.md)  
     * [Kubernetes Ports/Protokolle](https://kubernetes.io/docs/reference/networking/ports-and-protocols/)
     * [IPV4/IPV6 Dualstack](https://kubernetes.io/docs/concepts/services-networking/dual-stack/)
   
  1. kubectl 
     * [Start pod (container with run && examples)](/kubectl/run-with-example.md)
     * [Bash completion for kubectl](/kubectl/bash-completion.md)
     * [kubectl Spickzettel](/kubectl/spickzettel.md)
     * [Tipps&Tricks zu Deploymnent - Rollout](/kubectl/rollout.md) 

  1. Kubernetes - Shared Volumes 
     * [Shared Volumes with nfs](shared-volumes/nfs-multi.md)

  1. Kubernetes - Wartung / Debugging 
     * [kubectl drain/uncordon](/kubectl/uncordon-drain.md)
     * [Alte manifeste konvertieren mit convert plugin](/kubectl/convert-plugin.md)
     * [Curl from pod api-server](/kubernetes-advanced/curl-api-server.md)

  1. Kubernetes - Tipps & Tricks 
     * [Kubernetes Debuggen ClusterIP/PodIP](/tipps-tricks/cluster-ip-debug.md)
     * [Debugging pods](tipps-tricks/debugging-pods.md)
     * [Taints und Tolerations](kubernetes/taints-tolerations.md)
     * [Autoscaling Pods/Deployments](/kubernetes/autoscaling.md)

  1. Kubernetes Advanced 
     * [Curl api-server kubernetes aus pod heraus](kubernetes-advanced/curl-api-server.md)

  1. Kubernetes - Documentation 
     * [Documentation zu microk8s plugins/addons](https://microk8s.io/docs/addons)  
     * [Shared Volumes - Welche gibt es ?](https://kubernetes.io/docs/concepts/storage/volumes/)

  1. Kubernetes - Hardening 
     * [Kubernetes Tipps Hardening](kubernetes-security/tipps-hardening.md)
     * [Kubernetes Security Admission Controller Example](kubernetes-security/pod-security-admission.md)
     
  1. Kubernetes Interna / Misc.
     * [OCI,Container,Images Standards](docker-alternatives-kubernetes.md)
     * [Geolocation Kubernetes Cluster](https://learnk8s.io/bite-sized/connecting-multiple-kubernetes-clusters)
     
  1. Documentation 
     * [Good Doku with Tasks](https://kubernetes.io/docs/tasks/configure-pod-container/security-context/)

  
## Backlog

  1. Docker-Installation
     * [Installation Docker unter Ubuntu mit snap](install-ubuntu-snap.md)
     * [Installation Docker unter SLES 15](install-sles15-zypper.md)
  
  1. Dockerfile - Examples 
     * [Nginx mit content aus html-ordner](nginx-html-content.md)
     * [ssh server](ubuntu-ssh-server.md)
  
  1. Docker-Container Examples 
     * [2 Container mit Netzwerk anpingen](2-containers-with-network-ping.md)
     * [Container mit eigenem privatem Netz erstellen](container-with-own-bridge.md)  
  
  1. Docker-Netzwerk 
     * [Netzwerk](network.md)
     
  1. Docker Security 
     * [Scanning docker image with docker scan/snyx](docker/security/docker-scan-snyk.md) 
  
  1. Docker Compose
     * [yaml-format](yaml-format.md)
     * [Example with Ubuntu and Dockerfile](example-docker-compose-ubuntu-build.md)
     * [docker-compose und replicas](docker-compose-replicas.md)
     * [docker compose Reference](https://docs.docker.com/compose/compose-file/compose-file-v3/)
  
  1. Docker Swarm 
     * [Docker Swarm Beispiele](docker-swarm-examples.md)

  1. Docker - Dokumentation 
     * [Vulnerability Scanner with docker](https://docs.docker.com/engine/scan/#prerequisites)
     * [Vulnerability Scanner mit snyk](https://snyk.io/plans/)
     * [Parent/Base - Image bauen für Docker](https://docs.docker.com/develop/develop-images/baseimages/)
    
  1. Kubernetes - Überblick
     * [Installation - Welche Komponenten from scratch](/kubernetes/installation-components-overview.md)

  1. Kubernetes - microk8s (Installation und Management) 
     * [kubectl unter windows - Remote-Verbindung zu Kuberenets (microk8s) einrichten](kubectl-windows.md)
     * [Arbeiten mit der Registry](microk8s/registry.md)
     * [Installation Kubernetes Dashboard](/microk8s/dashboard.md) 

  1. Kubernetes - RBAC 
     * [Nutzer einrichten - kubernetes bis 1.24](/kubernetes/rbac-create-user.md) 
     
  1. kubectl 
     * [Tipps&Tricks zu Deploymnent - Rollout](/kubectl/rollout.md) 
     
  1. Kubernetes - Monitoring (microk8s und vanilla) 
     * [metrics-server aktivieren (microk8s und vanilla)](/microk8s/metrics-server.md)

  1. Kubernetes - Backups 
     + [Kubernetes Aware Cloud Backup - kasten.io](/backups/cluster-backup-kasten-io.md)

  1. Kubernetes - Tipps & Tricks 
     * [Assigning Pods to Nodes](/tipps-tricks/pods-2-nodes.md) 

  1. Kubernetes - Documentation 
     * [LDAP-Anbindung](https://github.com/apprenda-kismatic/kubernetes-ldap)
     * [Helpful to learn - Kubernetes](https://kubernetes.io/docs/tasks/)
     * [Environment to learn](https://killercoda.com/killer-shell-cks)
     * [Environment to learn II](https://killercoda.com/)
     * [Youtube Channel](https://www.youtube.com/watch?v=01qcYSck1c4)

  1. Kubernetes -Wann / Wann nicht 
     * [Kubernetes Wann / Wann nicht](kubernetes/wann-kubernetes-wann-nicht.md)

  1. Kubernetes - Hardening 
     * [Kubernetes Tipps Hardening](kubernetes-security/tipps-hardening.md)

  1. Kubernetes Deployment Scenarios 
     * [Deployment green/blue,canary,rolling update](/kubernetes/deployment-strategies-en.md)
     * [Praxis-Übung A/B Deployment](/kubectl-examples/08-ab-deployment.md)

  1. Kubernetes Probes (Liveness and Readiness) 
     * [Übung Liveness-Probe](/probes/uebung-liveness.md)
     * [Funktionsweise Readiness-Probe vs. Liveness-Probe](/probes/readiness.md) 
       
  1. Linux und Docker Tipps & Tricks allgemein 
     * [Auf ubuntu root-benutzer werden](sudo.md)
     * [IP - Adresse abfragen](ip-a.md)
     * [Hostname setzen](hostname.md)
     * [Proxy für Docker setzen](proxy-docker.md)
     * [vim einrückung für yaml-dateien](/vim/vim-yaml.md)
     * [YAML Linter Online](http://www.yamllint.com/)
     * [Läuft der ssh-server](ssh-running.md)
     * [Basis/Parent - Image erstellen](docker-base-image.md)
     * [Eigenes unsichere Registry-Verwenden. ohne https](insecure-registry.md)
     
  1. VirtualBox Tipps & Tricks 
     * [VirtualBox 6.1. - Ubuntu für Kubernetes aufsetzen ](virtualbox-ubuntu.md)
     * [VirtualBox 6.1. - Shared folder aktivieren](virtualbox-shared-folders.md)

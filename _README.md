# Microservices mit Docker und Kubernetes 


## Agenda
  1. Grundlagen
     * [Microservices-Trends 2026](#microservices-trends-2026)
     * [Was sind Microservices ?](#was-sind-microservices-)
     * [Best Practices fuer Multi-Cluster- und Hybrid-Umgebungen](#best-practices-fuer-multi-cluster--und-hybrid-umgebungen)
     * [Grundkonzepte von Microservices](#grundkonzepte-von-microservices)
     * [Architektur von Microservices (Schichten/Layers)](#architektur-von-microservices-schichtenlayers)
     * [12 factor app](#12-factor-app)
     * [Monolith vs. Microservices](#monolith-vs-microservices)
     * [Praxisbeispiele](#praxisbeispiele)
     * [Was ist devops](#was-ist-devops)
     * [Was ist ein API Gateway](#was-ist-ein-api-gateway)
     * [Microservice and Database](#microservice-and-database)

  1. Analyse Monolith / Microservice
     * [Indikatoren für Microservices (Wechsel von Monolith)](#indikatoren-für-microservices-wechsel-von-monolith)

  1. Grundwissen Microservices (Teil 2)
     * [Brainstorming Domäne](#brainstorming-domäne)
     * [Datenbank - Patterns - Teil 1](#datenbank---patterns---teil-1)
     * [Datenbank - Patterns - Teil 2](#datenbank---patterns---teil-2)
     * [Strategische Patterns](#strategische-patterns)
     * [Tests - Uebersicht](#tests---uebersicht)
     * [Monolith schneiden microservices](#monolith-schneiden-microservices)
     * [IAM als Bounded Context — fachlich oder technisch?](#iam-als-bounded-context-—-fachlich-oder-technisch)
     * [Authentication in Kubernetes (Kunde / Mobile App)](#authentication-in-kubernetes-kunde--mobile-app)
     * [API Gateway vs. Istio Service Mesh – Authentication](#api-gateway-vs-istio-service-mesh-–-authentication)
     * [JWT mit Keycloak und Istio — Login-Flow, Client Credentials, Validierung](#jwt-mit-keycloak-und-istio-—-login-flow-client-credentials-validierung)
     * [Datenmigration: Notification Service (Dual Write, Outbox, Backfill)](#datenmigration-notification-service-dual-write-outbox-backfill)

  1. Micro-Frontends
     * [Micro-Frontends — Teams am Frontend ohne Kollisionen](#micro-frontends-—-teams-am-frontend-ohne-kollisionen)
     * [Micro-Frontends — Kommunikation zwischen MFEs](#micro-frontends-—-kommunikation-zwischen-mfes)
     * [Micro-Frontends — Module Federation (Webpack/Vite, TypeScript)](#micro-frontends-—-module-federation-webpackvite-typescript)

  1. Übungen: Monolith schneiden
     * [Uebung: Monolith schneiden — DDD, Bounded Contexts und Strangler Fig](#uebung-monolith-schneiden-—-ddd-bounded-contexts-und-strangler-fig)
     * [Auswertung: EventStorming — ShopMax](#auswertung-eventstorming-—-shopmax)
     * [Auswertung: Bounded Contexts — ShopMax](#auswertung-bounded-contexts-—-shopmax)
     * [Weiterfuehrende Schritte: Monolith schneiden (Schritt 3–7)](#weiterfuehrende-schritte-monolith-schneiden-schritt-3–7)
     * [Musterloesung: Monolith schneiden mit DDD und Strangler Fig (Trainer)](#musterloesung-monolith-schneiden-mit-ddd-und-strangler-fig-trainer)

  1. Grundwissen Microservices - Synchrones Messaging
     * [gRPC vs. REST API](#grpc-vs-rest-api)
     * [API-Abfrage über REST-API](#api-abfrage-über-rest-api)
     * [OpenAPI-Spec aus Code generieren (Go, Python, Java, TS, Rust, C#, PHP)](#openapi-spec-aus-code-generieren-go-python-java-ts-rust-c#-php)
    
  1. Grundwissen Microservices - Async Messaging
     * [Asynchrones Messaging](#asynchrones-messaging)
     * [EventBus Implementierungen/Überblick](#eventbus-implementierungenüberblick)
     * [Kafka Schaubild](#kafka-schaubild)
     * [Schema Registry (confluent)](#schema-registry-confluent)
     * [Uebung: Kafka Schema Registry — Avro und Schema-Evolution](#uebung-kafka-schema-registry-—-avro-und-schema-evolution)
     * [Topic/Queue ohne Downtime migrieren](#topicqueue-ohne-downtime-migrieren)
     * [Disruptive Änderungen im Schema migrieren](#disruptive-änderungen-im-schema-migrieren)

  1. Grundwissen Microservices - Fehlertolaranz
     * [Circuit-Breaker und Fehlertoleranz](#circuit-breaker-und-fehlertoleranz)
  
  1. Grundwissen Microservices - Tests (Teil 3)
      * [Testing-Strategie: Was, wieviel, wann?](microservices/tests/00-testing-uebersicht.md)
      * [Static Tests](microservices/tests/01-testing-static.md)
      * [Unit-Tests](microservices/tests/02-testing-unit.md)
      * [Integration Testing mit Testcontainers](microservices/tests/03-testing-integration-testcontainers.md)
      * [Contract Testing mit OpenAPI](microservices/tests/04-testing-contract-openapi.md)
      * [Consumer-Driven Contract Testing mit Pact](microservices/tests/05-testing-contract-pact.md)
      * [End-to-End - e2e - Tests](microservices/tests/06-testing-e2e.md)
      * [Integration in GitLab CI/CD](microservices/tests/07-testing-ci-cd-gitlab.md)

  1. Linux Tipps & Tricks
     * [In den Root-Benutzer wechseln](#in-den-root-benutzer-wechseln)
 
  1. Docker-Grundlagen 
     * [Übersicht Architektur](#übersicht-architektur)
     * [Was ist ein Container ?](#was-ist-ein-container-)
     * [Was sind container images](#was-sind-container-images)
     * [Container vs. Virtuelle Maschine](#container-vs-virtuelle-maschine)
     * [Was ist ein Dockerfile](#was-ist-ein-dockerfile)
  
  1. Docker-Installation
     * [BEST for Ubuntu : Install Docker from Docker Repo](#best-for-ubuntu--install-docker-from-docker-repo)

  1. Docker-Praxis
     * [Docker run mit nginx](#docker-run-mit-nginx)
     * [Die wichtigsten Befehle](#die-wichtigsten-befehle)
     * [Aufräumen - container und images löschen](#aufräumen---container-und-images-löschen)
     * [Logs des Host-Systems zu den Containern auslesen](#logs-des-host-systems-zu-den-containern-auslesen)
     * [Logs anschauen - docker logs - mit Beispiel nginx](#logs-anschauen---docker-logs---mit-beispiel-nginx)
     * [Nginx mit portfreigabe laufen lassen](#nginx-mit-portfreigabe-laufen-lassen)
    
  1. Example with Dockerfile
     * [Ubuntu mit ping](#ubuntu-mit-ping)
     * [Slim multistage-build](#slim-multistage-build)
    
  1. Docker Security 
     * [Docker Security](#docker-security)
     * [Scanning docker image with docker scan/snyx(Deprecated)](#scanning-docker-image-with-docker-scansnyxdeprecated)
    
  1. Docker Compose
     * [Ist docker compose installiert?](#ist-docker-compose-installiert)
     * [Example with Wordpress / MySQL](#example-with-wordpress--mysql)
     * [Example with Ubuntu and Dockerfile](#example-with-ubuntu-and-dockerfile)
     * [Logs in docker - compose](#logs-in-docker---compose)
     * [docker compose Reference](https://docs.docker.com/compose/compose-file/compose-file-v3/)
    
  1. Docker - compose (Testprojekte)
     * [Testprojekt mit api und mongodb](#testprojekt-mit-api-und-mongodb)

  1. Microservices - Daten
     * [Überblick shared database / database-per-service](#überblick-shared-database--database-per-service)
     * [Umgang mit Joins bei database-per-service](#umgang-mit-joins-bei-database-per-service)
     * [Umgang mit Transaktionen bei database-per-service (SAGA)](#umgang-mit-transaktionen-bei-database-per-service-saga)
     * [Uebung: SAGA-Pattern mit Temporal (Docker Compose, Java)](#uebung-saga-pattern-mit-temporal-docker-compose-java)
     * [Apache Camel (EIP) vs. Temporal — Vergleich und Entscheidungshilfe](#apache-camel-eip-vs-temporal-—-vergleich-und-entscheidungshilfe)
     * [Event Sourcing](#event-sourcing)
    
  1. Microservice - flightapp - concepts
     * [Vorgehensweise nach dem SEED-Verfahren](#vorgehensweise-nach-dem-seed-verfahren)
     * [Vorgehensweise nach SEED on Detail](#vorgehensweise-nach-seed-on-detail)
     * [SEED vs. DDD mit EventStorming — Wann nehme ich was?](#seed-vs-ddd-mit-eventstorming-—-wann-nehme-ich-was)

  1. Microservice - flightapp - reservations 
     * [Template for microservice with python flask ](#template-for-microservice-with-python-flask-)
     * [Create microservice - reservations](#create-microservice---reservations)
     * [Upload image microservice - reservations](#upload-image-microservice---reservations)
     * [Build image reservations with gitlab ci/cd](#build-image-reservations-with-gitlab-cicd)

  1. Microservice - flightapp - flights
     * [Template for microservice flights with node bootstrap](#template-for-microservice-flights-with-node-bootstrap)
     * [Build flight app](#build-flight-app)
     * [Use premade version of flight app - with fixes already](#use-premade-version-of-flight-app---with-fixes-already)
     * [Upload image flight app](#upload-image-flight-app)

  1. Microservice - flightapp - Deployment Kubernetes
     * [Manual deployment](#manual-deployment)
     * [gitlab Deployment](#gitlab-deployment)
     * [github Deployment](#github-deployment)
     * [github Deployment-with-secret-not-working](#github-deployment-with-secret-not-working)

  1. Microservice - flightapp - Uebungen: Manuell in Kubernetes deployen
     * [Uebung: ms-reservations manuell deployen und Service erstellen](#uebung-ms-reservations-manuell-deployen-und-service-erstellen)
     * [Loesung: Service fuer ms-reservations](#loesung-service-fuer-ms-reservations)

  1. Kubernetes - Überblick
     * [Warum Kubernetes, was macht Kubernetes](#warum-kubernetes-was-macht-kubernetes)
     * [Aufbau Allgemein](#aufbau-allgemein)
     * [Structure Kubernetes Deep Dive](https://github.com/jmetzger/training-kubernetes-advanced/assets/1933318/1ca0d174-f354-43b2-81cc-67af8498b56c)
     * [Ausbaustufen Kubernetes](#ausbaustufen-kubernetes)
     * [Aufbau mit helm,OpenShift,Rancher(RKE),microk8s](#aufbau-mit-helmopenshiftrancherrkemicrok8s)
     * [Welches System ? (minikube, micro8ks etc.)](#welches-system--minikube-micro8ks-etc)

  1. Kubernetes - Einsatz
     * [Kubernetes Einsatz -> Risiken](#kubernetes-einsatz-->-risiken)
     * [Kubernetes Datenbanken in Kubernetes oder ausserhalb](#kubernetes-datenbanken-in-kubernetes-oder-ausserhalb)
    
  1. Kubernetes mit microk8s (Installation und Management)
     * [Installation Ubuntu - snap](#installation-ubuntu---snap)
     * [Create a cluster with microk8s](#create-a-cluster-with-microk8s)
     * [Remote-Verbindung zu Kubernetes (microk8s) einrichten](#remote-verbindung-zu-kubernetes-microk8s-einrichten)

  1. Kubernetes mit k3s
     * [Kubernetes mit k3s](#kubernetes-mit-k3s)

  1. Kubernetes - Client Tools und Verbindung einrichten
     * [Tools installieren und bash-completion / syntax highlightning](#tools-installieren-und-bash-completion--syntax-highlightning)
     * [Remote-Verbindung zu Kubernetes einrichten](#remote-verbindung-zu-kubernetes-einrichten)
     * [Tool zum Konvertion von docker-compose.yaml file manifesten](#tool-zum-konvertion-von-docker-composeyaml-file-manifesten)
    
  1. Kubernetes Praxis API-Objekte 
     * [Das Tool kubectl (Devs/Ops) - Spickzettel](#das-tool-kubectl-devsops---spickzettel)
     * [kubectl example with run](#kubectl-example-with-run)
     * [Bauen einer Applikation mit Resource Objekten](#bauen-einer-applikation-mit-resource-objekten)
     * [Anatomie einer Webanwendung](#anatomie-einer-webanwendung)
     * [kubectl/manifest/pod](#kubectlmanifestpod)
     * ReplicaSets (Theorie) - (Devs/Ops)
     * [kubectl/manifest/replicaset](#kubectlmanifestreplicaset)
     * Deployments (Devs/Ops)
     * [kubectl/manifest/deployments](#kubectlmanifestdeployments)
     * [Services - Aufbau](#services---aufbau)
     * [kubectl/manifest/service](#kubectlmanifestservice)
     * DaemonSets (Devs/Ops)
     * [Hintergrund Ingress](#hintergrund-ingress)
     * [Ingress Controller auf Digitalocean (doks) mit helm installieren](#ingress-controller-auf-digitalocean-doks-mit-helm-installieren)
     * [Documentation for default ingress nginx](https://kubernetes.github.io/ingress-nginx/user-guide/nginx-configuration/configmap/)
     * [Beispiel Ingress](#beispiel-ingress)
     * [Install Ingress On Digitalocean DOKS](#install-ingress-on-digitalocean-doks)
     * [Beispiel Ingress mit Hostnamen](#beispiel-ingress-mit-hostnamen)
     * [Achtung: Ingress mit Helm - annotations](#achtung-ingress-mit-helm---annotations)
     * [Permanente Weiterleitung mit Ingress](#permanente-weiterleitung-mit-ingress)
     * [ConfigMap Example](#configmap-example)
     * [Configmap MariaDB - Example](#configmap-mariadb---example)
     * [Configmap MariaDB my.cnf](#configmap-mariadb-mycnf)
     * [Secret MariaDB - Example](#secret-mariadb---example)
     * [Secrets aus HashiCorp Vault - 3 Wege](#secrets-aus-hashicorp-vault---3-wege)
     * [Security und Compliance im Betrieb von Kubernetes-Clustern](#security-und-compliance-im-betrieb-von-kubernetes-clustern)

  1. Kubernetes Praxis (Teil 2) - API Objekte 
     * [Hintergrund Statefulsets](#hintergrund-statefulsets)
     * [Übung Statefulsets](#übung-statefulsets)

  1. Kubernetes Praxis (Teil 3)
     * [Using private registry](#using-private-registry)

  1. Kubernetes Ingress
     * [Ingress Controller on Detail](#ingress-controller-on-detail)
     * [Traefik mit Helm installieren](#traefik-mit-helm-installieren)
     * [Beispiel Ingress Traefik mit Hostnamen](#beispiel-ingress-traefik-mit-hostnamen)
     * [Https/LetsEncrypt mit Traefik](#httpsletsencrypt-mit-traefik)

  1. ServiceMesh
     * [Istio — Service Mesh Überblick](#istio-—-service-mesh-überblick)
     * [Why a ServiceMesh ?](#why-a-servicemesh-)
     * [How does a ServiceMeshs work? (example istio](#how-does-a-servicemeshs-work-example-istio)
     * [istio security features](#istio-security-features)
     * [istio-service mesh - ambient mode](#istio-service-mesh---ambient-mode)
     * [istio-traffic-management](#istio-traffic-management)
     * [Performance comparison - baseline,sidecar,ambient](#performance-comparison---baselinesidecarambient)
     * [Übung: JWT-Token mit RBAC (RequestAuthentication + AuthorizationPolicy)](#übung-jwt-token-mit-rbac-requestauthentication-+-authorizationpolicy)
      
  1. Kubernetes (Debugging)
     * [Netzwerkverbindung zu pod testen](#netzwerkverbindung-zu-pod-testen)
    
  1. Kubernetes Netzwerk 
     * [DNS - Resolution - Services](#dns---resolution---services)

  1. Kubernetes Scaling / Resource Management 
     * [Autoscaling Pods/Deployments](#autoscaling-podsdeployments)
     * [Resources and Limits for containers](#resources-and-limits-for-containers)
     * [ResourceQuota und LimitRange im Namespace (Uebung)](#resourcequota-und-limitrange-im-namespace-uebung)
     * [ResourceQuotas and LimitQuotas by Namespace](https://kubernetes.io/docs/tasks/administer-cluster/manage-resources/quota-memory-cpu-namespace/)

  1. Kubernetes Tipps & Tricks
     * [Oomkiller and maxReadySeconds for safe migration to new pods](#oomkiller-and-maxreadyseconds-for-safe-migration-to-new-pods)
     * [Pod-Netzwerk debuggen durch weiteren Pod der daneben liegt kubectl debug](#pod-netzwerk-debuggen-durch-weiteren-pod-der-daneben-liegt-kubectl-debug)
     * [Aus pod mit curl api-server abfragen](#aus-pod-mit-curl-api-server-abfragen)

  1. Kubernetes - Monitoring 
     * [metrics-server aktivieren (microk8s und vanilla)](#metrics-server-aktivieren-microk8s-und-vanilla)
     * [Prometheus Überblick](#prometheus-überblick)
     * [Prometheus Kubernetes Stack installieren](#prometheus-kubernetes-stack-installieren)
     * [Prometheus - Services scrapen die keine Endpunkte für Prometheus haben](#prometheus---services-scrapen-die-keine-endpunkte-für-prometheus-haben)
    
  1. Kubernetes Storage (CSI) 
     * [Überblick Persistant Volumes (CSI)](#überblick-persistant-volumes-csi)
     * [Liste der Treiber mit Features (CSI)](https://kubernetes-csi.github.io/docs/drivers.html)
     * [Übung Persistant Storage](#übung-persistant-storage)
     * [Beispiel mariadb](#beispiel-mariadb)

  1. Helm
     * [Helm internals / secret a.s.o](#helm-internals--secret-aso)
    
  1. Kubernetes with ServerLess
     * [Kubernetes with Serverless](#kubernetes-with-serverless)

  1. Literatur / Documentation / Information (Microservices)
     * [Sam Newman - Microservices](https://www.amazon.de/Building-Microservices-English-Sam-Newman-ebook/dp/B09B5L4NVT/)
     * [Sam Newman - Vom Monolithen zu Microservices](https://www.amazon.de/Vom-Monolithen-Microservices-bestehende-umzugestalten/dp/3960091400/)
     * [Microservices.io Patterns](https://microservices.io)
     * [BFF](https://blog.bitsrc.io/bff-pattern-backend-for-frontend-an-introduction-e4fa965128bf)
     * [Microservices Up and Running](https://www.amazon.de/Kubernetes-Running-Dive-Future-Infrastructure/dp/109811020X/ref=sr_1_1)

  1. FAQ
     * [Verschlüsselung mit Thales docker-container](#verschlüsselung-mit-thales-docker-container)
    
  1. gitlab ci/cd
     * [Einfaches Beispielscript](#einfaches-beispielscript)
     * [Docker image bauen mit fastapi (python) und kaniko](#docker-image-bauen-mit-fastapi-python-und-kaniko)
       
## Backlog
 
  1. Praxis Microservices ohne Docker und Kubernetes 
     * [Schritt 1: Nodejs aufsetzen](#schritt-1-nodejs-aufsetzen)
     * [Schritt 2: Codebasis bereitsstellen](#schritt-2-codebasis-bereitsstellen)
     * [Schritt 3: Posts - Service testen](#schritt-3-posts---service-testen)

  1. Docker-Installation
     * [Installation Docker unter Ubuntu mit snap](#installation-docker-unter-ubuntu-mit-snap)
     * [Installation Docker unter SLES 15](#installation-docker-unter-sles-15)
  
  1. Docker-Grundlagen 
     * [Übersicht Architektur](#übersicht-architektur)
     * [Was ist ein Container ?](#was-ist-ein-container-)
     * [Was sind container images](#was-sind-container-images)
     * [Container vs. Virtuelle Maschine](#container-vs-virtuelle-maschine)
     * [Was ist ein Dockerfile](#was-ist-ein-dockerfile)
  
  1. Docker-Befehle 
     * [Logs anschauen - docker logs - mit Beispiel nginx](#logs-anschauen---docker-logs---mit-beispiel-nginx)
     * [Docker container/image stoppen/löschen](#docker-containerimage-stoppenlöschen)
     * [Docker containerliste anzeigen](#docker-containerliste-anzeigen)
     * [Docker nicht verwendete Images/Container löschen](#docker-nicht-verwendete-imagescontainer-löschen)
     * [Docker container analysieren](#docker-container-analysieren)
     * [Docker container in den Vordergrund bringen - attach](#docker-container-in-den-vordergrund-bringen---attach)
     * [Aufräumen - container und images löschen](#aufräumen---container-und-images-löschen)
     * [Nginx mit portfreigabe laufen lassen](#nginx-mit-portfreigabe-laufen-lassen)
     * [Docker container/image stoppen/löschen](#docker-containerimage-stoppenlöschen)
     * [Docker containerliste anzeigen](#docker-containerliste-anzeigen)
  
  1. Dockerfile - Examples 
     * [Ubuntu mit hello world](#ubuntu-mit-hello-world)
     * [Ubuntu mit ping](#ubuntu-mit-ping)
     * [Nginx mit content aus html-ordner](#nginx-mit-content-aus-html-ordner)
     * [Ubuntu mit hello world](#ubuntu-mit-hello-world)
     * [Nginx mit content aus html-ordner](#nginx-mit-content-aus-html-ordner)
 
  1. Docker-Netzwerk 
     * [Netzwerk](#netzwerk)
  
  1. Docker-Container Examples 
     * [2 Container mit Netzwerk anpingen](#2-container-mit-netzwerk-anpingen)
     * [Container mit eigenem privatem Netz erstellen](#container-mit-eigenem-privatem-netz-erstellen)
  
  1. Docker-Daten persistent machen / Shared Volumes 
     * [Überblick](#überblick)
     * [Volumes](#volumes)
     * [bind-mounts](#bind-mounts)
     
  1. Docker - Dokumentation 
     * [Vulnerability Scanner with docker](https://docs.docker.com/engine/scan/#prerequisites)
     * [Vulnerability Scanner mit snyk](https://snyk.io/plans/)
     * [Parent/Base - Image bauen für Docker](https://docs.docker.com/develop/develop-images/baseimages/)
        
  1. Docker - Projekt blog
     * [posts in blog dockerisieren](#posts-in-blog-dockerisieren)

  1. Docker Compose (backlog)
     * [yaml-format](#yaml-format)
     * [docker-compose und replicas](#docker-compose-und-replicas)
     * [Example with Wordpress / Nginx / MariadB - wrong](#example-with-wordpress--nginx--mariadb---wrong)
   
  1. Kubernetes Netzwerk 
     * [Mesh / istio](#mesh--istio)
     * [pubsub+ for graph kafka](https://solace.com/blog/how-a-financial-services-giant-cleaned-up-their-kafka-with-pubsub-event-portal/)

  1. Kubernetes GUI
     * [OpenLens](#openlens)

  1. Kubernetes - microk8s (Installation und Management) 
     * [Ingress controller in microk8s aktivieren](#ingress-controller-in-microk8s-aktivieren)
         
  1. Helm (Kubernetes Paketmanager) 
     * [Helm Grundlagen](#helm-grundlagen)
     * [Helm Warum ?](#helm-warum-)
     * [Helm Example](#helm-example)

  1. Kubernetes - RBAC 
     * [Nutzer einrichten microk8s ab kubernetes 1.25](#nutzer-einrichten-microk8s-ab-kubernetes-125)
 
  1. Kubernetes - Netzwerk (CNI's) / Mesh
     * [Netzwerk Interna](#netzwerk-interna)
     * [Übersicht Netzwerke](#übersicht-netzwerke)
     * [Calico - nginx example NetworkPolicy](#calico---nginx-example-networkpolicy)
     * [Beispiele Ingress Egress NetworkPolicy](#beispiele-ingress-egress-networkpolicy)
     * [Kubernetes Ports/Protokolle](https://kubernetes.io/docs/reference/networking/ports-and-protocols/)
     * [IPV4/IPV6 Dualstack](https://kubernetes.io/docs/concepts/services-networking/dual-stack/)
   
  1. kubectl 
     * [Start pod (container with run && examples)](#start-pod-container-with-run--examples)
     * [Bash completion for kubectl](#bash-completion-for-kubectl)
     * [kubectl Spickzettel](#kubectl-spickzettel)
     * [Tipps&Tricks zu Deploymnent - Rollout](#tippstricks-zu-deploymnent---rollout)

  1. Kubernetes - Shared Volumes 
     * [Shared Volumes with nfs](#shared-volumes-with-nfs)

  1. Kubernetes - Wartung / Debugging 
     * [kubectl drain/uncordon](#kubectl-drainuncordon)
     * [Alte manifeste konvertieren mit convert plugin](#alte-manifeste-konvertieren-mit-convert-plugin)
     * [Curl from pod api-server](#curl-from-pod-api-server)

  1. Kubernetes - Tipps & Tricks 
     * [Kubernetes Debuggen ClusterIP/PodIP](#kubernetes-debuggen-clusterippodip)
     * [Debugging pods](#debugging-pods)
     * [Taints und Tolerations](#taints-und-tolerations)

  1. Kubernetes Advanced 
     * [Curl api-server kubernetes aus pod heraus](#curl-api-server-kubernetes-aus-pod-heraus)

  1. Kubernetes - Documentation 
     * [Documentation zu microk8s plugins/addons](https://microk8s.io/docs/addons)
     * [Shared Volumes - Welche gibt es ?](https://kubernetes.io/docs/concepts/storage/volumes/)

  1. Kubernetes - Hardening 
     * [Kubernetes Tipps Hardening](#kubernetes-tipps-hardening)
     * [Kubernetes Security Admission Controller Example](#kubernetes-security-admission-controller-example)
     
  1. Kubernetes Interna / Misc.
     * [OCI,Container,Images Standards](#ocicontainerimages-standards)
     * [Geolocation Kubernetes Cluster](https://learnk8s.io/bite-sized/connecting-multiple-kubernetes-clusters)
     
  1. Documentation 
     * [Good Doku with Tasks](https://kubernetes.io/docs/tasks/configure-pod-container/security-context/)
  
  1. Docker-Container Examples 
     * [2 Container mit Netzwerk anpingen](#2-container-mit-netzwerk-anpingen)
     * [Container mit eigenem privatem Netz erstellen](#container-mit-eigenem-privatem-netz-erstellen)
  
  1. Docker-Netzwerk 
     * [Netzwerk](#netzwerk)
     
  1. Docker Security 
     * [Scanning docker image with docker scan/snyx](#scanning-docker-image-with-docker-scansnyx)
  
  1. Docker Compose
     * [yaml-format](#yaml-format)
     * [Example with Ubuntu and Dockerfile](#example-with-ubuntu-and-dockerfile)
     * [docker-compose und replicas](#docker-compose-und-replicas)
     * [docker compose Reference](https://docs.docker.com/compose/compose-file/compose-file-v3/)
  
  1. Docker Swarm 
     * [Docker Swarm Beispiele](#docker-swarm-beispiele)

  1. Docker - Dokumentation 
     * [Vulnerability Scanner with docker](https://docs.docker.com/engine/scan/#prerequisites)
     * [Vulnerability Scanner mit snyk](https://snyk.io/plans/)
     * [Parent/Base - Image bauen für Docker](https://docs.docker.com/develop/develop-images/baseimages/)
    
  1. Kubernetes - Überblick
     * [Installation - Welche Komponenten from scratch](#installation---welche-komponenten-from-scratch)

  1. Kubernetes - microk8s (Installation und Management) 
     * [kubectl unter windows - Remote-Verbindung zu Kuberenets (microk8s) einrichten](#kubectl-unter-windows---remote-verbindung-zu-kuberenets-microk8s-einrichten)
     * [Arbeiten mit der Registry](#arbeiten-mit-der-registry)
     * [Installation Kubernetes Dashboard](#installation-kubernetes-dashboard)

  1. Kubernetes - RBAC 
     * [Nutzer einrichten - kubernetes bis 1.24](#nutzer-einrichten---kubernetes-bis-124)
     
  1. kubectl 
     * [Tipps&Tricks zu Deploymnent - Rollout](#tippstricks-zu-deploymnent---rollout)
    
  1. Kubernetes - Backups 
     + [Kubernetes Aware Cloud Backup - kasten.io](/backups/cluster-backup-kasten-io.md)
     * [Backup- und Wiederherstellungsstrategien](#backup--und-wiederherstellungsstrategien)

  1. Kubernetes - Tipps & Tricks 
     * [Assigning Pods to Nodes](#assigning-pods-to-nodes)

  1. Kubernetes - Documentation 
     * [LDAP-Anbindung](https://github.com/apprenda-kismatic/kubernetes-ldap)
     * [Helpful to learn - Kubernetes](https://kubernetes.io/docs/tasks/)
     * [Environment to learn](https://killercoda.com/killer-shell-cks)
     * [Environment to learn II](https://killercoda.com/)
     * [Youtube Channel](https://www.youtube.com/watch?v=01qcYSck1c4)

  1. Kubernetes -Wann / Wann nicht 
     * [Kubernetes Wann / Wann nicht](#kubernetes-wann--wann-nicht)

  1. Kubernetes - Hardening 
     * [Kubernetes Tipps Hardening](#kubernetes-tipps-hardening)

  1. Kubernetes Deployment Scenarios 
     * [Deployment green/blue,canary,rolling update](#deployment-greenbluecanaryrolling-update)
     * [Praxis-Übung A/B Deployment](#praxis-übung-ab-deployment)

  1. Kubernetes Probes (Liveness and Readiness) 
     * [Übung Liveness-Probe](#übung-liveness-probe)
     * [Funktionsweise Readiness-Probe vs. Liveness-Probe](#funktionsweise-readiness-probe-vs-liveness-probe)
       
  1. Linux und Docker Tipps & Tricks allgemein 
     * [Auf ubuntu root-benutzer werden](#auf-ubuntu-root-benutzer-werden)
     * [IP - Adresse abfragen](#ip---adresse-abfragen)
     * [Hostname setzen](#hostname-setzen)
     * [Proxy für Docker setzen](#proxy-für-docker-setzen)
     * [vim einrückung für yaml-dateien](#vim-einrückung-für-yaml-dateien)
     * [YAML Linter Online](http://www.yamllint.com/)
     * [Läuft der ssh-server](#läuft-der-ssh-server)
     * [Basis/Parent - Image erstellen](#basisparent---image-erstellen)
     * [Eigenes unsichere Registry-Verwenden. ohne https](#eigenes-unsichere-registry-verwenden-ohne-https)
    
  1. Linux Tipps & Tricks
     * [Grafischen Modus deaktivieren](#grafischen-modus-deaktivieren)
    
  1. VirtualBox Tipps & Tricks 
     * [VirtualBox 6.1. - Ubuntu für Kubernetes aufsetzen ](#virtualbox-61---ubuntu-für-kubernetes-aufsetzen-)
     * [VirtualBox 6.1. - Shared folder aktivieren](#virtualbox-61---shared-folder-aktivieren)
    
  1. CloudInit
     * [Kubernetes Client einrichten mit bash](#kubernetes-client-einrichten-mit-bash)
     
  1. Microservices - Messaging
     * [EventBus Implementierungen/Überblick](#eventbus-implementierungenüberblick)
  

<div class="page-break"></div>

## Grundlagen

### Microservices-Trends 2026


![Microservices-Trends 2026](/images/microservices-trends-2026.svg)

### Die Stimmung: Ernüchterung nach dem Hype

Vor einigen Jahren galt die Devise: „Microservices sind immer die richtige Wahl."
Das hat sich geändert. Viele Teams haben schmerzhaft gelernt, dass Microservices
echte Komplexität mitbringen — verteilte Systeme, Netzwerklatenzen, viele kleine
Deployments, aufwändiges Monitoring. 2026 lautet die ehrlichere Frage:

> **Brauchen wir hier wirklich Microservices — oder lösen sie mehr Probleme als sie schaffen?**

Das ist kein Rückschritt, sondern Reife.

---

### Trend 1: Der Modulare Monolith als bewusste Entscheidung

#### Was ist ein Modularer Monolith?

Ein normaler Monolith ist eine große Anwendung, bei der alles wild durcheinander
verwoben ist — eine Änderung kann überall etwas kaputt machen.

Ein **modularer Monolith** ist anders: Die Anwendung läuft als ein einziger Prozess
(wie ein klassischer Monolith), ist aber intern in klar getrennte Module aufgeteilt.
Jedes Modul hat seine eigene Zuständigkeit und kommuniziert mit anderen Modulen
nur über definierte Schnittstellen.

```
Normaler Monolith:         Modularer Monolith:        Microservices:
                                                       
  ┌─────────────┐          ┌─────────────────┐         ┌──────┐  ┌──────┐
  │ Alles       │          │ Modul A │Modul B│         │ Svc A│  │ Svc B│
  │ durchein-   │          │─────────┼───────│         └──────┘  └──────┘
  │ ander       │          │ Modul C │Modul D│            Netz      Netz
  └─────────────┘          └─────────────────┘         ┌──────┐  ┌──────┐
                             Ein Prozess                │ Svc C│  │ Svc D│
                                                        └──────┘  └──────┘
```

**Wann ist ein modularer Monolith sinnvoll?**
- Kleines Team (unter 10 Entwickler)
- Die Domäne ist noch nicht vollständig verstanden
- Schnelle Iteration wichtiger als unabhängige Skalierung
- Infrastruktur-Aufwand soll gering bleiben

Der modulare Monolith kann später immer noch in Microservices aufgeteilt werden —
aber man trifft diese Entscheidung dann bewusst, nicht weil es gerade modern ist.

---

### Trend 2: Platform Engineering — Was ist das, und was ist der Unterschied zu DevOps?

#### Was ist DevOps?

DevOps war die Antwort auf die Trennung zwischen Entwicklung (Dev) und Betrieb (Ops).
Die Idee: Entwickler bauen nicht nur die Software, sie betreiben sie auch.
„You build it, you run it."

Das funktioniert gut für kleine Teams. Aber in großen Organisationen mit vielen Teams
entsteht ein neues Problem: **Jedes Team erfindet das Rad neu** — eigene CI/CD-Pipelines,
eigene Monitoring-Setups, eigene Deployment-Skripte.

#### Was macht ein Platform Team anders?

Ein **Platform Team** baut eine interne Plattform, die alle anderen Teams nutzen.
Statt dass jedes Entwicklungsteam selbst Kubernetes, CI/CD, Monitoring und Secrets-Management
aufbaut, gibt es einen „goldenen Weg" (**Golden Path**), der schon funktioniert.

```
DevOps (jedes Team für sich):      Platform Engineering:

  Team A: eigene Pipeline            Platform Team
  Team B: eigene Pipeline            └── Baut interne Plattform
  Team C: eigene Pipeline                (Golden Path)
  → viel Doppelarbeit                        │
                                    ┌────────┼────────┐
                                  Team A  Team B  Team C
                                    → nutzen Plattform
                                    → fokussieren auf Features
```

| | DevOps | Platform Engineering |
|---|---|---|
| Wer baut Infrastruktur? | Jedes Team selbst | Zentrales Platform-Team |
| Ziel | Dev und Ops zusammenbringen | Entwickler-Produktivität skalieren |
| Typische Größe | Kleine bis mittlere Teams | Ab ~5+ Entwicklungsteams |
| Produkt | Die eigene Anwendung | Die Plattform ist das Produkt |

**Wichtig:** Platform Engineering ersetzt DevOps nicht — es ist die nächste Stufe,
wenn DevOps in großen Organisationen an seine Grenzen kommt.

---

### Trend 3: Hybride Architekturen

Statt „alles Microservice oder alles Monolith" mischt man heute bewusst:

- **Microservices** für Teile, die unabhängig skalieren oder deployt werden müssen
- **Modularer Monolith** für den Kernbereich, der noch nicht stabil ist
- **Serverless / Functions-as-a-Service (FaaS)** für einzelne, event-getriebene Aufgaben
  (z.B. Bild-Resizing beim Upload — warum dafür einen dauerhaft laufenden Service betreiben?)

#### Was ist Event-Driven Architecture (EDA)?

**Event-Driven Architecture** bedeutet: Services kommunizieren nicht direkt miteinander
(„ruf mich an"), sondern über Ereignisse („ich teile mit, was passiert ist").

```
Klassisch (synchron):         Event-Driven:

  Bestellung  →  Zahlung       Bestellung
  Zahlung     →  Versand       └── publiziert: "Bestellung eingegangen"
  Versand     →  ...                  │
  (Kette reißt bei Fehler)     Zahlung ──── reagiert auf Event
                               Versand ──── reagiert auf Event
                               (jeder Service arbeitet unabhängig)
```

**Vorteil:** Services kennen sich nicht — weniger Abhängigkeiten, besser skalierbar.
**Nachteil:** Schwerer zu debuggen, da kein direkter Aufruf-Stack.

---

### Trend 4: AI-Workloads auf denselben Clustern

2026 laufen KI-Modelle (Inference, Embedding-Generierung, RAG-Pipelines) nicht mehr
auf separaten Systemen, sondern direkt im Kubernetes-Cluster neben den Microservices.

Das bringt neue Anforderungen:
- GPU-Scheduling in Kubernetes
- Neue Ressourcentypen (GPUs statt nur CPU/RAM)
- AIOps: KI hilft beim Monitoring und Alerting

---

### Trend 5: Observability, Security und Inter-Service-Kommunikation bleiben die größten Schmerzpunkte

Das sind 2026 die drei Themen, die Enterprise-Rollouts am stärksten bremsen:

**Observability** — „Was passiert gerade in meinem System?"
Klassisches Logging reicht nicht mehr. Distributed Tracing (ein Request über 10 Services
verfolgen) und strukturierte Metriken sind Pflicht.

**Security** — „Wie vertrauen sich Services gegenseitig?"
Jeder Service muss sich authentifizieren. mTLS (gegenseitige TLS-Verschlüsselung zwischen
Services) und feingranulare AuthorizationPolicies (wer darf mit wem reden?) sind Standard.
Genau hier kommt Istio ins Spiel.

**Inter-Service-Kommunikation** — „Wie reden Services miteinander zuverlässig?"
Retry-Logik, Circuit Breaker, Timeouts — das muss jeder Service können. Ein Service-Mesh
wie Istio nimmt diese Logik aus dem Anwendungscode heraus und macht sie zur Infrastruktur.

---

### Zusammenfassung: Die Kernbotschaft für 2026

| Früher | Heute |
|--------|-------|
| „Microservices immer" | „Microservices wo sinnvoll" |
| Jedes Team baut alles selbst | Platform Team baut goldenen Weg |
| Monolith = schlecht | Modularer Monolith = valide Option |
| Separate Infrastruktur für alles | Kubernetes als universelle Plattform |
| Security als Nachgedanke | Security und Observability von Anfang an |

**Die wichtigste Frage vor jeder Architekturentscheidung:**
*Welches Problem löst diese Entscheidung — und welche neuen Probleme schafft sie?*

### Was sind Microservices ?


```
1. Microservices oder Microservices-Architekturen sind ein Ansatz
zur Anwendungsentwicklung bei dem eine große Anwendung
aus modularen Komponenten oder Diensten aufgebaut wird.
```

```
2. Jedes Modul unterstützt eine bestimmte Aufgabe oder ein Geschäftsziel und verwendet eine einfache, klar definierte Schnittstelle (Contract / Vertrag), wie zum Beispiel eine Anwendungsprogrammierschnittstelle (API),
um mit anderen Diensten zu kommunizieren.
```

### Best Practices fuer Multi-Cluster- und Hybrid-Umgebungen


### Das Grundproblem: Warum ein einzelner Cluster nicht ausreicht

#### Die Latenz-Grenze: < 10 ms

Kubernetes-Cluster haben eine harte technische Grenze, die oft unterschaetzt wird:

> **Alle Nodes eines Clusters muessen mit einer Netzwerklatenz von unter 10 ms**
> **erreichbar sein — insbesondere die etcd-Knoten im Control Plane.**

Das bedeutet: Nodes in verschiedenen geografischen Regionen koennen **nicht** einfach
zu einem einzigen Cluster zusammengefasst werden.

![Latenz-Grenze in Kubernetes-Clustern](/images/multi-cluster-latenz.svg)

**Was passiert bei zu hoher Latenz?**
- etcd-Leader-Wahlen schlagen fehl
- Control Plane wird instabil
- Pods werden nicht mehr korrekt geplant

---

### Loesung: Multi-Cluster mit Geo-LoadBalancer

Statt einem grossen Cluster ueber alle Standorte: **ein Cluster pro Region**,
verbunden durch einen Geo-LoadBalancer, der Nutzer zur naechstgelegenen Region routed.

![Multi-Cluster mit Geo-LoadBalancer](/images/multi-cluster-geo-loadbalancer.svg)

**Wie der Geo-LoadBalancer entscheidet:**
- Anhand der IP-Geolokation des Nutzers
- Anhand von Healthchecks (faellt ein Cluster aus → Traffic zur naechsten Region)
- Anhand von Latenz-Messungen (Anycast oder Latency-Based Routing)

---

### Achtung: Multi-Cluster steigert die Komplexitaet erheblich

![Komplexitaetsvergleich: Ein Cluster vs. drei Cluster](/images/multi-cluster-komplexitaet.svg)

> **Faustregel:** Jeder zusaetzliche Cluster verdreifacht den Betriebsaufwand
> fuer das Platform-Team — nicht verdoppelt.

**Wann lohnt sich der Aufwand?**

| Situation | Empfehlung |
|-----------|------------|
| Startup, < 20 Entwickler | Ein Cluster, Namespaces zur Trennung |
| Regulatorik erfordert EU/US-Trennung | Multi-Cluster noetig |
| Prod-Ausfall kostet mehr als Overhead | Multi-Cluster noetig |
| Globale Nutzer mit Latenz-Anforderung | Multi-Cluster noetig |
| Kein globaler Traffic, keine Compliance | Ein Cluster reicht |

---

### Warum ueberhaupt mehrere Cluster?

Ein einzelner Kubernetes-Cluster hat praktische Grenzen:

- **Latenz**: Nodes muessen unter 10 ms erreichbar sein — schliesst Geo-Verteilung in einem Cluster aus
- **Skalierbarkeit**: Ab ca. 5.000 Nodes wird ein einzelner Cluster unhandlich
- **Isolation**: Prod und Dev im gleichen Cluster teilen das Schicksal bei einem Ausfall
- **Compliance**: Daten in der EU, Compute in den USA — rechtlich oft nicht mischbar
- **Verfuegbarkeit**: Ein Cluster = ein Single Point of Failure

![Single Cluster vs. Multi-Cluster](/images/multi-cluster-overview.svg)

---

### Die drei Multi-Cluster-Muster

#### Muster 1: Umgebungs-Trennung (Dev/Staging/Prod)

Das einfachste und haeufigste Muster: Ein Cluster pro Umgebung.

| Cluster | Wer nutzt ihn | Besonderheiten |
|---------|--------------|----------------|
| dev-cluster | Entwickler (frei experimentieren) | Keine strengen Policies |
| staging-cluster | QA, Integrationstests vor Release | Gleiche Configs wie Prod |
| prod-cluster | Echte Nutzer | RBAC streng, PodDisruptionBudget |

**Wann sinnvoll:**
- Wenn Prod-Stabilitat kritisch ist
- Wenn Compliance getrennte Umgebungen fordert
- Einfach zu verstehen und zu betreiben

**Herausforderung:** Gleiche Manifests muessen in mehrere Cluster deployt werden
→ Loesung: GitOps (ArgoCD ApplicationSet, Flux Kustomization)

---

#### Muster 2: Geografische Verteilung (Active-Active)

Mehrere Cluster in verschiedenen Regionen, alle nehmen Traffic an.

![Muster 2: Geografische Verteilung Active-Active](/images/multi-cluster-muster-geo.svg)

**Was wird repliziert:**
- Stateless Services: problemlos in beiden Clustern
- Stateful Services (Datenbanken): komplexer — oft regionale DBs mit Replikation

**Wann sinnvoll:**
- Globale Anwendungen mit Latenz-Anforderungen
- Compliance erfordert Datensouveraenitat
- Hochverfuegbarkeit auch bei Regions-Ausfall

---

#### Muster 3: Workload-Spezialisierung

Verschiedene Cluster fuer verschiedene Workload-Typen.

| Cluster | Workload | Hardware |
|---------|----------|----------|
| gpu-cluster | ML-Training | A100/H100 GPUs — teuer, intensiv |
| batch-cluster | Nacht-Jobs, Batch-Verarbeitung | Spot-Instanzen — guenstig |
| web-cluster | APIs, UIs, Standard-Services | Standard-Nodes, Autoscaling |

**Wann sinnvoll:**
- GPU-Workloads sollen regulaere Deployments nicht beeinflussen
- Kostenoptimierung durch Node-Typen pro Workload
- Teams mit sehr verschiedenen Anforderungen

---

### Hybrid-Cloud: On-Premise + Public Cloud

Hybrid bedeutet: eigene Rechenzentren (On-Premise) und Public Cloud gleichzeitig nutzen.

| Aspekt | On-Premise | Public Cloud (AWS/Azure/GCP) |
|--------|-----------|------------------------------|
| Hardware | Eigene Hardware | Elastisch, Pay-per-Use |
| Daten | Datenschutz-kritische Daten | Burst-Kapazitaet |
| Latenz | Gering zum Kernsystem | Abhaengig von Region |
| Kosten | Hohe Fixkosten | Variable Kosten |

#### Warum Hybrid?

| Grund | Erklaerung |
|-------|-----------|
| Regulatorik | Kundendaten duerfen das eigene RZ nicht verlassen |
| Bestandsinvestitionen | Hardware wurde gekauft und muss genutzt werden |
| Burst-Kapazitaet | On-Premise fuer Grundlast, Cloud fuer Spitzen |
| Migration | Schrittweise aus dem RZ in die Cloud |

#### Typische Hybrid-Architektur

![Hybrid-Architektur On-Premise und Public Cloud](/images/multi-cluster-hybrid-arch.svg)

---

### Verbindung zwischen Clustern: Netzwerk-Optionen

![Netzwerkoptionen zwischen Clustern: VPN, Service Mesh, Cilium ClusterMesh](/images/multi-cluster-netzwerk.svg)

---

### GitOps fuer Multi-Cluster: Das Fundament

Ohne GitOps wird Multi-Cluster-Management schnell zum Chaos.
Goldene Regel: **Ein Git-Repo als einzige Quelle der Wahrheit fuer alle Cluster.**

#### ArgoCD: ApplicationSet Pattern

```yaml
## Einmal definieren — in alle Cluster deployen
apiVersion: argoproj.io/v1alpha1
kind: ApplicationSet
spec:
  generators:
  - list:
      elements:
      - cluster: prod-eu
        url: https://prod-eu.example.com
      - cluster: prod-us
        url: https://prod-us.example.com
  template:
    spec:
      destination:
        server: '{{url}}'
      source:
        path: apps/myservice/overlays/{{cluster}}
```

**Ergebnis:** Ein Commit in Git → ArgoCD deployt automatisch in alle Cluster.

#### Kustomize Overlay-Struktur fuer Multi-Cluster

```
apps/myservice/
  base/                   ← gemeinsame Manifests
    deployment.yml
    service.yml
  overlays/
    dev/                  ← Dev-spezifisch
      kustomization.yaml
      replicas.yml        ← 1 Replica
    prod-eu/              ← Prod EU-spezifisch
      kustomization.yaml
      replicas.yml        ← 5 Replicas
      configmap.yml       ← EU-Endpoints
    prod-us/              ← Prod US-spezifisch
      kustomization.yaml
      replicas.yml        ← 5 Replicas
      configmap.yml       ← US-Endpoints
```

**Vorteil:** Keine Dopplung von Manifests, Unterschiede sind klar sichtbar.

---

### Sicherheit in Multi-Cluster-Umgebungen

#### 1. Cluster-uebergreifende Identitaet (SPIFFE/SPIRE)

Problem: Wie weiss Cluster A, dass eine Anfrage wirklich von Cluster B kommt?

Der SPIRE Server laeuft zentral und stellt jeder Workload eine kryptografische Identitaet aus
(z.B. `spiffe://prod-eu/ns/orders/sa/payment-svc`). Cluster B validiert dieses Zertifikat
automatisch — mTLS ohne manuelle Zertifikatsverwaltung.

#### 2. Secrets-Management (Vault / External Secrets Operator)

**Anti-Pattern:** Secrets in Git oder als Kubernetes-Secrets ohne Verschluesselung.

**Best Practice:** Zentraler Vault, alle Cluster holen Secrets zur Laufzeit.

| Cluster | Auth-Token | Policy |
|---------|-----------|--------|
| prod-eu | Cluster-eigenes Token | Voller Prod-Zugriff |
| prod-us | Cluster-eigenes Token | Voller Prod-Zugriff |
| staging | Cluster-eigenes Token | Eingeschraenkte Policy, weniger Rechte |

External Secrets Operator synchronisiert automatisch:
```yaml
apiVersion: external-secrets.io/v1beta1
kind: ExternalSecret
spec:
  secretStoreRef:
    name: vault-backend
  target:
    name: db-password       # → wird zu normalem Kubernetes Secret
  data:
  - secretKey: password
    remoteRef:
      key: secret/prod/database
      property: password
```

#### 3. RBAC-Strategie uebergreifend

| Cluster | Wer darf deployen | Wer darf lesen |
|---------|-------------------|----------------|
| dev | Alle Entwickler | Alle Entwickler |
| staging | CI/CD Pipeline | Entwickler, QA |
| prod | Nur CI/CD Pipeline | Senior Devs, Ops |

**Werkzeug:** OIDC-Integration (Keycloak, Okta) → ein Login, Rechte per Cluster-Rolle.

---

### Observability: Alles sichtbar machen

Ein grosses Problem bei Multi-Cluster: Logs und Metriken sind ueber alle Cluster verteilt.

#### Zentrales Monitoring

Jeder Cluster betreibt eine lokale Prometheus-Instanz, die per `remote_write` an eine
zentrale Instanz (Grafana Cloud, Thanos oder VictoriaMetrics) schreibt. Ein Dashboard
zeigt alle Cluster, Alerts koennen uebergreifend korreliert werden.

**Ergebnis:** Ein Dashboard zeigt alle Cluster, Alerts koennen uebergreifend korreliert werden.

#### Verteiltes Tracing (OpenTelemetry)

| Span | Service | Dauer | Hinweis |
|------|---------|-------|---------|
| 1 | EU-Frontend | 50 ms | |
| 2 | Cross-Cluster-Hop | 15 ms | Netzwerk zwischen Clustern! |
| 3 | US-Orders-API | 30 ms | |
| 4 | DB-Query | 10 ms | |

Die gleiche Trace-ID (`abc-123`) verbindet alle Spans uebergreifend — sichtbar in Jaeger/Tempo.

OpenTelemetry Collector in jedem Cluster, zentral gesammeltes Jaeger/Tempo.

---

### Best Practices auf einen Blick

| Thema | Best Practice |
|-------|---------------|
| Cluster-Anzahl | Klein anfangen — 1 Prod, 1 Dev. Nur bei echtem Bedarf mehr. |
| Netzwerk | VPN fuer Start, Cilium/Istio wenn Service Discovery noetig |
| GitOps | ArgoCD ApplicationSet oder Flux mit Cluster-Tags |
| Secrets | Zentraler Vault, nie Secrets in Git |
| Observability | Zentrales Prometheus/Grafana von Anfang an |
| RBAC | OIDC-basiert, Rechte per Cluster unterschiedlich |
| DNS | Eindeutige Cluster-Namen: prod-eu.k8s.example.com |
| Kosten | Cluster-Overhead einrechnen (3 Control Plane Nodes pro Cluster!) |

---

### Anti-Patterns

**Anti-Pattern 1: Snowflake-Cluster**
Jeder Cluster hat andere Addons, andere Versionen, andere Netzwerk-Plugins.
→ Katastrophe beim Troubleshooting, jeder Cluster eine eigene Wissenschaft.

**Loesung:** Cluster-as-Code (Crossplane, Cluster API) — alle Cluster gleich gebaut.

---

**Anti-Pattern 2: Manuelle Deployments in mehrere Cluster**
Entwickler deployen manuell mit `kubectl` in 5 Cluster.
→ Cluster laufen auseinander, niemand weiss welcher Stand wo ist.

**Loesung:** Nur GitOps, keine manuellen Deployments in Prod.

---

**Anti-Pattern 3: Zu viele Cluster zu frueh**
10 Cluster fuer ein 5-Mann-Team.
→ Mehr Wartungsaufwand als Nutzen, jeder Cluster braucht Updates, Monitoring, Sicherheits-Patches.

**Faustregel:** Cluster kosten Betrieb. Jeder neue Cluster muss seinen Nutzen rechtfertigen.

---

### Wann Multi-Cluster (nicht) anfangen?

| Starte mit einem Cluster wenn ... | Wechsle zu Multi-Cluster wenn ... |
|----------------------------------|----------------------------------|
| Team &lt; 20 Entwickler | Regulatorik es erfordert (DSGVO, Region) |
| Keine Compliance-Anforderungen | Prod-Ausfall kostet mehr als Overhead |
| Proof of Concept / Startup | Team &gt; 3 dedizierte Platform Engineers |
| Kein globaler Traffic | Verschiedene Workload-Typen (GPU + Web) |
| | Echter Geo-Bedarf (&lt; 50 ms in US und EU) |

### Grundkonzepte von Microservices


### Information Hiding  

#### Was ? 

  * Microservices leben das Konzept, so viel wie möglich Informationen
in einer Komponente zu verstecken 
  * Der Microservice gibt so wenig Informationen wie "nötig" preis. 

#### Warum ? 

  * Dadurch gibt es eine klare Grenze, **was**
    *  **einfach zu ändern** ist
    *  oder was **komplizierter zu ändern** ist (Änderung des Vertrages)
  * Informationen die versteckt ist, können __ohne Absprache__ geändert werden.

 ## Independant Deployability 

   * Teams unabhängig Änderung in Microservices machen und dieses redeployen und zwar ohne alle anderen
     * zu redeployen 
   * (Das ist die wichtigste Sache bei microservices, der Tipp Nr. 1)



### Architektur von Microservices (Schichten/Layers)


Bei Microservices meint man mit "Schichten" die **interne Strukturierung** eines einzelnen Microservices:

**Typische Schichten:**
- **Presentation Layer** - API-Endpunkte (REST, gRPC)
- **Business Logic Layer** - Geschäftslogik und Domänenregeln
- **Data Access Layer** - Datenbankzugriffe und Persistierung

**Wichtig:** Jeder Microservice hat seine eigenen Schichten - im Gegensatz zu monolithischen Architekturen, wo Schichten über die gesamte Anwendung gehen.


### 12 factor app


  * Das sind best-practices 

```
Die 12-Factors stammen von Heroku 
und beschreiben, wie eine App aussehen muss, 
damit sie sich problemlos in einer Cloud-Plattform betreiben lässt 
— also genau das, was Kubernetes heute von einem Workload erwartet.
```

  * Ursprünglich entwickelt von heroku 2011
  * Ursprünglich gedacht für cloud-native apps
  * Auch gut für microservices anwendbar

### Anwendung 

  * Checkliste: Gilt das für meinen Service ? 

Hier ist die Tabelle der Twelve-Factor App Principles:

| # | Prinzip | Beschreibung |
|---|---------|--------------|
| 1 | Codebase | Versionsverwaltetes Code-Repository |
| 2 | Dependencies | Abhängigkeiten sollten extern verwaltet werden. <br/> (spielte zur Zeit von heroku eine Rolle, weil Software auf dem Host ausgeführt wurde. Man soll sich also nicht darauf verlassen, was auf dem Host existiert. Bei Docker/Kubernetes ist das bereits im Container-Image selbst geregelt. Man könnte also sagen, die Regel ist bei Docker-Images ohnehin erfüllt.|
| 3 | Config | Konfiguration als Umgebungsvariablen |
| 4 | Backing Services | Datenbanken, Messaging etc. als externe Ressourcen |
| 5 | Build, Release, Run | Drei unabhängige Deployment-Schritte |
| 6 | Stateless Processes | Zustandslose, unabhängige Prozesse zum guten Skalieren |
| 7 | Port Binding | App bindet direkt an Port |
| 8 | Concurrency | Apps sollten in Module aufgeteillt werden zur einfachen Skalierung |
| 9 | Disposability | Schneller Start und einfaches Herunterfahren |
| 10 | DEV/PROD Parity | Entwicklungumgebung und Produktion möglichst ähnlich |
| 11 | Logs | Logs als Event-Streams behandeln (wie bei docker / kubernetes) |
| 12 | Admin Processes | Admin-Aufgaben als One-off-Prozesse <br/> Einmalige Aktion möglichst gescriptet und versioniert und sie sollten in der gleichen Umgebung 
umgesetzt werden. <br/> Job/Cronjob/initContainer |

### Monolith vs. Microservices


#### Schaubild Monolith mit Nachteilen 

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/b7e6316a-7b45-424d-8e8a-139b4a0941bb)

### Schaubild 

![Monolithisch vs. Microservices](https://d1.awsstatic.com/Developer%20Marketing/containers/monolith_1-monolith-microservices.70b547e30e30b013051d58a93a6e35e77408a2a8.png)

Quelle: AWS Amazon 

### Monolithische Architektur

  * Alle Prozesse eng miteinander verbunden.
  * Alles ist ein einziger Service 
  * Skalierung:
      * Gesamte Architektur muss skaliert werden bei Spitzen

### Herausforderung: Monolithische Architektur 

  * Verbesserung und Hinzufügen neuer Funktionen wird mit zunehmender Codebasis zunehmend komplexer 
  * Nachteil: Schwer zu experimentieren 
  * Nachteil: Hinderlich für die Umsetzung neuer Ideen/Konzepte 

### Vorteile: Monolithische Architektur  

   * Gut geeignet für kleinere Konzepte und Teams 
   * Gut geeignet, wenn Projekt nicht stark wächst.
   * Gut geeignet wenn Projekt durch ein kleines Team entwickelt wird.
   * Guter Ausgangspunkt für ein kleineres Projekt 
   * Mit einer MicroService - Architektur zu starten, kann hinderlich sein.

### Microservices 

  * Jede Anwendung wird in Form von eigenständigen Komponenten erstellt. 
  * Jeder Anwendungsprozess wird als Service ausgeführt
  * Services kommunizieren über schlanke API's miteinander 
  * Entwicklung in Hinblick auf Unternehmensfunktionen
  * Jeder Service erfüllt eine bestimmte Funktion.
  * Sie werden unabhängig voneinander ausgeführt, daher kann:
    * Jeder Service aktualisiert
    * bereitgestellt
    * skaliert werden   
 
### Eigenschaften von microservices 

  * Eigenständigkeit
  * Spezialisierung 

### Vorteil: Microservices 

  * Agilität
    * kleines Team sind jeweils für einen Service verantwortlich
    * können schnell und eigenverantwortlich arbeiten
    * Entwicklungszyklus wird verkürzt. 

  * Flexible Skalierung
    * Jeder Service kann unanhängig skaliert werden. 

  * Einfache Bereitstellung
    * kontinuierliche Integration und Bereitstellung
    * einfach:
      * neue Konzepte auszuprobieren und zurückzunehmen, wenn etwas nicht funktioniert. 
      
  * Technologische Flexibilität
    * Die Teams haben die Freiheit, das beste Tool zur Lösung ihrer spezifischen Probleme auszuwählen.
    * Infolgedessen können Teams, die Microservices entwickeln, das beste Tool für die jeweilige Aufgabe wählen.

  * Wiederverwendbarer Code
    * Die Aufteilung der Software in kleine, klar definierte Module ermöglicht es Teams, Funktionen für verschiedene Zwecke zu nutzen. 
    * Ein Service/Funktion als Baustein
    
  * Resilienz
    * Gut geplant/designed -> erhöht die Ausfallsicherheit 
    * Monolithisch: Eine Komponente fällt aus, kann zum Ausfall der gesamten Anwendung führen.
    * Microservice: kompletter Ausfall wird vermieden, nur einzelnen Funktionalitäten sind betroffen

### Gut aufgestellt mit Devops 

  * Weil
    * ansonsten durch alte Strukturen (kein Devops-Team) Geschwindkeit durch notwendige Klärung, Verantwortlichkeiten verloren geht.

### Nachteile: Microservices 

  * Höhere Komplexität 
  * Bei schlechter / nicht automatischer dokumentation kann man bei einer größeren Anzahl von Microservices den Überblick der Zusammenarbeit verlieren
  * Aufwand: Architektur von Monolithisch nach Microservices IST Aufwand ! 
  * Aufwand Wartung und Monitoring (Kubernetes) 
  * Erhöhte Knowledge bzgl. Debugging. 
  * Fallback-Aufwand (wenn ein Service nicht funktioniert, muss die Anwendung weiter arbeiten können, ohne das andere Service nicht funktionieren)
  * Erhöhte Anforderung an Konzeption (bzgl. Performance und Stabilität) 
  * Wichtiges Augenmerk (Netzwerk-Performance) 

### Nachteile: Microservices in Kubernetes 

  * andere Anforderungen an Backups und Monitoring 

### Praxisbeispiele


### Auswahl 

  * Netflix
  * Spotify (über 800 microservices, java) 
  * ebay

### References  

  * https://www.asioso.com/de_DE/blog/anwendungen-und-praxisbeispiele-von-microservices-b602


### Was ist devops


```
I. Ein DevOps-Team besteht aus Entwickler- und IT-Operations-Teams, die während des gesamten Produktlebenszyklus zusammenarbeiten, um die Geschwindigkeit und Qualität des Software-Deployments zu erhöhen. 

II. Im Rahmen eines DevOps-Modells sind Entwicklungs- und Operations-Teams nicht mehr voneinander isoliert. Manchmal verschmelzen diese beiden Teams zu einem einzigen Team, in dem die Ingenieure während des gesamten Anwendungslebenszyklus zusammenarbeiten –

```

### Was ist ein API Gateway


### Was ist ein API Gateway?

![API Gateway Übersicht](/images/api-gateway-overview.svg)

Ein API Gateway ist ein zentraler Einstiegspunkt, der alle eingehenden Anfragen von
externen Clients entgegennimmt und sie an die richtigen internen Microservices weiterleitet.

Der Client — egal ob Browser, Mobile App oder ein anderes System — kennt nur eine
einzige Adresse: `api.example.com`. Was dahinter passiert, ist ihm egal.

---

### Was macht ein API Gateway konkret?

API Gateways bieten ein reiches Feature-Set, das weit über einfaches Routing hinausgeht:

| Aufgabe | Beschreibung |
|---------|-------------|
| **Routing** | `/api/users` → User Service, `/api/orders` → Order Service |
| **Authentifizierung** | JWT, OAuth2, API-Keys prüfen bevor die Anfrage den Service erreicht |
| **Autorisierung** | Darf dieser Nutzer auf diese Route zugreifen? |
| **Rate Limiting** | Granular pro Client, Route oder API-Plan (z.B. Free vs. Pro) |
| **TLS-Terminierung** | HTTPS endet am Gateway, intern läuft HTTP |
| **Load Balancing** | Anfragen auf mehrere Instanzen eines Service verteilen |
| **Request-Transformation** | Header umschreiben, Payloads anpassen, Protokolle konvertieren |
| **Logging & Monitoring** | Alle Requests zentral erfassen |
| **Circuit Breaker** | Fehlgeschlagene Services abkapseln, Fallback liefern |
| **API-Versionierung** | `/v1/users` und `/v2/users` parallel betreiben |
| **Developer Portal** | Self-Service für externe Entwickler: Doku, API-Keys, Playground |

---

### Bekannte API Gateways

| Produkt | Einsatz |
|---------|---------|
| **Kong** | Open Source, plugin-basiert, sehr verbreitet; Enterprise-Version mit Developer Portal |
| **APISIX** | Open Source, Apache-Projekt, hohe Performance, ebenfalls plugin-basiert |
| **Tyk** | Open Source, stark bei API-Key-Management und Developer Portal |

---

### API Gateway vs. Service Mesh — was ist der Unterschied?

Ein häufiges Missverständnis: Istio ist rein für Ost-West (intern). Das stimmt nicht ganz.

**Istio kann über sein Ingress Gateway auch Nord-Süd-Traffic (extern → intern) steuern.**
Der entscheidende Unterschied liegt nicht in der Verkehrsrichtung, sondern im Feature-Set.

![Auth und API Gateway Flow](/images/auth-api-gateway-flow.svg)

| | API Gateway | Service Mesh (z.B. Istio) |
|---|---|---|
| **Nord-Süd (extern → intern)** | Ja, Hauptaufgabe | Ja, möglich via Ingress Gateway |
| **Ost-West (intern → intern)** | Nein | Ja, Kernaufgabe |
| **Schwerpunkt** | Externe Clients, Developer-Erfahrung | Service-zu-Service-Kommunikation |
| **Authentifizierung** | OAuth2, API-Keys, JWT für Nutzer/Apps | mTLS zwischen Services |
| **Rate Limiting** | Granular pro Client, Route und Plan | Begrenzt |
| **Developer Portal** | Ja (z.B. Kong, Tyk) | Nein |
| **Request-Transformation** | Ja (Header, Payload, Protokoll) | Begrenzt |
| **API-Versionierung** | Ja | Nein |
| **Observability intern** | Begrenzt | Stark (Tracing, Metriken je Service) |
| **mTLS zwischen Services** | Nein | Ja |

#### Wann reicht das Istio Ingress Gateway?

Das Istio Ingress Gateway ist ausreichend, wenn:
- kein Developer Portal benötigt wird
- kein komplexes Rate Limiting nach API-Plänen nötig ist
- die Nutzer intern sind (kein öffentliches API)

Ein dediziertes API Gateway ist sinnvoll, wenn:
- externe Entwickler das API nutzen sollen
- verschiedene API-Pläne (Free, Pro, Enterprise) verwaltet werden
- komplexe Auth-Flows (OAuth2 Authorization Code, API-Key-Verwaltung) nötig sind
- das API als Produkt vermarktet wird

**Faustregel:** Service Mesh und API Gateway schließen sich nicht aus — in produktiven
Setups laufen beide oft zusammen. Das API Gateway übernimmt den Eingang mit reichem
Feature-Set, das Service Mesh sichert und beobachtet die Kommunikation im Cluster.

---

### Zusammenfassung

Ein API Gateway ist kein optionales Extra, sondern bei jeder Microservices-Architektur
mit externen Clients praktisch unverzichtbar.

Der Unterschied zu einem Service Mesh wie Istio liegt nicht darin, dass Istio kein
Nord-Süd-Traffic kann — das kann es. Der Unterschied liegt im Feature-Set: API-Keys,
Developer Portals, granulares Rate Limiting nach Plänen und Request-Transformation
machen ein dediziertes API Gateway zur richtigen Wahl für externe APIs.

### Microservice and Database


  * Der MicroService sollte immer die Hoheit über seine Daten haben (Eine eigene Datenbank), das meint NICHT einen eigenen Datenbankserver 
  * Und sollte diese verändern und nutzen können (auch Struktur), ohne sich mit anderen abzusprechen.  .  

## Analyse Monolith / Microservice

### Indikatoren für Microservices (Wechsel von Monolith)


### Harte Faktoren (messbar)
- **Deployment-Frequenz**: Deployments dauern >1h oder nur wenige Male pro Monat möglich
- **Build-Zeit**: >30 Minuten für vollständigen Build
- **Team-Größe**: >15 Entwickler arbeiten am gleichen Codebase
- **Codebase-Größe**: >100k LOC oder >50 Module
- **Ausfallrate**: Einzelne Bugs führen zu Totalausfall
- **Skalierungskosten**: Gesamte Anwendung muss skaliert werden, obwohl nur Teile Last haben
- **Time-to-Market**: Neue Features brauchen Wochen/Monate statt Tage

### Weiche Faktoren
- **Entwickler-Produktivität**: Änderungen benötigen umfangreiche Regressionstests
- **Technologie-Lock-in**: Neue Technologien nicht bzw. schwer einsetzbar 
- **Team-Autonomie**: Teams blockieren sich gegenseitig
- **Onboarding**: Neue Entwickler brauchen Wochen zum Verständnis
- **Code-Ownership**: Unklare Verantwortlichkeiten

---

### Prompt für Migrationsanalyse

```
Analysiere unsere Anwendung auf Eignung für Microservices-Migration:

### Kontext
- Beschreibung: [Anwendungstyp und Domäne]
- Team-Größe: [Anzahl Entwickler]
- Deployment-Frequenz: [Wie oft?]
- Build/Deploy-Dauer: [Zeit in Minuten]

### Probleme
- Aktuelle Schmerzpunkte: [Liste der Probleme]
- Business-Anforderungen: [Skalierung, TTM, etc.]

### Infrastruktur
- Aktueller Stack: [Technologien]
- DevOps-Reifegrad: [CI/CD, Monitoring, etc.]
- Cloud/On-Premise: [Infrastruktur]

Bewerte:
1. Dringlichkeit der Migration (1-10)
2. Risiken und Voraussetzungen
3. Empfohlene Strategie (Big Bang vs. Strangler Pattern)
4. Erste Kandidaten für Extraktion
5. Geschätzte Kosten vs. Nutzen
```

## Grundwissen Microservices (Teil 2)

### Brainstorming Domäne


### Prozess aus Domain Driven Design

  * Eventstorming
  * https://entwickler.de/ddd/domain-driven-design-in-aktion-mehr-dynamik-mit-event-storming
  * 

### Welche Events gibt es ? (in der Vergangenheit) 

```
Bewerbungsgespräch geführt 
Bewerber akzeptiert 
Beispielvertrag erstellt 
Beispielvertrag verschickt
Beispielvertrag von zukünftigen Mitarbeiter angenommen 
```

### Wer löst dieses Event aus ? 


```
z.B. Button -> Bewerber 
Command -> Bewerber auf Button im Webfrontend geklickt

```



### Datenbank - Patterns - Teil 1


### Pattern Shared Database

  * Shared Database:Informations-Hiding ist schwierig 
  * Achtung: nur in 2 Situationen vernünftig
    1. Lesen statischer Referenzdaten (Postleitzahlen, Geschlecht, Bundesländer)
    2. Anbieten eines Service, der direkt eine Datenbank als definierten Endpunkt bereitstellt
       * Alternative: Database as-a-Service-Interface Pattern 

#### Wie häufig 
  
  * Eher selten

#### Referenz 

  * https://microservices.io/patterns/data/shared-database.html
  
### Pattern: Database View 

  * Die Daten werden nicht als Tabelle, sondern als View bereitgestellt
  * Datenbank (View) ist dann aber ein öffentlicher Vertrag

#### Wo ? 

  * Kann man dann machen, wenn man das monolithische Schema nicht auseinander nehmen kann
  * Achtung: performance views mysql (nur bei älteren Versionen)
  * Wenn der Aufwand für die Aufteilung zu gross ist, kann das der 1. Schritt in die richtige Richtung sein

### Pattern: Database-as-a-Service Interface

  * Manchmal müssen Clients eine Datenbank nur abfragen
  * z.B. eine dedizierte Datenbank, als Read-Only-Endpunkt 
    * gefüllt wird diese wenn sich Daten in der zugrundeliegenden Datenbank ändern
  * Wir sollten die Datenbank, die wir nach draussen anbieten, von der Datenbank 
    * getrennt halten, die wir innerhalb unserer Service-Grenzen einsetzen

#### Wie ?

  * Umsetzung durch eine Mapping - Engine.

#### Wann ? 

   * Wenn legacy-client lesenden Zugriff benötigen

#### Reference:

  * https://microservices.io/patterns/data/database-per-service.html

### Pattern: Database Wrapping Service 

   * Eine Datenbank mit einem Service wrappen.
   * Damit kann man auch sicherstellen, dass sich die Datenbank nicht verändert.
   * Zugriffe müssen jetzt aber geändert werden, von direkt auf die service api

#### Wann ? 

   * API davor setzen, um Veränderung der Datenbank zu hindern. 
   * Einschränken, was man machen darf. 

### Pattern: Aggregate Exposing Monolith 

  * Daten werden über einen Serviceendpunkt vom Monolithen selbst bereitgestellt
    * API oder ein Stream mit Events
  * Dadurch wird explizit, welche Informationen der neue Service benötigt

### Pattern: Change Data Ownership 

  * Der neue Dienst übernimmt die Ownership für die Daten 

### Pattern Synchronize Data in Application 

#### Schritt 1: Daten Bulk - synchronisieren
  
  * z.B. durch Batch-Job 
  * dann z.B. Change-Data-Caputre Prozess 

#### Schritt 2: Synchrones Schreiben, aus dem alten Schema lesen 

  * Erfolgt durch Deployment einer neuen Version der Anwendung

#### Schritt 3: Synchrones Schreiben, aus dem neuen Schema lesen 

  * Erfolgt wieder durch Deployment einer neuen Version der Anwendung 

#### Schritt 4: Alte Schema entfernen

  * Altes Schema kann jetzt gefahrlos entfernt werden

### Pattern: Tracer Write 

  * Inkrementelle Verschiebung der Source of Truth. 
  * D.h. nicht komplette Datenbank, sondern einzelne Tabellen

### Datenbank - Patterns - Teil 2


### Was sollte ich zuerst aufteilen: Code oder Datenbank, oder gleichzeitig.

 * Datenbank nur zuerst, wenn ich Sorge wg. der Performance habe.
 * Code in der Regeln zuerst (Machen die meisten Teams so) 
   * Vorteil: Ich weiss dann welche Daten der Service braucht 

 * Gleichzeitig auf keinen Fall 

### Anwendungsfall 1: Zuerst die Daten aufteilen 


#### Pattern: Repository per Bounded Context

  * Im Code habe ich pro Bounded Context ein Repository - Layer, der
    auf die Datenbank zugreift 

#### Pattern: Database per Bounded Context 

  * Vorsichtsmaßnahme, falls ich später ein Service draus machen will

### Anwendungsfall 2: Zuerst den Code aufteilen 

#### Pattern: Monolith as Data Access Layer 

  * [Monolith Data Access Layer - Schritt 1](https://photos.app.goo.gl/XhBKuGRAFaubN99CA)
  * [Monolith Data Access Layer - Schritt 2](https://photos.app.goo.gl/k3vg1BsoRonHi7pe9)

#### Pattern: Multischema Storage 

  * Service speichert Teile der Daten selbst 
    * und Teile der Daten kommen aus dem Monolithen 



#### Pattern: Split Table 

  * [Split Table - Teil 1](https://photos.app.goo.gl/DJ9oXXWwJg62qb199)
  * [Split Table - Teil 2](https://photos.app.goo.gl/T3yDBMCh5xr9NMKB8)
  * [Split Table - Teil 3](https://photos.app.goo.gl/ynrXyq4gD18HHAg66)

##### Grund

```
Tabellen die über Service - Grenzen hinweg existieren aufteilen
```

#### Pattern: Move Foreign Key Relationship To Code 

  * [Schritt 1](https://photos.app.goo.gl/zgSYAeg6Qq5vWnYx7)
  * [Schritt 2](https://photos.app.goo.gl/cciUQwy71HZXdHB67)


### Anwendungsfall 3: Gemeinsame genutzte statische Daten

#### Pattern: Duplicate Static Reference Data 

  * Jeder Service hat seine eigenen Ländercodes 
  * Zwar redundant, aber ändern sich fast nie.
  * Und ist es ein Problem, wenn sie sich ändern ? 
  * Letzte Änderung 2011 (bei Länder-Codes)

#### Pattern: Static Dedicated Reference Data Schema 

  * Dediziertes Schema für Ländercodes in eigener Datenbank 
  * Alle Services greifen auf diese Datenbank direkt zu 
  * Es gibt dort einen Vertrag (Änderungen an der Struktur sind kritisch) 

##### Nachteil 

  * Probleme beim Ändern des Encodings in der Datenbank (Migration von alter auf neue Datenbank) 

#### Pattern: Static Reference Data Library 

  * z.B. Ländercodes wandern von der Datenbank in eine Bibliothek 
    * die dann einfach eingebunden wird. 

##### Nachteil 
 
  * Schwierig, wenn verschiedene Programmier-Sprachen
 
#### Pattern: Static Reference Data Service 

  * z.B. dedizierter Service für Ländercodes 
  * REST-API

##### Aufbau 

```
    Service        Service
   Warehouse       Finance 
       \              /
        \            /
            Service
         Country Code 

```

### Strategische Patterns


### Pattern: Strangler Fig Application 

  * Technik zum Umschreiben von Systemen 

#### Wie umleitung, z.B.

  * http proxy 
  * oder s.u. branch by abstraction
  * An- und Abschalten mit Feature Toggle 
  * Über message broker 

#### http - proxy - Schritte 

  1. Schritt: Proxy einfügen
  2. Schritt: Funktionalität migrieren 
  3. Schritt: Aufrufe umleiten

#### Message broker

  * Monolith reagiert auf bestimmte Messages bzw. ignoriert bestimmte messages
  * monolith bekommt bestimmte nachrichten garnicht 
  * service reagiert auf bestimmte nachrichten 


### Pattern: Parallel Run 

  * Service und Teil im Monolith wird parallel ausgeführt
  * Und es wird überprüft, ob das Ergebnis in beiden Systemn das gleiche ist (z.B. per batch job)

### Pattern: Decorating Collaborator

  * Ansteuerung als nachgelagerten Prozess über einen Proxy
  * Example: Decorating Collaborator



### Pattern Branch by Abstraction 

  * Beispiel Notification 

#### Schritt 1: Abstraction der zu ersetzendne Funktionalität erstellen


#### Schritt 2: Ändern sie die Clients der bestehenden Funktionalität so, dass sie die neue Abstraktion verwenden


#### Schritt 3: Neue Implementierung der Abstraktion 

```
Erstellen Sie eine neue Implementierung der Abstraktion mit der 
überarbeiteten Funktionalität. 

In unserem Fall wird diese neue Implementierung unseren neuen 
Mikroservice aufrufen
```

#### Schritt 4: Abstraktion anpassen -> neue Implementierung

```
Abstraktion anpassen, dass sie unsere neue Implementierung verwendet
```

#### Schritt 5: Abstraktion aufräumen und alte Implementierung entfernen 



### Tests - Uebersicht


### Was testen wir — und wieviel davon?

Empfohlene Verteilung nach der **Testing Trophy** (optimiert fuer Microservices):

![Testing Trophy](/images/testing-trophy.svg)

> **Warum so viele Integration Tests?**
> In Microservices stecken die meisten Fehler an den Grenzen zwischen Service und
> Datenbank, Message Broker oder anderem Service — nicht in der isolierten Logik.

---

### Was testen wir wo?

| Testart | Was wird getestet | Werkzeug |
|---|---|---|
| **Static** | Syntaxfehler, Formatierung, bekannte Sicherheitsluecken | ESLint, SpotBugs, Trivy |
| **Unit** | Reine Geschaeftslogik (keine DB, kein Netzwerk) | JUnit, pytest, Jest |
| **Integration** | Service + echte DB / Kafka / Redis | Testcontainers |
| **Contract** | API-Vertrag zwischen Consumer und Provider | Pact, OpenAPI + Dredd |
| **E2E** | Vollstaendige User Journey ueber alle Services | Playwright, Cypress |

---

### Wann im Entwicklungsprozess?

```
Entwickler        Pre-Commit       CI (jeder PR)      Nightly        Release
    |                  |                 |                |              |
    |-- schreibt Code  |                 |                |              |
    |                  |                 |                |              |
    |                  |-- Static -----> |                |              |
    |                  |-- Unit -------> |                |              |
    |                  |                 |                |              |
    |                  |                 |-- Static ----> |              |
    |                  |                 |-- Unit ------> |              |
    |                  |                 |-- Integration->|              |
    |                  |                 |-- Contract --> |              |
    |                  |                 |                |              |
    |                  |                 |                |-- E2E -----> |
    |                  |                 |                |              |
    |                  |                 |                |              |-- Smoke Test
```

| Phase | Tests | Dauer | Ziel |
|---|---|---|---|
| **Pre-Commit** (lokal) | Static, Unit | < 1 Min. | Sofortfeedback, kein kaputten Code committen |
| **CI — jeder PR** | Static, Unit, Integration, Contract | 5-15 Min. | Kein defekter PR in main |
| **Nightly** | E2E | 30-60 Min. | Vollstaendige Systemvalidierung |
| **Vor Release** | Alle + manueller Smoke Test | — | Letztes Sicherheitsnetz |

---

### Warum E2E nicht bei jedem PR?

E2E-Tests sind langsam, flakey (timing-abhaengig) und teuer.
Ziel: **5-10 kritische User Journeys**, nicht jedes Feature.

```
Falsch: 500 E2E-Tests, laufen 2h, schlagen oft zufaellig fehl
Richtig: 10 E2E-Tests fuer die wichtigsten Pfade (Checkout, Login, Zahlung)
```

---

### Praktische Faustregeln

**Unit Test schreiben wenn:**
- Die Logik komplex ist (Berechnungen, Entscheidungen, Transformationen)
- Keine externe Abhaengigkeit benoetigt wird

**Integration Test schreiben wenn:**
- Code mit einer Datenbank, Kafka oder einem anderen Service interagiert
- SQL-Abfragen, ORM-Mappings oder Transaktionen getestet werden sollen

**Contract Test schreiben wenn:**
- Zwei Services miteinander kommunizieren
- Ein Service eine API eines anderen Services aufruft

**E2E Test schreiben wenn:**
- Ein kritischer Geschaeftsprozess end-to-end abgesichert werden soll
- Mehrere Services zusammenspielen muessen

---

### Anti-Patterns

| Anti-Pattern | Problem | Besser |
|---|---|---|
| Nur Unit Tests | Findet keine Integrationsfehler (Mock weicht von Realitaet ab) | Integration Tests mit Testcontainers |
| Zu viele E2E Tests | Langsam, flakey, schwer zu debuggen | Auf wenige kritische Journeys reduzieren |
| Kein Contract Test | Aenderung am Provider bricht Consumer unbemerkt | Pact zwischen allen Services |
| Geteilte Test-DB | Tests beeinflussen sich gegenseitig | Testcontainers — jeder Test eigene DB |
| Mocks fuer alles | Tests bestehen, Produktion bricht | Echte Abhaengigkeiten via Testcontainers |

### Monolith schneiden microservices


### Wie kann ich schneiden (NOT's) ? 

  * Code-Größe 
  * Technische Schnitt 
  * Amazon: 2 Pizzas, wieviele können sich davon, wie gross kann man team 
  * Microservice wegschmeissen und er müsste in wenigen Tagen oder mehreren Wochen wieder herstellen

### Wie kann ich schneiden (GUT) ? 

  * Fachlich 
  * DDD (Domain Driven Design) - Welche Aufgaben gibt es innerhalb des sogenannten Bounded Context in meiner Domäne 
  * Domäne: Bibliothek 
  * In der Bibliothek 
    * Leihe 
    * Suche 

### Bounded Context 

![Bounded Context](https://martinfowler.com/bliki/images/boundedContext/sketch.png)

### Zwei Merkmale mit den wir arbeiten

  * Kohäsion (innerer Zusammenhalt des Fachbereichs) - innerhalb eines Services  
  * Bindung (lose Bindung) - zwischen den Services 
  * Jeder Service soll unabhängig sein 

### Was heisst unabhängiger Service 
  
  1. Er muss funktionieren, auch wenn ein anderes Service nicht läuft (keine Abhängigkeit) 
  2. Er darf nicht DIREKT auf die Daten eines anderen Services zugreifen (maximal über Schnittstelle)
  3. Jeder Service ist völlig autark und hat seine eigene BusinessLogik und seine eigene Datenbank 

### Regeln für das Design von Services 

#### Regel 1:

```
Es sollte eine große Kohäsion innerhalb des Services geben.
(Bindung). Alles sollte möglichst benötigt werden.

(Ist eine schwache Kohäsion innerhalb des Services, sind Funktionen 
dort, die eigentlich in einen anderen Service gehören)
```

#### Regel 2: lose Bindung (zwischen Services) 

```
Es sollte eine lose Bindung zu anderen Services geben.
(Ist die Bindung zu gross, sind entweder die Services zu klein konzipiert
oder Funktionen sind an der falschen Stelle implementiert) 

zu klein: zu viele Abfragen anderer Service .... 

````

#### Regel 3: unabhängigkeit 

```
Jeder Service muss eigenständig sein und seine eigene Datenbank haben.
```

### Datenbanken 

#### Herangehensweise

```
heisst auch: 
o Kein großes allmächtiges Datenmodel, sondern viele kleine 
(nicht alles in jedem kleinen Datenmodel, sondern nur, was im jeweiligen
Bounded Context benötigt wird)
```

#### Eine Datenbank pro Service (eigenständig / abgespeckt) 


##### Warum ?

```
Axiom: Eine eigenständige Datenbank pro Service. Warum ? 
(Service will NEVER reach into another services database)
```

##### Punkt 1 : Jeder Service soll unabhängig laufen können 

```
Jeder Service soll unabhängig vom anderen Service sein 

o no DB for everything (If DB goes down our service goes down)
o it easier to scale (if one service needs more capacity)
o more resilient. If one service goes down, our service will still work.
```

#### Punkt 2: Datenbank schemata könnten sich unerwartet ändern 

```
o We (Service A) use data from Service B, directly retrieving it from the db.
o We (Service) want property name: Lisa
o Team of Service B changes this property to: firstName 
  AND do not inform us.
  (This breaks our service !!) . OUR SERV
```

#### Punkt 3: Freiheit der Datenbankwahl 

```
3.4.3 Some services might funtion more efficiently with different types
of DB's (sql vs. nosql)
```


### Beispiel - Bounded 

```
Der Bounded Context definiert den Einsatzbereich eines Domänenmodells. 
```

```
Es umfasst die Geschäftslogik für eine bestimmte Fachlichkeit. Als Beispiel beschreibt ein Domänenmodell 
die Buchung von S-Bahn-Fahrkarten 
und ein weiteres die Suche nach S-Bahn-Verbindungen. 
```

```
Da die beiden Fachlichkeiten wenig miteinander zu tun haben, 
sind es zwei getrennte Modelle. Für die Fahrkarten sind die Tarife relevant und für die Verbindung die Zeit, das Fahrziel und der Startpunkt der Reise.
```

```
oder z.B. die Domäne: Bibliothek 
Bibliothek 
  Leihe (bounded context 1)
  Suche (bounded context 2)
```



### IAM als Bounded Context — fachlich oder technisch?


### Die kritische Frage

Ein Bounded Context ist per DDD-Definition eine **sprachliche/fachliche Grenze** (Ubiquitous Language), keine technische.
Wenn IAM nur deshalb abgetrennt wird, weil "Authentifizierung ist halt Technik", ist das kein Bounded Context —
sondern eine technische Schicht (Shared Kernel, Infrastructure, Library).

### Wann IAM ein echter fachlicher BC ist

  * Es gibt eine eigene Fachlichkeit mit eigener Sprache: Rollen, Berechtigungen, Mandanten, Einladungs-Workflows, Access Reviews, Approval-Prozesse
  * Jemand im Business kümmert sich tatsächlich darum ("wer darf was") als eigene Domäne
  * Beispiel: SaaS mit komplexem Org-/Team-/Permission-Modell → ja, eigener BC

### Wann es kein eigener BC ist (häufiger als gedacht)

  * Du nutzt Keycloak / Auth0 / Entra ID → Authentifizierung ist ein **gekauftes Generic Subdomain**, eine externe Capability. Du integrierst dagegen über einen ACL, modellierst es aber nicht als eigenen BC.
  * "User registrieren" hat keine eigene Geschäftslogik außer "Datensatz anlegen" → das ist CRUD, kein BC.

### DDD-Einordnung

| Begriff | Bedeutung für IAM |
|---|---|
| Core Domain | fast nie — IAM ist selten dein Wettbewerbsvorteil |
| Generic Subdomain | meistens — "gelöstes Problem", kauf es ein |
| Supporting Subdomain | wenn du leichte eigene Logik brauchst |

### Bounded Context: Kundenverwaltung vs. IAM

Das Schaubild zeigt den sauberen Schnitt und das Owner-Konsumenten-Muster:

![Bounded Contexts: IAM vs. Kundenverwaltung](../images/bounded-contexts-iam-kunde.svg)

#### IAM (Generic BC)

  * **User** als Login-Subjekt: Credentials, Rollen, Permissions, Tokens, MFA
  * Events: `UserCreated`, `PasswordChanged`, `RoleAssigned`, `LoggedIn`
  * Sprache: Security/Technik

#### Kundenverwaltung (Domain-BC)

  * **Kunde** als Geschäftsobjekt: Stammdaten, Adressen, Präferenzen, Segment, Bonität, Vertragshistorie
  * Events: `CustomerRegistered`, `AddressChanged`, `CustomerBlocked`
  * Sprache: Marketing/Vertrieb

#### Verbindung

Der Customer referenziert eine `userId` aus IAM (oder umgekehrt).
Beide sind getrennte Aggregate mit eigener ID — nicht dasselbe Objekt.

### Warum die Trennung wichtig ist

  * Nicht jeder User ist ein Kunde (Mitarbeiter, Admins, Service-Accounts)
  * Nicht jeder Kunde hat einen Login (B2B-Firmenkunde mit mehreren Usern; Laufkundschaft ohne Account)
  * Lifecycle ist unterschiedlich: User kann gesperrt werden, Kunde bleibt bestehen — und umgekehrt

**Häufige Falle:** "Kunde" und "User" in eine Tabelle/ein Aggregat packen.
Funktioniert am Anfang, rächt sich spätestens wenn ein Kunde mehrere Logins braucht oder ein Mitarbeiter auch Kunde wird.

### Authorization: fachlich vs. technisch

Autorisierung hat drei Ebenen mit unterschiedlicher Zuständigkeit:

| Ebene | Frage | Wo modellieren |
|---|---|---|
| Authentication | Wer bist du? | IAM (immer) |
| Coarse-grained Authorization | Rollen, Gruppen | IAM (eigener BC) |
| Fine-grained Authorization | "Nur der Auftragsersteller darf stornieren" | Domain-BC (domänenspezifische Regel) |

**Der typische Fehler:** Berechtigungslogik über alle Contexts verschmieren.
Sauberer ist: IAM stellt Identität + Rollen bereit, jeder Domain-Context entscheidet selbst, was diese Rollen in seinem Kontext dürfen.

### Im Event Storming: Authorization als Policy

Im Event Storming taucht Authorization an zwei Stellen auf:

  1. **Als Policy vor Commands** — die Frage "Darf dieser Actor dieses Command auslösen?" wird als gelbe Policy/Regel zwischen Actor und Command modelliert.
     Beispiel: `OrderPlaced` → Policy "nur Customer mit verifiziertem Account" → `ShipOrder`

  2. **Als eigener Kontext mit eigenen Events** — `UserRegistered`, `RoleAssigned`, `PermissionGranted`, `TokenIssued`.
     Diese laufen in einer eigenen Swimlane.

### Ein Event, mehrere Konsumenten

Ein Event "gehört" immer genau einem Bounded Context (dem, der es publiziert) — aber mehrere Contexts können es konsumieren:

```
IAM publiziert: UserRegistered
   |
   +-> Kundenverwaltung  (legt Customer an)
   +-> Notification       (schickt Welcome-Mail)
   +-> Analytics          (zählt Signups)
```

**Wichtige Unterscheidung:**

| Typ | Beschreibung |
|---|---|
| Domain Event (intern) | reichhaltig, contextspezifische Sprache, bleibt im Context |
| Integration Event (extern) | schlank, stabil, für andere Contexts gedacht |

**Anti-Pattern:** Geteiltes Event-Schema, das beide Contexts gemeinsam ändern müssen → erzeugt versteckte Kopplung.
Besser: Owner definiert das Schema, Konsumenten passen sich an (oder nutzen einen ACL).

### Typische Registrierungs-Szenarien

#### 1. Self-Service Signup (B2C)

```
User klickt "Registrieren"
   |
IAM:    UserRegistered          <- Identität entsteht hier
   | (Integration Event)
Kunde:  CustomerRegistered      <- Kunde reagiert, legt Profil an
```

IAM ist der Auslöser. Kunde folgt.

#### 2. Kunde wird vom Vertrieb angelegt (B2B)

```
Vertrieb legt Firmenkunde an
   |
Kunde:  CustomerRegistered      <- Kunde existiert zuerst (ohne Login!)
   | später, wenn jemand Zugang braucht
IAM:    UserRegistered          <- Login wird nachträglich erzeugt
   |
Kunde:  UserLinkedToCustomer
```

Kunde ist der Auslöser. IAM folgt — oder kommt gar nicht (Laufkundschaft).

#### 3. Mitarbeiter-Onboarding

```
HR legt Mitarbeiter an
   |
IAM:    UserRegistered          <- nur User, kein Kunde
```

Nur IAM. Kein Customer-BC involviert.

### Faustregel

Frag im Event Storming nicht "ist das technisch oder fachlich?", sondern:

> **Spricht hier jemand eine andere Fachsprache, und ändert sich das Modell aus anderen Gründen?**

Wenn ja → Bounded Context. Wenn die einzige Antwort "das macht das Framework" ist → kein BC, sondern Infrastruktur.

Integration: Andere Contexts konsumieren IAM meist über ein Token (JWT mit Claims) oder einen ACL — kein direkter DB-Zugriff.
Im Event Storming zeichnet man das als Context Map mit Customer/Supplier-Beziehung (IAM = Upstream).

### Authentication in Kubernetes (Kunde / Mobile App)


### Wer authentifiziert sich wozu?

Zuerst die wichtigste Unterscheidung – es gibt **zwei völlig getrennte Auth-Ebenen**:

![Zwei Auth-Ebenen in Kubernetes](/images/auth-zwei-ebenen.svg)

> **ServiceAccount-Token ist Workload-Identität** – für Pod-zu-Pod oder CI/CD-zu-API-Server.
> Ein Kunde / eine Mobile App bekommt **niemals** einen ServiceAccount-Token zu sehen.

---

### Der kube-apiserver und seine Auth-Mechanismen

Der kube-apiserver ist der **einzige Einstiegspunkt** für alle Kubernetes-Operationen.
Er kennt mehrere Auth-Methoden – aber keine davon ist für Kunden gedacht.

![kube-apiserver Auth-Methoden](/images/auth-kube-apiserver.svg)

#### OIDC am kube-apiserver konfigurieren (für Entwickler-Login)

```yaml
## kube-apiserver Flags (z.B. in /etc/kubernetes/manifests/kube-apiserver.yaml)
spec:
  containers:
  - command:
    - kube-apiserver
    - --oidc-issuer-url=https://keycloak.example.com/realms/myrealm
    - --oidc-client-id=kubectl
    - --oidc-username-claim=email
    - --oidc-groups-claim=groups
    # Damit kann ein Entwickler per Keycloak-Login kubectl benutzen
```

```yaml
## RBAC: Entwickler-Gruppe darf Pods lesen
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
  name: dev-team-view
subjects:
- kind: Group
  name: "dev-team"          # kommt aus dem groups-Claim im OIDC-Token
  apiGroup: rbac.authorization.k8s.io
roleRef:
  kind: ClusterRole
  name: view
  apiGroup: rbac.authorization.k8s.io
```

---

### Kunde / Mobile App – der richtige Auth-Flow

Ein externer Kunde hat **keinen Zugang zum kube-apiserver**. Er kommuniziert ausschließlich
über den **Ingress** mit den Anwendungs-Services. Der OIDC-Provider stellt JWTs aus,
die am Ingress oder im Service Mesh geprüft werden.

![Kunde / Mobile App Auth-Flow in Kubernetes](/images/auth-kunde-mobile-flow.svg)

---

### JWT-Flow für Mobile App – vollständiger Code

#### 1. PKCE Authorization Code Flow (Mobile App / SPA)

```
Mobile App                    Keycloak                   Backend (K8s)
    |                             |                             |
    |── GET /auth?                |                             |
    |   response_type=code        |                             |
    |   code_challenge=S256  ────>|                             |
    |                             |                             |
    |<── Login-Seite (redirect) ──|                             |
    |                             |                             |
    |── User gibt Credentials ───>|                             |
    |                             |                             |
    |<── Authorization Code ──────|                             |
    |                             |                             |
    |── POST /token               |                             |
    |   code + code_verifier ────>|                             |
    |                             |                             |
    |<── Access Token (JWT) ──────|                             |
    |    Refresh Token            |                             |
    |                             |                             |
    |── GET /api/orders           |                             |
    |   Authorization: Bearer JWT |──────────────────────────>  |
    |                             |             JWT prüfen      |
    |                             |             Claims extrahieren
    |<─────────────────────────── Antwort ────────────────────  |
```

#### 2. Keycloak Realm + Client einrichten

```bash
## Realm und Client via Keycloak Admin CLI anlegen
kcadm.sh create realms -s realm=myapp -s enabled=true

kcadm.sh create clients -r myapp \
  -s clientId=mobile-app \
  -s 'redirectUris=["myapp://callback", "https://app.example.com/callback"]' \
  -s publicClient=true \
  -s 'standardFlowEnabled=true' \
  -s 'attributes={"pkce.code.challenge.method":"S256"}'

## Custom Claims (z.B. Kundennummer) als Mapper hinzufügen
kcadm.sh create clients/<client-id>/protocol-mappers/models -r myapp \
  -s name=customer-id \
  -s protocolMapper=oidc-user-attribute-mapper \
  -s 'config={"user.attribute":"customerId","claim.name":"customer_id","access.token.claim":"true"}'
```

#### 3. Istio: JWT validieren + Claims als Header setzen

```yaml
## RequestAuthentication: Istio prüft das JWT selbst (kein Code im Service nötig)
apiVersion: security.istio.io/v1beta1
kind: RequestAuthentication
metadata:
  name: customer-jwt
  namespace: production
spec:
  jwtRules:
  - issuer: "https://keycloak.example.com/realms/myapp"
    jwksUri: "https://keycloak.example.com/realms/myapp/protocol/openid-connect/certs"
    audiences:
    - "mobile-app"
    outputClaimToHeaders:
    - header: x-customer-id      # claim → HTTP-Header für Backend
      claim: customer_id
    - header: x-user-sub
      claim: sub
    - header: x-user-email
      claim: email
    forwardOriginalToken: false   # JWT nicht ans Backend weitergeben
```

```yaml
## AuthorizationPolicy: Requests OHNE gültiges JWT ablehnen
apiVersion: security.istio.io/v1beta1
kind: AuthorizationPolicy
metadata:
  name: require-customer-jwt
  namespace: production
spec:
  selector:
    matchLabels:
      app: order-service
  action: DENY
  rules:
  - from:
    - source:
        notRequestPrincipals: ["*"]   # kein JWT vorhanden → DENY
---
## Feinere Regel: GET ohne Auth, POST nur mit gültigem JWT
apiVersion: security.istio.io/v1beta1
kind: AuthorizationPolicy
metadata:
  name: order-service-rules
  namespace: production
spec:
  selector:
    matchLabels:
      app: order-service
  action: ALLOW
  rules:
  - to:
    - operation:
        methods: ["GET"]
        paths: ["/api/v1/products*"]   # Produktliste: öffentlich
  - from:
    - source:
        requestPrincipals: ["*"]        # Eingeloggte Kunden
    to:
    - operation:
        methods: ["GET", "POST"]
        paths: ["/api/v1/orders*"]
```

#### 4. Backend-Service liest Claims aus Headern (kein JWT-Parsing)

```go
// order-service/handler.go
// Istio hat das JWT validiert und Claims als Header gesetzt.
// Der Service braucht keine JWT-Bibliothek.
func CreateOrderHandler(w http.ResponseWriter, r *http.Request) {
    customerID := r.Header.Get("X-Customer-Id")
    userSub    := r.Header.Get("X-User-Sub")

    if customerID == "" {
        http.Error(w, "missing customer identity", http.StatusUnauthorized)
        return
    }

    order := Order{
        CustomerID: customerID,
        CreatedBy:  userSub,
    }
    // Datenbanklogik ...
    w.WriteHeader(http.StatusCreated)
    json.NewEncoder(w).Encode(order)
}
```

```python
## payment-service/app.py (FastAPI)
from fastapi import FastAPI, Header, HTTPException

app = FastAPI()

@app.post("/api/v1/payments")
async def create_payment(
    x_customer_id: str = Header(None, alias="x-customer-id"),
    x_user_sub:    str = Header(None, alias="x-user-sub"),
):
    if not x_customer_id:
        raise HTTPException(status_code=401, detail="No identity")

    # x_customer_id kommt gesetzt von Istio – keine weitere Validierung nötig
    return {"status": "ok", "customer": x_customer_id}
```

---

### Token-Refresh in der Mobile App

```swift
// iOS Swift – Token automatisch erneuern
class TokenManager {
    private var accessToken: String?
    private var refreshToken: String?
    private var expiresAt: Date?

    func getValidToken() async throws -> String {
        // Abgelaufen? Neu holen via Refresh Token
        if let exp = expiresAt, Date() > exp.addingTimeInterval(-60) {
            try await refreshAccessToken()
        }
        return accessToken ?? { throw AuthError.notAuthenticated }()
    }

    private func refreshAccessToken() async throws {
        let body = "grant_type=refresh_token&refresh_token=\(refreshToken!)&client_id=mobile-app"
        var req = URLRequest(url: URL(string: "https://keycloak.example.com/realms/myapp/protocol/openid-connect/token")!)
        req.httpMethod = "POST"
        req.httpBody = body.data(using: .utf8)

        let (data, _) = try await URLSession.shared.data(for: req)
        let tokens = try JSONDecoder().decode(TokenResponse.self, from: data)
        accessToken = tokens.accessToken
        expiresAt   = Date().addingTimeInterval(Double(tokens.expiresIn))
    }
}
```

---

### Zusammenfassung: Was validiert was?

![Zusammenfassung: Was validiert was?](/images/auth-zusammenfassung.svg)

---

### Login-Button in einer Web-App

Du baust **kein eigenes Login-Formular**. Keycloak stellt die Login-Seite bereit.
Deine App hat nur einen Button – Klick darauf startet den Redirect zu Keycloak.

![Login-Button Flow mit Keycloak](/images/auth-login-button-flow.svg)

Der entscheidende Punkt: **Ab Schritt ⑪ kontaktiert deine App Keycloak nicht mehr.**
Das JWT wird vom Istio Gateway lokal geprüft (Public Key wurde einmalig geholt und gecacht).

#### React-Beispiel mit keycloak-js

```bash
npm install keycloak-js
```

```javascript
// keycloak.js – einmalig initialisieren
import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
  url:      'https://keycloak.example.com',
  realm:    'myrealm',
  clientId: 'my-webapp',
});

export default keycloak;
```

```jsx
// App.jsx
import { useEffect, useState } from 'react';
import keycloak from './keycloak';

export default function App() {
  const [ready, setReady] = useState(false);

  useEffect(() => {
    keycloak
      .init({ onLoad: 'check-sso', pkceMethod: 'S256' })
      .then(() => setReady(true));

    // Token automatisch erneuern bevor es abläuft
    setInterval(() => keycloak.updateToken(60), 30000);
  }, []);

  if (!ready) return <p>Laden...</p>;

  if (!keycloak.authenticated) {
    return <button onClick={() => keycloak.login()}>Login</button>;
  }

  return (
    <div>
      <p>Eingeloggt als {keycloak.tokenParsed.email}</p>
      <button onClick={() => keycloak.logout()}>Logout</button>
      <Orders />
    </div>
  );
}
```

```javascript
// api.js – Token zu jedem API-Call hinzufügen
import keycloak from './keycloak';

export async function fetchOrders() {
  const response = await fetch('/api/orders', {
    headers: {
      Authorization: `Bearer ${keycloak.token}`,
    },
  });
  return response.json();
}
```

```yaml
## Keycloak Client-Konfiguration (Public Client für SPA)
## In Keycloak Admin Console:
## Client ID:        my-webapp
## Client Protocol: openid-connect
## Access Type:     public          ← kein Client-Secret nötig
## Valid Redirect:  https://app.example.com/*
## Web Origins:     https://app.example.com
```

---

### Weiterführendes

- [Keycloak – Mobile App PKCE](https://www.keycloak.org/docs/latest/securing_apps/#_mobile_apps)
- [Istio RequestAuthentication](https://istio.io/latest/docs/reference/config/security/request_authentication/)
- [oauth2-proxy Docs](https://oauth2-proxy.github.io/oauth2-proxy/)
- [Kubernetes OIDC Auth](https://kubernetes.io/docs/reference/access-authn-authz/authentication/#openid-connect-tokens)
- [RFC 7636 – PKCE](https://datatracker.ietf.org/doc/html/rfc7636)

### API Gateway vs. Istio Service Mesh – Authentication


Istio übernimmt Authentication für **beide Verkehrsrichtungen** im Kubernetes-Cluster:
Nord-Süd (externe Clients → Services) und Ost-West (Service → Service).

Ein dediziertes API Gateway (Kong, APISIX, Tyk) ist die **Alternative** – sinnvoll
nur wenn Istio an seine Grenzen stößt.

---

### Istio als primäre Lösung

Istio löst das Auth-Problem auf zwei Ebenen gleichzeitig:

| Ebene | Mechanismus | Schützt |
|-------|------------|---------|
| Nord-Süd | Istio Ingress Gateway + RequestAuthentication | Eingehende Requests von außen |
| Ost-West | PeerAuthentication (mTLS) + AuthorizationPolicy | Service-zu-Service intern |

![Istio übernimmt Nord-Süd und Ost-West](/images/auth-istio-nord-sued.svg)

---

### Teil 1: Nord-Süd mit Istio

#### Wie es funktioniert

Der **Istio Ingress Gateway** ist der einzige Einstiegspunkt von außen.
Er terminiert TLS und prüft das JWT via `RequestAuthentication` – direkt
gegen den JWKS-Endpoint von Keycloak. Die Backend-Services bekommen
die verifizierten Claims als HTTP-Header.

```
Mobile App                  Istio Ingress Gateway           Order Service
    │                               │                             │
    │── GET /api/orders             │                             │
    │   Authorization: Bearer JWT ─>│                             │
    │                               │  JWT prüfen (JWKS)          │
    │                               │  Signatur ✓  exp ✓          │
    │                               │  sub → x-user-sub           │
    │                               │  role → x-user-role         │
    │                               │                             │
    │                               │── mTLS ────────────────────>│
    │                               │   x-user-sub: user-42       │
    │                               │   x-user-role: customer     │
    │                               │                             │
    │<───────────────────────── Response ────────────────────────│
```

#### Code: Istio Gateway + VirtualService

```yaml
## Gateway: TLS terminieren, Port 443 öffnen
apiVersion: networking.istio.io/v1beta1
kind: Gateway
metadata:
  name: api-gateway
  namespace: production
spec:
  selector:
    istio: ingressgateway
  servers:
  - port:
      number: 443
      name: https
      protocol: HTTPS
    tls:
      mode: SIMPLE
      credentialName: api-tls-cert   # Secret mit TLS-Zertifikat (z.B. via cert-manager)
    hosts:
    - api.example.com
---
## VirtualService: Routing zu den Services
apiVersion: networking.istio.io/v1beta1
kind: VirtualService
metadata:
  name: api-routes
  namespace: production
spec:
  hosts:
  - api.example.com
  gateways:
  - api-gateway
  http:
  - match:
    - uri:
        prefix: /api/orders
    route:
    - destination:
        host: order-service
        port:
          number: 8080
  - match:
    - uri:
        prefix: /api/products
    route:
    - destination:
        host: product-service
        port:
          number: 8080
```

#### Code: RequestAuthentication – JWT prüfen

```yaml
## Istio validiert JWT selbst via JWKS – kein Code im Service nötig
apiVersion: security.istio.io/v1beta1
kind: RequestAuthentication
metadata:
  name: customer-jwt
  namespace: production
spec:
  # Kein selector → gilt für alle Services im Namespace
  jwtRules:
  - issuer: "https://keycloak.example.com/realms/myrealm"
    jwksUri: "https://keycloak.example.com/realms/myrealm/protocol/openid-connect/certs"
    audiences:
    - "myapp"
    outputClaimToHeaders:          # Claims → HTTP-Header für Backend-Services
    - header: x-user-sub
      claim: sub
    - header: x-user-role
      claim: role
    - header: x-customer-id
      claim: customer_id
    forwardOriginalToken: false    # rohen JWT nicht ans Backend weitergeben
```

#### Code: AuthorizationPolicy – Zugriff steuern (Nord-Süd)

```yaml
## Kein gültiges JWT → ablehnen
apiVersion: security.istio.io/v1beta1
kind: AuthorizationPolicy
metadata:
  name: require-jwt
  namespace: production
spec:
  selector:
    matchLabels:
      app: order-service
  action: DENY
  rules:
  - from:
    - source:
        notRequestPrincipals: ["*"]   # kein JWT vorhanden
---
## GET für alle eingeloggten User, POST nur für bestimmte Rollen
apiVersion: security.istio.io/v1beta1
kind: AuthorizationPolicy
metadata:
  name: order-service-rules
  namespace: production
spec:
  selector:
    matchLabels:
      app: order-service
  action: ALLOW
  rules:
  - to:
    - operation:
        methods: ["GET"]
        paths: ["/api/orders*"]
    when:
    - key: request.auth.principal
      notValues: [""]
  - to:
    - operation:
        methods: ["POST"]
        paths: ["/api/orders"]
    when:
    - key: request.auth.claims[role]
      values: ["customer", "admin"]
```

#### Code: Backend-Service liest nur Header

```go
// Go – Istio hat JWT validiert, Claims stehen als Header bereit
// Keine JWT-Bibliothek nötig
func createOrderHandler(w http.ResponseWriter, r *http.Request) {
    userSub    := r.Header.Get("X-User-Sub")
    role       := r.Header.Get("X-User-Role")
    customerID := r.Header.Get("X-Customer-Id")

    if userSub == "" {
        http.Error(w, "Unauthorized", http.StatusUnauthorized)
        return
    }
    log.Printf("Order von %s (Rolle: %s, KundeID: %s)", userSub, role, customerID)
    // Business-Logik ...
}
```

```python
## FastAPI – identisch
from fastapi import FastAPI, Header, HTTPException

app = FastAPI()

@app.post("/api/orders")
async def create_order(
    x_user_sub:    str = Header(None),
    x_user_role:   str = Header(None),
    x_customer_id: str = Header(None),
):
    if not x_user_sub:
        raise HTTPException(status_code=401)
    return {"status": "created", "user": x_user_sub, "role": x_user_role}
```

---

### Teil 2: Ost-West mit Istio

#### Code: mTLS cluster-weit erzwingen

```yaml
## PeerAuthentication STRICT: kein Plain-Text mehr zwischen Pods
apiVersion: security.istio.io/v1beta1
kind: PeerAuthentication
metadata:
  name: default
  namespace: istio-system   # gilt für gesamten Cluster
spec:
  mtls:
    mode: STRICT
```

#### Code: Service-zu-Service Zugriff einschränken

```yaml
## Nur order-service darf payment-service aufrufen
## Identität kommt aus dem Kubernetes ServiceAccount (SPIFFE/SVID)
apiVersion: security.istio.io/v1beta1
kind: AuthorizationPolicy
metadata:
  name: payment-allow-order
  namespace: production
spec:
  selector:
    matchLabels:
      app: payment-service
  action: ALLOW
  rules:
  - from:
    - source:
        principals:
        - "cluster.local/ns/production/sa/order-service"
    to:
    - operation:
        methods: ["POST"]
        paths: ["/internal/payments"]
```

```yaml
## ServiceAccounts explizit vergeben – Basis für die SPIFFE-Identität
apiVersion: v1
kind: ServiceAccount
metadata:
  name: order-service
  namespace: production
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: order-service
  namespace: production
spec:
  template:
    spec:
      serviceAccountName: order-service   # → SPIFFE: cluster.local/ns/production/sa/order-service
      containers:
      - name: order-service
        image: my-registry/order-service:1.0
```

#### Istio installieren und testen

```bash
## 1. Istio installieren
istioctl install --set profile=default -y

## 2. Namespace für Sidecar-Injection markieren
kubectl label namespace production istio-injection=enabled

## 3. Pods neu starten (Sidecar wird injiziert)
kubectl rollout restart deployment -n production

## 4. Prüfen: 2/2 = App + Envoy Sidecar
kubectl get pods -n production
## order-service-7d9f8b-xk2pq   2/2   Running

## 5. mTLS testen: Plain-Text-Verbindung muss fehlschlagen
kubectl run test --image=curlimages/curl --rm -it -- \
  curl http://payment-service.production.svc.cluster.local:9090/health
## → Connection reset (STRICT mTLS aktiv)

## 6. AuthorizationPolicy testen
kubectl exec -it deploy/order-service -n production -- \
  curl http://payment-service:9090/internal/payments -X POST
## → 200 OK  (order-service SA ist erlaubt)

kubectl exec -it deploy/product-service -n production -- \
  curl http://payment-service:9090/internal/payments -X POST
## → 403 RBAC: access denied  (product-service SA ist nicht erlaubt)
```

---

### Wann zusätzlich ein API Gateway?

Istio ist für die meisten Szenarien ausreichend. Ein API Gateway (Kong, APISIX, Tyk)
kommt hinzu, wenn spezifische API-Management-Features gebraucht werden,
die Istio nicht bietet.

![Wann reicht Istio – wann brauche ich ein API Gateway?](/images/auth-wann-api-gateway.svg)

#### Typische Gründe für ein API Gateway

**API-Key-Management:** Externe Partner oder Drittanbieter bekommen API-Keys statt JWTs.
Istio kennt kein Consumer-Konzept mit Key-Verwaltung.

**Developer Portal:** Öffentliche API-Dokumentation, Self-Service-Registrierung,
Key-Generierung für externe Entwickler.

**Request/Response-Transformation:** Body umschreiben, Header hinzufügen/entfernen,
Protokollkonvertierung (REST → gRPC) – in Istio nur über komplexe EnvoyFilter möglich.

#### Code: Kong vor Istio (wenn beides gebraucht wird)

```yaml
## Kong läuft als Deployment – der Ingress-Traffic geht durch Kong,
## dann ins Istio Mesh weiter
apiVersion: configuration.konghq.com/v1
kind: KongConsumer
metadata:
  name: partner-app
  namespace: production
  annotations:
    kubernetes.io/ingress.class: kong
username: partner-app
---
## API-Key für den Consumer
apiVersion: v1
kind: Secret
metadata:
  name: partner-app-key
  namespace: production
  labels:
    konghq.com/credential: key-auth
type: Opaque
stringData:
  kongCredType: key-auth
  key: "abc123geheimerschluessel"
---
## Route mit Key-Auth absichern
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: partner-api
  namespace: production
  annotations:
    konghq.com/plugins: key-auth-plugin
spec:
  ingressClassName: kong
  rules:
  - host: api.example.com
    http:
      paths:
      - path: /partner/
        pathType: Prefix
        backend:
          service:
            name: order-service   # Istio Sidecar läuft weiterhin im order-service Pod
            port:
              number: 8080
```

```
Internet
   │  API-Key oder JWT
   ▼
┌──────────────────────────────────┐
│  Kong API Gateway                │
│  API-Key prüfen, Rate Limit,     │
│  X-Consumer-* Header setzen      │
└────────────────┬─────────────────┘
                 │
                 ▼
┌──────────────────────────────────┐
│  Istio Service Mesh              │
│  mTLS Ost-West, AuthPolicy       │
│  (Kong-Pod hat auch Envoy)       │
└──────────────────────────────────┘
```

---

### Weiterführendes

- [Istio Ingress Gateway](https://istio.io/latest/docs/tasks/traffic-management/ingress/ingress-control/)
- [Istio RequestAuthentication](https://istio.io/latest/docs/reference/config/security/request_authentication/)
- [Istio AuthorizationPolicy](https://istio.io/latest/docs/reference/config/security/authorization-policy/)
- [Kong Kubernetes Ingress Controller](https://docs.konghq.com/kubernetes-ingress-controller/)
- [Apache APISIX für Kubernetes](https://apisix.apache.org/docs/ingress-controller/getting-started/)

### JWT mit Keycloak und Istio — Login-Flow, Client Credentials, Validierung


### Was ist JWT?

Ein **JSON Web Token (JWT)** ist ein kompaktes, selbstbeschreibendes Token,
das Informationen (Claims) ueber einen User oder Service traegt.
Es besteht aus drei Base64Url-kodierten Teilen, getrennt durch Punkte.

![JWT Aufbau – Header, Payload, Signatur](/images/jwt-aufbau.svg)

| Teil | Inhalt | Zweck |
|------|--------|-------|
| **Header** | Algorithm (RS256), Token-Typ | Wie wurde signiert? |
| **Payload** | sub, email, roles, exp | Wer bin ich, was darf ich? |
| **Signatur** | RSA-signiert mit Keycloak Private Key | Manipulationsschutz |

Die Signatur kann jeder mit dem **oeffentlichen Key** von Keycloak pruefen –
ohne dass Keycloak dafuer erreichbar sein muss.

---

### Weg 1: User meldet sich an (Browser/App)

Der Standard-Flow fuer Webanwendungen heisst **Authorization Code Flow**.
Kein Passwort wandert durch die App – nur ein kurzlebiger "Code".

![Authorization Code Flow – User Login](/images/jwt-login-flow.svg)

#### Was passiert Schritt fuer Schritt

| Schritt | Wer | Was |
|---------|-----|-----|
| 1 | Browser | Leitet zu Keycloak weiter — mit `redirect_uri=https://backend.com/callback` |
| 2 | Keycloak | Zeigt Login-Formular, prueft Credentials |
| 3 | Keycloak | Antwortet mit **HTTP 302 Redirect** zu `https://backend.com/callback?code=abc123` |
| 4 | Browser | **Folgt dem Redirect automatisch** — ladet die Callback-URL des Backends (Code steckt im Query-Parameter) |
| 5 | Backend | Schickt `code` + `client_secret` an Keycloak (Server-to-Server, Browser nicht beteiligt) |
| 6 | Keycloak | **Stellt den JWT aus** — prueft code, generiert Token, signiert ihn mit Private Key, schickt ihn ans Backend |
| 7 | Backend | Speichert JWT in Session oder Cookie |

**Der entscheidende Mechanismus in Schritt 3–4:**
Keycloak schickt keinen Token an den Browser — es schickt nur einen **Redirect**.
Der Browser folgt diesem Redirect blind und landet auf der Callback-URL des Backends.
Dabei uebertraegt er den `code` als URL-Parameter. Der Browser "weiss" nicht, was er tut —
er folgt nur einer HTTP-Weiterleitung.

**Warum laufen Schritt 5–6 am Browser vorbei?**
Das Backend ruft Keycloak direkt auf — ohne Browser-Beteiligung.
Dabei sendet es den `client_secret`, der **niemals** den Server verlassen darf.
Der Browser bekommt den `access_token` nie zu sehen — nur das Backend haelt ihn.

---

### Weg 2: Service holt sich selbst ein JWT (Service-to-Service)

Wenn kein User beteiligt ist – z.B. ein Cronjob oder Microservice ruft einen
anderen Service auf – gibt es keinen Login-Dialog.
Der Flow heisst **Client Credentials**.

![Client Credentials Flow – Service-to-Service](/images/jwt-client-credentials.svg)

```
POST https://keycloak/realms/myrealm/protocol/openid-connect/token
Content-Type: application/x-www-form-urlencoded

grant_type=client_credentials
&client_id=service-a
&client_secret=geheim123
```

Response:
```json
{
  "access_token": "eyJhbGciOiJSUzI1NiJ9...",
  "expires_in": 300,
  "token_type": "Bearer"
}
```

Der Service speichert den Token und sendet ihn bei jedem Folge-Request mit:

```
GET /api/orders
Authorization: Bearer eyJhbGciOiJSUzI1NiJ9...
```

**Wichtig:** Den Token bis kurz vor Ablauf cachen – nicht bei jedem Call
neu anfordern. `expires_in` gibt die Lebensdauer in Sekunden an.

---

### Wo landet das JWT im Request?

```
GET /api/orders HTTP/1.1
Host: my-service.example.com
Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyLTQyIiwiZW1haWwiOiJ1c2VyQGV4YW1wbGUuY29tIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImFkbWluIl19LCJleHAiOjE3MTYwMDAzMDB9.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

Das Token ist einfach ein langer String im `Authorization`-Header.
Der Empfaenger entschluesselt ihn (kein Netzwerkaufruf noetig) und
liest die Claims direkt aus dem Payload.

---

### Wie Istio das JWT prueft – ohne App-Code-Aenderung

Istio's Envoy-Sidecar interceptet jeden eingehenden Request **bevor**
er den App-Container erreicht. Die Validierung laeuft vollautomatisch.

![Istio JWT-Validierung](/images/jwt-istio-validation.svg)

#### Konfiguration: RequestAuthentication

```yaml
apiVersion: security.istio.io/v1beta1
kind: RequestAuthentication
metadata:
  name: jwt-keycloak
  namespace: production
spec:
  jwtRules:
  - issuer: "https://keycloak/realms/myrealm"
    jwksUri: "https://keycloak/realms/myrealm/protocol/openid-connect/certs"
```

Istio holt die **Public Keys automatisch** vom JWKS-Endpoint und prueft:
- Signatur korrekt? (RSA)
- Token nicht abgelaufen? (`exp`)
- Issuer stimmt? (`iss`)

#### Konfiguration: AuthorizationPolicy

```yaml
apiVersion: security.istio.io/v1beta1
kind: AuthorizationPolicy
metadata:
  name: require-admin
  namespace: production
spec:
  rules:
  - from:
    - source:
        requestPrincipals: ["*"]
    when:
    - key: request.auth.claims[realm_access/roles]
      values: ["admin"]
```

#### Was Istio mit validierten Claims macht

Nach erfolgreicher Pruefung extrahiert Istio Claims als HTTP-Header:

| Claim im JWT | HTTP-Header | Beispielwert |
|-------------|-------------|--------------|
| `sub` | `x-forwarded-client-cert` / `x-user-sub` | `user-42` |
| `email` | `x-user-email` | `user@example.com` |
| `realm_access.roles` | `x-user-role` | `admin` |

Die App-Container lesen einfach diese Header – kein JWT-Parsing,
keine Signaturpruefung im Code.

---

### Zusammenfassung: Wer macht was?

| Komponente | Aufgabe |
|-----------|---------|
| **Keycloak** | Ausstellen und Signieren von JWTs (OIDC-Server) |
| **Browser/App** | Leitet zur Login-Page weiter, tauscht Code gegen Token |
| **Backend-Service** | Speichert Token, sendet im `Authorization`-Header |
| **Microservice** | Holt Token per Client Credentials, cacht ihn |
| **Istio Sidecar** | Validiert JWT, blockiert invalide Requests, extrahiert Claims |
| **App-Container** | Muss JWT nicht kennen – liest nur Header |

**Der Kernvorteil von Istio:** Authentication wird aus dem App-Code
herausgezogen und zentral konfiguriert. Neue Services sind automatisch
geschuetzt, ohne dass Entwickler Auth-Bibliotheken einbinden muessen.

### Datenmigration: Notification Service (Dual Write, Outbox, Backfill)


Dieses Dokument zeigt anhand des **Notification Service** von ShopMax,
wie eine Datenbank-Migration beim Herausloesen eines Microservices konkret ablaeuft.

---

### Verwendete Patterns im Ueberblick

| Pattern | Zweck | Warum hier |
|---|---|---|
| **Database-per-Service** | Jeder Service bekommt eine eigene, isolierte DB | Zielarchitektur — ohne eigene DB sind Services faktisch noch ein Monolith |
| **Strangler Fig (DB-Ebene)** | Alte Tabelle bleibt waehrend der Migration erhalten | Kein Big-Bang-Cut — Monolith laeuft weiter, kein Datenverlustrisiko |
| **Backfill** | Historische Daten einmalig in neue DB kopieren | Neue DB muss vollstaendig sein, bevor Reads umgestellt werden koennen |
| **Dual Write** | Monolith schreibt waehrend Migration in beide DBs | Neue DB bleibt aktuell, bevor der Switch vollzogen ist |
| **Outbox Pattern** | Konsistenz beim Dual Write sicherstellen | Loest das Problem: Was passiert, wenn einer der beiden Writes fehlschlaegt? |

---

### Ausgangslage: Notifications in der Monolith-DB

Die `notifications`-Tabelle sitzt heute in der gemeinsamen Monolith-Datenbank
und haengt ueber Foreign Keys an `users` und `orders`:

```sql
-- Monolith-DB (PostgreSQL, gemeinsam mit allen anderen Services)

CREATE TABLE notifications (
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT NOT NULL REFERENCES users(id),
    order_id    BIGINT REFERENCES orders(id),
    type        VARCHAR(50)  NOT NULL,  -- ORDER_CONFIRMATION, SHIPPING, INVOICE
    channel     VARCHAR(20)  NOT NULL,  -- EMAIL, SMS
    content     TEXT,
    sent_at     TIMESTAMP,
    status      VARCHAR(20)  NOT NULL   -- SENT, FAILED, PENDING
);
```

```
+-----------------------------+
|       Monolith-DB           |
|                             |
|  users                      |
|  orders  <--+               |
|  notifications --+          |
|             |   |           |
|             FK  FK          |
+-----------------------------+
```

**Das Problem mit den Foreign Keys:**
Foreign Keys erzwingen, dass `users` und `orders` in derselben DB stehen wie `notifications`.
Solange das so ist, kann der Notification Service keine eigene DB haben —
er ist faktisch mit dem Monolith verdrahtet.

---

### Ziel: Eigene Datenbank fuer den Notification Service

Im Notification Service gibt es keine Foreign Keys mehr.
Statt `user_id` werden die benoetigten Daten denormalisiert gespeichert.

```sql
-- Notification-Service-DB (eigene PostgreSQL-Instanz)

CREATE TABLE notifications (
    id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    recipient_email  VARCHAR(255) NOT NULL,  -- denormalisiert, kein FK zu users
    recipient_phone  VARCHAR(50),
    order_reference  VARCHAR(100),           -- nur Referenz-ID, kein FK zu orders
    type             VARCHAR(50)  NOT NULL,
    channel          VARCHAR(20)  NOT NULL,
    content          TEXT,
    sent_at          TIMESTAMP,
    status           VARCHAR(20)  NOT NULL
);
```

```
+-----------------------------+     +-----------------------------+
|       Monolith-DB           |     |    Notification-Service-DB  |
|                             |     |                             |
|  users                      |     |  notifications              |
|  orders                     |     |    recipient_email (Text)   |
|  (notifications entfernt)   |     |    order_reference (Text)   |
|                             |     |    (keine Foreign Keys)     |
+-----------------------------+     +-----------------------------+
       [Monolith]                        [Notification Service]
```

**Warum Denormalisierung?**
Microservices duerfen nicht in fremde Datenbanken schauen.
Der Notification Service braucht nur die E-Mail-Adresse und die Bestellnummer —
diese Werte werden beim Schreiben mitgegeben, nicht nachtraeglich per JOIN geholt.

---

### Migrationspfad (Schritt fuer Schritt)

#### Phase 1 — Neue Datenbank aufsetzen

**Pattern: Database-per-Service**

Als erstes wird nur die Infrastruktur bereitgestellt: eine eigene PostgreSQL-Instanz
fuer den Notification Service mit dem neuen Schema (ohne Foreign Keys).
Der neue Service laeuft noch nicht — die DB ist leer und wird noch nicht befuellt.

```
[Monolith-DB]                    [Notification-Service-DB]
  notifications (alt)              notifications (neu, leer)
  users
  orders
```

**Warum zuerst die DB, noch vor dem Code?**
Ohne DB kann der neue Service keine Daten speichern.
Der Backfill (Phase 2) braucht eine fertige Zieldatenbank.

---

#### Phase 2 — Historische Daten migrieren

**Pattern: Backfill**

Die neue DB wird mit allen historischen Daten aus der Monolith-DB befuellt —
bevor der neue Service auch nur eine einzige neue Notification empfaengt.

```sql
-- Einmalige Backfill-Migration (laeuft gegen Monolith-DB)
-- Fuehrt den notwendigen JOIN noch einmal aus, um E-Mail + Telefon aufzuloesen

INSERT INTO notification_service_db.notifications
    (id, recipient_email, recipient_phone, order_reference,
     type, channel, content, sent_at, status)
SELECT
    gen_random_uuid(),
    u.email,
    u.phone,
    o.reference_number,
    n.type,
    n.channel,
    n.content,
    n.sent_at,
    n.status
FROM notifications n
JOIN users  u ON u.id = n.user_id
JOIN orders o ON o.id = n.order_id;
```

**Warum Backfill vor dem Code-Switch?**
Wuerde man den neuen Service zuerst aktivieren und erst danach den Backfill laufen lassen,
wuerde die neue DB neue Notifications empfangen, aber der historische Stand fehlt.
Reports, Retry-Logik und Kundenanfragen wuerden fehlschlagen.
Die neue DB muss vollstaendig sein, bevor der neue Service aktiv wird.

**Kontrolle nach dem Backfill:**

```sql
-- Zeilenanzahl muss uebereinstimmen
SELECT COUNT(*) FROM monolith_db.notifications;
SELECT COUNT(*) FROM notification_service_db.notifications;

-- Stichprobe: letzte 100 Eintraege manuell pruefen
SELECT * FROM notification_service_db.notifications
ORDER BY sent_at DESC LIMIT 100;
```

---

#### Phase 3 — Strangler Fig auf Code-Ebene

**Pattern: Strangler Fig**

Erst jetzt — nachdem die neue DB vollstaendig befuellt ist — wird der Code umgeschaltet.
Der direkte Aufruf bleibt als Sicherheitsnetz erhalten, der neue Event-Pfad kommt dazu.

**Phase 3a: Beide Pfade parallel betreiben**

```java
// Uebergangsphase: alter Aufruf bleibt, neuer Event-Pfad kommt dazu
public Order placeOrder(Cart cart, Customer customer) {
    Order order = createOrder(cart);
    paymentService.charge(customer, order.total());

    // Alter Pfad: laeuft weiter (Sicherheitsnetz)
    notificationService.sendOrderConfirmation(customer.email(), order);

    // Neuer Pfad: Event fuer den Notification Service
    eventBus.publish(new OrderPlacedEvent(
        order.id(),
        customer.email(),
        customer.phone(),
        order.total()
    ));

    return order;
}
```

```
[Monolith]
    |
    |-- direkter Aufruf --> [Notification-Code im Monolith] --> [Monolith-DB]
    |
    +-- Event -----------> [Neuer Notification Service]     --> [Notification-Svc-DB]
```

Beide Pfade laufen gleichzeitig. Neue Notifications landen in beiden DBs —
die neue DB hat nun historische Daten (aus Phase 2) und neue Daten (aus Events).

**Phase 3b: Neuen Pfad verifizieren**

```sql
-- Vergleich: gleiche Anzahl neuer Notifications seit Aktivierung des neuen Pfads?
SELECT COUNT(*) FROM monolith_db.notifications        WHERE sent_at > '2024-01-15';
SELECT COUNT(*) FROM notification_svc_db.notifications WHERE sent_at > '2024-01-15';
```

**Phase 3c: Alten Pfad entfernen**

Erst wenn der neue Pfad bestaetigt ist:

```java
// Nur noch Event — direkter Aufruf entfernt
public Order placeOrder(Cart cart, Customer customer) {
    Order order = createOrder(cart);
    paymentService.charge(customer, order.total());

    eventBus.publish(new OrderPlacedEvent(
        order.id(),
        customer.email(),
        customer.phone(),
        order.total()
    ));

    return order;
}
```

**Warum parallel und nicht direkt umschalten?**
Ein sofortiger Switch ist ein Big-Bang-Cut ohne Fallback.
Beim parallelen Betrieb laeuft der Monolith weiter als Sicherheitsnetz,
bis der neue Pfad bewiesen ist.

---

#### Phase 4 — Dual Write (nur zur Erklaerung — in der Praxis ueberspringen)

**Pattern: Dual Write**

> **Hinweis:** Phase 4 ist kein empfohlener Implementierungsschritt.
> Sie erklaert, was Teams ohne Outbox Pattern tun — und warum es ein Problem ist.
> In der Praxis direkt zu Phase 5 (Outbox) wechseln.

Ohne Outbox Pattern wuerde man versucht sein, einfach in beide DBs zu schreiben:

```java
// Im Monolith (naiver Ansatz)
public void sendNotification(Notification n) {
    monolithDb.insert(n);            // alte Tabelle
    notificationServiceDb.insert(n); // neue DB
}
```

```
[Monolith]
    |
    |-- INSERT --> [Monolith-DB: notifications]     (1. Write)
    |
    +-- INSERT --> [Notification-Service-DB]        (2. Write)
```

**Warum das nicht funktioniert:**
Die beiden INSERTs laufen nicht in einer Transaktion.
Schlaegt der zweite fehl (Netzwerkfehler, DB-Ausfall), ist die neue DB inkonsistent —
ohne Moeglichkeit zur automatischen Wiederholung. Datenverlust ist die Folge.
Deshalb: direkt zu Phase 5.

---

#### Phase 5 — Outbox Pattern fuer Konsistenz

**Pattern: Outbox Pattern**

Statt direkt in beide DBs zu schreiben, schreibt der Monolith atomar
in seine eigene DB — aber zusaetzlich in eine `outbox`-Tabelle.
Ein separater Relay-Prozess liest die Outbox und schreibt in die neue DB.

```sql
-- Neue Tabelle in der Monolith-DB
CREATE TABLE notification_outbox (
    id          BIGSERIAL PRIMARY KEY,
    payload     JSONB NOT NULL,      -- serialisierte Notification
    created_at  TIMESTAMP DEFAULT NOW(),
    relayed_at  TIMESTAMP            -- NULL = noch nicht uebertragen
);
```

```java
// Im Monolith: alles in EINER Transaktion
@Transactional
public void sendNotification(Notification n) {
    monolithDb.insert(n);
    outboxDb.insert(toJson(n));   // atomar mit obigem INSERT
    // kein direkter Write in Notification-Service-DB mehr
}
```

**Ja — die Aktualisierung der Service-DB ist asynchron.**

Der Monolith kehrt sofort zurueck, nachdem er in seine eigene DB geschrieben hat.
Die Notification-Service-DB wird erst danach aktualisiert — durch einen
separaten Relay-Prozess, der unabhaengig laeuft:

```
[Monolith] --Transaktion--> [Monolith-DB]
    |                           |
    | (kehrt sofort zurueck)    | notification_outbox: neuer Eintrag
    v                           |
[Response an Client]            |
                                | (asynchron, Millisekunden bis Sekunden spaeter)
                                v
                       [Relay-Prozess]
                                |
                                v
                   [Notification-Service-DB]
```

**Variante A — Polling (einfacher):**

Ein Hintergrund-Thread fragt die Outbox-Tabelle direkt per SQL ab,
schreibt in die neue DB und markiert den Eintrag als verarbeitet:

```sql
-- Schritt 1: Offene Eintraege holen (Relay-Prozess liest Monolith-DB)
SELECT id, payload
FROM notification_outbox
WHERE relayed_at IS NULL
ORDER BY created_at
LIMIT 100;

-- Schritt 2: Eintrag in Notification-Service-DB schreiben
INSERT INTO notification_svc_db.notifications (...)
VALUES (...);  -- aus payload deserialisiert

-- Schritt 3: Eintrag als erledigt markieren (Empfehlung: UPDATE)
UPDATE notification_outbox
SET relayed_at = NOW()
WHERE id = 42;
```

Schritt 2 und 3 laufen in einer Transaktion — faellt die Notification-Service-DB aus,
wird auch der Status-Update nicht committed. Der Eintrag bleibt offen und wird
beim naechsten Polling-Durchlauf erneut verarbeitet.

**Warum UPDATE statt DELETE?**
Gerade waehrend einer Migration will man den Audit-Trail behalten:
- Bei Problemen sieht man *wann* welcher Eintrag uebertragen wurde
- Doppelt verarbeitete Eintraege sind erkennbar (`relayed_at` bereits gesetzt)

Die Tabellengroesse wird nicht im Relay-Prozess geloest, sondern durch einen
separaten Cleanup-Job:

```sql
-- Separater CronJob (z.B. taeglich)
-- 7 Tage Aufbewahrung fuer Fehleranalyse, danach loeschen
DELETE FROM notification_outbox
WHERE relayed_at < NOW() - INTERVAL '7 days';
```

Die 7 Tage sind ein Richtwert — je nach SLA und Debugging-Beduerfnis anpassen.

Verzoegerung: typisch < 1 Sekunde. Einfach umzusetzen, aber erzeugt staendige
DB-Abfragen auch wenn nichts zu tun ist.

**Variante B — CDC / Change Data Capture (robuster):**

Statt zu pollen, lauscht ein Tool wie **Debezium** auf den PostgreSQL Write-Ahead-Log (WAL).
Jeder neue Eintrag in `notification_outbox` loest sofort ein Event aus — ohne Polling.

```
[Monolith-DB: notification_outbox]
    |
    | WAL (Write-Ahead-Log) -- PostgreSQL schreibt jeden Commit ins Log
    v
[Debezium] (liest WAL, produziert Events)
    |
    v
[Kafka Topic: notification-outbox]
    |
    v
[Notification Service] -- konsumiert und schreibt in eigene DB
```

Verzoegerung: typisch < 100ms. Kein Polling, hohe Zuverlaessigkeit, aber
mehr Infrastruktur (Debezium, Kafka).

**Was passiert bei einem Fehler im Relay?**

Der Outbox-Eintrag bleibt in der Tabelle (`relayed_at` bleibt NULL).
Beim naechsten Durchlauf wird er erneut verarbeitet.
Die Notification-Service-DB ist damit **eventually consistent** —
sie wird garantiert aktualisiert, aber nicht zwingend sofort.

> **Konsequenz fuer den Notification Service:**
> Er darf keine Annahme treffen, dass ein Eintrag sofort da ist.
> Lese-Anfragen kurz nach einem Write koennen noch den alten Stand zeigen.
> Das ist in diesem Fall akzeptabel — eine E-Mail-Bestaetigung darf
> mit wenigen Sekunden Verzoegerung ankommen.

**Warum Outbox statt Dual Write?**
Dual Write ist nicht atomar — bei einem Fehler zwischen den beiden Writes
entsteht Datenverlust oder Inkonsistenz ohne Moeglichkeit zur Wiederholung.
Das Outbox Pattern schreibt atomar in eine DB und uebertraegt dann
mit Retry-Logik in die zweite. Jeder Eintrag wird garantiert genau einmal uebertragen.

---

#### Phase 6 — Reads auf neue DB umstellen

Alle Stellen, die bisher aus `monolith_db.notifications` gelesen haben,
werden auf den Notification Service (bzw. seine DB) umgestellt.

```
Vorher: Monolith fragt SELECT * FROM notifications WHERE user_id = ?
Nachher: GET /notifications?userId=<ref>  -->  Notification Service
```

Dieser Schritt kann schrittweise erfolgen: erst Admin-UI, dann Reporting, dann Kundenbereich.

---

#### Phase 7 — Alte Tabelle abschalten

Erst wenn alle Reads und Writes auf die neue DB umgestellt sind und der
Relay-Prozess keine offenen Eintraege mehr hat:

```sql
-- Outbox leeren (alle uebertragen)
SELECT COUNT(*) FROM notification_outbox WHERE relayed_at IS NULL;
-- Muss 0 sein

-- Alte Tabelle entfernen
DROP TABLE notification_outbox;
DROP TABLE notifications;  -- aus Monolith-DB
```

```
Vorher:                          Nachher:
+----------------+               +----------------+
| Monolith-DB    |               | Monolith-DB    |
|  notifications |               |  (keine noti.) |
|  outbox        |               +----------------+
+----------------+
                                 +----------------------+
                                 | Notification-Svc-DB  |
                                 |  notifications       |
                                 +----------------------+
```

---

### Zusammenfassung: Warum diese Reihenfolge?

```
Phase 1: Neue DB aufsetzen      (Database-per-Service) -- nur Infrastruktur, noch keine Daten
Phase 2: Backfill               -- neue DB vollstaendig befuellen, bevor neuer Service startet
Phase 3: Code entkoppeln        (Strangler Fig)        -- erst jetzt umschalten, DB ist bereit
Phase 4: (Dual Write)           -- nur zur Erklaerung, in der Praxis ueberspringen
Phase 5: Outbox                 -- zuverlaessiger Relay fuer laufende Writes
Phase 6: Reads umstellen        -- neuer Service uebernimmt
Phase 7: Alte Tabelle loeschen  -- Monolith vollstaendig entkoppelt
```

**Die entscheidende Reihenfolge:** DB aufsetzen → Backfill → Code umschalten.
Wer den Code zuerst umschaltet, hat einen aktiven Service mit einer leeren oder
unvollstaendigen DB — das fuehrt zu inkonsistenten Daten, die schwer rueckzusetzen sind.

Jede Phase ist einzeln rueckrollbar. Laeuft etwas schief, laeuft der
Monolith weiter auf der alten DB — kein Datenverlust, kein Ausfall.

> **Faustregel:** Die Datenmigration ist fertig, wenn kein Code mehr
> die alte Tabelle referenziert — weder fuer Reads noch fuer Writes.
> Erst dann ist der Schnitt wirklich vollzogen.

## Micro-Frontends

### Micro-Frontends — Teams am Frontend ohne Kollisionen


### Das Problem: Wo fangen die Konflikte an?

Im Microservices-Backend hat jedes Team klare Grenzen: Team A besitzt den Produkt-Service,
Team B besitzt den Warenkorb-Service. Aber das Frontend? Oft arbeiten alle Teams an denselben
Dateien, im selben Repository, ohne klares Ownership.

Das fuehrt zu:
- Merge-Konflikten und gegenseitigem Blockieren
- Kein Team kann unabhaengig deployen — jede Aenderung braucht Abstimmung
- Unclear, wer fuer welchen Bereich der UI verantwortlich ist

**Die Loesung: das Microservices-Prinzip auf das Frontend uebertragen.**

---

### Kernkonzept: Der vertikale Schnitt

Die entscheidende Frage ist nicht "Wie teilen wir die technischen Schichten auf?"
sondern: **"Welcher Teil der Benutzeroberflaeche gehoert zu welcher Domaene?"**

![Vergleich: Technischer Schnitt vs. Domaenen-Schnitt](/images/micro-frontend-schnittstrategien.svg)

#### Technischer Schnitt — das Antipattern

Teams nach Technologie aufzuteilen klingt logisch, erzeugt aber genau das Problem, das wir loesen wollen:

| Team | Zustaendigkeit |
|---|---|
| Team UI | Alle Seiten in React / Vue / Angular |
| Team Plattform | API-Gateway, BFF fuer alle Domains |
| Team Backend A, B, C | Einzelne Services |

Jedes neue Feature benoetigt **Koordination ueber alle drei Teams hinweg** — unabhaengige Arbeit
ist nicht moeglich.

#### Domaenen-Schnitt — der richtige Ansatz

Teams nach Geschaeftsdomaene aufzuteilen, sodass jedes Team seinen **vollstaendigen vertikalen Slice** besitzt:

| Team | Frontend | BFF | Services | Datenbank |
|---|---|---|---|---|
| Team Katalog | Produktliste, Suche | Produkt-API | Produkt-Svc, Such-Svc | Produkt-DB |
| Team Warenkorb | Warenkorb-UI | Cart-API | Korb-Svc, Preis-Svc | Korb-DB |
| Team Checkout | Bestell-Formular | Order-API | Bestell-Svc, Zahlung-Svc | Bestell-DB |

**Wichtig:** Der Frontend-Schnitt muss nicht 1:1 zu den Backend-Services passen.
Ein Frontend-Bereich (z.B. Warenkorb) darf mehrere Backend-Services ansprechen —
solange das **Ownership klar im gleichen Team** liegt.

---

### Wie kommen die Teile zusammen? Die Shell-App

Wenn jedes Team sein eigenes Frontend-Stueck baut — wie sieht der Nutzer am Ende
eine zusammenhaengende Anwendung?

Die Antwort: eine **Shell-App (Host-App)** koordiniert das Zusammenfuegen.

![Micro-Frontend Laufzeit-Architektur](/images/micro-frontend-architektur.svg)

Die Shell-App stellt bereit:
- **Navigation und Routing** — welche MFE-URL wird geladen?
- **Shared Design System** — gemeinsame Komponenten (Button, Header, Formularfelder)
- **Auth-Kontext** — Login-Status wird an alle MFEs weitergegeben
- **Fehlerhandling** — was passiert, wenn ein MFE nicht laedt?

---

### Integrationsmuster

Es gibt drei gaengige Wege, Micro-Frontends zusammenzufuehren:

#### 1. Build-Time Integration (npm-Packages)

Jedes Team veroeffentlicht sein MFE als npm-Package. Die Shell-App importiert alle Packages
und baut sie gemeinsam.

```
// shell/package.json
{
  "dependencies": {
    "@shop/mfe-catalog":  "^2.1.0",
    "@shop/mfe-cart":     "^1.4.0",
    "@shop/mfe-checkout": "^3.0.0"
  }
}
```

**Vorteil:** Einfach, kein Infrastruktur-Aufwand, Type-Safety moeglich.

**Nachteil:** Aenderung in einem MFE erfordert neuen Build der Shell-App —
kein wirklich unabhaengiges Deployment.

---

#### 2. Laufzeit-Integration (Webpack Module Federation)

Die Shell-App laedt MFEs **zur Laufzeit** aus separaten URLs. Jedes Team kann deployen,
ohne dass die Shell-App neu gebaut werden muss.

```javascript
// webpack.config.js der Shell-App
new ModuleFederationPlugin({
  remotes: {
    catalog:  "catalog@https://catalog.shop.de/remoteEntry.js",
    cart:     "cart@https://cart.shop.de/remoteEntry.js",
    checkout: "checkout@https://checkout.shop.de/remoteEntry.js",
  }
})
```

```javascript
// webpack.config.js des Katalog-MFE (wird von der Shell geladen)
new ModuleFederationPlugin({
  name: "catalog",
  filename: "remoteEntry.js",
  exposes: {
    "./CatalogApp": "./src/App"
  }
})
```

**Vorteil:** Echte Entkopplung. Team Katalog kann jederzeit deployen, ohne Team Shell informieren zu muessen.

**Nachteil:** Mehr Infrastruktur-Aufwand, Versionskonflikte bei geteilten Libraries moeglich.

---

#### 3. iFrame-basierte Integration

Jedes MFE laeuft in einem eigenen iFrame. Kommunikation ueber `window.postMessage`.

**Vorteil:** Maximale Isolation, kein Risiko durch geteilte Bibliotheken.

**Nachteil:** Schlechte UX (Scrolling, Accessibility, Browser-History), Performance-Overhead.
Nur in Ausnahmefaellen empfehlenswert.

---

### Spielregeln fuer das interdisziplinaere Team

Micro-Frontends loesen viele Koordinationsprobleme — aber nur, wenn klare Vereinbarungen bestehen.

#### Das darf jedes Team eigenstaendig entscheiden

- Technologiewahl innerhalb des eigenen MFE (Framework-Version, State-Management, Testing)
- Release-Zeitpunkt des eigenen MFE
- Datenbankschema der eigenen Services
- Interne Architektur des eigenen Frontend-Stuecks

#### Das wird gemeinsam festgelegt (und dann nicht mehr geaendert ohne Abstimmung)

| Thema | Beispiel |
|---|---|
| Design System | Gemeinsame Komponenten-Library (`@shop/ui`) |
| API-Kontrakte | Wie spricht die Shell-App mit den MFEs? (Props, Events, Context) |
| Routing-Konvention | `/catalog/*`, `/cart/*`, `/checkout/*` gehoeren wem? |
| Authentifizierung | Wie wird der Auth-Token weitergereicht? |
| Shared Libraries | React-Version, die alle nutzen (vermeidet doppeltes Bundle) |

#### Contract Testing fuer MFE-Grenzen

Genau wie Backend-Services per Consumer-Driven Contract Testing integriert werden,
koennen MFEs ihre Schnittstellen absichern:

```javascript
// Team Katalog: definiert, welche Props die Shell erwartet
export interface CatalogAppProps {
  authToken: string;
  onAddToCart: (productId: string) => void;
}
```

Aendert Team Katalog diese Schnittstelle, schlaegt der Contract-Test sofort an —
bevor es zu Laufzeitfehlern in der Shell-App kommt.

---

### Wann lohnt sich das?

Micro-Frontends sind **kein Default** — sie bringen echten Overhead mit sich.

| Szenario | Empfehlung |
|---|---|
| 1-2 Teams am Frontend | Monolithisches Frontend, klare Ordnerstruktur reicht aus |
| 3+ Teams, viele gegenseitige Blockaden | Micro-Frontends erwaegen |
| Teams brauchen wirklich unabhaengige Releases | Module Federation sinnvoll |
| Sehr unterschiedliche Tech-Stacks im Team | iFrame oder Web Components |

#### Der haeufigste Fehler

Teams beginnen mit Micro-Frontends, weil es technisch interessant ist — aber
ohne echte Domaenengrenze. Das Ergebnis: die gleichen Koordinationsprobleme wie vorher,
nur mit mehr Infrastruktur.

**Erst Domaene finden, dann schneiden. Nicht andersherum.**

---

### Zusammenfassung

```
Microservice-Prinzip:          Micro-Frontend-Prinzip:
  Service A — DB A               MFE A — Services A — DB A
  Service B — DB B    ──────►    MFE B — Services B — DB B
  Service C — DB C               MFE C — Services C — DB C

Jeder Service: eigenstaendig    Jedes Team: eigenstaendig
deploybar, testbar, skalierbar  deploybar, von UI bis Datenbank
```

Die Schnittlinie liegt **nicht** zwischen UI und Backend,
sondern **vertikal durch alle Schichten**, entlang der Geschaeftsdomaene.

---

### Praxisbeispiele aus Deutschland und DACH

Diese Unternehmen setzen Micro-Frontends produktiv ein und haben darueber
oeffentlich geschrieben — gut geeignet als Diskussionsgrundlage im Training.

| Unternehmen | Ansatz | Besonderheit |
|---|---|---|
| **Zalando** | Server-Side Composition (Project Mosaic) | Eigenes Oekosystem: Tailor (Layout-Service), Skipper (Router). Pionier des Konzepts in Europa. |
| **OTTO** | Web Components (`@otto-ec/elements`) | Framework-agnostische Komponenten-Library als gemeinsames Design System fuer alle MFEs. |
| **SAP** | SAP Fiori Launchpad | Shell laedt Fiori-Apps als eigenstaendige MFEs, basierend auf UI5-Framework. Enterprise-Massstab. |
| **Siemens** | MindSphere IoT-Plattform | Partner-Teams liefern eigene MFEs in die Plattform — klassischer Multi-Vendor-Ansatz. |
| **ING Deutschland** | Banking-Portal | Erfahrungsbericht: Migration von Monolith auf MFEs im laufenden Betrieb. |

#### Was diese Beispiele gemeinsam haben

Alle haben den gleichen Ausgangspunkt: ein wachsendes Frontend-Team,
das sich gegenseitig blockiert hat. Die Loesung war immer der vertikale Schnitt —
nicht nach Technologie, sondern nach Geschaeftsdomaene.

---

### Weiterfuehrende Themen

- [MFE-Kommunikation — Custom Events, Shell, Backend-Zustand](micro-frontends-kommunikation.md)
- [Module Federation — Webpack-Konfiguration und Codebeispiele](micro-frontends-module-federation.md)
- [Was sind Microservices?](what-are.md)
- [Bounded Contexts und Domainschnitt](../microservices/monolith-schneiden.md)
- [IAM als Bounded Context](iam-als-bounded-context.md)
- [SEED vs. DDD / EventStorming](seed-vs-ddd-eventstorming.md)

### Micro-Frontends — Kommunikation zwischen MFEs


### Das Problem

Micro-Frontends laufen als separate JavaScript-Bundles im Browser.
Sie teilen **keine gemeinsamen Variablen, keinen gemeinsamen Store**.

```
MFE: Katalog                     MFE: Warenkorb
─────────────────                ─────────────────
let cartCount = 0;               let items = [];
                                 
// Diese Variable ist fuer       // ...komplett
// Warenkorb unsichtbar!         // unsichtbar
```

Trotzdem soll ein Klick auf "Kaufen" im Katalog den Warenkorb sofort aktualisieren.

![MFE Kommunikationsmuster](/images/micro-frontend-kommunikation.svg)

---

### Muster 1: Custom Events (empfohlen fuer lose Kopplung)

Der Browser stellt mit `CustomEvent` einen eingebauten Nachrichtenkanal bereit.
Kein Framework, keine gemeinsame Library noetig.

#### Sender: Katalog-MFE

```javascript
// CatalogApp.jsx — Button "In den Warenkorb"
function handleBuyClick(product) {
  // 1. Erst den eigenen Service aufrufen
  await fetch('/api/cart/items', {
    method: 'POST',
    body: JSON.stringify({ productId: product.id, qty: 1 })
  });

  // 2. Alle anderen MFEs benachrichtigen
  window.dispatchEvent(
    new CustomEvent('cart:item-added', {
      detail: { productId: product.id, name: product.name, qty: 1 }
    })
  );
}
```

#### Empfaenger: Warenkorb-MFE

```javascript
// CartApp.jsx — reagiert auf das Event
useEffect(() => {
  const handler = (event) => {
    const { productId, qty } = event.detail;
    setCartCount(prev => prev + qty);
    setCartVisible(true); // Warenkorb aufklappen
  };

  window.addEventListener('cart:item-added', handler);
  return () => window.removeEventListener('cart:item-added', handler);
}, []);
```

#### Warum `window`?

`window` ist das einzige Objekt, das alle MFEs im Browser teilen.
Events auf `window` sind global sichtbar — unabhaengig davon,
in welchem Framework oder Bundle das MFE laeuft.

#### Event-Namenskonvention

Prefix mit dem Domaenennamen verhindert Kollisionen:

```
cart:item-added          ✓  klar, welche Domaene
cart:item-removed        ✓
checkout:order-placed    ✓
added                    ✗  zu generisch, Konflikte moeglich
```

---

### Muster 2: Shell-App als Vermittler

Die Shell-App haelt den gemeinsamen Zustand und gibt ihn per Props weiter.

```javascript
// Shell-App: App.jsx
function App() {
  const [cartItems, setCartItems] = useState([]);
  const [cartOpen, setCartOpen]   = useState(false);

  function handleAddToCart(product) {
    setCartItems(prev => [...prev, product]);
    setCartOpen(true); // Warenkorb direkt oeffnen
  }

  return (
    <>
      <CatalogApp onAddToCart={handleAddToCart} />
      <CartApp
        items={cartItems}
        isOpen={cartOpen}
        onClose={() => setCartOpen(false)}
      />
    </>
  );
}
```

**Wann sinnvoll:** Wenn beide MFEs im gleichen Framework (z.B. React) laufen
und der geteilte Zustand ueberschaubar bleibt.

**Problem bei Wachstum:** Die Shell wird zum Flaschenhals, wenn viele MFEs
Zustand teilen muessen ("Prop-Drilling durch die Shell").

---

### Muster 3: Backend als gemeinsamer Zustand

Jedes MFE redet direkt mit dem Backend. Der Warenkorb-Service ist
die einzige Quelle der Wahrheit — keine Frontend-Synchronisation noetig.

#### Mit Server-Sent Events (SSE)

```javascript
// CartApp.jsx — abonniert Aenderungen vom Server
useEffect(() => {
  const eventSource = new EventSource('/api/cart/stream');

  eventSource.addEventListener('cart-updated', (event) => {
    const cart = JSON.parse(event.data);
    setCartItems(cart.items);
    setCartCount(cart.totalItems);
  });

  return () => eventSource.close();
}, []);
```

```javascript
// CatalogApp.jsx — schreibt nur in den Service
async function handleBuyClick(productId) {
  await fetch('/api/cart/items', {
    method: 'POST',
    body: JSON.stringify({ productId, qty: 1 })
  });
  // kein Event noetig — Cart-Service benachrichtigt Warenkorb-MFE
}
```

**Vorteil:** Zustand ueberlebt einen Browser-Reload.
Wenn der Nutzer die Seite neu laedt, zeigt der Warenkorb trotzdem den richtigen Stand.

---

### Welches Muster wann?

| Szenario | Empfehlung |
|---|---|
| Verschiedene Frameworks (React + Vue) | Custom Events — framework-agnostisch |
| Gleiches Framework, einfache App | Shell als Vermittler |
| Zustand muss persistiert werden | Backend-Zustand + SSE/Polling |
| Performance-kritisch (viele Updates) | WebSocket statt SSE |
| Einfachste Loesung zuerst | Custom Events, dann bei Bedarf erweitern |

---

### Was man vermeiden sollte

#### Direkter Import zwischen MFEs

```javascript
// catalog-mfe/src/App.jsx

// NIEMALS:
import CartStore from 'cart-mfe/src/store'; // harte Kopplung!
CartStore.addItem(product);                 // Katalog haengt jetzt von Warenkorb ab
```

Das zerstoert die Unabhaengigkeit — Katalog-MFE kann nicht mehr
ohne Warenkorb-MFE deployt werden.

#### Globale Variablen

```javascript
// NIEMALS:
window.__sharedCart = [];  // Wer ist Owner? Wer rauemt auf?
```

Kein Ownership, kein Lifecycle, schwer zu testen.

#### Die Faustregel

> **MFEs kommunizieren ueber Ereignisse oder Vertraege — nicht ueber
> gemeinsamen Code und nicht ueber direkte Referenzen.**

---

### Zusammenfassung: Das "Kaufen"-Beispiel

```
Nutzer klickt "Kaufen" im Katalog-MFE
        │
        ▼
  Katalog-MFE
  ├─ POST /api/cart/items  (Cart-Service speichert)
  └─ window.dispatchEvent('cart:item-added', { ... })
                │
                ▼ (Browser leitet weiter)
        Warenkorb-MFE
        ├─ empfaengt Event
        ├─ aktualisiert Zaehler
        └─ klappt Warenkorb-Drawer auf
```

Die beiden MFEs kennen sich **nicht** — sie kennen nur den Event-Namen.
Das ist lose Kopplung in der Praxis.

---

### Weiterfuehrendes

- [Micro-Frontends — Grundlagen und Schnittstrategien](micro-frontends.md)

### Micro-Frontends — Module Federation (Webpack/Vite, TypeScript)


### Was ist Module Federation?

Module Federation ist ein Feature von **Webpack 5** (seit 2020), das es erlaubt,
JavaScript-Module aus einem anderen laufenden Deployment zu laden —
zur Laufzeit, nicht beim Build.

Das ist der technische Kern hinter Laufzeit-Micro-Frontends:
Jedes Team deployt sein MFE eigenstaendig, die Shell-App laedt es dynamisch nach.

![Module Federation: Build-Zeit vs. Laufzeit](/images/micro-frontend-module-federation.svg)

---

### Was ist die Shell-App?

Die Shell-App ist der **aeussere Rahmen** der gesamten Anwendung.
Sie selbst enthaelt kaum eigene Fachlogik — sie stellt den Rahmen bereit,
in den die MFEs zur Laufzeit eingesetzt werden.

```
┌──────────────────────────────────────────────────────┐
│  Shell-App  (der Rahmen)                             │
│  ┌────────────────────────────────────────────────┐  │
│  │  Navigation · Auth-Kontext · Design System     │  │
│  └────────────────────────────────────────────────┘  │
│                                                      │
│  ┌──────────────┐  ┌──────────────┐  ┌───────────┐  │
│  │ MFE: Katalog │  │MFE: Warenkorb│  │MFE: Check-│  │
│  │              │  │              │  │out        │  │
│  └──────────────┘  └──────────────┘  └───────────┘  │
└──────────────────────────────────────────────────────┘
```

**Laeuft die Shell-App auf dem Server?** Nein — sie laeuft vollstaendig im Browser.
Der Server liefert nur die statischen Dateien aus (`index.html`, `main.js`).
Danach ist der Server nicht mehr beteiligt.

```
Server                       Browser des Nutzers
─────────────────            ─────────────────────────────
Gibt Dateien aus:            Fuehrt aus:
  index.html      ──────►     laedt main.js
  main.js         ──────►     Shell-App startet
                              Shell laedt MFEs nach
                              Nutzer sieht die Seite
```

**Warum heisst es Shell-App?** Nichts mit Linux — der Name kommt vom Bild
einer Huelle (Nussschale), die den Inhalt zusammenhaelt.
In der Webpack-Dokumentation heisst dieselbe Rolle **Host**.

| Name | Kontext |
|---|---|
| Shell-App | Branchenbezeichnung fuer MFE-Architektur |
| Host | Webpack-Terminologie |
| Container-App | aeltere Bezeichnung, gleiche Idee |

---

### Vom Code zum Kunden — Schritt fuer Schritt

**Schritt 1: Entwickler schreibt Code (TypeScript / React)**

```
catalog-mfe/src/
├── App.tsx            React-Komponente mit JSX
├── ProductList.tsx
└── api/products.ts    fetch-Aufrufe zum Backend
```

**Schritt 2: Webpack laeuft (lokal oder in CI/CD)**

Webpack liest alle Dateien, uebersetzt TypeScript → JavaScript,
loest alle Imports auf und schreibt das Ergebnis nach `dist/`:

```
catalog-mfe/dist/
├── remoteEntry.js     Einstiegspunkt fuer Module Federation
├── 42.chunk.js        der eigentliche App-Code
└── 891.chunk.js       vendor-Code (z.B. React)
```

**Schritt 3: CI/CD deployt die dist/-Dateien auf einen Webserver / CDN**

```
dist/*.js  ──►  https://catalog.shop.de/
```

Ab hier ist Webpack fertig. Auf dem Server liegen nur fertige `.js`-Dateien.

**Schritt 4: Kunde oeffnet https://shop.meinefirma.de**

```
Browser                         Server: shop.meinefirma.de
  │── GET / ──────────────────────────────────────────► │
  │◄── index.html ────────────────────────────────────  │
  │── GET /main.js ────────────────────────────────────►│
  │◄── main.js (Shell-App) ───────────────────────────  │
```

**Schritt 5: Shell-App startet im Browser und laedt die MFEs nach**

```
Browser                         catalog.shop.de     cart.shop.de
  │── GET /remoteEntry.js ──────────────────────►  │
  │◄── remoteEntry.js ─────────────────────────    │
  │── GET /42.chunk.js (CatalogApp) ─────────────► │
  │◄── 42.chunk.js ────────────────────────────    │
  │                                                     │── GET /remoteEntry.js ──────────────────────────►  │
  │◄── remoteEntry.js ─────────────────────────────── │
  │── GET /77.chunk.js (CartApp) ──────────────────────►│
  │◄── 77.chunk.js ────────────────────────────────── │
```

**Schritt 6: Fertig — der Nutzer sieht die komplette Anwendung**

Alle drei MFEs laufen im gleichen Browser-Tab, obwohl sie von drei verschiedenen
Servern geladen wurden und von drei verschiedenen Teams deployt wurden.
React wurde dabei **nur einmal geladen** (`singleton: true`).

---

### Sprache und Tooling

| Tool | Module Federation verfuegbar als | Hinweis |
|---|---|---|
| **Webpack 5** | direkt eingebaut — kein Plugin noetig | Standard fuer React/Angular-Projekte |
| **Vite** | Plugin `@originjs/vite-plugin-federation` | Neuere Projekte, schnellerer Dev-Server |
| **TypeScript** | vollstaendig unterstuetzt | Typen fuer Remote-Module per `declarations.d.ts` |
| **Framework** | framework-agnostisch | funktioniert mit React, Vue, Angular, Svelte, Vanilla JS |

---

### Die drei Rollen

```
HOST (Shell-App)           REMOTE (MFE)              SHARED
─────────────────          ──────────────             ────────────
laedt Module aus           stellt Module              gemeinsame Libraries
anderen Deployments        bereit (exposes)           (z.B. React)
zur Laufzeit               erzeugt remoteEntry.js     nur einmal geladen
```

---

### Schritt-fuer-Schritt Beispiel

#### Voraussetzungen

```
Node.js >= 16
Webpack >= 5
```

#### Verzeichnisstruktur

```
shop/
├── shell-app/          # Host
│   ├── src/
│   │   ├── App.tsx
│   │   └── bootstrap.tsx
│   └── webpack.config.js
│
├── catalog-mfe/        # Remote 1
│   ├── src/
│   │   ├── App.tsx
│   │   └── bootstrap.tsx
│   └── webpack.config.js
│
└── cart-mfe/           # Remote 2
    ├── src/
    │   ├── App.tsx
    │   └── bootstrap.tsx
    └── webpack.config.js
```

---

#### Remote konfigurieren: Katalog-MFE

Das Katalog-MFE stellt seine App als ladbares Modul bereit.

```javascript
// catalog-mfe/webpack.config.js
const { ModuleFederationPlugin } = require('webpack').container;
const deps = require('./package.json').dependencies;

module.exports = {
  mode: 'development',
  devServer: { port: 3001 },

  plugins: [
    new ModuleFederationPlugin({
      name: 'catalog',            // eindeutiger Name des Remote
      filename: 'remoteEntry.js', // Einstiegspunkt fuer den Host

      exposes: {
        './CatalogApp':  './src/App',
        './ProductCard': './src/components/ProductCard',
      },

      shared: {
        react:       { singleton: true, requiredVersion: deps.react },
        'react-dom': { singleton: true, requiredVersion: deps['react-dom'] },
      },
    }),
  ],
};
```

```tsx
// catalog-mfe/src/App.tsx
export default function CatalogApp() {
  return (
    <div>
      <h2>Produktkatalog</h2>
      <ProductList />
    </div>
  );
}
```

```tsx
// catalog-mfe/src/index.ts
import('./bootstrap'); // dynamischer Import — Pflicht bei Module Federation
```

```tsx
// catalog-mfe/src/bootstrap.tsx
import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';

ReactDOM.render(<App />, document.getElementById('root'));
```

**Warum `bootstrap.tsx`?**
Ohne den dynamischen Import wuerde Webpack React "eager" laden — bevor
der Host die shared-Konfiguration aushandeln kann. Das fuehrt zu zwei
React-Instanzen im Browser. Der Umweg ueber `bootstrap` gibt Webpack die noetige Zeit.

---

#### Host konfigurieren: Shell-App

```javascript
// shell-app/webpack.config.js
const { ModuleFederationPlugin } = require('webpack').container;
const deps = require('./package.json').dependencies;

module.exports = {
  mode: 'development',
  devServer: { port: 3000 },

  plugins: [
    new ModuleFederationPlugin({
      name: 'shell',

      remotes: {
        // Format: "<name>@<url>/remoteEntry.js"
        catalog: 'catalog@http://localhost:3001/remoteEntry.js',
        cart:    'cart@http://localhost:3002/remoteEntry.js',
      },

      shared: {
        react:       { singleton: true, requiredVersion: deps.react },
        'react-dom': { singleton: true, requiredVersion: deps['react-dom'] },
      },
    }),
  ],
};
```

```tsx
// shell-app/src/App.tsx
import React, { Suspense } from 'react';

const CatalogApp = React.lazy(() => import('catalog/CatalogApp'));
const CartApp    = React.lazy(() => import('cart/CartApp'));

export default function App() {
  return (
    <div>
      <nav>Shop Navigation</nav>

      <Suspense fallback={<div>Katalog laedt...</div>}>
        <CatalogApp />
      </Suspense>

      <Suspense fallback={<div>Warenkorb laedt...</div>}>
        <CartApp />
      </Suspense>
    </div>
  );
}
```

---

#### TypeScript: Typen fuer Remote-Module deklarieren

TypeScript kennt die Remote-Module nicht — sie kommen erst zur Laufzeit.
Ohne Deklaration gibt es einen Compiler-Fehler.

```typescript
// shell-app/src/declarations.d.ts
declare module 'catalog/CatalogApp' {
  const CatalogApp: React.ComponentType;
  export default CatalogApp;
}

declare module 'cart/CartApp' {
  const CartApp: React.ComponentType;
  export default CartApp;
}
```

---

#### Starten (lokale Entwicklung)

```
## Terminal 1
cd catalog-mfe && npm start   # http://localhost:3001

## Terminal 2
cd cart-mfe && npm start      # http://localhost:3002

## Terminal 3
cd shell-app && npm start     # http://localhost:3000
```

---

### Dynamic Remotes — URL aus Config laden

In Produktion sind die URLs Umgebungsvariablen, keine hartkodierten Strings.

```javascript
// shell-app/webpack.config.js
new ModuleFederationPlugin({
  name: 'shell',
  remotes: {
    catalog: `catalog@${process.env.CATALOG_URL}/remoteEntry.js`,
    cart:    `cart@${process.env.CART_URL}/remoteEntry.js`,
  },
})
```

---

### Vite-Alternative: `@originjs/vite-plugin-federation`

```javascript
// catalog-mfe/vite.config.ts
import federation from '@originjs/vite-plugin-federation';

export default defineConfig({
  plugins: [
    federation({
      name: 'catalog',
      filename: 'remoteEntry.js',
      exposes: { './CatalogApp': './src/App.tsx' },
      shared: ['react', 'react-dom'],
    }),
  ],
  build: { target: 'esnext' }, // Pflicht fuer Top-Level Await
});
```

```javascript
// shell-app/vite.config.ts
import federation from '@originjs/vite-plugin-federation';

export default defineConfig({
  plugins: [
    federation({
      name: 'shell',
      remotes: { catalog: 'http://localhost:3001/assets/remoteEntry.js' },
      shared: ['react', 'react-dom'],
    }),
  ],
  build: { target: 'esnext' },
});
```

---

### Haeufige Fallstricke

| Problem | Ursache | Loesung |
|---|---|---|
| `Shared module is not available` | React zweimal geladen | `singleton: true` in beiden Configs setzen |
| TypeScript-Fehler `Cannot find module` | Fehlende Typdeklaration | `declarations.d.ts` anlegen |
| MFE laedt nicht in Produktion | URL stimmt nicht | `remoteEntry.js`-URL pruefen, CORS-Header setzen |
| Weisser Bildschirm ohne Fehlermeldung | `eager` Consumption | `index.ts` nur `import('./bootstrap')` — nichts anderes |
| Verschiedene React-Versionen | Inkompatible `shared`-Config | `requiredVersion` in allen MFEs gleich setzen |

---

### Hintergrund: Was ist Webpack?

Browser verstehen kein TypeScript, kein JSX, keine relativen Imports.
Sie verstehen nur plain JavaScript und HTML.

**Webpack** ist ein Build-Tool: Es laeuft einmalig beim Entwickler oder in der CI/CD-Pipeline,
liest den Quellcode und erzeugt daraus fertige `.js`-Dateien, die der Browser direkt laden kann.

```
src/
├── App.tsx        ──┐
├── components/    ──┤  webpack  ──►  dist/main.js  (Browser versteht das)
└── styles.css     ──┘
```

Webpack laeuft ausschliesslich zur Build-Zeit — nie im Browser des Nutzers.

---

### Weiterfuehrendes

- [Micro-Frontends — Grundlagen und Schnittstrategien](micro-frontends.md)
- [MFE-Kommunikation — Custom Events, Shell, Backend-Zustand](micro-frontends-kommunikation.md)

## Übungen: Monolith schneiden

### Uebung: Monolith schneiden — DDD, Bounded Contexts und Strangler Fig


### Modell und Warum

Diese Uebung verwendet zwei aufeinander aufbauende Techniken:

| Technik | Zweck | Warum |
|---|---|---|
| **DDD Bounded Contexts** | Wo schneiden? | Fachliche Grenzen, nicht technische — der Code folgt der Sprache des Business |
| **Strangler Fig Pattern** | Wie migrieren? | Schrittweise Abloesung, kein riskanter Big-Bang-Rewrite |
| **Event Storming (vereinfacht)** | Was gehoert zusammen? | Sichtbar machen, welche Teile des Systems tatsaechlich zusammenhaengen |

> **Strangler Fig** = eine tropische Pflanze, die langsam um einen Baum waechst und ihn schliesslich ersetzt.
> Der alte Monolith laeuft weiter, waehrend neue Services ihn stueck fuer stueck ablösen.

---

### Ausgangslage: Der Monolith "ShopMax"

ShopMax ist ein 8 Jahre alter Online-Shop, entwickelt in Java/Spring Boot als einzelne
deploybare Einheit.

**Funktionsumfang (was der Shop kann):**

- Kunden koennen sich registrieren, einloggen, Profil pflegen
- Produkte werden gepflegt (Beschreibung, Preis, Bilder, Kategorien)
- Lagerbestaende werden gefuehrt
- Kunden legen Artikel in den Warenkorb
- Bestellungen werden aufgegeben und koennen storniert werden
- Zahlungen via Kreditkarte, PayPal, Rechnung
- Versand inkl. Tracking und Retouren
- E-Mail- und SMS-Benachrichtigungen (Bestellbestaetigung, Versand, Rechnung)
- Rechnungen werden erstellt und archiviert

Das Team klagt ueber:

- **Lange Deployments** (40 Min. Test + Build fuer jede Aenderung)
- **Angst vor Releases** (Bugfix in Zahlung bricht Produktsuche)
- **Skalierungsprobleme** (Black Friday: komplette App hochskalieren, obwohl nur Checkout bremst)
- **Team-Konflikte** (3 Teams arbeiten auf derselben Codebase, stoen sich gegenseitig)

#### Aktuelle Architektur (vereinfacht)

```
+------------------------------------------------------------+
|                    ShopMax Monolith                        |
|                                                            |
|  UserService    ProductService    OrderService             |
|  CartService    PaymentService    ShippingService          |
|  InventoryService    NotificationService                   |
|                                                            |
|  +------------------+                                      |
|  |   PostgreSQL DB  |  (eine DB, alle Tabellen drin)       |
|  +------------------+                                      |
+------------------------------------------------------------+
         |
    Load Balancer
         |
      Frontend
```

**Alles in einem Prozess. Ein Fehler kann alles "breaken".**

---

### Schritt 1: Domain Events identifizieren (Event Storming)

> **Warum dieser Schritt?**
> Bevor wir schneiden, muessen wir verstehen, was das System *tut*.
> Event Storming zeigt uns die wichtigen Geschaeftsereignisse — und damit die natuerlichen Grenzen.

#### Aufgabe (15 Min., Gruppen zu 3-4 Personen)

Schreibt auf Post-its (oder Zettel) alle **Ereignisse** (Domain Events), die in ShopMax
passieren. Format: **Vergangenheitsform**, orange Post-it.

Beispiele als Einstieg:

```
KundeRegistriert       ProduktGesucht         ZahlungFehlgeschlagen
```

**Optional (falls ihr das Gefühl habt, Euch fehlen Events): Ordnet die Events auf einer Zeitlinie an:**

Warum? Events die zeitlich nahe zusammen passieren, gehoeren oft zum selben Bounded Context.
Luecken in der Zeitlinie zeigen, wo noch Events fehlen.

```
[frueh]                                          [spaet]

KundeRegistriert -> ... -> ProduktGesucht -> ... -> ZahlungFehlgeschlagen -> ...
```

#### Orientierungshilfe: Wieviele Events sind richtig?

Als Daumenregel gilt: **3-5 Events pro Bounded Context**.
Wieviele Contexts ShopMax hat, erarbeitet ihr in Schritt 2.

| Anzahl Events | Bewertung | Typisches Problem |
|---|---|---|
| **< 10** | Zu wenig | Zu abstrakt — Contexts werden nicht sichtbar, Grenzen bleiben unklar |
| **10-17** | Eher zu wenig | Einige Prozesse fehlen, z.B. Fehlerszenarien (ZahlungFehlgeschlagen) |
| **18-30** | Gut | Deckt Hauptprozesse und wichtige Ausnahmen ab |
| **31-40** | Grenzwertig | Zu technisch, z.B. "EmailAdresseGeaendert" statt "KundeAktualisiert" |
| **> 40** | Zu viel | CRUD-Niveau statt Business-Events — Events sind Implementierungsdetails, keine Geschaeftsereignisse |

**Qualitaetscheck fuer eure Events:**
- Wuerden Business-Analysten (nicht Entwickler) den Begriff verstehen? ✓
- Ist das Event in der Vergangenheitsform formuliert? ✓
- Loest das Event eine Reaktion in mindestens einem anderen Bereich aus? ✓
- Wenn Nein zu allen drei: wahrscheinlich kein Domain Event, sondern ein technisches Detail ✗

#### Beobachtung

Markiert Events, die **thematisch zusammengehoeren** mit einer Farbe.
Ihr werdet feststellen: Einige Events gehoeren immer zusammen —
das sind Hinweise auf **Bounded Contexts**.

---

### Schritt 2: Bounded Contexts identifizieren

> **Warum dieser Schritt?**
> Ein Bounded Context ist ein fachlicher Bereich, der eine klare Sprache und
> klare Verantwortung hat. Innerhalb eines Contexts bedeutet "Bestellung" dasselbe.
> Ausserhalb koennte "Bestellung" etwas voellig anderes bedeuten.
>
> **Faustregeln:**
> - Ein Context = ein Team
> - Ein Context = eine Deployeinheit (spaeter)
> - Die Grenzen folgen der **Ubiquitous Language** (gemeinsamer Fachbegriffe)

#### Aufgabe

Analysiert eure Events aus Schritt 1 und gruppiert sie in Bounded Contexts:

- Wie viele Contexts findet ihr?
- Gebt jedem Context einen praegnanten Namen aus der Fachsprache des Business
- Welche Events gehoeren dazu?

Nutzt das folgende Raster (ihr braucht moeglicherweise mehr oder weniger Felder):

```
+-------------------+    +-------------------+    +-------------------+
|    Zahlung        |    |   Bestellprozess  |    |                   |
|                   |    |                   |    |                   |
|                   |    |                   |    |                   |
|                   |    |                   |    |                   |
+-------------------+    +-------------------+    +-------------------+

+-------------------+    +-------------------+    +-------------------+
|                   |    |                   |    |                   |
|                   |    |                   |    |                   |
|                   |    |                   |    |                   |
|                   |    |                   |    |                   |
+-------------------+    +-------------------+    +-------------------+
```

#### Diskussionsfragen

1. Warum ist "Lagerbestand" ein eigener Context und kein Teil von "Produktkatalog"?
   *(Hinweis: Wer aendert Lagerbestand? Wer aendert Produktdaten?)*

2. Koennte "Warenkorb" ein eigener Context sein? Was spricht dafuer, was dagegen?

3. Woran erkennt man, dass man einen Context **zu gross** oder **zu klein** geschnitten hat?

### Auswertung: EventStorming — ShopMax


Auswertung der Teilnehmer-Ergebnisse aus Schritt 1 der Uebung
"Monolith schneiden mit DDD und Strangler Fig Pattern".

---

### Gefundene Events (Gruppe)

```
KundeRegistriert        EmailVerifiziert        ProduktGesucht
ArtikelInWarenkorbHinzugefuegt
BestellungDurchgefuehrt BestellungStorniert     KundenBestaetigung(Verschickt)
ZahlungFehlgeschlagen   ZahlungErstattet        RechnungErstellt
BestandAbgefragt        BestandGeaendert        NachbestellungAusgeloest
VersandLabelErstellt    PaketVersendet          VersandBestaetigung(Erhalten)
RetoureRegistriert      RetoureLabelErstellt    RetoureErhalten
RabattCode
```

**Anzahl: ~20 Events — das liegt im guten Bereich (Ziel: 18-30).**

---

### Positiv aufgefallen

- **EmailVerifiziert** — wird oft vergessen, hier gefunden ✓
- **ZahlungFehlgeschlagen** — Fehlerfall explizit beruecksichtigt ✓
- **NachbestellungAusgeloest** — zeigt Denken ueber Lagerbestand hinaus ✓
- **RetoureRegistriert / RetoureLabelErstellt / RetoureErhalten** — dreiteiliger Retoure-Prozess vollstaendig ✓
- **VersandLabelErstellt** — guter Zwischenschritt vor dem Versand ✓

---

### Anmerkungen und Korrekturen

#### RabattCode — kein Domain Event

`RabattCode` ist ein **Objekt**, kein Ereignis.
Domain Events stehen in der Vergangenheitsform und beschreiben etwas, das passiert ist.

```
Falsch:  RabattCode
Richtig: RabattcodeEingeloest
         RabattcodeErstellt
         RabattcodeAbgelaufen
```

#### BestandAbgefragt — eher technisches Event

Eine Abfrage (Query) ist kein Geschaeftsereignis — sie veraendert keinen Zustand
und loest keine Reaktion in anderen Contexts aus.

```
Raus:    BestandAbgefragt
Behalten: BestandGeaendert, NachbestellungAusgeloest
```

#### ProduktGesucht — Grenzfall

Streng genommen ist eine Suche kein Domain Event (kein Zustandswechsel, keine Reaktion).
Im Kontext von Analytics oder Recommendation Engines koennte es eines sein.
Fuer ShopMax: eher raus.

#### PaketVersendet — richtig, aber LieferungZugestellt fehlt

`PaketVersendet` ist korrekt und war gut gefunden.
Es fehlt jedoch `LieferungZugestellt` als **zusaetzliches** Event.
Beide beschreiben verschiedene Zeitpunkte mit verschiedenen fachlichen Konsequenzen:

```
[PaketVersendet]                      [LieferungZugestellt]
 Paket verlaesst das Lager             Zusteller scannt beim Kunden
 → VersandEmail wird ausgeloest        → Rueckgabefrist beginnt
 → Tracking-Nummer wird aktiv          → Rechnungsziel beginnt (Zahlung auf Rechnung)
                                       → Bestellprozess formal abgeschlossen
```

Typische Ursache: Man denkt aus Shop-Perspektive ("wir haben versendet")
und vergisst die Kundenperspektive ("Paket ist angekommen").

---

### Vergessene Events

| Fehlendes Event | Warum wichtig |
|---|---|
| KundeAngemeldet | Login ist ein Geschaeftsereignis — relevant fuer Security, Session, Fraud-Detection |
| ProfilAktualisiert | "Profil pflegen" steht im Funktionsumfang — hat eigene Fachlogik (z.B. Adresse fuer Versand) |
| PasswortGeaendert | Eigener Sicherheits-Flow (Bestaetigung, Invalidierung von Sessions) |
| ProduktAngelegt | Wer legt Produkte an? Eigenes Team, eigene Fachlogik |
| PreisGeaendert | Preisaenderungen haben Downstream-Effekte (Warenkorb, laufende Bestellungen) |
| WarenkorbGeleert / WarenkorbAbgebrochen | Session laeuft ab — relevant fuer Analytics und Remarketing |
| BestellungBestaetigt | Zwischenzustand zwischen AufgegebenAndVersendet — loest oft Lagerreservierung aus |
| LagerbestandKritisch | Schwellenwert unterschritten — loest Einkaufsprozess aus, bevor Bestand auf 0 faellt |
| ArtikelAusverkauft | Bestand = 0 ist ein eigenes Event mit eigenen Reaktionen (Produktseite, Bestellsperre) |
| BestellbestaetigunsEmailVersendet | Benachrichtigungen sind eigene Events — sie koennen fehlschlagen und muessen nachverfolgt werden |
| VersandEmailVersendet | Gleicher Grund — der Versand der Email ist ein eigenes verfolgbares Ereignis |

**ZahlungErfolgt — groesste inhaltliche Luecke**

`ZahlungErfolgt` fehlt vollstaendig.
Die Gruppe hat `ZahlungFehlgeschlagen` und `ZahlungErstattet` gefunden, aber nicht den Normalfall.

`ZahlungErfolgt` loest mehr Downstream-Reaktionen aus als jedes andere Event:

```
ZahlungErfolgt
    → LagerbestandAktualisiert         (Reservierung wird fest)
    → LieferungVorbereitet             (Versand wird angestossen)
    → RechnungErstellt
    → BestellbestaetigunsEmailVersendet
```

Typische Ursache: Man denkt zuerst an Fehler ("was kann schiefgehen?")
und vergisst den Erfolgsfall explizit als Event zu benennen.

---

### Zusammenfassung fuer die Gruppe

| Kategorie | Bewertung |
|---|---|
| Anzahl Events | Gut (ca. 20) |
| Fehlerfaelle | Gut — ZahlungFehlgeschlagen explizit genannt |
| Retoure-Prozess | Sehr gut — alle drei Schritte gefunden |
| Format | Ein Fehler: RabattCode ist kein Event |
| Technische Events | BestandAbgefragt und ProduktGesucht hinterfragen |
| Groesste Luecke | Kundenverwaltung (Login, Profil) und ZahlungErfolgt fehlen |

> **Wichtigste Erkenntnis:**
> Die gefundenen Events decken vor allem den Bestellprozess gut ab.
> Kundenverwaltung und Produktpflege sind unterrepraesentiert —
> das wird sich in der naechsten Aufgabe zeigen.

### Auswertung: Bounded Contexts — ShopMax


Auswertung der Teilnehmer-Ergebnisse aus Schritt 2 der Uebung
"Monolith schneiden mit DDD und Strangler Fig Pattern".

---

### Gefundene Bounded Contexts (Gruppe)

```
Kundenverwaltung      NutzerVerwaltung      Warenkorb
Bestellungsprozess    Zahlungsprozess       Lager
Versand               Retoure (leer)
```

**Anzahl: 8 Contexts — etwas viel, siehe Diskussion unten.**

#### Events pro Context

| Context | Events |
|---|---|
| Kundenverwaltung | KundeRegistriert |
| NutzerVerwaltung | EmailVerifiziert, NutzerAngemeldet, PasswortGeaendert |
| Warenkorb | ProduktGesucht, ArtikelInWarenkorbHinzugefuegt, ArtikelInWarenkorbEntfernt, ArtikelAnzahlInWarenkorbGeaendert |
| Bestellungsprozess | BestellungAusgefuehrt, BestellungStorniert, RechnungErstellt, BestellbestaetigunsVerschickt |
| Zahlungsprozess | ZahlungAuthorisiert, ZahlungFehlgeschlagen, ZahlungErstattet |
| Lager | BestandAbgefragt, BestandGeaendert, NachbestellungAusgefuehrt |
| Versand | PaketVersendet, VersandlabelErstellt, VersandbestaetigunsVerschickt, RetoureErhalten |
| Retoure | (leer) |

---

### Positiv aufgefallen

- **NutzerAngemeldet und PasswortGeaendert** — in Schritt 1 noch gefehlt, jetzt gefunden ✓
- **ArtikelAnzahlInWarenkorbGeaendert** — feiner Unterschied zu Hinzufuegen/Entfernen, gut erkannt ✓
- **ZahlungAuthorisiert** statt ZahlungErfolgt — praeziser, trifft den richtigen Moment (Genehmigung durch Bank) ✓
- **Retoure als eigener Context** — der Gedanke ist richtig, auch wenn der Context noch leer ist ✓

---

### Diskussionspunkte

#### Kundenverwaltung und NutzerVerwaltung — eine gute Trennung, unglueckliche Benennung

Die Trennung ist fachlich begruendet:
- **NutzerVerwaltung** = Identity Management (Login, Passwort, Authentifizierung) — technisches Team, eigene Datenbank, eigene Sicherheitsanforderungen
- **Kundenverwaltung** = Kundenstammdaten (Adresse, Praeferenzen, Bestellhistorie) — fachliches Team, Shoplogik

Das ist ein bekanntes DDD-Muster: ein Nutzer (technische Identitaet) ist nicht dasselbe
wie ein Kunde (Geschaeftsentitaet). Ein Nutzer koennte mehrere Kundenprofile haben,
ein Kunde koennte angelegt werden bevor er einen Login hat.

Die Bezeichnung "NutzerVerwaltung" war ungluecklich — besser waere **IdentityManagement**
oder **Authentifizierung** gewesen, dann waere die Trennung sofort klar gewesen.

#### ProduktGesucht im Warenkorb — falscher Context

Eine Produktsuche gehoert nicht zum Warenkorb.
Der Warenkorb kuemmert sich um Artikel, die bereits ausgewaehlt wurden.
Die Suche findet vorher statt — im Produktkatalog (falls ueberhaupt als Event).

```
Warenkorb:       ArtikelInWarenkorbHinzugefuegt  ✓
                 ArtikelInWarenkorbEntfernt       ✓
                 ArtikelAnzahlGeaendert           ✓

Nicht hier:      ProduktGesucht                  ✗  (eher Produktkatalog oder raus)
```

#### RetoureErhalten im Versand — gehoert in Retoure

Der Retoure-Context wurde angelegt, aber leer gelassen.
`RetoureErhalten` wurde in Versand einsortiert — das ist inkonsistent.

Entweder gehoert `RetoureErhalten` in den Retoure-Context,
oder Retoure ist kein eigener Context und die Events werden in Versand zusammengefasst.
Beides ist vertretbar — aber nicht gemischt.

```
Option A: Retoure als eigener Context
+-------------------+
|     Retoure       |
| RetoureErhalten   |
| RetoureLabelErstellt (aus Schritt 1) |
| RetoureRegistriert  (aus Schritt 1) |
+-------------------+

Option B: Retoure als Teil von Versand
+-------------------+
|      Versand      |
| PaketVersendet    |
| VersandlabelErstellt |
| RetoureRegistriert|
| RetoureLabelErstellt |
| RetoureErhalten   |
+-------------------+
```

#### BestandAbgefragt im Lager — kein Domain Event

Wie bereits in Schritt 1 angemerkt: eine Abfrage ist kein Geschaeftsereignis.
Sie veraendert keinen Zustand und loest keine Reaktion aus.

```
Raus:     BestandAbgefragt
Behalten: BestandGeaendert, NachbestellungAusgefuehrt
```

#### ZahlungAuthorisiert vs. ZahlungErfolgt

`ZahlungAuthorisiert` ist praezise — es ist der Moment, wo die Bank die Zahlung genehmigt.
Aber der Prozess hat zwei Schritte:

```
1. ZahlungAuthorisiert  — Bank gibt gruenes Licht (Reservierung)
2. ZahlungErfolgt       — Geld wird tatsaechlich eingezogen (Capture)
```

Fuer einen einfachen Online-Shop laufen diese oft zusammen.
Bei grossen Bestellungen oder Vorkasse koennen sie auseinanderfallen.
Fuer ShopMax ist `ZahlungAuthorisiert` als einziges Event akzeptabel —
aber der Unterschied sollte bewusst sein.

---

### Fehlender Context: Produktkatalog

Der Produktkatalog fehlt vollstaendig als Context.
Im Funktionsumfang steht: "Produkte werden gepflegt (Beschreibung, Preis, Bilder, Kategorien)".
Wer pflegt die Produkte? Ein anderes Team als das, das Bestellungen bearbeitet.

```
Fehlender Context:
+-------------------+
|   Produktkatalog  |
|                   |
| ProduktAngelegt   |
| PreisGeaendert    |
| ProduktDeaktiviert|
+-------------------+
```

---

### Zusammenfassung fuer die Gruppe

| Kategorie | Bewertung |
|---|---|
| Anzahl Contexts | 8 — etwas viel, Kundenverwaltung/NutzerVerwaltung zusammenfuehren |
| Groesste Staerke | Warenkorb sauber abgegrenzt, Zahlungsprozess praezise |
| Groesste Luecke | Produktkatalog fehlt als eigener Context |
| Konsistenzproblem | Retoure-Context leer, RetoureErhalten in Versand |
| Zu entfernen | BestandAbgefragt (Query, kein Event), ProduktGesucht aus Warenkorb |

> **Faustregel fuer zu viele Contexts:**
> Wenn ein Context nur ein oder zwei Events hat und kein eigenes Team dahintersteht,
> ist er wahrscheinlich Teil eines groesseren Contexts.

### Weiterfuehrende Schritte: Monolith schneiden (Schritt 3–7)


> Dieses Material baut auf Schritt 1 und 2 aus `uebung-monolith-schneiden.md` auf.
> Einsatz je nach verfuegbarer Zeit und Gruppenfortschritt.

---

### Schritt 3: Context Map zeichnen

> **Warum dieser Schritt?**
> Contexts interagieren miteinander. Die Context Map zeigt, wer von wem abhaengt
> und welche Integration-Patterns verwendet werden.
> Sie macht unsichtbare Kopplungen sichtbar — und damit sichtbar,
> wo wir anfangen sollten zu schneiden.

#### Aufgabe

Zeichnet die Abhaengigkeiten zwischen euren Contexts aus Schritt 2.

Fuer jede Verbindung: Wer braucht Daten von wem? Wer publiziert Events, wer abonniert sie?

> **Was bedeutet "publiziert"?**
> Ein Service sendet ein Domain Event auf einen Event Bus (z.B. Kafka).
> Er interessiert sich nicht dafuer, wer zuhoert — er publiziert einfach.
> Andere Services *subscriben* selbst auf Events, die sie brauchen.
> Vorteil: Der Sender hat keine Abhaengigkeit zum Empfaenger.

#### Integration-Patterns markieren

Tragt fuer jede Verbindung ein, wie die Kommunikation stattfindet:

| Von | Nach | Aktuell (Monolith) | Ziel (Microservices) |
|---|---|---|---|
| | | | |
| | | | |
| | | | |
| | | | |

> **Schluesserkenntnis:** Ueberall wo heute ein *direkter Methodenaufruf* steht,
> haben wir eine **enge Kopplung**. Diese muss vor dem Schneiden entkoppelt werden.

---

### Schritt 4: Strangler Fig — Migrationsreihenfolge planen

> **Warum dieser Schritt?**
> Wir koennen nicht alle Contexts gleichzeitig herausloesen — das waere ein Big-Bang-Rewrite
> mit hohem Risiko. Der Strangler Fig Plan legt fest, in welcher **Reihenfolge** wir
> vorgehen und wie der Monolith dabei weiterlaeuft.
>
> **Kriterien fuer den ersten Schnitt:**
> - Geringer Abhaengigkeiten zu anderen Contexts (wenige Verbindungen in der Context Map)
> - Hoher Business-Wert (z.B. Skalierungsproblem loesen)
> - Klare Sprache / klare Teamverantwortung
> - Keine Datenbankfremdzugriffe von anderen Contexts

#### Bewertungsmatrix

Tragt eure Contexts aus Schritt 2 ein und bewertet jeden von 1 (schlecht) bis 5 (gut):

> **Skala:** 5 = ideal fuer den ersten Schnitt, 1 = ungeeignet.
> Bei "Wenige Abhaeng.": 5 = kaum andere Contexts haengen daran (leicht herausloesbar),
> 1 = viele Contexts haengen daran (hohes Risiko).

| Context | Wenige Abhaeng. | Business-Wert | Klare Verantwort. | Isolierte DB | **Score** |
|---|---|---|---|---|---|
| | | | | | |
| | | | | | |
| | | | | | |
| | | | | | |
| | | | | | |
| | | | | | |

**Welcher Context hat den hoechsten Score? Das ist euer Startkandidat.**

#### Migrationsphasen

Plant anhand eures Startkandiaten, wie der Monolith schrittweise abgeloest wird:

```
Phase 0 (heute):  [Kompletter Monolith]

Phase 1:          [Monolith ohne Context A] + [Service A]
                       Strangler Proxy leitet Calls um

Phase 2:          [Monolith ohne A, ohne B] + [Service A] + [Service B]

Phase N:          [letzte Contexts als Services]
                       Monolith ist "stranguliert" = leer = abgeschaltet
```

---

### Schritt 5: Anti-Corruption Layer (ACL) einplanen

> **Warum dieser Schritt?**
> Wenn ein neuer Microservice mit dem alten Monolith kommunizieren muss,
> soll er **nicht** die interne Sprache des Monolithen uebernehmen.
> Der ACL uebersetzt zwischen den Modellen — er schuetzt den neuen Service
> vor der technischen Schuld des Monolithen.

#### Beispiel: Notification Service greift auf Kundendaten zu

**Ohne ACL (falsch):**

```
Notification Service                 Monolith
      |                                  |
      |-- GET /internal/user/42 -------> |
      |<-- { user_id: 42,               |
      |       usr_mail: "...",           |  Monolith-internes Modell
      |       created_ts: 1234567 } ---- |  leckt in den neuen Service
```

**Mit ACL (richtig):**

```
Notification Service    ACL (Adapter)          Monolith
      |                      |                     |
      |-- getRecipient(42) -> |                     |
      |                      |-- GET /internal/user/42 -->|
      |                      |<-- { usr_mail, ... } ------|
      |<-- Recipient{         |  (uebersetzt Monolith-
      |     email: "...",     |   Modell in eigene Sprache)
      |     name: "..." } --- |
```

#### Aufgabe

Skizziert fuer den **Notification Service** einen ACL:

1. Welche Daten braucht der Notification Service vom Monolith?
2. Wie soll das interne Modell des Notification Service aussehen?
3. Was uebersetzt der ACL?

---

### Schritt 6: Datenbankstrategie planen

> **Warum dieser Schritt?**
> Die **geteilte Datenbank** ist das groesste Hindernis beim Schneiden.
> Solange zwei Services in dieselbe DB schreiben, sind sie faktisch ein Monolith —
> egal wie viele Services man deployt.

#### Database-per-Service Strategie

```
ZIEL: Jeder Service hat seine eigene, isolierte Datenbank

+-------------------+    +-------------------+    +-------------------+
| Notification DB   |    |  Product DB       |    |  Order DB         |
| (PostgreSQL)      |    |  (PostgreSQL)     |    |  (PostgreSQL)     |
|                   |    |                   |    |                   |
| notifications     |    | products          |    | orders            |
| templates         |    | categories        |    | order_items       |
+-------------------+    +-------------------+    +-------------------+
       ^                        ^                        ^
       |                        |                        |
[Notification Svc]        [Product Svc]            [Order Svc]
```

#### Migrationsstrategie fuer die DB

**Problem:** Die `notifications`-Tabelle steht heute in der Monolith-DB,
mit Foreign Keys zu `users`, `orders`, etc.

**Loesungsschritte:**

```
1. Tabelle duplizieren (Strangler Phase):
   - Neue notifications-DB anlegen
   - Monolith schreibt in BEIDE DBs (dual-write)
   - Notification Service liest nur aus neuer DB

2. Consumers umstellen:
   - Monolith liest jetzt auch aus neuer DB

3. Alte Tabelle abschalten:
   - Monolith schreibt nur noch in neue DB (ueber API-Call)
   - Foreign Keys weg (durch Events ersetzt)
```

#### Diskussionsfrage

Was passiert, wenn der Dual-Write fehlschlaegt? Eine DB wird geschrieben, die andere nicht.
Wie loest ihr das? *(Hinweis: Outbox Pattern, Saga Pattern)*

---

### Schritt 7: Den ersten Service ausloesen (praktisch)

> Jetzt wird es konkret. Wir loesen den **Notification Service** aus dem Monolith.

#### Vorher: Notification-Code im Monolith

```
// OrderService.java (im Monolith)
public Order placeOrder(Cart cart, Customer customer) {
    Order order = createOrder(cart);
    paymentService.charge(customer, order.total());
    inventoryService.reserve(cart.items());

    // Direkte Kopplung - das wollen wir weg
    notificationService.sendOrderConfirmation(customer.email(), order);

    return order;
}
```

#### Problem

`OrderService` muss wissen, wie Notifications funktionieren.
Wenn `notificationService.sendOrderConfirmation()` fehlschlaegt, schlaegt die ganze Bestellung fehl.

#### Schritt 7a: Event statt direkter Aufruf

```
// OrderService.java (refactored, noch im Monolith)
public Order placeOrder(Cart cart, Customer customer) {
    Order order = createOrder(cart);
    paymentService.charge(customer, order.total());
    inventoryService.reserve(cart.items());

    // Kein direkter Aufruf mehr - nur Event publizieren
    eventBus.publish(new OrderPlacedEvent(
        order.id(),
        customer.email(),
        order.items(),
        order.total()
    ));

    return order;
}
```

**Was aendert sich?**
- `OrderService` kennt `NotificationService` nicht mehr
- Notification kann asynchron, spaeter, oder gar nicht ankommen — Bestellung ist trotzdem fertig
- Notification Service kann deployed werden, ohne den Monolith zu kennen

#### Schritt 7b: Notification Service als eigener Prozess

```
// NotificationService (neuer, eigenstaendiger Service)
@EventHandler
public void on(OrderPlacedEvent event) {
    Email email = templateEngine.render(
        "order-confirmation",
        Map.of("orderId", event.orderId(),
               "items",   event.items())
    );
    emailGateway.send(event.customerEmail(), email);
}
```

#### Schritt 7c: Strangler Proxy (falls REST-APIs umgeleitet werden muessen)

```
         Request: POST /api/notify/order-confirmation
                         |
               +--------------------+
               |   API Gateway /    |
               |  Strangler Proxy   |
               +--------------------+
                   /            \
          (alt)   /              \ (neu, wenn Feature-Flag aktiv)
                 /                \
    +------------------+    +-------------------+
    |     Monolith     |    | Notification Svc  |
    | /api/notify/...  |    | /api/notify/...   |
    +------------------+    +-------------------+
```

Der Proxy leitet Traffic schrittweise um — z.B. 10% zum neuen Service, dann 50%, dann 100%.

---

### Ergebnis-Praesentation (10 Min. pro Gruppe)

Praesentiert eurer Ergebnis:

1. **Context Map** — welche Contexts habt ihr identifiziert?
2. **Erste Priorisierung** — mit welchem Context fangt ihr an und warum?
3. **Ein kritischer Schnitt** — wo ist es schwierig? Was koennte schiefgehen?

---

### Zusammenfassung: Wann ist ein Schnitt gut?

| Kriterium | Gut | Schlecht |
|---|---|---|
| Kommunikation | Async Events oder klar definierte APIs | Direkter DB-Zugriff fremder Services |
| Daten | Jeder Service hat eigene DB | Geteilte DB-Tabellen |
| Deployment | Service deployed unabhaengig | Release braucht Koordination mit anderen |
| Sprache | Einheitliche Fachbegriffe im Context | "Bestellung" bedeutet in zwei Services verschiedenes |
| Teamverantwortung | Ein Team = ein Service | Mehrere Teams aendern denselben Service |
| Fehler | Ausfall isoliert (Circuit Breaker) | Ein Ausfall reisst alles |

> **Faustregel:** Wenn ihr mehr als 30 Minuten braucht, um zu erklaeren,
> welches Team fuer ein Feature verantwortlich ist — dann ist der Schnitt falsch.

### Musterloesung: Monolith schneiden mit DDD und Strangler Fig (Trainer)


> **Hinweis fuer Trainer:** Diese Seite enthaelt die Musterloesung fuer die Uebung.
> Nicht an Teilnehmer ausgeben — erst nach der Ergebnis-Praesentation zeigen.

---

### Schritt 1: Domain Events (Musterloesung)

Eine vollstaendige Liste liegt bei 20-25 Events. Wichtig: alle Fehlerfaelle mitnehmen.

```
KundeRegistriert        ProfilAktualisiert      KundeGeloescht
ProduktGespeichert      PreisGeaendert          ProduktGeloescht
LagerbestandAktualisiert LagerbestandKritisch
ArtikelInWarenkorbGelegt WarenkorbGeleert
BestellungAufgegeben    BestellungStorniert     RechnungErstellt
ZahlungErfolgt          ZahlungFehlgeschlagen   RueckzahlungInitiiert
LieferungVersendet      LieferungZugestellt     RetourneEingeleitet
EmailVersendet          SMSVersendet            PushNotificationVersendet
```

**Zeitlinie (Musterloesung):**

```
[frueh]                                                              [spaet]

KundeRegistriert -> ProduktGesucht -> ArtikelInWarenkorbGelegt
  -> BestellungAufgegeben -> ZahlungErfolgt -> LagerbestandAktualisiert
  -> LieferungVersendet -> LieferungZugestellt -> EmailVersendet -> RechnungErstellt

Fehlerfall:
  -> ZahlungFehlgeschlagen -> (Wiederholung oder BestellungStorniert)
  -> RueckzahlungInitiiert -> EmailVersendet
```

Events die im Normalfall zeitlich eng zusammen liegen, deuten auf denselben Context hin
(z.B. ZahlungErfolgt + LagerbestandAktualisiert + LieferungVersendet passieren alle kurz
nach BestellungAufgegeben — moeglicher Hinweis auf engen Bestellprozess-Kern).

**Haeufige Fehler:**
- Nur Erfolgsfaelle (ZahlungErfolgt ohne ZahlungFehlgeschlagen)
- Technische Events statt Business-Events (z.B. "DatenbankEintragErstellt")
- CRUD-Level: "KundeUpdated" statt "ProfilAktualisiert" / "EmailGeaendert"

---

### Schritt 2: Bounded Contexts (Musterloesung)

```
+-------------------+    +-------------------+    +-------------------+
|   Kundenverwaltung |    |    Produktkatalog  |    |   Lagerbestand    |
|                   |    |                   |    |                   |
| KundeRegistriert  |    | ProduktGespeichert |    | LagerbestandAktual|
| ProfilAktualisiert|    | PreisGeaendert     |    | LagerbestandKrit. |
| KundeGeloescht    |    | ProduktGeloescht   |    |                   |
+-------------------+    +-------------------+    +-------------------+

+-------------------+    +-------------------+    +-------------------+
|   Bestellprozess  |    |      Zahlung       |    |  Versand/Logistik |
|                   |    |                   |    |                   |
| ArtikelI.Warenk.  |    | ZahlungErfolgt     |    | LieferungVersendet|
| BestellungAufgeg. |    | ZahlungFehlgeschl. |    | LieferungZugest.  |
| BestellungStorn.  |    | RueckzahlungInit.  |    | RetourneEingel.   |
| RechnungErstellt  |    |                   |    |                   |
+-------------------+    +-------------------+    +-------------------+

+-------------------+
|  Benachrichtigung |
|                   |
| EmailVersendet    |
| SMSVersendet      |
| PushNotification  |
+-------------------+
```

**Diskussionspunkte:**

*Warum Lagerbestand kein Teil von Produktkatalog?*
Produktdaten aendert das Produktmanagement-Team (selten).
Lagerbestand aendert das Lager-Team bei jeder Bestellung (staendig).
Verschiedene Teams, verschiedene Aenderungsfrequenz = verschiedene Contexts.

*Warum Warenkorb kein eigener Context?*
Der Warenkorb ist ein kurzlebiger Zustand im Bestellprozess — keine eigene Fachsprache,
kein eigenes Team, keine eigene DB sinnvoll. Bei sehr grossem Traffic (z.B. Amazon)
koennte er eigener Context werden.

*Woran erkennt man einen zu gross geschnittenen Context?*
Beispiel: Jemand fasst Bestellung, Zahlung und Versand in einem "Bestellprozess"-Context zusammen:

```
Zu gross:
+-----------------------------------------------+
|               Bestellprozess                  |
|                                               |
| BestellungAufgegeben  ZahlungErfolgt          |
| ZahlungFehlgeschlagen LieferungVersendet      |
+-----------------------------------------------+
```

Erkennungszeichen:
- Das Zahlung-Team und das Logistik-Team muessen denselben Context aendern
- Ein Bugfix in der Zahlungslogik blockiert ein Deployment des Versand-Teams
- Die Fachbegriffe kommen aus verschiedenen Abteilungen mit verschiedener Sprache

Korrekt geschnitten: Bestellung, Zahlung und Versand sind drei separate Contexts,
die ueber Events miteinander kommunizieren.

*Ist Benachrichtigung wirklich ein fachlicher Bounded Context?*

Das ist eine berechtigte Frage — und die Antwort haengt vom Unternehmen ab.

Argumente dagegen (eher technischer Service):
- Keine eigene Fachlogik aus der Domaene — "EmailVersendet" ist ein technisches Resultat, kein Geschaeftsereignis
- Kein eigenes Domänenmodell
- Aehnlich wie Logging oder Monitoring: ein Querschnittsservice

Argumente dafuer (eigener fachlicher Context):
- Eigene Geschaeftsregeln: Wer bekommt welche Nachricht ueber welchen Kanal? (SMS deaktiviert, Opt-out, Praeferenzen)
- Eigenes Team: Communications-Team mit Templates, Branding, Versanddienstleister-Anbindung
- Eigene Fehlerbehandlung: Retry-Logik bei fehlgeschlagener Email, Bounce-Management

Fazit fuer ShopMax:
- Kleiner Shop → technischer Infrastruktur-Service, kein eigener Context
- Grosser Shop (Zalando, Amazon) → eigenes Communications-Team mit Personalisierung, A/B-Tests, rechtlichen Anforderungen → eigener fachlicher Context

In der Uebung wurde Benachrichtigung als eigener Context gewaehlt, weil er sich gut
als **erster Schnitt** eignet (keine Abhaengigkeiten, kein kritischer Pfad) —
nicht weil er fachlich zwingend ein eigener Context sein muss.

*Woran erkennt man, dass ein Event im falschen Bounded Context ist?*

| Zeichen | Beispiel aus ShopMax |
|---|---|
| **Falsches Team ist verantwortlich** | `VersandEmailVersendet` im Versand-Context — die Email schickt das Benachrichtigungs-Team, nicht die Logistik |
| **Falsche Ubiquitous Language** | `ProduktGesucht` im Warenkorb — im Warenkorb gibt es keine "Suche", der Begriff gehoert zum Produktkatalog |
| **Context reagiert nie auf dieses Event** | `RetoureErhalten` im Versand — Versand versendet, er empfaengt keine Retouren |
| **Event enthaelt Daten aus einem anderen Context** | `BestellungAufgegeben` mit `zahlungsMethode`-Feld im Bestellprozess — Zahlungsmethode gehoert in den Zahlungs-Context |
| **Andere Contexts muessen hineinsehen um zu reagieren** | Wenn Zahlung auf ein Event im Bestellprozess zugreifen muss, um eigene Events auszuloesen — ist die Grenze falsch gezogen |

---

### Schritt 3: Context Map (Musterloesung)

```
                         +-------------------+
                         |   Produktkatalog  |
                         +-------------------+
                              ^         ^
                              |         |
               +--------------+         +-----------+
               |                                    |
    +-------------------+                +-------------------+
    |   Bestellprozess  |                |   Lagerbestand    |
    +-------------------+                +-------------------+
       |        |    |                            ^
       |        |    +----BestellungAufgegeben--->+
       |        |
       |        +--------BestellungAufgegeben---> +-------------------+
       |                                          |  Benachrichtigung |
       | BestellungAufgegeben                     +-------------------+
       v                                                   ^
    +-------------------+                                  |
    |      Zahlung      |--------ZahlungErfolgt----------->+
    +-------------------+
               |
               | ZahlungErfolgt
               v
    +-------------------+
    |  Versand/Logistik |
    +-------------------+
```

#### Integration-Patterns

| Von | Nach | Aktuell (Monolith) | Ziel (Microservices) |
|---|---|---|---|
| Bestellprozess | Zahlung | direkter Methodenaufruf | async Event (BestellungAufgegeben) |
| Bestellprozess | Lagerbestand | direkter DB-Zugriff | async Event (BestellungAufgegeben) |
| Bestellprozess | Benachrichtigung | direkter Methodenaufruf | async Event (BestellungAufgegeben) |
| Zahlung | Versand | direkter Methodenaufruf | async Event (ZahlungErfolgt) |
| Zahlung | Benachrichtigung | direkter Methodenaufruf | async Event (ZahlungErfolgt) |
| Bestellprozess | Produktkatalog | direkter DB-Zugriff | sync REST (Preisabfrage) |

---

### Schritt 4: Strangler Fig — Migrationsreihenfolge (Musterloesung)

#### Bewertungsmatrix

> **Skala:** 5 = ideal fuer den ersten Schnitt, 1 = ungeeignet.
> Bei "Wenige Abhaeng.": 5 = kaum andere Contexts haengen daran (leicht herausloesbar),
> 1 = viele Contexts haengen daran (hohes Risiko).

| Context | Wenige Abhaeng. | Business-Wert | Klare Verantwort. | Isolierte DB | **Score** |
|---|---|---|---|---|---|
| Benachrichtigung | 5 | 2 | 5 | 4 | **16** |
| Produktkatalog | 3 | 4 | 4 | 3 | **14** |
| Versand/Logistik | 3 | 3 | 4 | 4 | **14** |
| Zahlung | 2 | 5 | 3 | 3 | **13** |
| Kundenverwaltung | 3 | 3 | 3 | 3 | **12** |
| Bestellprozess | 1 | 5 | 2 | 2 | **10** |
| Lagerbestand | 3 | 4 | 4 | 3 | **14** |

**Ergebnis:** Startkandidat ist **Benachrichtigung**, dann **Produktkatalog** oder **Lagerbestand**.

**Warum Benachrichtigung zuerst?**
- Keine anderen Services haengen davon ab (nur eingehende Events, kein Upstream-Abhaenger)
- Kein kritischer Pfad: Ausfall = keine Email, aber keine Bestellungsfehler
- Gut isolierbar: eigene DB-Tabellen, kein DB-Join mit anderen Services noetig
- Team kann den gesamten Prozess (Strangler Proxy, DB-Migration, Deployment) lernen — ohne Risiko fuer den Checkout-Pfad

**Warum Bestellprozess zuletzt?**
- Fast alle anderen Contexts haengen davon ab
- Geteilte DB mit Foreign Keys zu fast allen Tabellen
- Kritischer Pfad: Ausfall = keine Bestellungen = Umsatzverlust

#### Migrationsphasen (konkret fuer ShopMax)

```
Phase 0 (heute):    [Kompletter Monolith]

Phase 1 (Monat 1):  [Monolith ohne Notification] + [Notification Service]
                         Strangler Proxy leitet Notification-Calls um


Phase 2 (Monat 3):  [Monolith ohne Notification, Produkt, Lager]
                    + [Product Service] + [Inventory Service] + [Notification Service]

Phase 3 (Monat 6):  [Monolith-Kern: Bestellung + Zahlung + Kunde]
                    + [weitere Services]

Phase N:            [Bestellprozess Service] + [Zahlungs Service] + [Kunden Service]
                         Monolith ist "stranguliert" = leer = abgeschaltet
```

## Grundwissen Microservices - Synchrones Messaging

### gRPC vs. REST API


### 1. Kernvergleich gRPC vs REST

| Kriterium | gRPC | REST |
|-----------|------|------|
| **Protokoll/Format** | HTTP/2 + Protocol Buffers (binär) | HTTP/1.1 + JSON (text) |
| **Performance** | ~7-10x schneller, kleinere Payloads | Langsamer, größere Payloads |
| **Streaming** | Unidirektional, bidirektional, Server/Client | Nicht nativ (Workarounds möglich) |
| **Browser-Support** | Eingeschränkt (gRPC-Web nötig) | Nativ unterstützt |
| **Typsicherheit** | Stark typisiert durch .proto | Schwach, manuelle Validierung |
| **Code-Generierung** | Automatisch für Client/Server | Manuell oder über OpenAPI |

### 2. Praxisbeispiel: User-Service ↔ Order-Service

#### Proto-Definition (`user.proto`)
```protobuf
syntax = "proto3";

package user;

service UserService {
  // Einfacher Request
  rpc GetUser(UserRequest) returns (UserResponse);
  
  // Server-Streaming: Live-Updates zu User-Aktivitäten
  rpc StreamUserActivity(UserRequest) returns (stream ActivityEvent);
}

message UserRequest {
  string user_id = 1;
}

message UserResponse {
  string user_id = 1;
  string name = 2;
  string email = 3;
  int32 credit_score = 4;
}

message ActivityEvent {
  string user_id = 1;
  string event_type = 2;
  int64 timestamp = 3;
}
```

#### Server-Implementierung (Python)
```python
import grpc
from concurrent import futures
import user_pb2
import user_pb2_grpc
import time

class UserService(user_pb2_grpc.UserServiceServicer):
    
    def GetUser(self, request, context):
        # Simuliere DB-Zugriff
        return user_pb2.UserResponse(
            user_id=request.user_id,
            name="Max Mustermann",
            email="max@example.com",
            credit_score=750
        )
    
    def StreamUserActivity(self, request, context):
        # Server-Streaming: Sende Live-Events
        events = ['login', 'purchase', 'logout']
        for event in events:
            yield user_pb2.ActivityEvent(
                user_id=request.user_id,
                event_type=event,
                timestamp=int(time.time())
            )
            time.sleep(2)  # Simuliere Events über Zeit

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    user_pb2_grpc.add_UserServiceServicer_to_server(UserService(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    print("User-Service läuft auf Port 50051")
    server.wait_for_termination()

if __name__ == '__main__':
    serve()
```

#### Client-Implementierung im Order-Service (Python)
```python
import grpc
import user_pb2
import user_pb2_grpc

class OrderService:
    
    def __init__(self):
        # Verbindung zum User-Service
        self.channel = grpc.insecure_channel('user-service:50051')
        self.user_stub = user_pb2_grpc.UserServiceStub(self.channel)
    
    def create_order(self, user_id, items):
        # 1. User-Daten abrufen
        user = self.user_stub.GetUser(user_pb2.UserRequest(user_id=user_id))
        
        # 2. Credit-Check
        if user.credit_score < 600:
            return {"error": "Insufficient credit score"}
        
        # 3. Order erstellen
        order = {
            "user_id": user.user_id,
            "user_name": user.name,
            "items": items,
            "status": "confirmed"
        }
        return order
    
    def monitor_user_activity(self, user_id):
        # Server-Streaming: Empfange Live-Events
        print(f"Überwache Aktivität von User {user_id}...")
        
        for activity in self.user_stub.StreamUserActivity(
            user_pb2.UserRequest(user_id=user_id)
        ):
            print(f"Event: {activity.event_type} um {activity.timestamp}")
            
            # Reagiere auf Events
            if activity.event_type == 'purchase':
                print("→ Triggere Empfehlungs-Engine")

## Verwendung
order_service = OrderService()
order = order_service.create_order("user123", ["item1", "item2"])
print(order)

## Live-Monitoring
order_service.monitor_user_activity("user123")
```

#### Code generieren
```bash
## Proto-Dateien kompilieren
python -m grpc_tools.protoc -I. --python_out=. --grpc_python_out=. user.proto
```

### 3. Wann was verwenden?

#### Nutze gRPC wenn:
- **Interne Microservices** (Service-to-Service)
- **Performance kritisch** (High-Throughput, Low-Latency)
- **Streaming benötigt** (Real-time Updates, Logs)
- **Polyglotte Umgebung** (automatische Code-Generierung für viele Sprachen)

#### Nutze REST wenn:
- **Public APIs** (externe Partner, Entwickler)
- **Browser-Clients** (Web-Apps ohne gRPC-Web)
- **Einfachheit bevorzugt** (schnelles Prototyping, Debugging)
- **HTTP/1.1-Infrastruktur** (Legacy-Systeme, bestimmte Proxies)

### Was wird generiert:

Aus `user.proto` generiert der Compiler:

```bash
python -m grpc_tools.protoc -I. --python_out=. --grpc_python_out=. user.proto
```

**Erzeugt automatisch:**
- `user_pb2.py` - Message-Klassen (UserRequest, UserResponse, etc.)
- `user_pb2_grpc.py` - Stub-Klassen und Service-Basis

```python
## user_pb2_grpc.py (generiert)
class UserServiceServicer(object):  # ← Basis für Server
    def GetUser(self, request, context):
        raise NotImplementedError()
    
    def StreamUserActivity(self, request, context):
        raise NotImplementedError()

class UserServiceStub(object):  # ← Für Client
    def __init__(self, channel):
        self.GetUser = channel.unary_unary(...)
        self.StreamUserActivity = channel.unary_stream(...)
```

### Was du selbst schreibst:

✍️ **Server:** Business-Logik in `UserService(UserServiceServicer)` - erbt nur von generierter Basis

✍️ **Client:** `OrderService` - nutzt generierten `UserServiceStub`, aber Logik ist manuell

**Zusammenfassung:** Der Compiler gibt dir typsichere Schnittstellen, die Implementierung schreibst du.

### API-Abfrage über REST-API


### Grundlagen 

  * synchrone Kommunikation -> bspw. REST-API (zu 95%)

### Idee dahinter (microservice) 

 *  Nie direkt auf Daten zuzugreifen (immer nur immer API)  

### Damit auch die Möglichkeit haben, Informationen zu verstecken

  * Prinzip von Hide Information, nur das wirklich gebraucht wird, kann abgefragt werden und der Rest ist nur im Hintergrund innerhalb des MicroServices zugänglich
  * API (klarer Vertrag, wo festgelegt, welche Parameter an die API übergeben werden können/müssen) und welche Information zurückkommt 

### Beispiel

```
## GET - Abfrage 
https://api.bitpanda.com/v1/trades
## i.d.R. kriegen wir die Information als json zurück 

```

```
PUT /shop/products/11 HTTP/1.1
Host: api.predic8.de
Content-Type: application/json

{
  "name": "Red Grapes",
  "price": 1.79,
  "category_url": "/shop/categories/Fruits",
  "vendor_url": "/shop/vendors/501"
}

curl -X PUT --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{
      "name": "Red Grapes",
      "price": 1.79,
      "category_url": "/shop/categories/Fruits",
      "vendor_url": "/shop/vendors/501"
}' 'https://api.predic8.de/shop/products/140'
```

### OpenAPI-Spec aus Code generieren (Go, Python, Java, TS, Rust, C#, PHP)


Uebersicht der wichtigsten Tools pro Sprache mit minimalen Code-Beispielen.

---

### Go

#### swaggo/swag (Kommentar-basiert)

Installation:

```bash
go install github.com/swaggo/swag/cmd/swag@latest
go get github.com/swaggo/gin-swagger
go get github.com/swaggo/files
```

Handler mit Annotations:

```go
// @Summary      Get user by ID
// @Description  Returns a single user
// @Tags         users
// @Param        id   path      int  true  "User ID"
// @Success      200  {object}  User
// @Failure      404  {object}  ErrorResponse
// @Router       /users/{id} [get]
func GetUser(c *gin.Context) {
    // ...
}

type User struct {
    ID    int    `json:"id" example:"1"`
    Name  string `json:"name" example:"Jochen"`
    Email string `json:"email" example:"j@example.com"`
}
```

Generieren:

```bash
swag init -g main.go -o ./docs
## erzeugt docs/swagger.json + docs/swagger.yaml
```

#### Huma (native, ohne Annotations)

```go
package main

import (
    "context"
    "github.com/danielgtaylor/huma/v2"
    "github.com/danielgtaylor/huma/v2/adapters/humachi"
    "github.com/go-chi/chi/v5"
)

type GreetingInput struct {
    Name string `path:"name" maxLength:"30" example:"world"`
}

type GreetingOutput struct {
    Body struct {
        Message string `json:"message"`
    }
}

func main() {
    router := chi.NewMux()
    api := huma.NewAPI("My API", "1.0.0", humachi.New(router, huma.DefaultConfig("My API", "1.0.0")))

    huma.Register(api, huma.Operation{
        OperationID: "get-greeting",
        Method:      "GET",
        Path:        "/greeting/{name}",
        Summary:     "Get a greeting",
    }, func(ctx context.Context, input *GreetingInput) (*GreetingOutput, error) {
        resp := &GreetingOutput{}
        resp.Body.Message = "Hello, " + input.Name
        return resp, nil
    })
}
// OpenAPI verfuegbar unter /openapi.json
```

---

### Python

#### FastAPI (nativ, Gold-Standard)

```bash
pip install fastapi uvicorn
```

```python
from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI(title="My API", version="1.0.0")

class User(BaseModel):
    id: int
    name: str
    email: str

class UserCreate(BaseModel):
    name: str
    email: str

@app.get("/users/{user_id}", response_model=User, tags=["users"])
def get_user(user_id: int):
    """Returns a single user by ID."""
    return {"id": user_id, "name": "Jochen", "email": "j@example.com"}

@app.post("/users", response_model=User, status_code=201, tags=["users"])
def create_user(user: UserCreate):
    return {"id": 1, **user.dict()}
```

Spec abrufen:

```bash
uvicorn main:app
## Swagger UI:  http://localhost:8000/docs
## ReDoc:       http://localhost:8000/redoc
## JSON-Spec:   http://localhost:8000/openapi.json
```

Spec als Datei exportieren:

```python
import json
from main import app

with open("openapi.json", "w") as f:
    json.dump(app.openapi(), f, indent=2)
```

#### Django REST Framework

```bash
pip install drf-spectacular
```

`settings.py`:

```python
INSTALLED_APPS = [..., "drf_spectacular"]
REST_FRAMEWORK = {
    "DEFAULT_SCHEMA_CLASS": "drf_spectacular.openapi.AutoSchema",
}
SPECTACULAR_SETTINGS = {"TITLE": "My API", "VERSION": "1.0.0"}
```

```bash
python manage.py spectacular --file schema.yaml
```

---

### TypeScript / Node.js

#### NestJS

```bash
npm install @nestjs/swagger
```

```typescript
// main.ts
import { NestFactory } from '@nestjs/core';
import { SwaggerModule, DocumentBuilder } from '@nestjs/swagger';
import { AppModule } from './app.module';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  const config = new DocumentBuilder()
    .setTitle('My API').setVersion('1.0').build();
  const document = SwaggerModule.createDocument(app, config);
  SwaggerModule.setup('docs', app, document);
  await app.listen(3000);
}
bootstrap();
```

```typescript
// user.controller.ts
import { Controller, Get, Param } from '@nestjs/common';
import { ApiTags, ApiOperation, ApiResponse } from '@nestjs/swagger';
import { ApiProperty } from '@nestjs/swagger';

export class UserDto {
  @ApiProperty({ example: 1 }) id: number;
  @ApiProperty({ example: 'Jochen' }) name: string;
}

@ApiTags('users')
@Controller('users')
export class UserController {
  @Get(':id')
  @ApiOperation({ summary: 'Get user by ID' })
  @ApiResponse({ status: 200, type: UserDto })
  findOne(@Param('id') id: string): UserDto {
    return { id: +id, name: 'Jochen' };
  }
}
```

UI unter `http://localhost:3000/docs`, JSON unter `/docs-json`.

#### Zod + Hono (modern, typsicher)

```bash
npm install hono @hono/zod-openapi zod
```

```typescript
import { OpenAPIHono, createRoute, z } from '@hono/zod-openapi';

const UserSchema = z.object({
  id: z.number().openapi({ example: 1 }),
  name: z.string().openapi({ example: 'Jochen' }),
}).openapi('User');

const route = createRoute({
  method: 'get',
  path: '/users/{id}',
  request: { params: z.object({ id: z.string() }) },
  responses: {
    200: { content: { 'application/json': { schema: UserSchema } }, description: 'OK' },
  },
});

const app = new OpenAPIHono();
app.openapi(route, (c) => c.json({ id: 1, name: 'Jochen' }));
app.doc('/openapi.json', { openapi: '3.0.0', info: { title: 'API', version: '1.0' } });
```

---

### Java / Spring Boot

#### springdoc-openapi

`pom.xml`:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.5.0</version>
</dependency>
```

```java
@RestController
@RequestMapping("/users")
@Tag(name = "users")
public class UserController {

    @Operation(summary = "Get user by ID")
    @ApiResponse(responseCode = "200", description = "Found")
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return new User(id, "Jochen", "j@example.com");
    }
}

@Schema(description = "User entity")
public record User(
    @Schema(example = "1") Long id,
    @Schema(example = "Jochen") String name,
    @Schema(example = "j@example.com") String email
) {}
```

Endpunkte:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- JSON: `http://localhost:8080/v3/api-docs`
- YAML: `http://localhost:8080/v3/api-docs.yaml`

---

### Rust

#### utoipa (mit Axum)

`Cargo.toml`:

```toml
[dependencies]
utoipa = { version = "4", features = ["axum_extras"] }
utoipa-swagger-ui = { version = "7", features = ["axum"] }
axum = "0.7"
serde = { version = "1", features = ["derive"] }
```

```rust
use axum::{routing::get, Json, Router};
use serde::Serialize;
use utoipa::{OpenApi, ToSchema};
use utoipa_swagger_ui::SwaggerUi;

##[derive(Serialize, ToSchema)]
struct User {
    #[schema(example = 1)]
    id: u64,
    #[schema(example = "Jochen")]
    name: String,
}

##[utoipa::path(
    get,
    path = "/users/{id}",
    params(("id" = u64, Path, description = "User ID")),
    responses((status = 200, body = User))
)]
async fn get_user() -> Json<User> {
    Json(User { id: 1, name: "Jochen".into() })
}

##[derive(OpenApi)]
##[openapi(paths(get_user), components(schemas(User)))]
struct ApiDoc;

##[tokio::main]
async fn main() {
    let app = Router::new()
        .route("/users/:id", get(get_user))
        .merge(SwaggerUi::new("/swagger-ui").url("/openapi.json", ApiDoc::openapi()));
    // ...
}
```

---

### C# / .NET

#### Swashbuckle (ASP.NET Core)

```bash
dotnet add package Swashbuckle.AspNetCore
```

```csharp
// Program.cs
var builder = WebApplication.CreateBuilder(args);
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen(c =>
    c.SwaggerDoc("v1", new() { Title = "My API", Version = "v1" }));

var app = builder.Build();
app.UseSwagger();
app.UseSwaggerUI();

app.MapGet("/users/{id}", (int id) => new User(id, "Jochen"))
   .WithTags("users")
   .WithOpenApi();

app.Run();

public record User(int Id, string Name);
```

UI: `/swagger`, JSON: `/swagger/v1/swagger.json`

---

### PHP

#### swagger-php (Annotations / Attributes)

```bash
composer require zircote/swagger-php
```

```php
use OpenApi\Attributes as OA;

##[OA\Info(version: '1.0.0', title: 'My API')]
class OpenApiSpec {}

class UserController
{
    #[OA\Get(
        path: '/users/{id}',
        tags: ['users'],
        parameters: [new OA\Parameter(name: 'id', in: 'path', required: true)],
        responses: [new OA\Response(response: 200, description: 'OK',
            content: new OA\JsonContent(ref: '#/components/schemas/User'))]
    )]
    public function getUser(int $id) { /* ... */ }
}

##[OA\Schema(schema: 'User')]
class User {
    #[OA\Property(example: 1)] public int $id;
    #[OA\Property(example: 'Jochen')] public string $name;
}
```

Generieren:

```bash
./vendor/bin/openapi src -o openapi.yaml
```

---

### Workflow-Tipps

**Spec versionieren:** Generierte `openapi.json` / `openapi.yaml` in Git checken — Diffs zeigen Breaking Changes sofort.

**CI-Check gegen Breaking Changes:**

```bash
## oasdiff vergleicht zwei Specs
docker run --rm -v $PWD:/specs tufin/oasdiff breaking \
  /specs/openapi-main.yaml /specs/openapi-pr.yaml
```

**Client-SDKs aus der Spec generieren:**

```bash
## OpenAPI Generator (Java-basiert, viele Sprachen)
docker run --rm -v $PWD:/local openapitools/openapi-generator-cli generate \
  -i /local/openapi.yaml -g typescript-axios -o /local/client-ts
```

**Lint:**

```bash
npx @stoplight/spectral-cli lint openapi.yaml
```

**Faustregel:** Tools, die aus Typen/Schemas generieren (FastAPI, NestJS, utoipa, Huma), liefern bessere Specs als kommentar-basierte (swaggo, swagger-php), weil sie nicht aus der Synchronitaet laufen koennen.

## Grundwissen Microservices - Async Messaging

### Asynchrones Messaging


![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/cd531b61-4856-4528-ba19-fd9761390c64)


### EventBus Implementierungen/Überblick


### Fertige Software, die einen Event Bus bereitsstellt

  * Kafka 
  * RabbitMQ
  * NATS 

### Was ist Ihre Aufgabe ? 

  * Events empfangen 
  * Events veröffentlichen (publish) für die Zuhörer (listener)
 

### Wie sehen Events aus ? 

  * Mit Events meinen wir Informations-Snippets 
    * Es ist nicht festgelegt, wie eine Event aussehen soll, es kann
      * Rohe Datenbytes
      * JSON
      * ein String
      * u.a. ... sein (was immer du verwenden willst)

### Was sind Listener ?

  * Listener sind Services, die von anderen Events von anderen Services erfahren wollen 

### Kafka Schaubild


<img width="731" height="531" alt="image" src="https://github.com/user-attachments/assets/ac4f1078-bde2-4ea0-a281-c7bfcc08aeaa" />


### Schema Registry (confluent)


### Confluent Schema Registry (Kafka-Lösungen) 

  * Versions every single schema (versioning) 
  * data validation
  * compatibility checking
  * versioning
  * evolution

### Software (Implementation) 

  * Confluence Schema Registry

### Uebung: Kafka Schema Registry — Avro und Schema-Evolution


### Hintergrund

Die Confluent Schema Registry zentralisiert die Verwaltung von Avro-Schemas.
Statt das Schema in jedem Producer/Consumer zu hardcoden, wird es einmalig registriert.
Die Registry erzwingt Kompatibilitaetsregeln und verhindert Breaking Changes.

```
Producer  →  Schema Registry  →  Kafka Topic  →  Consumer
           (Schema-ID + Daten)                  (holt Schema per ID)
```

---

### Teil 1 — Zentraler Setup (Trainer)

> Dieser Teil laeuft **einmalig**. Teilnehmer koennen beobachten, muessen aber nicht selbst ausfuehren.

#### 1.1 Namespace anlegen

```
kubectl create namespace kafka
```

#### 1.2 Kafka deployen (apache/kafka:3.9.0, KRaft-Mode)

```
kubectl apply -n kafka -f - <<'EOF'
---
apiVersion: v1
kind: Service
metadata:
  name: kafka-controller-headless
  namespace: kafka
spec:
  clusterIP: None
  ports:
  - name: client
    port: 9092
  - name: controller
    port: 9093
  selector:
    app.kubernetes.io/name: kafka
---
apiVersion: v1
kind: Service
metadata:
  name: kafka
  namespace: kafka
spec:
  ports:
  - name: client
    port: 9092
  selector:
    app.kubernetes.io/name: kafka
---
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: kafka-controller
  namespace: kafka
spec:
  serviceName: kafka-controller-headless
  replicas: 3
  selector:
    matchLabels:
      app.kubernetes.io/name: kafka
  template:
    metadata:
      labels:
        app.kubernetes.io/name: kafka
    spec:
      securityContext:
        fsGroup: 1000
        runAsUser: 1000
      containers:
      - name: kafka
        image: apache/kafka:3.9.0
        command:
        - /bin/bash
        - -c
        - |
          NODE_ID=${HOSTNAME##*-}
          HOST="${HOSTNAME}.kafka-controller-headless.kafka.svc.cluster.local"
          export KAFKA_NODE_ID=$NODE_ID
          export KAFKA_ADVERTISED_LISTENERS="PLAINTEXT://${HOST}:9092,CONTROLLER://${HOST}:9093"
          mkdir -p /var/lib/kafka/data/kafka-logs
          exec /etc/kafka/docker/run
        env:
        - name: CLUSTER_ID
          value: "MkU3OEVBNTcwNTJENDM2Qk"
        - name: KAFKA_PROCESS_ROLES
          value: "broker,controller"
        - name: KAFKA_CONTROLLER_QUORUM_VOTERS
          value: "0@kafka-controller-0.kafka-controller-headless.kafka.svc.cluster.local:9093,1@kafka-controller-1.kafka-controller-headless.kafka.svc.cluster.local:9093,2@kafka-controller-2.kafka-controller-headless.kafka.svc.cluster.local:9093"
        - name: KAFKA_LISTENERS
          value: "PLAINTEXT://0.0.0.0:9092,CONTROLLER://0.0.0.0:9093"
        - name: KAFKA_CONTROLLER_LISTENER_NAMES
          value: "CONTROLLER"
        - name: KAFKA_INTER_BROKER_LISTENER_NAME
          value: "PLAINTEXT"
        - name: KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR
          value: "3"
        - name: KAFKA_DEFAULT_REPLICATION_FACTOR
          value: "3"
        - name: KAFKA_MIN_INSYNC_REPLICAS
          value: "2"
        - name: KAFKA_LOG_DIRS
          value: "/var/lib/kafka/data/kafka-logs"
        - name: PATH
          value: "/opt/kafka/bin:/opt/java/openjdk/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin"
        ports:
        - containerPort: 9092
          name: client
        - containerPort: 9093
          name: controller
        volumeMounts:
        - name: data
          mountPath: /var/lib/kafka/data
  volumeClaimTemplates:
  - metadata:
      name: data
    spec:
      accessModes: ["ReadWriteOnce"]
      resources:
        requests:
          storage: 4Gi
EOF
```

```
kubectl -n kafka rollout status statefulset/kafka-controller
```

> **Hinweis:** KRaft-Mode (kein Zookeeper), 3 Combined-Controller+Broker Nodes.

#### 1.3 Schema Registry deployen (confluentinc/cp-schema-registry:7.7.1)

```
kubectl apply -n kafka -f - <<'EOF'
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: schema-registry
  namespace: kafka
spec:
  replicas: 1
  selector:
    matchLabels:
      app: schema-registry
  template:
    metadata:
      labels:
        app: schema-registry
    spec:
      enableServiceLinks: false
      containers:
      - name: schema-registry
        image: confluentinc/cp-schema-registry:7.7.1
        env:
        - name: SCHEMA_REGISTRY_HOST_NAME
          value: "schema-registry"
        - name: SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS
          value: "PLAINTEXT://kafka.kafka.svc.cluster.local:9092"
        - name: SCHEMA_REGISTRY_LISTENERS
          value: "http://0.0.0.0:8081"
        - name: SCHEMA_REGISTRY_KAFKASTORE_TOPIC_REPLICATION_FACTOR
          value: "3"
        ports:
        - containerPort: 8081
          name: http
---
apiVersion: v1
kind: Service
metadata:
  name: schema-registry
  namespace: kafka
spec:
  selector:
    app: schema-registry
  ports:
  - port: 8081
    name: http
    targetPort: 8081
EOF
```

#### 1.4 Verifikation

```
kubectl -n kafka get pods
```

Erwartete Ausgabe:

```
NAME                               READY   STATUS    RESTARTS   AGE
kafka-controller-0                 1/1     Running   0          2m
kafka-controller-1                 1/1     Running   0          2m
kafka-controller-2                 1/1     Running   0          2m
schema-registry-...                1/1     Running   0          1m
```

```
kubectl -n kafka run verify --image=curlimages/curl --restart=Never --rm -i -- \
  curl -s http://schema-registry.kafka.svc.cluster.local:8081/subjects
```

Erwartete Ausgabe: `[]`

---

### Teil 2 — Pro Teilnehmer

> **Setze als Erstes deine TLN-Nummer:**
>
> ```
> export TLN_NR=1   # <- deine Nummer
> export NS=tln${TLN_NR}
> export TOPIC=tln${TLN_NR}-orders
> ```

#### 2.1 Topic anlegen

```
kubectl -n kafka exec -it kafka-controller-0 -- \
  kafka-topics.sh --bootstrap-server localhost:9092 \
    --create --topic ${TOPIC} \
    --partitions 3 --replication-factor 2
```

Erwartete Ausgabe: `Created topic tln1-orders.`

#### 2.2 Avro-Schema registrieren

```
kubectl -n ${NS} run schemareg --image=curlimages/curl --restart=Never -i --rm -- \
  curl -s -X POST \
    -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    --data '{"schema": "{\"type\":\"record\",\"name\":\"Order\",\"namespace\":\"de.tln'${TLN_NR}'\",\"fields\":[{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"product\",\"type\":\"string\"},{\"name\":\"quantity\",\"type\":\"int\"}]}"}' \
    http://schema-registry.kafka.svc.cluster.local:8081/subjects/${TOPIC}-value/versions
```

Erwartete Ausgabe: `{"id":1}`

**Verifikation:**

```
kubectl -n ${NS} run verify --image=curlimages/curl --restart=Never -i --rm -- \
  curl -s http://schema-registry.kafka.svc.cluster.local:8081/subjects/${TOPIC}-value/versions/latest
```

#### 2.3 Producer-Pod (Avro Console Producer)

```
kubectl -n ${NS} run producer -it --rm --restart=Never \
  --image=confluentinc/cp-schema-registry:7.7.1 -- bash
```

Im Container:

```
kafka-avro-console-producer \
  --bootstrap-server kafka.kafka.svc.cluster.local:9092 \
  --topic tln${TLN_NR}-orders \
  --property schema.registry.url=http://schema-registry.kafka.svc.cluster.local:8081 \
  --property value.schema='{"type":"record","name":"Order","namespace":"de.tln'${TLN_NR}'","fields":[{"name":"id","type":"string"},{"name":"product","type":"string"},{"name":"quantity","type":"int"}]}'
```

Eingabe (jede Zeile = eine Nachricht):

```
{"id":"o-1","product":"Schraubenzieher","quantity":2}
{"id":"o-2","product":"Hammer","quantity":1}
```

Strg+D zum Beenden.

#### 2.4 Consumer-Pod

In **neuem Terminal**:

```
export TLN_NR=1
export NS=tln${TLN_NR}

kubectl -n ${NS} run consumer -it --rm --restart=Never \
  --image=confluentinc/cp-schema-registry:7.7.1 -- bash
```

Im Container:

```
kafka-avro-console-consumer \
  --bootstrap-server kafka.kafka.svc.cluster.local:9092 \
  --topic tln${TLN_NR}-orders \
  --from-beginning \
  --property schema.registry.url=http://schema-registry.kafka.svc.cluster.local:8081
```

**Erwartete Ausgabe:**

```
{"id":"o-1","product":"Schraubenzieher","quantity":2}
{"id":"o-2","product":"Hammer","quantity":1}
```

---

### Teil 3 — Schema-Evolution (Bonus)

Fuege ein optionales Feld hinzu (rueckwaertskompatibel):

```
kubectl -n ${NS} run schemareg2 --image=curlimages/curl --restart=Never -i --rm -- \
  curl -s -X POST \
    -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    --data '{"schema": "{\"type\":\"record\",\"name\":\"Order\",\"namespace\":\"de.tln'${TLN_NR}'\",\"fields\":[{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"product\",\"type\":\"string\"},{\"name\":\"quantity\",\"type\":\"int\"},{\"name\":\"customer\",\"type\":[\"null\",\"string\"],\"default\":null}]}"}' \
    http://schema-registry.kafka.svc.cluster.local:8081/subjects/${TOPIC}-value/versions
```

Erwartete Ausgabe: `{"id":2}` — Versionsnummer steigt auf 2.

Alle Versionen listen:

```
kubectl -n ${NS} run verify --image=curlimages/curl --restart=Never -i --rm -- \
  curl -s http://schema-registry.kafka.svc.cluster.local:8081/subjects/${TOPIC}-value/versions
```

Erwartete Ausgabe: `[1,2]`

#### Inkompatibles Schema testen

```
kubectl -n ${NS} run schemareg-bad --image=curlimages/curl --restart=Never -i --rm -- \
  curl -s -X POST \
    -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    --data '{"schema": "{\"type\":\"record\",\"name\":\"Order\",\"fields\":[{\"name\":\"id\",\"type\":\"int\"}]}"}' \
    http://schema-registry.kafka.svc.cluster.local:8081/subjects/${TOPIC}-value/versions
```

**Erwartete Ausgabe:** HTTP 409 — `Schema being registered is incompatible with an earlier schema`

---

### Teil 4 — Cleanup (pro Teilnehmer)

```
kubectl -n kafka exec kafka-controller-0 -- \
  kafka-topics.sh --bootstrap-server localhost:9092 \
    --delete --topic tln${TLN_NR}-orders

kubectl -n ${NS} run cleanup --image=curlimages/curl --restart=Never -i --rm -- \
  curl -s -X DELETE \
    http://schema-registry.kafka.svc.cluster.local:8081/subjects/tln${TLN_NR}-orders-value
```

---

### Diskussionsfragen

1. Warum ist Topic-Naming-Prefix (`tln${TLN_NR}-`) bei geteiltem Cluster sinnvoll? Welche Alternative gäbe es mit ACLs?
2. Welche Kompatibilitaetsmodi gibt es in der Schema Registry (`BACKWARD`, `FORWARD`, `FULL`, `NONE`) und wann nutzt man welchen?
3. Warum wird `${TOPIC}-value` als Subject-Name verwendet? (TopicNameStrategy vs. RecordNameStrategy)
4. Wie wuerdest du Producer-Isolation per Kafka-ACL pro `tln${TLN_NR}` umsetzen?

---

### Troubleshooting

| Symptom | Ursache | Fix |
|---|---|---|
| Producer haengt bei „Schema not found" | Schema-Registry-URL falsch | Service-DNS pruefen: `schema-registry.kafka.svc.cluster.local:8081` |
| `LEADER_NOT_AVAILABLE` | Broker noch nicht ready | `kubectl -n kafka get pods` — warten bis alle 3 Controller `1/1` |
| `409 Conflict` beim Schema-POST | Inkompatibel zu Vorversion | Kompatibilitaetsmodus pruefen: `GET /config/${SUBJECT}` |
| Topic-Erstellung „replication factor: 2 > 1" | Nur 1 Broker laeuft | Replication-Factor auf 1 reduzieren oder Controller-Replicas pruefen |
| Schema Registry CrashLoop | Kubernetes injiziert `SCHEMA_REGISTRY_PORT` | `enableServiceLinks: false` im Pod-Spec setzen |

### Topic/Queue ohne Downtime migrieren


### How can you be sure, that consumer goes not get data from the old topic 

  * Producer should only (!) write to one topic (the newest)

### How to switch to a new version ? (Part 1: producer) 

  1. Every topics has a version
  1. New Version :: myTopic.v0 -> myTopic.v1
  1. Whenever a new version is there, we will write it into topics: _meta_version
  1. Producer will be consumer for the _meta_version and wil get to know about the topic
  1. Producer will immediately write to the new topic, BUT ONLY to the new topic

### How to switch to a new version ? (Part 2: consumer) 

  1. consumer has to drain the old topic before switching to the new one
  1. how to know it is drained ? Ask for "watermark" offset (gives back the last offset)
  1. When you have the last message with the watermark offset -> switch to new topic

### Watermark - Background 

  1. The offset of the last message in topic, can be retrieved with "watermark"
  1. Example Code: https://github.com/confluentinc/confluent-kafka-python/blob/master/examples/get_watermark_offsets.py

### Reference:

  * https://gauravsarma1992.medium.com/migrating-kafka-topic-without-downtime-f863819cfb3d

### Disruptive Änderungen im Schema migrieren


### Step 1: Status Quo 

  1. Producer P1 is producing Message to Topic T1
  1. Consumer C1 and C2 are consuming this topic

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/25a964ce-ca64-4403-8685-70e53346e6b4)

### Step 2: New Topic T2 with breaking Schema S2 

   1. Topic is currently empty 

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/baef7656-386d-42e7-916b-693b6ced0bb5)

### Step 3: Maintaining history order with migration component (Mode 1 -> 2)

  * reproducing all existing records from topic T1 on topic T2
    * by transforming them into messages following schema S2 and producing them on topic T2.
  * Same keys are to be used (so it will be in the same partitions)
  * reproduced messages are to retain the original timestamp
    * in order to preserve the semantics of the message
  * No Downtime: producer P1 can meanwhile still produce new messages that may get consumed by existing consumer
  * Every message from P1 will be picked up by the migration component and reproduced in T2

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/2c220ecc-2f4d-4590-b6ab-7fceb5e4e3f7)
![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/2eb44846-96ee-4523-99a7-0dc396b65aa0)

### Step 4: New Version P1 (will not send to T1) any more 

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/61344c81-d2da-4d6f-9c9a-f94c072ae691)

### Step 5: P1 sends messages to T2 (after migration component has reproduced ...)

  1. Migration component has reproduced all messages from T1 -> T2
  1. AFTER THAT ! P1 will start to send messages to T2

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/c752284e-fb77-4b31-9bac-1fd50c532cca)

### Step 6: Switching operation mode of migration component (mode: 2->1)

  1. Migration component will from now on consume messages from topic T2 and reproduce them on topic T1.

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/131fa36c-3b52-49fb-9fa0-5d3c5ebb4d2f)

### Step 7: Ready for consumers 

  1. It is now possible for the consumers C1 and C2 to switch to T2 at any time

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/2606dc14-e2b1-48fd-ad6e-a2aa6a6088d7)

### Step 8: All consumers have migrated 

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/4269a17e-f9ef-44af-8889-7a8a214b406d)

### Step 9: Cleanup, when all have migrated 

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/1c4ed6c5-3d6d-4cc5-aea3-b0a9595df957)


### Reference

  * https://cymo.eu/blog/a-strategy-for-dealing-with-breaking-schema

## Grundwissen Microservices - Fehlertolaranz

### Circuit-Breaker und Fehlertoleranz


## Circuit Breaker & Retry-Strategien in Python

### Was ist das?

**Retry**: Wiederhole fehlgeschlagene Requests automatisch (z.B. bei temporären Netzwerkfehlern)

**Circuit Breaker**: Stoppt Requests zu einem Service, der wiederholt fehlschlägt → verhindert Kaskadeneffekte

### Wann was nutzen?

- **Retry**: Transiente Fehler (Timeout, 503, Netzwerkstörung)
- **Circuit Breaker**: Anhaltende Ausfälle, Schutz vor Überlastung
- **Kombination**: Oft zusammen eingesetzt

### States im Circuit Breaker
1. **Closed**: Normal, Requests gehen durch
2. **Open**: Service ausgefallen, Requests werden sofort abgelehnt
3. **Half-Open**: Test, ob Service wieder verfügbar

### Python-Implementierung

```python
from tenacity import retry, stop_after_attempt, wait_exponential
from pybreaker import CircuitBreaker

## RETRY mit tenacity
@retry(
    stop=stop_after_attempt(3),
    wait=wait_exponential(multiplier=1, min=1, max=10)
)
def call_api():
    response = requests.get("https://api.example.com")
    response.raise_for_status()
    return response.json()

## CIRCUIT BREAKER mit pybreaker
breaker = CircuitBreaker(
    fail_max=5,        # Nach 5 Fehlern → Open
    reset_timeout=60   # Nach 60s → Half-Open
)

@breaker
def protected_call():
    return requests.get("https://api.example.com").json()
```

### Best Practices

- Circuit Breaker für externe Services
- Monitoring & Logging der Failures
- Fallback-Strategien definieren

**Bibliotheken**: `tenacity`, `pybreaker`, `resilience4py`

## Grundwissen Microservices - Tests (Teil 3)

## Linux Tipps & Tricks

### In den Root-Benutzer wechseln


```
## kurs> 
sudo su -
## password von kurs eingegeben
## wenn wir vorher der benutzer kurs waren
```

## Docker-Grundlagen 

### Übersicht Architektur


![Docker Architecture - copyright geekflare](https://geekflare.com/wp-content/uploads/2019/09/docker-architecture-609x270.png)

### Was ist ein Container ?


```
- vereint in sich Software
- Bibliotheken 
- Tools 
- Konfigurationsdateien 
- keinen eigenen Kernel 
- gut zum Ausführen von Anwendungen auf verschiedenen Umgebungen 

- Container sind entkoppelt
- Container sind voneinander unabhängig 
- Können über wohldefinierte Kommunikationskanäle untereinander Informationen austauschen

- Durch Entkopplung von Containern:
  o Unverträglichkeiten von Bibliotheken, Tools oder Datenbank können umgangen werden, wenn diese von den Applikationen in unterschiedlichen Versionen benötigt werden.
```

### Was sind container images


  * Container Image benötigt, um zur Laufzeit Container-Instanzen zu erzeugen 
  * Bei Docker werden Docker Images zu Docker Containern, wenn Sie auf einer Docker Engine als Prozess ausgeführt werden.
  * Man kann sich ein Docker Image als Kopiervorlage vorstellen.
    * Diese wird genutzt, um damit einen Docker Container als Kopie zu erstellen   

### Container vs. Virtuelle Maschine


```
VM's virtualisieren Hardware
Container virtualisieren Betriebssystem 


```

### Was ist ein Dockerfile


### What is it ? 
 * Textdatei, die Linux - Kommandos enthält
   * die man auch auf der Kommandozeile ausführen könnte 
   * Diese erledigen alle Aufgaben, die nötig sind, um ein Image zusammenzustellen
   * mit docker build wird dieses image erstellt 
   
### Example 

```
## syntax=docker/dockerfile:1
FROM ubuntu:18.04
COPY . /app
RUN make /app
CMD python /app/app.py
```

## Docker-Installation

### BEST for Ubuntu : Install Docker from Docker Repo


### Walkthrough 

```
sudo apt-get update
sudo apt-get install -y \
    ca-certificates \
    curl \
    gnupg \
    lsb-release

sudo mkdir -p /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg

echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

sudo apt-get update
sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-compose-plugin
```

### Läuft der Dienst (dockerd) 

```
systemctl status docker 
```

### Docker als normaler Benutzer 

```
## Wenn dein unpriviligierter Benutzer kurs heisst
sudo su -
usermod -aG docker 11trainingdo 
exit
```

```
## ich wechsele nochmal in den Benutzer kurs
su - 11trainingdo
## jetzt darf kein Fehler kommen 
docker images 
```

### docker-compose ? 

```
## herausfinden, ob docker compose installieren 
docker compose version 
```

## Docker-Praxis

### Docker run mit nginx


### Beispiel (binden an ein terminal), detached

```
docker run -d --name my_nginx nginx:1.23
docker container ls
## wenn er nicht läuft dann sehen wir ihn nur mit
docker container ls -a
## oder 
docker ps
docker ps -a

## Ip-Adresse rausfinden
docker inspect my_nginx
docker inspect my_nginx | grep -i -A 20 networksettings
## ip ist: 172.17.0.2
## Webseite von nginx anzeigen 
curl http://172.17.0.2
```

### Erkundung 

```
docker images
```

```
## falls nicht root
sudo su -
```

```
## wo sind die overlays
cd /var/lib/docker/rootfs/overlayfs 
## now find out
ls -la
exit
```


```
## in den Container reinwechsel
## interactive 
docker exec -it my_nginx bash
```

```
## Falls wir Prozesse anschauen wollen mit tool ps
## im container
apt update
apt install -y procps
ps aux  | grep nginx
exit
```

```
## auf dem Host-System
ps aux | grep nginx
```

```
## oder wir führen nur ein Kommando aus
docker exec my_nginx cat /etc/os-release
```

### Die wichtigsten Befehle


```
## docker hub durchsuchen
docker search hello-world

docker run <image>
## z.b. // Zieht das image aus docker hub 
## hub.docker.com 
docker run hello-world

## images die lokal vorhanden sind.
docker images 

## container (laufende) 
docker container ls
docker ps 
## container (vorhanden, aber beendet)
docker container ls -a 
docker ps -a 

## z.b hilfe für docker run 
docker help run 

## Informationen zu Docker
## z.B. Was liegt wo ? 
docker info 


 


```

### Aufräumen - container und images löschen


### Alle nicht verwendeten container und images löschen 

```
## Alle container, die nicht laufen löschen 
docker container prune 

## Alle images, die nicht an eine container gebunden sind, löschen 
docker image prune 

## Alle nicht benötigten Daten löschen
docker system prune 
```

### Logs des Host-Systems zu den Containern auslesen


```
## e steht für ende // letzte Einträge des Logs 
journalctl -eu docker 

```

### Logs anschauen - docker logs - mit Beispiel nginx


### Allgemein 
```
## Erstmal nginx starten und container-id wird ausgegeben 
docker run -d nginx:1.22.1
a234
docker logs a234 # a234 sind die ersten 4 Ziffern der Container ID 
```

### Laufende Log-Ausgabe 

```
docker logs -f a234 
## Abbrechen CTRL + c 
```

### Nginx mit portfreigabe laufen lassen


```
docker run --name test-nginx -d -p 8080:80 nginx
```

```
## auf host machine ip des hosts ausfindig machen
ip a  show eth0 

docker container ls
sudo lsof -i
cat /etc/services | grep 8080
curl http://localhost:8080
docker container ls
## wenn der container gestoppt wird, keine ausgabe mehr, weil kein webserver
docker stop test-nginx 
curl http://localhost:8080


```


```
docker start test-nginx 
curl <ip-der-maschine>:8080
```

## Example with Dockerfile

### Ubuntu mit ping


### Image erstellen 

```
cd
mkdir myubuntu 
cd myubuntu/
```

```
nano Dockerfile
```

```
FROM ubuntu:24.04
RUN apt-get update; apt-get install -y inetutils-ping
## CMD ["/bin/bash"]
```

```
docker build -t fullubuntu:1.0 .
docker images 
```

```
## Variante 2
## nano Dockerfile
FROM ubuntu:24.04
RUN apt-get update && \
    apt-get install -y inetutils-ping && \
    rm -rf /var/lib/apt/lists/*
## CMD ["/bin/bash"]
```

```
docker build -t myubuntu:1.0 .
docker images
```

### Image (ping) testen  (mit image fullubuntu:1.0)

```
## -t wird benötigt, damit bash WEITER im Hintergrund im läuft.
## auch mit -d (ohne -t) wird die bash ausgeführt, aber "das Terminal" dann direkt beendet 
## -> container läuft dann nicht mehr 
docker run -d -t --name container-ubuntu fullubuntu:1.0
docker container ls

## docker inspect to find out ip of other container 
## 172.17.0.3 
docker inspect container-ubuntu | grep -i ipaddress
```

```
## Zweiten Container starten um 1. anzupingen 
docker run -d -t --name container-ubuntu2 fullubuntu:1.0 

## Ersten Container -> 2. anpingen 
docker exec -it container-ubuntu2 bash 
## Jeder container hat eine eigene IP 
ping 172.17.0.3

 
```


### Image (ping) testen  (mit image myubuntu:1.0)

```
## -t wird benötigt, damit bash WEITER im Hintergrund im läuft.
## auch mit -d (ohne -t) wird die bash ausgeführt, aber "das Terminal" dann direkt beendet 
## -> container läuft dann nicht mehr 
docker run -d -t --name container-ubuntu myubuntu:1.0
docker container ls

## docker inspect to find out ip of other container 
## 172.17.0.3 
docker inspect container-ubuntu | grep -i ipaddress
```

```
## Zweiten Container starten um 1. anzupingen 
docker run -d -t --name container-ubuntu2 myubuntu:1.0 

## Ersten Container -> 2. anpingen 
docker exec -it container-ubuntu2 bash 
## Jeder container hat eine eigene IP 
ping 172.17.0.3

 
```


### Slim multistage-build


### Why ? 

 * Ziel: Wir wollen ein möglichst kleine Image als Endprodukt haben
 * Um das zu erreichen, können mit einem speziellen Image, was alles bauen das image bauen
   * Dann, nehmen wir aber nur den Teil, den wir brauchen aus diesem Schritt 1: in unserem Fall /app/app.jar 
   * Und verwenden es mit einem wesentlichen kleineren Image
 * Ergebnis: Das fertige image ist wesentlich kleiner 

### Overview

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/c6ee24f7-3669-4410-bfe9-4e2d08cf8ac7)

### Step 1:

```
## Clone repo 
cd 
git clone https://github.com/jmetzger/multi-stage-example
cd multi-stage-example 
```

```
## Bauen und vor Target stoppen 
docker build . -t multi-stage-example:v1 --target=builder # - Build image using a specific stage

## Bauen
docker build . -t multi-stage-binary:v1
```

### Step 2: Run only binary-version 

```
## run 
docker run --name app -d -t multi-stage-binary:v1 sh  
docker exec -it app sh 
```

```
cd /app
ls -la
```

## Docker Security 

### Docker Security


### Generic 

  * Kann ich dem Image vertrauen (nur Images verwenden, denen ich vertrauen kann)
    * Im Zweifel eigene Images oder nur images von Docker Official Image / ~Verified Publisher~ (Suche auf Docker Hub)
  * Container möglichst nicht als Root laufen lassen (bzw. nicht solche Images verwenden)
  * Das nur das drinnen ist, was wirklich gebraucht wird (Produktion)
    * Im Idealfall sogar nur das Executable (siehe auch hashicorp/http-echo -> kein sh, keine bash)
  * Alle container einer applikation in einem eigenen Netzwerk  
  * Images scannen inkl. security scans. 

### Images die nicht als root laufen 

  * ~bitnami~ 
  * nginx unprivileged

```
## Variante 1:
Erkennbar durch USER - Eintrag in Dockerfile
## oder
docker compose exec database id
docker exec <container> id 
```

```
## https://hub.docker.com/r/bitnami/mariadb
## https://github.com/bitnami/containers/blob/main/bitnami/mariadb/11.0/debian-11/Dockerfile
USER 1001 
```


### Run container under specific user: 

```
## user with id 40000 does not need to exist in container 
docker run -it -u 40000 alpine 

## user kurs needs to exist in container (/etc/passwd) 
docker run -it -u kurs alpine 

```

### Default capabilities 

  * Set everytime a new container is started as default 
  * https://github.com/moby/moby/blob/master/profiles/seccomp/default.json


### Run container with less capabilities 

```
cd
mkdir captest
cd captest 
```

```
nano docker-compose.yml 
```

```
services: 
  nginx:
    image: nginx 
    cap_drop:
      - CHOWN
```

```
docker compose up -d
## start and exits 
docker compose ps 
## 
docker exec -it captest_nginx_1 bash 
##/ touch /tmp/foo; chown 10000 /tmp/foo  

## what happened -> wants to do chown, but it is not allowed 
docker logs captest_nginx_1 

```

```
docker compose down 
```


### Reference:

  * https://cheatsheetseries.owasp.org/cheatsheets/Docker_Security_Cheat_Sheet.html
  * https://www.redhat.com/en/blog/secure-your-containers-one-weird-trick
  * man capabilities

### Scanning docker image with docker scan/snyx(Deprecated)


### ACHTUNG_ Deprecated - USE Docker Scout instead (only Docker Desktop ?) 

### Prerequisites 

```
## install docker plugin in some cases
## Ubuntu
apt install docker-scan-plugin 
```

```
You need to be logged in on docker hub with docker login 
(with your account credentials)
```


### Example 

```
## Snyk (docker scan) 
docker help scan
docker scan --json --accept-license dockertrainereu/jm-hello-docker  > result.json
```

## Docker Compose

### Ist docker compose installiert?


### docker compose direkt als plugin für docker (aktuell die beste Wahl)

```
## Installiert man docker in der neuesten 20.10.21 
## existiert docker als plugin und wird anders aufgerufen
## Je nach distribution als Zusatzplugin von docker 
docker compose version 
```

### Ist docker-compose installiert (alte Version, nicht in docker integriert) 

```
## besser. mehr infos
docker-compose version 
docker-compose --version 

```


### Example with Wordpress / MySQL



### Schritt 1:
```bash
clear
cd
mkdir wp
cd wp
nano docker-compose.yml
```

### Schritt 2:

```yaml
services:
  database:
    image: mysql:5.7
    volumes:
      - database_data:/var/lib/mysql
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: mypassword
      MYSQL_DATABASE: wordpress
      MYSQL_USER: wordpress
      MYSQL_PASSWORD: wordpress

  wordpress:
    image: wordpress:latest
    depends_on:
      - database
    ports:
      - 8080:80
    restart: always
    environment:
      WORDPRESS_DB_HOST: database:3306
      WORDPRESS_DB_USER: wordpress
      WORDPRESS_DB_PASSWORD: wordpress
    volumes:
      - wordpress_plugins:/var/www/html/wp-content/plugins
      - wordpress_themes:/var/www/html/wp-content/themes
      - wordpress_uploads:/var/www/html/wp-content/uploads

volumes:
  database_data:
  wordpress_plugins:
  wordpress_themes:
  wordpress_uploads:


```

### Schritt 3:

```
docker compose up -d
## show all containers
docker ps
## show only containers from docker compose project
docker compose ps
```

### Schritt 3.5:

```
docker compose exec -it wordpress bash
```

```
apt update
apt install -y iputils-ping
ping -c4 database
exit
```

### Schritt 4: Alles wieder beenden 

```
## Achtung, damit werden auch die Container gelöscht 
docker compose down
## nur stoppen
docker compose stop 
```

### Example with Ubuntu and Dockerfile


### Schritt 1:

```
cd
mkdir bautest
cd bautest 
```

### Schritt 2:

```
## nano docker-compose.yml
services:
  myubuntu:
    build: ./myubuntu
    restart: always
```

### Schritt 3:

```
mkdir myubuntu 
cd myubuntu 
```

```
nano hello.sh
```

```
##!/bin/bash
let i=0

while true
do
  let i=i+1
  echo $i:hello-docker
  sleep 5
done

```

```
nano Dockerfile
```

```
FROM ubuntu:24.04
RUN apt-get update && apt-get install -y inetutils-ping
COPY hello.sh .
RUN chmod u+x hello.sh
CMD ["/hello.sh"]

```

### Schritt 4: 


```
cd ../
## wichtig, im docker-compose - Ordner seiend 
##pwd 
##~/bautest
docker compose up -d 
## wird image gebaut und container gestartet 

## Bei Veränderung vom Dockerfile, muss man den Parameter --build mitangeben 
docker compose up -d --build 
```

### Schritt 5: Logs anzeigen

```
docker logs bautest-myubuntu-1
docker compose logs 

```

### Logs in docker - compose


```
##Im Ordner des Projektes
##z.B wp 
cd ~/wp
docker compose logs
## jetzt werden alle logs aller services angezeigt 
```

### docker compose Reference

  * https://docs.docker.com/compose/compose-file/compose-file-v3/

## Docker - compose (Testprojekte)

### Testprojekt mit api und mongodb


### Was macht es ?

  * Das Projekt wird direkt gebaut
  * Es startet eine mongodb.
  * Daten werden Über api calls geschrieben/gelesen gelöscht

### Wie verwende ich es ?

#### Schritt 1: Aufsetzen 

```bash 
## Auf dem Docker - server direkt klonen und starten
cd
mkdir -p projects
cd projects
```

```bash 
git clone https://github.com/jmetzger/multiple-containers-in-docker.md mcid
cd mcid
docker compose up -d 
```

#### Schritt 2: [Optional] Datenbankverbindung aufbauen 

```
## Z.B. in visual studio code
## Extensions MongoDB installieren 
```


#### Schritt 3: Rest API-Calls absetzen

   * Diese finden sich bspw. hier: https://github.com/jmetzger/multiple-containers-in-docker/blob/main/rest.http

```
## Hierzu kann bspw. der REST-Client in Visual Studio Code verwendet werden 
```

## Microservices - Daten

### Überblick shared database / database-per-service


### Grundlegendes 

  * Grundlegende Entscheidung, ob
    * Database per Service
    * oder: Shared Database
  * Kann auch für einzelne Services unterschiedlich ausfallen

### Database per Service 

#### Prämisse 


  * Ein anderer Service kann nur über die API zugreifen.
  * Synchronisierung kann auch über andere Weg als synchron erfolgen (z.B. Messaging -> Saga) 

##### Umsetzung: 

  * Private-tables-per-service – each service owns a set of tables that must only be accessed by that service
  * Schema-per-service – each service has a database schema that’s private to that service
  * Database-server-per-service – each service has it’s own database server.

#### Vorteile

  * Das sichert die größe Unabhängigkeit
  * D.h. ein unabhängiges Deployment ist problemlos möglich

#### Nachteile 

  * Transaktionen funktionieren auf DB-Ebene nicht mehr.
  * JOINS sind schwierig umzusetzen.

### Reference:

  * https://microservices.io/patterns/data/database-per-service.html

### Shared Database 

#### Vorteile 

  * Joins sind einfach möglich
  * Transaktionen funktionieren

#### Nachteile 

  * Single Point of Failure (ausser natürlich Cluster)
  * Performance Engpässe (kann auch durch gute Optimierung behoben werden)

```
Development time coupling - a developer working on, for example, the OrderService will need to coordinate schema changes with the developers of other services that access the same tables. This coupling and additional coordination will slow down development.
```

#### Wie ?

  * Services teilen sich eine Datenbank

#### Ref:

  * https://microservices.io/patterns/data/shared-database.html

  

### Umgang mit Joins bei database-per-service


### 1 Pattern: api composition 

#### Nachteile:

  * Bei grossen Datenmengen, kann das sehr speicher und zeitintensiv sein

#### Schaubild 

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/09f1d1b4-bcc4-423a-a82c-9b46b291b49e)

#### Generell 

  * Oftmals wird dafür ein API-Gateway verwendet 

#### Ref:

 * https://microservices.io/patterns/data/api-composition.html

### 2 Pattern: CQRS (Command Query Responsibility Segregation)

#### Wie ? 

  * Datenbank erzeugen, die nur eine Leseansicht hat.
  * Synchrnónisierung erfolgt über Subscribition zu Domain Events

#### Schaubild 

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/eb670d23-186f-4bf6-8d6e-2d5970a4ca1c)

#### Vorteile 

  * Unterstützt mehrere denormalisierte Views, die performant und skalierbar sind
  * Abfragen sind einfache

#### Wann ? 

  * Notwendig bei Joins, wenn wir das Event Sourcing - Pattern verwenden 

#### Nachteile 

  * Eventuell doppelter Code
  * Lag bei den Views / NUR Eventuell Konsistente Views (keine Sicherheit)

#### Ref:

  * https://microservices.io/patterns/data/cqrs.html

### Umgang mit Transaktionen bei database-per-service (SAGA)


### Problem

  * Wenn wir auf das Database-per-Service-Pattern wechseln, koennen wir keine klassischen Datenbanktransaktionen mehr verwenden

### Beispiel-Problem

  * Das Database-per-Service-Pattern ist im Einsatz

```
x Ein Online-Shop
x Kunden haben ein Kreditlimit
x Die Anwendung muss sicherstellen, dass eine neue Bestellung das Kreditlimit des Kunden nicht ueberschreitet
x Bestellungen und Kunden liegen in verschiedenen Datenbanken, die von verschiedenen Services verwaltet werden
x Deshalb: Die Anwendung kann keine einfache lokale ACID-Transaktion verwenden
```

### Schaubild (Wie funktioniert es?)

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/f4615f49-5937-476e-bff7-d32e7de870c9)

### Saga Execution Coordinator (SEC) als zentrale Komponente

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/a33eb0a1-0e04-48a7-983c-9b6741202afe)

  * Enthaelt ein Saga-Log, das die Abfolge der Ereignisse einer verteilten Transaktion aufzeichnet
  * BEI FEHLER: Die SEC-Komponente prueft das Saga-Log, um die betroffenen Komponenten und die Reihenfolge der Kompensationstransaktionen zu ermitteln
  * Sie kann erkennen, welche Transaktionen erfolgreich zurueckgerollt wurden, welche noch ausstehen, und entsprechende Massnahmen einleiten

### Implementierung als Saga-Choreography-Pattern

#### Wann?

  * Greenfield (Entwicklung von Grund auf neu)

#### Wie?

  * Jeder Microservice, der Teil der Transaktion ist, veroeffentlicht ein Event, das vom naechsten Microservice verarbeitet wird

#### Schaubild (Erfolgsfall)

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/9261961c-41f7-4d96-b260-c64f332b6d14)

#### Schaubild (Fehlerfall)

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/0118fe17-6e95-4281-b305-1e33c868062c)

### Implementierung als Saga-Orchestration-Pattern

#### Wann?

  * Brownfield (es existieren bereits Microservices)

#### Wie?

  * Ein Orchestrator uebernimmt die Steuerung des gesamten Transaktionsprozesses

### Kompensationstransaktionen

Eine Kompensationstransaktion macht den fachlichen Effekt eines bereits committeten
Schritts rueckgaengig — weil ein `ROLLBACK` ueber Service-Grenzen hinweg nicht moeglich ist.

#### Beispiel: Bestellung schlaegt in Schritt 3 fehl

```
Schritt 1: Order-Service legt Bestellung an        → in Order-DB committed ✓
Schritt 2: Customer-Service reduziert Kreditlimit  → in Customer-DB committed ✓
Schritt 3: Payment-Service bucht Zahlung           → FEHLER ✗
```

Da Schritt 1 und 2 bereits in ihren jeweiligen Datenbanken committed sind,
kann kein technisches `ROLLBACK` mehr helfen. Der Saga Execution Coordinator
startet stattdessen Kompensationstransaktionen in umgekehrter Reihenfolge:

```
Kompensation zu Schritt 2: Customer-Service stellt Kreditlimit wieder her
Kompensation zu Schritt 1: Order-Service storniert die Bestellung
```

#### Wichtige Eigenschaften einer Kompensationstransaktion

- **Kein technisches Rollback** — sie ist eine neue, eigenstaendige Datenbankoperation
- **Idempotent** — sie muss mehrfach ausfuehrbar sein ohne zusaetzlichen Schaden
  (der SEC kann sie bei Fehler erneut aufrufen)
- **Kann selbst fehlschlagen** — der SEC muss auch das abfangen und wiederholen
- **Kein exakter Rueckgaengig-Effekt** — zwischen Original und Kompensation koennen
  andere Transaktionen gelaufen sein (z.B. hat der Kunde inzwischen etwas anderes bestellt)

> **Faustregel:** Kompensationstransaktionen implementieren "Business Undo",
> nicht "Technical Rollback". Der Zustand wird fachlich korrigiert,
> nicht technisch zurueckgesetzt.

### Produkte

  * Camunda (Framework)
  * Apache Camel

### Referenzen

  * https://www.baeldung.com/cs/saga-pattern-microservices#introduction-to-saga
  * https://microservices.io/patterns/data/saga.html

### Uebung: SAGA-Pattern mit Temporal (Docker Compose, Java)


**Dauer:** ca. 90-120 Minuten
**Level:** Fortgeschritten
**Stack:** Java 17, Maven, Temporal, Docker Compose

---

### Lernziel

Du implementierst eine **Reisebuchungs-Saga** mit drei Schritten (Hotel, Flug, Zahlung).
Schlaegt ein Schritt fehl, werden die bereits erfolgreichen Schritte ueber
**Kompensationen** in umgekehrter Reihenfolge zurueckgenommen.

Nach der Uebung kannst du:

- Temporal als Workflow-Engine per Docker Compose betreiben
- Workflow und Activities trennen
- den `io.temporal.workflow.Saga`-Helper fuer Kompensationen nutzen
- Erfolgs- und Fehlerfall in der Temporal Web-UI beobachten

---

### Szenario

```
T1  bookHotel       --> C1  cancelHotel
T2  bookFlight      --> C2  cancelFlight
T3  chargePayment   --> (Pivot, keine Kompensation)
```

Schlaegt `chargePayment` fehl, laufen `cancelFlight` (C2) und `cancelHotel` (C1)
in umgekehrter Reihenfolge.

---

### Vorbereitung

```bash
git clone https://github.com/jmetzger/training-microservices-docker-kubernetes
cd training-microservices-docker-kubernetes/microservices/uebung-saga-temporal
```

---

### Schritt 1: Infrastruktur starten

```bash
docker compose up -d postgresql temporal temporal-ui
```

Warte bis Temporal healthy ist (ca. 30 Sekunden):

```bash
docker compose ps
## temporal muss Status "healthy" zeigen
```

Web-UI erreichbar unter: http://localhost:8233

---

### Schritt 2: Code-Struktur verstehen

```
src/main/java/de/t3isp/saga/
├── BookingActivities.java       # Activity-Interface mit 5 Methoden
├── BookingActivitiesImpl.java   # Implementierung mit Fehler bei amount > 1000
├── BookingWorkflow.java         # Workflow-Interface
├── BookingWorkflowImpl.java     # Saga-Logik mit Kompensationen
├── SagaWorker.java              # Registriert Workflow + Activities
├── SagaStarter.java             # Loest zwei Workflows aus (Erfolg + Fehler)
└── Main.java                    # Einstiegspunkt (worker | starter)
```

---

### Wie funktioniert das Zusammenspiel?

#### Worker und Starter — zwei Rollen, ein Image

Beide Container werden aus demselben Dockerfile gebaut, uebernehmen aber verschiedene Aufgaben:

**`saga-worker`** — der Ausfuehrer (laeuft dauerhaft)
- Registriert `BookingWorkflowImpl` und `BookingActivitiesImpl` bei Temporal
- Wartet auf Aufgaben von der Task Queue `booking-saga`
- `restart: on-failure` — startet neu falls Temporal kurz nicht erreichbar ist

**`saga-starter`** — der Ausloser (laeuft einmalig)
- Loest zwei Workflows bei Temporal aus: 500 EUR (Erfolg) und 2000 EUR (Fehler)
- Beendet sich danach — `restart: "no"`
- Er fuehrt die Workflows nicht selbst aus, er beauftragt nur Temporal

```
saga-starter  -->  Temporal Server  -->  saga-worker
  (ausloesen)      (koordinieren,         (ausfuehren,
                    persistieren)          Activities laufen hier)
```

Temporal haelt den Workflow-State persistent. Faellt der Worker aus und kommt zurueck,
weiss Temporal genau wo der Workflow aufgehoert hat — und der Worker macht einfach weiter.

#### Wie wird der Workflow definiert?

Der Kern der Uebung liegt in `BookingWorkflowImpl.java`:

```java
public String bookTrip(String bookingId, double amount) {
    Saga saga = new Saga(
        new Saga.Options.Builder()
            .setParallelCompensation(false)  // C2 vor C1, nicht gleichzeitig
            .build());

    try {
        activities.bookHotel(bookingId);
        saga.addCompensation(activities::cancelHotel, bookingId);  // C1

        activities.bookFlight(bookingId);
        saga.addCompensation(activities::cancelFlight, bookingId); // C2

        activities.chargePayment(bookingId, amount);               // Pivot (kein Cancel)

        return "Buchung " + bookingId + " erfolgreich";

    } catch (Exception e) {
        saga.compensate();        // ruft C2, dann C1 — umgekehrte Reihenfolge
        throw Workflow.wrap(e);   // Fehler an Temporal weiterleiten
    }
}
```

**Warum steht `addCompensation` immer NACH der jeweiligen Activity?**

Schlaegt `bookHotel` selbst fehl, gibt es nichts zu stornieren.
Stuende die Registrierung davor, wuerde `cancelHotel` fuer eine Buchung aufgerufen,
die nie stattgefunden hat. Durch die Reihenfolge "erst buchen, dann registrieren"
kompensiert die Saga nur, was tatsaechlich erfolgreich war.

#### Was steckt hinter bookTrip?

Das Interface `BookingWorkflow.java` legt nur die Signatur fest:

```java
@WorkflowInterface
public interface BookingWorkflow {
    @WorkflowMethod
    String bookTrip(String bookingId, double amount);
}
```

Den tatsaechlichen Ablauf definiert `BookingWorkflowImpl.java`:

```java
public String bookTrip(String bookingId, double amount) {
    Saga saga = new Saga(...);
    try {
        activities.bookHotel(bookingId);            // T1
        saga.addCompensation(...cancelHotel);        // C1 registrieren

        activities.bookFlight(bookingId);            // T2
        saga.addCompensation(...cancelFlight);        // C2 registrieren

        activities.chargePayment(bookingId, amount); // T3 — Pivot, kein Cancel

        return "erfolgreich";
    } catch (Exception e) {
        saga.compensate();   // C2, dann C1 — umgekehrte Reihenfolge
        throw Workflow.wrap(e);
    }
}
```

Die Verbindung zwischen Interface und Implementierung stellt der **Worker** her —
er registriert beides beim Start bei Temporal:

```java
worker.registerWorkflowImplementationTypes(BookingWorkflowImpl.class);
```

Wenn der Starter `workflow.bookTrip()` aufruft, weiss Temporal:
"Diese Methode wird von `BookingWorkflowImpl.bookTrip()` ausgefuehrt —
durch den Worker auf der Queue `booking-saga`."

```
BookingWorkflow          BookingWorkflowImpl       BookingActivitiesImpl
(Interface/Vertrag)  --> (Ablauf/Reihenfolge)  --> (eigentliche Arbeit)
```

`BookingActivitiesImpl` enthaelt die eigentliche Arbeit — in der Uebung bewusst
einfach gehalten (nur Logging + gezielter Fehler):

```java
@Override
public String bookHotel(String bookingId) {
    log.info("[{}] ✓ Hotel gebucht", bookingId);
    return "hotel-" + bookingId;
}

@Override
public void cancelHotel(String bookingId) {
    log.info("[{}] ↩ Hotel STORNIERT (Kompensation C1)", bookingId);
}

@Override
public void chargePayment(String bookingId, double amount) {
    if (amount > 1000) {
        log.warn("[{}] ✗ Zahlung abgelehnt: {} EUR > Limit", bookingId, amount);
        throw new RuntimeException("Zahlung abgelehnt: " + amount + " EUR");
    }
    log.info("[{}] ✓ Zahlung von {} EUR erfolgreich", bookingId, amount);
}
```

In einem echten System wuerde hier stehen:
- `bookHotel` → HTTP-Call an ein Hotel-API
- `cancelHotel` → HTTP-Call mit der Buchungs-ID um zu stornieren
- `chargePayment` → Aufruf an Stripe, PayPal o.ae.

---

#### Wie loest der Starter einen Workflow aus?

```java
// 1. Verbindung zu Temporal aufbauen (gRPC, Port 7233)
WorkflowClient client = WorkflowClient.newInstance(
    WorkflowServiceStubs.newServiceStubs(
        WorkflowServiceStubsOptions.newBuilder()
            .setTarget("temporal:7233")
            .build()));

// 2. Workflow-Stub anlegen — nur lokales Proxy-Objekt, noch kein Netzwerkaufruf
BookingWorkflow workflow = client.newWorkflowStub(
    BookingWorkflow.class,
    WorkflowOptions.newBuilder()
        .setTaskQueue("booking-saga")   // welche Queue?
        .setWorkflowId("booking-001")   // eindeutige ID (fuer Deduplizierung)
        .build());

// 3. Workflow ausloesen — blockiert bis Workflow abgeschlossen ist
workflow.bookTrip("booking-001", 500.0);
```

Was dabei passiert:

```
Starter                    Temporal Server              Worker
   |                            |                          |
   |-- newWorkflowStub() -----> |                          |
   |   (nur lokal, kein Call)   |                          |
   |                            |                          |
   |-- bookTrip() ------------> |                          |
   |   (gRPC zu :7233)          | persistiert in PostgreSQL|
   |                            | <-- polling ------------ |
   |   (wartet...)              | -- Aufgabe ausgeben ----> |
   |                            |    Activities ausfuehren  |
   |                            | <-- Ergebnis ------------ |
   | <-- return / Exception --- |                          |
```

**Drei wichtige Punkte:**

- `newWorkflowStub()` erzeugt nur ein lokales Proxy-Objekt — noch kein Netzwerkaufruf.
  Einfach gesagt: es ist wie das Schreiben einer Adresse auf einen Briefumschlag —
  du weisst schon wohin der Brief soll, aber abgeschickt hast du noch nichts.
  Erst `bookTrip()` schickt den Brief ab.
- `bookTrip()` sendet den Start-Request an Temporal per gRPC — Temporal persistiert den Auftrag sofort in PostgreSQL, bevor der Worker ihn abarbeitet
- Der Aufruf **blockiert** bis der Workflow fertig ist. Will man nicht warten, gibt es stattdessen `WorkflowClient.start(workflow, ...)` — dann laeuft der Workflow asynchron weiter

---

### Schritt 3: Worker und Starter ausfuehren

**Worker starten** (laeuft dauerhaft im Hintergrund):

```bash
docker compose up -d saga-worker
docker compose logs -f saga-worker
```

**Starter ausfuehren** (in neuem Terminal):

```bash
docker compose run --rm saga-starter starter
```

---

### Erwartete Ausgabe

**Starter:**

```
=== Fall 1: Erfolgsfall (500 EUR) ===
ERGEBNIS: Buchung booking-001 erfolgreich abgeschlossen

=== Fall 2: Fehlerfall (2000 EUR) — Kompensationen werden erwartet ===
ERGEBNIS: Workflow fehlgeschlagen (erwartet) — Kompensationen gelaufen
```

**Worker-Logs (Fall 1 — Erfolg):**

```
[booking-001] ✓ Hotel gebucht
[booking-001] ✓ Flug gebucht
[booking-001] ✓ Zahlung von 500.0 EUR erfolgreich
```

**Worker-Logs (Fall 2 — Fehler mit Kompensation):**

```
[booking-002] ✓ Hotel gebucht
[booking-002] ✓ Flug gebucht
[booking-002] ✗ Zahlung abgelehnt: 2000.0 EUR > Limit 1000 EUR
[booking-002] ↩ Flug STORNIERT (Kompensation C2)   <-- umgekehrte Reihenfolge
[booking-002] ↩ Hotel STORNIERT (Kompensation C1)
```

---

### Schritt 4: Web-UI beobachten

Oeffne http://localhost:8233 und vergleiche die Event-History beider Workflows:

- `booking-success-001` — alle Activities gruener Status, kein Fehler
- `booking-failure-001` — `ChargePayment` rot, danach `CancelFlight` und `CancelHotel` als Kompensation

**Frage:** In welcher Reihenfolge erscheinen die Kompensationen in der Event-History?
Erkennst du, dass C2 vor C1 laeuft?

---

### Kernkonzepte im Code

#### Warum steht `addCompensation` nach der Activity?

```java
activities.bookHotel(bookingId);
saga.addCompensation(activities::cancelHotel, bookingId);  // NACH bookHotel
```

Schlaegt `bookHotel` selbst fehl, gibt es nichts zu stornieren.
Stuende die Registrierung davor, wuerde eine Buchung kompensiert, die nie stattgefunden hat.

#### Wie laufen Kompensationen?

```java
} catch (Exception e) {
    saga.compensate();   // ruft C2, dann C1 — umgekehrte Reihenfolge
    throw Workflow.wrap(e);
}
```

`saga.compensate()` arbeitet die registrierten Kompensationen von hinten nach vorne ab.
Das entspricht dem Prinzip: zuletzt gebucht = zuerst storniert.

#### Retry vs. Kompensation

In `BookingWorkflowImpl` ist `setMaximumAttempts(1)` gesetzt — keine Retries.
In echten Systemen wuerde Temporal die Activity bei transientem Fehler
automatisch wiederholen (konfigurierbar per `RetryOptions`).
Erst wenn alle Retries erschoepft sind, greift die Saga-Kompensation.

---

### Bonus-Aufgaben

#### A) Retry einbauen

Entferne `setMaximumAttempts(1)` aus den `ActivityOptions` und lass `chargePayment`
beim ersten Aufruf fehlschlagen, beim zweiten erfolgreich sein (z.B. statischer Zaehler).
Beobachte: Temporal retryt die Activity — die Kompensation greift NICHT.
Warum ist das korrekt?

#### B) Crash-Recovery

Baue ein `Thread.sleep(15000)` in `bookFlight` ein.
Starte den Worker (`docker compose restart saga-worker`) waehrend `bookFlight` schlaeft.
Beobachte in der Web-UI: der Workflow laeuft genau dort weiter, wo er aufgehoert hat.
Temporal persistiert den State — nicht der Worker.

#### C) Parallele Kompensation

Setze in `BookingWorkflowImpl`:

```java
new Saga.Options.Builder().setParallelCompensation(true).build()
```

Frage: Wann ist parallele Kompensation sinnvoll, wann gefaehrlich?

---

### Aufraeumen

```bash
docker compose down -v
```

---

### Checkliste

- [ ] Infrastruktur gestartet, Web-UI erreichbar (http://localhost:8233)
- [ ] Erfolgsfall: drei Buchungen, keine Kompensation in den Logs
- [ ] Fehlerfall: `chargePayment` schlaegt fehl, `cancelFlight` + `cancelHotel` laufen
- [ ] Kompensationen laufen in umgekehrter Reihenfolge (C2 vor C1)
- [ ] Event-History beider Workflows in der Web-UI verglichen

### Apache Camel (EIP) vs. Temporal — Vergleich und Entscheidungshilfe


### Herkunft

**Apache Camel** implementiert die **Enterprise Integration Patterns (EIP)** —
ein Katalog von Loesungen fuer typische Integrationsprobleme aus dem gleichnamigen Buch
von Gregor Hohpe & Bobby Woolf (2003).

![EIP-Grafik: Routing, Splitter, Translator, Aggregator](/images/eip-grafik.svg)

Die zentralen EIP-Bausteine in Camel:

| Pattern | Was es tut |
|---|---|
| **Content-Based Router** | Nachricht je nach Inhalt an verschiedene Ziele leiten |
| **Splitter** | Eine Nachricht in mehrere Teile aufteilen |
| **Aggregator** | Mehrere Nachrichten zu einer zusammenfassen |
| **Translator** | Format einer Nachricht umwandeln (z.B. XML → JSON) |
| **Dead Letter Channel** | Fehlgeschlagene Nachrichten separat behandeln |

---

### Direkter Vergleich

| | **Apache Camel** | **Temporal** |
|---|---|---|
| **Herkunft** | Enterprise Integration Patterns (EIP) | Workflow-Orchestrierung |
| **Kernfrage** | Wie verbinde und transformiere ich Systeme? | Wie orchestriere ich Geschaeftsprozesse zuverlaessig? |
| **Denkweise** | Nachrichten fliessen durch Routen | Code beschreibt Schritt-fuer-Schritt-Ablaeufe |
| **State** | Zustandslos (Nachrichten sind ephemer) | Zustandsbehaftet (Workflow-State wird persistiert) |
| **Fehlerbehandlung** | Dead Letter Channel, Retry in der Route | Automatische Retries, Saga-Kompensation |
| **Laufzeit** | Millisekunden bis Sekunden | Sekunden bis Monate (Long-Running) |
| **Staerke** | Protokoll-Uebersetzung, Routing, ETL | Business-Prozesse, Sagas, verteilte Transaktionen |
| **DSL** | Java, XML, YAML (deklarativ) | Java, Go, Python, TypeScript (Code) |

---

### Wann welches Tool?

**Apache Camel fuer SAGA nehmen, wenn:**
- Camel bereits fuer Integration im Einsatz ist
- Die Saga kurzlebig ist (Sekunden bis Minuten)
- Kompensationslogik einfach und flach ist
- Kein persistierter State ueber Crashes hinweg benoetigt wird

Camel hat seit Version 2.21 einen eigenen **Saga EIP** eingebaut:

```java
// Saga-Route: Schritte definieren
from("direct:bookTrip")
    .saga()
        .to("direct:bookHotel")
        .to("direct:bookFlight")
        .to("direct:chargePayment")
    .end();

// Hotel buchen — mit Kompensation
from("direct:bookHotel")
    .saga()
    .compensation("direct:cancelHotel")
    .log("Hotel gebucht")
    .to("http://hotel-service/book");

// Kompensation: Hotel stornieren
from("direct:cancelHotel")
    .log("Hotel storniert (Kompensation)")
    .to("http://hotel-service/cancel");
```

Schlaegt ein Schritt fehl, ruft Camel automatisch die registrierten
Kompensations-Routen in umgekehrter Reihenfolge auf — gleiches Prinzip wie bei Temporal,
aber ohne persistierten State. Faellt der Prozess zwischen zwei Schritten aus,
gibt es keine automatische Wiederherstellung.

**Temporal fuer SAGA nehmen, wenn:**
- Die Saga Stunden oder Tage dauern kann
- Crash-Recovery ohne Datenverlust Pflicht ist
- Komplexe oder verschachtelte Kompensationslogik benoetigt wird
- Observability ueber Web-UI wichtig ist


### Event Sourcing


### Nachteile:

  * Um Daten zu bekommen, müssen die verschiedenen Events "geparsed" werden und daraus ein Ergebnis geschrieben werden
  * Das ist zeitraubend -> als Ergänzung kann CQRS (Command Query Responsibility Segregation) verwendet werden (Es werden dann finale Daten in ein VIEW basierte Datenbank geschrieben)
    * Hier ist allerdings wieder das Thema der Eventuell konsistenten Daten (Damit müssen die Applikationen umgehen)
 
### Schaubild 

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/b6679016-962e-48c1-a3c7-7d0cae9cb67f)

### Refs: 

  * https://microservices.io/patterns/data/event-sourcing.html

## Microservice - flightapp - concepts

### Vorgehensweise nach dem SEED-Verfahren


### 7 Steps 

1. Akteure identifzieren 
1. Jobs identifizieren, die durch Akteure getätigt werden müssen
1. Entdecken/Entwickeln von Interaktionsschritte mit Ablaufdiagrammen 
1. High-Level Aktionen und Abfragen basierend auf den zu tätigenden Jobs (2) und den Interaktionsschritten ableiten
1. Jede Abfrage und Aktion als Spezifikation beschreiben, mit einem offenen Standard (wie OpenAPI Spezifikation [OAS] or GraphQL schemata)
1. Feedback zur Api erhalten 
1. Implementieren
   
 ## Reference:

   * https://www.oreilly.com/library/view/microservices-up-and/9781492075448/ch03.html
   * https://rolfsblog.ch/openapi/

### Vorgehensweise nach SEED on Detail


### Schritt 1: 

```
Identifizierung der bounded contexts 

o Flights Management
o Reservations Management 

Am Anfang macht es Sinn microservices eher 
grob/weit (coarse-grained) zu gestalten 
in Ihrer Funktion. 
(von allgemein zu spezifischer ist nachher einfach 
als anders herum) 
```

### Schritt 2: Akteure/Konsumenten mit der SEED - Methode identifizieren

  1. Eine Kunden will den Flug buchen 
  1. Die User-App der Airline (web, mobile, usw.)
  1. Die Web API mit der die App interagiert.(dies nennen manche auch "backend for frontends" oder BFF APIs.)
  1. The flights management microservice: ms-flights
  1. Das Reservierungs Management microservice: ms-reservations 

### Schritt 3: Beispiele von JTBD's (Jobs to be Done) 

```
Fiktiv: Gesammelt von Kunden Interviews und Business Analyse Recherche

1. Wenn ein Kunde mit der UI interagiert, muss die app einen Sitzplan anzeigen, welcher die verfügbaren und die belegten Plätze zeigt, so dass der Kunde einen Sitzplatz auswählen kann. 
2. Wenn ein Kunde eine Buchung finalisiert, muss die web app einen Sitz reservieren für den Kunden (so kann die App verhinden, dass es im Verlauf Konflikten bei den 
Sitzen gibt)
```

### Schritt 4: BFF-API als Vermittler (JTBD)

<img width="746" height="769" alt="image" src="https://github.com/user-attachments/assets/25f56865-f48f-4a51-ad0f-988c5608b039" />

```
(Backend for frontend) 

Empfehlung: Eine BFF-API (pro Frontend), die nur eine ganze schlanke Schicht hat ohne business
Logik Implementierung. 

Sie "orchestriert" nur die microservices 

Es gibt also jobs für die die BFF API microservices braucht.

Die folgende Liste an Job, die mehr technischen JTBD's beschreiben die Bedürfnisse ziwschen der BFF API und den microservices 

1. Wenn die BFF API angefragt wird einen Sitzplan zur Verfügung zu stellen, braucht die API msflights, um einen Plan der Sitze im Flugzeug zu bekommen, so dass die BFF API Verfügbarkeiten abholen kann und das finale Ergebnis erstellen kann.

2. Wenn die BFF API einen Sitzplan rendern soll, braucht die BFF API ms-reservations um eine Liste von bereits reservierten Sitzen zu bekommen, so dass die BFF API diese Daten dem Sitzplatz-Setup hinzufügen kann und ein Sitzplan zurückgibt. 

3. Wenn die BFF API gefragt wird, einen Sitzplatz zu reservieren, braucht die BFF API ms-reservations um die Reservierung auszuführen, so dass die API einen Sitzplatz reservieren kann

======================================
Achtung:
Wir lassen nicht ms-flights -> ms-reservations
aufrufen, um den Sitzplan zusammenzubauen 

Stattdessen lassen wir die BFF API diese 
interaktion tun. 

Direkt microservices-to-microservices call
sollten vermieden 
werden

============================================
```

### Schritt 5: Project Flight-Service: UML (Erstellen eines Ablaufdiagramms) 

  * https://github.com/jmetzger/training-microservices-docker-kubernetes/blob/main/microservices-flightapp/concept/02-uml.md

```
Folgendes kann man hier klar erkennen:

1. flight id muss ich abfragen, weil Kunden oft nicht direkt die Flugnummer eingeben ;o) 

###############################################
Randnotiz 
###############################################

Zwar müssten auch Tasks für die BFF API definiert werden,
lassen wir aber mal jetzt aussen vor. 
```

### Schritt 6: JBTD in actions und queries überführen 

```
Wir machen das für ms-flights und ms-reservations
```

#### A. Flights Microservice 

##### Get flight details 

```
  o Input flight_no,
    departure_local_date_time 
    (ISO8601 format und in der lokalen zeitzone)

  o Response: A unique flight_id identifying a 
    specific flight on a specific date. 
    In der Praxis, wird dieser Endpunkt noch
    mehr relevanten Felder zurückgeben, aber 
    diese sind für unseren Context unrelevant 
    also überspringen wir diese
```

##### Get flight seating (the diagram of seats on a flight)

```
  o Input flight_id
  o Response: Seat Map Object in JSON Format
```

#### B. Reservations Microservice 

##### Query already reserved seats on a flight

```
  o Input: flight_id
  o Response: A list of already-taken seat numbers,
              each seat number in a format like "2A" 
```
##### Reserve a seat on a flight 

```
  o Input: flight_id,customer_id,seat_num
  o Expected outcome: A seat is reserved and unavailable
    to others, or an error fired if the seat was unavailable 
  o Response: Success (200 Success) or failure (403 Forbidden) 
```

### Schritt 7: Ablaufdiagramm erstellen und anzeigen 

   * Schritte hier: [UML](/microservices-flightapp/concept/02-uml.md)

### Schritt 8: OpenAPI Spezifikation 

  * Wir werden das bei der Umsetzung testen. 

### Schritt 9: Implementieren 

### SEED vs. DDD mit EventStorming — Wann nehme ich was?


### Kurzuebersicht

| Kriterium | SEED | DDD + EventStorming |
|---|---|---|
| Ausgangspunkt | Akteure und ihre Jobs (JTBD) | Domaenen-Events |
| Richtung | Top-down: Akteur → Job → API | Bottom-up: Event → Aggregat → Service |
| Fokus | API-Design und Schnittstellen | Domaenenverstaendnis und Schnittfindung |
| Ergebnis | OpenAPI-Spezifikation | Bounded Contexts, Ubiquitous Language |
| Teamformat | Kleines Team, Entwickler-nah | Workshop mit Fachexperten und Devs |
| Wann ideal | Neue Services entwerfen (greenfield) | Monolith verstehen und schneiden |

---

### SEED — Service Experience Envelope Design

**Kernfrage:** *Wer will was tun, und welche API braucht er dafuer?*

SEED geht von Akteuren aus (z.B. Kunde, BFF-API, anderer Microservice) und
beschreibt deren "Jobs to be Done" (JTBD). Daraus werden Actions und Queries
abgeleitet und direkt als OpenAPI-Spec beschrieben.

#### Vorgehensweise (7 Schritte)

1. Akteure identifizieren
2. Jobs der Akteure ermitteln (JTBD)
3. Interaktionsschritte mit Ablaufdiagrammen entwickeln
4. Actions und Queries ableiten
5. Als OpenAPI-Spezifikation beschreiben
6. Feedback zur API einholen
7. Implementieren

#### Staerken von SEED

- Schnell zu einem konkreten API-Vertrag
- Gut geeignet, wenn die Domaene schon klar ist
- Entwickler koennen direkt loslegen (OpenAPI → Code)
- Verhindert "zu viele Features" durch JTBD-Fokus

#### Schwaechen von SEED

- Setzt voraus, dass die Domaene und die Servicegrenzen bekannt sind
- Deckt keine versteckten Geschaeftsprozesse auf
- Weniger geeignet fuer das Aufteilen eines gewachsenen Monolithen

---

### DDD + EventStorming

**Kernfrage:** *Was passiert in der Domaene, und wo liegen die natuерlichen Grenzen?*

EventStorming bringt Fachexperten und Entwickler zusammen, um alle
relevanten **Domaenen-Events** an einer Pinnwand zu sammeln (orange Karten).
Daraus entstehen Aggregate, Bounded Contexts und schliesslich Servicegrenzen.

#### Vorgehensweise (EventStorming grob)

1. Events sammeln (Was passiert im System? z.B. `BestellungAufgegeben`)
2. Events zeitlich sortieren
3. Commands und Akteure hinzufuegen (Was loest den Event aus?)
4. Aggregate identifizieren (Welche Daten gehoeren zusammen?)
5. Bounded Contexts abgrenzen (Wo aendert sich die Sprache/Verantwortung?)
6. Serviceschnitt ableiten

#### Staerken von DDD/EventStorming

- Legt versteckte Komplexitaet und Konflikte offen
- Schafft gemeinsames Verstaendnis zwischen Fachbereich und Technik
- Ideal zum Schneiden eines Monolithen
- Ergebnis sind stabile Servicegrenzen (nicht nur APIs)

#### Schwaechen von DDD/EventStorming

- Zeitaufwaendig (halber bis ganzer Tag fuer komplexe Domaenen)
- Braucht echte Fachexperten im Raum
- Liefert keine fertige API-Spezifikation

---

### Die Kombination: SEED + DDD

In der Praxis ergaenzen sich beide Methoden hervorragend:

```
EventStorming
    └─► Bounded Contexts gefunden
            └─► Pro Service: SEED anwenden
                    └─► OpenAPI-Spezifikation
                            └─► Implementierung
```

**Konkret:**
- EventStorming klaert *welche Services* es geben soll
- SEED klaert *welche API* jeder Service nach aussen anbietet

#### Beispiel: ShopMax

```
EventStorming ergibt:
  - Bounded Context "Bestellungen"
  - Bounded Context "Versand"
  - Bounded Context "Lager"

Danach SEED fuer den Bestellungs-Service:
  Akteur: Frontend-App
  JTBD:   Kunde will Bestellung aufgeben
  Action: POST /orders
  Query:  GET /orders/{id}
  → OpenAPI-Spec erstellen
```

---

### Entscheidungshilfe

```
Ist die Domaene unklar oder ein Monolith vorhanden?
  → JA  → EventStorming zuerst, dann SEED je Service
  → NEIN

Sind die Servicegrenzen bereits bekannt?
  → JA  → SEED direkt anwenden
  → NEIN → EventStorming fuer den unklaren Teil
```

---

### Fazit

| Situation | Empfehlung |
|---|---|
| Neues Projekt, Domaene klar | SEED |
| Monolith schneiden | EventStorming → dann SEED |
| Team-Workshop mit Fachbereich | EventStorming |
| API-Spec fuer bekannten Service | SEED |
| Komplexe, unbekannte Domaene | EventStorming + DDD |

## Microservice - flightapp - reservations 

### Template for microservice with python flask 


### Good idea to start microservices with a fixed template within your team 

#### Why ?

  * Way shorter implementation time for new microservice based on python

#### What do we use it for ?

  * We will use it for the reservations (get, put)

#### Refs:

  * https://github.com/inadarei/ms-python-flask-template/tree/master

### Create microservice - reservations


### Part 1: SEAT RESERVATIONS

#### Block 1: The OpenAPI - Definition 

##### Getting the OpenAPI - Definition we ;o) created

```
## Get if from:
https://raw.githubusercontent.com/jmetzger/ms-reservations/master/docs/api.yml
```

##### Use it to render a beautiful out in swagger, and it is also the docs 

  * https://editor.swagger.io/

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/792bfdd7-4ddb-4f85-84e9-e3732fbc4333)


#### Block 2: Clone ms-reservations and build it. 

```
cd
git clone https://github.com/jmetzger/ms-reservations.git msupandrunning
cd msupandrunning
## sudo apt install -y make
## make
## Alternativ
docker compose up -d 

```

```
docker compose logs 
```

#### Block 3: Open Client on redis-server to test 

##### Start redis-cli within redis-server 

```
## make redis
## Alternative:
docker compose exec ms-reservations-redis redis-cli -a 4n_ins3cure_P4ss
```

##### These are direct calls to redis trough the redis cli

```
echo "in redis client - enter our first seat reservation"
```

```
HSETNX flight:40d1-898d-bf84a266f1b9 12B b4cdf96e-a24a-a09a-87fb1c47567c
```

```
## this means success -> (integer) 1
```

```
HSETNX flight:40d1-898d-bf84a266f1b9 12C e0392920-a24a-b6e3-8b4ebcbe7d5c
```

##### Now retrieve the occupied seats (in redis cli) 

```
HKEYS flight:40d1-898d-bf84a266f1b9
```

##### This is the output of the reserved seats (keys) 

```
1) "12B"
2) "12C"
```

##### Like so you can also! get the passenger-id of a seat (so including both)

```
HGETALL flight:40d1-898d-bf84a266f1b9
```

##### That is the output 

```
1) "12B"
2) "b4cdf96e-a24a-a09a-87fb1c47567c"
3) "12C"
4) "b4cdf96e-a24a-a09a-87fb1c47567c"
```

##### Let's try double-booking (of seat)

```
HSETNX flight:40d1-898d-bf84a266f1b9 12B b4cdf96e-a24a-a09a-87fb1c47567c
```

##### This is how the result looks like 

```
## this means success error -> (integer) 0
```

##### now leave redis-cli again 

```
exit
```

#### Block 4: Test microservice with rest-api call 

```
## only works when project name is: msupandrunning
docker compose ps 
```

```
curl --verbose --header "Content-Type: application/json" --request PUT --data '{"seat_num":"12C","flight_id":"werty", "customer_id": "dfgh"}' http://165.22.18.90:7701/reservations
```

```
curl --verbose --header "Content-Type: application/json" --request PUT --data '{"seat_num":"12D","flight_id":"werty", "customer_id": "dfgh"}' http://165.22.18.90:7701/reservations
```

```
## Try once again
## --verbose also shows the headers 
curl --verbose --header "Content-Type: application/json" --request PUT --data '{"seat_num":"12D","flight_id":"werty", "customer_id": "dfgh"}' http://165.22.18.90:7701/reservations
```

```
## Try to get the data
curl --verbose --header "Content-Type: application/json" http://165.22.18.90:7701/reservations?flight_id=werty
```

### Reference 

  * https://redis.io/docs/latest/commands/hsetnx/

### Upload image microservice - reservations


### Step 1: Upload image to docker hub 

```
## eventually
cd
cd msupandrunning
## show all images build through this docker compose 
docker compose images
```

```
## msupandrunning-ms-reservations     latest  
## to upload it to docker hub, we would need to tag it
## one image can have multiple tags
```

#### Das image wird getagged 

  * Damit klar ist, wo es hingeschickt werden soll und zwar welches images 

```
## Bitte <namenskuerzel> ersetzen, z.B. jm  
docker tag msupandrunning-ms-reservations dockertrainereu/reservations-<namenskuerzel>:v14
## now enter dockertrainereu + password-you-will-get-from-your-trainer ;O)
docker login
```

```

## push the image to the server
## <namenskuerzel> ersetzen durch z.B. jm 
docker push dockertrainereu/reservations-<namenskuerzel>:v14
```

### Build image reservations with gitlab ci/cd


### Step 1: Clone Repo from github locally

```
cd
git clone https://github.com/jmetzger/ms-reservations.git
cd ms-reservations
```

### Step 1.5: Set identity 

```
## Ist sie gesetzt
git config user.name
git config user.email 
git config --list 

## Wenn nein 
git config --global user.name "Max Mustermann"
git config --global user.email "tn1@t3company.de"
```


### Step 1.7 gitlab repository erstellen 

  * Darauf achten, dass keine README.md angelegt, d.h. Haken rausnehmen für README.md
  * Und public haken setzen 



### Step 2: Change origin (target where push data) and push 

```
## of your newly created repo on gitlab 
git remote -v
git remote set-url origin https://gitlab.com/training.tn1/ms-jochen.git
## find out current branch and use it in next step
## marked with a *
git branch
## enter username + password 
git push -u origin master 
```



### Step 3a: (gitlab) build image and push to gitlab registry 

```
## modify .gitlab-ci.yml with pipeline editor as follows
stages:          # List of stages for jobs, and their order of execution
  - build

build-image:       # This job runs in the build stage, which runs first.
  stage: build
  image: docker:20.10.10
  services:
     - docker:20.10.10-dind
  script:
    - echo "user:"$CI_REGISTRY_USER
    - echo "pass:"$CI_REGISTRY_PASSWORD
    - echo "registry:"$CI_REGISTRY
    - echo $CI_REGISTRY_PASSWORD | docker login -u $CI_REGISTRY_USER $CI_REGISTRY --password-stdin
    - docker build -t $CI_REGISTRY_IMAGE .
    - docker images
    - docker push $CI_REGISTRY_IMAGE
    - echo "BUILD for $CI_REGISTRY_IMAGE done"
```

```
## this will run, when you commit
```

### Step 3b: Build image, when setting a tag and upload to docker hub 

#### 3a) Ministep 1 - add variables for docker in SETTINGS -> CI/CD -> Variables 

```
## add
DOCKER_USER
DOCKER_PASS
DOCKER_PROJECT # z.B, reservations-jm
in Settings -> CI/CD -> Variables (in your repo)
```

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/0b245254-8c72-485b-9160-8826ebcaffde)

#### 3b) Ministep 2
```
stages:
  - build

build-image:
  stage: build
  image:
    name: gcr.io/kaniko-project/executor:debug
    entrypoint: [""]
  script:
    - echo "user:"$DOCKER_USER
    - echo "project:"$DOCKER_PROJECT
    - mkdir -p /kaniko/.docker
    - echo "{\"auths\":{\"https://index.docker.io/v1/\":{\"auth\":\"$(printf "%s:%s" "${DOCKER_USER}" "${DOCKER_PASS}" | base64 | tr -d '\n')\"}}}" > /kaniko/.docker/config.json
    - /kaniko/executor
      --context "${CI_PROJECT_DIR}"
      --dockerfile "${CI_PROJECT_DIR}/Dockerfile"
      --destination "${DOCKER_USER}/${DOCKER_PROJECT}:${CI_COMMIT_TAG}"
    - echo "BUILD for "${DOCKER_USER}/${DOCKER_PROJECT}:${CI_COMMIT_TAG}" done"
  rules:
    - if: $CI_COMMIT_TAG
```

```
## Jetzt zum Testen (Triggern der Pipeline) 
## neuen Tag setzen 
CODE -> Tags -> New Tag -> (z.B.) v3
## https://gitlab.com/training.tn1/ms-jochen/-/tags/new
```

## Microservice - flightapp - flights

### Template for microservice flights with node bootstrap


### Good idea to start microservices with a fixed template within your team 

#### Why ?

  * Way shorter implementation time for new microservice based on node js 
#### What do we use it for ? 

  * Flights 


### Build flight app


### Step 1: Use bootstrap github template 

  * https://github.com/jmetzger/nodebootstrap-microservice

```
## as unpriviliged user / root
cd
```

```
## either usetemplate
## but we will just clone it locally
git clone https://github.com/jmetzger/nodebootstrap-microservice ms-flights
```

### Step 2: Create documentation 

```
cd ms-flights/docs
```

```
## Replace content in api.yml
## with our own definition from
https://raw.githubusercontent.com/jmetzger/ms-flights/master/docs/api.yml
```

```
## now render the docs and open 3939 port with container running
## make start
docker run -d --rm --name ms-nb-docs -p 3939:80 -v ${PWD}/api.yml:/usr/share/nginx/html/swagger.yaml -e SPEC_URL=swagger.yaml redocly/redoc:v2.0.0-rc.8-1

docker container ls 
```

```
## in browser of rdp
http://<public-ip-of-server>:3939
```

### Step 3: Cleanup and restructure 

#### Step 3.1 Delete and rename unneeded files 

```
## we will use the users module for something else 
cd
cd ms-flights
mv lib/users lib/flights
```

```
## / -> homedoc is no needed, so we delete it
rm -fR lib/homedoc
```

#### Step 3.2 Adjust appConfig.js to change routings 

```
nano appConfig.js 
```

```
## 1. delete this line from appConfig.js
## app.use('/',      require('homedoc')); // attach to root route

## 2. change line
## app.use('/users', require('users')); // attach to sub-route
## to ->
## app.use('/flights', require('flights')); // attach to sub-route
```

#### Step 3.3 Edit lib/flights/controllers/mappings.js for input validation and which functions to call

```
cd lib/flights/controllers
rm mappings.js
```

```
##the new version will look like this
wget https://raw.githubusercontent.com/jmetzger/ms-flights/master/lib/flights/controllers/mappings.js
```
#### Step 3.4 Edit lib/flights/controllers/actions.js  

```
cd
cd ms-flights
mkdir -p lib/flights/controllers/
cd lib/flights/controllers/
rm actions.js 
```

```
## the new version will look like this
wget https://raw.githubusercontent.com/jmetzger/ms-flights/master/lib/flights/controllers/actions.js
```

### Step 3.5 Delete lib/flights/models 

```
cd
cd ms-flights
rm -fR lib/flights/models/
```

#### Step 3.6 Integrate MySQL - data 

##### Step 3.6.1 Delete old stuff in migrations 

```
rm -fR migrations/* 
```

##### Step 3.6.2 Create files for upgrading/downgrading

```
## create migrations scripts (implemented in bootstrap)
## with correct dates 
make migration-create name=seat-maps
make migration-create name=flights
make migration-create name=sample-data
```

```
## if you are working as unprivileged user change permissions accordingly
## They are root after this process
cd
cd ms-flights
sudo chown -R 11trainingdo:11trainingdo migrations
```

```

```

##### Step 3.6.3 Populate files 

```
cd
cd ms-flights/migrations/sqls 

## migrations/sqls/[date]-seat-maps-up.sql with data of
wget https://raw.githubusercontent.com/jmetzger/ms-flights/master/migrations/sqls/20200602055112-seat-maps-up.sql

## migrations/sqls/[date]-flights-up.sql with data of
wget https://raw.githubusercontent.com/jmetzger/ms-flights/master/migrations/sqls/20200602055121-flights-up.sql

wget https://github.com/jmetzger/ms-flights/blob/master/migrations/sqls/20200602055127-sample-data-up.sql
```


##### Step 3.6.4 Do the migration 

```
cd
cd ms-flights 
## Doing make restart instead of make migrate, because new data needs to be in docker container
make restart
```

#### Step 3.7 Renaming from ms-nodebootstrap-example to ms-flights 

```
cd
cd ms-flights
grep -rl "ms-nodebootstrap-example" . | xargs sed -i 's/ms-nodebootstrap-example/ms-flights/g'
## when adjusted all entries doublecheck
grep -r ms-nodebootstrap-example
make restart
```

#### Step 3.8 Testing 

```
## Flight 
curl http://192.168.56.102:5501/flights?flight_no=AA34&departure_date_time=2020-05-17T13:20
```

```
## Seat map
curl --verbose http://192.168.56.102:5501/flights/AA34/seat_map
```

### Use premade version of flight app - with fixes already


### Step 1: Use ready project 

  * https://github.com/jmetzger/ms-flights

```
## as unpriviliged user / root
cd
```

```
## either usetemplate
## but we will just clone it locally
git clone https://github.com/jmetzger/ms-flights ms-flights
```

### Step 2: Create documentation 

```
cd
cd ms-flights/docs
```

```
## now render the docs and open 3939 port with container running
## WICHTIG: Befehl muss aus dem docs/-Verzeichnis ausgeführt werden,
## damit ${PWD}/api.yml korrekt auf docs/api.yml zeigt.
## Wird der Befehl aus ms-flights/ ausgeführt, legt Docker ein leeres
## Verzeichnis api.yml an und die Seite zeigt einen Fehler.
docker run -d --rm --name ms-nb-docs -p 3939:80 -v ${PWD}/api.yml:/usr/share/nginx/html/swagger.yaml -e SPEC_URL=swagger.yaml redocly/redoc:v2.0.0-rc.8-1

docker container ls 
```

```
## in browser of rdp
http://<public-ip-of-server>:3939
```

### Step 3: Build 

```
cd
cd ms-flights
docker compose up -d
docker compose logs 
```

### Step 4: Testing 

```
## öffentliche / public ip rausfinden
## auf dem Server
ip a show eth0
```



```
## Flight 
curl http://<public-ip>:5501/flights?flight_no=AA34\&departure_date_time=2020-05-17T13:20
```

```
## Seat map (gibt nur o.k. zurück, nicht implementiert)
curl http://<public-ip>:5501/flights/AA34/seat_map
```

---

### Fehleranalyse vom 20.05.2026: ms-flights startet nicht (Race Condition mit MySQL)

#### Was war das Problem?

Beim Start von `docker compose up` startete die Datenbank (`ms-flights-db`), aber der Service `ms-flights` schmierte sofort ab mit diesem Fehler in den Logs:

```
ms-flights  | running database migrations
ms-flights  | [ERROR] AssertionError: connect ECONNREFUSED 172.20.0.2:3306
```

**Warum passiert das?**

MySQL öffnet den TCP-Port 3306 schon bevor die Datenbank wirklich bereit ist, Anfragen anzunehmen. Das alte `wait-for.sh`-Script prüft nur ob der Port offen ist (per `nc -z`) — nicht ob MySQL wirklich läuft. Deshalb startet `ms-flights` zu früh, die Migration schlägt fehl.

```
ms-flights startet  →  wait-for.sh sieht Port 3306 offen  →  db-migrate läuft
                                                               ↓
                                                         ECONNREFUSED
                                                  (MySQL noch nicht bereit)
```

---

#### Vorher (fehlerhaft)

`docker-compose.yml` — ms-flights hatte `links` statt `depends_on`, kein `healthcheck`, kein `restart`:

```yaml
services:
  ms-flights:
    ...
    links:
      - ms-flights-db        # veraltet, stellt nur Netzwerk-Alias bereit
    # kein depends_on
    # kein restart: always
    command: ./shell/wait-for.sh ms-flights-db:3306 -- ./shell/start-dev.sh

  ms-flights-db:
    image: mysql:5.7
    ...
    restart: always
    # kein healthcheck!
```

`wait-for.sh` prüft nur TCP-Port (nicht ob MySQL wirklich Queries annimmt):

```sh
wait_for() {
  for i in `seq $TIMEOUT` ; do
    nc -z "$HOST" "$PORT" > /dev/null 2>&1   # nur TCP-Check!
    result=$?
    if [ $result -eq 0 ] ; then
      exec "$@"   # startet sofort, auch wenn MySQL noch nicht bereit
    fi
    sleep 1
  done
}
```

---

#### Nachher (funktioniert)

`docker-compose.yml` — `links` entfernt, `healthcheck` + `depends_on` + `restart: always` ergänzt:

```yaml
services:
  ms-flights:
    ...
    # kein links mehr — Services erreichen sich bereits per Service-Name
    depends_on:
      ms-flights-db:
        condition: service_healthy    # wartet bis DB wirklich bereit
    restart: always                   # startet nach Server-Reboot wieder
    command: ./shell/start-dev.sh    # wait-for.sh nicht mehr nötig

  ms-flights-db:
    image: mysql:5.7
    ...
    restart: always
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost", "-u", "root", "-pverysecretsomething"]
      interval: 5s        # alle 5 Sekunden prüfen
      timeout: 5s
      retries: 10
      start_period: 30s   # erst nach 30s anfangen zu prüfen (MySQL braucht Zeit)
```

**Ablauf jetzt:**

```
ms-flights-db startet  →  alle 5s: mysqladmin ping  →  nach ~30s: "healthy"
                                                          ↓
                                                    ms-flights startet
                                                          ↓
                                               Migrationen laufen durch
                                                          ↓
                                          Server läuft auf Port 5501
```

Logs nach dem Fix:

```
ms-flights-db  | mysqld: ready for connections.
ms-flights-db  | (healthy)
ms-flights     | running database migrations
ms-flights     | [INFO] No migrations to run
ms-flights     | [INFO] Done
ms-flights     | Express server instance listening on port 5501
```

### Upload image flight app


### Step 1: Upload image to docker hub 

```
cd
cd ms-flights
```

```
## from the last step 01 Create microservce you should already have an image
docker images | grep flights
## ms-flights-ms-flights                 latest  
## to upload it to docker hub, we would need to tag it
## one image can have multiple tags
docker tag ms-flights-ms-flights dockertrainereu/flights-jm:v11
docker login
## now enter gittrainereu + password-you-will-get-from-your-trainer ;O)
## push the image to the server 
docker push dockertrainereu/flights-jm:v11
```

## Microservice - flightapp - Deployment Kubernetes

### Manual deployment



### Überblick 

<img width="857" height="698" alt="image" src="https://github.com/user-attachments/assets/a2010075-30d1-4c77-a6e5-a2b07ae48bb6" />


### Schritt 1: configmap - flights

```
cd 
mkdir -p manifests
cd manifests
mkdir flight-app
cd flight-app
mkdir flights
cd flights 
nano 01-secret.yml 
```

```
## you could also create a secret with
##  kubectl create secret generic mariadb-secret --from-literal=MARIADB_ROOT_PASSWORD=11abc432 --dry-run=client -o yaml > 01-secrets.yml
```

```
### 01-secrets.yml
kind: Secret 
apiVersion: v1 
metadata:
  name: mariadb-secret
data:
  # als Wertepaare
  MARIADB_ROOT_PASSWORD: MTFhYmM0MzI=
```

```
cd
cd manifests/flight-app
## alle Unterverzeichnisse recursiv ausführen 
kubectl apply -Rf .
kubectl get secrets
kubectl get secrets mariadb-secret -o yaml
```

### Schritt 2: PersistentVolumeClaim 

```
cd
cd manifests/flight-app
cd flights
nano 02-pvc.yml 
```

```
## vi 02-pvc.yml
## now we want to claim space
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: pvc-do
spec:
  storageClassName: do-block-storage
  accessModes:
  - ReadWriteOnce
  resources:
     requests:
       storage: 1Gi
```

```
cd
cd manifests
cd flight-app
kubectl apply -Rf .  
```

  * Ref: https://docs.digitalocean.com/products/kubernetes/how-to/add-volumes/



### Schritt 3: mariadb Deployment 

```
cd
cd manifests/flight-app/flights 
nano 03-deploy-mariadb.yml
```

```
##deploy.yml 
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mariadb-deployment
spec:
  selector:
    matchLabels:
      app: mariadb
  replicas: 1 
  template:
    metadata:
      labels:
        app: mariadb
    spec:
      containers:
      - name: mariadb-cont
        image: mariadb:latest

        volumeMounts:
        -  mountPath: "/var/lib/mysql"
           name: do-volume

        envFrom:
        - secretRef:
            name: mariadb-secret
        
      volumes:
      - name: do-volume
        persistentVolumeClaim:
          claimName: pvc-do
```

```
cd
cd manifests/flight-app/
kubectl apply -Rf .
```

### Schritt 3.1 Service für MariaDB anlegen 

```
cd
cd manifests/flight-app/flights
nano 04-service-mariadb.yml
```


```
apiVersion: v1
kind: Service
metadata:
  name: ms-flights-db
spec:
  type: ClusterIP
  ports:
  - port: 3306
    protocol: TCP
  selector:
    app: mariadb
```

```
cd
cd manifests/flight-app/
kubectl apply -Rf .
```

### Schritt 3.2: Add flights deployment 

```
cd
cd manifests/flight-app/flights
nano 03-deploy-flights.yml
```

```
##deploy.yml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: flights-deployment
spec:
  selector:
    matchLabels:
      app: flights
  replicas: 1
  template:
    metadata:
      labels:
        app: flights
    spec:
      containers:
      - name: app
        image: dockertrainereu/flights-jm:v11
        command: [ "/bin/sh", "-c", "--" ]
        args: [ "while true; do sleep 30; done;" ]
        env:
        - name: NODE_ENV
          value: dev
              #- name: NODE_HOT_RELOAD
              #value: "1"
              #- name: NODE_LOGGER_GRANULARLEVELS
              #value: "1"
        - name: NODE_CONFIG_DISABLE_FILE_WATCH
          value: "Y"
```

```
cd
cd manifests/flight-app/
kubectl apply -Rf .
```

### Schritt 4: config für redis anlegen 

```
cd
mkdir -p  manifests/flight-app/reservations
cd manifests/flight-app/reservations
nano 01-redis-cm.yml
```

```
apiVersion: v1
kind: ConfigMap
metadata:
  name: redis-cm
data:

  REDIS_HOST: "ms-reservations-redis"
  REDIS_PORT: "6379"
  REDIS_DB: "0"
  REDIS_PWD: "4n_ins3cure_P4ss"

  redis-config: |
    appendonly yes
```

```
kubectl apply -f .
```

### Schritt 5: Redis ausrollen 

```
cd
cd manifests/flight-app/reservations
nano 02-redis-deploy.yml 
```


```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ms-reservations-redis
spec:
  replicas: 1
  selector:
    matchLabels:
      storage: redis
  strategy:
    type: Recreate

  template:
    metadata:
      labels:
        storage: redis

    spec:
      containers:
        - command:
            - redis-server
            - /usr/local/etc/redis/redis.conf
            - --requirepass
            - 4n_ins3cure_P4ss
          env:
            - name: REDIS_REPLICATION_MODE
              value: master
          image: redis:6-alpine
          name: ms-reservations-redis
          ports:
            - containerPort: 6379
          volumeMounts:
            - mountPath: /data
              name: data
            - mountPath: /usr/local/etc/redis/redis.conf
              name: config
      volumes:
        - name: data
          emptyDir: {}
        - name: config
          configMap:
            name: redis-cm
            items:
            - key: redis-config
              path: redis.conf
```

```
kubectl apply -f .
```


### Schritt 6: service für redis 

```
nano 03-redis-service.yml
```

```
apiVersion: v1
kind: Service
metadata:
  name: ms-reservations-redis
spec:
  ports:
    - name: "redis"
      port: 6379
  selector:
    storage: redis
```

```
kubectl apply -f .
```

### Schritt 7: deployment for reservations 

```
nano 04-reservations-deploy.yml
```

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ms-reservations
spec:
  replicas: 1
  selector:
    matchLabels:
      app: reservations
  template:
    metadata:
      labels:
        app: reservations
    spec:
      containers:
        - command: [ "/bin/bash", "-c", "--" ]
          args: [ "while true; do sleep 30; done;" ]
          #     - ./wait-for.sh
          #   - -t
          #   - "60"
          #   - ms-reservations-redis:6379
          #   - --
          #   - gunicorn
          #   - -b
          #   - 0.0.0.0:5000
          #   - --reload
          #   - -w
          #   - "1"
          #   - service:app
          env:
            - name: FLASK_ENV
              value: development
            - name: REDIS_DB
              valueFrom:
                configMapKeyRef:
                  key: REDIS_DB
                  name: redis-cm
            - name: REDIS_HOST
              valueFrom:
                configMapKeyRef:
                  key: REDIS_HOST
                  name: redis-cm
            - name: REDIS_PORT
              valueFrom:
                configMapKeyRef:
                  key: REDIS_PORT
                  name: redis-cm
            - name: REDIS_PWD
              valueFrom:
                configMapKeyRef:
                  key: REDIS_PWD
                  name: redis-cm
          image: dockertrainereu/reservations-jm:v16
          name: ms-reservations
          ports:
            - containerPort: 5000
          resources: {}
          volumeMounts:
            - mountPath: /app
              name: ms-reservations-claim0

      volumes:
        - name: ms-reservations-claim0
          emptyDir: {}
          #persistentVolumeClaim:
          #  claimName: ms-reservations-claim0
```

```
cd
cd manifests/flight-app
kubectl apply -R -f .
```

### Hinweis: kompose als Hilfestellung nutzen 

#### Hinweis: Lokal kompose installieren 

  * als root

[Kompose installieren](#tool-zum-konvertion-von-docker-composeyaml-file-manifesten)

```
## alle weiteren Schritte als kurs 
su - kurs 
```

####  ms-reservations clonen (zur Hilfe bzgl. der manifests)

```
cd 
git clone https://github.com/jmetzger/ms-reservations
cd ms-reservations

## create a dummy folder
mkdir dummy
cp -a docker-compose.yml dummy
cp -a database-dev.env dummy
cd dummy
kompose --file=docker-compose.yml convert
```

### gitlab Deployment


### Prerequisites 

  * 01-xxxx is done by you (manifests created) 


### Schritt 1: Neues repo anlegen und manifeste pushen 

```
### neues repo in gitlab anlegen
### achtung ohne README -> d.h. leer
z.B.
https://gitlab.com/training.tn1/ms-jochen-k8sdeploy
```

### Option Schritt 1: umbenennen 

```
cd
cd manifests/
mkdir project-flight-app
mv flight-app project-flight-app
cd project-flight-app
```

### Schritt 2: 

```
cd
cd manifests
cd flight-app
```

```
git init
git add -A
git status
git config --global user.email "you@email.com"
git config --global user.name "Phantomas"
git commit -am "initial release"
git log
##  Wo soll es hingehen, aus Startseite im Repo, wenn keine README gesetzt 
git remote add origin https://gitlab.com/training.tn1/ms-jochen-k8sdeploy.git
## In welchem Branch bin ich
git branch
git push -u origin master
```


### Schritt 3: KUBECONFIG_SECRET einrichten 

  * in Settings->CI/CD -> Variables -> KUBECONFIG_SECRET (als File)

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/ce299745-c478-409d-8416-0bb8261e8133)

```
## Inhalt kommt von meinem lokalen System, wo ich auch kubectl verwende
##  -> wenn eine Verbindung zum  Cluster besteht, ansonsten aus management tool des Clusters , z.B microk8s config 
cat .kube/config
```


### Schritt 4: pipeline mit kubectl einrichten 

  * Ich brauche ein image, das kubectl kann 


```
## on gitlab create a new pipeline
## by editing with pipeline editor
```

```
## use the following content 
deploy:
  image:
    name: alpine/k8s:1.36.1
    entrypoint: ['']
  script:
    - mkdir ~/.kube && cat "$KUBECONFIG_SECRET" > ~/.kube/config
    - kubectl cluster-info
    - pwd
    - ls -la
    - cd manifests && kubectl apply -Rf .
```

### Schritt 5: version des images ändern in ...

```
## image-version muss in docker hub vorhanden sein
z.B. v3 - > v4.

## hier z.B. direkt im repo ändern
flight-app/reservations/04-reservations-deploy.yml 

```


### Ref: 

  * https://docs.gitlab.com/ee/user/clusters/agent/ci_cd_workflow.html#update-your-gitlab-ciyml-file-to-run-kubectl-commands
  
  

### github Deployment


### Step 1: Create Repo in github 

```
## url
https://github.com/new

## Bitte hier keine Dateien anlegen
## keine README.md
## keine .gitignore

```

### Step 2: Create personal access token (Optional) 

  * you can do this here: https://github.com/settings/tokens/new

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/1ff54521-7f4d-4edb-8cba-f0c20a30782b)


### Step 3: Lokal projekt anlegen (auf dem kubectl - client) 

```
cd
mkdir -p github-test
cd github-test 
```

```
mkdir -p manifests
cd manifests
nano 01-deployment.yaml 
```

### Step 4: Populate project with sample manifest 

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
spec:
  selector:
    matchLabels:
      app: nginx
  replicas: 8
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.23
        ports:
        - containerPort: 80
```

### Step 5: Projekt unter Versionsverwaltung stellen und pushen 

```
cd
git init
git config --global user.email test@test.de
git config --global user.name "Jochen from m1"
```

```
git remote add origin https://github.com/gittrainereu/microjay2.git
```

```
git add .
git commit -am "Initial Release"
## wir werden gefragt nach:
## user-name
## password -> hier bitte den Personal Token verwenden 
git push -u origin master 
```

### Step 6: KUBERNETES_CONFIG als Secret anlegen 

```
## kopieren der Ausgabe von server mit kubectl
cat ~/.kube/config
```

```
## Enter it here, by adding a new secret: KUBERNETES_CONFIG
## secret für Repositry
https://github.com/gittrainereu/<your-repo> /settings/secrets/actions/new
```


![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/89e4fdc1-bcdb-4e69-8db6-3f630eff7655)

### Step 7: Setup github actions (in web ui of github)

  * workflow folder: .github/workflows
  * manifests - folder: manifests/
  
```
## create file .github/workflows/pipeline.yaml
## with content 
```

```
## adjust
## 1. server-url / use data from last step 
## 2. your-name / use your own namespace here
name: CI/CD
on: push
jobs:
  deploy:
    name: Deploy
    # needs: [ test, build ]
    runs-on: ubuntu-latest
    steps:
      - name: Set the Kubernetes context
        uses: azure/k8s-set-context@v2
        with:
          method: kubeconfig
          kubeconfig: ${{ secrets.KUBERNETES_CONFIG }}

      - name: Checkout source code
        uses: actions/checkout@v3

      - name: Deploy to the Kubernetes cluster
        uses: azure/k8s-deploy@v5
        with:
          namespace: <yournamespace>
          manifests: |
            manifests

```

### Step 9: watch and enjoy 




### Reference 

  * https://github.com/marketplace/actions/deploy-to-kubernetes-cluster

### github Deployment-with-secret-not-working


### What is not working ? 

  * We get an error in the pipeline, there seems
  * to be a misconfiguration about the secret we use

### Step 1: Create Repo in github 

```
## url
https://github.com/new
```

### Step 2: Create personal access token 

  * you can do this here: https://github.com/settings/tokens/new

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/1ff54521-7f4d-4edb-8cba-f0c20a30782b)




### Step 3: Clone Repo to local system (machine where we use kubectl ) 

```
## on local system -> clone to k8s-deploy
cd
mkdir -p github-test
cd github-test 
## so we all have the same folder in the training (for our ease) 
git clone <your-repo> k8s-deploy
cd k8s-deploy
```

```
mkdir -p manifests
cd manifests
nano 01-pod.yaml 
```

### Step 4: Populate Repo with sample manifest 

```
apiVersion: v1
kind: Pod
metadata:
  name: static-web
  labels:
    role: myrole
spec:
  containers:
    - name: web
      image: nginx
      ports:
        - name: web
          containerPort: 80
          protocol: TCP
```

### Step 5: Push changes 

```
git config --global user.email test@test.de
git config --global user.name "Jochen from m1"
```

```
git add -A
git commit -am "Initial Release"
git push -u origin main
```


### Step 6: Setup authentication in kubernetes (service account) - in kubectl - client 

```
## wird in deinem namespace angelegt 
## create serviceaccount
kubectl create serviceaccount github-actions-tln<nr>
```

```
cd
mkdir -p manifests
cd manifests
mkdir github-account
cd github-account 
```

```
nano 01-sasecret.yaml
```

```
## Secret für service account anlegen / wichtig: muss
## in neueren Versionen von kubernetes gemacht werden
## da secrets nicht mehr automatisc angelegt werden
## beim Erstellen von service account (Stand: 26.04.2024) 
apiVersion: v1
kind: Secret
type: kubernetes.io/service-account-token
metadata:
  name: github-actions-secret 
  annotations:
    kubernetes.io/service-account.name: github-actions-tln<nr>
```

```
kubectl apply -f .
```

```
nano 02-clusterrole.yml 
```

```
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRole
metadata:
  name: continuous-deployment-tln<nr>
rules:
  - apiGroups:
      - ''
      - apps
      - networking.k8s.io
    resources:
      - namespaces
      - deployments
      - replicasets
      - ingresses
      - services
      - secrets
    verbs:
      - create
      - delete
      - deletecollection
      - get
      - list
      - patch
      - update
      - watch
```

```
kubectl apply -f .
```

```
kubectl create clusterrolebinding continuous-deployment-tln<nr> \
    --clusterrole=continuous-deployment-tln<nr>
    --serviceaccount=<dein-namespace>:github-actions-tln<nr>
```

### Step 7: secrets auslesen und bei github eintragen 

```
kubectl get secrets github-actions-secret -o yaml 
```

```
## Copy the output
```

```
## Enter it here, by adding a new secret: KUBERNETES_SECRET
https://github.com/gittrainereu/<your-repo>/settings/secrets/actions/new
```

```
## Get the url of your kubernetes cluster
## And Copy it to clipboard
## We will need this for your pipeline 
kubectl config view -o 'jsonpath={.clusters[0].cluster.server}'
```

### Step 8: Setup github actions (in web ui of github)

  * workflow folder: .github/workflows
  * manifests - folder: manifests/
  
```
## create file .github/workflows/pipeline.yaml
## with content 
```

```
## adjust
## 1. server-url / use data from last step 
## 2. your-name / use your own namespace here
name: CI/CD
on: push
jobs:
  deploy:
    name: Deploy
    # needs: [ test, build ]
    runs-on: ubuntu-latest
    steps:
      - name: Set the Kubernetes context
        uses: azure/k8s-set-context@v2
        with:
          method: service-account
          k8s-url: <server-url>
          k8s-secret: ${{ secrets.KUBERNETES_SECRET }}

      - name: Checkout source code
        uses: actions/checkout@v3

      - name: Deploy to the Kubernetes cluster
        uses: azure/k8s-deploy@v1
        with:
          namespace: <yournamespace>
          manifests: |
            manifests/

```

### Step 9: watch and enjoy 




### Reference 

  * https://github.com/marketplace/actions/deploy-to-kubernetes-cluster

## Microservice - flightapp - Uebungen: Manuell in Kubernetes deployen

### Uebung: ms-reservations manuell deployen und Service erstellen


### Hintergrund

In dieser Uebung deployen wir nur den `ms-reservations` Microservice manuell auf Kubernetes.
Der Service benoetigt Redis als Datenspeicher — beides wird ausgerollt.

Am Ende testen wir die Erreichbarkeit des Services von innerhalb des Clusters mit einem
temporaeren image-Pod.

### Schritt 1: Verzeichnis anlegen

```
cd
mkdir -p manifests/reservations
cd manifests/reservations
kubectl create ns reservations-<dein-name>
```

### Schritt 2: ConfigMap fuer Redis anlegen

```
nano 01-redis-cm.yml
```

```
apiVersion: v1
kind: ConfigMap
metadata:
  name: redis-cm
data:
  REDIS_HOST: "ms-reservations-redis"
  REDIS_PORT: "6379"
  REDIS_DB: "0"
  REDIS_PWD: "4n_ins3cure_P4ss"
  redis-config: |
    appendonly yes
```

```
kubectl apply -f . -n reservations-<dein-name>
```

### Schritt 3: Redis Deployment anlegen

```
nano 02-redis-deploy.yml
```

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ms-reservations-redis
spec:
  replicas: 1
  selector:
    matchLabels:
      storage: redis
  strategy:
    type: Recreate
  template:
    metadata:
      labels:
        storage: redis
    spec:
      containers:
        - name: ms-reservations-redis
          image: redis:6-alpine
          command:
            - redis-server
            - /usr/local/etc/redis/redis.conf
            - --requirepass
            - 4n_ins3cure_P4ss
          env:
            - name: REDIS_REPLICATION_MODE
              value: master
          ports:
            - containerPort: 6379
          volumeMounts:
            - mountPath: /data
              name: data
            - mountPath: /usr/local/etc/redis/redis.conf
              name: config
      volumes:
        - name: data
          emptyDir: {}
        - name: config
          configMap:
            name: redis-cm
            items:
              - key: redis-config
                path: redis.conf
```

```
kubectl apply -f . -n reservations-<dein-name>
kubectl get pods -n reservations-<dein-name>
```

### Schritt 4: Redis Service anlegen

```
nano 03-redis-svc.yml
```

```
apiVersion: v1
kind: Service
metadata:
  name: ms-reservations-redis
spec:
  type: ClusterIP
  ports:
    - name: redis
      port: 6379
  selector:
    storage: redis
```

```
kubectl apply -f . -n reservations-<dein-name>
```

### Schritt 5: Reservations Deployment anlegen

```
nano 04-reservations-deploy.yml
```

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ms-reservations
spec:
  replicas: 1
  selector:
    matchLabels:
      app: reservations
  template:
    metadata:
      labels:
        app: reservations
    spec:
      containers:
        - name: ms-reservations
          image: dockertrainereu/reservations-jm:v1.1
          env:
            - name: FLASK_ENV
              value: development
            - name: REDIS_DB
              valueFrom:
                configMapKeyRef:
                  name: redis-cm
                  key: REDIS_DB
            - name: REDIS_HOST
              valueFrom:
                configMapKeyRef:
                  name: redis-cm
                  key: REDIS_HOST
            - name: REDIS_PORT
              valueFrom:
                configMapKeyRef:
                  name: redis-cm
                  key: REDIS_PORT
            - name: REDIS_PWD
              valueFrom:
                configMapKeyRef:
                  name: redis-cm
                  key: REDIS_PWD
          ports:
            - containerPort: 8000
```

```
kubectl apply -f . -n reservations-<dein-name>
kubectl get pods -n reservations-<dein-name>
```

### Schritt 6: Status pruefen

```
kubectl get pods -n reservations-<dein-name>
```

Erwartete Ausgabe (beide Pods Running):
```
NAME                                    READY   STATUS    RESTARTS   AGE
ms-reservations-xxxx                    1/1     Running   0          1m
ms-reservations-redis-xxxx              1/1     Running   0          2m
```

### Aufgabe: Service fuer ms-reservations erstellen und Verbindung testen 

Das Deployment laeuft — aber von aussen (auch innerhalb des Clusters) ist der Pod
noch nicht über ein Service erreichbar (Best Practice !). Erstelle dafuer eine Datei `05-reservations-svc.yml`.

Hinweise:
- Typ: `ClusterIP`
- Port der App: `8000`
- Der `selector` muss zum Label im Deployment passen

Wende danach alle Manifests erneut an:

```
kubectl apply -f . -n reservations-<dein-name>
kubectl get svc -n reservations-<dein-name>
```

Teste zuerst ob der Service erreichbar ist. Verwende das Image curlimages/curl. Es hat auch eine Shell und unterstuetzt alle HTTP-Methoden (GET, PUT, POST, ...):

Erwartete Ausgabe:
```
{"status": "pass"}
```

### Aufgabe: Reservierung durchfuehren

#### API-Endpunkte

| Methode | URL | Beschreibung |
|---------|-----|--------------|
| `PUT` | `http://ms-reservations:8000/reservations` | Reservierung anlegen |
| `GET` | `http://ms-reservations:8000/reservations?flight_id=<id>` | Alle Reservierungen eines Fluges |

#### Daten fuer die Reservierung (JSON-Body beim PUT)

```
{
  "flight_id": "FL001",
  "seat_num": "12A",
  "customer_id": "max"
}
```

#### ToDo 1: Führe eine Reservierung durch mit PUT 

Erwartete Ausgabe:
```
{"status": "success"}
```

#### ToDo 2: Reservierung abfragen (GET)

Erwartete Ausgabe:
```
{"12A": "max"}
```

#### ToDo  3: Doppelbuchung versuchen

Versuche denselben Sitz nochmal zu buchen — was passiert?

Erwartete Ausgabe:
```
{"error": "Could not complete reservation for 12A", "description": "Seat already reserved. Cannot double-book"}
```

### Zusatzaufgabe: Image-Version aktualisieren

Wenn die Reservierung geklappt hat: Aendere den Image-Tag in `04-reservations-deploy.yml`
von `v1.1` auf `v17` und beobachte den Rolling Update:

```
kubectl apply -f . -n reservations-<dein-name>
kubectl get pods -n reservations-<dein-name>
```

### Aufraeumen

```
kubectl delete namespace reservations-<dein-name>
```

### Loesung: Service fuer ms-reservations


### Loesung 1: Service-Manifest

```
nano 05-reservations-svc.yml
```

```
apiVersion: v1
kind: Service
metadata:
  name: ms-reservations
spec:
  type: ClusterIP
  ports:
    - name: http
      port: 8000
  selector:
    app: reservations
```

```
kubectl apply -f . -n reservations-<dein-name>
kubectl get svc -n reservations-<dein-name>
```

#### Erklaerung

| Feld | Bedeutung |
|------|-----------|
| `type: ClusterIP` | Service nur innerhalb des Clusters erreichbar |
| `port: 8000` | Port auf dem der Service lauscht und weiterleitet |
| `selector: app: reservations` | muss zum Label im Deployment-Pod passen |

Der `selector` ist der entscheidende Teil: Kubernetes sucht alle Pods mit diesem Label
und leitet Traffic dorthin weiter. Stimmt das Label nicht, gibt es keine Endpoints
und der Service antwortet mit Connection refused.

### Loesung 2: Reservierung durchfuehren

Wir verwenden `curlimages/curl` — das Image hat `curl` mit Shell und unterstuetzt
alle HTTP-Methoden (GET, PUT, POST, ...).

#### Schritt 1: Reservierung anlegen (PUT)

```
kubectl run -it --rm curlpod --image=curlimages/curl --restart=Never \
  -n reservations-<dein-name> \
  -- curl -s -X PUT -H "Content-Type: application/json" \
     -d '{"flight_id":"FL001","seat_num":"12A","customer_id":"max"}' \
     http://ms-reservations:8000/reservations
```

Erwartete Ausgabe:
```
{"status": "success"}
```

#### Schritt 2: Reservierung abfragen (GET)

```
kubectl run -it --rm curlpod --image=curlimages/curl --restart=Never \
  -n reservations-<dein-name> \
  -- curl -s "http://ms-reservations:8000/reservations?flight_id=FL001"
```

Erwartete Ausgabe:
```
{"12A": "max"}
```

#### Schritt 3: Doppelbuchung versuchen

```
kubectl run -it --rm curlpod --image=curlimages/curl --restart=Never \
  -n reservations-<dein-name> \
  -- curl -s -X PUT -H "Content-Type: application/json" \
     -d '{"flight_id":"FL001","seat_num":"12A","customer_id":"erika"}' \
     http://ms-reservations:8000/reservations
```

Erwartete Ausgabe:
```
{"error": "Could not complete reservation for 12A", "description": "Seat already reserved. Cannot double-book"}
```

### Loesung Zusatzaufgabe: Image-Version aktualisieren

In `04-reservations-deploy.yml` den Tag aendern:

```
          image: dockertrainereu/reservations-jm:v17
```

```
kubectl apply -f . -n reservations-<dein-name>
kubectl get pods -n reservations-<dein-name>
```

Erwartete Ausgabe:
```
Neue Pods mit einem kurzen AGE (kürzlich erstellt), werden angezeigt. 
```

## Kubernetes - Überblick

### Warum Kubernetes, was macht Kubernetes


  * Virtualisierung von Hardware - xfache bessere Auslastung
  * Ist in Google entstanden 
  * Software 2014 als OpenSource zur Verfügung gestellt (Angelegt an Borg)
  * Optimale Ausnutzung der Hardware, hunderte bis tausende Dienste können auf einigen Maschinen laufen (Cluster)  
  * Immutable - System
  * Selbstheilend
  
## Wozu dient Kubernetes ?

  * Orchestrierung von Containern
  * am gebräuchlisten aktuell Docker

### Aufbau Allgemein


### Schaubild 

![image](https://github.com/user-attachments/assets/f4de7c54-33a8-46e5-916c-1119575b1aed)

### Komponenten / Grundbegriffe

#### Master (Control Plane)

##### Aufgaben 

  * Der Master koordiniert den Cluster
  * Der Master koordiniert alle Aktivitäten in Ihrem Cluster
    * Planen von Anwendungen
    * Verwalten des gewünschten Status der Anwendungen
    * Skalieren von Anwendungen
    * Rollout neuer Updates.

##### Komponenten des Masters 

###### etcd

  * Verwalten der Konfiguration des Clusters (key/value - pairs) 
  
###### kube-controller-manager  
  
  * Zuständig für die Überwachung der Stati im Cluster mit Hilfe von endlos loops. 
  * kommuniziert mit dem Cluster über die kubernetes-api (bereitgestellt vom kube-api-server)

###### kube-api-server 

  * provides api-frontend for administration (no gui)
  * Exposes an HTTP API (users, parts of the cluster and external components communicate with it)
  * REST API
 
###### kube-scheduler 

  * assigns Pods to Nodes. 
  * scheduler determines which Nodes are valid placements for each Pod in the scheduling queue 
    ( according to constraints and available resources )
  * The scheduler then ranks each valid Node and binds the Pod to a suitable Node. 
  * Reference implementation (other schedulers can be used)
 
#### Nodes  

  * Worker Nodes (Knoten) sind die Arbeiter (Maschinen), die die Anwendungen ausführen
  * Control Plane Node, dort laufen die Tools zur Verwaltung/Beobachtung etc. des Clusters 
  * Ref: https://kubernetes.io/de/docs/concepts/architecture/nodes/

#### Pod/Pods 

  * Pods sind die kleinsten verwaltbaren Einheiten, die in Kubernetes erstellt und verwaltet werden können.
  * Ein Pod (übersetzt Gruppe) ist eine Gruppe von einem oder mehreren Containern
    * gemeinsam genutzter Speicher- und Netzwerkressourcen   
    * Befinden sich immer auf dem gleich virtuellen Server 
    
### Control Plane Node (former: master) - components 

### Worker Node (Minion) - components 

#### General 

  * On the nodes we will rollout the applications

#### kubelet

```
Node Agent that runs on every node (worker) 
Er stellt sicher, dass Container in einem Pod ausgeführt werden.
```

#### Kube-proxy 

  * Läuft auf jedem Node 
  * = Netzwerk-Proxy für die Kubernetes-Netzwerk-Services.
  * Kube-proxy verwaltet die Netzwerkkommunikation innerhalb oder außerhalb Ihres Clusters.
  
### Referenzen 

  * https://www.redhat.com/de/topics/containers/kubernetes-architecture


### Structure Kubernetes Deep Dive

  * https://github.com/jmetzger/training-kubernetes-advanced/assets/1933318/1ca0d174-f354-43b2-81cc-67af8498b56c

### Ausbaustufen Kubernetes


![image](https://github.com/user-attachments/assets/355652f2-1a2e-441b-93c2-5b68508158b1)

### Aufbau mit helm,OpenShift,Rancher(RKE),microk8s


![Aufbau](/images/aufbau-komponente-kubernetes.png)

### Welches System ? (minikube, micro8ks etc.)


## Überblick der Systeme 

### General 

```
kubernetes itself has not convenient way of doing specific stuff like 
creating the kubernetes cluster.

So there are other tools/distri around helping you with that.

```

### Kubeadm

#### General 

  * The official CNCF (https://www.cncf.io/) tool for provisioning Kubernetes clusters
    (variety of shapes and forms (e.g. single-node, multi-node, HA, self-hosted))
  * Most manual way to create and manage a cluster 

#### Disadvantages 

  * Plugins sind oftmals etwas schwierig zu aktivieren

### microk8s 

#### General

  * Created by Canonical (Ubuntu)
  * Runs on Linux
  * Runs only as snap
  * In the meantime it is also available for Windows/Mac
  * HA-Cluster 

#### Production-Ready ? 

  * Short answer: YES 

```
Quote canonical (2020):

MicroK8s is a powerful, lightweight, reliable production-ready Kubernetes distribution. It is an enterprise-grade Kubernetes distribution that has a small disk and memory footprint while offering carefully selected add-ons out-the-box, such as Istio, Knative, Grafana, Cilium and more. Whether you are running a production environment or interested in exploring K8s, MicroK8s serves your needs.

Ref: https://ubuntu.com/blog/introduction-to-microk8s-part-1-2

```

#### Advantages

  * Easy to setup HA-Cluster (multi-node control plane)
  * Easy to manage 

### minikube 

#### Disadvantages
  
  * Not usable / intended for production 

#### Advantages 

  * Easy to set up on local systems for testing/development (Laptop, PC) 
  * Multi-Node cluster is possible 
  * Runs und Linux/Windows/Mac
  * Supports plugin (Different name ?)


### k3s

  * Sehr schlanker Footprint
  * Sehr einfach und schnell zu installieren (ein binary)
  * Auch für die Produktion geeignet.
  * Sinnvoll für den Einsatz auf sehr ressourcenarmen Systemen 

### kind (Kubernetes-In-Docker)

#### General 

  * Runs in docker container 


#### For Production ?

```
Having a footprint, where kubernetes runs within docker 
and the applikations run within docker as docker containers
it is not suitable for production.
```



## Kubernetes - Einsatz

### Kubernetes Einsatz -> Risiken


### Kubernetes 

#### Leitungsteam 

  * Risiko: Vertrauen aufbauen.

##### Größter Schmerz 

  * Know-How Träger gehen weg 
    * Lösung: CI/CD Pipeline
  * Keine neuen Mitarbeiter zu finden/ nicht genug
    * junge Absolventen wollen hippe Technologie verwenden
    * Lösung: kein Technologie-Login bzgl der Programmierung
  * Dauert der Wechsel zu lange ?
    * Lösung: wie gross der Schmerz bzgl. Probleme die auftreten -> Implementierung zu lange (Time to market) 
    * Technologie-Schuld: Wo wollen wir in 10 Jahren (und halten wir die Technologie uptodate)  
    
#### Team / Prozesse 

  * Fehlendes Knowledge im Team für Kubernetes
    * Implikation -> Single Point of Failure
    * Berührungsängste / Nicht-Handlung bzgl. Kubernetes  
  * Keinen klaren Prozess Updates der Infrastruktur
    * Implikation; Veraltete Versionsstände
    * Implikation: Angreifbarkeit durch Sicherheitslücken  
  * keinen klaren Prozess auf Updates von Applikation (Prozess Anpassung der Manifeste)
    * Implikaton: manifeste funktionieren nicht mehr oder unerwartet auf neueren Version
    * Implikation: bestimmte Feature sind nicht mehr verfügbar (PSP - Pod Security Policies (deprecated), PSA (Pod Security Admission) 

#### Pods 

  * Pods (Container) als Root laufen lassen (Angriffsrisiko) 
    * Implikation: Wenn jemand den Pod hacked, kann er u.U. Rechte auf der Host-Maschinen bekommen.
   
#### Images / Sicherheit 

  * Images verwenden alte Versionen von Software und nicht überprüft, gescannt. 

#### Backup / Monitoring / Observeability 

   * Kubernetes Aware Backups-System (kasten.io) wird nicht verwendet 
     * Implikation: Das falsche wird gesichert.
     * Implikation: Hohe Komplexität beim Zurückspielen (falsche Volume wird zurückgesichert, unklar welches)  

   * Richtige Backupstrategie (manifeste, Grundeinrichtung Server, Konfigurationsdateien (speziell für Cluster-infrastruktur - besser Infrastructure by Code)  

#### Performance

   * Pods falsch konfiguriert sind.
     * Implikation: die falschen Pods werden verschoben.
    

### Microservices 

#### Fehlende Tools zur Analyse 

  * Entscheidende Tools für Monitoring, Tracing, Observeability nicht oder falsch eingerichtet.
  * das aller wichtigste Tracing (Jaeger) und pro Anfrage einen durchgängigen Key
  * Log Aggregation (ELK / EFK) -> Um aus der Vogelperspektive Problem erfassen und trigger zu setzen
    * Logs vom Server, Logs vom Kubernetes Cluster, Logs von den Pods -> zentral zusammenläuft
   
  * Implikation: Blindflug nach Ausrollen von MicroServices

#### Wir machen mal microservices weil es cool ist 

   * Wichtig: Warum nehme ich microservices
   * Warum kein Monolith
   * Microservices: Kaufpreis für Effekt

   * Implikation: Dat Ding fliegt mir um die Ohren
     * Team: Zu aufwändig.
     * Team: fehlendes Knowlegdge.
     * Team: Falsche Einschätzung der Performance
     * Team: Grobe Konzeptionsfehler

#### Zu schnell zu viel wollen 

   * Alles von Anfang bis Ende durchkonzipieren
   * Zuviele Microservices werden zu schnell online genommen
     * UND: die alte Funktionalität im Monolithen zu schnell abgeschaltet.
    
   * BESSER: Kleine Schritte, Erfahrungen sammeln, MESSEN ! 




### Kubernetes Datenbanken in Kubernetes oder ausserhalb


### Aspekt: Debugging (Expertise im Team) 

  * Kann kann ein Killer-Kriterium sein, weil ich jemand brauche,
    * der sowohl die DB beherrscht als auch Kubernetes
  * Wenn ich keinen solchen habe, sollte ich es NICHT in Kubernetes betreiben 
   
### Performance - Optimierung / Debugging 

  * Memory is key (Je mehr Arbeitsspeicher desto besser)
  * Funktionieren am besten, wenn alle häufig verwendeten Daten in den Arbeitsspeicher passen

### Nachteil Kubernetes:

  * Erhöhte Komplexität (wo muss ich hinlangen)
  * Manche Datenbanksystemen kommen nicht gut damit zurecht, wenn pods häufig mit der Datenbank
    * neu erstellt werden
   
 ## Wenn Kubernetes:

   * Gibt es einene Operator für diesen Datenbank

### Folgende Datenbanken gehen garnicht als Installation innerhalb des Kubernetes Clustter

  * Oracle

### Folgende Datenbanken sind nicht so gut geeignet eine Installation innerhalb des Kubernetes Cluster

  * mariadb und postgresql

### Referenz:

  * https://cloud.google.com/blog/products/databases/to-run-or-not-to-run-a-database-on-kubernetes-what-to-consider?hl=en
  * https://operatorhub.io/?keyword=mariadb


## Kubernetes mit microk8s (Installation und Management)

### Installation Ubuntu - snap


### Walkthrough

```
sudo snap install microk8s --classic
microk8s status

## Sobald Kubernetes zur Verfügung steht aktivieren wir noch das plugin dns
microk8s status
```

### Optional

```
## Execute kubectl commands like so 
microk8s kubectl
microk8s kubectl cluster-info

## Make it easier with an alias 
echo "alias kubectl='microk8s kubectl'" >> ~/.bashrc
source ~/.bashrc
kubectl

```
### Working with snaps 

```
snap info microk8s 

```

### Ref:

  * https://microk8s.io/docs/setting-snap-channel

### Create a cluster with microk8s


### Walkthrough 

```
## auf master (jeweils für jedes node neu ausführen)
microk8s add-node

## dann auf jeweiligem node vorigen Befehl der ausgegeben wurde ausführen
## Kann mehr als 60 sekunden dauern ! Geduld...Geduld..Geduld 
##z.B. -> ACHTUNG evtl. IP ändern 
microk8s join 10.128.63.86:25000/567a21bdfc9a64738ef4b3286b2b8a69

```

### Testing 

```
## auf dem master z.B variante 1
microk8s status

## Variante
microk8s kubectl get nodes
```



### Auf einem Node addon aktivieren z.B. ingress

```
gucken, ob es auf dem anderen node auch aktiv ist. 
```

### Ref:

  * https://microk8s.io/docs/high-availability

### Remote-Verbindung zu Kubernetes (microk8s) einrichten


```
## on CLIENT install kubectl 
sudo snap install kubectl --classic 

## On MASTER -server get config 
## als root
cd
microk8s config > /home/kurs/remote_config 

## Download (scp config file) and store in .kube - folder  
cd ~
mkdir .kube
cd .kube  # Wichtig: config muss nachher im verzeichnis .kube liegen 
## scp kurs@master_server:/path/to/remote_config config 
## z.B. 
scp kurs@192.168.56.102:/home/kurs/remote_config config
## oder benutzer 11trainingdo
scp 11trainingdo@192.168.56.102:/home/11trainingdo/remote_config config 

##### Evtl. IP-Adresse in config zum Server aendern 

## Ultimative 1. Test auf CLIENT 
kubectl cluster-info 

## or if using kubectl or alias 
kubectl get pods 

## if you want to use a different kube config file, you can do like so 
kubectl --kubeconfig /home/myuser/.kube/myconfig

```

## Kubernetes mit k3s

### Kubernetes mit k3s


### Install (Quickstart) - 1 Node Cluster

```
curl -sfL https://get.k3s.io | sh -
```

### Flannel 

  * Kann keine NetworkPolicies
  * Angelegte NetzwerkPolicies rennen ins Leere
  * Flannel läuft nicht als Pod, sondenr ist in der k3s - binary drin 

### Wenn Netzwerkpolicies dann z.B. calico install 

  * https://docs.tigera.io/calico/latest/getting-started/kubernetes/k3s/quickstart

## Kubernetes - Client Tools und Verbindung einrichten

### Tools installieren und bash-completion / syntax highlightning


### Tools helm und kubectl 

```
snap install kubectl --classic 
snap install helm --classic
## Voraussetzung, ist dass das Paket bash-completion installiert 
kubectl completion bash > /etc/bash_completion.d/kubectl
helm completion bash > /etc/bash_completion.d/helm
```



### Highlightning und Indenting nano

```
sudo echo "include /usr/share/nano/yaml.nanorc" >> /etc/nanorc 
sudo echo "set autoindent" >> /etc/nanorc
sudo echo "set tabsize 2" >> /etc/nanorc
sudo echo "set tabstospaces" >> /etc/nanorc 
```

### Remote-Verbindung zu Kubernetes einrichten


### config einrichten 

```
## als unpriviligierter Benutzer z.B. kurs
cd
mkdir -p .kube
cd .kube
```

```
cp /tmp/config config
ls -la
```

```
kubectl cluster-info
```

### Arbeitsbereich konfigurieren 

```
kubectl create ns jochen
kubectl get ns
kubectl config set-context --current --namespace jochen
kubectl get pods
```

### Tool zum Konvertion von docker-compose.yaml file manifesten


### 

```
## als root
sudo su -
```

```
curl -L https://github.com/kubernetes/kompose/releases/download/v1.26.0/kompose-linux-amd64 -o kompose
chmod +x kompose
sudo mv ./kompose /usr/local/bin/kompose
```



### Ref:

  * https://kubernetes.io/docs/tasks/configure-pod-container/translate-compose-kubernetes/

## Kubernetes Praxis API-Objekte 

### Das Tool kubectl (Devs/Ops) - Spickzettel


### Allgemein 

```
## Zeige Information über das Cluster 
kubectl cluster-info 

## Welche api-resources gibt es ?
kubectl api-resources 

## Hilfe zu object und eigenschaften bekommen
kubectl explain pod 
kubectl explain pod.metadata
kubectl explain pod.metadata.name 

```

### Arbeiten mit manifesten 

```
kubectl apply -f nginx-replicaset.yml 
## Wie ist aktuell die hinterlegte config im system
kubectl get -o yaml -f nginx-replicaset.yml 

## Änderung in nginx-replicaset.yml z.B. replicas: 4 
## dry-run - was wird geändert 
kubectl diff -f nginx-replicaset.yml 

## anwenden 
kubectl apply -f nginx-replicaset.yml 

## Alle Objekte aus manifest löschen
kubectl delete -f nginx-replicaset.yml 


```

### Ausgabeformate 

```
## Ausgabe kann in verschiedenen Formaten erfolgen 
kubectl get pods -o wide # weitere informationen 
## im json format
kubectl get pods -o json 

## gilt natürluch auch für andere kommandos
kubectl get deploy -o json 
kubectl get deploy -o yaml

## Eigenschaft auslesen
kubectl get pods nginx-deployment-74676ff58f-fxcjv -o jsonpath='{.metadata.ownerReferences[0].name}'

```



### Zu den Pods 

```
## Start einen pod // BESSER: direkt manifest verwenden
## kubectl run podname image=imagename 
kubectl run nginx image=nginx 

## Pods anzeigen 
kubectl get pods 
kubectl get pod
## Format weitere Information 
kubectl get pod -o wide 
## Zeige labels der Pods
kubectl get pods --show-labels 

## Zeige pods mit einem bestimmten label 
kubectl get pods -l app=nginx 

## Status eines Pods anzeigen 
kubectl describe pod nginx 

## Pod löschen 
kubectl delete pod nginx 

## Kommando in pod ausführen 
kubectl exec -it nginx -- bash 

```

### Zu den Pods (Logs) 

```
## log eines pods anzeigen
kubectl logs podname

## Logs aller pods im Deployment
## Wichtig Option --prefix
kubectl logs --prefix deploy/web-nginx 

```


### Arbeiten mit namespaces 

```
## Welche namespaces auf dem System 
kubectl get ns 
kubectl get namespaces 
## Standardmäßig wird immer der default namespace verwendet 
## wenn man kommandos aufruft 
kubectl get deployments 

## Möchte ich z.B. deployment vom kube-system (installation) aufrufen, 
## kann ich den namespace angeben
kubectl get deployments --namespace=kube-system 
kubectl get deployments -n kube-system 

## wir wollen unseren default namespace ändern 
kubectl config set-context --current --namespace <dein-namespace>
```

### Arbeiten mit der Config (lokal) 

```
## namespace jochen als default setzen
kubectl config set-context --current --namespace jochen
## config anzeigen 
kubectl config view 
```

### Referenz

  * https://kubernetes.io/de/docs/reference/kubectl/cheatsheet/

### kubectl example with run


### Example (that does work)

```
## Show the pods that are running 
kubectl get pods 

## Synopsis (most simplistic example 
## kubectl run NAME --image=IMAGE_EG_FROM_DOCKER
## example
kubectl run nginx --image=nginx:1.21

kubectl get pods 
## on which node does it run ? 
kubectl get pods -o wide 
```

### Example (that does not work) 

```
kubectl run meinfalscherpod --image=foo2
## ImageErrPull - Image konnte nicht geladen werden 
kubectl get pods 
## Weitere status - info 
kubectl describe pods meinfalscherpod
```

### Ref:

  * https://kubernetes.io/docs/reference/generated/kubectl/kubectl-commands#run

### Bauen einer Applikation mit Resource Objekten


![Bauen einer Webanwendung](images/WebApp.drawio.png)

### Anatomie einer Webanwendung


![image](https://github.com/user-attachments/assets/0a0c519e-fad3-4aac-b945-2e0a7fc2999c)

### kubectl/manifest/pod


### Walkthrough 

```
cd 
mkdir -p manifests
cd manifests
mkdir -p web
cd web
```

```
nano nginx-static.yml
```

```
apiVersion: v1
kind: Pod
metadata:
  name: nginx-static-web
  labels:
    webserver: nginx
spec:
  containers:
  - name: web
    image: nginx:1.23

```

```
kubectl apply -f nginx-static.yml 
kubectl describe pod nginx-static-web 
## show config 
kubectl get pod/nginx-static-web -o yaml
kubectl get pod/nginx-static-web -o wide 
```

```
## pod auf Basis von manifest löschen
kubectl delete -f nginx-static.yml
```

### kubectl/manifest/replicaset


### Schritt 1: Erstellen 

```
cd
mkdir -p manifests
cd manifests
mkdir 02-rs 
cd 02-rs 
nano rs.yml
```

```
apiVersion: apps/v1
kind: ReplicaSet
metadata:
  name: nginx-replica-set
spec:
  replicas: 10
  selector:
    matchLabels:
      tier: frontend
  template:
    metadata:
      name: template-nginx-replica-set
      labels:
        tier: frontend
    spec:
      containers:
        - name: nginx
          image: nginx:1.21
          ports:
            - containerPort: 80
             

             
```

```
kubectl apply -f rs.yml 
```

### Schritt 2: Erforschen 

```
kubectl get all
## Hash entsprechend anpassen
kubectl delete po nginx-replica-set-<hash>
## Dass einer neuer Pod dazugekommen ist (seht ihr an der Zeit) 
kubectl get all
```

```
## ändern image in rs.yml
## vorher
## image: 1.23
## jetzt
image: 1.22 
```

```
kubectl apply -f .
```

```
## Gibt es neue Pods ?
kubectl get all
## Welche Image - Version
kubectl describe pods nginx-replica-set-vh6cl
```

```
## FYI 
kubectl get rs
kubectl get pods --show-labels
```

### kubectl/manifest/deployments


### Schritt 1: Erstellen 

```
cd
cd manifests
mkdir 03-deploy
cd 03-deploy 
nano deploy.yml 
```

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
spec:
  selector:
    matchLabels:
      app: nginx
  replicas: 8 
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.21
        ports:
        - containerPort: 80
        
```

```
kubectl apply -f deploy.yml 
```

### Schritt 2: Erforschen 

```
kubectl get all 
```

```
nano deploy.yml
```

```
## image ändern in deploy.yml
## vorher: image: nginx:1.21
## jetzt
image: nginx:1.23
```

```
## Anwenden und watchen 
## kubectl apply -f . ; kubectl get all; kubectl get pods -w
## Nicer:
## Ändern des images von nginx:1.22 in nginx:1.23
## danach 
kubectl apply -f . && watch kubectl get pods 
```

### Services - Aufbau


![Services Aufbau](/images/kubernetes-services.drawio.svg)

### kubectl/manifest/service


### Warum Services ? 

  * Wenn in einem Deployment bei einem Wechsel des images neue Pods erstellt werden, erhalten diese eine neue IP-Adresse
  * Nachteil: Man müsste diese dann in allen Applikation ständig ändern, die auf die Pods zugreifen.
  * Lösung: Wir schalten einen Service davor !

### Hintergrund IP-Wechsel 
 
 <img width="930" height="134" alt="image" src="https://github.com/user-attachments/assets/26c16134-1f2a-4b42-8cca-355099d08604" />

 * Image-Version wurde jetzt in Deployment geändert, Ergebnis:

<img width="939" height="137" alt="image" src="https://github.com/user-attachments/assets/fb5a665b-98a7-445b-8ec7-27f12c2267e1" />

### Example I : Service with ClusterIP 

```
cd 
cd manifests
mkdir 04-service 
cd 04-service 
```

```
nano deploy.yml 
```

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: web-nginx
spec:
  selector:
    matchLabels:
      web: my-nginx
  replicas: 2
  template:
    metadata:
      labels:
        web: my-nginx
    spec:
      containers:
      - name: cont-nginx
        image: nginx
        ports:
        - containerPort: 80
```

```
nano service.yml
```


```
apiVersion: v1
kind: Service
metadata:
  name: svc-nginx
spec:
  type: ClusterIP
  ports:
  - port: 80
    protocol: TCP
  selector:
    web: my-nginx              
```        

```
kubectl apply -f . 
```

```
## find out endpoints, if they are working
kubectl get svc svc-nginx 
kubectl describe svc svc-nginx 
```

```
## now delete pod and see changes
## -> podip will disappear from service / kubectl describe svc-nginx 
kubectl delete po web-nginx-596cdd7d5c-2lsr6
kubectl get pods -o wide

kubectl get svc svc-nginx 

## New pod (with pod-ip) is detected by service
## and now in the list of the endpoints 
kubectl describe svc svc-nginx 
```


### Example II : Short version 

```
nano service.yml
## in Zeile type: 
## ClusterIP ersetzt durch NodePort 

kubectl apply -f .
kubectl get svc
## über welche externe IP können wir zugreifen ? 
kubectl get nodes -o wide
## im client 
curl http://164.92.193.245:30280
```

### Example II : Service with NodePort (long version)

```
apiVersion: v1
kind: Service
metadata:
  name: svc-nginx
spec:
  type: NodePort
  ports:
  - port: 80
    protocol: TCP
  selector:
    app: my-nginx
```        

### Example III: Service mit LoadBalancer (ExternalIP)

```
cd; cd manifests/04-service 
nano service.yml
## in Zeile type: 
## NodePort ersetzt durch LoadBalancer  

kubectl apply -f .
kubectl get svc svc-nginx

kubectl describe svc svc-nginx
## hier heisst das nicht External-IP ->
## sondern
```

<img width="775" height="63" alt="image" src="https://github.com/user-attachments/assets/3f1db219-e5d8-4bbf-a001-17fc5eaae93f" />

```
kubectl get svc svc-nginx -w 
## spätestens nach 5 Minuten bekommen wir eine externe ip
## z.B. 41.32.44.45

curl http://41.32.44.45 
```


### Example getting a specific ip from loadbalancer (if supported) 

```
apiVersion: v1
kind: Service
metadata:
  name: svc-nginx2
spec:
  type: LoadBalancer
  # this line to get a specific ip if supported
  loadBalancerIP: 10.34.12.34
  ports:
  - port: 80
    protocol: TCP
  selector:
    web: my-nginx
```       


### Ref.

  * https://kubernetes.io/docs/concepts/services-networking/connect-applications-service/

### Hintergrund Ingress




### Ref. / Dokumentation 

  * https://matthewpalmer.net/kubernetes-app-developer/articles/kubernetes-ingress-guide-nginx-example.html

### Ingress Controller auf Digitalocean (doks) mit helm installieren


### Basics 

  * Das Verfahren funktioniert auch so auf anderen Plattformen, wenn helm verwendet wird und noch kein IngressController vorhanden
  * Ist kein IngressController vorhanden, werden die Ingress-Objekte zwar angelegt, es funktioniert aber nicht. 

### Prerequisites 

  * kubectl und helm muss eingerichtet sein 

### Walkthrough (Setup Ingress Controller) 

```
## Setup repo
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
```

```
helm install nginx-ingress ingress-nginx/ingress-nginx --namespace ingress --create-namespace  
```

```
## See when the external ip comes available
kubectl -n ingress get all
kubectl --namespace ingress get services -o wide -w nginx-ingress-ingress-nginx-controller

## Output  
NAME                                     TYPE           CLUSTER-IP     EXTERNAL-IP      PORT(S)                      AGE     SELECTOR
nginx-ingress-ingress-nginx-controller   LoadBalancer   10.245.78.34   157.245.20.222   80:31588/TCP,443:30704/TCP   4m39s   app.kubernetes.io/component=controller,app.kubernetes.io/instance=nginx-ingress,app.kubernetes.io/name=ingress-nginx

## Now setup wildcard - domain for training purpose 
## inwx.com
*.lab1.t3isp.de A 157.245.20.222 


```


### Documentation for default ingress nginx

  * https://kubernetes.github.io/ingress-nginx/user-guide/nginx-configuration/configmap/

### Beispiel Ingress


### Prerequisits

```
## Ingress Controller muss aktiviert sein 
microk8s enable ingress
```

### Walkthrough 

#### Schritt 1:

```
cd 
mkdir -p manifests
cd manifests 
mkdir abi
cd abi 
```


```
## apple.yml 
## vi apple.yml 
kind: Pod
apiVersion: v1
metadata:
  name: apple-app
  labels:
    app: apple
spec:
  containers:
    - name: apple-app
      image: hashicorp/http-echo
      args:
        - "-text=apple"
---

kind: Service
apiVersion: v1
metadata:
  name: apple-service
spec:
  selector:
    app: apple
  ports:
    - protocol: TCP
      port: 80
      targetPort: 5678 # Default port for image
```

```
kubectl apply -f apple.yml 
```

```
## banana
## vi banana.yml
kind: Pod
apiVersion: v1
metadata:
  name: banana-app
  labels:
    app: banana
spec:
  containers:
    - name: banana-app
      image: hashicorp/http-echo
      args:
        - "-text=banana"

---

kind: Service
apiVersion: v1
metadata:
  name: banana-service
spec:
  selector:
    app: banana
  ports:
    - port: 80
      targetPort: 5678 # Default port for image
```

```
kubectl apply -f banana.yml
```

#### Schritt 2:

```
## Ingress
apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: example-ingress
  annotations:
    ingress.kubernetes.io/rewrite-target: /
spec:
  ingressClassName: nginx
  rules:
  - http:
      paths:
        - path: /apple
          backend:
            serviceName: apple-service
            servicePort: 80
        - path: /banana
          backend:
            serviceName: banana-service
            servicePort: 80
```

```
## ingress 
kubectl apply -f ingress.yml
kubectl get ing 
```

### Reference 

  * https://matthewpalmer.net/kubernetes-app-developer/articles/kubernetes-ingress-guide-nginx-example.html

### Find the problem 

```
## Hints 

## 1. Which resources does our version of kubectl support 
## Can we find Ingress as "Kind" here.
kubectl api-ressources 

## 2. Let's see, how the configuration works 
kubectl explain --api-version=networking.k8s.io/v1 ingress.spec.rules.http.paths.backend.service

## now we can adjust our config 
```

### Solution

```
## in kubernetes 1.22.2 - ingress.yml needs to be modified like so.
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: example-ingress
  annotations:
    ingress.kubernetes.io/rewrite-target: /
spec:
  ingressClassName: nginx
  rules:
  - http:
      paths:
        - path: /apple
          pathType: Prefix
          backend:
            service:
              name: apple-service
              port:
                number: 80
        - path: /banana
          pathType: Prefix
          backend:
            service:
              name: banana-service
              port:
                number: 80                
```

### Install Ingress On Digitalocean DOKS

### Beispiel Ingress mit Hostnamen


### Prequisites 

  * An IngressController is running in your cluster. Ask the trainer if you are unsure.

### Aufsetzen mit Ingress-Fehler

#### Step 1: pods and services

```
cd
mkdir -p manifests
cd manifests 
mkdir abi
cd abi
```

```
nano apple.yml
```

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: apple-deployment
spec:
  selector:
    matchLabels:
      app: apple
  replicas: 8
  template:
    metadata:
      labels:
        app: apple
    spec:
      containers:
      - name: apple-app
        image: hashicorp/http-echo
        args:
        - "-text=apple-<dein-name>"
```

```
kubectl apply -f .
nano apple-service.yml
```

```
kind: Service
apiVersion: v1
metadata:
  name: apple-service
spec:
  type: ClusterIP
  selector:
    app: apple
  ports:
    - protocol: TCP
      port: 80
      targetPort: 5678 # Default port for image
```

```
kubectl apply -f .
nano banana.yml 
```

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: banana-deployment
spec:
  selector:
    matchLabels:
      app: banana
  replicas: 8
  template:
    metadata:
      labels:
        app: banana
    spec:
      containers:
      - name: banana-app
        image: hashicorp/http-echo
        args:
        - "-text=banana-<dein-name>"
```

```
nano banana-service.yml 
```

```

kind: Service
apiVersion: v1
metadata:
  name: banana-service
spec:
  type: ClusterIP
  selector:
    app: banana
  ports:
    - port: 80
      targetPort: 5678 # Default port for image
```

```
kubectl apply -f .
```

#### Step 2: Ingress 

```
nano ingress.yml
```

```
## Ingress
apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: ingress-abi
spec:
  ingressClassName: nginx
  rules:
  - host: "<euername>.lab1.t3isp.de"
    http:
      paths:
        - path: /apple
          backend:
            serviceName: apple-service
            servicePort: 80
        - path: /banana
          backend:
            serviceName: banana-service
            servicePort: 80
```

```
## ingress 
kubectl apply -f ingress.yml
kubectl get ing 
```

#### Schritt 3: Wir finden/lösen das Problem 

##### (Mini-)Schritt 2.1 api-resource und ApiVersion identifizieren 

  * Den richtige api-resource and die richtige appVersion finden 

```
## Bitte nur die ersten 3 Zeilen anzeigen -> head -n 3  
kubectl explain ingress | head -n 3
```

```
## Nur Anzeige, nicht eingeben 
## GROUP:      networking.k8s.io
## KIND:       Ingress
## VERSION:    v1
```

```
## Wir erkennen das die richtige API-Ressource
## NICHT extensions/v1beta1
## SONDERN networking.k8s.io/v1
```

##### (Mini-)Schritt 2.2. api-resource und apiVersion ändern und ausführen 

```
## ist und korrigieren das.
nano ingress.yml 
```

```
## nur 
## apiVersion ändern wie folgt
apiVersion: networking.k8s.io/v1
## ändern 
```

```
## Das gesamte File sieht jetzt so aus:
## Ingress
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: ingress-abi
  annotations:
    ingress.kubernetes.io/rewrite-target: /
    # with the ingress controller from helm, you need to set an annotation 
    # otherwice it does not know, which controller to use
    # old version... use ingressClassName instead 
    # kubernetes.io/ingress.class: nginx
spec:
  ingressClassName: nginx
  rules:
  - host: "<euername>.lab1.t3isp.de"
    http:
      paths:
        - path: /apple
          backend:
            serviceName: apple-service
            servicePort: 80
        - path: /banana
          backend:
            serviceName: banana-service
            servicePort: 80
```

``` unknown field "spec.rules[0].http.paths[0].backend.serviceName"
kubectl apply -f .
```


##### (Mini-)Schritt 2.3: Fehler verstehen 

```
## Hier der letzte Fehler aus Schritt 2.2.
Error from server (BadRequest): error when creating "ingress.yml": Ingress in version "v1" cannot be handled as a Ingress: strict decoding error: unknown field "spec.rules[0].http.paths[0].backend.serviceName", unknown field "spec.rules[0].http.paths[0].backend.servicePort", unknown field "spec.rules[1].http.paths[0].backend.serviceName", unknown field "spec.rules[1].http.paths[0].backend.servicePort"
```

```
## Auszug
unknown field "spec.rules[0].http.paths[0].backend.serviceName", unknown field "spec.rules[0].http.paths[0].backend.servicePort", unknown field

## Bedeutet
## Ich kenne das Feld spec..backend.servicePort nicht.

## Heisst aber auch:
## Das Feld spec.rules.http.paths.backend .. kenne ich schon
```

```
## Wir forschen
kubectl explain ingress.spec.rules.http.paths.backend
```

```
## Er kennt eine Eigenschaft service, aber eben nich serviceName
FIELD: backend <IngressBackend>
...
  service       <IngressServiceBackend>
    service references a service as a backend. This is a mutually exclusive
    setting with "Resource".
```

```
## Was möchte er unter service haben ? 
kubectl explain ingress.spec.rules.http.paths.backend.service
```

```
## Bingo: name 
GROUP:      networking.k8s.io
KIND:       Ingress
VERSION:    v1

FIELD: service <IngressServiceBackend>
[...]
FIELDS:
  name  <string> -required-
    name is the referenced service. The service must exist in the same namespace
    as the Ingress object.
```

```
## Ergebnis:
## Statt:
## ingress.spec.rules.http.paths.backend.serviceName
## Erwartet Kubernetes jetzt:
## ingress.spec.rules.http.paths.backend.service.name
```
 
##### (Mini-)Schritt 2.4: Lösung umsetzen

```
nano ingress.yml
````

```
## Das gesamte File sieht jetzt so aus:
## Ingress
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: example-ingress
  annotations:
    ingress.kubernetes.io/rewrite-target: /
    # with the ingress controller from helm, you need to set an annotation 
    # otherwice it does not know, which controller to use
    # old version... use ingressClassName instead 
    # kubernetes.io/ingress.class: nginx
spec:
  ingressClassName: nginx
  rules:
  - host: "<euername>.lab1.t3isp.de"
    http:
      paths:
        - path: /apple
          backend:
            service:
              name: apple-service
            servicePort: 80
        - path: /banana
          backend:
            service:
              name: banana-service
            servicePort: 80
```

```
kubectl apply -f .
## --> Fehler 
```

##### (Mini-)Schritt 2.5: Nächsten Fehler verstehen und umsetzen (servicePort) 

```
## Folgender Fehler nach kubectl apply -f .
Error from server (BadRequest): error when creating "ingress.yml": Ingress in version "v1" cannot be handled as a Ingress: strict decoding error: unknown field "spec.rules[0].http.paths[0].backend.servicePort", unknown field "spec.rules[1].http.paths[0].backend.servicePort"

## <- servicePort kennt er nicht
```

```
## Schrittweise debuggen
kubectl explain ingress.spec.rules.http.paths.backend
kubectl explain ingress.spec.rules.http.paths.backend.service
kubectl explain ingress.spec.rules.http.paths.backend.service.port
## Und er braucht auch nocht Number
kubectl explain ingress.spec.rules.http.paths.backend.service.port.number

## so würde die Eigenschaft dann im yml-file aussehen.
## service:
##   port:
##     number: 80
```

```
## Wir setzen das um
nano service.yml
```

```
## So sieht das korrigierte .yml file aus.
## Ingress
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: apps-ingress
  annotations:
    ingress.kubernetes.io/rewrite-target: /
spec:
  ingressClassName: nginx
  rules:
  - host: app1.dein-training.de
    http:
      paths:
        - path: /apple
          backend:
            service:
              name: apple-service
              port:
                number: 80
        - path: /banana
          backend:
            service:
              name: banana-service
              port:
                number: 80
```

```
kubectl apply -f .
## --> Fehler
```

##### (Mini-)Schritt 2.6: Wir beheben den letzten Fehler 

```
## Fehler
The Ingress "apps-ingress" is invalid:
* spec.rules[0].http.paths[0].pathType: Required value: pathType must be specified
* spec.rules[1].http.paths[0].pathType: Required value: pathType must be specified

## Bedeutet
pathType ist jetz ein Pflichtfeld und wir müssen es es ergänzen
```

```
## Welche Werte sind möglich ?
kubectl explain ingress.spec.rules.http.paths.pathType
```

```
[...]
Possible enum values:
     - `"Exact"` matches the URL path exactly and with case sensitivity.
     - `"ImplementationSpecific"` matching is up to the IngressClass.
     [...]
     identically to Prefix or Exact path types.
     - `"Prefix"` matches based on a URL path prefix split by '/'.
```

```
## Anpassen
nano ingress.yml
```

```
## So sieht das korrigierte .yml file aus.
## Ingress
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: apps-ingress
  annotations:
    ingress.kubernetes.io/rewrite-target: /
spec:
  ingressClassName: nginx
  rules:
  - host: app1.dein-training.de
    http:
      paths:
        - path: /apple
          pathType: Prefix  # <- EINGEFUEGT 
          backend:
            service:
              name: apple-service
              port:
                number: 80
        - path: /banana
          pathType: Prefix  # <- EINGEFUEGT 
          backend:
            service:
              name: banana-service
              port:
                number: 80

```

```
kubectl apply -f .
kubectl get ingress
```



### Reference 

  * https://matthewpalmer.net/kubernetes-app-developer/articles/kubernetes-ingress-guide-nginx-example.html

#### Old Version: Find the problem 

```
## Hints 

## 1. Which resources does our version of kubectl support 
## Can we find Ingress as "Kind" here.
kubectl api-ressources 

## 2. Let's see, how the configuration works 
kubectl explain --api-version=networking.k8s.io/v1 ingress.spec.rules.http.paths.backend.service

## now we can adjust our config 
```

### Solution

```
## in kubernetes 1.22.2 - ingress.yml needs to be modified like so.
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: example-ingress
  annotations:
    ingress.kubernetes.io/rewrite-target: /
    # with the ingress controller from helm, you need to set an annotation 
    # old version useClassName instead 
    # otherwice it does not know, which controller to use
    # kubernetes.io/ingress.class: nginx 
spec:
  ingressClassName: nginx
  rules:
  - host: "app12.lab.t3isp.de"
    http:
      paths:
        - path: /apple
          pathType: Prefix
          backend:
            service:
              name: apple-service
              port:
                number: 80
        - path: /banana
          pathType: Prefix
          backend:
            service:
              name: banana-service
              port:
                number: 80                
```

### Analyse nach Erfolg 

```
kubectl get ing
kubectl describe ing ingress-abi
```

```
## Löschen eines Deployment testweise (zum Kennenlernen)
kubectl delete -f apple.yml
kubectl describe ing ingress-abi
kubectl apply -f apple.yml
kubectl describe ing ingress-abi
```

<img width="927" height="88" alt="image" src="https://github.com/user-attachments/assets/e30140d3-fa30-4833-b55b-902e661dee80" />


```
## Testweise löschen eines Service
kubectl delete -f apple-service.yml
kubectl describe ing ingress-abi
```




```
kubectl apply -f apple-service.yml
kubectl describe ing ingress-abi
```

### Achtung: Ingress mit Helm - annotations

### Permanente Weiterleitung mit Ingress


### Example

```
## redirect.yml 
apiVersion: v1
kind: Namespace
metadata:
  name: my-namespace

---

apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  annotations:
    nginx.ingress.kubernetes.io/permanent-redirect: https://www.google.de
    nginx.ingress.kubernetes.io/permanent-redirect-code: "308"
  creationTimestamp: null
  name: destination-home
  namespace: my-namespace
spec:
  rules:
  - host: web.training.local
    http:
      paths:
      - backend:
          service:
            name: http-svc
            port:
              number: 80
        path: /source
        pathType: ImplementationSpecific
```

```
Achtung: host-eintrag auf Rechner machen, von dem aus man zugreift 

/etc/hosts 
45.23.12.12 web.training.local
```


```
curl -I  http://web.training.local/source
HTTP/1.1 308 
Permanent Redirect 

```

### Umbauen zu google ;o) 

```
This annotation allows to return a permanent redirect instead of sending data to the upstream. For example nginx.ingress.kubernetes.io/permanent-redirect: https://www.google.com would redirect everything to Google.

```

### Refs:

  * https://github.com/kubernetes/ingress-nginx/blob/main/docs/user-guide/nginx-configuration/annotations.md#permanent-redirect
  * 

### ConfigMap Example


### Schritt 1: configmap vorbereiten 
```
cd 
mkdir -p manifests 
cd manifests
mkdir configmaptests 
cd configmaptests
nano 01-configmap.yml
```

```
### 01-configmap.yml
kind: ConfigMap 
apiVersion: v1 
metadata:
  name: example-configmap 
data:
  # als Wertepaare
  database: mongodb
  database_uri: mongodb://localhost:27017
```

```
kubectl apply -f 01-configmap.yml 
kubectl get cm
kubectl get cm -o yaml
```

### Schrit 2: Beispiel als Datei 


```
nano 02-pod.yml
```

```
kind: Pod 
apiVersion: v1 
metadata:
  name: pod-mit-configmap 

spec:
  # Add the ConfigMap as a volume to the Pod
  volumes:
    # `name` here must match the name
    # specified in the volume mount
    - name: example-configmap-volume
      # Populate the volume with config map data
      configMap:
        # `name` here must match the name 
        # specified in the ConfigMap's YAML 
        name: example-configmap

  containers:
    - name: container-configmap
      image: nginx:latest
      # Mount the volume that contains the configuration data 
      # into your container filesystem
      volumeMounts:
        # `name` here must match the name
        # from the volumes section of this pod
        - name: example-configmap-volume
          mountPath: /etc/config


```

```
kubectl apply -f 02-pod.yml 
```

```
##Jetzt schauen wir uns den Container/Pod mal an
kubectl exec pod-mit-configmap -- ls -la /etc/config
kubectl exec -it pod-mit-configmap --  bash
## ls -la /etc/config 
```

### Schritt 3: Beispiel. ConfigMap als env-variablen 

```
nano 03-pod-mit-env.yml
```

```
## 03-pod-mit-env.yml 
kind: Pod 
apiVersion: v1 
metadata:
  name: pod-env-var 
spec:
  containers:
    - name: env-var-configmap
      image: nginx:latest 
      envFrom:
        - configMapRef:
            name: example-configmap

```

```
kubectl apply -f 03-pod-mit-env.yml
```

```
## und wir schauen uns das an 
##Jetzt schauen wir uns den Container/Pod mal an
kubectl exec pod-env-var -- env
kubectl exec -it pod-env-var --  bash
## env

```


### Reference: 

 * https://matthewpalmer.net/kubernetes-app-developer/articles/ultimate-configmap-guide-kubernetes.html

### Configmap MariaDB - Example


### Schritt 1: configmap 

```
cd 
mkdir -p manifests
cd manifests
mkdir cftest 
cd cftest 
nano 01-configmap.yml 
```

```
kind: ConfigMap 
apiVersion: v1 
metadata:
  name: mariadb-configmap 
data:
  # als Wertepaare
  MARIADB_ROOT_PASSWORD: 11abc432
```

```
kubectl apply -f .
kubectl get cm
kubectl get cm mariadb-configmap -o yaml
```


### Schritt 2: Deployment 
```
nano 02-deploy.yml
```

```
##deploy.yml 
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mariadb-deployment
spec:
  selector:
    matchLabels:
      app: mariadb
  replicas: 1 
  template:
    metadata:
      labels:
        app: mariadb
    spec:
      containers:
      - name: mariadb-cont
        image: mariadb:latest
        envFrom:
        - configMapRef:
            name: mariadb-configmap

```

```
kubectl apply -f .
```

### Testing 

```
## Führt den Befehl env in einem Pod des Deployments aus  
kubectl exec deployment/mariadb-deployment -- env
## eigentlich macht er das:
## kubectl exec mariadb-deployment-c6df6f959-q6swp -- env
```


### Important Sidenode 

  * If configmap changes, deployment does not know
  * So kubectl apply -f deploy.yml will not have any effect
  * to fix, use stakater/reloader: https://github.com/stakater/Reloader


### Configmap MariaDB my.cnf


### configmap zu fuss 

```
cd
mkdir -p manifests
cd manifests
mkdir mariadb-vol-cm
cd mariadb-vol-cm
```

```
nano 01-mariadb-config2.yml 
```

```
kind: ConfigMap 
apiVersion: v1 
metadata:
  name: example-configmap 
data:
  # als Wertepaare
  database: mongodb
  my.cnf: |
   [mysqld]
   slow_query_log = 1
   innodb_buffer_pool_size = 1G
  
```

```
kubectl apply -f .
```

```
nano 02-deployment.yaml 
```

```
##deploy.yml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mariadb-deployment
spec:
  selector:
    matchLabels:
      app: mariadb
  replicas: 1
  template:
    metadata:
      labels:
        app: mariadb
    spec:
      containers:
      - name: mariadb-cont
        image: mariadb:latest
        envFrom:
        - configMapRef:
            name: mariadb-configmap

        volumeMounts:
          - name: example-configmap-volume
            mountPath: /etc/mysql

      volumes:
      - name: example-configmap-volume
        configMap:
          name: example-configmap
```

```
kubectl apply -f .
kubectl exec -it deployment/mariadb-deployment -- bash 
```

```
cd /etc/mysql
ls -la
cat my.cnf
```

### Secret MariaDB - Example


### Schritt 1: secret  

```
cd 
mkdir -p manifests
cd manifests
mkdir secrettest
cd secrettest 
```

```
kubectl create secret generic mariadb-secret --from-literal=MARIADB_ROOT_PASSWORD=11abc432 --dry-run=client -o yaml > 01-secrets.yml
```

```
kubectl apply -f .
kubectl get secrets 
kubectl get secrets  mariadb-secret  -o yaml
```


### Schritt 2: Deployment 
```
nano 02-deploy.yml
```

```
##deploy.yml 
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mariadb-deployment
spec:
  selector:
    matchLabels:
      app: mariadb
  replicas: 1 
  template:
    metadata:
      labels:
        app: mariadb
    spec:
      containers:
      - name: mariadb-cont
        image: mariadb:latest
        envFrom:
        - secretRef:
            name: mariadb-secret

```

```
kubectl apply -f .
```

### Testing 

```
## Führt den Befehl env in einem Pod des Deployments aus  
kubectl exec deployment/mariadb-deployment -- env
## eigentlich macht er das:
## kubectl exec mariadb-deployment-c6df6f959-q6swp -- env
```


### Important Sidenode 

  * If configmap changes, deployment does not know
  * So kubectl apply -f deploy.yml will not have any effect
  * to fix, use stakater/reloader: https://github.com/stakater/Reloader


### Secrets aus HashiCorp Vault - 3 Wege


![Übersicht: Secrets in Kubernetes mit HashiCorp Vault](/images/vault-secrets-overview.svg)

### Das Problem: Warum reichen native Kubernetes Secrets nicht aus?

Kubernetes hat ein eingebautes `Secret`-Objekt. Auf den ersten Blick wirkt es wie eine
Lösung — aber es hat grundlegende Schwächen:

#### 1. Secrets sind nur Base64-kodiert, nicht verschlüsselt

```
kubectl get secret my-secret -o yaml
```

```yaml
apiVersion: v1
kind: Secret
data:
  password: c3VwZXJzZWNyZXQ=   # das ist nur Base64 — kein echter Schutz
```

`echo "c3VwZXJzZWNyZXQ=" | base64 -d` gibt sofort `supersecret` zurück.
**Wer Lesezugriff auf die API hat, kann alle Secrets lesen.**

#### 2. Secrets liegen im Klartext in etcd

Die Kubernetes-Datenbank (etcd) speichert Secrets standardmäßig unverschlüsselt.
Wer Zugriff auf das etcd-Backup hat, hat alle Secrets.

#### 3. Kein Audit-Trail, keine Rotation, keine Ablaufzeiten

Native Kubernetes Secrets...
- wissen nicht, wer sie wann gelesen hat
- rotieren sich nicht automatisch
- laufen nicht ab
- haben keine Zugriffsrichtlinien pro Team oder Applikation

#### 4. Secrets landen im Git — der klassische Fehler

Entwickler committen versehentlich `.env`-Dateien oder YAML-Manifeste mit Passwörtern.
Selbst nach dem Löschen sind sie in der Git-Historie.

---

### Was ist HashiCorp Vault?

HashiCorp Vault ist ein dediziertes Secret-Management-System. Es löst genau die
Probleme oben:

| Funktion | Beschreibung |
|----------|-------------|
| **Verschlüsselung** | Alle Secrets werden verschlüsselt gespeichert (AES-256) |
| **Audit-Log** | Jeder Zugriff wird protokolliert: Wer, wann, welches Secret |
| **Dynamic Secrets** | Vault kann Passwörter on-demand generieren und automatisch rotieren |
| **Lease & TTL** | Secrets laufen automatisch ab und werden erneuert |
| **Policies** | Feingranulare Zugriffsregeln: App A darf nur Secret X lesen |
| **Auth-Methoden** | Kubernetes-Pods authentifizieren sich über ihren ServiceAccount |

#### Warum ist ein Vault-Cluster (HA) wichtig?

Ein einzelner Vault-Server ist ein **Single Point of Failure**. Wenn er ausfällt,
können keine Secrets mehr abgerufen werden — Pods starten nicht, Deployments schlagen
fehl. In Produktion ist Vault deshalb immer als Cluster betrieben:

![HashiCorp Vault HA Cluster](/images/vault-ha-cluster.svg)

**Raft (Integrated Storage)** ist heute der empfohlene Weg — Vault managed seinen
eigenen Cluster ohne externes Storage-Backend.

**Wichtig:** Vault muss nach einem Neustart manuell **unsealт** werden (oder über
Auto-Unseal z.B. mit AWS KMS / Azure Key Vault). Ein versiegelter Vault antwortet
auf keine Anfragen.

---

### Die 3 Wege: Secrets aus Vault in Kubernetes

#### Weg 1: Vault Agent Injector (Sidecar)

**Wie es funktioniert:**

Kubernetes startet automatisch einen zweiten Container (Sidecar) in jedem Pod,
der Annotations hat. Dieser `vault-agent`-Container holt die Secrets aus Vault
und schreibt sie als Datei in ein geteiltes Volume im Pod.

![Vault Agent Injector](/images/vault-agent-injector.svg)

**Konfiguration per Annotations:**

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: my-app
spec:
  template:
    metadata:
      annotations:
        vault.hashicorp.com/agent-inject: "true"
        vault.hashicorp.com/role: "my-app-role"
        vault.hashicorp.com/agent-inject-secret-config: "secret/data/my-app/config"
    spec:
      serviceAccountName: my-app
      containers:
      - name: my-app
        image: my-app:1.0
        # Secret liegt unter: /vault/secrets/config
```

**Vorteile:**
- Kein Kubernetes Secret wird erstellt (Secret nie in etcd)
- Automatische Rotation (Vault Agent hält Lease aufrecht)
- Weit verbreitet, viel Dokumentation

**Nachteile:**
- Jeder Pod bekommt einen zusätzlichen Container (Ressourcenverbrauch)
- App muss Secrets als Datei lesen (nicht als Env-Variable direkt)
- Annotations im Deployment — koppelt App an Vault

---

#### Weg 2: Vault CSI Provider

**Was ist CSI?**
CSI steht für **Container Storage Interface** — ein Standard, über den Kubernetes
externe Storage-Systeme anbindet. Der **Secrets Store CSI Driver** erweitert das:
Secrets aus externen Systemen (Vault, AWS Secrets Manager, Azure Key Vault) werden
wie Volumes in den Pod gemountet.

**Wie es funktioniert:**

![Vault CSI Provider](/images/vault-csi-provider.svg)

**SecretProviderClass definieren:**

```yaml
apiVersion: secrets-store.csi.x-k8s.io/v1
kind: SecretProviderClass
metadata:
  name: vault-db-creds
spec:
  provider: vault
  parameters:
    vaultAddress: "https://vault.example.com"
    roleName: "my-app-role"
    objects: |
      - objectName: "password"
        secretPath: "secret/data/my-app/db"
        secretKey: "password"
```

**Im Deployment:**

```yaml
spec:
  containers:
  - name: my-app
    volumeMounts:
    - name: secrets
      mountPath: "/mnt/secrets"
      readOnly: true
  volumes:
  - name: secrets
    csi:
      driver: secrets-store.csi.k8s.io
      readOnly: true
      volumeAttributes:
        secretProviderClass: "vault-db-creds"
```

**Vorteile:**
- Kein Sidecar-Container nötig (leichtgewichtiger als Weg 1)
- Funktioniert mit mehreren Secret-Backends (Vault, AWS, Azure) über dieselbe API
- Kann optional auch ein Kubernetes Secret anlegen (für Env-Variablen)

**Nachteile:**
- CSI Driver muss als DaemonSet auf allen Nodes laufen
- Etwas komplexere Ersteinrichtung
- Secret steht erst beim Pod-Start bereit (kein dynamisches Nachladen)

---

#### Weg 3: External Secrets Operator (ESO)

**Was ist der External Secrets Operator?**

Der ESO ist ein Kubernetes-Operator (ein Controller, der im Cluster läuft und
`ExternalSecret`-Objekte beobachtet). Er holt Secrets aus Vault und erstellt daraus
ganz normale Kubernetes `Secret`-Objekte — die App merkt gar nicht, dass Vault
im Spiel ist.

**Wie es funktioniert:**

![External Secrets Operator](/images/vault-eso.svg)

**ExternalSecret definieren:**

```yaml
apiVersion: external-secrets.io/v1beta1
kind: ExternalSecret
metadata:
  name: my-app-db-secret
spec:
  refreshInterval: 1h          # wie oft ESO bei Vault nachfragt
  secretStoreRef:
    name: vault-backend
    kind: ClusterSecretStore
  target:
    name: my-app-db-creds      # Name des Kubernetes Secrets das erstellt wird
    creationPolicy: Owner
  data:
  - secretKey: password        # Key im Kubernetes Secret
    remoteRef:
      key: secret/my-app/db    # Pfad in Vault
      property: password
```

**Das erstellte Kubernetes Secret:**

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: my-app-db-creds        # automatisch vom ESO erstellt
data:
  password: <base64-encoded>   # von Vault geholt, automatisch aktualisiert
```

**Vorteile:**
- App braucht keinerlei Vault-Kenntnisse (nutzt normales K8s Secret)
- Einfache Migration bestehender Apps
- Automatische Rotation über `refreshInterval`
- Unterstützt viele Backends: Vault, AWS SM, GCP Secret Manager, Azure Key Vault

**Nachteile:**
- Secret existiert als Kubernetes Secret in etcd (kurzzeitig im Klartext)
- ESO muss Schreibrecht auf Kubernetes Secrets haben (RBAC beachten)

---

### Vergleich der 3 Wege

| | Vault Agent Injector | Vault CSI Provider | External Secrets Operator |
|---|---|---|---|
| **Secret in etcd?** | Nein | Nein (optional) | Ja |
| **Sidecar nötig?** | Ja | Nein | Nein |
| **App muss angepasst werden?** | Dateipfad lesen | Dateipfad lesen | Nein (normales K8s Secret) |
| **Automatische Rotation** | Ja (Live) | Nein | Ja (per Intervall) |
| **Mehrere Secret-Backends** | Nur Vault | Ja | Ja |
| **Einstiegshürde** | Mittel | Mittel | Niedrig |
| **Empfohlen für** | Vault-first Teams | Multi-Cloud | Migration / Einfachheit |

---

### Empfehlung

- **Neu mit Vault starten, höchste Sicherheit:** Vault Agent Injector oder CSI Provider —
  das Secret landet nie in etcd.
- **Bestehende Apps migrieren oder Multi-Cloud:** External Secrets Operator —
  Apps müssen nicht angepasst werden.
- **Produktion:** Immer Vault als HA-Cluster betreiben (mind. 3 Nodes, Raft Storage,
  Auto-Unseal konfiguriert).

### Security und Compliance im Betrieb von Kubernetes-Clustern


### Ueberblick: Sicherheitsschichten

![Kubernetes Security Schichtenmodell](/images/security-compliance-schichten.svg)

Kubernetes-Security ist kein einzelnes Feature, sondern ein Schichtenmodell.
Jede Schicht schliesst Angriffsvektoren auf ihrer Ebene.

| Schicht | Thema | Schluessel-Tool |
|---------|-------|----------------|
| **Cluster-Infrastruktur** | etcd-Verschluesselung, TLS, Node-Haertung | Encryption at Rest, kubeadm |
| **Zugriffssteuerung** | Wer darf was im Cluster tun? | RBAC |
| **Netzwerk** | Welche Pods duerfen miteinander reden? | Network Policies, mTLS |
| **Workload** | Wie laufen Container ab? | PSA, SecurityContext, seccomp |
| **Secrets** | Wie werden Passwörter verwaltet? | Vault, ESO, CSI |
| **Images** | Sind Images vertrauenswuerdig? | Trivy, Cosign, Admission |
| **Audit** | Was ist im Cluster passiert? | Audit Logging |
| **Policy** | Werden Regeln automatisch durchgesetzt? | Kyverno, OPA/Gatekeeper |
| **Compliance** | Entspricht der Cluster Standards? | kube-bench, CIS Benchmark |

---

### 1. RBAC — Zugriffssteuerung

#### Das Konzept

RBAC (Role-Based Access Control) regelt, welche **Subjects** (Nutzer, Gruppen, ServiceAccounts)
welche **Aktionen** auf welchen **Ressourcen** ausfuehren duerfen.

```
Subject --> RoleBinding --> Role --> Regeln (Verben auf Ressourcen)
```

**Verben:** `get`, `list`, `watch`, `create`, `update`, `patch`, `delete`

**Scope:**
- `Role` + `RoleBinding` → gilt in einem Namespace
- `ClusterRole` + `ClusterRoleBinding` → gilt cluster-weit

#### Beispiel: Lese-Only-Rolle fuer Entwickler

```
apiVersion: rbac.authorization.k8s.io/v1
kind: Role
metadata:
  name: dev-readonly
  namespace: production
rules:
- apiGroups: [""]
  resources: ["pods", "services", "configmaps"]
  verbs: ["get", "list", "watch"]
```

```
apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
  name: dev-readonly-binding
  namespace: production
subjects:
- kind: User
  name: alice
  apiGroup: rbac.authorization.k8s.io
roleRef:
  kind: Role
  name: dev-readonly
  apiGroup: rbac.authorization.k8s.io
```

#### ServiceAccounts absichern

Jeder Pod erhaelt automatisch den `default`-ServiceAccount seines Namespace.
Dieser hat in vielen Clustern mehr Rechte als noetig.

```
## Token automatisches Mounten deaktivieren
apiVersion: v1
kind: ServiceAccount
metadata:
  name: my-app
automountServiceAccountToken: false
```

```
## Nur wenn noetig: explizit im Pod aktivieren
spec:
  serviceAccountName: my-app
  automountServiceAccountToken: true
```

#### Best Practices RBAC

| Regel | Begruendung |
|-------|-------------|
| Keine `cluster-admin` fuer normale Nutzer | Kompromittiertes Konto = voller Clusterzugriff |
| Eigene ServiceAccounts pro App | Isolierte Rechte, kein shared `default` |
| `ClusterRoleBinding` nur wenn wirklich cluster-weit noetig | Scope auf Namespace begrenzen |
| Regelmaessige Auditierung mit `kubectl auth can-i` | Rechtedrift erkennen |

```
## Pruefen: Darf alice Pods in production loeschen?
kubectl auth can-i delete pods --namespace=production --as=alice
```

---

### 2. Netzwerk-Sicherheit

#### Network Policies — die Firewall in Kubernetes

Standardmaessig darf jeder Pod mit jedem Pod im Cluster kommunizieren.
Network Policies aendern das: Sie definieren explizit erlaubten Traffic.

**Wichtig:** Network Policies werden vom CNI-Plugin durchgesetzt (Calico, Cilium, Weave).
Mit `kubenet` (Default in vielen Setups) werden Policies ignoriert.

##### Default-Deny fuer einen Namespace

```
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: default-deny-all
  namespace: production
spec:
  podSelector: {}      # alle Pods im Namespace
  policyTypes:
  - Ingress
  - Egress
```

Danach muss jede erlaubte Verbindung explizit aufgemacht werden.

##### Nur Frontend darf Backend erreichen

```
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: allow-frontend-to-backend
  namespace: production
spec:
  podSelector:
    matchLabels:
      app: backend
  ingress:
  - from:
    - podSelector:
        matchLabels:
          app: frontend
    ports:
    - protocol: TCP
      port: 8080
```

#### mTLS — Verschlüsselung zwischen Diensten

Network Policies kontrollieren den Zugriff, verschluesseln aber nicht.
Fuer Verschlüsselung zwischen Pods wird **mTLS** (mutual TLS) eingesetzt.

**Optionen:**
- **Istio / Linkerd (Service Mesh):** mTLS automatisch zwischen allen Diensten
- **cert-manager:** Zertifikate fuer einzelne Dienste ausstellen

```
## Istio: PeerAuthentication fuer strict mTLS im Namespace
apiVersion: security.istio.io/v1beta1
kind: PeerAuthentication
metadata:
  name: default
  namespace: production
spec:
  mtls:
    mode: STRICT
```

---

### 3. Pod- und Container-Sicherheit

#### SecurityContext

Auf Pod- und Container-Ebene definiert der `securityContext`, mit welchen
Linux-Rechten der Prozess laeuft.

```
apiVersion: v1
kind: Pod
spec:
  securityContext:
    runAsNonRoot: true          # kein Root-User
    runAsUser: 1000
    fsGroup: 2000
    seccompProfile:
      type: RuntimeDefault      # Standard-Syscall-Filter
  containers:
  - name: app
    securityContext:
      allowPrivilegeEscalation: false   # kein sudo/setuid
      readOnlyRootFilesystem: true      # Filesystem read-only
      capabilities:
        drop: ["ALL"]                   # alle Linux Capabilities entfernen
        add: ["NET_BIND_SERVICE"]       # nur was wirklich gebraucht wird
```

#### Pod Security Admission (PSA)

PSA erzwingt Sicherheitsprofile auf Namespace-Ebene per Label.
Details und Uebungen: [Pod Security Admission](pod-security-admission.md)

| Profil | Beschreibung |
|--------|-------------|
| `privileged` | Keine Einschraenkungen |
| `baseline` | Verhindert bekannte Privilegieneskalationen |
| `restricted` | Strenge Haertung, empfohlen fuer Produktion |

```
## Namespace mit restricted-Profil
kubectl label namespace production \
  pod-security.kubernetes.io/enforce=restricted \
  pod-security.kubernetes.io/warn=restricted
```

---

### 4. Secrets-Management

Native Kubernetes Secrets haben grundlegende Schwaechen:
- Nur Base64-kodiert, nicht verschluesselt
- Liegen standardmaessig im Klartext in etcd
- Kein Audit-Trail, keine automatische Rotation

Loesungsansaetze und Details: [Secrets aus HashiCorp Vault — 3 Wege](vault-secrets-integration.md)

**Kurzuebersicht der Optionen:**

| Ansatz | Secret in etcd? | Rotation | Aufwand |
|--------|----------------|----------|---------|
| Vault Agent Injector | Nein | Automatisch (live) | Mittel |
| Vault CSI Provider | Nein | Nein | Mittel |
| External Secrets Operator | Ja (kurz) | Per Intervall | Niedrig |

---

### 5. Image Security und Supply Chain

#### Das Problem

Ein Kubernetes-Cluster ist nur so sicher wie die Images, die darin laufen.
Angreifer nutzen Images mit bekannten CVEs oder schleusen boesartige Images ein.

#### Image Scanning mit Trivy

```
## Lokales Image scannen
trivy image nginx:latest

## Nur kritische CVEs anzeigen
trivy image --severity CRITICAL,HIGH nginx:latest
```

**In CI/CD einbauen:** Pipeline schlaegt fehl, wenn Critical CVEs gefunden werden.

#### Signed Images mit Cosign (Sigstore)

Cosign ermoeglicht das Signieren von Container-Images — der Cluster prueft
vor dem Start, ob die Signatur gueltig ist.

```
## Image signieren
cosign sign --key cosign.key registry.example.com/my-app:v1.0

## Signatur pruefen
cosign verify --key cosign.pub registry.example.com/my-app:v1.0
```

**Admission Controller** (z.B. Kyverno oder Connaisseur) kann das automatisch erzwingen:
Unsigned Images werden abgelehnt, bevor der Pod startet.

#### Best Practices Image Security

| Regel | Massnahme |
|-------|-----------|
| Keine `latest`-Tags in Produktion | Immer konkrete Version pinnen |
| Minimale Base-Images | `distroless`, `alpine` statt `ubuntu` |
| Regelmässiges Scannen | Trivy in CI/CD und als Scheduled Job im Cluster |
| Eigene Registry | Nur gepruefe Images aus interner Registry erlauben |
| Image Signing | Cosign + Admission Controller |

---

### 6. Audit Logging

#### Was wird protokolliert?

Das Kubernetes Audit Log erfasst alle API-Aufrufe: Wer hat wann was getan?

```
Jeder API-Call durchlaeuft 4 Stufen:
RequestReceived → ResponseStarted → ResponseComplete → Panic
```

#### Audit Policy konfigurieren

Die Policy legt fest, welche Events auf welchem Level aufgezeichnet werden:

| Level | Was wird gespeichert |
|-------|---------------------|
| `None` | Nichts |
| `Metadata` | Nur Metadaten (Wer, Was, Wann) — kein Body |
| `Request` | Metadaten + Request-Body |
| `RequestResponse` | Metadaten + Request + Response-Body |

```
## /etc/kubernetes/audit-policy.yaml
apiVersion: audit.k8s.io/v1
kind: Policy
rules:
## Secrets: nur Metadaten (kein Klartext-Inhalt im Log)
- level: Metadata
  resources:
  - group: ""
    resources: ["secrets"]

## Lesezugriffe auf pods: ignorieren (zu viel Rauschen)
- level: None
  verbs: ["get", "list", "watch"]
  resources:
  - group: ""
    resources: ["pods"]

## Alles andere: Metadaten
- level: Metadata
```

```
## kube-apiserver starten mit:
--audit-log-path=/var/log/kubernetes/audit.log
--audit-policy-file=/etc/kubernetes/audit-policy.yaml
--audit-log-maxage=30
--audit-log-maxbackup=10
--audit-log-maxsize=100
```

#### Typische Audit-Abfragen

```
## Wer hat das Secret "db-password" gelesen?
grep '"resource":"secrets"' audit.log | grep '"verb":"get"' | grep 'db-password'

## Welche Pods wurden heute geloescht?
grep '"verb":"delete"' audit.log | grep '"resource":"pods"'
```

---

### 7. etcd-Verschlüsselung (Encryption at Rest)

#### Das Problem

etcd ist die Kubernetes-Datenbank. Sie speichert alle Objekte — auch Secrets.
Standardmaessig liegen diese Secrets im Klartext in etcd.

Wer Zugriff auf ein etcd-Backup hat, kann alle Secrets auslesen:

```
## Ohne Encryption at Rest:
ETCDCTL_API=3 etcdctl get /registry/secrets/default/my-secret | strings
## → Klartext-Passwort sichtbar
```

#### Encryption at Rest aktivieren

```
## /etc/kubernetes/encryption-config.yaml
apiVersion: apiserver.config.k8s.io/v1
kind: EncryptionConfiguration
resources:
- resources:
  - secrets
  providers:
  - aescbc:
      keys:
      - name: key1
        secret: <base64-encoded-32-byte-key>
  - identity: {}   # Fallback fuer bereits gespeicherte, unverschluesselte Secrets
```

```
## kube-apiserver starten mit:
--encryption-provider-config=/etc/kubernetes/encryption-config.yaml
```

```
## Bestehende Secrets re-encrypten:
kubectl get secrets --all-namespaces -o json | kubectl replace -f -
```

**Empfehlung fuer Produktion:** Statt AES-CBC besser einen KMS-Provider nutzen
(AWS KMS, GCP KMS, Azure Key Vault) — der Schluessel liegt dann ausserhalb von etcd.

---

### 8. Policy Enforcement: Kyverno und OPA/Gatekeeper

#### Warum Policy Enforcement?

RBAC regelt Zugriff, aber nicht die Qualitaet von Manifesten.
Policy Engines pruefen beim Erstellen/Aendern von Ressourcen:
- Hat das Deployment Ressource-Limits?
- Laeuft der Container als root?
- Ist das Image aus der erlaubten Registry?

#### Kyverno

Kyverno arbeitet nativ mit Kubernetes-Manifesten — keine eigene Sprache noetig.

```
## Policy: Alle Pods muessen Ressource-Limits haben
apiVersion: kyverno.io/v1
kind: ClusterPolicy
metadata:
  name: require-resource-limits
spec:
  validationFailureAction: Enforce   # oder Audit
  rules:
  - name: check-container-resources
    match:
      any:
      - resources:
          kinds: ["Pod"]
    validate:
      message: "Ressource-Limits sind Pflicht."
      pattern:
        spec:
          containers:
          - name: "*"
            resources:
              limits:
                memory: "?*"
                cpu: "?*"
```

```
## Policy: Nur Images aus eigener Registry erlauben
apiVersion: kyverno.io/v1
kind: ClusterPolicy
metadata:
  name: allowed-registries
spec:
  validationFailureAction: Enforce
  rules:
  - name: check-registry
    match:
      any:
      - resources:
          kinds: ["Pod"]
    validate:
      message: "Nur registry.example.com ist erlaubt."
      pattern:
        spec:
          containers:
          - name: "*"
            image: "registry.example.com/*"
```

#### OPA/Gatekeeper

OPA (Open Policy Agent) + Gatekeeper bietet mehr Flexibilitaet durch Rego-Sprache,
ist aber komplexer.

```
## ConstraintTemplate (Rego-Logik)
apiVersion: templates.gatekeeper.sh/v1
kind: ConstraintTemplate
metadata:
  name: k8srequiredlabels
spec:
  crd:
    spec:
      names:
        kind: K8sRequiredLabels
  targets:
  - target: admission.k8s.gatekeeper.sh
    rego: |
      package k8srequiredlabels
      violation[{"msg": msg}] {
        not input.review.object.metadata.labels["team"]
        msg := "Label 'team' fehlt."
      }
```

#### Vergleich Kyverno vs. OPA/Gatekeeper

| | Kyverno | OPA/Gatekeeper |
|---|---------|----------------|
| **Sprache** | YAML/JSON (nativ K8s) | Rego (eigene DSL) |
| **Einstieg** | Einfach | Steiler |
| **Flexibilitaet** | Gut | Sehr hoch |
| **Mutation** | Ja (Manifeste anpassen) | Ja |
| **Community** | Wachsend, CNCF | Etabliert, CNCF |
| **Empfehlung** | Fuer die meisten Teams | Wenn komplexe Logik noetig |

---

### 9. Compliance-Frameworks und Tools

#### CIS Kubernetes Benchmark

Der CIS (Center for Internet Security) Benchmark ist der meistgenutzte
Standard fuer Kubernetes-Haertung. Er prueft u.a.:

- API-Server-Konfiguration (TLS, Admission Plugins)
- etcd-Sicherheit
- kubelet-Einstellungen
- RBAC und Service Accounts
- Network Policies

**Automatische Pruefung mit kube-bench:**

```
## kube-bench als Job im Cluster ausfuehren
kubectl apply -f https://raw.githubusercontent.com/aquasecurity/kube-bench/main/job.yaml
kubectl logs -l app=kube-bench
```

Ausgabe zeigt PASS/FAIL/WARN pro Check mit konkreter Behebungsanweisung.

#### NSA/CISA Kubernetes Hardening Guide

Die US-Behoerden NSA und CISA haben 2021 einen Leitfaden veroeffentlicht.
Schwerpunkte:

| Bereich | Empfehlungen |
|---------|-------------|
| Pod Security | Non-root, read-only FS, keine privilegierten Pods |
| Network Policies | Default-deny, explizite Freigaben |
| Authentication | MFA fuer Cluster-Zugriff, kubeconfig sichern |
| Audit Logging | Aktivieren und zentral sammeln |
| Updates | Regelmaessige Updates von Kubernetes und Nodes |

#### kube-score

kube-score analysiert Kubernetes-Manifeste statisch — ideal in CI/CD:

```
## Deployment pruefe
kube-score score deployment.yml
```

```
[CRITICAL] Container Security Context
    · app -> Container has no configured security context
      Set securityContext to run the container in a more secure context.

[WARNING] Container Resources
    · app -> CPU limit is not set
```

---

### 10. Sicherheits-Checkliste fuer Produktion

#### Zugriffssteuerung

- [ ] RBAC aktiviert und konfiguriert (kein Wildcard-Admin fuer normale Nutzer)
- [ ] ServiceAccounts pro App, `automountServiceAccountToken: false` als Default
- [ ] kubeconfig-Dateien nicht committet, Rotation konfiguriert
- [ ] `kubectl auth can-i` regelmaessig auditieren

#### Netzwerk

- [ ] Network Policies: Default-Deny pro Namespace, explizite Freigaben
- [ ] CNI-Plugin unterstuetzt Network Policies (Calico, Cilium, Weave)
- [ ] mTLS in Produktion (Service Mesh oder cert-manager)

#### Workload

- [ ] PSA: Namespace mit `baseline` oder `restricted` konfiguriert
- [ ] `runAsNonRoot: true`, `allowPrivilegeEscalation: false`
- [ ] `readOnlyRootFilesystem: true` wo moeglich
- [ ] Ressource-Limits fuer alle Container gesetzt

#### Secrets

- [ ] Encryption at Rest fuer etcd aktiviert (oder KMS-Provider)
- [ ] Kein Klartext in ConfigMaps, kein Secret im Git
- [ ] Secret-Management-System (Vault, ESO) fuer Produktion evaluiert

#### Images

- [ ] Kein `latest`-Tag in Produktion
- [ ] Image Scanning in CI/CD (Trivy)
- [ ] Nur Images aus vertrauenswuerdiger Registry
- [ ] Image Signing evaluieren (Cosign)

#### Audit und Compliance

- [ ] Audit Logging aktiviert, Policy definiert
- [ ] Logs zentral gesammelt (SIEM, Elastic, Loki)
- [ ] kube-bench regelmaessig ausgefuehrt
- [ ] Policy Engine (Kyverno oder OPA/Gatekeeper) fuer kritische Regeln

#### Updates

- [ ] Kubernetes-Version aktuell (maximal 2 Minor-Versionen hinter aktuellem Release)
- [ ] Node-OS regelmaessig gepatcht
- [ ] Automatische CVE-Alerts fuer verwendete Images

---

### Weiterführende Seiten in diesem Training

- [Kubernetes Tipps Hardening](tipps-hardening.md)
- [Pod Security Admission — Uebung](pod-security-admission.md)
- [Secrets aus HashiCorp Vault — 3 Wege](vault-secrets-integration.md)
- [Network Policies — Beispiele Ingress/Egress](#beispiele-ingress-egress-networkpolicy)

## Kubernetes Praxis (Teil 2) - API Objekte 

### Hintergrund Statefulsets


### Why ?

  * stable network identities (always the same name across restarts)  in contrast to deployments

```
Server:    10.0.0.10
Address 1: 10.0.0.10 kube-dns.kube-system.svc.cluster.local

Name:      web-0.nginx
Address 1: 10.244.1.6

nslookup web-1.nginx
Server:    10.0.0.10
Address 1: 10.0.0.10 kube-dns.kube-system.svc.cluster.local

Name:      web-1.nginx
Address 1: 10.244.2
```

```
The Pods' ordinals, hostnames, SRV records, and A record names have not changed, but the IP addresses associated with the Pods may have changed.
```




### Features 

  * Scaling Up: Ordered creation on scaling (web 2 till ready then web-3 till ready and so on) 

```
StatefulSet controller created each Pod sequentially 
with respect to its ordinal index, 

and it waited for each Pod's predecessor to be Running and Ready 

before launching the subsequent Pod
```

  * Scaling Down: last created pod is torn down firstly, till finished, then the one before

```
The controller deleted one Pod at a time, 
in reverse order with respect to its ordinal index, 
and it waited for each to be completely shutdown before deleting the next.
```

  * VolumeClaimTemplate (In addition if the pod is scaled the copies will have their own storage)
    * Plus: When you delete it, it gets recreated and claims the same persistentVolumeClaim 

```
volumeClaimTemplates:
  - metadata:
      name: www
    spec:
      accessModes: [ "ReadWriteOnce" ]
      resources:
        requests:
          storage: 1Gi
```

   * Update Strategy: RollingUpdate / OnDelete 
   * Feature: Staging an Update with Partitions
     * https://kubernetes.io/docs/tutorials/stateful-application/basic-stateful-set/#staging-an-update
   * Feature: Rolling out a canary 
     * https://kubernetes.io/docs/tutorials/stateful-application/basic-stateful-set/#rolling-out-a-canary

### Übung Statefulsets


### Overview 

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/7c3ba7c6-5695-4261-8b17-8eafd683ae6a)

###

```
cd
mkdir -p manifests
cd manifests
mkdir sts
cd sts
```

```
nano sts.yaml 
```

### 

```
apiVersion: v1
kind: Service
metadata:
  name: nginx
  labels:
    app: nginx
spec:
  ports:
  - port: 80
    name: web
  clusterIP: None
  selector:
    app: nginx
---
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: web
spec:
  serviceName: "nginx"
  replicas: 2
  selector:
    matchLabels:
      app: nginx
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: registry.k8s.io/nginx-slim:0.8
        ports:
        - containerPort: 80
          name: web
```

```
kubectl apply -f .
```


### Auflösung Namen.

```
ping web-0.nginx 
ping web-1.nginx 
```

### Test der Auflösung 

```
kubectl run --rm -it podtester --image=busybox
```

```
/ # ping web-0.nginx
/ # ping web-1.nginx
/ # exit 
```

### Referenz 

  * https://kubernetes.io/docs/tutorials/stateful-application/basic-stateful-set/


## Kubernetes Praxis (Teil 3)

### Using private registry


### Create config.json with login docker

#### Step 1: On docker machine 

```
docker login
```

```
cat ~/.docker/config.json
```

```
{
        "auths": {
                "https://index.docker.io/v1/": {
                        "auth": "ZG9............"
                }
}
```

```
copy to kubectl client
```

#### Step 2: on kubectl-client machine 

```
cd
mkdir -p manifests
cd manifests
mkdir docker
cd docker
```

```
## Für die Teilnehmer
cp /tmp/config.json .

```
```
kubectl create secret generic docker-credentials \
    --from-file=.dockerconfigjson=config.json \
    --type=kubernetes.io/dockerconfigjson \
    --dry-run=client -o yaml > secret.yaml 
```

```
cat secret.yaml
```

```
## umbenennen, damit es nicht von kubectl apply gelesen wird
mv config.json config.json.bkup 
```

```
kubectl apply -f .
```

```
nano pod.yaml 
```

```
apiVersion: v1
kind: Pod
metadata:
  name: dockertrainereu-pod
spec:
  containers:
  - name: private-container
    image: dockertrainereu/jm-hello-web
  imagePullSecrets:
  - name: docker-credentials
```

```
kubectl apply -f .
kubectl get pods dockertrainereu-pod
kubectl describe pods dockertrainereu-pod
```

## Kubernetes Ingress

### Ingress Controller on Detail


![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/abe9798c-eb2b-4afc-bd17-6bca294bfeaf)

### Traefik mit Helm installieren


```
helm repo add traefik https://traefik.github.io/charts

helm upgrade -n ingress --install traefik traefik/traefik --version 39.0.8 --create-namespace --skip-crds --reset-values

kubectl -n ingress get pods
kubectl -n ingress get svc
helm -n ingress status traefik 

## Use special crds helm chart instead, because it does not deploy crds for gateway-api by default
## We get an error on digitalocean doks
helm -n ingress upgrade --install traefik-crds traefik/traefik-crds --version 1.16.0 --reset-values 
```

### Beispiel Ingress Traefik mit Hostnamen


### Step 1: Walkthrough 

```
cd
mkdir -p manifests 
cd manifests
mkdir abi 
cd abi
```

```
nano apple-deploy.yml 
```

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: apple-app
  labels:
    app: apple
spec:
  replicas: 1
  selector:
    matchLabels:
      app: apple
  template:
    metadata:
      labels:
        app: apple
    spec:
      containers:
        - name: web
          image: hashicorp/http-echo
          args:
            - "-text=apple-<euer-name>"
```

```
nano apple-svc.yaml
```


```
kind: Service
apiVersion: v1
metadata:
  name: apple-service
spec:
  type: ClusterIP
  selector:
    app: apple
  ports:
    - protocol: TCP
      port: 80
      targetPort: 5678 # Default port for image
```

```
kubectl apply -f .
```

```
nano banana-deploy.yml
```

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: banana-app
  labels:
    app: banana
spec:
  replicas: 1
  selector:
    matchLabels:
      app: banana
  template:
    metadata:
      labels:
        app: banana
    spec:
      containers:
        - name: web
          image: hashicorp/http-echo
          args:
            - "-text=banana-<euer-name>"
```

```
nano banana-svc.yaml
```

```
kind: Service
apiVersion: v1
metadata:
  name: banana-service
spec:
  type: ClusterIP
  selector:
    app: banana
  ports:
    - port: 80
      targetPort: 5678 # Default port for image
```

```
kubectl apply -f .
```

### Step 2: Testing connection by podIP and Service 

```
kubectl get svc
kubectl get pods -o wide
kubectl run podtest --rm -it --image busybox
```

```
/ # wget -O - http://<pod-ip>:5678 
/ # wget -O - http://<cluster-ip>
```

### Step 3: Walkthrough 

```
nano ingress.yml
```

```
## Ingress
apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: example-ingress
spec:
  ingressClassName: traefik
  rules:
  - host: "<euername>.appv3.do.t3isp.de"
    http:
      paths:
        - path: /apple
          backend:
            serviceName: apple-service
            servicePort: 80
        - path: /banana
          backend:
            serviceName: banana-service
            servicePort: 80
```

```
## ingress 
kubectl apply -f ingress.yml
```

### Reference 

  * https://matthewpalmer.net/kubernetes-app-developer/articles/kubernetes-ingress-guide-nginx-example.html

### Step 4: Find the problem 

#### Fix 4.1: Fehler: no matches kind "Ingress" in version "extensions/v1beta1"

```
## Gibt es diese Landkarte überhaupt
kubectl api-versions
## auf welcher Landkarte/Gruppe befindet sich Ingress jetzt 
kubectl explain ingress | head
## -> jetzt auf networking.k8s.io/v1 

```

```
nano ingress.yml
```

```
## auf apiVersion: extensions/v1beta1
## wird -> networking.k8s.io/v1
```

```
kubectl apply -f .
```

#### Fix 4.2: Bad Request unkown field ServiceName / ServicePort 


```
## was geht für die Property backend 
kubectl explain ingress.spec.rules.http.paths.backend
## und was geht für service
kubectl explain ingress.spec.rules.http.paths.backend.service
```

```
nano ingress.yml
```

```
## Wir ersetzen 
## serviceName: apple-service 
## durch:
## service: 
##   name: apple-service 

## das gleiche für banana 
```

```
kubectl apply -f . 
```


#### Fix 4.3. BadRequest unknown field servicePort

```
## was geht für die Property backend 
kubectl explain ingress.spec.rules.http.paths.backend
## und was geht für service
kubectl explain ingress.spec.rules.http.paths.backend.service
## number 
kubectl explain ingress.spec.rules.http.paths.backend.service.port
```

```
## neue Variante sieht so aus
backend:
  service:
    name: apple-service
    port:
      number: 80
## das gleich für banana-service
```

```
kubectl apply -f .
```


#### Fix 4.4. pathType must be specificied 

```
## Was macht das ?
kubectl explain ingress.spec.rules.http.paths.pathType
```

```
      paths:
        - path: /apple
          pathType: Prefix
          backend:
            service:
              name: apple-service
              port:
                number: 80
        - path: /banana
          pathType: Exact 
          backend:
            service:
              name: banana-service
              port:
                number: 80                
```

```
kubectl apply -f .
kubectl get ingress example-ingress
```

### Step 5: bereits fertige Lösung 

```
nano ingress.yml
```

```
## Ingress
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: example-ingress
spec:
  ingressClassName: traefik
  rules:
  - host: "<euername>.appv3.do.t3isp.de"
    http:
      paths:
        - path: /apple
          pathType: Exact
          backend:
            service:
              name: apple-service
              port:
                number: 80
        - path: /banana
          pathType: Prefix 
          backend:
            service:
              name: banana-service
              port:
                number: 80
```

```
## ingress 
kubectl apply -f ingress.yml
kubectl describe ingress 
```

### Step 6: Testing 

```
## mit describe herausfinden, ob er die services gefunden hat
kubectl describe ingress example-ingress
```

```
## Im Browser auf:
## hier euer Name 
http://jochen.appv3.do.t3isp.de/apple
http://jochen.appv3.do.t3isp.de/apple/
http://jochen.appv3.do.t3isp.de/apple/foo 
http://jochen.appv3.do.t3isp.de/banana
## geht nicht 
http://jochen.appv3.do.t3isp.de/banana/nix
```



### Https/LetsEncrypt mit Traefik


### Prerequisites 

  * abi-projekt muss existieren

### Trainer: Schritt 1: cert-manager installieren 

```
helm repo add jetstack https://charts.jetstack.io
helm upgrade --install cert-manager jetstack/cert-manager \
--namespace cert-manager --create-namespace \
--version v1.19.2 \
--set crds.enabled=true \
--reset-values
```

  * Ref: https://artifacthub.io/packages/helm/cert-manager/cert-manager

### Trainer: Schritt 2: Create ClusterIssuer (gets certificates from Letsencrypt)

```
cd
mkdir -p manifests/cert-manager
cd manifests/cert-manager
nano cluster-issuer.yaml
```



```
## cluster-issuer.yaml
apiVersion: cert-manager.io/v1
kind: ClusterIssuer
metadata:
  name: letsencrypt-prod
spec:
  acme:
    server: https://acme-v02.api.letsencrypt.org/directory
    # Email-Adresse ändern - example.com ist nicht erlaubt 
    email: your-email@example.com
    privateKeySecretRef:
      name: letsencrypt-prod
    solvers:
    - http01:
        ingress:
          class: traefik
```

```
kubectl apply -f .
## Should be True 
kubectl get clusterissuer 
```


### Schritt 3: Ingress-Objekt mit TLS erstellen 

```
cd
cd manifests/abi
```

```
nano ingress.yml
```

```
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: example-ingress
  annotations:
    cert-manager.io/cluster-issuer: "letsencrypt-prod"
spec:
  ingressClassName: traefik
  tls:
  - hosts:
    - <dein-name>.appv2.do.t3isp.de
    secretName: example-tls

  rules:
  - host: "<dein-name>.appv2.do.t3isp.de"
    http:
      paths:
        - path: /apple
          pathType: Prefix
          backend:
            service:
              name: apple-service
              port:
                number: 80
        - path: /banana
          pathType: Exact
          backend:
            service:
              name: banana-service
              port:
                number: 80
```

```
kubectl apply -f .
```

 * Interessent, der cert-manager erstellt kurz ein Ingress - Objekt

<img width="1057" height="172" alt="image" src="https://github.com/user-attachments/assets/54dce6f5-9d53-4ce4-ac79-dcfe095f77b5" />

### Schritt 4: Herausfinden, ob Zertifikate erstellt werden 

```
kubectl describe certificate example-tls
```
```
## muss auf True stehen 
kubectl get cert
```

<img width="565" height="60" alt="image" src="https://github.com/user-attachments/assets/8d492fdf-a051-4b04-95cf-a62bdb3d0964" />

```
## Certificate Request 
kubectl get cr
## da ist das Zertfikat drin 
kubectl get secret example-tls
kubectl get orders 
```

#### Debugging 

  * Solange das Zertifikat nicht bestätigt bei der ACME-Anfrage (Challenge), seht ihr das noch unter

```
kubectl get challenges
```

#### Verschlüsselungstiefe ehöhen

  * Standardmäßig 2048bit

```
    # Hier legst du die Verschlüsselungstiefe fest
    cert-manager.io/private-key-algorithm: "RSA"
    cert-manager.io/private-key-size: "4096"

```


### Schritt 5: Testen

   * Aufruf der Subdomain im Browser (mit https): z.B. https://jochen.app.do.t3isp.de/banana

### Ref: 

  * https://hbayraktar.medium.com/installing-cert-manager-and-nginx-ingress-with-lets-encrypt-on-kubernetes-fe0dff4b1924

## ServiceMesh

### Istio — Service Mesh Überblick


### Was ist ein Service Mesh?

Ab einer gewissen Anzahl von Microservices entsteht ein fundamentales Problem:
Jeder Service muss sich selbst um **Sicherheit, Fehlertoleranz, Logging und Routing** kümmern.
Das führt zu dupliziertem Code in jedem Service — in unterschiedlichen Sprachen, mit unterschiedlicher Qualität.

Ein **Service Mesh** löst das auf Infrastrukturebene — ohne Änderungen am Applikationscode.

**Istio** ist das bekannteste Service Mesh für Kubernetes.

---

### Architektur

Istio besteht aus zwei Ebenen:

| Ebene | Komponente | Aufgabe |
|---|---|---|
| **Control Plane** | Istiod | Konfiguration, Zertifikate, Service Discovery |
| **Data Plane** | Envoy Proxy (Sidecar) | Übernimmt den gesamten ein/ausgehenden Traffic |

Der **Envoy-Proxy** wird als zweiter Container automatisch in jeden Pod injiziert.
Die Applikation merkt davon nichts — der Traffic läuft transparent durch den Proxy.

![Istio Architektur](/images/istio-architektur.svg)

Aktivierung für einen Namespace:

```
kubectl label namespace default istio-injection=enabled
```

---

### Die 5 Kern-Features

![Istio Features](/images/istio-features.svg)

#### 1. mTLS — Mutual TLS

Alle Verbindungen zwischen Services werden **automatisch verschlüsselt und authentifiziert**.
Kein Service kann sich gegenüber einem anderen als jemand anderes ausgeben.

```
## Alle Verbindungen im Namespace verschluesseln
apiVersion: security.istio.io/v1beta1
kind: PeerAuthentication
metadata:
  name: default
spec:
  mtls:
    mode: STRICT
```

#### 2. Traffic Management

Feingranulares Routing ohne Ingress-Magie — direkt auf Service-Ebene.

```
## 90% auf v1, 10% auf v2 (Canary Release)
apiVersion: networking.istio.io/v1alpha3
kind: VirtualService
metadata:
  name: backend
spec:
  http:
  - route:
    - destination:
        host: backend
        subset: v1
      weight: 90
    - destination:
        host: backend
        subset: v2
      weight: 10
```

#### 3. Observability — ohne Code

Istio generiert automatisch für jeden Service:
- **Metrics** (Requests/s, Latenz, Fehlerrate) → Prometheus
- **Distributed Tracing** → Jaeger
- **Service Graph** (wer spricht mit wem) → Kiali

Kein `import logging` oder SDK-Integration nötig.

#### 4. Resilience

Circuit Breaker, Retries und Timeouts werden zentral konfiguriert:

```
apiVersion: networking.istio.io/v1alpha3
kind: DestinationRule
metadata:
  name: backend
spec:
  host: backend
  trafficPolicy:
    outlierDetection:
      consecutive5xxErrors: 3
      interval: 10s
      baseEjectionTime: 30s
```

Dazu: **Fault Injection** zum gezielten Testen von Fehlerszenarien (Chaos Engineering).

#### 5. Authorization Policies

Wer darf wen aufrufen — auf Basis von Service-Identitäten, nicht IP-Adressen:

```
## Nur Frontend darf Backend aufrufen
apiVersion: security.istio.io/v1beta1
kind: AuthorizationPolicy
metadata:
  name: backend-allow-frontend
spec:
  selector:
    matchLabels:
      app: backend
  rules:
  - from:
    - source:
        principals: ["cluster.local/ns/default/sa/frontend"]
```

---

### Wann macht Istio Sinn?

| Situation | Empfehlung |
|---|---|
| < 5 Services, monolithisch | Kein Service Mesh nötig |
| 5–15 Services, wachsend | Service Mesh evaluieren |
| > 15 Services, Multi-Team | Service Mesh sinnvoll |
| Compliance/Zero Trust Pflicht | Service Mesh notwendig |

**Kosten:** Istio bringt Komplexität und Ressourcenoverhead (Envoy-Sidecars).
Der Break-Even liegt da, wo der Aufwand für manuelle Cross-Cutting-Concerns größer wird.

---

### Istio ohne Sidecars — Ambient Mesh

Seit Istio 1.18 gibt es den **Ambient Mode**: statt Sidecars ein Node-Level-Proxy (ztunnel).
Weniger Overhead, einfacheres Upgrade — noch in Entwicklung, aber produktionsreif.

Mehr dazu: https://istio.io/latest/blog/2022/introducing-ambient-mesh/

### Why a ServiceMesh ?


### What is a service mesh ?

```
A service mesh is an infrastructure layer
that gives applications capabilities
like zero-trust security, observability,
and advanced traffic management, without code changes.
```

### Advantages / Features 

 1. Observability & monitoring
 1. Traffic management
 1. Resilience & Reliability 
 1. Security
 1. Service Discovery

#### Observability & monitoring

  * Service mesh offers:
    * valuable insights into the communication between services
    * effective monitoring to help in troubleshooting application errors.

#### Traffic management

  * Service mesh offers:
    * intelligent request distribution
    * load balancing, 
    * support for canary deployments. 
    * These capabilities enhance resource utilization and enable efficient traffic management

#### Resilience & Reliability 

  * By handling retries, timeouts, and failures,
    * service mesh contributes to the overall stability and resilience of services
    * reducing the impact of potential disruptions.

#### Security

  * Service mesh enforces security policies, and handles authentication, authorization, and encryption
    * ensuring secure communication between services and eventually, strengthening the overall security posture of the application.

#### Service Discovery

  * With service discovery features, service mesh can simplify the process of locating and routing services dynamically
  * adapting to system changes seamlessly. This enables easier management and interaction between services.

### Overall benefits 

```
Microservices communication:
Adopting a service mesh can simplify the implementation of a microservices architecture by abstracting away infrastructure complexities.
It provides a standardized approach to manage and orchestrate communication within the microservices ecosystem.
```


### How does a ServiceMeshs work? (example istio


![image](https://github.com/user-attachments/assets/ad858ca2-2bdc-4604-beef-f543f833e56f)

![image](https://github.com/user-attachments/assets/cad96bcb-8fb8-445c-b371-5a3b728a0a5f)
* Source: kubebyexample.com 

### istio security features


### Overview 

![image](https://github.com/user-attachments/assets/c31b0e10-bdb9-43e1-a162-274711079e94)

### Security needs of microservices 

![image](https://github.com/user-attachments/assets/35092c36-ffd8-428b-bf71-b60ff3749fb7)

### Implementation of security 

![image](https://github.com/user-attachments/assets/2fce84cf-4483-4772-aabf-c27d099e303e)


### istio-service mesh - ambient mode


### Light: Only Layer 4 per node (ztunnel) 

  * No sidecar (envoy-proxy) per Pod, but one ztunnel agent per Node (Layer 4)
  * Enables security features (mtls, traffic encryption)

#### Like so:

  ![image](https://github.com/user-attachments/assets/755931d7-5bdd-43c9-8f93-28e8ee0b2bf3)

### Full fledged: Layer 4 (ztunnel) per Node & Layer 7 per Namespace

  * One waypoint - proxy is rolled out per Namespace, which connects to the ztunnel agents 

![image](https://github.com/user-attachments/assets/a2aadab7-2ec0-446f-a35a-e972b8ac46b8)

#### Features in "fully-fledged" - ambient - mode 

![image](https://github.com/user-attachments/assets/30b89a37-cb71-46e9-a395-aafb593ebb12)


### Advantages:

  * Less overhead
  * One can start step-by-step moving towards a mesh (Layer 4 firstly and if wanted Layer 7 for specicfic namespaces)
  * Old pattern: sidecar and new pattern: ambient can live side by side. 

### istio-traffic-management


  * https://istio.io/latest/docs/concepts/traffic-management/#retries

### Performance comparison - baseline,sidecar,ambient


  * https://livewyer.io/blog/2024/06/06/comparison-of-service-meshes-part-two/
  * https://github.com/livewyer-ops/poc-servicemesh2024/blob/main/docs/test-report-istio.md

### Übung: JWT-Token mit RBAC (RequestAuthentication + AuthorizationPolicy)


### Grundlagen 

#### Was ist JWKS ? 

**JWKS = JSON Web Key Set**

Ein JSON-Dokument, das die **öffentlichen Schlüssel** enthält, mit denen JWTs (JSON Web Tokens) verifiziert werden können.

**Typisches Format:**
```json
{
  "keys": [
    {
      "kty": "RSA",
      "kid": "abc123",
      "use": "sig",
      "n": "0vx7agoebGc...",
      "e": "AQAB"
    }
  ]
}
```

**Wie es funktioniert:**
1. Ein Identity Provider (Keycloak, Auth0, Google etc.) signiert JWTs mit seinem **Private Key**
2. Der JWKS-Endpunkt (z.B. `https://idp.example.com/.well-known/jwks.json`) stellt die zugehörigen **Public Keys** bereit
3. Istio (oder ein anderer Verifier) holt sich die Keys von dort und prüft damit die JWT-Signatur

**In Istio konkret** — `RequestAuthentication`:
```yaml
apiVersion: security.istio.io/v1
kind: RequestAuthentication
spec:
  jwtRules:
  - issuer: "https://idp.example.com"
    jwksUri: "https://idp.example.com/.well-known/jwks.json"
```

Istio cached die Keys und rotiert automatisch mit, wenn der IDP neue Keys veröffentlicht (via `kid` — Key ID).

**Kurz:** JWKS ist der standardisierte Weg, wie ein JWT-Verifier an die Public Keys kommt, ohne sie manuell konfigurieren zu müssen.

#### Begriffe 

**`kid`** (Key ID) — Eindeutige ID des Schlüssels. Damit weiß der Verifier, welcher Key aus dem Set zum JWT passt (das JWT hat `kid` im Header).

**`use`** (Public Key Use) — Wofür der Key gedacht ist:
- `"sig"` = Signaturprüfung (Standard bei JWT)
- `"enc"` = Verschlüsselung

**`n`** (Modulus) — Der RSA-Modulus, Base64url-kodiert. Das ist der mathematische Kern des öffentlichen RSA-Schlüssels.

**`e`** (Exponent) — Der RSA-Exponent, Base64url-kodiert. Fast immer `"AQAB"` (= 65537 dezimal), der Standard-Public-Exponent.

**Zusammenspiel:** `n` und `e` zusammen **sind** der öffentliche RSA-Schlüssel. Damit kann die JWT-Signatur mathematisch verifiziert werden, ohne den Private Key zu kennen.



### Step 0: Preparation 

```
cd
mkdir -p manifests/jwt
cd manifests/jwt 
```

### Step 1: Create http-bin and curl workloads 

```
kubectl create ns foo
kubectl apply -f <(istioctl kube-inject -f ~/istio/samples/httpbin/httpbin.yaml) -n foo
kubectl apply -f <(istioctl kube-inject -f ~/istio/samples/curl/curl.yaml) -n foo
```

### Step 2: Can we connect ? 

```
kubectl exec "$(kubectl get pod -l app=curl -n foo -o jsonpath={.items..metadata.name})" -c curl -n foo -- curl http://httpbin.foo:8000/ip -sS -o /dev/null -w "%{http_code}\n"
```

### Step 3: Create a RequestAuthentication 

```
nano 01-ra.yml 
```

```
apiVersion: security.istio.io/v1
kind: RequestAuthentication
metadata:
  name: "jwt-example"
  namespace: foo
spec:
  selector:
    matchLabels:
      app: httpbin
  jwtRules:
  - issuer: "testing@secure.istio.io"
    jwksUri: "https://raw.githubusercontent.com/istio/istio/release-1.29.1/security/tools/jwt/samples/jwks.json"
```

```
kubectl apply -f . 
```

### Step 4: Check with an invalid jwt 

  * Invalid is restricted, so we do not get access (no 200) 

```
kubectl exec "$(kubectl get pod -l app=curl -n foo -o jsonpath={.items..metadata.name})" -c curl -n foo -- curl "http://httpbin.foo:8000/headers" -sS -o /dev/null -H "Authorization: Bearer invalidToken" -w "%{http_code}\n"
```

### Step 5: But: without a jwt -> its work 

  * ... Because ! -> There is no AuthorizationPolicy

```
kubectl exec "$(kubectl get pod -l app=curl -n foo -o jsonpath={.items..metadata.name})" -c curl -n foo -- curl "http://httpbin.foo:8000/headers" -sS -o /dev/null -w "%{http_code}\n"
```

### Step 6: We create an AuthorizationPolicy 

>[!NOTE]
>requestPrincipal set to testing@secure.istio.io/testing@secure.istio.io. Istio constructs the requestPrincipal by combining the iss and sub of the JWT token with a / separator.
>
>iss = issuer
>sub = subject 

```
nano 02-ap.yml
```

```
apiVersion: security.istio.io/v1
kind: AuthorizationPolicy
metadata:
  name: require-jwt
  namespace: foo
spec:
  selector:
    matchLabels:
      app: httpbin
  action: ALLOW
  rules:
  - from:
    - source:
       requestPrincipals: ["testing@secure.istio.io/testing@secure.istio.io"]
```

```
kubectl apply -f 02-ap.yml
```

### Step 7: Test access 

  * jwt consists of 3 parts
    * HEADER / PAYLOAD / SIGNATURE
    * Each part is base64 encoded
  * cut -d. -f2 -> gets the 2nd part -> the payload 
   
```
## This is the way we get the token
TOKEN=$(curl https://raw.githubusercontent.com/istio/istio/release-1.29.1/security/tools/jwt/samples/demo.jwt -s) && echo "$TOKEN" | cut -d '.' -f2 - | base64 --decode
```

```
echo $TOKEN
```

```
## Testing with allowed jwt
kubectl exec "$(kubectl get pod -l app=curl -n foo -o jsonpath={.items..metadata.name})" -c curl -n foo -- curl "http://httpbin.foo:8000/headers" -sS -o /dev/null -H "Authorization: Bearer $TOKEN" -w "%{http_code}\n"
```

```
## Testing without a jwt
kubectl exec "$(kubectl get pod -l app=curl -n foo -o jsonpath={.items..metadata.name})" -c curl -n foo -- curl "http://httpbin.foo:8000/headers" -sS -o /dev/null -w "%{http_code}\n"
```

### Step 8: Update AuthorizationPolicy also needing a specific group 

```
nano 02-ap-group.yml
```

```
apiVersion: security.istio.io/v1
kind: AuthorizationPolicy
metadata:
  name: require-jwt
  namespace: foo
spec:
  selector:
    matchLabels:
      app: httpbin
  action: ALLOW
  rules:
  - from:
    - source:
       requestPrincipals: ["testing@secure.istio.io/testing@secure.istio.io"]
    when:
    - key: request.auth.claims[groups]
      values: ["group1"]
```

```
kubectl apply -f 02-ap-group.yml
```

### Step 9: get token included a claim for a group 

* Get the JWT that sets the groups claim to a list of strings: group1 and group2:

```
TOKEN_GROUP=$(curl https://raw.githubusercontent.com/istio/istio/release-1.29.1/security/tools/jwt/samples/groups-scope.jwt -s) && echo "$TOKEN_GROUP" | cut -d '.' -f2 - | base64 --decode
```

### Step 10: Test it with that token (so group1 must be included) 

```
kubectl exec "$(kubectl get pod -l app=curl -n foo -o jsonpath={.items..metadata.name})" -c curl -n foo -- curl "http://httpbin.foo:8000/headers" -sS -o /dev/null -H "Authorization: Bearer $TOKEN_GROUP" -w "%{http_code}\n"
```

### Step 11: Test with a token without group included 

  * We use that TOKEN before, which had not group 

```
kubectl exec "$(kubectl get pod -l app=curl -n foo -o jsonpath={.items..metadata.name})" -c curl -n foo -- curl "http://httpbin.foo:8000/headers" -sS -o /dev/null -H "Authorization: Bearer $TOKEN" -w "%{http_code}\n"
```

### Step 12: Cleanup 

```
kubectl delete namespace foo
```


### Reference: 

  * https://istio.io/latest/docs/tasks/security/authorization/authz-jwt/
  * JWT Debugger: https://www.jwt.io/

## Kubernetes (Debugging)

### Netzwerkverbindung zu pod testen


### Situation 

```
Managed Cluster und ich kann nicht auf einzelne Nodes per ssh zugreifen
```

### Was wollen wir testen (auf der Verbindungsebene) ?

<img width="900" height="343" alt="image" src="https://github.com/user-attachments/assets/937221ca-20ff-4b1f-926c-cee1f5923f60" />


### Behelf: Eigenen Pod starten mit busybox 

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/e49012af-c278-4922-8029-53896402e85a)


```
kubectl run podtest --rm -it --image busybox
```

### Example test connection 

#### Schritt 1: Die IP des Pods raussuchen, den ich den testen möchte 

```
kubectl get pods -o wide
```


#### Schritt 2: Verbindung test 

```
## -O -> Output (grosses O (buchstabe)) 
kubectl run podtest --rm -ti --image busybox
/ # wget -O - http://10.244.0.99
/ # ping -c 4 10.244.0.99
/ # exit 
```

## Kubernetes Netzwerk 

### DNS - Resolution - Services


### Exercise 

```
kubectl run podtest --rm -ti --image busybox
```

### Example with svc-nginx 

```
## in sh
wget -O - http://svc-nginx
wget -O - http://svc-nginx.jochen
wget -O - http://svc-nginx.jochen.svc
wget -O - http://svc-nginx.jochen.svc.cluster.local
```

### How to find the FQDN (Full qualified domain name) 

```
## in busybox (clusterIP)
#### Schritt 1: Service-IP ausfindig machen
wget -O - http://svc-nginx
## z.B. 10.109.24.227 

#### Schritt 2: nslookup mit dieser Service-IP
nslookup 10.109.24.227
## Ausgabe 
## name = svc-nginx.jochen.svc.cluster.local
```

## Kubernetes Scaling / Resource Management 

### Autoscaling Pods/Deployments


### Example: newest version with autoscaling/v2 used to be hpa/v1

```
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: hello
spec:
  replicas: 3
  selector:
    matchLabels:
      app: hello
  template:
    metadata:
      labels:
        app: hello
    spec:
      containers:
      - name: hello
        image: k8s.gcr.io/hpa-example
        resources:
          requests:
            cpu: 100m
---
kind: Service
apiVersion: v1
metadata:
  name: hello
spec:
  selector:
    app: hello
  ports:
    - port: 80
      targetPort: 80
---
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: hello
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: hello
  minReplicas: 2
  maxReplicas: 20
  metrics:
  - type: Resource
    resource:
      name: cpu
      target:
        type: Utilization
        averageUtilization: 80
```

  * https://docs.digitalocean.com/tutorials/cluster-autoscaling-ca-hpa/

### Reference 

  * https://kubernetes.io/docs/tasks/run-application/horizontal-pod-autoscale-walkthrough/#autoscaling-on-more-specific-metrics
  * https://medium.com/expedia-group-tech/autoscaling-in-kubernetes-why-doesnt-the-horizontal-pod-autoscaler-work-for-me-5f0094694054
  * Alternative: https://github.com/kubernetes/autoscaler/tree/master/cluster-autoscaler

### Resources and Limits for containers


### Prerequisites - install metrics - server 

  * That one is already present in k3s

```
helm repo add metrics-server https://kubernetes-sigs.github.io/metrics-server/
helm install metrics-server metrics-server/metrics-server --namespace kube-system --version 3.12.2
```

### Szenario 1 

```
cd
mkdir -p manifests
cd manifests
mkdir res
cd res
```

```
nano 01-pod.yaml
```

```
apiVersion: v1
kind: Pod
metadata:
  name: cpu-demo
spec:
  containers:
  - name: cpu-demo-ctr
    image: vish/stress
    resources:
      limits:
        cpu: "1"
      requests:
        cpu: "0.5"
    args:
    - -cpus
    - "2"
```


```
kubectl apply -f .
```

```
kubectl get pods cpu-demo
kubectl get pods cpu-demo -o yaml 
kubectl top pod cpu-demo
```




### Reference: 

  * https://kubernetes.io/docs/tasks/configure-pod-container/assign-cpu-resource/

### ResourceQuota und LimitRange im Namespace (Uebung)


### Hintergrund

![ResourceQuota und LimitRange im Namespace](/images/resource-quota-limitrange-v2.svg)

In einem geteilten Cluster muss verhindert werden, dass ein Namespace unbegrenzt CPU und RAM
belegt. Kubernetes bietet zwei Objekte dafuer:

| Objekt | Wirkung |
|--------|---------|
| `ResourceQuota` | Setzt eine harte Obergrenze fuer den gesamten Namespace (z.B. max. 4 CPU, max. 8Gi RAM) |
| `LimitRange` | Setzt Default-Werte und Grenzen pro Container/Pod, falls im Manifest keine Angaben stehen |

Zusammenspiel: Sobald eine `ResourceQuota` aktiv ist, **muss** jeder Pod `requests` und `limits`
definieren — sonst wird er abgelehnt. `LimitRange` loest dieses Problem, indem es automatisch
Defaults einsetzt.

---

### Schritt 1: Namespace anlegen

```
kubectl create namespace resource-<dein-name>
```

---

### Schritt 2: Manifests vorbereiten

```
cd
mkdir -p manifests/20-resource
cd manifests/20-resource
```

---

### Schritt 3: ResourceQuota erstellen

```
## vi 01-resourcequota.yml
apiVersion: v1
kind: ResourceQuota
metadata:
  name: quota-namespace
spec:
  hard:
    requests.cpu: "1"
    requests.memory: 512Mi
    limits.cpu: "2"
    limits.memory: 1Gi
    pods: "5"
```

```
kubectl apply -f 01-resourcequota.yml -n resource-<dein-name>
kubectl get resourcequota -n resource-<dein-name>
kubectl describe resourcequota quota-namespace -n resource-<dein-name>
```

Erwartete Ausgabe:

```
Name:            quota-namespace
Namespace:       resource-<dein-name>
Resource         Used  Hard
--------         ----  ----
limits.cpu       0     2
limits.memory    0     1Gi
pods             0     5
requests.cpu     0     1
requests.memory  0     512Mi
```

---

### Schritt 4: Pod OHNE limits anlegen (schlaegt fehl)

```
## vi 02-pod-no-limits.yml
apiVersion: v1
kind: Pod
metadata:
  name: pod-no-limits
spec:
  containers:
  - name: nginx
    image: nginx
```

```
kubectl apply -f 02-pod-no-limits.yml -n resource-<dein-name>
```

**Erwarteter Fehler:**

```
Error from server (Forbidden): error when creating "02-pod-no-limits.yml":
pods "pod-no-limits" is forbidden: failed quota: quota-namespace:
must specify limits.cpu for: nginx; must specify limits.memory for: nginx;
must specify requests.cpu for: nginx; must specify requests.memory for: nginx
```

---

### Schritt 5: LimitRange als Rettung

```
## vi 03-limitrange.yml
apiVersion: v1
kind: LimitRange
metadata:
  name: limits-per-container
spec:
  limits:
  - type: Container
    default:
      cpu: 200m
      memory: 128Mi
    defaultRequest:
      cpu: 100m
      memory: 64Mi
    max:
      cpu: "1"
      memory: 512Mi
    min:
      cpu: 50m
      memory: 32Mi
```

```
kubectl apply -f 03-limitrange.yml -n resource-<dein-name>
kubectl describe limitrange limits-per-container -n resource-<dein-name>
```

---

### Schritt 6: Pod OHNE limits nochmal anlegen (funktioniert jetzt)

```
kubectl apply -f 02-pod-no-limits.yml -n resource-<dein-name>
kubectl describe pod pod-no-limits -n resource-<dein-name> | grep -A6 Limits
```

Der LimitRange hat automatisch Defaults eingefuegt:

```
Limits:
  cpu:     200m
  memory:  128Mi
Requests:
  cpu:     100m
  memory:  64Mi
```

---

### Schritt 7: Quota-Verbrauch pruefen

```
kubectl describe resourcequota quota-namespace -n resource-<dein-name>
```

```
Resource         Used   Hard
--------         ----   ----
limits.cpu       200m   2
limits.memory    128Mi  1Gi
pods             1      5
requests.cpu     100m   1
requests.memory  64Mi   512Mi
```

---

### Schritt 8: Quota-Limit testen (zu viele Pods)

```
## vi 04-deployment-many.yml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-viele
spec:
  replicas: 6
  selector:
    matchLabels:
      app: nginx-viele
  template:
    metadata:
      labels:
        app: nginx-viele
    spec:
      containers:
      - name: nginx
        image: nginx
        resources:
          requests:
            cpu: 100m
            memory: 64Mi
          limits:
            cpu: 200m
            memory: 128Mi
```

```
kubectl apply -f 04-deployment-many.yml -n resource-<dein-name>
kubectl get pods -n resource-<dein-name>
kubectl get replicaset -n resource-<dein-name>
kubectl describe replicaset -n resource-<dein-name> | grep -A5 "Warning\|Error\|exceeded"
```

Nur 5 Pods werden gestartet (Quota: `pods: "5"`), der 6. schlaegt fehl mit:

```
exceeded quota: quota-namespace, requested: pods=1,
at limit: pods=5
```

---

### Aufraeumen

```
kubectl delete namespace resource-<dein-name>
```

---

### LimitRange: Validierung vs. Mutation

Ein wichtiges Detail: LimitRange-Felder verhalten sich grundlegend unterschiedlich.

| Feld | Typ | Verhalten |
|------|-----|-----------|
| `max` / `min` | Validierung | Verstoß → Pod wird **abgelehnt** (Fehler) |
| `default` / `defaultRequest` | Mutation | Werden nur eingesetzt, wenn der Nutzer **nichts angibt** |
| `maxLimitRequestRatio` | Validierung | Prueft das Verhaeltnis limit/request |

**Kein stillschweigendes Ueberschreiben:** Wenn ein Pod eigene `limits` oder `requests` setzt,
die den `max`-Wert der LimitRange ueberschreiten, wird der Pod abgelehnt — nichts wird
automatisch auf den Max-Wert zurechtgestutzt.

#### max-Wert-Ueberschreitung testen

```
## vi 05-pod-overlimit.yml
apiVersion: v1
kind: Pod
metadata:
  name: pod-overlimit
spec:
  containers:
  - name: nginx
    image: nginx
    resources:
      limits:
        cpu: "2"        # ueberschreitet max.cpu: "1" aus LimitRange
        memory: 128Mi
```

```
kubectl apply -f 05-pod-overlimit.yml -n resource-<dein-name>
```

**Erwarteter Fehler:**

```
Error from server (Forbidden): error when creating "05-pod-overlimit.yml":
pods "pod-overlimit" is forbidden:
maximum cpu usage per Container is 1, but limit is 2
```

#### Mutation tritt nur bei fehlenden Werten ein

Setzt der Nutzer eigene Werte (innerhalb von min/max), bleiben diese erhalten — der
LimitRange greift **nicht** ueberschreibend ein:

```
## Pod mit eigenem gueltigen Wert → LimitRange-Default wird NICHT eingesetzt
resources:
  limits:
    cpu: 500m      # bleibt 500m, nicht 200m (der Default)
    memory: 256Mi  # bleibt 256Mi, nicht 128Mi (der Default)
```

#### Sonderfall: bereits laufende Pods

Eine verschaerfte LimitRange betrifft **nur neu erstellte Pods**. Ein laufender Pod wird
nicht nachtraeglich getötet oder veraendert — die Pruefung greift ausschliesslich bei
Neuerstellung bzw. bei Spec-aendernden Updates.

---

### Zusammenfassung

| Szenario | Ergebnis |
|----------|----------|
| Pod ohne limits, keine LimitRange | Abgelehnt (Quota erzwingt Angaben) |
| Pod ohne limits, mit LimitRange | Akzeptiert (Defaults werden eingesetzt) |
| Pod mit limits > max der LimitRange | Abgelehnt (Validierungsfehler) |
| Pod mit eigenen gueltigen limits | Eigene Werte bleiben erhalten |
| Deployment ueberschreitet Pod-Limit | Nur erlaubte Anzahl Pods laeuft |
| LimitRange nachtraeglich verschaerft | Laufende Pods sind nicht betroffen |

### ResourceQuotas and LimitQuotas by Namespace

  * https://kubernetes.io/docs/tasks/administer-cluster/manage-resources/quota-memory-cpu-namespace/

## Kubernetes Tipps & Tricks

### Oomkiller and maxReadySeconds for safe migration to new pods


### What to achieve ?

 1. Deploy a working version
 2. Deploy a new version that fails with OOM-Killer (but we can be sure pod from old replicaset still works) 

### Step 1: Create deployment that works 

```
mkdir -p manifests
cd manifests
mkdir -p stress
cd stress
```

```
kubectl create ns mem-example 
nano deployment.yml
```

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: deploy-memtest
  namespace: mem-example
spec:
##  minReadySeconds: 120
  selector:
    matchLabels:
      app: memtest
  replicas: 3
  template:
    metadata:
      labels:
        app: memtest
    spec:
      containers:
      - name: memory-demo-ctr
        image: polinux/stress
        resources:
          requests:
            memory: "100Mi"
          limits:
            memory: "200Mi"
        command: ["stress"]
        args: ["--vm", "1", "--vm-bytes", "150M", "--vm-hang", "1"]

```

```
kubectl apply -f .
kubectl -n mem-example get all
kubectl get pods 
```

### Schritt 2: Now with oom - killer version 

  * More memory than available
  * So new pods fail (normally, old pods would be terminated
  * But: Due to minReadySeconds (each pod must at least 120seconds before state is switched to ready)
    * System will wait / and old pods are still availale

```
Change line --args from: --vm-bytes 150M to --vm-bytes 250M
```
   
```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: deploy-memtest
  namespace: mem-example
spec:
##  minReadySeconds: 120
  selector:
    matchLabels:
      app: memtest
  replicas: 3
  template:
    metadata:
      labels:
        app: memtest
    spec:
      containers:
      - name: memory-demo-ctr
        image: polinux/stress
        resources:
          requests:
            memory: "100Mi"
          limits:
            memory: "200Mi"
        command: ["stress"]
        args: ["--vm", "1", "--vm-bytes", "250M", "--vm-hang", "1"]

```

```
kubectl -n mem-example get all
kubectl apply -f .
kubectl -n mem-example get all
## after a while we will see the new pod being in mode OOMKiller 
kubectl -n mem-example get pods -w 
```

### Pod-Netzwerk debuggen durch weiteren Pod der daneben liegt kubectl debug


### Andere Anwendungsfälle 

  * Tools die nicht auf dem Pod installiert sind, benötigen

### Walkthrough 

```
kubectl run my-nginx --image=nginx
## Daneben einen pod starten, der auf das gleiche Netzwerk zugreift (d.h. die gleiche IP-Adresse hat)
kubectl debug -it my-nginx --image=busybox
```

```
## Kann ich rauspingen ?
ping www.google.de
```

### Reference:

  * https://kubernetes.io/docs/reference/kubectl/generated/kubectl_debug/
  * https://kubernetes.io/docs/tasks/debug/debug-application/debug-running-pod/

### Aus pod mit curl api-server abfragen


### Step 1: Prepare Permissions  

```
kubectl create ns app 
```

```
cd
mkdir -p manifests
cd manifests
mkdir curltest
cd curltest
```

```
nano 01-clusterrole.yml
```

```
kind: ClusterRole
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: service-reader
rules:
- apiGroups: [""] # "" indicates the core API group
  resources: ["services","endpoints"]
  verbs: ["get", "list"]
```

```
kubectl -n app apply -f .
```

```
## Einfacher hack, wir verwenden den default-service - account
kubectl -n app create rolebinding api-service-explorer:default --clusterrole service-reader --serviceaccount app:default

```

### Schritt 2: curlimage/curl starten

```
kubectl run -it --rm curltest --image=curlimages/curl -- sh
```

### Schritt 3: in curl - shell 

```
cd /var/run/secrets/kubernetes.io/serviceaccount
TOKEN=$(cat token)
env | grep KUBERNETES_SERVICE
curl https://$KUBERNETES_SERVICE_HOST/openapi/v2 --header "Authorization: Bearer $TOKEN" --cacert ca.crt
curl https://$KUBERNETES_SERVICE_HOST/api/v1/namespaces/app/services/ --header "Authorization: Bearer $TOKEN" --cacert ca.crt
```

```
## Now look into one of the services
 curl https://$KUBERNETES_SERVICE_HOST/api/v1/namespaces/app/services/apple-service/ --header "Authorization: Bearer $TOKEN" --cacert ca.crt
```

```
## We will get the pod ip's from the endpoints
 curl https://$KUBERNETES_SERVICE_HOST/api/v1/namespaces/app/endpoints/apple-service/ --header "Authorization: Bearer $TOKEN" --cacert ca.crt
```

### Reference 

  * https://nieldw.medium.com/curling-the-kubernetes-api-server-d7675cfc398c

## Kubernetes - Monitoring 

### metrics-server aktivieren (microk8s und vanilla)


### Warum ? Was macht er ? 

```
Der Metrics-Server sammelt Informationen von den einzelnen Nodes und Pods
Er bietet mit 

kubectl top pods
kubectl top nodes 

ein einfaches Interface, um einen ersten Eindruck über die Auslastung zu bekommen. 
```

### Walktrough 

```
## Auf einem der Nodes im Cluster (HA-Cluster) 
microk8s enable metrics-server 

## Es dauert jetzt einen Moment bis dieser aktiv ist auch nach der Installation 
## Auf dem Client
kubectl top nodes 
kubectl top pods 

```

### Kubernetes 

  * https://kubernetes-sigs.github.io/metrics-server/
  * kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml

### Prometheus Überblick


### What does it do ?

  * It monitors your system by collecting data
  * Data is pulled from your system by defined endpoints (http) from your cluster 
  * To provide data on your system, a lot of exporters are available, that
    * collect the data and provide it in Prometheus

### Technical 

  * Prometheus has a TDB (Time Series Database) and is good as storing time series with data
  * Prometheus includes a local on-disk time series database, but also optionally integrates with remote storage systems.
  * Prometheus's local time series database stores data in a custom, highly efficient format on local storage.
  * Ref: https://prometheus.io/docs/prometheus/latest/storage/

### What are time series ? 

  * A time series is a sequence of data points that occur in successive order over some period of time. 
  * Beispiel: 
    * Du willst die täglichen Schlusspreise für eine Aktie für ein Jahr dokumentieren
    * Damit willst Du weitere Analysen machen 
    * Du würdest das Paar Datum/Preis dann in der Datumsreihenfolge sortieren und so ausgeben
    * Dies wäre eine "time series" 

### Kompenenten von Prometheus 

![Prometheus Schaubild](https://www.devopsschool.com/blog/wp-content/uploads/2021/01/What-is-Prometheus-Architecutre-components1-740x414.png)

Quelle: https://www.devopsschool.com/

#### Prometheus Server 

1. Retrieval (Sammeln) 
   * Data Retrieval Worker 
     * pull metrics data
1. Storage 
   * Time Series Database (TDB)
     * stores metrics data
1. HTTP Server 
   * Accepts PromQL - Queries (e.g. from Grafana)
     * accept queries 
  
### Grafana ? 

  * Grafana wird meist verwendet um die grafische Auswertung zu machen.
  * Mit Grafana kann ich einfach Dashboards verwenden 
  * Ich kann sehr leicht festlegen (Durch Data Sources), so meine Daten herkommen

### Prometheus Kubernetes Stack installieren


  * using the kube-prometheus-stack (recommended !: includes important metrics)

### Step 1: Prepare values-file  

```
cd
mkdir -p manifests 
cd manifests 
mkdir -p monitoring 
cd monitoring 
```

```
vi values.yml 
```

```
fullnameOverride: prometheus

alertmanager:
  fullnameOverride: alertmanager

grafana:
  fullnameOverride: grafana

kube-state-metrics:
  fullnameOverride: kube-state-metrics

prometheus-node-exporter:
  fullnameOverride: node-exporter
```

### Step 2: Install with helm 

```
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm install prometheus prometheus-community/kube-prometheus-stack -f values.yml --namespace monitoring --create-namespace --version 61.3.1
```

### Step 3: Connect to prometheus from the outside world 

#### Step 3.1: Start proxy to connect (to on Linux Client)

```
## this is shown in the helm information 
helm -n monitoring get notes prometheus

## Get pod that runs prometheus 
kubectl -n monitoring get service 
kubectl -n monitoring port-forward svc/prometheus-prometheus 9090 &

```

#### Step 3.2: Start a tunnel in (from) your local-system to the server 

```
ssh -L 9090:localhost:9090 tln1@164.92.129.7
```

#### Step 3.3: Open prometheus in your local browser 

```
## in browser
http://localhost:9090 
```

### Step 4: Connect to the grafana from the outside world 

#### Step 4.1: Start proxy to connect 

```
## Do the port forwarding 
## Adjust your pods here
kubectl -n monitoring get pods | grep grafana 
kubectl -n monitoring port-forward grafana-56b45d8bd9-bp899 3000 &
```

#### Step 4.2: Start a tunnel in (from) your local-system to the server 

```
ssh -L 3000:localhost:3000 tln1@164.92.129.7
```






### References:

  * https://github.com/prometheus-community/helm-charts/blob/main/charts/kube-prometheus-stack/README.md
  * https://artifacthub.io/packages/helm/prometheus-community/prometheus

  

### Prometheus - Services scrapen die keine Endpunkte für Prometheus haben


### Prerequisites 

  * prometheus setup with helm

### Step 1: Setup

```
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm install my-prometheus-blackbox-exporter prometheus-community/prometheus-blackbox-exporter --version 8.17.0 --namespace monitoring --create-namespace

```

### Step 2: Find SVC 

```
kubectl -n monitoring get svc | grep blackbox
```

```
my-prometheus-blackbox-exporter   ClusterIP   10.245.183.66    <none>        9115/TCP              
```


### Step 3: Test with Curl 

```
kubectl run -it --rm curltest --image=curlimages/curl -- sh 
```

```
## Testen nach google in shell von curl
curl http://my-prometheus-blackbox-exporter.monitoring:9115/probe?target=google.com&module=http_2xx
```

```
## Looking for metric 
probe_http_status_code 200
```

### Step 4: Test apple-service with Curl 

```
## From within curlimages/curl pod 
curl http://my-prometheus-blackbox-exporter.monitoring:9115/probe?target=apple-service.app&module=http_2xx
```


### Step 5: Scrape Config (We want to get all services being labeled example.io/should_be_probed = true

```
prometheus:
  prometheusSpec:
    additionalScrapeConfigs:
    - job_name: "blackbox-microservices"
      metrics_path: /probe
      params:
        module: [http_2xx]
      # Autodiscovery through kube-api-server 
      # https://prometheus.io/docs/prometheus/latest/configuration/configuration/#kubernetes_sd_config
      kubernetes_sd_configs:
      - role: service
      relabel_configs:
        # Example relabel to probe only some services that have "example.io/should_be_probed = true" annotation
        - source_labels: [__meta_kubernetes_service_annotation_example_io_should_be_probed]
          action: keep
          regex: true
        - source_labels: [__address__]
          target_label: __param_target
        - target_label: __address__
          replacement:  my-prometheus-blackbox-exporter:9115
        - source_labels: [__param_target]
          target_label: instance
        - action: labelmap
          regex: __meta_kubernetes_service_label_(.+)
        - source_labels: [__meta_kubernetes_namespace]
          target_label: app
        - source_labels: [__meta_kubernetes_service_name]
          target_label: kubernetes_service_name
```

### Step 6: Test with relabeler 

 * https://relabeler.promlabs.com

```


```

### Step 7: Scrapeconfig einbauen 

```
## von kube-prometheus-grafana in values und ugraden 
 helm upgrade prometheus prometheus-community/kube-prometheus-stack -f values.yml --namespace monitoring --create-namespace --version 61.3.1
```

### Step 8: annotation in service einfügen 

```
kind: Service
apiVersion: v1
metadata:
  name: apple-service
  annotations:
    example.io/should_be_probed: "true"

spec:
  selector:
    app: apple
  ports:
    - protocol: TCP
      port: 80
      targetPort: 5678 # Default port for image
```


```
kubectl apply -f service.yml
```

### Step 9: Look into Status -> Discovery Services and wait

  * blackbox services should now appear under blackbox_microservices
  * and not being dropped

### Step 10: Unter http://64.227.125.201:30090/targets?search= gucken

  * .. ob das funktioniert

### Step 11: Hauptseite (status code 200) 

  * Metrik angekommen `?
  * http://64.227.125.201:30090/graph?g0.expr=probe_http_status_code&g0.tab=1&g0.display_mode=lines&g0.show_exemplars=0&g0.range_input=1h

### Step 12: pod vom service stoppen

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: apple-deployment
spec:
  selector:
    matchLabels:
      app: apple
  replicas: 8
  template:
    metadata:
      labels:
        app: apple
    spec:
      containers:
      - name: apple-app
        image: hashicorp/http-echo
        args:
        - "-text=apple-<dein-name>"


```

```
kubectl apply -f apple.yml # (deployment)

```

### Step 13: status_code 0


  * Metrik angekommen `?
  * http://64.227.125.201:30090/graph?g0.expr=probe_http_status_code&g0.tab=1&g0.display_mode=lines&g0.show_exemplars=0&g0.range_input=1h

## Kubernetes Storage (CSI) 

### Überblick Persistant Volumes (CSI)


### Grafik 

<img width="1001" height="575" alt="image" src="https://github.com/user-attachments/assets/48a5a2f0-56a4-48f7-a4af-0154025d437a" />


### Überblick 

#### Warum CSI ?

  * Each vendor can create his own driver for his storage 

#### Vorteile ? 

```
I. Automatically create storage when required.
II. Make storage available to containers wherever they’re scheduled.
III. Automatically delete the storage when no longer needed. 
```

#### Wie war es vorher ?

```
Vendor needed to wait till his code was checked in in tree of kubernetes (in-tree)
```

#### Unterschied static vs. dynamisch 

```
The main difference relies on the moment when you want to configure storage. For instance, if you need to pre-populate data in a volume, you choose static provisioning. Whereas, if you need to create volumes on demand, you go for dynamic provisioning.
```

### Komponenten 

#### Treiber 

  * Für jede Storage Class (Storage Provider) muss es einen Treiber geben

#### Storage Class 

### Liste der Treiber mit Features (CSI)

  * https://kubernetes-csi.github.io/docs/drivers.html

### Übung Persistant Storage


  * Step 1 + 2 : nur Trainer
  * ab Step 3: Trainees

### Requirements:

  * Ein NFS-Server oder eine Storage mit NFS muss im Netz zur Verfügung stehen. 

### Step 1: Do the same with helm - chart 

```
helm repo add csi-driver-nfs https://raw.githubusercontent.com/kubernetes-csi/csi-driver-nfs/master/charts
helm upgrade --install csi-driver-nfs csi-driver-nfs/csi-driver-nfs --namespace kube-system --version 4.13.2 --reset-values 
```

### Step 2: Storage Class 

```
cd
mkdir -p manifests
cd manifests
mkdir csi-storage
cd csi-storage 
nano 01-storageclass.yml
```

```
apiVersion: storage.k8s.io/v1
kind: StorageClass
metadata:
  name: nfs-csi
provisioner: nfs.csi.k8s.io
parameters:
  server: 10.135.0.6
  share: /var/nfs
reclaimPolicy: Retain
volumeBindingMode: Immediate
```

```
kubectl apply -f .
```

### Step 3: Persistent Volume Claim 

```
cd
mkdir -p manifests
cd manifests
mkdir csi
cd csi
nano 02-pvc.yaml
```

```
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: pvc-nfs-dynamic
spec:
  accessModes:
    - ReadWriteMany
  resources:
    requests:
      storage: 2Gi
  storageClassName: nfs-csi
```

```
kubectl apply -f .
kubectl get pvc
##
kubectl get pv 
```

### Step 4: Pod 

```
nano 03-pod.yaml
```

```
apiVersion: v1
kind: Pod
metadata:
  name: nginx-nfs
spec:
  containers:
    - image: nginx:1.23
      name: nginx-nfs
      command:
        - "/bin/bash"
        - "-c"
        - set -euo pipefail; while true; do echo $(date) >> /mnt/nfs/outfile; sleep 1; done
      volumeMounts:
        - name: persistent-storage
          mountPath: "/mnt/nfs"
          readOnly: false
  volumes:
    - name: persistent-storage
      persistentVolumeClaim:
        claimName: pvc-nfs-dynamic
```

```
kubectl apply -f .
kubectl get pods
kubectl describe pods nginx-nfs 
```

### Step 5: Testing

```
kubectl exec -it nginx-nfs -- bash 
```

```
cd /mnt/nfs
ls -la
## outfile
head /mnt/nfs/outfile 
tail -f /mnt/nfs/outfile
```

```
CTRL+C
exit
```

### Step 6: Destroy 

```
kubectl delete -f 03-pod.yaml 

### Verify in nfs - trainer !! 
```

### Step 7: Recreate 

```
kubectl apply -f 03-pod.yaml
```

```
kubectl exec -it nginx-nfs -- bash
```

```
## is old data here ? 
head /mnt/nfs/outfile 
##
tail -f /mnt/nfs/outfile
```

```
CTRL + C
exit
```
### Step 8: Cleanup 

```
kubectl delete -f .
```


### Reference:

 * https://rudimartinsen.com/2024/01/09/nfs-csi-driver-kubernetes/

### Beispiel mariadb


  * How to persistently use mariadb with a storage class / driver nfs.csi.

### Step 1: Treiber installieren 

  * https://github.com/kubernetes-csi/csi-driver-nfs/blob/master/docs/install-csi-driver-v4.6.0.md

```
curl -skSL https://raw.githubusercontent.com/kubernetes-csi/csi-driver-nfs/v4.6.0/deploy/install-driver.sh | bash -s v4.6.0 --
```

### Step 2: Storage Class 

```
apiVersion: storage.k8s.io/v1
kind: StorageClass
metadata:
  name: nfs-csi
provisioner: nfs.csi.k8s.io
parameters:
  server: 10.135.0.18
  share: /var/nfs
reclaimPolicy: Delete
volumeBindingMode: Immediate
mountOptions:
  - nfsvers=3
```

### Step 3: PVC, Configmap, Deployment 

```
mkdir -p manifests
cd manifests
mkdir mariadb-csi
cd mariadb-csi
```

```
nano 01-pvc.yaml
```

```
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: pvc-nfs-dynamic-mariadb
spec:
  accessModes:
    - ReadWriteMany
  resources:
    requests:
      storage: 2Gi
  storageClassName: nfs-csi
```

```
kubectl apply -f .
```

```
nano 02-configmap.yml
```

```
### 02-configmap.yml
kind: ConfigMap
apiVersion: v1
metadata:
  name: mariadb-configmap
data:
  # als Wertepaare
  MARIADB_ROOT_PASSWORD: 11abc432
```

```
nano 03-deployment.yml
```


```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mariadb-deployment
spec:
  selector:
    matchLabels:
      app: mariadb
  replicas: 1
  template:
    metadata:
      labels:
        app: mariadb
    spec:
      containers:
      - name: mariadb-cont
        image: mariadb:10.11
        envFrom:
        - configMapRef:
            name: mariadb-configmap
        volumeMounts:
        - name: persistent-storage
          mountPath: "/var/lib/mysql"
          readOnly: false
      volumes:
      - name: persistent-storage
        persistentVolumeClaim:
          claimName: pvc-nfs-dynamic-mariadb
```

```
kubectl apply -f .
```

```
kubectl describe po mariadb-deployment-<euer-pod>
```

## Helm

### Helm internals / secret a.s.o

## Kubernetes with ServerLess

### Kubernetes with Serverless


### What is serverless ?

  * Serverless-Computing ist eine Möglichkeit, Code auszuführen, ohne sich um Server zu kümmern.
  * Mit am beliebtesten ist: FaaS (Function as a service)
  * Serverless ist eine Kategorie von Cloud-Computing, die eine Plattform für die Entwicklung und Bereitstellung von Anwendungen bereitstellt, ohne sich um die zugrunde liegende Infrastruktur zu sorgen.

### Products to install on Kubernetes to use the Serverless concept.

#### OpenFaas 

##### Example  

#### OpenWhisk 

##### Example 

  * https://github.com/appvia/serverless-kube/tree/master/openwhisk

#### Kubeless 

  * Described as the most Kubernetes-native. Easy installation and simple architecture using Custom Resource Definitions. No scale-to-zero functionality at the time of writing.

#### Fission 

##### Example 

  * https://github.com/fission/fission#getting-started

#### Knative 

##### Example

  * https://github.com/appvia/serverless-kube/tree/master/knative#template-for-autoscaling-flask-app-using-knative

#### fn 

##### Example

  * https://github.com/appvia/serverless-kube/tree/master/fn#example-app-for-deployment-to-fn

### Reference 

  * https://www.appvia.io/blog/serverless-on-kubernetes 

## Literatur / Documentation / Information (Microservices)

### Sam Newman - Microservices

  * https://www.amazon.de/Building-Microservices-English-Sam-Newman-ebook/dp/B09B5L4NVT/

### Sam Newman - Vom Monolithen zu Microservices

  * https://www.amazon.de/Vom-Monolithen-Microservices-bestehende-umzugestalten/dp/3960091400/

### Microservices.io Patterns

  * https://microservices.io

### BFF

  * https://blog.bitsrc.io/bff-pattern-backend-for-frontend-an-introduction-e4fa965128bf

### Microservices Up and Running

  * https://www.amazon.de/Kubernetes-Running-Dive-Future-Infrastructure/dp/109811020X/ref=sr_1_1

## FAQ

### Verschlüsselung mit Thales docker-container


### Background 

  * Thales verwendet transparente Encryption (funktioniert über den Kernel)

<img width="928" height="187" alt="image" src="https://github.com/user-attachments/assets/336d3a1e-5dd1-4c81-a24d-f8c1a566854a" />

### Was macht ein GuardPoint ? 

  * Verschlüsselung: Alle neuen Dateien werden verschlüsselt gespeichert
  * Entschlüsselung: Autorisierte Prozesse können transparent lesen
  * Zugriffskontrolle: Policy definiert wer Zugriff hat
  * Audit: Alle Zugriffe werden geloggt

### Beispiel für mariadb (wenn kein uid_mapping) 

<img width="539" height="683" alt="image" src="https://github.com/user-attachments/assets/c060df8b-42fc-4ee0-b2b4-05f9f5bf754c" />

### Wie hänge ich ein mount ein, der nicht unter volumes liegt in docker ? 

  * Achtung, Rechte müssen richtig gesetzt sein
  * chown 10001:10001 /data/mariadb # wenn der mariadb - container unter diesem User läuft / Achtung uid_mapping, da ist es anders 

```
version: '3.8'

services:
  mariadb:
    image: mariadb:11.2
    container_name: mariadb-encrypted
    user: "10001:10001"
    environment:
      MYSQL_ROOT_PASSWORD: SuperSecret123
      MYSQL_DATABASE: appdb
      MYSQL_USER: appuser
      MYSQL_PASSWORD: AppPass456
    volumes:
      - /data/mariadb:/var/lib/mysql
    ports:
      - "3306:3306"
    restart: unless-stopped
````

## gitlab ci/cd

### Einfaches Beispielscript


### Edit .gitlab-ci.yml with pipeline editor 

```
## with this content
stages:          # List of stages for jobs, and their order of execution
  - build
  - test
  - deploy

build-job:       # This job runs in the build stage, which runs first.
  stage: build
  script:
    - ls -la
    - pwd
```



### Docker image bauen mit fastapi (python) und kaniko


### Schritt 1: Einloggen in gitlab

### Schritt 2: Import Project (für neues Projekt/Repo anlegen) 

https://gitlab.com/projects/new#import_project

```
und zwar für folgende URL
https://gitlab.com/jmetzger/training-gitlab-cicd-php-with-hello-world.git
```

```
und als projekt namen einen beliebigen verwenden
```

```
als visibility
-> public
```

### Schritt 3: Pipeline ausführen 


## Praxis Microservices ohne Docker und Kubernetes 

### Schritt 1: Nodejs aufsetzen


### Prerequisites

```
## We have set an ubuntu 22.04 LTS server with a user 11trainingdo 
## on digitalocean 
## When you set up a server you can use a script under advanced options 

## or: we have another server at hand 

```

```
## just in case curl is not installed 
apt install -y curl 
cd 
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.1/install.sh | bash
source ~/.bashrc 
## Install latest stable version 
nvm install lts/hydrogen 

## test if it is installed
nvm list 
```


```

## test node 
node 
>.exit

```

### Schritt 2: Codebasis bereitsstellen


### Cloning 

```
cd 
## Wird in den Ordner blog geklont 
git clone https://github.com/jmetzger/training-microservices-docker-kubernetes-uebungen blog 
```

### Schritt 3: Posts - Service testen


### Start service posts 

```
cd 
cd posts
npm start 
## output will listening to :4000 
```

### Find external ip 

```
## in most cases look for eth0 or enp0s3 or enp0s8 
## Example: 134.122.93.133
ip -br a 
```

### Open postmon web interface or donwload it.

```
## get it from here or use web there 
https://www.postman.com/
```

```
## Step 1: Send a post 

- Url: POST http://<ip-of-server>/posts 
- Set: Header -> Content-Type -> to  application/json  # this is need otherwice json is not detected from server 
- Body: set a title 
- Body: set dropdown (at the outer right to -> JSON) 
{
   "title": "my title is great"
}

Send ;o)

You should get a response with an id and a title 

```

```
## Step 2: Get all posts 
- Url: GET http://<ip-of-server>/posts 
- Change http-request to send 
- Set: Header -> Content-Type -> to  application/json  # this is need otherwice json is not detected from server 
```


## Docker-Installation

### Installation Docker unter Ubuntu mit snap


```

sudo su -
snap install docker

## for information retrieval 
snap info docker
systemctl list-units
systemctl list-units -t service
systemctl list-units -t service | grep docker

systemctl status snap.docker.dockerd.service
## oder (aber veraltet) 
service snap.docker.dockerd status

systemctl stop snap.docker.dockerd.service
systemctl status snap.docker.dockerd.service
systemctl start snap.docker.dockerd.service 

## wird der docker-dienst beim nächsten reboot oder starten des Server gestartet ? 
systemctl is-enabled snap.docker.dockerd.service

```

### Installation Docker unter SLES 15


### Walkthrough 


```
sudo zypper search -v docker*
sudo zypper install docker

## Dem Nutzer /z.B. Nutzer kurs die Gruppe docker hinzufügen 
## damit auch dieser den Docker-daemon verwenden darf 
sudo groupadd docker
sudo usermod -aG docker $USER

### Unter SLES werden Dienste nicht automatisch aktiviert und gestartet !!! 
## Service für start nach Boot aktivieren 
newgrp docker 
sudo systemctl enable docker.service
## Docker dienst starten 
sudo systemctl start docker.service
```


### Ausführlich mit Ausgaben 

```
sudo zypper search -v docker*

Repository-Daten werden geladen...
Installierte Pakete werden gelesen...

sudo zypper install docker

Dienst 'Basesystem_Module_x86_64' wird aktualisiert.
Dienst 'Containers_Module_x86_64' wird aktualisiert.
Dienst 'Desktop_Applications_Module_x86_64' wird aktualisiert.
Dienst 'Development_Tools_Module_x86_64' wird aktualisiert.
Dienst 'SUSE_Linux_Enterprise_Server_x86_64' wird aktualisiert.
Dienst 'Server_Applications_Module_x86_64' wird aktualisiert.
Repository-Daten werden geladen...
Installierte Pakete werden gelesen...
Paketabhängigkeiten werden aufgelöst...

Das folgende empfohlene Paket wurde automatisch gewählt:
  git-core

Die folgenden 7 NEUEN Pakete werden installiert:
  catatonit containerd docker docker-bash-completion git-core libsha1detectcoll1 runc

7 neue Pakete zu installieren.
Gesamtgröße des Downloads: 52,2 MiB. Bereits im Cache gespeichert: 0 B. Nach der Operation werden zusätzlich 242,1 MiB belegt.
Fortfahren? [j/n/v/...? zeigt alle Optionen] (j): j
Paket libsha1detectcoll1-1.0.3-2.18.x86_64 abrufen                                                                                                                                                                          (1/7),  23,2 KiB ( 45,8 KiB entpackt)
Abrufen: libsha1detectcoll1-1.0.3-2.18.x86_64.rpm .......................................................................................................................................................................................................[fertig]
Paket catatonit-0.1.5-3.3.2.x86_64 abrufen                                                                                                                                                                                  (2/7), 257,2 KiB (696,5 KiB entpackt)
Abrufen: catatonit-0.1.5-3.3.2.x86_64.rpm ...............................................................................................................................................................................................................[fertig]
Paket runc-1.1.4-150000.33.4.x86_64 abrufen                                                                                                                                                                                 (3/7),   2,6 MiB (  9,1 MiB entpackt)
Abrufen: runc-1.1.4-150000.33.4.x86_64.rpm ..............................................................................................................................................................................................................[fertig]
Paket containerd-1.6.6-150000.73.2.x86_64 abrufen                                                                                                                                                                           (4/7),  17,7 MiB ( 74,2 MiB entpackt)
Abrufen: containerd-1.6.6-150000.73.2.x86_64.rpm ........................................................................................................................................................................................................[fertig]
Paket git-core-2.35.3-150300.10.15.1.x86_64 abrufen                                                                                                                                                                         (5/7),   4,8 MiB ( 26,6 MiB entpackt)
Abrufen: git-core-2.35.3-150300.10.15.1.x86_64.rpm ......................................................................................................................................................................................................[fertig]
Paket docker-20.10.17_ce-150000.166.1.x86_64 abrufen                                                                                                                                                                        (6/7),  26,6 MiB (131,4 MiB entpackt)
Abrufen: docker-20.10.17_ce-150000.166.1.x86_64.rpm .....................................................................................................................................................................................................[fertig]
Paket docker-bash-completion-20.10.17_ce-150000.166.1.noarch abrufen                                                                                                                                                        (7/7), 121,3 KiB (113,6 KiB entpackt)
Abrufen: docker-bash-completion-20.10.17_ce-150000.166.1.noarch.rpm .....................................................................................................................................................................................[fertig]

Überprüfung auf Dateikonflikte läuft: ...................................................................................................................................................................................................................[fertig]
(1/7) Installieren: libsha1detectcoll1-1.0.3-2.18.x86_64 ................................................................................................................................................................................................[fertig]
(2/7) Installieren: catatonit-0.1.5-3.3.2.x86_64 ........................................................................................................................................................................................................[fertig]
(3/7) Installieren: runc-1.1.4-150000.33.4.x86_64 .......................................................................................................................................................................................................[fertig]
(4/7) Installieren: containerd-1.6.6-150000.73.2.x86_64 .................................................................................................................................................................................................[fertig]
(5/7) Installieren: git-core-2.35.3-150300.10.15.1.x86_64 ...............................................................................................................................................................................................[fertig]
Updating /etc/sysconfig/docker ...
(6/7) Installieren: docker-20.10.17_ce-150000.166.1.x86_64 ..............................................................................................................................................................................................[fertig]
(7/7) Installieren: docker-bash-completion-20.10.17_ce-150000.166.1.noarch ..............................................................................................................................................................................[fertig]

sudo groupadd docker
sudo usermod -aG docker $USER
// logout

newgrp docker 
sudo systemctl enable docker.service
sudo systemctl start docker.service
```

## Docker-Grundlagen 

### Übersicht Architektur


![Docker Architecture - copyright geekflare](https://geekflare.com/wp-content/uploads/2019/09/docker-architecture-609x270.png)

### Was ist ein Container ?


```
- vereint in sich Software
- Bibliotheken 
- Tools 
- Konfigurationsdateien 
- keinen eigenen Kernel 
- gut zum Ausführen von Anwendungen auf verschiedenen Umgebungen 

- Container sind entkoppelt
- Container sind voneinander unabhängig 
- Können über wohldefinierte Kommunikationskanäle untereinander Informationen austauschen

- Durch Entkopplung von Containern:
  o Unverträglichkeiten von Bibliotheken, Tools oder Datenbank können umgangen werden, wenn diese von den Applikationen in unterschiedlichen Versionen benötigt werden.
```

### Was sind container images


  * Container Image benötigt, um zur Laufzeit Container-Instanzen zu erzeugen 
  * Bei Docker werden Docker Images zu Docker Containern, wenn Sie auf einer Docker Engine als Prozess ausgeführt werden.
  * Man kann sich ein Docker Image als Kopiervorlage vorstellen.
    * Diese wird genutzt, um damit einen Docker Container als Kopie zu erstellen   

### Container vs. Virtuelle Maschine


```
VM's virtualisieren Hardware
Container virtualisieren Betriebssystem 


```

### Was ist ein Dockerfile


### What is it ? 
 * Textdatei, die Linux - Kommandos enthält
   * die man auch auf der Kommandozeile ausführen könnte 
   * Diese erledigen alle Aufgaben, die nötig sind, um ein Image zusammenzustellen
   * mit docker build wird dieses image erstellt 
   
### Example 

```
## syntax=docker/dockerfile:1
FROM ubuntu:18.04
COPY . /app
RUN make /app
CMD python /app/app.py
```

## Docker-Befehle 

### Logs anschauen - docker logs - mit Beispiel nginx


### Allgemein 
```
## Erstmal nginx starten und container-id wird ausgegeben 
docker run -d nginx:1.22.1
a234
docker logs a234 # a234 sind die ersten 4 Ziffern der Container ID 
```

### Laufende Log-Ausgabe 

```
docker logs -f a234 
## Abbrechen CTRL + c 
```

### Docker container/image stoppen/löschen


```
docker stop ubuntu-container 
## Kill it if it cannot be stopped -be careful
docker kill ubuntu-container

## Get nur, wenn der Container nicht mehr läuft 
docker rm ubuntu-container

## oder alternative
docker rm -f ubuntu-container 


## image löschen 
docker rmi ubuntu:xenial 

## falls Container noch vorhanden aber nicht laufend 
docker rmi -f ubuntu:xenial 

```

### Docker containerliste anzeigen


```
## besser 
docker container ls 
## Alle Container, auch die, die beendet worden sind 
docker container ls -a 


## deprecated 
docker ps 
## -a auch solche die nicht mehr laufen 
docker ps -a



```

### Docker nicht verwendete Images/Container löschen


```
docker system prune 
## Löscht möglicherweise nicht alles

## d.h. danach nochmal prüfen ob noch images da sind
docker images 
## und händisch löschen 
docker rmi <image-name>

```

### Docker container analysieren


```
docker run -t -d --name mein_container ubuntu:latest
docker inspect mein_container # mein_container = container name 
```

### Docker container in den Vordergrund bringen - attach


### docker attach - walkthrough 

```
docker run -d ubuntu 
1a4d...

docker attach 1a4d 

## Es ist leider mit dem Aufruf run nicht möglich, den prozess wieder in den Hintergrund zu bringen 

```

### interactiven Prozess nicht beenden (statt exit) 

```
docker run -it ubuntu bash  
## ein exit würde jetzt den Prozess beenden
## exit

## Alternativ ohne beenden (detach) 
## Geht aber nur beim start mit run -it 
CTRL + P, dann CTRL + Q 

```

### Reference: 

  * https://docs.docker.com/engine/reference/commandline/attach/

### Aufräumen - container und images löschen


### Alle nicht verwendeten container und images löschen 

```
## Alle container, die nicht laufen löschen 
docker container prune 

## Alle images, die nicht an eine container gebunden sind, löschen 
docker image prune 

## Alle nicht benötigten Daten löschen
docker system prune 
```

### Nginx mit portfreigabe laufen lassen


```
docker run --name test-nginx -d -p 8080:80 nginx
```

```
## auf host machine ip des hosts ausfindig machen
ip a  show eth0 

docker container ls
sudo lsof -i
cat /etc/services | grep 8080
curl http://localhost:8080
docker container ls
## wenn der container gestoppt wird, keine ausgabe mehr, weil kein webserver
docker stop test-nginx 
curl http://localhost:8080


```


```
docker start test-nginx 
curl <ip-der-maschine>:8080
```

### Docker container/image stoppen/löschen


```
docker stop ubuntu-container 
## Kill it if it cannot be stopped -be careful
docker kill ubuntu-container

## Get nur, wenn der Container nicht mehr läuft 
docker rm ubuntu-container

## oder alternative
docker rm -f ubuntu-container 


## image löschen 
docker rmi ubuntu:xenial 

## falls Container noch vorhanden aber nicht laufend 
docker rmi -f ubuntu:xenial 

```

### Docker containerliste anzeigen


```
## besser 
docker container ls 
## Alle Container, auch die, die beendet worden sind 
docker container ls -a 


## deprecated 
docker ps 
## -a auch solche die nicht mehr laufen 
docker ps -a



```

## Dockerfile - Examples 

### Ubuntu mit hello world


### Simple Version 

#### Schritt 1:
```
cd 
mkdir hello-world 
cd hello-world
```

#### Schritt 2:

```
## nano Dockerfile
FROM ubuntu:22.04 

COPY hello.sh .
RUN chmod u+x hello.sh
CMD ["/hello.sh"]
```

#### Schritt 3:
```
nano hello.sh 
```

```
##!/bin/bash
let i=0

while true
do
  let i=i+1
  echo $i:hello-docker
  sleep 5
done
```

#### Schritt 4:

```
## dockertrainereu/<dein-name>-hello-docker . 
## Beispiel
## 
docker build -t dockertrainereu/<dein-name>-hello-docker .

docker images
docker run dockertrainereu/<dein-name>-hello-docker 
```


#### Schritt 5:

```
docker login
user: dockertrainereu 
pass: --bekommt ihr vom trainer--

## docker push dockertrainereu/<dein-name>-hello-docker 
## z.B. 
docker push dockertrainereu/jm-hello-docker

## und wir schauen online, ob wir das dort finden

```



### Ubuntu mit ping


### Image erstellen 

```
cd
mkdir myubuntu 
cd myubuntu/
```

```
nano Dockerfile
```

```
FROM ubuntu:24.04
RUN apt-get update; apt-get install -y inetutils-ping
## CMD ["/bin/bash"]
```

```
docker build -t fullubuntu:1.0 .
docker images 
```

```
## Variante 2
## nano Dockerfile
FROM ubuntu:24.04
RUN apt-get update && \
    apt-get install -y inetutils-ping && \
    rm -rf /var/lib/apt/lists/*
## CMD ["/bin/bash"]
```

```
docker build -t myubuntu:1.0 .
docker images
```

### Image (ping) testen  (mit image fullubuntu:1.0)

```
## -t wird benötigt, damit bash WEITER im Hintergrund im läuft.
## auch mit -d (ohne -t) wird die bash ausgeführt, aber "das Terminal" dann direkt beendet 
## -> container läuft dann nicht mehr 
docker run -d -t --name container-ubuntu fullubuntu:1.0
docker container ls

## docker inspect to find out ip of other container 
## 172.17.0.3 
docker inspect container-ubuntu | grep -i ipaddress
```

```
## Zweiten Container starten um 1. anzupingen 
docker run -d -t --name container-ubuntu2 fullubuntu:1.0 

## Ersten Container -> 2. anpingen 
docker exec -it container-ubuntu2 bash 
## Jeder container hat eine eigene IP 
ping 172.17.0.3

 
```


### Image (ping) testen  (mit image myubuntu:1.0)

```
## -t wird benötigt, damit bash WEITER im Hintergrund im läuft.
## auch mit -d (ohne -t) wird die bash ausgeführt, aber "das Terminal" dann direkt beendet 
## -> container läuft dann nicht mehr 
docker run -d -t --name container-ubuntu myubuntu:1.0
docker container ls

## docker inspect to find out ip of other container 
## 172.17.0.3 
docker inspect container-ubuntu | grep -i ipaddress
```

```
## Zweiten Container starten um 1. anzupingen 
docker run -d -t --name container-ubuntu2 myubuntu:1.0 

## Ersten Container -> 2. anpingen 
docker exec -it container-ubuntu2 bash 
## Jeder container hat eine eigene IP 
ping 172.17.0.3

 
```


### Nginx mit content aus html-ordner


### Schritt 1: Simple Example 

```
## das gleich wie cd ~
## Heimatverzeichnis des Benutzers root 
cd
mkdir nginx-test
cd nginx-test
mkdir html
cd html/
nano index.html

```

```
Text, den du rein haben möchtest 
```

```
cd ..
vi Dockerfile 
```

```
FROM nginx:latest
COPY html /usr/share/nginx/html
```

```
## nameskürzel z.B. jm1 
docker build -t nginx-test  . 
docker images

```



### Schritt 2: docker laufen lassen

```
## und direkt aus der Registry wieder runterladen 
docker run --name hello-web -p 8080:80 -d nginx-test

## laufenden Container anzeigen lassen
docker container ls 
## oder alt: deprecated 
docker ps 

curl http://localhost:8080 


## 
docker rm -f hello-web 

```

### Ubuntu mit hello world


### Simple Version 

#### Schritt 1:
```
cd 
mkdir hello-world 
cd hello-world
```

#### Schritt 2:

```
## nano Dockerfile
FROM ubuntu:22.04 

COPY hello.sh .
RUN chmod u+x hello.sh
CMD ["/hello.sh"]
```

#### Schritt 3:
```
nano hello.sh 
```

```
##!/bin/bash
let i=0

while true
do
  let i=i+1
  echo $i:hello-docker
  sleep 5
done
```

#### Schritt 4:

```
## dockertrainereu/<dein-name>-hello-docker . 
## Beispiel
## 
docker build -t dockertrainereu/<dein-name>-hello-docker .

docker images
docker run dockertrainereu/<dein-name>-hello-docker 
```


#### Schritt 5:

```
docker login
user: dockertrainereu 
pass: --bekommt ihr vom trainer--

## docker push dockertrainereu/<dein-name>-hello-docker 
## z.B. 
docker push dockertrainereu/jm-hello-docker

## und wir schauen online, ob wir das dort finden

```



### Nginx mit content aus html-ordner


### Schritt 1: Simple Example 

```
## das gleich wie cd ~
## Heimatverzeichnis des Benutzers root 
cd
mkdir nginx-test
cd nginx-test
mkdir html
cd html/
nano index.html

```

```
Text, den du rein haben möchtest 
```

```
cd ..
vi Dockerfile 
```

```
FROM nginx:latest
COPY html /usr/share/nginx/html
```

```
## nameskürzel z.B. jm1 
docker build -t nginx-test  . 
docker images

```



### Schritt 2: docker laufen lassen

```
## und direkt aus der Registry wieder runterladen 
docker run --name hello-web -p 8080:80 -d nginx-test

## laufenden Container anzeigen lassen
docker container ls 
## oder alt: deprecated 
docker ps 

curl http://localhost:8080 


## 
docker rm -f hello-web 

```

## Docker-Netzwerk 

### Netzwerk


### Übersicht

```
3 Typen 

o none
o bridge (Standard-Netzwerk) 
o host 

### Additionally possible to install
o overlay (needed for multi-node)

```


### Kommandos 

```
## Netzwerk anzeigen 
docker network ls 

## bridge netzwerk anschauen 
## Zeigt auch ip der docker container an  
docker inspect bridge

## im container sehen wir es auch
docker inspect ubuntu-container 

```

### Eigenes Netz erstellen 

```
docker network create -d bridge test_net 
docker network ls 

docker container run -d --name nginx --network test_net nginx
docker container run -d --name nginx_no_net --network none nginx 

docker network inspect none 
docker network inspect test_net 

docker inspect nginx 
docker inspect nginx_no_net 

```

### Netzwerk rausnehmen / hinzufügen 

```
docker network disconnect none nginx_no_net
docker network connect test_net nginx_no_net 

### Das Löschen von Netzwerken ist erst möglich, wenn es keine Endpoints 
### d.h. container die das Netzwerk verwenden 
docker network rm test_net 
```



## Docker-Container Examples 

### 2 Container mit Netzwerk anpingen


```
clear
docker run --name dockerserver1 -dit ubuntu
docker run --name dockerserver2 -dit ubuntu
docker network ls
docker network inspect bridge
## dockerserver1 - 172.17.0.2
## dockerserver2 - 172.17.0.3
docker container ls
docker exec -it dockerserver1 bash
## im container 
apt update; apt install -y iputils-ping 
ping 172.17.0.3 
```

### Container mit eigenem privatem Netz erstellen


```
clear
## use bridge as type
## docker network create -d bridge test_net
## by bridge is default 
docker network create test_net
docker network ls
docker network inspect test_net

## Container mit netzwerk starten 
docker container run -d --name nginx1 --network test_net nginx
docker network inspect test_net

## Weiteres Netzwerk (bridged) erstellen
docker network create demo_net
docker network connect demo_net nginx1

## Analyse 
docker network inspect demo_net
docker inspect nginx1

## Verbindung lösen 
docker network disconnect demo_net nginx1

## Schauen, wir das Netz jetzt aussieht 
docker network inspect demo_net

```

## Docker-Daten persistent machen / Shared Volumes 

### Überblick


### Overview 

```
bind-mount  # not recommended 
volumes
tmpfs 
```

### Disadvantags 

```
stored only on one node
Does not work well in cluster


```

### Alternative for cluster 

```
glusterfs
cephfs 
nfs 

## Stichwort
ReadWriteMany 


```

### Volumes


### Storage volumes verwalten 

```
docker volume ls
docker volume create test-vol
docker volume ls
docker volume inspect test-vol
```

### Storage volumes in container einhängen

```
## Schritt 1
docker run -it --name container-test-vol --mount target=/test_data,source=test-vol ubuntu bash
1234ad# touch /test_data/README 
exit
## stops container 
docker container ls -a
```

```
## Schritt 2:
## create new container and check for /test_data/README 
docker run -it --name=container-test-vol2 --mount target=/test_data,source=test-vol ubuntu bash
ab45# ls -la /test_data/README 
```

### Storage volume löschen 

```
## Zunächst container löschen 
docker rm container-test-vol 
docker rm container-test-vol2
docker volume rm test-vol
```

### bind-mounts


```
## andere Verzeichnis als das Heimatverzeichnis von root funktionieren aktuell nicht mit 
## snap install docker 
## wg. des Confinements 
docker run -d -it  --name devtest --mount type=bind,source=/root,target=/app nginx:latest
docker exec -it devtest bash 
/# cd /app 
```

## Docker - Dokumentation 

### Vulnerability Scanner with docker

  * https://docs.docker.com/engine/scan/#prerequisites

### Vulnerability Scanner mit snyk

  * https://snyk.io/plans/

### Parent/Base - Image bauen für Docker

  * https://docs.docker.com/develop/develop-images/baseimages/

## Docker - Projekt blog

### posts in blog dockerisieren


### Walkthrough 

```
sudo -i 
cd 
git clone https://github.com/jmetzger/training-microservices-docker-kubernetes-uebungen blog
```

```
## Create Dockerfile in posts 
cd posts 
nano Dockerfile
```

```
FROM node:16-alpine

WORKDIR /app
## COPY package.json ./
COPY ./ ./
RUN npm install

CMD ["npm", "start"]
```

```
nano .dockerignore 
```

```
package-lock.json 
```

```
docker build -t dockertrainereu/<namenskuerzel>-posts:0.0.1 .
docker run -d -p 4000:4000 --name posts dockertrainereu/<namenkuerzel>-posts:0.0.1 
docker logs posts 
```

## Docker Compose (backlog)

### yaml-format


```
## Kommentare 

## Listen 
- rot
- gruen
- blau 

## Mappings 
Version: 3.7 

## Mappings können auch Listen enthalten 
expose: 
  - "3000"
  - "8000" 

## Verschachtelte Mappings 
build:
  context: .
  labels: 
    label1: "bunt"
    label2: "hell" 

```

### docker-compose und replicas


### Beispiel 

```
version: "3.9"
services:
  redis:
    image: redis:latest
    deploy:
      replicas: 1
    configs:
      - my_config
      - my_other_config
configs:
  my_config:
    file: ./my_config.txt
  my_other_config:
    external: true
```
### Ref:

  * https://docs.docker.com/compose/compose-file/compose-file-v3/

### Example with Wordpress / Nginx / MariadB - wrong


```
mkdir wp 
cd wp 
## nano docker-compose.yml 
```

```yaml
version: "3.7"

services:
    database:
        image: mysql:5.7
        volumes:
            - database_data:/var/lib/mysql
        restart: always
        environment:
            MYSQL_ROOT_PASSWORD: mypassword
            MYSQL_DATABASE: wordpress
            MYSQL_USER: wordpress
            MYSQL_PASSWORD: wordpress

    wordpress:
        image: wordpress:latest
        depends_on:
            - database
        ports:
            - 8080:80
        restart: always
        environment:
            WORDPRESS_DB_HOST: database:3306
            WORDPRESS_DB_USER: wordpress
            WORDPRESS_DB_PASSWORD: wordpress
        volumes:
            - wordpress_plugins:/var/www/html/wp-content/plugins
            - wordpress_themes:/var/www/html/wp-content/themes
            - wordpress_uploads:/var/www/html/wp-content/uploads
volumes:
    database_data:
    wordpress_plugins:
    wordpress_themes:
    wordpress_uploads:
```


```console
## now start the system
docker compose up -d 
## we can do some test if db is reachable 
docker exec -it wp-wordpress-1 bash 
## within shell do 
apt update 
apt-get install -y telnet
## this should work 
telnet database 3306

## and we even have logs
docker compose logs 

## 
docker compose down 
```

## Kubernetes Netzwerk 

### Mesh / istio


### Schaubild 

![istio Schaubild](https://istio.io/latest/docs/examples/virtual-machines/vm-bookinfo.svg)

### Istio 

```
## Visualization 
## with kiali (included in istio) 
https://istio.io/latest/docs/tasks/observability/kiali/kiali-graph.png

## Example 
## https://istio.io/latest/docs/examples/bookinfo/
The sidecars are injected in all pods within the namespace by labeling the namespace like so:
kubectl label namespace default istio-injection=enabled

## Gateway (like Ingress in vanilla Kubernetes) 
kubectl label namespace default istio-injection=enabled
```

### istio tls 

 * https://istio.io/latest/docs/ops/configuration/traffic-management/tls-configuration/


### istio - the next generation without sidecar 

  * https://istio.io/latest/blog/2022/introducing-ambient-mesh/

### pubsub+ for graph kafka

  * https://solace.com/blog/how-a-financial-services-giant-cleaned-up-their-kafka-with-pubsub-event-portal/

## Kubernetes GUI

### OpenLens


### Why not use lens ?

```
Interestingly the source of lens is opensource, but the binary is freeware.
As of 2023 there is a license key needed 

But OpenLens - client (binary) is opensource, so we use that one 
```

### Install openlens (Windows) 

```
## The easiest way to do so on windows is:
```

```
winget install openlens 
```

```
After that it will be available from Windows (not cmd.exe)
```

### References 

  * https://github.com/MuhammedKalkan/OpenLens

## Kubernetes - microk8s (Installation und Management) 

### Ingress controller in microk8s aktivieren


### Aktivieren

```
microk8s enable ingress
```

### Referenz 

  * https://microk8s.io/docs/addon-ingress

## Helm (Kubernetes Paketmanager) 

### Helm Grundlagen


### Wo ? 

```
artifacts helm 

```

 * https://artifacthub.io/

### Komponenten 

```
Chart - beeinhaltet Beschreibung und Komponenten 
tar.gz - Format 
oder Verzeichnis 

Wenn wir ein Chart ausführen wird eine Release erstellen 
(parallel: image -> container, analog: chart -> release)
```

### Installation 

```
## Beispiel ubuntu 
## snap install --classic helm

## Cluster muss vorhanden, aber nicht notwendig wo helm installiert 

## Voraussetzung auf dem Client-Rechner (helm ist nichts als anderes als ein Client-Programm) 
Ein lauffähiges kubectl auf dem lokalen System (welches sich mit dem Cluster verbinden kann).
-> saubere -> .kube/config 

## Test
kubectl cluster-info 

```


### Helm Warum ?


```
Ein Paket für alle Komponenten
Einfaches Installieren, Updaten und deinstallieren 
Feststehende Struktur 
```

### Helm Example


### Prerequisites 

  * kubectl needs to be installed and configured to access cluster
  * Good: helm works as unprivileged user as well - Good for our setup 
  * install helm on ubuntu (client) as root: snap install --classic helm 
    * this installs helm3
  * Please only use: helm3. No server-side components needed (in cluster) 
    * Get away from examples using helm2 (hint: helm init) - uses tiller  

### Simple Walkthrough (Example 0)

```
## Repo hinzufpgen 
helm repo add bitnami https://charts.bitnami.com/bitnami 
## gecachte Informationen aktualieren 
helm repo update

helm search repo bitnami 
## helm install release-name bitnami/mysql
helm install my-mysql bitnami/mysql
## Chart runterziehen ohne installieren 
## helm pull bitnami/mysql

## Release anzeigen zu lassen
helm list 

## Status einer Release / Achtung, heisst nicht unbedingt nicht, dass pod läuft 
helm status my-mysql 

## weitere release installieren 
## helm install neuer-release-name  bitnami/mysql 


```

### Under the hood 

```
## Helm speichert Informationen über die Releases in den Secrets
kubectl get secrets | grep helm 


```


### Example 1: - To get know the structure 

```
helm repo add bitnami https://charts.bitnami.com/bitnami 
helm search repo bitnami 
helm repo update
helm pull bitnami/mysql 
tar xzvf mysql-9.0.0.tgz 

```



### Example 2: We will setup mysql without persistent storage (not helpful in production ;o() 

```
helm repo add bitnami https://charts.bitnami.com/bitnami 
helm search repo bitnami 
helm repo update

helm install my-mysql bitnami/mysql


```


### Example 2 - continue - fehlerbehebung 

```
helm uninstall my-mysql 
## Install with persistentStorage disabled - Setting a specific value 
helm install my-mysql --set primary.persistence.enabled=false bitnami/mysql

## just as notice 
## helm uninstall my-mysql 

```

### Example 2b: using a values file 

```
## mkdir helm-mysql
## cd helm-mysql
## vi values.yml 
primary:
  persistence:
    enabled: false 
```

```
helm uninstall my-mysql
helm install my-mysql bitnami/mysql -f values.yml 
```

### Example 3: Install wordpress 

```
helm repo add bitnami https://charts.bitnami.com/bitnami 
helm install my-wordpress \
  --set wordpressUsername=admin \
  --set wordpressPassword=password \
  --set mariadb.auth.rootPassword=secretpassword \
    bitnami/wordpress
```

### Example 4: Install Wordpress with values and auth 

```

## mkdir helm-mysql
## cd helm-mysql
## vi values.yml
persistence:
  enabled: false



wordpressUsername: admin
wordpressPassword: password
mariadb:
  primary:
    persistence:
      enabled: false

  auth:
    rootPassword: secretpassword


```

```
helm uninstall my-wordpress 
helm install my-wordpress bitnami/wordpress -f values 
```

### Referenced

  * https://github.com/bitnami/charts/tree/master/bitnami/mysql/#installing-the-chart
  * https://helm.sh/docs/intro/quickstart/

## Kubernetes - RBAC 

### Nutzer einrichten microk8s ab kubernetes 1.25


### Schritt 1: Nutzer-Account auf Server anlegen und secret anlegen / in Client 

```
cd 
mkdir -p manifests/rbac
cd manifests/rbac
```

####  Mini-Schritt 1: Definition für Nutzer 

```
nano 01-sa.yml 
```

```
## vi service-account.yml 
apiVersion: v1
kind: ServiceAccount
metadata:
  name: training
```

```
kubectl apply -f .
```

#### Mini-Schritt 1.5: Secret erstellen 

  * From Kubernetes 1.25 tokens are not created automatically when creating a service account (sa)
  * You have to create them manually with annotation attached 
  * https://kubernetes.io/docs/reference/access-authn-authz/service-accounts-admin/#create-token

```
nano 02-secret.yml
```

```
## vi secret.yml 
apiVersion: v1
kind: Secret
type: kubernetes.io/service-account-token
metadata:
  name: trainingtoken
  annotations:
    kubernetes.io/service-account.name: training
```

```
kubectl apply -f .
```


#### Mini-Schritt 2: ClusterRolle festlegen - Dies gilt für alle namespaces, muss aber noch zugewiesen werden

```
nano 03-cr.yml 
```

```
### Bevor sie zugewiesen ist, funktioniert sie nicht - da sie keinem Nutzer zugewiesen ist 

## vi pods-clusterrole.yml 
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRole
metadata:
  name: pods-clusterrole
rules:
- apiGroups: [""] # "" indicates the core API group
  resources: ["pods"]
  verbs: ["get", "watch", "list"]
```

```
kubectl apply -f -
```

#### Mini-Schritt 3: Die ClusterRolle den entsprechenden Nutzern über RoleBinding zu ordnen 

```
nano 04-crb.yml
```

```
## vi rb-training-ns-default-pods.yml
apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
  name: rolebinding-ns-default-pods
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: pods-clusterrole 
subjects:
- kind: ServiceAccount
  name: training
```

```
kubectl apply -f .
```

#### Mini-Schritt 4: Testen (klappt der Zugang) 

```
## kubectl auth can-i get pods --as system:serviceaccount:<deinnamespace>:training
kubectl auth can-i get pods --as system:serviceaccount:jochen:training
```

### Schritt 2: Context anlegen / Credentials auslesen und in kubeconfig hinterlegen (bis Version 1.25.) 

#### Mini-Schritt 1: kubeconfig setzen 

```
kubectl config set-context training-ctx --cluster do-fra1-ks-cluster --user training

## extract name of the token from here 

TOKEN=`kubectl get secret trainingtoken -o jsonpath='{.data.token}' | base64 --decode`
echo $TOKEN
kubectl config set-credentials training --token=$TOKEN
kubectl config use-context training-ctx
```

```
## kubectl config set-context --current --namespace <dein-name>
kubectl config set-context --current --namespace jochen 
```

```
## Hier reichen die Rechte nicht aus 
kubectl get deploy
## Error from server (Forbidden): pods is forbidden: User "system:serviceaccount:jochen:training" cannot list # resource "pods" in API group "" in the namespace "jochen"
```

#### Mini-Schritt 2:
```
kubectl config use-context training-ctx
kubectl get pods 
```

#### Mini-Schritt 3: Zurück zum alten Default-Context 

```
kubectl config get-contexts
```

```
CURRENT   NAME                          CLUSTER            AUTHINFO    NAMESPACE
          do-fra1-ks-cluster-admin      do-fra1-ks-cluster   admin
*         training-ctx                  do-fra1-ks-cluster   training
```

```
kubectl config use-context microk8s  
```


### Refs:

  * https://docs.oracle.com/en-us/iaas/Content/ContEng/Tasks/contengaddingserviceaccttoken.htm
  * https://microk8s.io/docs/multi-user
  * https://faun.pub/kubernetes-rbac-use-one-role-in-multiple-namespaces-d1d08bb08286

### Ref: Create Service Account Token 

  * https://kubernetes.io/docs/reference/access-authn-authz/service-accounts-admin/#create-token

## Kubernetes - Netzwerk (CNI's) / Mesh

### Netzwerk Interna


### Network Namespace for each pod 

#### Overview 

![Overview Kubernetes Networking](https://www.inovex.de/wp-content/uploads/2020/05/Container-to-Container-Networking_3_neu-400x412.png)

#### General 

  * Each pod will have its own network namespace
    * with routing, networkdevices 
  * Connection to default namespace to host is done through veth - Link to bridge on host network 
    * similar like on docker to docker0 
  
```
  Each container is connected to the bridge via a veth-pair. This interface pair functions like a virtual point-to-point ethernet connection and connects the network namespaces of the containers with the network namespace of the host
```
  
  * Every container is in the same Network Namespace, so they can communicate through localhost
    * Example with hashicorp/http-echo container 1 and busybox container 2 ? 
 
 
### Pod-To-Pod Communication (across nodes)  
 
#### Prerequisites 
 
  * pods on a single node as well as pods on a topological remote can establish communication at all times
   * Each pod receives a unique IP address, valid anywhere in the cluster. Kubernetes requires this address to not be subject to network address   translation (NAT)
   * Pods on the same node through virtual bridge (see image above)
 
#### General (what needs to be done) - and could be doen manually
 
   * local bridge networks of all nodes need to be connected
   * there needs to be an IPAM (IP-Address Managemenet) so addresses are only used once
   * The need to be routes so, that each bridge can communicate with the bridge on the other network
   * Plus: There needs to be a rule for incoming network
   * Also: A tunnel needs to be set up to the outside world.

#### General - Pod-to-Pod Communiation (across nodes) - what would need to be done

![pod to pod across nodes](https://www.inovex.de/wp-content/uploads/2020/05/Pod-to-Pod-Networking.png)


#### General - Pod-to-Pod Communication (side-note) 

  * This could of cause be done manually, but it is too complex 
  * So Kubernetes has created an Interface, which is well defined 
    * The interface is called CNI (common network interface) 
    * Funtionally is achieved through Network Plugin (which use this interface) 
      * e.g. calico / cilium / weave net / flannel 


#### CNI 

  * CNI only handles network connectivity of container and the cleanup of allocated resources (i.e. IP addresses) after containers have been deleted (garbage collection) and therefore is lightweight and quite easy to implement. 
  * There are some basic libraries within CNI which do some basic stuff.
 
   
    


### Hidden Pause Container 

#### What is for ? 

  * Holds the network - namespace for the pod 
  * Gets started first and falls asleep later 
  * Will still be there, when the other containers die 

```
cd 
mkdir -p manifests 
cd manifests 
mkdir pausetest
cd pausetest
nano 01-nginx.yml
```

```
## vi nginx-static.yml 

apiVersion: v1
kind: Pod
metadata:
  name: nginx-pausetest
  labels:
    webserver: nginx:1.21
spec:
  containers:
  - name: web
    image: nginx
```

```
kubectl apply -f .

ctr -n k8s.io c list | grep pause
```


### References 

  * https://www.inovex.de/de/blog/kubernetes-networking-part-1-en/
  * https://www.inovex.de/de/blog/kubernetes-networking-2-calico-cilium-weavenet/

### Übersicht Netzwerke


### CNI 

  * Common Network Interface
  * Feste Definition, wie Container mit Netzwerk-Bibliotheken kommunizieren

### Docker - Container oder andere 

  * Container wird hochgefahren -> über CNI -> zieht Netzwerk - IP  hoch. 
  * Container witd runtergahren -> uber CNI -> Netzwerk - IP wird released 

### Welche gibt es ? 

  * Flanel
  * Canal 
  * Calico 
  * Cilium
  * Weave Net 
  
### Flannel

#### Overlay - Netzwerk 

  * virtuelles Netzwerk was sich oben drüber und eigentlich auf Netzwerkebene nicht existiert
  * VXLAN 

#### Vorteile 

  * Guter einfacher Einstieg 
  * redziert auf eine Binary flanneld 

#### Nachteile 

  * keine Firewall - Policies möglich 
  * keine klassichen Netzwerk-Tools zum Debuggen möglich. 

### Canal 

#### General 

  * Auch ein Overlay - Netzwerk 
  * Unterstüzt auch policies 

### Calico

#### Generell 

  * klassische Netzwerk (BGP)

#### Vorteile gegenüber Flannel 

  * Policy über Kubernetes Object (NetworkPolicies)

#### Vorteile 

  * ISTIO integrierbar (Mesh - Netz) 
  * Performance etwas besser als Flannel (weil keine Encapsulation)

#### Referenz 
  * https://projectcalico.docs.tigera.io/security/calico-network-policy

### Weave Net 

  * Ähnlich calico 
  * Verwendet overlay netzwerk
  * Sehr stabil bzgl IPV4/IPV6 (Dual Stack) 
  * Sehr grosses Feature-Set 
  * mit das älteste Plugin 


### microk8s Vergleich 

  * https://microk8s.io/compare

```
snap.microk8s.daemon-flanneld
Flannel is a CNI which gives a subnet to each host for use with container runtimes.

Flanneld runs if ha-cluster is not enabled. If ha-cluster is enabled, calico is run instead.

The flannel daemon is started using the arguments in ${SNAP_DATA}/args/flanneld. For more information on the configuration, see the flannel documentation.
```

### Calico - nginx example NetworkPolicy


```
## Schritt 1:
kubectl create ns policy-demo
kubectl create deployment --namespace=policy-demo nginx --image=nginx
kubectl expose --namespace=policy-demo deployment nginx:1.21 --port=80
## lassen einen 2. pod laufen mit dem auf den nginx zugreifen 
kubectl run --namespace=policy-demo access --rm -ti --image busybox
```
```
## innerhalb der shell 
wget -q nginx -O -
```
```
## Schritt 2: Policy festlegen, dass kein Ingress-Traffic erlaubt
## in diesem namespace: policy-demo 
kubectl create -f - <<EOF
kind: NetworkPolicy
apiVersion: networking.k8s.io/v1
metadata:
  name: default-deny
  namespace: policy-demo
spec:
  podSelector:
    matchLabels: {}
EOF
## lassen einen 2. pod laufen mit dem auf den nginx zugreifen 
kubectl run --namespace=policy-demo access --rm -ti --image busybox
```

```
## innerhalb der shell 
wget -q nginx -O -
```

```
## Schritt 3: Zugriff erlauben von pods mit dem Label run=access 
kubectl create -f - <<EOF
kind: NetworkPolicy
apiVersion: networking.k8s.io/v1
metadata:
  name: access-nginx
  namespace: policy-demo
spec:
  podSelector:
    matchLabels:
      app: nginx
  ingress:
    - from:
      - podSelector:
          matchLabels:
            run: access
EOF

## lassen einen 2. pod laufen mit dem auf den nginx zugreifen 
## pod hat durch run -> access automatisch das label run:access zugewiesen 
kubectl run --namespace=policy-demo access --rm -ti --image busybox
```

```
## innerhalb der shell 
wget -q nginx -O -
```

``` 
kubectl run --namespace=policy-demo no-access --rm -ti --image busybox
```

```
## in der shell  
wget -q nginx -O -
```

```

kubectl delete ns policy-demo 

```


### Ref:

  * https://projectcalico.docs.tigera.io/security/tutorials/kubernetes-policy-basic

### Beispiele Ingress Egress NetworkPolicy


### Links 

  * https://github.com/ahmetb/kubernetes-network-policy-recipes
  * https://k8s-examples.container-solutions.com/examples/NetworkPolicy/NetworkPolicy.html

### Example with http (Cilium !!) 

```
apiVersion: "cilium.io/v2"
kind: CiliumNetworkPolicy
description: "L7 policy to restrict access to specific HTTP call"
metadata:
  name: "rule1"
spec:
  endpointSelector:
    matchLabels:
      type: l7-test
  ingress:
  - fromEndpoints:
    - matchLabels:
        org: client-pod
    toPorts:
    - ports:
      - port: "8080"
        protocol: TCP
      rules:
        http:
        - method: "GET"
          path: "/discount"
```          
   
### Downside egress 

  * No valid api for anything other than IP's and/or Ports
  * If you want more, you have to use CNI-Plugin specific, e.g. 

#### Example egress with ip's 

```
## Allow traffic of all pods having the label role:app
## egress only to a specific ip and port 
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: test-network-policy
  namespace: default
spec:
  podSelector:
    matchLabels:
      role: app
  policyTypes:
  - Egress
  egress:
  - to:
    - ipBlock:
        cidr: 10.10.0.0/16
    ports:
    - protocol: TCP 
      port: 5432
```

### Example Advanced Egress (cni-plugin specific) 

#### Cilium

```
apiVersion: cilium.io/v2
kind: CiliumNetworkPolicy
metadata:
  name: "fqdn-pprof"
  namespace: msp
spec:
  endpointSelector:
    matchLabels:
      app: pprof
  egress:
  - toFQDNs:
    - matchPattern: '*.baidu.com'
  - toPorts:
    - ports:
      - port: "53"
        protocol: ANY
      rules:
        dns:
        - matchPattern: '*'
```

#### Calico 

  * Only Calico enterprise 
    * Calico Enterprise extends Calico’s policy model so that domain names (FQDN / DNS) can be used to allow access from a pod or set of pods (via label selector) to external resources outside of your cluster.
    * https://projectcalico.docs.tigera.io/security/calico-enterprise/egress-access-controls

#### Using isitio as mesh (e.g. with cilium/calico )

##### Installation of sidecar in calico 

  * https://projectcalico.docs.tigera.io/getting-started/kubernetes/hardway/istio-integration

##### Example 

```
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: test-network-policy
  namespace: default
spec:
  podSelector:
    matchLabels:
      role: app
  policyTypes:
  - Egress
  egress:
  - to:
    - ipBlock:
        cidr: 10.10.0.0/16
    ports:
    - protocol: TCP 
      port: 5432
```

### Kubernetes Ports/Protokolle

  * https://kubernetes.io/docs/reference/networking/ports-and-protocols/

### IPV4/IPV6 Dualstack

  * https://kubernetes.io/docs/concepts/services-networking/dual-stack/

## kubectl 

### Start pod (container with run && examples)


### Example (that does work)

```
## Show the pods that are running 
kubectl get pods 

## Synopsis (most simplistic example 
## kubectl run NAME --image=IMAGE_EG_FROM_DOCKER
## example
kubectl run nginx --image=nginx:1.21

kubectl get pods 
## on which node does it run ? 
kubectl get pods -o wide 
```

### Example (that does not work) 

```
kubectl run meinfalscherpod --image=foo2
## ImageErrPull - Image konnte nicht geladen werden 
kubectl get pods 
## Weitere status - info 
kubectl describe pods meinfalscherpod
```

### Ref:

  * https://kubernetes.io/docs/reference/generated/kubectl/kubectl-commands#run

### Bash completion for kubectl


### Walkthrough 

```
## Eventuell, wenn bash-completion nicht installiert ist.
apt install bash-completion
source /usr/share/bash-completion/bash_completion
## is it installed properly 
type _init_completion
```

```
## als root
## activate for all users 
kubectl completion bash | sudo tee /etc/bash_completion.d/kubectl > /dev/null

## verifizieren - neue login shell
su -

## zum Testen
kubectl g<TAB> 
kubectl get 
```

```
## alternativ z.B. als Benutzer kurs 
## activate for all users 
sudo -i 
kubectl completion bash | sudo tee /etc/bash_completion.d/kubectl > /dev/null
exit

## verifizieren - neue login shell
su - kurs

## zum Testen
kubectl g<TAB> 
kubectl get 

```

### Alternative für k als alias für kubectl 

```
source <(kubectl completion bash)
complete -F __start_kubectl k

```

### Reference 

  * https://kubernetes.io/docs/tasks/tools/included/optional-kubectl-configs-bash-linux/

### kubectl Spickzettel


### Allgemein 

```
## Zeige Information über das Cluster 
kubectl cluster-info 

## Welche api-resources gibt es ?
kubectl api-resources 

## Hilfe zu object und eigenschaften bekommen
kubectl explain pod 
kubectl explain pod.metadata
kubectl explain pod.metadata.name 

```

### Arbeiten mit manifesten 

```
kubectl apply -f nginx-replicaset.yml 
## Wie ist aktuell die hinterlegte config im system
kubectl get -o yaml -f nginx-replicaset.yml 

## Änderung in nginx-replicaset.yml z.B. replicas: 4 
## dry-run - was wird geändert 
kubectl diff -f nginx-replicaset.yml 

## anwenden 
kubectl apply -f nginx-replicaset.yml 

## Alle Objekte aus manifest löschen
kubectl delete -f nginx-replicaset.yml 


```

### Ausgabeformate 

```
## Ausgabe kann in verschiedenen Formaten erfolgen 
kubectl get pods -o wide # weitere informationen 
## im json format
kubectl get pods -o json 

## gilt natürluch auch für andere kommandos
kubectl get deploy -o json 
kubectl get deploy -o yaml

## Eigenschaft auslesen
kubectl get pods nginx-deployment-74676ff58f-fxcjv -o jsonpath='{.metadata.ownerReferences[0].name}'

```



### Zu den Pods 

```
## Start einen pod // BESSER: direkt manifest verwenden
## kubectl run podname image=imagename 
kubectl run nginx image=nginx 

## Pods anzeigen 
kubectl get pods 
kubectl get pod
## Format weitere Information 
kubectl get pod -o wide 
## Zeige labels der Pods
kubectl get pods --show-labels 

## Zeige pods mit einem bestimmten label 
kubectl get pods -l app=nginx 

## Status eines Pods anzeigen 
kubectl describe pod nginx 

## Pod löschen 
kubectl delete pod nginx 

## Kommando in pod ausführen 
kubectl exec -it nginx -- bash 

```

### Zu den Pods (Logs) 

```
## log eines pods anzeigen
kubectl logs podname

## Logs aller pods im Deployment
## Wichtig Option --prefix
kubectl logs --prefix deploy/web-nginx 

```


### Arbeiten mit namespaces 

```
## Welche namespaces auf dem System 
kubectl get ns 
kubectl get namespaces 
## Standardmäßig wird immer der default namespace verwendet 
## wenn man kommandos aufruft 
kubectl get deployments 

## Möchte ich z.B. deployment vom kube-system (installation) aufrufen, 
## kann ich den namespace angeben
kubectl get deployments --namespace=kube-system 
kubectl get deployments -n kube-system 

## wir wollen unseren default namespace ändern 
kubectl config set-context --current --namespace <dein-namespace>
```

### Arbeiten mit der Config (lokal) 

```
## namespace jochen als default setzen
kubectl config set-context --current --namespace jochen
## config anzeigen 
kubectl config view 
```

### Referenz

  * https://kubernetes.io/de/docs/reference/kubectl/cheatsheet/

### Tipps&Tricks zu Deploymnent - Rollout


### Warum 

```
Rückgängig machen von deploys, Deploys neu unstossen.
(Das sind die wichtigsten Fähigkeiten

```

### Beispiele 

```
## Deployment nochmal durchführen 
## z.B. nach kubectl uncordon n12.training.local 
kubectl rollout restart deploy nginx-deployment 

## Rollout rückgängig machen 
kubectl rollout undo deploy nginx-deployment 

```

## Kubernetes - Shared Volumes 

### Shared Volumes with nfs


### Create new server and install nfs-server

```
## on Ubuntu 20.04LTS
apt install nfs-kernel-server 
systemctl status nfs-server 

vi /etc/exports 
## adjust ip's of kubernetes master and nodes 
## kmaster
/var/nfs/ 192.168.56.101(rw,sync,no_root_squash,no_subtree_check)
## knode1
/var/nfs/ 192.168.56.103(rw,sync,no_root_squash,no_subtree_check)
## knode 2
/var/nfs/ 192.168.56.105(rw,sync,no_root_squash,no_subtree_check)

exportfs -av 
```

### On all nodes (needed for production) 

```
## 
apt install nfs-common 

```

### On all nodes (only for testing)

```
#### Please do this on all servers (if you have access by ssh)
### find out, if connection to nfs works ! 

## for testing 
mkdir /mnt/nfs 
## 10.135.0.18 is our nfs-server 
mount -t nfs 10.135.0.18:/var/nfs /mnt/nfs 
ls -la /mnt/nfs
umount /mnt/nfs
```

### Persistent Storage-Step 1: Setup PersistentVolume in cluster

```
cd
cd manifests 
mkdir -p nfs 
cd nfs 
nano 01-pv.yml 
```

```
apiVersion: v1
kind: PersistentVolume
metadata:
  # any PV name
  name: pv-nfs-tln<nr>
  labels:
    volume: nfs-data-volume-tln<nr>
spec:
  capacity:
    # storage size
    storage: 1Gi
  accessModes:
    # ReadWriteMany(RW from multi nodes), ReadWriteOnce(RW from a node), ReadOnlyMany(R from multi nodes)
    - ReadWriteMany
  persistentVolumeReclaimPolicy:
    # retain even if pods terminate
    Retain
  nfs:
    # NFS server's definition
    path: /var/nfs/tln<nr>/nginx
    server: 10.135.0.18
    readOnly: false
  storageClassName: ""
```

```
kubectl apply -f 01-pv.yml 
kubectl get pv 
```

### Persistent Storage-Step 2: Create Persistent Volume Claim 

```
nano 02-pvc.yml
```

```
## vi 02-pvc.yml 
## now we want to claim space
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: pv-nfs-claim-tln<nr>
spec:
  storageClassName: ""
  volumeName: pv-nfs-tln<nr>
  accessModes:
  - ReadWriteMany
  resources:
     requests:
       storage: 1Gi
```


```
kubectl apply -f 02-pvc.yml
kubectl get pvc
```

### Persistent Storage-Step 3: Deployment 

```
## deployment including mount 
## vi 03-deploy.yml 
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
spec:
  selector:
    matchLabels:
      app: nginx
  replicas: 4 # tells deployment to run 4 pods matching the template
  template:
    metadata:
      labels:
        app: nginx
    spec:
       
      containers:
      - name: nginx
        image: nginx:latest
        ports:
        - containerPort: 80
        
        volumeMounts:
          - name: nfsvol
            mountPath: "/usr/share/nginx/html"

      volumes:
      - name: nfsvol
        persistentVolumeClaim:
          claimName: pv-nfs-claim-tln<tln>


```

```
kubectl apply -f 03-deploy.yml 

```

### Persistent Storage Step 4: service 

```
## now testing it with a service 
## cat 04-service.yml 
apiVersion: v1
kind: Service
metadata:
  name: service-nginx
  labels:
    run: svc-my-nginx
spec:
  type: NodePort
  ports:
  - port: 80
    protocol: TCP
  selector:
    app: nginx
```        

```
kubectl apply -f 04-service.yml 
```

### Persistent Storage Step 5: write data and test

```
## connect to the container and add index.html - data 
kubectl exec -it deploy/nginx-deployment -- bash 
## in container
echo "hello dear friend" > /usr/share/nginx/html/index.html 
exit 

## now try to connect 
kubectl get svc 

## connect with ip and port
kubectl run -it --rm curly --image=curlimages/curl -- /bin/sh 
## curl http://<cluster-ip>
## exit

## now destroy deployment 
kubectl delete -f 03-deploy.yml 

## Try again - no connection 
kubectl run -it --rm curly --image=curlimages/curl -- /bin/sh 
## curl http://<cluster-ip>
## exit 
```

### Persistent Storage Step 6: retest after redeployment 

```
## now start deployment again 
kubectl apply -f 03-deploy.yml 

## and try connection again  
kubectl run -it --rm curly --image=curlimages/curl -- /bin/sh 
## curl http://<cluster-ip>
## exit 
```




## Kubernetes - Wartung / Debugging 

### kubectl drain/uncordon


```
## Achtung, bitte keine pods verwenden, dies können "ge"-drained (ausgetrocknet) werden 
kubectl drain <node-name>
z.B. 
## Daemonsets ignorieren, da diese nicht gelöscht werden 
kubectl drain n17 --ignore-daemonsets 

## Alle pods von replicasets werden jetzt auf andere nodes verschoben 
## Ich kann jetzt wartungsarbeiten durchführen 

## Wenn fertig bin:
kubectl uncordon n17 

## Achtung: deployments werden nicht neu ausgerollt, dass muss ich anstossen.
## z.B. 
kubectl rollout restart deploy/webserver 


```

### Alte manifeste konvertieren mit convert plugin


### What is about? 

  * Plugins needs to be installed seperately on Client (or where you have your manifests) 

### Walkthrough 

```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl-convert"
## Validate the checksum
curl -LO "https://dl.k8s.io/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl-convert.sha256"
echo "$(<kubectl-convert.sha256) kubectl-convert" | sha256sum --check
## install 
sudo install -o root -g root -m 0755 kubectl-convert /usr/local/bin/kubectl-convert

## Does it work 
kubectl convert --help 

## Works like so 
## Convert to the newest version 
## kubectl convert -f pod.yaml

```

### Reference 

  * https://kubernetes.io/docs/tasks/tools/install-kubectl-linux/#install-kubectl-convert-plugin 

### Curl from pod api-server


### Step 1: Prepare Permissions  

```
kubectl create ns app 
```

```
cd
mkdir -p manifests
cd manifests
mkdir curltest
cd curltest
```

```
nano 01-clusterrole.yml
```

```
kind: ClusterRole
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: service-reader
rules:
- apiGroups: [""] # "" indicates the core API group
  resources: ["services","endpoints"]
  verbs: ["get", "list"]
```

```
kubectl -n app apply -f .
```

```
## Einfacher hack, wir verwenden den default-service - account
kubectl -n app create rolebinding api-service-explorer:default --clusterrole service-reader --serviceaccount app:default

```

### Schritt 2: curlimage/curl starten

```
kubectl run -it --rm curltest --image=curlimages/curl -- sh
```

### Schritt 3: in curl - shell 

```
cd /var/run/secrets/kubernetes.io/serviceaccount
TOKEN=$(cat token)
env | grep KUBERNETES_SERVICE
curl https://$KUBERNETES_SERVICE_HOST/openapi/v2 --header "Authorization: Bearer $TOKEN" --cacert ca.crt
curl https://$KUBERNETES_SERVICE_HOST/api/v1/namespaces/app/services/ --header "Authorization: Bearer $TOKEN" --cacert ca.crt
```

```
## Now look into one of the services
 curl https://$KUBERNETES_SERVICE_HOST/api/v1/namespaces/app/services/apple-service/ --header "Authorization: Bearer $TOKEN" --cacert ca.crt
```

```
## We will get the pod ip's from the endpoints
 curl https://$KUBERNETES_SERVICE_HOST/api/v1/namespaces/app/endpoints/apple-service/ --header "Authorization: Bearer $TOKEN" --cacert ca.crt
```

### Reference 

  * https://nieldw.medium.com/curling-the-kubernetes-api-server-d7675cfc398c

## Kubernetes - Tipps & Tricks 

### Kubernetes Debuggen ClusterIP/PodIP


### Situation 

  * Kein Zugriff auf die Nodes, zum Testen von Verbindungen zu Pods und Services über die PodIP/ClusterIP 

### Lösung 

```
## Wir starten eine Busybox und fragen per wget und port ab
## busytester ist der name 
## long version 
kubectl run -it --rm --image=busybox busytester 
## wget <pod-ip-des-ziels> 
## exit 


## quick and dirty 
kubectl run -it --rm --image=busybox busytester -- wget <pod-ip-des-ziels>  

```

### Debugging pods


### How ?

  1. Which pod is in charge 
  1. Problems when starting: kubectl describe po mypod 
  1. Problems while running: kubectl logs mypod 

### Taints und Tolerations


### Taints 

```
Taints schliessen auf einer Node alle Pods aus, die nicht bestimmte taints haben:

Möglichkeiten:

o Sie werden nicht gescheduled - NoSchedule 
o Sie werden nicht executed - NoExecute 
o Sie werden möglichst nicht gescheduled. - PreferNoSchedule 

```

### Tolerations 

```
Tolerations werden auf Pod-Ebene vergeben: 
tolerations: 

Ein Pod kann (wenn es auf einem Node taints gibt), nur 
gescheduled bzw. ausgeführt werden, wenn er die 
Labels hat, die auch als
Taints auf dem Node vergeben sind.
```

### Walkthrough  

#### Step 1: Cordon the other nodes - scheduling will not be possible there 

```
## Cordon nodes n11 and n111 
## You will see a taint here 
kubectl cordon n11
kubectl cordon n111
kubectl describe n111 | grep -i taint 
```



### Step 2: Set taint on first node 

```
kubectl taint nodes n1 gpu=true:NoSchedule
```

### Step 3

```
cd 
mkdir -p manifests
cd manifests 
mkdir tainttest 
cd tainttest 
nano 01-no-tolerations.yml
```

```
##vi 01-no-tolerations.yml 
apiVersion: v1
kind: Pod
metadata:
  name: nginx-test-no-tol
  labels:
    env: test-env
spec:
  containers:
  - name: nginx
    image: nginx:1.21
```

```
kubectl apply -f . 
kubectl get po nginx-test-no-tol
kubectl get describe nginx-test-no-tol
```

### Step 4:

```
## vi 02-nginx-test-wrong-tol.yml 
apiVersion: v1
kind: Pod
metadata:
  name: nginx-test-wrong-tol
  labels:
    env: test-env
spec:
  containers:
  - name: nginx
    image: nginx:latest
  tolerations:
  - key: "cpu"
    operator: "Equal"
    value: "true"
    effect: "NoSchedule"
```

```
kubectl apply -f .
kubectl get po nginx-test-wrong-tol
kubectl describe po nginx-test-wrong-tol
```

### Step 5:

```
## vi 03-good-tolerations.yml 
apiVersion: v1
kind: Pod
metadata:
  name: nginx-test-good-tol
  labels:
    env: test-env
spec:
  containers:
  - name: nginx
    image: nginx:latest
  tolerations:
  - key: "gpu"
    operator: "Equal"
    value: "true"
    effect: "NoSchedule"
```

```
kubectl apply -f .
kubectl get po nginx-test-good-tol
kubectl describe po nginx-test-good-tol
```

#### Taints rausnehmen 

```
kubectl taint nodes n1 gpu:true:NoSchedule-
```

#### uncordon other nodes 

```
kubectl uncordon n11
kubectl uncordon n111
```

### References 
  
  * [Doku Kubernetes Taints and Tolerations](https://kubernetes.io/docs/concepts/scheduling-eviction/taint-and-toleration/)
  * https://blog.kubecost.com/blog/kubernetes-taints/

## Kubernetes Advanced 

### Curl api-server kubernetes aus pod heraus


### Step 1: Prepare Permissions  

```
kubectl create ns app 
```

```
cd
mkdir -p manifests
cd manifests
mkdir curltest
cd curltest
```

```
nano 01-clusterrole.yml
```

```
kind: ClusterRole
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: service-reader
rules:
- apiGroups: [""] # "" indicates the core API group
  resources: ["services","endpoints"]
  verbs: ["get", "list"]
```

```
kubectl -n app apply -f .
```

```
## Einfacher hack, wir verwenden den default-service - account
kubectl -n app create rolebinding api-service-explorer:default --clusterrole service-reader --serviceaccount app:default

```

### Schritt 2: curlimage/curl starten

```
kubectl run -it --rm curltest --image=curlimages/curl -- sh
```

### Schritt 3: in curl - shell 

```
cd /var/run/secrets/kubernetes.io/serviceaccount
TOKEN=$(cat token)
env | grep KUBERNETES_SERVICE
curl https://$KUBERNETES_SERVICE_HOST/openapi/v2 --header "Authorization: Bearer $TOKEN" --cacert ca.crt
curl https://$KUBERNETES_SERVICE_HOST/api/v1/namespaces/app/services/ --header "Authorization: Bearer $TOKEN" --cacert ca.crt
```

```
## Now look into one of the services
 curl https://$KUBERNETES_SERVICE_HOST/api/v1/namespaces/app/services/apple-service/ --header "Authorization: Bearer $TOKEN" --cacert ca.crt
```

```
## We will get the pod ip's from the endpoints
 curl https://$KUBERNETES_SERVICE_HOST/api/v1/namespaces/app/endpoints/apple-service/ --header "Authorization: Bearer $TOKEN" --cacert ca.crt
```

### Reference 

  * https://nieldw.medium.com/curling-the-kubernetes-api-server-d7675cfc398c

## Kubernetes - Documentation 

### Documentation zu microk8s plugins/addons

  * https://microk8s.io/docs/addons

### Shared Volumes - Welche gibt es ?

  * https://kubernetes.io/docs/concepts/storage/volumes/

## Kubernetes - Hardening 

### Kubernetes Tipps Hardening


### PSA (Pod Security Admission) 

```
Policies defined by namespace.
e.g. not allowed to run container as root.

Will complain/deny when creating such a pod with that container type

```

### Möglichkeiten in Pods und Containern 

```
## für die Pods
kubectl explain pod.spec.securityContext 
kubectl explain pod.spec.containers.securityContext
```

### Example (seccomp / security context) 

```
A. seccomp - profile
https://github.com/docker/docker/blob/master/profiles/seccomp/default.json

```

```
apiVersion: v1
kind: Pod
metadata:
  name: audit-pod
  labels:
    app: audit-pod
spec:
  securityContext:
    seccompProfile:
      type: Localhost
      localhostProfile: profiles/audit.json

  containers:

  - name: test-container
    image: hashicorp/http-echo:0.2.3
    args:
    - "-text=just made some syscalls!"
    securityContext:
      allowPrivilegeEscalation: false

```

### SecurityContext (auf Pod Ebene) 

```
kubectl explain pod.spec.containers.securityContext 

```


### NetworkPolicy 

```
## Firewall Kubernetes 
```

### Kubernetes Security Admission Controller Example



### Seit: 1.2.22 Pod Security Admission 

  * 1.2.22 - ALpha - D.h. ist noch nicht aktiviert und muss als Feature Gate aktiviert (Kind)
  * 1.2.23 - Beta -> d.h. aktiviert  

### Vorgefertigte Regelwerke 

  * privileges - keinerlei Einschränkungen 
  * baseline - einige Einschränkungen 
  * restricted - sehr streng 

### Praktisches Beispiel für Version ab 1.2.23 - Problemstellung 

```
mkdir -p manifests
cd manifests
mkdir psa 
cd psa 
nano 01-ns.yml 
```

```
## Schritt 1: Namespace anlegen 
## vi 01-ns.yml 

apiVersion: v1
kind: Namespace
metadata:
  name: test-ns<tln>
  labels:
    pod-security.kubernetes.io/enforce: baseline
    pod-security.kubernetes.io/audit: restricted
    pod-security.kubernetes.io/warn: restricted

```

```
kubectl apply -f 01-ns.yml 
```

```
## Schritt 2: Testen mit nginx - pod 
## vi 02-nginx.yml 

apiVersion: v1
kind: Pod
metadata:
  name: nginx
  namespace: test-ns<tln>
spec:
  containers:
    - image: nginx
      name: nginx
      ports:
        - containerPort: 80

```

```
## a lot of warnings will come up 
kubectl apply -f 02-nginx.yml
```

```
## Schritt 3:
## Anpassen der Sicherheitseinstellung (Phase1) im Container 

## vi 02-nginx.yml 

apiVersion: v1
kind: Pod
metadata:
  name: nginx
  namespace: test-ns<tln>
spec:
  containers:
    - image: nginx
      name: nginx
      ports:
        - containerPort: 80
      securityContext:     
        seccompProfile:    
          type: RuntimeDefault
```

```
kubectl delete -f 02-nginx.yml
kubectl apply -f 02-nginx.yml
kubectl -n test-ns<tln> get pods 
```

```
## Schritt 4: 
## Weitere Anpassung runAsNotRoot 
## vi 02-nginx.yml 
apiVersion: v1
kind: Pod
metadata:
  name: nginx
  namespace: test-ns<tln>
spec:
  containers:
    - image: nginx
      name: nginx
      ports:
        - containerPort: 80
      securityContext:
        seccompProfile:
          type: RuntimeDefault
        runAsNonRoot: true

```

```
## pod kann erstellt werden, wird aber nicht gestartet 
kubectl delete -f 02-nginx.yml 
kubectl apply -f 02-nginx.yml 
kubectl -n test-ns<tln> get pods
kubectl -n test-ns<tln> describe pods nginx 
```

### Praktisches Beispiel für Version ab 1.2.23 -Lösung - Container als NICHT-Root laufen lassen

  * Wir müssen ein image, dass auch als NICHT-Root laufen kann 
  * .. oder selbst eines bauen (;o)) 
  o bei nginx ist das bitnami/nginx 
 
```
## vi 03-nginx-bitnami.yml 
apiVersion: v1
kind: Pod
metadata:
  name: bitnami-nginx
  namespace: test-ns<tln>
spec:
  containers:
    - image: bitnami/nginx
      name: bitnami-nginx
      ports:
        - containerPort: 80
      securityContext:
        seccompProfile:
          type: RuntimeDefault
        runAsNonRoot: true
```

```
## und er läuft als nicht root 
kubectl apply -f 03_pod-bitnami.yml 
kubectl -n test-ns<tln> get pods
```

## Kubernetes Interna / Misc.

### OCI,Container,Images Standards


### Schritt 1:

```
cd
mkdir bautest
cd bautest 
```

### Schritt 2:

```
## nano docker-compose.yml
version: "3.8"

services:
  myubuntu:
    build: ./myubuntu
    restart: always
```

### Schritt 3:

```
mkdir myubuntu 
cd myubuntu 
```

```
nano hello.sh
```

```
##!/bin/bash
let i=0

while true
do
  let i=i+1
  echo $i:hello-docker
  sleep 5
done

```

```
## nano Dockerfile 
FROM ubuntu:latest
RUN apt-get update; apt-get install -y inetutils-ping
COPY hello.sh .
RUN chmod u+x hello.sh
CMD ["/hello.sh"]

```

### Schritt 4: 


```
cd ../
## wichtig, im docker-compose - Ordner seiend 
##pwd 
##~/bautest
docker-compose up -d 
## wird image gebaut und container gestartet 

## Bei Veränderung vom Dockerfile, muss man den Parameter --build mitangeben 
docker-compose up -d --build 
```

### Geolocation Kubernetes Cluster

  * https://learnk8s.io/bite-sized/connecting-multiple-kubernetes-clusters

## Documentation 

### Good Doku with Tasks

  * https://kubernetes.io/docs/tasks/configure-pod-container/security-context/

## Docker-Container Examples 

### 2 Container mit Netzwerk anpingen


```
clear
docker run --name dockerserver1 -dit ubuntu
docker run --name dockerserver2 -dit ubuntu
docker network ls
docker network inspect bridge
## dockerserver1 - 172.17.0.2
## dockerserver2 - 172.17.0.3
docker container ls
docker exec -it dockerserver1 bash
## im container 
apt update; apt install -y iputils-ping 
ping 172.17.0.3 
```

### Container mit eigenem privatem Netz erstellen


```
clear
## use bridge as type
## docker network create -d bridge test_net
## by bridge is default 
docker network create test_net
docker network ls
docker network inspect test_net

## Container mit netzwerk starten 
docker container run -d --name nginx1 --network test_net nginx
docker network inspect test_net

## Weiteres Netzwerk (bridged) erstellen
docker network create demo_net
docker network connect demo_net nginx1

## Analyse 
docker network inspect demo_net
docker inspect nginx1

## Verbindung lösen 
docker network disconnect demo_net nginx1

## Schauen, wir das Netz jetzt aussieht 
docker network inspect demo_net

```

## Docker-Netzwerk 

### Netzwerk


### Übersicht

```
3 Typen 

o none
o bridge (Standard-Netzwerk) 
o host 

### Additionally possible to install
o overlay (needed for multi-node)

```


### Kommandos 

```
## Netzwerk anzeigen 
docker network ls 

## bridge netzwerk anschauen 
## Zeigt auch ip der docker container an  
docker inspect bridge

## im container sehen wir es auch
docker inspect ubuntu-container 

```

### Eigenes Netz erstellen 

```
docker network create -d bridge test_net 
docker network ls 

docker container run -d --name nginx --network test_net nginx
docker container run -d --name nginx_no_net --network none nginx 

docker network inspect none 
docker network inspect test_net 

docker inspect nginx 
docker inspect nginx_no_net 

```

### Netzwerk rausnehmen / hinzufügen 

```
docker network disconnect none nginx_no_net
docker network connect test_net nginx_no_net 

### Das Löschen von Netzwerken ist erst möglich, wenn es keine Endpoints 
### d.h. container die das Netzwerk verwenden 
docker network rm test_net 
```



## Docker Security 

### Scanning docker image with docker scan/snyx


### ACHTUNG_ Deprecated - USE Docker Scout instead (only Docker Desktop ?) 

### Prerequisites 

```
## install docker plugin in some cases
## Ubuntu
apt install docker-scan-plugin 
```

```
You need to be logged in on docker hub with docker login 
(with your account credentials)
```


### Example 

```
## Snyk (docker scan) 
docker help scan
docker scan --json --accept-license dockertrainereu/jm-hello-docker  > result.json
```

## Docker Compose

### yaml-format


```
## Kommentare 

## Listen 
- rot
- gruen
- blau 

## Mappings 
Version: 3.7 

## Mappings können auch Listen enthalten 
expose: 
  - "3000"
  - "8000" 

## Verschachtelte Mappings 
build:
  context: .
  labels: 
    label1: "bunt"
    label2: "hell" 

```

### Example with Ubuntu and Dockerfile


### Schritt 1:

```
cd
mkdir bautest
cd bautest 
```

### Schritt 2:

```
## nano docker-compose.yml
services:
  myubuntu:
    build: ./myubuntu
    restart: always
```

### Schritt 3:

```
mkdir myubuntu 
cd myubuntu 
```

```
nano hello.sh
```

```
##!/bin/bash
let i=0

while true
do
  let i=i+1
  echo $i:hello-docker
  sleep 5
done

```

```
nano Dockerfile
```

```
FROM ubuntu:24.04
RUN apt-get update && apt-get install -y inetutils-ping
COPY hello.sh .
RUN chmod u+x hello.sh
CMD ["/hello.sh"]

```

### Schritt 4: 


```
cd ../
## wichtig, im docker-compose - Ordner seiend 
##pwd 
##~/bautest
docker compose up -d 
## wird image gebaut und container gestartet 

## Bei Veränderung vom Dockerfile, muss man den Parameter --build mitangeben 
docker compose up -d --build 
```

### Schritt 5: Logs anzeigen

```
docker logs bautest-myubuntu-1
docker compose logs 

```

### docker-compose und replicas


### Beispiel 

```
version: "3.9"
services:
  redis:
    image: redis:latest
    deploy:
      replicas: 1
    configs:
      - my_config
      - my_other_config
configs:
  my_config:
    file: ./my_config.txt
  my_other_config:
    external: true
```
### Ref:

  * https://docs.docker.com/compose/compose-file/compose-file-v3/

### docker compose Reference

  * https://docs.docker.com/compose/compose-file/compose-file-v3/

## Docker Swarm 

### Docker Swarm Beispiele


### Generic examples 

```
## should be at least version 1.24 
docker info

## only for one network interface
docker swarm init

## in our case, we need to decide what interface
docker swarm init --advertise-addr 192.168.56.101

## is swarm active 
docker info | grep -i swarm
## When it is -> node command works 
docker node ls
## is the current node the manager 
docker info | grep -i "is manager"

## docker create additional overlay network 
docker network ls

## what about my own node -> self
docker node inspect self
docker node inspect --pretty self
docker node inspect --pretty self | less

```

```
## Create our first service 
docker service create redis
docker images
docker service ls
## if service-id start with  j 
docker service inspect j
docker service ps j
docker service rm j
docker service ls
```

```
## Start with multiple replicas and name 
docker service create --name my_redis --replicas 4 redis
docker service ls
## Welche tasks 
docker service ps my_redis
docker container ls
docker service inspect my_redis

## delete service
docker service rm
```

### Add additional node 

```
## on first node, get join token 
docker swarm join-token manager

## on second node execute join command
docker swarm join --token SWMTKN-1-07jy3ym29au7u3isf1hfhgd7wpfggc1nia2kwtqfnfc8hxfczw-2kuhwlnr9i0nkje8lz437d2d5 192.168.56.101:2377

## check with node command
docker node ls 

## Make node a simple worker
## Does not make, because no highavailable after crush node 1
## Take at LEAST 3 NODES 
docker node demote <node-name>

```

### expose port

```
docker service create --name my_web \
                        --replicas 3 \
                        --publish published=8080,target=80 \
                        nginx
```

### Ref 

  * https://docs.docker.com/engine/swarm/services/

## Docker - Dokumentation 

### Vulnerability Scanner with docker

  * https://docs.docker.com/engine/scan/#prerequisites

### Vulnerability Scanner mit snyk

  * https://snyk.io/plans/

### Parent/Base - Image bauen für Docker

  * https://docs.docker.com/develop/develop-images/baseimages/

## Kubernetes - Überblick

### Installation - Welche Komponenten from scratch


### Step 1: Server 1 (manuell installiert -> microk8s)

```
## Installation Ubuntu - Server 

## cloud-init script 
## s.u. BASIS (keine Voraussetzung - nur zum Einrichten des Nutzers 11trainingdo per ssh) 

## Server 1 - manuell 
## Ubuntu 20.04 LTS - Grundinstallation 

## minimal Netzwerk - öffentlichen IP 
## nichts besonderes eingerichtet - Standard Digitalocean 

## Standard vo Installation microk8s 
lo               UNKNOWN        127.0.0.1/8 ::1/128
## public ip / interne 
eth0             UP             164.92.255.234/20 10.19.0.6/16 fe80::c:66ff:fec4:cbce/64
## private ip 
eth1             UP             10.135.0.3/16 fe80::8081:aaff:feaa:780/64

snap install microk8s --classic 
## namensaufloesung fuer pods 
microk8s enable dns 

```

```
## Funktioniert microk8s 
microk8s status
```

### Steps 2: Server 2+3 (automatische Installation -> microk8s ) 

```
## Was macht das ? 
## 1. Basisnutzer (11trainingdo) - keine Voraussetzung für microk8s
## 2. Installation von microk8s 
##.>>>>>>> microk8s installiert <<<<<<<<
## - snap install --classic microk8s 
## >>>>>>> Zuordnung zur Gruppe microk8s - notwendig für bestimmte plugins (z.B. helm)  
## usermod -a -G microk8s root 
## >>>>>>> Setzen des .kube - Verzeichnisses auf den Nutzer microk8s -> nicht zwingend erforderlich 
## chown -r -R microk8s ~/.kube 
## >>>>>>> REQUIRED .. DNS aktivieren, wichtig für Namensauflösungen innerhalb der PODS
## >>>>>>> sonst funktioniert das nicht !!! 
## microk8s enable dns 
## >>>>>>> kubectl alias gesetzt, damit man nicht immer microk8s kubectl eingeben muss
## - echo "alias kubectl='microk8s kubectl'" >> /root/.bashrc

## cloud-init script 
## s.u. MITMICROK8S (keine Voraussetzung - nur zum Einrichten des Nutzers 11trainingdo per ssh) 
##cloud-config
users:
  - name: 11trainingdo
    shell: /bin/bash

runcmd:
  - sed -i "s/PasswordAuthentication no/PasswordAuthentication yes/g" /etc/ssh/sshd_config
  - echo " " >> /etc/ssh/sshd_config 
  - echo "AllowUsers 11trainingdo" >> /etc/ssh/sshd_config 
  - echo "AllowUsers root" >> /etc/ssh/sshd_config 
  - systemctl reload sshd 
  - sed -i '/11trainingdo/c 11trainingdo:$6$HeLUJW3a$4xSfDFQjKWfAoGkZF3LFAxM4hgl3d6ATbr2kEu9zMOFwLxkYMO.AJF526mZONwdmsm9sg0tCBKl.SYbhS52u70:17476:0:99999:7:::' /etc/shadow
  - echo "11trainingdo ALL=(ALL) ALL" > /etc/sudoers.d/11trainingdo
  - chmod 0440 /etc/sudoers.d/11trainingdo
  
  - echo "Installing microk8s"
  - snap install --classic microk8s
  - usermod -a -G microk8s root
  - chown -f -R microk8s ~/.kube
  - microk8s enable dns 
  - echo "alias kubectl='microk8s kubectl'" >> /root/.bashrc
```
```
## Prüfen ob microk8s - wird automatisch nach Installation gestartet
## kann eine Weile dauern
microk8s status

```

### Step 3: Client - Maschine (wir sollten nicht auf control-plane oder cluster - node arbeiten

```
Weiteren Server hochgezogen. 
Vanilla + BASIS 

## Installation Ubuntu - Server 

## cloud-init script 
## s.u. BASIS (keine Voraussetzung - nur zum Einrichten des Nutzers 11trainingdo per ssh) 

## Server 1 - manuell 
## Ubuntu 20.04 LTS - Grundinstallation 

## minimal Netzwerk - öffentlichen IP 
## nichts besonderes eingerichtet - Standard Digitalocean 

## Standard vo Installation microk8s 
lo               UNKNOWN        127.0.0.1/8 ::1/128
## public ip / interne 
eth0             UP             164.92.255.232/20 10.19.0.6/16 fe80::c:66ff:fec4:cbce/64
## private ip 
eth1             UP             10.135.0.5/16 fe80::8081:aaff:feaa:780/64

```

```
##### Installation von kubectl aus dem snap
## NICHT .. keine microk8s - keine control-plane / worker-node 
## NUR Client zum Arbeiten 
snap install kubectl --classic 

##### .kube/config 
## Damit ein Zugriff auf die kube-server-api möglich
## d.h. REST-API Interface, um das Cluster verwalten.
## Hier haben uns für den ersten Control-Node entschieden
## Alternativ wäre round-robin per dns möglich 

## Mini-Schritt 1:
## Auf dem Server 1: kubeconfig ausspielen 
microk8s config > /root/kube-config 
## auf das Zielsystem gebracht (client 1) 
scp /root/kubeconfig 11trainingdo@10.135.0.5:/home/11trainingdo

## Mini-Schritt 2:
## Auf dem Client 1 (diese Maschine) kubeconfig an die richtige Stelle bringen 
## Standardmäßig der Client nach eine Konfigurationsdatei sucht in ~/.kube/config 
sudo su -
cd 
mkdir .kube 
cd .kube 
mv /home/11trainingdo/kube-config config 

## Verbindungstest gemacht
## Damit feststellen ob das funktioniert. 
kubectl cluster-info 

```

### Schritt 4: Auf allen Servern IP's hinterlegen und richtigen Hostnamen überprüfen 

```
## Auf jedem Server 
hostnamectl 
## evtl. hostname setzen 
## z.B. - auf jedem Server eindeutig 
hostnamectl set-hostname n1.training.local 

## Gleiche hosts auf allen server einrichten.
## Wichtig, um Traffic zu minimieren verwenden, die interne (private) IP

/etc/hosts 
10.135.0.3 n1.training.local n1
10.135.0.4 n2.training.local n2
10.135.0.5 n3.training.local n3 

```

### Schritt 5: Cluster aufbauen 

```
## Mini-Schritt 1:
## Server 1: connection - string (token) 
microk8s add-node 
## Zeigt Liste und wir nehmen den Eintrag mit der lokalen / öffentlichen ip
## Dieser Token kann nur 1x verwendet werden und wir auf dem ANDEREN node ausgeführt
## microk8s join 10.135.0.3:25000/e9cdaa11b5d6d24461c8643cdf107837/bcad1949221a

## Mini-Schritt 2:
## Dauert eine Weile, bis das durch ist. 
## Server 2: Den Node hinzufügen durch den JOIN - Befehl 
microk8s join 10.135.0.3:25000/e9cdaa11b5d6d24461c8643cdf107837/bcad1949221a

## Mini-Schritt 3:
## Server 1: token besorgen für node 3
microk8s add-node 

## Mini-Schritt 4:
## Server 3: Den Node hinzufügen durch den JOIN-Befehl 
microk8s join 10.135.0.3:25000/09c96e57ec12af45b2752fb45450530c/bcad1949221a

## Mini-Schritt 5: Überprüfen ob HA-Cluster läuft 
Server 1: (es kann auf jedem der 3 Server überprüft werden, auf einem reicht 
microk8s status | grep high-availability 
high-availability: yes 
```

### Ergänzend nicht notwendige Scripte 

```
## cloud-init script 
## s.u. BASIS (keine Voraussetzung - nur zum Einrichten des Nutzers 11trainingdo per ssh) 

## Digitalocean - unter user_data reingepastet beim Einrichten 

##cloud-config
users:
  - name: 11trainingdo
    shell: /bin/bash

runcmd:
  - sed -i "s/PasswordAuthentication no/PasswordAuthentication yes/g" /etc/ssh/sshd_config
  - echo " " >> /etc/ssh/sshd_config 
  - echo "AllowUsers 11trainingdo" >> /etc/ssh/sshd_config 
  - echo "AllowUsers root" >> /etc/ssh/sshd_config 
  - systemctl reload sshd 
  - sed -i '/11trainingdo/c 11trainingdo:$6$HeLUJW3a$4xSfDFQjKWfAoGkZF3LFAxM4hgl3d6ATbr2kEu9zMOFwLxkYMO.AJF526mZONwdmsm9sg0tCBKl.SYbhS52u70:17476:0:99999:7:::' /etc/shadow
  - echo "11trainingdo ALL=(ALL) ALL" > /etc/sudoers.d/11trainingdo
  - chmod 0440 /etc/sudoers.d/11trainingdo
```

## Kubernetes - microk8s (Installation und Management) 

### kubectl unter windows - Remote-Verbindung zu Kuberenets (microk8s) einrichten


### Walkthrough (Installation)

```
## Step 1
chocolatry installiert. 
(powershell als Administrator ausführen)
## https://docs.chocolatey.org/en-us/choco/setup
Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))

## Step 2
choco install kubernetes-cli 

## Step 3
testen:
kubectl version --client 

## Step 4:
## powershell als normaler benutzer öffnen 
```

### Walkthrough (autocompletion) 

```
in powershell (normaler Benutzer) 
kubectl completion powershell | Out-String | Invoke-Expression
```

### kubectl - config - Struktur vorbereiten  

```
## in powershell im heimatordner des Benutzers .kube - ordnern anlegen
## C:\Users\<dein-name>\
mkdir .kube 
cd .kube 
```

### IP von Cluster-Node bekommen 

```
## auf virtualbox - maschine per ssh einloggen 
## öffentliche ip herausfinden - z.B. enp0s8 bei HostOnly - Adapter
ip -br a 
```

### config für kubectl aus Cluster-Node auslesen (microk8s) 

```
## auf virtualbox - maschine per ssh einloggen / zum root wechseln 
## abfragen
microk8s config 

## Alle Zeilen ins clipboard kopieren
## und mit notepad++ in die Datei \Users\<dein-name>\.kube\config 
## schreiben

## Wichtig: Zeile cluster -> clusters / server 
## Hier ip von letztem Schritt eintragen:
## z.B. 
Server: https://192.168.56.106/......
```

### Testen 

```
## in powershell
## kann ich eine Verbindung zum Cluster aufbauen ? 
kubectl cluster-info 
```



  * https://kubernetes.io/docs/tasks/tools/install-kubectl-windows/

### Arbeiten mit der Registry

### Installation Kubernetes Dashboard


### Reference:

  * https://blog.tippybits.com/installing-kubernetes-in-virtualbox-3d49f666b4d6    

## Kubernetes - RBAC 

### Nutzer einrichten - kubernetes bis 1.24


### Enable RBAC in microk8s 

```
## This is important, if not enable every user on the system is allowed to do everything 
microk8s enable rbac 
```

### Schritt 1: Nutzer-Account auf Server anlegen / in Client 

```
cd 
mkdir -p manifests/rbac
cd manifests/rbac
```

####  Mini-Schritt 1: Definition für Nutzer 

```
## vi service-account.yml 
apiVersion: v1
kind: ServiceAccount
metadata:
  name: training
  namespace: default
```

```
kubectl apply -f service-account.yml 
```


#### Mini-Schritt 2: ClusterRolle festlegen - Dies gilt für alle namespaces, muss aber noch zugewiesen werden

```
### Bevor sie zugewiesen ist, funktioniert sie nicht - da sie keinem Nutzer zugewiesen ist 

## vi pods-clusterrole.yml 
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRole
metadata:
  name: pods-clusterrole
rules:
- apiGroups: [""] # "" indicates the core API group
  resources: ["pods"]
  verbs: ["get", "watch", "list"]
```

```
kubectl apply -f pods-clusterrole.yml 
```

#### Mini-Schritt 3: Die ClusterRolle den entsprechenden Nutzern über RoleBinding zu ordnen 
```
## vi rb-training-ns-default-pods.yml
apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
  name: rolebinding-ns-default-pods
  namespace: default
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: pods-clusterrole 
subjects:
- kind: ServiceAccount
  name: training
  namespace: default
```

```
kubectl apply -f rb-training-ns-default-pods.yml
```

#### Mini-Schritt 4: Testen (klappt der Zugang) 

```
kubectl auth can-i get pods -n default --as system:serviceaccount:default:training
```

### Schritt 2: Context anlegen / Credentials auslesen und in kubeconfig hinterlegen (bis Version 1.25.) 

#### Mini-Schritt 1: kubeconfig setzen 

```
kubectl config set-context training-ctx --cluster microk8s-cluster --user training

## extract name of the token from here 

TOKEN=`kubectl get secret trainingtoken -o jsonpath='{.data.token}' | base64 --decode`
echo $TOKEN
kubectl config set-credentials training --token=$TOKEN
kubectl config use-context training-ctx

## Hier reichen die Rechte nicht aus 
kubectl get deploy
## Error from server (Forbidden): pods is forbidden: User "system:serviceaccount:kube-system:training" cannot list # resource "pods" in API group "" in the namespace "default"
```

#### Mini-Schritt 2:
```
kubectl config use-context training-ctx
kubectl get pods 
```

### Refs:

  * https://docs.oracle.com/en-us/iaas/Content/ContEng/Tasks/contengaddingserviceaccttoken.htm
  * https://microk8s.io/docs/multi-user
  * https://faun.pub/kubernetes-rbac-use-one-role-in-multiple-namespaces-d1d08bb08286

### Ref: Create Service Account Token 

  * https://kubernetes.io/docs/reference/access-authn-authz/service-accounts-admin/#create-token

## kubectl 

### Tipps&Tricks zu Deploymnent - Rollout


### Warum 

```
Rückgängig machen von deploys, Deploys neu unstossen.
(Das sind die wichtigsten Fähigkeiten

```

### Beispiele 

```
## Deployment nochmal durchführen 
## z.B. nach kubectl uncordon n12.training.local 
kubectl rollout restart deploy nginx-deployment 

## Rollout rückgängig machen 
kubectl rollout undo deploy nginx-deployment 

```

## Kubernetes - Backups 

### Backup- und Wiederherstellungsstrategien


![Backup-Schichten in Kubernetes](/images/backup-schichten.svg)

### Was muss gesichert werden?

In einer Kubernetes-Umgebung gibt es drei unabhängige Schichten:

| Schicht | Was | Tools |
|---------|-----|-------|
| **Cluster-Zustand** | etcd: alle Kubernetes-Objekte (Deployments, Services, ConfigMaps, Secrets) | `etcdctl snapshot`, Velero |
| **Persistent Volumes** | Anwendungsdaten (Datenbanken, Upload-Ordner, etc.) | CSI-Snapshots, Velero, Restic |
| **Anwendungsdaten** | Datenbankinhalt auf Anwendungsebene | pg_dump, mysqldump, mongodump |

> **Wichtig:** etcd-Backup und Volume-Backup sind unabhängig. Ein etcd-Restore ohne passende Volumes bringt leere PVCs zurück — und umgekehrt stehen Volumes ohne Kubernetes-Objekte nutzlos herum.

---

### etcd Backup

etcd speichert den kompletten Cluster-Zustand. Fällt etcd aus oder werden Daten korrumpiert,
verliert der Cluster alle Konfigurationen.

#### Snapshot erstellen

```
ETCDCTL_API=3 etcdctl snapshot save /backup/etcd-$(date +%Y%m%d-%H%M%S).db \
  --endpoints=https://127.0.0.1:2379 \
  --cacert=/etc/kubernetes/pki/etcd/ca.crt \
  --cert=/etc/kubernetes/pki/etcd/healthcheck-client.crt \
  --key=/etc/kubernetes/pki/etcd/healthcheck-client.key
```

#### Snapshot verifizieren

```
ETCDCTL_API=3 etcdctl snapshot status /backup/etcd-20240522-1200.db --write-out=table
```

Erwartete Ausgabe:
```
+----------+----------+------------+------------+
|   HASH   | REVISION | TOTAL KEYS | TOTAL SIZE |
+----------+----------+------------+------------+
| a1b2c3d4 |    12345 |       1234 |    5.2 MB  |
+----------+----------+------------+------------+
```

#### Snapshot wiederherstellen

```
## Kubernetes-Komponenten stoppen (Control Plane)
systemctl stop kube-apiserver kube-controller-manager kube-scheduler

## etcd aus Snapshot wiederherstellen
ETCDCTL_API=3 etcdctl snapshot restore /backup/etcd-20240522-1200.db \
  --data-dir=/var/lib/etcd-restore \
  --name=master-1 \
  --initial-cluster=master-1=https://MASTER_IP:2380 \
  --initial-advertise-peer-urls=https://MASTER_IP:2380

## etcd-Datenverzeichnis tauschen
mv /var/lib/etcd /var/lib/etcd-old
mv /var/lib/etcd-restore /var/lib/etcd

## Dienste neu starten
systemctl start kube-apiserver kube-controller-manager kube-scheduler etcd
```

> **Warnung:** Ein etcd-Restore setzt den Cluster auf den Stand des Snapshots zurück.
> Alle Änderungen nach dem Snapshot-Zeitpunkt gehen verloren.

---

### Velero — Kubernetes-natives Backup

Velero ist das meistgenutzte Open-Source-Tool für Kubernetes-Backups.
Es sichert Kubernetes-Objekte und — über CSI-Snapshots oder Restic — auch Volumes.

#### Architektur

```
kubectl apply -f ...
       │
       ▼
   Velero Server (läuft im Cluster)
       │
       ├──► Kubernetes API → sichert Objekte als JSON in S3/GCS/Azure Blob
       │
       └──► CSI Snapshots / Restic → sichert Volume-Inhalte
```

#### Installation (mit MinIO als S3-Speicher)

```
## Velero CLI installieren
wget https://github.com/vmware-tanzu/velero/releases/latest/download/velero-linux-amd64.tar.gz
tar -xf velero-linux-amd64.tar.gz
mv velero-linux-amd64/velero /usr/local/bin/

## Velero im Cluster installieren (Beispiel mit S3)
velero install \
  --provider aws \
  --plugins velero/velero-plugin-for-aws:v1.9.0 \
  --bucket velero-backups \
  --backup-location-config region=eu-central-1 \
  --use-node-agent \
  --default-volumes-to-fs-backup
```

#### Backup erstellen

```
## Gesamten Cluster sichern
velero backup create cluster-backup-$(date +%Y%m%d)

## Einzelnen Namespace sichern
velero backup create shop-backup --include-namespaces production-shop

## Backup mit Zeitplan (täglich um 2:00 Uhr)
velero schedule create daily-backup --schedule="0 2 * * *" --include-namespaces production
```

#### Backup-Status prüfen

```
velero backup get
velero backup describe cluster-backup-20240522
velero backup logs cluster-backup-20240522
```

Erwartete Ausgabe (backup describe):
```
Name:         cluster-backup-20240522
Namespace:    velero
...
Phase:        Completed
...
Included Namespaces:  *
Total items to be backed up:  312
Items backed up:              312
```

#### Wiederherstellen

```
## Vollständige Wiederherstellung
velero restore create --from-backup cluster-backup-20240522

## Nur einen Namespace wiederherstellen
velero restore create --from-backup cluster-backup-20240522 \
  --include-namespaces production-shop

## Restore-Status prüfen
velero restore get
velero restore describe <restore-name>
```

---

### CSI Volume Snapshots

CSI Snapshots sind der standardisierte Weg, PersistentVolumes direkt auf Storage-Ebene
zu sichern — schnell, konsistent, ohne Datenkopie über das Netzwerk.

#### VolumeSnapshot erstellen

```
## vi snapshot.yml
apiVersion: snapshot.storage.k8s.io/v1
kind: VolumeSnapshot
metadata:
  name: postgres-snapshot-20240522
spec:
  volumeSnapshotClassName: csi-aws-vsc
  source:
    persistentVolumeClaimName: postgres-data
```

```
kubectl apply -f snapshot.yml -n production
kubectl get volumesnapshot -n production
```

#### PVC aus Snapshot wiederherstellen

```
## vi pvc-from-snapshot.yml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: postgres-data-restored
spec:
  dataSource:
    name: postgres-snapshot-20240522
    kind: VolumeSnapshot
    apiGroup: snapshot.storage.k8s.io
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 20Gi
```

---

### Datenbankbackups auf Anwendungsebene

CSI-Snapshots und Velero sichern Dateien — aber eine laufende Datenbank kann in einem
inkonsistenten Zustand sein. Für konsistente Backups braucht es datenbankspezifische Tools.

#### PostgreSQL (pg_dump)

```
## Backup als Job im Cluster ausführen
kubectl run pg-backup --restart=Never --rm -i \
  --image=postgres:16 \
  --env="PGPASSWORD=geheim" \
  -- pg_dump -h postgres-service -U myuser mydatabase \
  > backup-$(date +%Y%m%d).sql
```

Oder als CronJob für automatisches Backup:

```
## vi 01-pg-backup-cronjob.yml
apiVersion: batch/v1
kind: CronJob
metadata:
  name: postgres-backup
spec:
  schedule: "0 3 * * *"
  jobTemplate:
    spec:
      template:
        spec:
          restartPolicy: OnFailure
          containers:
          - name: pg-backup
            image: postgres:16
            env:
            - name: PGPASSWORD
              valueFrom:
                secretKeyRef:
                  name: postgres-secret
                  key: password
            command:
            - /bin/sh
            - -c
            - |
              pg_dump -h postgres-service -U myuser mydatabase \
              | gzip > /backup/dump-$(date +%Y%m%d-%H%M).sql.gz
            volumeMounts:
            - name: backup-storage
              mountPath: /backup
          volumes:
          - name: backup-storage
            persistentVolumeClaim:
              claimName: backup-pvc
```

#### MySQL / MariaDB

```
kubectl run mysql-backup --restart=Never --rm -i \
  --image=mysql:8 \
  -- mysqldump -h mysql-service -u root -pgeheim --all-databases \
  > backup-$(date +%Y%m%d).sql
```

#### Restore PostgreSQL

```
kubectl run pg-restore --restart=Never --rm -i \
  --image=postgres:16 \
  --env="PGPASSWORD=geheim" \
  -- psql -h postgres-service -U myuser mydatabase \
  < backup-20240522.sql
```

---

### Wiederherstellungsszenarien

#### Szenario 1: Einzelne Ressource versehentlich gelöscht

```
## Velero Backup vorhanden → gezielter Restore
velero restore create --from-backup daily-backup-20240522 \
  --include-resources deployments \
  --selector app=payment-service

## Alternativ: aus Git (wenn GitOps genutzt wird)
git checkout HEAD~1 -- kubernetes/payment/deployment.yml
kubectl apply -f kubernetes/payment/deployment.yml
```

#### Szenario 2: Namespace komplett gelöscht

```
velero restore create --from-backup daily-backup-20240522 \
  --include-namespaces production-shop
```

#### Szenario 3: Cluster-Totalausfall (neuer Cluster)

```
## 1. Neuen Cluster aufsetzen (Kubernetes installieren)
## 2. Velero im neuen Cluster installieren
## 3. Auf das gleiche Backup-Ziel (S3) zeigen
velero install \
  --provider aws \
  --bucket velero-backups \
  --backup-location-config region=eu-central-1

## 4. Backups werden automatisch erkannt
velero backup get

## 5. Vollständig wiederherstellen
velero restore create --from-backup cluster-backup-20240522
```

#### Szenario 4: Datenkorruption (Anwendungsebene)

```
## Datenbank stoppen / in Wartungsmodus
kubectl scale deployment postgres --replicas=0 -n production

## Altes Volume löschen und aus Snapshot wiederherstellen
kubectl delete pvc postgres-data -n production
kubectl apply -f pvc-from-snapshot.yml -n production

## Datenbank starten
kubectl scale deployment postgres --replicas=1 -n production
```

---

### Backup-Strategie im Vergleich

| Methode | Was wird gesichert | Konsistenz | Geschwindigkeit | Granularität |
|---------|-------------------|------------|-----------------|--------------|
| **etcd Snapshot** | Kubernetes-Objekte | Hoch | Schnell | Cluster-weit |
| **Velero (Objekte)** | Kubernetes-Objekte | Hoch | Schnell | Namespace/Label |
| **Velero + Restic** | Objekte + Volumes | Datei-konsistent | Langsam (Datei-Kopie) | Namespace/Label |
| **CSI Snapshot** | Volume-Inhalt | Storage-konsistent | Sehr schnell | Pro PVC |
| **pg_dump / mysqldump** | Datenbankinhalt | Transaktions-konsistent | Mittel | Pro DB/Tabelle |

**Empfehlung für Produktivumgebungen:**
- Velero für Kubernetes-Objekte (täglich, stündlich für kritische Namespaces)
- CSI Snapshots für Volumes (stündlich)
- pg_dump/mysqldump für Datenbanken (täglich, vor Migrationen)
- etcd Snapshot vor jedem Cluster-Upgrade

---

### Zusammenfassung

Kubernetes-Backups bestehen aus mehreren unabhängigen Schichten — wer nur etcd sichert,
verliert im Ernstfall alle Anwendungsdaten. Wer nur Volumes sichert, kann den Cluster
nicht wiederherstellen.

Eine vollständige Backup-Strategie kombiniert:
1. **Velero** für Kubernetes-Objekte (alle Namespaces, täglich)
2. **CSI Snapshots** für Persistent Volumes (stündlich)
3. **Datenbankdumps** für transaktionskonsistente Backups (täglich)
4. **etcd Snapshots** vor Cluster-Upgrades und Änderungen an der Control Plane

Entscheidend ist nicht nur, dass Backups laufen — sondern dass Restores regelmäßig
getestet werden. Ein nicht getestetes Backup ist kein Backup.

## Kubernetes - Tipps & Tricks 

### Assigning Pods to Nodes


### Walkthrough 

```
## leave n3 as is 
kubectl label nodes n7 rechenzentrum=rz1
kubectl label nodes n17 rechenzentrum=rz2
kubectl label nodes n27 rechenzentrum=rz2

kubectl get nodes --show-labels
```

```
## nginx-deployment 
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
spec:
  selector:
    matchLabels:
      app: nginx
  replicas: 9 # tells deployment to run 2 pods matching the template
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:latest
        ports:
        - containerPort: 80
      nodeSelector:
        rechenzentrum: rz2

## Let's rewrite that to deployment 
apiVersion: v1
kind: Pod
metadata:
  name: nginx
  labels:
    env: test
spec:
  containers:
  - name: nginx
    image: nginx
    imagePullPolicy: IfNotPresent
  nodeSelector:
    rechenzentrum=rz2



```

### Ref:

  * https://kubernetes.io/docs/concepts/scheduling-eviction/assign-pod-node/

## Kubernetes - Documentation 

### LDAP-Anbindung

  * https://github.com/apprenda-kismatic/kubernetes-ldap

### Helpful to learn - Kubernetes

  * https://kubernetes.io/docs/tasks/

### Environment to learn

  * https://killercoda.com/killer-shell-cks

### Environment to learn II

  * https://killercoda.com/

### Youtube Channel

  * https://www.youtube.com/watch?v=01qcYSck1c4

## Kubernetes -Wann / Wann nicht 

### Kubernetes Wann / Wann nicht


### Frage: Kubernetes: Sollen wir das machen und was kost' mich das ? 

#### Rechtliche Regulatorien 

#### Nationale Grenzen 

#### Cloud oder onPrem (private Cloud)

#### Gegenfragen:

```
1. Monolithisches System (SAP Rx) <-> oder stark modulares System (Web-Applikation mit microservices)
    
   Kubernetes : weniger sinnvoll  <-> sehr sinnvoll.
   
   
```

#### Kosten: 

```
  o Konzeption / Planung 
  o Cluster / Manpower (Cluster-Kompetenz) 
  o Neue Backup-Strategie / Software 
  o Monitoring (ELK / EFK - STack (Elastich Search / Logstash-Fluent)) 
```

#### Anforderungen an Last 

  * Statisch (immer gleich) 
  * Dynamisch (stark wechselnd) - Einsparpotential durch Features Cloudanbieter (nur so viel bezahlen wie ich nutze) 

#### Nutzt mir Skailierung und kann ich skalieren

  * Gibt meine Applikation 
  * Habe durch mehr Webservice der gleichen Typs eine bessere Performance 

#### Kubernetes -> Kategorien. Warum ? 

  * Kosten durch Umstellung auf Cloud senken ? 
  * Automatisches Skalieren meiner Software bei Hochlast / Bedarf (verbunden mit dynamische Kosten) 
  * Erleichtertes Handling Updates (schnelleres Time-To-Market -> neuere Versioninierung) 

## Kubernetes - Hardening 

### Kubernetes Tipps Hardening


### PSA (Pod Security Admission) 

```
Policies defined by namespace.
e.g. not allowed to run container as root.

Will complain/deny when creating such a pod with that container type

```

### Möglichkeiten in Pods und Containern 

```
## für die Pods
kubectl explain pod.spec.securityContext 
kubectl explain pod.spec.containers.securityContext
```

### Example (seccomp / security context) 

```
A. seccomp - profile
https://github.com/docker/docker/blob/master/profiles/seccomp/default.json

```

```
apiVersion: v1
kind: Pod
metadata:
  name: audit-pod
  labels:
    app: audit-pod
spec:
  securityContext:
    seccompProfile:
      type: Localhost
      localhostProfile: profiles/audit.json

  containers:

  - name: test-container
    image: hashicorp/http-echo:0.2.3
    args:
    - "-text=just made some syscalls!"
    securityContext:
      allowPrivilegeEscalation: false

```

### SecurityContext (auf Pod Ebene) 

```
kubectl explain pod.spec.containers.securityContext 

```


### NetworkPolicy 

```
## Firewall Kubernetes 
```

## Kubernetes Deployment Scenarios 

### Deployment green/blue,canary,rolling update


### Canary Deployment 

```
A small group of the user base will see the new application 
(e.g. 1000 out of 100.000), all the others will still see the old version

From: a canary was used to test if the air was good in the mine 
(like a test balloon) 
```

### Blue / Green Deployment 

```
The current version is the Blue one 
The new version is the Green one 

New Version (GREEN) will be tested and if it works  
the traffic will be switch completey to the new version (GREEN) 

Old version can either be deleted or will function as fallback 
```

### A/B Deployment/Testing 

```
2 Different versions are online, e.g. to test a new design / new feature 
You can configure the weight (how much traffic to one or the other) 
by the number of pods
```

#### Example Calculation 

```
e.g. Deployment1: 10 pods
Deployment2: 5 pods

Both have a common label,
The service will access them through this label 
```

### Praxis-Übung A/B Deployment


### Walkthrough 

```
cd
cd manifests
mkdir ab 
cd ab 
```

```
## vi 01-cm-version1.yml 
apiVersion: v1
kind: ConfigMap
metadata:
  name: nginx-version-1
data:
  index.html: |
    <html>
    <h1>Welcome to Version 1</h1>
    </br>
    <h1>Hi! This is a configmap Index file Version 1 </h1>
    </html>
```

```
## vi 02-deployment-v1.yml 
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deploy-v1
spec:
  selector:
    matchLabels:
      version: v1
  replicas: 2
  template:
    metadata:
      labels:
        app: nginx
        version: v1
    spec:
      containers:
      - name: nginx
        image: nginx:latest
        ports:
        - containerPort: 80
        volumeMounts:
            - name: nginx-index-file
              mountPath: /usr/share/nginx/html/
      volumes:
      - name: nginx-index-file
        configMap:
          name: nginx-version-1
```

```
## vi 03-cm-version2.yml 
apiVersion: v1
kind: ConfigMap
metadata:
  name: nginx-version-2
data:
  index.html: |
    <html>
    <h1>Welcome to Version 2</h1>
    </br>
    <h1>Hi! This is a configmap Index file Version 2 </h1>
    </html>
```

```
## vi 04-deployment-v2.yml 
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deploy-v2
spec:
  selector:
    matchLabels:
      version: v2
  replicas: 2
  template:
    metadata:
      labels:
        app: nginx
        version: v2
    spec:
      containers:
      - name: nginx
        image: nginx:latest
        ports:
        - containerPort: 80
        volumeMounts:
            - name: nginx-index-file
              mountPath: /usr/share/nginx/html/
      volumes:
      - name: nginx-index-file
        configMap:
          name: nginx-version-2
```

```
## vi 05-svc.yml 
apiVersion: v1
kind: Service
metadata:
  name: my-nginx
  labels:
    svc: nginx
spec:
  type: NodePort
  ports:
  - port: 80
    protocol: TCP
  selector:
    app: nginx
```

```
kubectl apply -f . 
## get external ip  
kubectl get nodes -o wide 
## get port
kubectl get svc my-nginx -o wide 
## test it with curl apply it multiple time (at least ten times)
curl <external-ip>:<node-port>
```

## Kubernetes Probes (Liveness and Readiness) 

### Übung Liveness-Probe



### Übung 1: Liveness (command) 

```
What does it do ? 
 
* At the beginning pod is ready (first 30 seconds)
* Check will be done after 5 seconds of pod being startet
* Check will be done periodically every 5 minutes and will check
  * for /tmp/healthy
  * if file is there will return: 0 
  * if file is not there will return: 1 
* After 30 seconds container will be killed
* After 35 seconds container will be restarted
```

```
## cd
## mkdir -p manifests/probes
## cd manifests/probes 
## vi 01-pod-liveness-command.yml 

apiVersion: v1
kind: Pod
metadata:
  labels:
    test: liveness
  name: liveness-exec
spec:
  containers:
  - name: liveness
    image: busybox
    args:
    - /bin/sh
    - -c
    - touch /tmp/healthy; sleep 30; rm -f /tmp/healthy; sleep 600
    livenessProbe:
      exec:
        command:
        - cat
        - /tmp/healthy
      initialDelaySeconds: 5
      periodSeconds: 5
```

```
## apply and test 
kubectl apply -f 01-pod-liveness-command.yml 
kubectl describe -l test=liveness pods 
sleep 30
kubectl describe -l test=liveness pods 
sleep 5 
kubectl describe -l test=liveness pods 
```

```
## cleanup
kubectl delete -f 01-pod-liveness-command.yml
 
``` 

### Übung 2: Liveness Probe (HTTP)

```
## Step 0: Understanding Prerequisite:
This is how this image works:
## after 10 seconds it returns code 500 
http.HandleFunc("/healthz", func(w http.ResponseWriter, r *http.Request) {
    duration := time.Now().Sub(started)
    if duration.Seconds() > 10 {
        w.WriteHeader(500)
        w.Write([]byte(fmt.Sprintf("error: %v", duration.Seconds())))
    } else {
        w.WriteHeader(200)
        w.Write([]byte("ok"))
    }
})
```

```
## Step 1: Pod  - manifest 
## vi 02-pod-liveness-http.yml
## status-code >=200 and < 400 o.k. 
## else failure 
apiVersion: v1
kind: Pod
metadata:
  labels:
    test: liveness
  name: liveness-http
spec:
  containers:
  - name: liveness
    image: k8s.gcr.io/liveness
    args:
    - /server
    livenessProbe:
      httpGet:
        path: /healthz
        port: 8080
        httpHeaders:
        - name: Custom-Header
          value: Awesome
      initialDelaySeconds: 3
      periodSeconds: 3
```

```
## Step 2: apply and test
kubectl apply -f 02-pod-liveness-http.yml
## after 10 seconds port should have been started 
sleep 10 
kubectl describe pod liveness-http

```


### Reference:
 
   * https://kubernetes.io/docs/tasks/configure-pod-container/configure-liveness-readiness-startup-probes/

### Funktionsweise Readiness-Probe vs. Liveness-Probe


### Why / Howto / 

  * Readiness checks, if container is ready and if it's not READY 
    * SENDS NO TRAFFIC to the container   
  

### Difference to LiveNess 

  * They are configured exactly the same, but use another keyword
    * readinessProbe instead of livenessProbe 

### Example 

```
readinessProbe:
  exec:
    command:
    - cat
    - /tmp/healthy
  initialDelaySeconds: 5
  periodSeconds: 5
```

### Reference 

  * https://kubernetes.io/docs/tasks/configure-pod-container/configure-liveness-readiness-startup-probes/#define-readiness-probes

## Linux und Docker Tipps & Tricks allgemein 

### Auf ubuntu root-benutzer werden


```
## kurs> 
sudo su -
## password von kurs eingegeben
## wenn wir vorher der benutzer kurs waren
```

### IP - Adresse abfragen


```
## IP-Adresse abfragen
ip a
```

### Hostname setzen


```
## als root 
hostnamectl set-hostname server.training.local 
## damit ist auch sichtbar im prompt 
su - 
```

### Proxy für Docker setzen


### Walktrough

```
## as root
systemctl list-units -t service | grep docker
systemctl cat snap.docker.dockerd.service
systemctl edit snap.docker.dockerd.service
## in edit folgendes reinschreiben
[Service]
Environment="HTTP_PROXY=http://user01:password@10.10.10.10:8080/"
Environment="HTTPS_PROXY=https://user01:password@10.10.10.10:8080/"
Environment="NO_PROXY= hostname.example.com,172.10.10.10"

systemctl show snap.docker.dockerd.service --property Environment
systemctl restart snap.docker.dockerd.service
systemctl cat snap.docker.dockerd.service
cd /etc/systemd/system/snap.docker.dockerd.service.d/
ls -la
cat override.conf
```

### Ref

  * https://www.thegeekdiary.com/how-to-configure-docker-to-use-proxy/

### vim einrückung für yaml-dateien


### Ubuntu (im Unterverzeichnis /etc/vim/vimrc.local - systemweit) 

```
hi CursorColumn cterm=NONE ctermbg=lightred ctermfg=white
autocmd FileType y?ml setlocal ts=2 sts=2 sw=2 ai number expandtab cursorline cursorcolumn
```

### Testen 

```
vim test.yml 
Eigenschaft: <return> # springt eingerückt in die nächste Zeile um 2 spaces eingerückt

## evtl funktioniert vi test.yml auf manchen Systemen nicht, weil kein vim (vi improved) 


```

### YAML Linter Online

  * http://www.yamllint.com/

### Läuft der ssh-server


```
systemctl status sshd 
systemctl status ssh
```

### Basis/Parent - Image erstellen


### Auf Basis von debootstrap 

```
## Auf einem Debian oder Ubuntu - System 
## folgende Schritte ausführen 
## z.B. virtualbox -> Ubuntu 20.04. 

### alles mit root durchführen
apt install debootstrap
cd
debootstrap focal focal > /dev/null
tar -C focal -c . | docker import - focal 

## er gibt eine checksumme des images 
## so kann ich das sehen
## müsste focal:latest heissen
docker images

## teilchen starten 
docker run --name my_focal2 -dit focal:latest bash 

## Dann kann ich danach reinwechseln 
docker exec -it my_focal2 bash 
```

### Virtuelle Maschine Windows/OSX mit Vagrant erstellen

```
## Installieren.
https://vagrantup.com 
## ins terminal 
cd 
cd Documents 
mkdir ubuntu_20_04_test 
cd ubuntu_20_04_test
vagrant init ubuntu/focal64
vagrant up 
## Wenn die Maschine oben ist, kann direkt reinwechseln
vagrant ssh 
## in der Maschine kein pass notwendig zum Wechseln 
sudo su -

## wenn ich raus will
exit
exit

## Danach kann ich die maschine wieder zerstören
vagrant destroy -f 
```


### Ref:

  * https://docs.docker.com/develop/develop-images/baseimages/

### Eigenes unsichere Registry-Verwenden. ohne https


### Setup insecure registry (snap)

```

systemctl restart 

```

### Spiegel - Server (mirror -> registry-mirror) 

```
https://docs.docker.com/registry/recipes/mirror/

```

### Ref:

  * https://docs.docker.com/registry/insecure/

## Linux Tipps & Tricks

### Grafischen Modus deaktivieren


  * Besser: komplett deinstallieren

### Das geht immer 

```
## also root
sudo su -
## target ohne Grafik 
systemctl isolate multi-user
## Beim Start auch diese Target setzen
systemctl set-default multi-user
```

## VirtualBox Tipps & Tricks 

### VirtualBox 6.1. - Ubuntu für Kubernetes aufsetzen 


### Vorbereitung 

  * Ubuntu Server 22.04 LTS - ISO herunterladen

### Schritt 1: Virtuelle Maschine erstellen 

```
In VirtualBox Manager -> Menu -> Maschine -> Neu (Oder Neu icon) 

Seite 1:
Bei Name Ubuntu Server eingeben (dadurch wird gleich das richtige ausgewählt, bei den Selects) 
Alles andere so lassen.
Weiter 

Seite 2: 
Hauptspeicher mindest 4 GB , d.h. 4096 auswählen (füpr Kubernetes / microk8s)
Weiter

Seite 3:
Festplatte erzeugen ausgewählt lassen
Weiter

Seite 4: 
Dateityp der Festplatte: VDI ausgewählt lassen
Weiter 

Seite 5:
Art der Speicherung -> dynamisch alloziert ausgewählt lassen
Weiter 

Seite 6:
Dateiname und Größe -> bei Größe mindestens 30 GB einstellen (bei Bedarf größer) 
-> Erzeugen 
```

### Schritt 2: ISO einhängen / Netzwerk und starten / installieren

```
Neuen Server anklicken und ändern klicken:

1. 
Massenspeicher -> Controller IDE -> CD (Leer) klicken
CD - Symbol rechts neben -> Optisches Laufwerk (sekundärer Master) -> klicken -> Abbild auswählen
Downgeloadetes ISO Ubuntu 22.04 auswählen -> Öffnen klicken 

2. 
Netzwerk -> Adapter 2 (Reiter) anklicken -> Netzwerkadapter aktivieren
Angeschlossen an -> Host-only - Adapter 

3. 
unten rechts -> ok klicken 

```

### Schritt 3: Starten klicken und damit Installationsprozess beginnen

```
Try or install Ubuntu Server -> ausgewählt lassen

Seite 1:
Use up -.... Select your language 
-> English lassen
Enter eingeben

Seite 2: Keyboard Configuration
Layout auswählen (durch Navigieren mit Tab-Taste) -> Return
German auswählen (Pfeiltaste nach unten bis German, dann return)
Identify Keyboard -> Return
Keyboard Detection starting -> Ok 
Jetzt die gewünschten tasten drücken und Fragen beantworten
Layout - Variante bestätigen mit OK 

-> Done 

Seite 3: Choose type of install 
Ubuntu - Server ausgewählt lassen

-> Done 

Seite 4: Erkennung der Netzwerkkarten 
(192.168.56.1x) sollte auftauchen

-> Done 

Seite 5: Proxy

leer lassen

-> Done 

Seite 6: Mirror Address

kann so bleiben

-> Done 

Seite 7: 

Guided Storage konfiguration
Entire Disk 

-> Done 

Seite 8: File System Summary

-> Done 

Seite 9: Popup: Confirm destructive action 
Bestätigen, dass gesamte Festplatte überschrieben wird
(kein Problem, da Festplatte ohnehin leer und virtuell)

-> Continue 

Seite 10: Profile Setup

User eingeben / einrichten 
Servernamen einrichten 

-> Done 

Seite 11: SSH Setup 

Haken bei: Install OpenSSH Server 
setzen

-> Done 

Seite 12: Featured Server Snaps 

Hier brauchen wir nichts auswählen, alles kann später installiert werden

-> Done 

Seit 13:  Installation

Warten bis Installation Complete und dies auch unten angezeigt wird (Reboot Now):
(es dauert hier etwas bis alle Updates (unattended-upgrades) im Hintergrund durchgeführt worden sind) 

-> Reboot Now 

Wenn "Failed unmounting /cdrom" kommt 
dann einfach Server stoppen
-> Virtual Box Manager -> Virtuelle Maschine auswählen -> Rechte Maustaste -> Schliessen ->  Ausschalten 

```

### Schritt 4: Starten des Gast-Systems in virtualbox 

```
* Im VirtualBox Manager auf virtuelle Maschine klicken
* Neben dem Start - Pfeil -> Dreieck anklicken und Ohne Gui starten wählen 
* System startet dann im Hintergrund (kein 2. Fenster)
```

### Erklärung 

 * Console wird nicht benötigt, da wir mit putty (ssh) arbeiten zum Administrieren des Clusters
 * Putty-Verbindung muss nur auf sein, wenn wir administrieren
 * Verwendung des Clusters (nutzer/Entwickler) erfolgt ausschliesslich über kubectl in powershell !! 

### VirtualBox 6.1. - Shared folder aktivieren


### Prepare 

```
Walkthrough

## At the top menu of the virtual machine 
## Menu -> Geräte -> Gasterweiterung einlegen 

## In the console do a 
mount /dev/cdrom /mnt
cd /mnt
sudo apt-get install -y build-essential linux-headers-`uname -r`
sudo ./VBoxLinuxAdditions.run 


sudo reboot

```

### Configure 

```
Geräte -> Gemeinsame Ordner 
Hinzufügen (blaues Ordnersymbol mit + ) -> 
Ordner-Pfad: C:\Linux (Ordner muss auf Windows angelegt sein) 
Ordner-Name: linux
checkbox nicht ausgewählt bei : automatisch einbinden, nur lesbar
checkbox ausgewählt bei: Permanent erzeugen

Dann rebooten

In der virtuellen Maschine:  
sudo su -
mkdir /linux
## linux ist der vergebene Ordnername 
mount -t vboxsf linux /linux 

## Optional, falls du nicht zugreifen kannst:
sudo usermod -aG vboxsf root 
sudo usermod -aG vboxsf <your-user>


```


### persistent setzen (beim booten mounten) 
```
echo "linux	/linux	vboxsf	defaults	0	0" >> /etc/fstab 
reboot
```

### Reference:

  * https://gist.github.com/estorgio/1d679f962e8209f8a9232f7593683265

## CloudInit

### Kubernetes Client einrichten mit bash


```
##!/bin/bash 

groupadd sshadmin
USERS="11trainingdo $(echo tln{1..20})"
echo $USERS
for USER in $USERS
do
  echo "Adding user $USER"
  useradd -s /bin/bash --create-home $USER
  usermod -aG sshadmin $USER
  echo "$USER:deinpassword" | chpasswd
done

## We can sudo with 11trainingdo
usermod -aG sudo 11trainingdo 

## Now let us do some generic setup 
echo "Installing kubectl"
snap install --classic kubectl

echo "Installing helm"
snap install --classic helm 



## 20.04 and 22.04 this will be in the subfolder
if [ -f /etc/ssh/sshd_config.d/50-cloud-init.conf ]
then
  sed -i "s/PasswordAuthentication no/PasswordAuthentication yes/g" /etc/ssh/sshd_config.d/50-cloud-init.conf
fi

### both is needed 
sed -i "s/PasswordAuthentication no/PasswordAuthentication yes/g" /etc/ssh/sshd_config

usermod -aG sshadmin root

## TBD - Delete AllowUsers Entries with sed 
## otherwice we cannot login by group 

echo "AllowGroups sshadmin" >> /etc/ssh/sshd_config 
systemctl reload sshd 

#### BASH Completion ###
## update repo 
apt-get update 
apt-get install -y bash-completion
source /usr/share/bash-completion/bash_completion
## is it installed properly
type _init_completion

## 1. kubectl completion -> activate for all users
kubectl completion bash | sudo tee /etc/bash_completion.d/kubectl > /dev/null

## 2. helm completion -> activate for all users 
helm completion bash | sudo tee /etc/bash_completion.d/helm > /dev/null


## Activate syntax - stuff for vim
## Tested on Ubuntu 
echo "hi CursorColumn cterm=NONE ctermbg=lightred ctermfg=white" >> /etc/vim/vimrc.local 
echo "autocmd FileType y?ml setlocal ts=2 sts=2 sw=2 ai number expandtab cursorline cursorcolumn" >> /etc/vim/vimrc.local 

## Activate Syntax highlightning/autoindenting for nano 
## v1 - old version / remove if new version works 
##cd /usr/local/bin
##git clone https://github.com/serialhex/nano-highlight.git 
## Now set it generically in /etc/nanorc to work for all 
##echo 'include "/usr/local/bin/nano-highlight/yaml.nanorc"' >> /etc/nanorc 

#####################################
## v2 - new version / more simplistic
##################################### 
echo "include /usr/share/nano/yaml.nanorc" >> /etc/nanorc 
echo "set autoindent" >> /etc/nanorc
echo "set tabsize 2" >> /etc/nanorc
echo "set tabstospaces" >> /etc/nanorc 

## Install nfs-common for mounting, just in case we need it for persistant storage exercise 
apt-get install -y nfs-common

```

## Microservices - Messaging

### EventBus Implementierungen/Überblick


### Fertige Software, die einen Event Bus bereitsstellt

  * Kafka 
  * RabbitMQ
  * NATS 

### Was ist Ihre Aufgabe ? 

  * Events empfangen 
  * Events veröffentlichen (publish) für die Zuhörer (listener)
 

### Wie sehen Events aus ? 

  * Mit Events meinen wir Informations-Snippets 
    * Es ist nicht festgelegt, wie eine Event aussehen soll, es kann
      * Rohe Datenbytes
      * JSON
      * ein String
      * u.a. ... sein (was immer du verwenden willst)

### Was sind Listener ?

  * Listener sind Services, die von anderen Events von anderen Services erfahren wollen 

# Special deep dive 

## How are information about realeases stored 

  1. Stored in secrets 
  1. One secret for every release 
  1. gzip | base64 | base64 done before saving it there 

## what is stored in each secret object concerning helm 

  * Chart-Information 
  * chart-templates 
  * manifest (like it is applied by helm)

  ## Process of helm install

  ```
  helm pull -> helm template -> kubectl apply -f -> construct helm secret and create by sending it to 
  kube-api-server
  ```

  ## Process of helm uninstall 

  ```
  # Get secret of release, current revision 
  1. helm get secret sh.helm ..... 

  2. -> extract manifests

  3. kubectl delete -f manifest

  4. delete all secrets for that release with kubectl 
  kubectl delete secrets sh.helm.release.v1.my-mariadb.v2
  kubectl delete secrets sh.helm.release.v1.my-mariadb.v2

  ```


  ## How to get information for release (raw) 

  ```
  kubectl get secrets sh.helm.release.v1.my-mariadb.v2 -o jsonpath='{.data.release}' | base64 -d | base64 -d | gzip -d > all.yml


  ```

  ## How to get information of applied manifest 

  ```
  helm get manifest my-mariadb 
  ```
  

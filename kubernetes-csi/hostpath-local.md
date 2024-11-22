# HostPath Local 

## Prerequisites 

  * We need to create the folder on the file system
  * k3s (one node only) is running 

## Walkthrough 

```
# do this a root 
sudo su -
```

```
cd
mkdir -p manifests
cd manifests
mkdir local-hostpath
cd local-hostpath
nano 01-storageclass.yml 
```





## Reference :

  * https://lapee79.github.io/en/article/use-a-local-disk-by-local-volume-static-provisioner-in-kubernetes/

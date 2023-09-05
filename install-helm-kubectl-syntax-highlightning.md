# Installation und Einrichtung der Client-Tools (Ubuntu) 

## Tools helm und kubectl 

```
snap install kubectl --classic 
snap install helm --classic
kubectl completion bash > /etc/bash_completion.d/kubectl
helm completion bash > /etc/bash_completion.d/helm
```



## Highlightning und Indenting nano

```
sudo echo "include /usr/share/nano/yaml.nanorc" >> /etc/nanorc 
sudo echo "set autoindent" >> /etc/nanorc
sudo echo "set tabsize 2" >> /etc/nanorc
sudo echo "set tabstospaces" >> /etc/nanorc 
```

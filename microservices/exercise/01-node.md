# Step 1: Setup node 

## Prerequisites

```
# We have set an ubuntu 22.04 LTS server with a user 11trainingdo 
# on digitalocean 
# When you set up a server you can use a script under advanced options 

# or: we have another server at hand 

```

```
# just in case curl is not installed 
apt install -y curl 
cd 
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.1/install.sh | bash
source ~/.bashrc 
# Install latest stable version 
nvm install lts/hydrogen 

# test if it is installed
nvm list 
```


```

# test node 
node 
>.exit

```

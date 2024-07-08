# Scanning image with docker scan (using snyk) 

## ACHTUNG_ Deprecated - USE Docker Scout instead (only Docker Desktop ?) 

## Prerequisites 

```
# install docker plugin in some cases
# Ubuntu
apt install docker-scan-plugin 
```

```
You need to be logged in on docker hub with docker login 
(with your account credentials)
```


## Example 

```
# Snyk (docker scan) 
docker help scan
docker scan --json --accept-license dockertrainereu/jm-hello-docker  > result.json
```

# Multi-stage build 

## Warum ? 

  * Oberfläche klein für Angriffe 

## Beispiel - Übung 


```
cd
mkdir -p projects/multi-stage-golang
cd projects/multi-stage-golang
```

```
nano main.go
```

```
package main

import "fmt"

func main() {
  fmt.Println("hello, world")
}
```


```
nano Dockerfile
```


```
FROM golang:1.25 AS binary
WORKDIR /src
COPY main.go .
RUN go build -o /bin/hello ./main.go

FROM scratch
COPY --from=binary /bin/hello /bin/hello
CMD ["/bin/hello"]
```

```
docker build -t golang-app:1.0 .
```

```
docker images
docker run --name golang-app golang-app:1.0
docker container ls -a
```

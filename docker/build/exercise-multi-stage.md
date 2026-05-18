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
nano Dockerfile
```


```
FROM golang:1.25
WORKDIR /src
COPY <<EOF ./main.go
package main

import "fmt"

func main() {
  fmt.Println("hello, world")
}
EOF
RUN go build -o /bin/hello ./main.go

FROM scratch
COPY --from=0 /bin/hello /bin/hello
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

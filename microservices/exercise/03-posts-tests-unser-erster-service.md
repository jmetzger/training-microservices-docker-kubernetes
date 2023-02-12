# Testing Posts 

## Start service posts 

```
cd 
cd posts
npm start 
# output will listening to :4000 
```

## Find external ip 

```
# in most cases look for eth0 or enp0s3 or enp0s8 
# Example: 134.122.93.133
ip -br a 
```

## Open postmon web interface or donwload it.

```
# Step 1: Send a post 

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
# Step 2: Get all posts 
- Change http-request to send 
- Set: Header -> Content-Type -> to  application/json  # this is need otherwice json is not detected from server 
```


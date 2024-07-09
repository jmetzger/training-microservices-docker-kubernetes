# Dead Letter Queue 

## What is it ?




## When not to use it ?

```
For example, it’s not recommended to use a DLQ for a queue where the exact order of messages is important,
as reprocessing a DLQ message breaks the order of the messages on arrival.
```


## How to implement it ?


## Reference 

  * https://www.baeldung.com/kafka-spring-dead-letter-queue

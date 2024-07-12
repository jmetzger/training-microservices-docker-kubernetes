# Change schema, when there is a breaking changed 

## Step 1: Status Quo 

  1. Producer P1 is producing Message to Topic T1
  1. Consumer C1 and C2 are consuming this topic

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/25a964ce-ca64-4403-8685-70e53346e6b4)

## Step 2: New Topic T2 with breaking Schema S2 

   1. Topic is currently empty 

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/baef7656-386d-42e7-916b-693b6ced0bb5)

## Step 3: Maintaining history order with migration component (Mode 1 -> 2)

  * reproducing all existing records from topic T1 on topic T2
    * by transforming them into messages following schema S2 and producing them on topic T2.
  * Same keys are to be used (so it will be in the same partitions)
  * reproduced messages are to retain the original timestamp
    * in order to preserve the semantics of the message
  * No Downtime: producer P1 can meanwhile still produce new messages that may get consumed by existing consumer
  * Every message from P1 will be picked up by the migration component and reproduced in T2

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/2c220ecc-2f4d-4590-b6ab-7fceb5e4e3f7)
![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/2eb44846-96ee-4523-99a7-0dc396b65aa0)

## Step 4: New Version P1 (will not send to T1) any more 

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/61344c81-d2da-4d6f-9c9a-f94c072ae691)

## Step 5: P1 sends messages to T2 (after migration component has reproduced ...)

  1. Migration component has reproduced all messages from T1 -> T2
  1. AFTER THAT ! P1 will start to send messages to T2

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/c752284e-fb77-4b31-9bac-1fd50c532cca)

## Step 6: Switching operation mode of migration component (mode: 2->1)

  1. Migration component will from now on consume messages from topic T2 and reproduce them on topic T1.

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/131fa36c-3b52-49fb-9fa0-5d3c5ebb4d2f)

## Step 7: Ready for consumers 

  1. It is now possible for the consumers C1 and C2 to switch to T2 at any time

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/2606dc14-e2b1-48fd-ad6e-a2aa6a6088d7)

## Step 8: All consumers have migrated 

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/4269a17e-f9ef-44af-8889-7a8a214b406d)

## Step 9: Cleanup, when all have migrated 

![image](https://github.com/jmetzger/training-microservices-docker-kubernetes/assets/1933318/1c4ed6c5-3d6d-4cc5-aea3-b0a9595df957)


## Reference

  * https://cymo.eu/blog/a-strategy-for-dealing-with-breaking-schema

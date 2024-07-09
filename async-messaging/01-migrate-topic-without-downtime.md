# Migrate Topic without downtime (Schema without breaking change)

## How can you be sure, that consumer goes not get data from the old topic 

  * Producer should only (!) write to one topic (the newest)

## How to switch to a new version ? (Part 1: producer) 

  1. Every topics has a version
  1. New Version :: myTopic.v0 -> myTopic.v1
  1. Whenever a new version is there, we will write it into topics: _meta_version
  1. Producer will be consumer for the _meta_version and wil get to know about the topic
  1. Producer will immediately write to the new topic, BUT ONLY to the new topic

## How to switch to a new version ? (Part 2: consumer) 

  1. consumer has to drain the old topic before switching to the new one
  1. how to know it is drained ? Ask for "watermark" offset (gives back the last offset)
  1. When you have the last message with the watermark offset -> switch to new topic

## Watermark - Background 

  1. The offset of the last message in topic, can be retrieved with "watermark"
  1. Example Code: https://github.com/confluentinc/confluent-kafka-python/blob/master/examples/get_watermark_offsets.py

## Reference:

  * https://gauravsarma1992.medium.com/migrating-kafka-topic-without-downtime-f863819cfb3d

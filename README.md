토픽 생성 (카프카 토픽 중 한개에 접근한다.)

``` 
bin/kafka-topics.sh --create \
--bootstrap-server localhost:9092 \
--topic payments \
--partitions 2 \
--replication-factor 2

bin/kafka-configs.sh \
--bootstrap-server localhost:9092 \
--alter --add-config min.insync.replicas=2 \
--topic payments
```
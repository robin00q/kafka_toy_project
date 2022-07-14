
아래의 명령어를 통해 결제완료에 대한 토픽을 생성한다.
- topic 명 : payments
- 2개의 파티션을 생성하며, 리더파티션을 포함하여 2개의 replicas 가 생성된다.
- 결제에 대한 이벤트는 누락되면 안되는 중요한 이벤트기에, min.insync.replicas=2 로 설정한다.
  - 프로듀서는 acks=all 옵션을 사용하기에, 복제된 파티션에 레코드가 저장됐을 경우에만 이벤트가 발행된다.

``` 
kafka-topics --create \
--bootstrap-server localhost:9092 \
--topic payments \
--partitions 2 \
--replication-factor 2

kafka-configs \
--bootstrap-server localhost:9092 \
--alter --add-config min.insync.replicas=2 \
--topic payments

kafka-topics --alter \
--partitions 2 \
--bootstrap-server localhost:9092 \
--topic payments
```

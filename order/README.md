# Order

### 기능정의
1. 사용자는 제품들을 주문한다.
2. 사용자는 제품들에 대한 주문을 취소한다.

### 필요 도메인
- Order : 주문에 대한 정보를 총괄하는 Root Entity
- OrderLine : 주문 내에 하나씩의 제품을 표현한다.
- ShippingInfo : 주문에 대한 배송정보를 표현한다.

### 패키지 구성
- me.sjlee.order.domain :도메인에 대한 정보를 갖는다.
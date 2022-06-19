insert into shipping_info (shipping_id, shipping_addr1, shipping_addr2, shipping_zipcode, receiver_name, receiver_phone) values (1, '서울시 관악구', '805호', '12345', '이석준', '010-1234-1234')

insert into purchase_order (orderer_id, orderer_name, shipping_id, order_status, total_amounts) values (1, '이석준', 1, 'PAYMENT_WAITING', 500000)

insert into order_line (amounts, order_id, price, product_id, quantity) values (100000, 1, 10000, 1, 10)
insert into order_line (amounts, order_id, price, product_id, quantity) values (400000, 1, 20000, 2, 20)
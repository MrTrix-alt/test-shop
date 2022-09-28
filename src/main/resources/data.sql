INSERT INTO customer(id,name,last_name,age) VALUES(nextval('customer_seq'),'Иван','Иванов',18);
INSERT INTO customer(id,name,last_name,age) VALUES(nextval('customer_seq'),'Александр','Казаков',20);
INSERT INTO customer(id,name,last_name,age) VALUES(nextval('customer_seq'),'Мария','Александрова',18);
INSERT INTO customer(id,name,last_name,age) VALUES(nextval('customer_seq'),'Игорь','Попов',18);

INSERT INTO item(id,name,price) VALUES (nextval('item_seq'),'Телевизор',20000.01);
INSERT INTO item(id,name,price) VALUES (nextval('item_seq'),'Наушники',2500.22);
INSERT INTO item(id,name,price) VALUES (nextval('item_seq'),'Смартфон',15550.88);
INSERT INTO item(id,name,price) VALUES (nextval('item_seq'),'Соковыжималка',5500.75);
INSERT INTO item(id,name,price) VALUES (nextval('item_seq'),'Клавиатура',550.35);

INSERT INTO purchase(id,bought_at,customer_id) VALUES (nextval('purchase_seq'),'2020-02-02',1);
INSERT INTO purchase(id,bought_at,customer_id) VALUES (nextval('purchase_seq'),'2020-02-02',1);
INSERT INTO purchase(id,bought_at,customer_id) VALUES (nextval('purchase_seq'),'2022-09-27',1);
INSERT INTO purchase(id,bought_at,customer_id) VALUES (nextval('purchase_seq'),'2020-02-02',1);
INSERT INTO purchase(id,bought_at,customer_id) VALUES (nextval('purchase_seq'),'2020-02-03',2);

INSERT INTO purchase_item(id,item_id,purchase_id) VALUES (nextval('purchaseitem_seq'),1,1);
INSERT INTO purchase_item(id,item_id,purchase_id) VALUES (nextval('purchaseitem_seq'),2,3);
INSERT INTO purchase_item(id,item_id,purchase_id) VALUES (nextval('purchaseitem_seq'),3,3);
INSERT INTO purchase_item(id,item_id,purchase_id) VALUES (nextval('purchaseitem_seq'),3,3);
INSERT INTO purchase_item(id,item_id,purchase_id) VALUES (nextval('purchaseitem_seq'),1,2);
INSERT INTO purchase_item(id,item_id,purchase_id) VALUES (nextval('purchaseitem_seq'),2,2);
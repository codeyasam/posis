INSERT INTO product_type (name) VALUES ('bag');
INSERT INTO product_type (name) VALUES ('device');
INSERT INTO product_type (name) VALUES ('producttype3');

INSERT INTO end_product (name, product_type_id) VALUES ('tigernu', 1);
INSERT INTO end_product (name, product_type_id) VALUES ('bobby', 1);
INSERT INTO end_product (name, product_type_id) VALUES ('riut', 1);
INSERT INTO end_product (name, product_type_id) VALUES ('zhifu', 1);

INSERT INTO inventory (acquired_price, selling_price, stock_quantity, product_id)
	VALUES (800, 1500, 15, 1);

INSERT INTO inventory (acquired_price, selling_price, stock_quantity, product_id)
	VALUES (500, 1000, 5, 1);

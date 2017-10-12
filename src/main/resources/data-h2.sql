INSERT INTO product_type (name) VALUES ('bag');
INSERT INTO product_type (name) VALUES ('device');
INSERT INTO product_type (name) VALUES ('producttype3');

INSERT INTO end_product (name, product_type_id) VALUES ('tigernu', 1);
INSERT INTO end_product (name, product_type_id) VALUES ('bobby', 1);

INSERT INTO inventory (acquired_price, selling_price, stock_quantity) 
	VALUES (800, 1500, 15);

INSERT INTO inventory_product (inventory_id, product_id) VALUES (1, 1);


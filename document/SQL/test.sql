--オーダーあたりの全情報を取得する

SELECT 
--ordersテーブル
o.id AS o_id,
o.user_id AS o_user_id,
o.status AS o_status,
o.total_price AS o_total_price,
o.order_date AS o_order_date,
o.destination_name AS o_destination_name,
o.destination_email AS o_destination_email,
o.destination_zipcode AS o_destination_zipcode,
o.destination_address AS o_destination_address,
o.destination_tel AS o_destination_tel,
o.delivery_time AS o_delivery_time,
o.payment_method AS o_payment_method,
--order_itemsテーブル
oi.id AS oi_id,
oi.item_id AS oi_item_id,
oi.order_id AS oi_order_id,
oi.quantity AS oi_quantity,
oi.size AS oi_size,
--itemsテーブル
i.id AS i_id,
i.name AS i_name,
i.description AS i_description,
i.price_m AS i_price_m,
i.price_l AS i_price_l,
i.image_path AS i_image_path,
i.deleted AS i_deleted, 
--order_toppingsテーブル
ot.id AS ot_id,
ot.topping_id AS ot_topping_id,
ot.order_item_id AS ot_order_item_id,
--toppingテーブル
t.id AS t_id,
t.name AS t_name,
t.price_m AS t_price_m,
t.price_l AS t_price_l

FROM orders AS o 
LEFT OUTER JOIN order_items AS oi 
ON o.id = oi.order_id
LEFT OUTER JOIN items AS i 
ON oi.item_id = i.id 
LEFT OUTER JOIN order_toppings AS ot 
ON oi.id = ot.order_item_id
LEFT OUTER JOIN toppings AS t
ON ot.topping_id = t.id
WHERE
o.user_id = 1344464926
AND
o.status = 0
GROUP BY o.id,oi.id,i.id,ot.id,t.id
ORDER BY o.id;

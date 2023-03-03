SELECT
ot.id AS ot_id
t.name AS t_name,
t.price_m AS t_price_m,
t.price_l AS t_price_l,
oi.size AS oi_size,
oi.quantity AS oi_quantity,
i.image_path AS i_image_path,
i.name AS i_name,



FROM oder_toppings AS ot
LEFT OUTER JOIN  toppings AS t
ON ot.topping_id = t.id 

create table Products (
  product_id   bigSerial not null primary key,
  product_name varchar(45) not null,
  quantity     int not null,
  price        double precision not null,
  created_at   timestamp not null,
  updated_at   timestamp not null
);
// Use DBML to define your database structure
// Docs: https://dbml.dbdiagram.io/docs

Table parameter {
    id BIGSERIAL [primary key]
    key VARCHAR(100)
    value VARCHAR(500)
    state VARCHAR(1)
    of VARCHAR(50)
    additional VARCHAR(500)
}

TABLE permit {
    id BIGSERIAL [primary key]
    state VARCHAR(1)
    name VARCHAR(100)
    type VARCHAR(50)
    icon VARCHAR(50)
    parent BIGINT
    url VARCHAR(100)
}

TABLE role {
    id BIGSERIAL [primary key]
    name VARCHAR(100)
    state VARCHAR(1)
}

TABLE permit_role{
    id BIGSERIAL [primary key]
    permit BIGINT
    role BIGINT
}

Ref: permit.id < permit_role.permit
Ref: role.id < permit_role.role

 TABLE final_user{
    id BIGSERIAL [primary key]
    name VARCHAR(300) 
    auth_type VARCHAR(1) 
    email VARCHAR(100) 
    password VARCHAR(20)
    cellphone VARCHAR(12)
    role BIGINT 
    creation_date TIMESTAMP 
    address VARCHAR(100)
    identification VARCHAR(15) UNIQUE
    id_type VARCHAR(2)
    state VARCHAR(1) 
    token VARCHAR
    PRIMARY KEY (id)
}

Ref: final_user.role < role.id

TABLE type_acount{
    id BIGSERIAL [primary key]
    name VARCHAR(100)
    state VARCHAR(1)
    icon VARCHAR(500)
}

TABLE acount{
    id BIGSERIAL [primary key]
    name VARCHAR(100)
    amount VARCHAR(100)
    number VARCHAR(20)
    type_acount BIGINT
    state VARCHAR(1) 
}

Ref: acount.type_acount < type_acount.id

TABLE users_acount {
    id BIGSERIAL [primary key]
    final_user BIGINT
    acount BIGINT
    state VARCHAR(1)
}

Ref: users_acount.final_user > final_user.id
Ref: users_acount.acount > acount.id

TABLE movements{
    id BIGSERIAL  [primary key]
    final_user BIGINT
    acount_first VARCHAR 
    acount_second VARCHAR 
    amount VARCHAR
    type_move VARCHAR
    code VARCHAR
    date TIMESTAMP
}

Ref: movements.final_user < final_user.id

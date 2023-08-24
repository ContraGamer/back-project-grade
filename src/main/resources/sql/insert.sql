-- Ultima edición por Alexander Gonzalez -- 23-08-2023

-- Permisos iniciales
INSERT INTO permit(state,name,type,icon,parent,url) VALUES('A', 'Administración','group', 'apps',0,'');-- 1
INSERT INTO permit(state,name,type,icon,parent,url) VALUES('A', 'Cuenta','group', 'apps',0,'');-- 2
INSERT INTO permit(state,name,type,icon,parent,url) VALUES('A', 'Transacciones','group', 'apps',0,'');-- 3
INSERT INTO permit(state,name,type,icon,parent,url) VALUES('A', 'Usuarios','group', 'apps',1,'');-- 4
INSERT INTO permit(state,name,type,icon,parent,url) VALUES('A', 'Roles','group', 'apps',1,'');-- 5
INSERT INTO permit(state,name,type,icon,parent,url) VALUES('A', 'Permisos','group', 'apps',1,'');-- 6
INSERT INTO permit(state,name,type,icon,parent,url) VALUES('A', 'Gestionar','group', 'apps',2,'');-- 7
INSERT INTO permit(state,name,type,icon,parent,url) VALUES('A', 'Movimientos','group', 'apps',3,'');--8
INSERT INTO permit(state,name,type,icon,parent,url) VALUES('A', 'Trasferir','group', 'apps',3,'');-- 9
INSERT INTO permit(state,name,type,icon,parent,url) VALUES('A', 'Recargar','group', 'apps',3,'');-- 10

-- Roles iniciales
INSERT INTO role(name,state) VALUES('Super Administrador','A');-- 1
INSERT INTO role(name,state) VALUES('Administrador','A');-- 2
INSERT INTO role(name,state) VALUES('Cliente','A');-- 3

-- Permisos para Super administrador
INSERT INTO permit_role(permit,role) VALUES(1,1);
INSERT INTO permit_role(permit,role) VALUES(2,1);
INSERT INTO permit_role(permit,role) VALUES(3,1);
INSERT INTO permit_role(permit,role) VALUES(4,1);
INSERT INTO permit_role(permit,role) VALUES(5,1);
INSERT INTO permit_role(permit,role) VALUES(6,1);
INSERT INTO permit_role(permit,role) VALUES(7,1);
INSERT INTO permit_role(permit,role) VALUES(8,1);
INSERT INTO permit_role(permit,role) VALUES(9,1);
INSERT INTO permit_role(permit,role) VALUES(10,1);

-- Permisos para Administrador
INSERT INTO permit_role(permit,role) VALUES(1,2);
INSERT INTO permit_role(permit,role) VALUES(2,2);
INSERT INTO permit_role(permit,role) VALUES(3,2);
INSERT INTO permit_role(permit,role) VALUES(4,2);
INSERT INTO permit_role(permit,role) VALUES(7,2);
INSERT INTO permit_role(permit,role) VALUES(8,2);
INSERT INTO permit_role(permit,role) VALUES(9,2);
INSERT INTO permit_role(permit,role) VALUES(10,2);

-- Permisos para Cliente
INSERT INTO permit_role(permit,role) VALUES(2,3);
INSERT INTO permit_role(permit,role) VALUES(3,3);
INSERT INTO permit_role(permit,role) VALUES(7,3);
INSERT INTO permit_role(permit,role) VALUES(8,3);
INSERT INTO permit_role(permit,role) VALUES(9,3);
INSERT INTO permit_role(permit,role) VALUES(10,3);

-- Super administrador
INSERT INTO final_user(name,auth_type,email,password,cellphone,role,creation_date,address,identification,id_type,state)
            VALUES    ('Super administrador', 'A', 'superadministrador@gmail.com', 'Pass123$', '3003787938',1,NOW(),'','','01','A'); -- 1

-- Administrador
INSERT INTO final_user(name,auth_type,email,password,cellphone,role,creation_date,address,identification,id_type,state)
            VALUES    ('Anderson Alexander Gonzalez Ruiz', 'A', 'ander18158697@gmail.com', 'Alex123$', '3003787938',2,NOW(),'Cra 80 Bis # 7a - 15','1032484364','01','A'); -- 2

-- Cliente
INSERT INTO final_user(name,auth_type,email,password,cellphone,role,creation_date,address,identification,id_type,state)
            VALUES    ('July Rodriguez', 'A', 'july.rodriguez17@gmail.com', 'July17$', '3003787938',3,NOW(),'','20191373004','01','A'); -- 3


-- Tipos de cuenta
INSERT INTO type_acount(name, state, icon) VALUES('DÉBITO','A', 'https://cdn-icons-png.flaticon.com/512/1611/1611179.png');
INSERT INTO type_acount(name, state, icon) VALUES('CRÉDITO','A', 'https://cdn-icons-png.flaticon.com/512/4021/4021708.png');

-- Cuentas iniciales
INSERT INTO acount(name,amount,number,type_acount) VALUES('Cuenta nómina','0', '1234567812345671',1);
INSERT INTO acount(name,amount,number,type_acount) VALUES('Tarjeta crédito','0','1234567812345672',2);
INSERT INTO acount(name,amount,number,type_acount) VALUES('Cuenta nómina','500000','1234567812345673',1);
INSERT INTO acount(name,amount,number,type_acount) VALUES('Tarjeta crédito','1000000','1234567812345674',2);

-- Asignación de cuenta a clientes
INSERT INTO users_acount(final_user,acount) VALUES(3,1);
INSERT INTO users_acount(final_user,acount) VALUES(3,2);
INSERT INTO users_acount(final_user,acount) VALUES(2,1);
INSERT INTO users_acount(final_user,acount) VALUES(2,2);

-- Asignación de parametros
-- Colores
INSERT INTO parameter(key, value, state, of, additional) VALUES('color-td-primary', '#daa520', 'A', 'FRONT', 'DÉBITO');
INSERT INTO parameter(key, value, state, of, additional) VALUES('color-td-secundary', '#1B2631', 'A', 'FRONT', 'DÉBITO');
INSERT INTO parameter(key, value, state, of, additional) VALUES('color-tc-primary', '#1B2631', 'A', 'FRONT', 'CRÉDITO');
INSERT INTO parameter(key, value, state, of, additional) VALUES('color-tc-secundary', '#b8860b', 'A', 'FRONT', 'CRÉDITO');
INSERT INTO parameter(key, value, state, of, additional) VALUES('color-td-primary-dark', '#b8860b', 'A', 'FRONT', 'primary-color');
-- Security levels
INSERT INTO parameter(key, value, state, of, additional) VALUES('level-attack-without-security', 'true', 'A', 'ALL', 'without'); 
INSERT INTO parameter(key, value, state, of, additional) VALUES('level-attack-medium-security', 'false', 'I', 'ALL', 'medium');
INSERT INTO parameter(key, value, state, of, additional) VALUES('level-attack-high-security', 'false', 'I', 'ALL', 'high');
-- environment locations
INSERT INTO parameter(key, value, state, of, additional) VALUES('local-host', 'localhost:4200', 'A', 'BACK', '');
INSERT INTO parameter(key, value, state, of, additional) VALUES('environment-develop', '', 'I', 'BACK', '');
INSERT INTO parameter(key, value, state, of, additional) VALUES('environment-laboratory', '', 'I', 'BACK', '');
INSERT INTO parameter(key, value, state, of, additional) VALUES('environment-prod', '', 'I', 'BACK', '');
INSERT INTO parameter(key, value, state, of, additional) VALUES('time-session', '100', 'A', 'BACK', 'minutes');
-- 
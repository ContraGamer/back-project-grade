-- Ultima edición por Alexander Gonzalez -- 10-08-2022


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
INSERT INTO type_acount(name) VALUES('DÉBITO');
INSERT INTO type_acount(name) VALUES('CRÉDITO');

-- Cuentas iniciales
INSERT INTO acount(name,amount,number,type_acount) VALUES('Cuenta nómina','1234567812345678','0',1);
INSERT INTO acount(name,amount,number,type_acount) VALUES('Tarjeta crédito','1234567812345678','0',2);

-- Asignación de cuenta a clientes
INSERT INTO users_acount(final_user,acount) VALUES(3,1);
INSERT INTO users_acount(final_user,acount) VALUES(3,2);




















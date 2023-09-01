-- Database: banksdb

 DROP TABLE IF EXISTS transactions;
 DROP TABLE IF EXISTS accounts;
 DROP TABLE IF EXISTS banks;
 DROP TABLE IF EXISTS users;
 DROP TABLE IF EXISTS type_currency;
 DROP TABLE IF EXISTS type_transaction;

-- DROP DATABASE IF EXISTS banksdb;


--CREATE DATABASE banksdb;

CREATE TABLE IF NOT EXISTS banks
(
    id SERIAL NOT NULL PRIMARY KEY,
    name character varying(30) NOT NULL,
    address character varying(30)
);

INSERT INTO banks(name, address) VALUES ('Clever-Bank', 'Гомель, ул.Советская,5');
INSERT INTO banks(name, address) VALUES ('Alfa-Bank', 'Гомель, ул.Советская,71');
INSERT INTO banks(name, address) VALUES ('Belarusbank', 'Гомель, ул.Фрунзе,1');
INSERT INTO banks(name, address) VALUES ('Priorbank', 'Гомель, ул.Красноармейская,3');
INSERT INTO banks(name, address) VALUES ('MTBank', 'Гомель, ул.Советская,97');
INSERT INTO banks(name, address) VALUES ('Belagroprombank', 'Гомель, ул.Макаенка,31');

CREATE TABLE IF NOT EXISTS users
(
    id SERIAL NOT NULL PRIMARY KEY,
    lastname character varying(30) NOT NULL,
    name character varying(30) NOT NULL,
    middlename character varying(30) NOT NULL,
    address character varying(30),
    phone_number character varying(15)
);

INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Кечко', 'Елена', 'Петровна', 'Гомель, ул.Советская,17', '80291798196');

INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Сидоров', 'Иван', 'Петрович', 'Минск, пр.Независимости,2', '80441234567');

INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Сорокина', 'Анна', 'Андреевна', 'Гомель, ул.Телегина,7', '80334567891');

INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Наумов', 'Роман', 'Сергеевич', 'Гомель, ул.Хатаевича,30', '80447224315');

INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Иванов', 'Василий', 'Андреевич', 'Гомель, ул.Сергеева,10', '80337531526');

INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Ковалев', 'Артем', 'Романович', 'Речица, ул.Советская,87', '80291456321');
	
INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Самойлов', 'Михаил', 'Семенович', 'Брест, ул.Минская,13', '80332654978');

INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Бабич', 'Никита', 'Сергеевич', 'Гомель, пр.Октября,76', '80297651234');
	
INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Ложкин', 'Алексей', 'Александрович', 'Гомель, ул.Мазурова,4', '80293549127');
	
INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Шабалин', 'Александр', 'Алексеевич', 'Минск, ул.Независимости,45', '80447681256');
	
INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Давыдов', 'Кирилл', 'Алексеевич', 'Гомель, ул.Кирова,88', '80447352165');

INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Савельев', 'Савелий', 'Савельевич', 'Витебск, ул.Свободы,13', '80443156248');
	
INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Калинков', 'Артемий', 'Васильевич', 'Гомель, ул.Песина,75', '8293456710');
	
INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Шамычек', 'Андрей', 'Александрович', 'Гомель, ул.Головацкого,91', '80334512067');

INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Соколов', 'Виктор', 'Владимирович', 'Гомель, ул.Ильича,3', '80331023459');
	
INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Бужан', 'Максим', 'Русланович', 'Минск, ул.Азовская,4', '80291798145');
	
INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Газманов', 'Андрей', 'Васильевич', 'Гомель, ул.Ленина,100', '80291456210');

INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Смолячков', 'Роман', 'Игоревич', 'Минск, ул.Красноармейская,15', '80291785219');

INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Мохорев', 'Василий', 'Васильевич', 'Гомель, ул.Жарковского,63', '80297513268');
	
INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Романов', 'Арсений', 'Витальевич', 'Минск, ул.Радужная,15', '80447865124');
	
INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Березовская', 'Елена', 'Михайловна', 'Гомель, ул.Б.Царикова,31', '80291253697');

INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Лебедева', 'Ирина', 'Сергеевна', 'Минск, ул.Рокосовского,62', '80293574981');
	
INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Кузьменкова', 'Екатерина', 'Юрьевна', 'Гомель, ул.Б.Хмельницкого,67', '80333478560');
	
INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Зубей', 'Екатерина', 'Владимировна', 'Брест, ул.Сябровская,117', '80331023014');

INSERT INTO users(lastname, name, middlename, address, phone_number) 
	VALUES ('Синица', 'Дарья', 'Валерьевна', 'Гомель, ул.Барыкина,50', '80337564213');

CREATE TABLE IF NOT EXISTS type_currency
(
    id SERIAL NOT NULL PRIMARY KEY,
    name character varying(5) NOT NULL
);

INSERT INTO type_currency(name) VALUES ('BYN');
INSERT INTO type_currency(name) VALUES ('RUB');
INSERT INTO type_currency(name) VALUES ('USD');
INSERT INTO type_currency(name) VALUES ('EUR');

CREATE TABLE IF NOT EXISTS type_transaction
(
    id SERIAL NOT NULL PRIMARY KEY,
    type character varying(30) NOT NULL
);

INSERT INTO type_transaction(type) VALUES ('Перевод');
INSERT INTO type_transaction(type) VALUES ('Снятие средств');
INSERT INTO type_transaction(type) VALUES ('Пополнение счета');


CREATE TABLE IF NOT EXISTS accounts
(
    id SERIAL NOT NULL PRIMARY KEY,
    number_account character varying(50) NOT NULL UNIQUE,
    opening_date date NOT NULL,
    remainder double precision NOT NULL,
    id_user integer NOT NULL,
    id_bank integer NOT NULL,
    id_currency integer NOT NULL,
    FOREIGN KEY (id_bank) REFERENCES banks (id) ON DELETE CASCADE,
    FOREIGN KEY (id_currency) REFERENCES type_currency (id),
    FOREIGN KEY (id_user) REFERENCES users (id)
);

INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('456112', '2022-12-17 12:15:00', 400.00, 1, 1, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('65471', '2020-09-11 11:11:11', 100.00, 1, 2, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('456', '2021-12-07 17:00:10', 1000.00, 1, 3, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('789456', '2023-08-01 15:15:15', 150.00, 2, 1, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('AB1245', '2019-11-15 11:15:05', 300.00, 2, 3, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('KL12K', '2023-05-04 9:45:15', 250.00, 3, 1, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('LVB456', '2023-07-09 10:10:10', 10.00, 4, 4, 1);

INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('123AKM', '2023-05-02 16:50:50', 1.00, 5, 3, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('320SDF', '2023-01-23 17:23:01', 0.01, 6, 1, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('741825', '2022-03-24 13:24:15', 15.00, 7, 1, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('OUT987', '2022-03-24 09:10:17', 15.00, 7, 2, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('765WER45', '2022-03-24 15:45:47', 15.00, 7, 3, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('865423', '2020-09-09 15:53:10', 5.00, 8, 1, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('425FT7', '2019-03-30 16:34:15', 1.00, 9, 2, 1);

INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('A123 B456 C789', '2018-04-21 11:12:13', 14.00, 10, 3, 1);

INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('123', '2020-10-25 17:00:10', 10.00, 11, 5, 1);

INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('321', '2023-07-10 15:15:17', 170.00, 11, 1, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('789', '2022-09-30 09:09:09', 0.10, 12, 1, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('3201 7515 8888', '2023-05-05 09:09:09', 1.10, 12, 5, 1);

INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('3514QWER', '2018-07-13 12:36:50', 140.00, 13, 3, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('9621TNK', '2020-07-13 12:00:10', 74.00, 13, 4, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('6544FGB', '2020-12-01 14:45:10', 30.00, 14, 1, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('75315FGA', '2018-11-09 09:10:00', 15.00, 15, 1, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('PO7651T', '2022-03-07 15:14:10', 76.50, 15, 2, 1);

INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('23156FRAS', '2019-02-04 08:40:40', 20.00, 16, 3, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('740 2365', '2017-03-09 10:15:20', 25.00, 17, 4, 1);

INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('PO35210N', '2021-03-22 16:32:47', 68.70, 17, 1, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('7531349Q', '2021-12-02 11:48:30', 50.00, 18, 3, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('MHS96', '2021-06-06 09:06:06', 66.66, 18, 1, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('984316TYU', '2023-02-17 15:15:55', 60.00, 19, 4, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('DFV731M', '2020-01-15 17:37:37', 777.00, 20, 1, 1);

INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('TYR753103', '2023-03-12 18:00:40', 1500.00, 20, 5, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('TWQ51', '2023-07-18 16:59:56', 850.00, 21, 1, 1);

INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('RT09077', '2022-04-29 18:58:38', 88.00, 21, 3, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('QWERTY123', '2023-06-28 18:35:27', 20.00, 21, 4, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('UDS75412', '2023-05-27 14:42:32', 6500.00, 22, 1, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('AB12 CD65 EF98', '2021-06-29 14:42:32', 10.00, 22, 4, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('UYR123 45', '2023-08-15 13:15:12', 125.00, 23, 1, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('789159', '2023-03-20 16:23:48', 100.00, 23, 2, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('HGV2341', '2023-06-25 11:45:31', 754.00, 24, 4, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('7851POI', '2023-01-22 12:10:32', 31.00, 25, 1, 1);
	
INSERT INTO accounts(
	number_account, opening_date, remainder, id_user, id_bank, id_currency)
	VALUES ('Y45ERW1', '2022-01-10 19:36:01', 1.00, 25, 3, 1);
	
CREATE TABLE IF NOT EXISTS transactions
(
    number_check SERIAL NOT NULL PRIMARY KEY,
    transaction_date timestamp NOT NULL,
    id_type_of_transaction integer NOT NULL,
    money double precision NOT NULL,
    id_sender bigint,
    id_receiver bigint,
    FOREIGN KEY (id_type_of_transaction) REFERENCES type_transaction (id),
    FOREIGN KEY (id_receiver) REFERENCES accounts (id),
    FOREIGN KEY (id_sender) REFERENCES accounts (id)
);

--INSERT INTO transactions(transaction_date, id_type_of_transaction, money, id_sender, id_receiver)
--	VALUES (?, ?, ?, ?, ?);



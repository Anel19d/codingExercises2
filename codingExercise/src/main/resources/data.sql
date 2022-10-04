create table user_financial_account(
  id integer primary key AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  open_date date
);

create table spending_transaction(
  id integer primary key AUTO_INCREMENT,
  transaction_date date NOT NULL,
  account_id integer NOT NULL,
  spending_amount double NOT NULL,
  CONSTRAINT fk_user_financial_account_id
      FOREIGN KEY(account_id)
      REFERENCES user_financial_account(id)
);

--insert into user_financial_account (name, open_date) values ('Alfonso Herrera','2019-12-14');
--insert into user_financial_account (name, open_date) values ('Pedro Gonzalez','2020-08-20');
--insert into user_financial_account (name, open_date) values ('Maria Vazquez','2022-09-15');
--
--
--insert into spending_transaction (transaction_date, account_id, spending_amount) values
--('2021-08-23', 1, 1000.0);
--insert into spending_transaction (transaction_date, account_id, spending_amount) values
--('2021-09-11', 1, 2000.0);
--insert into spending_transaction (transaction_date, account_id, spending_amount) values
--('2021-10-10', 1, 500.0);
--insert into spending_transaction (transaction_date, account_id, spending_amount) values
--('2021-10-11', 1, 800.0);
--insert into spending_transaction (transaction_date, account_id, spending_amount) values
--('2022-09-16',2, 500.0);
--insert into spending_transaction (transaction_date, account_id, spending_amount) values
--('2022-09-17', 2, 300.0);
--insert into spending_transaction (transaction_date, account_id, spending_amount) values
--('2022-09-20',2, 200.0);
--insert into spending_transaction (transaction_date, account_id, spending_amount) values
--('2022-09-27', 3, 4000.0);
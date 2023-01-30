CREATE DATABASE IF NOT EXISTS trading_bot;

USE trading_bot;

CREATE TABLE IF NOT EXISTS Utilisateur(usr_id INT AUTO_INCREMENT, usr_email VARCHAR(50) NOT NULL, usr_password VARCHAR(51) NOT NULL, usr_signup_datetime DATETIME NOT NULL, PRIMARY KEY(usr_id), UNIQUE(usr_email));

CREATE TABLE IF NOT EXISTS Exchange(exc_id INT AUTO_INCREMENT, exc_name VARCHAR(50) NOT NULL, exc_url VARCHAR(100) NOT NULL, PRIMARY KEY(exc_id), UNIQUE(exc_name));

CREATE TABLE IF NOT EXISTS State(sta_id INT AUTO_INCREMENT, sta_name VARCHAR(50) NOT NULL, PRIMARY KEY(sta_id), UNIQUE(sta_name));

CREATE TABLE IF NOT EXISTS Side(sid_id INT AUTO_INCREMENT,sid_name VARCHAR(50) NOT NULL, PRIMARY KEY(sid_id), UNIQUE(sid_name));

CREATE TABLE IF NOT EXISTS Type(typ_id INT AUTO_INCREMENT, typ_name VARCHAR(50) NOT NULL,PRIMARY KEY(typ_id), UNIQUE(typ_name));

CREATE TABLE IF NOT EXISTS Account (acc_id INT AUTO_INCREMENT, exc_id INT NOT NULL, usr_id INT NOT NULL, PRIMARY KEY(acc_id), FOREIGN KEY(exc_id) REFERENCES Exchange(exc_id), FOREIGN KEY(usr_id) REFERENCES Utilisateur(usr_id));

CREATE TABLE IF NOT EXISTS Action(act_id INT AUTO_INCREMENT,act_datetime DATETIME NOT NULL,act_name VARCHAR(50) NOT NULL,typ_id INT NOT NULL,PRIMARY KEY(act_id),UNIQUE(act_datetime),UNIQUE(act_name),FOREIGN KEY(typ_id) REFERENCES Type(typ_id));

CREATE TABLE IF NOT EXISTS Bot(bot_id INT AUTO_INCREMENT,bot_start_date DATETIME NOT NULL,bot_end_date DATETIME NOT NULL,bot_initial_amount DOUBLE NOT NULL,bot_base_balance DOUBLE NOT NULL,bot_quote_balance DOUBLE NOT NULL,acc_id INT NOT NULL,PRIMARY KEY(bot_id),FOREIGN KEY(acc_id) REFERENCES Account(acc_id));

CREATE TABLE IF NOT EXISTS TransactionOrder(tra_id INT AUTO_INCREMENT,tra_datetime DATETIME NOT NULL,tra_base_amount DOUBLE NOT NULL,tra_quote_amount DOUBLE NOT NULL,tra_bid_price DOUBLE NOT NULL,sid_id INT NOT NULL,sta_id INT NOT NULL,bot_id INT NOT NULL,PRIMARY KEY(tra_id),FOREIGN KEY(sid_id) REFERENCES Side(sid_id),FOREIGN KEY(sta_id) REFERENCES State(sta_id),FOREIGN KEY(bot_id) REFERENCES Bot(bot_id));

CREATE TABLE IF NOT EXISTS Filler(fil_id INT AUTO_INCREMENT,fil_datetime DATETIME NOT NULL,fil_price DOUBLE NOT NULL,fil_base_amount DOUBLE NOT NULL,fil_quote__amount DOUBLE NOT NULL,tra_id INT NOT NULL,PRIMARY KEY(fil_id),FOREIGN KEY(tra_id) REFERENCES TransactionOrder(tra_id));

CREATE TABLE IF NOT EXISTS BalanceHistory(bal_id INT AUTO_INCREMENT,bal_datetime DATETIME NOT NULL,bal_base_amount DOUBLE NOT NULL,bal_quote_amount DOUBLE NOT NULL,bal_price DOUBLE NOT NULL,bot_id INT NOT NULL,PRIMARY KEY(bal_id),FOREIGN KEY(bot_id) REFERENCES Bot(bot_id));

CREATE TABLE IF NOT EXISTS performs(usr_id INT,act_id INT,PRIMARY KEY(usr_id, act_id),FOREIGN KEY(usr_id) REFERENCES Utilisateur(usr_id),FOREIGN KEY(act_id) REFERENCES Action(act_id));

CREATE TABLE IF NOT EXISTS is_affected_by(bot_id INT,act_id INT,PRIMARY KEY(bot_id, act_id),FOREIGN KEY(bot_id) REFERENCES Bot(bot_id),FOREIGN KEY(act_id) REFERENCES Action(act_id));

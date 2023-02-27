--
-- File generated with SQLiteStudio v3.4.3 on Fri Feb 24 12:55:19 2023
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: colors
CREATE TABLE IF NOT EXISTS colors (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, color TEXT NOT NULL, uid TEXT NOT NULL);
INSERT INTO colors (id, color) VALUES (1, '#123456');

-- Table: logins
CREATE TABLE IF NOT EXISTS logins (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, username TEXT NOT NULL, password TEXT NOT NULL);
INSERT INTO logins (id, username, password) VALUES (1, 'treim840', 'test1234');

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;

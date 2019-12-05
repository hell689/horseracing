use horseracing;
insert into horse (name) values 
('horse 1'),
('horse 2'),
('horse 3'),
('horse 4'),
('horse 5'),
('horse 6'),
('horse 7');

insert into race (name, date, fixresult) values 
('race 1', '2018-12-25 10:00:00', null),
('race 2', '2018-12-26 10:00:00', null),
('race 3', '2018-12-27 12:00:00', null);

insert into runner (race_id, horse_id, rate, place) values
(1, 1, 1.5, null),
(1, 2, 1.5, null),
(1, 3, 1.5, null),
(1, 4, 1.5, null),
(1, 5, 1.5, null),
(1, 6, 1.5, null);

insert into user (login, password, email,  name, surname, balance, role) values 
('client1', '123', 'client1@gmail.com', 'Alex', 'Koch', 10, 0),
('client2', '123', 'client2@gmail.com','Pit', 'Smit', 0, 0),
('Admin', '123', 'mmm@gmail.com', 'Jim', 'Davidson', null,  1),
('bookmaker1', '123', 'book1@gmail.com', 'Pol', 'Tot', null, 2),
('bookmaker2', '123', 'book2@gmail.com', 'Alex', 'Trump', null, 2);

insert into bet (user_id, runner_id, bettype, finalrate, amount, bettime, win) values
(1, 2, 0, 1.5, 30, '2018-12-23 09:00:00', null),
(2, 1, 0, 1.5, 10, '2018-12-23 19:00:00', null),
(1, 2, 0, 1.5, 15, '2018-12-24 09:00:00', null);

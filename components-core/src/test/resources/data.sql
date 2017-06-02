INSERT INTO uuid_persistent_resource(uuid, creation_date, label) VALUES
('d5666c5a-df28-49be-b511-cfd58d0867cf', '2017-01-01 12:00:00', 'label1'),
('d5666c5a-df28-49be-b512-cfd58d0867cf', '2017-01-01 12:00:00', 'label2'),
('d5666c5a-df28-49be-b513-cfd58d0867cf', '2017-01-01 12:00:00', 'label3'),
('d5666c5a-df28-49be-b514-cfd58d0867cf', '2017-01-02 12:00:00', 'label4');

INSERT INTO country(code, name, creation_date) VALUES
( 'FR', 'France', '2017-01-01 12:00:00'),
( 'BE', 'Belgium', '2017-01-01 12:00:00');

INSERT INTO event_log(id, creation_date, description) VALUES
('d5666c5a-df28-49be-b711-cfd58d0867cf', '2017-01-01 12:00:00', 'membership.loginmembership.login');

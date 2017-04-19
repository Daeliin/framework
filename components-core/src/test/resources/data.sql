INSERT INTO uuid_persistent_resource(uuid, creation_date, label) VALUES
('d5666c5a-df28-49be-b511-cfd58d0867cf', '2017-01-01 12:00:00', 'Label1'),
('d5666c5a-df28-49be-b512-cfd58d0867cf', '2017-01-01 12:00:00', 'Label2'),
('d5666c5a-df28-49be-b513-cfd58d0867cf', '2017-01-01 12:00:00', 'Label3'),
('d5666c5a-df28-49be-b514-cfd58d0867cf', '2017-01-02 12:00:00', 'Label4');

INSERT INTO country(code, name, creation_date) VALUES
( 'FR', 'France', '2017-01-01 12:00:00');

INSERT INTO event_log(uuid, creation_date, description_key) VALUES
('d5666c5a-df28-49be-b711-cfd58d0867cf', '2017-01-01 12:00:00', 'membership.loginmembership.login');

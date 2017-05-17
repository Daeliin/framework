INSERT INTO permission(id, name, creation_date) VALUES
('ADMIN', 'ADMIN', '2017-05-05 12:00:00'),
('USER', 'USER', '2017-05-05 12:00:00'),
('MODERATOR', 'MODERATOR', '2017-05-05 12:00:00');

INSERT INTO account(id, username, email, enabled, password, token, creation_date) VALUES
('ACCOUNT1', 'admin', 'admin@daeliin.com', true, '$2a$10$ggIHKT/gYkYk0Bt2yP4xvOybahPn7GfSwC0T3fYhCzrZ9ta9LAYt6', 'b5e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802af8e97fbed4999c0dd838577590d9e', '2017-01-01 12:00:00'),
('ACCOUNT2', 'john', 'john@daeliin.com', true, '$2a$10$dJ9dhL7FqCDXBb1kKj5y.ub4ohRm7VARPLlg5H.AHcd/T/XVEgpA.', 'c4e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802ad8997fbed4999c0dd838577590d9e', '2017-01-01 12:00:00'),
('ACCOUNT3', 'inactive', 'inactive@daeliin.com', false, '$2a$10$dJ9dhL7FqCDXBb1kKj5y.ub4ohRm7VARPLlg5H.AHcd/T/XVEgpA.', 'c4e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802ad8997fbed4999c0dd838577590d9e', '2017-01-01 12:00:00');

INSERT INTO account_permission(account_id, permission_id) VALUES
('ACCOUNT1', 'ADMIN'),
('ACCOUNT2', 'USER');

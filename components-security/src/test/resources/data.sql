INSERT INTO permission(id, uuid, label) VALUES
(1, RANDOM_UUID(), 'ADMIN'),    
(2, RANDOM_UUID(), 'USER'); 

INSERT INTO account(id, uuid, email, username, password, token, signup_date, enabled) VALUES 
(1, RANDOM_UUID(), 'admin@daeliin.com', 'admin', '$2a$10$ggIHKT/gYkYk0Bt2yP4xvOybahPn7GfSwC0T3fYhCzrZ9ta9LAYt6', 'b5e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802af8e97fbed4999c0dd838577590d9e', '2015-06-02 11:00:00', true),
(2, RANDOM_UUID(), 'john@daeliin.com', 'john', '$2a$10$dJ9dhL7FqCDXBb1kKj5y.ub4ohRm7VARPLlg5H.AHcd/T/XVEgpA.', 'c4e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802ad8997fbed4999c0dd838577590d9e', '2015-05-10 14:30:00', true),
(3, RANDOM_UUID(), 'inactive@daeliin.com', 'inactive', '$2a$10$dJ9dhL7FqCDXBb1kKj5y.ub4ohRm7VARPLlg5H.AHcd/T/XVEgpA.', 'c4e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802ad8997fbed4999c0dd838577590d9e', '2015-05-10 14:30:00', false);

INSERT INTO account_permission(id, uuid, account_id, permission_id) VALUES 
(1, RANDOM_UUID(), 1, 1),
(2, RANDOM_UUID(), 2, 2),
(3, RANDOM_UUID(), 3, 2);

INSERT INTO permission(id, label) VALUES
(1, 'ROLE_ADMIN'),    
(2, 'ROLE_USER'); 

INSERT INTO user(id, email, username, password, token, signedup_date, enabled) VALUES 
(1, 'admin@daeliin.com', 'Admin', '$2a$10$ggIHKT/gYkYk0Bt2yP4xvOybahPn7GfSwC0T3fYhCzrZ9ta9LAYt6', 'b5e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802af8e97fbed4999c0dd838577590d9e', '2015-06-02 11:00:00', true),
(2, 'john@daeliin.com', 'John', '$2a$10$dJ9dhL7FqCDXBb1kKj5y.ub4ohRm7VARPLlg5H.AHcd/T/XVEgpA.', 'c4e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802ad8997fbed4999c0dd838577590d9e', '2015-05-10 14:30:00', true),
(3, 'inactive@daeliin.com', 'Inactive', '$2a$10$dJ9dhL7FqCDXBb1kKj5y.ub4ohRm7VARPLlg5H.AHcd/T/XVEgpA.', 'c4e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802ad8997fbed4999c0dd838577590d9e', '2015-05-10 14:30:00', false);

INSERT INTO user_permission(id, user_id, permission_id) VALUES 
(1, 1, 1),
(2, 2, 2),
(3, 3, 2);
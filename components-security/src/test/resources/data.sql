INSERT INTO permission(id, uuid, label) VALUES
(1, '19694106-6971-4279-a66a-d65cda25df00', 'ADMIN'),    
(2, '29694106-6971-4279-a66a-d65cda25df01', 'USER'); 

INSERT INTO account(id, uuid, email, username, password, token, signup_date, enabled) VALUES 
(1, '39694106-6971-4279-a66a-d65cda25df02', 'admin@daeliin.com', 'admin', '$2a$10$ggIHKT/gYkYk0Bt2yP4xvOybahPn7GfSwC0T3fYhCzrZ9ta9LAYt6', 'b5e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802af8e97fbed4999c0dd838577590d9e', '2015-06-02 11:00:00', true),
(2, '49694106-6971-4279-a66a-d65cda25df03', 'john@daeliin.com', 'john', '$2a$10$dJ9dhL7FqCDXBb1kKj5y.ub4ohRm7VARPLlg5H.AHcd/T/XVEgpA.', 'c4e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802ad8997fbed4999c0dd838577590d9e', '2015-05-10 14:30:00', true),
(3, '59694106-6971-4279-a66a-d65cda25df04', 'inactive@daeliin.com', 'inactive', '$2a$10$dJ9dhL7FqCDXBb1kKj5y.ub4ohRm7VARPLlg5H.AHcd/T/XVEgpA.', 'c4e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802ad8997fbed4999c0dd838577590d9e', '2015-05-10 14:30:00', false);

INSERT INTO account_permission(id, uuid, account_id, permission_id) VALUES 
(1, '69694106-6971-4279-a66a-d65cda25df05', 1, 1),
(2, '79694106-6971-4279-a66a-d65cda25df06', 2, 2),
(3, '89694106-6971-4279-a66a-d65cda25df07', 3, 2);

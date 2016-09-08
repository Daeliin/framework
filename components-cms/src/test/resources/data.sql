INSERT INTO account(id, uuid, email, username, password, token, signup_date, enabled) VALUES 
(1, '39694106-6971-4279-a66a-d65cda25df02', 'admin@daeliin.com', 'admin', '$2a$10$ggIHKT/gYkYk0Bt2yP4xvOybahPn7GfSwC0T3fYhCzrZ9ta9LAYt6', 'b5e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802af8e97fbed4999c0dd838577590d9e', '2015-06-02 11:00:00', true),
(2, '49694106-6971-4279-a66a-d65cda25df03', 'john@daeliin.com', 'john', '$2a$10$dJ9dhL7FqCDXBb1kKj5y.ub4ohRm7VARPLlg5H.AHcd/T/XVEgpA.', 'c4e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802ad8997fbed4999c0dd838577590d9e', '2015-05-10 14:30:00', true),
(3, '59694106-6971-4279-a66a-d65cda25df04', 'inactive@daeliin.com', 'inactive', '$2a$10$dJ9dhL7FqCDXBb1kKj5y.ub4ohRm7VARPLlg5H.AHcd/T/XVEgpA.', 'c4e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802ad8997fbed4999c0dd838577590d9e', '2015-05-10 14:30:00', false);


INSERT INTO article(id, uuid, author_id, title, description, content, creation_date, publication_date, published) VALUES 
(1, '39694106-6971-4279-a66a-d65cda25df01', 2, 'Welcome to sample.com', 'Today is the day we start sample.com', 'We open our door today, you''ll find content very soon.', '2016-05-20 14:30:00', '2016-05-20 15:30:00', true),
(2, '39694106-6971-4279-a66a-d65cda25df02', 2, 'Sample.com is live', 'Today is the day we go live at sample.com', 'We go live today, here''s our first content.', '2016-05-20 14:30:00', null, false),
(3, '39694106-6971-4279-a66a-d65cda25df03', 2, 'Sample.com is live', 'Today is the day we go live at sample.com', 'We go live today, here''s our first content.', '2016-05-20 14:30:00', null, false),
(4, '39694106-6971-4279-a66a-d65cda25df04', 2, 'Sample.com is live', 'Today is the day we go live at sample.com', 'We go live today, here''s our first content.', '2016-05-20 14:30:00', '2016-05-20 15:30:00', true);
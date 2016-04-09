INSERT INTO permission(id, label) VALUES
(1, 'ADMIN'),    
(2, 'MODERATOR'), 
(3, 'USER'); 

INSERT INTO account(id, email, username, password, token, signup_date, enabled) VALUES 
(1, 'admin@daeliin.com', 'Admin', '$2a$10$ggIHKT/gYkYk0Bt2yP4xvOybahPn7GfSwC0T3fYhCzrZ9ta9LAYt6', 'b5e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802af8e97fbed4999c0dd838577590d9e', '2015-06-02 11:00:00', true),
(2, 'john@daeliin.com', 'John', '$2a$10$dJ9dhL7FqCDXBb1kKj5y.ub4ohRm7VARPLlg5H.AHcd/T/XVEgpA.', 'c4e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802ad8997fbed4999c0dd838577590d9e', '2015-05-10 14:30:00', true),
(3, 'moderator@daeliin.com', 'Moderator', '$2a$10$dJ9dhL7FqCDXBb1kKj5y.ub4ohRm7VARPLlg5H.AHcd/T/XVEgpA.', 'c4e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802ad8997fbed4999c0dd838577590d9e', '2015-05-10 14:30:00', false);

INSERT INTO account_permission(id, account_id, permission_id) VALUES 
(1, 1, 1),
(2, 2, 2),
(3, 3, 3);

INSERT INTO article(id, author_id, title, description, content, creation_date, publication_date, published) VALUES 
(1, 3, 'Welcome to sample.com', 'Today is the day we start sample.com', 'We open our door today, you''ll find content very soon.', '2016-05-20 14:30:00', '2016-05-20 15:30:00', true),
(2, 1, 'Sample.com is live', 'Today is the day we go live at sample.com', 'We go live today, here''s our first content.', '2016-05-20 14:30:00', null, false);
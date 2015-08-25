INSERT INTO permission(id, label) VALUES
(1, 'ROLE_ADMIN'),    
(2, 'ROLE_USER'); 

INSERT INTO user(id, username, password, enabled) VALUES (1, 'Admin', '$2a$10$ggIHKT/gYkYk0Bt2yP4xvOybahPn7GfSwC0T3fYhCzrZ9ta9LAYt6', true);
INSERT INTO user_permission(id, user_id, permission_id) VALUES (1, 1, 1);

INSERT INTO user(id, username, password, enabled) VALUES (2, 'John', '$2a$10$dJ9dhL7FqCDXBb1kKj5y.ub4ohRm7VARPLlg5H.AHcd/T/XVEgpA.', true);
INSERT INTO user_permission(id, user_id, permission_id) VALUES (2, 2, 2);
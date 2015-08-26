INSERT INTO user(id, username, password, enabled) VALUES (2, 'John', '$2a$10$dJ9dhL7FqCDXBb1kKj5y.ub4ohRm7VARPLlg5H.AHcd/T/XVEgpA.', true);

INSERT INTO user_permission(id, user_id, permission_id) VALUES (2, 2, 2);
INSERT INTO user(id, username, password, enabled) VALUES (1, 'Admin', '$2a$10$ggIHKT/gYkYk0Bt2yP4xvOybahPn7GfSwC0T3fYhCzrZ9ta9LAYt6', true);

INSERT INTO user_permission(id, user_id, permission_id) VALUES (1, 1, 1);
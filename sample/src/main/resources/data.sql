INSERT INTO user(id, username, password, enabled) VALUES 
(1, 'Tom', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(2, 'John', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(3, 'Fred', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(4, 'Chino', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(5, 'Corey', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(6, 'Mat', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(7, 'Chris', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(8, 'Sean', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(9, 'Wayne', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(10, 'Brad', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(11, 'Brian', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(12, 'Marc', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(13, 'Perry', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(14, 'Bob', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(15, 'Ted', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(16, 'Eliot', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(17, 'Keith', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(18, 'Kate', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(19, 'Kim', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(20, 'Cindy', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(21, 'Melany', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(25, 'Grace', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(23, 'Grace', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(24, 'Grace', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true),
(22, 'Grace', '$2a$04$8FI0HAdXpRPEwPid6tTovuHq4svtUJ/JFN88eb2QEWTFq.Ml5WEB6', true);

INSERT INTO permission(id, label) VALUES
(1, 'ROLE_ADMIN'),    
(2, 'ROLE_USER');    

INSERT INTO user_permission(id, user_id, permission_id) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 2, 2);
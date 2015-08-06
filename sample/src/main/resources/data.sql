INSERT INTO user(id, username, password, enabled) VALUES 
(1, 'Tom', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(2, 'John', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(3, 'Fred', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(4, 'Chino', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(5, 'Corey', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(6, 'Mat', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(7, 'Chris', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(8, 'Sean', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(9, 'Wayne', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(10, 'Brad', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(11, 'Brian', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(12, 'Marc', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(13, 'Perry', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(14, 'Bob', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(15, 'Ted', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(16, 'Eliot', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(17, 'Keith', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(18, 'Kate', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(19, 'Kim', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(20, 'Cindy', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(21, 'Melany', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(25, 'Grace', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(23, 'Grace', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(24, 'Grace', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true),
(22, 'Grace', '$2a$04$k0j.oy3WIJxrR7n51D16Se0PjFQTch61oZH.jQFmhAaZrXXEeTiSO', true);

INSERT INTO credential(id, label) VALUES
(1, 'ADMIN'),    
(2, 'USER');    

INSERT INTO user_credential(user_id, credential_id) VALUES
(1, 1),
(2, 2);
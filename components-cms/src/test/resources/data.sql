INSERT INTO account(id, username, email, enabled, password, token, creation_date) VALUES
('ACCOUNT1', 'admin', 'admin@daeliin.com', true, '$2a$10$ggIHKT/gYkYk0Bt2yP4xvOybahPn7GfSwC0T3fYhCzrZ9ta9LAYt6', 'b5e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802af8e97fbed4999c0dd838577590d9e', '2017-01-01 12:00:00');

INSERT INTO article(id, creation_date, author_id, title, url_friendly_title, description, content, publication_date, published) VALUES
('ARTICLE1', '2016-05-20 14:30:00', 'ACCOUNT1', 'Welcome to sample.com', 'Welcome-to-sample-com', 'Today is the day we start sample.com', 'We open our door today, you''ll find content very soon.', '2016-05-20 15:30:00', true),
('ARTICLE2', '2016-05-20 14:30:00', 'ACCOUNT1', 'Sample.com is live', 'sample-com-is-live', 'Today is the day we go live at sample.com', 'We go live today, here''s our first content.', null, false);

INSERT INTO news(id, creation_date, article_id, author_id, content, source) VALUES
('NEWS1', '2016-05-20 14:30:00', 'ARTICLE2', 'ACCOUNT1', 'Some news content 1', 'https://daeliin.com'),
('NEWS2', '2016-05-21 14:30:00', 'ARTICLE2', 'ACCOUNT1', 'Some news content 1', null);

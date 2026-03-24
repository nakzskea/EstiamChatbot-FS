
--mot de passe = Root --
INSERT INTO app_user ( email, password, pseudo, admin) VALUES
    ( 'a@a.com', '$2a$10$snvDOPIPLlteMkz/sIT4fuB7GEIpUyWf/tEuSMKoNHcKCvc9mNQ76', 'utilisateur A', true),
    ( 'b@b.com', '$2a$10$snvDOPIPLlteMkz/sIT4fuB7GEIpUyWf/tEuSMKoNHcKCvc9mNQ76', 'utilisateur B', false),
    ( 'c@c.com', '$2a$10$snvDOPIPLlteMkz/sIT4fuB7GEIpUyWf/tEuSMKoNHcKCvc9mNQ76', 'utilisateur C', false);

INSERT INTO recipe (name, description, creator_id) VALUES
    ('Pâtes carbonara', 'Des pâtes al dente mélangées à une sauce onctueuse à base d’œufs, de fromage pecorino, de pancetta et de poivre noir.', 2),
    ('Tarte aux pommes', 'Une pâte brisée croustillante garnie de fines tranches de pommes caramélisées et d’une touche de cannelle.', 2),
    ('Salade César', 'Une salade fraîche avec des croûtons grillés, du poulet, de la sauce César et du parmesan râpé.', 3);
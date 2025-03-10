INSERT INTO users (first_name, last_name, date_of_birth, city, email, password, role, profile_picture)
VALUES ('John', 'Doe', '1985-03-15', 'Paris', 'john.doe@example.com', '$2a$10$ZgBlK6t/ezZm5X8HDkD55OpWLHXScR8GUpcOL8HHMnR56uR2rAGaK', 'PROFESIONAL', 'profile.jpg');

-- Insertion d'une activité dans la table "activity"
INSERT INTO activity (name, description, duration, city, adress, zip_code, price, max_participants, category)
VALUES 
('Parachute Saut', 
 'Venez vivre l\'expérience du saut en parachute avec une vue imprenable sur la mer.', 
 30, 
 'Nice', 
 '123 Avenue de la Mer', 
 '06000', 
 250, 
 10, 
 'AERIEN');

-- Insertion d'images associées à l'activité
INSERT INTO activity_pictures (activity_id, picture)
VALUES 
(LAST_INSERT_ID(), 'image1.jpg'),
(LAST_INSERT_ID(), 'image2.jpg');

-- Insertion dans la table de liaison activity_pre_reservation (Supposons que l\'ID de pré-réservation est 1)
INSERT INTO activity_pre_reservation (activity_id, pre_reservation_id)
VALUES 
(LAST_INSERT_ID(), 1);

-- Insertion dans la table de liaison activity_final_reservation (Supposons que l\'ID de réservation finale est 2)
INSERT INTO activity_final_reservation (activity_id, final_reservation_id)
VALUES 
(LAST_INSERT_ID(), 2);

-- Insertion d'une revue pour l'activité (Supposons que l\'ID de revue est 1)
INSERT INTO review (title, body, created_at, activity_id, user_id)
VALUES 
('Super expérience', 'Le saut en parachute était incroyable!', NOW(), LAST_INSERT_ID(), 1);


-- Insertion d'une revue dans la table "review"
-- Supposons que l'ID de l'utilisateur est 1 et l'ID de l'activité est 1

INSERT INTO review (title, body, created_at, users_id, activity_id, rating)
VALUES 
('Excellente activité', 
 'L\'activité était incroyable et bien organisée, je recommande fortement!', 
 NOW(), 
 1, -- ID de l'utilisateur (remplacez par l'ID réel de l'utilisateur)
 1, -- ID de l'activité (remplacez par l'ID réel de l'activité)
 5); -- Note (remplacez par la note réelle)

-- Insertion d'une réservation finale dans la table "final_reservation"
-- Supposons que l'ID de l'utilisateur est 1 et l'ID de l'activité est 1

INSERT INTO final_reservation (reserved_at, total_price, payement_date, participants, date_of_activity, status)
VALUES 
(NOW(), 199.99, NOW(), 5, '2025-05-10', 'PAID');

-- Supposons que l'ID de la réservation finale est 1 et l'ID de l'utilisateur est 1

INSERT INTO users_final_reservation (users_id, final_reservation_id)
VALUES 
(1, 1); -- Remplacez par les IDs réels de l'utilisateur et de la réservation


-- Supposons que l'ID de la réservation finale est 1 et l'ID de l'activité est 1

INSERT INTO activity_final_reservation (activity_id, final_reservation_id)
VALUES 
(1, 1); -- Remplacez par les IDs réels de l'activité et de la réservation


-- Insertion d'une pré-réservation dans la table "pre_reservation"
-- Supposons que l'ID de l'utilisateur est 1 et l'ID de l'activité est 1

INSERT INTO pre_reservation (reserved_at, expiration_date, total_price, participants, date_of_activity, status)
VALUES 
(NOW(), '2025-05-15', 150.00, 3, '2025-05-10', 'PENDING');

-- Supposons que l'ID de la pré-réservation est 1 et l'ID de l'utilisateur est 1

INSERT INTO users_pre_reservation (users_id, pre_reservation_id)
VALUES 
(1, 1); -- Remplacez par les IDs réels de l'utilisateur et de la pré-réservation


-- Supposons que l'ID de la pré-réservation est 1 et l'ID de l'activité est 1

INSERT INTO activity_pre_reservation (activity_id, pre_reservation_id)
VALUES 
(1, 1); -- Remplacez par les IDs réels de l'activité et de la pré-réservation

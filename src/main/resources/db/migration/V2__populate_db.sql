INSERT INTO
    client (name)
VALUES
    ('Anna'),
    ('Bill'),
    ('Courtney'),
    ('Dilan'),
    ('Elon'),
    ('Fred'),
    ('George'),
    ('Ilona'),
    ('Jeff'),
    ('Kurt');

INSERT INTO
    planet (id, name)
VALUES
    ('01MER', 'Mercury'),
    ('02VEN', 'Venus'),
    ('03EAR', 'Earth'),
    ('04MAR', 'Mars'),
    ('35TAT', 'Tatooine'),
    ('14VUL', 'Vulcan14'),
    ('15VUL', 'Vulcan15');

INSERT INTO
    ticket (client_id, from_planet_id, to_planet_id)
VALUES
    (1, '03EAR', '04MAR'),
    (2, '03EAR', '01MER'),
    (3, '03EAR', '02VEN'),
    (4, '03EAR', '35TAT'),
    (5, '03EAR', '14VUL'),
    (6, '14VUL', '35TAT'),
    (7, '01MER', '03EAR'),
    (8, '02VEN', '14VUL'),
    (9, '04MAR', '02VEN'),
    (10, '35TAT', '04MAR');
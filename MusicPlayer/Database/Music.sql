begin transaction;

DROP TABLE if EXISTS composer, piece, genre, users, roles;

CREATE TABLE composer (
    composer_id serial,
    name varchar (200) NOT NULL,
    nationality varchar (200) NOT NULL,
    era varchar (200) NOT NULL,
    CONSTRAINT PK_composer PRIMARY KEY (composer_id)
);

CREATE TABLE genre (
    genre_id serial,
    name varchar (200) NOT NULL,
    CONSTRAINT PK_genre PRIMARY KEY (genre_id)
);

CREATE TABLE piece (
    piece_id serial,
    name varchar (200) NOT NULL,
    composer_id INT NOT NULL,
    genre_id INT NOT NULL,
    CONSTRAINT PK_piece PRIMARY KEY (piece_id),
    CONSTRAINT FK_composer FOREIGN KEY (composer_id) REFERENCES composer(composer_id),
    CONSTRAINT FK_genre FOREIGN KEY (genre_id) REFERENCES genre(genre_id)
);

create table users (
    username varchar(200) primary key,
    password varchar(200) not null,
    email varchar(200) not null
);

create table roles (
    username varchar(200) references users,
    role varchar(200) not null,
    primary key (username, role)
);

insert into users(username, password, email) values('admin', '', 'admin@test.com');
insert into roles(username, role) values('admin', 'ADMIN');

INSERT INTO composer (name, nationality, era) VALUES
('Beethoven', 'German', 'Romantic'),
('Mozart', 'Austrian', 'Classical'),
('Bach', 'German', 'Baroque'),
('Vivaldi', 'Italian', 'Baroque'),
('Debussy', 'French', 'Impressionism'),
('Ravel', 'French', 'Impressionism'),
('Prokofiev', 'Russian', '20th Century'),
('Shostakovich', 'Russian', '20th Century'),
('Haydn', 'Austrian', 'Classical'),
('Tchaikovsky', 'Russian', 'Romantic');

INSERT INTO genre (name) VALUES
('Symphony'),
('Opera'),
('Ballet'),
('String Quartet'),
('Concerto'),
('Sonata');

INSERT INTO piece (name, composer_id, genre_id) VALUES
('Ode to joy', 1, 1),
('Emperor Piano Concerto', 1, 5),
('Moonlight Sonata', 1, 6),
('Eine Kliene Nachtmusik', 2, 4),
('The Magic Flute', 2, 2),
('Jupiter Symphony', 2, 1),
('Art of Fugue', 3, 6),
('6 Sonatas and Partitas for Solo Violin', 3, 6),
('Cello Suites', 3, 6),
('Summer', 4, 5),
('Spring', 4, 5),
('Fall', 4, 5),
('Winter', 4, 5),
('Girl with the flaxen hair', 5, 6),
('Clare de Lune', 5, 6),
('Bolero', 6, 1),
('Pictures at an Exhibition', 6, 1),
('String quartet no 1', 6, 4),
('Romeo and Juliet', 7, 3),
('String Quartet no 8', 8, 4),
('Surprise Symphony', 9, 1),
('Farewell Symphony', 9, 1),
('London Symphony', 9, 1),
('The Nutcracker', 10, 3),
('Eugene Onegin', 10, 2),
('Swan Lake', 10, 3);

commit;
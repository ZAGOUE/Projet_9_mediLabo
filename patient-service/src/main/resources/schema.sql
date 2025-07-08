CREATE TABLE IF NOT EXISTS patient (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       prenom VARCHAR(50),
    nom VARCHAR(50),
    date_de_naissance DATE,
    genre VARCHAR(10),
    adresse VARCHAR(255),
    telephone VARCHAR(20)
    );

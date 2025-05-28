SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";
CREATE DATABASE IF NOT EXISTS `ProjecteProg` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `ProjecteProg`;

# usuaris registrats (id_nom, nom, cognoms, imatge, població, correu electrònic, salt, contrasenya)
CREATE TABLE `usuaris` (
  `usuari` VARCHAR(50) PRIMARY KEY,
  `nom` VARCHAR(50) NOT NULL,
  `cognoms` VARCHAR(150) NOT NULL,
  `imatge` VARCHAR(150) NOT NULL,
  `poblacio` VARCHAR(50) NOT NULL,
  `email` VARCHAR(50) NOT NULL UNIQUE,
  `salt` VARCHAR(4) NOT NULL,
  `contrasenya` VARCHAR(100) NOT NULL,
  `dataInsercio` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

#Pixel art(id_nom, mida, dibuix, data de guardat)
CREATE TABLE `pixelart` (
  `id_llenc` INTEGER AUTO_INCREMENT,
  `usuari` VARCHAR(50) NOT NULL,
  `mida` INTEGER NOT NULL,
  `imatge` VARCHAR(150) NOT NULL,
  `dataInsercio` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY(`id_llenc`),
  FOREIGN KEY (`usuari`) REFERENCES `usuaris`(`usuari`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

#Partida_pescamines(id_nom, matriu, temps)
CREATE TABLE `partida_pescamines` (
  `id_partida` INTEGER AUTO_INCREMENT,
  `usuari` VARCHAR(50) NOT NULL,
  `partida` LONGTEXT NOT NULL,
  `temps` TIME,
  `dataInsercio` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY(`id_partida`),
  FOREIGN KEY (`usuari`) REFERENCES `usuaris`(`usuari`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

#Ranking_Pecamines( id_nom, el nivell, el temps)
CREATE TABLE `ranking_pescamines` (
  `id_ranking` INTEGER AUTO_INCREMENT,
  `usuari` VARCHAR(50) NOT NULL,
  `dificultat` ENUM('facil', 'normal', 'difícil') NOT NULL,
  `temps` TIME NOT NULL,
  `dataInsercio` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY(`id_ranking`),
  FOREIGN KEY (`usuari`) REFERENCES `usuaris`(`usuari`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

#woodle(if_nom, informacio de la partida: guanyada o perduda, acerts, errors, datade partida)
CREATE TABLE `wordle` (
  `id_partida` INTEGER AUTO_INCREMENT,
  `usuari` VARCHAR(50) NOT NULL,
  `guanyada` BOOLEAN NOT NULL,
  `acerts` INTEGER NOT NULL DEFAULT 0,
  `errors` INTEGER NOT NULL DEFAULT 0,
  `intents` INTEGER NOT NULL DEFAULT 0,
  `paraula_secreta` VARCHAR(20),
  `dataPartida` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  
  PRIMARY KEY(`id_partida`),
  FOREIGN KEY (`usuari`) REFERENCES `usuaris`(`usuari`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
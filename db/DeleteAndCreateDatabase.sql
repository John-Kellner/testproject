-- Löscht die bestehende Datenbank und erstellt eine neue
DROP DATABASE IF EXISTS laiquendi;
CREATE DATABASE IF NOT EXISTS laiquendi COLLATE 'latin1_swedish_ci';

DROP PROCEDURE IF EXISTS deleteUser;

-- Anzeige der Stored Procedures
-- SHOW PROCEDURE STATUS;

-- Losch Funktion auf der Datenbank mit
-- CALL deleteUser(63, "info@otherpics.de");

-- Lösche die Adresse des angegebenen Users
CREATE PROCEDURE deleteUser(pk_userID BIGINT (100), email VARCHAR(100))
BEGIN
  DECLARE message VARCHAR(100);
  SET @userID = (SELECT u.PK_user FROM User u WHERE u.email = email AND u.PK_user= pk_userID);

  if @userID != '' THEN

    -- Loesche die Adresse
    DELETE a FROM Adresse AS a
      INNER JOIN User u ON a.FK_user = u.PK_user
    WHERE a.FK_user = @userID;

    -- Loeschen der Tabitems
    DELETE t FROM TabItems AS t
      INNER JOIN Einstellung e ON t.FK_einstellung = e.einstellungId
      INNER JOIN User u ON e.einstellungId = u.FK_einstellung
    WHERE u.PK_user = @userID;

    -- Loeschen des Templates
    DELETE t FROM Template AS t
      INNER JOIN Einstellung e ON t.FK_einstellung = e.einstellungId
      INNER JOIN User u ON e.einstellungId = u.FK_einstellung
    WHERE u.PK_user = @userID;

    -- Loeschen der Images
    DELETE i FROM Images AS i
      INNER JOIN Einstellung e ON i.FK_einstellung = e.einstellungId
      INNER JOIN User u ON e.einstellungId = u.FK_einstellung
    WHERE u.PK_user = @userID;

    -- Such die Einstellung ID
    SET @einstellungID = (SELECT e.einstellungId FROM Einstellung AS e
      INNER JOIN User u ON e.einstellungId = u.FK_einstellung
    WHERE e.user = @userID);

    -- Setzt die Bidirektionale Beziehung zwischen Einstellung - User auf NULL
    UPDATE Einstellung e SET e.user = NULL WHERE e.einstellungId = @einstellungID;

    -- Setzt die Bidirektionale Beziehung von User - Einstellung auf NULL
    UPDATE User u SET u.FK_einstellung = NULL WHERE PK_user = @userID;

    -- Loeschen der Einstellungen
    DELETE e FROM Einstellung e WHERE e.einstellungId = @einstellungID;

    -- Loesche Beziehungstabelle Studio / User
    DELETE uf FROM User_Fotostudio uf
      INNER JOIN User AS u ON uf.user = u.PK_user
    WHERE u.PK_user = @userID;

    -- Loesche den Kunden
    DELETE u FROM User u
    WHERE u.PK_user = @userID;

  ELSE
    SELECT @userID;
    set message = 'No User found!';
    SELECT message;
  END IF;
END

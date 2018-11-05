SELECT PK_Fotostudio, email, name, password,`telephone_number`, fax, oeffnungszeiten, page, a.strasse, a.plz, a.ort, a.land FROM Fotostudio f
INNER JOIN Adresse a ON f.PK_fotostudio = a.FK_Adresse_von_Studio;

Insert into Fotostudio (email,name,password,`telephone_number`,fax, oeffnungszeiten, page) VALUES ('info@photograph4u.de','Ulli Schlieper','1234','07307 - 92 91 30','07307 - 92 91 31','Montag/Samstag von 09:00 bis 13:00 Uhr <br> Dienstag bis Freitag 09:00 bis 18:00 Uhr <br> und nach Terminvereinbarung unter 07307- 929130','http://www.atelierschlieper.de/');
Insert into Adresse (land,ort,plz,strasse,FK_Adresse_von_Studio) VALUES ('Deutschland','Senden','89250','Hauptstraße 40, ',6);

SELECT * from fotostudio;
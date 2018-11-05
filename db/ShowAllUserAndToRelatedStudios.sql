-- Listet alle User und Fotostudios auf
SELECT u.PK_user AS UID, u.name AS User,u.email AS Email, u.password AS Password, s.PK_fotostudio AS StudioID, s.name AS Studio FROM User u
  INNER JOIN User_Fotostudio f ON u.PK_user = f.user
  INNER JOIN Fotostudio s ON f.fotostudio = s.PK_fotostudio;

-- Listet alle User zu einem bestimmten Fotostudio auf
SELECT u.PK_user AS UID, u.name AS User,u.email AS Email, u.password AS Password, s.PK_fotostudio AS StudioID, s.name AS Studio FROM User u
  INNER JOIN User_Fotostudio f ON u.PK_user = f.user
  INNER JOIN Fotostudio s ON f.fotostudio = s.PK_fotostudio
  WHERE s.PK_fotostudio = 2;
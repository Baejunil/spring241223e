create database todo
default character set utf8
collate utf8_general_ci;
SHOW DATABASES;

use todo;
select*from todo;
ALTER TABLE todo MODIFY COLUMN isdone BOOLEAN DEFAULT FALSE;

create database cardatabase
default character set utf8
collate utf8_general_ci;
use cardatabase;
select*from car;

create database football
default character set utf8
collate utf8_general_ci; 
select*from football_match;
INSERT INTO football_match (home_team, away_team, match_date) 
VALUES ('Team A', 'Team B', '2025-01-01 15:00:00');
SELECT * FROM football_match;
select*from football_match_seq;
select*from posts;
USE football;
ALTER TABLE football_match
MODIFY COLUMN id BIGINT AUTO_INCREMENT;


ALTER TABLE football_match DROP COLUMN teamA;
ALTER TABLE football_match DROP COLUMN teamB;

SELECT home_team, away_team, match_date, COUNT(*)
FROM football_match
GROUP BY home_team, away_team, match_date
HAVING COUNT(*) > 1;

DELETE fm1
FROM football_match fm1
JOIN football_match fm2
ON fm1.home_team = fm2.home_team
AND fm1.away_team = fm2.away_team
AND fm1.match_date = fm2.match_date
AND fm1.id < fm2.id
WHERE fm1.id IS NOT NULL;football_match


DELETE FROM football_match
WHERE id NOT IN (
    SELECT MIN(id)
    FROM football_match
    GROUP BY home_team, away_team, match_date
);
SET SQL_SAFE_UPDATES = 0;

USE football;  -- 기존 데이터베이스 선택

CREATE TABLE posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
SHOW COLUMNS FROM football_match;

ALTER TABLE football_match ADD COLUMN status VARCHAR(255);
ALTER TABLE football_match ADD COLUMN home_score INT;
ALTER TABLE football_match ADD COLUMN away_score INT;
ALTER TABLE football_match ADD COLUMN matchday VARCHAR(255);
SELECT * FROM football_match ORDER BY match_date ASC;

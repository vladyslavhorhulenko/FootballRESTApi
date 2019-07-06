
CREATE TABLE IF NOT EXISTS Player(
    id_player SERIAL PRIMARY KEY NOT NULL ,
    name_player VARCHAR(30),
    surname VARCHAR(30),
    position VARCHAR(30),
    birthday date 

);

CREATE TABLE IF NOT EXISTS Team(
    id_team SERIAL PRIMARY KEY NOT NULL ,
    name_team VARCHAR(30),
    captain BIGINT,
    CONSTRAINT fk_captain_id FOREIGN KEY (captain) REFERENCES Player(id_player)
);

ALTER TABLE Player ADD COLUMN IF NOT EXISTS team BIGINT;
ALTER TABLE Player ADD CONSTRAINT fk_team_id FOREIGN KEY (team) REFERENCES Team(id_team);
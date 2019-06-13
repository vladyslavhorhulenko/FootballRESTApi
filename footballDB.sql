CREATE DATABASE "FootballDB";

CREATE TABLE public.player
(
    id_player bigint NOT NULL DEFAULT nextval('player_id_player_seq'::regclass),
    birthday timestamp without time zone,
    name_player character varying(255) COLLATE pg_catalog."default",
    "position" character varying(255) COLLATE pg_catalog."default",
    surn_player character varying(255) COLLATE pg_catalog."default",
    id_team bigint,
    CONSTRAINT player_pkey PRIMARY KEY (id_player),
    CONSTRAINT fk9fsf2q8depkva936mj3e4wya9 FOREIGN KEY (id_team)
        REFERENCES public.team (id_team) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.player
    OWNER to postgres;

CREATE TABLE public.team
(
    id_team bigint NOT NULL DEFAULT nextval('team_id_team_seq'::regclass),
    name_team character varying(255) COLLATE pg_catalog."default",
    id_captain bigint,
    CONSTRAINT team_pkey PRIMARY KEY (id_team),
    CONSTRAINT fkno1g2f6kgc3xv1bsfqylpo7ec FOREIGN KEY (id_captain)
        REFERENCES public.player (id_player) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.team
    OWNER to postgres;
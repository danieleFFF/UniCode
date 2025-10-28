-- public.avatar definition

-- Drop table

-- DROP TABLE public.avatar;

CREATE TABLE public.avatar (
                               id serial4 NOT NULL,
                               url_immagine varchar(255) NOT NULL,
                               CONSTRAINT avatar_pkey PRIMARY KEY (id),
                               CONSTRAINT avatar_url_immagine_key UNIQUE (url_immagine)
);


-- public.linguaggi definition

-- Drop table

-- DROP TABLE public.linguaggi;

CREATE TABLE public.linguaggi (
                                  id serial4 NOT NULL,
                                  nome varchar(255) NOT NULL,
                                  tipo_esercizi varchar(255) NOT NULL,
                                  descrizione_corso text NULL,
                                  url_video_teoria varchar(255) NULL,
                                  CONSTRAINT check_tipo_esercizi CHECK (((tipo_esercizi)::text = ANY ((ARRAY['compilabile'::character varying, 'demo'::character varying])::text[]))),
	CONSTRAINT linguaggi_nome_key UNIQUE (nome),
	CONSTRAINT linguaggi_pkey PRIMARY KEY (id)
);


-- public.trofei definition

-- Drop table

-- DROP TABLE public.trofei;

CREATE TABLE public.trofei (
                               id serial4 NOT NULL,
                               nome varchar(255) NOT NULL,
                               descrizione text NULL,
                               punti_trofeo int4 NOT NULL,
                               codice_trofeo varchar(255) NOT NULL,
                               CONSTRAINT trofei_codice_trofeo_key UNIQUE (codice_trofeo),
                               CONSTRAINT trofei_pkey PRIMARY KEY (id)
);


-- public.esercizi definition

-- Drop table

-- DROP TABLE public.esercizi;

CREATE TABLE public.esercizi (
                                 id serial4 NOT NULL,
                                 id_linguaggio int4 NOT NULL,
                                 titolo varchar(255) NOT NULL,
                                 descrizione text NOT NULL,
                                 difficolta varchar(20) NOT NULL,
                                 soluzione_demo text NULL,
                                 CONSTRAINT check_difficolta CHECK (((difficolta)::text = ANY ((ARRAY['base'::character varying, 'intermedio'::character varying, 'avanzato'::character varying])::text[]))),
	CONSTRAINT esercizi_pkey PRIMARY KEY (id),
	CONSTRAINT esercizi_id_linguaggio_fkey FOREIGN KEY (id_linguaggio) REFERENCES public.linguaggi(id)
);


-- public.test_case definition

-- Drop table

-- DROP TABLE public.test_case;

CREATE TABLE public.test_case (
                                  id serial4 NOT NULL,
                                  id_esercizio int4 NOT NULL,
                                  "input" text NULL,
                                  output_atteso text NOT NULL,
                                  CONSTRAINT test_case_pkey PRIMARY KEY (id),
                                  CONSTRAINT test_case_id_esercizio_fkey FOREIGN KEY (id_esercizio) REFERENCES public.esercizi(id) ON DELETE CASCADE
);


-- public.utenti definition

-- Drop table

-- DROP TABLE public.utenti;

CREATE TABLE public.utenti (
                               id serial4 NOT NULL,
                               username varchar(255) NOT NULL,
                               email varchar(255) NOT NULL,
                               password_hash varchar(255) NOT NULL,
                               punti_totali int4 DEFAULT 0 NOT NULL,
                               id_avatar int4 NULL,
                               CONSTRAINT utenti_email_key UNIQUE (email),
                               CONSTRAINT utenti_pkey PRIMARY KEY (id),
                               CONSTRAINT utenti_username_key UNIQUE (username),
                               CONSTRAINT utenti_id_avatar_fkey FOREIGN KEY (id_avatar) REFERENCES public.avatar(id)
);


-- public.esercizi_svolti definition

-- Drop table

-- DROP TABLE public.esercizi_svolti;

CREATE TABLE public.esercizi_svolti (
                                        id_utente int4 NOT NULL,
                                        id_esercizio int4 NOT NULL,
                                        tempo_impiegato_sec int4 NOT NULL,
                                        punti_ottenuti int4 NOT NULL,
                                        is_first_try bool DEFAULT false NOT NULL,
                                        CONSTRAINT esercizi_svolti_pkey PRIMARY KEY (id_utente, id_esercizio),
                                        CONSTRAINT esercizi_svolti_id_esercizio_fkey FOREIGN KEY (id_esercizio) REFERENCES public.esercizi(id),
                                        CONSTRAINT esercizi_svolti_id_utente_fkey FOREIGN KEY (id_utente) REFERENCES public.utenti(id)
);


-- public.trofei_utenti definition

-- Drop table

-- DROP TABLE public.trofei_utenti;

CREATE TABLE public.trofei_utenti (
                                      id_utente int4 NOT NULL,
                                      id_trofeo int4 NOT NULL,
                                      CONSTRAINT trofei_utenti_pkey PRIMARY KEY (id_utente, id_trofeo),
                                      CONSTRAINT trofei_utenti_id_trofeo_fkey FOREIGN KEY (id_trofeo) REFERENCES public.trofei(id),
                                      CONSTRAINT trofei_utenti_id_utente_fkey FOREIGN KEY (id_utente) REFERENCES public.utenti(id)
);
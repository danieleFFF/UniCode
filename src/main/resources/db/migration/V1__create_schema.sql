CREATE TABLE public.avatar (
                               id          SERIAL       PRIMARY KEY,
                               url_image   VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE public.languages (
                                  id               SERIAL       PRIMARY KEY,
                                  name             VARCHAR(255) NOT NULL UNIQUE,
                                  type_exercise    VARCHAR(255) NOT NULL,
                                  description_course  TEXT,
                                  url_theory_video VARCHAR(255),
                                  CONSTRAINT check_type_exercise
                                      CHECK (type_exercise IN ('compilable', 'demo'))
);

CREATE TABLE public.trophies (
                                 id            SERIAL       PRIMARY KEY,
                                 name          VARCHAR(255) NOT NULL,
                                 description   TEXT,
                                 points_trophy INTEGER      NOT NULL,
                                 cod_trophy    VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE public.exercises (
                                  id             SERIAL       PRIMARY KEY,
                                  id_language    INTEGER      NOT NULL,
                                  title          VARCHAR(255) NOT NULL,
                                  description    TEXT         NOT NULL,
                                  difficulty     VARCHAR(20)  NOT NULL,
                                  solution_demo  TEXT,
                                  CONSTRAINT check_difficulty
                                      CHECK (difficulty IN ('Easy', 'Medium', 'Hard')),
                                  CONSTRAINT exercises_id_language_fkey
                                      FOREIGN KEY (id_language)
                                          REFERENCES public.languages(id)
                                          ON DELETE CASCADE
);

CREATE TABLE public.test_cases (
                                   id              SERIAL       PRIMARY KEY,
                                   id_exercise     INTEGER      NOT NULL,
                                   input           TEXT,
                                   expected_output TEXT         NOT NULL,
                                   CONSTRAINT test_cases_id_exercise_fkey
                                       FOREIGN KEY (id_exercise)
                                           REFERENCES public.exercises(id)
                                           ON DELETE CASCADE
);

CREATE TABLE public.users (
                              id            SERIAL       PRIMARY KEY,
                              username      VARCHAR(255) NOT NULL UNIQUE,
                              email         VARCHAR(255) NOT NULL UNIQUE,
                              password_hash VARCHAR(255) NOT NULL,
                              total_points  INTEGER      NOT NULL DEFAULT 0,
                              id_avatar     INTEGER,
                              CONSTRAINT users_id_avatar_fkey
                                  FOREIGN KEY (id_avatar)
                                      REFERENCES public.avatar(id)
);

CREATE TABLE public.exercises_done (
                                       id_user          INTEGER NOT NULL,
                                       id_exercise      INTEGER NOT NULL,
                                       time_sec         INTEGER NOT NULL,
                                       points_obtained  INTEGER NOT NULL,
                                       is_first_try     BOOLEAN NOT NULL DEFAULT FALSE,
                                       CONSTRAINT exercises_done_pkey
                                           PRIMARY KEY (id_user, id_exercise),
                                       CONSTRAINT exercises_done_id_user_fkey
                                           FOREIGN KEY (id_user)
                                               REFERENCES public.users(id)
                                               ON DELETE CASCADE,
                                       CONSTRAINT exercises_done_id_exercise_fkey
                                           FOREIGN KEY (id_exercise)
                                               REFERENCES public.exercises(id)
                                               ON DELETE CASCADE
);

CREATE TABLE public.users_trophies (
                                       id_user    INTEGER NOT NULL,
                                       id_trophy  INTEGER NOT NULL,
                                       CONSTRAINT users_trophies_pkey
                                           PRIMARY KEY (id_user, id_trophy),
                                       CONSTRAINT users_trophies_id_user_fkey
                                           FOREIGN KEY (id_user)
                                               REFERENCES public.users(id)
                                               ON DELETE CASCADE,
                                       CONSTRAINT users_trophies_id_trophy_fkey
                                           FOREIGN KEY (id_trophy)
                                               REFERENCES public.trophies(id)
                                               ON DELETE CASCADE
);

CREATE INDEX idx_exercises_language ON public.exercises(id_language);
CREATE INDEX idx_test_cases_exercise ON public.test_cases(id_exercise);
CREATE INDEX idx_ex_done_user ON public.exercises_done(id_user);
CREATE INDEX idx_ex_done_exercise ON public.exercises_done(id_exercise);

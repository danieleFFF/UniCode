CREATE TABLE public.courses_topics (
                                       id              SERIAL       PRIMARY KEY,
                                       id_language     INTEGER      NOT NULL,
                                       title           VARCHAR(255) NOT NULL,
                                       introduction    TEXT,
                                       code_example    TEXT,
                                       key_concepts    TEXT,
                                       url_video       VARCHAR(255),
                                       difficulty      VARCHAR(20)  DEFAULT 'Medium',
                                       CONSTRAINT courses_topics_id_language_fkey
                                           FOREIGN KEY (id_language)
                                               REFERENCES public.languages(id)
                                               ON DELETE CASCADE,

                                       CONSTRAINT check_topic_difficulty
                                           CHECK (difficulty IN ('Easy', 'Medium', 'Hard'))
);

CREATE INDEX idx_courses_topics_language ON public.courses_topics(id_language);
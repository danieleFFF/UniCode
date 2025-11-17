CREATE TABLE IF NOT EXISTS submissions (
                                           id SERIAL PRIMARY KEY,
                                           id_user INTEGER NOT NULL,
                                           id_exercise INTEGER NOT NULL,
                                           points_earned INTEGER NOT NULL,
                                           time_taken_seconds INTEGER NOT NULL,
                                           completed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                           code TEXT,
                                           FOREIGN KEY (id_exercise) REFERENCES exercises(id),
    UNIQUE(id_user, id_exercise)
    );
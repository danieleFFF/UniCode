UPDATE exercises SET points = 0 WHERE id_language = 6;
DELETE FROM test_cases WHERE id_exercise IN (SELECT id FROM exercises WHERE id_language = 6);

DELETE FROM test_cases WHERE id_exercise = (SELECT id FROM exercises WHERE title = 'Deep Object Clone' AND id_language = 5);
INSERT INTO test_cases (id_exercise, input, expected_output) VALUES
((SELECT id FROM exercises WHERE title = 'Deep Object Clone' AND id_language = 5), '{"a":1,"b":{"c":2}}', '{"a":1,"b":{"c":2}}'),
((SELECT id FROM exercises WHERE title = 'Deep Object Clone' AND id_language = 5), '{"x":{"y":{"z":3}}}', '{"x":{"y":{"z":3}}}');

DELETE FROM test_cases WHERE id_exercise = (SELECT id FROM exercises WHERE title = 'Longest Common Subsequence' AND id_language = 3);
INSERT INTO test_cases (id_exercise, input, expected_output) VALUES
((SELECT id FROM exercises WHERE title = 'Longest Common Subsequence' AND id_language = 3), 'ABCDGH
AEDFHR', '3'),
((SELECT id FROM exercises WHERE title = 'Longest Common Subsequence' AND id_language = 3), 'ABC
AC', '2');

DELETE FROM test_cases WHERE id_exercise = (SELECT id FROM exercises WHERE title = 'Binary Tree Level Order' AND id_language = 2);
INSERT INTO test_cases (id_exercise, input, expected_output) VALUES
((SELECT id FROM exercises WHERE title = 'Binary Tree Level Order' AND id_language = 2), '[3,9,20,null,null,15,7]', '[[3],[9,20],[15,7]]');

DELETE FROM test_cases WHERE id_exercise = (SELECT id FROM exercises WHERE title = 'Merge Sort' AND id_language = 1);
INSERT INTO test_cases (id_exercise, input, expected_output) VALUES
((SELECT id FROM exercises WHERE title = 'Merge Sort' AND id_language = 1), '[5,2,8,1,9]', '[1,2,5,8,9]'),
((SELECT id FROM exercises WHERE title = 'Merge Sort' AND id_language = 1), '[3,1,4,1,5]', '[1,1,3,4,5]');

DELETE FROM test_cases WHERE id_exercise = (SELECT id FROM exercises WHERE title = 'Promise All Implementation' AND id_language = 5);
INSERT INTO test_cases (id_exercise, input, expected_output) VALUES
((SELECT id FROM exercises WHERE title = 'Promise All Implementation' AND id_language = 5), '[1,2,3]', '[1,2,3]');

DELETE FROM test_cases WHERE id_exercise = (SELECT id FROM exercises WHERE title = 'Curry Function' AND id_language = 5);
INSERT INTO test_cases (id_exercise, input, expected_output) VALUES
((SELECT id FROM exercises WHERE title = 'Curry Function' AND id_language = 5), '1 2 3', '6');

DELETE FROM test_cases WHERE id_exercise = (SELECT id FROM exercises WHERE title = 'Object Deep Merge' AND id_language = 5);
INSERT INTO test_cases (id_exercise, input, expected_output) VALUES
((SELECT id FROM exercises WHERE title = 'Object Deep Merge' AND id_language = 5), '{"a":1}
{"b":2}', '{"a":1,"b":2}');

INSERT INTO test_cases (id_exercise, input, expected_output) VALUES
((SELECT id FROM exercises WHERE title = 'List Flatten' AND id_language = 1), '[[1, 2], [3, [4, 5]]]', '[1, 2, 3, 4, 5]'),
((SELECT id FROM exercises WHERE title = 'Merge Sort' AND id_language = 1), '[5, 2, 8, 1, 9]', '[1, 2, 5, 8, 9]'),
((SELECT id FROM exercises WHERE title = 'LRU Cache' AND id_language = 1), 'capacity=2, put(1,1), put(2,2), get(1)', '1');

INSERT INTO test_cases (id_exercise, input, expected_output) VALUES
((SELECT id FROM exercises WHERE title = 'LinkedList Cycle Detection' AND id_language = 2), '1->2->3->4->2', 'true'),
((SELECT id FROM exercises WHERE title = 'Binary Tree Level Order' AND id_language = 2), '[3,9,20,null,null,15,7]', '[[3],[9,20],[15,7]]');

INSERT INTO test_cases (id_exercise, input, expected_output) VALUES
((SELECT id FROM exercises WHERE title = 'Valid Parentheses' AND id_language = 3), '()[]{}', 'true'),
((SELECT id FROM exercises WHERE title = 'Valid Parentheses' AND id_language = 3), '([)]', 'false'),
((SELECT id FROM exercises WHERE title = 'Longest Common Subsequence' AND id_language = 3), 'ABCDGH AEDFHR', '3'),
((SELECT id FROM exercises WHERE title = 'Longest Common Subsequence' AND id_language = 3), 'ABC AC', '2');

INSERT INTO test_cases (id_exercise, input, expected_output) VALUES
((SELECT id FROM exercises WHERE title = 'Object Deep Merge' AND id_language = 5), '{a:1}, {b:2}', '{a:1,b:2}'),
((SELECT id FROM exercises WHERE title = 'Promise All Implementation' AND id_language = 5), '[Promise.resolve(1), Promise.resolve(2)]', '[1,2]'),
((SELECT id FROM exercises WHERE title = 'Curry Function' AND id_language = 5), 'add(1)(2)(3)', '6'),
((SELECT id FROM exercises WHERE title = 'Find Maximum Value' AND id_language = 5), '[3, 1, 4, 1, 5, 9]', '9'),
((SELECT id FROM exercises WHERE title = 'Find Maximum Value' AND id_language = 5), '[-5, -2, -8]', '-2'),
((SELECT id FROM exercises WHERE title = 'Palindrome Checker' AND id_language = 5), 'racecar', 'true'),
((SELECT id FROM exercises WHERE title = 'Palindrome Checker' AND id_language = 5), 'hello', 'false'),
((SELECT id FROM exercises WHERE title = 'Deep Object Clone' AND id_language = 5), '{a: {b: 1}}', '{a: {b: 1}}');

INSERT INTO test_cases (id_exercise, input, expected_output) VALUES
((SELECT id FROM exercises WHERE title = 'Join Tables' AND id_language = 6), '', 'Customer orders joined'),
((SELECT id FROM exercises WHERE title = 'Subquery Filter' AND id_language = 6), '', 'Employees above average'),
((SELECT id FROM exercises WHERE title = 'Window Functions' AND id_language = 6), '', 'Ranked employees'),
((SELECT id FROM exercises WHERE title = 'Recursive CTE' AND id_language = 6), '', 'Subordinates hierarchy'),
((SELECT id FROM exercises WHERE title = 'Find Most Popular Product' AND id_language = 6), '', 'Most ordered product');

INSERT INTO test_cases (id_exercise, input, expected_output)
SELECT id, 'hello', 'olleh' FROM exercises WHERE title = 'Reverse String' AND id_language = 1 AND NOT EXISTS (SELECT 1 FROM test_cases WHERE id_exercise = exercises.id);

INSERT INTO test_cases (id_exercise, input, expected_output) 
SELECT id, 'Python', 'nohtyP' FROM exercises WHERE title = 'Reverse String' AND id_language = 1 AND EXISTS (SELECT 1 FROM test_cases tc WHERE tc.id_exercise = exercises.id HAVING COUNT(*) < 2);

INSERT INTO test_cases (id_exercise, input, expected_output)
SELECT id, '10', '55' FROM exercises WHERE title = 'Fibonacci' AND id_language = 1 AND NOT EXISTS (SELECT 1 FROM test_cases WHERE id_exercise = exercises.id);

INSERT INTO test_cases (id_exercise, input, expected_output)
SELECT id, '5', '120' FROM exercises WHERE title = 'Factorial' AND id_language = 2 AND NOT EXISTS (SELECT 1 FROM test_cases WHERE id_exercise = exercises.id);

INSERT INTO test_cases (id_exercise, input, expected_output)
SELECT id, '5 3 8 1 2', '1 2 3 5 8' FROM exercises WHERE title = 'Bubble Sort' AND id_language = 3 AND NOT EXISTS (SELECT 1 FROM test_cases WHERE id_exercise = exercises.id);

INSERT INTO test_cases (id_exercise, input, expected_output)
SELECT id, '7', '13' FROM exercises WHERE title = 'Fibonacci' AND id_language = 1 AND NOT EXISTS (SELECT 1 FROM test_cases tc WHERE tc.id_exercise = exercises.id AND tc.input = '7');

INSERT INTO test_cases (id_exercise, input, expected_output)
SELECT id, '10 20', '30' FROM exercises WHERE title = 'Sum of Two Numbers' AND id_language = 1 AND NOT EXISTS (SELECT 1 FROM test_cases tc WHERE tc.id_exercise = exercises.id AND tc.input = '10 20');

INSERT INTO test_cases (id_exercise, input, expected_output)
SELECT id, '6', '720' FROM exercises WHERE title = 'Factorial Calculation' AND id_language = 1 AND NOT EXISTS (SELECT 1 FROM test_cases tc WHERE tc.id_exercise = exercises.id AND tc.input = '6');

INSERT INTO test_cases (id_exercise, input, expected_output)
SELECT id, 'world', 'dlrow' FROM exercises WHERE title = 'Reverse String' AND id_language = 3 AND NOT EXISTS (SELECT 1 FROM test_cases tc WHERE tc.id_exercise = exercises.id AND tc.input = 'world');

INSERT INTO test_cases (id_exercise, input, expected_output)
SELECT id, '20', '2 3 5 7 11 13 17 19' FROM exercises WHERE title = 'Prime Numbers in Range' AND id_language = 3 AND NOT EXISTS (SELECT 1 FROM test_cases tc WHERE tc.id_exercise = exercises.id AND tc.input = '20');

INSERT INTO test_cases (id_exercise, input, expected_output)
SELECT id, '15', '2 3 5 7 11 13' FROM exercises WHERE title = 'Prime Numbers in Range' AND id_language = 2 AND NOT EXISTS (SELECT 1 FROM test_cases tc WHERE tc.id_exercise = exercises.id AND tc.input = '15');

INSERT INTO test_cases (id_exercise, input, expected_output)
SELECT id, '10 20 30', '60' FROM exercises WHERE title = 'Sum of Array' AND id_language = 3 AND NOT EXISTS (SELECT 1 FROM test_cases tc WHERE tc.id_exercise = exercises.id AND tc.input = '10 20 30');

INSERT INTO test_cases (id_exercise, input, expected_output)
SELECT id, '6', '720' FROM exercises WHERE title = 'Factorial' AND id_language = 3 AND NOT EXISTS (SELECT 1 FROM test_cases tc WHERE tc.id_exercise = exercises.id AND tc.input = '6');

INSERT INTO test_cases (id_exercise, input, expected_output) 
SELECT id, '0', '1' FROM exercises WHERE title = 'Factorial' AND id_language = 3 AND NOT EXISTS (SELECT 1 FROM test_cases tc WHERE tc.id_exercise = exercises.id AND tc.input = '0');

INSERT INTO test_cases (id_exercise, input, expected_output)
SELECT id, '[3,2,4], 6', '[1,2]' FROM exercises WHERE title = 'Two Sum' AND id_language = 3 AND NOT EXISTS (SELECT 1 FROM test_cases tc WHERE tc.id_exercise = exercises.id AND tc.input = '[3,2,4], 6');

INSERT INTO test_cases (id_exercise, input, expected_output)
SELECT id, 'world', 'dlrow' FROM exercises WHERE title = 'String Reversal' AND id_language = 2 AND NOT EXISTS (SELECT 1 FROM test_cases tc WHERE tc.id_exercise = exercises.id AND tc.input = 'world');

INSERT INTO test_cases (id_exercise, input, expected_output)
SELECT id, '4', 'false' FROM exercises WHERE title = 'Prime Number Check' AND id_language = 2 AND NOT EXISTS (SELECT 1 FROM test_cases tc WHERE tc.id_exercise = exercises.id AND tc.input = '4');

INSERT INTO test_cases (id_exercise, input, expected_output) 
SELECT id, '2', 'true' FROM exercises WHERE title = 'Prime Number Check' AND id_language = 2 AND NOT EXISTS (SELECT 1 FROM test_cases tc WHERE tc.id_exercise = exercises.id AND tc.input = '2');

INSERT INTO test_cases (id_exercise, input, expected_output)
SELECT id, 'cat, dog', 'false' FROM exercises WHERE title = 'Anagram Checker' AND id_language = 2 AND NOT EXISTS (SELECT 1 FROM test_cases tc WHERE tc.id_exercise = exercises.id AND tc.input = 'cat, dog');

INSERT INTO test_cases (id_exercise, input, expected_output)
SELECT id, '[10,15,20,25]', '[10,20]' FROM exercises WHERE title = 'Array Filter' AND id_language = 5 AND NOT EXISTS (SELECT 1 FROM test_cases tc WHERE tc.id_exercise = exercises.id AND tc.input = '[10,15,20,25]');

INSERT INTO test_cases (id_exercise, input, expected_output)
SELECT id, 'log, 500', 'debounced' FROM exercises WHERE title = 'Debounce Function' AND id_language = 5 AND NOT EXISTS (SELECT 1 FROM test_cases tc WHERE tc.id_exercise = exercises.id AND tc.input = 'log, 500');

INSERT INTO test_cases (id_exercise, input, expected_output)
SELECT id, '', 'Employees filtered by salary' FROM exercises WHERE title = 'Employees with Salary > 50000' AND id_language = 6 AND NOT EXISTS (SELECT 1 FROM test_cases tc WHERE tc.id_exercise = exercises.id);

INSERT INTO test_cases (id_exercise, input, expected_output)
SELECT id, '', 'Top department by average' FROM exercises WHERE title = 'Top Department by Average Salary' AND id_language = 6 AND NOT EXISTS (SELECT 1 FROM test_cases tc WHERE tc.id_exercise = exercises.id);

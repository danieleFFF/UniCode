INSERT INTO public.languages (name, type_exercise, description_course, url_theory_video)
VALUES
    ('Python', 'compilable', 'Learn Python basics: variables, loops, and functions.', 'https://youtube.com/py_intro'),
    ('C++', 'compilable', 'Master C++ syntax and object-oriented programming.', 'https://youtube.com/cpp_intro'),
    ('SQL', 'demo', 'Understand how to query and manipulate databases using SQL.', 'https://youtube.com/sql_course');

INSERT INTO public.exercises (id_language, title, description, difficulty, solution_demo)
VALUES
    (1, 'Sum of Two Numbers',
     'Write a function that takes two integers and returns their sum.',
     'Easy',
     'def sum_two_numbers(a, b):\n    return a + b'),
    (1, 'Factorial Calculation',
     'Write a recursive function to calculate the factorial of a number.',
     'Medium',
     'def factorial(n):\n    return 1 if n <= 1 else n * factorial(n - 1)');

INSERT INTO public.exercises (id_language, title, description, difficulty, solution_demo)
VALUES
    (2, 'Reverse String',
     'Write a C++ function that reverses a given string.',
     'Easy',
     '#include <algorithm>\n#include <string>\nstd::string reverseString(std::string s){ std::reverse(s.begin(), s.end()); return s; }'),
    (2, 'Prime Numbers in Range',
     'Display all prime numbers between 1 and N.',
     'Medium',
     '#include <iostream>\nbool isPrime(int n){ if(n<2)return false; for(int i=2;i*i<=n;i++) if(n%i==0) return false; return true; }\nint main(){int N; std::cin>>N; for(int i=2;i<=N;i++) if(isPrime(i)) std::cout<<i<<\" \";}');

INSERT INTO public.exercises (id_language, title, description, difficulty, solution_demo)
VALUES
    (3, 'Employees with Salary > 50000',
     'Select all employees whose salary is greater than 50000.',
     'Easy',
     'SELECT * FROM employees WHERE salary > 50000;'),
    (3, 'Top Department by Average Salary',
     'Find the department with the highest average salary.',
     'Medium',
     'SELECT department_id, AVG(salary) AS avg_salary FROM employees GROUP BY department_id ORDER BY avg_salary DESC LIMIT 1;');


INSERT INTO public.test_cases (id_exercise, input, expected_output)
VALUES
    (1, '3 5', '8'),
    (1, '-2 10', '8'),
    (2, '5', '120'),
    (2, '0', '1');

INSERT INTO public.test_cases (id_exercise, input, expected_output)
VALUES
    (3, 'hello', 'olleh'),
    (3, 'abcd', 'dcba'),
    (4, '10', '2 3 5 7'),
    (4, '5', '2 3 5');

INSERT INTO public.test_cases (id_exercise, input, expected_output)
VALUES
    (5, '', 'All employees with salary > 50000'),
    (6, '', 'Department with highest average salary');

INSERT INTO public.avatar (url_image)
VALUES
    ('');

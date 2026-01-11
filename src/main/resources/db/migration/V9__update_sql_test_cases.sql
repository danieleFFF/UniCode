UPDATE public.test_cases 
SET input = 'CREATE TABLE employees (id INTEGER PRIMARY KEY, name TEXT, department_id INTEGER, salary INTEGER, hire_date TEXT);
INSERT INTO employees VALUES (1, ''John Doe'', 1, 60000, ''2020-01-15'');
INSERT INTO employees VALUES (2, ''Jane Smith'', 2, 45000, ''2019-05-20'');
INSERT INTO employees VALUES (3, ''Bob Johnson'', 1, 75000, ''2018-03-10'');
INSERT INTO employees VALUES (4, ''Alice Brown'', 3, 52000, ''2021-07-01'');
INSERT INTO employees VALUES (5, ''Charlie Davis'', 2, 48000, ''2020-11-25'');',
    expected_output = 'John Doe|60000
Bob Johnson|75000
Alice Brown|52000'
WHERE id_exercise = 7;

UPDATE public.test_cases 
SET input = 'CREATE TABLE employees (id INTEGER PRIMARY KEY, name TEXT, department_id INTEGER, salary INTEGER, hire_date TEXT);
INSERT INTO employees VALUES (1, ''John'', 1, 60000, ''2020-01-15'');
INSERT INTO employees VALUES (2, ''Jane'', 2, 45000, ''2019-05-20'');
INSERT INTO employees VALUES (3, ''Bob'', 1, 75000, ''2018-03-10'');
INSERT INTO employees VALUES (4, ''Alice'', 2, 55000, ''2021-07-01'');',
    expected_output = '1|67500.0'
WHERE id_exercise = 8;

UPDATE public.test_cases 
SET input = 'CREATE TABLE products (id INTEGER PRIMARY KEY, product_name TEXT, price REAL, category TEXT);
CREATE TABLE order_items (id INTEGER PRIMARY KEY, order_id INTEGER, product_id INTEGER, quantity INTEGER);
INSERT INTO products VALUES (1, ''Laptop'', 999.99, ''Electronics'');
INSERT INTO products VALUES (2, ''Mouse'', 29.99, ''Electronics'');
INSERT INTO products VALUES (3, ''Keyboard'', 79.99, ''Electronics'');
INSERT INTO order_items VALUES (1, 1, 1, 1);
INSERT INTO order_items VALUES (2, 1, 2, 2);
INSERT INTO order_items VALUES (3, 2, 2, 1);
INSERT INTO order_items VALUES (4, 3, 2, 3);
INSERT INTO order_items VALUES (5, 3, 3, 1);',
    expected_output = 'Mouse|4'
WHERE id_exercise = 15;


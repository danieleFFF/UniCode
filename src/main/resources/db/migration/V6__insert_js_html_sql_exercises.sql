INSERT INTO public.languages (name, type_exercise, description_course, url_theory_video)
VALUES
    ('JavaScript', 'compilable', 'Learn JavaScript fundamentals: DOM manipulation, functions, and async programming.', 'https://youtube.com/js_intro'),
    ('HTML', 'demo', 'Master HTML structure: semantic elements, forms, and accessibility.', 'https://youtube.com/html_intro');

INSERT INTO public.exercises (id_language, title, description, difficulty, solution_demo)
VALUES
    (4, 'Find Maximum Value',
     'Write a function that takes an array of numbers and returns the maximum value.',
     'Easy',
     'function findMax(arr) {\n    return Math.max(...arr);\n}'),
    (4, 'Palindrome Checker',
     'Write a function that checks if a given string is a palindrome (reads the same forwards and backwards).',
     'Medium',
     'function isPalindrome(str) {\n    const cleaned = str.toLowerCase().replace(/[^a-z0-9]/g, "");\n    return cleaned === cleaned.split("").reverse().join("");\n}'),
    (4, 'Deep Object Clone',
     'Write a function that creates a deep clone of a nested object without using JSON methods.',
     'Hard',
     'function deepClone(obj) {\n    if (obj === null || typeof obj !== "object") return obj;\n    if (Array.isArray(obj)) return obj.map(item => deepClone(item));\n    const cloned = {};\n    for (const key in obj) {\n        if (obj.hasOwnProperty(key)) cloned[key] = deepClone(obj[key]);\n    }\n    return cloned;\n}');

INSERT INTO public.exercises (id_language, title, description, difficulty, solution_demo)
VALUES
    (5, 'Basic Page Structure',
     'Create a basic HTML page with a header, main content section, and footer using semantic elements.',
     'Easy',
     '<!DOCTYPE html>\n<html lang="en">\n<head>\n    <meta charset="UTF-8">\n    <title>My Page</title>\n</head>\n<body>\n    <header><h1>Welcome</h1></header>\n    <main><p>Content goes here</p></main>\n    <footer><p>Footer</p></footer>\n</body>\n</html>'),
    (5, 'Contact Form',
     'Create an HTML form with name, email, and message fields. Include proper labels and input validation attributes.',
     'Medium',
     '<!DOCTYPE html>\n<html lang="en">\n<head>\n    <meta charset="UTF-8">\n    <title>Contact</title>\n</head>\n<body>\n    <form>\n        <label for="name">Name:</label>\n        <input type="text" id="name" name="name" required>\n        <label for="email">Email:</label>\n        <input type="email" id="email" name="email" required>\n        <label for="message">Message:</label>\n        <textarea id="message" name="message" required></textarea>\n        <button type="submit">Send</button>\n    </form>\n</body>\n</html>'),
    (5, 'Accessible Navigation',
     'Create a responsive navigation menu with proper ARIA attributes, skip links, and keyboard accessibility.',
     'Hard',
     '<!DOCTYPE html>\n<html lang="en">\n<head>\n    <meta charset="UTF-8">\n    <title>Nav Demo</title>\n</head>\n<body>\n    <a href="#main" class="skip-link">Skip to main content</a>\n    <nav role="navigation" aria-label="Main navigation">\n        <ul>\n            <li><a href="/" aria-current="page">Home</a></li>\n            <li><a href="/about">About</a></li>\n            <li><a href="/contact">Contact</a></li>\n        </ul>\n    </nav>\n    <main id="main" tabindex="-1"><h1>Welcome</h1></main>\n</body>\n</html>');

INSERT INTO public.exercises (id_language, title, description, difficulty, solution_demo)
VALUES
    (3, 'Find Most Popular Product',
     'Write a query to find the product with the most orders from the products and order_items tables.',
     'Hard',
     'SELECT p.product_name, COUNT(oi.product_id) AS order_count FROM products p JOIN order_items oi ON p.id = oi.product_id GROUP BY p.id, p.product_name ORDER BY order_count DESC LIMIT 1;');

INSERT INTO public.test_cases (id_exercise, input, expected_output)
VALUES
    (7, '[3, 1, 4, 1, 5, 9, 2, 6]', '9'),
    (7, '[-5, -2, -8, -1]', '-1'),
    (8, 'racecar', 'true'),
    (8, 'hello', 'false'),
    (9, '{"a": 1, "b": {"c": 2}}', '{"a": 1, "b": {"c": 2}}');

INSERT INTO public.test_cases (id_exercise, input, expected_output)
VALUES
    (10, '', 'Valid semantic HTML structure'),
    (11, '', 'Form with proper labels and validation'),
    (12, '', 'Navigation with ARIA attributes');

INSERT INTO public.test_cases (id_exercise, input, expected_output)
VALUES
    (13, '', 'Product with highest order count');

INSERT INTO exercises (id_language, title, description, difficulty, points, solution_demo) VALUES
(1, 'FizzBuzz', 'Write a function that prints numbers from 1 to n. For multiples of 3 print "Fizz", for multiples of 5 print "Buzz", for both print "FizzBuzz".', 'Easy', 10, 'def fizzbuzz(n):\n    for i in range(1, n+1):\n        if i % 15 == 0: print("FizzBuzz")\n        elif i % 3 == 0: print("Fizz")\n        elif i % 5 == 0: print("Buzz")\n        else: print(i)'),
(1, 'Count Vowels', 'Write a function that counts the number of vowels in a given string.', 'Easy', 10, 'def count_vowels(s):\n    return sum(1 for c in s.lower() if c in "aeiou")'),
(1, 'List Flatten', 'Write a function that flattens a nested list into a single list.', 'Medium', 50, 'def flatten(lst):\n    result = []\n    for item in lst:\n        if isinstance(item, list): result.extend(flatten(item))\n        else: result.append(item)\n    return result'),
(1, 'Binary Search', 'Implement binary search algorithm to find an element in a sorted list.', 'Medium', 50, 'def binary_search(arr, target):\n    left, right = 0, len(arr) - 1\n    while left <= right:\n        mid = (left + right) // 2\n        if arr[mid] == target: return mid\n        elif arr[mid] < target: left = mid + 1\n        else: right = mid - 1\n    return -1'),
(1, 'Merge Sort', 'Implement the merge sort algorithm.', 'Hard', 100, 'def merge_sort(arr):\n    if len(arr) <= 1: return arr\n    mid = len(arr) // 2\n    left = merge_sort(arr[:mid])\n    right = merge_sort(arr[mid:])\n    return merge(left, right)'),
(1, 'LRU Cache', 'Implement a Least Recently Used (LRU) cache with get and put operations.', 'Hard', 100, 'from collections import OrderedDict\nclass LRUCache:\n    def __init__(self, capacity):\n        self.cache = OrderedDict()\n        self.capacity = capacity');

INSERT INTO exercises (id_language, title, description, difficulty, points, solution_demo) VALUES
(2, 'String Reversal', 'Write a method that reverses a given string without using StringBuilder.reverse().', 'Easy', 10, 'public String reverse(String s) {\n    char[] chars = s.toCharArray();\n    int left = 0, right = chars.length - 1;\n    while (left < right) {\n        char temp = chars[left];\n        chars[left++] = chars[right];\n        chars[right--] = temp;\n    }\n    return new String(chars);\n}'),
(2, 'Prime Number Check', 'Write a method that checks if a given number is prime.', 'Easy', 10, 'public boolean isPrime(int n) {\n    if (n <= 1) return false;\n    for (int i = 2; i <= Math.sqrt(n); i++)\n        if (n % i == 0) return false;\n    return true;\n}'),
(2, 'Anagram Checker', 'Write a method that checks if two strings are anagrams of each other.', 'Medium', 50, 'public boolean isAnagram(String s1, String s2) {\n    char[] a = s1.toCharArray();\n    char[] b = s2.toCharArray();\n    Arrays.sort(a);\n    Arrays.sort(b);\n    return Arrays.equals(a, b);\n}'),
(2, 'LinkedList Cycle Detection', 'Implement an algorithm to detect if a linked list has a cycle.', 'Medium', 50, 'public boolean hasCycle(ListNode head) {\n    ListNode slow = head, fast = head;\n    while (fast != null && fast.next != null) {\n        slow = slow.next;\n        fast = fast.next.next;\n        if (slow == fast) return true;\n    }\n    return false;\n}'),
(2, 'Binary Tree Level Order', 'Implement level order traversal of a binary tree.', 'Hard', 100, 'public List<List<Integer>> levelOrder(TreeNode root) {\n    List<List<Integer>> result = new ArrayList<>();\n    if (root == null) return result;\n    Queue<TreeNode> queue = new LinkedList<>();\n    queue.offer(root);\n    while (!queue.isEmpty()) {\n        int size = queue.size();\n        List<Integer> level = new ArrayList<>();\n        for (int i = 0; i < size; i++) {\n            TreeNode node = queue.poll();\n            level.add(node.val);\n            if (node.left != null) queue.offer(node.left);\n            if (node.right != null) queue.offer(node.right);\n        }\n        result.add(level);\n    }\n    return result;\n}');

INSERT INTO exercises (id_language, title, description, difficulty, points, solution_demo) VALUES
(3, 'Sum of Array', 'Write a function that calculates the sum of all elements in an array.', 'Easy', 10, '#include <iostream>\nusing namespace std;\nint sumArray(int arr[], int n) {\n    int sum = 0;\n    for (int i = 0; i < n; i++) sum += arr[i];\n    return sum;\n}'),
(3, 'Factorial', 'Write a function that calculates the factorial of a number.', 'Easy', 10, '#include <iostream>\nusing namespace std;\nlong long factorial(int n) {\n    if (n <= 1) return 1;\n    return n * factorial(n - 1);\n}'),
(3, 'Two Sum', 'Given an array of integers, find two numbers that add up to a target value.', 'Medium', 50, '#include <vector>\n#include <unordered_map>\nusing namespace std;\nvector<int> twoSum(vector<int>& nums, int target) {\n    unordered_map<int, int> map;\n    for (int i = 0; i < nums.size(); i++) {\n        int complement = target - nums[i];\n        if (map.count(complement)) return {map[complement], i};\n        map[nums[i]] = i;\n    }\n    return {};\n}'),
(3, 'Valid Parentheses', 'Write a function that checks if a string of parentheses is valid.', 'Medium', 50, '#include <stack>\n#include <string>\nusing namespace std;\nbool isValid(string s) {\n    stack<char> st;\n    for (char c : s) {\n        if (c == ''('' || c == ''['' || c == ''{'') st.push(c);\n        else {\n            if (st.empty()) return false;\n            if (c == '')'' && st.top() != ''('') return false;\n            if (c == '']'' && st.top() != ''['') return false;\n            if (c == ''}'' && st.top() != ''{'') return false;\n            st.pop();\n        }\n    }\n    return st.empty();\n}'),
(3, 'Longest Common Subsequence', 'Find the longest common subsequence between two strings.', 'Hard', 100, '#include <string>\n#include <vector>\nusing namespace std;\nint lcs(string s1, string s2) {\n    int m = s1.size(), n = s2.size();\n    vector<vector<int>> dp(m+1, vector<int>(n+1, 0));\n    for (int i = 1; i <= m; i++)\n        for (int j = 1; j <= n; j++)\n            dp[i][j] = s1[i-1] == s2[j-1] ? dp[i-1][j-1] + 1 : max(dp[i-1][j], dp[i][j-1]);\n    return dp[m][n];\n}');

INSERT INTO exercises (id_language, title, description, difficulty, points, solution_demo) VALUES
(5, 'Array Filter', 'Write a function that filters an array to only include even numbers.', 'Easy', 10, 'function filterEvens(arr) {\n    return arr.filter(n => n % 2 === 0);\n}'),
(5, 'Object Deep Merge', 'Write a function that deep merges two objects.', 'Medium', 50, 'function deepMerge(obj1, obj2) {\n    const result = {...obj1};\n    for (const key in obj2) {\n        if (typeof obj2[key] === "object" && obj2[key] !== null && typeof result[key] === "object")\n            result[key] = deepMerge(result[key], obj2[key]);\n        else result[key] = obj2[key];\n    }\n    return result;\n}'),
(5, 'Promise All Implementation', 'Implement your own version of Promise.all().', 'Hard', 100, 'function promiseAll(promises) {\n    return new Promise((resolve, reject) => {\n        const results = [];\n        let completed = 0;\n        promises.forEach((p, i) => {\n            Promise.resolve(p).then(value => {\n                results[i] = value;\n                if (++completed === promises.length) resolve(results);\n            }).catch(reject);\n        });\n    });\n}'),
(5, 'Debounce Function', 'Implement a debounce function that delays invoking a function.', 'Medium', 50, 'function debounce(fn, delay) {\n    let timeoutId;\n    return function(...args) {\n        clearTimeout(timeoutId);\n        timeoutId = setTimeout(() => fn.apply(this, args), delay);\n    };\n}'),
(5, 'Curry Function', 'Implement a curry function that transforms a function to accept arguments one at a time.', 'Hard', 100, 'function curry(fn) {\n    return function curried(...args) {\n        if (args.length >= fn.length) return fn.apply(this, args);\n        return (...nextArgs) => curried(...args, ...nextArgs);\n    };\n}');

INSERT INTO exercises (id_language, title, description, difficulty, points, solution_demo) VALUES
(4, 'Image Gallery', 'Create an HTML image gallery with thumbnails and a lightbox effect using only HTML and CSS.', 'Medium', 50, '<!DOCTYPE html>\n<html>\n<head><title>Gallery</title></head>\n<body>\n<div class="gallery">\n<img src="img1.jpg" alt="Image 1">\n<img src="img2.jpg" alt="Image 2">\n</div>\n</body>\n</html>'),
(4, 'Responsive Table', 'Create a responsive HTML table that adapts to mobile screens.', 'Easy', 10, '<!DOCTYPE html>\n<html>\n<body>\n<table>\n<thead><tr><th>Name</th><th>Age</th></tr></thead>\n<tbody><tr><td>John</td><td>25</td></tr></tbody>\n</table>\n</body>\n</html>'),
(4, 'HTML Email Template', 'Create an HTML email template with proper table-based layout for email clients.', 'Hard', 100, '<!DOCTYPE html>\n<html>\n<body>\n<table width="600" cellpadding="0" cellspacing="0">\n<tr><td>Header</td></tr>\n<tr><td>Content</td></tr>\n<tr><td>Footer</td></tr>\n</table>\n</body>\n</html>'),
(4, 'Video Embed', 'Create an HTML page with embedded video player with controls and fallback content.', 'Easy', 10, '<!DOCTYPE html>\n<html>\n<body>\n<video controls width="640">\n<source src="video.mp4" type="video/mp4">\nYour browser does not support video.\n</video>\n</body>\n</html>');

INSERT INTO exercises (id_language, title, description, difficulty, points, solution_demo) VALUES
(6, 'Select All Users', 'Write a query to select all users from the users table.', 'Easy', 10, 'SELECT * FROM users;'),
(6, 'Count by Category', 'Write a query to count the number of products in each category.', 'Easy', 10, 'SELECT category, COUNT(*) as count FROM products GROUP BY category;'),
(6, 'Join Tables', 'Write a query to join orders and customers tables to show customer names with their orders.', 'Medium', 50, 'SELECT c.name, o.order_id, o.total FROM customers c JOIN orders o ON c.id = o.customer_id;'),
(6, 'Subquery Filter', 'Write a query to find employees who earn more than the average salary.', 'Medium', 50, 'SELECT * FROM employees WHERE salary > (SELECT AVG(salary) FROM employees);'),
(6, 'Window Functions', 'Write a query using window functions to rank employees by salary within each department.', 'Hard', 100, 'SELECT name, department, salary, RANK() OVER (PARTITION BY department ORDER BY salary DESC) as rank FROM employees;'),
(6, 'Recursive CTE', 'Write a recursive query to find all subordinates of a manager in an employee hierarchy.', 'Hard', 100, 'WITH RECURSIVE subordinates AS (\n  SELECT id, name, manager_id FROM employees WHERE manager_id = 1\n  UNION ALL\n  SELECT e.id, e.name, e.manager_id FROM employees e JOIN subordinates s ON e.manager_id = s.id\n) SELECT * FROM subordinates;');

INSERT INTO test_cases (id_exercise, input, expected_output) VALUES
((SELECT id FROM exercises WHERE title = 'FizzBuzz' AND id_language = 1), '15', '1\n2\nFizz\n4\nBuzz\nFizz\n7\n8\nFizz\nBuzz\n11\nFizz\n13\n14\nFizzBuzz'),
((SELECT id FROM exercises WHERE title = 'Count Vowels' AND id_language = 1), 'Hello World', '3'),
((SELECT id FROM exercises WHERE title = 'Binary Search' AND id_language = 1), '[1,2,3,4,5], 3', '2');

INSERT INTO test_cases (id_exercise, input, expected_output) VALUES
((SELECT id FROM exercises WHERE title = 'String Reversal' AND id_language = 2), 'hello', 'olleh'),
((SELECT id FROM exercises WHERE title = 'Prime Number Check' AND id_language = 2), '17', 'true'),
((SELECT id FROM exercises WHERE title = 'Anagram Checker' AND id_language = 2), 'listen, silent', 'true');

INSERT INTO test_cases (id_exercise, input, expected_output) VALUES
((SELECT id FROM exercises WHERE title = 'Sum of Array' AND id_language = 3), '1 2 3 4 5', '15'),
((SELECT id FROM exercises WHERE title = 'Factorial' AND id_language = 3), '5', '120'),
((SELECT id FROM exercises WHERE title = 'Two Sum' AND id_language = 3), '[2,7,11,15], 9', '[0,1]');

INSERT INTO test_cases (id_exercise, input, expected_output) VALUES
((SELECT id FROM exercises WHERE title = 'Array Filter' AND id_language = 5), '[1,2,3,4,5,6]', '[2,4,6]'),
((SELECT id FROM exercises WHERE title = 'Debounce Function' AND id_language = 5), 'function, 300', 'debounced function');

INSERT INTO test_cases (id_exercise, input, expected_output) VALUES
((SELECT id FROM exercises WHERE title = 'Select All Users' AND id_language = 6), '', 'All users returned'),
((SELECT id FROM exercises WHERE title = 'Count by Category' AND id_language = 6), '', 'Category counts');

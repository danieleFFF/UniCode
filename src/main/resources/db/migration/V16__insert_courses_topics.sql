INSERT INTO public.courses_topics (id_language, title, introduction, code_example, key_concepts, url_video, difficulty)
VALUES (
           (SELECT id FROM public.languages WHERE name = 'Python'),
           'Syntax and Variables',
           'A variable is a reusable container for storing a value. This video explains how to declare variables and covers the four basic data types in Python. It also demonstrates how to print variables efficiently.',
           '# Variables and Data Types
       age = 21              # Integer
       price = 10.99         # Float
       name = "Bro"          # String
       is_online = True      # Boolean

       # Printing with f-strings (recommended)
       print(f''Hello {name}'')
       print(f''You are {age} years old'')',
           '**Variables**: Reusable containers for storing data values (Strings, Integers, etc.).
       **Integers**: Whole numbers without any decimal point (e.g., 21).
       **Floats**: Floating-point numbers that contain decimals (e.g., 10.99).
       **Strings**: A series of characters used to represent text, enclosed in quotes.
       **Booleans**: Binary values that can only be True or False.
       **f-strings**: A modern way to format strings in Python by embedding variables directly inside curly braces {}.',
           'https://youtu.be/LKFrQXaoSMQ',
        'Easy'
       );

INSERT INTO public.courses_topics (id_language, title, introduction, code_example, key_concepts, url_video, difficulty)
VALUES (
           (SELECT id FROM public.languages WHERE name = 'Python'),
           'Data Structures (Lists, Dictionaries)',
           'This video covers Python''s four main built-in data structures: Lists, Tuples, Sets, and Dictionaries. It explains their characteristics, such as mutability and ordering, and demonstrates common operations like slicing, adding, and removing items.',
           '# List (Mutable Sequence)
       animals = ["pig", "cow", "horse"]
       animals.append("sheep")
       print(animals[1])  # cow

       # Dictionary (Key-Value Pairs)
       prices = {"apple": 0.99, "banana": 0.59}
       prices["cherry"] = 1.50
       print(f''Apple cost: {prices["apple"]}'')',
           '**List**: A mutable, ordered sequence of elements, widely used for general-purpose storage.
       **Dictionary**: An unordered collection of key-value pairs, similar to a hash map in other languages.
       **Tuple**: An immutable sequence, meaning items cannot be added, removed, or changed after creation.
       **Set**: An unordered collection of unique elements, useful for mathematical operations like unions and eliminating duplicates.
       **Mutability**: The ability of an object to be changed after it is created (Lists are mutable, Tuples are immutable).',
           'https://youtu.be/R-HLU9Fl5ug',
        'Medium'
       );


INSERT INTO public.courses_topics (id_language, title, introduction, code_example, key_concepts, url_video, difficulty)
VALUES (
           (SELECT id FROM public.languages WHERE name = 'Python'),
           'Functions and Modules',
           'A function is a block of reusable code that is executed only when it is called. Functions help to avoid repetition and make code more modular. You can pass data, known as parameters, into a function, and a function can return data as a result.',
           '# Defining a function with parameters
       def happy_birthday(name, age):
           print(f''Happy Birthday to {name}!'')
           print(f''You are {age} years old!'')

       # Defining a function that returns a value
       def add_numbers(x, y):
           return x + y

       # Invoking (calling) the functions
       happy_birthday(''Bro'', 21)

       result = add_numbers(5, 10)
       print(result)  # Output: 15',
           '**Function**: A block of reusable code that performs a specific task and runs only when invoked.
       **Parameter**: A variable listed inside the parentheses in the function definition (a placeholder).
       **Argument**: The actual value sent to the function when it is called.
       **Return Statement**: A statement used to end a function and send a result back to the caller.
       **Invoke**: The action of calling a function using its name followed by parentheses ().',
           'https://youtu.be/89cGQjB5R4M',
        'Medium'
       );

INSERT INTO public.courses_topics (id_language, title, introduction, code_example, key_concepts, url_video, difficulty)
VALUES (
           (SELECT id FROM public.languages WHERE name = 'Python'),
           'Object-Oriented Programming (OOP)',
           'Object-Oriented Programming (OOP) is a programming paradigm based on the concept of "objects", which can contain data (attributes) and code (methods). A class functions as a blueprint for creating these objects. This video demonstrates how to create a class representing a Car, assign attributes like model and color, and define methods for actions like driving.',
           'class Car:
           def __init__(self, make, model, year, color):
               self.make = make
               self.model = model
               self.year = year
               self.color = color

           def drive(self):
               print(f''This {self.model} is driving'')

           def stop(self):
               print(f''This {self.model} is stopped'')

       # Creating an instance (object) of the Car class
       car_1 = Car("Chevy", "Corvette", 2021, "blue")

       car_1.drive()
       car_1.stop()',
           '**Class**: A blueprint or template for creating objects (e.g., the concept of a "Car").
       **Object**: An instance of a class (e.g., a specific Corvette).
       **Attribute**: A variable that belongs to an object (e.g., color, model).
       **Method**: A function that belongs to an object and performs an action (e.g., drive, stop).
       **__init__**: A special method (constructor) automatically called when a new object is created to initialize its attributes.
       **self**: A reference to the current instance of the class, used to access variables that belong to the class.',
           'https://youtu.be/q2SGW2VgwAM',
        'Hard'
       );

INSERT INTO public.courses_topics (id_language, title, introduction, code_example, key_concepts, url_video, difficulty)
VALUES (
           (SELECT id FROM public.languages WHERE name = 'C++'),
           'Classes and Templates',
           'Class templates allow you to define a generic class that can work with different data types, reducing code duplication. Instead of writing separate classes for integers, strings, etc., you create a single "blueprint". The compiler then generates the specific classes at compile-time based on the types you provide (instantiation).',
           '// defined with template parameters for Type (T) and Size (N)
       template <typename T, int N>
       class Array {
       private:
           T m_Array[N];
       public:
           int GetSize() const { return N; }

           void Fill(T value) {
               for(int i = 0; i < N; i++)
                   m_Array[i] = value;
           }

           T& At(int index) {
               return m_Array[index];
           }
       };

       int main() {
           // Instantiate an array of 5 Integers
           Array<int, 5> intArray;
           intArray.Fill(10);

           // Instantiate an array of 5 Strings
           Array<std::string, 5> stringArray;
           stringArray.Fill(''Hello'');

           return 0;
       }',
           '**Class Template**: A generic class definition that allows working with different data types without rewriting code.
       **template <typename T>**: The syntax to declare a template, where "T" acts as a placeholder for the data type.
       **Instantiation**: The process where the compiler generates a specific class from the template (e.g., Array<int> is a specific instance of the Array template).
       **Non-Type Template Parameter**: A template argument that is a value (like an integer size) rather than a type, which must be known at compile-time.
       **Compile-time**: The phase where templates are processed and code is generated, as opposed to runtime.',
           'https://youtu.be/mQqzP9EWu58',
        'Medium'
       );

INSERT INTO public.courses_topics (id_language, title, introduction, code_example, key_concepts, url_video, difficulty)
VALUES (
           (SELECT id FROM public.languages WHERE name = 'C++'),
           'Pointers and Memory Management',
           'A pointer is a variable that stores the memory address of another variable. They are powerful tools for dynamic memory management, efficient array handling, and passing large data structures to functions without making copies. The video uses a "free pizza" analogy: instead of carrying pizzas door-to-door (passing by value), it''s easier to tell people the address where the pizzas are located (passing by reference/pointer).',
           'std::string name = "Bro";
       int age = 21;

       // Creating pointers
       std::string *pName = &name; // pName stores the address of name
       int *pAge = &age;           // pAge stores the address of age

       // Dereferencing (accessing the value at the address)
       std::cout << *pName; // Outputs: Bro
       std::cout << *pAge;  // Outputs: 21

       // Arrays are already pointers to the first element
       std::string pizzas[5] = {"Pizza1", "Pizza2", "Pizza3", "Pizza4", "Pizza5"};
       std::string *pPizzas = pizzas;

       std::cout << *pPizzas; // Outputs: Pizza1',
           '**Pointer**: A variable that holds the memory address of another variable.
       **Address-of Operator (&)**: A symbol used to get the memory address of a variable (e.g., &age).
       **Dereference Operator (*)**: A symbol used to access the value stored at the memory address a pointer is holding.
       **Memory Address**: The specific location in the computer''s memory where data is stored (often displayed in hexadecimal).
       **Null Pointer**: A pointer that does not point to any valid memory location, often used to indicate "no value".',
           'https://youtu.be/slzcWKWCMBg',
        'Hard'
       );

INSERT INTO public.courses_topics (id_language, title, introduction, code_example, key_concepts, url_video, difficulty)
VALUES (
           (SELECT id FROM public.languages WHERE name = 'C++'),
           'Standard Template Library (STL)',
           'The STL is a powerful set of C++ template classes to provide general-purpose classes and functions with templates that implement many popular and commonly used algorithms and data structures like vectors, lists, queues, and stacks. It acts as a "toolbox" for developers, optimizing efficiency and code reusability.',
           '#include <vector>
       #include <algorithm>
       #include <iostream>

       int main() {
           // 1. Container (Vector)
           std::vector<int> numbers = {4, 2, 5, 1, 3};

           // 2. Algorithm (Sort) using Iterators (begin, end)
           std::sort(numbers.begin(), numbers.end());

           // 3. Iterating through the sorted container
           for (int n : numbers) {
               std::cout << n << " ";
           }
           // Output: 1 2 3 4 5
           return 0;
       }',
           '**Containers**: Objects that store data. Examples include Vectors (dynamic arrays), Lists (linked lists), and Maps (key-value pairs).
       **Algorithms**: A collection of functions to process data, such as sorting, searching, and manipulating elements within containers.
       **Iterators**: Objects that point to an element inside a container, acting like a cursor to navigate through data.
       **Functors (Function Objects)**: Objects that can be treated as functions, often used to customize the behavior of algorithms (e.g., defining a custom sorting order).',
           'https://youtu.be/_NlRcT5gWpo',
        'Hard'
       );

INSERT INTO public.courses_topics (id_language, title, introduction, code_example, key_concepts, url_video, difficulty)
VALUES (
           (SELECT id FROM public.languages WHERE name = 'Java'),
           'JVM, Syntax, and Primitive Types',
           'Java distinguishes between Primitive Types (which hold simple values) and Reference Types (which hold the memory address of complex objects). Understanding this distinction is crucial for understanding how the JVM manages memory. When you manipulate a primitive, you work with the value directly; when you manipulate a reference (like a String), you are working with a pointer to an object in memory.',
           '// Primitive Type: Holds the value directly
       int x = 10;
       int y = x; // Copies the value 10
       y = 20;    // x remains 10, y becomes 20

       // Reference Type: Holds a memory address pointing to an object
       String s1 = "Hello";
       String s2 = s1; // Copies the REFERENCE (address), not the object itself

       // In memory:
       // x: [ 10 ]
       // s1: [ 0x123 ] --> points to "Hello" object',
           '**Primitive Types**: Basic data types (int, double, boolean, char, etc.) that store simple values directly in the stack memory.
       **Reference Types**: Complex data types (Objects like String, Arrays, Classes) where the variable stores a memory address (reference) pointing to the object in the heap.
       **Memory Address**: The specific location in memory where data is stored. Reference variables hold these addresses rather than the data itself.
       **Value Assignment**: Assigning one primitive to another copies the value. Assigning one reference to another copies the address (so both point to the same object).',
           'https://youtu.be/OmcFVHpb0v0',
        'Easy'
       );

INSERT INTO public.courses_topics (id_language, title, introduction, code_example, key_concepts, url_video, difficulty)
VALUES (
           (SELECT id FROM public.languages WHERE name = 'Java'),
           'Classes and Objects',
           'Classes and Objects are the essence of Java. A Class serves as a "blueprint" or template to create objects, defining the attributes (variables) and behaviors (methods) they will have. An Object is a specific "instance" of a class. The video uses a "Pokemon" class example to demonstrate how to define instance variables (name, level), methods (attack), and constructors.',
           'public class Pokemon {
           String name;
           int level;

           // Constructor: Special method to initialize objects
           Pokemon(String name, int level) {
               this.name = name;
               this.level = level;
           }

           void attack() {
               System.out.println(name + " attack!");
           }

           public static void main(String[] args) {
               // Creating an object (Instance) of the Pokemon class
               Pokemon p1 = new Pokemon("Eevee", 25);

               p1.attack(); // Output: Eevee attack!
               System.out.println(p1.level); // Output: 25
           }
       }',
           '**Class**: A blueprint or template for creating objects (e.g., the abstract idea of a "Pokemon").
       **Object**: A specific instance of a class created in memory (e.g., a specific "Eevee" with level 25).
       **Instance Variable**: Variables defined inside a class that belong to each specific object (each Pokemon has its own unique name and level).
       **Constructor**: A special method called when an object is instantiated (using the "new" keyword) to initialize its attributes.
       **this keyword**: A reference to the current object instance, often used in constructors to distinguish between fields and parameters (e.g., this.name = name).',
           'https://youtu.be/IUqKuGNasdM',
        'Medium'
       );

INSERT INTO public.courses_topics (id_language, title, introduction, code_example, key_concepts, url_video, difficulty)
VALUES (
           (SELECT id FROM public.languages WHERE name = 'Java'),
           'Inheritance and Polymorphism',
           'Inheritance allows a class (subclass) to acquire the properties and methods of another class (superclass), creating an "is-a" relationship (e.g., a Lion "is an" Animal). Polymorphism allows a reference variable of a superclass type to point to an object of a subclass. This enables "Method Overriding", where the specific method implementation is determined at runtime based on the actual object type, not the variable type.',
           'class Animal {
           void makeSound() {
               System.out.println("Some generic animal sound");
           }
       }

       class Lion extends Animal {
           @Override
           void makeSound() {
               System.out.println("Roar!");
           }
       }

       public class Main {
           public static void main(String[] args) {
               // Polymorphism: Animal reference holding a Lion object
               Animal myAnimal = new Lion();

               // Calls the Lion''s version of the method (Runtime Polymorphism)
               myAnimal.makeSound(); // Output: Roar!
           }
       }',
           '**Inheritance**: The mechanism where a new class (subclass) derives attributes and methods from an existing class (superclass) using the "extends" keyword.
       **Polymorphism**: The ability of an object to take on many forms. Commonly used to treat objects of different subclasses as objects of their common superclass.
       **Method Overriding**: When a subclass provides a specific implementation for a method that is already defined in its superclass.
       **Super Keyword**: A reference variable used to refer to the immediate parent class object, often used to call the parent''s constructor or methods.
       **is-a Relationship**: The fundamental relationship in inheritance (e.g., a Car "is a" Vehicle).',
           'https://youtu.be/gc216ndOrb4',
        'Hard'
       );

INSERT INTO public.courses_topics (id_language, title, introduction, code_example, key_concepts, url_video, difficulty)
VALUES (
           (SELECT id FROM public.languages WHERE name = 'Java'),
           'Collections Framework',
           'The Java Collections Framework provides a set of interfaces and classes to store and manipulate groups of data efficiently. It includes widely used data structures like Arrays, Linked Lists, Trees, and Hash Tables. The video explains the hierarchy (Iterable -> Collection -> List/Set/Queue) and when to use specific implementations (e.g., ArrayList vs. LinkedList) based on performance needs (Big O complexity) for operations like access, insertion, and removal.',
           'import java.util.*;

       public class Main {
           public static void main(String[] args) {
               // 1. LIST: Ordered collection, allows duplicates
               List<String> names = new ArrayList<>();
               names.add("Alice");
               names.add("Bob");
               names.add("Alice"); // Allowed

               // Utility method from Collections class
               Collections.sort(names);

               // 2. SET: Unique elements, no duplicates
               Set<Integer> uniqueNumbers = new HashSet<>();
               uniqueNumbers.add(10);
               uniqueNumbers.add(20);
               uniqueNumbers.add(10); // Ignored

               // 3. MAP: Key-Value pairs
               Map<String, Integer> ages = new HashMap<>();
               ages.put("Alice", 25);
               ages.put("Bob", 30);

               System.out.println(ages.get("Alice")); // Output: 25
           }
       }',
           '**Collection Interface**: The root interface in the hierarchy (extending Iterable) representing a group of objects.
       **List**: An ordered collection (sequence) that allows duplicate elements. Implementations: **ArrayList** (fast random access, backed by array), **LinkedList** (fast inserts/deletes, backed by nodes).
       **Set**: A collection that contains no duplicate elements. Implementations: **HashSet** (unordered, fast), **TreeSet** (sorted).
       **Map**: An object that maps keys to values; cannot contain duplicate keys. Implementations: **HashMap** (fast lookup O(1)), **TreeMap** (sorted keys).
       **Queue/Deque**: Collections used to hold multiple elements prior to processing (FIFO/LIFO).
       **Big O Notation**: Used to describe the performance/complexity of operations (e.g., Accessing ArrayList is O(1), Searching LinkedList is O(n)).',
           'https://youtu.be/viTHc_4XfCA',
        'Hard'
       );

INSERT INTO public.courses_topics (id_language, title, introduction, code_example, key_concepts, url_video, difficulty)
VALUES (
           (SELECT id FROM public.languages WHERE name = 'JavaScript'),
           'Variables, Data Types, and Operators',
           'JavaScript is a dynamically typed language, meaning variables can hold different types of data at different times. This video introduces the three ways to declare variables (var, let, const), with a strong recommendation to use "let" and "const" to avoid scope issues. It also covers the seven primitive data types and essential operators, highlighting the difference between loose equality (==) and strict equality (===).',
           '// 1. Variable Declaration
       let name = "Alice";      // Can be reassigned
       const age = 30;          // Cannot be changed (Constant)
       // var city = "NY";      // Avoid using var (old way)

       // 2. Data Types & Dynamic Typing
       let data = 42;           // Number
       data = "Hello";          // Now it''s a String (Dynamic)

       // 3. Operators & Strict Equality
       let x = "5";
       let y = 5;

       // Loose equality (checks value, converts type)
       console.log(x == y);     // true

       // Strict equality (checks value AND type) - Recommended
       console.log(x === y);    // false',
           '**let vs const**: Use "const" for values that won''t change and "let" for variables that will. Avoid "var" due to scoping issues.
       **Primitive Types**: The basic data building blocks: Number, String, Boolean, Undefined, Null, Symbol, and BigInt.
       **Dynamic Typing**: The ability of a variable to change its data type at runtime (e.g., holding a number, then a string).
       **Strict Equality (===)**: A comparison operator that checks if both the value AND the data type are identical, preventing unexpected type coercion bugs.
       **Type Coercion**: The automatic or implicit conversion of values from one data type to another (e.g., "5" - 2 becomes 3).',
           'https://youtu.be/g23MWKirn1c',
        'Easy'
       );

INSERT INTO public.courses_topics (id_language, title, introduction, code_example, key_concepts, url_video, difficulty)
VALUES (
           (SELECT id FROM public.languages WHERE name = 'JavaScript'),
           'Event Handling & The Event Object (e)',
           'Event handling is the process of capturing and responding to user interactions (like clicks, key presses, or mouse movements) on a web page. When an event occurs, the browser creates an "Event Object" (often named "e" or "event") which is passed to the handler function. This object contains crucial details about the event, such as which element was clicked (target), the mouse coordinates, or if special keys (Ctrl, Alt) were held down.',
           '// Selecting the button element
       const myButton = document.querySelector("#myBtn");

       // Adding an event listener for the "click" event
       // The function receives the event object "e" automatically
       myButton.addEventListener("click", function(e) {

           // Accessing properties of the event object
           console.log("Button clicked!");
           console.log("Element clicked (Target):", e.target);
           console.log("X Coordinate:", e.clientX);

           // Checking if the Control key was held down
           if (e.ctrlKey) {
               console.log("You held the Control key!");
           }
       });',
           '**Event Listener**: A method (addEventListener) that attaches an event handler to an element without overwriting existing handlers.
       **Event Object (e)**: An object automatically passed to the event handler containing information about the event (e.g., e.target, e.clientX).
       **e.target**: A property of the event object that refers to the specific HTML element that dispatched the event.
       **Callback Function**: The function that is executed ("called back") when the event is triggered.
       **Mouse Event**: A specific type of event object generated by mouse interactions (click, mouseup, mousedown), containing coordinates and button states.',
           'https://youtu.be/_BVkOvpyRI0',
        'Medium'
       );

INSERT INTO public.courses_topics (id_language, title, introduction, code_example, key_concepts, url_video, difficulty)
VALUES (
           (SELECT id FROM public.languages WHERE name = 'JavaScript'),
           'Asynchronous Programming (Promises & Async/Await)',
           'Asynchronous programming allows JavaScript to perform long-running tasks (like fetching data from a server or reading files) without blocking the main execution thread. This video explains the evolution from "Callback Hell" to Promises, and finally to the modern Async/Await syntax, which makes asynchronous code look and behave more like synchronous code.',
           '// 1. Using Promises (.then / .catch)
       const fetchPromise = fetch("https://api.example.com/data");

       fetchPromise
           .then(response => response.json())
           .then(data => console.log("Data received:", data))
           .catch(error => console.error("Something went wrong:", error));

       // 2. Modern Async/Await (Cleaner & Readable)
       async function fetchData() {
           try {
               const response = await fetch("https://api.example.com/data");
               const data = await response.json();
               console.log("Data received:", data);
           } catch (error) {
               console.error("Something went wrong:", error);
           }
       }

       fetchData();',
           '**Asynchronous**: Code that runs in the background (like a timer or network request) allowing the rest of the program to continue executing immediately.
       **Callback**: A function passed as an argument to another function, to be executed later (often used in older async code, leading to "Callback Hell").
       **Promise**: An object representing the eventual completion (or failure) of an asynchronous operation. It has three states: Pending, Resolved (Success), or Rejected (Failure).
       **Async/Await**: Syntactic sugar built on top of Promises that allows you to write asynchronous code that looks synchronous, improving readability.
       **Try/Catch**: A block used to handle errors in async/await functions (similar to .catch() in Promises).',
           'https://youtu.be/670f71LTWpM',
        'Hard'
       );

INSERT INTO public.courses_topics (id_language, title, introduction, code_example, key_concepts, url_video, difficulty)
VALUES (
           (SELECT id FROM public.languages WHERE name = 'HTML'),
           'Tags and Attributes',
           'HTML elements are the building blocks of web pages, defined by tags (keywords surrounded by angle brackets). Attributes provide additional information about these elements, such as where a link points to or the size of an image. They are always specified in the start tag and usually come in name/value pairs.',
           '<a href="https://www.w3schools.com">Visit W3Schools</a>

       <img src="image.jpg" width="500" height="600">

       <p title="This text appears on hover">Hover over me!</p>

       <html lang="en">',
           '**Tag**: The keywords surrounded by angle brackets (e.g., <html>, <a>) that define an element.
       **Attribute**: Additional information provided in the start tag, usually in the format name="value".
       **href**: The attribute used in anchor (<a>) tags to specify the URL of the page the link goes to.
       **src**: The "source" attribute used in image (<img>) tags to specify the path to the image file.
       **style**: An attribute used to add CSS styles (like color or size) directly to an element.
       **lang**: An attribute usually placed in the <html> tag to declare the language of the page (important for search engines and screen readers).
       **title**: An attribute that adds a tooltip description when a user hovers over the element.',
           'https://youtu.be/yMX901oVtn8',
        'Easy'
       );

INSERT INTO public.courses_topics (id_language, title, introduction, code_example, key_concepts, url_video, difficulty)
VALUES (
           (SELECT id FROM public.languages WHERE name = 'HTML'),
           'Forms and Inputs',
           'Forms are the primary way users interact with a website, allowing them to send data to a server (e.g., for logging in, signing up, or searching). A form is defined by the <form> tag and contains various <input> elements. This video demonstrates how to build a sign-up form using different input types (text, email, password, radio, checkbox, date), how to use labels for accessibility, and how to structure the form using fieldsets.',
           '<form action="/submit-data">
         <fieldset>
           <legend>Account Details</legend>

           <label for="email">Email:</label>
           <input type="email" id="email" name="email" placeholder="user@example.com" required>

           <label for="pass">Password:</label>
           <input type="password" id="pass" name="password">
         </fieldset>

         <fieldset>
           <legend>Preferences</legend>

           <p>Subscribe to newsletter?</p>
           <input type="radio" id="yes" name="news" value="yes" checked>
           <label for="yes">Yes</label>

           <input type="radio" id="no" name="news" value="no">
           <label for="no">No</label>

           <br>

           <input type="checkbox" id="terms" name="terms">
           <label for="terms">I agree to Terms</label>
         </fieldset>

         <input type="submit" value="Sign Up">
       </form>',
           '**<form>**: The container element for all form controls. attributes like "action" (where to send data) and "method" (GET/POST) are commonly used.
       **<input>**: The most versatile form element. The "type" attribute determines its behavior (e.g., text, email, password, radio, checkbox, date, submit).
       **<label>**: Defines a text label for an input. The "for" attribute must match the "id" of the input to bind them together (clicking the label focuses the input).
       **Placeholder**: An attribute that specifies a short hint that describes the expected value of an input field.
       **Fieldset & Legend**: <fieldset> groups related elements in a form (drawing a box around them), and <legend> defines a caption for that group.',
           'https://youtu.be/H9zdfZrFUp0',
        'Medium'
       );

INSERT INTO public.courses_topics (id_language, title, introduction, code_example, key_concepts, url_video, difficulty)
VALUES (
           (SELECT id FROM public.languages WHERE name = 'HTML'),
           'Semantic HTML',
           'Semantic HTML means using tags that convey the *meaning* of the content, not just its appearance. Unlike generic tags like <div>, semantic tags (like <header>, <article>, <footer>) clearly define the purpose of a section to the browser and developers. This improves accessibility for screen readers and helps Search Engine Optimization (SEO).',
           '<div id="header">Title</div>
       <div class="post">Content...</div>

       <body>
         <header>
           <h1>My Website</h1>
           <nav>
             <a href="#home">Home</a>
             <a href="#about">About</a>
           </nav>
         </header>

         <main>
           <article>
             <h2>Blog Post Title</h2>
             <p>This is the main content...</p>
           </article>

           <aside>
             <h3>Related Links</h3>
             </aside>
         </main>

         <footer>
           <p>&copy; 2023 My Website</p>
         </footer>
       </body>',
           '**Semantic Tags**: Elements that clearly describe their meaning in a human- and machine-readable way (e.g., <header> vs <div>).
       **<header>**: Represents introductory content, typically a group of introductory or navigational aids (logos, titles).
       **<nav>**: Defines a section of navigation links.
       **<main>**: Specifies the main content of the document; there should be only one <main> element per page.
       **<article>**: Represents a self-contained composition (like a blog post or news story) that is independently distributable.
       **<section>**: Defines a thematic grouping of content, typically with a heading.
       **<aside>**: Defines content aside from the content it is placed in (like a sidebar or related links).
       **<footer>**: Defines a footer for a document or section, often containing copyright info or contact links.',
           'https://youtu.be/kX3TfdUqpuU',
        'Hard'
       );

INSERT INTO public.courses_topics (id_language, title, introduction, code_example, key_concepts, url_video, difficulty)
VALUES (
           (SELECT id FROM public.languages WHERE name = 'SQL'),
           'Selection and Joins',
           'A Join is a clause used to combine rows from two or more tables based on a related column between them (usually a Foreign Key). The video uses a Venn diagram analogy: "Inner Join" is the intersection (only matching records), "Left Join" takes everything from the left table (plus matches), and "Right Join" takes everything from the right table (plus matches). This allows you to query related data, such as finding which customer made a specific transaction.',
           '-- 1. INNER JOIN: Only rows with matches in BOTH tables
       SELECT transaction_id, amount, first_name, last_name
       FROM transactions
       INNER JOIN customers
       ON transactions.customer_id = customers.customer_id;

       -- 2. LEFT JOIN: All rows from Left table + matches from Right
       -- (Shows transactions even without a customer)
       SELECT *
       FROM transactions
       LEFT JOIN customers
       ON transactions.customer_id = customers.customer_id;

       -- 3. RIGHT JOIN: All rows from Right table + matches from Left
       -- (Shows customers even if they made no transactions)
       SELECT *
       FROM transactions
       RIGHT JOIN customers
       ON transactions.customer_id = customers.customer_id;',
           '**Inner Join**: Returns records that have matching values in both tables.
       **Left Join**: Returns all records from the left table, and the matched records from the right table (NULL if no match).
       **Right Join**: Returns all records from the right table, and the matched records from the left table (NULL if no match).
       **Foreign Key**: A field (or collection of fields) in one table, that refers to the PRIMARY KEY in another table.
       **ON Clause**: The part of the JOIN statement that specifies the column(s) used to link the two tables.',
           'https://youtu.be/G3lJAxg1cy8',
        'Easy'
       );

INSERT INTO public.courses_topics (id_language, title, introduction, code_example, key_concepts, url_video, difficulty)
VALUES (
           (SELECT id FROM public.languages WHERE name = 'SQL'),
           'Functions and Aggregations',
           'Aggregate functions perform a calculation on a set of values (a column) and return a single value. They are essential for reporting and data analysis. Common functions include counting rows, summing values, and finding averages. The video demonstrates these using an "Order Details" table to calculate total sales and product quantities.',
           '-- 1. COUNT: How many orders contain Product ID 10?
       SELECT COUNT(*)
       FROM order_details
       WHERE product_id = 10;

       -- 2. SUM: Total quantity sold for Product ID 10
       SELECT SUM(quantity)
       FROM order_details
       WHERE product_id = 10;

       -- 3. MIN/MAX/AVG: Analyzing Unit Price
       SELECT
           MIN(unit_price) AS cheapest_price,
           MAX(unit_price) AS most_expensive,
           AVG(unit_price) AS average_price
       FROM order_details;

       -- 4. Math inside Aggregations: Average Order Value (Price * Quantity)
       SELECT AVG(unit_price * quantity) AS avg_order_value
       FROM order_details;',
           '**COUNT()**: Returns the number of rows that match a specified criterion (e.g., how many orders exist).
       **SUM()**: Returns the total sum of a numeric column.
       **AVG()**: Returns the average value of a numeric column.
       **MIN() / MAX()**: Returns the smallest or largest value of the selected column.
       **Alias (AS)**: Used to give a table or a column in a result set a temporary name (e.g., "AS average_price") to make the output more readable.',
           'https://youtu.be/jcoJuc5e3RE',
        'Hard'
       );
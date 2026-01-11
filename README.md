Web application used to exercise on some programming languages (Python, C++, SQL, ecc), similarly to Codewars.

## How to run the project

**Prerequisites:** Java 21, PostgreSQL, Node.js, RapidAPI account with a Judge0 plan and api key.

### Database
Open DBeaver

### Judge0 (for code compilation)
Create 'application-local.yml' file under 'application.yml' containing the code:
```bash
judge0:
  api-key: YOUR_JUDGE0_API_KEY
```

### Backend
```bash
mvn spring-boot:run
```

### Frontend
```bash
cd frontend
ng serve
```


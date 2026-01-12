Web application used to exercise on some programming languages (Python, C++, SQL, ecc), similarly to Codewars.


https://github.com/user-attachments/assets/bdc623d4-ad05-42e7-972d-670ff3ae0816

(feature not shown in the video)
<img width="1642" height="778" alt="image" src="https://github.com/user-attachments/assets/76ae4067-6745-4d10-b169-73ccd2d2a6c5" />
(feature not shown in the video)
<img width="1607" height="847" alt="image" src="https://github.com/user-attachments/assets/fe1d9de5-7e1b-4d23-98fb-bc63f5d3f85f" />
(feature not shown in the video)
<img width="483" height="662" alt="image" src="https://github.com/user-attachments/assets/df00a10b-ed83-403d-8cdc-09bcbb93b2c1" />

## How to run the project

**Prerequisites:** Java 21, PostgreSQL, Node.js, RapidAPI account (with a Judge0 plan and API key).

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


Web Application used to exercise on some programming languages (Python, C++, SQL, etc).

https://github.com/user-attachments/assets/bdc623d4-ad05-42e7-972d-670ff3ae0816

(feature not shown in the video)
<img width="1642" height="778" alt="image" src="https://github.com/user-attachments/assets/76ae4067-6745-4d10-b169-73ccd2d2a6c5" />
(feature not shown in the video)
<img width="1607" height="847" alt="image" src="https://github.com/user-attachments/assets/fe1d9de5-7e1b-4d23-98fb-bc63f5d3f85f" />
###
(feature not shown in the video)
###
<img width="500" height="662" alt="image" src="https://github.com/user-attachments/assets/df00a10b-ed83-403d-8cdc-09bcbb93b2c1" />

(feature not shown in the video)
<img width="742" height="783" alt="image" src="https://github.com/user-attachments/assets/6acd4bea-84ab-4d7d-a066-148b65774b81" />

###
# How to run

**Prerequisites:** Java 21, PostgreSQL, Node.js, RapidAPI account (with a Judge0 plan and API key).

### Database
Open DBeaver and create a PostgreSQL connection with any credentials (name & password). 
### 
Insert your credentials in the file src -> main -> resources -> **application.yml**.
### 
Insert the same credentials in the **pom.xml** file at line 126, in the flyway plugin section.
###
Check for any thread running on ports 8080 and terminate it.

### Judge0 API (for code compilation, not necessary to run application correctly)
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

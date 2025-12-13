Web application per esercitarsi su vari linguaggi di programmazione (Python, C++, SQL, ecc), simile a Codewars.

## Come runnare il progetto

**Prerequisiti:** Java 21, PostgreSQL, Node.js

### Backend
```bash
mvn spring-boot:run
```

### Frontend
```bash
cd frontend
ng serve
```

Il database viene creato automaticamente. Di default usa `postgres` con password `1234`.

Se vuoi usare la funzione di recupero password via email, configura le variabili `MAIL_USERNAME` e `MAIL_PASSWORD` con un account Gmail.


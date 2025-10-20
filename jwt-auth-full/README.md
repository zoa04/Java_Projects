# JWT Authentication - Spring Boot (Starter)

Features:
- User registration and login
- JWT token generation and validation
- Role-based access control (ROLE_USER, ROLE_ADMIN)
- CORS support and example instructions for integrating a React SPA

Run:
1. Java 11+ and Maven
2. mvn spring-boot:run
3. H2 console: http://localhost:8080/h2-console (jdbc:h2:mem:authdb)

API (examples):
- POST /api/auth/register { username, password, roles (optional) }
- POST /api/auth/login { username, password } -> returns { token }
- GET /api/users (ROLE_ADMIN required)
- GET /api/profile (authenticated)

React integration:
- Add `Authorization: Bearer <token>` header to requests after login.
- Enable CORS at /api/** (already configured).

This is a starter template — I can add refresh tokens, OAuth2, or a React frontend if you want.


## Frontend
A React SPA was added at `frontend/`. Start with `npm install` and `npm start`.

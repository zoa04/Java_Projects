# Matrícula e Controle de Frequência (Jakarta EE / JBoss-WildFly)

Projeto Jakarta EE (WAR) pronto para deploy em JBoss / WildFly. Fornece REST endpoints para:
- Cadastro de alunos (students)
- Cadastro de disciplinas/courses
- Matrícula (enrollment) de alunos em disciplinas
- Registro de frequência (attendance) por aula/data
- Relatórios simples (frequência por aluno/disciplina)

### Como usar (desenvolvimento local)
1. Java 11+ e Maven.
2. Empacote: `mvn clean package` — gerará `target/matricula-frequencia.war`.
3. Deploy no WildFly / JBoss (arraste o WAR no admin console ou coloque em `standalone/deployments/`).
4. Endpoints base: `http://localhost:8080/matricula-frequencia/api/...`

### Endpoints principais (REST)
- `POST /api/students` — criar aluno `{ "name":"João", "email":"joao@example.com" }`
- `GET /api/students` — listar alunos
- `POST /api/courses` — criar disciplina `{ "name":"Algoritmos", "code":"ALG101" }`
- `GET /api/courses` — listar disciplinas
- `POST /api/enrollments` — matricular `{ "studentId":1, "courseId":1 }`
- `POST /api/attendance` — registrar presença `{ "enrollmentId":1, "date":"2025-10-20", "present":true }`
- `GET /api/reports/attendance?studentId=1&courseId=1` — relatório de frequência

### Banco de dados
- Configuração JPA em `src/main/resources/META-INF/persistence.xml` usando H2 para testes locais.
- Em produção/ WildFly você deve configurar datasource no servidor e alterar `jta-data-source` para o JNDI apropriado.

---
Projeto gerado automaticamente — posso estender com autenticação, UI, export CSV/PDF ou integração com LDAP se desejar.

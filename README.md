# My Music Journal - Backend

## 🎯 Project Overview

MyMusicJournal is a comprehensive web application for tracking and documenting your concert experiences. Users can create, edit, and delete journal entries for concerts they've attended, including artist information, dates, venues, cities, ratings, and personal notes. Built with React (frontend), Spring Boot (backend), and PostgreSQL (database), the application follows MVC architecture and implements a RESTful API.

## 🧩 Technical Competencies

This project develops the following technical competencies:

- **Backend Development:** Implementation of server-side logic and RESTful API endpoints with Spring Boot.
- **Database Design:** Creation and structuring of PostgreSQL relational databases with proper schema design.
- **Data Access Components:** Development of repositories for seamless communication between the API and database using Spring Data JPA.
- **API Security:** Implementation of JWT-based authentication and authorization with Spring Security.
- **Testing:** Validation of system behavior using testing frameworks like JUnit, Mockito, and integration tests.
- **Database Migrations:** Management of schema evolution using Flyway for version control.
- **DTOs and Mapping:** Implementation of Data Transfer Objects with MapStruct for clean API contracts.

## ⚙️ Technologies and Tools

This project leverages a modern stack of technologies ensuring performance, scalability, and industry best practices:

### Core Technologies

- **Language:** Java 21
- **Framework:** Spring Boot 3.5.7
- **Database:** PostgreSQL 42.6.0
- **ORM:** JPA/Hibernate
- **Authentication:** JWT (JSON Web Tokens) via Auth0 library
- **Mapping:** MapStruct 1.6.3
- **Build Tool:** Maven 3.x

### Development & Testing

- **Testing Framework:** JUnit 5
- **Mocking:** Mockito
- **Testing Library:** spring-boot-starter-test
- **Security Testing:** spring-security-test

### Database & Migration

- **Migrations:** Flyway
- **In-Memory Testing:** H2 Database

### Version Control & Tools

- **Repository:** GitHub
- **API Testing:** Postman
- **Project Management:** Jira

## ✨ Features

### Authentication & Authorization

- **User Registration** with email validation and password confirmation
- **User Login** with JWT token generation (24-hour expiration)
- **Secure Logout** with proper session handling
- **JWT-based Authentication** for all protected endpoints
- **Password Hashing** using BCrypt for security

### Journal Entry Management

**Protected Endpoints (Authentication Required)**

- **Create Journal Entry** - Add new concert experiences with:

  - Concert selection from catalog
  - Personal rating (1-5 scale)
  - Personal notes (10-5000 characters)
  - Optional background image
  - Automatic date capture

- **View Journal Entries** with advanced filtering:

  - All user's entries (no filter)
  - Filter by year
  - Filter by city
  - Filter by genre
  - Combination filters (year + city, year + genre, city + genre, or all three)

- **View Single Entry** - Detailed view of a specific journal entry

- **Update Journal Entry** - Modify existing entries (user's own entries only)

- **Delete Journal Entry** - Remove entries (user's own entries only)

### Concert Management

**Public Endpoints (No Authentication Required)**

- **Browse All Concerts** with optional filtering:

  - Filter by year
  - Filter by city
  - Filter by genre
  - Combination filters

- **View Concert Details** - Get complete information about a specific concert

**Protected Endpoints (Authentication Required)**

- **Get Map Data** - Retrieve latitude/longitude for user's attended concerts for visualization on interactive map

### Data Relationships

```
User (1) ──→ (N) JournalEntry
Concert (1) ──→ (N) JournalEntry

User ←──(N:M)──→ Concert (through JournalEntry)
```

- **User to Journal Entry:** One-to-Many relationship. Each user can have multiple journal entries documenting their concert experiences.
- **Concert to Journal Entry:** One-to-Many relationship. Each concert can appear in multiple users' journals.
- **User to Concert:** Many-to-Many relationship. Users can attend multiple concerts, and each concert can be attended by multiple users, materialized through the JournalEntry table.

## 📋 API Endpoints

### Authentication Endpoints (`/api/auth`)

| Method | Endpoint    | Auth Required | Description                             |
| ------ | ----------- | ------------- | --------------------------------------- |
| POST   | `/register` | No            | Register new user account               |
| POST   | `/login`    | No            | Authenticate user and receive JWT token |
| POST   | `/logout`   | No            | Invalidate user session                 |

### Journal Entry Endpoints (`/api/journal`)

| Method | Endpoint | Auth Required | Description                                        |
| ------ | -------- | ------------- | -------------------------------------------------- |
| POST   | `/`      | Yes           | Create new journal entry                           |
| GET    | `/`      | Yes           | Get user's journal entries (with optional filters) |
| GET    | `/{id}`  | Yes           | Get single journal entry by ID                     |
| PUT    | `/{id}`  | Yes           | Update journal entry                               |
| DELETE | `/{id}`  | Yes           | Delete journal entry                               |

**Query Parameters for GET `/`:**

- `year` (Optional, Integer) - Filter entries by year
- `city` (Optional, String) - Filter entries by city
- `genre` (Optional, String) - Filter entries by music genre

### Concert Endpoints (`/api/concerts`)

| Method | Endpoint | Auth Required | Description                               |
| ------ | -------- | ------------- | ----------------------------------------- |
| GET    | `/`      | No            | Get all concerts (with optional filters)  |
| GET    | `/{id}`  | No            | Get concert details by ID                 |
| GET    | `/map`   | Yes           | Get map data for user's attended concerts |

**Query Parameters for GET `/`:**

- `year` (Optional, Integer) - Filter concerts by year
- `city` (Optional, String) - Filter concerts by city
- `genre` (Optional, String) - Filter concerts by music genre

## 🔒 Security Features

- **JWT Authentication:** 24-hour token expiration for API requests
- **Password Security:** BCrypt hashing with salt for password storage
- **Authorization:** Users can only access their own journal entries
- **CORS Configuration:** Restricted to specified frontend origins
- **Spring Security:** Comprehensive security framework integration
- **Environment Variables:** Sensitive credentials (database password, JWT secret) stored in `.env` file, not in source code

## 🚀 Getting Started

### Prerequisites

- Java 21 or higher
- PostgreSQL 12 or higher (installed and running)
- Maven 3.6.0 or higher
- Git for version control

### Installation Steps

#### 1. Clone the Repository

```bash
git clone https://github.com/DaniPacheco8/MyMusicJournal-BackEnd.git
cd MyMusicJournal-BackEnd
```

#### 2. Configure the Database

Create a PostgreSQL database for the application:

```sql
CREATE DATABASE mymusicjournal;
```

#### 3. Set Up Environment Variables

Create a `.env` file in the project root:

```bash
# Database Credentials
DB_PASSWORD=your_database_password

# JWT Configuration
JWT_SECRET=your_jwt_secret_key_must_be_at_least_256_bits_long
```

The application uses `spring-dotenv` to load these variables automatically.

#### 4. Run the Application

**Using Maven Wrapper (if available):**

```bash
./mvnw spring-boot:run
```

**Using System Maven:**

```bash
mvn spring-boot:run
```

**Building JAR for Production:**

```bash
mvn clean package
java -jar target/journal-0.0.1-SNAPSHOT.jar
```

#### 5. Verify Installation

The API will be available at: `http://localhost:8080`

Test a simple endpoint with curl:

```bash
curl http://localhost:8080/api/concerts
```

## 🧩 Project Structure

```
MyMusicJournal-BackEnd/
├── src/
│   ├── main/
│   │   ├── java/com/mymusic/journal/
│   │   │   ├── controller/          # REST Controllers
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── JournalEntryController.java
│   │   │   │   └── ConcertController.java
│   │   │   ├── service/             # Business Logic
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── JournalEntryService.java
│   │   │   │   └── ConcertService.java
│   │   │   ├── repository/          # Data Access Layer
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── JournalEntryRepository.java
│   │   │   │   └── ConcertRepository.java
│   │   │   ├── entity/              # JPA Entities
│   │   │   │   ├── User.java
│   │   │   │   ├── JournalEntry.java
│   │   │   │   └── Concert.java
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   ├── request/
│   │   │   │   │   ├── UserRegisterRequestDTO.java
│   │   │   │   │   ├── UserLoginRequestDTO.java
│   │   │   │   │   └── JournalEntryRequestDTO.java
│   │   │   │   └── response/
│   │   │   │       ├── AuthResponseDTO.java
│   │   │   │       ├── UserResponseDTO.java
│   │   │   │       ├── JournalEntryResponseDTO.java
│   │   │   │       ├── ConcertDTO.java
│   │   │   │       └── ConcertMapDTO.java
│   │   │   ├── mapper/              # MapStruct Mappers
│   │   │   │   ├── UserMapper.java
│   │   │   │   ├── JournalEntryMapper.java
│   │   │   │   └── ConcertMapper.java
│   │   │   ├── security/            # JWT & Security
│   │   │   │   ├── JwtTokenProvider.java
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   └── CustomUserDetailsService.java
│   │   │   ├── config/              # Spring Configuration
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── JwtProperties.java
│   │   │   ├── exception/           # Error Handling
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   └── ErrorResponse.java
│   │   │   ├── seeder/              # Database Seeding
│   │   │   │   └── DatabaseSeeder.java
│   │   │   └── MyMusicJournalApplication.java  # Main Application Class
│   │   └── resources/
│   │       ├── application.properties       # Configuration
│   │       ├── application-dev.properties   # Development profile
│   │       └── db/migration/                # Flyway migrations
│   │           └── V1__Add_rating_and_backgroundImage_to_journal_entries.sql
│   └── test/
│       ├── java/com/mymusic/journal/
│       │   ├── controller/
│       │   │   └── JournalEntryControllerIntegrationTest.java
│       │   └── service/
│       │       └── JournalEntryServiceTest.java
│       └── resources/
│           └── application.properties       # Test configuration
├── pom.xml                          # Maven dependencies
├── .env                             # Environment variables (not in git)
├── .gitignore                       # Git ignore rules
└── README.md                        # This file
```

## 🧪 Testing

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=JournalEntryControllerIntegrationTest

# Run with coverage
mvn test jacoco:report
```

### Test Coverage

- **JournalEntryService:** Unit tests for business logic
- **JournalEntryController:** Integration tests for API endpoints
- **Currently:** ~70% code coverage for JournalEntry features

### Future Testing Improvements

- [ ] AuthService unit tests
- [ ] ConcertService unit tests
- [ ] Controller error handling tests
- [ ] JWT security tests
- [ ] Target 80%+ code coverage

## 🔄 Development Workflow

1. Create a feature branch from `dev`:

   ```bash
   git checkout dev
   git checkout -b feature/your-feature-name
   ```

2. Make your changes and commit:

   ```bash
   git add .
   git commit -m "Description of changes"
   ```

3. Push and create a Pull Request:
   ```bash
   git push origin feature/your-feature-name
   ```

## 📝 Database Migrations

The project uses Flyway for database version control. Currently:

- Flyway is disabled in development (using Hibernate's `ddl-auto=update`)
- For production, migrations should be enabled with `spring.flyway.enabled=true`

**Future Improvement:** Create comprehensive migration files:

- V0\_\_Initial_schema.sql (create all tables)
- Additional migrations for schema changes

## 🚨 Known Issues & Future Improvements

### Security

- [ ] Move database credentials to environment variables (In Progress)
- [ ] Implement strong password validation policy
- [ ] Add refresh token mechanism
- [ ] Implement role-based access control (RBAC)

### Features

- [ ] Pagination support for large datasets
- [ ] Sorting by various fields
- [ ] Search functionality (full-text search)
- [ ] Concert creation endpoint (admin feature)
- [ ] User profile management endpoints
- [ ] Statistics/dashboard endpoints
- [ ] Image upload functionality

### Code Quality

- [ ] Increase test coverage to 80%+
- [ ] Add Swagger/OpenAPI documentation
- [ ] Add database indexes for performance
- [ ] Implement comprehensive request/response logging
- [ ] Refactor duplicate filtering logic

## 📧 Contact & Support

For questions, issues, or collaboration opportunities:

| Name                 | Role                     | LinkedIn                                                 | GitHub                                    |
| -------------------- | ------------------------ | -------------------------------------------------------- | ----------------------------------------- |
| **Daniella Pacheco** | Developer & Project Lead | [LinkedIn](https://www.linkedin.com/in/daniellapacheco/) | [GitHub](https://github.com/DaniPacheco8) |

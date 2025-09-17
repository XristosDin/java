-------------------------ΔΕΣ ΜΟΝΟ ΤΟ SRC-------------------------------
# Medical Appointment System

A web-based application for managing medical appointments between doctors and patients. This system allows doctors to create availability slots and patients to book appointments with doctors of various specialties.

## Technologies Used

- **Backend**: Java with Spring Boot
- **Database**: PostgreSQL
- **Frontend**: HTML, CSS, JavaScript
- **API**: RESTful API

## Project Structure

The project follows a standard Spring Boot application structure:

```
src/main/java/com/antonis33mark/javaweb/
├── controller/           # REST API controllers
├── converters/           # Data conversion utilities
├── entities/             # JPA entity classes
├── enums/                # Enumeration types
├── records/              # Data transfer objects
├── repository/           # Data access layer
├── service/              # Business logic layer
└── JavaWebApplication.java  # Main application class
```

## Features

- **User Management**:
  - Support for different user types (Doctors, Patients, Admins)
  - User authentication and authorization

- **Doctor Features**:
  - Doctor profile management
  - Speciality selection
  - Availability management
  - View scheduled appointments

- **Patient Features**:
  - Patient registration and profile management
  - Search for available appointments
  - Book appointments with doctors
  - View appointment history

- **Appointment Management**:
  - Create, update, and delete appointments
  - Filter appointments by doctor, patient, date range, or availability
  - Fee management for appointments

## Setup and Installation

### Prerequisites

- Java 17 or higher
- PostgreSQL
- Gradle

### Database Setup

1. Create a PostgreSQL database named `database`
2. Configure the database connection in `src/main/resources/application.properties`

### Building and Running

1. Clone the repository
2. Navigate to the project directory
3. Build the project:
   ```
   ./gradlew build
   ```
4. Run the application:
   ```
   ./gradlew bootRun
   ```
5. Access the application at `http://localhost:8080`

## API Endpoints

### Appointment Endpoints

- `GET /appointments` - Get all appointments
- `GET /appointments/{id}` - Get appointment by ID
- `GET /appointments/doctor/{doctorUserName}` - Get appointments by doctor
- `GET /appointments/patient/{patientUserName}` - Get appointments by patient
- `GET /appointments/available` - Get available appointments
- `GET /appointments/dateRange` - Get appointments within a date range
- `POST /appointments` - Create a new appointment
- `PUT /appointments` - Update an existing appointment
- `DELETE /appointments` - Delete all appointments
- `DELETE /appointments/{id}` - Delete appointment by ID

### Doctor Endpoints

- `GET /doctors` - Get all doctors
- `GET /doctors/{username}` - Get doctor by username
- `POST /doctors` - Create a new doctor
- `PUT /doctors/{username}` - Update an existing doctor
- `DELETE /doctors/{username}` - Delete doctor by username
- `DELETE /doctors` - Delete all doctors

### Patient Endpoints

- `GET /patients` - Get all patients
- `GET /patients/{username}` - Get patient by username
- `POST /patients` - Create a new patient
- `PUT /patients/{username}` - Update an existing patient
- `DELETE /patients/{username}` - Delete patient by username
- `DELETE /patients` - Delete all patients

## Contributing

This project was developed as part of the Web Programming course at the University of Piraeus. Contributions are welcome through pull requests.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

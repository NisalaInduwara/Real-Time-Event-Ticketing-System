# Real-Time-Event-Ticketing-System

## Introduction

The Real-Time Event Ticketing System is a comprehensive event management platform designed to streamline ticketing for various events. The system leverages a producer-consumer pattern, ensuring real-time ticket distribution and handling. It includes features like event creation, ticket booking, customer management, and ticket validation.

This system provides a backend built with Spring Boot, a frontend built with Angular, and a database using SQL to manage event and ticket information. It is designed to be scalable and efficient, handling a large number of users and ticket requests.

## Setup Instructions

### Prerequisites

Before setting up the system, ensure you have the following software installed:

- **Java Development Kit (JDK) 11 or higher**  
  Download from [Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or [OpenJDK](https://adoptopenjdk.net/).
- **Node.js** (for the frontend)  
  Download from [Node.js official website](https://nodejs.org/).
- **Maven** (for building the backend)  
  Install from [Maven official website](https://maven.apache.org/).
- **Git** (for cloning the repository)  
   Download from [Git official website](https://git-scm.com/).

## Backend Setup (Spring Boot)

### Clone the repository:

```bash
git clone https://github.com/your-repository/Real-Time-Event-Ticketing-System.git
cd Real-Time-Event-Ticketing-System/project/backend
```

## Build the Backend:

Ensure that Maven is installed, and build the Spring Boot backend using the following command:

```bash
mvn clean install
```

## Run the Backend:

To run the backend Spring Boot application, use the following command:

```bash
mvn spring-boot:run
```

## Frontend Setup (Angular)

### Navigate to the frontend directory:

```bash
cd ../frontend
```

### Install Dependencies:

Install the necessary dependencies using npm:

```bash
npm install
```

### Run the Frontend:

Start the Angular application:

```bash
ng serve
```

## Database Setup (SQL)

- Create Database: Create a database named event_ticketing_system in your SQL server.
- Configure Database Connection: Modify the application.properties file in the backend to configure your database connection:

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/event_ticketing_system
spring.datasource.username=your-database-username
spring.datasource.password=your-database-password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

- Run Database Migration: Run any necessary database migration scripts or use JPA/Hibernate to automatically set up tables.

## Usage Instructions

1. Backend Configuration:

- The backend can be configured by modifying the application.properties file located in src/main/resources.
- Key configuration options include database connection details, server port, and application-specific settings like ticket pool size and event details.

2. Frontend Configuration:

- The frontend can be configured by modifying the src/environments/environment.ts file.
- Set the backend API URL in the apiUrl variable for the frontend to interact with the backend.

## Starting the System

Once the backend and frontend are configured, you can start both the backend and frontend services.

1. Start the backend:

```bash
mvn spring-boot:run
```

2. Start the frontend:

```bash
ng serve
```

The system is now running, and you can access the application in your browser at http://localhost:4200.

## Explanation of UI Controls

1. Event Dashboard:

- Create Event: A form that allows admins to create new events by specifying details like event name, date, location, and ticket prices.
- View Events: A list of events, with options to view, edit, or delete them.

2. Ticket Booking:

- Available Tickets: A display of available tickets for the selected event.
- Booking Form: A form where users can select the number of tickets they wish to purchase, enter payment details, and finalize their booking.

3. User Profile:

- Account Settings: Allows users to view and edit their account information, including personal details and past ticket bookings.
- Booking History: A list of events the user has attended, with options to download tickets or view booking details.

4. Admin Controls:

- Manage Customers: An interface where admins can add, delete, or view customer details.
- Ticket Pool Management: A control to view and manage the number of available tickets for each event.

5. Error Handling:

- The UI provides error messages for invalid actions (e.g., invalid payment details, unavailable tickets) and guides the user through the correction process.

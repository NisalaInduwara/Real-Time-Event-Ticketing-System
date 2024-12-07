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

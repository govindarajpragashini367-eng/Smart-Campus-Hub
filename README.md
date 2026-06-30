# Smart Campus Hub

## Overview

Smart Campus Hub is a web-based campus service management system developed using Java Spring Boot. The system allows students to submit campus-related service requests, track request status, receive notifications, and manage their profiles. Administrators can view and manage submitted requests efficiently.

This project was developed following Object-Oriented Programming (OOP) principles and MVC architecture.

---

## Features

### Student Features
- User Registration
- User Login
- Profile Management
- Submit Service Requests
- View Request Status
- View Notifications
- Forgot Password Functionality
- Track Previous Requests

### Administrator Features
- Admin Login
- View All Service Requests
- Update Request Status
- Manage Student Requests

---

## Technologies Used

### Backend
- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate

### Frontend
- HTML5
- CSS3
- Thymeleaf

### Database
- MySQL

### Development Tools
- IntelliJ IDEA
- Maven

---

## Project Architecture

The project follows the MVC (Model-View-Controller) Architecture.

### Model Layer
Stores application data.

Files:
- User.java
- ServiceRequest.java
- Notification.java
- RequestStatus.java
- RequestType.java

### View Layer
Handles user interface.

Files:
- home.html
- login.html
- register.html
- profile.html
- request-form.html
- notifications.html
- admin-requests.html

### Controller Layer
Handles user requests.

Files:
- UserController.java
- ServiceRequestController.java

### Service Layer
Contains business logic.

Files:
- ServiceRequestService.java
- NotificationService.java

### Repository Layer
Handles database operations.

Files:
- UserRepository.java
- ServiceRequestRepository.java
- NotificationRepository.java

---

## OOP Concepts Used

### Encapsulation
Implemented using private attributes and public getter/setter methods.

Example:
- User.java
- ServiceRequest.java
- Notification.java

### Abstraction
Implemented through Service classes that hide business logic from Controllers.

Example:
- ServiceRequestService.java
- NotificationService.java

### Inheritance
Used through Spring Data JPA repositories.

Example:

```java
public interface UserRepository extends JpaRepository<User, Long>
```

### Polymorphism
Utilized through framework-provided interfaces and repository implementations.

---

## Project Structure

```
src
├── main
│   ├── java
│   │   ├── controller
│   │   ├── model
│   │   ├── repository
│   │   ├── service
│   │   └── SmartCampusHubApplication.java
│   │
│   └── resources
│       ├── templates
│       └── static
```

---

## Main Pages

- Home Page
- Login Page
- Registration Page
- Profile Page
- Service Request Form
- My Requests Page
- Notifications Page
- Admin Dashboard

---

## How to Run the Project

1. Clone the repository

```bash
git clone <repository-url>
```

2. Open the project in IntelliJ IDEA

3. Configure MySQL Database

Update:

```properties
application.properties
```

with your database credentials.

4. Install Maven Dependencies

```bash
mvn clean install
```

5. Run the application

```bash
SmartCampusHubApplication.java
```

6. Open browser

```text
http://localhost:8080
```

---

## Future Enhancements

- Email Notifications
- Role-Based Access Control
- Dashboard Analytics
- Mobile Application Integration
- Real-Time Request Tracking

---

## Author

Pragshini

BSc Software Engineering Student

Smart Campus Hub – OOP Based Spring Boot Project

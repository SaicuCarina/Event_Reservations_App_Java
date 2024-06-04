# Event Reservations App

Using object-oriented programming (OOP) principles to ensure solid and maintainable code, this project is a event management system written in Java. Users of the system can explore and reserve events, as well as manage their reservations. Furthermore, it has administrative features for handling users and events.

## Technologies Used
  - Java: The primary programming language for implementing the logic and functionalities of the system.
  - JDBC: For database connectivity and operations.
  - MySQL: To manage and query the database.

## Key Features

  - User Management: Users can register, log in, and view their reservations.
  - Event Management: The system supports various event categories such as shows, concerts, and conferences, each with specific dress codes.
  - Reservation System: Users can reserve seats for events, with checks to ensure seat availability and restrictions based on user activity (e.g., cancellation history).
  - OOP Principles: The project is structured using core OOP principles.
  - Database Integration: The system integrates with a relational database to store and manage data related to users, events, reservations, and locations.

 ## User Functionalities

 1. User Registration and Login:
      - Users can register an account and log in with their credentials.
 2. View Available Events:
      - Users can view a list of all available events.
      - Events are filtered to show only those that have not yet occurred.
 3. Reserve Events:
      - Users can reserve seats for events.
      - The system ensures that users do not exceed the available seat limit.
      - If a user has canceled more than three reservations in the past month, they are restricted from making new reservations for a period.
 4. Cancel Reservations:
      - Users can cancel their reservations.
      - The cancellation date is recorded, and the reserved seats are added back to the event's available seats.
 5. View Personal Reservations:
      - Users can view all their reservations, including details about each event and dress code requirements.
 6. Reporting:
      - The system provides reporting capabilities to track reservation activities.

## Project Structure



## Database

For the purpose of this project, the database acts as a central location for organizing and controlling several components of the event reservation system.

<img width="635" alt="image" src="https://github.com/SaicuCarina/Event_Reservations_App_Java/assets/93483071/d73e9570-3c76-4b5a-854e-f62daf20ae79">


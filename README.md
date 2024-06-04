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

### DAO (Data Access Object):

1. UserDAO.java: This file contains the 'UserDAO' class, responsible for handling database operations related to users, such as retrieving user information, adding new users, updating user details, and deleting users.
2. EventDAO.java: Here, you'll find the 'EventDAO' class, which manages database interactions related to events. It includes methods for fetching event details, adding new events, updating event information, and deleting events.
3. LocationDAO.java: The 'LocationDAO' class deals with database operations concerning event locations. It provides functionalities to retrieve location details, add new locations, update location information, and delete locations.
4. ReservationDAO.java: This file houses the 'ReservationDAO class', responsible for handling database operations related to event reservations. It includes methods for adding reservations, retrieving reservation details, updating reservation information, and canceling reservations.

### MODELS:

1. User.java: The 'User' class defines the structure of a user entity. It contains fields such as ID, username, email and password. Additionally, it may include methods for accessing and manipulating user data.
2. Event.java: Here, you'll find the 'Event' class, which represents the structure of an event entity. It typically includes attributes like ID, name, date, time, location, available seats, and possibly other details related to events.
3. Location.java: The 'Location' class defines the structure of a location entity. It includes properties such as ID, name, address, and capacity, reflecting the essential information associated with event locations.
4. Reservation.java: This file contains the 'Reservation' class, representing the structure of a reservation entity. It includes attributes like reservation ID, user ID, event ID, number of reserved seats, reservation date, cancellation date, and any other relevant information related to reservations.

### Enum:

1. EventCategory.java: This file contains the 'EventCategory' enum, which defines the various categories of events available, such as shows, concerts, conferences, etc. Enumerations can include types like SHOW, CONCERT, CONFERENCE, etc., depending on the requirements.

### Abstract Classes:

1. DressCode.java: In this file, we have the abstract class 'DressCode', which serves as a skeleton for defining the dress code associated with different types of events. This class can have abstract or concrete methods to handle specific clothing requirements for each event category.
2. DressCodeShow.java: Here we find the 'DressCodeShow' class, which extends the abstract class 'DressCode'. This class can provide specific implementations for the dress code associated with show events.
3. DressCodeConcert.java: Similar to 'DressCodeShow', we have the DressCodeConcert class, which extends 'DressCode', and may contain specific implementations for the dress code at concerts.
4. DressCodeConference.java: This class, which is a subclass of 'DressCode', can provide specific implementations for the dress code at conferences.

### Interfaces:

1. Search.java: In this file, we have the 'Search' interface, which defines the methods used for searching information in the database. These methods can include searching by name, date, location, etc., depending on the application's needs.
2. Reserve.java: Here we have the 'Reserve' interface, which contains methods associated with reservation operations. These methods can include adding a new reservation, canceling an existing reservation, updating reservation details, etc.

## Database

For the purpose of this project, the database acts as a central location for organizing and controlling several components of the event reservation system.

<img width="635" alt="image" src="https://github.com/SaicuCarina/Event_Reservations_App_Java/assets/93483071/d73e9570-3c76-4b5a-854e-f62daf20ae79">


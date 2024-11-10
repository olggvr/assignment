# Event managment API
## Description
Rest API, created to abile users register their own events and invite other users to participate.
User abilities:
- Account creation (with ADMIN, VISITOR, PRINCIPAL roles)
- Administrators can create and manage events
- Visitors can participate events after authentication and authorization

## Using stack 
- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Security**
- **Hibernate**
- **JUnit**

Also using **Swagger UI** as quick UI realisation

## Handles
1. ***User ability:* User registration**
   - User can register as visitor, event administrator or principal by using:
     ```
     HTTP POST: "/register"
     Params:
       - username
       - password
       - role (enum: VISITOR, ADMIN, PRINCIPAL)
     ```
     
2. ***User ability:* Authorization**
   - User can authorizate to get ability depending on it's role
     ```
     HTTP POST: "/login"
     Params:
       - username
       - password
     ```
     
3. ***Admin ability:* create event**
   - If user has role ADMIN, they will be able to create events if contract is signed by principal
     ```
     HTTP POST: "/admin/create"
     Params:
       - title
     ```
     
4. ***Admin requirement:* get signed contract from principal**
   - If admin want to create new event, he has to send contract to principal and get acception
     ```
     HTTP POST: "admin/send"
     Params:
       - adminId (id of admin sender)
       - principalId (id of needed principal)
     ```
     
5. ***Principal ability:* Accept event creation by signing contract**
   - Principal receives contracts and has ability to sign it
     ```
     HTTP POST: "principal/sign"
     Params:
       - adminId (id of admin sender)
       - principalId (id of needed principal)
     ```
     
6. ***Visitor ability:* check all actual events list avaible**
   - Visitor can check all events he can participate
     ```
     HTTP GET: "/visitor/show-events"
     Params: none
     ```
     
7. ***Visitor ability:* participate in event**
   - User can choose event he likes and participate it:
     ```
     HTTP GET: "visitor/participate/{id}"
     Params:
       - id
     ```

## Additional info
- All data saves in MySQL db server
- App is developed with **Solid** principles, it provides ease testing and flexibility

## Run with docker
```
git clone https://github.com/olggvr/assignment.git
docker compose up -d
```
To update compose:
```
docker compose up --build -d
```

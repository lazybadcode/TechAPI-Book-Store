# Winney's Book Store API - X-Series

# BookStore
Bookstore is a simple Java Spring Boot application to create RESTful API for a bookstore that allows a user to login, perform user related tasks, view a list of
books and place book orders.

---
# Functionality And Approach Taken:
BookStore uses Postgres and consists of database named “techAPI” which have 4 tables named: "user_authen", "users" , "book", "orders".  Jpa/Hibernate is used to communicate with database. In java project the database structure is present in “model” folder. BookStore supports secured communication with JWT following functionality:
<ul>
    <li>user registration API. over a secured communication.
</ul>

<ul>
    <li>user login API. over a secured communication.
</ul>

<ul>
<li>(Login required) for apis below
    <ul>
        <li> /users/create
        <li> /users/orders
        <li> /users/getUserOrder (It is require to create user first)
        <li> /users/delete (Delete logged in user’s record and order history)
    </ul>
<li>(Not Login required) for apis below
    <ul>
        <li> /users/register
        <li> /users/login
        <li> /books
    </ul>
</ul>

Please find a api document as Swagger UI as provided.
By default, it runs in port:9000. You can open up the swagger ui from following url: 
http://localhost:9000/swagger-ui/index.html

![alt text][img1]

[img1]: ./images/swagger-api.PNG "Book Store API"


###### Login required it need token to access api
![alt text][img0]

[img0]: ./images/swagger-api-secure.PNG "Secure API"

### Prerequisites
- Project structure organize the code with the following directories to separate the code into logical pieces
```
    config / — centralized configulation
    exception/ - centralized exception
    interceptor/ - manage logger to perform actions before and after web requests.
    controller/ - responsible for processing incoming REST API requests.
    model/ - represents data to implements business logic.
    repository/ = returns a reference to the entity
    service/ - implements business logic
    security/ — JWT authentication and authorization
    test/ - Junit test for user services
    BookStoreAPI Collection.postman_collection = postman colletion for all APIs
   

```
- Postgres ERD

![alt text][img2]

[img2]: ./images/ERD-diagram.PNG "Table relationship"



- Sequence Diagram for incoming REST API requests of 2 modules
```
     - Users

     - Books
    
```

###### /users/create

![alt text][img3]

[img3]: ./images/user-create-sq.PNG "Sequence for /users/create"



###### /users/orders

![alt text][img4]

[img4]: ./images/user-orders-sq.PNG "Sequence for /users/orders"



###### /users/getUserOrder

![alt text][img5]

[img5]: ./images/user-getorders-sq.PNG "Sequence for /users/getUserOrder"


###### /users/delete

![alt text][img6]

[img6]: ./images/user-delete-sq.PNG "Sequence for /users/delete"

###### /books

![alt text][img7]

[img7]: ./images/book-get-sq.PNG "Sequence for /books"
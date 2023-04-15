# Blog API Project

This is a Blog API project that allows users to add posts, comment on these posts, and search them by category. The technologies used in this project are Java 19, Spring 6, Spring Boot 3, Spring Security JWT, Spring JPA, Lombok, Spring DevTools, Microsoft SQL Server, Swagger, Docker, and AWS.

## Technologies

- Java 19
- Spring 6
- Spring Boot 3
- Spring Security JWT
- Spring JPA
- Lombok
- Spring DevTools
- Microsoft SQL Server
- Swagger
- Docker
- AWS

## Installation

To run this project locally, follow these steps:

1. Clone the repository:

```
git clone https://github.com/username/Blog-API-Project.git
``` 

2. Navigate to the project directory:

```
cd Blog-API-Project
```

3. Build the project:

```
./mvnw clean package
```

4. Start the Docker container:

```
docker-compose up
```

5. Access the API documentation in your web browser:

```
http://localhost:8080/swagger-ui.html
```

## Usage

Once the Docker container is up and running, you can use the API to add posts, comment on posts, and search for posts by category. You can also view the API documentation in your web browser by visiting `http://localhost:8080/swagger-ui.html`.

### Endpoints

| HTTP Method | Endpoint | Description |
| --- | --- | --- |
| POST | /api/auth/signup | Register a new user |
| POST | /api/auth/signin | Authenticate user and receive JWT token |
| GET | /api/posts | Get all posts |
| GET | /api/posts/{id} | Get post by ID |
| POST | /api/posts | Create new post |
| PUT | /api/posts/{id} | Update post by ID |
| DELETE | /api/posts/{id} | Delete post by ID |
| GET | /api/posts/search?category={category} | Search posts by category |
| GET | /api/posts/{postId}/comments | Get comments for a post |
| POST | /api/posts/{postId}/comments | Create a new comment for a post |
| PUT | /api/posts/{postId}/comments/{commentId} | Update a comment for a post |
| DELETE | /api/posts/{postId}/comments/{commentId} | Delete a comment for a post |


## Contributing

Contributions to this project are welcome. If you would like to contribute, please fork the repository and submit a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more information.

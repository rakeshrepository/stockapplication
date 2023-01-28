# Steps to execute the stock application

#### Step 1: Clone the project
    git clone https://github.com/rakeshrepository/stockapplication.git

#### Step 2: Enter to stockapplication folder
    cd /stockapplication

#### Step 3: Build docker image
    docker build -t stock-application .
Please do not change the image tag name, in case if need any change please make sure to update
in docker-compose file.

#### Step 4: Run docker compose file
    docker-compose up
This command bring up the container spring boot application, postgres database and pgadmin UI.
It is also stitch all the container for internal communication.

#### Step 5: Access Swagger UI from below end point
    http://localhost:8080/swagger-ui/index.html#
Swagger UI can be used to test all the CRUD apis.
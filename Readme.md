# Steps to execute the stock application

#### Step 1: Clone the project
    git  clone 

#### Step 2: go to stockapplication folder
    cd /stockapplication

#### Step 3: Build docker image
    docker build -t stock-application .

#### Step 4: Run docker compose file(brings the database and UI)
    docker-compose up

#### Step 5: Access Swagger UI from below end point
    http://localhost:8080/swagger-ui/index.html#
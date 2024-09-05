# Expenses Tracker

## Overview

A Spring Boot Application which exposes REST Apis to track your regular expenses.

## Gradle Commands

- **Clean**: Remove build artifacts.
  ```bash
  ./gradlew clean
  ```

- **Build**: Compile the project and create the build artifacts.
  ```bash
  ./gradlew build
  ```

- **Test**: Run tests.
  ```bash
  ./gradlew test
  ```

- **Skip Tests**: Build the project without running tests.
  ```bash
  ./gradlew build -x test
  ```

## Docker Commands

- **Build Docker Image**: Create a Docker image from your Dockerfile.
  ```bash
  docker build -t expenses-tracker:tag .
  ```

- **Push to Docker Hub**: Push the Docker image to Docker Hub.
  ```bash
  docker push expenses-tracker:tag
  ```

- **Pull from Docker Hub**: Pull the Docker image from Docker Hub.
  ```bash
  docker pull expenses-tracker:tag
  ```

- **Start Application**: Run the Docker container.
  ```bash
  docker run -d --name your-container-name -p host-port:container-port expenses-tracker:tag
  ```

- **Stop Application**: Stop and remove the Docker container.
  ```bash
  docker stop your-container-name
  docker rm your-container-name
  ```

## Run Application on AWS EC2

### 1. **Sign In to AWS Management Console**
- Go to [AWS Management Console](https://aws.amazon.com/console/) and sign in with your credentials.

### 2. **Navigate to EC2 Dashboard**
- In the AWS Management Console, type "EC2" in the search bar and select "EC2" from the dropdown.

### 3. **Launch Instance**
- Click on the "Launch Instance" button to start the instance creation process.

### 4. **Choose an Amazon Machine Image (AMI)**
- In the "Choose an Amazon Machine Image (AMI)" step, select "Amazon Linux 2 AMI (HVM), SSD Volume Type". This will use the Amazon Linux OS.

### 5. **Choose an Instance Type**
- In the "Choose an Instance Type" step, select `t3.micro`. This instance type is eligible for the AWS Free Tier if you're using a free-tier account.

### 6. **Configure Instance**
- Click on "Next: Configure Instance Details".
- Ensure the "Network" is set to the default VPC.
- Set the "Subnet" to a default subnet (if applicable).

### 7. **Add Storage**
- Click on "Next: Add Storage".
- By default, Amazon Linux 2 AMI comes with 8 GB of storage. To increase it to 30 GB:
    - Modify the "Size (GiB)" field for the root volume to 30 GB.
- Click "Next: Add Tags".

### 8. **Add Tags**
- Optionally, add tags to help identify your instance (e.g., Key: `Name`, Value: `MyInstance`).
- Click "Next: Configure Security Group".

### 9. **Configure Security Group**
- Choose "Create a new security group".
- Name the security group and add rules:
    - **Type:** Custom TCP Rule | **Port Range:** 8080 | **Source:** Anywhere (0.0.0.0/0) or a specific IP range (if you want to restrict access).
    - **Type:** SSH | **Port Range:** 22 | **Source:** Anywhere (0.0.0.0/0) or a specific IP range (for security reasons, it’s best to restrict SSH access to your IP).
- Click "Review and Launch".

### 10. **Prepare Your User Data Script**
- Create a script file named user-data.sh with the commands you want to run on instance startup.
- **user-data.sh**: Run both expenses-tracker and postgres on AWS EC2 linux instances.
```bash
#!/bin/bash

# Define log file location
LOG_FILE="/var/log/setup-script.log"

# Redirect stdout and stderr to the log file
exec > >(tee -a $LOG_FILE) 2>&1

# Update the package index and install necessary packages
echo "Updating package index..."
yum update -y

# Install Docker and start the service
echo "Installing Docker..."
yum install -y docker

# Start Docker service
echo "Starting Docker service..."
systemctl start docker

# Ensure Docker starts on boot
echo "Enabling Docker to start on boot..."
systemctl enable docker

# Add ec2-user to the docker group to run Docker commands without sudo
echo "Adding ec2-user to docker group..."
usermod -aG docker ec2-user

# Pull Docker images
echo "Pulling Docker images..."
docker pull postgres:latest
docker pull ravindraambati/expenses-tracker:latest

# Create Docker network if it does not exist
if ! docker network inspect my-network >/dev/null 2>&1; then
  echo "Creating Docker network 'my-network'..."
  docker network create my-network
else
  echo "Docker network 'my-network' already exists."
fi

# Run PostgreSQL container
echo "Running PostgreSQL container..."
docker run --name my-postgres --network my-network \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=expenses_tracker \
  -p 5432:5432 \
  -d postgres:latest

# Run application container
echo "Running expenses-tracker application container..."
docker run --name expenses-tracker-app --network my-network \
  -e DB_HOST=my-postgres \
  -e DB_PORT=5432 \
  -e DB_NAME=expenses_tracker \
  -e DB_USER=postgres \
  -e DB_PASSWORD=postgres \
  -p 80:8080 \
  -d ravindraambati/expenses-tracker:latest

echo "Setup complete."
```

### 11. Configure User Data
- In the "Configure Instance" step, scroll down to find the "Advanced Details" section.
- In the "User data" field, paste the contents of your user-data.sh script. Alternatively, you can upload a script file if the console allows it, but pasting the script is typically more straightforward.

### 12. **Review and Launch**
- Review your instance configuration and make sure everything is correct.
- Click "Launch".
- When prompted to select a key pair, either choose an existing key pair or create a new one. Download the key pair file (`.pem`) and keep it safe as it is required to SSH into your instance.

### 13. **Launch the Instance**
- Click the "Launch Instances" button.
- Your instance will now be launched. You can monitor its status on the EC2 Dashboard under "Instances".

### 14. **Access Your Instance**
- Once the instance state shows "running" and the status checks are complete, you can access it via SSH:
    - Retrieve the public IP address or DNS name from the EC2 Dashboard.
    - Use the following command to connect to your instance (replace `your-key.pem` with your key file name and `ec2-user@your-public-ip` with your instance’s public IP or DNS name):

      ```bash
      ssh -i "your-key.pem" ec2-user@your-public-ip
      ```

## Docker Image Management with AWS ECR

This guide will help you manage Docker images with Amazon Elastic Container Registry (ECR). Follow these steps to tag and push Docker images to your ECR repositories.

### 1. **Create an ECR Repository**

#### For a Private Repository

Create a private repository in ECR:

```bash
aws ecr create-repository --repository-name my-private-repo
```

#### For a Public Repository

Create a public repository in ECR:

```bash
aws ecr create-public-repository --repository-name my-public-repo
```

### 2. **Authenticate Docker to ECR**

#### For Private Repositories

Login to your private ECR registry:

```bash
aws ecr get-login-password --region <region> | docker login --username AWS --password-stdin <aws_account_id>.dkr.ecr.<region>.amazonaws.com
```

Replace `<region>` with your AWS region and `<aws_account_id>` with your AWS account ID.

### 3. **Tag Your Docker Image**

Tag your Docker image for the ECR repository:

#### For Private Repositories

```bash
docker tag my-app:latest <aws_account_id>.dkr.ecr.<region>.amazonaws.com/my-private-repo:latest
```

#### For Public Repositories

```bash
docker tag my-app:latest public.ecr.aws/<aws_account_id>/my-public-repo:latest
```

### 4. **Push Your Docker Image**

Push the tagged image to the ECR repository:

#### For Private Repositories

```bash
docker push <aws_account_id>.dkr.ecr.<region>.amazonaws.com/my-private-repo:latest
```

#### For Public Repositories

```bash
docker push public.ecr.aws/<aws_account_id>/my-public-repo:latest
```

### 5. **Verify Your Images**

To list your repositories and verify the image:

#### List Private Repositories

```bash
aws ecr describe-repositories
```

#### List Public Repositories

```bash
aws ecr-public describe-repositories
```

## Environment Variables

To configure the application, you need to set the following environment variables. These variables are used to connect to the PostgreSQL database:

- **`DB_HOST`**: The hostname of the PostgreSQL server. Default is `localhost`.
- **`DB_PORT`**: The port number on which the PostgreSQL server is listening. Default is `5432`.
- **`DB_NAME`**: The name of the PostgreSQL database to connect to. Default is `expenses_tracker`.
- **`DB_USER`**: The username for connecting to the PostgreSQL database. Default is `postgres`.
- **`DB_PASSWORD`**: The password for the specified user. Default is `postgres`.

### Example Configuration

You can set these variables in a `.env` file in your project root directory:

```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=expenses_tracker
DB_USER=postgres
DB_PASSWORD=postgres
```

## Usage

"# expenses-tracker"
### Expenses Details

<details>
<summary>GET | /expenses | to fetch ALL Expense Details</summary>

#### Request:
###### URI: /expenses
###### HTTP Verb: GET
###### Body: None

#### Response:
##### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve the expenses details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested expenses details are not found

##### Response Body Type: JSON
Example Response Body:
```
[
  {
    "id": 99,
    "amount": 123.45,
    "paidBy":"userId or alias",
    "paidTo":"receiverId or alias",
    "dateAndTime":"localDateTime",
    "description":"sample test",
    "currency": {
      "id": 33,
      "currency": "INR",
      "exchangeRate":"",
      "defaultCurrency":"INR",
      "description":"sample test",
      "isDeleted":"false",
      "lastUpdatedBy":"userId or alias",
      "lastUpdatedDateAndTime":"localDateTime"
    },
    "paymentMode": {
      "id": 99,
      "paymentMode": "CASH",
      "cardDetails":"",
      "isDebitCard":"false",
      "isCreditCard":"false",
      "accountDetails":"",
      "description":"sample test",
      "isDeleted":"false",
      "lastUpdatedBy":"userId or alias",
      "lastUpdatedDateAndTime":"localDateTime"
    },
    "category":{
      "id": 33,
      "expensesCategory": "FOOD",
      "alias":["breakfast","lunch","dinner"],
      "description":"sample test",
      "isDeleted":"false",
      "lastUpdatedBy":"userId or alias",
      "lastUpdatedDateAndTime":"localDateTime"
    },
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  {
    "id": 98,
    "amount": 123.45,
    "paidBy":"userId or alias",
    "paidTo":"receiverId or alias",
    "dateAndTime":"localDateTime",
    "description":"sample test",
    "currency": {
      "id": 33,
      "currency": "INR",
      "exchangeRate":"",
      "defaultCurrency":"INR",
      "description":"sample test",
      "isDeleted":"false",
      "lastUpdatedBy":"userId or alias",
      "lastUpdatedDateAndTime":"localDateTime"
    },
    "paymentMode": {
      "id": 99,
      "paymentMode": "CASH",
      "cardDetails":"",
      "isDebitCard":"false",
      "isCreditCard":"false",
      "accountDetails":"",
      "description":"sample test",
      "isDeleted":"false",
      "lastUpdatedBy":"userId or alias",
      "lastUpdatedDateAndTime":"localDateTime"
    },
    "category":{
      "id": 33,
      "expensesCategory": "FOOD",
      "alias":["breakfast","lunch","dinner"],
      "description":"sample test",
      "isDeleted":"false",
      "lastUpdatedBy":"userId or alias",
      "lastUpdatedDateAndTime":"localDateTime"
    },
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  }
]
```
</details>


<details>
<summary>GET | /expenses/{id} | to fetch Expense Details</summary>

#### Request:
###### URI: /expenses/{id}
###### HTTP Verb: GET
###### Body: None

#### Response:
##### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve the expenses details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested expenses details are not found

##### Response Body Type: JSON
Example Response Body:
```
{
  "id": 99,
  "amount": 123.45,
  "paidBy":"userId or alias",
  "paidTo":"receiverId or alias",
  "dateAndTime":"localDateTime",
  "description":"sample test",
  "currency": {
    "id": 33,
    "currency": "INR",
    "exchangeRate":"",
    "defaultCurrency":"INR",
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  "paymentMode": {
    "id": 99,
    "paymentMode": "CASH",
    "cardDetails":"",
    "isDebitCard":"false",
    "isCreditCard":"false",
    "accountDetails":"",
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  "category":{
    "id": 33,
    "expensesCategory": "FOOD",
    "alias":["breakfast","lunch","dinner"],
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>POST | /expenses | Create Expenses Details</summary>

#### Request:
###### URI: /expenses
###### HTTP Verb: POST
###### Body Type: JSON
Example Request Body:
```
{
  "amount": 123.45,
  "paidBy":"userId or alias",
  "paidTo":"receiverId or alias",
  "dateAndTime":"localDateTime",
  "description":"sample test",
  "currency": "INR",
  "paymentMode": "4315***99",
  "category":"lunch",
  "isDeleted":"false"
}
```

#### Response:
##### HTTP Status:
1. **201** CREATED  - When the User is authenticated and authorized to create the expenses details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized

##### Body: None
</details>


<details>
<summary>PUT | /expenses/{id} | Create or update Expenses Details</summary>

#### Request:
###### URI: /expenses
###### HTTP Verb: POST
###### Body Type: JSON
Example Request Body:
```
{
  "id": 99,
  "amount": 123.45,
  "paidBy":"userId or alias",
  "paidTo":"receiverId or alias",
  "dateAndTime":"localDateTime",
  "description":"sample test",
  "currency": "INR",
  "paymentMode": "4315***99",
  "category":"breakfast",
  "isDeleted":"false"
}
```

#### Response:
##### HTTP Status:
1. **201** OK - When the User is authenticated and authorized to create the expenses details
2. **204** NO CONTENT - When the User is authenticated and authorized to update the expenses details
3. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
4. **403** FORBIDDEN - When the user is authenticated but unauthorized
5. **404** NOT FOUND - When the user is authenticated and authorized but requested expenses details are not found

##### Body: None
</details>


<details>
<summary>PATCH | /expenses/{id} | update partial Expenses Details</summary>

#### Request:
###### URI: /expenses/{id}
###### HTTP Verb: PATCH
###### Request Body Type: JSON
Example Request Body:
```
{
  "amount": 123.45,
  "category": "dinner"
}
```

#### Response:
##### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to update the expenses details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested expenses details are not found

##### Response Body Type: JSON
Example Response Body:
```
{
  "id": 99,
  "amount": 123.45,
  "paidBy":"userId or alias",
  "paidTo":"receiverId or alias",
  "dateAndTime":"localDateTime",
  "description":"sample test",
  "currency": {
    "id": 33,
    "currency": "INR",
    "exchangeRate":"",
    "defaultCurrency":"INR",
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  "paymentMode": {
    "paymentMode": "CARD",
    "cardDetails":"4315***99",
    "isDebitCard":"true",
    "isCreditCard":"false",
    "accountDetails":"",
    "description":"sample test"
  },
  "category":"FOOD",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>DELETE | /expenses/{id} | delete partial Expenses Details</summary>

#### Request:
###### URI: /expenses/{id}
###### HTTP Verb: DELETE
###### Body: None

#### Response:
##### HTTP Status:
1. **204** NO CONTENT - When the User is authenticated and authorized to delete the expenses details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested expenses details are not found

##### Body: None
</details>

### Payment Mode Details

<details>
<summary>GET | /paymentMode | to fetch ALL paymentMode Details</summary>

#### Request:
###### URI: /paymentMode
###### HTTP Verb: GET
###### Body: None

#### Response:
##### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve all paymentMode details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested paymentMode details are not found

##### Response Body Type: JSON
Example Response Body:
```
[
  {
    "id": 99,
    "paymentMode": "CASH",
    "cardDetails":"",
    "isDebitCard":"false",
    "isCreditCard":"false",
    "accountDetails":"",
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  {
    "id": 99,
    "paymentMode": "CARD",
    "cardDetails":"4315***99",
    "isDebitCard":"false",
    "isCreditCard":"true",
    "accountDetails":"",
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  {
    "id": 99,
    "paymentMode": "UPI",
    "cardDetails":"",
    "isDebitCard":"false",
    "isCreditCard":"false",
    "accountDetails":"",
    "upiAddress":"*****@upi",
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  }
]
```
</details>


<details>
<summary>GET | /paymentMode/{id} | to fetch Expense Details</summary>

#### Request:
###### URI: /paymentMode/{id}
###### HTTP Verb: GET
###### Body: None

#### Response:
##### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve the paymentMode details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested paymentMode details are not found

##### Response Body Type: JSON
Example Response Body:
```
{
  "id": 12,
  "paymentMode": "CARD",
  "cardDetails":"4315***99",
  "isDebitCard":"true",
  "isCreditCard":"false",
  "accountDetails":"",
  "description":"sample test",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>GET | /paymentMode/search | to fetch Expense Details</summary>

#### Request:
###### URI: /paymentMode/search
###### HTTP Verb: GET
###### Request Body Type: JSON
Example Request Body:
```
{
"searchStr":"CASH"
}
```

#### Response:
##### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve the paymentMode details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested paymentMode details are not found

##### Response Body Type: JSON
Example Response Body:
```
[
{
  "id": 12,
  "paymentMode": "CARD",
  "cardDetails":"4315***99",
  "isDebitCard":"true",
  "isCreditCard":"false",
  "accountDetails":"",
  "description":"sample test",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
]
```
</details>


<details>
<summary>POST | /paymentMode | Create Expenses Details</summary>

#### Request:
###### URI: /paymentMode
###### HTTP Verb: POST
###### Body Type: JSON
Example Request Body:
```
{
  "paymentMode": "CARD",
  "cardDetails":"4315***99",
  "isDebitCard":"true",
  "isCreditCard":"false",
  "accountDetails":"",
  "description":"sample test"
}
```

#### Response:
##### HTTP Status:
1. **201** CREATED  - When the User is authenticated and authorized to create the paymentMode details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized

##### Body: None
</details>


<details>
<summary>PUT | /paymentMode/{id} | Create or update Expenses Details</summary>

#### Request:
###### URI: /paymentMode
###### HTTP Verb: POST
###### Body Type: JSON
Example Request Body:
```
{
  "id": 99,
  "amount": 123.45,
  "paidBy":"userId or alias",
  "paidTo":"receiverId or alias",
  "dateAndTime":"localDateTime",
  "description":"sample test",
  "currency": "INR",
  "paymentMode": "CASH",
  "category":"FOOD"
}
```

#### Response:
##### HTTP Status:
1. **201** OK - When the User is authenticated and authorized to create the paymentMode details
2. **204** NO CONTENT - When the User is authenticated and authorized to update the paymentMode details
3. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
4. **403** FORBIDDEN - When the user is authenticated but unauthorized
5. **404** NOT FOUND - When the user is authenticated and authorized but requested paymentMode details are not found

##### Body: None
</details>


<details>
<summary>PATCH | /paymentMode/{id} | update partial Expenses Details</summary>

#### Request:
###### URI: /paymentMode/{id}
###### HTTP Verb: PATCH
###### Request Body Type: JSON
Example Request Body:
```
{
  "amount": 123.45,
  "paymentMode": "CARD",
  "category":"FOOD"
}
```

#### Response:
##### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to update the paymentMode details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested paymentMode details are not found

##### Response Body Type: JSON
Example Response Body:
```
{
  "id": 99,
  "amount": 123.45,
  "paidBy":"userId or alias",
  "paidTo":"receiverId or alias",
  "dateAndTime":"localDateTime",
  "description":"sample test",
  "currency": "INR",
  "paymentMode": "CASH",
  "category":"FOOD",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>DELETE | /paymentMode/{id} | delete partial Expenses Details</summary>

#### Request:
###### URI: /paymentMode/{id}
###### HTTP Verb: DELETE
###### Body: None

#### Response:
##### HTTP Status:
1. **204** NO CONTENT - When the User is authenticated and authorized to delete the paymentMode details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested paymentMode details are not found

##### Body: None
</details>

### Currency Details

<details>
<summary>GET | /currency | to fetch ALL currency Details</summary>

#### Request:
###### URI: /currency
###### HTTP Verb: GET
###### Body: None

#### Response:
##### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve all currency details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested currency details are not found

##### Response Body Type: JSON
Example Response Body:
```
[
  {
    "id": 33,
    "currency": "INR",
    "exchangeRate":"",
    "defaultCurrency":"INR",
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  {
    "id": 44,
    "currency": "USD",
    "exchangeRate":"",
    "defaultCurrency":"INR",
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  {
    "id": 55,
    "currency": "EUR",
    "exchangeRate":"",
    "defaultCurrency":"INR",
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  }
]
```
</details>


<details>
<summary>GET | /currency/{id} | to fetch Expense Details</summary>

#### Request:
###### URI: /currency/{id}
###### HTTP Verb: GET
###### Body: None

#### Response:
##### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve the currency details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested currency details are not found

##### Response Body Type: JSON
Example Response Body:
```
{
  "id": 55,
  "currency": "EUR",
  "exchangeRate":"",
  "defaultCurrency":"INR",
  "description":"sample test",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>GET | /currency/search | to fetch Expense Details</summary>

#### Request:
###### URI: /currency/search
###### HTTP Verb: GET
###### Request Body Type: JSON
Example Request Body:
```
{
"searchStr":"INR"
}
```

#### Response:
##### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve the currency details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested currency details are not found

##### Response Body Type: JSON
Example Response Body:
```
{
  "id": 55,
  "currency": "EUR",
  "exchangeRate":"",
  "defaultCurrency":"INR",
  "description":"sample test",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>POST | /currency | Create Expenses Details</summary>

#### Request:
###### URI: /currency
###### HTTP Verb: POST
###### Body Type: JSON
Example Request Body:
```
{
  "currency": "EUR",
  "exchangeRate":"",
  "defaultCurrency":"INR",
  "description":"sample test"
}
```

#### Response:
##### HTTP Status:
1. **201** CREATED  - When the User is authenticated and authorized to create the currency details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized

##### Body: None
</details>


<details>
<summary>PUT | /currency/{id} | Create or update Expenses Details</summary>

#### Request:
###### URI: /currency
###### HTTP Verb: POST
###### Body Type: JSON
Example Request Body:
```
{
  "id": 55,
  "currency": "EUR",
  "exchangeRate":"",
  "defaultCurrency":"INR",
  "description":"sample test"
}
```

#### Response:
##### HTTP Status:
1. **201** OK - When the User is authenticated and authorized to create the currency details
2. **204** NO CONTENT - When the User is authenticated and authorized to update the currency details
3. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
4. **403** FORBIDDEN - When the user is authenticated but unauthorized
5. **404** NOT FOUND - When the user is authenticated and authorized but requested currency details are not found

##### Body: None
</details>


<details>
<summary>PATCH | /currency/{id} | update partial Expenses Details</summary>

#### Request:
###### URI: /currency/{id}
###### HTTP Verb: PATCH
###### Request Body Type: JSON
Example Request Body:
```
{
  "currency": "EUR",
  "exchangeRate":"",
  "description":"sample test"
}
```

#### Response:
##### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to update the currency details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested currency details are not found

##### Response Body Type: JSON
Example Response Body:
```
{
  "id": 55,
  "currency": "EUR",
  "exchangeRate":"",
  "defaultCurrency":"INR",
  "description":"sample test",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>DELETE | /currency/{id} | delete partial Expenses Details</summary>

#### Request:
###### URI: /currency/{id}
###### HTTP Verb: DELETE
###### Body: None

#### Response:
##### HTTP Status:
1. **204** NO CONTENT - When the User is authenticated and authorized to delete the currency details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested currency details are not found

##### Body: None
</details>

### Expenses Category Details

<details>
<summary>GET | /expensesCategory | to fetch ALL expensesCategory Details</summary>

#### Request:
###### URI: /expensesCategory
###### HTTP Verb: GET
###### Body: None

#### Response:
##### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve all expensesCategory details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested expensesCategory details are not found

##### Response Body Type: JSON
Example Response Body:
```
[
  {
    "id": 33,
    "expensesCategory": "FOOD",
    "alias":["breakfast","lunch","dinner"],
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  {
    "id": 44,
    "expensesCategory": "TRAVEL",
    "alias":["train","flight","bus","cab"],
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  {
    "id": 55,
    "expensesCategory": "LOAN",
    "alias":["emi","home loan","car loan","gold loan","personal loan","credit card emi"],
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  }
]
```
</details>


<details>
<summary>GET | /expensesCategory/{id} | to fetch Expense Details</summary>

#### Request:
###### URI: /expensesCategory/{id}
###### HTTP Verb: GET
###### Body: None

#### Response:
##### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve the expensesCategory details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested expensesCategory details are not found

##### Response Body Type: JSON
Example Response Body:
```
{
  "id": 55,
  "expensesCategory": "EDUCATION",
  "alias":["school fee", "tuition fee","online courses","udemy","certification"],
  "description":"sample test",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>GET | /expensesCategory/search | to fetch Expense Details</summary>

#### Request:
###### URI: /expensesCategory/search
###### HTTP Verb: GET
###### Request Body Type: JSON
Example Request Body:
```
{
"searchStr":"certification"
}
```
#### Response:
##### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve the expensesCategory details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested expensesCategory details are not found

##### Response Body Type: JSON
Example Response Body:
```
[
{
  "id": 55,
  "expensesCategory": "EDUCATION",
  "alias":["school fee", "tuition fee","online courses","udemy","certification"],
  "description":"sample test",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
]
```
</details>


<details>
<summary>POST | /expensesCategory | Create Expenses Details</summary>

#### Request:
###### URI: /expensesCategory
###### HTTP Verb: POST
###### Body Type: JSON
Example Request Body:
```
{
  "expensesCategory": "FOOD",
  "description":"sample test"
}
```

#### Response:
##### HTTP Status:
1. **201** CREATED  - When the User is authenticated and authorized to create the expensesCategory details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized

##### Body: None
</details>


<details>
<summary>PUT | /expensesCategory/{id} | Create or update Expenses Details</summary>

#### Request:
###### URI: /expensesCategory
###### HTTP Verb: POST
###### Body Type: JSON
Example Request Body:
```
{
  "id": 55,
  "expensesCategory": "FUEL",
  "alias":["petrol","diesel","cng","charging"],
  "description":"sample test"
}
```

#### Response:
##### HTTP Status:
1. **201** OK - When the User is authenticated and authorized to create the expensesCategory details
2. **204** NO CONTENT - When the User is authenticated and authorized to update the expensesCategory details
3. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
4. **403** FORBIDDEN - When the user is authenticated but unauthorized
5. **404** NOT FOUND - When the user is authenticated and authorized but requested expensesCategory details are not found

##### Body: None
</details>


<details>
<summary>PATCH | /expensesCategory/{id} | update partial Expenses Details</summary>

#### Request:
###### URI: /expensesCategory/{id}
###### HTTP Verb: PATCH
###### Request Body Type: JSON
Example Request Body:
```
{
  "expensesCategory": "HOUSEHOLD",
  "alias":["house repairs","flooring","cleaning"],
  "description":"sample test"
}
```

#### Response:
##### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to update the expensesCategory details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested expensesCategory details are not found

##### Response Body Type: JSON
Example Response Body:
```
{
  "id": 55,
  "expensesCategory": "HOUSEHOLD",
  "description":"sample test",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>PATCH | /expensesCategory/{id}/add/alias | update partial Expenses Details</summary>

#### Request:
###### URI: /expensesCategory/{id}/add/alias
###### HTTP Verb: PATCH
###### Request Body Type: JSON
Example Request Body:
```
{
  "alias":["water","electricity bill","plumbing repairs"]
}
```

#### Response:
##### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to update the expensesCategory details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested expensesCategory details are not found

##### Response Body Type: JSON
Example Response Body:
```
{
  "id": 55,
  "expensesCategory": "HOUSEHOLD",
  "alias":["house repairs","flooring","cleaning","water","electricity bill","plumbing repairs"],
  "description":"sample test",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>PATCH | /expensesCategory/{id}/remove/alias | update partial Expenses Details</summary>

#### Request:
###### URI: /expensesCategory/{id}/remove/alias
###### HTTP Verb: PATCH
###### Request Body Type: JSON
Example Request Body:
```
{
  "alias":["water"]
}
```

#### Response:
##### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to update the expensesCategory details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested expensesCategory details are not found

##### Response Body Type: JSON
Example Response Body:
```
{
  "id": 55,
  "expensesCategory": "HOUSEHOLD",
  "alias":["house repairs","flooring","cleaning","electricity bill","plumbing repairs"],
  "description":"sample test",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>DELETE | /expensesCategory/{id} | delete partial Expenses Details</summary>

#### Request:
###### URI: /expensesCategory/{id}
###### HTTP Verb: DELETE
###### Body: None

#### Response:
##### HTTP Status:
1. **204** NO CONTENT - When the User is authenticated and authorized to delete the expensesCategory details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested expensesCategory details are not found

##### Body: None
</details>

### User Details

<details>
<summary>GET | /User | to fetch ALL User Details</summary>

#### Request:
###### URI: /User
###### HTTP Verb: GET
###### Body: None

#### Response:
##### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve all User details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested User details are not found

##### Response Body Type: JSON
Example Response Body:
```
[
  {
    "id": 33,
    "firstName": "ravindra",
    "lastName":"reddy",
    "emailId":"ravindra@email.com",
    "mobileNo":"0987654321",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  {
    "id": 44,
    "firstName": "ravindra",
    "lastName":"reddy",
    "emailId":"ravindra@email.com",
    "mobileNo":"0987654321",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  {
    "id": 55,
    "firstName": "ambati",
    "lastName":"reddy",
    "emailId":"ravindra@email",
    "mobileNo":"0987654321",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  }
]
```
</details>


<details>
<summary>GET | /User/{id} | to fetch Expense Details</summary>

#### Request:
###### URI: /User/{id}
###### HTTP Verb: GET
###### Body: None

#### Response:
##### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve the User details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested User details are not found

##### Response Body Type: JSON
Example Response Body:
```
  {
    "id": 55,
    "firstName": "ambati",
    "lastName":"reddy",
    "emailId":"ravindra@email",
    "mobileNo":"0987654321",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  }
```
</details>


<details>
<summary>GET | /User/search | to fetch Expense Details</summary>

#### Request:
###### URI: /User/search
###### HTTP Verb: GET
###### Request Body Type: JSON
Example Request Body:
```
{
"searchStr":"ravindra"
}
```

#### Response:
##### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve the User details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested User details are not found

##### Response Body Type: JSON
Example Response Body:
```
  {
    "id": 55,
    "firstName": "ambati",
    "lastName":"reddy",
    "emailId":"ravindra@email",
    "mobileNo":"0987654321",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  }
```
</details>


<details>
<summary>POST | /User | Create Expenses Details</summary>

#### Request:
###### URI: /User
###### HTTP Verb: POST
###### Body Type: JSON
Example Request Body:
```
{
  "firstName": "ravindra",
  "lastName":"ambati",
  "emailId":"ravindra@email",
  "mobileNo":"0987654321"
}
```

#### Response:
##### HTTP Status:
1. **201** CREATED  - When the User is authenticated and authorized to create the User details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized

##### Body: None
</details>


<details>
<summary>PUT | /User/{id} | Create or update Expenses Details</summary>

#### Request:
###### URI: /User
###### HTTP Verb: POST
###### Body Type: JSON
Example Request Body:
```
{
  "id": 55,
  "firstName": "ravindra",
  "lastName":"",
  "emailId":"ravindra@email",
  "mobileNo":"0987654321"
}
```

#### Response:
##### HTTP Status:
1. **201** OK - When the User is authenticated and authorized to create the User details
2. **204** NO CONTENT - When the User is authenticated and authorized to update the User details
3. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
4. **403** FORBIDDEN - When the user is authenticated but unauthorized
5. **404** NOT FOUND - When the user is authenticated and authorized but requested User details are not found

##### Body: None
</details>


<details>
<summary>PATCH | /User/{id} | update partial Expenses Details</summary>

#### Request:
###### URI: /User/{id}
###### HTTP Verb: PATCH
###### Request Body Type: JSON
Example Request Body:
```
{
  "firstName": "ravindra",
  "lastName":"ambati",
  "mobileNo":"0987654321"
}
```

#### Response:
##### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to update the User details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested User details are not found

##### Response Body Type: JSON
Example Response Body:
```
{
  "id": 55,
  "firstName": "ravindra",
  "lastName":"ambati",
  "emailId":"ravindra@email",
  "mobileNo":"0987654321",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>DELETE | /User/{id} | delete partial Expenses Details</summary>

#### Request:
###### URI: /User/{id}
###### HTTP Verb: DELETE
###### Body: None

#### Response:
##### HTTP Status:
1. **204** NO CONTENT - When the User is authenticated and authorized to delete the User details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested User details are not found

##### Body: None
</details>

## Contact Information

If you have any questions, feel free to reach out on:

- Email: [ravindra.reddy.ambati@outlook.in](mailto:ravindra.reddy.ambati@outlook.in)

## Contributing


## License

Information about the license.

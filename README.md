
# 📜 Wishlist API  [![CI](https://github.com/m1theus/wishlist-api/actions/workflows/ci.yml/badge.svg)](https://github.com/m1theus/wishlist-api/actions/workflows/ci.yml) ![Coverage](.github/badges/jacoco.svg) ![Branches](.github/badges/branches.svg)

A microservice that allows users to manage wishlists and their products.

Built with:
-  Java 21
- Spring Boot 3.5
-  MongoDB
-  Clean Architecture
- Tested with JUnit 5 + Cucumber BDD

---

##  Features

- ✅ Create a wishlist with products.
- ✅ Add a product to a wishlist (max 20 products).
- ✅ Remove a product from a wishlist.
- ✅ List products in a wishlist.
- ✅ Check if a product exists in any wishlist.
- ✅ Fully tested with behavior-driven development (BDD) using Cucumber.

---

## 🗂️ Project Structure (Clean Architecture)

```
src
├── main
│   ├── java
│   │   └── dev.mmartins.wishlistapi
│   │       ├── domain          → Entities & Repositories (Enterprise Business Rules)
│   │       ├── application     → Use Cases (Application Business Rules)
│   │       ├── infrastructure  → MongoDB Adapters
│   │       └── entrypoint      → REST Controllers
├── test
│   ├── java
│   │   └── dev.mmartins.wishlistapi.behavior
│   │       ├── steps           → Cucumber Step Definitions
│   │       └── CucumberSpringConfiguration.java
│   └── resources
│       └── dev/mmartins/wishlistapi/behavior/features
│           └── *.feature       → BDD Feature Files
```

---

## Tech Stack

| Tool            | Version   | Description                     |
|-----------------|-----------|---------------------------------|
| Java            | 21        | Runtime                         |
| Spring Boot     | 3.5.0     | Framework                       |
| MongoDB         | 5+        | Database                        |
| Cucumber        | 7.22.0    | Behavior-Driven Testing         |
| JUnit           | 5         | Test Framework                  |
| Springdoc       | 2.3.0     | OpenAPI / Swagger UI            |
| Gradle          | Latest    | Build Tool                      |

---

## Running the App

### Prerequisites
- Java 21
- MongoDB running on `localhost:27017`
- Gradle installed (or use `./gradlew`)

###  Run the app:
Note: You will need to have a MongoDB, where you can follow this steps:

```bash
docker run -d -p 27017:27017 --name mongo \
-e MONGO_INITDB_ROOT_USERNAME=root \
-e MONGO_INITDB_ROOT_PASSWORD=example \
mongo
```

or just run the docker-compose file with `./run-compose.sh`
```bash
./run-compose.sh
```
---

Run the app:
```bash
./gradlew bootRun
```

---

## 🔗 API Documentation (Swagger)

Once the app is running:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Running Tests

###  Run unit, integration, and BDD tests:

```bash
./gradlew clean test
```

###  Run only Cucumber BDD tests:

```bash
./gradlew test --tests "*CucumberConfigTests"
```

---

## BDD with Cucumber

### Feature files located at:

```
src/test/resources/dev/mmartins/wishlistapi/behavior/features/
```

### Run all features:

```properties
# junit-platform.properties
cucumber.features=dev/mmartins/wishlistapi/behavior/features
cucumber.glue=dev.mmartins.wishlistapi.behavior.steps
cucumber.plugin=pretty, summary
```

---

## API Endpoints Example

| Method | Endpoint                               | Description                 |
|--------|----------------------------------------|-----------------------------|
| POST   | `/wishlists`                           | Create a wishlist           |
| POST   | `/wishlists/{id}/products`             | Add a product to wishlist   |
| DELETE | `/wishlists/{id}/products/{product}`   | Remove product from wishlist |
| GET    | `/wishlists/{id}/products`             | List products in a wishlist |
| GET    | `/wishlists/{id}/products/{productId}` | Check if product is in wishlists |

---


##  Contributions

Pull requests are welcome. 

---

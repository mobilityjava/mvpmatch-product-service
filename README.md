# Product Service
Microservice that contains all aspects of the core domain "Product".

## Local startup
To start application run.
```
./mvnw -pl service-application spring-boot:run
```
The service will be accessible on http://localhost:8080

## Testing
### Unit Tests
All unit tests are bound to "test" goal. Just run:
```
./mvnw test
```
# Integration Tests
All integration tests are bound to "integration-test" goal. Just run:
```
./mvnw integration-test
```

### Build docker image for the project with maven
```
mvn install -DskipTests
```

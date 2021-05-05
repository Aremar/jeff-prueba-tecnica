# jeff-prueba-tecnica

Application image is uploaded to [DockerHub](https://hub.docker.com/r/aremar/jeff-techtest)

## RUN TO TEST

---
Launching `docker-compose` up will run the application and MySQL DB for testing purposes.\
Note: the compose has a healthcheck on mysql DB, so maybe takes a few seconds to fully execute.


## API

---
The **OpenAPI** contract can be found in *api-docs.json*\
Also, when the application is running, the OpenAPI contract is exposed in http://localhost:8081/recommendations/v3/api-docs \
The **SwaggerUI** is accessible via http://localhost:8081/recommendations/swagger-ui.html \
The **SwaggerUI** is enabled to call and test the API.

Exposure of those endpoints will be disabled in production/live environments.

## What does the application do?

---
The application will provide a list of recommended products tailored to the customer.\
These recommendations are based on a certain *threshold*. This *threshold* is preconfigures in the application, but can be overridden by an optional query parameter called **minScore**.\
If a product has the same or more score than that threshold, it will be recommended.

The application also provides a marketing-oriented optional parameter, **extraHighMarginProducts**, that will pick up the provided number of extra products to take in count for the recommendations.\
This extra products are selected by a relation of margin-and-score by product.
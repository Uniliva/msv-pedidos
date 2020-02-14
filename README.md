## MSV-PEDIDOS

Micro-serviço de gerenciamento de pedidos.

---

[![Build Status](https://travis-ci.com/Uniliva/msv-pedidos.svg?branch=master)](https://travis-ci.com/Uniliva/msv-pedidos)
[![codecov](https://codecov.io/gh/Uniliva/msv-pedidos/branch/master/graph/badge.svg)](https://codecov.io/gh/Uniliva/msv-pedidos)

----
<br>

### Tecnologia

Foi desenvolvido usando:
- Java 8
- Rest
- Spring Boot
- Spring Data
- Swagger
- H2 

---
<br>

### Testasndo

#### Rodar localmente

1. Baixe o projeto 
2. Acesse a pasta e execute

```shellscript
mvn spring-boot:run 
```
3. Acesse 

```shellscript
#base
http://localhost:8080

#swagger
http://localhost:8080/swagger-ui.html

#H2-console
http://localhost:8080/h2-console
```


##### Testando no Heroku 

1. Acesse:


```shellscript
#base
https://msv-pedidos.herokuapp.com/

#swagger
https://msv-pedidos.herokuapp.com/swagger-ui.html

#H2-console
https://msv-pedidos.herokuapp.com/h2-console
```

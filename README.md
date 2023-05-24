# Consideraciones que he seguido en el ejercicio:
##### Realizada con spring boot v 3.0.0, java target version 17

## Tests:
### Como correr los Tests.
```shell
./gradlew test
```

## Como ejecutar la aplicación:
- inicializamos la aplicación, 
```shell
./gradlew bootRun
```

### Request de ejemplo a la aplicación
- request de ejemplo:
```shell
curl --location --request GET 'http://localhost:8080/priceAggregate?application_date=2020-06-14-10.00.00&product_id=35455&brand_id=1'
```

### Documentacion de la api autogenerada con springdoc-openapi-starter-webmvc-ui
en el directorio ci/api-doc/api-docs.yaml o accesible via http://localhost:8080/v3/api-docs.yaml

---

Kata de Arquitectura Hexagonal
En nuestra aplicación disponemos de Productos, Precios y Descuentos.

El precio de un producto es válido durante un rango de fechas, en ese rango pueden existir diferentes precios para el mismo producto, pero
será el que tenga la prioridad más alta el que usaremos.
El rango de fechas debe ser válido, la fecha de inicio no puede ser mayor que la de final.
Si la prioridad del precio en ese momento es igual o menor a 10, el amount debe tener más de 2 dígitos.

El descuento de productos es opcional, es posible que algunos productos no tengan descuentos a aplicar. El descuento nunca será mayor del
50%.

El caso de uso que vamos a implementar es la lectura del precio de un producto con un posible descuento aplicado.

Input: Como entrada le pasaremos el producto del cual queremos el precio y el brand asociado al mismo.

Output: El resultado es el precio del producto para ese brand con el descuento aplicado (si es que lo hubiera).

Notas

El proyecto en un momento determinado del tiempo necesita, alternativamente, de dos tipos de drivers de persistencia distintos: Spring Data
o directamente Jdbc Template.

A pesar de ser un caso de uso de lectura, vamos a reutilizar (por conveniencia) el modelo de dominio usado en escritura.

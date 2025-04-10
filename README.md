# Prueba Técnica BCNC - 2025

**Autor:** Wilow Maui García

Este proyecto representa una prueba técnica desarrollada en 2025 para BCNC, diseñada para demostrar habilidades en desarrollo de aplicaciones Java utilizando Spring Boot y una arquitectura hexagonal modular.

## Instrucciones de Ejecución

Para iniciar la aplicación, siga estos pasos:

1.  **Construcción del Proyecto:**
    * Navegue al directorio raíz del proyecto (`prueba_tecnica`).
    * Ejecute el comando `mvn clean install` para compilar y empaquetar todos los módulos.

2.  **Ejecución de la Aplicación:**
    * Navegue al directorio del módulo de infraestructura (`infrastructure`).
    * Ejecute el comando `mvn spring-boot:run` para iniciar la aplicación Spring Boot.

## Instrucciones para Ejecutar las Pruebas

Para ejecutar las pruebas unitarias e integrales del proyecto, siga estos pasos:

1.  **Ejecución de las pruebas:**
    * Navegue al directorio raíz del proyecto (`prueba_tecnica`).
    * Ejecute el comando `mvn test` para compilar y empaquetar todos los módulos y  ejecutar todas las pruebas unitarias e integrales definidas en el.
    * Maven mostrará los resultados de las pruebas en la terminal, indicando cuántas pruebas se ejecutaron, cuántas fallaron y cuántas se omitieron.

## Consideraciones de Implementación

* **Moneda en la Respuesta:**
    * El enunciado solicita la devolución del "precio final". Se ha considerado que la moneda es un componente esencial de este precio, por lo que la respuesta incluye el tipo de moneda.

* **Prioridad y Fecha de Vigencia:**
    * En caso de encontrar múltiples precios vigentes con la misma prioridad, se ha implementado la lógica para seleccionar la tarifa más reciente.
    * Se permite realizar consultas sin especificar una fecha. En tales casos, se retornan los precios que estén en el periodo de vigencia actual.

* **Optimización con Caché:**
    * Se ha implementado un sistema de caché para optimizar las consultas, especialmente aquellas que buscan el precio actual (sin fecha especificada), que se considera el caso de uso más común.
    * En un entorno de producción, sería necesario implementar la invalidación o actualización de la caché cuando se añaden o modifican precios, para mantener la coherencia de los datos. Sin embargo, para los propósitos de esta prueba, se ha implementado solo la funcionalidad requerida.

* **Persistencia de Datos con H2:**
    * El uso de H2 en modo "in-memory" (en memoria) implica que los datos se almacenan en la memoria RAM y se pierden cada vez que se reinicia la aplicación. Para evitar la pérdida de datos y simular un entorno de base de datos más realista, se ha optado por el modo "file", que permite que los datos se guarden en un archivo en el disco.
## Arquitectura

Se ha adoptado una arquitectura hexagonal modular, dividiendo la aplicación en tres módulos Maven (haciendo así una separación lógica, no solo conceptual):

1.  **Dominio (`domain`):**
    * Contiene las entidades de dominio y las interfaces de los servicios (puertos de salida).
    * Define qué operaciones se pueden realizar con los objetos de dominio.

2.  **Aplicación (`application`):**
    * Implementa los servicios definidos en el dominio .
    * Contiene la lógica de negocio principal de la aplicación.
    * Define el comportamiento de los puertos de entrada (repositorios).

3.  **Infraestructura (`infrastructure`):**
    * Proporciona la implementación concreta de los puertos de salida (repositorios).
    * Contiene los controladores de la API, la configuración de la aplicación, los DTOs, los mappers y el punto de entrada de la aplicación.

## Tecnologías y Herramientas

* **MapStruct:** Generación automática de mappers para la conversión entre DTOs y entidades de dominio.
* **Spring Cache (@Cacheable):** Implementación de caché para optimizar el rendimiento de las consultas.
* **Lombok:** Generación automática de código boilerplate (getters, setters, constructores).
* **ControllerAdvice:** Manejo centralizado de excepciones.
* **SLF4J:** Logging de la aplicación.
* **Flyway:** Migraciones de base de datos.
* **Swagger:** Documentación de la API.
* **JPQL (Java Persistence Query Language):** Lenguaje de consultas para interactuar con la base de datos de manera orientada a objetos.


## Patrones de Diseño y Buenas Prácticas

* **Patrones de Spring:** Uso de adaptadores y singletons para los beans de Spring.
* **Interfaces y Constructores:** Uso de interfaces para definir contratos y constructores para la inyección de dependencias y creación de objetos.
* **Modularidad:** División de la aplicación en módulos para mejorar la mantenibilidad y la escalabilidad.
* **Convenciones de Nombres:** Uso de nombres claros y descriptivos para clases, métodos y variables.
* **Documentación:** Uso de Swagger para la documentación de la API y comentarios en el código.
* **Manejo de Excepciones:** Uso de ControllerAdvice para el manejo centralizado de excepciones.
# 🐱‍🏍 PokéAPI REST Consumer

Este proyecto es una API REST desarrollada con **Spring Boot 3.4.4** y **Java 17** que consume la [PokéAPI](https://pokeapi.co/). Permite obtener información básica de los Pokémon como imagen, nombre, ID, tipo y habilidades. Además, implementa cacheo inteligente para mejorar el rendimiento en las consultas repetidas.

---
## Requisitos Previos

- **Java 17**
- **Spring Boot 3.3.3**
- **Maven**
- **Docker (opcional para el despliegue en contenedor)**

## Arquitectura y Stack Tecnológico

### Tecnologías Utilizadas:

![Java](https://img.shields.io/badge/Java-17-brightgreen.svg?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen.svg?style=for-the-badge&logo=spring-boot)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-19+-brightgreen.svg?style=for-the-badge&logo=docker)

---

## Endpoints
- 🔹 **Obtener Pokémon paginados**
  - **URL:** `/pokemon?offset=0&limit=10`
  - **Método:** `GET`
  - **Descripción:** Obtiene una lista de Pokémon paginados.

- 🔹 **Obtener Pokémon por Id o Nombre**
  - **URL:** `/pokemon/{idOrName}`
  - **Método:** `GET`
  - **Descripción:** Obtiene información detallada de un Pokémon por su ID o nombre.
---
## 🔍 Spring Boot Actuator Endpoints

- 🔹 **Health Check**
  - **URL:** `/actuator/health`
  - **Método:** `GET`
  - **Descripción:** Verifica el estado de salud de la aplicación

- 🔹 **Métricas**
  - **URL:** `/actuator/metrics`
  - **Método:** `GET`
  - **Descripción:** Proporciona métricas detalladas de la aplicación

- 🔹 **Info**
  - **URL:** `/actuator/info`
  - **Método:** `GET`
  - **Descripción:** Muestra información general de la aplicación

Los endpoints de Actuator proporcionan:
- Monitoreo en tiempo real
- Métricas de rendimiento
- Estado de salud del sistema
- Información sobre caché
- Detalles de configuración

Para acceder a todos los endpoints disponibles, visita: `/actuator`

---

## 🚀 Características

- 🔁 **Cache Inteligente**:
    - Consulta primero en caché.
    - Si no encuentra, realiza la petición a PokéAPI y almacena el resultado.
    - Si se cambia el `limit` en la paginación, la caché se limpia automáticamente.

- 📄 **Paginación configurable**:
    - Valor por defecto: `10`.
    - Se puede personalizar el `limit` de Pokémon por página.

- 🔍 **Búsqueda por ID o Nombre**:
    - El endpoint valida el dato recibido (si es un número o string).
    - Realiza la búsqueda correspondiente en PokéAPI.

- 📦 **Documentación Swagger** disponible para testear fácilmente los endpoints.

---

## 📸 Ejemplo de respuesta

```json
[
  {
    "id": 1,
    "name": "bulbasaur",
    "image": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
    "weight": 69,
    "types": ["grass", "poison"],
    "abilities": ["overgrow", "chlorophyll"]
  }
]

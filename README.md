# **API Foro en Back-end**

Una API back-end usando Spring Boot para administrar tópicos en un foro. El projecto hecho para el desafío de Alura LATAM intenta demostrar habilidades escenciales del back-end, incluyendo creación de API, autenticación, e integración con base de datos en MySQL.

---

## **Funciones**
- API REST con endpoints para crear, leer, actualizar y borrar tópicos del foro. Mas acceso y registro para nuevos usuarios. 
- Autenticación de usuarios usando tokens JWT.
- Configuración basada en variables de entorno.
- Integración con base de datos MySQL usando JPA e Hibernate.
- Pagination for large datasets.
- Habilidad de paginación para grandes colecciones de datos.

---

# **Instrucciones de Configuración**
## Requerimientos
- Java 17 o mayor
- Maven
- Base de datos en MySQL
- Insomnia API o similar.

## Variables de Entorno
Establezca las siguientes variables de entorno en su sistema antes de ejecutar la aplicación:
|     Variables       |     Descripción                               |     Ejemplo                          |
|---------------------|-----------------------------------------------|--------------------------------------|
| `DB_HOST`           | URL para la base de datos                     |`jdbc:mysql://localhost:3306/foro`    |
| `DB_USERNAME`       | Nombre de usuario de la base de datos         |`root`                                |
| `DB_PASSWORD`       | Contraseña de la base de datos                |`Contrasena123456`                    |
| `SECRETO_TOKEN`     | Secreto usado en la encriptación del Token JWT|`token-secreto`                       |

## Configurando la Base de Datos
1. Cree una base de datos en MySQL
2. Ejecute las migraciones en Flyway de forma automática ejecutando la aplicación, o inicialice la base de datos de forma manual de ser necesario.

---

# **Ejecutando la Aplicación**

1. Copie este repositorio:
```bash
git clone https://github.com/Ernest-94/alura-foro-hub.git
cd alura-foro-hub
```
2. Compile el projecto usando Maven
```bash
mvn clean install
```
3. Ejecute la aplicación
```bash
mvn spring-boot:run
```

---

# **Endpoints**
## Registro y Acceso de Usuarios
| Método                 | Endpoint                     | Descripción                                                                     |
|------------------------|------------------------------|---------------------------------------------------------------------------------|
| POST                   | `/registrar`                 |Registra un nuevo usuario colocando un nombre, email y contraseña                |
| POST                   | `/login`                     |Accede y crea un token de autenticación usando un email y contraseña registrados |

## Administración de Tópicos
| Método                 | Endpoint                     | Descripción                                                                               |
|------------------------|------------------------------|-------------------------------------------------------------------------------------------|
| POST                   | `topicos`                    | Crea un nuevo tópico                                                                      |
| GET                    | `topicos`                    | Muestra una lista paginada de todos los tópicos                                           |
| GET                    | `topicos/{id}`               | Muestra detalles de un tópico en específico                                               |
| PUT                    | `topicos/{id}`               | Actualiza la información de un tópico existente seleccionado (permiso solo para el autor) |
| DELETE                 | `topicos/{id}`               | Borra el tópico seleccionado (permiso solo para el autor)                                 |
## Autenticación
- Un token JWT es necesario para interactuar con los endpoints de tópicos.

---

## **Tecnologías Usadas**
- **Java 17**
- **Spring Boot**
- **Maven**
- **MySQL**
- **JWT (JSON Web Token)**
- **Flyway**
- **Insomnia**

---

## Desarrollado por
**Ernesto Alejandro Acevedo Jiménez** para la formación de Java y Spring Framework de **Oracle ONE** y **Alura LATAM**
- **GitHub:** https://github.com/Ernest-94

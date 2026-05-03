# FichajeApp

Aplicación de fichaje para empresas que permite controlar las jornadas laborales de los trabajadores, gestionar solicitudes de vacaciones y llevar el seguimiento de los proyectos activos junto con el personal asignado a cada uno.

---

## ¿Por qué esta aplicación?

Desde hace unos años, se está intentando prohibir que las empresas hagan que los trabajadores fichen en papel para evitar la manipulación de las jornadas laborales, y así tener un seguimiento más óptimo del cumplimiento de las jornadas sin sobrepasar los límites permitidos de horas extras, además de asegurar que en caso de hacerse horas extras, éstas estén registradas para su posterior pago.

Esta aplicación ayuda a las empresas a cumplir esta normativa al registrar las jornadas, que posteriormente no podrán editarse.

Asimismo, tener localizado y registrado en un mismo espacio las vacaciones de los trabajadores permite mantener mejor localizado todo y poder consultarlo de una forma sencilla.

Además, para las empresas a menudo es un problema mantener un control sobre qué trabajadores están asignados a qué proyectos, por lo que esta aplicación facilita tener toda esa información en un mismo lugar.

---

## Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| JavaFX + Scene Builder | Interfaz gráfica intuitiva |
| MySQL + XAMPP + phpMyAdmin | Creación y gestión de la base de datos |
| JDBC (via Maven) | Conexión entre la aplicación y la base de datos |
| Draw.io | Diagramas en la fase de planteamiento |
| IntelliJ IDEA | Entorno de desarrollo |

---

## Requisitos previos

Para inicializar el programa es necesario tener los siguientes elementos:

| # | Componente | Descripción |
|---|---|---|
| 1 | JDK 21 | Base de ejecución de la aplicación JavaFX |
| 2 | JavaFX SDK 21 | Librería de interfaz gráfica |
| 3 | XAMPP (Apache + MySQL) | Servidor local con MySQL y phpMyAdmin |
| 4 | mysql-connector-j (JDBC) | Driver de conexión Java ↔ MySQL |
| 5 | IntelliJ IDEA | IDE de desarrollo (solo equipo desarrollador) |

---

## Instalación y puesta en marcha

### 1. Clonar el repositorio

Puedes utilizar tanto GitHub Desktop como cualquier otro método para clonar el repositorio.

### 2. Importar el esquema de base de datos

1. Abrir phpMyAdmin en [http://localhost/phpmyadmin](http://localhost/phpmyadmin).
2. Crear una nueva base de datos llamada `fichajeapp` con cotejamiento `utf8mb4_general_ci`.
3. Ir a la pestaña **«Importar»** y cargar el archivo `.sql` del proyecto.
4. Verificar que se han creado las tablas: `usuarios`, `fichaje`, `vacaciones`, `proyecto`, `proyecto_usuarios`.

### 3. Configurar la cadena de conexión JDBC

Localizar en el código fuente la clase de conexión (`DBConnection.java`) y ajustar según tus datos:

```java
String URL  = "jdbc:mysql://localhost:3306/fichajeapp";
String USER = "";
String PASS = "";
```

### 4. Ejecutar la aplicación desde IntelliJ

1. Abrir el proyecto: `File → Open` → seleccionar la carpeta del proyecto.
2. Esperar a que IntelliJ indexe el proyecto y descargue dependencias (sincronizar el proyecto).
3. Ejecutar con **▶ Run**, ya que el proyecto está preparado para abrir la aplicación desde cualquier pestaña.
4. Comprobar en la consola que no aparecen errores de conexión JDBC.

---

## Estructura del repositorio

El repositorio contiene una carpeta general llamada `Intermodular` para distinguir el proyecto. Dentro, hay una carpeta por cada asignatura donde se ha desarrollado la parte correspondiente del Intermodular, con los requerimientos que cada profesor ha solicitado.

La aplicación desarrollada se encuentra en la carpeta de **programación**, organizada en subcarpetas siguiendo el patrón **MVC (Modelo Vista Controlador)** para mantener el código limpio y escalable.

```
Intermodular/
└── programacion/
    ├── modelo/
    ├── vista/
    └── controlador/
```

---

## Estado actual del proyecto

Esta es la primera **Beta** de la aplicación. La funcionalidad disponible actualmente incluye:

- ✅ Crear, modificar y consultar usuarios dados de alta
- ✅ Registrar fichajes de entrada y salida

En futuras versiones se pretende incorporar todas las funcionalidades mencionadas: gestión completa de vacaciones, control de proyectos y asignación de personal.

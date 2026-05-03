# 🖥️ Requisitos del Sistema e Instalación del Entorno

> Aplicación de Fichajes — JavaFX + JDBC + MySQL (XAMPP)

---

## 1. Tipo de Sistema donde se Ejecuta

La aplicación se despliega en dos tipos de equipos con roles diferenciados:

| Rol | Tipo de equipo |
|---|---|
| Usuario final | PC de usuario (escritorio o portátil corporativo) |
| Base de datos | Servidor local en red de área local (LAN corporativa) |

### Justificación

- **PC de usuario** — La interfaz está desarrollada con JavaFX, que genera ventanas de escritorio nativas. Cada empleado ejecuta el cliente desde su propio equipo corporativo, permitiendo el registro de entrada/salida de forma individual sin depender de un navegador.

- **Servidor local (LAN)** — La base de datos MySQL, gestionada mediante XAMPP, reside en una máquina centralizada dentro de la red de la empresa. Todos los clientes JavaFX conectan a ese servidor mediante JDBC. 

- No se plantea máquina virtual ni equipo en dedicado porque el volumen de datos y usuarios es reducido, la conectividad es local y el mantenimiento lo realiza el mismo equipo de desarrollo.

---

## 2. Requisitos de Hardware

Se distinguen los requisitos del **equipo de usuario** (cliente JavaFX) y los del **servidor de base de datos** (XAMPP + MySQL).

| Componente | Mínimo | Recomendado (usuario) | Recomendado (servidor) |
|---|---|---|---|
| CPU | Dual-core 1,8 GHz | Quad-core 2,5 GHz | Quad-core 2,5 GHz |
| RAM | 4 GB | 8 GB | 8 GB |
| Almacenamiento | 512 MB libres | 1 GB libres | 10 GB libres (BD + logs) |
| Periféricos | Teclado y ratón | Teclado y ratón | — |

### Notas

- La aplicación JavaFX es liviana: su mayor consumo es la carga inicial de la JVM y la renderización de la interfaz.
- El servidor MySQL no requiere pantalla en producción; puede administrarse remotamente vía phpMyAdmin desde cualquier PC de la red.
- El almacenamiento del servidor depende del volumen de fichajes: una empresa de 50 empleados con 3 años de histórico no supera los 500 MB de datos.


---

## 3. Sistema Operativo Recomendado

### Equipos de usuario (cliente JavaFX)

| | |
|---|---|
| **SO principal** | Windows 11 Pro / Home (64 bits) |
| **Versión mínima** | Windows 10 (versión 21H2 o superior) |
| **Alternativa Linux** | Ubuntu 22.04 LTS o superior |
| **macOS** | Compatible desde macOS 12 Monterey |

**Justificación** — Windows es el sistema operativo mayoritario en entornos corporativos, lo que simplifica el soporte y la distribución del ejecutable. JavaFX es multiplataforma (JVM), por lo que la aplicación funciona igualmente en Linux o macOS sin modificar el código, pero Windows reduce la carga de soporte al equipo técnico.

### Servidor de base de datos (XAMPP + MySQL)

| | |
|---|---|
| **SO principal** | Windows Server 2022 / Windows 10-11 (entorno pequeño) |
| **Alternativa recomendada** | Ubuntu Server 22.04 LTS |
| **SO descartado** | macOS: XAMPP existe pero no es la plataforma natural para servidores de producción |

**Justificación** — En empresas pequeñas es habitual reutilizar un PC con Windows como servidor de base de datos. Si se busca mayor estabilidad y menor coste de licencias, Ubuntu Server 22.04 LTS es la alternativa más robusta.

---

## 4. Instalación del Entorno

### Orden de instalación

| # | Componente | Descripción |
|---|---|---|
| 1 | JDK 21 | Base de ejecución de la aplicación JavaFX |
| 2 | JavaFX SDK 21 | Librería de interfaz gráfica |
| 3 | XAMPP (Apache + MySQL) | Servidor local con MySQL y phpMyAdmin |
| 4 | mysql-connector-j (JDBC) | Driver de conexión Java ↔ MySQL |
| 5 | IntelliJ IDEA | IDE de desarrollo (solo equipo desarrollador) |
| 6 | Importar esquema SQL | Crear la base de datos y las tablas |
| 7 | Configurar cadena de conexión | Ajustar host, puerto, usuario y contraseña |
| 8 | Ejecutar y verificar | Comprobar que la app conecta correctamente |

---

### Paso 1 — JDK 21

1. Descargar **JDK 21 LTS** desde [https://www.oracle.com/java/technologies/downloads/#java21] (la 21 porque siempre es conveniente tener la última versión de LTS).
2. Ejecutar el instalador y marcar las opciones **"Add to PATH"** y **"Set JAVA_HOME"**.
3. Verificar la instalación:
   ```bash
   java -version
   ```

---

### Paso 2 — JavaFX SDK 21

1. Descargar las dependencias desde Maven Repository
2. Sincronizar el proyecto.
3. En mi caso utilicé Scene Builder para hacer la estructura del proyecto de forma gráfica directamente, pero no es necesario
para reproducir el proyecto.

---

### Paso 3 — XAMPP

1. Descargar XAMPP desde [https://www.apachefriends.org](https://www.apachefriends.org) (versión con MySQL 8.x).
2. Instalar en la ruta por defecto: `C:\xampp`
3. En el **Panel de Control de XAMPP**, iniciar los módulos **Apache** y **MySQL**.
4. Acceder a phpMyAdmin en [http://localhost/phpmyadmin](http://localhost/phpmyadmin).

---

### Paso 4 — Conector JDBC (mysql-connector-j) y Lombook (para acceso a datos)

**Opción A — Manual:**
1. Descargar la dependencia desde Maven Reposiory
2. Sincronizar el proyecto
```xml
<dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>9.6.0</version>
            <scope>compile</scope>
</dependency>
<dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.44</version>
            <scope>compile</scope>
</dependency>
```

---

### Paso 5 — Importar el esquema de base de datos

1. Abrir phpMyAdmin en [http://localhost/phpmyadmin](http://localhost/phpmyadmin).
2. Crear una nueva base de datos llamada `fichajeapp` con cotejamiento `utf8mb4_general_ci`.
3. Ir a la pestaña **«Importar»** y cargar el archivo `.sql` del proyecto.
4. Verificar que se han creado las tablas: `usuarios`, `fichaje`, `vacaciones`, `proyecto`, `proyecto_usuarios`.

---

### Paso 6 — Configurar la cadena de conexión JDBC

Localizar en el código fuente la clase de conexión (`DBConnection.java`) y ajustar según tus datos:

```java
String URL  = "jdbc:mysql://localhost:3306/fichajeapp";
String USER = "";
String PASS = "";
```
---

### Paso 7 — Ejecutar la aplicación desde IntelliJ

1. Abrir el proyecto: `File → Open` → seleccionar la carpeta del proyecto.
2. Esperar a que IntelliJ indexe el proyecto y descargue dependencias (sincronizar el proyecto).
3. Configurar la clase principal (`HelloAplication.java`) en `Run → Edit Configurations`.
4. Ejecutar con **▶ Run**.
5. Comprobar en la consola que no aparecen errores de conexión JDBC.

---

## 5. Resumen de versiones

| Componente | Versión |
|---|---|
| JDK | Oracle |
| JavaFX SDK |
| XAMPP | 8.2.x (MySQL 8.x + phpMyAdmin) |
| mysql-connector-j | 8.3.0 o superior |
| IntelliJ IDEA | 2024.x (Community) |
| SO usuario | Windows 10 / 11 (64 bits) |
| SO servidor BD | Windows 10/11 o Ubuntu Server 22.04 LTS |



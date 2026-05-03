
# Project Title

A brief description of what this project does and who it's for

Readme arquitectura · MD
Copiar

# 🏗️ Arquitectura del Proyecto — Sistema de Fichajes
 
> Aplicación de escritorio desarrollada con **JavaFX + JDBC + MySQL (XAMPP)**
 
---
 
## 1. Arquitectura General
 
El proyecto sigue una arquitectura **MVC (Modelo - Vista - Controlador)** adaptada a JavaFX, separando claramente las responsabilidades en capas:
 
```
┌─────────────────────────────────────────────────────┐
│                  CAPA DE VISTA                      │
│         Archivos .fxml + estilo.css                 │
│  login, inicio, perfil, lista-usuarios, sidebar...  │
└────────────────────┬────────────────────────────────┘
                     │  eventos / bindings
┌────────────────────▼────────────────────────────────┐
│               CAPA DE CONTROLADOR                   │
│   LoginController, InicioController, SidebarCon-    │
│   troller, ListaUsuariosController, PerfilCon-      │
│   troller, CambiarDatosController, CrearUsuario-    │
│   Controller, BaseController                        │
└────────────────────┬────────────────────────────────┘
                     │  llamadas de datos
┌────────────────────▼────────────────────────────────┐
│                  CAPA DE DATOS                      │
│     UsuarioDAO  ·  DBConnection  ·  DBSchem         │
└────────────────────┬────────────────────────────────┘
                     │  SQL / JDBC
┌────────────────────▼────────────────────────────────┐
│              BASE DE DATOS (MySQL)                  │
│                 XAMPP / phpMyAdmin                  │
└─────────────────────────────────────────────────────┘
```
 
### Descripción de cada capa
 
| Capa | Paquete / carpeta | Responsabilidad |
|---|---|---|
| **Vista** | `resources/org.example.appfichaje` + `estilos/` | Archivos `.fxml` que definen la interfaz gráfica y `estilo.css` para los estilos |
| **Controlador** | `controller/` | Gestiona los eventos de la UI y coordina la lógica entre vista y datos |
| **Modelo** | `model/` | Clases de dominio (`Usuario`) y enumeraciones (`Rol`, `TipoJornada`) |
| **Acceso a datos** | `dao/` + `database/` | `UsuarioDAO` ejecuta las consultas SQL; `DBConnection` gestiona la conexión JDBC |
| **Utilidades** | `data/` + `fichero/` | `DataSet` para datos en memoria; `horas.txt` para fichaje por fichero |
 
---
 
## 2. Diagrama de Clases
 
```
┌─────────────────────┐
│    HelloApplication │
│─────────────────────│
│ + start(Stage)      │
└──────────┬──────────┘
           │ lanza
┌──────────▼──────────┐
│      Launcher       │
└─────────────────────┘
 
                                ┌─────────────────────┐
                                │  LoginController    │
                                │─────────────────────│
                                │  InicioController   │
                                │─────────────────────│
                                │  PerfilController   │
                                │─────────────────────│
                                │  ListaUsuariosCtrl  │
                                │─────────────────────│
                                │  CambiarDatosCtrl   │
                                │─────────────────────│
                                │  CrearUsuarioCtrl   │
                                └─────────────────────┘
 
┌─────────────────────┐         ┌─────────────────────┐
│  SidebarController  │         │      Usuario        │
│─────────────────────│         │─────────────────────│
│ + sidebar.fxml      │         │ - id        : int   │
│ (componente global) │         │ - nombre    : String│
└─────────────────────┘         │ - apellidos : String│
                                │ - dni       : String│
┌─────────────────────┐         │ - email     : String│
│     UsuarioDAO      │────────▶│ - rol    : Rol ⬡   │
│─────────────────────│         │ - tipoJornada: ⬡    │
│ + getAll()          │         │ - horasSemanales    │
│                                 - fechaAlta : Date  │
│ + insertar()        │         │ - fechaBaja : Date  │
│ + actualizar()      │         └──────────┬──────────┘
│                     │                    │ usa
└──────────┬──────────┘         ┌──────────▼──────────┐
           │ usa                │    ⬡ Rol (Enum)     │
┌──────────▼──────────┐         │─────────────────────│
│    DBConnection     │         │  ADMIN              │
│─────────────────────│         │  EMPLEADO           │
│ + getConnection()   │         └─────────────────────┘
└─────────────────────┘
                                ┌─────────────────────┐
                                │ ⬡ TipoJornada (Enum)│
                                │─────────────────────│
                                │  COMPLETA           │
                                │  PARCIAL            │
                                └─────────────────────┘
```
 
> **Nota:** `⬡` indica enumeración. Las flechas `◄────` indican herencia (los controladores extienden `BaseController`).
 
---
 
## 3. Mejora del MPO — Componente Sidebar reutilizable
 
### El problema que resolvía
 
En aplicaciones JavaFX es habitual definir el menú lateral **en cada pantalla por separado**. Esto significa que si la app tiene 6 vistas, el código del menú se repite 6 veces. Cualquier cambio (añadir un botón, cambiar un estilo) obliga a modificar todos los archivos `.fxml` uno a uno.
 
### La solución implementada
 
Se ha creado un **componente propio reutilizable** (`sidebar.fxml` + `SidebarController`) que encapsula todo el menú lateral. Para incluirlo en cualquier vista basta con una sola línea:
 
```xml
<fx:include fx:id="sidebar" source="sidebar.fxml"/>
```
 
En la práctica, colocado en el lado izquierdo de un `BorderPane`:
 
```xml
<left>
    <fx:include fx:id="sidebar" source="sidebar.fxml"/>
</left>
```
 
### Beneficios concretos
 
| Antes (sin componente) | Después (con componente) |
|---|---|
| Código del menú repetido en cada `.fxml` | Definido una sola vez en `sidebar.fxml` |
| Cambiar un botón = modificar 6 archivos | Cambiar un botón = modificar 1 archivo |
| Controladores con lógica de navegación mezclada | Cada controlador solo gestiona su propia vista |
| Mayor probabilidad de inconsistencias entre vistas | El menú es siempre idéntico en toda la app |
 
---
 
## 4. Mejora del MPO — Uso de Enums en lugar de Strings
 
### Qué se ha cambiado
 
Los campos `rol` y `tipoJornada` del modelo `Usuario` utilizan **enumeraciones** (`Rol` y `TipoJornada`) en lugar de cadenas de texto planas como tenía antes.
 
 
### Por qué es mejor usar Enums que Strings
 
**1. Seguridad en tiempo de compilación**
Con un `String`, cualquier valor es válido: `"ADMINNN"`, `""`, `null`... El error solo aparece en ejecución. Con un `Enum`, si el valor no existe el compilador lo rechaza antes de ejecutar nada.
 
**2. Valores controlados y documentados**
El `Enum` actúa como documentación viva: al escribir `Rol.` el IDE muestra exactamente las opciones disponibles (`ADMIN`, `EMPLEADO`). Con un `String` el desarrollador tiene que buscar en el código o en la base de datos qué valores son válidos.
 
**3. Eliminación de errores por typos**
`"EMPLEADO"` y `"empleado"` son dos strings distintos. `Rol.EMPLEADO` es siempre el mismo valor, sin importar quién lo escriba.
 
**5. Coherencia con la base de datos**
MySQL también define estos campos como `ENUM('ADMIN','EMPLEADO')` y `ENUM('COMPLETA','PARCIAL')`. Usar `Enum` en Java refleja exactamente esa restricción a nivel de aplicación, cerrando el contrato en ambos extremos.
 
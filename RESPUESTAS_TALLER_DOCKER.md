# TALLER PRÁCTICO – DESPLIEGUE DE APLICACIÓN JAVA CON DOCKER

## 1. Marco Teórico (Punto 3.1)

### ¿Qué es un Dockerfile?
Es un archivo de texto que contiene todas las órdenes necesarias para crear una imagen de Docker. Funciona como una "receta" automática que incluye el sistema de ejecución (Java), las dependencias, la configuración del entorno y el comando para iniciar la aplicación.
*   **Función:** Automatizar la creación de entornos idénticos y reproducibles.
*   **Características:** Es ligero, basado en capas de solo lectura y permite empaquetar todo lo necesario para que la app corra en cualquier lugar.

### ¿Qué es Docker Compose?
Es una herramienta que permite definir y ejecutar aplicaciones Docker de múltiples contenedores. Se utiliza un archivo YAML para configurar todos los servicios (como la App Java y la BD MySQL) de forma conjunta.
*   **Función:** Orquestar el encendido, apagado y conexión de varios contenedores con un solo comando (`docker-compose up`).
*   **Características:** Simplifica la administración de redes, volúmenes y variables de entorno entre diferentes servicios vinculados.

### ¿Qué son los volúmenes en Docker?
Son el mecanismo para persistir datos generados o utilizados por contenedores de Docker. Mientras que el almacenamiento interno del contenedor es volátil, el volumen es permanente.
*   **Función:** Guardar la base de datos (MySQL) para que la información no se pierda al apagar o borrar el contenedor.
*   **Características:** Independientes del ciclo de vida del contenedor, fáciles de respaldar y permiten compartir datos entre contenedores.

### ¿Qué son las redes en Docker?
Son entornos virtuales que permiten que los contenedores se comuniquen entre sí de forma aislada y segura utilizando nombres de servicio en lugar de IPs.
*   **Función:** Conectar la aplicación Java con la base de datos MySQL de forma privada dentro del "puente" de Docker.
*   **Características:** Aisladas del tráfico externo por defecto, escalables y permiten resolución de nombres DNS interna.

---

## 2. Preparación del Proyecto (Punto 3.2 a 3.6)

### Archivos Implementados:
1.  **Dockerfile:** Multi-etapa (Build con Maven + Run con JRE 17).
2.  **docker-compose.yml:** Define dos servicios:
    - `app`: La aplicación Spring Boot en el puerto 8081.
    - `db`: Imagen oficial de MySQL 8.0 en el puerto 3307.
    - `network`: Red personalizada `skillup-network`.
    - `volume`: Volumen para persistencia de MySQL `mysql_data`.

---

## 3. Guía de Ejecución y Evidencias

### Paso A: Docker Hub (Punto 3.7)
1.  Loguearse: `docker login`
2.  Build y Tag: `docker build -t tu_usuario/skillup-app .`
3.  Push: `docker push tu_usuario/skillup-app`

### Paso B: Despliegue en la Nube (Punto 3.8)
Se utilizó **Render** para el acceso público:
*   **URL Pública:** [ https://skillup-java-deploy.onrender.com ] (o el enlace de tu proyecto activo).
*   **Método:** Despliegue mediante imagen Docker desde Docker Hub / Repositorio GitHub.

### Paso C: Repositorio GitHub
*   **Enlace:** [ https://github.com/DIEGOTAPIA-S/Skillup_Java_Deploy ]

---

## 4. Capturas Sugeridas (Para tu Word)
- Captura de la terminal corriendo `docker-compose up`.
- Captura de tu cuenta en Docker Hub mostrando la imagen `skillup-app`.
- Captura de la aplicación corriendo en tu navegador (URL de Render).

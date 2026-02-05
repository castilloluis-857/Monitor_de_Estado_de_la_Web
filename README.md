# Monitor Estado Web

**Monitor Estado Web** es un monitor de infraestructura ligero para escritorio que permite supervisar la disponibilidad de servicios críticos con una interfaz minimalista y alertas en tiempo real.

---

## 📺 Demostración Visual

### 📽️ Video de Funcionamiento

Aquí puedes ver el comportamiento de la aplicación: desde el inicio de la vigilancia hasta la detección de una caída de servidor con alerta sonora.


<p align="center">
  <img src="https://github.com/user-attachments/assets/3c828abb-7d80-4edc-a6fa-365d10a0d9d6" width="50%" alt="Demostración Vigía Web">
</p>

---

### 📸 Capturas de Pantalla
<p align="center">
<img width="625" height="598" alt="image" src="https://github.com/user-attachments/assets/b6741182-f7fc-4e9e-a9dd-6f9b7f594441" />
</p>

#### 1. Estado Online (Sistema Operativo)

Cuando el servicio responde correctamente (Código 200 OK), la interfaz se mantiene en tonos verdes vibrantes.

#### 2. Detección de Errores y Caídas

Interfaz visual ante un error 404 o pérdida total de conexión. Se observa el cambio de estado del LED y el registro en la consola.

---

## ✨ Características Principales

* **Monitoreo Asíncrono:** Consultas en segundo plano que no bloquean la interfaz de usuario.
* **Diagnóstico de Errores:** Identificación precisa de códigos HTTP (404, 500, 301) y fallos de red (-1).
* **Persistencia de Datos:** Registro automático de incidentes en `uptime_errors.log`.
* **Alertas Inteligentes:** Notificaciones auditivas del sistema cuando un servicio cae.

---

## 🛠️ Stack Técnico

* **Lenguaje:** Java 21.
* **Framework UI:** JavaFX.
* **Persistencia:** File I/O (Logs persistentes).
* **Arquitectura:** Diseño orientado a objetos con separación de lógica de red y vista.

---

## 🚀 Instalación y Uso

1. **Clonar el repositorio:**
```bash
git clone https://github.com/castilloluis-857/Monitor_de_Estado_de_la_Web.git

```


2. **Ejecutar:**
```bash
mvn clean javafx:run

```

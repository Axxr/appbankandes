

---

# README - Proyecto Android en Kotlin (Jetpack Compose)

Este proyecto es una aplicación Android creada con Kotlin y Jetpack Compose. A continuación se encuentran las instrucciones para compilar y ejecutar la aplicación en tu entorno local, así como las instrucciones para probar el caso de uso del login con usuario y contraseña incorrectos.

## Requisitos

Antes de comenzar, asegúrate de tener los siguientes requisitos instalados en tu máquina local:

- [Android Studio](https://developer.android.com/studio) (última versión recomendada)
- JDK 11 o superior (recomendado para la compilación de proyectos Android)
- Kotlin: Utiliza la versión 1.8.x (o la última versión estable compatible con Compose).
- Gradle: Usa Gradle 7.x.
- Plugin de Android: Asegúrate de usar el plugin de Android 7.3.x o superior.

## Instalación

### 1. Clonar el repositorio

Primero, clona este repositorio en tu máquina local usando el siguiente comando:

```bash
git clone https://github.com/Axxr/appbankandes.git
```

### 2. Abrir el proyecto en Android Studio

Abre Android Studio y selecciona la opción **Open**. Luego selecciona la carpeta del proyecto que acabas de clonar.

### 3. Configurar el proyecto

Android Studio debería detectar automáticamente las dependencias del proyecto y descargarlas. Si no es así, puedes sincronizar el proyecto manualmente haciendo clic en **File > Sync Project with Gradle Files**.

### 4. Construir el proyecto

Para compilar la aplicación, asegúrate de que tu dispositivo o emulador esté configurado y seleccionado, luego ejecuta la app usando el botón **Run** en Android Studio o usando el siguiente comando en la terminal:

```bash
./gradlew build
```

### 5. Ejecutar la app

Para ejecutar la aplicación en un emulador o dispositivo físico, haz clic en el botón **Run** en Android Studio o usa el siguiente comando:

```bash
./gradlew installDebug
```

## Probar el caso de uso de login con credenciales incorrectas

La app tiene una pantalla de inicio de sesión donde puedes ingresar un nombre de usuario y una contraseña. Si introduces las siguientes credenciales incorrectas, se debe mostrar un mensaje de error:

- **Username:** `12345678`
- **Password:** `TestPass123`


La aplicación debería mostrar un mensaje de error como el siguiente:

   ```
   "Usuario y/o contraseña incorrectos"
   ```


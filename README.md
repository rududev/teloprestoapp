# TeLoPresto 📦

![Version](https://img.shields.io/badge/versión-0.01.0015-blue)
![API](https://img.shields.io/badge/API-26%2B-green)
![Platform](https://img.shields.io/badge/platform-Android-brightgreen)

Aplicación Android para la gestión de préstamos cotidianos de objetos entre personas.

---

## Descripción

TeLoPresto permite registrar de forma rápida y sencilla cualquier tipo de préstamo cotidiano, ya sea un objeto físico, una cantidad de dinero o un favor. La aplicación aporta una evidencia informal pero seria del acuerdo entre las partes, incluyendo fotografía del objeto y firma digital del receptor.

Desarrollada como **Proyecto Intermodular** del Ciclo Formativo de Grado Superior de **Desarrollo de Aplicaciones Multiplataforma (DAM)**.

---

## Características

- Registro de préstamos con persona, categoría, fechas y notas
- Captura de fotografía del objeto prestado mediante la cámara del dispositivo
- Firma digital táctil del receptor como evidencia del acuerdo
- Gestión del estado de los préstamos (activo / devuelto)
- Historial de préstamos devueltos
- Estadísticas básicas de actividad
- Recordatorios automáticos por fecha de devolución
- Gestión de personas y categorías personalizables
- Funciona completamente sin conexión a internet

---

## Tecnologías utilizadas

- **Lenguaje:** Java
- **Plataforma:** Android (API 26+)
- **Arquitectura:** MVVM (Model-View-ViewModel)
- **Base de datos:** Room (SQLite)
- **UI:** Layouts XML + Material Design 3
- **Notificaciones:** AlarmManager + NotificationManager
- **Control de versiones:** Git + GitHub

---

## Estructura del proyecto

```
app/src/main/java/com/telopresto/app/
├── model/          # Entidades Room (Prestamo, Persona, Categoria)
├── dao/            # Interfaces de acceso a datos
├── database/       # AppDatabase (Singleton)
├── repository/     # Capa de repositorio
├── viewmodel/      # ViewModels con LiveData
└── view/           # Activities, Fragments y vistas personalizadas
```

---

## Requisitos

- Android 8.0 (API 26) o superior
- Cámara trasera (opcional, para foto del objeto)

---

## Instalación

1. Clona el repositorio:
```bash
git clone https://github.com/rududev/TeLoPresto.git
```
2. Abre el proyecto en Android Studio
3. Sincroniza las dependencias con Gradle
4. Ejecuta la app en un dispositivo o emulador con API 26+

---

## Autor

**Rubén Ángel Durán Pérez**  
Ciclo Formativo de Grado Superior — Desarrollo de Aplicaciones Multiplataforma  
Curso 2025-2026

---

## Licencia

Proyecto académico — uso educativo.

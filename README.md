# Patchy

App Android para seguir un plan de acondicionamiento de cuatro semanas pensado para quien
empieza de cero: sin poder hacer una flexión, con poco fondo aeróbico y con un banco, una
barra y unos discos en casa. Cada sección del plan lleva la evidencia en la que se apoya.

## Qué hace

- **Hoy** — qué toca según la fecha (fuerza A/B, cardio de la semana, sábado ligero,
  grippers), con flechas para recorrer los días y marcar los hechos.
- **Plan** — calendario por semanas con gráfico de progreso, rutina de fuerza, progresión de
  cardio, la escalera hasta la primera flexión, técnica para cargas pesadas, grippers y la meta
  para pasar de fase.
- **Comida** — proteína, calorías y grasa saturada calculadas a partir del perfil; guía sobre
  huevos, leche, frijoles y suplementos.
- **Registro** — barras por semana, bitácora libre, medidas de cintura, gripper y metas.
- **Más** — perfil (peso, estatura, edad, sexo, fecha de inicio), tema visual, principios,
  créditos y fuentes.

Todo se guarda en el teléfono. Sin cuentas ni conexión.

## Construir

Requiere Android Studio (o el JDK 17+ y el SDK con API 37).

```
./gradlew assembleDebug
```

El APK queda en `app/build/outputs/apk/debug/app-debug.apk`. Para instalar por USB:

```
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

Kotlin + Jetpack Compose, sin dependencias fuera de AndroidX. minSdk 26.

## Diseño

La interfaz adapta el sistema de diseño de [Komi Store](https://github.com/kurikomi-labs/komi-store)
(personalidad «Manga»: papel y tinta, bordes duros, sombras sin desenfoque, sellos y tramas),
bajo Apache License 2.0. Las paletas son temas por personaje de Touhou Project, en claro y
oscuro. Detalles y cambios en `THIRD_PARTY_NOTICES.md`.

Tipografías: Anton, Noto Sans y JetBrains Mono (SIL Open Font License).

## Aviso

El plan es material informativo con sus fuentes citadas; no sustituye a un médico ni a un
entrenador.

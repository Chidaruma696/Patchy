# Avisos de terceros

## Komi Store — sistema de diseño

La interfaz de Patchy adapta el sistema de diseño de **Komi Store**
(https://github.com/kurikomi-labs/komi-store), Copyright kurikomi-labs y colaboradores,
distribuido bajo la **Apache License 2.0** (copia completa en `licenses/komi-store-LICENSE.txt`).

Archivos derivados, todos en `app/src/main/java/com/patchy/app/ui/komi/`:

| Patchy | Origen en komi-store (`core/presentation/src/commonMain/kotlin/zed/rainxch/core/presentation/`) |
|---|---|
| `Personality.kt` | `personality/model/*.kt`, `personality/manga/{MangaColors,MangaAccentSwatch,MangaPaper,MangaAccent,MangaShape,MangaShadow,MangaType,HeadlineMarker}.kt`, `personality/MangaPersonality.kt`, `personality/utils/PersonalityThemeProvider.kt`, `status/StatusPalette.kt`, `spacing/Spacing.kt`, `personality/model/ColorContrast.kt` |
| `Ink.kt` | `personality/manga/decoration/{InkModifiers,InkPress,InkFocusRing,GridPaper,SpeedLineWash,StarburstShape}.kt` |
| `Komi.kt` | `components/text/{KomiText,KomiHeadline,KomiTextRole}.kt`, `components/surfaces/*.kt`, `components/buttons/{KomiButton,KomiButtonVariant,KomiButtonSize,MangaButtonRoles}.kt`, `components/chips/*.kt`, `components/badge/*.kt`, `components/inputs/{KomiCheckbox,KomiTextField}.kt`, `components/dividers/KomiDivider.kt`, `components/bars/{KomiTopBar,KomiBottomBar,KomiNavItem}.kt`, `components/scaffold/KomiScaffold.kt` |

Cambios respecto al original (Apache 2.0 §4 b):

- Portado de Kotlin Multiplatform a un único módulo Android; sin `composeResources`, las fuentes
  se cargan desde `res/font`.
- Solo la personalidad «Manga»; la rama «Classic» de cada componente se eliminó.
- Sin selección de script por idioma: siempre Anton / Noto Sans / JetBrains Mono.
- Las paletas (`MangaColors`, `MangaAccent`, `MangaPaper`) se sustituyeron por temas propios de
  Patchy: un tema por personaje de Touhou Project, cada uno con papel claro y oscuro
  (`Themes` en `Personality.kt`). El modo claro/oscuro lo elige la persona o el sistema.
- Sin hover de escritorio, toasts, estados de carga, campos de contraseña ni chips de entrada.
- `KomiChip` admite `fill`/`ink` para sellos de color semántico; `KomiButton` admite `container`;
  `KomiSurface` admite `borderColor`. `KomiBadge` muestra texto en vez de contadores.
- Tamaño del cuerpo de texto 14.5 sp en vez de 13.5 sp.

## Tipografías

- **Anton** — Copyright 2020 The Anton Project Authors. SIL Open Font License 1.1.
- **Noto Sans** — Copyright 2022 The Noto Project Authors. SIL Open Font License 1.1.
- **JetBrains Mono** — Copyright 2020 The JetBrains Mono Project Authors. SIL Open Font License 1.1.

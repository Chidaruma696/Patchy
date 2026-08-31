package com.patchy.app.data

data class Source(val name: String, val url: String)
data class LadderStep(val step: String, val name: String, val percent: Int, val measured: Boolean)

/** Todo el contenido del plan, neutro: sirve para cualquier persona que empiece de cero. */
object Content {

    // ---------- Para quién ----------
    val forWhom = listOf(
        "Este plan es para alguien que **empieza de cero**: sin poder hacer una flexión, con poco fondo aeróbico y con un banco, una barra y unos discos en casa. Cuatro semanas de base antes de una rutina con barra completa.",
        "No es un plan de adelgazar. Quien empieza con poco músculo gana músculo y pierde grasa a la vez si come suficiente proteína; con déficit calórico pierde lo poco que tiene. **El plan es construir, no recortar.**",
    )

    const val START_EVIDENCE = "Peso normal con poco músculo y grasa alta se llama obesidad de peso normal; la intervención con más respaldo es entrenar fuerza, no recortar calorías (NASM). En principiantes se gana músculo y se pierde grasa a la vez, y está documentado «incontables veces» (Barakat 2020)."

    val principles = listOf(
        "**Doble progresión.** Se sube peso solo cuando salen todas las series en el rango alto.",
        "**Cada músculo dos veces por semana**, con 5–10 series semanales: suficiente para un principiante.",
        "**Caminar antes de correr.** Alternar trote y caminata reduce lesiones a la mitad frente a correr seguido.",
        "**1.6 g de proteína por kilo**, en cuatro tomas. Por encima no hay más ganancia.",
        "**Sin día cero.** El hábito se automatiza en unos 66 días de repetición diaria; el sábado es ligero, no vacío.",
        "**Descanso solo con señal**: dolor articular, fiebre, dos noches sin dormir. Las agujetas no cuentan.",
    )

    // ---------- Fuerza ----------
    val dayA = listOf(
        listOf("Flexiones inclinadas", "3 × 8–10", "manos en el banco"),
        listOf("Remo con barra", "3 × 10–12", "empieza con la barra"),
        listOf("Press de banca", "2 × 10", "barra vacía"),
        listOf("Colgarse de la barra", "3 × 15 s", "pies en una silla"),
        listOf("Curl de bíceps", "2 × 12", "barra o mancuernas"),
        listOf("Fondos en el banco", "2 × 8–10", "codo a 90°, no más"),
    )

    val dayB = listOf(
        listOf("Peso muerto rumano", "3 × 10", "cadera atrás"),
        listOf("Sentadilla al banco", "3 × 10–12", "siéntate y levántate"),
        listOf("Extensión y curl de pierna", "2 × 12", "rodillo del banco"),
        listOf("Puente de glúteo", "2 × 12", "disco en la cadera"),
        listOf("Plancha", "2 × 20 s", "rodillas si hace falta"),
    )

    const val STRENGTH_INTRO = "Domingo A, martes B, jueves A. La siguiente semana empieza por B. La barra **vacía** las dos primeras sesiones de cada ejercicio nuevo: no es perder el tiempo, es aprender el movimiento. Descansa 90 segundos entre series."

    const val DOUBLE_PROGRESSION = "**Doble progresión.** Nunca subas peso hasta completar todas las series en el rango alto de repeticiones. Llegas a 3×12 en las tres series → añades un disco por lado → vuelves a 3×8. Sin esto, con saltos de 4–5 kg, te atrancas en un mes."

    val benchSafety = listOf(
        "**No llegues nunca al fallo.** Para siempre 2 o 3 repeticiones antes de no poder más.",
        "**No pongas los collarines en press de banca si entrenas sin ayudante.** Suena al revés, pero si te quedas atrapado puedes inclinar la barra y dejar que los discos se deslicen y caigan. Con los seguros puestos, no puedes. En los demás ejercicios sí van. Y agarra la barra con el pulgar rodeándola.",
    )

    const val DIPS_SAFETY = "Baja solo hasta que el brazo quede **paralelo al suelo**, el codo a unos 90°. Espalda pegada al borde del banco, hombros atrás y abajo. Más profundo no da más tríceps: pasa la carga a la parte delantera del hombro. Cuanto más cerca los pies, más fácil."

    val armsNote = listOf(
        "**El tríceps es el 60–65 % del brazo.** Todo el mundo hace curl y se olvida del tríceps, que es el que de verdad da volumen. Por eso los fondos entran en el plan y no son opcionales.",
        "El resto lo hace la comida: en alguien delgado el límite no es el entrenamiento, es cuánto come. Y 1.5–2.5 cm de contorno en el primer año bien hecho es lo normal; es lento, pero no para.",
    )

    const val STRENGTH_EVIDENCE = "Trabajar cada músculo 2 veces por semana supera a 1 con igual volumen (Schoenfeld 2016); con el volumen igualado la frecuencia deja de importar (2019). Principiantes crecen con 5–10 series semanales por músculo (Schoenfeld 2017); este plan está en ese rango. Press sin ayudante: sin collarines y con topes si el banco los tiene. Fondos: tope a 90° para no cargar la cápsula anterior del hombro. Tríceps ≈ 60–65 % de la masa del brazo (StatPearls). Contorno de brazo: +4–6 % en 8–12 semanas."

    // ---------- Calentamiento ----------
    const val WARMUP_INTRO = "El calentamiento no es opcional, pero tampoco son 20 minutos: aquí son **5**. Sube la temperatura, mueve las articulaciones que vas a usar y ensaya el primer movimiento sin peso. Con el cuerpo frío se rinde menos y los tejidos toleran peor la carga."

    val warmup = listOf(
        listOf("1 · Temperatura", "2–3 min de marcha rápida, escaleras o trote muy suave"),
        listOf("2 · Articulaciones", "10 círculos por lado: hombros, caderas y muñecas"),
        listOf("3 · Ensayo", "El primer ejercicio del día con la barra vacía × 10, fácil"),
        listOf("4 · Aproximación", "Cuando ya cargues discos: 1 serie con la mitad del peso × 8 antes de las series de trabajo"),
    )

    const val WARMUP_STRETCH = "**El estiramiento estático largo va después, no antes.** Mantener un estiramiento más de 60 segundos por músculo justo antes de entrenar baja la fuerza (~7 % de media); menos de 30–45 segundos apenas afecta. Antes del entrenamiento: movimiento. Después, o en el sábado de movilidad: estira lo que quieras."

    const val WARMUP_CARDIO = "En cardio el calentamiento ya está dentro del diseño: los primeros 3–5 minutos van más despacio de tu ritmo normal, y en las semanas de intervalos siempre se empieza caminando. Igual al final: los últimos 2–3 minutos, suave."

    const val WARMUP_EVIDENCE = "Calentar mejora el rendimiento en la gran mayoría de protocolos estudiados y el calentamiento activo y dinámico supera al estiramiento estático (Fradkin 2010). Estirar estático ≥60 s por músculo justo antes reduce la fuerza ~7.5 % de media; <45 s apenas afecta (Kay & Blazevich 2012; Simic 2013, efecto pequeño pero consistente). La subida progresiva de carga se asocia con ~25 % menos lesiones por distensión (revisión 2025). La serie de aproximación es práctica estándar: ensaya el patrón sin acumular fatiga."

    // ---------- Escalera ----------
    const val LADDER_INTRO = "Una flexión en el suelo mueve el **64 % del peso corporal**. Es fuerza de verdad, no un mínimo básico. Cuanto más altas las manos, menos peso empujas. Cuando te salgan **3×12 cómodas** en un nivel, bajas al siguiente."

    val ladder = listOf(
        LadderStep("01", "Contra la pared", 25, false),
        LadderStep("02", "En la mesa o barra de cocina, ~90 cm", 33, false),
        LadderStep("03", "En el respaldo del banco, ~60 cm", 41, true),
        LadderStep("04", "En el asiento del banco, ~45 cm", 48, false),
        LadderStep("04b", "Alternativa: rodillas en el suelo", 49, true),
        LadderStep("05", "En un escalón, ~20 cm", 58, false),
        LadderStep("06", "En el suelo", 64, true),
    )

    const val LADDER_OUTRO = "Los porcentajes sin ≈ están medidos en placa de fuerza; los demás son interpolados. Recorrer la escalera entera son unas 8 a 12 semanas. Es lento y es normal."

    const val LADDER_EVIDENCE = "Fuerzas medidas en placa: manos a 61 cm = 41 %, rodillas = 49 %, manos a 30 cm = 55 %, suelo = 64 % del peso corporal; la carga baja de forma lineal con la altura (Ebben et al. 2011; modelo predictivo en Sci. Reports 2025). Colgarse: principiantes 10–30 s, sumar 5 s cada 2–3 semanas; con pies apoyados si no llegas a 10 s."

    // ---------- Cardio ----------
    const val CARDIO_INTRO = "Nada de cuerda ni sprints al principio. Saltar está entre 8.8 y 12.3 MET —la intensidad de correr— frente a 3.8 de caminar rápido, y además tiene 2–4 semanas de barrera de coordinación. Si no aguantas más de unos saltos no dice nada malo de ti: era el ejercicio equivocado."

    val cardioTable = listOf(
        listOf("1–2", "Caminar 30–40 min"),
        listOf("3–4", "1 min trote / 2 min caminar × 8"),
        listOf("5–6", "2 min trote / 2 min caminar × 7"),
        listOf("7–8", "3 min trote / 90 s caminar × 6"),
        listOf("9 +", "20 min de trote continuo"),
    )

    const val CARDIO_KEY = "Los tramos de trote van **lentos**. A un ritmo en el que podrías mantener una conversación en frases completas. Si acabas ahogado ibas demasiado rápido, por ridículamente suave que te parezca. Casi todo el mundo se salta esto y por eso nunca progresa. Si una semana te cuesta, la repites en vez de avanzar."

    const val CARDIO_EVIDENCE = "Alternar trote y caminata produce menos lesiones que correr seguido con el mismo volumen, porque tendones y huesos se adaptan más despacio que el corazón; los programas de principiante tienen 20–25 % de lesiones el primer año y bajan mucho con intervalos (IJERPH 2023). El salto del Couch-to-5K a 20 min seguidos en la semana 5 es donde abandona la mayoría; esta progresión es más gradual a propósito. Mucho volumen fácil y poco duro es el modelo con más respaldo en resistencia (Seiler, 80/20)."

    const val LIGHT_DAY = "Caminar 20–30 minutos y 10 de movilidad. No es descanso, es la señal diaria que construye el hábito sin sumar fatiga. Cuenta igual que cualquier otra casilla."

    // ---------- Cargas fuera del gimnasio ----------
    const val LOADS_INTRO = "Si tu trabajo o tu vida incluyen levantar peso —cajas, bultos, muebles—, ese es el momento de más riesgo: carga pesada, sin calentar y con prisa. Una hernia para meses. El máximo que la NIOSH considera seguro con las dos manos **en condiciones ideales es 23 kg**; muchas cargas cotidianas lo superan, así que si te cuestan no es debilidad."

    val loadCues = listOf(
        "**Cadera atrás, no espalda doblada.** Rodillas flexionadas, pecho arriba, espalda recta como una tabla.",
        "**La carga pegada al cuerpo.** Cada centímetro que la separas multiplica la carga sobre las lumbares.",
        "**Aprieta el abdomen antes de levantar**, como si fueras a recibir un golpe. Eso es lo que protege la columna.",
        "**Nunca gires con la carga en las manos.** Levanta primero, después mueve los pies. Girar con la columna comprimida es el mecanismo principal de hernia discal.",
        "**Nunca en frío.** Antes de una tanda de cargas pesadas, 15–20 sentadillas al aire o bisagras de cadera sin peso valen como calentamiento.",
    )

    const val RDL_NOTE = "El peso muerto rumano es literalmente el patrón de levantar una carga del suelo, hecho despacio y con peso controlable. Entrenarlo no añade riesgo: lo quita, porque practicas con 10 kg el movimiento que luego haces con 30."

    const val LOADS_EVIDENCE = "La mayoría de lesiones al levantar ocurren con el tronco flexionado y girado; la columna no está diseñada para girar bajo carga (QLS Safety). Ecuación NIOSH: 23 kg es el máximo en condiciones óptimas; para reducir lesiones de verdad se recomiendan 14–18 kg (ErgoIBV)."

    // ---------- Comida ----------
    const val FOOD_INTRO = "Objetivo: **1.6 g de proteína por kilo de peso al día**, repartidos en **4 comidas**. Sin recortar calorías. Quien empieza con poco músculo y hace dieta pierde el poco que tiene y acaba más delgado con la misma grasa."

    const val PROFILE_HINT = "Completa tu perfil en la pestaña Más (peso, estatura, edad) y esta tabla se calcula sola."

    const val PROTEIN_SPLIT = "Repartir la proteína en 4 tomas no es solo por el músculo: la proteína es el estímulo dietético más fuerte de la gastrina, la hormona que ordena producir ácido. Cuatro tomas moderadas cargan el estómago cuatro veces poco; una grande lo carga una vez mucho."

    val food = listOf(
        listOf("3 huevos (cocidos o revueltos, no fritos)", "19 g"),
        listOf("150 g de pollo", "35 g"),
        listOf("1 lata de atún", "25 g"),
        listOf("1 taza de frijoles de la olla", "15 g"),
        listOf("1 taza de yogur natural", "10 g"),
        listOf("1 vaso de leche, con comida", "8 g"),
        listOf("Total", "112 g"),
    )

    val eggsNote = listOf(
        "Los ensayos en adultos jóvenes sanos dicen que **hasta 3 huevos al día** no empeoran la relación LDL/HDL, mejoran la función del HDL y producen partículas de LDL más grandes y menos aterogénicas. Ese es el rango con respaldo.",
        "Entre el 15 y el 25 % de la gente es «hiperrespondedora» y le sube el LDL más. Si en una analítica sale el colesterol alto, baja a 1 entero y compensa con claras: la clara es proteína casi pura sin grasa.",
        "Los casos de 16 o 25 huevos al día existen y son reales, pero son experimentos de una sola persona. 16 huevos son 26 g de grasa saturada: prácticamente el tope diario antes de comer nada más.",
    )

    const val MILK_NOTE = "La leche neutraliza el ácido del estómago 10–20 minutos y después la proteína y el calcio disparan la gastrina, que ordena producir más ácido del que había. Uno o dos vasos con la comida no son problema; la leche como remedio para el ardor, sí lo es: lo quita un rato y lo devuelve con intereses. El yogur lleva la misma proteína y calcio, pero es lo que mejor tolera la gente en la práctica."

    val beansNote = listOf(
        "Los frijoles no tocan el ácido del estómago: sus azúcares (rafinosa, estaquiosa) lo atraviesan intactos y fermentan en el colon. Eso es gas e hinchazón, que se siente en la misma zona y se confunde con gastritis, pero no la agrava. La fibra soluble de legumbres se asocia con un **60 % menos de úlcera duodenal**.",
        "Lo que sí pesa es **la manteca**: los refritos con manteca son grasa, y la grasa frena el vaciado gástrico. **Cómo comerlos:** de la olla, remojo de una noche con una cucharadita de sal y tirar el agua (baja los azúcares del gas un 25–50 %), media taza para empezar. Si aun así hinchan, lentejas.",
    )

    const val STOMACH_TIP = "Si el estómago protesta con comidas grandes, picante o refrescos con gas, los tres son el mismo mecanismo: volumen y gas. Comidas moderadas en vez de dos grandes, sin gasificados, picante a tolerancia, y nada de entrenar recién comido (60–90 min). Para el dolor de espalda, **paracetamol antes que naproxeno**: el naproxeno multiplica por 4–5 el riesgo de daño gástrico; el ibuprofeno, por menos de 2."

    const val FOOD_EVIDENCE = "Proteína total: por encima de ~1.6 g/kg/día no hay más ganancia de masa magra (Morton 2018, 49 estudios); reparto óptimo 0.4 g/kg por comida en 4 tomas (Schoenfeld & Aragon 2018). Péptidos y aminoácidos como estímulo principal de la gastrina (StatPearls). Frijoles: fermentación colónica, no ácido gástrico; fibra soluble y −60 % de úlcera duodenal (Aldoori 1997). Huevos: hasta 3/día en jóvenes sanos (J Nutr 2017); dosis-respuesta e hiperrespondedores 15–25 % (Vincent 2019); sin asociación hasta 1/día (BMJ 2020); 25/día, caso único (Kern, NEJM 1991). Grasa saturada <10 % (OMS 2023). Leche y rebote ácido. AINE: riesgo relativo naproxeno 4–5, ibuprofeno <2."

    // ---------- Suplementos ----------
    const val SUPP_INTRO = "Es la parte más divertida de investigar y la menos importante de todas. **No gastes nada el primer mes.** Entrena las cuatro semanas primero; si al final sigues, entonces compras."

    val supplements = listOf(
        "**Creatina — sí, más adelante.** Monohidrato, 3–5 g al día, a cualquier hora, sin fase de carga. Bien disuelta y con comida. Verifica que el bote diga monohidrato, no «HCL» ni un preentreno con creatina dentro.",
        "**Proteína en polvo — no hace falta.** Es comida en polvo; lo único que aporta es comodidad si no llegas comiendo.",
        "**Cuidado con el ganador de peso.** Es 80 % azúcar y cuesta el triple de lo que vale.",
    )

    const val CREATINE_WATER = "Con la fase de carga (20 g/día una semana) se ven 1–2 kg más en la báscula; con 3–5 g diarios la retención es pequeña o nula. Los efectos digestivos también dependen de la dosis: con 5 g son raros; con 10 g, la mitad de la gente tiene diarrea."

    const val SUPP_EVIDENCE = "3–5 g/día sin carga llenan los depósitos en 3–4 semanas; la carga es opcional (ISSN, postura oficial). Molestias digestivas dependientes de dosis: diarrea en 29 % con 5 g frente a 56 % con 10 g (Ostojic 2008)."

    // ---------- Grippers ----------
    const val GRIP_INTRO = "Si tienes un gripper de resistencia ajustable en kilos, sirve. Entrena **agarre de cierre** (apretar); lo que hace falta para la barra es **agarre de sostén** (aguantar colgado), que es otro patrón. Así que no sustituye los 15 segundos colgado. Donde sí suma es en el antebrazo."

    val gripProtocol = listOf(
        listOf("Ajuste", "La resistencia en la que 10–12 cierres completos cuesten y el último salga justo"),
        listOf("Series", "3 × 8–12 por mano · 60–90 s de descanso"),
        listOf("Ejecución", "Cierre completo, aguanta 1 segundo, abre despacio"),
        listOf("Progresión", "Cuando 3×12 salgan con las dos manos, sube 2.5–5 kg y vuelve a 8"),
        listOf("Equilibrio", "2 × 15 abriendo los dedos contra una liga de goma, para los extensores"),
        listOf("Días", "Domingo y jueves, justo al terminar la sesión de fuerza. Nunca el día antes de remo"),
    )

    val gripWarn = listOf(
        "**No a diario.** Los tendones de la mano y el codo se recuperan más lento que el músculo, y gripper todos los días + remos + curls es la receta clásica de codo de golfista (dolor en la cara interna del codo). Si aparece, los grippers son lo primero que se quita, y se vuelve a ellos cuando lleve dos semanas sin doler.",
    )

    const val GRIP_OUTRO = "Si a 20 repeticiones seguidas todavía no cuesta, el gripper está demasiado flojo y estás entrenando resistencia, no fuerza ni tamaño. Sube."

    const val GRIP_EVIDENCE = "Hipertrofia de antebrazo: 6–12 repeticiones cerca del fallo, igual que cualquier músculo; 100 repeticiones ligeras construyen resistencia, no tamaño. Cierre frente a sostén: los grippers solo trabajan el cierre; colgarse y peso muerto trabajan el sostén. Sesiones intensas 2–3 por semana como máximo por la recuperación tendinosa. Epicondilitis medial: lesión por sobreuso de flexores y agarre repetido; mejora en semanas o meses (StatPearls)."

    // ---------- Hábito y descanso ----------
    val restRules = listOf(
        "Descansar es válido solo con una de estas cuatro señales: **dolor agudo o localizado en una articulación** (no el dolor muscular difuso de las agujetas), **fiebre o enfermedad**, **dos noches seguidas de menos de 5 horas**, o un día de trabajo físico que te dejó la espalda cargada. Ese día la sesión se convierte en caminar 20 minutos, no en nada. Y la regla que sostiene todo: **nunca dos días cero seguidos**. Uno se absorbe; dos es donde se rompe el hábito.",
        "Las agujetas no cuentan como señal. Con agujetas se entrena, y se pasan antes.",
    )

    const val HABIT_EVIDENCE = "El hábito se automatiza en una mediana de 66 días (rango 18–254), con una señal de contexto estable y repetición diaria; saltarse un día suelto no afecta la formación de forma medible, la constancia sí (Lally 2010). Por eso el sábado tiene acción y no hueco."

    val calendarRules = listOf(
        "Semana impar" to "A · B · A",
        "Semana par" to "B · A · B",
        "Grippers" to "domingo y jueves, al terminar",
        "Cardio" to "sem 1–2 caminar 30–40 min · sem 3–4 trote 1 / caminar 2 × 8",
        "Sábado" to "ligero: caminar 20–30 + movilidad 10 · el último de cada ciclo, cinta y foto",
    )

    const val CALENDAR_INTRO = "De domingo a domingo, **sin día cero**. Fuerza domingo, martes y jueves; cardio lunes, miércoles y viernes; el sábado es **ligero**: caminar 20–30 min y 10 de movilidad. Los grippers van **después** de la fuerza del domingo y del jueves: así tienen siempre 48 horas o más antes del siguiente remo."

    const val CALENDAR_MISSED = "Si una semana falla un día, no lo recuperes: sigue el calendario como si nada. La alternancia A/B se mantiene por posición en la semana, no por lo que hiciste la última vez. Una sesión movida de día no es una sesión perdida; una saltada sí."

    const val BASELINE = "Antes de empezar: **cintura a la altura del ombligo y una foto**, misma luz y mismo sitio que usarás el último sábado del ciclo. Sin punto de partida no hay comparación."

    const val DOMS = "Las agujetas empiezan a las 12–24 h, pegan más fuerte entre las 24 y las 72, y se van en 5–7 días. Es normal, no es lesión. Y la segunda vez que hagas la misma sesión dolerán un 60–80 % menos: el cuerpo aprende rápido."

    // ---------- Medir ----------
    const val MEASURE_INTRO = "La báscula dice poco al principio. Un principiante con proteína suficiente está en la única ventana donde se gana músculo y se pierde grasa a la vez: el peso puede no moverse en meses mientras el cuerpo cambia por completo."

    val measureCues = listOf(
        "**Cintura con cinta métrica**, a la altura del ombligo, el mismo día de cada mes.",
        "**Una foto** cada 4 semanas, misma luz, mismo sitio.",
        "**Los números de la libreta.** Si no lo apuntas no hay progresión, hay improvisación. Incluye los kilos del gripper.",
    )

    const val MEASURE_OUTRO = "Y no existe la pérdida de grasa localizada: mil abdominales no queman la panza. Meter músculo en hombros y espalda cambia la silueta bastante más que perder dos kilos."

    const val MEASURE_EVIDENCE = "Ganancia realista el primer año: 0.5–1 kg de músculo al mes con todo bien hecho (Outlift). Contorno de brazo: 1.3–2.5 cm el primer año."

    val gateItems = listOf(
        "3 × 12 flexiones con las manos en el asiento del banco",
        "30 segundos colgado de la barra sin apoyar los pies",
    )

    const val DISCLAIMER = "Nada de esto sustituye a un médico: si algo duele de forma aguda o el cansancio no cuadra con el esfuerzo, es momento de consulta y de análisis, no de más entrenamiento."

    // ---------- Fuentes ----------
    val sources = listOf(
        Source("Morton et al. 2018, BJSM — proteína y masa magra", "https://pubmed.ncbi.nlm.nih.gov/28698222/"),
        Source("ISSN, postura oficial sobre creatina", "https://www.ncbi.nlm.nih.gov/pmc/articles/PMC2048496/"),
        Source("Ostojic 2008 — molestias digestivas y dosis de creatina", "https://pubmed.ncbi.nlm.nih.gov/18373286/"),
        Source("Ebben et al. 2011 — carga de cada variante de flexión (resumen)", "https://www.yourwellnessnerd.com/blogs/blog/research-how-heavy-are-push-ups"),
        Source("Modelo predictivo de flexión inclinada, Sci. Reports 2025", "https://www.nature.com/articles/s41598-025-28012-7"),
        Source("Schoenfeld 2016 — frecuencia de entrenamiento", "https://www.researchgate.net/publication/301578131_Effects_of_Resistance_Training_Frequency_on_Measures_of_Muscle_Hypertrophy_A_Systematic_Review_and_Meta-Analysis"),
        Source("Schoenfeld 2019 — frecuencia, actualización", "https://pubmed.ncbi.nlm.nih.gov/30558493/"),
        Source("Schoenfeld 2017 — series semanales", "https://pubmed.ncbi.nlm.nih.gov/27433992/"),
        Source("Schoenfeld & Aragon 2018 — proteína por comida", "https://www.tandfonline.com/doi/full/10.1186/s12970-018-0215-1"),
        Source("Barakat 2020 — recomposición corporal", "https://www.semanticscholar.org/paper/fa40632e786fa5a9b0409993ca8455cd53c8fc16"),
        Source("NASM — obesidad de peso normal", "https://www.nasm.org/resource-center/blog/fitness/skinny-fat"),
        Source("Press sin ayudante — Art of Manliness", "https://www.artofmanliness.com/health-fitness/fitness/the-4-rules-of-bench-pressing-without-a-spotter/"),
        Source("Fondos en banco, profundidad — FitCraft", "https://getfitcraft.com/exercises/bench-dips"),
        Source("Tríceps, anatomía — StatPearls", "https://www.ncbi.nlm.nih.gov/books/NBK536996/"),
        Source("Colgarse, progresión — Dead Hangs", "https://deadhangs.com/deadhang-progressions/"),
        Source("Fradkin 2010 — calentamiento y rendimiento, metaanálisis", "https://www.researchgate.net/publication/40483585_Effects_of_Warming-up_on_Physical_Performance_A_Systematic_Review_With_Meta-analysis"),
        Source("Kay & Blazevich 2012 — estiramiento estático y fuerza", "https://www.anatomytrains.com/wp-content/uploads/manual/acute_stretch.pdf"),
        Source("Simic 2013 — estiramiento previo, revisión", "https://onlinelibrary.wiley.com/doi/abs/10.1111/j.1600-0838.2012.01444.x"),
        Source("Calentamiento y prevención de lesiones — revisión 2025", "https://www.mdpi.com/2075-4663/14/5/187"),
        Source("Lesiones en programas de carrera — IJERPH 2023", "https://www.ncbi.nlm.nih.gov/pmc/articles/PMC10487403/"),
        Source("Progresión de carrera para principiantes — Pheidi", "https://pheidi.training/articles/beginner-running-progression/"),
        Source("MET de la cuerda — Elite Jumps", "https://elitejumps.co/blogs/guides/jump-rope-vs-other-forms-of-cardio"),
        Source("Seiler, distribución 80/20", "https://roadmancycling.com/blog/stephen-seiler-80-20-polarised-training-cyclists"),
        Source("Ecuación NIOSH — ErgoIBV", "https://www.ergoibv.com/en/posts/niosh-lifting-equation-method/"),
        Source("Técnica de levantamiento — QLS Safety", "https://safety.mlsascp.com/proper-lifting-technique-1.html"),
        Source("Gastrina, fisiología — StatPearls", "https://www.ncbi.nlm.nih.gov/books/NBK534822/"),
        Source("Aldoori 1997 — fibra y úlcera duodenal", "https://academic.oup.com/aje/article-pdf/145/1/42/150456/145-1-42.pdf"),
        Source("Remojo y oligosacáridos del frijol — J Nutr Sci Vitaminol", "https://www.jstage.jst.go.jp/article/jnsv1973/48/4/48_4_283/_article"),
        Source("Por qué los frijoles dan gas — Cleveland Clinic", "https://health.clevelandclinic.org/why-do-beans-make-you-fart"),
        Source("Kern 1991 — 25 huevos al día, NEJM", "https://www.nejm.org/doi/full/10.1056/NEJM199103283241306"),
        Source("Hasta 3 huevos/día en jóvenes sanos — J Nutr 2017", "https://www.sciencedirect.com/science/article/pii/S0022316622106760"),
        Source("Vincent 2019 — colesterol dietético, AJCN", "https://pubmed.ncbi.nlm.nih.gov/30596814/"),
        Source("Huevo y riesgo cardiovascular — BMJ 2020", "https://pubmed.ncbi.nlm.nih.gov/32132002/"),
        Source("OMS 2023 — grasa saturada", "https://www.ncbi.nlm.nih.gov/books/NBK594769/"),
        Source("Leche y rebote ácido — Ubie", "https://ubiehealth.com/doctors-note/does-milk-help-acid-reflux-relief-guide-37-science13e6"),
        Source("AINE y mucosa gástrica — PMC 2017", "https://pmc.ncbi.nlm.nih.gov/articles/PMC5478398/"),
        Source("Grippers: protocolo — BoxLife", "https://boxlifemagazine.com/forearm-training-showdown-gym-vs-rice-vs-grips/"),
        Source("Grippers: tipos de agarre — Active Forge", "https://activeforgepro.com/best-hand-grip-strengtheners-forearm-growth/"),
        Source("Epicondilitis medial — StatPearls", "https://www.ncbi.nlm.nih.gov/books/NBK519000/"),
        Source("Ritmo de ganancia muscular — Outlift", "https://outlift.com/how-much-muscle-can-you-gain-in-a-year-naturally/"),
        Source("Agujetas — Physiopedia", "https://www.physio-pedia.com/Delayed_Onset_Muscle_Soreness"),
        Source("Lally 2010 — formación de hábitos, Eur J Soc Psychol", "https://onlinelibrary.wiley.com/doi/10.1002/ejsp.674"),
    )
}

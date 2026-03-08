#set document(title: "ЛР1 Модульное тестирование")
#set page(paper: "a4", margin: (top: 2cm, bottom: 2cm, left: 2cm, right: 2cm))

#set text(lang: "ru", font: "Liberation Serif", size: 14pt)
#set par(justify: true, leading: 0.8em, first-line-indent: 1cm)
#set heading(numbering: "1.")
#show heading: set par(first-line-indent: 0pt)

#show raw: set text(font: "Liberation Mono")
#let source(path, lang) = block(
  fill: luma(240),
  stroke: 0.5pt + luma(180),
  inset: 10pt,
  radius: 4pt,
  below: 1.5em,
  raw(read(path), lang: lang, block: true),
)

// ─────────────────────────── Титульный лист ───────────────────────────

#align(center)[
  #set par(first-line-indent: 0pt)
  #text(size: 12pt)[
    Федеральное государственное автономное образовательное учреждение\
    высшего образования\
    *«Национальный исследовательский университет ИТМО»*
  ]

  #v(0.4cm)
  #text(size: 12pt)[Факультет программной инженерии и компьютерной техники]

  #v(3.5cm)

  #text(size: 16pt, weight: "bold")[
    Лабораторная работа №1\
    «Модульное тестирование»
  ]

  #v(0.6cm)
  #text(size: 13pt)[по дисциплине «Тестирование программного обеспечения»]
  #v(0.4cm)
  #text(size: 13pt)[Вариант: 413732]

  #v(5cm)

  #align(right)[
    #set par(first-line-indent: 0pt)
    #grid(
      columns: 10cm,
      row-gutter: 0.5em,
      align: (right, left),
      [*Студент:*],
      [Анкудинов Кирилл Константинович],
      [*Группа:*],
      [P3318],
      [*Преподаватель:*],
      [Егошин Алексей Васильевич],
    )
  ]

  #v(1fr)
  #text(size: 12pt)[Санкт-Петербург, 2026]
]

#pagebreak()

#set page(numbering: "1")
#counter(page).update(1)

// ─────────────────────────── Методика тестирования ───────────────────────────

#outline(
  title: [*Оглавление*],
  depth: 3,
)

#pagebreak()

// ─────────────────────────── Задание 1 ───────────────────────────

= Задание 1. Разложение $cos(x)$ в степенной ряд

== Описание реализации

Функция $cos(x)$ вычисляется через ряд Тейлора:

$ cos(x) = sum_(n=0)^(infinity) frac((-1)^n x^(2n), (2n)!) = 1 - frac(x^2, 2!) + frac(x^4, 4!) - dots $

Перед вычислением аргумент приводится к диапазону $[-π, π]$.
Суммирование продолжается до тех пор,
пока абсолютное значение очередного слагаемого не станет меньше `eps` (по-умолчанию - `Double.MIN_VALUE`).
== Методика

Тестирование проводится при помощи *анализа эквивалентности*.

$ frac(d, d x)cos(x) = 0 => -sin(x) = 0 => x = pi n, n in ZZ $
$ frac(d^2, d x^2)cos(x) = 0 => -cos(x) = 0 => frac(pi, 2) + pi n, n in ZZ $

#box(
  width: 100%,
  fill: rgb(235, 245, 255),
  inset: 1em,
  radius: 4pt,
  stroke: 1pt + rgb(0, 100, 200),
)[
  *Note:* $cos(x)$ --- функция с периодом $2pi$
]

- Граничные значения:
  + $x = 0 + 2pi n$
  + $x = frac(pi, 2) + 2pi n$
  + $x = pi + 2pi n$
  + $x = frac(3pi, 2) + 2pi n$

- Граничные значения (с точки зрения языка прогрммирования):
  + $x = "NaN"$
  + $x = ±infinity$

- Области эквивалентности:
  + $(0; frac(pi, 2)) + 2pi n$
  + $(frac(pi, 2); pi) + 2pi n$
  + $(pi; frac(3pi, 2)) + 2pi n$
  + $(frac(3pi, 2); 2pi) + 2pi n$

#pagebreak()

#block(above: 2em)[
  #figure(
    image("assets/images/cos.png", width: 100%),
    caption: [$cos(x)$],
  )
]

Тестовое покрытие:

#table(
  columns: (auto, 1fr, auto),
  stroke: 0.5pt,
  align: (left, left),
  fill: (_, row) => if row == 0 { luma(230) } else { none },
  [Аргумент $cos(x)$], [Ожидаемый результат], [Граничное значение?],
  [$x = "NaN"$], [$"NaN"$], [$checkmark$],
  [$x = ± infinity$], [$"NaN"$], [$checkmark$],
  [$x = 0$], [$1$], [$checkmark$],
  [$x = frac(pi, 4)$], [$0.7071067812$], [$crossmark$ (Область 1)],
  [$x = frac(pi, 2)$], [$0$], [$checkmark$],
  [$x = frac(3pi, 4)$], [$-0.7071067812$], [$crossmark$ (Область 2)],
  [$x = pi$], [$-1$], [$checkmark$],
  [$x = frac(5pi, 4)$], [$-0.7071067812$], [$crossmark$ (Область 3)],
  [$x = frac(3pi, 2)$], [$0$], [$checkmark$],
  [$x = frac(7pi, 4)$], [$0.7071067812$], [$crossmark$ (Область 4)],
  [$x = 2pi$], [$1$], [$checkmark$],
  [$x = -999$], [$0.999649853$], [$crossmark$ (Внешняя точка)],
  [$x = 777$], [$-0.5177182207$], [$crossmark$ (Внешняя точка)],
)

== Результаты

#block(above: 2em)[
  #figure(
    image("assets/results/task1.png", width: 100%),
    caption: [Результаты тестов для задания №1],
  )
]

#pagebreak()

// ─────────────────────────── Задание 2 ───────────────────────────

= Задание 2. Биномиальная куча

== Описание реализации

Биномиальная куча (Binomial Heap) — структура данных, представляющая собой
набор биномиальных деревьев, упорядоченных по степени. Реализованы ключевые методы:
- `insert`
- `extractMin`

== Методика тестирования
Тестирование проводится при помощи *таблицы решений*.

Условия — *наличие/отсутствие* деревьев рангов 0, 1, 2 в корневом списке до операции.
Действия — проверка соответствия внутреннего состояния теоретическому либо ожидание исключения.

=== Таблицы решений для `insert`

#text(size: 9pt)[
  #table(
    columns: (1.2fr, auto, auto, auto, auto, auto, auto, auto, auto),
    stroke: 0.5pt,
    align: (left, center, center, center, center, center, center, center, center),
    fill: (_, row) => if row == 0 or row == 5 { luma(240) } else { none },
    [*Таблица решений для `insert`*], [T1], [T2], [T3], [T4], [T5], [T6], [T7], [T8],
    [*Условия*], [], [], [], [], [], [], [], [],
    [$exists B_0$], [], [+], [], [], [+], [+], [], [+],
    [$exists B_1$], [], [], [+], [], [+], [], [+], [+],
    [$exists B_2$], [], [], [], [+], [], [+], [+], [+],
    [], [], [], [], [], [], [], [], [],
    [*Действия*], [], [], [], [], [], [], [], [],
    [Состояние = теоретическое], [+], [+], [+], [+], [+], [+], [+], [+],
  )
]

#v(1em)

=== Таблицы решений для `extractMin`

#text(size: 9pt)[
  #table(
    columns: (1.2fr, auto, auto, auto, auto, auto, auto, auto, auto),
    stroke: 0.5pt,
    align: (left, center, center, center, center, center, center, center, center),
    fill: (_, row) => if row == 0 or row == 5 { luma(240) } else { none },
    [*Таблица решений для `extractMin`*], [T1], [T2], [T3], [T4], [T5], [T6], [T7], [T8],
    [*Условия*], [], [], [], [], [], [], [], [],
    [$exists B_0$], [], [+], [], [], [+], [+], [], [+],
    [$exists B_1$], [], [], [+], [], [+], [], [+], [+],
    [$exists B_2$], [], [], [], [+], [], [+], [+], [+],
    [], [], [], [], [], [], [], [], [],
    [*Действия*], [], [], [], [], [], [], [], [],
    [Ожидаем исключение], [+], [], [], [], [], [], [], [],
    [Корректное значение min], [], [+], [+], [+], [+], [+], [+], [+],
    [Состояние = теоретическое], [], [+], [+], [+], [+], [+], [+], [+],
  )
]

#pagebreak()

== Результаты

#block(above: 2em)[
  #figure(
    image("assets/results/task2.png", width: 100%),
    caption: [Результаты тестов для задания №2],
  )
]

#pagebreak()

// ─────────────────────────── Задание 3 ───────────────────────────

= Задание 3. Доменная модель

== Исходный текст <domain-model-text>

#block(
  fill: luma(245),
  stroke: 0.5pt + luma(180),
  inset: 10pt,
  radius: 4pt,
  below: 1.5em,
)[
  #set par(first-line-indent: 0pt)
  _«Они сидели на мостовой и смотрели с некоторым беспокойством, как огромные
  дети тяжело прыгают по песку, а дикие лошади с грохотом везут по небу
  в Неизведанные Области свежие запасы армированных изгородей.»_
]

== Доменная модель

Из текста выделены следующие сущности и их отношения:

*\#TODO: insert UML diagram*

== Методика тестирования

Тестирование проводится при помощи *таблицы переходов*.

=== Таблицы переходов `Person`

Таблица `watch`:
#text(size: 9pt)[
  #table(
    columns: (auto, auto, 1fr, auto, 1fr),
    stroke: 0.5pt,
    align: (left, left, left, left, left),
    fill: (_, row) => if row == 0 { luma(230) } else { none },
    [№], [Текущее состояние], [Вход / условие], [Следующее состояние], [Ожидаемый результат],
    [1], [(нет объекта)], [`init`], [`CALM`], [`emotion == CALM`],
    [2], [`ANY`], [`watchChild(size = HUGE)`], [`SOMEWHAT_WORRIED`], [`emotion == SOMEWHAT_WORRIED`],
    [3], [`ANY`], [`watchChild(size = NORMAL)`], [`CALM`], [`emotion == CALM`],
    [4], [`ANY`], [`watchHorse(wild = true)`], [`WORRIED`], [`emotion == WORRIED`],
    [5], [`ANY`], [`watch(null)`], [(исключение)], [`IllegalArgumentException`],
  )
]

#block(above: 2em)[
  #figure(
    image("assets/state_machines/person_emotion.png", width: 100%),
    caption: [Конечный автомат эмоций `Person`],
  )
]

#pagebreak()

Таблица `sitOn`:
#text(size: 9pt)[
  #table(
    columns: (auto, auto, 1fr, auto, 1fr),
    stroke: 0.5pt,
    align: (left, left, left, left, left),
    fill: (_, row) => if row == 0 { luma(230) } else { none },
    [№], [Текущее состояние], [Вход / условие], [Следующее состояние], [Ожидаемый результат],
    [1], [(нет объекта)], [`init()`], [`NO_LOCATION`], [`location == null`],
    [2], [`NO_LOCATION`], [`sitOn(location != null)`], [`AT_LOCATION(L)`], [`location == L`],
    [3], [`NO_LOCATION`], [`sitOn(null)`], [(исключение)], [`IllegalArgumentException("Location cannot be null")`],
    [4],
    [`AT_LOCATION(L)`],
    [`sitOn(location == L)`],
    [(исключение)],
    [`IllegalArgumentException("Person is already at this location")`],

    [5], [`AT_LOCATION(L1)`], [`sitOn(location = L2), L2 != null, L2 != L1`], [`AT_LOCATION(L2)`], [`location == L2`],
  )
]

#block(above: 2em)[
  #figure(
    image("assets/state_machines/person_location.png", width: 100%),
    caption: [Конечный автомат местоположений `Person`],
  )
]

#pagebreak()

=== Таблицы переходов `Child`

#text(size: 9pt)[
  #table(
    columns: (auto, auto, 4cm, auto, 4cm),
    // Фиксированная ширина для текстовых колонок
    stroke: 0.5pt,
    align: (left, left, left, left, left),
    fill: (_, row) => if row == 0 { luma(230) } else { none },
    [№], [Текущее состояние], [Вход / условие], [Следующее состояние], [Ожидаемый результат],
    [1], [(нет объекта)], [`init(size != null)`], [`NO_LOCATION`], [```location == null```, размер сохранён],
    [2], [(нет объекта)], [`init(size == null)`], [(исключение)], [`IllegalArgumentException("Size cannot be null")`],
    [3], [`NO_LOCATION`], [`jumpOn(sand != null)`], [`ON_SAND`], [`location == sand`],
    [4], [`NO_LOCATION`], [`jumpOn(sand == null)`], [(исключение)], [`IllegalArgumentException("Sand cannot be null")`],
    [5], [`ON_SAND`], [`jumpOn(sand != null)`], [`ON_SAND`], [Локация меняется на новый `sand`],
  )
]

#block(above: 2em)[
  #figure(
    image("assets/state_machines/child.png", width: 100%),
    caption: [Конечный автомат прыжков `Child` на песке],
  )
]

#pagebreak()

=== Таблицы переходов `Horse`

#text(size: 9pt)[
  #table(
    columns: (auto, auto, 1fr, auto, 1fr),
    stroke: 0.5pt,
    align: (left, left, left, left, left),
    fill: (_, row) => if row == 0 { luma(230) } else { none },
    [№], [Текущее состояние], [Вход / условие], [Следующее состояние], [Ожидаемый результат],
    [1], [(нет объекта)], [`init(wild)`], [`NO_DESTINATION`], [`location == null`],
    [2],
    [`NO_DESTINATION`],
    [`pull(cargo != null, sky != null, destination != null)`],
    [`AT_UNKNOWN_REGIONS`],
    [`location == destination`],

    [3],
    [`NO_DESTINATION`],
    [`pull(cargo == null, sky != null, destination != null)`],
    [(исключение)],
    [`IllegalArgumentException("Cargo cannot be null")`],

    [4],
    [`NO_DESTINATION`],
    [`pull(cargo != null, sky == null, destination != null)`],
    [(исключение)],
    [`IllegalArgumentException("'Through' cannot be null")`],

    [5],
    [`NO_DESTINATION`],
    [`pull(cargo != null, sky != null, destination == null)`],
    [(исключение)],
    [`IllegalArgumentException("Destination cannot be null")`],
  )
]

#block(above: 2em)[
  #figure(
    image("assets/state_machines/horse.png", width: 100%),
    caption: [Конечный автомат местоположений `Horse`],
  )
]

#pagebreak()

=== Таблицы переходов `Supply`

#text(size: 9pt)[
  #table(
    columns: (auto, auto, 1fr, auto, 1fr),
    stroke: 0.5pt,
    align: (left, left, left, left, left),
    fill: (_, row) => if row == 0 { luma(230) } else { none },
    [№], [Текущее состояние], [Вход / условие], [Следующее состояние], [Ожидаемый результат],
    [1],
    [(нет объекта)],
    [`init(fences == null)`],
    [(исключение)],
    [`IllegalArgumentException("Supply must contain at least one fence")`],

    [2],
    [(нет объекта)],
    [`init(fences.isEmpty())`],
    [(исключение)],
    [`IllegalArgumentException("Supply must contain at least one fence")`],

    [3], [(нет объекта)], [`init(all fences FRESH)`], [`FRESH`], [`getOverallFreshness() == Freshness.FRESH`],
    [4], [(нет объекта)], [`init(any fence STALE)`], [`STALE`], [`getOverallFreshness() == Freshness.STALE`],
  )
]

#block(above: 2em)[
  #figure(
    image("assets/state_machines/supply.png", width: 100%),
    caption: [Конечный автомат свежести `Supply`],
  )
]

#pagebreak()

== Результаты

#block(above: 2em)[
  #figure(
    image("assets/results/task3.png", width: 100%),
    caption: [Результаты тестов для задания №3],
  )
]

// ─────────────────────────── Заключение ───────────────────────────

= Заключение

В ходе лабораторной работы было реализовано и протестировано три программных модуля при помощи различных методик тестирования:
- Задание №1 - Метод "Анализ эквивалентности"
- Задание №2 - Метод "Таблица решений"
- Задание №3 - Метод "Таблица переходов"


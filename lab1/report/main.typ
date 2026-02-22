#set document(title: "ЛР1 Модульное тестирование")
#set page(paper: "a4", margin: (top: 2cm, bottom: 2cm, left: 2cm, right: 2cm))

#set text(lang: "ru", font: "Liberation Serif", size: 14pt)
#set par(justify: true, leading: 0.8em, first-line-indent: 1cm)
#set heading(numbering: "1.")
#show heading: set par(first-line-indent: 0pt)

#show raw: set text(font: "Liberation Mono", size: 11pt)
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

= Методика тестирования

Лабораторная работа состоит из трёх заданий, для каждого из которых применялось
модульное тестирование методом «белого ящика» с использованием фреймворка *JUnit 5*. Сборка и тестирование проекта выполняется с помощью *Gradle*. Тестируемый код
написан на *Java 17*.

Для каждого задания тесты запускались командой:

```bash
./gradlew test        # All tests
./gradlew :task1:test # Tests of task 1
./gradlew :task2:test # Tests of task 2
./gradlew :task3:test # Tests of task 3
```

#pagebreak()

// ─────────────────────────── Задание 1 ───────────────────────────

= Задание 1. Разложение $cos(x)$ в степенной ряд

== Описание реализации

Функция $cos(x)$ вычисляется через ряд Тейлора:

$ cos(x) = sum_(n=0)^(infinity) frac((-1)^n x^(2n), (2n)!) = 1 - frac(x^2, 2!) + frac(x^4, 4!) - dots $

Перед вычислением аргумент приводится к диапазону $[-π, π]$.
Суммирование продолжается до тех пор,
пока абсолютное значение очередного слагаемого не станет меньше `Double.MIN_VALUE`,
либо не будет достигнуто максимальное число итераций.

Особые случаи обрабатываются явно: при $x = "NaN"$ или $x = ±infinity$ метод возвращает
`NaN`.

== Тестовые случаи

Тесты разделены на две группы:
+ *Граничные значения* (`"Corner values"`) — 13 тест-кейсов:
+ *Диапазон $[-π, π]$* (`"[-PI; PI] range"`) — 16 тест-кейсов из файла `values.csv`:

Для обеих групп используется `assertEquals(...)` с `TOLERANCE = 1e-5`.

== Результаты

#source("assets/task1.result.txt", "text")

Все 29 тестов пройдены успешно. Неудачных тестов нет.

#pagebreak()

// ─────────────────────────── Задание 2 ───────────────────────────

= Задание 2. Биномиальная куча

== Описание реализации

Биномиальная куча (Binomial Heap) — структура данных, представляющая собой
набор биномиальных деревьев, упорядоченных по степени. Реализованы операции:
`insert`, `getMin`, `extractMin`. Слияние куч (`merge`) является вспомогательной
операцией, используемой внутри остальных.

Для сохранения трэйса выполнения, в реализацию добавлено поле класса `TraversalTracer`.
Характерные точки выделены в `enum` `TracePoint`:

#table(
  columns: (auto, 1fr),
  stroke: 0.5pt,
  align: (left, left),
  fill: (_, row) => if row == 0 { luma(230) } else { none },
  [*Точка*], [*Место вызова*],
  [`INSERT`], [Начало метода `insert`],
  [`MERGE`], [Начало метода `merge`],
  [`MERGE_LISTS`], [Слияние двух корневых списков по степени в `mergeLists`],
  [`CONSOLIDATE`], [Устранение деревьев одинаковой степени в `consolidate`],
  [`EXTRACT_MIN`], [Начало метода `extractMin`],
  [`GET_MIN`], [Начало метода `getMin`],
  [`REVERSE`], [Разворот списка дочерних узлов в `reverseList`],
)

== Тестовые случаи

#table(
  columns: (1fr, auto),
  stroke: 0.5pt,
  align: (left, left),
  fill: (_, row) => if row == 0 { luma(230) } else { none },
  [*Сценарий*], [*Ожидаемый результат*],
  [Вставка в пустую кучу], [`INSERT → MERGE`],
  [Вставка второго элемента (слияние двух $T_0$)], [`INSERT → MERGE → MERGE_LISTS`],
  [Вставка третьего элемента (слияние $T_1$ и $T_0$)], [`INSERT → MERGE → MERGE_LISTS → CONSOLIDATE`],
  [`getMin` при одном элементе], [`GET_MIN`],
  [`extractMin` при одном элементе], [`EXTRACT_MIN`],
  [`extractMin` при двух элементах (с разворотом детей)], [`EXTRACT_MIN → REVERSE → MERGE`],
  [Корректность порядка извлечения (5 элементов)], [Проверка значений, без трэйса],
  [`getMin` на пустой куче], [`NoSuchElementException`],
  [`extractMin` на пустой куче], [`NoSuchElementException`],
)

== Результаты

#source("assets/task2.result.txt", "text")

Все 9 тестов пройдены успешно. Неудачных тестов нет.

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

#table(
  columns: (auto, 1fr),
  stroke: 0.5pt,
  align: (left, left),
  fill: (_, row) => if row == 0 { luma(230) } else { none },
  [*Класс / тип*], [*Ключевые атрибуты*],
  [`Person`], [`location`, `emotion`],
  [`Child`], [`size`, `location`],
  [`Horse`], [`wild: boolean`, `location`],
  [`Supply`], [`fences: List<ReinforcedFence>`],
  [`ReinforcedFence`], [`freshness: Freshness`, `reinforced = true`],
  [`Location`], [`getName(): String`],
  [`Pavement`], [impl. `Location`],
  [`Sand`], [impl. `Location`],
  [`Sky`], [impl. `Location`],
  [`UnknownRegions`], [impl. `Location`],
  [`Size`], [`HUGE`, `NORMAL`],
  [`Freshness`], [`FRESH`, `STALE`],
  [`Emotion`], [`CALM`, `SOMEWHAT_WORRIED`, `WORRIED`],
  [`Observable`], [Маркерный интерфейс],
)

== Тестовые случаи

Проверяют, что сущности, их поведение и свойства соответствуют тому,
что описано в #link(<domain-model-text>)[исходном текста]

#table(
  columns: (auto, 1fr),
  stroke: 0.5pt,
  align: (left, left),
  fill: (_, row) => if row == 0 { luma(230) } else { none },
  [*Тест*], [*Что проверяется*],
  [`theyAreOnThePavement`], [Оба человека находятся на мостовой],
  [`pavementIsCalledPavement`], [Название локации «мостовая»],
  [`watchingHugeChildCausesSomeWorry`], [Наблюдение за ребёнком → `SOMEWHAT_WORRIED`],
  [`worryIsSomeNotExtreme`], [Состояние не равно `WORRIED` (не полное беспокойство)],
  [`childrenAreHuge`], [Размер ребёнка — `HUGE`],
  [`childrenAreNotNormalSized`], [Размер не равен `NORMAL`],
  [`childrenJumpOnSand`], [Ребёнок прыгает на песок],
  [`sandIsCalledSand`], [Название локации «песок»],
  [`childHasNoLocationBeforeJumping`], [Новый ребёнок не имеет местоположения],
  [`horsesAreWild`], [Лошадь является дикой],
  [`horsesArriveAtUnknownRegions`], [Лошадь прибывает в Неизведанные Области],
  [`unknownRegionsIsCalledCorrectly`], [Название «Неизведанные Области»],
  [`routeGoesthroughTheSky`], [Название маршрута «небо»],
  [`horseHasNoDestinationBeforePulling`], [Новая лошадь не имеет местоположения],
  [`suppliesAreFresh`], [Запас из свежих изгородей — свежий],
  [`supplyWithOneStaleFenceIsNotFresh`], [Запас с одной несвежей изгородью — не свежий],
  [`allFencesInSupplyAreReinforced`], [Все изгороди в запасе армированы],
  [`fenceIsAlwaysReinforced...`], [Армированность не зависит от свежести],
)

== Результаты

#source("assets/task3.result.txt", "text")

Все 18 тестов пройдены успешно. Неудачных тестов нет.

// ─────────────────────────── Заключение ───────────────────────────

= Заключение

В ходе лабораторной работы было реализовано и протестировано три программных модуля.
Итоговая статистика:
#table(
  columns: (1fr, auto, auto, auto, auto),
  stroke: 0.5pt,
  align: (left, right, right, right, right),
  fill: (_, row) => if row == 0 { luma(230) } else { none },
  [*Подпроект*], [*Всего*], [*Пройдено*], [*Ошибок*], [*Пропущено*],
  [task1 ($cos(x)$)], [29], [29], [0], [0],
  [task2 (Binomial Heap)], [9], [9], [0], [0],
  [task3 (Domain model)], [18], [18], [0], [0],
  [*Итого*], [*56*], [*56*], [*0*], [*0*],
)

Все 56 тестов пройдены успешно, сбоев нет.

#set document(title: "ЛР2 Интеграционное тестирование")
#set page(paper: "a4", margin: (top: 2cm, bottom: 2cm, left: 2cm, right: 2cm))

#set text(lang: "ru", font: "Liberation Serif", size: 14pt)
#set par(justify: true, leading: 0.8em)
#set heading(numbering: "1.")
#show heading: set par(first-line-indent: 0pt)

#show link: it => {
  text()[#underline([#it.body])]
}

#set math.equation(numbering: "(1)")

#show raw: set text(font: "Liberation Mono")
#let source(path, lang) = block(
  fill: luma(240),
  stroke: 0.5pt + luma(180),
  inset: 10pt,
  radius: 4pt,
  below: 1.5em,
  raw(read(path), lang: lang, block: true),
)

#let render-test-data(csv-path) = {
  let data = csv(csv-path, delimiter: ";")
  let headers = data.first()
  let boundary-col = headers.len() - 1
  let columns-spec = headers
    .enumerate()
    .map(((i, _)) => {
      // колонка со значением функции обычно предпоследняя
      if i == headers.len() - 2 { 1fr } else { auto }
    })

  table(
    stroke: 0.5pt,
    fill: (_, row) => if row == 0 { luma(230) } else { none },
    columns: columns-spec,
    // Set up header
    table.header(..headers.map(h => [*#h*])),
    ..data
      .slice(1)
      .map(row => {
        row
          .enumerate()
          .map(((i, cell)) => {
            if i == boundary-col {
              // колонка is_boundary
              if cell == "true" {
                text(fill: green, sym.checkmark)
              } else {
                text(fill: red, sym.crossmark)
              }
            } else {
              cell // остальные как есть
            }
          })
      })
      .flatten(),
  )
}

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
    Лабораторная работа №2\
    "Интеграционное тестирование"
  ]

  #v(0.6cm)
  #text(size: 13pt)[по дисциплине «Тестирование программного обеспечения»]
  #v(0.4cm)
  #text(size: 13pt)[Вариант: 18001]

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

= Задание
Провести интеграционное тестирование программы, осуществляющей вычисление системы функций:

#text(size: 11pt)[
  $
    f(x) = cases(
      csc(x) "if" x <= 0,
      ((((log_5(x)^3) + log_5(x)) - log_3(x)) * ln(x)) - ((log_2(x) * (log_2(x) + ln(x))) / ((log_5(x) - (log_3(x)^2))^2)) "if" x > 0
    )
  $
]
= Математическое обоснование

== Базовые функции и их разложение в ряды

=== Синус

$ sin(x) = sum_(n=0)^(infinity) frac((-1)^n x^(2n), (2n)!) = 1 - frac(x^2, 2!) + frac(x^4, 4!) - dots $

=== Натуральный логарифм

$ ln(1 + x) = sum_(n=0)^(infinity) frac((-1)^n x^(n+1), (n+1)) = x - frac(x^2, 2) + frac(x^3, 3) - dots $ <ln-taylor>

Необходимо вычислить значение функции $ln(a)$ для произвольного положительного аргумента $a > 0$ с использованием разложения в ряд.

Прямое применение стандартного ряда Тейлора (См. @ln-taylor)
ограничено интервалом сходимости $-1 < x <= 1$,
что соответствует значениям $a in (0, 2]$.
Для аргументов вне этого интервала ряд расходится или сходится крайне медленно.

Используем свойство логарифмической функции:
$ ln a = ln (m dot 2^k) = ln m + k dot ln 2, $
где $k in ZZ$, а мантисса $m$ приведена к интервалу $[1; 2)$. Такое представление существует и единственно для любого положительного $a$. Константа $ln 2$ может быть предвычислена с необходимой точностью.

#pagebreak()

Для вычисления $ln m$ при $m in [1; 2)$ используем ряд Меркатора, основанный на представлении аргумента в виде дробно-линейной функции. Выполним замену:
$ m = (1+y)/(1-y), $ откуда $ y = (m-1)/(m+1). $
При $m in [1; 2)$ получаем $y in [0; 1/3)$, что обеспечивает быструю сходимость ряда.

Логарифм от данного выражения раскладывается в ряд:
$ ln((1+y)/(1-y)) = 2(y + y^3/3 + y^5/5 + y^7/7 + dots) = 2 sum_(n=0)^oo y^(2n+1)/(2n+1). $

Объединяя полученные выражения, получаем рабочую формулу для вычисления натурального логарифма произвольного положительного числа:
$ ln a = k dot ln 2 + 2 sum_(n=0)^N 1/(2n+1) ((m-1)/(m+1))^(2n+1) $

Где:
- $a = m dot 2^k$, $m in [1; 2)$, $k in ZZ$;
- $N$ — количество членов ряда;

== Выражение остальных функций через базовые

- $csc(x) = 1/sin(x)$

- $log_a (x) = ln(x)/ln(a)$

== Область допустимых значений

#let wolfram-url = "https://www.wolframalpha.com/input?i=%28%28%28%28%28log_5%28x%29+%5E+3%29+%2B+log_5%28x%29%29+-+log_3%28x%29%29+*+ln%28x%29%29+-+%28%28log_2%28x%29+*+%28log_2%28x%29+%2B+ln%28x%29%29%29+%2F+%28%28log_5%28x%29+-+%28log_3%28x%29+%5E+2%29%29+%5E+2%29%29%29"

+ $csc(x)$  не определён при $sin(x) = 0$, т.е. при $x = pi n, n in ZZ$

+ Для выражения с логарифмами:
  - Все логарифмы требуют $x > 0$
  - Согласно #link(wolfram-url)[WolframAlpha], область допустимых значений:

    $ D = {x in RR: x > 0, x != 1, x != 3^(1 div log_3(5)) } $

= Архитектура приложения

// TODO: UML HERE

= Модульное тестирование

Прежде чем проводить интеграционное тестирование,
необходимо убедится, что каждый элемент системы работает корректно в изоляции от остальных.

== `Sine`

=== Методика тестирования

Тестирование проводится при помощи *анализа эквивалентности*.

- Граничные значения:
  + $x = 0 + 2pi n$
  + $x = frac(pi, 2) + 2pi n$
  + $x = pi + 2pi n$
  + $x = frac(3pi, 2) + 2pi n$

- Области эквивалентности:
  + $(0; frac(pi, 2)) + 2pi n$
  + $(frac(pi, 2); pi) + 2pi n$
  + $(pi; frac(3pi, 2)) + 2pi n$
  + $(frac(3pi, 2); 2pi) + 2pi n$

#block(above: 2em)[
  #figure(
    image("assets/graphs/sin.png", width: 100%),
    caption: [$sin(x)$],
  )
]

#pagebreak()

Тестовое покрытие:
#render-test-data("test_resources/sin.csv")

=== Результаты

#block(above: 2em)[
  #figure(
    image("assets/results/module/sin.png", width: 100%),
    caption: [Результаты модульного теста для $sin(x)$],
  )
]

#pagebreak()

== `Cosecans`

=== Методика тестирования

Тестирование проводится при помощи *анализа эквивалентности*.

- Граничные значения:
  + $x = 0 + 2pi n$
  + $x = frac(pi, 2) + 2pi n$
  + $x = pi + 2pi n$
  + $x = frac(3pi, 2) + 2pi n$

- Области эквивалентности:
  + $(0; frac(pi, 2)) + 2pi n$
  + $(frac(pi, 2); pi) + 2pi n$
  + $(pi; frac(3pi, 2)) + 2pi n$
  + $(frac(3pi, 2); 2pi) + 2pi n$

#block(above: 2em)[
  #figure(
    image("assets/graphs/csc.png", width: 100%),
    caption: [$csc(x)$],
  )
]

#pagebreak()

Тестовое покрытие для числовых результатов:
#render-test-data("test_resources/csc.csv")

Тестовое покрытие для исключений:
#table(
  columns: (auto, 1fr, auto),
  stroke: 0.5pt,
  align: (left, left),
  fill: (_, row) => if row == 0 { luma(230) } else { none },
  [*x*], [*csc(x)*], [*is_boundary*],
  [0], [`ArithmeticException`], text(fill: green, sym.checkmark),
  [3.14159265], [`ArithmeticException`], text(fill: green, sym.checkmark),
)

=== Результаты

#block(above: 2em)[
  #figure(
    image("assets/results/module/csc.png", width: 100%),
    caption: [Результаты модульного теста для $csc(x)$],
  )
]

#pagebreak()

== `NaturalLogarithm`

=== Методика тестирования

Тестирование проводится при помощи *анализа эквивалентности*.

- Граничные значения:
  + $x = 0$ (граница области определения)

- Области эквивалентности:
  + $(-oo; 0]$ (не определён)
  + $(0; +oo)$

Тестовое покрытие для числовых результатов:
#render-test-data("test_resources/ln.csv")

Тестовое покрытие для исключений:
#table(
  columns: (auto, 1fr, auto),
  stroke: 0.5pt,
  align: (left, left),
  fill: (_, row) => if row == 0 { luma(230) } else { none },
  [*x*], [*ln(x)*], [*is_boundary*],
  [0], [`ArithmeticException`], text(fill: green, sym.checkmark),
  [-1], [`ArithmeticException`], text(fill: red, sym.crossmark),
)

=== Результаты

#block(above: 2em)[
  #figure(
    image("assets/results/module/ln.png", width: 100%),
    caption: [Результаты модульного теста для $ln(x)$],
  )
]

#pagebreak()

== `BaseNLogarithm`

=== Методика тестирования

Тестирование проводится при помощи *анализа эквивалентности*.

- Граничные значения:
  + $x = 0$ (граница области определения)

- Области эквивалентности:
  + $(-oo; 0]$ (не определён)
  + $(0; +oo)$

Тестовое покрытие для числовых результатов:
#render-test-data("test_resources/log_base_n.csv")

Тестовое покрытие для исключений:
#table(
  columns: (auto, 1fr, auto),
  stroke: 0.5pt,
  align: (left, left),
  fill: (_, row) => if row == 0 { luma(230) } else { none },
  [*x*], [*log_a(x)*], [*is_boundary*],
  [$0$], [`ArithmeticException`], text(fill: green, sym.checkmark),
  [$-1$], [`ArithmeticException`], text(fill: red, sym.crossmark),
)

=== Результаты

#block(above: 2em)[
  #figure(
    image("assets/results/module/log_n.png", width: 100%),
    caption: [Результаты модульного теста для $log_n(x)$],
  )
]

#pagebreak()

== `EquationSystem`

=== Методика тестирования

Тестирование проводится при помощи *анализа эквивалентности*.

- Граничные значения:
  + $x <= 0$: (Почти аналогично Cosecant)
    + $x = 0 - 2pi n$
    + $x = -frac(pi, 2) - 2pi n$
    + $x = -pi - 2pi n$
    + $x = -frac(3pi, 2) - 2pi n$
  + $x = 1$ — знаменатель логарифмической части обращается в нуль
  + $x = 3^(1 div log_3(5))$ — знаменатель логарифмической части обращается в нуль

- Области эквивалентности:
  + $x <= 0$: (Почти аналогично Cosecant)
    + $(0; -frac(pi, 2)) - 2pi n$
    + $(-frac(pi, 2); -pi) - 2pi n$
    + $(-pi; -frac(3pi, 2)) - 2pi n$
    + $(-frac(3pi, 2); -2pi) - 2pi n$

  + $(0; 1)$
  + $(1; 3^(1 div log_3(5)))$
  + $(3^(1 div log_3(5)); +oo)$

Тестовое покрытие для числовых результатов:

#render-test-data("test_resources/equation_system.csv")

Тестовое покрытие для исключений:

#table(
  columns: (auto, 1fr, auto),
  stroke: 0.5pt,
  fill: (_, row) => if row == 0 { luma(230) } else { none },
  table.header([*x*], [*f(x)*], [*is_boundary*]),
  [$0$], [`ArithmeticException` ($csc$ не определён)], text(fill: green, sym.checkmark),
  [$-pi$], [`ArithmeticException` ($csc$ не определён)], text(fill: green, sym.checkmark),
  [$1$], [`ArithmeticException` (знаменатель $= 0$)], text(fill: green, sym.checkmark),
  [$3^(1 div log_3(5)$], [`ArithmeticException` (знаменатель $= 0$)], text(fill: green, sym.checkmark),
)

=== Результаты

#block(above: 2em)[
  #figure(
    image("assets/results/module/system.png", width: 100%),
    caption: [Результаты модульного теста для $f(x)$ (системы)],
  )
]

#pagebreak()

= Интеграционное тестирование

Для интеграционного тестирования выбрана стратегия *Bottom-Up* (снизу вверх):

*Интеграционные* тесты подключают реальные реализации зависимостей:

#table(
  columns: (auto, 1fr),
  stroke: 0.5pt,
  align: (left, left),
  fill: (_, row) => if row == 0 { luma(230) } else { none },
  [*Тест*], [*Зависимости*],
  [`CosecantIntegrationTest`], [`Sine`],
  [`BaseNLogarithmIntegrationTest`], [`NaturalLogarithm`],
  [`EquationSystemIntegrationTest`],
  [
    - `Cosecant`
    - `BaseNLogarithm`
    - `NaturalLogarithm`
  ],
)

=== Результаты

#block(above: 2em)[
  #figure(
    image("assets/results/integration/csc.png", width: 100%),
    caption: [Результаты интеграционного тестирования для $csc(x)$],
  )
]

#block(above: 2em)[
  #figure(
    image("assets/results/integration/log_n.png", width: 100%),
    caption: [Результаты интеграционного тестирования для $log_n(x)$],
  )
]

#block(above: 2em)[
  #figure(
    image("assets/results/integration/system.png", width: 100%),
    caption: [Результаты интеграционного тестирования для $f(x)$],
  )
]

=== Графики из CSV.

Действия:

+ Получение `CSV` файлов:

  ```bash
  ./gradlew run --args='--start=-5 --stop=5 --step=0.001 --functions=all'
  ```
+ Построение графиков

  ```bash
  python visualizer/main.py output
  ```


#block(above: 2em)[
  #figure(
    image("assets/visualizer/all.png", width: 100%),
    caption: [Визуализация графиков, построенныя на основе CSV файлов],
  )
]

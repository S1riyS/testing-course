
#set document(title: "ЛР3 Функциональное тестирование UI")
#set page(paper: "a4", margin: (top: 2cm, bottom: 2cm, left: 2cm, right: 2cm))

#set text(lang: "ru", font: "Liberation Serif", size: 14pt)
#set par(justify: true, leading: 0.5em)
#set heading(numbering: "1.")
#show heading: set par(first-line-indent: 0pt)

#show link: it => {
  text()[#underline([#it.body])]
}

#show raw: set text(font: "Liberation Mono")

#let callout(
  title: none,
  color: blue,
  body,
) = {
  let base-color = color
  let bg-color = color.transparentize(90%)

  block(
    width: 100%,
    inset: (x: 1em, y: 0.7em),
    fill: bg-color,
    stroke: (left: (thickness: 4pt, paint: base-color)),
    radius: 0.3em,
    {
      if title != none {
        strong(text(fill: base-color, title))
        linebreak()
      }
      body
    },
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
    Лабораторная работа №4\
    "Нагрузочное и стресс тестирования"
  ]

  #v(0.6cm)
  #text(size: 13pt)[по дисциплине «Тестирование программного обеспечения»]
  #v(0.4cm)
  #text(size: 13pt)[Вариант: auto]

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

#outline(
  title: [*Оглавление*],
  depth: 3,
)

#pagebreak()

= Параметры тестирования

Конгфигурации:

- URL первой конфигурации (\$ 4300)

  http://stload.se.ifmo.ru:8080?token=519250419&user=-1333020361&config=1;

- URL второй конфигурации (\$ 7300)

  http://stload.se.ifmo.ru:8080?token=519250419&user=-1333020361&config=2;

- URL третьей конфигурации (\$ 12200)

  http://stload.se.ifmo.ru:8080?token=519250419&user=-1333020361&config=3;

Параметры:

- Максимальное количество параллельных пользователей - *10*
- Средняя нагрузка, формируемая одним пользователем - *40 запр. в мин.*
- Максимально допустимое время обработки запроса - *600 мс*

#pagebreak()

= Нагрузочное тестирование

== Конфигурация Jmeter

=== Общая конфигурация

Конфигурация нагрузочного тестирования состоит из 3 Thread Group
объектов, по 1 на каждую конфигурацию, конфигурация каждого из них
идентична, за исключением параметра config при формировании запроса

#align(center)[
  #block(above: 1em)[
    #figure(
      image("assets/load_cfg_general.png", width: 50%),
      caption: [Общая конфигурация \ нагрузочного тестирования],
    )
  ]
]

=== Thread Group

#align(center)[
  #block(above: 1em)[
    #figure(
      image("assets/load_cfg_thread_group.png", width: 80%),
      caption: [Конфигурация Thread Group \ нагрузочного тестирования],
    )
  ]
]

=== HTTP Request

#align(center)[
  #block(above: 1em)[
    #figure(
      image("assets/load_cfg_http_request.png", width: 100%),
      caption: [Конфигурация HTTP Request \ нагрузочного тестирования],
    )
  ]
]

=== Duration Assertion

#align(center)[
  #block(above: 1em)[
    #figure(
      image("assets/load_cfg_duration_assertion.png", width: 100%),
      caption: [Конфигурация Duration Assertion \ нагрузочного тестирования],
    )
  ]
]

=== Constant Throughput Timer

#align(center)[
  #block(above: 1em)[
    #figure(
      image("assets/load_cfg_constant_throughput.png", width: 100%),
      caption: [Конфигурация Constant Throughput Timer \ нагрузочного тестирования],
    )
  ]
]

#pagebreak()

== Результаты

#align(center)[
  #block(above: 1em)[
    #figure(
      image("assets/load_result_response_time.png", width: 100%),
      caption: [Response Time Graph нагрузочного тестирования],
    )
  ]
]

#align(center)[
  #block(above: 1em)[
    #figure(
      image("assets/load_result_aggregate_graph.png", width: 100%),
      caption: [Aggregate Graph нагрузочного тестирования],
    )
  ]
]

#align(center)[
  #block(above: 1em)[
    #figure(
      image("assets/load_result_statistic.png", width: 100%),
      caption: [Statistics нагрузочного тестирования],
    )
  ]
]

#align(center)[
  #block(above: 1em)[
    #figure(
      image("assets/load_result_time_distribution.png", width: 100%),
      caption: [Statistics нагрузочного тестирования],
    )
  ]
]

== Выбор конфигурации

Исходя из результатов нагрузочного тестирования, можно сделать вывод, что ни одна конфигурация не укладывается в требования.
Однако с учетом отсутсвия альтернатив сделаем выбор в пользу *Кофигурации №3*,
так как она показала лучший (из худших) результатов:
- _Avg_: 704 ms
- _95%_: 770 ms
- _Max_: 772 ms

= Стресс тестирование

== Конфигурация Jmeter

- _Request_: Использует параметры *Конфигурации №3*
- _Constant Throughput Time_: Аналогично нагрузочному тестированию (40 RPM)
- _Duration Assertion_: Удален, т.к. конфигурация не справлялась даже с обычной нагрузкой

#align(center)[
  #block(above: 1em)[
    #figure(
      image("assets/stress_cfg_all.png", width: 100%),
      caption: [Конфигурация Thread Group \ стресс тестирования],
    )
  ]
]

#pagebreak()

== Результаты

Так как _Duration Assertion_ удален,
то единственная вариант получить ошибку - ответ с кодом 503.

Из *9970* запросов таких было всего *1421*, т.е. *14.25%*.

#align(center)[
  #block(above: 1em)[
    #figure(
      image("assets/stress_result_requests_summary.png", width: 100%),
      caption: [Requests Summary стресс тестирования],
    )
  ]
]

#align(center)[
  #block(above: 1em)[
    #figure(
      image("assets/stress_result_response_time.png", width: 100%),
      caption: [Интервалы между ошибками],
    )
  ]
]

#align(center)[
  #block(above: 1em)[
    #figure(
      image("assets/stress_result_latency_and_failure.png", width: 100%),
      caption: [Заредржка и частота ошибок \ в зависимости от количества одновременных запросов],
    )
  ]
]

#pagebreak()

Большие значения "Interval Between Errors" - вероятно период восстановления системы

#align(center)[
  #block(above: 1em)[
    #figure(
      image("assets/stress_result_errors_interval.png", width: 100%),
      caption: [Интервалы между ошибками],
    )
  ]
]

package com.homework.nasibullin.datasourceimpl


import com.homework.nasibullin.dataclasses.ActorDto
import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.datasources.MoviesDataSource

class MoviesDataSourceImpl : MoviesDataSource {
    override fun getMovies() = listOf(
            MovieDto(
                    title = "Гнев человеческий",
                    description = "Эйч — загадочный и холодный на вид джентльмен, но внутри него пылает жажда справедливости. " +
                            "Преследуя свои мотивы, он внедряется в инкассаторскую компанию, чтобы выйти на соучастников серии многомиллионных ограблений," +
                            " потрясших Лос-Анджелес. В этой запутанной игре у каждого своя роль, но под подозрением оказываются все. Виновных же обязательно постигнет гнев человеческий.",
                    rateScore = 3,
                    ageRestriction = 18,
                    imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/5JP9X5tCZ6qz7DYMabLmrQirlWh.jpg",
                    posterUrl = "https://www.themoviedb.org/t/p/original/xkvPZY81ZliNh33tGsrUPTUFXXA.jpg",
                    genre = "боевики",
                    actors = listOf(
                            ActorDto(
                                    name = "Джейсон Стэтхем",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/lldeQ91GwIVff43JBrpdbAAeYWj.jpg"
                            ),
                            ActorDto(
                                    name = "Холт МакКэллани",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/8NvOcP35qv5UHWEdpqAvQrKnQQz.jpg"
                            ),
                            ActorDto(
                                    name = "Джош Хартнетт",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/dCfu2EN7FjISACcjilaJu7evwEc.jpg"
                            )

                    )
            ),
            MovieDto(
                    title = "Мортал Комбат",
                    description = "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего мира Шан Цзун посылает могущественного криомансера Саб-Зиро на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд.",
                    rateScore = 5,
                    ageRestriction = 18,
                    imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/pMIixvHwsD5RZxbvgsDSNkpKy0R.jpg",
                    posterUrl = "https://www.themoviedb.org/t/p/original/vUswluOoI4N1lomTxnR62QUzbF6.jpg",
                    genre = "боевики",
                    actors = listOf(
                            ActorDto(
                                    name = "Льюис Тан",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/lkW8gh20BuwzHecXqYH1eRVuWpb.jpg"
                            ),
                            ActorDto(
                                    name = "Холт МакКэллани",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/aAfaMEEqD8syHv5bLi5B3sccrM2.jpg"
                            ),
                            ActorDto(
                                    name = "Джош Хартнетт",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/Am9vM77uZd9bGODugwmWtOfzx6E.jpg"
                            )
                    )

            ),
            MovieDto(
                    title = "Упс... Приплыли!",
                    description = "От Великого потопа зверей спас ковчег. Но спустя полгода скитаний они готовы сбежать с него куда угодно. Нервы на пределе. Хищники готовы забыть про запреты и заглядываются на травоядных. " +
                            "Единственное спасение — найти райский остров. Там простор и полно еды. Но даже если он совсем близко, будут ли рады местные такому количеству гостей?",
                    rateScore = 5,
                    ageRestriction = 6,
                    imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/546RNYy9Wi5wgboQ7EtD6i0DY5D.jpg",
                    posterUrl ="https://www.themoviedb.org/t/p/original/LhGcIC9Df2tXjtF5bLBb8OVkTw.jpg",
                    genre = "мультфильмы",
                    actors = listOf(
                            ActorDto(
                                    name = "Тара Флинн",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/17gBs4aux2NcnMvf3DK5UKUFttn.jpg"
                            ),
                            ActorDto(
                                    name = "Ава Коннолли",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/o8uE77C4wQHYHJW6En192kjxJGd.jpg"
                            ),
                            ActorDto(
                                    name = "Мэри Мюррей",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/1ZRP9IfehCSx5OeBQQDcVPvKYD0.jpg"
                            )
                    )
            ),
            MovieDto(
                    title = "The Box",
                    description = "Уличный музыкант знакомится с музыкальным продюсером, и они вдвоём отправляются в путешествие, которое перевернёт их жизни.",
                    rateScore = 4,
                    ageRestriction = 12,
                    imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/fq3DSw74fAodrbLiSv0BW1Ya4Ae.jpg",
                    posterUrl = "https://www.themoviedb.org/t/p/original/wRAaTBveXfFvCrEvcL3Ep9hMq83.jpg",
                    genre = "драмы",
                    actors = listOf(
                            ActorDto(
                                    name = "Чханёль",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/qqvTuk4CTvS1IE47CUozhcHVahz.jpg"
                            ),
                            ActorDto(
                                    name = "Чо Даль Хван",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/jpEPPXmVC3EDMqrDQDYyXEMYlah.jpg"
                            ),
                            ActorDto(
                                    name = "Ким Юн Сун",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/fDO7vJVRkZOOY1GtQMJzf4N136q.jpg"
                            )
                    )
            ),
            MovieDto(
                    title = "Сага о Дэнни",
                    description = "Tekashi69 или Сикснайн — знаменитый бруклинский рэпер с радужными волосами — прогремел синглом «Gummo», коллабом с Ники Минаж, а также многочисленными преступлениями.",
                    rateScore = 2,
                    ageRestriction = 18,
                    imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/5xXGQLVtTAExHY92DHD9ewGmKxf.jpg",
                    posterUrl ="https://www.themoviedb.org/t/p/original/5xXGQLVtTAExHY92DHD9ewGmKxf.jpg",
                    genre = "драмы",
                    actors = listOf(
                            ActorDto(
                                    name = "6ix9ine",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/xAlvyeC9zLbygGMxmmyTHymwuZP.jpg"
                            )
                    )
            ),
            MovieDto(
                    title = "Пчелка Майя",
                    description = "Летний сбор пыльцы позади, и пчёлы пребывают в возбуждённом ожидании, ведь из столицы прибыл гонец, чтобы пригласить улей на соревнования за Кубок Мёда.",
                    rateScore = 4,
                    ageRestriction = 0,
                    imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/xltjMeLlxywym14NEizl0metO10.jpg",
                    posterUrl ="https://www.themoviedb.org/t/p/original/tMS2qcbhbkFpcwLnbUE9o9IK4HH.jpg",
                    genre = "мультфильмы",
                    actors = listOf(
                            ActorDto(
                                    name = "Бенсон Джек Энтони",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/aVfEldX1ksEMrx45yNBAf9MAIDZ.jpg"
                            ),
                            ActorDto(
                                    name = "Франсис Берри",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/qCp0psD5qzguABpRxWmMuC04kcl.jpg"
                            ),
                            ActorDto(
                                    name = "Кристиан Харисиу",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/8OpoYvO1QqBYRAp1LxxUIiRdQG0.jpg"
                            )
                    )
            ),
            MovieDto(
                    title = "Круэлла",
                    description = "Невероятно одаренная мошенница по имени Эстелла решает сделать себе имя в мире моды.",
                    rateScore = 4,
                    ageRestriction = 12,
                    imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/hUfyYGP9Xf6cHF9y44JXJV3NxZM.jpg",
                    posterUrl ="https://www.themoviedb.org/t/p/original/wK9Kd0vyuqgt41AF8CMzMBAw9KJ.jpg",
                    genre = "фантастика",
                    actors = listOf(
                            ActorDto(
                                    name = "Эмма Стоун",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/2hwXbPW2ffnXUe1Um0WXHG0cTwb.jpg"
                            ),
                            ActorDto(
                                    name = "Эмма Томпсон",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/xr8Ki3CIqweWWqS5q0kUYdiK6oQ.jpg"
                            ),
                            ActorDto(
                                    name = "Джоэл Фрай",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/4nEKEWJpaTHncCTv6zeP98V0qGI.jpg"
                            )
                    )
            ),
            MovieDto(
                    title = "Чёрная вдова",
                    description = "Чёрной Вдове придется вспомнить о том, что было в её жизни задолго до присоединения к команде Мстителей",
                    rateScore = 3,
                    ageRestriction = 16,
                    imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/mbtN6V6y5kdawvAkzqN4ohi576a.jpg",
                    posterUrl ="https://www.themoviedb.org/t/p/original/dShZ6Y3i1l6S3arJuk3P45eX6T.jpg",
                    genre = "фантастика",
                    actors = listOf(
                            ActorDto(
                                    name = "Скарлетт Йоханссон",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/6NsMbJXRlDZuDzatN2akFdGuTvx.jpg"
                            ),
                            ActorDto(
                                    name = "Флоренс Пью",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/75PvULemW8BvheSKtPMoBBsvPLh.jpg"
                            ),
                            ActorDto(
                                    name = "Рэйчел Вайс",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/x87wtRVVvsOG7hkfJlzNkkfXQCN.jpg"
                            )
                    )
            ),
            MovieDto(
                    title = "Судная ночь навсегда",
                    description = "Этим летом все правила будут нарушены. Группа мародеров решает, что ежегодная Судная ночь не должна заканчиваться с наступлением утра," +
                            " а может продолжаться бесконечно. Никто больше не будет в безопасности.",
                    rateScore = 4,
                    ageRestriction = 18,
                    imageUrl = "https://www.themoviedb.org/t/p/original/zEKa7Gfrr94V76w1A83khcML4Df.jpg",
                    posterUrl = "https://www.themoviedb.org/t/p/original/kGUcCqsENSDI1oU3wU3bVVVf8v7.jpg",
                    genre = "боевики",
                    actors = listOf(
                            ActorDto(
                                    name = "Джош Лукас",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/aHYyysVwQ7D0iYLjlUgiknBQGwW.jpg"
                            ),
                            ActorDto(
                                    name = "Анна де Лагуэра",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/nVhTkLBwZu4zOzYbnW9mtmHQfyg.jpg"
                            ),
                            ActorDto(
                                    name = "Тенорч Хуерта",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/5qoAmQpcPCjf4Pd6aTZOeINGYzk.jpg"
                            )
                    )
            ),
            MovieDto(
                    title = "Война будущего",
                    description = "В будущем идёт разрушительный конфликт с инопланетной расой. В попытке переломить ход войны учёные начинают призывать в свою армию солдат из прошлого.",
                    rateScore = 5,
                    ageRestriction = 12,
                    imageUrl = "https://www.themoviedb.org/t/p/original/jdzuxuA05lW4DzedZqa43SYhaZ.jpg",
                    posterUrl = "https://www.themoviedb.org/t/p/original/ceiwpwT6bxuAKtK6suPUDbuWEHK.jpg",
                    genre = "фантастика",
                    actors = listOf(
                            ActorDto(
                                    name = "Крис Пратт",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/gXKyT1YU5RWWPaE1je3ht58eUZr.jpg"
                            ),
                            ActorDto(
                                    name = "Ивонна Старковски",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/wio1VaQDOggDfPOTJf2vxGfooxZ.jpg"
                            ),
                            ActorDto(
                                    name = "Джордж Симмонс",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/7kIiPojgSVNRXb5z0hiijcD5LJ6.jpg"
                            )
                    )
            ),
            MovieDto(
                    title = "Тихое место 2",
                    description = "474 дня прошло после нападения на Землю охотящихся на звук существ, семья Эбботт продолжает бороться за жизнь в полной тишине." +
                            " Столкнувшись со смертельной угрозой в собственном доме, они вынуждены отправиться во внешний мир, где находят убежище и старого знакомого семьи.",
                    rateScore = 3,
                    ageRestriction = 16,
                    imageUrl = "https://www.themoviedb.org/t/p/original/sdOTD3C9h4Etl6rYrtYLAJOuUWN.jpg",
                    posterUrl = "https://www.themoviedb.org/t/p/original/uX4SrYuSaJAYsnFDZExyzjp4pZo.jpg",
                    genre = "ужасы",
                    actors = listOf(
                            ActorDto(
                                    name = "Эмили Блант",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/nPJXaRMvu1vh3COG16GzmdsBySQ.jpg"
                            ),
                            ActorDto(
                                    name = "Киллиан Мёрфи",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/i8dOTC0w6V274ev5iAAvo4Ahhpr.jpg"
                            ),
                            ActorDto(
                                    name = "Милисенд Симонс",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/vn7hejb0IRFvSrZxpxqY9RbBxMe.jpg"
                            )
                    )
            ),
            MovieDto(
                    title = "Довод",
                    description = "После теракта в киевском оперном театре агент ЦРУ объединяется с британской разведкой, " +
                            "чтобы противостоять русскому олигарху, который сколотил состояние на торговле оружием. ",
                    rateScore = 4,
                    ageRestriction = 16,
                    imageUrl = "https://www.themoviedb.org/t/p/original/m96dj44zZJ8TxpaMZDJv63TldZh.jpg",
                    posterUrl = "https://www.themoviedb.org/t/p/original/epoids15egPuq933RvT0Y34L478.jpg",
                    genre = "фантастика",
                    actors = listOf(
                            ActorDto(
                                    name = "Джон Вашингтон",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/eWNCcG4DqqhFKtWP56Ds8MiKPXB.jpg"
                            ),
                            ActorDto(
                                    name = "Роберт Паттинсон",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/8A4PS5iG7GWEAVFftyqMZKl3qcr.jpg"
                            ),
                            ActorDto(
                                    name = "Элизабет Дебики",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/sJxj44aKdY0pjSIgnxBgMWLrQmw.jpg"
                            )
                    )
            ),
            MovieDto(
                    title = "Кролик Питер 2",
                    description = "Продолжение истории маленького и непоседливого кролика по имени Питер." +
                            " Беатрис, Томас и крольчата, наконец, находят общий язык и начинают спокойную и размеренную жизнь за городом.",
                    rateScore = 4,
                    ageRestriction = 0,
                    imageUrl = "https://www.themoviedb.org/t/p/original/gGQEXTCU5IawU6929RGBHXRWXjZ.jpg",
                    posterUrl = "https://www.themoviedb.org/t/p/original/tIJQQPCDUzTzBh1ltiIJeKotYAR.jpg",
                    genre = "комедии",
                    actors = listOf(
                            ActorDto(
                                    name = "Джеймс Корден",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/xGB0gfZ48M27gQjjL7inJIh1Pqj.jpg"
                            ),
                            ActorDto(
                                    name = "Рози Бёрн",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/4oQWCLK7gd6RNKF0WJipJo7TyFP.jpg"
                            ),
                            ActorDto(
                                    name = "Домлан Глисон",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/uDbwncuKlqL0fAuucXSvgakJDrc.jpg"
                            )
                    )
            ),
            MovieDto(
                    title = "Лука",
                    description = "В прекрасном приморском городке на Итальянской Ривьере мальчик Лука проводит незабываемое лето, наполненное мороженым, пастой и бесконечными поездками на скутере. Вместе с ним эти приключения переживает его новый лучший друг, который на самом деле – морское чудовище из другого мира.",
                    rateScore = 4,
                    ageRestriction = 6,
                    imageUrl = "https://www.themoviedb.org/t/p/original/8tABCBpzu3mZbzMB3sRzMEHEvJi.jpg",
                    posterUrl="https://www.themoviedb.org/t/p/original/tiVmLZ6bTPH0bWTs13amLZhKL4o.jpg",
                    genre = "мультфильмы",
                    actors = listOf(ActorDto(
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oNLhzkZXNw1RNihne9P5q57cRcd.jpg",
                                     name = "Джейкоб Трамбле"
                            ),
                            ActorDto(
                                    name = "Эмма Берман",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/r3QkZtW6Iaq56ziZqvPXAQLOcTr.jpg"
                            ),
                            ActorDto(
                                    name = "Джек Дилан ГрейзерДомлан Глисон",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/cZdGLa78UP7VzMgNbDRnoaSkZm1.jpg"
                            )
                    )
            ),
            MovieDto(
                    title = "Майор Гром: Чумной Доктор",
                    description = "Майор полиции Игорь Гром известен всему Санкт-Петербургу своим пробивным характером и непримиримой позицией по отношению к преступникам всех мастей. Неимоверная сила, аналитический склад ума и неподкупность – всё это делает майора Грома идеальным полицейским, не знающим преград. Но всё резко меняется с появлением человека в маске Чумного Доктора.",
                    rateScore = 3,
                    ageRestriction = 12,
                    imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/wnesEmcq7xdw1Rm1Bn6XEFTkenR.jpg",
                    posterUrl = "https://www.themoviedb.org/t/p/original/4e3hHuXbg1Gh9xuz045bRoImArR.jpg",
                    genre = "фантастика",
                    actors = listOf(
                            ActorDto(
                                    name = "Тихон Жизневский",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/nWM4bvQk5Pq74uu3r2wLawOHMJr.jpg"
                            ),
                            ActorDto(
                                    name = "Любовь Аксёнова",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/yOQX4JPwX1uhMyVT1jJigAk7iUr.jpg"
                            ),
                            ActorDto(
                                    name = "Алексей Маклаков",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/u1i5aLdon5fHggZVcIL1VkytqBN.jpg"
                            )
                    )
            ),
            MovieDto(
                    title = "Годзилла против Конга",
                    description = "Человечество стало виной тому, что Годзилла и Конг вынуждены сойтись в неравной схватке. Организация «Монарх», отслеживающая жизнь монстров на земле, отправляет экспедицию в неизведанные земли острова Черепа, где надеется раскрыть тайну происхождения титанов.",
                    rateScore = 5,
                    ageRestriction = 12,
                    imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg",
                    posterUrl = "https://www.themoviedb.org/t/p/original/rvaOf23XpF3Bbkxis6aTRPwUdJ2.jpg",
                    genre = "фантастика",
                    actors = listOf(
                            ActorDto(
                                    name = "Александр Скарсгард",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/hIuDik6KDmHLrqZWxBVdXzUw1kq.jpg"
                            ),
                            ActorDto(
                                    name = "Милли Бобби Браун",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/yzfxLMcBMusKzZp9f1Z9Ags8WML.jpg"
                            ),
                            ActorDto(
                                    name = "Ребекка Холл",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/cVZaQrUY7F5khCBYdKDlEppHnQi.jpg"
                            )
                    )
            ),
            MovieDto(
                    title = "Монстрические питомцы",
                    description = "В новом фильме любимый монструозный щенок Дракулы страдает от нехватки внимания со стороны хозяина, слишком занятого делами отеля, а ведь в Пёсике столько энергии и он бесконечно хочет играть в мяч! Драк решает найти своему питомцу компаньона, и после серии неудачных попыток Пёсик наконец-то выбирает себе друга, который грозит Дракуле еще большей суетой.",
                    rateScore = 4,
                    ageRestriction = 6,
                    imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/dkokENeY5Ka30BFgWAqk14mbnGs.jpg",
                    posterUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/dkokENeY5Ka30BFgWAqk14mbnGs.jpg",
                    genre = "мультфильмы",
                    actors = listOf(
                            ActorDto(
                                    name = "Брайан Халл",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/mLq5C8100kjFad6AMLNUXX3kcPf.jpg"
                            ),
                            ActorDto(
                                    name = "Дженнифер Клуска",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/wpYWJ7Kw5qcXEOZyTuIEsfqMqXq.jpg"
                            ),
                            ActorDto(
                                    name = "Дерек Драймон",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/1ZdPd2JaiigVDB7hPxy29RAx9GY.jpg"
                            )
                    )
            ),
            MovieDto(
                    title = "Космический джем: Новое поколение",
                    description = "Чтобы спасти сына, знаменитый чемпион НБА отправляется в сказочный мир, где в команде мультяшек вынужден сражаться на баскетбольной площадке с цифровыми копиями знаменитых игроков.",
                    rateScore = 4,
                    ageRestriction = 6,
                    imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/5bFK5d3mVTAvBCXi5NPWH0tYjKl.jpg",
                    posterUrl = "https://www.themoviedb.org/t/p/original/1MMMF4KlwNFCSR1KzQQuxapUMAa.jpg",
                    genre = "комедии",
                    actors = listOf(
                            ActorDto(
                                    name = "Леброн Джеймс",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/rmIYKVdYT60zfsqfjNuB71f1y82.jpg"
                            ),
                            ActorDto(
                                    name = "Дон Чидл",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/b1EVJWdFn7a75qVYJgwO87W2TJU.jpg"
                            ),
                            ActorDto(
                                    name = "Седрик Джо",
                                    avatarUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/2LXuhY0h5MlxLu7X55w4oAiVDWP.jpg"
                            )
                    )
            )
    )
}
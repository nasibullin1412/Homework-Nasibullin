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
            )
    )
}
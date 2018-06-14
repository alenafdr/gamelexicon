## О проекте
Проект представляет собой REST API приложение, которое позволяет участвовать в многоступенчатой игре. 
На каждом шаге пользователю нужно выбрать правильный перевод для слова из предложенных вариантов (правильный ответ только один).
Пользователь может зарегистрироваться, используя уникальное имя. 

В игре может участвовать любой пользователь, авторизованный или нет. Авторизованный пользователь может менять настройки игры, 
у неавторизованного настройки по умолчанию (количество шагов - 20, время на ответ - 5 сек, язык вопроса - русский, язык ответа - английский).
Так же для авторизованного пользователя ведется статистика : 
- слова, которые угаданы быстрее всего; 
- слова, угаданные большее количество раз;  
- слова с большим количеством промахов;

Администратор может: 
- добавлять, редактировать, удалять пользователей; 
- импортировать слова из файла (.csv) 
- добавлять поддерживаемые языки

## Процесс игры

### Создание игры
Для того, чтобы начать игру, нужно отправить POST запрос на  
```
api/games/   
```
Будет создана новая игра и возвращен объект вида  
```
{
       "game_id": 13,
       "language_question": "русский",
       "language_answer": "english",
       "steps": [
           {
               "step_id": 48,
               "result": false
           },{
               "step_id": 49,
               "result": false
           },{
               "step_id": 50,
               "result": false
           },{
               "step_id": 51,
               "result": false
           }
       ]
   }
```

### Получение шага игры
Для получения каждого шага нужно отправить GET запрос на  
```
/api/steps/{id} 
```

Будет выдан объект вида
```
{
    "id": 48,
    "word_question": {
        "id": 7,
        "name": "собака"
    },
    "options": [
        {
            "id": 10,
            "name": "cat"
        },{
            "id": 13,
            "name": "dog"
        },{
            "id": 9,
            "name": "home"
        },{
            "id": 14,
            "name": "car"
        }
    ],
    "result": false
}
```

### Установка ответа на шаг игры
Для сохранения ответа нужно отправить PUT запрос на  
```
api/steps/
```

Метод принимает на вход объект вида
```
{
    "id": 48,
    "word_question": {
        "id": 7,
        "name": "собака"
    },
    "word_answer": {
        "id": 13,
        "name": "dog"
    }
}

```
Важно отметить, что учитывается время выдачи шага и время получения ответа. Если ответ был дан позже, чем это предусмотрено настройками (5 сек по умолчанию), ответ не будет засчитан

### Завершение игры
По окончании игры нужно отправить PUT запрос на адрес
```
/api/games/
```
Метод принимает на вход объект вида
```
{
    "game_id": 48,
    "language_question": "русский",  
        "language_answer": "english",  
        "steps": [  
            {  
                "step_id": 58,  
                "result": false  
            },{  
                "step_id": 59,   
                "result": false  
            },{  
                "step_id": 60,  
                "result": false  
            },{  
                "step_id": 57,  
                "result": false  
            }    
        ] 
}
```
Обновляет данные об игре, выдает результаты игры в виде объекта Game, указанного выше

## Детальное описание API 

ссылка

## Используемые технологии
Java 8  
Spring Boot 2.0.1.RELEASE  
Spring Data 2.0.1.RELEASE  
Spring Security 5.0.4.RELEASE    
MySQL 5.5  
Liquibase 3.4.1  
Maven 3.5.0  
Junit 4.12   
Mockito 1.10.19  
PowerMock 1.6.4  

## Мониторинг статистики
Мониторинг статистики настроен с помощью Grafana. Запрос делается непосредственно в базу данных.  
![](https://github.com/alenafdr/gamelexicon/blob/master/src/main/resources/screenshots/setting_data_sources.png)  
Настройки можно импортировать из папки src/main/resources/grafana. На данный момент настрона следующая статистика   

![](https://github.com/alenafdr/gamelexicon/blob/master/src/main/resources/screenshots/list.png)  
### Users  
![](https://github.com/alenafdr/gamelexicon/blob/master/src/main/resources/screenshots/users.png)
### Words
![](https://github.com/alenafdr/gamelexicon/blob/master/src/main/resources/screenshots/words.png)  
### Games
![](https://github.com/alenafdr/gamelexicon/blob/master/src/main/resources/screenshots/games.png)  

## Система сборки
Для сброки проекта был использован Jenkins  

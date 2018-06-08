##Registration, login, logout

```/registration```      Принимает на вход объект параметры login, password, first_name, last_name. Выполняет login и выдает token  
```/login```              Принимает на вход параметры  login и password, выдает token  
```/logout```             По токену в header c заголовком 'X-Auth-Token' определяет пользователя, выполняет logout



##Games
Объект Game
```
{  
    "game_id": 13,  
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
        }    ]  
}  
```
```api/games/```      GET     получить список игр для пользователя (определяет пользователя по токену в заголовке 'X-Auth-Token' )  
```api/games/{id}```  GET     получить игру по id  
```api/games/```      POST    создать новую игру, возвращает объект Game, в котором указаны id шагов  
```api/games/```      PUT     принимает на вход объект Game, выдает результаты игры  
```api/games/{id}```  DELETE  удалить игру с текущим id  

##Steps  
Объект Step
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
            "id": 13,  
            "name": "dog"  
        }  
    ],  
    "result": true  
}  
```
```api/steps/```      GET     получить список шагов для пользователя (определяет пользователя по токену в заголовке 'X-Auth-Token' )  
```api/steps/{id}```  GET     получить шаг по id  
```api/steps/```      POST    создать новый шаг - возвращает объект Step  
```api/steps/ ```     PUT     принимает на вход объект Step  
```api/steps/{id}```  DELETE  удалить шаг с текущим id  
  
Метод PUT принимает на вход объект вида   
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


##Users  
Все действия доступны только для пользователя с ролью ADMIN  
Объект User
```
{
       "id": 3,
       "email": "test email",
       "first_name": "first name",
       "last_name": "last name",
       "roles": [
           {
               "id": 2,
               "name": "ROLE_USER"
           }
       ],
       "token": "zksjdfgsbvueifyhkvbc54dsfg1"
       "setting_id": 3
   }
```

```api/users/```      GET     получить список пользователей  
```api/users/{id}```  GET     получить пользователя по id  
```api/users/```      POST    создать нового пользователя - возвращает объект User    
```api/users/```      PUT     принимает на вход объект User, обновляет текущий объект   
```api/users/{id}```  DELETE  удалить пользователя с текущим id   



##Settings
Объект Setting
```
{
    "id": 2,
    "lang_question": {
        "id": 1,
        "name": "русский"
    },
    "lang_answer": {
        "id": 2,
        "name": "english"
    },
    "amount_steps": 20,
    "sec_for_answer": 5,
    "amount_repeat": 5
}
```

```api/settings/```      GET     получить список настроек (только для пользователя, с ролью ADMIN)   
```api/settings/{id}```  GET     получить настройки по id пользователя  
```api/settings/```      POST    создать новые настройки - возвращает объект Setting    
```api/settings/```      PUT     принимает на вход объект Setting, обновляет текущий объект  
```api/settings/{id}```  DELETE  удалить настройки с текущим id  

##Languages
Все действия доступны только для пользователя с ролью ADMIN  
Объект Language
```
{
    "id": 1,
    "name": "ru"
}
```

```api/languages/```      GET     получить список языков  
```api/languages/{id}```  GET     получить язык по id  
```api/languages/```      POST    создать новый язык - возвращает объект Language    
```api/languages/```      PUT     принимает на вход объект Language, обновляет текущий объект  
```api/languages/{id}```  DELETE  удалить язык с текущим id   


##Word  
Все действия доступны только для пользователя с ролью ADMIN  
Объект Word
```
{
       "id": 5,
       "name": "дом",
       "language": {
           "id": 1,
           "name": "ru"
       }
   }
```

```api/words/```      GET     получить список слов  
```api/words/{id}```  GET     получить слово по id  
```api/words/```      POST    создать новое слово - возвращает объект Word    
```api/words/```      PUT     принимает на вход объект Word, обновляет текущий объект  
```api/words/{id}```  DELETE  удалить слово с текущим id   


##Statistic  
Каждый метод принимает на вход id пользователя и параметр limit. Так же в загловке 'X-Auth-Token' передается токен и вслучае совпадения токена с id пользователя, по которому запрашивают статистику, выдается ответ. Пользователь с ролью ADMIN может смотреть статистику по любому игроку     

```/api/statistic/{id}/abovethreshold```  Список слов, угаданых быстрее всего  
```/api/statistic/{id}/mosthits```        Список слов, угаданных болшее количество раз  
```/api/statistic/{id}/mostmisses```      Список слов, с большим количеством промахов  


##Импорт новых слов из таблицы
Иморт может сделать только пользвоатель с ролью ADMIN

```/upload```     POST    На вход метод принимает файл с расширением .csv

Данный в файле дожны быть организованы следющим образом:
```
слово_оригинал;язык_оригинала;слово_перевод;язык_перевода
```
Например
```
time;en;время;ru
```
В базу будет записан перевод в обе стороны (т.е. для слова 'time' будет установлен перевод на русский и для слова 'время' будет установлен перевод на английский)

При остуствии языка, он будет добавлен
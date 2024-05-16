## Учебный проект rest-api-producer

Проект rest-api-producer представляет собой учебный проект, разработанный для демонстрации простого REST API. В проекте используется DTO (Data Transfer Object) для передачи данных между клиентом и сервером.

### Эндпоинты API:

1. **Получить приветственное сообщение**
    - **Метод:** GET
    - **URL:** [http://localhost:8080/api/sayHello](http://localhost:8080/api/sayHello)

      #### Тест. Получение приветственного сообщения:
      <img width="1552" alt="Screenshot 2024-05-16 at 13 35 49" src="https://github.com/devolw/producer-rest-api/assets/104515806/177fdab9-4bbe-4134-ac35-4aed36549830">

2. **Получить список пользователей**
    - **Метод:** GET
    - **URL:** [http://localhost:8080/people](http://localhost:8080/people)

      #### Тест. Получение списка пользователей:
      <img width="1552" alt="Screenshot 2024-05-16 at 13 36 04" src="https://github.com/devolw/producer-rest-api/assets/104515806/cb77baaf-c0b0-4d7f-a603-254819a1f7f4">

3. **Получить пользователя по id**
    - **Метод:** GET
    - **URL:** [http://localhost:8080/people/{id}](http://localhost:8080/people/{id})
    - Здесь `{id}` заменяется на идентификатор пользователя.

      #### Тест1. Получение списка пользователей по id (Успешный):
      <img width="1552" alt="Screenshot 2024-05-16 at 13 36 54" src="https://github.com/devolw/producer-rest-api/assets/104515806/f82e7b7e-d85c-4377-a2f6-edf022132066">

      #### Тест2. Получение списка пользователей по id (Ошибочный - в ответ получен лог допущенных ошибок):
      <img width="1552" alt="Screenshot 2024-05-16 at 13 36 20" src="https://github.com/devolw/producer-rest-api/assets/104515806/2e1bfa97-f015-422c-b399-eb1aa0776259">

4. **Добавить нового пользователя**
    - **Метод:** POST
    - **URL:** [http://localhost:8080/people](http://localhost:8080/people)
    - **Тело запроса (JSON):**
      ```json
      {
          "name": "Name should be between 2 and 30 characters",
          "age": "Age should be greater than 0",
          "email": "Email must be a well-formed email address"
      }
      ```
    - Поля `name`, `age`, и `email` должны соответствовать указанным валидационным требованиям.

      #### Тест1. Добавление нового пользователя (Успешный):
      <img width="1552" alt="Screenshot 2024-05-16 at 13 37 48" src="https://github.com/devolw/producer-rest-api/assets/104515806/7d25f785-e2ca-4772-8472-ab22cf8bfb02">

      #### Тест2. Добавление нового пользователя (Ошибочный - в ответ получен лог допущенных ошибок):
      <img width="1552" alt="Screenshot 2024-05-16 at 13 38 27" src="https://github.com/devolw/producer-rest-api/assets/104515806/a4ce44a7-8850-422d-b088-b3fe48aeb774">

      #### Структура таблицы БД и результат добавления нового пользователя:
      <img width="1172" alt="Screenshot 2024-05-16 at 13 38 01" src="https://github.com/devolw/producer-rest-api/assets/104515806/af5882ce-7e72-4da3-809e-761eb2a50d1d">


Этот API позволяет взаимодействовать с пользователями, получая информацию о них, добавляя новых пользователей и приветствуя клиентов. Он является учебным и предназначен для демонстрации базовых концепций разработки RESTful API с использованием DTO.

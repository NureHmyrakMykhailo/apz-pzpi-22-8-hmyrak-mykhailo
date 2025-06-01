# API Документація для проєкту "Програмна системи для автоматизації роботи бібліотек"

API забезпечує функціональність для управління бібліотечними процесами. Він дозволяє реєструвати, відстежувати та аналізувати книги, читачів та персоналії, забезпечуючи ефективне управління бібліотекою.

## Встановлення та налаштування

### Передумови

Для взаємодії з проєктом необхідно встановити:
- [.NET SDK](https://dotnet.microsoft.com/) (версія 7.0 та вище)
- [Visual Studio](https://visualstudio.microsoft.com/) (2022 або новіше)
- [SQL Server](https://www.microsoft.com/en-us/sql-server/) (2019 або новіше)
- [Entity Framework Core](https://docs.microsoft.com/en-us/ef/core/) (7.0 та вище)

Веб‑клієнт:
Node.js (версія 18 та вище)
Npm
Create React App
Браузер Chrome або Firefox



Мобільний клієнт:
- [Android Studio](https://developer.android.com/studio) (Chipmunk або новіше)
- Android SDK (версія 31 та вище)
- [Kotlin](https://kotlinlang.org/) (версія 1.8+)
- Емулятор Android або реальний пристрій

### Крок 1: Клонування репозиторію

Виконати клонування репозиторію на локальний комп'ютер:

```
git clone https://github.com/NureHmyrakMykhailo/apz-pzpi-22-8-hmyrak-mykhailo/tree/main/Lab5
```

### Крок 2: Створення бази даних

Створити базу даних з назвою "LibrarySystem". Таблиці автоматично створюються під час першого запуску завдяки міграціям Entity Framework Core.

### Крок 3: Запуск серверу

В `appsettings.json` змінити рядок підключення до бази даних на свої власні дані:
{
"ConnectionStrings": {
"DefaultConnection": "Server=localhost;Database=LibrarySystem;Trusted_Connection=True;"
}
}

Перейдіть у кореневу директорію проєкту та виконайте команду запуску сервера:

```
dotnet run
```

Або відкрийте рішення в Visual Studio та запустіть проект.

За умовчанням сервер доступний за адресою `http://localhost:5000`. Це можна змінити в файлі конфігурації (`appsettings.json`).

### Крок 4: Запуск веб-клієнта

Перейдіть до каталогу веб-клієнта (папка `web-client` у корені репозиторію). Встановіть залежності командою:

```
npm install
```

Запустіть веб-інтерфейс у режимі розробки:

```
npm start
```

Після цього він буде доступний за адресою `http://localhost:3000`.
Для створення production-збірки скористайтеся:

```
npm run build
```




### Крок 5: Запуск мобільного клієнта

Відкрийте проект мобільного клієнта в Android Studio. Переконайтеся, що в `gradle.properties` або `BuildConfig` встановлено правильний `BASE_URL` (наприклад, `http://localhost:5000`). Запустіть емулятор або підключіть Android-пристрій і виконайте запуск конфігурації app. Після встановлення додаток автоматично з'єднається з REST API та відобразить актуальні дані.

### Крок 6: Налаштування автентифікації

Для роботи з системою необхідно налаштувати автентифікацію. В `appsettings.json` встановіть параметри JWT:
{
"JwtSettings": {
"SecretKey": "ваш_секретний_ключ",
"Issuer": "ваш_іссуер",
"Audience": "ваша_аудиторія",
"ExpirationInMinutes": 60
}
}

Після налаштування всіх компонентів система буде готова до використання. Користувачі зможуть авторизуватися через веб-інтерфейс або мобільний додаток, отримуючи доступ до відповідних функцій відповідно до їх ролей (адміністратор, бібліотекар, читач).

## 1. BooksController API
### Маршрути
---

#### Add Book
```http
```http
POST '/api/books/admin/{userId}/add'
```
```
Опис: Додає нову книгу від імені адміністратора.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
**Body:**
title (String): Назва книги.
author (String): Автор книги.
categoryID (Long): Ідентифікатор категорії.
isbn (String): ISBN книги.
publicationDate (Date): Дата публікації.
status (String): Статус доступності.
Відповідь:
200 OK: Книга була успішно додана.
400 Bad Request: Помилка у вхідних даних.
403 Forbidden: Відсутність дозволу на виконання дії.
404 Not Found: Адміністратора не знайдено.
---

#### Get Book By ID
```http
```http
GET '/api/books/{bookID}'
```
```
Опис: Отримує книгу за її унікальним ідентифікатором.
Параметри запиту:
**Path:**
bookID (Long): Ідентифікатор книги.
Відповідь:
200 OK: Книга знайдена.
404 Not Found: Книга не знайдена.
---

#### Get All Books
```http
```http
GET '/api/books'
```
```
Опис: Отримує всі книги.
Відповідь:
200 OK: Список всіх книг.
---

#### Update Book
```http
```http
PUT '/api/books/admin/{userId}/{bookID}'
```
```
Опис: Оновлює дані книги від імені адміністратора.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
bookID (Long): Ідентифікатор книги.
**Body:**
title (String): Назва книги.
author (String): Автор книги.
categoryID (Long): Ідентифікатор категорії.
isbn (String): ISBN книги.
publicationDate (Date): Дата публікації.
status (String): Статус доступності.
Відповідь:
200 OK: Книга оновлена.
400 Bad Request: Помилка у вхідних даних.
403 Forbidden: Відсутність дозволу на виконання дії.
404 Not Found: Книга не знайдена.
---

#### Delete Book
```http
```http
DELETE '/api/books/admin/{userId}/{bookID}'
```
```
Опис: Видаляє книгу від імені адміністратора.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
bookID (Long): Ідентифікатор книги.
Відповідь:
204 No Content: Книга видалена.
404 Not Found: Книга не знайдена.

## 2. ReadersController API
### Маршрути
---

#### Add Reader
```http
```http
POST '/api/readers/admin/{userId}/add'
```
```
Опис: Додає нового читача від імені адміністратора.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
**Body:**
firstName (String): Ім'я читача.
lastName (String): Прізвище читача.
email (String): Електронна пошта читача.
phone (String): Номер телефону читача.
Відповідь:
200 OK: Читач був доданий.
400 Bad Request: Помилка у вхідних даних.
403 Forbidden: Відсутність дозволу на виконання дії.
404 Not Found: Адміністратора не знайдено.
---

#### Get Reader By ID
```http
```http
GET '/api/readers/{readerID}'
```
```
Опис: Отримує читача за його ідентифікатором.
Параметри запиту:
**Path:**
readerID (Long): Ідентифікатор читача.
Відповідь:
200 OK: Читач знайдений.
404 Not Found: Читач не знайдений.
---

#### Get All Readers
```http
```http
GET '/api/readers'
```
```
Опис: Отримує список всіх читачів.
Відповідь:
200 OK: Список всіх читачів.
---

#### Update Reader
```http
```http
PUT '/api/readers/admin/{userId}/{readerID}'
```
```
Опис: Оновлює дані читача від імені адміністратора.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
readerID (Long): Ідентифікатор читача.
**Body:**
firstName (String): Ім'я читача.
lastName (String): Прізвище читача.
email (String): Електронна пошта.
phone (String): Номер телефону.
Відповідь:
200 OK: Читач оновлений.
400 Bad Request: Помилка у вхідних даних.
403 Forbidden: Відсутність дозволу на виконання дії.
404 Not Found: Читач не знайдений.
---

#### Delete Reader
```http
```http
DELETE '/api/readers/admin/{userId}/{readerID}'
```
```
Опис: Видаляє читача від імені адміністратора.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
readerID (Long): Ідентифікатор читача.
Відповідь:
204 No Content: Читач видалений.
404 Not Found: Читач не знайдений.

## 3 ItemsController API
### Маршрути
---

#### Add Item
```http
```http
POST '/api/items/admin/{userId}/add'
```
```
Опис: Додає новий екземпляр книги.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
**Body:**
bookId (Long): Ідентифікатор книги.
statusId (Long): Ідентифікатор статусу книги.
barcode (String): Штрих-код екземпляра книги.
location (String): Місцезнаходження книги.
dateAdded (Date): Дата додавання екземпляра.
Відповідь:
200 OK: Екземпляр книги успішно доданий.
400 Bad Request: Помилка у вхідних даних.
403 Forbidden: Відсутність дозволу на виконання дії.
404 Not Found: Книга або статус не знайдені.
---

#### Get Item By ID
```http
```http
GET '/api/items/{itemID}'
```
```
Опис: Отримує екземпляр книги за його ідентифікатором.
Параметри запиту:
**Path:**
itemID (Long): Ідентифікатор екземпляра.
Відповідь:
200 OK: Екземпляр знайдений.
404 Not Found: Екземпляр не знайдений.
---

#### Get All Items
```http
```http
GET '/api/items'
```
```
Опис: Отримує список всіх екземплярів книг.
Відповідь:
200 OK: Список всіх екземплярів книг.
---

#### Update Item
```http
```http
PUT '/api/items/admin/{userId}/{itemID}'
```
```
Опис: Оновлює екземпляр книги від імені адміністратора.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
itemID (Long): Ідентифікатор екземпляра.
**Body:**
statusId (Long): Ідентифікатор нового статусу книги.
location (String): Оновлене місцезнаходження книги.
Відповідь:
200 OK: Екземпляр оновлений.
400 Bad Request: Помилка у вхідних даних.
403 Forbidden: Відсутність дозволу на виконання дії.
404 Not Found: Екземпляр або статус не знайдені.
---

#### Delete Item
```http
```http
DELETE '/api/items/admin/{userId}/{itemID}'
```
```
Опис: Видаляє екземпляр книги від імені адміністратора.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
itemID (Long): Ідентифікатор екземпляра.
Відповідь:
204 No Content: Екземпляр видалено.
404 Not Found: Екземпляр не знайдений.

## 4. CategoriesController API
### Маршрути
---

#### Add Category
```http
```http
POST '/api/categories/admin/{userId}/add'
```
```
Опис: Додає нову категорію від імені адміністратора.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
**Body:**
name (String): Назва категорії.
description (String): Опис категорії.
Відповідь:
200 OK: Категорія була успішно додана.
400 Bad Request: Помилка у вхідних даних.
403 Forbidden: Відсутність дозволу на виконання дії.
404 Not Found: Адміністратора не знайдено.
---

#### Get Category By ID
```http
```http
GET '/api/categories/{categoryID}'
```
```
Опис: Отримує категорію за її ідентифікатором.
Параметри запиту:
**Path:**
categoryID (Long): Ідентифікатор категорії.
Відповідь:
200 OK: Категорія знайдена.
404 Not Found: Категорія не знайдена.
---

#### Get All Categories
```http
```http
GET '/api/categories'
```
```
Опис: Отримує список всіх категорій.
Відповідь:
200 OK: Список всіх категорій.
---

#### Update Category
```http
```http
PUT '/api/categories/admin/{userId}/{categoryID}'
```
```
Опис: Оновлює дані категорії від імені адміністратора.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
categoryID (Long): Ідентифікатор категорії.
**Body:**
name (String): Назва категорії.
description (String): Опис категорії.
Відповідь:
200 OK: Категорія оновлена.
400 Bad Request: Помилка у вхідних даних.
403 Forbidden: Відсутність дозволу на виконання дії.
404 Not Found: Категорія не знайдена.
---

#### Delete Category
```http
```http
DELETE '/api/categories/admin/{userId}/{categoryID}'
```
```
Опис: Видаляє категорію від імені адміністратора.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
categoryID (Long): Ідентифікатор категорії.
Відповідь:
204 No Content: Категорія видалена.
404 Not Found: Категорія не знайдена.

## 5. StatusesController API
### Маршрути
---

#### Add Status
```http
```http
POST '/api/statuses/admin/{userId}/add'
```
```
Опис: Додає новий статус книги від імені адміністратора.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
**Body:**
statusName (String): Назва статусу.
description (String): Опис статусу.
Відповідь:
200 OK: Статус успішно доданий.
400 Bad Request: Помилка у вхідних даних.
403 Forbidden: Відсутність дозволу на виконання дії.
404 Not Found: Адміністратора не знайдено.
---

#### Get Status By ID
```http
```http
GET '/api/statuses/{statusID}'
```
```
Опис: Отримує статус за його ідентифікатором.
Параметри запиту:
**Path:**
statusID (Long): Ідентифікатор статусу.
Відповідь:
200 OK: Статус знайдений.
404 Not Found: Статус не знайдений.
---

#### Get All Statuses
```http
```http
GET '/api/statuses'
```
```
Опис: Отримує список всіх статусів.
Відповідь:
200 OK: Список всіх статусів.
---

#### Update Status
```http
```http
PUT '/api/statuses/admin/{userId}/{statusID}'
```
```
Опис: Оновлює статус книги від імені адміністратора.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
statusID (Long): Ідентифікатор статусу.
**Body:**
statusName (String): Назва статусу.
description (String): Опис статусу.
Відповідь:
200 OK: Статус оновлений.
400 Bad Request: Помилка у вхідних даних.
403 Forbidden: Відсутність дозволу на виконання дії.
404 Not Found: Статус не знайдений.
---

#### Delete Status
```http
```http
DELETE '/api/statuses/admin/{userId}/{statusID}'
```
```
Опис: Видаляє статус книги від імені адміністратора.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
statusID (Long): Ідентифікатор статусу.
Відповідь:
204 No Content: Статус видалений.
404 Not Found: Статус не знайдений.

## 6. UsersController API
### Маршрути
---

#### Register User
```http
```http
POST '/api/users/register'
```
```
Опис: Реєструє нового користувача.
Параметри запиту:
**Body:**
username (String): Ім'я користувача.
email (String): Електронна пошта користувача.
password (String): Пароль користувача.
role (String): Роль користувача (наприклад, "admin", "reader").
Відповідь:
200 OK: Користувач успішно зареєстрований.
400 Bad Request: Помилка у вхідних даних.
409 Conflict: Користувач з таким іменем або email вже існує.
---

#### Login User
```http
```http
POST '/api/users/login'
```
```
Опис: Авторизує користувача.
Параметри запиту:
**Body:**
username (String): Ім'я користувача.
password (String): Пароль користувача.
Відповідь:
200 OK: Авторизація успішна.
400 Bad Request: Помилка у вхідних даних.
401 Unauthorized: Невірний логін чи пароль.
---

#### Get User By ID
```http
```http
GET '/api/users/{userID}'
```
```
Опис: Отримує користувача за його ідентифікатором.
Параметри запиту:
**Path:**
userID (Long): Ідентифікатор користувача.
Відповідь:
200 OK: Користувач знайдений.
404 Not Found: Користувач не знайдений.
---

#### Get All Users
```http
```http
GET '/api/users'
```
```
Опис: Отримує список всіх користувачів.
Відповідь:
200 OK: Список всіх користувачів.
---

#### Update User
```http
```http
PUT '/api/users/admin/{userId}/{userID}'
```
```
Опис: Оновлює дані користувача від імені адміністратора.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
userID (Long): Ідентифікатор користувача.
**Body:**
username (String): Ім'я користувача.
email (String): Електронна пошта.
role (String): Роль користувача.
Відповідь:
200 OK: Користувач оновлений.
400 Bad Request: Помилка у вхідних даних.
403 Forbidden: Відсутність дозволу на виконання дії.
404 Not Found: Користувач не знайдений.
---

#### Delete User
```http
```http
DELETE '/api/users/admin/{userId}/{userID}'
```
```
Опис: Видаляє користувача від імені адміністратора.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
userID (Long): Ідентифікатор користувача.
Відповідь:
204 No Content: Користувач видалений.
404 Not Found: Користувач не знайдений.

## 8. HistController API
### Маршрути
---

#### Add History Record
```http
```http
POST '/api/hist/admin/{userId}/add'
```
```
Опис: Додає запис історії видачі книги.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
**Body:**
readerId (Long): Ідентифікатор читача.
itemId (Long): Ідентифікатор екземпляра книги.
borrowDate (Date): Дата видачі книги.
returnDate (Date): Дата повернення книги.
Відповідь:
200 OK: Запис успішно додано.
400 Bad Request: Помилка у вхідних даних.
403 Forbidden: Відсутність дозволу на виконання дії.
404 Not Found: Читач або екземпляр не знайдені.
---

#### Get History By ID
```http
```http
GET '/api/hist/{histID}'
```
```
Опис: Отримує запис історії видачі книги за його ідентифікатором.
Параметри запиту:
**Path:**
histID (Long): Ідентифікатор запису.
Відповідь:
200 OK: Запис знайдений.
404 Not Found: Запис не знайдений.
---

#### Get All History
```http
```http
GET '/api/hist'
```
```
Опис: Отримує всі записи історії видачі книг.
Відповідь:
200 OK: Список всіх записів.
---

#### Update History Record
```http
```http
PUT '/api/hist/admin/{userId}/{histID}'
```
```
Опис: Оновлює запис історії видачі книги від імені адміністратора.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
histID (Long): Ідентифікатор запису.
**Body:**
returnDate (Date): Оновлена дата повернення книги.
Відповідь:
200 OK: Запис оновлений.
400 Bad Request: Помилка у вхідних даних.
403 Forbidden: Відсутність дозволу на виконання дії.
404 Not Found: Запис не знайдений.
---

#### Delete History Record
```http
```http
DELETE '/api/hist/admin/{userId}/{histID}'
```
```
Опис: Видаляє запис історії видачі книги від імені адміністратора.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
histID (Long): Ідентифікатор запису.
Відповідь:
204 No Content: Запис видалено.
404 Not Found: Запис не знайдений.

## 9. PersonsController API
### Маршрути
---

#### Add Person
```http
```http
POST '/api/persons/admin/{userId}/add'
```
```
Опис: Додає нову особу.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
**Body:**
firstName (String): Ім'я.
lastName (String): Прізвище.
dateOfBirth (Date): Дата народження.
address (String): Адреса.
email (String): Електронна пошта.
Відповідь:
200 OK: Особа успішно додана.
400 Bad Request: Помилка у вхідних даних.
403 Forbidden: Відсутність дозволу на виконання дії.
404 Not Found: Адміністратора не знайдено.
---

#### Get Person By ID
```http
```http
GET '/api/persons/{personID}'
```
```
Опис: Отримує особу за її ідентифікатором.
Параметри запиту:
**Path:**
personID (Long): Ідентифікатор особи.
Відповідь:
200 OK: Особа знайдена.
404 Not Found: Особа не знайдена.
---

#### Get All Persons
```http
```http
GET '/api/persons'
```
```
Опис: Отримує список всіх осіб.
Відповідь:
200 OK: Список всіх осіб.
---

#### Update Person
```http
```http
PUT '/api/persons/admin/{userId}/{personID}'
```
```
Опис: Оновлює дані особи від імені адміністратора.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
personID (Long): Ідентифікатор особи.
**Body:**
firstName (String): Оновлене ім'я.
lastName (String): Оновлене прізвище.
dateOfBirth (Date): Оновлена дата народження.
address (String): Оновлена адреса.
email (String): Оновлена електронна пошта.
Відповідь:
200 OK: Особа оновлена.
400 Bad Request: Помилка у вхідних даних.
403 Forbidden: Відсутність дозволу на виконання дії.
404 Not Found: Особа не знайдена.
---

#### Delete Person
```http
```http
DELETE '/api/persons/admin/{userId}/{personID}'
```
```
Опис: Видаляє особу від імені адміністратора.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
personID (Long): Ідентифікатор особи.
Відповідь:
204 No Content: Особа видалена.
404 Not Found: Особа не знайдена.
## RolesController API
### Маршрути
---

#### Add Role
```http
```http
POST '/api/roles/admin/{userId}/add'
```
```
Опис: Додає нову роль користувача.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
**Body:**
roleName (String): Назва ролі.
description (String): Опис ролі.
Відповідь:
200 OK: Роль успішно додана.
400 Bad Request: Помилка у вхідних даних.
403 Forbidden: Відсутність дозволу на виконання дії.
404 Not Found: Адміністратора не знайдено.
---

#### Get Role By ID
```http
```http
GET '/api/roles/{roleID}'
```
```
Опис: Отримує роль за її ідентифікатором.
Параметри запиту:
**Path:**
roleID (Long): Ідентифікатор ролі.
Відповідь:
200 OK: Роль знайдена.
404 Not Found: Роль не знайдена.
---

#### Get All Roles
```http
```http
GET '/api/roles'
```
```
Опис: Отримує список всіх ролей.
Відповідь:
200 OK: Список всіх ролей.
---

#### Update Role
```http
```http
PUT '/api/roles/admin/{userId}/{roleID}'
```
```
Опис: Оновлює роль користувача від імені адміністратора.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
roleID (Long): Ідентифікатор ролі.
**Body:**
roleName (String): Оновлена назва ролі.
description (String): Оновлений опис ролі.
Відповідь:
200 OK: Роль оновлена.
400 Bad Request: Помилка у вхідних даних.
403 Forbidden: Відсутність дозволу на виконання дії.
404 Not Found: Роль не знайдена.
---

#### Delete Role
```http
```http
DELETE '/api/roles/admin/{userId}/{roleID}'
```
```
Опис: Видаляє роль користувача від імені адміністратора.
Параметри запиту:
**Path:**
userId (Long): Ідентифікатор адміністратора.
roleID (Long): Ідентифікатор ролі.
Відповідь:
204 No Content: Роль видалено.
404 Not Found: Роль не знайдена.
API Документація для проєкту "Програмна система для автоматизації роботи бібліотек"

1. BooksController API
Маршрути
Add Book
POST '/api/books/admin/{userId}/add'
•	Опис: Додає нову книгу від імені адміністратора.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
o	Body:
	title (String): Назва книги.
	author (String): Автор книги.
	categoryID (Long): Ідентифікатор категорії.
	isbn (String): ISBN книги.
	publicationDate (Date): Дата публікації.
	status (String): Статус доступності.
•	Відповідь:
o	200 OK: Книга була успішно додана.
o	400 Bad Request: Помилка у вхідних даних.
o	403 Forbidden: Відсутність дозволу на виконання дії.
o	404 Not Found: Адміністратора не знайдено.
Get Book By ID
GET '/api/books/{bookID}'
•	Опис: Отримує книгу за її унікальним ідентифікатором.
•	Параметри запиту:
o	Path:
	bookID (Long): Ідентифікатор книги.
•	Відповідь:
o	200 OK: Книга знайдена.
o	404 Not Found: Книга не знайдена.
Get All Books
GET '/api/books'
•	Опис: Отримує всі книги.
•	Відповідь:
o	200 OK: Список всіх книг.
Update Book
PUT '/api/books/admin/{userId}/{bookID}'
•	Опис: Оновлює дані книги від імені адміністратора.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
	bookID (Long): Ідентифікатор книги.
o	Body:
	title (String): Назва книги.
	author (String): Автор книги.
	categoryID (Long): Ідентифікатор категорії.
	isbn (String): ISBN книги.
	publicationDate (Date): Дата публікації.
	status (String): Статус доступності.
•	Відповідь:
o	200 OK: Книга оновлена.
o	400 Bad Request: Помилка у вхідних даних.
o	403 Forbidden: Відсутність дозволу на виконання дії.
o	404 Not Found: Книга не знайдена.
Delete Book
DELETE '/api/books/admin/{userId}/{bookID}'
•	Опис: Видаляє книгу від імені адміністратора.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
	bookID (Long): Ідентифікатор книги.
•	Відповідь:
o	204 No Content: Книга видалена.
o	404 Not Found: Книга не знайдена.

2. ReadersController API
Маршрути
Add Reader
POST '/api/readers/admin/{userId}/add'
•	Опис: Додає нового читача від імені адміністратора.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
o	Body:
	firstName (String): Ім'я читача.
	lastName (String): Прізвище читача.
	email (String): Електронна пошта читача.
	phone (String): Номер телефону читача.
•	Відповідь:
o	200 OK: Читач був доданий.
o	400 Bad Request: Помилка у вхідних даних.
o	403 Forbidden: Відсутність дозволу на виконання дії.
o	404 Not Found: Адміністратора не знайдено.
Get Reader By ID
GET '/api/readers/{readerID}'
•	Опис: Отримує читача за його ідентифікатором.
•	Параметри запиту:
o	Path:
	readerID (Long): Ідентифікатор читача.
•	Відповідь:
o	200 OK: Читач знайдений.
o	404 Not Found: Читач не знайдений.
Get All Readers
GET '/api/readers'
•	Опис: Отримує список всіх читачів.
•	Відповідь:
o	200 OK: Список всіх читачів.
Update Reader
PUT '/api/readers/admin/{userId}/{readerID}'
•	Опис: Оновлює дані читача від імені адміністратора.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
	readerID (Long): Ідентифікатор читача.
o	Body:
	firstName (String): Ім'я читача.
	lastName (String): Прізвище читача.
	email (String): Електронна пошта.
	phone (String): Номер телефону.
•	Відповідь:
o	200 OK: Читач оновлений.
o	400 Bad Request: Помилка у вхідних даних.
o	403 Forbidden: Відсутність дозволу на виконання дії.
o	404 Not Found: Читач не знайдений.
Delete Reader
DELETE '/api/readers/admin/{userId}/{readerID}'
•	Опис: Видаляє читача від імені адміністратора.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
	readerID (Long): Ідентифікатор читача.
•	Відповідь:
o	204 No Content: Читач видалений.
o	404 Not Found: Читач не знайдений.

3 ItemsController API
Маршрути
Add Item
POST '/api/items/admin/{userId}/add'
•	Опис: Додає новий екземпляр книги.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
o	Body:
	bookId (Long): Ідентифікатор книги.
	statusId (Long): Ідентифікатор статусу книги.
	barcode (String): Штрих-код екземпляра книги.
	location (String): Місцезнаходження книги.
	dateAdded (Date): Дата додавання екземпляра.
•	Відповідь:
o	200 OK: Екземпляр книги успішно доданий.
o	400 Bad Request: Помилка у вхідних даних.
o	403 Forbidden: Відсутність дозволу на виконання дії.
o	404 Not Found: Книга або статус не знайдені.
Get Item By ID
GET '/api/items/{itemID}'
•	Опис: Отримує екземпляр книги за його ідентифікатором.
•	Параметри запиту:
o	Path:
	itemID (Long): Ідентифікатор екземпляра.
•	Відповідь:
o	200 OK: Екземпляр знайдений.
o	404 Not Found: Екземпляр не знайдений.
Get All Items
GET '/api/items'
•	Опис: Отримує список всіх екземплярів книг.
•	Відповідь:
o	200 OK: Список всіх екземплярів книг.
Update Item
PUT '/api/items/admin/{userId}/{itemID}'
•	Опис: Оновлює екземпляр книги від імені адміністратора.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
	itemID (Long): Ідентифікатор екземпляра.
o	Body:
	statusId (Long): Ідентифікатор нового статусу книги.
	location (String): Оновлене місцезнаходження книги.
•	Відповідь:
o	200 OK: Екземпляр оновлений.
o	400 Bad Request: Помилка у вхідних даних.
o	403 Forbidden: Відсутність дозволу на виконання дії.
o	404 Not Found: Екземпляр або статус не знайдені.
Delete Item
DELETE '/api/items/admin/{userId}/{itemID}'
•	Опис: Видаляє екземпляр книги від імені адміністратора.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
	itemID (Long): Ідентифікатор екземпляра.
•	Відповідь:
o	204 No Content: Екземпляр видалено.
o	404 Not Found: Екземпляр не знайдений.

4. CategoriesController API
Маршрути
Add Category
POST '/api/categories/admin/{userId}/add'
•	Опис: Додає нову категорію від імені адміністратора.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
o	Body:
	name (String): Назва категорії.
	description (String): Опис категорії.
•	Відповідь:
o	200 OK: Категорія була успішно додана.
o	400 Bad Request: Помилка у вхідних даних.
o	403 Forbidden: Відсутність дозволу на виконання дії.
o	404 Not Found: Адміністратора не знайдено.
Get Category By ID
GET '/api/categories/{categoryID}'
•	Опис: Отримує категорію за її ідентифікатором.
•	Параметри запиту:
o	Path:
	categoryID (Long): Ідентифікатор категорії.
•	Відповідь:
o	200 OK: Категорія знайдена.
o	404 Not Found: Категорія не знайдена.
Get All Categories
GET '/api/categories'
•	Опис: Отримує список всіх категорій.
•	Відповідь:
o	200 OK: Список всіх категорій.
Update Category
PUT '/api/categories/admin/{userId}/{categoryID}'
•	Опис: Оновлює дані категорії від імені адміністратора.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
	categoryID (Long): Ідентифікатор категорії.
o	Body:
	name (String): Назва категорії.
	description (String): Опис категорії.
•	Відповідь:
o	200 OK: Категорія оновлена.
o	400 Bad Request: Помилка у вхідних даних.
o	403 Forbidden: Відсутність дозволу на виконання дії.
o	404 Not Found: Категорія не знайдена.
Delete Category
DELETE '/api/categories/admin/{userId}/{categoryID}'
•	Опис: Видаляє категорію від імені адміністратора.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
	categoryID (Long): Ідентифікатор категорії.
•	Відповідь:
o	204 No Content: Категорія видалена.
o	404 Not Found: Категорія не знайдена.

5. StatusesController API
Маршрути
Add Status
POST '/api/statuses/admin/{userId}/add'
•	Опис: Додає новий статус книги від імені адміністратора.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
o	Body:
	statusName (String): Назва статусу.
	description (String): Опис статусу.
•	Відповідь:
o	200 OK: Статус успішно доданий.
o	400 Bad Request: Помилка у вхідних даних.
o	403 Forbidden: Відсутність дозволу на виконання дії.
o	404 Not Found: Адміністратора не знайдено.
Get Status By ID
GET '/api/statuses/{statusID}'
•	Опис: Отримує статус за його ідентифікатором.
•	Параметри запиту:
o	Path:
	statusID (Long): Ідентифікатор статусу.
•	Відповідь:
o	200 OK: Статус знайдений.
o	404 Not Found: Статус не знайдений.
Get All Statuses
GET '/api/statuses'
•	Опис: Отримує список всіх статусів.
•	Відповідь:
o	200 OK: Список всіх статусів.
Update Status
PUT '/api/statuses/admin/{userId}/{statusID}'
•	Опис: Оновлює статус книги від імені адміністратора.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
	statusID (Long): Ідентифікатор статусу.
o	Body:
	statusName (String): Назва статусу.
	description (String): Опис статусу.
•	Відповідь:
o	200 OK: Статус оновлений.
o	400 Bad Request: Помилка у вхідних даних.
o	403 Forbidden: Відсутність дозволу на виконання дії.
o	404 Not Found: Статус не знайдений.
Delete Status
DELETE '/api/statuses/admin/{userId}/{statusID}'
•	Опис: Видаляє статус книги від імені адміністратора.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
	statusID (Long): Ідентифікатор статусу.
•	Відповідь:
o	204 No Content: Статус видалений.
o	404 Not Found: Статус не знайдений.

6. UsersController API
Маршрути
Register User
POST '/api/users/register'
•	Опис: Реєструє нового користувача.
•	Параметри запиту:
o	Body:
	username (String): Ім'я користувача.
	email (String): Електронна пошта користувача.
	password (String): Пароль користувача.
	role (String): Роль користувача (наприклад, "admin", "reader").
•	Відповідь:
o	200 OK: Користувач успішно зареєстрований.
o	400 Bad Request: Помилка у вхідних даних.
o	409 Conflict: Користувач з таким іменем або email вже існує.
Login User
POST '/api/users/login'
•	Опис: Авторизує користувача.
•	Параметри запиту:
o	Body:
	username (String): Ім'я користувача.
	password (String): Пароль користувача.
•	Відповідь:
o	200 OK: Авторизація успішна.
o	400 Bad Request: Помилка у вхідних даних.
o	401 Unauthorized: Невірний логін чи пароль.
Get User By ID
GET '/api/users/{userID}'
•	Опис: Отримує користувача за його ідентифікатором.
•	Параметри запиту:
o	Path:
	userID (Long): Ідентифікатор користувача.
•	Відповідь:
o	200 OK: Користувач знайдений.
o	404 Not Found: Користувач не знайдений.
Get All Users
GET '/api/users'
•	Опис: Отримує список всіх користувачів.
•	Відповідь:
o	200 OK: Список всіх користувачів.
Update User
PUT '/api/users/admin/{userId}/{userID}'
•	Опис: Оновлює дані користувача від імені адміністратора.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
	userID (Long): Ідентифікатор користувача.
o	Body:
	username (String): Ім'я користувача.
	email (String): Електронна пошта.
	role (String): Роль користувача.
•	Відповідь:
o	200 OK: Користувач оновлений.
o	400 Bad Request: Помилка у вхідних даних.
o	403 Forbidden: Відсутність дозволу на виконання дії.
o	404 Not Found: Користувач не знайдений.
Delete User
DELETE '/api/users/admin/{userId}/{userID}'
•	Опис: Видаляє користувача від імені адміністратора.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
	userID (Long): Ідентифікатор користувача.
•	Відповідь:
o	204 No Content: Користувач видалений.
o	404 Not Found: Користувач не знайдений.

8. HistController API
Маршрути
Add History Record
POST '/api/hist/admin/{userId}/add'
•	Опис: Додає запис історії видачі книги.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
o	Body:
	readerId (Long): Ідентифікатор читача.
	itemId (Long): Ідентифікатор екземпляра книги.
	borrowDate (Date): Дата видачі книги.
	returnDate (Date): Дата повернення книги.
•	Відповідь:
o	200 OK: Запис успішно додано.
o	400 Bad Request: Помилка у вхідних даних.
o	403 Forbidden: Відсутність дозволу на виконання дії.
o	404 Not Found: Читач або екземпляр не знайдені.
Get History By ID
GET '/api/hist/{histID}'
•	Опис: Отримує запис історії видачі книги за його ідентифікатором.
•	Параметри запиту:
o	Path:
	histID (Long): Ідентифікатор запису.
•	Відповідь:
o	200 OK: Запис знайдений.
o	404 Not Found: Запис не знайдений.
Get All History
GET '/api/hist'
•	Опис: Отримує всі записи історії видачі книг.
•	Відповідь:
o	200 OK: Список всіх записів.
Update History Record
PUT '/api/hist/admin/{userId}/{histID}'
•	Опис: Оновлює запис історії видачі книги від імені адміністратора.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
	histID (Long): Ідентифікатор запису.
o	Body:
	returnDate (Date): Оновлена дата повернення книги.
•	Відповідь:
o	200 OK: Запис оновлений.
o	400 Bad Request: Помилка у вхідних даних.
o	403 Forbidden: Відсутність дозволу на виконання дії.
o	404 Not Found: Запис не знайдений.
Delete History Record
DELETE '/api/hist/admin/{userId}/{histID}'
•	Опис: Видаляє запис історії видачі книги від імені адміністратора.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
	histID (Long): Ідентифікатор запису.
•	Відповідь:
o	204 No Content: Запис видалено.
o	404 Not Found: Запис не знайдений.

9. PersonsController API
Маршрути
Add Person
POST '/api/persons/admin/{userId}/add'
•	Опис: Додає нову особу.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
o	Body:
	firstName (String): Ім'я.
	lastName (String): Прізвище.
	dateOfBirth (Date): Дата народження.
	address (String): Адреса.
	email (String): Електронна пошта.
•	Відповідь:
o	200 OK: Особа успішно додана.
o	400 Bad Request: Помилка у вхідних даних.
o	403 Forbidden: Відсутність дозволу на виконання дії.
o	404 Not Found: Адміністратора не знайдено.
Get Person By ID
GET '/api/persons/{personID}'
•	Опис: Отримує особу за її ідентифікатором.
•	Параметри запиту:
o	Path:
	personID (Long): Ідентифікатор особи.
•	Відповідь:
o	200 OK: Особа знайдена.
o	404 Not Found: Особа не знайдена.
Get All Persons
GET '/api/persons'
•	Опис: Отримує список всіх осіб.
•	Відповідь:
o	200 OK: Список всіх осіб.
Update Person
PUT '/api/persons/admin/{userId}/{personID}'
•	Опис: Оновлює дані особи від імені адміністратора.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
	personID (Long): Ідентифікатор особи.
o	Body:
	firstName (String): Оновлене ім'я.
	lastName (String): Оновлене прізвище.
	dateOfBirth (Date): Оновлена дата народження.
	address (String): Оновлена адреса.
	email (String): Оновлена електронна пошта.
•	Відповідь:
o	200 OK: Особа оновлена.
o	400 Bad Request: Помилка у вхідних даних.
o	403 Forbidden: Відсутність дозволу на виконання дії.
o	404 Not Found: Особа не знайдена.
Delete Person
DELETE '/api/persons/admin/{userId}/{personID}'
•	Опис: Видаляє особу від імені адміністратора.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
	personID (Long): Ідентифікатор особи.
•	Відповідь:
o	204 No Content: Особа видалена.
o	404 Not Found: Особа не знайдена.
RolesController API
Маршрути
Add Role
POST '/api/roles/admin/{userId}/add'
•	Опис: Додає нову роль користувача.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
o	Body:
	roleName (String): Назва ролі.
	description (String): Опис ролі.
•	Відповідь:
o	200 OK: Роль успішно додана.
o	400 Bad Request: Помилка у вхідних даних.
o	403 Forbidden: Відсутність дозволу на виконання дії.
o	404 Not Found: Адміністратора не знайдено.
Get Role By ID
GET '/api/roles/{roleID}'
•	Опис: Отримує роль за її ідентифікатором.
•	Параметри запиту:
o	Path:
	roleID (Long): Ідентифікатор ролі.
•	Відповідь:
o	200 OK: Роль знайдена.
o	404 Not Found: Роль не знайдена.
Get All Roles
GET '/api/roles'
•	Опис: Отримує список всіх ролей.
•	Відповідь:
o	200 OK: Список всіх ролей.
Update Role
PUT '/api/roles/admin/{userId}/{roleID}'
•	Опис: Оновлює роль користувача від імені адміністратора.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
	roleID (Long): Ідентифікатор ролі.
o	Body:
	roleName (String): Оновлена назва ролі.
	description (String): Оновлений опис ролі.
•	Відповідь:
o	200 OK: Роль оновлена.
o	400 Bad Request: Помилка у вхідних даних.
o	403 Forbidden: Відсутність дозволу на виконання дії.
o	404 Not Found: Роль не знайдена.
Delete Role
DELETE '/api/roles/admin/{userId}/{roleID}'
•	Опис: Видаляє роль користувача від імені адміністратора.
•	Параметри запиту:
o	Path:
	userId (Long): Ідентифікатор адміністратора.
	roleID (Long): Ідентифікатор ролі.
•	Відповідь:
o	204 No Content: Роль видалено.
o	404 Not Found: Роль не знайдена.

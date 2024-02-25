# Monitoring Service

### Примеры http запросов находятся в [этом файле](src/main/resources/http/examples.http)
### Основная информация

- Проект написан на Java 17.0.4
- Для развёртывания проекта использовался контейнер сервлетов Apache Tomcat 10.1.15
- Для сборки проекта использовался Apache Maven (maven-war-plugin для упаковки проекта)
- Для автогенерации boilerplate кода использовалась библиотека Lombok.
- Для реализации АОП-функционала использовалась библиотека AspectJ
- Для тестирования проекта использовались библиотеки JUnit 5 и Mockito.

### Инструкция по запуску
1. Запустить docker-контейнер с БД PostgreSQL:

    ```
    docker-compose up -d
    ```
   
2. В моделях стартеров выполнить команду для публикацию зависимостей в локальный репозиторий:

    ```
    mvn clean install
    ```

3. В monitoring-service выполнить команды:

    ```
    mvn clean package
    mvn spring-boot:run
    ```


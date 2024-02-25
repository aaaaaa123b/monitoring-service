# Monitoring Service

### Примеры http запросов находятся в [этом файле](src/main/resources/http/examples.http)

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



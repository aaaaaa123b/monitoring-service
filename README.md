# Monitoring Service

- Проект написан на Java 17.0.4.
- Для сборки проекта использовался Apache Maven.
- Для автогенерации boilerplate кода использовалась библиотека Lombok.
- Для тестирования проекта использовались библиотеки JUnit 5 и Mockito.

1. Запустить docker-контейнер с БД PostgreSQL:

```
docker-compose up -d
```


2. После запуска БД скомпилировать проект и упаковать его в .jar архив:

```
mvn clean package
```

2. Перейти в директорию с архивом:

```
cd /target
```

3. Запустить проект:

```
java -jar .\monitoring-service-1.0-SNAPSHOT.jar
```
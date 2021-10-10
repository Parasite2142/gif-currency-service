## Подготовка
1. Получить api_id к апи предоставляющему информацию по ценности валют, https://docs.openexchangerates.org/ <br/> 
2. Получить api_key для гиф сервиса по данной инструкции https://developers.giphy.com/docs/api#quick-start-guide

### Обязательные конфигурации
1. api.сurrency.appId: ваш id полученный в подготовка.1
2. api.giphy-api.appId: ваш id полученный в подготовка.2

### Необязательные конфигурации
1. server.port: изначально 8081
2. baseCurrency: изначально RUB, валюта по отношению к которой мы сравниваем рост.

## Сборка и запуск
1. Easy-way: в корне проекта gradle clean bootJar, java -jar .\gif-currency-service-0.0.1-SNAPSHOT.jar<br/> -Dserver.port=8888 и другие необходимые конфиги, либо редактировать application.yaml
2. Dockerfile: в корне проекта docker build image -t name:tag . <br/> После того как образ был сделан <br/>docker container run --rm <br/>-e "api.currency.appId=ваш id из 1.1" <br/> -e "api.giphy-api.apiId=ваш id из 1.2" <br/>-e "server.port=8082" <br/> -p 8082:8082 docker.io/library/name:tag
3. boot-docker-image: в корне проекта gradle bootBuildImage запуск контейнера идентичен примеру выше <br/> image будет иметь подобно название docker.io/library/gif-currency-service:0.0.1-SNAPSHOT p.s версия может отличаться


### Описания api

http://localhost:8081/api/v1/rich-broke/{currCode}

Проверяет если курс по отношению к базовой валюте за сегодня стал выше вчерашнего, 
то отдаем рандомную гиф из тредов rich иначе возвращаем гиф из тредов broke

### Ограничения работы с api
~~1. Если передать код валюты, которая при этом является базовой валютой, возвращает ошибку.~~
2. Если валюты нету в списке валют, которая предоставляется https://docs.openexchangerates.org/ так-же возвращает ошибку.<br/> Список валют, можно посмотреть в корне проекта в файле Currencies.json либо вызвать GET http://localhost:8081/api/v1/rich-broke/currencies
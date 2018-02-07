# Demo project for PropellerAds QA Automation Meetup#2
UI tests for PropellerAds SSP
[Слайды](https://www.slideshare.net/ssuser0cca6c/junit-87445136)

Тестовые сценарии носят ознакомительный характер 
и подготовлены для демонстрации концепций JUnit 5

JUnit 5.0.3 + Selenide 4.10.01 + Allure 2.5

#### Запуск локально в chrome
1. Установить JDK 1.8, maven 3.3.9+
2. Запустить тесты, вызвав в директории проекта
```
$ mvn clean test -Dselenide.browser=chrome
```
#### Запуск удаленно в chrome (на Selenoid, Selenium grid, Zalenium etc)
1. Установить JDK 1.8, maven 3.3.9+
2. Запустить тесты, вызвав в директории проекта
```
$ mvn clean test -Dselenide.browser=chrome -Dis.remote=true -Dremote.driver.url=[URL вашего hub]
```
#### Отчет Allure
1. После запуска тестов, в директории проекта
```
$ mvn allure:serve
```
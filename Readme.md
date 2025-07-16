# healenium + Java - Selenium on Maven Framework
- orchestrated by ankur malviya


### To setup Healenium see the tutorial:

https://automationmentors.blogspot.com/2023/12/healenium-updated-dec2023.html

Youtube demo for complete setup : https://www.youtube.com/watch?v=Xg_sqE4VUyU

## How to start
### 1.Start Healenium backend from infra folder

```cd infra```

```docker-compose up -d```

To download this file into your project use this command:

```$ curl https://raw.githubusercontent.com/healenium/healenium-example-maven/master/infra/docker-compose.yml -o docker-compose.yml```

Create /db/sql folder on the same level in your project. Add init.sql file into ./db/sql/init.sql folder in your project via command:

```$ curl https://raw.githubusercontent.com/healenium/healenium-client/master/example/init.sql -o init.sql```

Verify that images ```healenium/hlm-backend:3.4.8``` (or latest version ) and ```postgres:11-alpine```  and ```healenium/hlm-selector-imitator:1.2``` are up and running

In addition use Selenium - Java dependencies on POM : 4.16.1 version (or latest version)

Additional (optional): Testng

### 2. Project structure
```
|__infra
    |__db/sql
        |__init.sql
    |__docker-compose.yml
|__src/main/java/
|__src/test/java/
|__pom.xml
``` 

### 3.Run test in terminal with maven

In ```BaseTest.java``` class select necessary driver: **LOCAL**, **PROXY** or **REMOTE** and browser to run: chrome, firefox or edge.

```driver = new DriverContext(DriverType.LOCAL).getDriver(BrowserType.CHROME);```

**LOCAL** - used for local run. It's been set by default in BaseTest.java class. For this driver should be used docker-compose file from test example.

**PROXY** - used if you're running tests using healenium-proxy. For this driver you need to set docker-compose containers as in example by link:
https://github.com/healenium/healenium-example-dotnet/blob/master/infra/docker-compose.yml

**REMOTE** - used if you-re running test on remote machine. Do not forget to provide necessary host. In this test example it's been used remote machine with Selenoid.

In ```BaseTest.java``` class select necessary framework: **SELENIUM** or **SELENIDE**.

```pages = new FrameworkContext(FrameworkType.SELENIDE, driver).setFramework();```

If you want to execute all tests, please use the command: ```mvn clean test```


### 4.After test execution you should see generated report link in command line logs


Report contains only healed locators with old-new values and a button that tells if healing was successful for further algorithm corrections


### 5. Screenshots & Resuts (include healed locators information ) can be found on HLM-Baclkend running on localhost:7878

Also you could take a screenshots for your com.epam.healenium.tests like it implements here: BaseTest.screenshot

### 6. @DisableHealing annotation

If don't want to use Healenium in some methods just use @DisableHealing annotation. 




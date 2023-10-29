# MİKROSERVİS İŞLEMLERİ VE NOTLARIM

## 1. Kurulum adımları

    ### 1.1. Boş bir gradle projesi açtık.
    ### 1.2. dependencies.gradle dosyasını kodladık
        #### 1.2.1. ext{} bloğu içerisindeki kütüphaneleri projemize dahil ettik.
        #### 1.2.2. versions{} bloğu içerisindeki kütüphanelerin versiyonlarını belirledik.
        #### 1.2.3. libs{} bloğu içerisinde kullanacağımız kütüphaneleri belirledil.
    ### 1.3. build.gradle dosyasını kodladık, bu dosya içinde tüm alt projelerimizde ortak
    olarak kullanacağımız kütüphaneleri belirledik.
    ### 1.4. uygulamamızın mimarisi gereği ihtiyaç duyduğumuz mikroservisleri belirleyerek onları
    modül olarak ekledik.
    ### 1.5. her bir modül için eklememiz gereken aşağıdaki kod bloğunu
    build.gradle dosyalarına ekledik.
```
    buildscript {
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${versions.springBoot}")
        }
    }
```
    ### 1.6. tüm modüllerimize monolitik mimaride kullandığımız default kodlamaları ekledik.
    ### 1.7. Eğer bir modül içinde kullanmak istediğimiz özel bağımlılıklar var ise bunları 
    build.gradle dosyalarına ekledik.

## 2. MongoDB Kurulum ve Kullanım

### 2.1. MongoDB Docker üzerinde çalıştırmak

    docker kurulu olan bir sistem üzerinde command satırına girerek aşağıda 
    yeralan komutları yazarak docker üzerinde çalıştırıyoruz.

    > docker run --name dockermongo -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=BilgeAdmin -e MONGO_INITDB_ROOT_PASSWORD=root -d mongo
### 2.2. MongoDB ye bağlanmak

    DİKKAT!!!
    mongodb kullanırken admin kullanıcısı ve şifrelerini veritabanlarına 
    erişmek için kullanmayınız.
    Yeni bir veritabanı oluşturmak için admin kullanıcısı ile işlem yapılır ve
    bu veritananına atanmak üzere yeni bir kullanıcı oluşturulur. böylelikle
    admin kullanıcısına ihtiyaç kalmadan DB üzerinde işlemler gerçekleştirilir.
    1- Öncelikle bir veritabanı oluşturuyorsunuz. (FacebookDB)
    2- mongosh  kullanarak konsolda 'use DB_ADI' yazıyorsunuz ve ilgili DB ye geçiş yapıyorsunuz
    3- yine aynı ekranda yeni bir kullanıcı oluşturmanız gereklidir. bu kullanıcı yetkili olacaktır.
    db.createUser({
        user: "Java7User",
        pwd: "root",
        roles: ["readWrite", "dbAdmin"]
    })
    -- db.createUser({user: "Java7User",pwd: "root",roles: ["readWrite", "dbAdmin"]})

##  3. RabbitMQ Kurulum ve Kullanım

    ### 3.1. RabbitMQ Docker üzerinde çalıştırmak
    docker run -d --name my-rabbitmq -e RABBITMQ_DEFAULT_USER=java7 -e RABBITMQ_DEFAULT_PASS=root -p 5672:5672 -p 15672:15672 rabbitmq:3-management

    ### 3.2. RabbitMQ ye bağlanmak
    gradle import -> org.springframework.boot:spring-boot-starter-amqp:VERSION
    Rabbit Config yapılandırılır ve kuyruk yapısı tanımlanır.

## 4. Zipkin Server kurmak ve Kullanmak

    docker run --name zipkinfb -d -p 9411:9411 openzipkin/zipkin

    Zipkin için gerekli bağımlılılar:    
    'org.springframework.cloud:spring-cloud-starter-sleuth:3.1.7'
    'org.springframework.cloud:spring-cloud-sleuth-zipkin:3.1.7'

    application.yml içine eklenilecek kodlar:
    zipkin:
        enabled: true
        base-url: http://localhost:9411
        service:
          name: config-server

## 5. Redis Kurulum ve Kullanım

    docker run --name localredis -d -p 6379:6379 redis

    Redis için gerekli bağımlılılar:
    'org.springframework.boot:spring-boot-starter-data-redis:$VERSION'

    Redis için bağlantı kodlamalarını yapmakmız gerekli:
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {

        return new LettuceConnectionFactory(
                new RedisStandaloneConfiguration("localhost", 6379));
    }

## 6. ElasticSearch Kurulum ve Kullanım

    DİKKAT!!!
    Spring ile kullanımında sürüm önemlidir. Hangi Spring boot sürümünü
    kullandıysanız ona uygun bir ElasticSearch sürümü kullanmalısınız.

    1- docker network create somenetwork
    2- docker run -d --name elasticsearch --net somenetwork -p 9200:9200 -p 9300:9300 -e ES_JAVA_OPTS="-Xms512m -Xmx2048m" -e "discovery.type=single-node" elasticsearch:7.17.9

    ElasticSearch için gerekli bağımlılılar:
    'org.springframework.boot:spring-boot-starter-data-elasticsearch:$VERSION'

    -------------------- English Version---------------------------

    
Tabii ki, işte çeviri:

1. Installation steps
1.1. Open a blank Gradle project.
1.2. Code the dependencies.gradle file
1.2.1. Included the libraries in the ext{} block to our project.1.2.2. Determined the versions of the libraries in the versions{} block.1.2.3. Specified the libraries we will use in the libs{} block.

1.3. Coded the build.gradle file, in this file we determined the libraries that we will use in common in all our sub-projects.
1.4. Determined the microservices that we need for the architecture of our application and added them as modules.
1.5. Added the following code block that we need to add to each module build.gradle files.
buildscript {
dependencies {
classpath("org.springframework.boot:spring-boot-gradle-plugin:${versions.springBoot}")
}
}
1.6. Added the default codings that we use in monolithic architecture to all our modules.
1.7. If there are any special dependencies that we want to use in a module, we added them to the build.gradle files.
2. MongoDB Setup and Usage
2.1. Run MongoDB on Docker
Run the following commands on a system with Docker installed by entering the command line below.

docker run --name dockermongo -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=BilgeAdmin -e MONGO_INITDB_ROOT_PASSWORD=root -d mongo

2.2. Connect to MongoDB
IMPORTANT!!!Do not use admin user and passwords to access databases when using MongoDB.To create a new database, the admin user is used to process and a new user is created to be assigned to this database. This way, there is no need for the admin user to perform operations on the DB.

1. First, you create a database. (FacebookDB)2. You use mongosh to write 'use DB_NAME' in the console and switch to the relevant DB3. You need to create a new user on the same screen. This user will be authorized.

db.createUser({
user: "Java7User",
pwd: "root",
roles: ["readWrite", "dbAdmin"]
})
-- db.createUser({user: "Java7User",pwd: "root",roles: ["readWrite", "dbAdmin"]})

## 3. RabbitMQ Setup and Usage

3.1. Run RabbitMQ on Docker
docker run -d --name my-rabbitmq -e RABBITMQ_DEFAULT_USER=java7 -e RABBITMQ_DEFAULT_PASS=root -p 5672:5672 -p 15672:15672 rabbitmq:3-management

3.2. Connect to RabbitMQ
gradle import -> org.springframework.boot:spring-boot-starter-amqp:VERSIONRabbit Config is configured and the queue structure is defined.

4. Setting up and Using Zipkin Server
docker run --name zipkinfb -d -p 9411:9411 openzipkin/zipkin

Required dependencies for Zipkin:'org.springframework.cloud:spring-cloud-starter-sleuth:3.1.7''org.springframework.cloud:spring-cloud-sleuth-zipkin:3.1.7'

Codes to be added to application.yml:

zipkin:
enabled: true
base-url: http://localhost:9411
service:
name: config-server
5. Redis Setup and Usage
docker run --name localredis -d -p 6379:6379 redis

Required dependencies for Redis:'org.springframework.boot:spring-boot-starter-data-redis:$VERSION'

We need to do the connection codings for Redis:

@Bean
public LettuceConnectionFactory redisConnectionFactory() {

return new LettuceConnectionFactory(
new RedisStandaloneConfiguration("localhost", 6379));
}

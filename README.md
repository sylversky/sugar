# Sugar ORM
##### (Sugar ORM for automatic one to many relationship and encrypted database)


Insanely easy way to work with Android databases. This is <b>custom version</b> based from master version of Sugar ORM.
Sugar ORM was built in contrast to other ORM's to have:

- A simple, concise, and clean integration process with minimal configuration.
- Automatic table and column naming through reflection.
- Support for migrations between different schema versions.

Official documentation can be found [here](http://satyan.github.io/sugar) - Check some examples below. The example application is provided in the **example** folder in the source.

## What is supported in this version
- Use <b>SugarRecord.insertOrUpdate( )</b> to insert or update the data
- Added <b>@IgnoreUpdate</b> annotation
- Automatic one to many insert and query
- Find All with return List
- Drop table
- Encrypted database

See below for more example and explaination ...


## Install
Add bintray repository on your project build.gradle
```
allprojects {
    repositories {
        ...
        maven {
            url 'https://dl.bintray.com/sylversky/AndroidLibrary/'
        }
    }
}
```


Add on your app build.gradle
```
dependencies {
    compile 'com.sylversky.library:sugarorm:1.0.0'
}
```

## Configuration

Extends SugarApp on your application class
```java
public class ClientApp extends SugarApp {

}
```
or you can manualy init the sugar orm on you application class
```java
public class ClientApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
```

Open your AndroidManifest.xml and put some like this inside appication tag:
```
<application ...
android:name=".ClientApp">
.
.
<meta-data android:name="DATABASE" android:value="sugar_example.db" />  //database file name
<meta-data android:name="VERSION" android:value="1" /> //database version
<meta-data android:name="QUERY_LOG" android:value="true" /> //database log
<meta-data android:name="DOMAIN_PACKAGE_NAME" android:value="yourentitypackage" /> //location of package for all of your entity table
.
.
</application>
```

## How to use

### Create Entity Table
```java
@Table
public class Person {
  Long id;
  String regId;
  String name;
  Date dob;
  
  public Person(){}
  
  public Person(Long id, String regId, String name, Date dob){
    this.id = id;
    this.regId = regId;
    this.name = name;
    this.dob = dob;
  }
  
}
```

This is will create table PERSON with columns ID,REG_ID,NAME,DOB on SQLite.
*** Field "Long id" is a <b>must</b> to declare. This will be the <b>primary key</b> (autoincrement) for your table.
*** When you need to create your own constructor, you <b>must</b> add an empty constructor

### Primary Key with another data type
The default primary key is with Long data type.
If you need other data type (for example String), just keep the "Long id" for primary key, add new filed and put the <b>@Unique</b> annotation.
```java
@Table
public class Person {
  Long id;
  @Unique
  String personId;
  
  String regId;
  String name;
  Date dob;
  
  public Person(){}
  
  public Person(Long id, String personId, String regId, String name, Date dob){
    this.id = id;
    this.personId = personId;
    this.regId = regId;
    this.name = name;
    this.dob = dob;
  }
  
}
```

### Insert
```java
Person person = new Person(1l,"abc12345","Leonardo", new Date());
SugarRecord.insertOrUpdate(person);
```

### Query
```java
Person person = SugarRecord.findById(Person.class, 1);
```

### Query with condition
```java
List<Person> person = SugarRecord.find(Person.class, "NAME = ?", "Leonardo");
```

### Query All
```java
List<Person> person = SugarRecord.findAll(Person.class);
```


### Update
```java
Person person = SugarRecord.findById(Person.class, 1);
person.regId = "def12345"
person.name = "Donatelo"; // modify the values
SugarRecord.update(person);
```

### Delete
```java
Person person = SugarRecord.findById(Person.class, 1);
SugarRecord.delete(person);
```

### Bulk Insert
```java
List<Person> persons = new ArrayList<>();
persons.add(new Person(1l,"abc12345","Leonardo", new Date()))
persons.add(new Person(2l,"def12345","Donatelo", new Date()))
persons.add(new Person(3l,"ghi12345","Michaelangelo", new Date()))
SugarRecord.insertOrUpdate(persons);
```

### Drop table
```java
SugarRecord.drop(Person.class);
```
*** Becarefull when use drop. This function will remove the structure of table.


### Ignore Update
You can ignoring update for specified column. Only work when use <b>SugarRecord.insertOrUpdate( )</b> function.
For example : we need to give flag data read/unread, but ignoring to replace when refreshed from api server.
Don't worry, you still can update specified column with SugarRecord.update( ) function.

##### Create entitiy table with @IgnoreUpdate
```java
@Table
public class Person {
  Long id;
  String regId;
  String name;
  Date dob;
  @IgnoreUpdate
  boolean read;
  
  public Person(){}
  
  public Person(Long id, String regId, String name, Date dob){
    this.id = id;
    this.regId = regId;
    this.name = name;
    this.dob = dob;
  }
  
}
```


##### Then do some Insert
```java
Person person = new Person(1l,"abc12345","Leonardo", new Date());
SugarRecord.insertOrUpdate(person);
```

Now read flag is 'false';

##### Update read flag to 'true'
```java
Person person = SugarRecord.findById(Person.class, 1);
person.read = true;
SugarRecord.update(person);
```

Now read flag is 'true';

##### Try to Insert again
```java
Person person = new Person(1l,"abc12345","Leonardo", new Date());
SugarRecord.insertOrUpdate(person);
```
This will insert data person with default read value is 'false', but because use @IgnoreUpdate now read flag still 'true'


### Automatic One To Many
When you need other entity be member of your entity, Sugar will automatically manage it.

##### Create entitiy table
```java
@Table
public class Address {
    Long id;
    String street;
    String province;
    
    public Address(){}
    
    public Address(Long id, String street, String province){
        this.id = id;
        this.street = street;
        this.province = province;
    }
}
```

##### Create entitiy table with other entity member
```java
@Table
public class Person {
  Long id;
  String regId;
  String name;
  Date dob;
  List<Address> address;
  
  public Person(){}
  
  public Person(Long id, String regId, String name, Date dob, List<Address> address){
    this.id = id;
    this.regId = regId;
    this.name = name;
    this.dob = dob;
    this.address = address;
  }
  
}
```

##### Then do some Insert
```java
List<Address> addressList = new ArrayList();
addressList.add(new Address(1l,"my office","my province"));
addressList.add(new Address(2l,"my home","my countryside province"));

Person person = new Person(1l,"abc12345","Leonardo", new Date(), addressList);
SugarRecord.insertOrUpdate(person);
```
Person and address will automatically insert into different table.

##### Query
```java
Person person = SugarRecord.findById(Person.class, 1);
List<Address> addresList = person.address;
```
With execute just one find( ) function, person and address will automatically combined.

### Migration
1. Just declare your new column inside your entity.
2. Increase meta VERSION value on AndroidManifest.xml.
   ```
   <application ...
   android:name=".ClientApp">
   .
   .
   <meta-data android:name="VERSION" android:value="2" /> //database version
   .
   .
   </application>
   ```

Database schema will automatically updated.

### Encrypt Database
Just add SugarDbConfiguration with your password for encrypt when initialize Sugar

```java
public class ClientApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this, new SugarDbConfiguration().setEncryptedPassword("mysugarpassword"));
    }

    @Override
    public void onTerminate() {
        SugarContext.terminate();
        super.onTerminate();
    }
}
```

### When using ProGuard
```java
# Ensures entities remain un-obfuscated so table and columns are named correctly
-keep class com.yourpackage.yourapp.domainclasspackage.** { *; }
```

### Known Issues. 
#### 1. Instant Run. 
Instant-Run seems to prevent Sugar ORM from finding the "table" classes, therefore it cannot create the DB tables if you run the app for the first time 

When running your app for the first time Turn off Instant run once to allow for the DB tables to be created
You can enable it after the tables have been created. 

To disable Instant-Run in Android Studio: 

``(Preferences (Mac) or Settings (PC) -> Build, Execution, Deployment -> Instant Run -> Untick "Enable Instant Run..." )``

## Contributing

Please fork this repository and contribute back using [pull requests](https://github.com/satyan/sugar/pulls). Features can be requested using [issues](https://github.com/satyan/sugar/issues). All code, comments, and critiques are greatly appreciated.

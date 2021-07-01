# teamspeak-querry-bot
A simple teamspeak query bot for people, who need a simple template with a own auto-reconnector.
> **It is based on the [TeamSpeak-3-Java-API](https://github.com/TheHolyWaffle/TeamSpeak-3-Java-API.git) by [TheHolyWaffle](https://github.com/TheHolyWaffle).**

### Problems
There are no known problems except of **using a normal serverquery**. please use instead the one and only **serveradmin serverquerry** (got created with the **first start** of your teamspeak server). If you **forgot** your serveradmin password, [just reset it](https://support.teamspeak.com/hc/en-us/articles/360002712898-How-do-I-change-or-reset-the-password-of-the-serveradmin-Server-Query-account-).

### How i use it?
First do a new Project with maven and add this in the pom.xml:
```pom.xml
<repositories>
    <repository>
        <id>jcenter</id>
        <name>jcenter-bintray</name>
        <url>https://jcenter.bintray.com</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.theholywaffle</groupId>
        <artifactId>teamspeak3-api</artifactId>
        <version>1.2.0</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>1.6.1</version>
    </dependency>
</dependencies>
```
..then add all the files and change some variables in  the `TeamspeakQuerry.jar`.
***I strongly recommend to use the serveradmin querry!***
```java
/**
 * Some variables to easily change query login and server.
 * THIS IS THE MOST IMPORTANT STEP!
*/

public static String teamspeakHost = "localhost"; // Teamspeak host address
public static String teamspeakQueryName = "serveradmin"; // Teamspeak query name
public static String teamspeakQueryPassword = "123"; // Teamspeak query password
public static String teamspeakQueryNickname = "TestBot"; // Teamspeak query nickname

/**
 * Some variables for other Stuff
 * we will need this too!
*/

public static int AutoReconnectCheck = 15; // Sets how often the AutoReconnect will check in SECONDS
public static int AutoReconnectTry = 3; // Sets how often the AutoReconnect will try to reconnect, if it isn't connected in SECONDS
```

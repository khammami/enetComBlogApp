# BlogApp
In this practical, you build an app that uses the [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html). The app, called BlogApp, stores a list of posts in a Room database and displays the list in a RecyclerView. The BlogApp app is basic, but sufficiently complete that you can use it as a template to build on.

The BlogApp app does the following:

* Works with a database to get and save posts
* Displays all the posts in a RecyclerView in MainActivity.
* Opens a second Activity when the user taps the + FAB button. When the user enters a post, the app adds the post to the database and then the list updates automatically.
* Opens the same second Activity when user taps a post in the RecyclerView to edit it.
* On the second Activity , the user may choose a specific date for the post. If not, the post take the current date.
* Do not initializes the data in the database if there is no existing data.
* Add a menu item that allows the user to delete all the data.
* You also enable the user to swipe a post to delete it from the database.

## Application overview

<img width="200" src="./assets/48d8e7f54423c97c.png"> <img width="200" src="./assets/166762058738007.png"> <img width="200" src="./assets/92cf0052d87532d6.png">

<img width="200" src="./assets/adb8c01ef40ed9b9.png"> <img width="200" src="./assets/a468c9f6056cbbe4.png"> <img width="200" src="./assets/a3206d1c58d82ba9.png">

<img width="200" src="./assets/8bee37e8967aed27.png"> <img width="200" src="./assets/8af77b645db7c17c.png">
 
**Note**: Confirmation Alerts are optionals

You must follow the diagram below for your database table

![post_table](./assets/12a829f43ec92553.png)

## IMPORTANT
in order to store complex data like Date in RoomDatabase you need to use converters:

```java
public class Converters {
   @TypeConverter
   public static Date fromTimestamp(Long value) {
       return value == null ? null : new Date(value);
   }

   @TypeConverter
   public static Long dateToTimestamp(Date date) {
       return date == null ? null : date.getTime();
   }
}
```
 
then add the following annotation to your RoomDatabase class under @Database :

 ```java
@Database(entities = ...)
@TypeConverters({Converters.class})
```
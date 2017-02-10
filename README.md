# JavaRant
A devRant API wrapper for Java.

## Using JavaRant
JavaRant is available on Maven, simply add this dependency to your `pom.xml` file:

```
<dependency>
	<groupId>com.scorpiac.javarant</groupId>
	<artifactId>javarant</artifactId>
	<version>1.0</version>
</dependency>
```

## Class overview

### DevRant
This is the main class to get rants.
From here you can get a list of rants, get a random rant, or search for rants containing a specific term.

Examples:

```
// Get the first 10 rants.
Rant[] rants = DevRant.getRants(Sort.ALGO, 10, 0);

// Get a random rant.
Rant random = DevRant.surprise();

// Search for rants containing "wtf".
Rant[] wtf = DevRant.search("wtf");

// Get the weekly rants.
Rant[] weekly = DevRant.weekly();
```

### RantContent
This is the base class for rants and comments, which have the following attributes:

- id
- user
- upvotes
- downvotes
- content
- image

Additionally you can call `getScore()` which calculates the total score.

### Rant
Rants are get through one of the methods in `DevRant`, or by an id (`Rant.byId(id)`).
The latter will throw a `NoSuchRantException` if the id is invalid.
In addition to the `RantContent` attributes, a `Rant` also contains:

- tags
- commentCount
- comments

The comments can be accessed by calling `getComments()`.
This will also fetch them if that has not been done yet.
Alternatively you can call `fetchComments()` to fetch the comments, or `fetchComments(force)` to force fetch them (i.e. fetch them again).

Examples:

```
// Get a rant by its id.
Rant rant = Rant.byId(136761);

// Fetch and get the comments.
Comment[] comments = rant.getComments();

// Force fetch the comments.
boolean success = rant.fetchComments(true);
```

### Collab
Collabs are an extension of rants.
They are get through `DevRant.collabs()` or by an id (`Collab.byId(id)`).
The latter will throw a `NoSuchRantException` if the id is invalid, or a `NotACollabException` if it is simply a rant instead of a collab.
Besides the attributes from a rant, collabs have the following attributes:

- projectType
- description
- techStack
- teamSize
- url

Like with a rant the comments can be fetched or force fetched.
There is also more data which needs to be fetched, similar to the comments this is done by calling `fetchData()` or `fetchData(force)` to force fetch it.

Examples:

```
// Get a collab by its id.
Collab collab = Collab.byId(420392);

// Force fetch the data.
boolean success = collab.fetchData(true);
```

### Comment
A comment only contains the attributes from `RantContent`.

### Image
An image contains a link to the image on devRant, and a width and height.

### User
Users can be get from rants, comments, by id (`User.byId(id)`) or username (`User.byUsername(username)`).
The latter two will throw a `NoSuchUserException` for non-existing users.
When they are get from a rant or comment, only the id, username and score are given.
The other data will be fetched and stored as soon as it's accessed.
Alternatively you can fetch the data by calling `fetchData()`, or `fetchData(force)` to force fetch the data (i.e. fetch it again).

Examples:

```
// Get a user by their id.
User me = User.byId(102959);

// Get a user by their username.
User alsoMe = User.byUsername("LucaScorpion");

// Fetch the data.
boolean success = me.fetchData();
```

### Sort
These are the different sort options which are used by `DevRant.getRants`.
You can pick between `ALGO`, `RECENT` or `TOP`.

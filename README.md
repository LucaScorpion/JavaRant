# JavaRant
A devRant API wrapper for Java.

[![Maven Central](https://img.shields.io/maven-central/v/com.scorpiac.javarant/javarant.svg)](https://mvnrepository.com/artifact/com.scorpiac.javarant/javarant)
[![Jenkins](https://img.shields.io/jenkins/s/https/jenkins.scorpiac.com/job/JavaRant/job/master.svg)](https://jenkins.scorpiac.com/job/JavaRant/)
[![Jenkins tests](https://img.shields.io/jenkins/t/https/jenkins.scorpiac.com/job/JavaRant/job/master.svg)](https://jenkins.scorpiac.com/job/JavaRant/job/master/lastCompletedBuild/testReport/)

## Using JavaRant
JavaRant is available on [Maven](http://mvnrepository.com/artifact/com.scorpiac.javarant/javarant), simply add this dependency to your `pom.xml` file:

```xml
<dependency>
	<groupId>com.scorpiac.javarant</groupId>
	<artifactId>javarant</artifactId>
	<version>2.0.0</version>
</dependency>
```

## Getting started

To access devRant simply create a new `DevRant` object:

```
DevRant devRant = new DevRant();
```

Most object that are returned from `DevRant` are wrapped in a `Result` object.
This will contain an optional result value, along with an error message.
If the optional result is empty, then an error occurred and the error message will be set.
For example:

```
Result<CommentedRant> result = devRant.getRant(832125);

if (!result.getValue().isPresent()) {
    System.out.println("An error occurred: " + result.getError());
} else {
    CommentedRant rant = result.getValue().get();
    System.out.println(rant.getUser().getUsername() + '\n' + rant.getText());
}
```

The `DevRant` class itself can be used to get specific rants and users.

```
// Get a specific rant.
Result<CommentedRant> rant = devRant.getRant(686001);

// Get a user by username.
Result<User> me = devRant.getUser("LucaScorpion");
```

The `DevRant` class contains 2 methods for getting to specific parts of the api.
First, `getFeed()` which returns a `DevRantFeed` object.
This is used to access the rant and collab feeds.

```
// Get the 10 latest rants.
Result<List<Rant>> recent = devRant.getFeed().getRants(Sort.RECENT, 10, 0);

// Get the 10 best stories.
Result<List<Rant>> stories = devRant.getFeed().getStories(Sort.TOP, 0);

// Get 10 collabs.
Result<List<Collab>> collabs = devRant.getFeed().getCollabs(10);
```

Second, `getAuth()` which returns a `DevRantAuth` object, which is used to access user functionality.
Note that a user needs to be logged in before this can be accessed.

```
// Log in to devRant.
char[] password = "<password>".toCharArray();
devRant.login("<username>", password);

// Upvote a rant.
devRant.getAuth().voteRant(832125, Vote.UP);

// Clear the vote on a comment.
devRant.getAuth().voteComment(832169, Vote.NONE);

// Log out to clear the token.
devRant.logout();
```

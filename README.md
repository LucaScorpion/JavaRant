# JavaRant
A devRant API wrapper for Java.

[![Maven Central](https://img.shields.io/maven-central/v/com.scorpiac.javarant/javarant.svg)](https://mvnrepository.com/artifact/com.scorpiac.javarant/javarant)
[![Jenkins](https://img.shields.io/jenkins/s/https/jenkins.scorpiac.com/job/JavaRant/job/rework-to-2.0.svg)]()

## Using JavaRant
JavaRant is available on [Maven](http://mvnrepository.com/artifact/com.scorpiac.javarant/javarant), simply add this dependency to your `pom.xml` file:

```
<dependency>
	<groupId>com.scorpiac.javarant</groupId>
	<artifactId>javarant</artifactId>
	<version>1.3</version>
</dependency>
```

## Getting started

To access devRant simply create a new `DevRant` object:

```
DevRant devRant = new DevRant();
```

You can then use it to get specific rants and users, or access the feed:

```
// Get a specific rant.
Optional<CommentedRant> rant = devRant.getRant(686001);

// Get a user by username.
Optional<User> me = devRant.getUser("LucaScorpion");

// Get the 10 newest rants.
List<Rant> recent = devRant.getFeed().getRants(Sort.RECENT);
```

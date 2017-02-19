# JavaRant
A devRant API wrapper for Java.

## Using JavaRant
JavaRant is available on Maven, simply add this dependency to your `pom.xml` file:

```
<dependency>
	<groupId>com.scorpiac.javarant</groupId>
	<artifactId>javarant</artifactId>
	<version>1.3</version>
</dependency>
```

## Class overview

### DevRant
This is the main class to interact with devRant.
To use it, you first need to create an instance of it.

```
DevRant devRant = new DevRant();
```

It can then be used to get rants and collabs from the feed or by searching.

```
List<Rant> rants = devRant.getRants(Sort.ALGO, 10, 0);
List<Rant> wtf = devRant.search("wtf");
List<Rant> weekly = devRant.getWeekly();

List<Collab> collabs = devRant.getCollabs();

Rant random = devRant.getSurprise();
```

It can also be used to get specific items based on an id (or username).
These methods will throw an exception if the specified item does not exist (`NoSuchRantException` for rants and `NoSuchUserException` for users).
Additionally, `getCollab` will also throw a `NotACollabException` if the requested rant is a normal rant instead of a collab.

```
Rant rant = devRant.getRant(422850);
Collab collab = devRant.getCollab(420025);

User me = devRant.getUser("LucaScorpion");
User me2 = devRant.getUser(102959);
```

To post or vote anything we first need to log in.
This will make a request to get an authentication token, which is stored for later use.
Your username and password will _not_ be stored.
The login method uses `char[]` instead of `String` for the password parameter for security reasons (and most of the times you have a password it should already be in the form of a `char[]`).

```
devRant.login("username", "password".toCharArray());
```

Voting on rants and comments can be done by passing the `Rant` or `Comment` object to vote on, or by directly passing the id.

```
devRant.vote(rant, Vote.DOWN);
devRant.vote(comment, Vote.NONE);

devRant.voteRant(429863, Vote.UP);
devRant.voteComment(424558, Vote.UP);
```

To post a rant you simply need the content and tags.
To post a comment you need the text and a `Rant` object, or the id of the rant to comment on.

```
devRant.postRant("Hello world!", "hello, not a rant");

devRant.postComment(rant, "This is a comment.");
devRant.postComment(424553, "And another one.");
```

You can log out to clear the authorization token.

```
devRant.logout();
```

### RantContent
This is the base class for rants and comments, which have the following attributes:

- id
- user
- upvotes
- downvotes
- score
- content
- image

### Rant
In addition to the `RantContent` attributes, rants also contain:

- tags
- commentCount
- comments

The comments can be accessed by calling `getComments()`.
This will also fetch them if that has not been done yet.
Alternatively you can call `fetchComments()` to fetch the comments, or `fetchComments(force)` to force fetch them (i.e. fetch them again).

### Collab
Collabs are an extension of rants.
Besides the attributes from a rant, collabs contain the following:

- projectType
- description
- techStack
- teamSize
- url

Like with a rant the comments can be fetched or force fetched.
There is also more data which needs to be fetched, similar to the comments this is done by calling `fetchData()` or `fetchData(force)` to force fetch it.

### Comment
A comment only contains the attributes from `RantContent`.

### Image
An image has the following attributes:

- url
- width
- height

### User
Users can be get from rants, comments, by id or username.
When they are get from a rant or comment, only the id, username and score are given.
The other data will be fetched and stored as soon as it's accessed.
Alternatively you can fetch the data by calling `fetchData()`, or `fetchData(force)` to force fetch the data (i.e. fetch it again).

### Sort
These are the different sort options, which are used when getting rants from the feed.
You can pick between `ALGO`, `RECENT` or `TOP`.

### Vote
These are the different vote options, which are used to vote on rants and comments.
The vote options are `UP`, `NONE` and `DOWN`.

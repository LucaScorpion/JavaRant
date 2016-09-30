# JavaRant
A devRant wrapper for Java.

## Class overview

### DevRant
This is the main class to get rants.
From here you can get a list of rants, get a random rant, or search for rants containing a specific term.

Examples:

	// Get the first 10 rants.
	Rant[] rants = DevRant.getRants(Sort.ALGO, 10, 0);
	// Get a random rant.
	Rant random = DevRant.surprise();
	// Search for rants containing "wtf".
	Rant[] wtf = DevRant.search("wtf");

### RantContent
This is the base class for rants and comments, which have the following attributes:

- id
- user
- upvotes
- downvotes
- content

Additionally you can call getScore() which calculates the total score.

### Rant
Rants are get through one of the methods in DevRant, or by an id using byId(id).
This will throw a NoSuchRantException if the id is invalid.
In addition to the RantContent attributes, a rant also contains:

- image
- tags
- commentCount
- comments

The comments can be accessed by calling getComments().
This will also fetch them if that has not been done yet.
Alternatively you can call fetchComments() to force fetching the comments.

Examples:

	// Get a rant by its id.
	Rant rant = Rant.byId(136761);
	// Fetch and get the comments.
	Comment[] comments = rant.getComments();
	// Force fetch the comments.
	boolean success = rant.fetchComments();

### Comment
A comment only contains the attributes from RantContent.

### User
Users can be get from rants, comments, or by id or username.
The latter two will throw a NoSuchUserException for non-existing users.
When they are get from a rant or comment, only the id, username and score are given.
The other data will be fetched and stored as soon as it's accessed.
Alternatively you can force fetch the data by calling fetchData().

Examples:

	// Get a user by their id.
	User me = User.byId(102959);
	// Get a user by their username
	User alsoMe = User.byUsername("LucaScorpion");
	// If the user data is not fetched, do so.
	if (!me.isFetched())
		boolean success = me.fetchData();

### Sort
This is an enum containing the sort options which are used by DevRant.getRants. You can pick between ALGO, RECENT or TOP.

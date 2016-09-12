# JavaRant
A devRant wrapper in Java

### Examples
	// Get the first 10 rants.
	Rant[] rants = DevRant.getRants(Sort.ALGO, 10, 0);
	
	// Get the comments of the first rant.
	Comment[] comments = rants[0].getComments();
	
	// Get the author of the first rant.
	User author = rants[0].getUser();
	author.fetchData();
	
	// Get a user by their id.
	User me = User.byId(102959);

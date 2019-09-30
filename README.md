# 32 Final Project #

## Purpose of project ##

When people sit down to watch a movie, the suggestions based of a particular film seem limited and sometimes unrelated. Additionally, you can't get suggestions from multiple streaming services at once: if you're on Netflix you get Netflix's suggestions, if you're on Hulu, you get Hulu's suggestions, etc. Our website is able to do this, using IMDB's data.<br/>
We assume that this would involve some web-scraping or using these services' APIs, but the specific criteria used for determining the search make this fairly open-ended, especially as we can prioritize criteria differently to get a better but decently fast search. Some of the criteria we consider are 
1. label-based suggestions regarding genre 
2. director and crew similarity across movies
3. the user's personal taste if they create a profile
4. the user's region(s)/language(s) 
5. the time period the movie comes from.
6. the general popularity of the movie.

The goals we'd initially outlined are:
1. Critical features: Ability to get more movie suggestions based on a more specific query (ex: instead of simply horror movies, you could look up slasher horror or psychological horror), or based off a number of criteria instead of just one. Also a database of films, their genre/subgenera, maybe part of the script, etc.
2. These features are being included because they are something that doesn't really exist for movie viewers. 
3. Challenges: We'll have to use/find detailed descriptions of movies in order to be able to create some kind of database implement a search function. We also have to use a number of criteria to make similarity quantifiable, and we'll be making our own ranking system for that. Coming up with the ranking system can be challenging, and making our search fast can also be tricky. Some of the features that could help us get a better search might also involve some sort of indexing beforehand or using cloud credit, so defining this problem well and managing to keep things optimal for testing and for the demo would be important.

## Instructions for running the project ##

1. Navigate to project directory.
2. Run "mvn package" in the terminal. This runs unit tests, and builds the package.
3. Type "./run" to run the package.
4. To exit the program at any point, use the Ctrl+C exit.

## Instructions for interacting with the website ##

After building the program using steps 1 and 2 from the command line instructions, type
"./run --gui" in terminal.

Following this, navigate to http://localhost:4567/filmdb to access the webpage.

There will be a search bar in which the user can enter a possible movie name and receive
suggestions from the user.

Our advanced search has the following features:
1. Search by region
2. Search by year
3. Search by genre
4. Search by director
5. Search by actor or other crew member
6. Search by rating/popularity

In addition, on the top-right corner of the page is an icon which allows the user to login
or to create an account. Upon logging in, the user is able to visit their dashboard to get
customized search results based on movies the user may have saved. To save a movie, when 
a user comes across a movie's profile page, the user may click a "+" button to add it to
the list of movies the user enjoys. To remove a movie, the user may hover over the movie
profile and click a "-" button to remove it.

## Testing ##

We have tested our website by organizing two rounds of testing where different users
use our website simultaneously. In this process, we have tested many aspects of functionality
including the following:
1. Sockets and multiple sessions working correctly
2. Multiple profiles working correctly
3. Reasonable results produced by the ranker
4. Any website crashes that occur with different user behavior

We have also used the above to get feedback on whether our interface is intuitive,
whether the operations it offers are reasonably fast, whether different features meet
user expectations, and to assess any changes we could make to improve this.

### Unit tests ###
Unit tests are contained for relevant packages in the src/test/java package folder in 
the project directory. These are run when the project is built by running "mvn package".

Some of the areas covered by our unit tests include:
1. Trie commands, and related functionality in the suggest package.
2. SQL queries in FilmQueryTest.
3. Tests for the movie class in MovieTest.
4. Tests for the command classes in respective classes.

### System tests ###
These are provided in a system tests folder, and test many of the GUI-related functions
using commandline commands that test the back-end.
Our system tests rigorously test our command manager and all the command instances that run on it.

## Optimizations ##

Optimizations are made primarily in our database and in our ranker.
The database has been cleaned and information has been aggregated into a few tables, with little missing data, so that whole nodes
can be obtained from single queries. The SQL queries themselves use less expensive operations.

## Package structure: ##

Packages:

#### Main ####
* CommandManager.java (receives commands and passes them to appropriate methods)
* Main.java
#### Repl ####
* Repl class 
#### GraphData ####
* Vertex (abstract/interface)
* Edge (abstract/interface)
* Movie (subclass)
* MEdge (subclass)
#### Movies package ####
* Movie Database
* Select command (handles the case when a user selects a movie.)
#### Suggest ####
* Suggest command
* Word processing (file used by the trie for autocorrect)
#### Trie ####
* Trie 
#### Ranker ####
* Ranker
#### Query #### (contains database Logic: SQL querying + caching)
* FilmQuery (singleton that has SQL queries and an instance of the connection to database)
* SearchCommand
#### Socket ####
* File for setting up socket
#### Handler ####
* Handler: Contains handlers for display
* UserHandler: Contains handlers for user login and registration
#### resources package, JS files ####
* filmcommand.js for display and interaction with the landing page
* JS files for AJAX calls
* websockets.js for handling sockets
#### resources package, CSS files ####
* mainstyle.css
### test package (separate)

## Overview of back-end functionality / call structure ##

When the program is run, the repl starts until the program is exited.
At this time, a connection to the database is also established, and the Trie loads in suggested names.
When a user logs in, the Trie restricts suggestions based on the user's region(s).

The underlying node class keeps track of: 
1. A film's id, used to uniquely identify the film
2. A film's name
3. The film's director
4. The film's region(s)
5. The film's year of release
6. The film's cast member(s)

## Overview of front-end functionality / call structure ##

Handlers:
1. When the user visits the localhost:4567/filmdb page, the FrontHandler loads the display.
2. The Suggest handler receives post requests so that it can send suggestions to display.
3. If the user clicks in the top right corner to login or register, depending on whether . 
4. Once the user clicks on one of the suggestions displayed by the suggest handler, or presses
   the enter button, the search handler is triggered which navigates to a results page.
   This results page contains a display of suggested movies based on what the user has entered.
When a profile is clicked, we hit a similarity handler that returns a list of movies to be displayed to the front end.

## Overview of database ##

Our database has 2 tables:
1. Titles, which helps create movie nodes. This table has information about a film's name, id, year of release,
   genres, runtime, list of languages/regions, ratings, number of votes, streaming services, director, and movie cast.
2. Crew, associates person_id with film titles, and describes the role that the person had in the film.

## Overview of ranking system ##

The ranking system takes into account different genres associated with movies, and accounts for
similarities between genres, and dissimilarities between them as well. It prioritizes perfect
matches in genre over similar genres, and prioritizes similarity over dissimilarity.

## Overview of security ##

We avoid SQL injections by always using prepared statements.

## Overview of client calls to server ##
1. Calls for initializing the page.
2. Calls for getting suggestions for display.
3. Calls for getting search results for display.
4. Calls for getting similar movies to display when a movie is clicked.

## Overview of back end ##
1. The relevant lists are loaded in when the program is run so that they can be displayed on the GUI.
2. A handler calls the command class when the user types something in so that suggestions can be generated.
3. A handler calls relevant methods when parameters are entered and enter is pressed so that results can be fetched.
These results are fetched by assembling a minimal SQL query that gets all relevant results.
The ranker then sorts these results using a comparator to display the most relevant results first.
This is done using a comparator, a priority queue, and with the help of a rawRank() method written inside the movie class.
This sorted list is sent back by post request for display.
4. A handler calls relevant methods in the SelectCommand class to get movies that are similar to a given movie.
These results are then sent to the front end for display.

## Known bugs: ## 
None

## Distribution of work: ##

### Jasrine Dham: ###
* Creating and cleaning a database from 6 IMDB tsv files.
* Moving columns to different tables for faster queries and for optimizing space. In the process, 
culling redundant data and titles, and dropping unnecessary tables.
* Removing adult content and tv data. 
* Reorganizing data for faster querying.
* Modifying region names from 2- and 3-letter codes to readable names for easier display.<br/>Concatenating
regions together into a single column for using less space and improving search.
* Unit testing for most of the code.

### Shuchi Agrawal: ###
* Setting up the Command Manager and REPL. Setting up command classes that can run on the command manager.
* Connecting to the database and relevant queries so that commands can work on the REPL and on the GUI.
* Designing and setting up the loading page. Setting up CSS, JS, and Spark handlers/routes.
* Readme and documentation, clean up.
* Part of the unit testing and system testing.
* Web scraping for helping set up the database.

### Thomas Vandermillen: ###
* Creating a ranking system based on genres, directors, crew, and other factors
to determine a list of movies with greater similarity to a given movie.
This works with the search bar to return relevant results.
* Finding movies that are similar to a given movie.
* Setting up a comparator that orders results to display relevant results earlier.

- - - -


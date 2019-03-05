# term-project
Your project outline document is important for matching with a mentor TA, as well as developing an idea that can become a successful term project. You will write an outline document that lists each member of your team along with their strengths and weaknesses. Furthermore, you will include a list of up to 3 project ideas, and detail their requirments.

The requirments section for each idea should describe what problems that ideas is attempting to solve and how your project will solve them. It should describe the critical features you will need to develop, why those features are being included, and what you expect will be most challenging about each feature.


Group members: 
Thomas Vandermillen
strengths: Java, graphics
weaknesses: javascript, html, spark

Shuchi Agrawal:
strengths: graphics, webGL
weaknesses: project management


Jasrine Dham
strengths:
weaknesses:


Kate Lummis
strengths: Java, html
weaknesses: spark/javascript/ajax


Project ideas:
1. Movie suggester
The problem that we want to solve with this project is an that when people sit down to watch a movie, the suggestions based of a particular film seem limited and sometimes unrelated. Additionally, you can't get suggestions from multiple streaming services at once: if you're on Netflix you get Netflix's suggestions, if you're on Hulu, you get Hulu's suggestions, etc. There is no way to get suggestions regardless of streaming service, so we wanted to create a website that would do that.
We assume that this would involve some web-scraping or using these services' APIs, but the specific criteria used for determining the search make this fairly open-ended, especially as we can prioritize criteria differently to get a better but decently fast search. Some of the criteria we could consider are (1) label-based suggestions (2) style similarity between frames (using a neural net to compare two frames' style score) so that films with similar aesthetic get put closer together (3) datasets/searches on the web (4) similarity between screenplays found on the web, et cetera.
    a. Critical features: Ability to get more movie suggestions based on a more specific query (ex: instead of simply horror movies, you could look up slasher horror or psychological horror), or based off a number of criteria instead of just one. Also a database of films, their genre/subgenera, maybe part of the script, etc.
    b. These features are being included because they are something that doesn't really exist for movie viewers. 
    c. Challenges: We'll have to use/find detailed descriptions of movies in order to be able to create some kind of database implement a search function. We also have to use a number of criteria to make similarity quantifiable, and we'll be making our own ranking system for that. Coming up with the ranking system can be challenging, and making our search fast can also be tricky. Some of the features that could help us get a better search might also involve some sort of indexing beforehand or using cloud credit, so defining this problem well and managing to keep things optimal for testing and for the demo would be important.

2. Game Engine / Implementing a game on webGL
Problem to solve and how to solve it: We would attempt to make either a game engine that can support a graphically involved game, or implement a graphically involved version of a game (such as the Pokemon card game, or standard board games) so that it is more engaging to play them, and so that they can be played online (without the board, or even long distance), while still being fun. 
    a. Critical features: webGL API, nitty gritties of handling a game engine.
    b. These features are being included because some of us are interested in graphics and want to make a decent UI and have experience in webGL. We would also have room to use AFrame (or to extend our code to account for Stereopsis, and to attach it to a controller) which would be a cool way to end up with a product on AR/VR platforms, or to include animations.
    c. Challenges: a game engine that can host any game is complex and challenging. Successfully implementing one level of a game is also challenging and involved, and would require some thought and organization for the game play. There is a lot of room and necessity for thinking about design, but a strong need for really good back end. Any improvement on existing game engines would be a plus.

3. 

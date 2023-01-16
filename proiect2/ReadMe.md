This is the second and final stage of the POO TV project.
Made by Echimenco Rares-Adrian

How does it work?
I'll explain it based on the commands it can receive,
be aware that any errors will be handled on a case by case scenario:

0) The Setup
Before any commands can be received, there are some things that must happen:
    0.1)reading the data from the .json input files, it is placed in the Input
class which is further made up of Users, Movies, and Actions.
    0.2)initializing the platform with the Users as the RegisteredUsers and the
Movies as MovieRuns and the state as not logged, saved in code as 
"Homepage NoLog".
    0.3)making compatible rating and subscription lists with the 
RegisteredUsers.
    0.4)finishing touches and the output array is initialized.

1) Running the instructions; they are taken one by one from Input->Actions+
until none are left, each being interpreted as follows.

2)Change page actions
These change the page we are in, and the future actions we may take, and they
are:
    2.1)Login, if we are not logged, we move to the login page, where we wait
for valid credentials, if failed, going back to the non logged homepage
(potentially kicking out a logged user).
    2.2)Register, the same as with Login, but instead it waits for the data
of a new user.
    2.3)Movies, after logging and/or registering(the above are the pages, not 
the logs/registers themselves, they will be explained in chapter 3.1/3.2) we 
arrive at the logged homepage, which has among other thigs, the Movies page, 
which as a result displays all the movies available for the currentUser. From 
here a user might, search(3.3), filter(3.4), or ask details for a certain
movie. If accessed from any non logged pages it prints a simple error and does
nothing.
    2.4)Details, as hinted above, it looks for a specific movie, based on its
name. It returns to the logged homepage if it gives an error or the movie asked
does not exist(or is not available for the logged user). From here we can
purchase, watch, like, rate and subscribe to any genre of the movie.
(3.7 up to 3.11)
    2.5)Upgrades, as long as the user is logged in, this page is accessible.
It offers the option to buy tokes and upgrade the account from standard to
premium.(3.5 and 3.6 respectively).
    2.6)Logout, self-explanatory, anywhere a user is he/she/it is disconnected
and returned to the not logged homepage.
    2.7)Back, if logged in, it backtracks to the last valid page change along
with all the requirements effects, and possible errors the page would have.
If used by a not logged in "user", it prints an error message.

3)On page actions
On every page there are actions On that page that actually make the "world" go
round:
    3.1)Login, yes the same as with the page, now we take the name and password
from the input and check if they match any in the RegisteredUsers array.
If successful, the user with matched credentials is logged in, otherwise it
goes back to the not logged homepage.
    3.2)Register, in similar fashion, we take the whole user data and see if
the name is unique, in an affirmative case, we add the user to the list of 
RegisteredUsers and log in the new user; otherwise, or in case of error we are
brought back to the not logged homepage.
    3.3)Search, it looks through the ListedMovies and keeps only the ones that
start with the same string as the one recieved by the search action 
(StartsWith). It can only be accessed from the Movies page, so ony other page
trying to search will fail the search and print the error message.
Note:Finding no movie is not a cause to error.
    3.4)Filter, actually sort would describe it better, can sort the listed
movies by duration, and rating, both ascending and descending, along with 
filtering for actors and genres. As with Search this "function" can only be
called from the movies page, it prints the basic error if called while in any
other page, and sorting an empty list does not give an error, but sorting with
no filters does.
    3.5)Buy tokens, only accessible from the Upgrades page, and if called 
successfully, attempts to convert "balance" from the logged user into useable
tokens. Errors include not having enough "balance" or being called from
anywhere else, and will result in the more serious penalty of kicking the user
out, along with the error message.
    3.6)Buy premium account, only accessible also from Upgrades, and consumes
10 tokens to convert the account type from standard to premium. As with buy
tokens it erros the same, logging out the user + error message.
    3.7)Purchase, when in the Details page, the movie shown can be purchased
and that means the following:
        3.7.1) check if there is a movie listed
        3.7.2) check if the movie is not purchased by the user
        3.7.3) check if the user can buy the movie
if all conditions are met, the one free movie is deducted from the premium 
users and if the user is standard or premium but already used up the free
movies we deduct 2 tokens from the account, and add the movie to the
pruchased movies list of the user. Errors at any point will see us print the
error message.
    3.8)Watch, as above(and any below actions), when in the Details page, a
movie may be watched if:
        3.8.1) there is a movie listed
        3.8.2) the user has purchased the listed movie
if both statements are true the movie is watched, and added to the users
watched movies array. Errors from an empty list of movies will return us
to the logged homepage and all errors will print the error message.
    3.9)Like, from th Details page, a watched movie can be liked if listed.
All errors will see us returned to the logged homepage with the error message.
Likes are permanent here, so liking twice will not unlike, but instead will
bring error upon the user.
    3.10)Rate, from Details(as always...), if the movie is listed and watched
a rating may be applied from 1 to 5(aka stars). Rating an already rated movie
will override the previous rating. This will immediately update the rating of
the movi. Any errors will only bring about the dreaded error message.
    3.11)Subscribe, can be used from the Details page, on any of the genres of
the listed movie, adding the user to a special Subscription service, where any
newly added movies with at least one genre in common with any the users subed
ones will make the user receive a notification(even if the user is not logged).
Errors from already being subbed to a genre will put us in the logged
homepage and errors from no movie listed will relist the movies along with the
page changing to Movies. Both cases recieve the all loved message.

4)Database
New movies are always made and controversial movies are always in need of
*removal*
    Changes to the database are added to a list, and when a user reloads the
Movies page, they are made official. if the movies are already there, an error
is thrown.

5)Recommendation
The premium users had a bit too less of the spicy stuff, so now we are
recommending a movie if a premium user makes the last action. How is the
movie determined: 
    5.1) what is the most liked genre of the user
    5.2) what movies are not already watched by the user
    5.3) from that genre which movie has the most likes
    5.4) if ties happen, at 5.1 alphabetical order of the genres themselves
takes priority(Action vs Thriller = Thriller wins) and at 5.3 the tie is broken
by what movie is shown the first in the list. 
    5.5) if no winner movie is found then the second most liked genre is used
for 5.1 and if no genres are left then the recommendation will be a sad
"No recommendation".

6)Output
At the end of the Actions a (usually big) array of generated messages from
all the errors, the successful logins and registers, listed, searched,
filtered, detailed, bought, watched, liked and rated movies will be placed
here to be shipped to results.out in .json format.


!IMPORTANT!
OOP Stuff: I could implement over the first stage of my project only the
Observer pattern in the notifications when the database changes(Update), over 
the Singleton used in ActionHandler.

There definetly is stuff that I overlooked because it wasn't useful for the
tests.

Javadoc is missing entirely, do not try to look it up.

Coding style is lacking (594 errors).

To whoever told me after the first stage to remake this project entirely,
you were right in the sense of "bugginess" and ease of extension, but due to
time constraints, I had to reuse it, so here we are.

For any of the above I take full responsibility, and I am sorry.

Otherwise, thanks for reading!
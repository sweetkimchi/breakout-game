# breakout

This project implements the game of Breakout.

Name: Ji Yun Hyo

### Timeline

Start Date: January 22nd, 2021 @ 08:34PM

Finish Date: February 1st, 2021

Hours Spent: ~30 hours

### Resources Used

All image files are under 'data' folder. All txt files containing level information on project main page.

1. [JavaFX Game: Space Invaders (for Beginners)](https://www.youtube.com/watch?v=FVo1fm52hz0&list=PL4h6ypqTi3RTIoPa_Qz3haEo3OXJQqOwc&index=5)
2. [JavaFX Animation](https://zetcode.com/gui/javafx/animation/#:~:text=AnimationTimer%20is%20the%20most%20simple,every%20frame%20of%20the%20animation.&text=Timeline%20is%20the%20most%20complex%20tool%20for%20doing%20high%2Dlevel%20animations.)
3. [Java Inner Classes](https://www.tutorialspoint.com/java/java_innerclasses.htm)
4. [Breakout Tile Set](https://opengameart.org/content/breakout-brick-breaker-tile-set-free)
5. [Ball Image](https://www.google.com/search?q=brick+breaker+ball+png&tbm=isch&hl=en&sa=X&ved=2ahUKEwjotreSq73uAhVL6VMKHWA4C-EQrNwCKAJ6BQgBELAB&biw=2545&bih=1272#imgrc=HIMr7i97XrxHqM)
6. [Breakout Tile Set 2](https://opengameart.org/content/ui-pack)
7. [Smooth Movement](https://www.youtube.com/watch?v=L5GJ-i_6uGQ)
8. [HTML formal](https://www.w3schools.com/html/tryit.asp?filename=tryhtml_styles_background-color2)
9. [JFrame](https://www.tutorialspoint.com/how-to-display-a-jframe-to-the-center-of-a-screen-in-java#:~:text=By%20default%2C%20a%20JFrame%20can,()%20method%20of%20Window%20class.)
10. [JFrame Documentation](https://docs.oracle.com/javase/10/docs/api/javax/swing/JFrame.html)
11. [Ball Image](https://pngtree.com/freepng/red-energy-ball-pokiehl_3996999.html)
12. [Java File Read](https://www.w3schools.com/java/java_files_read.asp)
13. [Splash Screen](https://www.youtube.com/watch?v=x-vlOrUBxjc)

### Running the Program

Main class: BreakoutApp.java

Data files needed: All the png files under folder 'data.' 

Key/Mouse inputs:

1. Space - shoot missiles
2. LEFT - move paddle to left
3. RIGHT - move paddle to right
4. P - pause the game/resume the game
5. R - restarts the game from the last level you left off

Cheat keys:

1. L - add one life
2. M - 10 more missiles
3. F - make ball bigger and go through blocks
4. D - decrease ball size
5. G - level up paddle/no level up limit
6. V - decrease size of paddle
7. 0~9 - jump to level (or the maximum level)

### Notes/Assumptions

Assumptions or Simplifications:
I decided not to implement the DotPaddle (but left it for possible future implementations on my own
just for fun). I also decided not to implement the invincibility cheat code because it felt very redudant
with "get lives" cheat code. I also decided not to implement the "indestructible layer of blocks" Power-up
because when I did implement it, the game was super boring. I wanted the game to be fun and engaging.
I also wanted to be of decent quality that I would want to play it from time to time. 

Known Bugs: 1. The ball might get stuck at the edges (left and right). If this happens try to take the paddle
out of the screen and hit the ball with the very edge of the paddle. That knocks the ball back in the game for now.

Lags a little when too many sprites are created all at once. I spent a lot of time fixing these bugs
because bugs are the things that make the gaming experience terrible. Some bugs I encountered were 1) choppy movement of paddles,
2) the blocks getting stuck on the edge while trying to change directions, 3) missiles fired by enemy not doing damage, 
and so many more. I fixed all bugs I encountered.

Extra credit: Not sure what this is.

### Game Desgin
I spent a lot of time trying to get rid of things that made the game not enjoyable to play. I researched
a specific way of implementing the paddle velocity that would result in much smoother paddle movement. I 
also tried different implementations to make sure that the paddle bounces the ball off at different angles
depending on where on the paddle the ball hits. I tried my best to make the game look pleaseing to look at.
UI/UX is key for gaming experience. As an avid gamer, I hold high standards
when it comes to gaming experience. So even though I knew I wouldn't be graded on how "smooth" or enjoyable the 
game is, I couldn't resist but to try to make the experience as good as possible. I also made the blocks at Level 2
move in random directions according to a pre-determined probability. So if you run Level 2 multiple times,
you'll get a different block movement throughout the game. 

Another thing is that I purposefully decided that damage
done by the ball on the boss will not drop power ups. In this level, only the missiles drop power ups. The reason
is that I wanted to make the left and right sides of the boss its vulnerable spot. So once the ball enters
from the sides, the ball does "super attack" and damages the boss a lot. This lead to excessive dropping of
power ups which caused immense lag that rendered the game unplayable. So I decided to leave that out despite
the fact that the could go against my original plan. Moreover, I purposefully decided not to display how much 
HP (health points) the boss has because I thought that not knowing when the boss is going to go down makes
the game more fun and interesting. If we see the exact number, I just felt like the game was a little
boring. 

I created a BreakoutAPP class that handles everything that is displayed on the screen. BreakoutApp
keeps track of all revelant "game data" such as lists that contain all sprites that are created in the game. 
It also handles most of the graphics (e.g. splash screen, game screen). For managing the sprites, I considered many options when it came to how I would structure all different parts of the project. I considered
just using one class to reprsent all sprites. However, I decided in the end that I would create one class
for each sprite I would add. And all of these classes would extend the Sprite class which handles all the 
operations such as different sprites colliding, initializing the objects that inherit this class, and removing
sprites from the map once they are dead or consumed. I found that this improved readability and prevented
me from having to write duplicate code. Although I tried my best to simplify duplicate codes, for version 3.0,
I do want to try to improve the cleanniess of the code. Right now, there are too many variables declared as
private at the top of BreakoutApp and Srite classes. I also want to look for ways to improve the overall
experience of the game. I realized that the game lags a little bit when too many sprites are produced 
on the screen at the same time. 

On a separte note, I tried my best to follow the SRP. I tried my best to ensure that each method is commpleting
one specific task and when I thought that the method was doing more than one thing AND that future additions of 
features would complicate the method, I refactored the method. I hope the method descriptions are descriptive enough.

Lastly, it was necessary to check collision (inside Sprite class) of blocks and other things separately in order to adhere to different
conditions each sprite was subject to. I might need to do some more research to better implement this but
I could not find a better solution. 

### Extra Features
Like I stated earlier, the primary thought when designing and building this app was that the game
needs to be fun. When I implemented some stuff and found that it was really no fun (I know because
I'm an avid game player), I decided to get rid of the features: invincibility (game became too boring because I didn't
even have to move my paddle to win), paddle leveling up (power ups usually
leveled up the paddle so fast that "experience" was needless and not fun), displaying number of hits
required to destroy the block, dot paddle (you couldn't even "see" that the dot paddle was doing dot damage). I ended up adding a lot of extra features such as the 'pause' key, super-ball that goes through the blocks,
keys to increase/decrease paddle speed, key to add more missiles, keys to increase/decrease size of the paddles, 
switching brick images when it has low health, switching paddle image when it becomes faster.

One last thing I thought interesting was that I actually made selecting images for the blocks possible through
the parameter I feed in the program from my level#.txt files. The numbering system for all my image files start with
numbers. I saved the postfix so that when I get fed in a image number as one of the parameters, I can just add the
postfix and load the image on the brick. So it is so much easier to load/change/renew images. 

### Impression
To be honest, there are so many more things I want to do to improve this current app. I now know what design
works better and what implementation results in "smoother" experience. I made a lot mistakes when implementing
this game such as duplicating code, too many variables, and etc. However, following Professor Duvall's guidance
such as make the code as readable as possible really helped me see ways in which I can make the code more
readable to the future readers. I wish I had spent more time making the code readable, so next time I will
certainly do that. And because I know what things work now, I am sure I can come up with a much better design for
future projects. There were also times when I had to choose the "dirty" code design because for unknown reasons,
some clean code made the gaming experience a little bad (e.g. slowing down the game, laggy, etc).

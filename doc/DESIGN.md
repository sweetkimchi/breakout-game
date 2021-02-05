# DESIGN Document for PROJECT_NAME

## NAME(s)

#### Examples

You need to put blank lines to write some text

in separate paragraphs.

Emphasis, aka italics, with *asterisks* or _underscores_.

Strong emphasis, aka bold, with **asterisks** or __underscores__.

Combined emphasis with **asterisks and _underscores_**.

You can also make lists:

* Bullets are made with asterisks

1. You can order things with numbers.

You can put links to commits like
this: [My favorite commit](https://coursework.cs.duke.edu/compsci308_2019spring/example_bins/commit/ae099c4aa864e61bccb408b285e8efb607695aa2)

You can show images like this:
![This is cool, too bad you can't see it](crc-example.png "An alternate design")

You can put blocks of code in here like this:

```java
    /**
 * Returns sum of all values in given list.
 */
public int getTotal(Collection<Integer> data){
    int total=0;
    for(int d:data){
    total+=d;
    }
    return total;
    }
```

## Role(s)

Develop a variation of the Breakout game.

## Design Goals

The design goal here was to make it easy to add new levels and to add new types of sprite.

## High-Level Design

Breakout App extends Application class, handles the game engine, displays sprites including GUI,
handles keyboard input, and updates all sprites. Sprite class is the super-class to all types of
sprites: Ball, Block, Boss, DotPaddle, Missile, MissilePaddle, Paddle, and PowerUp. Sprite class
keeps information regarding each sprite object, creates new sprites to display on GUI, and deletes
certain sprites that meet the condition. Levels class reads information on levels from files and
launches a new level by calling setupGame from BreakoutApp class.

## Assumptions or Simplifications
Assumption here is that up to 10 new sprites will be added to the game. There will not be too many level templates that are created. 
The assumption here was that I was building this as if for a startup. I do not think there were any
real simplifications. Unimplemented features were not implemented because I found better ways to enhance
the user experience.

## Changes from the Plan

The design roughly stayed the same and did not change much. However, I did not implement DotPaddle
class because I thought the game was not that fun with the dot paddle. I also did not implement the layer 
of blocks that form at the bottom because that really killed the fun of the game as I did not even have to move
in order to win the game. Gaining experience as the paddle kills the blocks was not implemented. The instant
level-up power ups were enough to level up the paddle. The number of hits required to destroy the block
was not implemented because I thought making the block display "broken block" image would give better
use experience.

## How to Add New Features

1. Keyboard inputs (e.g. cheat code, game control, etc): Modify handleKeyInput in BreakoutApp to add
new logic for a new keycode.
2. Adding a new type of sprite: Create a class that extends Sprite. In the
update method of Sprite, put down the logic for the new sprite (for example, collision behavior), make an
ArrayList<the new sprite> to contain all pointers to the newly created objects in Breakout App, and
add the new sprite to update method in BreakoutApp.
3. Adding a new type of power up: Create a PowerUp object and give it a new "String type" and a new image. In Sprite class,
update the logic for the new type of powerup in checkPowerUpCollision.
4. New Level: Use the already-existing templates to create stationary block, moving block, and/or boss stage. Change the
image by including the prefix (three digit number) of the image file. Choose from images in 'data' folder. Also, the coder can create a completely
   new template for his/her own use. Laucnh level.
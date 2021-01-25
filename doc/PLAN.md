# Game Plan

### NAME: Ji Yun Hyo

## Interesting Breakout Variants

1. Brick Breaker Escape - I like how the ball is really huge. It is just more interesting to bounce
   around larger balls. For some reason, small balls make it seem like I'm bouncing around metal
   balls whereas bigger balls make it feel like I'm bouncing around exercise balls that are more
   elastic. Gives me more pleasure to see it bounce around and destroy the bricks.
2. Bricks n balls - I like how the HP of each of the bricks is represented by a number. It is
   precise and unambiguous. It is also pleasing to see all the numbers drop each time the ball hits
   the respective bricks

## Paddle Ideas

Players can select between one of two paddles at the start of the game

1. The paddle can shoot missiles, dealing damage to the blocks.
2. The ball deflected off of the paddle does DoT(damage over time) damage to blocks. In other words,
   the damaged blocks will get additional damage over time.
3. Paddles gain "experience" as they destroy more blocks, eventually leveling up to become stronger.
   Stronger is defined as the paddle becoming longer (covers more area) and their weapon of choice
   (e.g. DoT damage or missiles) doing more damage to the blocks. Highest possible level is capped
   unless removed by the cheat key. Levels up are permanent even if a player loses a life or
   advances to the next round.

## Block Ideas

1. Block(s) that shoot missiles which can damage the paddle including a huge, powerful "Boss Block"
   for the final stage that takes multiple shots to be taken down. I'm thinking of the last stage
   being the "Boss Stage" where you'd have to destroy the boss.

2. Number of hits required to destroy the block is indicated by the number written on the block.
   When the number reaches 0, the block is destroyed. Each level will have higher number of total
   hits required to destroy all blocks.

3. Blocks that float around the map, making it harder for the player to hit the blocks.

4. Blocks will drop power-ups when damaged (not necesarily destroyed) by chance.

## Power-up Ideas

1. Instant gain of a large amount of experience (used to level up the paddles).

2. Bigger sized ball (x2 or x3) which makes it easier for the player to hit it with the paddle and
   more likely for the ball to hit the blocks.

3. An indestructible layer of blocks forms at the bottom of the map for a certain period of time.
   During this time, the ball cannot fall through the indestructible layer and cause the player to
   lose a life.

## Cheat Key Ideas

1. Invincibility - the paddle does not receive damage from missiles fired from blocks
2. Clearing the current level
3. Instantly leveling up the paddle. Repeated use also allowed
4. Infinite lives to play the game
5. Removes the highest level restriction: the paddle can level up infinitely beyond the original
   level cap

## Level Descriptions

1. Initial Stage - An easy, tutorial-like stage to get the users accustomed to the game. Generous
   power-up items. The blocks are stationary and are organized in a standard way. B represents the
   blocks and P represents the paddle.<br />

   B B B B B B <br />   B B B B B B <br /> B B B B B B

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;P

2. Middle Stage - Set of moving blocks that the player has to destroy. The blocks will start out in
   positions shown below and will be moving in various directions at various speeds.

   B B<br />   B B <br />   B B <br />   B B <br />   B B <br />
   <br />
   &nbsp;P

3. Final Stage - One HUGE "Boss Block" (to be named later) will face the paddle. Imagine a HUGE
   block that fills up the screen. Every once in a while, the Boss Block will fire missiles in all
   directions. Occasionally, based on some pre-determined probability, the Boss Block will drop
   power-up items when damaged.

   <font size="10">  B <br />
   <br />
   <font size="2"> P <br />

## Class Ideas

1. Sprite - update()
2. Ball - getCenterX(), getCenterY()
3. Paddle - getExperience()
4. Boss - fire()
5. Block - implode()
6. Missile - checkCollide()
7. BreakoutApp - start()
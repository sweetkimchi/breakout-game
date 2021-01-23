# Game Plan
### NAME: Ji Yun Hyo

## Interesting Breakout Variants
1. Brick Breaker Escape - I like how the ball is really huge. It is just more interesting to bounce
   around larger balls. For some reason, small balls make it seem like I'm bouncing around metal balls
   whereas bigger balls make it feel like I'm bouncing around exercise balls that are more elastic. 
   Gives me more pleasure to see it bounce around and destroy the bricks.
2. Bricks n balls - I like how the HP of each of the bricks is represented by a number. It is precise
and unambiguous. It is also pleasing to see all the numbers drop each time the ball hits the respective
   bricks

## Paddle Ideas
1. A paddle that can activate a shield against missiles fired by the bricks by pressing a button. 
   The shield charges proportionally to the distance traveled by the paddle. However, when the shield 
   is being used, the paddle does not charge the shield. If shield charge depletes while pressing the
   key to activate the shield, it starts charging again as the paddle moves. Shield absorbs the missiles
   and removes them from the game.
2. Paddle gains "experience" as it destroys more blocks, eventually leveling up to become stronger. Stronger
   is defined as longer (covers more area), balls deflected off the paddle does more damage to the bricks, and
   it has more "lives" so it can take more damage before "dying."

## Block Ideas
1. Blocks that shoot missiles which can damage the paddle including a huge, powerful "Boss Block" for the final stage 
   that takes multiple shots to be taken down. I'm thinking of the last stage being a the "Boss Stage" where you'd have to 
   destroy the boss.

2. Number of hits required to destroy the block is indicated by the number written on the block. When
    the number reaches 0, the block is destroyed. Each level will have higher number of total hits
   required to destroy all blocks progressively.
   
3. Blocks that float around the map, making it harder for the player to aim the blocks.

## Power-up Ideas
Level-ups
1. Leveling up through experience. Each time a paddle successfully destroys a block, it gains experience
proportional to the number of hits it took the destroy the block. Blocks with more lives give more experience.
   When the experience reaches a certain point, the paddle levels up. Level is reset to the initial level
   at the start of each stage. There will be a highest level the paddle can reach.


Power-up charges can be charged up to two charges. Activating the power-ups at different charge levels will
   have different abilities. Activating the power-up abilities discharges the power-up charge accordingly
   
2. Charge 1: increases damage by 2x the current damage of the paddle for a pre-determined amount of time

3. Charge 2: destroys all blocks on the ball's path (without bouncing off) for a pre-determined amount of time

## Cheat Key Ideas
1. Invincibility - the paddle does not receive damage from missiles fired from blocks
2. Clearing the current level
3. Instantly leveling up the paddle. Repeated use also allowed
4. Infinite lives to play the game
5. Remove the highest level restriction

## Level Descriptions
1. Initial Stage - An easy, tutorial-like stage to get the users accustomed to the game. Generous power-up
   items. 
2. Middle Stage - 
3. Final Stage - One "Boss Block" (to be named later) will face the paddle. The Boss Block will fire
missiles in all directions. Occasionally, based on some pre-determined probability,
   the Boss Block will drop power-up items when damaged.

## Class Ideas
1. Entity - contains attributes common to all entities of the game
2. Ball
3. Paddle
4. Boss
5. Block
# Maze

### This is a maze generating and solving Java application.

## Description

The user is prompted to either select a file containing a stored maze or generate one dynamically. Once the maze is created, there exist a number of possible algorithms to solve it. At this moment the below are implemented:
- Prune: This scans the maze repeatedly, marking dead-ends as it encounters them. It continues to do so until no more dead ends can be found. What remains is tiles that can be part of a solution.
- BFS: This scans the maze, beginning from the start point. Once the end point is reached, it marks the path that was followed to reach it. If a solution is possible, the shortest path will be found.

## Future enhancements
My goal is to add graphics and display the steps of the generation / solution as occur. This could be running continuously, creating and solving random mazes. It would make a nice screensaver! :)

### Next changes:
The below are minor changes that should happen soon:
- Convert the maze loader and generators to different subclasses of Maze, which will implement an Interface

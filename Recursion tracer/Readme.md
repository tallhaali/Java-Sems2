This repository contains Java code examples to help you deeply understand recursion and how recursive calls are traced, visualized, and explained. The main focus is on an immersive maze-solving problem using Depth-First Search (DFS), but you can also adapt the tracing techniques to other recursive problems.

## Features
- **Maze Solver with DFS:**
  - Randomly generates a 4x4 maze with open cells and walls.
  - Uses recursion (DFS) to find a path from the top-left to the bottom-right corner.
  - Traces every recursive call, backtrack, and decision with detailed English explanations.
  - Shows how variables `x` and `y` change as the recursion explores the maze.
- **Recursion Tracing:**
  - The code prints each step, including entering and exiting recursive calls, base cases, and backtracking.
  - Indentation visually represents the depth of recursion.

## How to Use
1. **Clone the repository or copy the code to your Java project.**
2. **Compile and run `Tracer.java`:**
   ```sh
   javac Tracer.java
   java Tracer
   ```
3. **Observe the output:**
   - The maze is printed (0 = open, 1 = wall).
   - Each recursive call is traced with explanations.
   - You can see how DFS explores, backtracks, and finds a path (if one exists).

## Example Output
```
Maze (0=open, 1=wall):
0 0 1 0 
1 0 1 0 
1 0 0 0 
1 1 1 0 

Tracing recursive maze solving from (0, 0):

At position (0, 0)
Exploring neighbors of (0, 0)...
Trying to move right...
At position (0, 1)
... (output continues)
```

## What is DFS?
DFS (Depth-First Search) is a fundamental algorithm for exploring trees, graphs, and mazes. It goes as deep as possible along each branch before backtracking, making it ideal for recursive solutions.

## Other Recursion Examples
You can adapt the tracing style in this code to other recursive problems, such as:
- Calculating factorials
- Fibonacci numbers
- Tower of Hanoi
- Permutations and combinations

## Learning Goals
- Understand how recursion works step by step
- Visualize the call stack and backtracking
- See how DFS explores all possible paths in a maze

---
Feel free to use, modify, and share these examples to help others learn recursion and DFS!

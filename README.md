### About: 
```bash
This repository is about a project I did as a part of my science propaedeutic seminar called "Simulations in Java". The file "Daniel Benner - 
Seminararbeit - Genetische Algorithmen im zellulären Automaten.pdf" is my scientific paper about the simulation I coded and contains all the 
results I got investigating my own algorithm. 
It's written in German, but I will explain my program so it can nevertheless be understood.

During this project I implemented a genetic algorithm using simple reinforcement learning.
In a given, twodimensional grid the goal is to find a way from one place to another avoiding walls.
The first generation of agents will perform a randomly generated path after which the second generation adopts the path of last generations 
agent that got the closest and implement small changes to it. This process of constant evaluation of the best, adopting his path and 
performing small mutations results, given the right variables, in a path from start to finish.

The variables used to controll the algorithm are:
 - startPopCount: amount of agents (the higher the faster the algorithm but also the more processing power needed)
 - wayLength: length of the agents paths measured in tiles (has to be fitted to the distance between start and finish and the impact of obstacles)
 - mutWslkeit: probability of a step in an agents path to mutate (best to be pretty low - between .01 and .04)
 - kreuzungsStaerke: how much of the best agents path is adopted by the next generation (best to be pretty high - between .97 and 1)

The program itself is placed inside the folder "Seminararbeit gAizA - Final" and the five main classes are: Controller, Genetic, Individuum, 
Kaestchen and Wall. Inside the "Tests" folder the same classes can be found but including multiple versions of the Controller class to showcase 
some examples.
```

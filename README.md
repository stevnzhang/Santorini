# Santorini

## 📂 Table of Contents
- [Overview](#-overview)
- [Features](#-features)
  - [Basic Gameplay](#basic-gameplay)
  - [God Cards](#god-cards)
- [Testing](#-testing)
- [How to run & play Santorini](#-how-to-run--play-santorini)
  - [Run/Start Commands](#runstart-commands)
  - [New Game](#new-game)
  - [Initializing Workers](#initializing-workers)
  - [Moving](#moving)
  - [Building](#building)
- [Learning Goals](#-learning-goals)
- [Author's Note](#%EF%B8%8F-authors-note)

## 📖 Overview
Santorini is a popular strategy board game where you play as a Greek God or Goddess and try to win it all by leveraging unique powers.

Read more about the game's background and how it's played [here](https://roxley.com/products/santorini)!

## 🚀 Features

### Basic Gameplay

- [Place Workers](#initializing-workers)
- [Move](#moving)
- [Build](#building)

### God Cards

- [Pan](https://www.youtube.com/watch?v=W42qtDcyCfk)
  - You also win by jumping down two (2) levels.
- [Minotaur](https://www.youtube.com/watch?v=CAseaszZF0c)
  - Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.
- [Demeter](https://www.youtube.com/watch?v=GSqdq7gSEjA)
  - Your Worker may build one additional time, but not on the same space.

## 💻 Testing
Achieved 100% code coverage using unit integration and functional tests.

## 🎲 How to run & play Santorini

### Run/Start Commands

Navigate to the respective backend and frontend folders, then run these commands:

- Backend\
`npx kill-port 8080`\
`mvn clean install`\
`mvn exec:exec`


- Frontend:\
`npm install`\
`npm run compile`\
`npm run start`

### New Game

Just click on the New Game button to restart the game!

### Initializing workers

Whenever you start a new game, simply click on any two (2) valid board positions to place Player 1's workers. Then, click on two (2) additional valid board positions to place Player 2's workers. After the players have been initialized, you can begin to start moving and building with the workers.

Invalid placements will display an error message in the instructions at the top of the board.

### Moving

First, select a worker (has to be the current player's worker). The cell of the worker you selected should be highlighted a pale yellow. You can then click any valid board location to move the selected worker to that location.

Player 1 is "X"\
Player 2 is "O"

Invalid selections and moves will display an error message in the instructions at the top of the board.


### Building

Right after moving a worker, the worker that was just moved stays selected. We continue to use this selected worker to place a valid tower.

Each additional [ ] is the equivalent of a new tower. E.g [ [ X ] ] is a 2-high tower with Player 1 in it.

Towers with a dome will have "D" in the cell, and the cell will be highlighted blue, like in Santorini.

Invalid selections and placements will display an error message in the instructions at the top of board.


#### Disclaimer *
Undo and skip have not been implemented. While skip for Demeter has not been implemented in the frontend, it has been implemented and tested in the backend. I have fully implemented Minotaur and Pan in both backend and frontend.

Additionally, to change and play with different God Cards, you will have to change the gc1 (player1) and gc2 (player2) variables in `app.java` lines 28-29.

_I am planning to update this game in the near future to implement the undo and skip feature (using immutable board states) as well as implementing a frontend screen to handle assigning each player a god card. Stay tuned!_

## 📋 Learning Goals
- Demonstrate a comprehensive design and development process including object-oriented analysis, object-oriented design, and implementation.
- Demonstrate the use of design goals to influence design choices, assigning responsibilities carefully, using design patterns where appropriate, discussing trade-offs among alternative designs, and choosing an appropriate solution. The core logic of the game is testable and completely independent from the graphical user interface (GUI).
- Communicate design ideas clearly, including design documents that demonstrate fluency with the basic notation of UML class diagrams and interaction diagrams, the correct use of design vocabulary, and an appropriate level of formality in the specification of system behavior.

## ✍️ Author's Note

Santorini was a project that spanned 4 weeks.

The first two weeks was a design challenge - considering design alternatives and tradeoffs - and was predominantly focused on backend development. This was one of the projects where I spent a lot of time thinking, planning, and drawing things on the whiteboard before I started coding.

When I revisited Santorini for the remaining two weeks, I was tasked with building a playable frontend (sorry, not much time was spent on the frontend :') and extending the existing game with god cards, which was nightmarish. A large portion of my design had to be re-planned and re-factored to allow the game to coexist with the god cards. After the complete backend design overhaul, I implemented each god card and connected it with a frontend using Handlebars + React ([reference](https://github.com/CMU-17-214/s22-rec07-solution)).

Santorini was a fun project for learning more about backend development, it's not simply just writing code, but truly understanding what is needed for our application and how to design + implement it. There is a lot of planning, considering alternatives + tradeoffs, and communicating with other for their feedback. I also spent a great deal of time following good coding practices such as code documentation, testing, encapsulation, polymorphism, exception handling, design patterns which is reflected in the code that I have written. Feel free to take a look at the source code and let me know if there's anything you would like me to elaborate on!

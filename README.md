# How to play with this GUI

#### Start the game with:

Backend:\
`npx kill-port 8080`\
`mvn clean install`\
`mvn exec:exec`

Frontend:\
`npm install`\
`npm run compile`\
`npm run start`

## New Game

Just click on the New Game button to restart the game!

### Disclaimer*
Undo and skip have not been implemented. While skip for Demeter has not been implemented in the frontend, it has been implemented and tested in the backend. I have fully implemented Minotaur and Pan in both backend and frontend.

Additionally, to change and play with different God Cards, you will have to change the gc1 (player1) and gc2 (player2) variables in `app.java` lines 28-29.

## Initializing workers

Whenever you start a new game, simply click on any two (2) valid board positions to place Player 1's workers. Then, click on two (2) additional valid board positions to place Player 2's workers. After the players have been initialized, you can begin to start moving and building with the workers.

Invalid placements will display an error message in the instructions at the top of the board.

## Moving

First, select a worker (has to be the current player's worker). The cell of the worker you selected should be highlighted a pale yellow. You can then click any valid board location to move the selected worker to that location.

Player 1 is "X"\
Player 2 is "O"

Invalid selections and moves will display an error message in the instructions at the top of the board.


## Towering

Right after moving a worker, the worker that was just moved stays selected. We continue to use this selected worker to place a valid tower.

Each additional [ ] is the equivalent of a new tower. E.g [ [ X ] ] is a 2-high tower with Player 1 in it.

Towers with a dome will have "D" in the cell, and the cell will be highlighted blue, like in Santorini.

Invalid selections and placements will display an error message in the instructions at the top of board.

## References
Used Recitation 7's TicTacToe [Solution](https://github.com/CMU-17-214/s22-rec07-solution) as a reference for code.



# Homework 3: Santorini (Part 1)

In this assignment, you will design and implement the core logic of a boardgame called Santorini (without god cards) **in and only in Java**. The focus of this assignment is on considering design alternatives for code. In Homework 5, we will revisit the game and extend it with god cards, with a GUI, and with an AI opponent. 

This assignment is intended as a gentle introduction to modeling on a relatively simple problem, but it requires going through all steps of the design process.

This assignment has the following learning goals:

* Demonstrate a comprehensive design and development process including object-oriented analysis, object-oriented design, and implementation.
* Demonstrate the use of design goals to influence your design choices, assigning responsibilities carefully, using design patterns where appropriate, discussing trade-offs among alternative designs, and choosing an appropriate solution. The core logic of your solution must be testable and completely independent from your solution’s eventual graphical user interface (GUI).
* Communicate design ideas clearly, including design documents that demonstrate fluency with the basic notation of UML class diagrams and interaction diagrams, the correct use of design vocabulary, and an appropriate level of formality in the specification of system behavior.

## Task 1: Object-Oriented Analysis & Design

For this milestone, you will analyze and design the game *Santorini* without god cards (see appendix for rules). You should focus on the game-related functionality of the program, not its user interface. Think of playing the game by calling a sequence of methods, which you could execute in a test case; it is also helpful to think about and possibly sketch out the GUI and how it interacts with the game at this early stage. Note that the game (without god cards) is fairly simple, so you likely won't need more than a few classes/objects/methods.

**Deliverable 1: Domain model.** Create a domain model describing the important concepts of the game. Your domain model should be represented by a UML class diagram; you may optionally include a glossary. For more information on domain models, see Chapter 9 of Larman’s Applying UML and Patterns. Turn this in as `domain-model.pdf` in the root directory of your git repository.

**Deliverable 2: System sequence diagram.** Create a system sequence diagram identifying all interactions between a user and the system when the user plays the game. The system sequence diagram should help you determine what interactions the high-level system makes available to its users. For more information on system sequence diagrams, see Chapter 10 of Larman’s Applying UML and Patterns. Turn this in as `system-sequence-diagram.pdf`.

**Deliverable 3: Behavioral contract.** Provide behavioral contracts for the following interaction initiated by the user: *The user attempts to move a worker*. The contract should explicitly describe the preconditions and postconditions for the interaction, and your behavioral contract should be consistent with your domain model and interaction diagrams. Constructing behavioral contracts should help you envision important changes of internal state of the game when a player interacts with the game. You may provide explicit examples to clarify your contract. For more information on contracts, see Chapter 11 of Larman’s Applying UML and Patterns. Turn this in as `contract.pdf` or `contract.md`.


### Extra Credit: Peer review option

Design is often an interactive process where you bounce of ideas with others. We encourage you to discuss your design with other students, with 5 bonus points per student (**total 10 bonus points at the maximum**) whom you discuss with. To obtain bonus points, you must follow the following guidelines when and after your discussion.

Once you have a reasonably complete initial draft, you may show them your diagrams and text (but not your code) and ask each other questions and give each other feedback. You may improve your designs based on their feedback and what you see in those designs that you critique from others, but the final submission must still be yours. 

To obtain bonus points, you need to submit an extra `peer-review.pdf` file with your homework that contains: for each student you discussed with (1) The names of the student you worked with. (2) The initial version of your designs before you received feedback. (3) A short description of whether and how you changed your design based on feedback you received or other designs you critiqued.

Grading: We will only grade the final submitted deliverables, not your initial designs or how much you helped others or were helped. We will **deduct 5 points** if you take this option, but `peer-review.pdf` is incomplete. Sharing your work with other students beyond this peer review option (e.g., sharing code or sharing diagrams without providing the `peer-review.pdf` file) will be considered as an academic integrity violation as per our syllabus.

## Task 2: Implementation & Test

Implement the game **in and only in Java** based on your design and test it. As usual, document your code using Javadoc for all public functions.

It is encouraged that your tests should include unit tests as well as integration tests that set up the game and play sequences of turns. To achieve that, it is a good strategy to write tests while you implement each function, and go back to add a little more tests in case you missed any important test cases and funcitonality after you complete your implementation.

There is no specific numeric goal for testing (neither for your codes or for our grading), but we expect to see tests of individual key functions (e.g. move and build functions) and tests of sequences of multiple actions of game play. But we are NOT looking for coverage of every possible corner case, and we will NOT inject bugs to evaluate the bug-finding ability of your test suites like Homework 2. Remeber that this homework is not all about tests, and you should not spend too much time in writing **complete** test suites. However, as a useful and necessary step in software construction, tests are helpful in that they can help you build confidence that your implementation is correct at a high level. 

We would like you to run your code by calling the methods directly. We do not expect a user interface, either in command line or graphical, in this assignment. You may find it useful to create a simple command line UI when you are developing the code, but we don’t expect you to test a command line UI as part of your program/testing.

We recommend that you complete Task 1 (modeling) before this implementation, but you should update models by insights gained from the implementation. We expect that models and code align.

Deliverables: Commit all your code to your GitHub repository and ensure that your project is built and tested on Github Actions -- which you will need to set up yourself (see appendix3). 

## Task 3: Justification

Write a short report to answer the following questions to justify your design choices. You should refer to design goals, principles, and patterns in your answers where appropriate. You should also embed the object-level interaction diagram and the object model to backup your explanation in your text. Note that you only need to give the necessary parts of the diagram to illustrate your idea instead of giving a complete diagram. Turn this in as `justification.pdf` or `justification.md`.

1. How can a player interact with the game? What are the possible actions? Please include necessary parts of the Object Model to explain.

2. What state does the game need to store? And where should is be stored? Please include necessary parts of the Object Model to explain.

3. How does the game determine what is a valid build (either a normal block or a dome) and how does the game perform the build? Please include necessary parts of an object-level Interaction Diagram and the Object Model to explain.

Notes on interaction diagram and object model:

1. Your interaction diagram to backup your answer can be a segment of a complete UML interaction diagram, which can provide enough information to illustrate your design. Please make sure you are using the correct symbols and notations. For more information on interaction diagram notation, see Chapter 15 of Larman’s Applying UML and Patterns.

2. Your object model to backup your answer can be a segment of a complete object model of your codes. But please make sure that the partial object model can provide enough information to illustrate your design and that you are using the correct symbols and notations. The objects and methods in your object model should correspond to the objects in the related interaction diagrams. For more information on object model, see Chapter 16 of Larman’s Applying UML and Patterns.


## Submitting your work

As in previous homework assignments you push your solution your Santorini repository on GitHub and submit a link to the final commit to Canvas. A link will look like `https://github.com/CMU-17-214/<reponame>/commit/<commitid>`. 

Please work in the main branch and include the solutions for Task 1, Task 2, and Task 3 in it. The files for Task 1 and Task 3 should all be located in the root directory of your repository.

## Evaluation

The homework is worth 150 points. We will grade the homework roughly with the following rubric:

**Design (30pt):**

* [ ] 10: The domain model in file `domain-model.pdf` describes the vocabulary of the problem, uses suitable notation, and is at the right level of abstraction
* [ ] 10: The system sequence diagram in file  `system-sequence-diagram.pdf` is reasonably complete, uses suitable notation, and is at the right level of abstraction.
* [ ] 10: The behavior contract in file `contract.pdf` or `contract.md` is reasonably complete regarding pre- and post-conditions.

**Implementation (70pt):**

* [ ] 30: All core functionality of the game is implemented and follows all rules as specified. Specifically we will look for: initializing the game, rejecting invalid moves and builds, updating state after moving and building, and determining the winner and ending the game.
* [ ] 10: The implementation aligns with models. We will look for: same names, state and methods in the same classes/objects, associations cardinalities reflect implementation, and interactions possible as shown in diagrams.
* [ ] 5: The public methods of the code are well documented.
* [ ] 10: The student applied the ideas of specification, structural ,and integration tests into their test suites. The key functions like validating a move, a build, and tests of sequences of game play are tested at a reasonable level. The tests follow good practices (e.g. redundancy, independence, readibility. NOT including the completeness of test suites).
* [ ] 5: The build and tests are automated on Github Actions.
* [ ] 5: Commits are reasonably cohesive; commit messages are reasonable.
* [ ] 5: The implementation practices reasonable style, and the codes can pass a reasonable linter check (e.g. checkstyle.xml in previous homework).


**Justification (50pt):**

General requirements for justification answers: The design across all models makes reasonable decisions about responsibility assignment. The justification in file uses suitable terminology and discusses design alternatives in a meaningful way, demonstrating an engagement with design principles and tradeoffs. The use or lack of design patterns is appropriate.

General requirements for object model: The object model is reasonably complete within the scope of the question, uses suitable notation, and is at the right level of abstraction. It is consistent with the system sequence diagram if there is any.

General requirements for object-level interaction diagrams: the diagram describes the internal interactions for corresponding action and is consistent with the object model (i.e., only uses methods and accesses state to which the classes have access according to the model), uses suitable notation, and is at the right level of abstraction.

* [ ] 15: The answers include both text and in-line object model. The ways for users to interact with the game, and which part is responsible for the interaction are clearly stated in the object model. The above general requirements also apply.
* [ ] 15: The answers include both text and in-line object model. The locations storing different states are clearly stated (players, current player, worker locations, towers, winner) in the object model. The above general requirements also apply. 
* [ ] 20: The answers include text, in-line object model, and in-line interaction diagram. The classes and the process that check whether a build is valid are clearly stated in interaction diagram and the object model. The above general requirements also apply. 

**Bonus points (10pt max):**

* [ ] 10: If the peer review option is used, and an extra document `peer-review.pdf` is included, for each student you work with, covering (1) the other students' names, (2) the old design, (3) a description of changes. (5 points per student working with).

## Appendix 1: Santorini Rules

Santorini has very simple rules, but the game is very extensible. You can find the original rules [online](https://roxley.com/products/santorini). Beyond the actual board game, you can also find an App that implements the game if you want to try to play it.

In a nutshell, the rules are as follows: The game is played on a 5 by 5 grid, where each grid can contain towers consisting of blocks and domes. Two players have two workers each on any field of the grid. Throughout the game, the workers move around and build towers. The first worker to make it on top of a level-3 tower wins. Note that though the official rules require that if a player cannot further move any worker, she/he will lose, you don't need to consider this as a winning condition in this homework.

As setup, both players pick starting positions for both their workers on the grid. (For simplicity, in Homework 3 and 5, **you can assume a player (e.g. Player A) always starts first**). Players take turns. In each turn, they select one of their workers, move this worker to an adjacent unoccupied field, and afterward add a block or dome to an unoccupied adjacent field of their new position. Locations with a worker or a dome are considered occupied. Worker can only climb a maximum of one level when moving. Domes can only be built on level-3 towers. You can assume there are infinite pieces to play.

That's it. You probably want to play a few rounds to get a feel for the game mechanics. There are god powers that modify the game behavior, but those will not be relevant until Homework 5.

## Appendix 2: Notation & Tools

To ease communication and avoid ambiguity, we expect all models to use UML notation for class and sequence diagrams. Chapters 9, 10, 15, and 16 of Larman’s Applying UML and Patterns provide many details and guidance on UML notation. We do not require much formality, but we expect that associations are used rather than attributes where appropriate and that each association includes a name and cardinalities. Attributes and methods should be specified correctly, but we do not require precise descriptions of visibility or types. 

It is important that your models demonstrate an understanding of appropriate levels of abstraction. For example, your domain model should not refer to implementation artifacts, and your object model should not include low-level details such as getter and setter methods, unless they aid the general understanding of your design. 

UML contains notation for many advanced concepts, such as loops and conditions in interaction diagrams. You may use UML notation for these advanced concepts, but we do not require you to do so. If you find you need advanced concepts, you may describe such concepts with your own notation or textual comments, as long as you clearly communicate your intent. 

To maximize clarity, we recommend that you draw UML diagrams with software tools. We do not require specific tools, and you may share tool-related tips on Piazza. There are several easy to use online tools like [Draw.io](https://draw.i/) and [Yumly](https://yuml.me/), and also many desktop tools and IDE plugins. We strongly recommend that you do not mechanically extract models from a software implementation; such mechanically generated models are almost always at an inappropriate level of abstraction. We will accept handwritten models or photographs of models (such as whiteboard sketches) if the models are clearly legible.

## Appendix 3: Setting up your Java Project

Please refer to Appendix3.pdf.

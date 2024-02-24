//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Froggie Feeding Frenzie
// Course: CS 300 Fall 2023
//
// Author: Calvin Weng Jien Yong
// Email: cyong4@wisc.edu
// Lecturer: Mouna Kacem
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: Wen Tao Thye
// Partner Email: wthye@wisc.edu
// Partner Lecturer's Name: Hobbes LeGault
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// X Write-up states that pair programming is allowed for this assignment.
// X We have both read and understand the course Pair Programming Policy.
// X We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: NONE
// Online Sources: NONE
//
///////////////////////////////////////////////////////////////////////////////

import java.io.File;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

import java.lang.Thread;
import java.util.Random;
import java.util.Scanner;

/**
 * This class manages the fuctionalities of the Froggie Feeding Frenzie game application.
 */
public class FrogGame extends PApplet {
  private ArrayList<GameActor> gameActors; // array list of the gameActors in the game
  private int score; // the player's current score
  private PImage backgroundImg; // the image to use for the background
  private boolean isGameOver; // keeps track if the game is over, is true if the game is over
  private Random randGen; // random number generator
  private static final int BUG_COUNT = 5; // how many bugs should be on the screen at all times

  /**
   * The main method that launches the Froggie Feeding Frenzie application.
   *
   * @param args Command-line arguments
   */
  public static void main(String[] args) {
    PApplet.main("FrogGame");
  }

  /**
   * Sets the dimension of the display window.
   */
  @Override
  public void settings() {
    size(800, 600);
  }

  /**
   * Sets up the window, backgound, and game actors.
   */
  @Override
  public void setup() {
    this.getSurface().setTitle("Froggie Feeding Frenzie"); // set title of window
    this.imageMode(PApplet.CENTER); // images when drawn will use x,y as their center
    this.rectMode(PApplet.CENTER); // rectangles when drawn will use x,y as their center
    this.focused = true; // window is "active" upon start up
    this.textAlign(PApplet.CENTER); // text written to screen will have center alignment
    this.textSize(30); // text is 30 pt
    randGen = new Random();

    // Load in and save the background image
    backgroundImg = loadImage("images" + File.separator + "background.jpg");

    gameActors = new ArrayList<GameActor>();

    // Set the processing variables
    Hitbox.setProcessing(this);
    GameActor.setProcessing(this);
    Bug.setProcessing(this);
    Tongue.setProcessing(this);
    Frog.setProcessing(this);

    initGame();
  }

  /**
   * Draws the background and game actors to the screen.
   */
  @Override
  public void draw() {
    // Check if game is over
    if (isGameOver) {
      text("GAME OVER\n RESTART [R]", width / 2, height / 2);
      return;
    }

    while(key == 'p'){
      text("Game Paused\nRESUME [L]\nRESTART [R]\nQUIT [ESC]", width / 2, 250);
      return;
    }

    // Draw the backgound image to the middle of the screen.
    image(backgroundImg, width / 2, height / 2);

    // Draw the pause option on the top right of the screen
    text("Pause[P]", 730,40);

    for (GameActor actor : gameActors) {
      // Display all game actors to the screen
      actor.draw();

      // Make all moveable game actors move
      if (actor instanceof Moveable) {
        Moveable moveableBug = (Moveable) actor;
        moveableBug.move();
      }
    }

    runGameLogicChecks();

    // Display the Frog's health and the score to the screen.
    for (GameActor actor : gameActors) {
      if (actor instanceof Frog) {
        Frog frog = (Frog) actor;
        text("Health: " + frog.getHealth(), 80, 40);
        text("Score: " + score, 240, 40);
      }
      break;
    }
  }

  /**
   * Adds a new bug of random type to the game.
   */
  private void addNewBug() {
    // Generate the type and xy-coordinates of the bug
    int bug = randGen.nextInt(4);
    int bugX = randGen.nextInt(this.width);
    int bugY = randGen.nextInt(this.height - 150);

    switch (bug) {
      // Create a regular Bug at (x,y) worth 25 points
      case 0:
        gameActors.add(new Bug(bugX, bugY, 25));
        break;

      // Create a BouncingBug at (x,y) with dx of 2 and dy of 5
      case 1:
        gameActors.add(new BouncingBug(bugX, bugY, 2, 5));
        break;

      // Create a CirclingBug at (x,y) that has a radius of 25 and a random set of RGB values
      case 2:
        int[] tintColor = new int[3];
        for (int x = 0; x < 3; x++) {
          tintColor[x] = randGen.nextInt(256);
        }
        gameActors.add(new CirclingBug(bugX, bugY, 25, tintColor));
        break;

      // Create a StrongBug at (x,y) that has an initial health of 3
      case 3:
        gameActors.add(new StrongBug(bugX, bugY, 3));
        break;
    }
  }

  /**
   * Allow players to click and drag the Frog when the mouse if over the Frog.
   */
  @Override
  public void mousePressed() {
    for (GameActor actor : gameActors) {
      if (actor instanceof Frog) {
        Frog frog = (Frog) actor;

        // Check if the mouse is over the Frog
        if (frog.isMouseOver()) {
          frog.mousePressed();
        }
      }
      break;
    }
  }

  /**
   * Handles the release of mouse to stop dragging the Frog.
   */
  @Override
  public void mouseReleased() {
    for (GameActor actor : gameActors) {
      // Check if game actor is a Frog
      if (actor instanceof Frog) {
        Frog frog = (Frog) actor;
        frog.mouseReleased();
      }
      break;
    }
  }

  /**
   * Start attack or reset the game when relevant keys are pressed.
   */
  @Override
  public void keyPressed() {
    // Let the frog start attacking if the space key is pressed
    if (key == ' ') {
      for (GameActor actor : gameActors) {
        if (actor instanceof Frog) {
          Frog frog = (Frog) actor;
          frog.startAttack();
        }
        break;
      }
    }

    // Reset the game to its initial state if the 'r' key is pressed
    if (key == 'r') {
      initGame();
    }
  }

  /**
   * Sets the game to its initial state before playing the game.
   */
  public void initGame() {
    // Set score at 0
    score = 0;

    // Make the game not over
    isGameOver = false;

    // Remove all actors from the list of actors
    gameActors.clear();

    // Add a frog with 100 health
    gameActors.add(new Frog(width / 2, height - 100, 100));

    // Add bugs of random types
    for (int i = 0; i < BUG_COUNT; i++) {
      addNewBug();
    }
  }

  /**
   * Retrieves the Frog instance from the list of game actors.
   *
   * @return the Frog instance if it is found, null otherwise
   */
  private Frog assignToFrog() {
    for (GameActor actor : gameActors) {
      // Check if the game actor is a frog
      if (actor instanceof Frog) {
        Frog frog = (Frog) actor;
        return frog;
      }
    }
    return null;
  }

  /**
   * Runs all game logic checks.
   */
  private void runGameLogicChecks() {
    Frog frog = assignToFrog();
    if (frog == null) {
      return;
    }

    // Stop attacking if the Frog's tongue hits the edge of the screen
    if (frog.tongueHitBoundary()) {
      frog.stopAttack();
    }

    for (GameActor actor : gameActors) {
      // Check if the game actor is a bug
      if (!(actor instanceof Bug)) {
        continue;
      }

      if (!(actor instanceof StrongBug)) {
        Bug bug = (Bug) actor;

        // If a non-StongBug is hit, stop the frog's attack, remove the bug, increase the score, and
        // add another bug
        if (bug.isEatenBy(frog)) {
          frog.stopAttack();
          gameActors.remove(bug);
          score += bug.getPoints();
          addNewBug();
          break;
        }
      } else {
        StrongBug strongBug = (StrongBug) actor;

        // If a StongBug is hit, stop the frog's attack and decrease its health
        if (strongBug.isEatenBy(frog)) {
          frog.stopAttack();
          strongBug.loseHealth();
        }

        // If a StongBug is dead, remove the bug, increase the score, and add another bug
        if (strongBug.isDead()) {
          gameActors.remove(strongBug);
          score += strongBug.getPoints();
          addNewBug();
          break;
        }
      }
    }

    for (GameActor actor1 : gameActors) {
      // Check if the game actor is a bug
      if (!(actor1 instanceof Bug))
        continue;

      Bug bug1 = (Bug) actor1;

      // Decrease the health of the frog if it is hit by a bug
      if (frog.isHitBy(bug1)) {
        frog.loseHealth();
      }
    }

    // Game is over when the frog is dead
    if (frog.isDead()) {
      isGameOver = true;
      return;
    }
  }
}


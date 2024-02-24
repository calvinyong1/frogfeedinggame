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


import java.util.Random;
import java.util.ArrayList;

public class BouncingBug extends Bug implements Moveable {
  private boolean goDown; //keeps track if bug is moving up or down
  private boolean goLeft; //keeps track if bug is moving left or right
  private static final int POINTS = 100; //number of points this bug is worth
  private Random randGen; //a random generator to determine the initial directions
  private int[] speedNums;
      //the number of pixels to move horizontally and vertically, formatted [dx,dy]

  /**
   * Constructor for the BouncingBug object. Creates a new Bouncing Bug according to the parameters
   *
   * @param x  the x-coordinate for the center of this BouncingBug
   * @param y  the y-coordinate for the center of this BouncingBug
   * @param dx the number of pixels to move horizontally
   * @param dy the number of pixels to move vertically
   */
  public BouncingBug(float x, float y, int dx, int dy) {
    super(x, y, POINTS);
    randGen = new Random();
    goDown = randGen.nextBoolean();
    goLeft = randGen.nextBoolean();

    speedNums = new int[2];
    speedNums[0] = dx;
    speedNums[1] = dy;

  }

  /**
   * Moves this BouncingBug dx pixels left or right (depending on the current horizontal direction)
   * and dy pixels up or down (depending on the current vertical direction)
   *
   * @SpecifiedBy move in interface Moveable
   */
  public void move() {
    int horizontal = 1;
    int vertical = -1;

    if (!shouldMove()) {
      return;
    }
    if (goLeft) {
      horizontal = -1;
    }
    if (goDown) {
      vertical = 1;
    }
    float x = getX() + horizontal * speedNums[0];
    float y = getY() + vertical * speedNums[1];

    if (x >= 800 || x <= 0) {
      x = goLeft ? 0 : 800;
      goLeft = !goLeft;
    }
    if (y >= 600 || y <= 0) {
      y = goDown ? 600 : 0;
      goDown = !goDown;
    }
    this.setX(x);
    this.setY(y);
  }

  /**
   * Reports if the BouncingBug needs to move.
   *
   * @return true if bug should move
   * @SpecifiedBy move in interface Moveable
   */
  public boolean shouldMove() {
    return true;
  }
}



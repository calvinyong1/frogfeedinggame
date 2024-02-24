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


public class Bug extends GameActor {
  //path to the image used for bugs, all bugs use the same image
  private static final String IMG_PATH = "images/bug.png";
  //how many points this bug gives for being eaten
  private int points;

  /**
   * Constructor for the Bug object
   *
   * @param x      the x-coordinate for the center of this bug
   * @param y      the y-coordinate for the center of this bug
   * @param points the number of points the Bug is worth
   */
  public Bug(float x, float y, int points) {
    super(x, y, IMG_PATH);
    this.points = points;
  }

  /**
   * Gets how many points this Bug is worth
   *
   * @return number of points this bug is worth
   */
  public int getPoints() {
    return points;
  }

  /**
   * Determines whether or not this bug has been eaten by the Frog.
   *
   * @return true if the Bug hitbox collides with Frog
   */
  public boolean isEatenBy(Frog f) {
    try {
      Hitbox tongueHitbox = f.getTongueHitbox();
      Hitbox bugHitbox = getHitbox();
      return bugHitbox.doesCollide(tongueHitbox);
    } catch (IllegalStateException e) {
      return false;
    }
  }
}

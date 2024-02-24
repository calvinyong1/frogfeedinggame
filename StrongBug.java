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


public class StrongBug extends Bug implements Moveable {
  private int currentHealth; //the current health of this StringBug, updates when Bug takes damage
  private final int MAX_HEALTH; //the max health of this StrongBug
  private static final int POINTS = 500; //the number of points ALL StrongBugs are worth, 500

  /**
   * Constructor for StrongBug class. Creates a StrongBug object according to the parameters
   *
   * @param x      the x-coordinate for the center of this StrongBug
   * @param y      the y-coordinate for the center of this StrongBug
   * @param health the max health for this StrongBug
   * @throws IllegalArgumentException with descriptive message if health is below one
   */
  public StrongBug(float x, float y, int health) {
    super(x, y, POINTS);
    MAX_HEALTH = health;
    currentHealth = MAX_HEALTH;

    if (health < 1)
      throw new IllegalArgumentException("Health cannot be below 1");
  }

  /**
   * Moves this StrongBug 3 units to the right, wrapping around the screen when the center hits the
   * edge of the window. The Hitbox should move with the StrongBug. The x,y-coordinate of only
   * changes if the StrongBug should move.
   *
   * @SpecifiedBy move in interface Moveable
   */
  public void move() {
    if (!shouldMove()) {
      return;
    }
    float newX = getX() + 3;
    if (newX >= 800) {
      newX = 0;
    }
    setX(newX);
    moveHitbox(getX(), getY());
  }

  /**
   * Reports if this StrongBug is dead.
   *
   * @return true if StrongBug is dead
   */
  public boolean isDead() {
    if (currentHealth <= 0) {
      return true;
    }

    return false;
  }

  /**
   * Decreases the health of this StrongBug by 1.
   */
  public void loseHealth() {
    currentHealth--;
  }

  /**
   * Reports if the StrongBug needs to move.
   *
   * @return true if StrongBug should move
   * @SpecifiedBy shouldMove() in interface Moveable
   */
  @Override
  public boolean shouldMove() {
    if (currentHealth != MAX_HEALTH) {
      return true;
    }

    return false;
  }

  /**
   * Determines whether or not this bug has been eaten by the Frog. If the Bug has been hit by the
   * frog: decrease the StrongBug's health resize the image to 75% of its current height and 75% of
   * it's current width change the dimensions of the hitbox to match the new image dimensions.
   *
   * @param f the frog that has possibly eaten this bug
   * @return true if this Bug's Hitbox overlaps that Frog's Tongue's Hitbox, false otherwise
   */
  @Override
  public boolean isEatenBy(Frog f) {
    try {
      Hitbox tongueHitbox = f.getTongueHitbox();
      Hitbox bugHitbox = getHitbox();

      if (tongueHitbox.doesCollide(bugHitbox)) {
        loseHealth();
        image.resize((int) (image.width * 0.75), (int) (image.height * 0.75));
        bugHitbox.changeDimensions((float) (image.width * 0.75), (float) (image.height * 0.75));
        return true;
      }
    } catch (IllegalStateException e) {
      return false;
    }
    return false;
  }
}

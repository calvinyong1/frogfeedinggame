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


/**
 * An instantiable class for all game actors in the Froggie Feeding Frenzie game. Game actors are
 * images that are drawn the screen that also have hitboxes.
 */
public class GameActor {
  private float[] coordinates;
  private Hitbox hitbox;
  protected processing.core.PImage image;
  protected static processing.core.PApplet processing;

  /**
   * Constructor for a new GameActor object by setting the coordinates, loading the image, and
   * creating the hitbox.
   *
   * @param x       the x-coordinate for the center of this object and its hitbox
   * @param y       the y-coordinate for the center of this object and its hitbox
   * @param imgPath the path to the image that will be loaded in
   * @throws IllegalStateException with a descriptive message if processing is null
   */
  public GameActor(float x, float y, String imgPath) {
    // Check if processing is null
    if (GameActor.processing == null) {
      throw new IllegalStateException(
          "Processing is null. Unable to draw GameActors to the screen. ");
    }

    // Set the coordinates of GameActor object
    this.coordinates = new float[] {x, y};

    // Load image of GameActor object
    this.image = processing.loadImage(imgPath);

    // Create hitbox of GameActor object
    this.hitbox = new Hitbox(x, y, image.width, image.height);
  }

  /**
   * Sets the processing for all GamActors
   *
   * @param processing the instance of a PApplet to draw onto
   */
  public static void setProcessing(processing.core.PApplet processing) {
    GameActor.processing = processing;
  }

  /**
   * Getter for the x-coordinate.
   *
   * @return the x-coordinate of center of this GameActor
   */
  public float getX() {
    return coordinates[0];
  }

  /**
   * Getter for the y-coordinate.
   *
   * @return the y-coordinate of center of this GameActor
   */
  public float getY() {
    return coordinates[1];
  }

  /**
   * Setter for the x-coordinate.
   *
   * @param newX the new x-coordinate for the center of this GameActor
   */
  public void setX(float newX) {
    coordinates[0] = newX;

    // Move hitbox to new coordinates
    moveHitbox(newX, coordinates[1]);
  }

  /**
   * Setter for the y-coordinate.
   *
   * @param newY the new y-coordinate for the center of this GameActor
   */
  public void setY(float newY) {
    coordinates[1] = newY;

    // Move hitbox to new coordinates
    moveHitbox(coordinates[0], newY);
  }

  /**
   * Getter for the Hitbox.
   *
   * @return the Hitbox of this GameActor
   */
  public Hitbox getHitbox() {
    return hitbox;
  }

  /**
   * Moves this GameActors Hitbox to the provided x,y-coordinates
   *
   * @param x the new x-coordinate for the center of the GameActor's hitbox
   * @param y he new y-coordinate for the center of the GameActor's hitbox
   */
  public void moveHitbox(float x, float y) {
    hitbox.setPosition(x, y);
  }

  /**
   * Draws the image of the GameActor to the screen.
   */
  public void draw() {
    processing.image(image, coordinates[0], coordinates[1]);
  }
}


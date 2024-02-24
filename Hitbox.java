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

import processing.core.PApplet;

/**
 * An instantiable class maintains data about Hitboxes used for different objects in the Froggie
 * Feeding Frenzie game. Hitboxes in this application are rectangles.
 *
 * @author Michelle & Calvin Weng Jien Yong & Wen Tao Thye
 */
public class Hitbox {
  /** the 2D coordinates of the center of this hitbox [x,y] */
  private float[] coordinates;
  /** the width of the box */
  private float width;
  /** the height of the box */
  private float height;
  /** the PApplet that the hitbox can draw on */
  private static PApplet processing;

  /**
   * Creates a new Hitbox object based on the given parameters.
   *
   * @param x,      the x-coordinate of the center of the hitbox
   * @param y,      the y-coordinate of the center of the hitbox
   * @param width,  the width of the hitbox
   * @param height, the height of the hitbox
   * @throws IllegalStateException if processing is null
   * @author Michelle
   */
  public Hitbox(float x, float y, float width, float height) {
    if (Hitbox.processing == null)
      throw new IllegalStateException(
          "Processing is null. setProcessing() must be called before " + "creating any Hitbox objects.");
    this.coordinates = new float[] {x, y};
    this.width = width;
    this.height = height;
  }

  /**
   * Changes the coordinates of the center of this Hitbox
   *
   * @param x, the new x-coordinate of the Hitbox's center
   * @param y, the new y-coordinate of the Hitbox's center
   * @author Michelle
   */
  public void setPosition(float x, float y) {
    this.coordinates[0] = x;
    this.coordinates[1] = y;
  }

  /**
   * Detects if this Hitbox and another collide by overlapping.
   *
   * @param other, the Hitbox to check for if it collides with this one
   * @return true if the 2 Hitboxes overlap, false otherwise
   * @author Calvin Weng Jien Yong
   * @author Wen Tao Thye
   */
  public boolean doesCollide(Hitbox other) {
    // Calculate the left, right, top, and bottom coordinates of this hitbox
    float thisLeft = coordinates[0] - width / 2;
    float thisRight = coordinates[0] + width / 2;
    float thisTop = coordinates[1] - height / 2;
    float thisBottom = coordinates[1] + height / 2;

    // Calculate the left, right, top, and bottom coordinates of the other hitbox
    float otherLeft = other.coordinates[0] - other.width / 2;
    float otherRight = other.coordinates[0] + other.width / 2;
    float otherTop = other.coordinates[1] - other.height / 2;
    float otherBottom = other.coordinates[1] + other.height / 2;

    // Compare the positions of two hitboxes to check if they overlap
    boolean condition1 = !(otherLeft > thisRight || thisLeft > otherRight);
    boolean condition2 = !(otherTop > thisBottom || thisTop > otherBottom);

    // Two hitboxes collide if they overlap horizontally and vertically
    return condition1 && condition2;
  }

  /**
   * Sets the processing for all Hitboxes
   *
   * @param processing, the instance of a PApplet to draw onto
   * @author Michelle
   */
  public static void setProcessing(PApplet processing) {
    Hitbox.processing = processing;
  }

  /**
   * Change both the width and height of this Hitbox
   *
   * @param width,  the new width for the Hitbox
   * @param height, the new height for the Hitbox
   * @author Michelle
   */
  public void changeDimensions(float width, float height) {
    this.height = height;
    this.width = width;
  }

  /**
   * Draws the Hitbox to the screen. Provided solely for visualization and debugging purposes.
   *
   * @author Michelle
   */
  public void visualizeHitbox() {
    processing.noFill(); // make the inside of the rectangle clear
    processing.strokeWeight(3); // make the lines thicker
    // draw a rectangle to the screen
    processing.rect(coordinates[0], coordinates[1], width, height);
  }
}

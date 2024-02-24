///////////////////////////// FILE HEADER ///////////////////////////////////////
//
// Title: Froggie Feeding Frenzie
//
// Author: Calvin Yong Weng Jien
// Email: calvinyongwj@gail.com
//
///////////////////////////////////////////////////////////////////////////////

import java.io.File;

/**
 * An instantiable class maintains data about a Frog in the Froggie Feeding Frenzie game. They an be
 * drawn to the screen, dragged around by the mouse, and attack Bugs with its Tongue.
 */
public class Frog extends GameActor implements Moveable {
  private int health;
  private Tongue tongue;
  private boolean isDragging;
  private float oldMouseX;
  private float oldMouseY;
  private static final String IMG_PATH = "images" + File.separator + "frog.png";

  /**
   * Constructor for a new Frog object using the provided parameters. The Frog is NOT dragging by
   * default.
   *
   * @param x      the x-coordinate for the center of the Frog and starting point of the tongue
   * @param y      the y-coordinate for the center of the Frog and starting point of the tongue
   * @param health the initial health of this Frog
   * @throws IllegalArgumentException with a descriptive message if health is less than 1
   */
  public Frog(float x, float y, int health) {
    // Call GameActor constructor
    super(x, y, IMG_PATH);

    // Check if the frog's health is less than 1
    if (health < 1) {
      throw new IllegalArgumentException("Health of the frog is less than 1.");
    }

    this.health = health;
    this.tongue = new Tongue(x, y);
    this.isDragging = false;
  }

  /**
   * Draws the image of the Frog to the screen. If the Frog's tongue is active: (1)draw the tongue
   * and (2) extend the tongue by moving it's x-coordinate to the Frog's x-coordinate and up 2
   * pixels.
   *
   * @Overrides draw in class GameActor
   */
  public void draw() {
    // Draw the image of the Frog to the screen
    super.draw();

    // Draw and extend tongue if tongue is active
    if (tongue.isActive()) {
      tongue.draw();
      tongue.extend(getX(), -2);
    }
  }

  /**
   * Moves the Frog by dragging it by the mouse, if it should be dragging. (See write-up for more
   * details on implementing the dragging functionality.) The starting point of the tongue and the
   * Hitbox need to move along with the Frog. If the Frog's tongue is NOT active, move the tongue's
   * endpoint along with the Frog as well. The Frog only moves if it should move.
   *
   * @see move in interface Moveable
   */
  public void move() {
    // Calculate the distance moved by the mouse
    float mouseX = processing.mouseX;
    float mouseY = processing.mouseY;
    float dx = mouseX - oldMouseX;
    float dy = mouseY - oldMouseY;

    // Move the xy-coordinates of the Frog by the values calculated
    if (isDragging) {
      setX(getX() + dx);
      setY(getY() + dy);
      tongue.updateStartPoint(getX(), getY());
    }
    // update the previous xy-coordinates of the mouse to its current position
    oldMouseX = mouseX;
    oldMouseY = mouseY;

    // Move the tongue's endpoint with the Frog if the tongue is not active
    if (!tongue.isActive()) {
      tongue.extend(getX(), dx);
    }
  }

  /**
   * Determines whether or not this Frog has run into a Bug.
   *
   * @param b the Bug to check that if it collides with the Frog
   * @return true if the Bug's Hitbox and Frog's Hitbox overlap, false otherwise
   */
  public boolean isHitBy(Bug b) {
    return getHitbox().doesCollide(b.getHitbox());
  }

  /**
   * Gets the Hitbox for this Frog's tongue.
   *
   * @return IllegalStateException - if the tongue is currently inactive
   */
  public Hitbox getTongueHitbox() {
    // Check if the Fog's tongue is active
    if (!tongue.isActive()) {
      throw new IllegalStateException("The tongue is currently inactive.");
    }

    return tongue.getHitbox();
  }

  /**
   * Decreases the health of this Frog by 1.
   */
  public void loseHealth() {
    health--;
  }

  /**
   * Gets the current health of the Frog
   *
   * @return the current health of this Frog
   */
  public int getHealth() {
    return health;
  }

  /**
   * Reports if the Frog needs to move on the screen.
   *
   * @return true if the Frog is being dragged, false otherwise
   * @see shouldMove in interface Moveable
   */
  public boolean shouldMove() {
    return isDragging;
  }

  /**
   * Determines if this frog is dead.
   *
   * @return true if this Frog's health is 0 or lower, false otherwise
   */
  public boolean isDead() {
    return health <= 0;
  }

  /**
   * Changes this Frog so it is now being dragged. This method should only be called externally when
   * the mouse is over this frog and has been clicked.
   */
  public void mousePressed() {
    // Check if the mouse is over the Frog
    if (isMouseOver()) {
      isDragging = true;
      oldMouseX = GameActor.processing.mouseX;
      oldMouseY = GameActor.processing.mouseY;
    }
  }

  /**
   * Changes this Frog so it is no longer being dragged. This method should only be called
   * externally when the mouse has been released.
   */
  public void mouseReleased() {
    isDragging = false;
  }

  /**
   * Determines if the mouse is over the Frog's image
   *
   * @return true, if the mouse is inside the Frog's bounding box of the image, false otherwise
   */
  public boolean isMouseOver() {
    // Calculate the left, right, top, and bottom boundaries of the Frog's image
    float frogLeft = getX() - image.width / 2;
    float frogRight = getX() + image.width / 2;
    float frogTop = getY() - image.height / 2;
    float frogBottom = getY() + image.height / 2;

    // Check if the mouse is inside the calculated boundaries
    return (GameActor.processing.mouseX >= frogLeft && GameActor.processing.mouseX <= frogRight && GameActor.processing.mouseY >= frogTop && GameActor.processing.mouseY <= frogBottom);
  }

  /**
   * Starts an attack by resetting the tongue to it's default state and activating the tongue.
   */
  public void startAttack() {
    tongue.reset();
    tongue.activate();
  }

  /**
   * Stops the attack by deactivating the tongue.
   */
  public void stopAttack() {
    tongue.deactivate();
  }

  /**
   * Reports if this Frog's tongue has hit the top of the screen.
   *
   * @return true if the tongue has hit the top of the screen, false otherwise
   */
  public boolean tongueHitBoundary() {
    return tongue.hitScreenBoundary();
  }
}

///////////////////////////// FILE HEADER ///////////////////////////////////////
//
// Title: Froggie Feeding Frenzie
//
// Author: Calvin Yong Weng Jien
// Email: calvinyongwj@gail.com
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

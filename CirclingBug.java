///////////////////////////// FILE HEADER ///////////////////////////////////////
//
// Title: Froggie Feeding Frenzie
//
// Author: Calvin Yong Weng Jien
// Email: calvinyongwj@gail.com
//
///////////////////////////////////////////////////////////////////////////////


public class CirclingBug extends Bug implements Moveable {
  private float[] circleCenter; //the x,y-coordinates for the center of the circle path
  private static final int POINTS = 200; //number of points a CirclingBug is worth
  private double radius; //the radius of the circle path this bug moves on
  private double ticks; //keeps track of how long the bug has been moving
  private int[] tintColor; //color used to tint the image when being drawn [Red,Green,Blue]

  /**
   * Constructor for CirclingBug object
   *
   * @param circleX   the x-coordinate for the center of the circle path
   * @param circleY   the y-coordinate for the center of the circle path
   * @param radius    the radius of this CirclingBug's circle path
   * @param tintColor an array containing the Red,Green, and Blue values for the tint color
   */

  public CirclingBug(float circleX, float circleY, double radius, int[] tintColor) {
    super(circleX, (float) radius + circleY, POINTS);

    circleCenter = new float[2];
    circleCenter[0] = circleX;
    circleCenter[1] = circleY;

    this.radius = radius;
    this.tintColor = tintColor;
    this.ticks = 0.0;
  }

  /**
   * Draws the image to the screen, tinting it with the tintColor before drawing it. After the image
   * is drawn to the screen the tinting effect will need to undone by tinting it again with white.
   * (255, 255, 255)
   *
   * @Overrides draw() in class GameActor
   */
  @Override
  public void draw() {
    processing.tint(tintColor[0], tintColor[1], tintColor[2]);
    processing.image(image, getX(), getY());
    processing.tint(255, 255, 255);
  }

  /**
   * Moves this CirclingBug to the next position on its circular path. The Hitbox should move with
   * the CirclingBug. The bug only changes its xy-coordinates if it should move. Ticks will advance
   * by 0.05 whenever this method is called.
   *
   * @SpecifiedBy move in interface Moveable
   */
  @Override
  public void move() {
    ticks += 0.05;

    if (!shouldMove()) {
      return;
    }

    float newX = (float) (radius * Math.cos(ticks) + circleCenter[0]);
    float newY = (float) (radius * Math.sin(ticks) + circleCenter[1]);

    this.setX(newX);
    this.setY(newY);
    this.moveHitbox(newX, newY);
  }

  /**
   * Reports if the BouncingBug needs to move.
   *
   * @return true if bug should move
   * @SpecifiedBy move in interface Moveable
   */
  @Override
  public boolean shouldMove() {
    return true;
  }
}

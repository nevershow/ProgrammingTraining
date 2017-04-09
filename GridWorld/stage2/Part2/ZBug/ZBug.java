import info.gridworld.actor.Bug;
import info.gridworld.grid.Location;

/**
 * A <code>ZBug</code> traces out a "Z" of a given size. <br />
 */
public class ZBug extends Bug {
  private int steps;
  private int seg;
  private int sideLength;

  /**
   * Constructs a Z bug that traces a Z of a given side length
   * @param length the side length
   */
  public ZBug(int length) {
    steps = seg = 0;
    sideLength = length;
    this.setDirection(Location.EAST);
  }

  /**
   * Moves to the next location.
   */
  public void act() {
    if (seg < 3) {
      if (steps < sideLength) {
        if (canMove()) {
          move();
          ++steps;
        }
      } else {
        switch (seg) {
        case 0:
          this.setDirection(Location.SOUTHWEST);
          break;
        case 1:
          this.setDirection(Location.EAST);
          break;
        default:
          break;
        }
        steps = 0;
        ++seg;
      }
    }
  }
}

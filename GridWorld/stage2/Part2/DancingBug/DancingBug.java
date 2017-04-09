import info.gridworld.actor.Bug;

/**
 * A <code>DancingBug</code> "dances" by making different turns before each move. <br />
 */
public class DancingBug extends Bug {
  private int steps;
  private int arrLength;
  private int [] turnTimes;

  /**
   * Constructs a Z bug that traces a Z of a given side length
   * @param arr the arr of turn times
   */
  public DancingBug(int... arr) {
    steps = 0;
    arrLength = arr.length;
    if (arrLength > 0) {
    	turnTimes = new int[arrLength];
    	turnTimes = arr.clone();
    }
  }

  /**
   * Moves to the next location.
   */
  public void act() {
  	// check if the arr has entries
  	if (arrLength > 0) {
	    int times = turnTimes[steps % arrLength];
	    for (int i = 0; i < times; ++i)
	      turn();
	    ++steps;
  	}

    if (canMove())
      move();
    else
      turn();
  }
}

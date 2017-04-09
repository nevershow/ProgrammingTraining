import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

/**
 * A <code>Jumper</code> can jump forward two cells each moves. <br/>
 * It "jumps" over flowers and rocks. It doesn't leave anything behind it when
 * it jumps. <br />
 */
public class Jumper extends Actor {
	/**
	 * Constructs a jumper
	 */
	public Jumper() {
	}

	/**
	 * Turns the bug 45 degrees to the right without changing its location.
	 */
	public void turn() {
		setDirection(getDirection() + Location.HALF_RIGHT);
	}
	
	/**
	 * Moves if it can move, turns otherwise.
	 */
	public void act() {
		Grid<Actor> grid = getGrid();
		if (grid == null)
			return;
		Location loc = getLocation();
		Location next = loc.getAdjacentLocation(getDirection());

		// facing an edge of the grid, turn right 90 degree
		if (!grid.isValid(next)) {
			turn();
			turn();
			return;
		}

		Location nexttwo = next.getAdjacentLocation(getDirection());
		// two cells in front of the jumper is out of the grid
		if (!grid.isValid(nexttwo)) {
			turn();
			return;
		}

		Actor front = grid.get(nexttwo);
		// two cells in front of the jumper is empty or is occupied by a Bug
		if (front instanceof Bug || front == null) {
			if (front != null) // eat the Bug
				grid.remove(nexttwo);
			moveTo(nexttwo);
		} else {
			turn();
		}
	}
}

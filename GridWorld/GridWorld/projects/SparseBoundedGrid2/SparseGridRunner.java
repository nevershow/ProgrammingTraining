import info.gridworld.grid.*;
import info.gridworld.actor.*;

/**
* This class runs a world with additional grid choices.
*/
public class SparseGridRunner {
	private SparseGridRunner() {}
	
	public static void main(String[] args) {
		ActorWorld world = new ActorWorld();
		 world.addGridClass("SparseBoundedGrid2");
		world.add(new Location(2, 2), new Critter());
		world.show();
	}
}

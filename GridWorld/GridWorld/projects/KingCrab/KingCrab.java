import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

public class KingCrab extends CrabCritter
{ 
	
	/* Push away the actors that in the left front of, front of and right front of this.
	Return true if can push away, otherwise false.
	*/	
	private boolean pushAway(Location loc) {
		Grid<Actor> grid = getGrid();
		Location loc2 = loc.getAdjacentLocation(getLocation().getDirectionToward(loc));
		if (grid.isValid(loc2) && grid.get(loc2) == null) {
			grid.get(loc).moveTo(loc2);
		    return true;
		}
		return false;
	}
	
	// If can't push away the actor, eat it.(remove)
    public void processActors(ArrayList<Actor> actors)
    {
        for (Actor a : actors)
        {
            if (!pushAway(a.getLocation()))
                a.removeSelfFromGrid();
        }
    }
}

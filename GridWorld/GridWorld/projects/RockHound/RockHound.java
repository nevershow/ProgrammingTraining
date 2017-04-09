import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

import java.util.ArrayList;
import java.awt.Color;

public class RockHound extends Critter
{
    private static final double DARKENING_FACTOR = 0.2;

    /**
     * Randomly selects a neighbor that is a rock and remove it.
     */
    public void processActors(ArrayList<Actor> actors)
    {
        int n = actors.size();
        if (n == 0) {
        	return;
        }
        else {
        	for (Actor actor : actors)
        		if (actor instanceof Rock)
                    actor.removeSelfFromGrid();
        }
    }
}

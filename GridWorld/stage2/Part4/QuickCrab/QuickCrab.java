import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

public class QuickCrab extends CrabCritter
{
	// check if this can move two steps left or right
    public void moveTwoSteps(ArrayList<Location> locs, int direction) {
   	 	Grid gr = getGrid();
        Location loc = getLocation();
        Location loc2;
        Location neighborLoc = loc.getAdjacentLocation(direction);
        if (gr.isValid(neighborLoc) && gr.get(neighborLoc) == null) { 
       	 loc2 = neighborLoc.getAdjacentLocation(direction);
        	  if (gr.isValid(loc2) && gr.get(loc2) == null)
        		  locs.add(loc2);
        }
	}
    
    // If can move two steps left or right, randomly select one location. Ohterwise move like Crab.
    public ArrayList<Location> getMoveLocations()
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        moveTwoSteps(locs, getDirection() + Location.LEFT);
        moveTwoSteps(locs, getDirection() + Location.RIGHT);
        if (locs.size() > 0) {
			return locs;
		}
        else {
			return super.getMoveLocations();
		}
    }
}

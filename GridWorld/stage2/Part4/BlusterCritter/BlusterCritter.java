import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.util.ArrayList;
import java.awt.Color;

public class BlusterCritter extends Critter
{
    private static final double COLOR_FACTOR = 0.2;
    private int courage;
    private static Location eightLocs[] = new Location[8];
    
    
    public BlusterCritter() {
    	courage = 8;
    	init();
    	setColor(Color.GRAY);
	}
    
    public BlusterCritter(int _courage) {
    	courage = _courage;
    	init();
    	setColor(Color.GRAY);
	}

    public int getcourage() {return courage;}
    
    private void init() {
    	eightLocs[0]= new Location(-2, -1); 
    	eightLocs[1]= new Location(-2,  1); 
    	eightLocs[2]= new Location(-1, -2); 
    	eightLocs[3]= new Location(-1,  2); 
    	eightLocs[4]= new Location(2,  -1); 
    	eightLocs[5]= new Location(2,   1); 
    	eightLocs[6]= new Location(1,  -2); 
    	eightLocs[7]= new Location(1,   2); 
	}
    
    /**
     * Finds the valid locations two steps forward away from this critter in 8 directions.
     * @param directions - an array of directions (which are relative to the
     * current direction)
     * @return a set of valid locations that are two steps away from the current
     * location in the given directions
     */
    public ArrayList<Location> getLocationsInDirections(int[] directions)
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        Grid gr = getGrid();
        int direction = getDirection();
        Location loc = getLocation();
        Location loc2;
        for (int d : directions)
        {
            Location neighborLoc = loc.getAdjacentLocation(direction + d);
            if (gr.isValid(neighborLoc)) {
                locs.add(neighborLoc);
                loc2 = neighborLoc.getAdjacentLocation(direction + d);
                if (gr.isValid(loc2))
					locs.add(loc2);
            }
        }
        return locs;
    }  
  
    /**
     * Gets the actors for processing. Return the actors that two steps ayaw from this.
     * Postcondition: The state of all actors is unchanged.
     * @return a list of actors that this critter wishes to process.
     */
    public ArrayList<Actor> getActors()
    {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        
        int[] dirs = { 0, 45, 90, 135, 180, 225, 270, 315};
        for (Location loc : getLocationsInDirections(dirs))
        {
            Actor a = getGrid().get(loc);
            if (a != null && a instanceof Critter)
                actors.add(a);
        }
        
        Location loc = getLocation();
        Grid<Actor> grid = getGrid();
    	for (int i = 0; i < 8; ++i)
        {
    		Location tmp = new Location(loc.getRow() + eightLocs[i].getRow(), loc.getCol() + eightLocs[i].getCol());
    		if (grid.isValid(tmp)) {
                Actor a = grid.get(tmp);
                if (a instanceof Critter)
                    actors.add(a);
			}
        }
        return actors;
    }

    /**
     * If the number of processActors is less than courage, its color brighten.
     * Otherwise darken.
     */
    public void processActors(ArrayList<Actor> actors)
    {
        int n = actors.size();
        if (n < courage) {
        	Color c = getColor();
        	int red = (int) (c.getRed() * (1 + COLOR_FACTOR));
        	int green = (int) (c.getGreen() * (1 + COLOR_FACTOR));
        	int blue = (int) (c.getBlue() * (1 + COLOR_FACTOR));

        	setColor(new Color(Math.min(red, 255), Math.min(green, 255), Math.min(blue, 255)));
        }
        else {
        	Color c = getColor();
        	int red = (int) (c.getRed() * (1 - COLOR_FACTOR));
        	int green = (int) (c.getGreen() * (1 - COLOR_FACTOR));
        	int blue = (int) (c.getBlue() * (1 - COLOR_FACTOR));

        	setColor(new Color(Math.max(red, 5), Math.max(green, 5), Math.max(blue, 5)));
		}	
    }
}

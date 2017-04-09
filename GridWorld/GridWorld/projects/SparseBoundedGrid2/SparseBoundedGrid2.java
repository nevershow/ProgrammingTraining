import java.util.ArrayList;
import java.util.HashMap;

import info.gridworld.grid.*;

public class SparseBoundedGrid2 extends AbstractGrid<Object> {
	private HashMap<Location, Object> occupantMap;
	private int row;
	private int col;
	
	public SparseBoundedGrid2(int rows, int cols) {
		if (rows <= 0)
            throw new IllegalArgumentException("rows <= 0");
        if (cols <= 0)
            throw new IllegalArgumentException("cols <= 0");
        occupantMap = new HashMap<Location, Object> ();
        row = rows;
        col = cols;
	}
	
	public int getNumRows( ) {
		return row;
	}
	
	public int getNumCols() {
		return col;
	}
	
    public boolean isValid(Location loc)
    {
    	if (loc == null)
            throw new NullPointerException("loc == null");
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }
    
    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> theLocations = new ArrayList<Location>();

        // Look at all grid locations.
        for (Location loc : occupantMap.keySet())
        	theLocations.add(loc);
        
        return theLocations;
    }

    public Object get(Location loc)
    {	
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        
        return occupantMap.get(loc);
    }

    public Object put(Location loc, Object obj)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        if (obj == null)
            throw new NullPointerException("obj == null");

        // Add the object to the grid.
        Object oldOccupant = remove(loc);
        occupantMap.put(loc, obj);
        return oldOccupant;
    }

    public Object remove(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        
        // Remove the object from the grid.
        Object obj = get(loc);
        occupantMap.remove(loc);
        return obj;
    }
}
import java.util.ArrayList;

import info.gridworld.grid.*;

public class UnboundedGrid2 extends AbstractGrid<Object> {
	private Object[][] occupantArray;
	private int len;
	private String invalid = " isn't an valid location!";	
	
	public UnboundedGrid2() {
        len = 16;
        occupantArray = new Object[len][len];
	}
	
	public int getNumRows( ) {
		return -1;
	}
	
	public int getNumCols() {
		return -1;
	}
	
    public boolean isValid(Location loc)
    {
    	return loc.getRow() >= 0 && loc.getCol() >= 0;
    }
    
    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> theLocations = new ArrayList<Location>();
        Location loc;
        // Look at all grid locations.
        for (int r = 0; r < len; r++) {
			for (int c = 0; c < len; c++) {
				loc = new Location(r, c);
                if (get(loc) != null)
                    theLocations.add(loc);
			}
		}
        return theLocations;
    }

    public Object get(Location loc)
    {	
        if (!isValid(loc))
            throw new IllegalArgumentException(loc + invalid);
        if (loc.getRow() >= len || loc.getCol() >= len) 
        	return null;
        return occupantArray[loc.getRow()][loc.getCol()];
    }

    public Object put(Location loc, Object obj)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException(loc + invalid);
        if (obj == null)
            throw new NullPointerException("obj == null");

        // Add the object to the grid.
        int r = loc.getRow();
        int c = loc.getCol();
        int tmp = Math.max(r, c) + 1;
        if (tmp >= len) 
        	expand(tmp);
        
        Object oldOccupant = remove(loc);
        occupantArray[r][c] = obj; 
        return oldOccupant;
    }

    public Object remove(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException(loc + invalid);

        int r = loc.getRow();
        int c = loc.getCol();
        if (r >= len || c>= len) 
			return null;

        // Remove the object from the grid.
        Object obj = get(loc);
        occupantArray[r][c] = null; 
        return obj;
    }
    
    public void expand(int tmp) {
    	int newlen = len;
		while (newlen <= tmp)
			newlen *= 2;
		
		Object newarray[][] = new Object[newlen][newlen]; 
		for (int r = 0; r < len; r++)
			for (int c = 0; c < len; c++)
				newarray[r][c] = occupantArray[r][c]; 
		len = newlen;
		occupantArray = newarray;
	}
}
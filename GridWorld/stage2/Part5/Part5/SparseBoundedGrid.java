import java.util.ArrayList;

import info.gridworld.grid.*;

public class SparseBoundedGrid extends AbstractGrid<Object> {
	private SparseGridNode [] occupantArray;
	private int row;
	private int col;
	private String invalid = " isn't an valid location!";
	
	public SparseBoundedGrid(int rows, int cols) {
		if (rows <= 0)
            throw new IllegalArgumentException("rows <= 0");
        if (cols <= 0)
            throw new IllegalArgumentException("cols <= 0");
        occupantArray = new SparseGridNode[rows];
        row = rows;
        col = cols;
	}
	
	public int getNumRows() {
		return row;
	}
	
	public int getNumCols() {
		return col;
	}
	
    public boolean isValid(Location loc)
    {
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }
    
    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> theLocations = new ArrayList<Location>();

        // Look at all grid locations.
        for (int r = 0; r < getNumRows(); r++)       
            for (SparseGridNode node = occupantArray[r]; node != null; node = node.getNext())           
                theLocations.add(new Location(r, node.getCol()));

        return theLocations;
    }

    public Object get(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException(loc + invalid);
        int r = loc.getRow();
        int c = loc.getCol();
        for (SparseGridNode node = occupantArray[r]; node != null; node = node.getNext())
        	if (c == node.getCol())
				return node.getOccupant();
        
        return null; // can't find
    }

    public Object put(Location loc, Object obj)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException(loc + invalid);
        if (obj == null)
            throw new NullPointerException("obj == null");

        // Add the object to the grid.
        Object oldOccupant = remove(loc);
        int r = loc.getRow();
        int c = loc.getCol();
        SparseGridNode node = occupantArray[r];
        occupantArray[r] = new SparseGridNode(obj, c, node);
        return oldOccupant;
    }

    public Object remove(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException(loc + invalid);
        
        // Remove the object from the grid.
        Object obj = get(loc);
        int c = loc.getCol();
        int r = loc.getRow();
        SparseGridNode pre = occupantArray[r];
        if (pre != null) {
        	if (pre.getCol() == c) {
				occupantArray[r] = pre.getNext(); 
			}
        	else {
        		SparseGridNode next = pre.getNext();
            for (; next != null && next.getCol() != c; pre = pre.getNext(), next = next.getNext()) ;
            if (next != null) 
				pre.setNext(next.getNext());
        	}
        }
        return obj;
    }
}
public class SparseGridNode {
	private Object occupant;
	private int col;
	private SparseGridNode next;
	
	public SparseGridNode(Object occ, int initcol, SparseGridNode initnext) {
		occupant = occ;
		col = initcol;
		next = initnext;
	}
	
	public Object getOccupant() {
		return occupant;
	}
		
	public int getCol() {
		return col;
	}
	
	public SparseGridNode getNext() {
		return next;
	}
	
	public void setNext(SparseGridNode initnext) {
		next = initnext;
	}
}

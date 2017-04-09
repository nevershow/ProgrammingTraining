package info.gridworld.maze;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.grid.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;

import javax.swing.JOptionPane;

/*
 * Low:   534 1175 350 973 1052
 * Smart: 552 761 330 175 420
 */

/*
 * A node store direction and its counter
 * If move forward one step in a direction, increase the counter
 * If moves back, decrease the counter.
 */
class Node {
	private int dir;
	private int ct;
	public Node(int initdir, int initct) {
		dir = initdir;
		ct = initct;
	}
	public int getDir() {return dir;}
	public int getCt() {return ct;}
	public void setCt(int deta) { ct += deta;}
}

/**
 * A <code>MazeBug</code> can find its way in a maze. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class MazeBug extends Bug {
	private Location next;            // the next location to move
	private Integer stepCount = 0;    // count how many step has the MazeBug moved 
	private boolean isEnd = false;    // whether the MazeBug reach the goal
	private boolean hasShown = false; // has shown final message
	private Stack<Location> path = new Stack<>();  // store a path to the goal
	private ArrayList<Node> arr = new ArrayList<>();

	/**
	 * Constructs a box bug that traces a square of a given side length
	 * @param length the side length
	 */
	public MazeBug() {
		setColor(Color.GREEN);
		arr.add(new Node(0, 0));
		arr.add(new Node(90, 0));
		arr.add(new Node(270, 0));
		arr.add(new Node(180, 0));
	}

	/**
	 * Moves to the next location of the square.
	 */
	public void act() {
		boolean willMove = canMove();
		if (isEnd) {
			if (!hasShown) { // show step count when reach the goal
				String msg = stepCount.toString() + " steps";
				JOptionPane.showMessageDialog(null, msg);
				hasShown = true;
			}
			Object[] patharr = path.toArray();
			Grid<Actor> grid = getGrid();
			for (Object loc : patharr) {
				System.out.println(loc);
				grid.get((Location)loc).setColor(Color.GREEN);
			}
			return;
		} else if (willMove) {  // increase step count when move
			move();
			++stepCount;
		} else {  // If can't move, return back and decrease the direction counter
			Grid<Actor> grid = getGrid();
			Location loc = getLocation();
			Location top = path.pop();
			++stepCount;
			grid.remove(top);
			setDirection(getLocation().getDirectionToward(top));
			moveTo(top);
			Flower flower = new Flower(getColor());
			flower.putSelfInGrid(getGrid(), loc);
			
			// decrease the direction (move into this location before) counter
			int dir = 180 + ((getDirection() / 90) % 2) * 180 - getDirection();
			for (Node node : arr)
				if (node.getDir() == dir) {
					node.setCt(-1);
					return;
				}
		}
	}

	/**
	 * Find all positions that can be move to and never visited.
	 * @param loc: the location to detect.
	 * @return List of positions.
	 */
	public Location getValid(Location loc) {
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return null;
		
		// sort the arr and move to a direction with larger counter
		Location adjLocation;
		arr.sort(new Comparator<Node>() {
			@Override
			public int compare(Node a, Node b) {
				return (a.getCt() < b.getCt()) ? 1 : -1;
			}
		});
		for (Node node : arr) {
			adjLocation = getLocation().getAdjacentLocation(node.getDir());
			if (gr.isValid(adjLocation) && 
					(gr.get(adjLocation) == null || gr.get(adjLocation).getColor().equals(Color.RED))) {
				node.setCt(1);
				return adjLocation;
			}
		}
		return null;
	}

	/**
	 * Tests whether this bug can move forward into a new location.
	 * @return true if this bug can move.
	 */
	public boolean canMove() {
		Grid<Actor> gr = getGrid();
		Actor adj;
		Location loc = getValid(getLocation());
		if (loc != null) {
			adj = gr.get(loc);
			next = loc;
			isEnd = adj != null && adj.getColor().equals(Color.RED);
			return true;
		}
		return false;
	}

	/**
	 * Moves the bug forward, putting a flower into the location it previously occupied.
	 */
	public void move() {
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return;
		Location loc = getLocation();
		path.push(loc);
		setDirection(getLocation().getDirectionToward(next));
		moveTo(next);
		Flower flower = new Flower(getColor());
		flower.putSelfInGrid(gr, loc);
	}
}
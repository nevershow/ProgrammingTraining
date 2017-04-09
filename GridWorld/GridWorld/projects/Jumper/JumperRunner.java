import info.gridworld.actor.*;
import info.gridworld.grid.Location;

import java.awt.Color;

/**
 * This class runs a world that contains a jumper. <br />
 */
public class JumperRunner {
	private JumperRunner() {}
    public static void main(String[] args) {
        ActorWorld world = new ActorWorld();
        Jumper jumper = new Jumper();
        world.add(new Location(4, 0), jumper);
        world.add(new Location(0, 0), new Bug());
        world.add(new Location(3, 0), new Flower());
        world.add(new Location(0, 2), new Rock());
        world.show();
    }
}

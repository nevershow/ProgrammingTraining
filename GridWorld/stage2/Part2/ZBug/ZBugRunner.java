import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;

import java.awt.Color;

/**
 * This class runs a world that contains Z bugs. <br />
 */
public class ZBugRunner {
  private ZBugRunner() {}
  public static void main(String[] args) {
    ActorWorld world = new ActorWorld();
    ZBug alice = new ZBug(9);
    alice.setColor(Color.ORANGE);
    world.add(new Location(0, 0), alice);
    world.show();
  }
}

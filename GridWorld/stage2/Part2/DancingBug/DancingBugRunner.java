import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import java.awt.Color;

/**
 * This class runs a world that contains a Z bug. <br />
 */
public class DancingBugRunner {
  private DancingBugRunner() {}
  public static void main(String[] args) {
    ActorWorld world = new ActorWorld();
    DancingBug alice = new DancingBug(new int[] {2, 3, 1, 0, 4, 3, 0, 1, 2});
    alice.setColor(Color.ORANGE);
    world.add(new Location(4, 0), alice);
    world.show();
  }
}

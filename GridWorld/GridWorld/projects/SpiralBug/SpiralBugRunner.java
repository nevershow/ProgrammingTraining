import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import info.gridworld.grid.UnboundedGrid;
import java.awt.Color;

/**
 * This class runs a world that contains a spiral bug <br />
 */
public class SpiralBugRunner {
  private SpiralBugRunner() {}
  public static void main(String[] args) {
    ActorWorld world = new ActorWorld(new UnboundedGrid<Actor>());
    SpiralBug alice = new SpiralBug(1);
    alice.setColor(Color.ORANGE);
    world.add(new Location(6, 0), alice);
    world.show();
  }
}

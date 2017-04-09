import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

public class QuickCrabRunner
{
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        world.add(new Location(5, 5), new Bug());
        world.add(new Location(4, 4), new Rock());
        world.add(new Location(7, 3), new Rock());
        world.add(new Location(6, 5), new Flower());
        world.add(new Location(5, 2), new Flower());
        world.add(new Location(4, 7), new Flower());
        world.add(new Location(5, 3), new Rock());
        world.add(new Location(4, 5), new QuickCrab());
        world.add(new Location(6, 1), new QuickCrab());
        world.show();
    }
}
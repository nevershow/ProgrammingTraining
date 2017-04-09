import static org.junit.Assert.*;
import org.junit.Test;

import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location; 

public class JumperTest {
	
	@Test
	public void testJumper() {
        ActorWorld world = new ActorWorld();
        Jumper jumper = new Jumper();
        world.add(new Location(4, 0), jumper);
        world.add(new Location(0, 0), new Bug());
        world.add(new Location(3, 0), new Flower());
        world.add(new Location(0, 2), new Rock());
        world.show();

		jumper.act();
		assertEquals(new Location(2, 0),  jumper.getLocation());
		
		jumper.act();
		assertEquals(new Location(0, 0),  jumper.getLocation());
		
		jumper.act();
		assertEquals(90,  jumper.getDirection());
		
		jumper.act();
		assertEquals(135,  jumper.getDirection());
		
		jumper.act();
		assertEquals(new Location(2, 2),  jumper.getLocation());
 	}
}
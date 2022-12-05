/* $Id$ */
package games.stendhal.server.entity.item;

import static org.junit.Assert.assertThat;

import org.junit.Test;

import games.stendhal.client.entity.Player;
import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import utilities.PlayerTestHelper;
import utilities.RPClass.BlockTestHelper;
import utilities.RPClass.ItemTestHelper;

public class handCartsTest {

	//@BeforeClass
	public static void setUpBeforeClass() {
		
		BlockTestHelper.generateRPClasses();
		PlayerTestHelper.generatePlayerRPClasses();
		MockStendlRPWorld.get();
		ItemTestHelper.generateRPClasses();
		Log4J.init();
	}
		
		
		//test if the hand carts create
		//@Test
		public final void testExecute(){
			
			final HandCart hc = new HandCart();
			final StendhalRPZone zone = new StendhalRPZone("zone", 10, 10);
			
			zone.add(hc);
			assertEquals(hc.getX(), 1);
			assertEquals(hc.getY(), 1);
			
			hc.reset();
			assertEquals(hc.getX(), 1);
			assertEquals(hc.getY(), 1);		
			
		}
		
		//test if hand carts move
		public void testForPush() {
			
			HandCart hc = new HandCart();
			StendhalRPZone zone = new StendhalRPZone("zone", 10, 10);
			games.stendhal.server.entity.player.Player player = PlayerTestHelper.createPlayer("user");
			
			hc.setPosition(0,0);
			
			zone.add(hc);
			assertEquals(hc.getX(), 1);
			assertEquals(hc.getY(), 1);
			
			hc.push(player, Direction.RIGHT);
			assertEquals(hc.getX(), 2);
			assertEquals(hc.getY(), 1);
			
			hc.push(player, Direction.LEFT);
			assertEquals(hc.getX(), 0);
			assertEquals(hc.getY(), 1);
			
			hc.push(player, Direction.UP);
			assertEquals(hc.getX(), 1);
			assertEquals(hc.getY(), 2);
			
			hc.push(player,Direction.DOWN);
			assertEquals(hc.getX(), 1);
			assertEquals(hc.getY(), 0);
		}
		
		
		
		
		
		
}



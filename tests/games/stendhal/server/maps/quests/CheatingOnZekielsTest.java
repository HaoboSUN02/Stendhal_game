package games.stendhal.server.maps.quests;

import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.maps.semos.wizardstower.FirstFloorCreatures;
import games.stendhal.server.maps.semos.wizardstower.WizardsGuardStatueNPC;
import games.stendhal.server.maps.semos.wizardstower.WizardsGuardStatueSpireNPC;
import utilities.PlayerTestHelper;
import games.stendhal.server.entity.player.Player;
import utilities.QuestHelper;


public class CheatingOnZekielsTest{
	//getting zone names and objects needed
	private static final String ZONE_NAME = "int_semos_wizards_tower_basement";
	private static final String ZONE_NAME_2 = "int_semos_wizards_tower_1";
	private static final String ZONE_NAME_3 = "int_semos_wizards_tower_7";
	private Engine en = null;
	private Player player = null;
	private SpeakerNPC npc = null;
	


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//quest helper class method to set up before class
		QuestHelper.setUpBeforeClass();
		
	}
	
		@Before
		public void setUp() {
		//get world
		MockStendlRPWorld.get();
		
		//get zone names
		final StendhalRPZone zoneBasement = new StendhalRPZone(ZONE_NAME);
		final StendhalRPZone zoneSpire = new StendhalRPZone(ZONE_NAME_3);
		
		//initialise and configure zones of floors needed for quest logic
		new WizardsGuardStatueNPC().configureZone(zoneBasement, null);
		npc = SingletonRepository.getNPCList().get("Zekiel the guardian");
		new WizardsGuardStatueSpireNPC().configureZone(zoneSpire, null);
		
		
		//get npc engine for FSM add quest to world
		

		
	}
	
	
	 
	@Test
	public void testCheatingZekielsTest() {
		
		final ZekielsPracticalTestQuest quest = new ZekielsPracticalTestQuest();
		
		
		quest.addToWorld();
		en = npc.getEngine();
		
		player = PlayerTestHelper.createPlayer("player");
		
		

		
		//Go through and exhaust initial dialogue steps, FSM needs these gone or trying to set to candles done
		//straight away and trying to say hi and get sent on the stairs just results in quest state being reset
		//to the start 
		en.step(player,"hi");
		assertEquals("Greetings Stranger! I am Zekiel the #guardian.", getReply(npc));
		
		en.step(player, "guardian");
		assertEquals("I watch and guard this #tower, the residence of the #wizards circle.", getReply(npc));
		
		en.step(player, "tower");
		assertEquals("If you want to reach the spire, you have to pass the practical #test.", getReply(npc));
 
		assertTrue(en.step(player, "test"));
		assertTrue(en.step(player, "quest"));
		assertTrue(en.step(player, "bye"));
		
		//set to candles_done 
		player.setQuest("zekiels_practical_test", "candles_done");
		 
		assertTrue(en.step(player, "hi"));
		assertTrue(en.step(player, "start"));
		assertTrue(en.step(player, "send"));
		
		
		//new stendhalrpzone
		final StendhalRPZone zoneFloor1 = new StendhalRPZone(ZONE_NAME_2);
		
		//class for floor 1, need to use configureZone on this as it will replicate the zone where
		//candles are dropped in the bug
		new FirstFloorCreatures().configureZone(zoneFloor1, null);
		
		//add player to zone to drop item
		zoneFloor1.add(player);
		
		//check player definitely in correct zone
		assertEquals(zoneFloor1, player.getZone());
		
		int numItems = zoneFloor1.getItemsOnGround().size();
		
		//drop candle using zone class where player is as we don't need to simulate player dropping from
		//their inventory because if they keep it they would be forced to drop it when going back down 
		//anyway
		Item item = SingletonRepository.getEntityManager().getItem("candle");
		zoneFloor1.add(item, player);
		
		
		//check candle was definitely dropped
		assertEquals(numItems + 1, zoneFloor1.getItemsOnGround().size());
		
		//need to call this function in the quest directly as needs the correct zone as paramater
		//if this isn't done we would get null exceptions as quest tests use mockstendhalrpworld so if we didnt input 
		//that zone then the function would try to use regular stendhalrpworld which has not been generated for the test
		quest.checkForCandles(zoneFloor1);
		
		en.step(player, "hi");
		
		//if candles havent been removed from the ground fail test
		if (zoneFloor1.getItemsOnGround().size() > numItems) {
			fail("candles still on floor 1");
		}
		
		
	}

}

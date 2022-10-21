package games.stendhal.server.maps.semos.wizardstower;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.actions.equip.DropAction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.Item;
import games.stendhal.common.EquipActionConsts;
import marauroa.common.game.RPAction;
import marauroa.common.game.RPObject;


import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;


public class ZekielPracticalCheatTest extends ZonePlayerAndNPCTestImpl {
	
	private static final String ZONE_NAME = "int_semos_wizards_tower_1";
	private static final String ZONE_NAME_2 = "int_semos_wizards_tower_basement";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
		setupZone(ZONE_NAME_2);
	}

	public ZekielPracticalCheatTest() {
//		setNpcNames("Zekiel the Guardian");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new FirstFloorCreatures(), ZONE_NAME);
		addZoneConfigurator(new WizardsGuardStatueNPC(), ZONE_NAME_2);
		
	}
	
//	player.setQuest(GRAFINDLE_QUEST_SLOT, "done");
//	player.setQuest(ZARA_QUEST_SLOT, "done");
	
	

	/**
	 * Tests for hiAndBye.
	 */
	@Test
	public void dropCandleTestIfStillThere() {
		// initialise zekiel and engine for him so we can talk to him after we fail to restart 
		final SpeakerNPC npc = getNPC("Zekiel the guardian");
		final Engine en = npc.getEngine();
		
		//create zone and add player to it
		final StendhalRPZone localzone = new StendhalRPZone(ZONE_NAME, 15, 15);
		final StendhalRPZone localzone2 = new StendhalRPZone(ZONE_NAME_2, 15, 15);
		localzone.add(player);
		
		//check npc engine is working
		assertTrue(en.step(player, "hi"));
		assertEquals("Greetings Stranger! I am Zekiel the #guardian.", getReply(npc));
		
		//set the player to the right quest and step in the quest for the first part of the bug
		player.setQuest("zekiels_practical_test", "second_step");
		
		//record how many items are on the floor
		int itemsOnFloor = localzone.getItemsOnGround().size();
		
		
		//get player to drop item on floor
		Item item = SingletonRepository.getEntityManager().getItem("candle");
		player.equip("bag", item);
		RPAction action = new RPAction();
		action.put("type", "drop");
		List<String> path = Arrays.asList(Integer.toString(player.getID().getObjectID()), "bag", Integer.toString(item.getID().getObjectID()));
		action.put(EquipActionConsts.SOURCE_PATH, path);
		action.put("x",  player.getX());
		action.put("y", player.getY());
		new DropAction().onAction(player, action);
		
		//test if item has been dropped
		assertEquals(itemsOnFloor + 1, localzone.getItemsOnGround().size());
		//get player to go basement zone and check if test has been failed as bug needs to be replicated exactly
//		localzone.remove(player);
//		localzone2.add(player);
//		assertTrue(en.step(player, "hi"));
//		assertEquals("Greetings! You have so far failed the practical test. Tell me, if you want me to #send you on it again " +
//				"right now, or if there is anything you want to #learn about it first.", getReply(npc));
		
	
		
		//put player back to floor 1 and check if candle is still there, if it is, fail test
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	

}

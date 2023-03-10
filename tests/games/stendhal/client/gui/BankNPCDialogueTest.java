package games.stendhal.client.gui;
import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.maps.ados.bank.BankNPC;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;

public class BankNPCDialogueTest {
	
	//Testing the bank npcs in the fado and nalwor bank for bank statements
	//Have to test the third bank npc in a different file, as there are two different
	//bank npcs in different zones both called BankNPC.java, which creates a clash
	//when trying to import them both into the same file 
	
	private static final String EXPECTED_REPLY = "Your items have been added to the bank statement";
	private Engine en = null;
	private Player player = null;
	private SpeakerNPC npc;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
	}
	
	@Before
	public void setUp() {
		MockStendlRPWorld.get();
		final StendhalRPZone adosBank = new StendhalRPZone("int_ados_bank");
		new BankNPC().configureZone(adosBank, null);
		npc = SingletonRepository.getNPCList().get("Rachel");
		en = npc.getEngine();
		
		
	}																																																								

	@Test
	public void testBankStatementNPCDialogue() {
			
		player = PlayerTestHelper.createPlayer("player");
		
		en.step(player, "hi");
		en.step(player, "statements");
		assertEquals(EXPECTED_REPLY, getReply(npc));
		
	}
}
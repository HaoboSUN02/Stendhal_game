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
import games.stendhal.server.maps.nalwor.bank.BankNPC;
import games.stendhal.server.maps.fado.bank.TellerNPC;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;

public class BankStatementTravelLogTest {
	
	//Testing the bank npcs in the fado and nalwor bank for bank statements
	//Have to test the hird bank npc in a different file, as there are two different
	//bank npcs in different zones both called BankNPC.java, which creates a clash
	//when tryig to import them both into the same file 
	
	private static final String EXPECTED_REPLY = "Your items have been added to the bank statement";
	private Engine en = null;
	private Engine en2 = null;
	private Player player = null;
	private SpeakerNPC npc1;
	private SpeakerNPC npc2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
	}
	
	@Before
	public void setUp() {
		MockStendlRPWorld.get();
		final StendhalRPZone nalworBank = new StendhalRPZone("int_nalwor_bank");
		final StendhalRPZone fadoBank = new StendhalRPZone("int_fado_bank");
		new BankNPC().configureZone(nalworBank, null);
		npc1 = SingletonRepository.getNPCList().get("Grafindle");
		new TellerNPC().configureZone(fadoBank, null);
		npc2 = SingletonRepository.getNPCList().get("Yance");
		en = npc1.getEngine();
		en2 = npc2.getEngine();
		
		
	}																																																								

	@Test
	public void testBankStatementNPCDialogue() {
			
		player = PlayerTestHelper.createPlayer("player");
		
		en.step(player, "hi");
		en.step(player, "statements");
		assertEquals(EXPECTED_REPLY, getReply(npc1));
		
		en2.step(player, "hi");
		en2.step(player, "statements");
		assertEquals(EXPECTED_REPLY, getReply(npc2));
		
	}
}


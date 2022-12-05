/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.nalwor.bank;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import games.stendhal.common.Direction;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.player.Player;
//import games.stendhal.server.entity.slot.BankSlot;
import marauroa.common.game.RPObject;
import marauroa.common.game.RPSlot;
import games.stendhal.client.gui.progress.*;

/**
 * Builds the nalwor bank npcs.
 *
 * @author kymara
 */
public class BankNPC implements ZoneConfigurator {
	//
	// ZoneConfigurator
	//

	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildoldNPC(zone);
		buildladyNPC(zone);
	}

	//
	// name inspired by a name in lotr
	// TODO: He complains if someone steals something from his chest: they should be sent to elf jail.

	private void buildoldNPC(final StendhalRPZone zone) {
		final SpeakerNPC oldnpc = new SpeakerNPC("Grafindle") {

			@Override
			protected void createPath() {
				setPath(null);
			}

			@Override
			protected void createDialog() {
				addGreeting("Greetings. If you need #help, please ask.");
				addJob("I work here in the bank.");
				addHelp("That room has two chests owned by this bank and two owned by Semos bank.");
				addGoodbye("Goodbye, young human.");
				//remaining behaviour defined in Take Gold for Grafindle quest
			}
		};

		oldnpc.setDirection(Direction.DOWN);
		oldnpc.setEntityClass("elfbankeroldnpc");
		oldnpc.setDescription("You see Grafindle who works in Nalwor bank.");
		oldnpc.setPosition(13, 17);
		oldnpc.initHP(100);
		zone.add(oldnpc);
	}

	//
	// Ariannyddion is welsh for bank, so ...
	//
	private void buildladyNPC(final StendhalRPZone zone) {
		final SpeakerNPC ladynpc = new SpeakerNPC("Nnyddion") {

			@Override
			protected void createPath() {
				final List<Node> nodes = new LinkedList<Node>();
				nodes.add(new Node(26, 30));
				nodes.add(new Node(16, 30));
				nodes.add(new Node(16, 31));
				nodes.add(new Node(17, 31));
				nodes.add(new Node(17, 30));
				nodes.add(new Node(26, 30));
				setPath(new FixedPath(nodes, true));
			}

			@Override
			protected void createDialog() {
				addGreeting("Welcome to Nalwor Bank. I'm here to #help.");
				addHelp("Customers can deposit their items in the chests in that small room behind me. The two chests on the right are under Semos management.");
				addOffer("I can #help you.");
				addJob("I help customers of the bank, elves and even humans.");
				addQuest("I don't need anything, thank you.");
				addGoodbye("Goodbye, thank you for your time.");
			}
		};

		ladynpc.setDescription("You see a pretty female elf in a beautiful dress.");
		ladynpc.setEntityClass("elfbankladynpc");
		ladynpc.setDirection(Direction.DOWN);
		ladynpc.setPosition(17, 31);
		ladynpc.initHP(100);
		ladynpc.add(ConversationStates.ATTENDING,
		"statements",
		null,
		ConversationStates.ATTENDING,
		"Your items have been added to the bank statement",
		new ChatAction() {
	//new action for to update bank slots page
	@Override
	public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {
		// code to get bank slot information goes here
		RPSlot nalworChest = player.getSlot("bank_nalwor");
		Iterator<RPObject> items = nalworChest.iterator();
		
		
		
		//loop through as long as there is a next object to visit
		while (items.hasNext()) {
			//get object
			RPObject temp = items.next();
			//attempt to get object name 
			String test = temp.getID().toString();
			
			BankPage bankp = new BankPage();
			bankp.setContent(test, test, test, items);
			
			//for in-game testing purposes
			npc.say(test);
			
		}
		
		
		// code to put bank slot information in travel log page for bank statements
		// using progress log controller?
		 ladynpc.say("Your items have been added to the bank statement");
		
		
		
	}
});
		zone.add(ladynpc);
	}
}

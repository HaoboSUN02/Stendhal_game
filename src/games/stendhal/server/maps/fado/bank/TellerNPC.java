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
package games.stendhal.server.maps.fado.bank;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import marauroa.common.game.RPObject;
import marauroa.common.game.RPSlot;

/**
 * Builds the bank teller NPC.
 *
 * @author timothyb89
 */
public class TellerNPC implements ZoneConfigurator {
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
		buildNPC(zone);
	}

	//
	// IL0_TellerNPC
	//

	private void buildNPC(final StendhalRPZone zone) {
		final SpeakerNPC bankNPC = new SpeakerNPC("Yance") {

			@Override
			protected void createPath() {
				final List<Node> nodes = new LinkedList<Node>();
				nodes.add(new Node(15, 3));
				nodes.add(new Node(15, 16));
				setPath(new FixedPath(nodes, true));
			}

			@Override
			protected void createDialog() {
				addGreeting("Welcome to the Fado Bank! Do you need #help?");
				addJob("I am the manager for the bank.");
				addHelp("Just to the left, you can see a few chests. Open one and you can store your belongings in it.");
				addGoodbye("Have a nice day.");
			}
		};

		bankNPC.setEntityClass("youngnpc");
		bankNPC.setPosition(15, 3);
		bankNPC.initHP(1000);
		bankNPC.setDescription("Yance is the Fado bank manager. He can give advice on how to use the chests.");
		//added fsm for Yance to react to statements dialogue and cause bank statement update
		bankNPC.add(ConversationStates.ATTENDING,
				"statements",
				null,
				ConversationStates.ATTENDING,
				"Your items have been added to the bank statement",
				new ChatAction() {
			//new action for to update bank slots page
			@Override
			public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {
				// code to get bank slot information goes here
				RPSlot fadoSlot = player.getSlot("bank_fado");
				Iterator<RPObject> items = fadoSlot.iterator();
				
				
				
				//loop through as long as there is a next object to visit
				while (items.hasNext()) {
					//get object
					RPObject temp = items.next();
					//attempt to get object name 
					String test = temp.getID().toString();
					
					//for in-game testing purposes
					bankNPC.say(test);
					
				}
				
				
				// code to put bank slot information in travel log page for bank statements
				// using progress log controller?
				
				
				
			}
		});
		zone.add(bankNPC);
	}
}


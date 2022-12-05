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

package games.stendhal.server.entity.item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Test;

import games.stendhal.client.entity.Player;
import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import utilities.PlayerTestHelper;
import utilities.RPClass.BlockTestHelper;
import utilities.RPClass.ItemTestHelper;

public class handCartsTest {
	
	private SpeakerNPC npc = null;
	private Engine en = null;

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
	@SuppressWarnings("deprecation") 
	public void testExecute(){
			
			final HandCart hc = new HandCart();
			final StendhalRPZone zone = new StendhalRPZone("zone", 10, 10);
			
			hc.setPositon(1,1);
			
			zone.add(hc);
			assertEquals(hc.getX(), 1);
			assertEquals(hc.getY(), 1);
			
			hc.reset();
			assertEquals(hc.getX(), 1);
			assertEquals(hc.getY(), 1);		
			
	}
		
	//test if hand carts move
	@SuppressWarnings("deprecation")
	public void testForPush() {
			
			final HandCart hc = new HandCart();
			final StendhalRPZone zone = new StendhalRPZone("zone", 10, 10);
			final games.stendhal.server.entity.player.Player player = PlayerTestHelper.createPlayer("user");
			
			hc.setPosition(1,1);
			
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
		
	//test for multiPlayer push the hand cart
	@SuppressWarnings("deprecation")
	public void twoPushPlayer() {
			
			final HandCart hc = new HandCart();
			final StendhalRPZone zone = new StendhalRPZone("zone", 10, 10);
			final games.stendhal.server.entity.player.Player player = PlayerTestHelper.createPlayer("user");
			
			hc.setPosition(1,1);
			//this is to add zone and setting the positions using coordinates
			zone.add(hc);
			assertEquals(hc.getX(), 1);
			assertEquals(hc.getY(), 1);
			
			hc.push(player, Direction.RIGHT);
			assertEquals(hc.getX(), 2);
			assertEquals(hc.getY(), 1);
			
			hc.push(player, Direction.LEFT);
			assertEquals(hc.getX(), 0);
			assertEquals(hc.getY(), 1);
			
			hc.reset();
			
			hc.push(player, Direction.UP);
			assertEquals(hc.getX(), 1);
			assertEquals(hc.getY(), 2);
			
			hc.push(player,Direction.DOWN);
			assertEquals(hc.getX(), 1);
			assertEquals(hc.getY(), 0);
	}
		
	//test hand cart collision when it moves
	@SuppressWarnings("deprecation")
	public void testForCollision() {
			
			final HandCart hc_error = new HandCart();
			final StendhalRPZone zone = new StendhalRPZone("zone", 10, 10);
			final games.stendhal.server.entity.player.Player player = PlayerTestHelper.createPlayer("user");
			
			hc_error.setPosition(1,1);
			
			zone.add(hc_error,false);
			assertEquals(hc_error.getX(), 1);
			assertEquals(hc_error.getY(), 1);
			
			//can move successfully
			hc_error.push(player, Direction.RIGHT);
			assertEquals(hc_error.getX(), 2);
			assertEquals(hc_error.getY(), 1);
			
			hc_error.push(player, Direction.LEFT);
			assertEquals(hc_error.getX(), 0);
			assertEquals(hc_error.getY(), 1);
			
			hc_error.push(player, Direction.UP);
			assertEquals(hc_error.getX(), 1);
			assertEquals(hc_error.getY(), 2);
			
			hc_error.push(player,Direction.DOWN);
			assertEquals(hc_error.getX(), 1);
			assertEquals(hc_error.getY(), 0);
			
			/*add a obstacle(when player try to move hand cart, 
			 * if there are any thing near it, the move will fail.
			 */
			//Obstacle o = new Obstacle();
			//o.setPosition(2,1);
			//zone.add(o, false);
			
			//push wrong, because obstacle
			//hc_error.push(player, Direction.RIGHT);
			//assertFalse(hc_error.getX(2));	
			//assertFalse(hc_error.getY(1));
		
			
	}
	
	public void buyHandCart() {
		
		//we can buy hand cart from who
		npc = SingletonRepository.getNPCList().get("farmer");
		en = npc.getEngine();
		final games.stendhal.server.entity.player.Player player = PlayerTestHelper.createPlayer("user");
		
		en.step(player, "hi");
		assertEquals("What you want to buy?", getReply(npc));
		en.step(player, "Hand cart");
		assertEquals("What type of hand carts you want? 1. owenr $100; 2. pubilc $50", getReply(npc));
		en.step(player, "1");
		assertEquals("$100, do you buy?", getReply(npc));
		en.step(player, "2");
		assertEquals("$50, do you buy?", getReply(npc));
		en.step(player, "yes");
		assertEquals("Give you, thanks for your purchasing", getReply(npc));
		en.step(player, "bye");
		assertEquals("Bye, have a good day.", getReply(npc));
		
		
		}
	}



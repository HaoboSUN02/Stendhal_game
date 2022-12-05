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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import games.stendhal.client.entity.RPEntity;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import utilities.PlayerTestHelper;
import utilities.RPClass.BlockTestHelper;
import utilities.RPClass.ItemTestHelper;

public class handCartsTestChest {
	
	    //@BeforeClass
		public static void setUpBeforeClass() {
			
			BlockTestHelper.generateRPClasses();
			PlayerTestHelper.generatePlayerRPClasses();
			MockStendlRPWorld.get();
			ItemTestHelper.generateRPClasses();
			Log4J.init();
		}
		
		// this is for checking if the hand cart open or close
		//@Test
		public void testForOpen() {
			
			HandCart hc = new HandCart();
			
			//for the begin,when the hand cart is closed
			hc.close();
			assertFalse(hc.isOpen());
			
			//for opening the hand cart
			hc.isOpen();
			assertTrue(hc.isOpen());
			
			//for closing the hand cart
			hc.close();
			assertFalse(hc.isOpen());
			
		}
		
		//this is for when hand carts move, the hand carts should close
		public void testForMoveClose() {
			
			final HandCart hc = new HandCart();
			final games.stendhal.server.entity.player.Player player = PlayerTestHelper.createPlayer("user");
			
			assertTrue(hc.isOpen());
			
			//when hand cart is used by player, hand carts which is opened is wrong
			hc.onUsed();
			assertFalse(hc.isOpen());
			
			
			
		}
		
		
}

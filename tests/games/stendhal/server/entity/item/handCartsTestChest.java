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
		
		//test if the hand cart open or close
		//@Test
		public void testForOpen() {
			
			HandCart hc = new HandCart();
			
			//at the begin, the hand cart is closed
			assertFalse(hc.isOpen());
			
			//open the hand cart
			hc.open();
			aseertTrue(hc.isOpen());
			
			//after that close the hand cart
			hc.close();
			assertFalse(hc.isOpen());
			
		}
		
		//test when hand carts move, the hand carts should close
		//public void testForMoveClose() {
			
			
		//}
		
		
}

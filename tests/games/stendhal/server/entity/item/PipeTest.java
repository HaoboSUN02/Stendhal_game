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

//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertThat;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
//import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

//import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.entity.creature.*;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.rp.StendhalRPAction;

import marauroa.common.Log4J;
import utilities.RPClass.CreatureTestHelper;
import utilities.RPClass.ItemTestHelper;

public class PipeTest {
	//private Player player = null;
	private Creature noob_creature=null;
	private static StendhalRPZone playerzone;
	private static int xpos = 10;
	private static int ypos = 10;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Log4J.init();
		MockStendlRPWorld.get();
		ItemTestHelper.generateRPClasses();

	}
	
//	Set Up to the conditions required to check feature 
//	@Before
	public void setUp() // Took inspirations from FruitsForCoraliaTest.java
	{
//		PlayerTestHelper.generateCreatureRPClasses();
	
		//final Player noob_player = PlayerTestHelper.createPlayer("player");
//		player = PlayerTestHelper.createPlayer("player");
		//PlayerTestHelper.registerPlayer(noob_player);
		
		
		playerzone = new StendhalRPZone("int_semos_guard_house",100,100);
		SingletonRepository.getRPWorld().addRPZone(playerzone);


		CreatureTestHelper.generateRPClasses();
		noob_creature = SingletonRepository.getEntityManager().getCreature("skeleton");
		StendhalRPAction.placeat(playerzone, noob_creature, xpos+2, ypos+1);
		
	}
	
	
	
	
	
	@Test
	public void isPipeCreated() throws Exception // Throws an error if it is not able to create the pipe
		{
			
			String name="pipe";
			String clazz ="";
			String subclass="";
			Map<String,String> attributes =new HashMap<String,String>();
			new Pipe(name,clazz,subclass,attributes); // As it extends class item
			
		}
	
	@Test
	public void isPipeinBag() throws Exception
	{
		
		//setUp();
		final Player noob_player = PlayerTestHelper.createPlayer("bob");
		String name="pipe";
		String clazz ="";
		String subclass="";
		Map<String,String> attributes =new HashMap<String,String>();
		Pipe noob_pipe= new Pipe(name,clazz,subclass,attributes);
		noob_player.equip("bag",noob_pipe);
	}
	@Test
	public void isPipeinLeftHand() throws Exception
	{
		
		//setUp();
		final Player noob_player = PlayerTestHelper.createPlayer("bob");
		String name="pipe";
		String clazz ="";
		String subclass="";
		Map<String,String> attributes =new HashMap<String,String>();
		Pipe noob_pipe= new Pipe(name,clazz,subclass,attributes);
		noob_player.equip("bag",noob_pipe);
		noob_player.equip("lhand", noob_pipe);
	}
	@Test
	public void isPipeinRightHand() throws Exception
	{
		
		//setUp();
		final Player noob_player = PlayerTestHelper.createPlayer("bob");
		String name="pipe";
		String clazz ="";
		String subclass="";
		Map<String,String> attributes =new HashMap<String,String>();
		Pipe noob_pipe= new Pipe(name,clazz,subclass,attributes);
		noob_player.equip("bag",noob_pipe);
		noob_player.equip("rhand", noob_pipe);
//		assertTrue(player.isEquipped("pipe of charming"));
	}
//	@Test
	public void isPipeWorkingOnBeingInBagOrInEitherHands() throws Exception
	{
		
		setUp();
		

		
		Player noob_player = PlayerTestHelper.createPlayer("bob");
		noob_player.teleport(playerzone, xpos, ypos, null, noob_player);
		noob_creature.setTarget(noob_player);
		String name="pipe";
		String clazz ="";
		String subclass="";
		Map<String,String> attributes =new HashMap<String,String>();
		Pipe noob_pipe= new Pipe(name,clazz,subclass,attributes);
		noob_player.equip("bag",noob_pipe);
		noob_player.equip("rhand", noob_pipe);
		assertEquals(noob_player.isAttacked(), false);
		
//		assertTrue(player.isEquipped("pipe of charming"));
	}



	



	

}
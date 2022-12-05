package games.stendhal.server.entity.item;

import games.stendhal.client.entity.Chest;
import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.events.MovementListener;
import games.stendhal.server.core.events.TurnListener;
import games.stendhal.server.core.events.ZoneEnterExitListener;
import games.stendhal.server.entity.ActiveEntity;
import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPObject;

public class HandCart extends Chest implements ZoneEnterExitListener,
MovementListener, TurnListener {
	
	public HandCart() {
	
		
	}

	@Override
	public void onTurnReached(int currentTurn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntered(ActiveEntity entity, StendhalRPZone zone, int newX, int newY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExited(ActiveEntity entity, StendhalRPZone zone, int oldX, int oldY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeMove(ActiveEntity entity, StendhalRPZone zone, int oldX, int oldY, int newX, int newY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMoved(ActiveEntity entity, StendhalRPZone zone, int oldX, int oldY, int newX, int newY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntered(RPObject object, StendhalRPZone zone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExited(RPObject object, StendhalRPZone zone) {
		// TODO Auto-generated method stub
		
	}
	

	public void close() {
		// TODO Auto-generated method stub
		
	}

	public void onUsed() {
		// TODO Auto-generated method stub
		
	}

	public void push(Player player, Direction down) {
		// TODO Auto-generated method stub
		
	}

	public void setPositon(int i, int j) {
		// TODO Auto-generated method stub
		
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}

	public void setPosition(int i, int j) {
		// TODO Auto-generated method stub
		
	}

}


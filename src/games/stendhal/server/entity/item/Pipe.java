package games.stendhal.server.entity.item;

import java.util.Map;

import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.player.Player;

public class Pipe extends Item {
	public Pipe(final String name, final String clazz, final String subclass, final Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);
}
	public Pipe(final Pipe item) {
		super(item);
	}
	
	public Pipe() {
		super("pipe of charm", "ring", "pipe_of_charm", null);
		put("amount", 1);
	}
	
	public boolean isBroken() {
		return  getInt("amount") == 0;
	}

	
	public void damage() {
		put("amount", 0);
	}

	@Override
	public void repair() {
		put("amount", 1);
	}

	public String describe() {
		String text;
		if (isBroken()) {
			text = "You see an §'emerald ring', known as the ring of life. The gleam is lost from the stone and it has no powers.";
		} else {
			text = "You see an §'emerald ring', known as the ring of life. Wear it, and you risk less from death.";
		}

		if (isBound()) {
			text = text + " It is a special quest reward for " + getBoundTo()
					+ ", and cannot be used by others.";
		}
		return text;
	}

	/**
	 * Notify the player that it is not required to carry this ring
	 * in the finger slot to get its benefits.
	 */
	@Override
	public boolean onEquipped(final RPEntity entity, final String slot) {
		if ((slot.equals("rhand")||slot.equals("lhand"))&& entity instanceof Player) {
			((Player) entity).sendPrivateText(
				"The aura of the ring is unchanged as you slide it on your finger."
				+ " You realize that even carrying it in your bag or on your keyring"
				+ " you will be under its influence.");
		}

		return super.onEquipped(entity, slot);
	}
	
	
}

package games.stendhal.server.entity.item;

import java.util.Map;



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
}
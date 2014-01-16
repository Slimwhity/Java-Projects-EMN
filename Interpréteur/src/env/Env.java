package env;

import java.util.HashMap;

@SuppressWarnings("serial")
public class Env<E> extends HashMap<String, E> {
	
	// returns the value of a variable in the environment
	public E lookup(String var) {
		return get(var);
	}
	// binds a variable in the environment
	public void bind(String var, E value) {
		put(var, value);
	}
}


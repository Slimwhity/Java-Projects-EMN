package parser;


public class Literal extends Expression {
	protected int value;
	
	public Literal(int value) {
		this.value = value;
	}
	
	public String toString(String offset) {
		return this.getClass().getSimpleName() + '(' + Integer.toString(value) + ')';
	}
	
	public int eval(Env<Integer> envVar, Env<Function> envFunc) {
		// retourne l'entier associé au litéral
		return this.value;
	}
}

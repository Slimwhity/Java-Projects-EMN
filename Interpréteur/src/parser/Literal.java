package parser;

public class Literal extends Expression {
	protected int value;
	
	public Literal(int value) {
		this.value = value;
	}
	
	public String toString() {
		return super.toString() + '(' + Integer.toString(value) + ')';
	}
	
	public int eval() {
		return this.value;
	}
}

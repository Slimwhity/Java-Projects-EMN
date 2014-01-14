package parser;

public class Variable extends Expression {
	protected String name;
	
	public Variable(String name) {
		this.name = name;
	}
	
	public String toString() {
		return super.toString() + "(\"" + this.name + "\")";
	}
}

package parser;

public class Variable extends Expression {
	protected String name;
	
	public Variable(String name) {
		this.name = name;
	}
	
	public String toString(String offset) {
		return this.getClass().getSimpleName() + "(\"" + name + "\")";
	}

	@Override
	public int eval(Env<Integer> envVar) throws EvaluationError {
		return envVar.lookup(name);
	}
	
	
}

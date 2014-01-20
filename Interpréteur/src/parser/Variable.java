package parser;

public class Variable extends Expression {
	protected String identifier;
	
	public Variable(String name) {
		this.identifier = name;
	}
	
	public String toString(String offset) {
		return this.getClass().getSimpleName() + "(\"" + identifier + "\")";
	}

	@Override
	public int eval(Env<Integer> envVar, Env<Function> envFunc) throws EvaluationError {
		return envVar.lookup(identifier);
	}
	
	
}

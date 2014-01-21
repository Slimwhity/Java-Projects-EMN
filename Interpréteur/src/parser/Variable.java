package parser;

import errors.EvaluationError;

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
		// Retourne l'entier associé à la variable dans l'environnement de variables
		return envVar.lookup(identifier);
	}
	
	
}

package parser;

import errors.EvaluationError;

public class UnaryMinus extends Expression {
	protected Expression exp;
	
	public UnaryMinus(Expression exp) {
		this.exp = exp;
	}
	
	public String toString(String offset) {
		offset += align();
		return this.getClass().getSimpleName() + '(' + exp.toString(offset) + ')';
	}

	@Override
	public int eval(Env<Integer> envVar, Env<Function> envFunc) throws EvaluationError {
		// retourne l'opposé de l'evaluation de l'expression
		return - exp.eval(envVar, envFunc);
	}

}

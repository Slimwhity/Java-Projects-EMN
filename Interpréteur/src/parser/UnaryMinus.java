package parser;

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
	public int eval(Env<Integer> envVar) throws EvaluationError {
		return - exp.eval(envVar);
	}

}

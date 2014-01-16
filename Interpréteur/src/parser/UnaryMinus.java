package parser;

public class UnaryMinus extends Expression {
	protected Expression exp;
	
	public UnaryMinus(Expression exp) {
		this.exp = exp;
	}
	
	public String toString() {
		return super.toString() + '(' + exp + ')';
	}

	@Override
	public int eval() throws EvaluationError {
		return - exp.eval();
	}

}

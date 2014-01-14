package parser;


public class ConditionnalExpression extends Expression {
	protected Expression exp1;
	protected Expression exp2;
	protected Expression exp3;
	
	public ConditionnalExpression(Expression exp1, Expression exp2, Expression exp3) {
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.exp3 = exp3;
	}
	
	public String toString() {
		return super.toString() + '(' + exp1 + ", " + exp2 + ", " + exp3 + ')';
	}
}

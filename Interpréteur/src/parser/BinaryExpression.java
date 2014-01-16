package parser;
import java.io.IOException;

import lexer.Rpar;
import lexer.SLexer;
import lexer.OPERAND.OP;
import lexer.UnexpectedCharacter;

public class BinaryExpression extends Expression {
	private OP operand;
	private Expression exp1;
	private Expression exp2;
	
	public BinaryExpression(OP operand, Expression exp1, Expression exp2) {
		this.operand = operand;
		this.exp1 = exp1;
		this.exp2 = exp2;
	}
	
	public void parse_() throws IOException, UnexpectedCharacter {
		if (!(SLexer.getToken() instanceof Rpar)) {
			throw new UnexpectedCharacter(0);
		}
	}
	
	public String toString() {
		return super.toString() + '(' + operand + ',' + exp1 + ',' + exp2 + ')';
	}

	@Override
	public int eval() throws EvaluationError {
		int val1 = exp1.eval();
		int val2 = exp2.eval();
		switch (operand) {
		case PLUS :  return val1 + val2;
		case DIV:
			if (val2 == 0) throw new EvaluationError("Divide by zero in binary expression");
			return val1 / val2;
		case EQUALS : return val1 == val2 ? 1 : 0;
		case LOWER: return val1 < val2 ? 1 : 0;
		case MINUS: return val1 - val2;
		case TIMES: return val1 * val2;
		}
		return 0;
	}
	
	
}

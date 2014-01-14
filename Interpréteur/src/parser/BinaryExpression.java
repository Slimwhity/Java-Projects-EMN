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
}

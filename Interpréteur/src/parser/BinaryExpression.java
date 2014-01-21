package parser;
import java.io.IOException;

import errors.EvaluationError;
import errors.SyntacticError;
import errors.UnexpectedCharacter;
import lexer.Rpar;
import lexer.SLexer;
import lexer.Token;
import lexer.OPERAND.OP;

public class BinaryExpression extends Expression {
	private OP operand;
	private Expression exp1;
	private Expression exp2;
	
	public BinaryExpression(OP operand, Expression exp1, Expression exp2) {
		this.operand = operand;
		this.exp1 = exp1;
		this.exp2 = exp2;
	}
	
	public BinaryExpression(OP operateur) {
		this.operand = operateur;
	}

	public void parse_() throws IOException, UnexpectedCharacter {
		// On parse la première expression
		exp1 = Expression.parse(SLexer.getToken());
		Token nextToken = SLexer.getToken();
		if (nextToken instanceof Rpar) {
			throw new SyntacticError("Binary expression : unexpected right parenthesis");
		}
		// Puis la deuxième
		exp2 = Expression.parse(nextToken);
		nextToken = SLexer.getToken();
		if (!(nextToken instanceof Rpar)) {
			throw new SyntacticError("Binary expression : missing right parenthesis");
		}
	}
	
	public String toString(String offset) {
		offset += align();
		return  this.getClass().getSimpleName() + '(' + operand + ',' +
				'\n' + offset + exp1.toString(offset) + ',' +
				'\n' + offset + exp2.toString(offset) + ')';
	}
	
	
	/*
	 * Retourne l'évaluation de l'expression binaire.
	 * Voir les différents comportements en fonction de l'Operand.
	 * @see parser.Expression#eval(parser.Env, parser.Env)
	 */
	@Override
	public int eval(Env<Integer> envVar, Env<Function> envFunc) throws EvaluationError {
		int val1 = exp1.eval(envVar, envFunc);
		int val2 = exp2.eval(envVar, envFunc);
		switch (operand) {
		case PLUS :  return val1 + val2;
		case DIV:
			if (val2 == 0) throw new EvaluationError("Divide by zero in binary expression");
			return val1 / val2;
		case EQUALS : return val1 == val2 ? 1 : 0;
		case LOWER: return val1 < val2 ? 1 : 0;
		case MINUS: return val1 - val2;
		case TIMES: return val1 * val2;
		default : throw new EvaluationError("BynaryExpression error : undefined operator");
		}
	}
	
	
}

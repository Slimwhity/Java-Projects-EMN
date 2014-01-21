package parser;

import java.io.IOException;

import errors.EvaluationError;
import errors.SyntacticError;
import errors.UnexpectedCharacter;
import lexer.Rpar;
import lexer.SLexer;
import lexer.Token;

public class ConditionnalExpression extends Expression {
	protected Expression exp1;
	protected Expression exp2;
	protected Expression exp3;
	
	public ConditionnalExpression() {}
	
	public void parse_() throws SyntacticError, IOException, UnexpectedCharacter {
		// On lit successivement les 3 expression en vérifiant qu'aucune Rpar ne vient interferer.
		exp1 = parse(SLexer.getToken());
		Token nextToken = SLexer.getToken();
		if (nextToken instanceof Rpar) throw new SyntacticError("Conditional expression : unexpected right parenthesis");
		exp2 = parse(nextToken);
		nextToken = SLexer.getToken();
		if (nextToken instanceof Rpar) throw new SyntacticError("Conditional expression : unexpected right parenthesis");
		exp3 = parse(nextToken);
		if (!(SLexer.getToken() instanceof Rpar)) {
			throw new SyntacticError("Conditional expression : missing right parenthesis");
		}
	}

	public String toString(String offset) {
		offset += align();
		return this.getClass().getSimpleName() + '(' + exp1.toString(offset) + ", " +
				'\n' + offset + exp2.toString(offset) + ',' +
				'\n' + offset + exp3.toString(offset) + ')';
	}

	@Override
	public int eval(Env<Integer> envVar, Env<Function> envFunc) throws EvaluationError {
		// Retourne exp2 si exp1 != 0, exp3 sinon
		return exp1.eval(envVar, envFunc) != 0 ? exp2.eval(envVar, envFunc) : exp3.eval(envVar, envFunc);
	}
}

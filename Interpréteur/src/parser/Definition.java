package parser;

import java.io.IOException;

import lexer.SLexer;
import lexer.UnexpectedCharacter;

public class Definition extends AST {
	private Variable variable;
	private Expression exp;
	
	public Definition() {}

	protected void parse() throws SyntacticError, IOException, UnexpectedCharacter {
		Expression var = Expression.parse(SLexer.getToken());
		// On lit une variable puis une expression
		if (var instanceof Variable) {
			Expression exp = Expression.parse(SLexer.getToken());
			this.variable = (Variable) var;
			this.exp = exp;
			if (!(SLexer.getToken() instanceof lexer.Rpar)) {
				throw new SyntacticError("Paranthèse droite manquante");
			}
		} else throw new SyntacticError("Définition variable : l'expression lue n'est pas une variable");
	}
	
	public void eval(Env<Integer> envVar, Env<Function> envFunc) throws EvaluationError {
		envVar.bind(variable.identifier, exp.eval(envVar, envFunc));
	}

	public String toString() {
		return "Definition(" + variable + ", " + exp + ')'; 
	}

	@Override
	public String toString(String offset) {
		offset += align();
		return "Definition(" + variable.toString(offset) + ',' + "\n" + offset + exp.toString(offset) + ')';
	}
	
}

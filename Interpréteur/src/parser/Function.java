package parser;

import java.io.IOException;
import java.util.List;

import lexer.Rpar;
import lexer.SLexer;
import lexer.UnexpectedCharacter;

public class Function extends AST {
	/*
	 * Function ::= '(' 'define' Head body ')' 
	 */
	
	private Head head;
	private Body body;
	
	public Function() {
		head = new Head();
		body = new Body();
	}
	
	@Override
	public String toString(String offset) {
		offset += align();
		return "Function(\n" +
				offset + head.toString(offset) + ",\n" +
				offset + body.toString(offset) + '\n' +
				offset + ')';
	}

	public void parse() throws IOException, UnexpectedCharacter {
		head.parse();
		body.parse();
		if (!(SLexer.getToken() instanceof Rpar)) throw new SyntacticError("Paranthèse droite manquante définition de la fonction " + head.funcName);
		
	}
	
	public void eval(Env<Integer> envVar, Env<Function> envFunc) {
		envFunc.bind(head.funcName, this);
	}
	
	public int eval(List<Integer> params, Env<Function> envFunc) throws EvaluationError {
		// Appel à eval de Head pour constituer l'environnement local à la fonction
		return body.eval(head.eval(params), envFunc);
	}
 	

}

package parser;

import java.io.IOException;

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
		// TODO Auto-generated method stub
		return null;
	}

	public void parse() throws IOException, UnexpectedCharacter {
		head.parse();
		body.parse();
		if (!(SLexer.getToken() instanceof Rpar)) throw new SyntacticError("Paranthèse droite manquante définition de la fonction " + head.funcName);
		
	}

}

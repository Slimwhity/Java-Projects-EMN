package parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lexer.KWDEFINE;
import lexer.Lpar;
import lexer.Rpar;
import lexer.SLexer;
import lexer.Token;
import lexer.UnexpectedCharacter;

public class Program extends AST {
	/* 
	 * Program ::= Function* Body
	 */
	private List<Function> functions;
	private Body body;
	
	public Program() {
		this.functions = new ArrayList<Function>();
	}
	
	public void parse() throws IOException, UnexpectedCharacter {
		boolean isFunction = true;
		while (isFunction) {
			Token firstToken = SLexer.getToken();
			if (firstToken instanceof Lpar) {
				Token secondToken = SLexer.getToken();
				if (secondToken instanceof KWDEFINE) {
					Function newFunction = new Function();
					newFunction.parse();
					functions.add(newFunction);
					if (!(SLexer.getToken() instanceof Rpar)) throw new SyntacticError("Paranthèse droite manquante");  
				} else {
					isFunction = false;
					body = new Body();
					body.parse();
				}
			}
		}
 	}
	
	@Override
	public String toString(String offset) {
		// TODO Auto-generated method stub
		return null;
	}

}

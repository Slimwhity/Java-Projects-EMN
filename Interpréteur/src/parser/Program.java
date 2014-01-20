package parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lexer.Equal;
import lexer.KWDEFINE;
import lexer.Lpar;
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
					//if (!(SLexer.getToken() instanceof Rpar)) throw new SyntacticError("Paranthèse droite manquante");  
				} else if (secondToken instanceof Equal){
					isFunction = false;
					body = new Body();
					Definition def = new Definition();
					def.parse();
					body.definitions.add(def);
					body.parse();
				} else {
					body = new Body();
					body.exp = Expression.parseLpar(secondToken);
					isFunction = false;
				}
			} else {
				body = new Body();
				body.exp = Expression.parse(firstToken);
				isFunction = false;
			}
		}
 	}
	
	@Override
	public String toString(String offset) {
		offset += align();
		String s = "Program("; 
		for (Function func : functions) s += "\n" + offset + func.toString(offset);
		s+= "\n\n" + offset + body.toString(offset);
		return s + "\n)";
	}

	public int eval(Env<Integer> envVar, Env<Function> envFunc) throws EvaluationError {
		for (Function func : functions) func.eval(envVar, envFunc);
		return body.eval(envVar, envFunc);
		
	}

}

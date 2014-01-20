package parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lexer.SLexer;
import lexer.Token;
import lexer.UnexpectedCharacter;

public class Body extends AST {
	protected List<Definition> definitions;
	protected Expression exp;
	
	public Body() {
		definitions = new ArrayList<Definition>();
	}
	
	public void parse() throws IOException, UnexpectedCharacter {
		/*
		 * Syntaxe d'une définition : (= Variable Expression)
		 * Body ::= Definition* Expression  
		 */
		boolean isDefinition = true;
		while (isDefinition) {
			// Boucle pour lire toutes les définitions
			Token lpar = SLexer.getToken();
			// Le premier Token lu doit être une LPar
			if (lpar instanceof lexer.Lpar) {
				Token second = SLexer.getToken();
				// Le second token est un Equal
				if (second instanceof lexer.Equal) {
					Definition newDef = new Definition();
					newDef.parse();
					definitions.add(newDef);
				} else {
					exp = Expression.parseLpar(second);
					isDefinition = false;
				}
			}
			else {
				exp = Expression.parse(lpar);
				isDefinition = false;
			}
		}
	}
	
	public String toString(String offset) {
		offset += align();
		String s = "Body("; 
		for (Definition def : definitions) s += "\n" + offset + def.toString(offset);
		s+= "\n\n" + offset + exp.toString(offset) + '\n' + offset + ')';
		return s;
	}
	
	public int eval(Env<Integer> envVar, Env<Function> envFunc) throws EvaluationError {
		for (Definition def : definitions) {
			def.eval(envVar, envFunc);
		}
		return exp.eval(envVar, envFunc);
	}

}

package parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import errors.EvaluationError;
import errors.UnexpectedCharacter;
import lexer.SLexer;
import lexer.Token;

public class Body extends AST {
	protected List<Definition> definitions;
	protected Expression exp;

	public Body() {
		definitions = new ArrayList<Definition>();
	}

	public void parse() throws IOException, UnexpectedCharacter {
		/*
		 * Syntaxe d'une définition : '(' Equal Variable Expression ')' Body ::= Definition* Expression
		 * Les erreurs sont traitées par Definition et
		 * Expression.
		 */
		Token firstToken = SLexer.getToken();
		// Le premier Token lu doit être une LPar
		if (firstToken instanceof lexer.Lpar) {
			Token second = SLexer.getToken();
			// Le second token est un Equal
			if (second instanceof lexer.Equal) {
				// On créé une nouvelle définition et on l'ajoute à la liste.
				Definition newDef = new Definition();
				newDef.parse();
				definitions.add(newDef);
				// Appel récursif à parse() pour continuer à lire des
				// Definitions.
				parse();
			} else {
				// S'il ne s'agit pas d'une définition, alors on est entrain de
				// lire l'expression du corps
				exp = Expression.parseAfterLpar(second);
			}
		} else {
			// Cas ou le corps est un litéral ou une variable.
			exp = Expression.parse(firstToken);
		}
	}

	public String toString(String offset) {
		offset += align();
		String s = "Body(";
		for (Definition def : definitions)
			s += "\n" + offset + def.toString(offset);
		s += "\n\n" + offset + exp.toString(offset) + '\n' + offset + ')';
		return s;
	}
	
	/*
	 * Les définitions sont d'abord evaluées, ce qui se traduit par leur ajout à l'environnement de variables.
	 * Le corps est ensuite evalué via l'appel à exp.eval().
	 */
	public int eval(Env<Integer> envVar, Env<Function> envFunc)
			throws EvaluationError {
		for (Definition def : definitions) {
			def.eval(envVar, envFunc);
		}
		return exp.eval(envVar, envFunc);
	}

}

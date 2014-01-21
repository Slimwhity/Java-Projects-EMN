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
		 * Syntaxe d'une d�finition : '(' Equal Variable Expression ')' Body ::= Definition* Expression
		 * Les erreurs sont trait�es par Definition et
		 * Expression.
		 */
		Token firstToken = SLexer.getToken();
		// Le premier Token lu doit �tre une LPar
		if (firstToken instanceof lexer.Lpar) {
			Token second = SLexer.getToken();
			// Le second token est un Equal
			if (second instanceof lexer.Equal) {
				// On cr�� une nouvelle d�finition et on l'ajoute � la liste.
				Definition newDef = new Definition();
				newDef.parse();
				definitions.add(newDef);
				// Appel r�cursif � parse() pour continuer � lire des
				// Definitions.
				parse();
			} else {
				// S'il ne s'agit pas d'une d�finition, alors on est entrain de
				// lire l'expression du corps
				exp = Expression.parseAfterLpar(second);
			}
		} else {
			// Cas ou le corps est un lit�ral ou une variable.
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
	 * Les d�finitions sont d'abord evalu�es, ce qui se traduit par leur ajout � l'environnement de variables.
	 * Le corps est ensuite evalu� via l'appel � exp.eval().
	 */
	public int eval(Env<Integer> envVar, Env<Function> envFunc)
			throws EvaluationError {
		for (Definition def : definitions) {
			def.eval(envVar, envFunc);
		}
		return exp.eval(envVar, envFunc);
	}

}

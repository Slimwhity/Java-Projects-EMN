package parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lexer.EOF;
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
		// Construit le programe principal. Un fois le corps parse, la fonction attend un token EOF.
		Token firstToken = SLexer.getToken();
		if (firstToken instanceof Lpar) {
			Token secondToken = SLexer.getToken();
			if (secondToken instanceof KWDEFINE) {
				/*
				 * Cas Function Function ::= '(' define head body ')' Donc dès
				 * qu'on a lu un KWDEFINE, on sait qu'on a reconnu une fonction
				 * On délègue le traitement à la fonction.
				 */
				Function newFunction = new Function();
				newFunction.parse();
				functions.add(newFunction);
				parse();
			} else if (secondToken instanceof Equal) {
				/*
				 * Cas Définition Definition ::= '(' '=' Variable Expression ')'
				 * On traite immédiatement la définition, car on a avancé dans
				 * le fichier à la lecture de firstToken. On construit le Body
				 * et on y ajoute la définition, puis on délègue le parse de
				 * body à Body.
				 */
				body = new Body();
				Definition def = new Definition();
				def.parse();
				body.definitions.add(def);
				body.parse();
				if (!(SLexer.getToken() instanceof EOF)) throw new SyntacticError("Expression parsing error : EOF expected, found token");
			} else {
				/*
				 * Cas d'un programme sans fonction et sans définition. On
				 * construit donc le body et on lui délègue le parse.
				 */
				body = new Body();
				body.exp = Expression.parseAfterLpar(secondToken);
				if (!(SLexer.getToken() instanceof EOF)) throw new SyntacticError("Expression parsing error : EOF expected, found token");
			}
		} else {
			/*
			 * Si le 1er token n'est pas une Lpar, on est dans un programme avec
			 * seulement un identifiant ou un literal => on passe le traitement
			 * à Expression
			 */
			body = new Body();
			body.exp = Expression.parse(firstToken);
			if (!(SLexer.getToken() instanceof EOF)) throw new SyntacticError("Expression parsing error : EOF expected, found token");
		}
	}

	@Override
	public String toString(String offset) {
		offset += align();
		String s = "Program(";
		for (Function func : functions)
			s += "\n" + offset + func.toString(offset);
		s += "\n\n" + offset + body.toString(offset);
		return s + "\n)";
	}

	/*
	 * Methode principale d'évalutation du programme. Elle lie d'abord les
	 * fonctions déclarées dans le programme avec l'environnement de fonctions
	 * ... ... Puis evalue le corps du programme.
	 */
	public int eval(Env<Integer> envVar, Env<Function> envFunc)
			throws EvaluationError {
		for (Function func : functions)
			func.eval(envFunc);
		return body.eval(envVar, envFunc);

	}

}

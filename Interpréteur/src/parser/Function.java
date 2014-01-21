package parser;

import java.io.IOException;
import java.util.List;

import errors.EvaluationError;
import errors.SyntacticError;
import errors.UnexpectedCharacter;
import lexer.Rpar;
import lexer.SLexer;

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
		// On d�l�gue directement les traitements � Head et � body.
		head.parse();
		body.parse();
		// Il faut simplement v�rifier que la d�finition de la fonction se termine bien par un Rpar.
		if (!(SLexer.getToken() instanceof Rpar)) throw new SyntacticError("Paranth�se droite manquante d�finition de la fonction " + head.funcName);
		
	}
	
	// Distinguo entre evaluation d'un appel et d'une d�finition de fonction
	public void eval(Env<Function> envFunc) {
		// Ici on �value la fonction, ce qui se concr�tise par son ajout dans l'environnement de fonctions...
		envFunc.bind(head.funcName, this);
	}
	
	public int eval(List<Integer> params, Env<Function> envFunc) throws EvaluationError {
		// ... Ici on �value son appel, via eval de Head pour constituer l'environnement local � la fonction
		Env<Integer> localEnv = head.eval(params);
		return body.eval(localEnv, envFunc);
	}
 	

}

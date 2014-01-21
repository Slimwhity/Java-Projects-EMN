package parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import errors.EvaluationError;
import errors.SyntacticError;
import errors.UnexpectedCharacter;
import lexer.IDENTIFIER;
import lexer.Lpar;
import lexer.Rpar;
import lexer.SLexer;
import lexer.Token;

public class Head extends AST {
	/*
	 * Head ::= '(' FUNCTION VARIABLE* ')'
	 */
	protected String funcName;
	protected List<Variable> args;
	
	public Head() {
		this.args = new ArrayList<Variable>();
	}
	
	@Override
	public String toString(String offset) {
		offset += align();
		if (args.isEmpty()) return "Head(" + funcName + ')';
		else {
			String s = "Head(" + funcName + ", ";
			for (Variable arg : args) {
				s+= arg.toString(offset) + " ";
			}
			return s + ')';
		}
	}

	public void parse() throws IOException, UnexpectedCharacter {
		Token firstToken = SLexer.getToken();
		// Le 1er token doit �tre une Lpar
		if (firstToken instanceof Lpar) {
			Token secondToken = SLexer.getToken();
			// Le token suivant est l'identiant de la fonction
			if (secondToken instanceof IDENTIFIER) {
				funcName = ((IDENTIFIER) secondToken).value;
			} else throw new SyntacticError("Function definition : missing identifier");
			/*
			 * On va ensuite parser tous les arguments de la fonction
			 * Les variables doivent �tre instance d'IDENTIFIER
			 * La chaine de variables doit se terminer par une Rpar.
			 */
			boolean isVariable = true;
			Token nextToken;
			while (isVariable) {
				nextToken = SLexer.getToken();
				if (nextToken instanceof IDENTIFIER) {
					// On a identifi� une Variable, on l'ajoute � la liste des arguments
					Variable var = new Variable(((IDENTIFIER) nextToken).value);
					args.add(var);
				} else if (nextToken instanceof Rpar) isVariable = false;
				else throw new SyntacticError("Function definition <" + funcName + "> invalid argument declaration");
			}
		} else throw new SyntacticError("Function definition : missing left parenthsis");
	}
	
	/*
	 * Cette m�thode eval singuli�re permet de cr�er l'environnement local d'ex�cution de la fonction
	 * Elle est utilis� lors de l'appel � la fonction
	 * A chaque argument d�clar� dans le Head, on associe la valeur enti�re du param�tre pass� lors de l'appel
	 */
	public Env<Integer> eval(List<Integer> params) throws EvaluationError {
		if (args.size() != params.size()) throw new EvaluationError("Incorrect args number for call to " + funcName);
		Env<Integer> localEnv = new Env<Integer>();
		for (int i=0; i<args.size(); i++) {
			localEnv.bind(args.get(i).identifier, params.get(i));
		}
		return localEnv;
	}

}

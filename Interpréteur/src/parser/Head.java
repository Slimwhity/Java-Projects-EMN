package parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lexer.IDENTIFIER;
import lexer.Lpar;
import lexer.Rpar;
import lexer.SLexer;
import lexer.Token;
import lexer.UnexpectedCharacter;

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
		if (firstToken instanceof Lpar) {
			Token secondToken = SLexer.getToken();
			if (secondToken instanceof IDENTIFIER) {
				funcName = ((IDENTIFIER) secondToken).value;
			} else throw new SyntacticError("Identifiant absent dans la définition d'une fonction");
			boolean isVariable = true;
			while (isVariable) {
				Token nextToken = SLexer.getToken();
				if (nextToken instanceof IDENTIFIER) {
					Variable var = new Variable(((IDENTIFIER) nextToken).value);
					args.add(var);
				} else if (nextToken instanceof Rpar) isVariable = false;
				else throw new SyntacticError("Définition de la fonction " + funcName);
			}
		}
	}

	public Env<Integer> eval(List<Integer> params) throws EvaluationError {
		if (args.size() != params.size()) throw new EvaluationError("Incorrect args number for call to " + funcName);
		Env<Integer> localEnv = new Env<Integer>();
		for (int i=0; i<args.size(); i++) {
			localEnv.bind(args.get(i).identifier, params.get(i));
		}
		return localEnv;
	}

}

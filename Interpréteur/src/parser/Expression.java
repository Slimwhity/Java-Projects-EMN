package parser;

import java.io.IOException;

import lexer.*;
import lexer.OPERAND.OP;

public abstract class Expression extends AST {
	
	public static Expression parse() throws SyntacticError, IOException, UnexpectedCharacter {
		Expression exp = parse(SLexer.getToken());
		if (!(SLexer.getToken() instanceof EOF)) throw new SyntacticError();
		// TODO : Reporter le test sur EOF dans Program
		return exp;
	}

	protected static Expression parseLpar(Token token) throws SyntacticError, IOException, UnexpectedCharacter {
		// Cette methode parse les lexemes suivant une paranthèse ouvrante.
		if (token instanceof OPERAND) {
			if (((OPERAND) token).operateur != OP.MINUS) {
				Expression exp1 = parse(SLexer.getToken());
				Token nextToken2 = SLexer.getToken();
				if (nextToken2 instanceof Rpar) {
					throw new SyntacticError();
				}
				Expression exp2 = parse(nextToken2);
				//be.parse_();
				nextToken2 = SLexer.getToken();
				if (!(nextToken2 instanceof Rpar)) {
					throw new SyntacticError();
				}
				return new BinaryExpression(((OPERAND) token).operateur, exp1, exp2);
			} else {
				Expression exp1 = parse(SLexer.getToken());
				Token nextToken = SLexer.getToken();
				if (nextToken instanceof Rpar) {
					return new UnaryMinus(exp1);
				}
				Expression exp2 = parse(nextToken);
				if (!(SLexer.getToken() instanceof Rpar)) {
					throw new SyntacticError();
				}
				return new BinaryExpression(OP.MINUS, exp1, exp2);
			} 
		}
		else if (token instanceof KWIF) {
			Expression exp1 = parse(SLexer.getToken());
			Token nextToken = SLexer.getToken();
			if (nextToken instanceof Rpar) throw new SyntacticError();
			Expression exp2 = parse(nextToken);
			nextToken = SLexer.getToken();
			if (nextToken instanceof Rpar) throw new SyntacticError();
			Expression exp3 = parse(nextToken);
			if (!(SLexer.getToken() instanceof Rpar)) {
				throw new SyntacticError();
			}
			return new ConditionnalExpression(exp1, exp2, exp3);
		} else if (token instanceof IDENTIFIER) {
			/* Function call
			 * Expression ::= '(' FUNCTION Expression* ')'
			 */
			FunctionCall call = new FunctionCall(((IDENTIFIER) token).value);
			// Après avoir récupéré le nom de l'appelée on délègue le traitement à l'instance de FunctionCall
			call.parse_();
			// TODO : Est-ce qu'il y aurait ici une exception à traiter ?
			return call;
		}
		throw new SyntacticError();
	}

	public static Expression parse(Token currentToken) throws IOException, UnexpectedCharacter, SyntacticError {
		
		if(currentToken instanceof LITERAL) {
			/*
			 * Expression ::= LITERAL
			 */
			return new Literal(((LITERAL) currentToken).value);
		} else if (currentToken instanceof IDENTIFIER) {
			/*
			 * Expression ::= VARIABLE
			 */
			return new Variable(((IDENTIFIER) currentToken).value);
		} else if (currentToken instanceof Lpar) {
			return parseLpar(SLexer.getToken());
		} 
		throw new SyntacticError();
	}
	
	public abstract int eval(Env<Integer> envVar, Env<Function> envFunc) throws EvaluationError; 

}

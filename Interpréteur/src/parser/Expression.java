package parser;

import java.io.IOException;

import lexer.*;
import lexer.OPERAND.OP;

public abstract class Expression extends AST {

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
			return parseAfterLpar(SLexer.getToken());
		} 
		throw new SyntacticError("Expression parsing : unknown error");
	}
	
	protected static Expression parseAfterLpar(Token token) throws SyntacticError, IOException, UnexpectedCharacter {
		// Cette methode parse les lexemes suivant une paranthèse ouvrante.
		if (token instanceof OPERAND) {
			/* BinaryExpression
			 * Expression ::= '(' OP Expression Expression ')'
			 */
			if (((OPERAND) token).operateur != OP.MINUS) {
				BinaryExpression tmpBinary = new BinaryExpression(((OPERAND) token).operateur);
				// On est certain d'être en présence d'une BinaryExpression : on délègue le parsing à BinaryExpression.
				tmpBinary.parse_();
				return tmpBinary;
			} else {
				/*
				 * Dans ce cas, on est soit en présence d'une UnaryMinus soit d'une BinaryExpression
				 * On est obligé d'affiner le parsing pour determiner quelle expression construire
				 */
				Expression exp1 = parse(SLexer.getToken());
				Token nextToken = SLexer.getToken();
				// Si le token lu après la première expression est une Rpar, il s'agit d'une UnaryMinus
				if (nextToken instanceof Rpar) {
					return new UnaryMinus(exp1);
				}
				// Sinon, on construit une BinaryExpression avec l'OP MINUS
				Expression exp2 = parse(nextToken);
				if (!(SLexer.getToken() instanceof Rpar)) {
					throw new SyntacticError("Binary expression : missing right parenthesis");
				}
				return new BinaryExpression(OP.MINUS, exp1, exp2);
			} 
		}
		else if (token instanceof KWIF) {
			/* ConditionnalExpression
			 * Expression ::= '(' IF Expression Expression Expression ')'
			 * On determine immédiatement le type d'expression, donc on peut déléguer le traitement
			 * à la classe ConditionnalExpression
			 */
			ConditionnalExpression tmpConditionnal = new ConditionnalExpression();
			tmpConditionnal.parse_();
			return tmpConditionnal;
		} else if (token instanceof IDENTIFIER) {
			/* Function call
			 * Expression ::= '(' FUNCTION Expression* ')'
			 */
			FunctionCall call = new FunctionCall(((IDENTIFIER) token).value);
			// Après avoir récupéré le nom de l'appelée on délègue le traitement à l'instance de FunctionCall
			call.parse_();
			return call;
		}
		throw new SyntacticError("Expression parsing : unknown error");
	}

	// Méthode eval implémentée dans les classes filles concrètes
	public abstract int eval(Env<Integer> envVar, Env<Function> envFunc) throws EvaluationError; 

}

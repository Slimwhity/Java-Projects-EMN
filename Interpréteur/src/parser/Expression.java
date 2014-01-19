package parser;

import java.io.IOException;

import lexer.*;
import lexer.OPERAND.OP;

public abstract class Expression extends AST {
	
	public static Expression parse() throws SyntacticError, IOException, UnexpectedCharacter {
		Expression exp = parse(SLexer.getToken());
		if (!(SLexer.getToken() instanceof EOF)) throw new SyntacticError();
		return exp;
	}
	
	public static Expression parse2(Token token) throws SyntacticError, IOException, UnexpectedCharacter {
		if (token instanceof Lpar) {
			return parseLpar(token);
		}
		throw new SyntacticError();
	}

	protected static Expression parseLpar(Token token) throws SyntacticError, IOException, UnexpectedCharacter {
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
		}
		throw new SyntacticError();
	}

	public static Expression parse(Token currentToken) throws IOException, UnexpectedCharacter, SyntacticError {
		//Token currentToken = SLexer.getToken();
		
		if(currentToken instanceof LITERAL) {
			return new Literal(((LITERAL) currentToken).value);
		} else if (currentToken instanceof IDENTIFIER) {
			return new Variable(((IDENTIFIER) currentToken).value);
		} else if (currentToken instanceof Lpar) {
			Token nextToken = SLexer.getToken();
			if (nextToken instanceof OPERAND) {
				if (((OPERAND) nextToken).operateur != OP.MINUS) {
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
					return new BinaryExpression(((OPERAND) nextToken).operateur, exp1, exp2);
				} else {
					Expression exp1 = parse(SLexer.getToken());
					nextToken = SLexer.getToken();
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
			else if (nextToken instanceof KWIF) {
				Expression exp1 = parse(SLexer.getToken());
				nextToken = SLexer.getToken();
				if (nextToken instanceof Rpar) throw new SyntacticError();
				Expression exp2 = parse(nextToken);
				nextToken = SLexer.getToken();
				if (nextToken instanceof Rpar) throw new SyntacticError();
				Expression exp3 = parse(nextToken);
				if (!(SLexer.getToken() instanceof Rpar)) {
					throw new SyntacticError();
				}
				return new ConditionnalExpression(exp1, exp2, exp3);
			}
		} 
		throw new SyntacticError();
	}
	
	public abstract int eval(Env<Integer> envVar) throws EvaluationError; 

}

package parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lexer.Rpar;
import lexer.SLexer;
import lexer.Token;
import lexer.UnexpectedCharacter;

public class FunctionCall extends Expression {
	
	private String calleeName;
	protected List<Expression> args;
	
	public FunctionCall(String name) {
		calleeName = name;
		this.args = new ArrayList<Expression>();
	}
	
	protected void parse_() throws IOException, UnexpectedCharacter {
		boolean isExpression = true;
		Token firstToken;
		
		while (isExpression) {
			firstToken = SLexer.getToken();
			if (firstToken instanceof Rpar) {
				isExpression = false;
			} else {
				Expression exp = Expression.parse(firstToken);
				args.add(exp);
			}
		}
	}
	
	@Override
	public String toString(String offset) {
		offset += align();
		if (args.isEmpty()) return "FunctionCall(" + calleeName + ')';
		else {
			String s = "FunctionCall(" + calleeName;
			for (Expression arg : args) {
				s+= ", \n" + offset + arg.toString(offset);
			}
			return s + ')';
		}
	}

	@Override
	public int eval(Env<Integer> envVar, Env<Function> envFunc) throws EvaluationError { 
		Function f = envFunc.lookup(calleeName);
		List<Integer> params = new ArrayList<Integer>(args.size());
		for (Expression arg : args) params.add(arg.eval(envVar, envFunc));
		return f.eval(params, envFunc);
	}

}

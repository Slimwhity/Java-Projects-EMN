package parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lexer.Rpar;
import lexer.SLexer;
import lexer.Token;
import lexer.UnexpectedCharacter;

public class FunctionCall extends Expression {
	/*
	 * Expression ::= '(' Function Expression* ')'
	 */
	
	private String calleeName;
	protected List<Expression> args;
	
	public FunctionCall(String name) {
		calleeName = name;
		this.args = new ArrayList<Expression>();
	}
	
	/*
	 * Cette methode parse a pour charge de parser toutes les expressions qui sont spécifiées
	 * dans l'appel de la fonction. Il s'agit des arguments qu'on passera à la fonction.
	 * Il est possible de passer un appel de fonction en argument.
	 * Les exceptions sont remontées par Expression; si on ne trouve pas de Rpar une exception sera levée.
	 */
	protected void parse_() throws IOException, UnexpectedCharacter {
		Token firstToken = SLexer.getToken();
		// Si le token lu n'est pas un Rpar, on peut tenter de parser une expression
		if (!(firstToken instanceof Rpar)) {
			// Les erreurs sont traitées par Expression. Lorsqu'on rencontre une Rpar, l'appel récursif s'arrête.
			Expression exp = Expression.parse(firstToken);
			args.add(exp);
			parse_();
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
	
	/*
	 * L'évaluation de l'appel passe par la récupération de la fonction dans l'environnement de fonctions
	 * puis par l'évaluation de chacun des arguments.
	 * On délègue ensuite l'évaluation à la fonction, qui constituera son environnement local avec la liste
	 * des arguments pré-calculés.
	 * @see parser.Expression#eval(parser.Env, parser.Env)
	 */
	@Override
	public int eval(Env<Integer> envVar, Env<Function> envFunc) throws EvaluationError { 
		Function f = envFunc.lookup(calleeName);
		List<Integer> params = new ArrayList<Integer>(args.size());
		for (Expression arg : args) params.add(arg.eval(envVar, envFunc));
		return f.eval(params, envFunc);
	}

}

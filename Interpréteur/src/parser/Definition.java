package parser;

public class Definition extends AST {
	private Variable variable;
	private Expression exp;
	
	public Definition(Variable var, Expression exp) {
		variable = var;
		this.exp = exp;
	}
	
	public void eval(Env<Integer> envVar) throws EvaluationError {
		envVar.bind(variable.name, exp.eval(envVar));
	}
 	
	public String toString() {
		return "Definition(" + variable + ", " + exp + ')'; 
	}

	@Override
	public String toString(String offset) {
		offset += align();
		return "Definition(" + variable.toString(offset) + ',' + "\n" + offset + exp.toString(offset) + ')';
	}
	
}

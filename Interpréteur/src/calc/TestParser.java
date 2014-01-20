package calc;

import lexer.SLexer;
import parser.*;

public class TestParser {
	public static void main(String args[]) {
		try {
			  SLexer.init(args[0]); // initialisation du "lexer"
			  
			  /* Piste bleue */
//			  Body body = new Body(); // Création d'une instance de body
//			  body.parse(); // Reconnaissance de body
			  
			  /* Piste rouge */
			  
			  Program prog = new Program();
			  prog.parse();
			  System.out.println(prog.toString(""));
			  
//			  Expression exp = Expression.parse(); // reconnaissance d'une expression
//			  System.out.println(exp); // affichage de l'arbre de syntaxe abstrait
//			  System.out.println(exp.eval(new Env<Integer>())); // Evalutation de l'expression
			} catch (Exception e) {
			  e.printStackTrace();
			}
	}
}

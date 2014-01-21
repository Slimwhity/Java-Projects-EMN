package calc;

import lexer.SLexer;
import parser.*;

public class Calc {
	public static void main(String args[]) {
		try {
			  SLexer.init(args[0]); // initialisation du "lexer"
			  
			  /* Piste verte */
//			  Expression exp = Expression.parse(); // reconnaissance d'une expression
//			  System.out.println(exp); // affichage de l'arbre de syntaxe abstrait
//			  System.out.println(exp.eval(new Env<Integer>())); // Evalutation de l'expression
			  
			  /* Piste bleue */
//			  Body body = new Body(); // Création d'une instance de body
//			  body.parse(); // Reconnaissance de body
//			  Env<Integer> envVar = new Env<Integer>();
//			  System.out.println(body.toString("")); // Affichage
//			  System.out.println("\nRésultat du programme : " + body.eval(envVar)); // Evaluation du body
			  
			  /* Piste rouge */
			  Program prog = new Program();
			  prog.parse();
			  System.out.println(prog.toString(""));
			  Env<Integer> envVar = new Env<Integer>();
			  Env<Function> envFunc = new Env<Function>();
			  System.out.println("\nProgram result is : " + prog.eval(envVar, envFunc));
			  
			} catch (Exception e) {
			  e.printStackTrace();
			}
	}
}

package calc;

import lexer.SLexer;
import parser.*;

public class Calc {
	public static void main(String args[]) {
		try {
			  SLexer.init(args[0]); // initialisation du "lexer"
			  Expression exp = Expression.parse(); // reconnaissance d'une expression
			  System.out.println(exp); // affichage de l'arbre de syntaxe abstrait
			  System.out.println(exp.eval());
			} catch (Exception e) {
			  e.printStackTrace();
			}
	}
}

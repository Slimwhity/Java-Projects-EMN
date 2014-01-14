package lexer;

public class OPERAND extends Token {
	public enum OP {MINUS, TIMES, PLUS, DIV, EQUALS, LOWER;}
	
	public OP operateur; 
	
	public OPERAND(OP op) {
		this.operateur = op;
	}
	
	public String toString() {
		return operateur.toString();
	}
}

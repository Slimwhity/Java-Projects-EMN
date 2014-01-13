package lexer;

public class OPERAND extends Token {
	public enum OP {
		MINUS, TIMES, PLUS, DIV, EQUAL, LOWER;
		
		public String toString() {
			switch (this) {
			case MINUS : return "-";
			case TIMES : return "*";
			case PLUS : return "+";
			case DIV : return "/";
			case EQUAL : return "==";
			case LOWER : return "<";
			default : return "";
			}
		}
	}
	
	public OP operateur; 
	
	public OPERAND(OP op) {
		this.operateur = op;
	}
}

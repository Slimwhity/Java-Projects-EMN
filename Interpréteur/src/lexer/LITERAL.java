package lexer;

public class LITERAL extends Token {
	public int value;
	
	public LITERAL(String value) {
		this.value = Integer.parseInt(value);
	}
	
	public String toString() {
		return "Literal("+ value+')';
	}

	
}

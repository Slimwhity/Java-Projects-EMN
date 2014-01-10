package lexer;

public class Identifier extends Token {
	private String value;
	
	public Identifier(String value) {
		this.value = value;
	}
	
	public String toString() {
		return "ID" + value;
	}

}

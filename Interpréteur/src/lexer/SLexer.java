package lexer;

import java.io.IOException;

import errors.UnexpectedCharacter;

public class SLexer {
	private static Lexer lexer;
	
	public static void init(String filename) throws IOException {
		lexer = new Lexer(filename);
	}
	
	public static Token getToken() throws IOException, UnexpectedCharacter {
		try {
			return lexer.getToken();
		}  catch (IOException e){
			lexer.getFileReader().close(); // close the reader
			throw e; // pass the exception up the stack
		}
	}
	
}

// Author: Yiran Cao.805

package core;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

class MyTokenizer implements Tokenizer {

	private static String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static String lowercase = "abcdefghijklmnopqrstuvwxyz";
	private static String digits = "0123456789";
	private static String whitespaces = " \n\t\r";
	
	private Scanner src;
	private int entry;
	private int end;
	private String line;
	private TokenKind curTokenKind;
	private String cur;

	private int int_token_value;
	private String id_token_name;

	
	private MyTokenizer(Scanner in) {
		this.src = in;
		this.entry = 0;
		this.end = 0;
		this.line = null;
		this.int_token_value = 0;
		this.id_token_name = null;
		this.cur = "";
		this.skipToken();
	}
	public static MyTokenizer create(Scanner in) {
		return new MyTokenizer(in);
	}

	public void skipToken(){

		if (cur.equals("") && !src.hasNext()) {
			this.curTokenKind = TokenKind.EOF;
		} else {
			if (cur.equals("")) {
				cur = src.next();
			}

			// Finite State Automaton for Core
			if (cur.equals(",")) {
				this.curTokenKind = TokenKind.COMMA;
				cur = "";
			} else if (cur.equals(";")) {
				this.curTokenKind = TokenKind.SEMICOLON;
				cur = "";
			} else if (cur.equals("[")) {
				this.curTokenKind = TokenKind.LEFT_BRACKET;
				cur = "";
			} else if (cur.equals("]")) {
				this.curTokenKind = TokenKind.RIGHT_BRACKET; 
				cur = "";
			} else if (cur.equals("(")) {
				this.curTokenKind = TokenKind.LEFT_PARENTHESIS;
				cur = "";
			} else if (cur.equals(")")) {
				this.curTokenKind = TokenKind.RIGHT_PARENTHESIS;
				cur = "";
			} else if (cur.equals("+")) {
				this.curTokenKind = TokenKind.PLUS_OPERATOR;
				cur = "";
			} else if (cur.equals("-")) {
				this.curTokenKind = TokenKind.MINUS_OPERATOR;
				cur = "";
			} else if (cur.equals("=")) {
				//ASSINGMENT or EQUALITY_TEST
				if(!src.hasNext()) {
					this.curTokenKind = TokenKind.ERROR;
				} else {
					cur = src.next();
					if (cur.equals("=")) {
						this.curTokenKind = TokenKind.EQUALITY_TEST;
						cur = "";
					} else {
						this.curTokenKind = TokenKind.ASSIGNMENT_OPERATOR;
					}
				}
			} else if (cur.equals("<")) {
				// LESS_TEST or LESS_EQUAL_TEST
				if(!src.hasNext()) {
					this.curTokenKind = TokenKind.ERROR;
				} else {
					cur = src.next();
					if (cur.equals("=")) {
						this.curTokenKind = TokenKind.LESS_EQUAL_TEST;
						cur = "";
					} else {
						this.curTokenKind = TokenKind.LESS_TEST;
					}
				}
			} else if (cur.equals(">")) {
				// GRESTER_TEST or GREATER_EQUAL_TEST
				if(!src.hasNext()) {
					this.curTokenKind = TokenKind.ERROR;
				} else {
					cur = src.next();
					if (cur.equals("=")) {
						this.curTokenKind = TokenKind.GREATER_EQUAL_TEST;
						cur = "";
					} else {
						this.curTokenKind = TokenKind.GREATER_TEST;
					}
				}
			} else if (cur.equals("|")) {
				if(!src.hasNext()) {
					this.curTokenKind = TokenKind.ERROR;
				} else {
					cur = src.next();
					if (!cur.equals("|")) {
						this.curTokenKind = TokenKind.ERROR;
					} else {
						this.curTokenKind = TokenKind.OR_OPERATOR;
						cur = "";
					}
				}
			} else if (cur.equals("&")) {
				if(!src.hasNext()) {
					this.curTokenKind = TokenKind.ERROR;
				} else {
					cur = src.next();
					if (!cur.equals("&")) {
						this.curTokenKind = TokenKind.ERROR;
					} else {
						this.curTokenKind = TokenKind.AND_OPERATOR;
						cur = "";
					}
				}
			} else if (cur.equals("!")){
				if(!src.hasNext()) {
					this.curTokenKind = TokenKind.ERROR;
				} else {
					cur = src.next();
					if (!cur.equals("=")) {
						this.curTokenKind = TokenKind.ERROR;
					} else {
						this.curTokenKind = TokenKind.INEQUALITY_TEST;
						cur = "";
					}
				}
			} else if (uppercase.contains(cur)) {
				StringBuffer buffer = new StringBuffer(cur);
				boolean updated = false;
				while (src.hasNext()) {
					// System.out.println("checking uppercase " + cur);
					cur = src.next();
					if (lowercase.contains(cur)) {
						// System.out.println("error: lowercase letter " + cur + " follows uppercase");
						updated = true;
						this.curTokenKind = TokenKind.ERROR;
						break;
					} else if (digits.contains(cur)) {
						buffer.append(cur);
						break;
					} else if (uppercase.contains(cur)) {
						buffer.append(cur);
					} else {
						updated = true;
						this.curTokenKind = TokenKind.IDENTIFIER;
						this.id_token_name = new String(buffer);
						break;
					}
				}
				while (!updated && src.hasNext()) {
					cur = src.next();
					if(digits.contains(cur)) {
						buffer.append(cur);
					} else if(lowercase.contains(cur) && uppercase.contains(cur)) {
						// System.out.println("error: lowercase letter " + cur + " follows digits");
						updated = true;
						this.curTokenKind = TokenKind.ERROR;
						break;
					} else {
						updated = true;
						this.curTokenKind = TokenKind.IDENTIFIER;
						this.id_token_name = new String(buffer);
						break;
					}
				}
				if (!updated) {
					cur = "";
					this.curTokenKind = TokenKind.IDENTIFIER;
					this.id_token_name = new String(buffer);
				}
			} else if (digits.contains(cur)) {
				StringBuffer buffer = new StringBuffer(cur);
				boolean updated = false;
				while (src.hasNext()) {
					cur = src.next();
					if (digits.contains(cur)) {
						buffer.append(cur);
					} else if (uppercase.contains(cur) || lowercase.contains(cur)) {
						this.curTokenKind = TokenKind.ERROR;
						updated = true;
						break;
					} else {
						this.curTokenKind = TokenKind.INTEGER_CONSTANT;
						updated = true;
						this.int_token_value = Integer.parseInt(new String(buffer));
						break;
					}
				}
				if(!updated) {
					cur = "";
					this.curTokenKind = TokenKind.INTEGER_CONSTANT;
					this.int_token_value = Integer.parseInt(new String(buffer));
				}
			} else if (lowercase.contains(cur)) {
				StringBuffer buffer = new StringBuffer(cur);
				boolean updated = false;
				String name = "";
				while (src.hasNext()) {
					cur = src.next();
					if (uppercase.contains(cur) || digits.contains(cur)) {
						this.curTokenKind = TokenKind.ERROR;
						updated = true;
						break;
					} else if (lowercase.contains(cur)) {
						buffer.append(cur);
					} else {
						updated = true;
						name = new String(buffer);
						break;
					}
				}
				if (!updated) {
					cur = "";
					name = new String(buffer);
				}

				switch (name) {
					case "program": 
						this.curTokenKind = TokenKind.PROGRAM;
						break;
					case "begin": 
						this.curTokenKind = TokenKind.BEGIN;
						break;
					case "end":
						this.curTokenKind = TokenKind.END;
						break;
					case "int":
						this.curTokenKind = TokenKind.INT;
						break;
					case "if":
						this.curTokenKind = TokenKind.IF;
						break;
					case "then":
						this.curTokenKind = TokenKind.THEN;
						break;
					case "else":
						this.curTokenKind = TokenKind.ELSE;
						break;
					case "while":
						this.curTokenKind = TokenKind.WHILE;
						break;
					case "loop":
						this.curTokenKind = TokenKind.LOOP;
						break;
					case "read":
						this.curTokenKind = TokenKind.READ;
						break;
					case "write":
						this.curTokenKind = TokenKind.WRITE;
						break;
					default:
						this.curTokenKind = TokenKind.ERROR;
						break;
				}
			} else if (whitespaces.contains(cur)) {
				boolean updated = false;
				while (src.hasNext()) {
					cur = src.next();
					if (!whitespaces.contains(cur)) {
						updated = true;
						this.curTokenKind = TokenKind.SPACE;
						break;
					}
				}
				if (!updated) {
					cur = "";
					this.curTokenKind = TokenKind.SPACE;
				}
			} else {
				this.curTokenKind = TokenKind.ERROR;
			}
		}
	}

	public TokenKind getToken(){
		while (this.curTokenKind == TokenKind.SPACE) skipToken();
		return this.curTokenKind;
	}

	public int intVal() {
		return this.int_token_value;
	}

	public String idName(){
		return this.id_token_name;
	}
}
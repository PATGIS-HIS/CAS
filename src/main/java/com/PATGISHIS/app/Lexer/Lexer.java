package com.PATGISHIS.app.Lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private final String input;
    private final List<Token> tokens = new ArrayList<>();
    private int position = 0;

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        while (position < input.length()) {
            char current = input.charAt(position);

            if (Character.isWhitespace(current)) {
                position++;
                continue;
            }

            if (Character.isDigit(current) || current == '.') {
                tokenizeNumber();
                continue;
            }

            if (Character.isLetter(current)) {
                tokenizeIdentifier();
                continue;
            }

            switch (current) {
                case '+':
                    addToken(TokenType.PLUS);
                    break;
                case '-':
                    addToken(TokenType.MINUS);
                    break;
                case '*':
                    addToken(TokenType.MULTIPLY);
                    break;
                case '/':
                    addToken(TokenType.DIVIDE);
                    break;
                case '^':
                    addToken(TokenType.EXPONENT);
                    break;
                case '(':
                    addToken(TokenType.LPAREN);
                    break;
                case ')':
                    addToken(TokenType.RPAREN);
                    break;
                case '=':
                    addToken(TokenType.EQUAL);
                    break;
                case '<':
                    addToken(TokenType.LESS_THAN);
                    break;
                case '>':
                    addToken(TokenType.GREATER_THAN);
                    break;
                default:
                    throw new RuntimeException(
                            "Unexpected character '" + current +
                                    "' at position " + position);
            }
        }

        tokens.add(new Token(TokenType.EOF, "", position));
        return tokens;
    }

    private void tokenizeNumber() {
        int start = position;

        while (position < input.length() &&
                Character.isDigit(input.charAt(position)) ||
                input.charAt(position) == '.') {
            position++;
        }

        String value = input.substring(start, position);
        tokens.add(new Token(TokenType.NUMBER, value, start));
    }

    private void tokenizeIdentifier() {
        int start = position;
        while (position < input.length() &&
                Character.isLetterOrDigit(input.charAt(position))) {

            position++;
        }

        String value = input.substring(start, position);
        tokens.add(new Token(TokenType.IDENTIFIER, value, start));
    }

    private void addToken(TokenType type) {
        tokens.add(new Token(
            type, 
            String.valueOf(input.charAt(position)), 
            position
        ));
        position++;
    }
}

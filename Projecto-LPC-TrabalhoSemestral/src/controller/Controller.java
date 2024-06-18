package controller;

import asd.TokenWithLine;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class Controller implements java.io.Serializable {

    // Atributos
    private JTextArea jLabelLineCount;
    private List<TokenWithLine> tokens; // Remova esta declaração

    // Método para definir o JTextArea
    public void setJLabelLineCount(JTextArea jLabelLineCount) {
        this.jLabelLineCount = jLabelLineCount;
    }

    // Método para atualizar o número da linha
    private void updateLineNumber(int lineNumber) {
        if (jLabelLineCount != null) { // Verifica se jLabelLineCount não é null
            jLabelLineCount.setText("Linha: " + lineNumber);
        } else {
            System.err.println("Erro: jLabelLineCount não foi inicializado.");
        }
    }

    // Método para o preenchimento da Tabela
    public void preencherTabela(javax.swing.JTable tabela) {
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        model.setRowCount(0); // Limpa a tabela antes de preenchê-la novamente

        for (TokenWithLine token : tokens) {
            String lexema = token.getToken();
            int linha = token.getLineNumber();
            String tipoLexema = verificarLexema(lexema, linha);
            Object[] row;

            if (isOperadorAtribuicao(lexema)) {
                row = new Object[]{token.getToken(), "Operador de Atribuição", token.getLineNumber()}; // Corrigindo a ordem dos elementos no array
            } else {
                row = new Object[]{token.getToken(), tipoLexema, token.getLineNumber()}; // Corrigindo a ordem dos elementos no array
            }

            model.addRow(row); // Adiciona uma nova linha à tabela com o número da linha, o token e seu tipo de lexema
        }
    }

    public void printTokens() {
        for (TokenWithLine tokenWithLine : tokens) {
            System.out.println(tokenWithLine.getToken());
        }
    }

    public Controller() {
        this.tokens = new ArrayList<>();
    }

    public void setTokens(String input) {
        String[] lines = input.split("\n");
        for (int lineNumber = 0; lineNumber < lines.length; lineNumber++) {
            String line = lines[lineNumber];
            String[] tmpTokens = line.split("\\s+");
            for (String token : tmpTokens) {
                if (!token.isEmpty()) {
                    // Verifica se o token contém o sinal de atribuição :=
                    if (token.contains(":=")) {
                        String[] subTokens = token.split("(?<=:=)|(?=:=)");
                        for (String subToken : subTokens) {
                            if (!subToken.isEmpty()) {
                                TokenWithLine tokenWithLine = new TokenWithLine(subToken.trim(), lineNumber + 1);
                                tokens.add(tokenWithLine);
                            }
                        }
                    } else {
                        TokenWithLine tokenWithLine = new TokenWithLine(token.trim(), lineNumber + 1);
                        tokens.add(tokenWithLine);
                    }
                }
            }
        }
    }

// Variáveis Globais
    private static final String[] PALAVRAS_RESERVADAS = {"div", "or", "and", "not", "if", "then", "else", "of", "uses", "while", "do", "begin", "end", "read", "readln", "writeln", "write", "var", "array", "function", "procedure", "program", "true", "false", "char", "integer", "boolean"};
    private static final String[] OP_RELACIONAIS = {"<", ">", "<=", ">=", "==", "!="};
    private static final String OP_ARITMETICOS = "+ * / -";
    private static final String[] OP_LOGICOS = {"and", "or", "not"};
    private static final String OP_ATRIBUICAO = ":=";
    private static final String COMENTARIOS = " ' ' ";
    private static final String[] DELIMITADORES = {",", ".", ":", "..", ";", " "};
    private static final String CARACTERES_ESPECIAIS = "( ) [ ] { }";
    private static final String LETRAS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";
    private static final String NUMEROS = "0123456789";
    private static final String IDENTIFICADOR = LETRAS + NUMEROS;

    // Método para verificar o tipo de lexema
    public String verificarLexema(String lexema, int linha) {
        if (isOperadorAtribuicao(lexema)) {
            return "Operador de Atribuição";
        } else if (isPalavraReservada(lexema)) {
            return "Palavra Reservada";
        } else if (isComentario(lexema)) {
            return "Comentário";
        } else if (isOperadorRelacional(lexema)) {
            return "Operador Relacional";
        } else if (isOperadorAritmetico(lexema)) {
            return "Operador Aritmético";
        } else if (isOperadorLogico(lexema)) {
            return "Operador Lógico";
        } else if (isCaractereEspecial(lexema)) {
            return "Caractere Especial";
        } else if (isDelimitador(lexema)) {
            return "Delimitador";
        } else if (isIdentificador(lexema)) {
            return "Identificador";
        } else if (isNumero(lexema)) {
            return "Constante";
        } else {
            return "Erro léxico (linha " + linha + ")"; // Retorna o erro léxico com o número da linha
        }
    }

    // Métodos para verificar o tipo de token
    private boolean isPalavraReservada(String lexema) {
        lexema = lexema.toLowerCase();
        return List.of(PALAVRAS_RESERVADAS).contains(lexema);
    }

    private boolean isComentario(String lexema) {
        return lexema.startsWith("'") && lexema.endsWith("'");
    }

    private boolean isOperadorLogico(String lexema) {
        return List.of(OP_LOGICOS).contains(lexema);
    }

    private boolean isOperadorAritmetico(String lexema) {
        return OP_ARITMETICOS.contains(lexema);
    }

    private boolean isOperadorAtribuicao(String lexema) {
        return lexema.equals(OP_ATRIBUICAO);
    }

    private boolean isOperadorRelacional(String lexema) {
        return List.of(OP_RELACIONAIS).contains(lexema);
    }

    private boolean isDelimitador(String lexema) {
        return List.of(DELIMITADORES).contains(lexema);
    }

    private boolean isCaractereEspecial(String lexema) {
        return CARACTERES_ESPECIAIS.contains(lexema);
    }

    private boolean isNumero(String lexema) {
        return lexema.matches("[0-9]+");
    }

    private boolean isIdentificador(String lexema) {
        lexema = lexema.trim();
        if (!LETRAS.contains(String.valueOf(lexema.charAt(0)))) {
            return false;
        }
        for (int i = 1; i < lexema.length(); i++) {
            if (!IDENTIFICADOR.contains(String.valueOf(lexema.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

}

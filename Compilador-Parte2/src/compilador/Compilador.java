/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author dasilva
 */
public class Compilador {
    private Integer linha = 0;
    private Integer coluna = 0;
    private List<PalavraReservada> palavrasReservadas;
    private String codigo;
    private Integer tamanhoCodigo;
    private Integer contador = 0;
    
    private Stack<Simbolo> tabelaSimbolo = new Stack<>();
    private Integer escopoAtual = -1;
   
    private AnalisadorSemantico analisadorSemantico = new AnalisadorSemantico();
    
    private Texto texto = new Texto();
    
    private Token token;

    public Compilador(String codigo) {
        this.codigo = codigo;
        this.tamanhoCodigo = this.codigo.length();
        this.iniciarPalavrasReservadas();
        this.token = this.scan();
    }
    
    private void iniciarPalavrasReservadas() {
        this.palavrasReservadas = new ArrayList<>();
        this.palavrasReservadas.add(new PalavraReservada("main", Tipo.MAIN));
        this.palavrasReservadas.add(new PalavraReservada("if", Tipo.IF));
        this.palavrasReservadas.add(new PalavraReservada("else", Tipo.ELSE));
        this.palavrasReservadas.add(new PalavraReservada("while", Tipo.WHILE));
        this.palavrasReservadas.add(new PalavraReservada("do", Tipo.DO));
        this.palavrasReservadas.add(new PalavraReservada("for", Tipo.FOR));
        this.palavrasReservadas.add(new PalavraReservada("int", Tipo.INT));
        this.palavrasReservadas.add(new PalavraReservada("float", Tipo.FLOAT));
        this.palavrasReservadas.add(new PalavraReservada("char", Tipo.CHAR));
    }
    
    public List<Token> scannear() {
        List<Token> tokens = new ArrayList<>();
        
        while(this.contador < this.tamanhoCodigo) {
            try {
                Token token = this.scan();
                tokens.add(token);
            } catch (LexicalException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        return tokens;
    }
    
    
    
    public void compilar() {
        this.programa();
        
    }
    
    
    private char ler() {
        char c = codigo.charAt(contador++);
        
        if (c == '\n') {
            this.linha++;
            this.coluna = 0;
            
            texto.adicionarLinha(linha);
            texto.adicionarColuna(coluna);
        } else {
            coluna++;
            texto.adicionarColuna(coluna);
        }
        
        return c;
    }
    
    public boolean fimDeArquivo() {
        return this.contador >= this.tamanhoCodigo;
    }
    
    private void voltar(char c) {
        contador--;
        
        if (c == '\n') {
            linha--;
            coluna = texto.getLinhas().get(linha).getUltimaColuna().getPosicao();
        } else 
            coluna--;
            
    }
    
    public Token scan() {
        String lexema = "";
        Token token = new Token(lexema, Tipo.FIM_ARQUIVO);
        
        char c;
        do {
            c = ler();
        } while (Character.isWhitespace(c) && !this.fimDeArquivo());
        
        
        if (this.fimDeArquivo())
            return new Token(lexema, Tipo.FIM_ARQUIVO);
        
        if (Character.isLetter(c) || c == '_') {            // IDENTIFICADOR OU PALAVRA RESERVADA
            lexema += c;
            
            c = ler();
            while (Character.isLetterOrDigit(c) || c == '_') {
                lexema += c;
                c = ler();
            }
            this.voltar(c);
            
            Tipo classe = null;
            for (PalavraReservada palavraReservada : this.palavrasReservadas) {
                if (palavraReservada.getString().equals(lexema))
                    classe = palavraReservada.getClasse();
            }
            if (classe == null)
                classe = Tipo.IDENTIFICADOR;
            
            token = new Token(lexema, classe);
            
        } else if (Character.isDigit(c) || c == '.') {      // NUMERO
            lexema += c;
            
            if (c == '.') {                             // PONTO FLUTUANTE
                c = ler();
                
                if (Character.isDigit(c)) {
                    lexema += c;
                    
                    c = ler();
                    while (Character.isDigit(c)) {
                        lexema += c;
                        c = ler();
                    }
                    
                    voltar(c);
                    token = new Token(lexema, Tipo.PONTOFLUTUANTE);
                    
                } else 
                    throw new LexicalException(linha, coluna, "má formação de ponto flutuante");
            } else {
                
                c = ler();
                while (Character.isDigit(c)) {
                    lexema += c;
                    c = ler();
                }
                
                if (c == '.') {                         //PONTO FLUTUANTE
                    lexema += c;
                    
                    c = ler();
                    if (Character.isDigit(c)) {
                        lexema += c;
                        
                        c = ler(); 
                        while (Character.isDigit(c)) {
                            lexema += c;
                            c = ler();
                        }
                        
                        voltar(c);
                        token = new Token(lexema, Tipo.PONTOFLUTUANTE);
                        
                    } else {
                        throw new LexicalException(linha, coluna, "má formação de ponto flutuante");
                    }
                } else {                                  // INTEIRO
                    voltar(c);
                    token = new Token(lexema, Tipo.INTEIRO);
                }
                
            }
        } else {                                // CARACTERES ESPECIAIS
            switch(c) {
                case '(':
                    lexema += c;
                    token = new Token(lexema, Tipo.ABREPARENTESE);
                    break;
                case ')':
                    lexema += c;
                    token = new Token(lexema, Tipo.FECHAPARENTESE);
                    break;
                case '{':
                    lexema += c;
                    token = new Token(lexema, Tipo.ABRECHAVE);
                    break;
                case '}':
                    lexema += c;
                    token = new Token(lexema, Tipo.FECHACHAVE);
                    break;
                case ',':
                    lexema += c;
                    token = new Token(lexema, Tipo.VIRGULA);
                    break;
                case ';':
                    lexema += c;
                    token = new Token(lexema, Tipo.PONTOVIRGULA);
                    break;
                case '+':
                    lexema += c;
                    token = new Token(lexema, Tipo.ADICAO);
                    break;
                case '-':
                    lexema += c;
                    token = new Token(lexema, Tipo.SUBTRACAO);
                    break;
                case '*':
                    lexema += c;
                    token = new Token(lexema, Tipo.MULTIPLICACAO);
                    break;
                case '/':
                    lexema += c;
                    
                    c = ler();
                    if (c == '/') {
                        do {
                            c = ler();
                        } while (c != '\n' && !this.fimDeArquivo());
                        
                        if (!this.fimDeArquivo()) {
                            this.voltar(c);
                            return this.scan(); // ver com o professor se isso é suave na nave
                        } else
                            break;
                    } else if (c == '*') {
                        do {
                            c = ler();
                            
                            if (c == '*') {
                                c = ler();
                                while (c == '*') {
                                    c = ler();
                                }
                                
                                if (c == '/')
                                    return this.scan();
                            }
                        } while (!this.fimDeArquivo());
                        
                        throw new LexicalException(linha, coluna, "comentário de múltiplas linhas não fechado.");
                    }
                    this.voltar(c);
                    token = new Token(lexema, Tipo.DIVISAO);
                    break;
                case '=':                               // = e ==
                    lexema += c;
                    
                    c = ler();
                    if (c != '=') {
                        token = new Token(lexema, Tipo.ATRIBUICAO);
                        this.voltar(c);
                    } else {
                        lexema += c;
                        token = new Token(lexema, Tipo.IGUAL);
                    }
                    break;
                case '<':
                    lexema += c;
                    
                    c = ler();
                    if (c == '=') {
                        lexema += c;
                        token = new Token(lexema, Tipo.MENORIGUAL);
                    } else {
                        token = new Token(lexema, Tipo.MENOR);
                        this.voltar(c);
                    }
                    break;
                case '>':
                    lexema += c;
                    
                    c = ler();
                    if (c == '=') {
                        lexema += c;
                        token = new Token(lexema, Tipo.MAIORIGUAL);
                    } else {
                        token = new Token(lexema, Tipo.MAIOR);
                        this.voltar(c);
                    }
                    break;
                case '!':
                    lexema += c;
                    
                    c = ler();
                    if (c == '=') {
                        lexema += c;
                        token = new Token(lexema, Tipo.DIFERENTE);
                    } else {
                        this.voltar(c);
                        throw new LexicalException(linha, coluna, "lexema desconhecido da linguagem: " + lexema);    
                    }
                    break;
                case '\'':
                    lexema += c;
                    
                    c = ler();          // ler o char
                    lexema += c;
                    
                    c = ler();
                    if (c == '\'') {
                        lexema += c;
                        token = new Token(lexema, Tipo.CARACTER);
                    } else {
                        this.voltar(c);
                        throw new LexicalException(linha, coluna, "o tipo char só aceita um caracter.");
                    }
                    
                    break;
                default:
                    lexema += c;
                    throw new LexicalException(linha, coluna, "lexema desconhecido da linguagem: " + lexema);
            }
        }
        
        return token;
    }

    public void programa() {
        if (token.getTipo()== Tipo.INT) {
            token = this.scan();
            if (token.getTipo() == Tipo.MAIN) {
                token = this.scan();
                if (token.getTipo() == Tipo.ABREPARENTESE) {
                    token = this.scan();
                    if (token.getTipo() == Tipo.FECHAPARENTESE) {
                        token = this.scan();
                        
                        bloco();
                    } else {
                        throw new SintaxException(linha, coluna, "Faltou fechar o parentese.");
                    }
                } else {
                    throw new SintaxException(linha, coluna, "Depois do 'main' vem um '('.");
                }
            } else {
                throw new SintaxException(linha, coluna, "Depois do 'int' vem o 'main'.");
            }
        } else {
            throw new SintaxException(linha, coluna, "O programa começa com 'int'.");
        }
    }

    private void destruirVariaveis(int escopo) {
        while (!this.tabelaSimbolo.empty() && this.tabelaSimbolo.peek().getEscopo() == escopo) {
            this.tabelaSimbolo.pop();
        }
    }
    
    private void bloco() {
        this.escopoAtual++;
        
        if (token.getTipo() == Tipo.ABRECHAVE) {
            
            token = this.scan();
            
            while (token.getTipo() == Tipo.INT
                    || token.getTipo() == Tipo.FLOAT
                    || token.getTipo() == Tipo.CHAR) {
                
                this.decl_var();
            }
            
            
            while (token.getTipo() == Tipo.IDENTIFICADOR 
                    || token.getTipo() == Tipo.WHILE
                    || token.getTipo() == Tipo.DO
                    || token.getTipo() == Tipo.IF 
                    || token.getTipo() == Tipo.ABRECHAVE) {
                this.comando();
            }
            
            if (token.getTipo() == Tipo.FECHACHAVE) {
                token = this.scan();
                
            } else {
                throw new SintaxException(linha, coluna, "esperado um '}'.");
            }
        } else {
            throw new SintaxException(linha, coluna, "esperado um '{'.");
        }
        
        this.destruirVariaveis(this.escopoAtual);
        this.escopoAtual--;
    }
    
    public void decl_var() {
        Simbolo simbolo = new Simbolo(escopoAtual);
        
        Tipo tipo = tipo();
        simbolo.setTipo(tipo);
        
        if (token.getTipo() == Tipo.IDENTIFICADOR) {
            simbolo.setNome(token.getLexema());
            if (this.analisadorSemantico.isIdentificadorNovo(simbolo, tabelaSimbolo)) {
                
                this.tabelaSimbolo.push(simbolo);
                token = this.scan();

                while (token.getTipo() == Tipo.VIRGULA) {
                    token = this.scan();

                    if (token.getTipo() == Tipo.IDENTIFICADOR) {
                        simbolo = new Simbolo(escopoAtual);
                        simbolo.setTipo(tipo);
                        simbolo.setNome(token.getLexema());
                        if (this.analisadorSemantico.isIdentificadorNovo(simbolo, tabelaSimbolo)) {
                            this.tabelaSimbolo.push(simbolo);
                            token = this.scan();
                        } else {
                            throw new SemanticException(linha, coluna, "Já existe uma variável declarada com o nome '" + token.getLexema() + "'.");
                        }
                    } else {
                        throw new SintaxException(linha, coluna, "Era esperado um identificador");
                    }
                }

                if (token.getTipo() == Tipo.PONTOVIRGULA) {
                    token = this.scan();
                } else {
                    throw new SintaxException(linha, coluna, "Era esperado ';'.");
                }
            } else {
                throw new SemanticException(linha, coluna, "Já existe uma variável declarada com o nome '" + token.getLexema() + "'.");
            }
        } else
            throw new SintaxException(linha, coluna, "Era esperado um identificador");
        
    }
    
    public Tipo tipo() {
        if (token.getTipo() == Tipo.INT
                || token.getTipo() == Tipo.FLOAT
                || token.getTipo() == Tipo.CHAR) {
            
            Tipo retorno = token.getTipo();
            token = this.scan();
            return retorno;
        } else
            throw new SintaxException(linha, coluna, "Tipo desconhecido.");
    }
    
    private void comando() {
        if (token.getTipo() == Tipo.IDENTIFICADOR || token.getTipo() == Tipo.ABRECHAVE) {
            this.comandoBasico();
        } else if (token.getTipo() == Tipo.WHILE || token.getTipo() == Tipo.DO) {
            this.iteracao();
        } else if (token.getTipo() == Tipo.IF) {
            token = this.scan();
            
            if (token.getTipo() == Tipo.ABREPARENTESE) {
                token = this.scan();
                
                this.expressaoRelacional();
                
                if (token.getTipo() == Tipo.FECHAPARENTESE) {
                    token = this.scan();
                    
                    this.comando();
                    
                    if (token.getTipo() == Tipo.ELSE) {
                        token = this.scan();
                        
                        this.comando();
                    }
                } else {
                    throw new SintaxException(linha, coluna, "esperado um ')'.");
                }
            } else {
                throw new SintaxException(linha, coluna, "esperado um '('.");
            }
        } else {
            throw new SintaxException(linha, coluna, "esperado um comando básico, um if ou uma iteração");
        }
    }
    
    private void comandoBasico() {
        if (token.getTipo() == Tipo.IDENTIFICADOR) {
            this.atribuicao();
        } else if (token.getTipo() == Tipo.ABRECHAVE) {
            this.bloco();
        } else {
            throw new SintaxException(linha, coluna, "esperado uma atribuição ou um bloco.");
        }
    }
    
    private void atribuicao() {
        if (token.getTipo() == Tipo.IDENTIFICADOR) {
            Simbolo simbolo = this.analisadorSemantico.obterSimbolo(token.getLexema(), tabelaSimbolo, escopoAtual);
            if (simbolo != null) {
                
                token = this.scan();

                if (token.getTipo() == Tipo.ATRIBUICAO) {
                    token = this.scan();

                    Tipo tipo = expressaoAritmetica();
                    if (this.analisadorSemantico.tiposCompativeis(simbolo.getTipo(), tipo)) {
                        if (token.getTipo() == Tipo.PONTOVIRGULA) {
                            token = this.scan();
                        } else {
                            throw new SintaxException(linha, coluna, "esperado um ';'.");
                        }
                    } else {
                        throw new SemanticException(linha, coluna, "Tipos incompatíveis.");
                    }

                } else {
                    throw new SintaxException(linha, coluna, "esperado um '='.");
                }
            } else {
                throw new SemanticException(linha, coluna, "o identificador '" + token.getLexema() + "' não foi declarado.");
            }
        } else {
            throw new SintaxException(linha, coluna, "esperado um identificador.");
        }
    }
    
    private void iteracao() {
        if (token.getTipo() == Tipo.WHILE) {
            token = this.scan();
            
            if (token.getTipo() == Tipo.ABREPARENTESE) {
                token = this.scan();
                
                this.expressaoRelacional();
                
                if (token.getTipo() == Tipo.FECHAPARENTESE) {
                    token = this.scan();
                    
                    this.comando();
                } else {
                    throw new SintaxException(linha, coluna, "esperado um ')'.");
                }
            } else {
                throw new SintaxException(linha, coluna, "esperado um '('.");
            }
        } else if (token.getTipo() == Tipo.DO) {
            token = this.scan();
            
            this.comando();
            
            if (token.getTipo() == Tipo.WHILE) {
                token = this.scan();
                
                if (token.getTipo() == Tipo.ABREPARENTESE) {
                    token = this.scan();
                    
                    this.expressaoRelacional();
                    
                    if (token.getTipo() == Tipo.FECHAPARENTESE) {
                        token = this.scan();
                        
                        if (token.getTipo() == Tipo.PONTOVIRGULA) {
                            token = this.scan();
                            
                        } else {
                            throw new SintaxException(linha, coluna, "esperado um ';'.");
                        }
                    } else {
                        throw new SintaxException(linha, coluna, "esperado um ')'.");
                    }
                } else {
                    throw new SintaxException(linha, coluna, "esperado um '('.");
                }
            } else {
                throw new SintaxException(linha, coluna, "esperado um 'while'.");
            }
        } else {
            throw new SintaxException(linha, coluna, "comando de iteração incorreto.");
        }
    }

    private Tipo expressaoAritmetica() {
        Tipo tipo = termo();
        Tipo tipoResultante = expressaoAritmeticaAux(tipo);
        
        return tipoResultante;
    }

    private Tipo expressaoAritmeticaAux(Tipo tipo) {
        if (token.getTipo() == Tipo.ADICAO || token.getTipo() == Tipo.SUBTRACAO) {
            Tipo operador = token.getTipo();
            
            token = this.scan();
            
            Tipo tipo2 = termo();
            Tipo tipoResultante = this.analisadorSemantico.calcularTipos(tipo, operador, tipo2);
            
            return expressaoAritmeticaAux(tipoResultante);
        }
        
        return tipo;
    }
    
    private Tipo termo() {
        
        Tipo tipo = fator();
        
        while (token.getTipo() == Tipo.MULTIPLICACAO || token.getTipo() == Tipo.DIVISAO) {
            Tipo operador = token.getTipo();
            
            token = this.scan();
            Tipo tipo2 = fator();
            
            tipo = this.analisadorSemantico.calcularTipos(tipo, operador, tipo2);
        }
        
        return tipo;
    }

    private Tipo fator() {
        if (token.getTipo() == Tipo.ABREPARENTESE) {
            token = this.scan();
            
            Tipo retorno = expressaoAritmetica();
            
            if (token.getTipo() == Tipo.FECHAPARENTESE) {
                token = this.scan();
                return retorno;
            } else { 
                throw new SintaxException(linha, coluna, "parêntese não foi fechado corretamente.");
            }
        } else if (token.getTipo() == Tipo.IDENTIFICADOR
                    || token.getTipo() == Tipo.INTEIRO
                    || token.getTipo() == Tipo.PONTOFLUTUANTE
                    || token.getTipo() == Tipo.CARACTER) {
            try {
                Tipo retorno = this.analisadorSemantico.obterTipo(token, tabelaSimbolo, escopoAtual);
                
                
                
                token = this.scan();
                return retorno;
            } catch (RuntimeException e) {
                throw new SemanticException(linha, coluna, e.getMessage());
            }
        } else {
            throw new SintaxException(linha, coluna, "era esperado um termo ou um parêntese.");
        }
    }

    private void expressaoRelacional() {
        this.expressaoAritmetica();
        
        this.operadorRelacional();
        
        this.expressaoAritmetica();
    }

    private void operadorRelacional() {
        if (token.getTipo() == Tipo.MAIOR
                || token.getTipo() == Tipo.MENOR
                || token.getTipo() == Tipo.MAIORIGUAL
                || token.getTipo() == Tipo.MENORIGUAL
                || token.getTipo() == Tipo.IGUAL
                || token.getTipo() == Tipo.DIFERENTE)
            token = this.scan();
        else
            throw new SintaxException(linha, coluna, "esperado um operador relacional.");
    }
}

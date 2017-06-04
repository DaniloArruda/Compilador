/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.Stack;

/**
 *
 * @author danilo
 */
public class AnalisadorSemantico {
    
    // chamado na declaração de variáveis
    public boolean isIdentificadorNovo(Simbolo simbolo, Stack<Simbolo> tabelaSimbolo) {
        return tabelaSimbolo.search(simbolo) == -1;
    }
    
    // chamado no uso de variáveis
    public Simbolo obterSimbolo(String nome, Stack<Simbolo> tabelaSimbolo, int escopo) {
        if (escopo == -1)
            return null;
        
        Simbolo simbolo = new Simbolo(escopo);
        simbolo.setNome(nome);
        
        int posicao = tabelaSimbolo.search(simbolo);
        if (posicao == -1)
            return this.obterSimbolo(nome, tabelaSimbolo, --escopo);
        
        Simbolo simboloAchado = tabelaSimbolo.get(tabelaSimbolo.size() - posicao);
        return simboloAchado;
    }

    public Tipo obterTipo(Token token, Stack<Simbolo> tabelaSimbolo, int escopoAtual) {
       if (token.getTipo() == Tipo.INTEIRO) return Tipo.INT;
       if (token.getTipo() == Tipo.PONTOFLUTUANTE) return Tipo.FLOAT;
       if (token.getTipo() == Tipo.CARACTER) return Tipo.CHAR;
       
        if (token.getTipo() == Tipo.IDENTIFICADOR) {
           Simbolo simbolo = this.obterSimbolo(token.getLexema(), tabelaSimbolo, escopoAtual);
           if (simbolo != null)
               return simbolo.getTipo();
           else
               throw new RuntimeException("o identificador '" + token.getLexema() + "' não foi declarado.");
        }
        return null;
    }

    public boolean tiposCompativeis(Tipo tipoRecebe, Tipo tipoAtribui) {
        if (tipoRecebe == tipoAtribui) return true;
        if (tipoRecebe == Tipo.CHAR || tipoAtribui == Tipo.CHAR) return false;
        
        return tipoRecebe == Tipo.FLOAT && tipoAtribui == Tipo.INT;
    }

    public Tipo calcularTipos(Tipo tipo, Operador operador, Tipo tipo2) {
        if (tipo == Tipo.FLOAT || tipo2 == Tipo.FLOAT) return Tipo.FLOAT;
        
        if (operador == Operador.DIVISAO) return Tipo.FLOAT;
        
        return Tipo.INT;
    }
}

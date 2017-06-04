/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author danilo
 */
public class LexicalException extends RuntimeException {

    public LexicalException(int linha, int coluna, String message) {
        
        super("Erro na "
                + "linha " + linha 
                + " coluna " + (coluna - 1) 
                + ": " + message 
                + " \nÚltimo token lido: ");
        
    }
    
}

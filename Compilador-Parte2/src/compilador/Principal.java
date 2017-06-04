/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.IOException;

/**
 *
 * @author dasilva
 */
public class Principal {
    public static void main(String[] args) throws IOException {
        
        /*if (args.length == 1) {
            String caminhoArquivo = args[0];
            LeitorArquivo leitor = new LeitorArquivo();

            String texto = leitor.ler(caminhoArquivo);

            Compilador compilador = new Compilador(texto);

            compilador.programa();
        } else 
            System.out.println("Número inesperado de parâmetros.");*/
        
        
        LeitorArquivo leitor = new LeitorArquivo();

        String texto = leitor.ler("/home/danilo/codigo.lg");

        Compilador compilador = new Compilador(texto);

        compilador.programa();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author dasilva
 */
public class LeitorArquivo {
    private FileReader arquivo;
    private BufferedReader leitor;

    public LeitorArquivo() {
    }

    
    public String ler(String caminhoArquivo) throws FileNotFoundException, IOException {
        String texto = "";
        
        this.arquivo = new FileReader(caminhoArquivo);
        this.leitor = new BufferedReader(arquivo);
        
        String linha = leitor.readLine();
        while (linha != null) {
            texto += linha + "\n";
            linha = leitor.readLine();
        }
        
        arquivo.close();
        return texto;
    }

}

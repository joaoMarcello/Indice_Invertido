/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash_application;
import java.util.ArrayList;
import sortMethods.*;

/**
 *  Classe que representa um palavra de um documento. Como precisa ser ordenada, implementa KeyElement
 * 
 * @author JM
 */
public class Palavra implements KeyElement {
    
    //classe interna privada que vai guardar a quantidade de vezes que um documento com um certo id possui essa palavra
    private class Doc{
        int id;   //id documento
        int quant = 1;   //quantidade de vezes que a palavra aparece no documento
        
        //construtor
        Doc(int id){
            this.id = id;
        }
    }
    
    // ----------- ATRIBUTOS -----------
    // representa a palavra
    String word;
    // a chave da palavra
    int key;
    int idDoc;
    // lista que possui todos os documentos a qual essa palavra pertence e a quantidade em que a aparece em cada documento
    ArrayList<Doc> listaDocs;
    
    
    
    // -------- CONSTRUTOR --------------------
    // Recebe uma string que representa a palavra e o id do documento a qual ela pertence
    public Palavra(String s, int idDoc){
        //transformando a palavra em minuscula
        word = s.toLowerCase();
        key = 0;
        listaDocs = new ArrayList<Doc>();
        this.idDoc = idDoc;

        //pegando o ultimo caractere
        char last = word.charAt(word.length() - 1);
        
        //checando se o ultimo caractere eh diferente de uma letra ou um numero
        if( (last > 'z' || last < 'a') && (last < '0' || last > '9')  ){
            //se sim, remove ele da palavra
            word = word.substring(0, word.length() - 1);
        }
        
        //pegando o primeiro caractere
        char f = word.charAt(0);
        //checando se o primeiro caractere eh diferente de uma letra ou um numero
        if( (f > 'z' || f < 'a') && (f < '0' || f > '9')  ){
            //se sim, remove ele da palavra
            word = word.substring(1, word.length());
        }
        
        //gerando a chave
        key = generateKey();
        
        // adicionando o id do arquivo na lista de Doc
        this.addIdDoc(idDoc);
    }
    
    
    // ------------ METODOS ------------------------------
    
    // Calcula o valor da chave. A chave eh o somatorio da multiplicacao do valor da tabela ASCII 
    // do caractere i multiplicado pela sua posicao na string + 1
    int generateKey(){
        int k = 0;
        
        // Realizando o somatorio. Cada caractere eh multiplicado pelo seu peso na palavra
        for(int i = 0; i < word.length(); i++){
            //k += (i + 1) * word.charAt(i);
            k += word.charAt(i);
        }
        
        //retorna a chave
        return k;
    }
    
    
    // Adiciona o id do documento que possui essa palavra em uma lista. A quantidade de vezes que a palavra aparece 
    // no documento tambem eh salvo.
    public void addIdDoc(int idDoc){
        //percorrendo a lista de Docs
        for(Doc d : listaDocs)
            if(d.id == idDoc){  // se ja existia um Doc com o id especificado
                d.quant++;   // incrementa a quantidade de vezes que a palavra aparece no documento 
                return;
            }
        
        // eh um novo documento, entao adicionamos na lista de Docs
        Doc doc = new Doc(idDoc);
        listaDocs.add(doc);
    }
    
    
    // Retorna a quantidade de vezes que essa palavra aparece em um determinado documento.
    public int quantVezesApareceNoDoc(int idDoc){
        for(Doc d : listaDocs)
            if(d.id == idDoc)
                return d.quant;
        return 0;
    }
    
    
    // Retorna a quantidade de documentos que possuem essa palavra
    public int quantDocsPossuemEssaPalavra(){
        return listaDocs.size();
    }
    
    
    // Funcao usada para adiionar essa palavra em um novo hash. Eh necessaria pois so a palavra sabe quantas vezes ela aparece
    // em cada documento. Recebe um hash como parametro
    public void addPalavrasHash(Hash_Linear_Div h){
        for(Doc d : listaDocs){
            for(int i = 0; i < d.quant; i++){
                Palavra p = new Palavra(word, d.id);
                h.insert(p);
            }
        }
    }
    
    
    // Retorna a chave da palavra usada para ordenacao
    @Override
    public int getKey() {
       return key;
    }

    
    // Funcao de comparacao usada para ordenacao
    @Override
    public int compare(KeyElement e) {
        int r = word.compareToIgnoreCase(((Palavra)e).word);
        
        if(r < 0)
            return -1;
        else if(r > 0)
            return 1;
        return 0;
    }
    
    
    // Funcao usada para imprimir os dados da palavra.
    // retorna uma string que contem os documentos que possuem essa palavra e a quantidade de vezes que 
    // essa palavra aparece no documento
    public String getDocAndQuantforPrint(){
        int i = 0;
        String s = "";
        
        for(Doc d : listaDocs){
            s =  s.concat("" + d.quant + " " + d.id + (i + 1 < listaDocs.size() ? ", " : "") );
            i++;
        }
        return s;
    }
    
    
    // Funcao usada para imprimir os dados da palavra.
    // retorna uma string que contem os documentos que possuem essa palavra e a quantidade de vezes que 
    // essa palavra aparece no documento
    public String getDocAndQuantforPrint(Document[] doc){
        int i = 0;
        String s = "";
        
        for(Doc d : listaDocs){
            s = s + doc[d.id - 1].path + " " + d.quant + (i + 1 < listaDocs.size() ? ", " : "");
            i++;
        }
        return s;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash_application;

import sortMethods.Ordenator;

/**
 *      Classe que representa o indice invertido ordenado. O indice invertido eh uma estrutura que contem cada palavra que aparece
 *  pelo menos uma vez em uma lista de documentos.
 * 
 * @author JM
 */
public class InvertedIndex_Ord {
    // ---------- ATRIBUTOS -----------
    
    // Vetor que guarda todas as palavras nao repetidas de todos os documentos
    Palavra v[];
    
    
    // --------- CONSTRUTOR 1 -------------
    // cria o indice invertido a partir de um hash_Linear ou hash_Quadratico
    public InvertedIndex_Ord(Hash_Linear_Div h, Document docs[]){
        //criando um vetor de Palavra do tamanho da quant. de elementos no hash
        v = new Palavra[h.quantElm];
        int i = 0;

        //adicionando cada palavra do hash no vetor
        for(Palavra p : h.table){
            // Se existe a palavra...
            if(p != null){ 
                v[i] = p;  // adiciona a palavra no vetor
                i++;   // incrementando o indice do vetor
            }
        }

        //ordenado o vetor de palavras usando quicksort
        Ordenator.medianQuickSort(v, 0, v.length - 1);
    }
    
    
    // --------- CONSTRUTOR 2 -------------
    // cria o indice invertido a partir de um Hash_TreeRB
    public InvertedIndex_Ord(Hash_TreeRB_Div h, Document docs[]){
        //criando um vetor de Palavra do tamanho da quant. de elementos no hash
        v = new Palavra[h.quantPalavras()];
        
        int i = 0;
        
        // percorrendo cada arvore da tabela de TreeRB do hash
        for(TreeRB t : h.tabela){
            // se a arvore nao estiver vazia...
            if(t != TreeRB.NIL){
                // adiciona as palavras da arvore no vetor de Palavras
                i = TreeRB.addPalavrasHashTree(t, v, i);
            }
        }
        
        //ordenado o vetor de palavras usando quicksort
        Ordenator.medianQuickSort(v, 0, v.length - 1);
    }
    
    
    // ------------------ METODOS --------------------------------
    
    // Imprime o indice invertido
    public void print(Document[] docs){
        System.out.println("\n+----------- INDICE INVERTIDO ------------+");
        // percorrendo cada palavra no vetor de Palavras
        int i = 1;
        for(Palavra p : v){
            // imprimindo as informacoes
            System.out.print(i + " " + p.word + " " + p.getDocAndQuantforPrint(docs) + "\n");
            i++;
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash_application;

import sortMethods.KeyElement;

/**
 *  Classe que guarda as informacoes de um determinado documento. Como precisa ser ordenado, implementa KeyElement.
 * 
 * @author JM
 */
public class Document implements KeyElement {
    
    // variavel geradorora de ids para o documento
    private static int idDocs = 1;
    
    // ---------- ATRIBUTOS ---------
    
    // o identificador do documento. Cada documento vai ter um unico identificador
    int id;
    // a relevancia do documento dada uma consulta. Varia conforme a consulta
    double relevancia = 0;
    // o caminho do arquivo
    String path;
    
    
    // ---------- CONSTRUTOR -------------
    public Document(String path){
        this.path = path;
        id = idDocs;
        idDocs++;
    }
    
    
    // ----------- METODOS -------------------
    
    // Retorna a chave do documento usada para ordenacao
    @Override
    public int getKey() {
        return (int)relevancia;
    }

    // Funcao de comparacao usada para ordenacao
    // As comparacoes foram invertidas para que os documentos sejam ordenados em ordem decrescente
    // Assumimos que a variavel passada como parametro eh um Document
    @Override
    public int compare(KeyElement e) {
        if(relevancia < ((Document)e).relevancia)
            return 1;
        else if(relevancia > ((Document)e).relevancia)
            return -1;
        return 0;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash_application;

import java.util.ArrayList;

/**
 *  Interface que define os metodos que um indice invertido precisa implementar.
 * 
 * @author JM
 */
public interface InvertedIndex {
    // busca uma palavra na estrutura
    public Palavra search(String s);
    
    // retorna um vetor de documentos ordenados por relevancia dada uma consulta.
    // Recebe:
    //      * palavras: um arrayList que contem uma palavra da consulta em cada posicao da lista
    public Document[] getDocOrdenadosPorRelevancia(ArrayList<String> palavras);
    
    // Retorna a quantidade de palavras que tem no indice invertido
    public int quantPalavrasTemNoHash();
    
    // imprime as palavras do indice invertido
    public void print();
}

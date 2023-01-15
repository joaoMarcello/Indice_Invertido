/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash_application;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import sortMethods.Ordenator;

/**
 *      Classe que representa o indice invertido. O indice invertido eh uma estrutura que contem cada palavra que aparece
 *  pelo menos uma vez em uma lista de documentos.
 * 
 * @author JM
 */
public class InvertedIndex_LQ implements InvertedIndex {

    // ------------- ATRIBUTOS ------------------------
    // tabela hash
    Hash_Linear_Div hash;
    // vetor com os documentos
    Document docs[];
    
    long timeToCreateHash = 0;
    
    // --------------- CONTRUTOR ----------------------------
    public InvertedIndex_LQ(String paths[], int min, String tratCollision, String hashFunction) throws IOException{
        // lista que vai conter as palavras de uma linha de um arquivo de texto
        ArrayList<String> lista;
        // leitor de entradas
        Scanner input;
        // o hash que vai conter as palavras
        
        // selecionando o hash com as especificacoes informadas
        // se o hash tem que tratar as colisoes de forma linear...
        if(tratCollision.equals("linear")){  
            // se a funcao de hash tem que ser pelo metodo da divisao...
            if(hashFunction.equals("div"))  
                // h recebe um hash_linear_div
                hash = new Hash_Linear_Div();
            else
                // senao, h recebe um hash_linear_mult
                hash = new Hash_Linear_Mult();
        }
        // senao, o hash tem que tratar as colisoes de forma quadratica
        else{
            // se a funcao de hash tem que ser pelo metodo da divisao...
            if(hashFunction.equals("div"))
                // h recebe um hash_quad_div
                hash = new Hash_Quad_Div();
            else
                // senao, h recebe hash_quad_mult
                hash = new Hash_Quad_Mult();
        }
        
        // criando um vetor de documentos (a classe documento so guarda o id e o caminho do documento)
        docs = new Document[paths.length];
        
        // adicionando os documentos no vetor
        for(int i = 0; i < docs.length; i++)
            docs[i] = new Document(paths[i]);
        
        long tempoInicial = System.currentTimeMillis();
        
        for(int i =0; i < docs.length; i++){
            try {
                //abrindo o arquivo que esta no caminho paths[i]
                input = new Scanner(Paths.get(docs[i].path));
                
                //enquanto houver dados no arquivo
                while(input.hasNext()){
                    //lendo uma linha do arquivo
                    String s = input.nextLine();
                    
                    //transformando a linha em uma lista em que cada item eh uma palavra
                    lista = Hash_Application.getSeparatedString(s);

                    // percorrendo a lista que contem as palavras
                    for(String str : lista){
                        if(str.equals("") || str.equals(" ") || (str.charAt(0) >= '0' && str.charAt(0) <= '9') )
                            continue;
                        
                        //transformando a palavra num objeto do tipo Palavra
                        Palavra p = new Palavra(str, docs[i].id);
                        
                        //se a Palavra for menor que o tamanho minimo e for diferente de " "
                        if(p.word.length() <= min && !p.word.equals(" "))
                            //tenta inserir a palavra no hash
                            if (!hash.insert(p)){
                                
                                do{
                                    //caso nao consiga, aumenta o tamanho do hash
                                    hash.aumentarTamanho( hash.size * 2/3);
                                    //tenta inserir novamente
                                    //hash.insert(p);
                                }while(!hash.insert(p));
                            }
                    }

                }
                //fechando o arquivo
                input.close();
                
                long tempoFinal = System.currentTimeMillis();
                
                timeToCreateHash = tempoFinal - tempoInicial;

            } catch (IOException ex) {  // nao conseguiu abrir o arquivo
                System.err.println("Erro ao abrir " + docs[i].path);
                throw ex;
            }
        }
    }
    
    
    // ------------------ METODOS --------------------------------
    
    // Procura uma palavra no indice invertido
    @Override
    public Palavra search(String s){
        return hash.search(s);
    }
    
    
        // Recebe uma string.
    // Retorna um arrayList contendo as palavras da string em cada indice
    public static ArrayList<String> getSeparatedString(String s){
        ArrayList<String> lista = new ArrayList<String>();
        
        while(true){
            boolean find = false;
            
            //percorrendo a string caractere por caractere
            for(int i = 0; i < s.length(); i++){
                //se encontrou espaco
                if(s.charAt(i) == ' '){   
                    lista.add(s.substring(0, i));  //adiciona a substring no array
                    s = s.substring(i + 1);  //corta a string ate o ponto onde achou o ' '
                    find = true; //sinaliza que achou
                    break;
                }
            }
            
            if(!find){  //se nao achou nenhum espaco...
                lista.add(s);
                break;  //sai do loop;
            }
        }
        
        return lista;
    }
    
    @Override
    public int quantPalavrasTemNoHash(){
        return hash.quantElm;
    }
    
    // -------- FUNCOES USADAS PARA CALCULO DO PESO --------------
    
    // Retorna o numero de palavras diferentes em um determinado documento. Recebe o id do documento como parametro
    public int quantPalavrasDiferenteNoDoc(int idDoc){
        // a quantidade de palavras diferentes
        int q = 0;
        
        // percorrendo cada palavra do vetor de palavras
        for(int i = 0; i < hash.table.length; i++)
            // se o documento possui a palavra atual...
            if(hash.table[i] != null && hash.table[i].quantVezesApareceNoDoc(idDoc) > 0)
                q++;  // incrementa a quantidade de palavras
        
        return q;
    }
    
    
    // Retorna a quantidade de vezes que uma determinada palavra aparece em um documento. 
    // Recebe o id do documento como parametro
    public int quantVezesTermoApareceNoDoc(int idDoc, String palavra){
        Palavra p;
        
        p = hash.search(palavra);
        
        if(p != null){
            return p.quantVezesApareceNoDoc(idDoc);
        }
        
        // se nao achou a palavra...
        return 0;
    }
    
    
    // Retorna a quantidade de documentos na colecao que possuem uma determinada palavra. 
    // Recebe uma string como parametro
    public int quantDocsPossuemPalavra(String palavra){
        Palavra p;
        
        p = hash.search(palavra);
        
        if(p != null){
            return p.quantDocsPossuemEssaPalavra();
        }
        
        // se nao encontrou a palavra...
        return 0;
    }
    
    //----------------------------------------------------------------------------------------------------------
    
    
    // Retorna o peso de uma palavra em um determinado documento.
    // Recebe o id do documento e uma palavra
    public double weigth(int idDoc, String palavra){
        // o peso da palavra no documento
        double w = 0;
        // quantidade de vezes que a palavra aparece nesse documento
        int f = quantVezesTermoApareceNoDoc(idDoc, palavra);
        // quantidade de documentos na colecao que possuem essa palavra
        double d = quantDocsPossuemPalavra(palavra);
        // quantidade de documentos na colecao
        int n = docs.length;
        
        // se nenhum documento possui essa palavra...
        if(d == 0)
            return 0; //o peso eh zero!
        
        
        // calculando o peso
        w = f * (Math.log(n)/ d);
        
        return w;  // retorna o peso
    }
    
    
    // calcula a relevancia de um documento dada as palavras de uma consulta.
    // Recebe: o id do documento
    //         as palavras da consulta separadas em um arrayList
    public double calcularRelevanciaDoc(int idDoc, ArrayList<String> consulta){
        // a relevancia do documento
        double r = 0;
        // a quantidade de palavras diferentes no documento
        double n = quantPalavrasDiferenteNoDoc(idDoc);
        // somatorio dos pesos de cada palavra da consulta
        double somatorio = 0;
        
        // calculando o somatorio dos pesos de cada palavra da consulta
        for(String palavra : consulta)
            somatorio += weigth(idDoc, palavra);
        
        // calculando a relevancia
        r = (1/n) * somatorio;
        
        return r;  // retorna a relevancia do documento
    }
    
    
    // Retorna os documentos ordenados pela relevancia dada as palavras de uma consulta.
    // Recebe: as palavras da consulta separadas em um arrayList 
    @Override
    public Document[] getDocOrdenadosPorRelevancia(ArrayList<String> palavras){
        // calculando a relevancia de cada documento
        for(int i = 0; i < docs.length; i++)
            docs[i].relevancia = this.calcularRelevanciaDoc(docs[i].id, palavras);
        
        // ordenando os documentos pela sua relevancia em ordem decrescente
        Ordenator.medianQuickSort(docs, 0, docs.length - 1);
        
        return docs;  // retorna os documentos ordenados
    }
    
    
    public void print(){
        InvertedIndex_Ord ind = new InvertedIndex_Ord(hash, docs);
        ind.print(docs);
    }

}

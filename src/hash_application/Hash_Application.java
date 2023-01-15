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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Classe que possui a funcao Main e alguns métodos utilitarios.
 * 
 * @author JM
 */
public class Hash_Application {
    
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
    
    
    /**   +------------------------------------------+
     *    |                 MAIN                     |
     *    +------------------------------------------+
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String paths[] = {"doc1.txt", "doc2.txt", "doc3.txt", "doc4.txt"};
        Scanner input = new Scanner(System.in);
        int numDocs = -1;
        
        
        // solicita ao usuario a quantidade de arquivos que deverao ser analisados
        do{
            System.out.println(" Informe a quantidade de arquivos:");
            numDocs = input.nextInt();
        }while(numDocs <= 0);  // a quantidade tem que ser positiva
        
        // criando um vetor que vai guardas os caminhos para os arquivos de texto
        paths = new String[numDocs];
        
        input.nextLine();
        // lendo os caminhos para os arquivos e os guardando no vetor
        for(int i = 0; i < numDocs; i++){
            System.out.println(" Informe o nome do arquivo " + (i + 1) + ": ");
            paths[i] = input.nextLine();
        }

        String funcaoHash, tratCollision;
        int c = 10;
        
        // solicitando a forma como o hash deve ser criado (tem que ser informado o tamanho minimo de caracteres permitido
        // em uma palavra, a forma de tratamento de colisao e a funcao hash. 
        System.out.println(" Informe a funcao de hash utilizada no hash [div/mult]:");
        funcaoHash = input.nextLine();
        System.out.println(" Informe o metodo de tratamento de colisao utilizada no hash [linear/quad/tree]:");
        tratCollision = input.nextLine();
        System.out.println(" Informe um tamanho C. Palavras menores que C caracteres nao serao consideradas:");
        c = input.nextInt();
        
        InvertedIndex ind;
        // se o tratamento de colisao tem que ser usando arvore rubro negra...
        if(tratCollision.equals("tree")){
            try{
                // chama a funcao que usa um hashTreeRB para criar um indice invertido
                ind = new InvertedIndex_TreeRB(paths, c, funcaoHash);
            }catch(IOException ex){
                System.out.println("Houve um problema ao abrir um arquivo");
                return;
            }
        }
        else{
            try{
                // chama a funcao que usa um hashLinear ou hashQuad para criar um indice invertido
                ind = new InvertedIndex_LQ(paths, c, tratCollision ,funcaoHash);
            }catch(IOException ex){
                System.out.println("Houve um problema ao abrir um arquivo");
                return;
            }
        }

        
        int option = -1;
        double limiar = 0.03;
        ArrayList<String> consulta;
        Document doc[];
        
        do{
            
            System.out.println("\n+-----------------------------------+");
            System.out.println("|          MENU PRINCIPAL           |");
            System.out.println("+-----------------------------------+");
            System.out.println("|    1- Realizar consulta           |");
            System.out.println("|    2- Imprimir indice invertido   |");
            System.out.println("|    3- Sair                        |");
            System.out.println("+-----------------------------------+");
            
            System.out.println("\n Informe a sua opcao:");
            // lendo a opcao do usuario
            option = input.nextInt();
            
            input.nextLine();
            
            switch(option){
                // realiza uma consulta. Os documentos sao ordenados de forma decresente por ordem de relevancia dada uma consulta
                case 1:
                    System.out.println(" Informe sua consulta: ");
                    // lendo a consulta, separando suas palavras e coloca num arrayList em que cada indice possui uma palavra da consulta
                    consulta = Hash_Application.getSeparatedString(input.nextLine());
                    
                    // doc recebe um vetor de documentos ordenados de forma decrescente por ordem de relevancia
                    doc = ind.getDocOrdenadosPorRelevancia(consulta);
                    
                    System.out.println("\n---------- Documentos Ordenados Por Relevancia ----------");
                    System.out.println("Limiar: " + limiar + "  (documentos com relevancia menor que o limiar nao serao mostrados)\n");
                    
                    // percorrendo o vetor de Document
                    for(int i = 0; i < doc.length; i++){
                        // se a relevancia do documento for maior que o limiar estabelecido...
                        if(doc[i].relevancia >= limiar)
                            // imprime o caminho e a relevancia do arquivo
                            System.out.println(doc[i].path + "   Relevancia: " + doc[i].relevancia);
                    }
                    break;
                // imprime o indice invertido
                case 2:
                    ind.print();
                    break;
                // progrma encerrado
                case 3:
                    System.out.println("Programa encerrado.");
                    break;
                // opcao invalida
                default:
                    System.out.println("Opcao invalida!");
            }
            
        }while(option != 3);
        
    }
    
}

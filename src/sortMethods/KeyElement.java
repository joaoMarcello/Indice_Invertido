/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortMethods;

/**  Elementos em um vetor que querem ser ordenados usando a classe
 * Ordenator devem implementar essa interface.
 *
 * @author JM
 */
public interface KeyElement {
    //a chave do elemento
    int getKey();   
    
    //funcao de comparacao. Deve retornar 1, 0 ou -1 de acordo com a relacao entre as chaves dos elementos comparados
    int compare(KeyElement e);  
}

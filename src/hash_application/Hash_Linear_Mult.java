/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash_application;

/**
 *      Classe reponsavel por guardar cada palavra encontrada em um documento em um hash fechado (enderecamento aberto), 
 *      metodo da multiplicacao usada como funcao Hash e tratamento de colisoes linear.
 * 
 *      Para tamanho do Hash, eh recomendavel um tamanho 2^p sendo p um numero inteiro positivo
 * 
 * @author JM
 */
public class Hash_Linear_Mult extends Hash_Linear_Div {
    
	// ----------- CONTRUTOR 1 ----------------------
    public Hash_Linear_Mult(){
        super(32);
    }
    
	// ----------- CONTRUTOR 1 ----------------------
    public Hash_Linear_Mult(int size){
        super(size);
    }
    
	
    // --------------- MÈTODOS ----------------------

    // funcao usada para o calculo do indice que o dado vai se colocdo no vetor
    // Recebe a chave do dado
    @Override
    int hashFunction(int n){
        double A = ( (Math.sqrt(5L) - 1L)/2L );
        double h = size * ((n * A) % 1);
        
        return (int)Math.floor(h);
    }
    
}

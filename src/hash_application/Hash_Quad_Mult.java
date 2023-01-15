/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash_application;

/**
 *      Classe reponsavel por guardar cada palavra encontrada de um documento em um hash fechado (enderecamento aberto), 
 *      metodo da multiplicacao usada como funcao Hash e tratamento de colisoes quadratico. (Eh filha de Hash_Quad_Div)
 * 
 *      Para tamanho do Hash, eh recomendavel um tamanho 2^p sendo p um numero iteiro positivo
 * 
 * @author JM
 */
public class Hash_Quad_Mult extends Hash_Quad_Div {
    
    // ------- CONSTRUTOR 1 -----------
    public Hash_Quad_Mult(){
        super(32);
    }
    
    
    // ------- CONSTRUTOR 2 -----------
    public Hash_Quad_Mult(int size){
        super(size);
    }
    
    // sobreescrevendo a funcao de hash
    @Override
    public int hashFunction(int n){
        double A = ( (Math.sqrt(5L) - 1L)/2L );
        double h = size * ((n * A) % 1);
        
        return (int)Math.floor(h);
    } 
   
}

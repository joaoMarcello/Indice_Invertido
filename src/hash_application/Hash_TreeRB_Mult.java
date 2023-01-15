/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash_application;

/**
 *  Classe que representa um hash que trata as colisoes usando uma arvore rubro negra e cuja funcao de hash
 *  eh pelo metodo da multiplicacao. (Eh filha da classe Hash_TreeRB_Div)
 * 
 * @author JM
 */
public class Hash_TreeRB_Mult extends Hash_TreeRB_Div {
    
    // ----- CONTRUTOR --------
    public Hash_TreeRB_Mult(){
        super();
    }
    
    
    // ------- METODOS -----------
    
    // mudando a funcao hash para o metodo da multiplicacao
    @Override
    int hashFunction(int key){
        double A = ( (Math.sqrt(5L) - 1L)/2L );
        double h = size * ((key * A) % 1);
        
        return (int)Math.floor(h);
    }
}

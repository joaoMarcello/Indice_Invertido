/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash_application;

/**
 *      Classe reponsavel por guardar cada palavra encontrada em um documento em um hash fechado (enderecamento aberto), 
 *      metodo da divisao usada como funcao Hash e tratamento de colisoes quadratico. (Eh filha de Hash_Linear_Div)
 *      
 *      Para tamanho do Hash, eh recomendavel um numero primo nao muito proximo a uma potencia exata de 2.
 * 
 * @author JM
 */
public class Hash_Quad_Div extends Hash_Linear_Div {
    
    // ---------- CONTRUTOR 1 ----------------
    public Hash_Quad_Div(){
        super();
    }
    
    
    // ---------- CONTRUTOR 2 ----------------
    public Hash_Quad_Div(int size){
        super(size);
    }
	
	
    // ---------- MÉTODOS -------------------------
    
    // insere uma palavra na tabela hash. As colisoes sao tratadas de forma quadratica
    @Override
    public boolean insert(Palavra e){
        int i = 0;
        int ind = hashFunction(e.key);

        
        do{
            // se a posicao esta vazia...
            if(table[ind] == null){
                // adiciona a palavra na tabela hash
                table[ind] = e;
                // incrementa a quantidade de palavras diferentes no hash
                quantElm++;
                return true;
            }
            // se encontrou a mesma palavra no hash...
            else if(table[ind].word.equals(e.word)){
                // incrementando a quantidade de colisoes
                quantCollision++;
                
                // adiciona o id do documento da Palavra e no lista de documentos da Palavra que
                // estah em table[ind]
                table[ind].addIdDoc(e.idDoc);
                
                return true;
            }
            // senao, calcula o proximo indice
            else{
                i++;
                ind = hashFunction(ind + i);
            }
        }while(i <= size);
        
        // nao conseguiu inserir!
        return false;
    }
    
    // Procura um elemento no hash. Um objeto do tipo Palavra eh recebido como parametro
    // Retorna: a palavra, caso encontre, ou null
    @Override
    public Palavra search(Palavra p){
        int i = 0;
        // calculando o possivel indice onde o elemento pode estar
        int ind = hashFunction(p.key);
        
        do{
            // se a posicao nao estiver vazia e a palavra eh igual a palavra que queremos encontrar...
            if(table[ind] != null && table[ind].word.equals(p.word))
                return table[ind];
            else{
                i++;
                // calcula o novo indice onde o elemento pode estar
                ind = hashFunction(ind + i);
            }
        }while(i <= size);
        
        // nao conseguiu encontrar
        return null;
    }
    
    // Procura um elemento no hash. Uma string eh recebida como parametro
    // Retorna: a palavra, caso encontre, ou null
    @Override
    public Palavra search(String s){
        // criando um objeto do tipo Palavra com a string e um valor aleatorio para o id do documento
        // ja que a busca nao leva o id em consideracao
        Palavra p = new Palavra(s, 1);
        
        return search(p);
    } 
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash_application;

/**
 *  Classe que representa um hash que trata as colisoes usando uma arvore rubro negra e cuja funcao de hash
 *  eh pelo metodo da divisao.
 * 
 * @author JM
 */
public class Hash_TreeRB_Div {
    
    // ----------- ATRIBUTOS -----------------
    
    // tamanho da tabela hash
    int size = 64;
    // a tabela
    TreeRB tabela[];
    
    int quantCollision = 0;
    
    // ---------- CONSTRUTOR ---------------
    public Hash_TreeRB_Div(){
        // criando a tabela hash (eh um vetor em que cada posicao eh uma arvore rubro negra)
        tabela = new TreeRB[size];
        
        // adicionando arvores RB vazias no vetor hash
        for(int i =0; i < tabela.length; i++)
            tabela[i] = TreeRB.NIL;
    }
    
    
    // ----------- METODOS ---------------------
    
    // funcao hash
    int hashFunction(int key){
        return key % size;
    }
    
    
    // insere um objeto do tipo Palavra no vetor hash
    public boolean insert(Palavra p){

        // calculando o indice em que p deve ficar
        int j = hashFunction(p.getKey());
        
        // inderindo p na arvore RB que esta no indice j do vetor hash
        if(tabela[j] != TreeRB.NIL)
            quantCollision++;
        
        tabela[j] = TreeRB.inserir(tabela[j], p);
        
        return true;
    }
    
    // Procura um elemento no hash. Um objeto do tipo Palavra eh recebido como parametro
    // Retorna: a palavra, caso encontre, ou null
    public Palavra search(Palavra p){
        // calculando o possivel indice onde o elemento pode estar na tabela
        int ind = hashFunction(p.key);
        TreeRB node;
        
        // procurando o elemento na arvore que esta no indice calculado
        node = TreeRB.search(tabela[ind], p);
        
        // se encontrou...
        if(node != TreeRB.NIL)
            // retorna o objeto do tipo Palavra encontrado
            return node.word;
        //nao encontrou
        return null;
    }
    
    // Procura um elemento no hash. Uma string eh recebida como parametro
    // Retorna: a palavra, caso encontre, ou null
    public Palavra search(String s){
        // criando um objeto do tipo Palavra com a string e um valor aleatorio para o id do documento
        // ja que a busca nao leva o id em consideracao
        Palavra p = new Palavra(s, 1);
        
        return search(p);
    }
    
    
    // retorna a soma da quantidade de palavras de cada arvore RB da tabela hash.
    public int quantPalavras(){
        int q = 0;
        
        // percorrendo cada arvore da tabela
        for(int i = 0; i < tabela.length; i++)
            // se a arvore nao estiver vazia...
            if(tabela[i] != TreeRB.NIL){
                // adiciona a quantidade de palavras da arvore a variavel q
                q += TreeRB.numNodes(tabela[i]);
            }
        
        return q;
    }
    
}

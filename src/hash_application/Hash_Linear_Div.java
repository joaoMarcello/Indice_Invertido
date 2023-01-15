/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash_application;

/**
 *      Classe reponsavel por guardar cada palavra encontrada em um documento em um hash fechado (enderecamento aberto), 
 *      metodo da divisao usada como funcao Hash e tratamento de colisoes linear.
 * 
 *      Para tamahno do Hash, eh recomendavel um numero primo nao muito proximo a uma potencia exata de 2.  
 * 
 *  Obs.: As palavras nao estao ordenadas e podem existir espacos vazios. Por esse motivo, se faz necessario a criacao 
 *        de uma nova estrutura que contem somente as palavras ordenadas em ordem alfabetica. 
 *        Essa estrutura vai ser o indice invertido ordenado.
 * 
 * @author JM
 */
public class Hash_Linear_Div{
    
    // ------- ATRIBUTOS ------------
    
    // tamanho do hash
    protected int size = 43;
    // vetor que vai guardar as Palavras
    Palavra table[];
    // quantidade de palavras diferentes no hash
    int quantElm = 0;
    // quantidade de colisoes
    int quantCollision = 0;
    
    // -------- CONTRUTOR 1 --------
    public Hash_Linear_Div(){
        table = new Palavra[size];
        for(int i = 0; i < size; i++ )
            table[i] = null;
    }
    
    // ------- CONTRUTOR 2 --------- 
    // (recebe o tamanho do hash como parametro)
    public Hash_Linear_Div(int size){
        this.size = size;
        table = new Palavra[size];
        
        for(int i = 0; i < size; i++ )
            table[i] = null;
    }
    
    
    // ----------- METODOS --------------------
    
    // funcao hash
    int hashFunction(int n){
        return n % size;
    }
    
    
    // Inserir palavra. (Trata as colisoes de forma linear)
    public boolean insert(Palavra e){
        int i = 0;
        
        do{
            // calculando o hash
            int j = hashFunction(e.getKey() + i);
            
            // se for a primeira vez adicionando uma palavra
            if(table[j] == null){
                // adiciona a palavra no hash na posicao que foi calculada
                table[j] = e;
                // incrementando a quant. de elementos no hash
                quantElm++;
                
                return true;
            }
            //colisao: checando se palavra ja foi adicionada
            else if(table[j].word.equals(e.word)){
                // incrementando a quantidade de colisoes que ocorreram
                quantCollision++;
                
                //se sim, adiciona o documento que possui essa palavra na lista de documentos da palavra
                table[j].addIdDoc(e.idDoc);
                return true;
            }
            //colisao: eh uma palavra nova
            else{
                i++;
            }
                        
        }while(i <= size);
        
        // nao conseguiu inserir!
        return false;
    }
    
    // Procura um elemento no hash. Um objeto do tipo Palavra eh recebido como parametro
    // Retorna: a palavra, caso encontre, ou null
    public Palavra search(Palavra p){
        int i = 0;
        
        do{
            // calculando o possivel indice onde o elemento pode estar
            int j = hashFunction(p.key + i);
            
            // se a posicao nao estiver vazia e a palavra eh igual a palavra que queremos encontrar...
            if(table[j] != null && table[j].word.equals(p.word) ){
                // retorna a Palavra encontrada
                return table[j];
            }
            else
                i++;
                        
        }while(i <= size);
        
        // nao conseguiu encontrar a palavra
        return null;
    }
    
    // Procura um elemento no hash. Uma string eh recebida como parametro
    // Retorna: a palavra, caso encontre, ou null
    public Palavra search(String str){
        // criando um objeto do tipo Palavra com a string e um valor aleatorio para o id do documento
        // ja que a busca nao leva o id em consideracao
        Palavra palavra = new Palavra(str, 1);
        
        return search(palavra);
    }

    
    // Aumenta o tamanho do hash
    public boolean aumentarTamanho(int quant){
        // se a quantidade informada for maior do que 0
        if(quant > 0){
            // criando um novo hash com o tamanho do atual + quantidade informada
            Hash_Linear_Div newHash;
            
            // escolhendo o tipo do novo hash
            if(this instanceof Hash_Linear_Mult)
                newHash = new Hash_Linear_Mult(size + quant);
            
            else if(this instanceof Hash_Quad_Mult)
                newHash = new Hash_Quad_Mult(size + size);
            
            else if(this instanceof Hash_Quad_Div)
                newHash = new Hash_Quad_Div(size + quant);
            
            else
                newHash = new Hash_Linear_Div(size + quant);
            
            // iterando em cada posicao do vetor hash
            for(int i = 0; i < size; i++)
                // se esta ocupado...
                if(table[i] != null){
                    // adiciona a palavra no novo hash
                    table[i].addPalavrasHash(newHash);
                }
            
            // fazendo o hash ficar com as mesmas propriedades do novo hash
            size = newHash.size;
            table = newHash.table;
            quantElm = newHash.quantElm;
            quantCollision += newHash.quantCollision;
            
            return true;  // conseguiu aumentar o tamanho do hash
        }
        
        // informou uma quantidade invalida!
        return false;
    }
    
    
    // imprime as informacoes do HashAberto
    void print(){
        System.out.println("\n\nTam: " + size);
        for(int i =0; i < size; i++)
            if(table[i] != null){
                System.out.print(i + ": " + table[i].getKey() + "  " + (table[i]).word + " " + table[i].getDocAndQuantforPrint());
                
                table[i].getDocAndQuantforPrint();
                System.out.println();
            }
    }
    
    
    // Retorna o numero de palavras diferentes em um determinado documento. Recebe o id do documento como parametro
    public int quantPalavrasDiferenteNoDoc(int idDoc){
        // a quantidade de palavras diferentes
        int q = 0;
        
        // percorrendo cada palavra do vetor de palavras
        for(int i = 0; i < table.length; i++)
            // se o documento possui a palavra atual...
            if(table[i] != null && table[i].quantVezesApareceNoDoc(idDoc) > 0)
                q++;  // incrementa a quantidade de palavras
        
        return q;
    }
    
}

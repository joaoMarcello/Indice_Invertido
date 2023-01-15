/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash_application;

/**
 * Classe que representa uma arvore Rubro Negra que adiciona objetos  do tipo Palavra.
 * 
 * @author JM
 */
public class TreeRB  {
    
    // definindo constantes para as cores
    private static final int RED = 1;
    private static final int BLACK = 0;
    
    // ----------- ATRIBUTOS --------------
    
    // o objeto do tipo Palavra que vai ser guardado no noh
    Palavra word;
    // ponteiros para o filho esquerdo, filho direito e o pai
    TreeRB esq, dir, pai;
    // cor do no
    int cor = RED;
    
    // valor padrao para no vazio. Deve ser utilizado para incializar uma instancia de treeRB
    public static TreeRB NIL = new TreeRB();
    
    
    // ------------- CONTRUTOR 1 ---------------
    public TreeRB(){
        word = null;
        esq = NIL;
        dir = NIL;
        pai = NIL;
        cor = BLACK;
    }
    
    
    // ------------- CONTRUTOR 2 ---------------
    // cria um no da arvore (receba um objeto do tipo Palavra como parametro)
    public TreeRB(Palavra p){
        super();
        this.word = p;

        esq = NIL;
        dir = NIL;
        pai = NIL;
        cor = BLACK;
    }

    
    // --------------- METODOS ---------------------
    
    // insere uma Palavra na arvore.
    // Parametros:
    //      * aux: a raiz da arvore que queremos inserir a palavra
    //      * p: a palavra que queremos inserir
    // Retorna:
    //      - a raiz da arvore com a Palavra inserida
    public static TreeRB inserir(TreeRB aux, Palavra p ){
        NIL.pai = NIL;
        
        TreeRB raiz = aux;
        TreeRB y = NIL;
        TreeRB x = aux;
        // criando um no que contem a Palavra p
        TreeRB z = new TreeRB(p);
        
        while(x != NIL){
            y = x;
            
            // se x eh igual a p, ou seja, ja existe essa palavra na arvore
            if(x.word.word.equals(p.word)){
                // adiciona o id do documento da palavra p em x
                x.word.addIdDoc(p.idDoc);
                // retorna a raiz da arvore
                return raiz;
            }
            // se a chave da palavra eh menor que a chave da palavra no noh x
            else if(z.word.key < x.word.key)
                // a palavra deve ser inserida na sub arvore esquerda, logo x recebe a sub arvore esquerda
                x = x.esq;
            else
                // a palavra deve ser inserida na sub arvore direita, logo x recebe a sub arvore direita
                x = x.dir;
        }

        // definindo o pai do no z como y
        z.pai = y;
        
        // se o pai eh nulo, significa que z eh o primeiro no da arvore
        if(y == NIL){
            // logo, z se torna a raiz da arvore
           raiz = z; 
        }
        // se a chave da palavra em z eh menor que a chave da palavra em y
        else if(z.word.key < y.word.key)
            // significa que z eh filho esquerdo de y
            y.esq = z;
        else
            // senao, z eh filho direito de y
            y.dir = z;
        
        z.esq = NIL;
        z.dir = NIL;
        z.cor = RED;
        
        // corrigindo qualquer quebra das regras da arvore RG que possa ter ocorrido
        raiz = insertFixUp(raiz, z);
        
        // retorna a raiz da arvore com o elemento inserido
        return raiz;
    }
    
    
    static private TreeRB insertFixUp(TreeRB t, TreeRB z){
        TreeRB y = NIL;
        
        // enquanto a cor do pai de z for vermelho...
        while(z.pai.cor == RED){
            // se o pai de z eh filho esquerdo do avo de z
            if(z.pai == ((z.pai).pai).esq){
                // y recebe o filho direito do avo de z, ou seja, o tio de z
                y = z.pai.pai.dir;
                
                //se o tio de z eh vermelho...
                if(y.cor == RED){  
                    // recolore tio, pai e avo de z
                    z.pai.cor = BLACK;
                    y.cor = BLACK;
                    z.pai.pai.cor = RED;
                    z = z.pai.pai;
                }
                // senao --> tio eh preto. Se z eh filho direito...
                else if(z == z.pai.dir){
                    z = z.pai;
                    t = rotacaoEsq(t, z);
                }
                else{
                    z.pai.cor = BLACK;
                    z.pai.pai.cor = RED;

                    t = rotacaoDir(t, z.pai.pai);
                }
            }
            else{
                // y recebe o filho esquerdo do avo de z, ou seja, o tio de z
                y = z.pai.pai.esq;
                
                // se o tio de z form vermelho...
                if(y.cor == RED){
                    // recolore tio, pai e avo de z
                    z.pai.cor = BLACK;
                    y.cor = BLACK;
                    z.pai.pai.cor = RED;
                    z = z.pai.pai;
                }
                // senao --> tio eh preto. Se z eh filho esquerdo...
                else if(z == z.pai.esq){
                    z = z.pai;
                    t = rotacaoDir(t, z);
                }
                else{
                    z.pai.cor = BLACK;
                    z.pai.pai.cor = RED;

                    t = rotacaoEsq(t, z.pai.pai); 
                }
            }
        }
        
        // fazendo a raiz da arvore ser preta
        t.cor = BLACK;
        return t;
    }
    // Corrige qualquer quebra das regras da arvore RG que possa ter ocorrido com uma insercao.
    // Parametros:
    //      * t: a raiz da arvore
    //      * z: o noh que foi inserido
    // Retorna:
    //      - a arvore corrigida.
    /*static private TreeRB insertFixUp(TreeRB t, TreeRB z){
        TreeRB y = NIL;
        
        // enquanto a cor do pai de z for vermelho...
        while(z.pai.cor == RED){
            // se o pai de z eh filho esquerdo do avo de z
            if(z.pai == ((z.pai).pai).esq){
                // y recebe o filho direito do avo de z, ou seja, o tio de z
                y = z.pai.pai.dir;
                
                //se o tio de z eh vermelho...
                if(y.cor == RED){  
                    // recolore tio, pai e avo de z
                    z.pai.cor = BLACK;
                    y.cor = BLACK;
                    z.pai.pai.cor = RED;
                    return insertFixUp(t, z.pai.pai);
                }
                // senao --> tio eh preto. Se z eh filho direito...
                else if(z == z.pai.dir){
                    z = z.pai;
                    t = rotacaoEsq(t, z);
                }
                z.pai.cor = BLACK;
                z.pai.pai.cor = RED;

                t = rotacaoDir(t, z.pai.pai);
            }
            else{
                // y recebe o filho esquerdo do avo de z, ou seja, o tio de z
                y = z.pai.pai.esq;
                
                // se o tio de z form vermelho...
                if(y.cor == RED){
                    // recolore tio, pai e avo de z
                    z.pai.cor = BLACK;
                    y.cor = BLACK;
                    z.pai.pai.cor = RED;
                    return insertFixUp(t, z.pai.pai);
                }
                // senao --> tio eh preto. Se z eh filho esquerdo...
                else if(z == z.pai.esq){
                    z = z.pai;
                    t = rotacaoDir(t, z);
                }
                z.pai.cor = BLACK;
                z.pai.pai.cor = RED;
                
                t = rotacaoEsq(t, z.pai.pai); 
            }
        }
        
        // fazendo a raiz da arvore ser preta
        t.cor = BLACK;
        return t;
    }*/
    
    
    // realiza uma rotacao a esquerda
    private static TreeRB rotacaoEsq(TreeRB t, TreeRB x){
        TreeRB y = x.dir;
        x.dir = y.esq;
        
        if(y.esq != NIL)
            y.esq.pai = x;
        
        y.pai = x.pai;
        
        if(x.pai == NIL){
            t = y;
        }
        else if(x == x.pai.esq){
            x.pai.esq = y;
        }
        else
            x.pai.dir = y;
        
        y.esq = x;
        x.pai = y;
        
        return t;
    }
    
    
    // realiza uma rotacao a direita
    private static TreeRB rotacaoDir(TreeRB t, TreeRB x ){
        TreeRB y = x.esq;
        x.esq = y.dir;
        
        if(y.dir != NIL)
            y.dir.pai = x;
        
        y.pai = x.pai;
        
        if(x.pai == NIL){
            t = y;
        }
        else if(x == x.pai.dir){
            x.pai.dir = y;
        }
        else
            x.pai.esq = y;
        
        y.dir = x;
        x.pai = y;
        
        return t;
    }
    
    // busca uma palavra em uma arvore rubro negra.
    public static TreeRB search(TreeRB t, Palavra p){
        // se a arvore nao esta vazia...
        if(t != TreeRB.NIL){
            // se encontrou a palavra...
            if(t.word.word.equals(p.word))
                // retorna o no onde encontrou a palavra
                return t;
            else if(p.key < t.word.key)
                return search(t.esq, p);
            else
                return search(t.dir, p);
        }
        
        // nao encontrou
        return TreeRB.NIL;
    }
    
    
    // exibe os dados da arvore em pre ordem 
    public static void preOrdem(TreeRB aux){
        if(aux != TreeRB.NIL){
            System.out.println(aux.word.word + " " + aux.word.getDocAndQuantforPrint() );
            preOrdem(aux.esq);
            preOrdem(aux.dir);
        }
    }
    
    
    // retorna o numero de nohs da arvore
    public static int numNodes(TreeRB t){
        int q = 0;
        
        if(t != TreeRB.NIL){
            // quantidade de nohs da sub arvore esquerda
            int nl = numNodes(t.esq);
            // quantidade de nohs da sub arvore direita
            int nr = numNodes(t.dir);
            return 1 + nl + nr;
        }
        
        return q;
    }
    
    // Copia as palavras da arvore para um vetor de palavras.
    // Parametros:
    //      * t: a raiz da arvore
    //      * v: o vetor de palavras
    //      * i: o indice do vetor aonde se deve comecar a insercao
    // Retorna:
    //      - o ultimo indice adicionado + 1
    public static int addPalavrasHashTree(TreeRB t, Palavra v[], int i){
        // se a arvore nao estiver vazia...
        if(t != TreeRB.NIL){
            // coloca a palavra no vetor no indice informado
            v[i] = t.word;
            // incrementa o indice
            i++;
            // adiciona as palavras da subarvore esquerda de t no vetor
            i = addPalavrasHashTree(t.esq, v, i);
            // adiciona as palavras da subarvore direita de t no vetor
            i = addPalavrasHashTree(t.dir, v, i);
        }
        
        // retorna o ultimo indice que adicionou + 1
        return i;
    }
}




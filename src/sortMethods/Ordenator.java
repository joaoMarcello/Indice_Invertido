/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortMethods;

/**  Possui metodos estaticos para ordenacao de vetor de KeyElements
 *
 * @author JM
 */
public class Ordenator {
    
    //ordena um vetor usando o metodo insertSort (similar a ordenacao de cartas de um jogador de poker).
    //eh bom para estruturas quase ordenadas
    static void insertSort(KeyElement[] v){
        
        //percorrendo o vetor a partir do segundo elemento ate o ultimo
        for(int j = 1; j < v.length; j++){ 
            //atribuindo o elemento atual(v[j]) na variavel key
            KeyElement key = v[j]; 
            
            //inicializando i como o indice do elemento anterior ao nosso elemento atual
            int i = j - 1; 
            
            //esse loop while vai percorrer o vetor da direita pra esquerda comecando do elemento anterior ao elemento atual
            //e vai procurar o melhor lugar para colocar o elemento atual
            while(i >= 0 &&  v[i].compare(key) == 1) {   //enquanto ainda nao chegou no prim. elem. do vetor e o elm. no vetor for maior que o elm. atual
                v[i + 1] = v[i];       //atribuindo o elm. maior que elm. atual na posicao do elm. atual 
                i--;          //decremento pra continuar a percorrer o vetor
            }
            
            v[i + 1] = key;          //colocando o elm. atual na posicao do ultimo elemento que foi maior do que ele.
        }
    }
    
    //Funcao usada para intercalar dois intervalos de um vetor em ordem crescente. (Utilizada no metodo mergeSort).
    //recebe um vetor e um intervalo (p <= q <= r). Quebra esse intervalo do vetor em dois pedacos: p ate q  e p+1 ate r.
    //assume que os dois pedacos estao ordenados em ordem crescente.
    //retorna o vetor com os dois pedacos intercalados e ordenados.
    private static void merge(KeyElement[] v,int p, int q, int r){
        //calculando o tamanho da primeira particao
        int n1 = q - p + 1;  
        
        //calculando tamanho da segunda particao
        int n2 = r - q;  
        
        //os vetores abaixo podem ser considerados pilhas 
        KeyElement[] left = new KeyElement[n1];         //criando o vetor que vai guardar os elementos da primeira particao  
        KeyElement[] right = new KeyElement[n2];        //criando o vetor que vai guardar os elementos da segunda particao

         //copiando os elementos do vetor v de p ate q para o vetor left
        for(int i = 0; i < n1; i++)  
            left[i] = v[p + i];  

        //copiando os elementos do vetor v de q+1 ate r para o vetor right
        for(int j = 0; j < n2; j++)
            right[j] = v[q + j + 1];  

        //inicializando as variaveis i e j com 0 para percorrer cada uma das pilhas
        int i = 0; 
        int j = 0;
        
        //comecando o processo de intercalar os vetores
        for(int k = p; k <= r; k++){
            //checando qual dos valores do topo das duas pilhas eh o menor
            if(i < n1 && (j >= n2 || left[i].compare(right[j]) <= 0 ) ){    //se for o do vetor left...
                v[k] = left[i];                   //copiando o valor de left[i] para o vetor v
                i++;                            //indo para o proximo elemento da pilha da esquerda
            }
            else if(j < n2){                   //se for o do vetor right...
                v[k] = right[j];             //copiando o valor de right[j] para o vetor v
                j++;                      //indo para o proximo elemento da pilha da direita
            }
        }
    }
    
    
    // realiza o processo de ordenacao de um vetor.
    // v: o vetor a ser ordenado.
    // p: eh o primeiro indice do vetor.
    // r: eh o ultimo indice do vetor.
    // retorna o vetor ordenado.
    static void mergeSort(KeyElement[] v, int p, int r){
        if(p < r){
            //a variavel q eh responsavel por partir o vetor em dois pedacos (esq. e dir.)
            int q = (int)Math.floor((p + r)/2L);   
            
            //ordenando o lado esquerdo da particao do vetor
            mergeSort(v, p, q);     
            
            //ordenando o lado direito da paticao do vetor
            mergeSort(v, q+1, r);  
            
            //intercala os dois pedacos (ambos ja estao ordenados)
            merge(v, p, q, r);         
        }
    }
    
    
    //metodo de ordenacao em que a cada iteracao do loop se procura o menor elemento com a menor chave do vetor
    static void selectsort(KeyElement[] v){
        //indice do menor elemento do vetor
        int menor; 
        
        //percorrendo o vetor ate o penultimo elemento. A cada iteracao, o elemento em i eh o menor encontrado 
        //em relacao aos elementos que ainda restam no vetor
        for(int i = 0; i < v.length - 1; i++){ 
            menor = i; 
            
            //procurando se existe algum elemento no vetor que seja menor que v[menor]
            for(int j = i + 1; j < v.length; j++) 
                if(v[j].compare(v[menor]) == -1 )       //se encontrou...
                    menor = j;           //...ele agora eh o novo menor elemento
            
            //trocando v[i] por v[menor]
            KeyElement temp = v[i];
            v[i] = v[menor];
            v[menor] = temp;
        }
    }
    
    
    //---------- FUNCOES UTILITARIAS PARA A MANIPULACAO DE UM HEAP ------------
    
    //retorna o indice do filho esquerdo do indice informado.
    //i: eh o indice
    private static int left(int i){
        return 2*i + 1;
    } 
    
    //retorna o indice do filho direito do indice informado
    private static int right(int i){
        return 2*i + 2;
    }
    
    //faz com que a subarvore no indice i se torne um heap maximo. (assume que as outras subarvores alem do informado
    //ja sao um heap maximo)
    //v: o heap
    //i: o indice o qual queremos conferir se eh ou nao um heap maximo
    //heapSize: o tamanho do heap
    private static void maxHeapify(KeyElement[] v,int i, int heapSize){
        //pegando o indice do filho esquerdo de v[i]
        int l = Ordenator.left(i);  
        
        //pegando o indice do filho direito de v[i]
        int r = Ordenator.right(i);  
        
        //variavel para guardar o indice do maior entre o no pai e os filhos 
        int bigger;   
        
        //checando se filho esquerdo eh maior que o pai
        if(l < heapSize && v[l].compare(v[i]) == 1 )  
            bigger = l;          //se sim, atribui o indice do filho esq. a variavel maior
        else
            bigger = i;         //se nao, o maior, por enquanto, eh o pai
        
        
        //checando se o filho direito eh o maior de todos
        if(r < heapSize && v[r].compare(v[bigger]) == 1)  
            bigger = r;           //se sim, atribui o indice do filho dir. a variavel maior
        
        if(bigger != i){        //se o maior nao for o pai...
            KeyElement aux = v[i]; 
            
            //troca os valores do pai com o do maior dos filhos, fazendo o filho assim o filho se tornar pai (...?!)
            v[i] = v[bigger];
            v[bigger] = aux;
            
            //checando se o novo pai eh o maior entre ele e os seus filhos
            Ordenator.maxHeapify(v, bigger, heapSize);
        }
    }
    
    //constroi um heap maximo, em que cada noh tem o valor da chave maior que os seus filhos
    static void buildMaxHeap(KeyElement[] v ){
        //lembrando: em um heap, as folhas podem ser encontrados usando a expressao floor(heapSize/2)
        for(int i = v.length/2; i >= 0; i--)
            Ordenator.maxHeapify(v, i, v.length);
    }
    
    //------------------------------------------------------------------------------------------
    
    
    //ordena um vetor
    static void heapSort(KeyElement[] v){
        //fazendo o vetor se transformar num heap maximo(o maior valor vai ficar na raiz da arvore, ou seja, no indice 0 do vetor
        Ordenator.buildMaxHeap(v);  
                            
        //armazenando o tamanho do heap
        int heapSize = v.length;   
        
        //variavel necessaria para fazer a troca entre dois valores do heap
        KeyElement aux;  
        
        //percorrendo o heap comecando da ultima posicao e indo ate a primeira posiao
        for(int i = heapSize - 1; i >= 1; i--){  
            //pegando o primeiro elem. do heap (o maior) e colocando na  posicao i  do heap. 
            //(a ordenacao ocorre de tras pra frente)
            //o elemento que estava na posicao i eh colocado na primeira posicao
            aux = v[0];
            v[0] = v[i];
            v[i] = aux;
            
            //diminuido o tamanho do heap, assim, os elementos ja ordenados nao sao alterados
            heapSize--;  
            
            //como o novo elem. na primeira posicao do heap pode desfazer a propriedade de heap maximo do heap, eh
            //necessario utilizar a funcao maxHeapify
            Ordenator.maxHeapify(v, 0, heapSize);  //agora, garantimos novamente que o primeiro elm. do vetor eh o maior
        }
    }
    
    
    //-------- FUNCOES DE PARTICAO USADAS NO METODO DE QUICKSORT --------------------------
    
    //funcao de particao que utiliza como pivo o ultimo elemento do intervalo p ate r, ou seja, o elemento no indice r
    static int partition(KeyElement[] v, int p, int r){
        //atribuindo o pivo a variavel x
        KeyElement x = v[r]; 
        
        //i: representa o indice maximo dos elm. do vetor que sao menores ou iguais ao pivo
        int i = p - 1; 
        
        //variavel usada para fazer trocas de posicoes no vetor
        KeyElement aux;
        
        //percorrendo o vetor de p ate r - 1, ja que r eh o pivo e nao vai ter sua posicao alterada (por enquanto)
        for(int j = p; j < r; j++){  
            if (v[j].compare(x) <= 0){         //se o elemento em v[j] for menor que o pivo
                i++;         //atualiza o indice maximo os elementos do vetor menores que o pivo
                
                //tira o elemento que estava em v[i] e troca pelo elemento(v[j]) que eh menor que o pivo
                aux = v[i];
                v[i] = v[j];
                v[j] = aux;
            }
        }
        
        //colocando o pivo no seu local apropriado que seria no indice logo depois 
        //ao indice maximo dos elementos menores ou iguais ao pivo, ou seja, no indice i + 1
        aux = v[i + 1];
        v[i + 1] = v[r];
        v[r] = aux;
        
        return i + 1;         //o indice onde se encontra o pivo
    }
    
    
    //funcao de particao que utiliza como pivo o primeiro elemento do intervalo p ate r, ou seja, o elemento no indice p
    static int firstPartition(KeyElement[] v, int p, int r){
        //trocando o elemento na posicao p com o elemento na posicao r
        KeyElement aux = v[p];   
        v[p] = v[r];
        v[r] = aux;
        
        return Ordenator.partition(v, p, r);
    }
    
    
    //recebe o vetor e mais tres indices. retona o indice da mediana entre os tres valores
    static int getMedian(KeyElement[] v, int a, int b, int c){
        if(v[a].compare(v[b]) == 1 && v[a].compare(v[c]) == 1 ){    //se o elemento v[a] eh o maior...
            if(v[b].compare(v[c]) == 1)   //se o elemento v[b] for o maior que v[c]...
                return b;                 //...v[b] eh a mediana
            else
                return c;   //senao, v[c] eh a mediana
        }
        else if(v[b].compare(v[a]) == 1 && v[b].compare(v[c]) == 1){     //se o elemento v[b] eh o maior...
            if(v[a].compare(v[c]) == 1)        //se o elemento v[a] for maior que v[c]...
                return a;                      //...v[a] eh a mediana
            else
                return c;                      //senao, v[c] eh a mediana
        }
        else{                          //v[c] eh o maior
            if(v[a].compare(v[b]) == 1)    //se v[a] for maior que v[b]...
                return a;                 //v[a] eh a mediana
            else
                return b;                 //senao, v[b] eh a mediana
        }
    }
    
    //funcao de particao que utiliza como pivo o elemento que esta no meio do intervalo p ate r
    static int medianPartition(KeyElement[] v, int p, int r){
        /*int m = p + (r-p)/2;
        KeyElement aux = v[m];
        v[m] = v[r];
        v[r] = aux;*/
        int m = Ordenator.getMedian(v, p, (p + r)/2, r);
        KeyElement aux = v[m];
        v[m] = v[r];
        v[r] = aux;
        
        return Ordenator.partition(v, p, r);
    }
    
    //-----------------------------------------------------------------------------------------------
    
    
    //versao de quicksort que utiliza como pivo o ultimo elemento de cada particao
    static void quickSort(KeyElement[] v, int p, int r){
        if(p < r){
            int q = Ordenator.partition(v, p, r);
            Ordenator.quickSort(v, p, q - 1);
            Ordenator.quickSort(v, q + 1, r);
        }
    }
    
    
    //versao de quicksort que ultiliza como pivo o primeiro elemento de cada particao
    static void firstQuickSort(KeyElement[] v, int p, int r){
        if(p < r){
            int q = Ordenator.firstPartition(v, p, r);
            Ordenator.firstQuickSort(v, p, q - 1);
            Ordenator.firstQuickSort(v, q + 1, r);
        }
    }
    
    //versao de quicksort que utiliza como pivo a mediana entre o primeiro, o ultimo e o elemento medio
    //de cada particao do vetor
    public static void medianQuickSort(KeyElement[] v, int p, int r){
        if(p < r){
            int q = Ordenator.medianPartition(v, p, r);
            Ordenator.medianQuickSort(v, p, q - 1);
            Ordenator.medianQuickSort(v, q + 1, r);
        }
    }
    
    
    //v: o vetor a ser ordenado
    //k: a maior chave de um elemento do vetor
    //retorna um vetor b ordenado
    static KeyElement[] countSort(KeyElement[] v, int k){
        int[] c = new int[k + 1];   //fornece um espaco de armazenamento temporario
        
        KeyElement[] b = new KeyElement[v.length];  //vai conter a saida ordenada

        
        for(int j =0; j < v.length; j++)  
            c[ v[j].getKey() ] = c[ v[j].getKey() ] + 1;  
        //a partir daqui, c[i] contem o numero de elementos de v cuja chave seja i
        
        for(int i = 1; i < k + 1; i++)
            c[i] += c[i - 1];
        //agora, c[i] contem o numero de elementos menores que ou iguais a i
        
        //colocando os elementos de v no seu indice apropriado em b
        for(int j = v.length - 1; j >= 0; j--){
            b[ c[ v[j].getKey() ] - 1 ] = v[j];
            c[ v[j].getKey() ] = c[ v[j].getKey() ] - 1;
        }
        
        return b;        //retornando o vetor ordenado
    }
    
    
    //Esse metodo de ordenacao eh uma melhoria do metodo de insercao. Ele fica dividindo o vetor em partes menores
    //e aplica nelas o metodo de insercao. Esse procedimento acontece ate o tamanho das partes ser igual a 1
    public static void shellSort(KeyElement[] v) {
        int h = 1;
        int n = v.length;

        //usando a sequencia de Knuth para determinar o valor inicial de h
        while(h < n) {
            h = h * 3 + 1;
        }

        h = (h-1) / 3;    //voltando um termo da sequencia de Knuth
        int j;
        KeyElement temp;

        while (h > 0) {
            System.out.println("\nH: " + h);
            
            for (int i = h; i < n; i++) {
                temp = v[i];
                j = i;
                
                //efetuando comparacoes entre elementos de distancia h
                while (j >= h && v[j - h].compare(temp) == 1) {
                    v[j] = v[j - h];
                    j = j - h;
                }
                v[j] = temp;
                
                System.out.println();
                for(KeyElement elm : v){
                    System.out.print(elm.getKey() + " ");
                }
            }
            
            h = (h-1) / 3;   //voltando um termo da sequencia de Knuth
        }
    }
    
    
    //----------- METODOS UTILITARIOS USADOS NO ALGORITMO RADIXSORT ----------------------------------
    
    //retorna a maior chave do vetor
    private static int getMax(KeyElement[] v){
        int bigger = v[0].getKey();        //assumindo que o primeiro elemento eh o maior
        
        for(int i = 1; i < v.length; i++)
            if(v[i].getKey() > bigger)     //se encontrou algum elemento maior...
                bigger = v[i].getKey();    //... o atribui a variavel bigger 
        
        return bigger;   //retornando a maior chave
    }
    
    
    //versão de countSort usada no algoritmo de ordenação RadixSort.
    //exp: eh o expoente usado para extrair o digito atual da chave
    private static KeyElement[] countSortExpVersion(KeyElement[] v, int k, int exp){
        int[] c = new int[k + 1];   //fornece um espaco de armazenamento temporario

        KeyElement[] b = new KeyElement[v.length];  //vai conter a saida ordenada

        
        for(int j =0; j < v.length; j++)  
            c[ (v[j].getKey()/exp) % 10 ] = c[ (v[j].getKey()/exp) % 10 ] + 1;  
        //a partir daqui, c[i] contem o numero de elementos de v cuja chave seja i

        for(int i = 1; i < k + 1; i++)
            c[i] += c[i - 1];
        //agora, c[i] contem o numero de elementos menores que ou iguais a i

        //colocando os elementos de v no seu indice apropriado em b
        for(int j = v.length - 1; j >= 0; j--){
            b[ c[ (v[j].getKey()/exp) % 10 ] - 1 ] = v[j];
            c[ (v[j].getKey()/exp) % 10 ] = c[ (v[j].getKey()/exp) % 10 ] - 1;
        }

        return b;        //retornando o vetor ordenado
    }
    
    //------------------------------------------------------------------------------------------------
    
    
    
    //metodo de ordenacao que usa os digitos da chave e as ordena usando um outro metodo de ordenacao
    //estavel, no caso, o countsort
    public static KeyElement[] radixSort(KeyElement[] v, int maxKey){
       
       for(int exp = 1; maxKey/exp > 0; exp *= 10)
           v = Ordenator.countSortExpVersion(v, maxKey, exp);  //ordenando pelo digito atual
       
       return v;
    }
    
}

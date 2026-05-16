// Lógica do analisador léxico:
// Lê um caractere da frase
// vai para um switch onde ele tenta identificar o que o caractere é
// é um operador? "+, -, *, /": retorna o token de acordo com o que ele é
// è uma letra minuscula?: continua lendo as letras até achar um delimitador, e ve se pode classificar como uma variavel
// é uma letra Maiuscula?: verifica se é uma das palavras reservadas
// é um número?: continua empilhando, verifica se possui ponto ou não


public class AnalisadorLexico{
   public LeitorArquivo leitor;
   String VERMELHO = "\u001B[31m";  
   String RESET    = "\u001B[0m";

    // função que irá montar os caracteres para fazer o token
   public Token proxToken(){
        StringBuilder lexema = new StringBuilder(); //StringBuilder ajuda a montar um lexema de caractere em caractere
        int caractere = leitor.lerProximoCaractere();
        int estado = 0;
        String palavra = "";
        TipoToken tipoFinal = null;

        //While para iniciar a análise léxica:
        while(caractere !=-1 || estado != 0){ // Enquanto não for o final do arquivo OU não for o estado inicial
            char c = (char)caractere; // tranforma int em caractere
           
            switch (estado) {
                case 0: //indica o estado inicial   
                    if(Character.isLetter(c)){// o Caractere é uma letra?
                        lexema.append(c);
                        estado = 1;
                     } //if
                    else if(Character.isWhitespace(c)){
                        caractere = leitor.lerProximoCaractere();
                        continue;
                    }
                    else if(Character.isDigit(c)){
                        lexema.append(c);
                        estado = 4;
                    }
                    else if("<>=!:".indexOf(c) != -1){ // se for um desses operadores, vai para o estado de verificação
                        lexema.append(c);
                        estado = 7;
                    }else if("*+-/()".indexOf(c)!=-1){
                        lexema.append(c);
                        estado = 8;
                        continue;
                    }else if (c == '"'){
                        lexema.append(c);
                        estado = 9;
                    }else if(c == '#'){
                        estado = 10;
                    }else{
                        System.out.println(VERMELHO + "\n| ERRO LÉXICO: O caractere [" + c + "] é inválido nessa linguagem!" + RESET);
                        System.exit(1); // Para o programa inteiro aqui!
                    }
                    break;
                case 1: //Estado de empilhamento de caracteres considerando receber um delimitador
                    if(Character.isLetter(c) || Character.isDigit(c)){ //se é um número ou uma letra, empilha
                        lexema.append(c);
                       
                    }else if("+-*/=()>!<:.".indexOf(c) != -1 ){ //Se o proximo caractere FOR um especial que é permitido
                        leitor.retroceder(caractere); // desle o ultimo caractere
                        palavra = lexema.toString(); //salva o lexema
                        estado = 2;
                        continue;
                    }else if(Character.isWhitespace(c)){
                        //não retrocesde e monta o lexema para fazer a verificação
                        palavra = lexema.toString();
                        estado = 2;
                        continue; 
                    }else if(caractere == -1){ //Se encontrar o final do arquivo, pega a palavra montada e faz a verificação de PR e var
                        palavra = lexema.toString();
                        estado = 2;
                        continue;
                    }else{

                        System.out.println(VERMELHO + "\n| ERRO LÉXICO: Caractere '" + c + "' é inválido nessa linguagem!" + RESET);
                        System.exit(1); // Para o programa inteiro aqui!
                    }

                    break;//case 1
                case 2: //Estado para verificar palavras variaveis e reservadas
                        // verifica se é tudo maísculo ou se a primeira letra é minúscula
                        if(palavra.equals(palavra.toUpperCase())){
                            estado = 3;
                            continue;
                        }else if(Character.isLowerCase(palavra.charAt(0))){//verifica se a primeira letra é minúscula
                            tipoFinal = TipoToken.Var;
                            estado = 100; //estado para a montagem de token de tipo variável
                            continue;
                        }else{
                            System.out.println(VERMELHO + "\n| ERRO LÉXICO: A palavra '" + palavra + "' não pode ser uma variável!" + RESET);
                            System.exit(1); // Para o programa inteiro aqui!
                        }
                break; //case 2
                case 3:// Estado para verificar se é uma palavra reservada
                        TipoToken TokenEncontrado = TipoToken.buscarPalavra(palavra);
                        if(TokenEncontrado != null){
                            tipoFinal = TokenEncontrado;
                            estado = 100;
                            continue;
                        }else{
                            System.out.println(VERMELHO + "\n| ERRO LÉXICO: A palavra '" + palavra + "' não é uma palavra reservada válida!" + RESET);
                            System.exit(1); //Para o programa Inteiro!
                        }
                continue; //interrompe o resto do switch e while, faz ele ir para o próximo loop sem perder o caractere
                
                case 4: // Estado para Verificação de números Int e Reais
                    if(Character.isDigit(c)){ //se é um número empilha
                        lexema.append(c);
                        estado = 4;
                       
                    }else if("+-*/=()>!<:".indexOf(c) != -1){ //Se o proximo caractere FOR um especial que é permitido
                        leitor.retroceder(caractere); // desle o ultimo caractere
                        palavra = lexema.toString(); //salva o lexema
                        tipoFinal = TipoToken.NumInt;
                        estado = 100;
                        continue;
                    }else if(Character.isWhitespace(c)){
                        //não retrocesde e monta o lexema para fazer a verificação
                        palavra = lexema.toString();
                        tipoFinal = TipoToken.NumInt;
                        estado = 100;
                    }else if(".".indexOf(c) != -1){ //se o próximo caractere for um ., vamos para o estado de digitos do tipo REAL
                        lexema.append(c);
                        estado = 5;
                    }else if( caractere == -1){ //Se encontrar o final do arquivo, pega a palavra que estava sendo feita e salva
                        palavra = lexema.toString();
                        tipoFinal = TipoToken.NumInt;
                        estado = 100;
                        continue;
                    }
                break;
                case 5: // Estado para verificação de números reais
                    if(Character.isDigit(c)){
                        lexema.append(c);
                        estado = 6;
                    }else{
                        String erro;
                        if(c == '.'){erro = "Dois '.'";}
                        else if(caractere == -1){erro = "Final de arquivo";}
                        else if(Character.isLetter(c)){ erro = "Existe uma letra após o '.'";}
                        else{ erro = "Número inválido!";}
                        System.out.println(VERMELHO + "\n| ERRO LÉXICO: Número Real incompleto [" + lexema + "]: "+ erro + RESET);
                        System.exit(1); //Para o programa Inteiro!
                    }
                break;
                case 6: // Estado para verificação de números inteiros

                    if(Character.isDigit(c)){
                        lexema.append(c);
                        estado = 6;
                    }else if("+-*/=()>!<:".indexOf(c) != -1){ //Se o proximo caractere FOR um especial que é permitido
                        leitor.retroceder(caractere); // desle o ultimo caractere
                        palavra = lexema.toString(); //salva o lexema
                        tipoFinal = TipoToken.NumReal;
                        estado = 100;
                        continue;
                    }else if(Character.isWhitespace(c)){
                        //não retrocesde e monta o lexema para fazer a verificação
                        palavra = lexema.toString();
                        tipoFinal = TipoToken.NumReal;
                        estado = 100;
                    }else if(caractere == -1){ //Se encontrar o final do arquivo, pega a palavra que estava sendo feita e salva
                        palavra = lexema.toString();
                        tipoFinal = TipoToken.NumReal;
                        estado = 100;
                        continue;
                    }
                break;
                case 7: // Estado de verificação de operadores aritméticos
                    if("<>=!:".indexOf(c) != -1){
                        lexema.append(c);
                        estado = 7;
                    }else{//não é um operador composto
                        if((caractere != -1)){ //chegou no fim do arquivo, retroscede e termina de formar o operador para enfim verificar
                        leitor.retroceder(caractere);}
                        
                        palavra = lexema.toString();
                        TokenEncontrado = TipoToken.buscarPalavra(palavra);
                        if(TokenEncontrado != null){
                            tipoFinal = TokenEncontrado;
                            estado = 100;
                            continue;
                         }else{
                            System.out.println(VERMELHO + "\n| ERRO LÉXICO: A palavra '" + palavra + "' não é uma palavra reservada válida!" + RESET);
                            System.exit(1); //P
                         }
                    }
                break;
                case 8: // Estado: Operadores aritméticos 
                     palavra = lexema.toString();
                     TokenEncontrado = TipoToken.buscarPalavra(palavra);
                     if(TokenEncontrado != null ){
                        tipoFinal = TokenEncontrado;
                        estado = 100;
                        continue;
                     }else{
                        System.out.println(VERMELHO + "\n| ERRO LÉXICO: O operador '" + lexema + "' não é válido" + RESET);
                        System.exit(1); //P
                     }
                break;
                case 9: // Estado: cadeia de palavras
                    if(c!='"'){
                        lexema.append(c);
                    }else if (c == '"'){
                        lexema.append(c);
                        palavra = lexema.toString();
                        tipoFinal = TipoToken.Cadeia;
                        estado = 100;
                        continue;
                    }if (caractere == -1) { 
                        System.out.println(VERMELHO + "\n| ERRO LÉXICO: Cadeia de caracteres não foi fechada!" + RESET);
                        System.exit(1);
                    }
                break;
                case 10: // estado: pula linha de comentários
                        if (c == '\n' || caractere == -1) { //chegou no quebra linha de onde o comentário se encontra
                            lexema.setLength(0);  //limpa tudo
                            estado = 0;          // Volta para o início para voltar a ler os outros tokens
                            if (caractere != -1) { // se não for o final do arquivo, lê o próximo caractere
                                caractere = leitor.lerProximoCaractere();
                            }
                            continue; }
                break;
                case 100:// Estado de aceitação!!
                        return new Token(palavra,tipoFinal);
                default:
                break;
            }//switch
            caractere = leitor.lerProximoCaractere();
        }//while
        return null;
    }
}// AnalisadorLexico

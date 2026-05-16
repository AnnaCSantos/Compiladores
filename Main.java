// Objetivo:
// Criar análisador léxico com base em automatos
// Criar com base na tabela da linguagem GYH


// Implementação
//  - Lê um arquivo
//          - Precisa receber um arquivo válido! okay
//          - Precisa receber o parâmetro certo!
//          - Arquivo não pode estar vazio
//  - Ler linha inteira
//  - Ler cada caracter e ir passando de um em um
//  - Verificar cada caracter do alfabeto
//  - Verificar se chegou em algum delimitador de palavra
//      - Tentar enquadrar na tabela de tokens ( se não, identifica erro e para de rodar)
//  - Ir para a próxima linha se não tiver nenhum erro léxico


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Receber dados do teclado

public class Main {
    public static void main(String[] args) throws Exception {
        // Variáveis globais uteis
        String VERMELHO = "\u001B[31m";   // cor do print: Vermelho
        String RESET    = "\u001B[0m";
        String VERDE    = "\u001B[32m";
        //String AMARELO  = "\u001B[33m";
        String AZUL = "\u001B[34m";
       
        // variaveis
        Scanner teclado = new Scanner(System.in);                      //Recebe o nome do arquivo a ser analisado                              // Booleano para usar como condição do while
        String  c_codigoFonte = "";                          
        File arquivo = null;

        //criação de classes
        LeitorArquivo leitor_arquivo = new LeitorArquivo();

        System.out.println("| Informe o nome do arquivo a ser análisado!");
        System.out.println("| Estrutura: nome.gyh");
       
        while (arquivo == null) {                                     // Enquanto arquivo valido for falso, continua tentando receber
            c_codigoFonte = teclado.nextLine();
            arquivo = leitor_arquivo.validarArquivo(c_codigoFonte);
        }//while

        teclado.close();//Fecha o teclado para não receber mais nada!
        System.out.println(VERDE + "| Arquivo válido! Iniciando análise..." + RESET);

        // Depois de válido o arquivo, inicia-se a leitura de caracteres do arquivo
        AnalisadorLexico analisador = new AnalisadorLexico();
        analisador.leitor = leitor_arquivo;
       
        System.out.println(AZUL + "+------------------------------------+" + RESET);
        System.out.println(AZUL + "|       TOKENS IDENTIFICADOS         |" + RESET);
        System.out.println(AZUL + "+------------------------------------+" + RESET);

        Token t;
        try {

            List<Token> listaTokens = new ArrayList<>(); // Cria uma

            // O loop vai rodar enquanto o analisador encontrar tokens
            // Quando chegar no fim do arquivo, o proxToken retorna null e o loop para
            while ((t = analisador.proxToken()) != null) {
                
                //System.out.println(AZUL + "| Token: " + RESET + t.toString()); // exibir 1 por linha
                System.out.print(" "+t.toString()); // Exibir um na frente do outro
            }
            System.out.println(AZUL + "\n--------------------------------------" + RESET);
            System.out.println(VERDE + "\n--------------------------------------" + RESET);
            System.out.println(VERDE + "Análise concluída com sucesso!" + RESET);
            System.out.println(VERDE + "--------------------------------------" + RESET);
           
        } catch (Exception e) {
            System.err.println(VERMELHO + "| Erro durante a análise: " + e.getMessage() + RESET);
        }
    }
}





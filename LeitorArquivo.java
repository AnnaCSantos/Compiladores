import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;


public class LeitorArquivo {
    String VERMELHO = "\u001B[31m";   // cor do print: Vermelho
    String RESET    = "\u001B[0m";

    private PushbackInputStream r_buffer;

    public File validarArquivo(String caminho){ //Função de tipo "File" para passar um arquivo para o análisador léxico se validado
            if (caminho.isEmpty()) {// Se o nome do arquivo estiver vazio
                System.out.println(VERMELHO + "| Erro: Insira o nome do arquivo!" +RESET);
                return null;
            }else if (!caminho.endsWith(".gyh")){
                 System.out.println(VERMELHO + "| Erro: O arquivo precisa ser .gyh"+ RESET);
                 return null;
            }
           
            File arquivo = new File(caminho); // Cria uma variável de arquivo para verificar a existência e retornar para o análisador léxico
           
             // ---- Verificação de arquivo: Tratamento de erro ----
            try{
                if(!arquivo.exists()){
                    System.out.println(VERMELHO + "| Erro: Arquivo não encontrado!" + RESET);
                    return null;
                }
                if(arquivo.length() == 0){
                    System.out.println(VERMELHO + "| Erro: O arquivo está vazio!"+ RESET);
                    return null;
                }
                
                FileInputStream arq_fisico = new FileInputStream(arquivo); //caminho para conseguir ler os dados dos arquivo
                this.r_buffer = new PushbackInputStream(arq_fisico, 1); //leitor com memória, devolve 1 caractere para o caminho que é feito a leitua, importante para o retrosceder

                return arquivo;
            }catch(Exception erro){
                System.out.println(VERMELHO + "| Erro ao abrir o arquivo:" + erro.getMessage() + RESET);
                return null;
            }// try catch


    }//validarArquivo


    public int lerProximoCaractere(){
        int caractere = -1;
        try {
            caractere = r_buffer.read(); //Transforma o caractere em um número
        }catch (IOException erro){
            System.out.println(VERMELHO + "| Erro ao ler caractere: " + erro.getMessage() + RESET);
        }
        return caractere; //retorna caractere em forma de número
    }
   
    public void retroceder(int caractere) {
        try {
            // Se o arquivo não acabou e o buffer existe
            if (caractere != -1 && r_buffer != null) {
                // O comando unread "deslê" o ultimo caractere que provavelmente é um delimitador
                r_buffer.unread(caractere);
            }
        } catch (IOException e) {
            System.out.println("Erro ao tentar voltar o caractere.");
        }
    }
}//leitorArquivo







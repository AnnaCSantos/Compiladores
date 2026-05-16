//CONTRUTOR DE TOKEN: SUa função é obter o valor do token,
public class Token {
    private TipoToken sigla; //varivael do tipo enum 
    private String lexema;   // String do valor que é o caracter lido

    public Token(String lexema, TipoToken sigla){ //CONSTRUTOR: Atribui os valores das variáveis
        this.sigla = sigla;
        this.lexema = lexema;
    }
    public TipoToken getSigla() {    //getter para o tipo
        return sigla;
    }
    public String getLexema() {      //getter para o caractere
        return lexema;
    }

    public String toString() {      //constrói a estrutura do token
        return "<'" + lexema + "'," + sigla + ">";
    }
}

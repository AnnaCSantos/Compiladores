// Cabeçalho de Tokens: SIGLAS

import java.util.HashMap;

public enum TipoToken {
   //Palavras Chave
   PCDec,PCProg,PCInt,PCReal,PCLer,PCImprimir,PCSe,PCEntao,PCSenao,PCEnqto,PCIni,PCFim,
   //Operadores aritméticos 
   OpAritMult,OpAritDiv,OpAritSoma,OpAritSub,
   OpRelMenor,OpRelMenorIgual,OpRelMaior,OpRelMaiorIgual,OpRelIgual,OpRelDif,
   //Booleanos
   OpBoolE,OpBoolOu,
   Delim,
   Atrib,
   AbrePar,FechaPar,
   Var,
   NumInt,
   NumReal,
   Cadeia;
   private static final HashMap<String, TipoToken> PR = new HashMap<>(); //

   static {
        PR.put("DEC", PCDec); 
        PR.put("PROG", PCProg);
        PR.put("INT", PCInt);
        PR.put("LER", PCLer);
        PR.put("REAL", PCReal);
        PR.put("IMPRIMIR", PCImprimir);
        PR.put("SE", PCSe);
        PR.put("SENAO", PCSenao);
        PR.put("ENTAO", PCEntao);
        PR.put("ENQTO", PCEnqto);
        PR.put("INI", PCIni);
        PR.put("FIM", PCFim);
        //OPERADORES ,,,,,,,,,,
        PR.put("*", OpAritMult);
        PR.put("/", OpAritDiv);
        PR.put("+", OpAritSoma);
        PR.put("-", OpAritSub);
        //Operadores Relacionais
        PR.put("<", OpRelMenor);
        PR.put("<=", OpRelMenorIgual);
        PR.put(">", OpRelMaior);
        PR.put(">=", OpRelMaiorIgual);
        PR.put("==", OpRelIgual);
        PR.put("!=", OpRelDif);
        //Booleanos  
        PR.put("E", OpBoolE);
        PR.put("OU", OpBoolOu);
        PR.put(":", Delim);
        PR.put(":=", Atrib);
        PR.put("(", AbrePar);
        PR.put(")", FechaPar);
   }
   private TipoToken() {
   }
   public static TipoToken buscarPalavra(String texto) {
        return PR.get(texto); // Retorna Null se não existir
   }
}


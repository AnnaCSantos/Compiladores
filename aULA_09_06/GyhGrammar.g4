grammar GyhGrammar;

// Programa → ':' 'DEC' ListaDeclaracoes ':' 'PROG' ListaComandos;

// Sintáticas antes, léxicas depois
// Maiúscula = léxico. Minúscula = sintático.
programa: Delim {System.out.println("\n Entrando no arquivo.g4: "+_input.LT(-1).getText());}       //() indica que depois da gramática ele executa um código java, ou seja, ele imprime o token depois do Delim    
          PCDec EOF;



PCDec:      'DEC';
PCProg:     'PROG';
PCInt:      'INT';
PCReal:     'REAL';
PCLer:      'LER';
PCImprimir: 'IMPRIMIR';
PCSe:       'SE';
PCSenao:    'SENAO';
PCEntao:    'ENTAO';
PCEnqto:    'ENQTO';
PCIni:      'INI';
PCFim:      'FIM';


OpAritMult: '*';
OpAritDiv:  '/';
OpAritSoma: '+';
OpAritSub:  '-';
OpRelMenor: '<';
OpRelMenorIgual: '<=';
OpRelMaior: '>';
OpRelMaiorIgual: '>='; 
OpRelIgual: '==';
OpRelDif: '!=';
OpBoolE: 'E';
OpBoolOu: 'OU';

Delim:      ':';
Atrib:      ':=';
AbrePar:    '(';
FechaPar:   ')';
Var:        [a-z][a-z|A-Z|0-9]+;
NumInt:     [0-9]+;
NumReal:    [0-9]+'.'[0-9]+;   
Cadeia:  '"'[^"\r\n]*'"';
Comentario: '#' [a-zA-Z0-9]*[^' '\r]*'\n' -> skip; // -> ignora comentários
EspacoBranco: [' '|'\n']* -> skip;                  //Ignora espaços em branco







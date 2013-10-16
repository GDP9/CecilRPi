import java.io.*;
import org.antlr.runtime.*;
import org.antlr.runtime.debug.DebugEventSocketProxy;


public class __Test__ {

    public static void main(String args[]) throws Exception {
        CecilLexer lex = new CecilLexer(new ANTLRFileStream("C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\output\\__Test___input.txt", "UTF8"));
        CommonTokenStream tokens = new CommonTokenStream(lex);

        CecilParser g = new CecilParser(tokens, 49100, null);
        try {
            g.program();
        } catch (RecognitionException e) {
            e.printStackTrace();
        }
    }
}
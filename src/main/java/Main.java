import calculator.Scientific.ScientificCalculator;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;


public class Main {
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
        new ScientificCalculator();
    }
}

package services.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Tristan Cadet
 * @author Paul Du
 */
public final class ConsoleIO {
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(System.out);
    
    private ConsoleIO () { }
    
    public static void print(String message) {
        out.print(message);
    }
    
    public static void println() {
        out.println();
    }
    
    public static void println(String message) {
        out.println(message);
    }
    
    public static String ask(String question) {
        out.print(question + " ");
        out.flush();
        
        String response;
        try {
            response = in.readLine();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            return null;
        }
        return response;
    }
    
    public static int askNumber(String question) {
        return askNumber(question, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    public static int askNumber(String question, int min, int max) {
        Integer response = null;
        while (response == null) {
            try {
                response = Integer.parseInt(ask(question));
                if (response < min || response > max) {
                    out.println("/!\\ Erreur de saisie - Le nombre doit être compris entre " + min + " et " + max + " /!\\");
                    response = null;
                }
            } catch (NumberFormatException ex) {
                out.println("/!\\ Erreur de saisie - Nombre entier attendu /!\\");
            }
        }
        
        return response;
    }
    
    public static Date askDate(String question) {
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
        Date response = null;
        while (response == null) {
            try {
                response = sf.parse(ask(question));
            } catch (ParseException ex) {
                out.println("/!\\ Erreur de saisie - Date attendu en format dd/MM/yyyy /!\\");
            }
        }
        
        return response;
    }

    public static Date askTime(String question) {
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
        Date response = null;
        while (response == null) {
            try {
                response = sf.parse(ask(question));
            } catch (ParseException ex) {
                out.println("/!\\ Erreur de saisie - Date attendu en format \"HH:mm\" /!\\");
            }
        }
        
        return response;
    }
    
    public static void printMessageBox(String message) {
        for (int i = 0; i < message.length()+4; i++) {
            out.print("*");
        }
        out.println();
        out.println("* " + message + " *");
        for (int i = 0; i < message.length()+4; i++) {
            out.print("*");
        }
        out.println();
    }
}

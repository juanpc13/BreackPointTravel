/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidad;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

/**
 *
 * @author jcpleitez
 */
public class Validador extends PlainDocument{
    
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static boolean validCorreo(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
    
    // Regresion de Correo
    private String regex = "";
    private String text = "";

    public Validador(int mode) {
        switch(mode){
            case 1://letras con espacio
                regex = "[A-Za-z ]{0,32}";
                break;
            case 2://correo
                regex = "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}";
                break;            
            case 3://numeros
                regex = "[0-9]";
                break;
            default:
                regex = "";
                break;
        }
    }
    
    @Override
    public void insertString(int offset, String txt, AttributeSet a) {
        try {
            // Sin Regresion
            if(regex.isEmpty()){
                super.insertString(offset, txt, a);
            }
            
            // Se aplica la regresion
            text = getText(0, getLength());
            if ((text + txt).matches(regex)) {
                super.insertString(offset, txt, a);
            }
        } catch (Exception ex) {
            Logger.getLogger(Validador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

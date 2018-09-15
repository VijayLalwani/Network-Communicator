package Methods;

import java.awt.Color;
import javax.swing.JTextField;

/******************
 * @author Lalwani*
 ******************/

public class Methods {
    
    public static void textFieldFocus(JTextField tf,String str){
        if(tf.getText().equals("")||tf.getText().equals(str)){
            tf.setText("");
            tf.setForeground(Color.BLACK);
        }
    }
    
    public static void textFieldFocusLost(JTextField tf,String str){
        if(tf.getText().equals("")){
            tf.setText(str);
            tf.setForeground(Color.LIGHT_GRAY);
        }
    }
}

import javax.swing.JFrame;
import java.util.ArrayList;


public class LifeFrame extends JFrame{


    public LifeFrame(){

        add(new LifePanel());
        setSize(1300,700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
       new LifeFrame();
    }
}

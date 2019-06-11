
import javax.swing.JFrame;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lina
 */
public class GameFrameUser {
    public static void main(String[] args){
        //create an instance object of Trial
        GameFrame p1 = new GameFrame();
        p1.setTitle("JPRG Assignment");
        p1.setSize(1000,600);
        p1.setVisible(true);
        p1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

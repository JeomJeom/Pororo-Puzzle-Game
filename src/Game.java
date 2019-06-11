/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lee Hwa Mun
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Game {
    private ImageIcon image = new ImageIcon("images/train.jpeg");
    private int num,timeTaken;
    private String convert, name;
    private File histFile = new File("history.txt");
    public Game(){
        checkHistFile();
    }
    
    public void plusNum(){
        num++;
    }
        
    public int getNum(){
        return num;
    }
        
    public void resetNum(){
        num = 0;
    }
    
    public ImageIcon getImage(){      
        return image;
    }
    
    public ImageIcon miniPict(){
        Image icon = image.getImage();
        Image mini = icon.getScaledInstance(190,125,java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(mini);
        return newIcon;
    }
    public void appendHist(){
        try{
            FileWriter fw = new FileWriter(histFile,true);
            fw.append(name+" "+num+" "+getTime(timeTaken).substring(5)+"\n");
            fw.close();
        }catch(IOException ex){
            checkHistFile();
        }
    }
    public String getHist() {
        String display = "";
        try{
            Scanner histScanner = new Scanner(histFile);
            int serialNo = 1;
            while(histScanner.hasNext()){
                display += serialNo+"\t\t";
                display += histScanner.next()+"\t\t";
                display += histScanner.nextInt()+"\t\t";
                display += histScanner.next()+"\n";
                serialNo++;
            }
        }catch(IOException ex){
            checkHistFile();
        }
        return display;
    }
    public void checkHistFile(){
        if(!histFile.exists()){
            try{
                FileWriter fw = new FileWriter(histFile);
                fw.close();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
    
    public void setName (String s){
        name = s;
    }
    
    public String getName(){
        return name;
    }
    public void setTime(int time){
        timeTaken = time;
    }
    public String getTime(int t)  {   
        int hour,minute,second,a;
        String mytimer,strsec,strmin,strhour;   
        t=t%(60*60*24);   
        second=t%60;   
        a=t/60;   
        minute=a%60;
        hour=a/60;   
        
        if(second<=9)   {    
            strsec="0"+String.valueOf(second);   
        }else{    
            strsec=String.valueOf(second);   
        }   
        
        if(minute<=9)   {
            strmin="0"+String.valueOf(minute);    
        }    else    {     
            strmin=String.valueOf(minute);    
        }    
        
        if(hour<=9)    {
            strhour="0"+String.valueOf(hour);    
        }    else    {     
            strhour=String.valueOf(hour);    
        }    
        
        mytimer="Time:"+strhour+":"+strmin+":"+strsec;    
        return  mytimer;  
    } 

}
/* References
 * 
 * Travanelli, 2012. For the Love of Pororo! [photograph]. 
 * Available from: http://thesecretmap.wordpress.com/2012/06/19/for-the-love-of-pororo/ [Accessed 15 January 2013]
 */
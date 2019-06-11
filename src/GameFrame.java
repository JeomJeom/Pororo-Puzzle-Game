 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lina
 */
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Image;
import javax.swing.GrayFilter;
import javax.swing.Timer;

public class GameFrame extends JFrame
                            implements ActionListener{ 
    private JLabel titleLbl, nameLbl, pieceLbl,compLbl, gameLbl,timerLbl;
    private JButton startBtn, histBtn, resetBtn, exitBtn,picBtn;
    private GameFrame.Resize tempBtn;
    private JTextField nameTxt,timerTxt;
    private JTextArea infoTxt,histTxt;    
    private JPanel northPanel, centerPanel, cLeftPanel, cRightPanel, southPanel, sLeftPanel;
    private JScrollPane infoScText,histScroll;
    private GameFrame.Resize[] imgBtn,puzzleBtn;
    private int firstClick, secClick, test;
    private ImageIcon[] imgArr;
    private int singleClick, congrat;
    private Game game = new Game();
    private int t = 0;  
    private Timer time;

    
    public GameFrame(){
        // north
        titleLbl = new JLabel("My Puzzle Game");
        northPanel = new JPanel ();
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        northPanel.add(titleLbl);  
        titleLbl.setForeground(Color.blue);
        titleLbl.setFont(new Font("Serif", Font.BOLD, 25));
        
        // south
        nameLbl = new JLabel("Enter player's name:");
        //timerLbl = new JLabel("Waiting...", SwingConstants.TOP);
        //add(timerLbl);
        startBtn = new JButton ("Start Game");
        histBtn = new JButton("Get History");
        histBtn.addActionListener(this);
        resetBtn = new JButton("Reset Game");
        exitBtn = new JButton("Exit Game");
        
        //Change the 4 Jbuttons colour to red
        startBtn.setBackground(Color.red);
        histBtn.setBackground(Color.red);
        resetBtn.setBackground(Color.red);
        exitBtn.setBackground(Color.red);
        
        nameTxt = new JTextField ();
        infoTxt = new JTextArea("Instructions: \n \n Enter your name and click 'Start Game' button",10,20);   
        infoTxt.setEditable(false);
        infoScText = new JScrollPane(infoTxt, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        picBtn = new JButton("Original Picture:",game.miniPict());
        picBtn.setHorizontalTextPosition(JButton.LEFT);
        picBtn.setVerticalTextPosition(JButton.TOP);
                
        southPanel = new JPanel(); 
        sLeftPanel = new JPanel();
                
        southPanel.setLayout(new GridLayout(1,3));
        southPanel.setBorder(new TitledBorder("~~Puzzle Game~~"));        
        sLeftPanel.setLayout(new GridLayout(3,2));
        
        sLeftPanel.add(nameLbl);
        sLeftPanel.add(nameTxt);
        sLeftPanel.add(startBtn);
        sLeftPanel.add(histBtn);
        sLeftPanel.add(resetBtn);
        sLeftPanel.add(exitBtn);
        
        southPanel.add(sLeftPanel);
        southPanel.add(infoScText);
        southPanel.add(picBtn);
        
        // center
        centerPanel = new JPanel();
        cLeftPanel = new JPanel();
        cRightPanel = new JPanel();
        
        imgArr = ImageUtil.sliceImage(3,3,game.getImage()); 

        imgBtn = new GameFrame.Resize[9];
        for(int i = 0; i < imgBtn.length; i++){
            imgBtn[i] = new GameFrame.Resize();
            cLeftPanel.add(imgBtn[i]);
            imgBtn[i].addActionListener(this);
            imgBtn[i].setEnabled(false);
        }  

        puzzleBtn = new GameFrame.Resize[9];
        for(int x = 0; x < puzzleBtn.length; x++){
            puzzleBtn[x] = new GameFrame.Resize();
            cRightPanel.add(puzzleBtn[x]);
            puzzleBtn[x].addActionListener(this);
        }
        
        shuffle(); 
         
        //to assign the image to the JButton in the array
        for(int i=0; i<imgBtn.length;i++){
            imgBtn[i].setIcon(imgArr[i]);
        }

        //set Layouts
        centerPanel.setLayout(new GridLayout(1,2));
        cLeftPanel.setBorder(new TitledBorder("Puzzle Piece"));
        cRightPanel.setBorder(new TitledBorder("Complete the puzzle"));
        cLeftPanel.setLayout(new GridLayout(3,3));
        cRightPanel.setLayout(new GridLayout(3,3));  
        
        centerPanel.add(cLeftPanel);
        centerPanel.add(cRightPanel);
        
        //Content layout
        setLayout(new BorderLayout());                    
                
        //add the panels into content pane
        add(northPanel,BorderLayout.NORTH);
        add(centerPanel,BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);     
   
        resetBtn.setEnabled(false);
        resetBtn.addActionListener(this);
        startBtn.addActionListener(this);
        exitBtn.addActionListener(this);
        
        timerTxt=new JTextField(8);
        timerTxt.setEditable(false);
        //create timer
        time = new Timer(1000,this);   
        timerTxt.setText(game.getTime(t)); 
        timerTxt.setLayout(new FlowLayout(FlowLayout.RIGHT));  
        northPanel.add(timerTxt);   
        setBounds(100,100,100,100);   
        validate();     
    }
    
    //shuffle the images
    //the repeat here is to ensure that after shuffling of the images, 
    //the positions of the images are all messed up and not repeated(not in the same place).
    public void shuffle(){    
         boolean repeat;
         for(int i = 0; i < imgBtn.length; i++){
            do{
                repeat = false;
                int random = (int)(Math.random() * 9);
                    if(imgBtn[i] == imgBtn[random])
                        repeat = true;
                    if(repeat == false){
                        tempBtn = imgBtn[random];
                        imgBtn[random] = imgBtn[i];
                        imgBtn[i] = tempBtn;
                        
                    }
            }while(repeat);
       }
    }
    
    //resize the window
    //change the image icon to image
    private class Resize extends JButton{
        private ImageIcon originalImg;
        
        public void setIcon(ImageIcon icon) {
            super.setIcon(icon);
            originalImg = icon;
        }
       public void setEnabled(boolean isEnabled) {
            super.setEnabled(isEnabled);
            if(getIcon() != null && !isEnabled) {
                ImageIcon icon = (ImageIcon) getIcon();//get the icon
                Image newImg = GrayFilter.createDisabledImage(icon.getImage()/*change it to an image*/); //apply the gray thingy
                super.setIcon(new ImageIcon(newImg)/*create an imageicon with the gray image(change it back to an icon)*/);//set icon
            }
            else if(isEnabled){
                super.setIcon(originalImg);
            }
        }
        @Override
        protected void paintComponent(Graphics g){
            ImageIcon newIcon = (ImageIcon) getIcon();
            int width = getWidth();
            int height = getHeight();
            if(newIcon == null){
                g.setColor(Color.CYAN);
                g.fillRect(0, 0, width, height);//use a rectangle to solve the problem of the images appearing weird on the puzzleBtns
            }else{
                Image newImage = newIcon.getImage();
                g.drawImage(newImage, 0, 0, width, height, null);//resize the image to fit the size of the button(x-axis: 0 to width,, y-axis: 0 to height) 
            }
        }
    }
    
        public void actionPerformed(ActionEvent e){
            //Timer
            if (e.getSource() == time){ 
                t+=1;
               timerTxt.setText(game.getTime(t));
               return;
            }// End of Timer
            
            //Disable the start button & Enable the imgBtn once the player has enter his/her name,and
            //prompt the player if the name is not entered
            if (e.getSource() == startBtn){
                game.setName(nameTxt.getText());
                time.start();
                if(!nameTxt.getText().equals("")){
                    time.start();
                    startBtn.setEnabled(false);
                    resetBtn.setEnabled(true);
                    
                    for(int i=0;i<imgBtn.length;i++){
                        imgBtn[i].setEnabled(true);
                        //If this statement --> imgBtn[i].setEnabled(true); is not placed inside the for-loop, 
                        //it will only make 1 imgBtn to be true.
                        //Since we would like to enable all imgBtn, a for-loop is essential here.
                    }
                    if (e.getSource() instanceof JButton){
                        infoTxt.replaceRange("Instructions: \n\n Select a piece of the puzzle from the \n'Puzzle Pieces' panel.", 0, 63);   
                    }
                }
                else{
                    JOptionPane.showMessageDialog(
                            null,
                            "Please enter your name to begin");
                }
            }// End of startBtn
            
           //Disable multiple selections and
           //Check if the puzzle is place in the right position
           for(int i = 0; i < imgBtn.length; i++){
               if(e.getSource() == imgBtn[i]){
                   for(int j=0;j<=8;j++){
                       if(cLeftPanel.getComponent(j)==imgBtn[i]){
                          //infoTxt.setText("Instructions:\n\n Puzzle Piece " + (j+1) + " selected,\nplease place into the correct position\nwithin the 'Complete the Puzzle' panel.");
                           infoTxt.append("\n\n Puzzle Piece " + (j+1) + " selected,\n please place into the correct position\nwithin the 'Complete the Puzzle' panel.");
                       }
                   }
                }
               
               if((e.getSource() == imgBtn[i])&&(singleClick == 0)){
                    firstClick = i;
                    imgBtn[i].setEnabled(false);
                    singleClick++;//count the number of moves
                    game.plusNum();
                    
                }
                if((e.getSource()==puzzleBtn[i])&&(singleClick == 1)){
                    secClick = i;
                    if(firstClick == secClick){
                        puzzleBtn[i].setIcon(imgArr[i]);
                        JOptionPane.showMessageDialog(
                                null,
                                "You are right");
                        singleClick--;
                        congrat++;
                        
                        //Show message if win
                        if(congrat == 9){
                            time.stop();
                            game.setTime(t);
                            game.appendHist();
                            t=0;
                            
                            JOptionPane.showMessageDialog(
                                null,
                                "Game Completed! You did it in "+game.getNum() + " steps");
                            
                           infoTxt.setText("Instructions: \n\nCompleted! You did it in "+ game.getNum() +"steps. \nClick 'Reset Game' to play again");       
                        }
                    }else{
                        imgBtn[firstClick].setEnabled(true);
                        JOptionPane.showMessageDialog(
                                null,
                                "Wrong! Try again.");
                        singleClick--;
                    }
                }
           }
           
           //History
           if(e.getSource() == histBtn){
               histTxt = new JTextArea("S/N\t\tPlayer's Name\t\tNumber of steps\tTimeTaken\n");
               histScroll = new JScrollPane(histTxt);
               histTxt.append(game.getHist());
               JOptionPane.showMessageDialog(
                       null,
                       histScroll); 
           }//End of History
               
           //Reset
           if (e.getSource() == resetBtn){
                    //infoT("Instructions: \n\n Select a piece of the puzzle from the \n'Puzzle Pieces' panel.", 0, 63);
                    time.stop();
                    t=0;
                    timerTxt.setText(game.getTime(0));
                    congrat = 0;
                    startBtn.setEnabled(true);
                    resetBtn.setEnabled(false);
                    game.resetNum();
                    shuffle();
                    for(int i=0; i <imgBtn.length; i++){
                        imgBtn[i].setEnabled(false);    
                        imgBtn[i].setIcon(imgArr[i]);
                    }
                    for (int j = 0; j < puzzleBtn.length;j++){
                       puzzleBtn[j].setIcon(null);
                    }
                    
           }// End of Reset
           
           
           
           //Exit the game by clicking the exit button
            if(e.getSource() == exitBtn){
                JOptionPane.showMessageDialog(
                        null,
                        "Thank you");
                System.exit(0);
            }
            
        }
                
            //   counter++;      
        //timerLbl.setText("Time used: " + counter);
                                
}
    
//Still need to add the followings....    
//History

//put the arrays into Game.java




/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lina and Lee Hwa Mun
 */

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class ImageUtil {
    public static ImageIcon [] sliceImage(int rows, int cols, ImageIcon img) {
        BufferedImage orgImage = new BufferedImage(
                img.getIconWidth(),img.getIconHeight(), 
                BufferedImage.TYPE_INT_RGB);
        Graphics g = orgImage.createGraphics();
        img.paintIcon(null, g, 0, 0);
        g.dispose();
        
        int numOfSlice = rows * cols;

        int sliceWidth  = orgImage.getWidth()  / cols;
        int sliceHeight = orgImage.getHeight() / rows;
        
        ImageIcon [] imgArr = new ImageIcon[numOfSlice];
        
        int count = 0;
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                BufferedImage currImg = orgImage.getSubimage(sliceWidth * y, sliceHeight * x, sliceWidth, sliceHeight);
                imgArr[count++] = new ImageIcon(currImg);
            }
        }
        return imgArr; 
    }
    
    public static ImageIcon cropImage(ImageIcon img, int preferedWidth, int preferedHeight) {
        double widthRatio  = (double)preferedWidth  / img.getIconWidth();
        double heightRatio = (double)preferedHeight / img.getIconHeight();
        
        double ratio = (widthRatio > heightRatio) ? widthRatio : heightRatio;
        
        int newWidth  = (int) Math.ceil(img.getIconWidth()  * ratio);
        int newHeight = (int) Math.ceil(img.getIconHeight() * ratio);
        
        ImageIcon resultIcon = new ImageIcon(img.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        
        BufferedImage orgImage = new BufferedImage(resultIcon.getIconWidth(),resultIcon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = orgImage.createGraphics();
        resultIcon.paintIcon(null, g, 0, 0);
        g.dispose();
        
        BufferedImage finalImage = orgImage.getSubimage(0, 0, preferedWidth, preferedHeight);
        
        return (new ImageIcon(finalImage));
    }
}

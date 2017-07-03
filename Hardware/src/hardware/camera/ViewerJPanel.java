package hardware.camera;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.JPanel;

public class ViewerJPanel extends JPanel implements  ImageChangeListener {
    private CamStream camStream;
    private PercentArea percentArea = new PercentArea();
    private Image backingImage;
    private int imageWidth;
    private int imageHeight;
    
    public ViewerJPanel() {}
    public ViewerJPanel(int width, int height) {
        setSize(width, height);
    }

    public void setCurrentURL(URL mjpegStreamUrl)  {
        if (camStream != null) {
            camStream.removeImageChangeListener(this);
            camStream.unhook();
        }
        camStream = new CamStream(mjpegStreamUrl, 1, 1000, false);
        camStream.addImageChangeListener(this);
        camStream.start();
    }

    @Override
    public void imageChanged(ImageChangeEvent ce) {
        update(getGraphics());
        getToolkit().sync();
    }


    public void paint(Graphics g) {
        update(g);
    }


    public void update(Graphics g) {
        if (g == null) return;
        Dimension d = getSize();
        if (backingImage == null || backingImage.getWidth(this) != d.width || backingImage.getHeight(this) != d.height) {
            backingImage = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
        }
        Graphics gg2 = backingImage.getGraphics();
        if (camStream != null) {
            Image img = camStream.getCurrent();
            if (img != null) {
                paintFrame(gg2, img, d);
            }
        }
        g.drawImage(backingImage, 0, 0, null);
        gg2.dispose();
    }

    public void paintFrame(Graphics g, Image img, Dimension d) {
        imageWidth = img.getWidth(this);
        imageHeight = img.getHeight(this);
        if (imageWidth == -1 || imageHeight == -1) return;
        Rectangle imgarea = percentArea.getArea(imageWidth, imageHeight);
        g.drawImage(img, 0, 0, d.width, d.height, imgarea.x, imgarea.y, imgarea.x + imgarea.width, imgarea.y + imgarea.height, this);
    }  
}

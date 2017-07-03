package hardware.camera;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.IntBuffer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import sun.awt.image.IntegerComponentRaster;

/**
 *
 * @author blueskii
 */
public class ViewerCanvas extends Canvas implements  ImageChangeListener, Runnable {
    private GraphicsContext gc;
    private CamStream camStream;
    private java.awt.Image imgAwt;    
    private Image imgFx;
    private BufferedImage bufferedImage;
    private BufferedImage converted;
    
    public ViewerCanvas() {}
    public ViewerCanvas(double width, double height) {
        super(width, height);
    }
    
    public void setSize(double width, double height) {
        setWidth(width);
        setHeight(height);
    }
   
    public void setCurrentURL(URL mjpegStreamUrl)  {
        gc = getGraphicsContext2D();
        
        if (camStream != null) {
            camStream.removeImageChangeListener(this);
            camStream.unhook();
        }
        
        camStream = new CamStream(mjpegStreamUrl, 1, 1000, false);
        camStream.addImageChangeListener(this);
        camStream.start();
        
        Thread thread = new Thread(this);
        thread.start();
    }
    
    @Override
    public void run() {
        java.awt.Image imgAwt;
        while(true) {
            imageChanged(null);
        }
    }    
    
    @Override
    public void imageChanged(ImageChangeEvent ie) {
        imgAwt = camStream.getCurrent();
        try {
            if(imgAwt != null) {
                imgFx = createFXImage(imgAwt);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        gc.drawImage(imgFx, 0, 0);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 

    public Image createFXImage(java.awt.Image image) throws IOException {
        if(bufferedImage == null) {
            bufferedImage = new BufferedImage((int)getWidth(), (int)getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
        }
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, (int)getWidth(), (int)getHeight(), 0, 0, image.getWidth(null), image.getHeight(null), null);
        g.dispose();
        
        int bw = bufferedImage.getWidth();
        int bh = bufferedImage.getHeight();
        if(converted == null) {
            converted = new BufferedImage(bw, bh, BufferedImage.TYPE_INT_ARGB_PRE);
        }
        Graphics2D g2d = converted.createGraphics();
        g2d.drawImage(bufferedImage, 0, 0, null);
        g2d.dispose();
        bufferedImage = converted;
        WritableImage wimg = new WritableImage(bw, bh);
        PixelWriter pw = wimg.getPixelWriter();
        IntegerComponentRaster icr = (IntegerComponentRaster) bufferedImage.getRaster();
        int data[] = icr.getDataStorage();
        int offset = icr.getDataOffset(0);
        int scan = icr.getScanlineStride();
        PixelFormat<IntBuffer> pf = (bufferedImage.isAlphaPremultiplied() ?
                                     PixelFormat.getIntArgbPreInstance() :
                                     PixelFormat.getIntArgbInstance());
        pw.setPixels(0, 0, bw, bh, pf, data, offset, scan);
        return wimg;
    } 
}

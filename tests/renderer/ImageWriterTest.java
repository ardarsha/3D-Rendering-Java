package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import java.io.File;

class ImageWriterTest {

    private final String dir = "out/testImages/" + this.getClass().getName() + '/';

    ImageWriterTest(){
        File file = new File(dir);
        file.mkdirs();
    }

    @Test
    void writePixel() {
        ImageWriter ir = new ImageWriter(dir  + "writePixel", 50,50,500,500);
        ir.writePixel(0,0,new Color(255,255,255));
        ir.writePixel(49,1,new Color(255,255,255));
        ir.writeToimage();
    }

    @Test
    void writeToImage(){
        ImageWriter ir = new ImageWriter(dir  + "writeToImage", 500,500,500,500);
        for (int i = 0; i < ir.getHeight(); ++i) {
            for (int j = 0; j < ir.getWidth(); ++j) {
                if (i % 50 == 0 || j % 50 == 0) {
                    ir.writePixel(j, i, new Color(255, 255, 255));
                }
            }
        }

        ir.writeToimage();
    }
}
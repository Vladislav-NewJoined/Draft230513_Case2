import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/*import static java.lang.FdLibm.Cbrt.C;
import static sun.jvm.hotspot.debugger.x86.X86ThreadContext.PC;*/

//  Пример для этого класса здесь: https://kalanir.blogspot.com/2010/02/how-to-merge-multiple-images-into-one.html
public class Draft0_1_2_Case2 {
    public static void main(String[] args) throws IOException {
        int rows = 2;   //we assume the no. of rows and cols are known and each chunk has equal width and height
        int cols = 1;
        int chunks = rows * cols;

        int chunkWidth, chunkHeight;
        int type;
        //fetching image files
        File[] imgFiles = {new File("NASA_Photos_Of_Month\\image1.jpg"), new File("NASA_Photos_Of_Month\\image2.jpg")};
        /*for (int i = 0; i < chunks; i++) {
            imgFiles[i] = new File("NASA_Photos_Of_Month//image1");
        }*/

        //creating a bufferd image array from image files
        BufferedImage[] buffImages = new BufferedImage[chunks];
        for (int i = 0; i < chunks; i++) {
            buffImages[i] = ImageIO.read(imgFiles[i]);
        }
        type = buffImages[0].getType();
        chunkWidth = buffImages[0].getWidth();
        chunkHeight = buffImages[0].getHeight();

        //Initializing the final image
        BufferedImage finalImg = new BufferedImage(chunkWidth*cols, chunkHeight*rows, type);

        int num = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                finalImg.createGraphics().drawImage(buffImages[num], chunkWidth * j, chunkHeight * i, null);
                num++;
            }
        }
        System.out.println("Image concatenated.....");
        ImageIO.write(finalImg, "jpeg", new File("finalImg.jpg"));




    }

}

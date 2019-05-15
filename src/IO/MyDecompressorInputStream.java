package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
/**
 * Client MyDecompressorInputStream extends OutputStream
 * Decompressor byte array -
 * Take each time byte number and convert them to a 8 char`s.
 */

public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    public MyDecompressorInputStream(InputStream ips)
    {
        in = ips;
    }

    // Until the file not empty, read from it
    public int read(byte[] b) throws IOException{
        ArrayList<Byte> fromFile = new ArrayList<>();
            while (in.available() > 0)
                fromFile.add((byte) in.read());
            
        // read the details of the maze.
        for (int i = 0; i < 8; i++) {
            b[i] = fromFile.remove(0);
        }
        
        byte[] byteArrayFinal = new byte[b.length];
        int beginFrom = 8;
        while (fromFile.size() != 0) {
            byte byteNum = fromFile.remove(0);
            byte[] binaryValues = ByteToBinary(byteNum);
            //Every 8 binary
            for (int j = 0; j < 8; beginFrom++) {
                byteArrayFinal[beginFrom] = binaryValues[j];
                j++;
            }
            if (fromFile.size() == 1) {
                if (byteArrayFinal.length % 8 != 0) {//the maze is not n*n
                    byteNum = fromFile.remove(0);
                    byte[] lastBinaryValues = ByteToBinary(byteNum);
                    for (int j = 8 - byteArrayFinal.length % 8; j < 8; beginFrom++) {
                        byteArrayFinal[beginFrom] = lastBinaryValues[j];
                        j++;
                    }
                }
            }
        }
        for (int f = 8; f < byteArrayFinal.length; f++)
            b[f] = byteArrayFinal[f];//copy to original array
        return 0;
    }

    // Overwrite
    public int read() {
        return 0;
    }

    //Get byte number and convert it to binary vector
    private byte[] ByteToBinary(int b) {
        int Byte = b;
        if (Byte < 0)
            Byte += 256;
        byte[] binaryVec = new byte[8];
        for (int i = 7; i >= 0; i--) {
            binaryVec[i] = (byte) (Byte % 2);
            Byte = Byte / 2;
        }
        return binaryVec;
    }
}

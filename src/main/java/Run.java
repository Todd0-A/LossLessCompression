import bitSequence.BitSequence;
import org.jcodec.api.JCodecException;

import java.io.File;
import java.io.IOException;

public class Run {
    public static void main(String[] args) throws IOException, JCodecException {
        File video=new File("MovTest.mov");
        Compress compress =new Compress();
        VideoFrames frames=new VideoFrames(video);
        BitSequence[] b=new BitSequence[10000];
        for(int i=0;i<10000;i++){
            double p=i*0.0001;
            b[i] = CreatBits(p);
        }
        compress.compressionP(b);

        //Files compression
        //frames.VideoDecompression(frames.VideoDecompression1(frames.VideoCompression1(frames.VideoCompression())));

        //BitSequence[] CompressedFiles=compress.Compression("src/main/InputFiles/");

        //Files Decompression

        /*
        compress.DeCompression(CompressedFiles);
         */

        //Video file compression


        compress =new Compress();
       // BitSequence[] CompressedVideo=compress.VCompression(frames.VideoCompression());

        //Video file decompression
        /*
        frames.VideoDecompression(compress.VDecompression(CompressedVideo));
        */
    }

    private static BitSequence CreatBits(double p) {
        BitSequence bitSequence=new BitSequence(0,10000);
        int count= (int) (bitSequence.getBitCount()*p);
        for(int i=0;i<count;i++){
            bitSequence.setBitValue(i,true);
        }
        return bitSequence;
    }
}

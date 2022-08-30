import Hashes.RLE;
import bitSequence.BitSequence;
import org.jcodec.api.JCodecException;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Run {
    public static void main(String[] args) throws IOException, JCodecException {
        File video=new File("Mp4Test.mp4");
        Compress_Decompress compress =new Compress_Decompress();
        Huffman huffman = new Huffman();
        RLE rle= new RLE();
        VideoFrames frames=new VideoFrames(video);
        BitSequence[] b=new BitSequence[1000];
        for(int i=0;i<1000;i++){
            double p=i*0.001;
            b[i] = CreatBits(p,1000);
        }

        compress.compressionP(b);
        //System.out.println("________________________________________________");
        huffman.test(b);
        //rle.test(b);

        //Files compression
        //frames.VideoDecompression(frames.VideoDecompression1(frames.VideoCompression1(frames.VideoCompression())));

        //BitSequence[] CompressedFiles=compress.Compression("src/main/InputFiles/");

        //Files Decompression

        /*
        compress.DeCompression(CompressedFiles);
         */

        //Video file compression


        compress =new Compress_Decompress();
        //frames.VideoEncode();

        //Video file decompression

       // frames.VideoDecode(frames.VideoEncode());

    }

    private static BitSequence CreatBits(double p,int n) {
        Random random=new Random();
        BitSequence bitSequence=new BitSequence(0,n);
        int count= (int) (n*p);
        for(int i=0;i<count;i++){
            bitSequence.setBitValue(random.nextInt(bitSequence.getBitCount()),true);
            //bitSequence.setBitValue(i,true);
        }
        return bitSequence;
    }
}

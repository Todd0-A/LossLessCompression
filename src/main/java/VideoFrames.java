import bitSequence.BitSequence;
import com.google.common.primitives.Bytes;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.api.awt.AWTSequenceEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.*;
import org.jcodec.scale.AWTUtil;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VideoFrames{


    /*
        public static void main(String[] args) throws IOException, JCodecException {;
            File file = new File("1.mp4");
            byte[] bytes= Files.readAllBytes(file.toPath());
            System.out.println(new BitSequence(bytes).getBitCount());
            BitSequence bitSequence=new BitSequence(bytes);
            FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(file));
            List<BitSequence> b=VideoCompression(grab);
            Compress compress=new Compress();
            BitSequence[] encoders= compress.Compression(b);
            grab.seekToSecondSloppy(0);
            VideoDecompression(grab, List.of(encoders));
        }*/
    private File file;
    private FrameGrab grab;
    private Picture picture;
    private int Width;
    private int Height;
    private Rect Crop;
    private int LowBitsNum;
    private byte[][] LowBits;
    private ColorSpace color;
    public VideoFrames(File file) throws IOException, JCodecException {
      this.grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(file));
      this.picture=grab.getNativeFrame();
      this.Width=picture.getWidth();
      this.Height=picture.getHeight();
      this.Crop=picture.getCrop();
      this.LowBitsNum=picture.getLowBitsNum();
      this.LowBits=picture.getLowBits();
      this.color=picture.getColor();
      this.file=file;
    }

    //Video frames encode
    public List<BitSequence> VideoCompression() throws IOException {
        byte[][] data=picture.getData();
        BitSequence bitSequence=PictureDataToBitSequence(data);
        List<BitSequence> out = new ArrayList<>();
        out.add(PictureDataToBitSequence(data));
        int count=0;
        while (null != (picture = grab.getNativeFrame())) {
            count++;
            BitSequence xor= bitSequence.xor(PictureDataToBitSequence(picture.getData()));
            bitSequence=PictureDataToBitSequence(picture.getData());
            out.add(xor);
            System.out.println("FrameDataSize:\t"+ bitSequence.getBitCount()+"\tFrameNumber\t"+ out.size()+"\tP=\t"+out.get(count).getP());
        }
        return out;
    }
    public List<BitSequence> VideoCompression1(List<BitSequence> input) throws IOException {

        BitSequence bitSequence=input.get(0);
        List<BitSequence> out = new ArrayList<>();
        out.add(bitSequence);
        int count=0;
        for(int i=1;i<input.size();i++){
            count++;
            BitSequence xor= bitSequence.xor(input.get(i));
            bitSequence=input.get(i);
            out.add(xor);
            System.out.println("FrameDataSize:\t"+ bitSequence.getBitCount()+"\tFrameNumber\t"+ out.size()+"\tP=\t"+out.get(count).getP());
        }
        return out;
    }
    public List<BitSequence> VideoDecompression1(List<BitSequence> input) throws IOException, JCodecException {
        BitSequence bitSequence=input.get(0);
        List<BitSequence> out = new ArrayList<>();
        out.add(bitSequence);
        int count=0;
        for(int i=1;i<input.size();i++){
            count++;
            bitSequence=bitSequence.xor(input.get(i));
            out.add(bitSequence);
            System.out.println("FrameDataSize:\t"+ bitSequence.getBitCount()+"\tFrameNumber\t"+ out.size()+"\tP=\t"+out.get(count).getP());
        }
        return out;
    }
    //Video frames decode
    public void VideoDecompression(List<BitSequence> input) throws IOException, JCodecException {
        grab.seekToSecondSloppy(0);
        picture=grab.getNativeFrame();
        byte[][] data=picture.getData();
        BitSequence bitSequence=PictureDataToBitSequence(data);
        SeekableByteChannel out = null;
        try {
            out = NIOUtils.writableFileChannel("Out_"+file.getName());
            AWTSequenceEncoder encoder = new AWTSequenceEncoder(out, Rational.R(25, 1));
            BufferedImage image0 = AWTUtil.toBufferedImage(picture);
            encoder.encodeImage(image0);
            for (int i = 1; i < input.size(); i++) {
                    bitSequence=bitSequence.xor(input.get(i));
                    Picture cur = new Picture(Width, Height, ByteArrToByte(bitSequence.toByteArray(), data.length, data[0].length), LowBits, color, LowBitsNum, Crop);
                    BufferedImage image = AWTUtil.toBufferedImage(cur);
                    // Encode the image
                    encoder.encodeImage(image);
                }
            // Finalize the encoding, i.e. clear the buffers, write the header, etc.
            System.out.println("Finished");
            encoder.finish();
        } finally {
            NIOUtils.closeQuietly(out);
        }
    }
    public BitSequence PictureDataToBitSequence(byte[][] data){
        return new BitSequence(ByteArrToByte(data,data.length,data[0].length));
    }
    public byte[][] ByteArrToByte(byte[] b, int nNum1, int nNum2)
    {
        byte[][] newB = new byte[nNum1][nNum2];
        for (int i = 0; i < nNum1; i++)
        {
            for (int j = 0; j < nNum2; j++)
            {
                newB[i][j] = b[j + nNum2 * i];
            }
        }
        return newB;
    }
    public byte[] ByteArrToByte(byte[][] b, int nNum1, int nNum2)
    {
        List<Byte> list = new ArrayList<>();
        for (int i = 0; i < nNum1; i++)
        {
            for (int j = 0; j < nNum2; j++)
                list.add(b[i][j]);
        }
        return Bytes.toArray(list);
    }

}

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.image.BufferedImage;

public class test {
    public static void main(String[] args) throws IOException, JCodecException {
        File file = new File("src/main/InputFiles/1.mov");
        FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(file));
        List<Picture> pl= new ArrayList<>();

        BufferedImage bufferedImage = AWTUtil.toBufferedImage(grab.seekToFramePrecise(1000).getNativeFrame());
        ImageIO.write(bufferedImage, "png", new File("frame.png"));

    }

}

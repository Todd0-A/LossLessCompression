import Hashes.*;
import bitSequence.BitSequence;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Compress {

    public static void main(String[] args) throws IOException {
        BitSequence[] encoders = Compression();
        System.out.println("Compression Finished");
        Decompresssion(encoders);
        System.out.println("Decompression Finished");
    }
    //Encode
    public static List<Integer> nList=new ArrayList<>();
    public static List<Integer> lList=new ArrayList<>();
    public static List<String>  FileNames= new ArrayList<>();
    public static List<HashFunction[]> HashesList= new ArrayList<>();
    public static List<Integer> kList =new ArrayList<>();
    public static BitSequence[] Compression() throws  IOException{
        Random random = new Random();
        String filePath="src/main/InputFiles";
        File file=new File(filePath);
        File[] files = file.listFiles();
        BitSequence[] encoders = new BitSequence[files.length];
        for(int i=0;i<files.length;i++){
            int k= random.nextInt(3);
            kList.add(k+1);
            HashFunction[] hashes = creatHashes(k+1);
            HashesList.add(hashes);
            FileNames.add(files[i].getName());
            byte[] bytes=Files.readAllBytes(files[i].toPath());
            BitSequence bitSequence = new BitSequence(bytes);
            nList.add(bitSequence.getBitCount());
            lList.add((int) (bitSequence.getBitCount()*0.28));
            encoders[i] = Encoder.Encoder(nList.get(i),lList.get(i),hashes,bitSequence);
        }
        return  encoders;
    }
    public static void  Decompresssion(BitSequence[] encoders){
        for(int i=0;i<encoders.length;i++){
            BitSequence out=Decoder.Decoder(nList.get(i),lList.get(i),HashesList.get(i),encoders[i]);
            writeByte(out.toByteArray(),"src/main/OutputFiles/"+FileNames.get(i));
        }
    }
    public static HashFunction[] creatHashes(int k){
        HashFunction[] hashes =new HashFunction[k];
        Random random = new Random();
        for(int i=0;i<k;i++){
            int x =random.nextInt(9);
            int seed= random.nextInt(500);
            switch (x%8) {
                case 0 -> hashes[i] = new APHash(seed);
                case 1 -> hashes[i] = new BKDRHash(seed);
                case 2 -> hashes[i] = new DJBHash(seed);
                case 3 -> hashes[i] = new ELFHash(seed);
                case 4 -> hashes[i] = new JSHash(seed);
                case 5 -> hashes[i] = new PJWHash(seed);
                case 6 -> hashes[i] = new RSHash(seed);
                default -> hashes[i] = new Hash1(seed);
            }
        }
        return hashes;
    }
    public static void writeByte(byte[] bytes,String filePath)
    {
        File file = new File(filePath);
        try {
            OutputStream os = new FileOutputStream(file);
            os.write(bytes);
            os.close();
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

}
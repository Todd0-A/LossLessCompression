import Hashes.*;
import bitSequence.BitSequence;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Compress_Decompress {

    private List<Integer> nList;//recorde number of bits of each file
    private List<Integer> lList;//recorde length of each bloom filter bit sequence for each file
    private List<String>  FileNames;//recorde the filenames for compression and decompression
    private List<HashFunction[]> HashesList;//collection of k number hash functions list
    private List<Integer> kList;//recorde number of hash functions for each bloom filter
    private Encoder encoder;
    private Decoder decoder;
    public Compress_Decompress() {
        this.encoder= new Encoder();
        this.nList=new ArrayList<>();
        this.lList=new ArrayList<>();
        this.FileNames=new ArrayList<>();
        this.HashesList=new ArrayList<>();
        this.kList=new ArrayList<>();
        this.decoder=new Decoder();
    }
    //Files compression
    public BitSequence[] Compression(String filepath) throws  IOException{
        Random random = new Random();
        File file=new File(filepath);
        File[] files;
        files = file.listFiles();
        System.out.println(Arrays.toString(files));
        BitSequence[] encoders = new BitSequence[files.length];
        for(int i=0;i<files.length;i++){
            this.FileNames.add(files[i].getName());
            byte[] bytes=Files.readAllBytes(files[i].toPath());
            BitSequence bitSequence = new BitSequence(bytes);
            this.nList.add(bitSequence.getBitCount());
            this.lList.add((int) (bitSequence.getBitCount()*0.271));
            int k=(int)(1.447*Math.log((1-bitSequence.getP())*Math.pow(0.693,2)*Math.pow(bitSequence.getP(),-1)));
            if(k<=0)
                k=1;
            this.kList.add(k);
            HashFunction[] hashes = creatHashes(k);
            this.HashesList.add(hashes);
            encoders[i] = encoder.encoder(this.nList.get(i),this.lList.get(i),hashes,bitSequence);
            System.out.println(bitSequence.getP()+"\t"+kList.get(i)+"\t"+(double)(bitSequence.getBitCount())/(double)(encoders[i].getBitCount()));
        }
        return  encoders;
    }
    //Video file compression
    public BitSequence[] VCompression(List<BitSequence> list){
        Random random = new Random();
        BitSequence[] encoders = new BitSequence[list.size()];
        for(int i = 0; i<list.size(); i++){
            int k=(int)(1.447*Math.log((1-list.get(i).getP())*Math.pow(0.693,2)*Math.pow(list.get(i).getP(),-1)));
            if(k<=0)
                k=1;
            this.kList.add(k);
            HashFunction[] hashes = creatHashes(k);
            this.HashesList.add(hashes);
            this.nList.add(list.get(i).getBitCount());
            this.lList.add((int) (list.get(i).getBitCount()*0.271));
            encoders[i] = encoder.encoder(this.nList.get(i),this.lList.get(i),hashes,list.get(i));
            System.out.println(list.get(i).getP()+"\t"+kList.get(i)+"\t"+(double) (list.get(i).getBitCount())/(double) (encoders[i].getBitCount()));
        }
        return  encoders;
    }
    //Files decompression
    public void  DeCompression(BitSequence[] encoders){
        for(int i=0;i<encoders.length;i++){
            BitSequence out=decoder.decoder(this.nList.get(i),this.lList.get(i),this.HashesList.get(i),encoders[i]);
            writeByte(out.toByteArray(),"src/main/OutputFiles/Out_"+this.FileNames.get(i));
        }
    }
    //video file decompression
    public List<BitSequence>  VDecompression(BitSequence[] encoders){
        List<BitSequence> decoder =new ArrayList<>();
        for(int i=0;i<encoders.length;i++){
            BitSequence out=this.decoder.decoder(this.nList.get(i),this.lList.get(i),this.HashesList.get(i),encoders[i]);
            decoder.add(out);
        }
        return  decoder;
    }
    //creat a k number hash function array
    private HashFunction[] creatHashes(int k){
        HashFunction[] hashes =new HashFunction[k];
        Random random = new Random();
        for(int i=0;i<k;i++){
            int seed= random.nextInt(500);
            hashes[i]=new GHash(seed,i);
        }
        return hashes;
    }
    //write byte array to file
    private void writeByte(byte[] bytes,String filePath)
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

    public void compressionP(BitSequence[] input) {
        for(int i=0;i<input.length;i++){
            int k=(int)(1.447*Math.log((1-input[i].getP())*Math.pow(0.693,2)*Math.pow(input[i].getP(),-1)));
            if(k<=0)
                k=1;
            if(k>=16)
                k=16;
            HashFunction[] hashes = creatHashes(k);

            long startTime = System.nanoTime();
            BitSequence b=encoder.encoder(input[i].getBitCount(),(int)(input[i].getBitCount()*input[i].getP()*k*1.447+1),hashes,input[i]);
            //decoder.decoder(input[i].getBitCount(),(int)(input[i].getBitCount()*input[i].getP()*k*1.447+1),hashes,b);
            long endTime = System.nanoTime();
            System.out.println(input[i].getP()+"\t"+k+"\t"+(double)(input[i].getBitCount())/(double)(b.getBitCount()));
            //System.out.println(input[i].getP()+"\t"+(endTime - startTime));
        }
    }
}
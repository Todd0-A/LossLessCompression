import Hashes.HashFunction;
import bitSequence.BitSequence;
import java.util.BitSet;
public class Encoder {

    /*
            Input: a positive integer n;
            Input: the bitmap’s size, l , such that 1 ≤ l ≤ n
            Input: set, H, consisting of k random functions from {0, . . . , l − 1}{0,...,n−1};
            Input: a length-n bit sequence, I;
             */
    public BitSequence encoder(int n, int l, HashFunction[] H, BitSequence I){

        //Add ture value of input to bloom filter
        int prime = HashFunction.getPrimeNumber(n);
        BitSet bitmap= new BitSet();
        for(int i=0;i<n;i++){
            if(I.getBitValue(i)){
                for(HashFunction h: H) {
                    bitmap.set(h.hash(l, i,prime), true);
                }
            }
        }

        //Construct witness and append to Bloom filter bit sequence
        int index = l;
        for(int i=0;i<n;i++){
            if(I.getBitValue(i)){
                bitmap.set(index,true);
                index++;
            }
            else{
                boolean fp=true;
                for(HashFunction h :H){
                    if(!bitmap.get(h.hash(l,i,prime))){
                        fp=false;
                        break;
                    }
                }
                if(fp){
                    bitmap.set(index,false);
                    index++;
                }
            }
        }
        BitSequence output =new BitSequence(0,index);
        for(int i=0;i<index;i++){
            output.setBitValue(i,bitmap.get(i));
        }
        return  output;
    }

}

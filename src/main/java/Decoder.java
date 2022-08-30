import Hashes.HashFunction;
import bitSequence.BitSequence;

public class Decoder {
    /*
    n: number of bits
    l:length of bloom filter bit sequence
    H: Hash function array of bloom filter
    B:input bloom filter and witness bit sequence
     */
    public BitSequence decoder(int n, int l, HashFunction[] H, BitSequence B){

        //Construct output bit sequence
        BitSequence output=new BitSequence(new byte[n/8]);
        int index=l;
        int prime = HashFunction.getPrimeNumber(n);

        //Decode values from bloom filter and witness bit sequence to output
        for(int i=0;i<n;i++){
            boolean tp=true;
            for(HashFunction h : H){
                if(!B.getBitValue(h.hash(l,i,prime))){
                    tp=false;
                    break;
                }
            }
            if(tp){
                tp=B.getBitValue(index);
                index++;
            }

            output.setBitValue(i,tp);
        }
        return output;
    }

}

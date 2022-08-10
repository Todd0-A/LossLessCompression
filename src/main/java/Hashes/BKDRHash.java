package Hashes;

import com.google.common.primitives.Longs;

public class BKDRHash implements HashFunction{
    private int seed;
    public BKDRHash(int seed){
        this.seed = seed;
    }

    public int getSeed() {
        return seed;
    }

    private String HashName="BKDRHash";
    public String getHashName() {
        return HashName;
    }
    @Override
    public int hash(int n, int value,int p) {
        byte[] bytes = Longs.toByteArray(value);
        int hash = 0;
        int len = bytes.length;
        for (int i = 0; i < len; i++) {
            hash = (hash *seed) + bytes[i];
        }
        return Math.abs(hash%n);
    }
}

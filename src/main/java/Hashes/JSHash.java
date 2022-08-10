package Hashes;

import com.google.common.primitives.Longs;

import java.util.Random;

public class JSHash implements HashFunction{

    private final int seed;

    public JSHash(int seed) {
        this.seed = seed;
    }

    public int getSeed() {
        return seed;
    }

    @Override
    public int hash(int n, int value,int p) {
        Random random = new Random(seed);
        byte[] bytes = Longs.toByteArray(value);
        int hash =random.nextInt(n);
        for (int i = 0; i < bytes.length; i++) {
            hash ^= ((hash << 5) + bytes[i] + (hash >> 2));
        }
        return Math.abs(hash%n);
    }
    private String HashName="JSHash";
    public String getHashName() {
        return HashName;
    }
}

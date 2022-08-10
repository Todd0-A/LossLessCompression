package Hashes;

import com.google.common.primitives.Longs;

import java.util.Random;

public class DJBHash implements HashFunction{

    private final int seed;

    public DJBHash(int seed){ this.seed =seed;
    }
    private String HashName="DJBHash";

    public int getSeed() {
        return seed;
    }

    public String getHashName() {
        return HashName;
    }
    @Override
    public int hash(int n, int value,int p) {
        Random random = new Random(seed);
        byte[] bytes = Longs.toByteArray(value);
        int hash =random.nextInt(n);
        int len = bytes.length;
        for (int i = 0; i < len; i++) {
            hash = ((hash << 5) + hash) + bytes[i];
        }
        return Math.abs(hash%n);
    }
}

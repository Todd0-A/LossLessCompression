package Hashes;

import com.google.common.primitives.Longs;

import java.util.Random;

public class APHash implements HashFunction{
    private int seed;
    public APHash(int seed){this.seed = seed;}
    private String HashName="APHash";
    public String getHashName() {
        return HashName;
    }

    public int getSeed() {
        return seed;
    }

    @Override
    public int hash(int n, int value,int p) {
        Random random = new Random(seed);
        byte[] bytes= Longs.toByteArray(value);
        int hash = random.nextInt(n);
        int len = bytes.length;
        for (int i = 0; i < len; i++) {
            if ((i & 1) == 0) {
                hash ^= ((hash << 7) ^ bytes[i] ^ (hash >> 3));
            } else {
                hash ^= (~((hash << 11) ^ bytes[i] ^ (hash >> 5)));
            }
        }
        return Math.abs(hash%n);
    }
}

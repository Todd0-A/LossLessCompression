package Hashes;

import com.google.common.primitives.Longs;

import java.util.Random;

public class ELFHash implements HashFunction{
    private final int seed;

    @Override
    public int hash(int n, int value,int p) {
        Random random = new Random(seed);
        byte[] bytes = Longs.toByteArray(value);
        int hash = random.nextInt(n);
        int x = random.nextInt(n);
        int len = bytes.length;
        for (int i = 0; i < len; i++) {
            hash = (hash << 4) + bytes[i];
            if ((x = hash & 0xF0000000) != 0) {
                hash ^= (x >> 24);
                hash &= ~x;
            }
        }
        return Math.abs(hash%n);
    }

    public int getSeed() {
        return seed;
    }

    public ELFHash(int seed) {
        this.seed = seed;
    }
    private String HashName="ELFHash";
    public String getHashName() {
        return HashName;
    }
}

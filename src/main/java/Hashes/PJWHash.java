package Hashes;

import com.google.common.primitives.Longs;

import java.util.Random;

public class PJWHash implements HashFunction{

    private final int seed;

    public PJWHash(int seed) {
        this.seed =seed;
    }

    public int getSeed() {
        return seed;
    }

    @Override
    public int hash(int n, int value,int p) {
        Random random = new Random(seed);
        byte[] bytes= Longs.toByteArray(value);
        long BitsInUnsignedInt = (4*8);
        long ThreeQuarters = ((BitsInUnsignedInt * 3) / 4);
        long OneEighth = (BitsInUnsignedInt / 8);
        long HighBits = (long) (0xFFFFFFFF) << (BitsInUnsignedInt - OneEighth);
        int hash = random.nextInt(n);
        long test = random.nextLong();
        for (int i = 0; i < bytes.length; i++) {
            hash = (hash << OneEighth) + bytes[i];
            if ((test = hash & HighBits) != 0) {
                hash = (int) ((hash ^ (test >> ThreeQuarters)) & (~HighBits));
            }
        }
        return Math.abs(hash%n);
    }
    private String HashName="PJWHash";
    public String getHashName() {
        return HashName;
    }
}

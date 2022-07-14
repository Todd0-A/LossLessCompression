package Hashes;

import Hashes.HashFunction;
import com.google.common.primitives.Longs;

import java.util.Random;

public class RSHash implements HashFunction {
    private int seed;
    public RSHash(int seed){
        this.seed = seed;
    }

    public int getSeed() {
        return seed;
    }

    @Override
    public int hash(int n, int value,int p) {
        byte[] bytes = Longs.toByteArray(value);
        Random random = new Random(seed);
        int hash = 0;
        int magic = p;
        int len = bytes.length;
        for (int i = 0; i < len; i++) {
            hash = hash *magic + bytes[i];
            magic = magic*HashFunction.getPrimeNumber(random.nextInt(10)*n);
        }
        return Math.abs(hash%n);
    }
    private String HashName="RSHash";
    public String getHashName() {
        return HashName;
    }}
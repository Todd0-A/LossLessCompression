package Hashes;

import Hashes.HashFunction;

import java.util.Random;
import java.util.stream.IntStream;

public class Hash1 implements HashFunction {
    private int seed;

    public int getSeed() {
        return seed;
    }

    public Hash1(int seed){
        this.seed = seed;
    }
    @Override
    public int hash(int n,int value,int p) {
        Random random = new Random(seed);
        int a = Math.abs(random.nextInt(10000));
        int b = Math.abs(random.nextInt(10000));
        return  Math.abs(((a*value+b)%p)%n);
    }
    private String HashName="SimpleHash";
    public String getHashName() {
        return HashName;
    }
}

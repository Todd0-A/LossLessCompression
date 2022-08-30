package Hashes;

import java.util.Random;

public class GHash implements HashFunction{
    private int seed;
    private Hash1 hash1;
    private BKDRHash bkdrHash;
    private int i;
    public GHash(int seed,int i) {
        this.seed=seed;
        this.bkdrHash = new BKDRHash(seed);
        this.hash1 = new Hash1(seed);
        this.i =i;
    }

    @Override
    public int hash(int n, int value, int p) {
        return (hash1.hash(n,value,p)+this.i*bkdrHash.hash(n,value,p))%n;
    }
    @Override
    public String getHashName() {
        return this.getClass().getName();
    }

    @Override
    public int getSeed() {
        return seed;
    }
}

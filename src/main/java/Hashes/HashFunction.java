package Hashes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public interface HashFunction {
    //Get hash number of value,n: number of bits,p: prime number
    abstract public int hash(int n,int value,int p);
    abstract public String getHashName();
    abstract public int getSeed();
    public static int getPrimeNumber(int n){
        while (true){
            n++;
            if (isPrime(n))
                return n;
        }
    }
    public static boolean isPrime(int x) {
        return IntStream.rangeClosed(2, (int) (Math.sqrt(x)))
                .allMatch(n -> x % n != 0);
    }
}

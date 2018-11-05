package com.project.java.presentation.tools.oca.java;

/**
 * Created by john on 03.01.2016.
 */
public class Test {

    public static void main(String[] args) {
        int m = 9, n=1, x=0;
        while (m > n){
            m--;
            n += 2;
            x +=m + n;
        }
        System.out.println(x);
    }
}

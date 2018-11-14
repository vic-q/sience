package com.demo.sience;

import java.util.Arrays;
import java.util.List;

/**
 * @author wangqing 
 */
public class Test2 {

    public static void main(String[] args) {
        String str = "azzzzhhhhhxyzzzzzzz";
        int[] array = {1,2,3,4,5,6,7,8};

        System.out.println("left=" + test(array).get(0));
        System.out.println("right=" + test(array).get(1));

    }

    public static List<Integer> test(int[] array) {
        int max = 0;
        int idx = 0;
        for (int n = 0; n < array.length; n++) {
            int cn = array[n];
            int count = 1;
            for (int i = n + 1; i < array.length; i++) {
                int ci = array[i];
                if ((ci - cn) == 1) {
                    count++;
                    if (count > max) {
                        max = count;
                        idx = n;
                    }
                } else {
                    break;
                }
            }
        }
        return Arrays.asList(array[idx], array[idx+max]);
        //array.substring(idx, idx + max);
    }

}

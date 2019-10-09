package com.validity.exercise.potentialDuplicates.helper;

import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * This class is the implementation of LevenshteinDistance algorithm.
 *
 */
@Service
public class LevenshteinDistance {
	/**
	 * 
	 * @param x String 1 
	 * @param y String 2
	 * @return integer distance between the strings x,y
	 */
	public static int calculate(String x, String y) {
	    int[][] dp = new int[x.length() + 1][y.length() + 1];
	 
	    for (int i = 0; i <= x.length(); i++) {
	        for (int j = 0; j <= y.length(); j++) {
	            if (i == 0) {
	                dp[i][j] = j;
	            }
	            else if (j == 0) {
	                dp[i][j] = i;
	            }
	            else {
	                dp[i][j] = findMin(dp[i - 1][j - 1]
	                 + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)), 
	                  dp[i - 1][j] + 1,
	                  dp[i][j - 1] + 1);
	            }
	        }
	    }
	 
	    return dp[x.length()][y.length()];
	}

	//changes made here @Jigar
	private static int findMin(int num1, int num2, int num3) {
		int min = Integer.MAX_VALUE;
		min = Math.min(num1,min);
		min = Math.min(num2,min);
		min = Math.min(num3,min);
		return min;
	}

	public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }
 
//    public static int min(int... numbers) {
//        return Arrays.stream(numbers)
//          .min().orElse(Integer.MAX_VALUE);
//    }


}

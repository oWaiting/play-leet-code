package com.me.play.code;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <a href="https://leetcode.cn/problems/minimum-number-of-arrows-to-burst-balloons/description/?envType=study-plan-v2&envId=leetcode-75"></a>
 */
public class Solution452 {


    public int findMinArrowShots(int[][] points) {

        Arrays.sort(points, Comparator.comparingInt(o -> o[1]));

        int pos = points[0][1];
        int ans = 1;
        for (int[] p : points) {
            if (p[0] > pos) {
                pos = p[1];
                ans++;
            }
        }
        return ans;
    }

}

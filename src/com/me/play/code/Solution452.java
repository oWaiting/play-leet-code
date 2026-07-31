package com.me.play.code;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 452. 用最少数量的箭引爆气球
 * <a href="https://leetcode.cn/problems/minimum-number-of-arrows-to-burst-balloons/description/?envType=study-plan-v2&envId=leetcode-75">题目链接</a>
 * <p>
 * 题目：有一些球形气球贴在一面墙上（XY 平面），用数组 points 表示，
 * 其中 points[i] = [x_start, x_end] 表示一个气球的水平直径从 x_start 到 x_end。
 * 可以从 x 轴上任意点垂直射出箭，若箭在 x 处射出，
 * 则所有满足 x_start <= x <= x_end 的气球都会被引爆。
 * 返回引爆所有气球所需的最少箭数。
 * <p>
 * 思路：贪心算法 —— 按气球「右边界」升序排序，
 * 第一支箭射在第一个气球的右边界（贪心选择：尽可能多地引爆后续重叠气球），
 * 后续气球如果左边界 > 当前箭的位置，说明无法被这支箭引爆，需要新射一支箭。
 * <p>
 * 与 435 题（无重叠区间）的异同：
 * - 相同点：都按右边界排序，贪心地选择最早结束的位置
 * - 不同点：435 题统计「需要移除多少区间」，本题统计「需要多少支箭（即有多少组不重叠区间）」
 * - 重叠判定：本题边界相接（p[0] == pos）算重叠，一支箭可以引爆；435 题边界相接不算重叠
 */
public class Solution452 {

    /**
     * 计算引爆所有气球所需的最少箭数
     *
     * @param points 气球数组，points[i] = [x_start, x_end] 表示气球的水平直径
     * @return 最少箭数
     */
    public int findMinArrowShots(int[][] points) {
        // 边界情况：没有气球，不需要箭
        if (points == null || points.length == 0) {
            return 0;
        }

        // 第一步：按气球「右边界」升序排序
        // 贪心核心：优先处理结束最早的气球，把箭射在它的右边界，
        // 这样这支箭能覆盖的范围尽可能多地延伸到右侧，引爆更多重叠气球
        // 例如 [[10,16],[2,8],[1,6],[7,12]] 排序后为 [[1,6],[2,8],[7,12],[10,16]]
        Arrays.sort(points, Comparator.comparingInt(o -> o[1]));

        // 第二步：贪心射箭
        // pos 记录当前这支箭的射击位置（初始为第一个气球的右边界）
        int pos = points[0][1];
        // ans 记录箭的数量，第一支箭已经确定
        int ans = 1;

        // 遍历所有气球
        for (int[] p : points) {
            if (p[0] > pos) {
                // 当前气球的左边界 > 当前箭的位置 → 这支箭射不到这个气球
                // 需要新射一支箭，位置设在当前气球的右边界（贪心选择）
                pos = p[1];
                ans = ans + 1;
            }
            // 否则 p[0] <= pos，当前气球能被这支箭引爆，无需额外操作
            // 注意：这里用 > 而非 >=，因为边界相接（p[0] == pos）时箭刚好能引爆气球
        }


        return ans;
    }

}

package com.me.play.code;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 435. 无重叠区间
 * <a href="https://leetcode.cn/problems/non-overlapping-intervals/solutions/3077218/tan-xin-zheng-ming-pythonjavaccgojsrust-3jx4f/?envType=study-plan-v2&envId=leetcode-75">题目链接</a>
 * <p>
 * 题目：给定一个区间的集合 intervals，其中 intervals[i] = [start_i, end_i]，
 * 返回需要移除区间的最小数量，使剩余区间互不重叠。
 * <p>
 * 思路：贪心算法 —— 按区间「结束时间」升序排序，优先保留结束最早的区间，
 * 因为结束越早，给后续区间留出的空间越大，能保留的不重叠区间就越多，
 * 从而需要移除的区间数最少。
 */
public class Solution75 {

    /**
     * 计算需要移除的最少区间数
     *
     * @param intervals 区间数组，intervals[i] = [start_i, end_i]
     * @return 需要移除的区间数量
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        // 边界情况：空数组或只有一个区间，不存在重叠
        if (intervals == null || intervals.length <= 1) {
            return 0;
        }

        // 第一步：按区间「结束值」升序排序
        // 贪心核心：结束越早的区间越优先保留，给后面留出更多空间
        // 例如 [[1,100],[2,3],[4,5]] 排序后为 [[2,3],[4,5],[1,100]]
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));

        // 第二步：遍历排序后的区间，统计最多能保留多少个不重叠区间
        // end 记录上一个被保留区间的结束值
        int end = intervals[0][1];
        // removeCount 记录需要移除的区间数
        int removeCount = 0;

        // 从第二个区间开始遍历（第一个区间默认保留）
        for (int i = 1; i < intervals.length; i = i + 1) {
            if (intervals[i][0] >= end) {
                // 当前区间的起始值 >= 上一个保留区间的结束值 → 不重叠，保留
                // 更新 end 为当前区间的结束值
                end = intervals[i][1];
            } else {
                // 当前区间与上一个保留区间重叠 → 必须移除当前区间
                // 注意：这里不移除上一个保留区间，因为它结束更早，保留它更优
                removeCount = removeCount + 1;
            }
        }

        return removeCount;
    }
}

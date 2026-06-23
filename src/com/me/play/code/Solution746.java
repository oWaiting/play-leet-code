package com.me.play.code;

public class Solution746 {

    /**
     * <pre>
     * 给你一个整数数组 cost ，其中 cost[i] 是从楼梯第 i 个台阶向上爬需要支付的费用。一旦你支付此费用,即可选择向上爬一个或者两个台阶。
     *
     * 你可以选择从下标为 0 或下标为 1 的台阶开始爬楼梯。
     *
     * 请你计算并返回达到楼梯顶部的最低花费。
     *
     *
     *
     * 示例 1：
     *
     * 输入：cost = [10,15,20]
     * 输出：15
     * 解释：你将从下标为 1 的台阶开始。
     * - 支付 15 ，向上爬两个台阶，到达楼梯顶部。
     * 总花费为 15 。
     * 示例 2：
     *
     * 输入：cost = [1,100,1,1,1,100,1,1,100,1]
     * 输出：6
     * 解释：你将从下标为 0 的台阶开始。
     * - 支付 1 ，向上爬两个台阶，到达下标为 2 的台阶。
     * - 支付 1 ，向上爬两个台阶，到达下标为 4 的台阶。
     * - 支付 1 ，向上爬两个台阶，到达下标为 6 的台阶。
     * - 支付 1 ，向上爬一个台阶，到达下标为 7 的台阶。
     * - 支付 1 ，向上爬两个台阶，到达下标为 9 的台阶。
     * - 支付 1 ，向上爬一个台阶，到达楼梯顶部。
     * 总花费为 6 。
     *
     * 提示：
     *
     * 2 <= cost.length <= 1000
     * 0 <= cost[i] <= 999
     * <pre/>
     * @param cost 每个台阶向上爬需要支付的费用数组
     * @return 达到楼梯顶部的最低花费
     */
    public int minCostClimbingStairs(int[] cost) {
        // dp[i] 表示到达第 i 个台阶的最小花费
        // 数组长度为 cost.length + 1，因为楼梯顶部是最后一个台阶之后的位置
        int[] dp = new int[cost.length + 1];

        // 初始状态：可以从第 0 或第 1 个台阶开始，所以到达这两个台阶的花费为 0
        dp[0] = 0;  // 到达第 0 个台阶的花费
        dp[1] = 0;  // 到达第 1 个台阶的花费

        // 动态规划：计算到达每个台阶的最小花费
        // 对于第 i 个台阶，可以从第 i-1 个台阶爬 1 步，或者从第 i-2 个台阶爬 2 步
        for (int i = 2; i <= cost.length; i++) {
            // 到达第 i 个台阶的最小花费 = min(
            //     从第 i-1 个台阶爬 1 步的花费: dp[i-1] + cost[i-1],
            //     从第 i-2 个台阶爬 2 步的花费: dp[i-2] + cost[i-2]
            // )
            dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
        }

        // 返回到达楼梯顶部（最后一个台阶之后）的最小花费
        return dp[cost.length];

    }


    public static void main(String[] args) {
        int cost = new Solution746().minCostClimbingStairs(new int[]{10, 15, 20});

        System.out.println(cost);

        cost = new Solution746().minCostClimbingStairs(new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1});

        System.out.println(cost);
    }

}
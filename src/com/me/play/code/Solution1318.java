package com.me.play.code;

/**
 * <a href="https://leetcode.cn/problems/minimum-flips-to-make-a-or-b-equal-to-c/description/?envType=study-plan-v2&envId=leetcode-75"></a>
 */
public class Solution1318 {


    /**
     * 逐位分析 a, b, c 的二进制表示：
     * - 如果 c 的某一位是 1：a OR b 对应位至少有一个为 1 即可，若 a 和 b 该位都是 0，则需翻转 1 次
     * - 如果 c 的某一位是 0：a OR b 对应位必须都为 0，a 和 b 中有几个 1 就需要翻转几次（最多 2 次）
     */
    public int minFlips(int a, int b, int c) {
        int flips = 0;
        // 题目约束 a, b, c <= 10^9 ≈ 2^30，最多需要 30 个二进制位
        // Java int 固定 32 位，写 32 是保守写法，覆盖所有位，多出的高位都是 0 不影响结果
        for (int i = 0; i < 32; i++) {
            // 提取第 i 位（位编号从 0 开始，从右往左）：
            // >> 右移 i 位把目标位移到最低位，& 1 只保留最低位，其余清零
            // 例：a=13(1101), i=2 → 1101>>2 = 0011 → 0011&1 = 1，即第2位是1
            int bitA = (a >> i) & 1;
            int bitB = (b >> i) & 1;
            int bitC = (c >> i) & 1;

            if (bitC == 1) {
                // c 该位为 1，a OR b 该位至少有一个 1
                // 如果 a 和 b 该位都是 0，需要翻转 1 次
                if (bitA == 0 && bitB == 0) {
                    flips = flips + 1;
                }
            } else {
                // c 该位为 0，a OR b 该位必须都为 0
                // a 和 b 该位有几个 1 就需要翻转几次
                flips = flips + bitA + bitB;
            }
        }
        return flips;
    }

    public static void main(String[] args) {
        int retVal = new Solution1318().minFlips(2, 6, 5);
        System.out.println(retVal);
    }
}

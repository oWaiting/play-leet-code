package com.me.play.code;

import java.util.Arrays;

/**
 * <h1>基数排序 (Radix Sort)</h1>
 *
 * <p><b>核心思想：</b>基数排序是一种非比较排序，按照数字的每一位进行排序。
 * 从最低位（LSD，Least Significant Digit）开始，依次对每一位进行稳定排序（通常用计数排序），
 * 直到最高位排序完成，整个数组就有序了。</p>
 *
 * <h2>算法步骤：</h2>
 * <ol>
 *   <li>找出数组中的最大值，确定最大位数（决定了排序的轮数）</li>
 *   <li>从个位开始，对每一位使用计数排序（或其他稳定排序）</li>
 *   <li>逐位处理：个位 → 十位 → 百位 → ... → 最高位</li>
 *   <li>所有位处理完成后，数组就完全有序了</li>
 * </ol>
 *
 * <h2>复杂度分析：</h2>
 * <ul>
 *   <li><b>时间复杂度：</b>O(d × (n + k))，d 是最大位数，k 是基数（通常为 10）</li>
 *   <li><b>空间复杂度：</b>O(n + k)，需要计数数组和输出数组</li>
 *   <li><b>稳定性：</b>稳定（每一轮使用稳定排序）</li>
 * </ul>
 *
 * <h2>为什么从低位开始？</h2>
 * <p>从低位到高位（LSD）排序，高位排序的结果不会破坏低位已经排好的顺序，
 * 因为每一位都使用稳定排序。例如：先按个位排，再按十位排时，十位相同的元素
 * 会保持个位排序的顺序。</p>
 *
 * <h2>适用场景：</h2>
 * <p>适合整数、字符串等固定长度或有限长度的数据。当数字位数 d 较小时非常高效。</p>
 *
 * @author AndyHe
 */
public class RadixSort {

    /**
     * 基数排序（LSD 版本，从最低位到最高位）
     *
     * @param arr 待排序的非负整数数组
     */
    public static void radixSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int n = arr.length;

        // 第一步：找到最大值，确定最大位数
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        // 第二步：对每一位（个、十、百、千...）进行计数排序
        // exp 表示当前处理的位：1=个位, 10=十位, 100=百位, ...
        // 当 max/exp == 0 时，说明所有数字的当前位都已经处理完毕
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(arr, n, exp);
        }
    }

    /**
     * 对数组的某一位进行计数排序
     *
     * <p>例如 exp=1 时按个位排序，exp=10 时按十位排序。</p>
     *
     * @param arr 原数组
     * @param n   数组长度
     * @param exp 当前位数（1, 10, 100, ...）
     */
    private static void countingSortByDigit(int[] arr, int n, int exp) {
        // 十进制，基数固定为 10（每位数字 0~9）
        int[] count = new int[10];
        int[] output = new int[n];

        // 统计当前位每个数字（0-9）出现的次数
        // digit = (arr[i] / exp) % 10
        for (int i = 0; i < n; i++) {
            int digit = (arr[i] / exp) % 10;
            count[digit]++;
        }

        // 前缀和：count[i] 表示当前位数字 ≤ i 的元素个数
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // 反向遍历，保证稳定性
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            int pos = count[digit] - 1;
            output[pos] = arr[i];
            count[digit]--;
        }

        // 拷贝回原数组
        System.arraycopy(output, 0, arr, 0, n);
    }

    /**
     * 测试入口
     */
    public static void main(String[] args) {
        System.out.println("========== 基数排序 (Radix Sort) ==========\n");

        // 测试用例 1：普通乱序数组
        int[] arr1 = {170, 45, 75, 90, 802, 24, 2, 66};
        System.out.println("测试 1 - 普通乱序数组：");
        System.out.println("  排序前：" + Arrays.toString(arr1));
        radixSort(arr1);
        System.out.println("  排序后：" + Arrays.toString(arr1));
        System.out.println("  验证：" + (isSorted(arr1) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 2：不同位数的数字
        int[] arr2 = {3, 89, 456, 12, 7890, 1, 234, 56, 7, 901};
        System.out.println("\n测试 2 - 不同位数的数字：");
        System.out.println("  排序前：" + Arrays.toString(arr2));
        radixSort(arr2);
        System.out.println("  排序后：" + Arrays.toString(arr2));
        System.out.println("  验证：" + (isSorted(arr2) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 3：大量重复元素
        int[] arr3 = {123, 123, 456, 123, 789, 456, 789, 123};
        System.out.println("\n测试 3 - 大量重复元素：");
        System.out.println("  排序前：" + Arrays.toString(arr3));
        radixSort(arr3);
        System.out.println("  排序后：" + Arrays.toString(arr3));
        System.out.println("  验证：" + (isSorted(arr3) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 4：已有序数组
        int[] arr4 = {1, 2, 3, 10, 20, 30, 100, 200, 300};
        System.out.println("\n测试 4 - 已有序数组：");
        System.out.println("  排序前：" + Arrays.toString(arr4));
        radixSort(arr4);
        System.out.println("  排序后：" + Arrays.toString(arr4));
        System.out.println("  验证：" + (isSorted(arr4) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 5：包含 0 的数组
        int[] arr5 = {0, 100, 50, 0, 25, 75, 0, 10};
        System.out.println("\n测试 5 - 包含 0 的数组：");
        System.out.println("  排序前：" + Arrays.toString(arr5));
        radixSort(arr5);
        System.out.println("  排序后：" + Arrays.toString(arr5));
        System.out.println("  验证：" + (isSorted(arr5) ? "✓ 通过" : "✗ 失败"));
    }

    private static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
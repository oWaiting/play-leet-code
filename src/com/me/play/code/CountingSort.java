package com.me.play.code;

import java.util.Arrays;

/**
 * <h1>计数排序 (Counting Sort)</h1>
 *
 * <p><b>核心思想：</b>计数排序不是基于比较的排序算法。它统计每个元素出现的次数，
 * 然后根据元素的值直接计算出它在有序数组中的位置。</p>
 *
 * <h2>算法步骤：</h2>
 * <ol>
 *   <li>找出数组中的最大值和最小值，确定计数范围</li>
 *   <li>创建一个计数数组 count，count[i] 表示元素 i+min 出现的次数</li>
 *   <li>遍历原数组，统计每个元素出现次数</li>
 *   <li>对计数数组做前缀和，此时 count[i] 表示 ≤ i+min 的元素个数</li>
 *   <li>反向遍历原数组，根据计数数组确定每个元素的位置，保证稳定性</li>
 * </ol>
 *
 * <h2>复杂度分析：</h2>
 * <ul>
 *   <li><b>时间复杂度：</b>O(n + k)，其中 k 是数据的取值范围（max - min）</li>
 *   <li><b>空间复杂度：</b>O(n + k)，需要计数数组和输出数组</li>
 *   <li><b>稳定性：</b>稳定（反向遍历即可保证）</li>
 * </ul>
 *
 * <h2>适用场景：</h2>
 * <p>当数据范围 k 远小于数据量 n 时（如成绩排名、年龄统计），计数排序非常高效。
 * 但如果 k 很大（如数据范围 0~10^9），则不适合，因为计数数组会太大。</p>
 *
 * @author AndyHe
 */
public class CountingSort {

    /**
     * 计数排序（稳定版）
     *
     * @param arr 待排序的整数数组
     */
    public static void countingSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int n = arr.length;

        // 第一步：找到数组中的最大值和最小值
        int max = arr[0];
        int min = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > max) max = arr[i];
            if (arr[i] < min) min = arr[i];
        }

        // 计数范围是 [min, max]，长度为 max - min + 1
        int range = max - min + 1;
        int[] count = new int[range];

        // 第二步：统计每个元素出现的次数
        // 将元素值 arr[i] 映射到下标 arr[i] - min
        for (int i = 0; i < n; i++) {
            count[arr[i] - min]++;
        }

        // 第三步：对计数数组做前缀和
        // 此时 count[i] 表示 ≤ i+min 的元素个数
        // 也就是元素 i+min 在有序数组中的"最后一个位置 + 1"
        for (int i = 1; i < range; i++) {
            count[i] += count[i - 1];
        }

        // 第四步：反向遍历原数组，构建有序结果
        // 反向遍历保证稳定性（相同元素，后面的仍然在后面）
        int[] output = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            int idx = arr[i] - min;      // 元素在计数数组中的下标
            int pos = count[idx] - 1;    // 元素在输出数组中的位置
            output[pos] = arr[i];
            count[idx]--;                // 该元素计数减 1
        }

        // 第五步：将排序结果拷贝回原数组
        System.arraycopy(output, 0, arr, 0, n);
    }

    /**
     * 测试入口
     */
    public static void main(String[] args) {
        System.out.println("========== 计数排序 (Counting Sort) ==========\n");

        // 测试用例 1：普通乱序数组（小范围）
        int[] arr1 = {4, 2, 2, 8, 3, 3, 1};
        System.out.println("测试 1 - 普通乱序数组（小范围）：");
        System.out.println("  排序前：" + Arrays.toString(arr1));
        countingSort(arr1);
        System.out.println("  排序后：" + Arrays.toString(arr1));
        System.out.println("  验证：" + (isSorted(arr1) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 2：成绩排序场景（0-100 分）
        int[] arr2 = {85, 92, 78, 95, 88, 72, 90, 85, 100, 65};
        System.out.println("\n测试 2 - 成绩排序（0-100 分，典型计数排序场景）：");
        System.out.println("  排序前：" + Arrays.toString(arr2));
        countingSort(arr2);
        System.out.println("  排序后：" + Arrays.toString(arr2));
        System.out.println("  验证：" + (isSorted(arr2) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 3：包含负数
        int[] arr3 = {-3, 5, -1, 2, 0, -4, 3, -2, 1, 4};
        System.out.println("\n测试 3 - 包含负数：");
        System.out.println("  排序前：" + Arrays.toString(arr3));
        countingSort(arr3);
        System.out.println("  排序后：" + Arrays.toString(arr3));
        System.out.println("  验证：" + (isSorted(arr3) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 4：大量重复元素
        int[] arr4 = {1, 0, 1, 0, 1, 0, 1, 0, 0, 1};
        System.out.println("\n测试 4 - 大量重复元素（0/1 数组）：");
        System.out.println("  排序前：" + Arrays.toString(arr4));
        countingSort(arr4);
        System.out.println("  排序后：" + Arrays.toString(arr4));
        System.out.println("  验证：" + (isSorted(arr4) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 5：单元素数组
        int[] arr5 = {7};
        System.out.println("\n测试 5 - 单元素数组：");
        System.out.println("  排序前：" + Arrays.toString(arr5));
        countingSort(arr5);
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
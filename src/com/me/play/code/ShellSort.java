package com.me.play.code;

import java.util.Arrays;

/**
 * <h1>希尔排序 (Shell Sort)</h1>
 *
 * <p><b>核心思想：</b>希尔排序是插入排序的改进版，也称为"缩小增量排序"。
 * 插入排序在对"基本有序"的数据排序时效率很高，希尔排序通过先对间隔较大的子序列进行排序，
 * 让数组逐步变得"基本有序"，最后再用间隔为 1 的普通插入排序完成。</p>
 *
 * <h2>算法步骤：</h2>
 * <ol>
 *   <li>选择一个增量序列（gap），通常从 n/2 开始，每次减半直到 1</li>
 *   <li>对每个 gap，将数组分成 gap 个子序列，对每个子序列做插入排序</li>
 *   <li>缩小 gap（gap = gap / 2），重复步骤 2</li>
 *   <li>当 gap = 1 时，就是普通的插入排序，但此时数组已经基本有序了</li>
 * </ol>
 *
 * <h2>复杂度分析：</h2>
 * <ul>
 *   <li><b>时间复杂度：</b>取决于增量序列。使用 Hibbard 增量 O(n^(3/2))，使用 n/2 递减约为 O(n²)</li>
 *   <li><b>空间复杂度：</b>O(1)，原地排序</li>
 *   <li><b>稳定性：</b>不稳定（因为分组排序时相同元素可能被分到不同组）</li>
 * </ul>
 *
 * <h2>为什么比插入排序快？</h2>
 * <p>当 gap 很大时，元素可以一次移动很远，而不是一步步地移动。这减少了数据移动的总次数，
 * 让数组快速接近有序状态，从而让最后的 gap=1 插入排序非常高效。</p>
 *
 * @author AndyHe
 */
public class ShellSort {

    /**
     * 希尔排序（使用 n/2 递减的增量序列）
     *
     * @param arr 待排序的整数数组
     */
    public static void shellSort(int[] arr) {
        int n = arr.length;

        // 外层循环：控制增量 gap，从 n/2 开始，每次减半
        for (int gap = n / 2; gap > 0; gap /= 2) {

            // 中层循环：从 gap 开始，对每个元素在其所在的子序列中做插入排序
            // 这实际上是对所有 gap 个子序列交替进行插入排序
            for (int i = gap; i < n; i++) {
                int key = arr[i]; // 当前要插入的元素
                int j = i;

                // 内层循环：在间隔为 gap 的子序列中，从后往前找插入位置
                // 和普通插入排序完全一样，只是把"间隔 1"换成了"间隔 gap"
                while (j >= gap && arr[j - gap] > key) {
                    arr[j] = arr[j - gap]; // 元素后移 gap 位
                    j -= gap;
                }

                arr[j] = key; // 插入到正确位置
            }
        }
    }

    /**
     * 测试入口
     */
    public static void main(String[] args) {
        System.out.println("========== 希尔排序 (Shell Sort) ==========\n");

        // 测试用例 1：普通乱序数组
        int[] arr1 = {49, 38, 65, 97, 76, 13, 27, 49, 55, 4};
        System.out.println("测试 1 - 普通乱序数组：");
        System.out.println("  排序前：" + Arrays.toString(arr1));
        shellSort(arr1);
        System.out.println("  排序后：" + Arrays.toString(arr1));
        System.out.println("  验证：" + (isSorted(arr1) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 2：中等规模数组
        int[] arr2 = {23, 12, 1, 8, 34, 54, 2, 77, 90, 3, 7, 45, 21, 66, 19};
        System.out.println("\n测试 2 - 中等规模数组：");
        System.out.println("  排序前：" + Arrays.toString(arr2));
        shellSort(arr2);
        System.out.println("  排序后：" + Arrays.toString(arr2));
        System.out.println("  验证：" + (isSorted(arr2) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 3：完全逆序数组
        int[] arr3 = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        System.out.println("\n测试 3 - 完全逆序数组：");
        System.out.println("  排序前：" + Arrays.toString(arr3));
        shellSort(arr3);
        System.out.println("  排序后：" + Arrays.toString(arr3));
        System.out.println("  验证：" + (isSorted(arr3) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 4：包含大量重复元素
        int[] arr4 = {5, 1, 5, 2, 5, 3, 5, 4, 5, 5};
        System.out.println("\n测试 4 - 包含大量重复元素：");
        System.out.println("  排序前：" + Arrays.toString(arr4));
        shellSort(arr4);
        System.out.println("  排序后：" + Arrays.toString(arr4));
        System.out.println("  验证：" + (isSorted(arr4) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 5：已有序数组
        int[] arr5 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println("\n测试 5 - 已有序数组：");
        System.out.println("  排序前：" + Arrays.toString(arr5));
        shellSort(arr5);
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
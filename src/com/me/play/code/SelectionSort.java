package com.me.play.code;

import java.util.Arrays;

/**
 * <h1>选择排序 (Selection Sort)</h1>
 *
 * <p><b>核心思想：</b>将数组分为"已排序区"和"未排序区"。每一轮从未排序区中选出最小（或最大）的元素，
 * 放到已排序区的末尾（即与未排序区的第一个元素交换位置）。</p>
 *
 * <h2>算法步骤：</h2>
 * <ol>
 *   <li>从 i=0 开始，在第 i 到 n-1 的元素中找到最小值的下标 minIdx</li>
 *   <li>将 arr[minIdx] 与 arr[i] 交换</li>
 *   <li>i 右移一位，重复上述过程，直到 i=n-1</li>
 * </ol>
 *
 * <h2>复杂度分析：</h2>
 * <ul>
 *   <li><b>时间复杂度：</b>最坏 O(n²)，平均 O(n²)，最好 O(n²)（无论数据如何，每轮都要扫描剩余全部元素）</li>
 *   <li><b>空间复杂度：</b>O(1)，原地排序</li>
 *   <li><b>稳定性：</b>不稳定（交换可能破坏相等元素的相对顺序）</li>
 * </ul>
 *
 * <h2>特点：</h2>
 * <p>选择排序的交换次数最少（每轮最多一次），当数据移动成本很高时比较有优势。
 * 但比较次数始终为 n(n-1)/2，数据量大的时候效率低。</p>
 *
 * @author AndyHe
 */
public class SelectionSort {

    /**
     * 选择排序：每次从未排序部分选出最小值，放到已排序部分末尾
     *
     * @param arr 待排序的整数数组
     */
    public static void selectionSort(int[] arr) {
        int n = arr.length;

        // 外层循环：i 是"已排序区"和"未排序区"的分界线
        // i 位置即将放第 i 小的元素
        for (int i = 0; i < n - 1; i++) {
            // 假设当前位置 i 就是最小值所在位置
            int minIdx = i;

            // 内层循环：在未排序区 [i+1, n-1] 中寻找真正的最小值
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j; // 更新最小值下标
                }
            }

            // 如果最小值不在当前位置，则交换
            if (minIdx != i) {
                int temp = arr[i];
                arr[i] = arr[minIdx];
                arr[minIdx] = temp;
            }
        }
    }

    /**
     * 测试入口
     */
    public static void main(String[] args) {
        System.out.println("========== 选择排序 (Selection Sort) ==========\n");

        // 测试用例 1：普通乱序数组
        int[] arr1 = {64, 25, 12, 22, 11};
        System.out.println("测试 1 - 普通乱序数组：");
        System.out.println("  排序前：" + Arrays.toString(arr1));
        selectionSort(arr1);
        System.out.println("  排序后：" + Arrays.toString(arr1));
        System.out.println("  验证：" + (isSorted(arr1) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 2：已经有序的数组
        int[] arr2 = {1, 2, 3, 4, 5, 6, 7};
        System.out.println("\n测试 2 - 已有序数组：");
        System.out.println("  排序前：" + Arrays.toString(arr2));
        selectionSort(arr2);
        System.out.println("  排序后：" + Arrays.toString(arr2));
        System.out.println("  验证：" + (isSorted(arr2) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 3：完全逆序数组
        int[] arr3 = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        System.out.println("\n测试 3 - 完全逆序数组：");
        System.out.println("  排序前：" + Arrays.toString(arr3));
        selectionSort(arr3);
        System.out.println("  排序后：" + Arrays.toString(arr3));
        System.out.println("  验证：" + (isSorted(arr3) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 4：包含重复元素（测试稳定性）
        int[] arr4 = {5, 2, 8, 2, 9, 1, 5, 5};
        System.out.println("\n测试 4 - 包含重复元素：");
        System.out.println("  排序前：" + Arrays.toString(arr4));
        selectionSort(arr4);
        System.out.println("  排序后：" + Arrays.toString(arr4));
        System.out.println("  验证：" + (isSorted(arr4) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 5：空数组
        int[] arr5 = {};
        System.out.println("\n测试 5 - 空数组：");
        System.out.println("  排序前：" + Arrays.toString(arr5));
        selectionSort(arr5);
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
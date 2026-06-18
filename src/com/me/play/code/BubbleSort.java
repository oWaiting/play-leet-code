package com.me.play.code;

import java.util.Arrays;

/**
 * <h1>冒泡排序 (Bubble Sort)</h1>
 *
 * <p><b>核心思想：</b>重复遍历数组，依次比较相邻的两个元素，如果顺序错误（前 > 后）就交换它们。
 * 每一轮遍历会把当前未排序部分的最大值"冒泡"到末尾，就像气泡从水底升到水面一样。</p>
 *
 * <h2>算法步骤：</h2>
 * <ol>
 *   <li>从数组开头开始，依次比较相邻元素 arr[j] 和 arr[j+1]</li>
 *   <li>如果 arr[j] > arr[j+1]，交换两者</li>
 *   <li>每完成一轮，最大的元素就会被放到正确的位置（数组末尾）</li>
 *   <li>重复 n-1 轮，直到数组完全有序</li>
 * </ol>
 *
 * <h2>复杂度分析：</h2>
 * <ul>
 *   <li><b>时间复杂度：</b>最坏 O(n²)，平均 O(n²)，最好 O(n)（已有序时，有优化标记）</li>
 *   <li><b>空间复杂度：</b>O(1)，原地排序</li>
 *   <li><b>稳定性：</b>稳定（相等元素不会交换）</li>
 * </ul>
 *
 * <h2>优化：</h2>
 * <p>添加一个 swapped 标记，如果某一轮没有发生任何交换，说明数组已经有序，可以提前结束。</p>
 *
 * @author AndyHe
 */
public class BubbleSort {

    /**
     * 冒泡排序（优化版）
     *
     * @param arr 待排序的整数数组
     */
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        // 外层循环：控制比较的轮数，最多 n-1 轮
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false; // 优化标记：本轮是否发生了交换

            // 内层循环：进行相邻元素比较，每轮比较范围缩小（因为末尾已排好）
            // 第 i 轮时，末尾 i 个元素已经就位，所以只需比较到 n-1-i
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    // 交换相邻元素，大的往后移
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            // 如果本轮没有交换，说明数组已经有序，提前结束
            if (!swapped) {
                break;
            }
        }
    }

    /**
     * 测试入口
     */
    public static void main(String[] args) {
        System.out.println("========== 冒泡排序 (Bubble Sort) ==========\n");

        // 测试用例 1：普通乱序数组
        int[] arr1 = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("测试 1 - 普通乱序数组：");
        System.out.println("  排序前：" + Arrays.toString(arr1));
        bubbleSort(arr1);
        System.out.println("  排序后：" + Arrays.toString(arr1));
        System.out.println("  验证：" + (isSorted(arr1) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 2：已经有序的数组（测试优化）
        int[] arr2 = {1, 2, 3, 4, 5, 6, 7};
        System.out.println("\n测试 2 - 已有序数组（测试提前终止优化）：");
        System.out.println("  排序前：" + Arrays.toString(arr2));
        bubbleSort(arr2);
        System.out.println("  排序后：" + Arrays.toString(arr2));
        System.out.println("  验证：" + (isSorted(arr2) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 3：完全逆序数组
        int[] arr3 = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        System.out.println("\n测试 3 - 完全逆序数组：");
        System.out.println("  排序前：" + Arrays.toString(arr3));
        bubbleSort(arr3);
        System.out.println("  排序后：" + Arrays.toString(arr3));
        System.out.println("  验证：" + (isSorted(arr3) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 4：包含重复元素
        int[] arr4 = {5, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
        System.out.println("\n测试 4 - 包含重复元素：");
        System.out.println("  排序前：" + Arrays.toString(arr4));
        bubbleSort(arr4);
        System.out.println("  排序后：" + Arrays.toString(arr4));
        System.out.println("  验证：" + (isSorted(arr4) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 5：单元素数组
        int[] arr5 = {42};
        System.out.println("\n测试 5 - 单元素数组：");
        System.out.println("  排序前：" + Arrays.toString(arr5));
        bubbleSort(arr5);
        System.out.println("  排序后：" + Arrays.toString(arr5));
        System.out.println("  验证：" + (isSorted(arr5) ? "✓ 通过" : "✗ 失败"));
    }

    /**
     * 辅助方法：验证数组是否非递减有序
     */
    private static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
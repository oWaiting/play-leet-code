package com.me.play.code;

import java.util.Arrays;

/**
 * <h1>快速排序 (Quick Sort)</h1>
 *
 * <p><b>核心思想：</b>也是分治策略。每次选择一个"基准元素"（pivot），
 * 将数组分为两部分：左边所有元素 ≤ pivot，右边所有元素 ≥ pivot，
 * 然后递归地对左右两部分进行同样的操作。</p>
 *
 * <h2>算法步骤：</h2>
 * <ol>
 *   <li>选择一个基准元素 pivot（这里选最右边的元素）</li>
 *   <li>分区（Partition）：重排数组，使得 pivot 左边都 ≤ pivot，右边都 > pivot</li>
 *   <li>递归地对左子数组和右子数组进行快速排序</li>
 * </ol>
 *
 * <h2>复杂度分析：</h2>
 * <ul>
 *   <li><b>时间复杂度：</b>最坏 O(n²)（每次 pivot 都是极值），平均 O(n log n)，最好 O(n log n)</li>
 *   <li><b>空间复杂度：</b>O(log n)（递归调用栈深度），最坏 O(n)</li>
 *   <li><b>稳定性：</b>不稳定</li>
 * </ul>
 *
 * <h2>为什么快？</h2>
 * <p>虽然最坏情况是 O(n²)，但实际中很难遇到（可以通过随机选 pivot 避免）。
 * 内层循环非常简单，常数因子小，CPU 缓存友好，所以实际运行速度通常是所有 O(n log n) 算法中最快的。</p>
 *
 * @author AndyHe
 */
public class QuickSort {

    /**
     * 快速排序（递归实现）
     *
     * @param arr 待排序的整数数组
     */
    public static void quickSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * 递归地对 [left, right] 区间进行快速排序
     *
     * @param arr   原数组
     * @param left  左边界
     * @param right 右边界
     */
    private static void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return; // 区间内只有 0 或 1 个元素，已经有序
        }

        // 分区：找到 pivot 的最终位置
        int pivotIndex = partition(arr, left, right);

        // 递归排序 pivot 左边的部分
        quickSort(arr, left, pivotIndex - 1);

        // 递归排序 pivot 右边的部分
        quickSort(arr, pivotIndex + 1, right);
    }

    /**
     * 分区操作：以 arr[right] 为 pivot，将数组分为两部分
     *
     * <p>使用 Lomuto 分区方案：</p>
     * <ul>
     *   <li>i 指向"小于等于 pivot 区域"的最后一个元素</li>
     *   <li>j 遍历数组，遇到小于等于 pivot 的元素就放到 i+1 的位置</li>
     * </ul>
     *
     * @param arr   原数组
     * @param left  左边界
     * @param right 右边界（pivot 所在位置）
     * @return pivot 最终的下标位置
     */
    private static int partition(int[] arr, int left, int right) {
        int pivot = arr[right]; // 选择最右边的元素作为基准
        int i = left - 1;       // i 指向"小于等于 pivot 的区间"的末尾

        // j 从 left 扫描到 right-1（pivot 本身不参与比较）
        for (int j = left; j < right; j++) {
            // 如果当前元素 <= pivot，就把它放到"小于等于 pivot 区域"
            if (arr[j] <= pivot) {
                i++;
                // 交换 arr[i] 和 arr[j]
                swap(arr, i, j);
            }
        }

        // 最后把 pivot 放到正确的位置：i+1
        // 此时 arr[left..i] 都 ≤ pivot，arr[i+2..right] 都 > pivot
        swap(arr, i + 1, right);
        return i + 1; // 返回 pivot 的最终位置
    }

    /**
     * 交换数组中两个位置的元素
     */
    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /**
     * 测试入口
     */
    public static void main(String[] args) {
        System.out.println("========== 快速排序 (Quick Sort) ==========\n");

        // 测试用例 1：普通乱序数组
        int[] arr1 = {10, 7, 8, 9, 1, 5};
        System.out.println("测试 1 - 普通乱序数组：");
        System.out.println("  排序前：" + Arrays.toString(arr1));
        quickSort(arr1);
        System.out.println("  排序后：" + Arrays.toString(arr1));
        System.out.println("  验证：" + (isSorted(arr1) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 2：中等规模数组
        int[] arr2 = {33, 10, 59, 26, 41, 58, 76, 12, 89, 35, 22, 67, 45, 91, 18};
        System.out.println("\n测试 2 - 中等规模数组：");
        System.out.println("  排序前：" + Arrays.toString(arr2));
        quickSort(arr2);
        System.out.println("  排序后：" + Arrays.toString(arr2));
        System.out.println("  验证：" + (isSorted(arr2) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 3：完全逆序数组（最坏情况之一：pivot 始终是最小值）
        int[] arr3 = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        System.out.println("\n测试 3 - 完全逆序数组：");
        System.out.println("  排序前：" + Arrays.toString(arr3));
        quickSort(arr3);
        System.out.println("  排序后：" + Arrays.toString(arr3));
        System.out.println("  验证：" + (isSorted(arr3) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 4：包含重复元素
        int[] arr4 = {4, 2, 4, 1, 3, 4, 5, 2, 4};
        System.out.println("\n测试 4 - 包含重复元素：");
        System.out.println("  排序前：" + Arrays.toString(arr4));
        quickSort(arr4);
        System.out.println("  排序后：" + Arrays.toString(arr4));
        System.out.println("  验证：" + (isSorted(arr4) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 5：已有序数组（最坏情况之一：pivot 始终是最大值）
        int[] arr5 = {1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println("\n测试 5 - 已有序数组：");
        System.out.println("  排序前：" + Arrays.toString(arr5));
        quickSort(arr5);
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
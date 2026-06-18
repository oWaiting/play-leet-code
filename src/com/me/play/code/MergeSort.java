package com.me.play.code;

import java.util.Arrays;

/**
 * <h1>归并排序 (Merge Sort)</h1>
 *
 * <p><b>核心思想：</b>采用经典的分治（Divide and Conquer）策略。
 * 将一个大数组递归地拆分成两个小数组，分别排序，然后将两个有序的小数组合并成一个有序的大数组。</p>
 *
 * <h2>算法步骤：</h2>
 * <ol>
 *   <li><b>分解（Divide）：</b>将数组从中间分成左右两半</li>
 *   <li><b>解决（Conquer）：</b>递归地对左右两半分别进行归并排序</li>
 *   <li><b>合并（Combine）：</b>将两个有序的子数组合并成一个有序数组</li>
 * </ol>
 *
 * <h2>复杂度分析：</h2>
 * <ul>
 *   <li><b>时间复杂度：</b>最坏 O(n log n)，平均 O(n log n)，最好 O(n log n)（始终如此）</li>
 *   <li><b>空间复杂度：</b>O(n)，需要额外的临时数组来合并</li>
 *   <li><b>稳定性：</b>稳定</li>
 * </ul>
 *
 * <h2>特点：</h2>
 * <p>归并排序是稳定且时间复杂度始终为 O(n log n) 的排序算法，适合处理大规模数据，
 * 特别适合外部排序（数据在磁盘上无法一次性读入内存）。但需要 O(n) 的额外空间。</p>
 *
 * @author AndyHe
 */
public class MergeSort {

    /**
     * 归并排序（递归实现）
     *
     * @param arr 待排序的整数数组
     */
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return; // 空数组或单元素数组无需排序
        }
        mergeSort(arr, 0, arr.length - 1);
    }

    /**
     * 递归地对 [left, right] 区间进行归并排序
     *
     * @param arr   原数组
     * @param left  左边界（包含）
     * @param right 右边界（包含）
     */
    private static void mergeSort(int[] arr, int left, int right) {
        // 递归终止条件：区间只有一个元素时天然有序
        if (left >= right) {
            return;
        }

        // 计算中间位置，避免溢出（left + right 可能超过 int 范围）
        int mid = left + (right - left) / 2;

        // 递归排序左半部分 [left, mid]
        mergeSort(arr, left, mid);

        // 递归排序右半部分 [mid+1, right]
        mergeSort(arr, mid + 1, right);

        // 合并两个有序子数组
        merge(arr, left, mid, right);
    }

    /**
     * 合并两个有序子数组 arr[left..mid] 和 arr[mid+1..right]
     *
     * @param arr   原数组
     * @param left  左子数组起始位置
     * @param mid   左子数组结束位置（也是右子数组的前一个位置）
     * @param right 右子数组结束位置
     */
    private static void merge(int[] arr, int left, int mid, int right) {
        // 计算左右子数组的长度
        int n1 = mid - left + 1; // 左子数组长度
        int n2 = right - mid;    // 右子数组长度

        // 创建临时数组存放左右子数组的数据
        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];

        // 拷贝数据到临时数组
        for (int i = 0; i < n1; i++) {
            leftArr[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArr[j] = arr[mid + 1 + j];
        }

        // 合并：双指针法，比较左右数组当前元素，取较小的放入原数组
        int i = 0, j = 0;  // i 指向左数组，j 指向右数组
        int k = left;      // k 指向原数组的写入位置

        while (i < n1 && j < n2) {
            // 注意：这里用 <= 保证稳定性（相等时优先取左边的）
            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }

        // 将左数组剩余元素拷贝到原数组（如果有的话）
        while (i < n1) {
            arr[k++] = leftArr[i++];
        }

        // 将右数组剩余元素拷贝到原数组（如果有的话）
        while (j < n2) {
            arr[k++] = rightArr[j++];
        }
    }

    /**
     * 测试入口
     */
    public static void main(String[] args) {
        System.out.println("========== 归并排序 (Merge Sort) ==========\n");

        // 测试用例 1：普通乱序数组
        int[] arr1 = {38, 27, 43, 3, 9, 82, 10};
        System.out.println("测试 1 - 普通乱序数组：");
        System.out.println("  排序前：" + Arrays.toString(arr1));
        mergeSort(arr1);
        System.out.println("  排序后：" + Arrays.toString(arr1));
        System.out.println("  验证：" + (isSorted(arr1) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 2：大规模数组（展示 O(n log n) 效率）
        int[] arr2 = {12, 11, 13, 5, 6, 7, 1, 15, 9, 8, 14, 2, 4, 10, 3};
        System.out.println("\n测试 2 - 中等规模数组：");
        System.out.println("  排序前：" + Arrays.toString(arr2));
        mergeSort(arr2);
        System.out.println("  排序后：" + Arrays.toString(arr2));
        System.out.println("  验证：" + (isSorted(arr2) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 3：完全逆序数组
        int[] arr3 = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        System.out.println("\n测试 3 - 完全逆序数组：");
        System.out.println("  排序前：" + Arrays.toString(arr3));
        mergeSort(arr3);
        System.out.println("  排序后：" + Arrays.toString(arr3));
        System.out.println("  验证：" + (isSorted(arr3) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 4：包含重复元素
        int[] arr4 = {5, 2, 8, 2, 9, 1, 5, 5, 3};
        System.out.println("\n测试 4 - 包含重复元素：");
        System.out.println("  排序前：" + Arrays.toString(arr4));
        mergeSort(arr4);
        System.out.println("  排序后：" + Arrays.toString(arr4));
        System.out.println("  验证：" + (isSorted(arr4) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 5：已有序数组
        int[] arr5 = {1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println("\n测试 5 - 已有序数组：");
        System.out.println("  排序前：" + Arrays.toString(arr5));
        mergeSort(arr5);
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
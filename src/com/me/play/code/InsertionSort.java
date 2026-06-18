package com.me.play.code;

import java.util.Arrays;

/**
 * <h1>插入排序 (Insertion Sort)</h1>
 *
 * <p><b>核心思想：</b>类似于打扑克牌时整理手牌。将数组分为"已排序区"和"未排序区"，
 * 每次从未排序区取出第一个元素，在已排序区中从后往前扫描，找到合适的位置插入。</p>
 *
 * <h2>算法步骤：</h2>
 * <ol>
 *   <li>从第 2 个元素（i=1）开始，认为第 1 个元素已经有序</li>
 *   <li>取出当前元素 key = arr[i]</li>
 *   <li>在已排序区 [0, i-1] 中从后往前找：如果 arr[j] > key，就把 arr[j] 后移一位</li>
 *   <li>找到合适位置后，把 key 放进去</li>
 *   <li>重复直到所有元素都插入完成</li>
 * </ol>
 *
 * <h2>复杂度分析：</h2>
 * <ul>
 *   <li><b>时间复杂度：</b>最坏 O(n²)，平均 O(n²)，最好 O(n)（已有序时每轮只比较一次）</li>
 *   <li><b>空间复杂度：</b>O(1)，原地排序</li>
 *   <li><b>稳定性：</b>稳定</li>
 * </ul>
 *
 * <h2>适用场景：</h2>
 * <p>插入排序在数据量小（n ≤ 50）或数据基本有序时表现优异，常被用作高级排序算法（如快排、归并）
 * 在小规模子问题上的优化手段。JDK 的 TimSort 就使用了插入排序来优化小数组。</p>
 *
 * @author AndyHe
 */
public class InsertionSort {

    /**
     * 插入排序
     *
     * @param arr 待排序的整数数组
     */
    public static void insertionSort(int[] arr) {
        int n = arr.length;

        // 外层循环：从未排序区取出第一个元素
        // i=0 时只有一个元素，天然有序，所以从 i=1 开始
        for (int i = 1; i < n; i++) {
            int key = arr[i]; // 当前要插入的元素（暂存起来，因为后续移动会覆盖）
            int j = i - 1;

            // 内层循环：在已排序区 [0, i-1] 中从后往前找插入位置
            // 如果 arr[j] > key，就把 arr[j] 往后挪一位，给 key 腾位置
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j]; // 元素后移
                j--;
            }

            // 此时 j+1 就是 key 应该插入的位置
            // （循环结束时 j 指向第一个 <= key 的元素，或者 j=-1）
            arr[j + 1] = key;
        }
    }

    /**
     * 测试入口
     */
    public static void main(String[] args) {
        System.out.println("========== 插入排序 (Insertion Sort) ==========\n");

        // 测试用例 1：普通乱序数组
        int[] arr1 = {12, 11, 13, 5, 6};
        System.out.println("测试 1 - 普通乱序数组：");
        System.out.println("  排序前：" + Arrays.toString(arr1));
        insertionSort(arr1);
        System.out.println("  排序后：" + Arrays.toString(arr1));
        System.out.println("  验证：" + (isSorted(arr1) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 2：已经有序的数组（最好情况）
        int[] arr2 = {1, 2, 3, 4, 5, 6, 7};
        System.out.println("\n测试 2 - 已有序数组（最好情况 O(n)）：");
        System.out.println("  排序前：" + Arrays.toString(arr2));
        insertionSort(arr2);
        System.out.println("  排序后：" + Arrays.toString(arr2));
        System.out.println("  验证：" + (isSorted(arr2) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 3：完全逆序数组（最坏情况）
        int[] arr3 = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        System.out.println("\n测试 3 - 完全逆序数组（最坏情况 O(n²)）：");
        System.out.println("  排序前：" + Arrays.toString(arr3));
        insertionSort(arr3);
        System.out.println("  排序后：" + Arrays.toString(arr3));
        System.out.println("  验证：" + (isSorted(arr3) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 4：基本有序的数组（展示插入排序的优势）
        int[] arr4 = {1, 2, 4, 5, 3, 6, 7, 9, 8};
        System.out.println("\n测试 4 - 基本有序数组（展示插入排序优势）：");
        System.out.println("  排序前：" + Arrays.toString(arr4));
        insertionSort(arr4);
        System.out.println("  排序后：" + Arrays.toString(arr4));
        System.out.println("  验证：" + (isSorted(arr4) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 5：包含重复元素
        int[] arr5 = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
        System.out.println("\n测试 5 - 包含重复元素：");
        System.out.println("  排序前：" + Arrays.toString(arr5));
        insertionSort(arr5);
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
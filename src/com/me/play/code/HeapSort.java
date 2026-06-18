package com.me.play.code;

import java.util.Arrays;

/**
 * <h1>堆排序 (Heap Sort)</h1>
 *
 * <p><b>核心思想：</b>利用"堆"（Heap）这种数据结构来进行排序。堆是一种特殊的完全二叉树：
 * 大顶堆（Max Heap）中每个节点的值都大于等于其子节点，堆顶是最大值。
 * 堆排序通过反复建堆→取堆顶→调整堆的过程完成排序。</p>
 *
 * <h2>算法步骤：</h2>
 * <ol>
 *   <li><b>建堆（Build Heap）：</b>将数组原地构建成一个大顶堆</li>
 *   <li><b>排序：</b>依次将堆顶（最大值）与堆末尾元素交换，然后缩小堆的范围，重新调整堆</li>
 *   <li>重复步骤 2，直到堆的大小为 1</li>
 * </ol>
 *
 * <h2>复杂度分析：</h2>
 * <ul>
 *   <li><b>时间复杂度：</b>最坏 O(n log n)，平均 O(n log n)，最好 O(n log n)（始终如此）</li>
 *   <li><b>空间复杂度：</b>O(1)，原地排序</li>
 *   <li><b>稳定性：</b>不稳定</li>
 * </ul>
 *
 * <h2>特点：</h2>
 * <p>堆排序是原地排序，不需要额外空间，时间复杂度稳定为 O(n log n)。
 * 但实际中通常比快排慢，因为堆的跳跃式访问对 CPU 缓存不友好。</p>
 *
 * @author AndyHe
 */
public class HeapSort {

    /**
     * 堆排序
     *
     * @param arr 待排序的整数数组
     */
    public static void heapSort(int[] arr) {
        int n = arr.length;
        if (n <= 1) {
            return;
        }

        // 第一步：建堆 —— 从最后一个非叶子节点开始，向前逐个调整
        // 最后一个非叶子节点的下标是 n/2 - 1
        // 因为：最后一个叶子节点是 n-1，它的父节点是 (n-1-1)/2 = n/2 - 1
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // 第二步：排序 —— 逐个取出堆顶元素（最大值）
        // 堆顶是 arr[0]，将它与堆的最后一个元素交换，然后缩小堆的范围
        for (int i = n - 1; i > 0; i--) {
            // 将堆顶（当前最大值）移到数组末尾
            swap(arr, 0, i);

            // 对剩下的堆 [0, i-1] 重新调整，使其恢复大顶堆性质
            heapify(arr, i, 0);
        }
    }

    /**
     * 堆化：将以 root 为根的子树调整为大顶堆
     *
     * <p><b>大顶堆的性质：</b>每个节点的值都 ≥ 其左右子节点的值。</p>
     *
     * <p><b>前提：</b>root 的左右子树已经是大顶堆（这是自底向上建堆的关键前提）。</p>
     *
     * @param arr    原数组
     * @param n      堆的有效大小（参与堆化的元素个数）
     * @param root   当前需要调整的子树的根节点下标
     */
    private static void heapify(int[] arr, int n, int root) {
        int largest = root;       // 假设根节点是最大的
        int left = 2 * root + 1;  // 左子节点下标
        int right = 2 * root + 2; // 右子节点下标

        // 如果左子节点存在且大于当前最大值
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // 如果右子节点存在且大于当前最大值
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // 如果最大值不是根节点，需要交换，并递归调整受影响的子树
        if (largest != root) {
            swap(arr, root, largest);

            // 递归调整被交换的子树（largest 位置现在是原来 root 的小值，
            // 可能破坏了该子树的大顶堆性质）
            heapify(arr, n, largest);
        }
    }

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /**
     * 测试入口
     */
    public static void main(String[] args) {
        System.out.println("========== 堆排序 (Heap Sort) ==========\n");

        // 测试用例 1：普通乱序数组
        int[] arr1 = {12, 11, 13, 5, 6, 7};
        System.out.println("测试 1 - 普通乱序数组：");
        System.out.println("  排序前：" + Arrays.toString(arr1));
        heapSort(arr1);
        System.out.println("  排序后：" + Arrays.toString(arr1));
        System.out.println("  验证：" + (isSorted(arr1) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 2：中等规模数组
        int[] arr2 = {4, 10, 3, 5, 1, 8, 7, 9, 6, 2};
        System.out.println("\n测试 2 - 中等规模数组：");
        System.out.println("  排序前：" + Arrays.toString(arr2));
        heapSort(arr2);
        System.out.println("  排序后：" + Arrays.toString(arr2));
        System.out.println("  验证：" + (isSorted(arr2) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 3：完全逆序数组
        int[] arr3 = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        System.out.println("\n测试 3 - 完全逆序数组：");
        System.out.println("  排序前：" + Arrays.toString(arr3));
        heapSort(arr3);
        System.out.println("  排序后：" + Arrays.toString(arr3));
        System.out.println("  验证：" + (isSorted(arr3) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 4：包含重复元素
        int[] arr4 = {5, 3, 8, 3, 1, 5, 7, 5, 2};
        System.out.println("\n测试 4 - 包含重复元素：");
        System.out.println("  排序前：" + Arrays.toString(arr4));
        heapSort(arr4);
        System.out.println("  排序后：" + Arrays.toString(arr4));
        System.out.println("  验证：" + (isSorted(arr4) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 5：已有序数组
        int[] arr5 = {1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println("\n测试 5 - 已有序数组：");
        System.out.println("  排序前：" + Arrays.toString(arr5));
        heapSort(arr5);
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
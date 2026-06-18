package com.me.play.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <h1>桶排序 (Bucket Sort)</h1>
 *
 * <p><b>核心思想：</b>将数据分散到多个有序的"桶"中，每个桶内部使用其他排序算法（如插入排序）
 * 或递归使用桶排序，最后按顺序把各个桶的数据合并起来。</p>
 *
 * <h2>算法步骤：</h2>
 * <ol>
 *   <li>确定桶的数量，创建桶列表</li>
 *   <li>遍历原数组，根据映射函数将每个元素放入对应的桶中</li>
 *   <li>对每个非空桶内部进行排序（这里用 Collections.sort）</li>
 *   <li>按桶的顺序，依次取出所有元素，放回原数组</li>
 * </ol>
 *
 * <h2>复杂度分析：</h2>
 * <ul>
 *   <li><b>时间复杂度：</b>平均 O(n + k)，最坏 O(n²)（所有元素落入同一个桶）</li>
 *   <li><b>空间复杂度：</b>O(n + k)，k 为桶的数量</li>
 *   <li><b>稳定性：</b>取决于桶内排序算法是否稳定</li>
 * </ul>
 *
 * <h2>适用场景：</h2>
 * <p>数据均匀分布时效率极高。常用于浮点数排序，或数据范围已知且分布均匀的场景。
 * 著名的 MapReduce 框架就借鉴了桶排序的思想。</p>
 *
 * @author AndyHe
 */
public class BucketSort {

    /**
     * 桶排序
     *
     * @param arr 待排序的浮点数数组（假设数据在 [0, 1) 范围内均匀分布）
     */
    public static void bucketSort(double[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int n = arr.length;

        // 第一步：创建 n 个桶
        // 每个桶是一个 ArrayList<Double>
        List<Double>[] buckets = new List[n];
        for (int i = 0; i < n; i++) {
            buckets[i] = new ArrayList<>();
        }

        // 第二步：将每个元素放入对应的桶中
        // 映射函数：bucketIndex = (int)(n * arr[i])
        // 因为 arr[i] ∈ [0, 1)，所以 n*arr[i] ∈ [0, n)
        for (int i = 0; i < n; i++) {
            int bucketIdx = (int) (n * arr[i]);
            // 防止浮点数精度问题导致越界
            if (bucketIdx >= n) {
                bucketIdx = n - 1;
            }
            buckets[bucketIdx].add(arr[i]);
        }

        // 第三步：对每个桶内部排序
        for (int i = 0; i < n; i++) {
            Collections.sort(buckets[i]);
        }

        // 第四步：按顺序合并所有桶的数据
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (double value : buckets[i]) {
                arr[index++] = value;
            }
        }
    }

    /**
     * 桶排序的整数版本（适用于较大范围的数据）
     * 通过自定义映射函数将整数映射到桶中
     *
     * @param arr 待排序的整数数组
     */
    public static void bucketSortInt(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int n = arr.length;

        // 找到最大值和最小值
        int max = arr[0], min = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > max) max = arr[i];
            if (arr[i] < min) min = arr[i];
        }

        // 桶的数量
        int bucketCount = Math.max(n / 2, 1);
        List<Integer>[] buckets = new List[bucketCount];
        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = new ArrayList<>();
        }

        // 映射函数：将 [min, max] 映射到 [0, bucketCount-1]
        double range = (double) (max - min) + 1; // 加 1 避免除零

        for (int i = 0; i < n; i++) {
            int bucketIdx = (int) ((arr[i] - min) / range * bucketCount);
            if (bucketIdx >= bucketCount) {
                bucketIdx = bucketCount - 1;
            }
            buckets[bucketIdx].add(arr[i]);
        }

        // 桶内排序
        for (int i = 0; i < bucketCount; i++) {
            Collections.sort(buckets[i]);
        }

        // 合并
        int index = 0;
        for (int i = 0; i < bucketCount; i++) {
            for (int value : buckets[i]) {
                arr[index++] = value;
            }
        }
    }

    /**
     * 测试入口
     */
    public static void main(String[] args) {
        System.out.println("========== 桶排序 (Bucket Sort) ==========\n");

        // 测试用例 1：浮点数 [0, 1) 范围
        double[] arr1 = {0.78, 0.17, 0.39, 0.26, 0.72, 0.94, 0.21, 0.12, 0.23, 0.68};
        System.out.println("测试 1 - 浮点数 [0, 1) 范围：");
        System.out.println("  排序前：" + Arrays.toString(arr1));
        bucketSort(arr1);
        System.out.println("  排序后：" + Arrays.toString(arr1));
        System.out.println("  验证：" + (isSorted(arr1) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 2：更多浮点数
        double[] arr2 = {0.53, 0.89, 0.15, 0.33, 0.42, 0.97, 0.01, 0.66, 0.28, 0.74};
        System.out.println("\n测试 2 - 更多浮点数：");
        System.out.println("  排序前：" + Arrays.toString(arr2));
        bucketSort(arr2);
        System.out.println("  排序后：" + Arrays.toString(arr2));
        System.out.println("  验证：" + (isSorted(arr2) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 3：整数桶排序
        int[] arr3 = {42, 15, 78, 33, 91, 27, 56, 8, 64, 50};
        System.out.println("\n测试 3 - 整数桶排序（使用 bucketSortInt）：");
        System.out.println("  排序前：" + Arrays.toString(arr3));
        bucketSortInt(arr3);
        System.out.println("  排序后：" + Arrays.toString(arr3));
        System.out.println("  验证：" + (isSorted(arr3) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 4：包含重复元素
        int[] arr4 = {5, 2, 5, 1, 8, 5, 3, 2, 7, 5};
        System.out.println("\n测试 4 - 整数包含重复元素：");
        System.out.println("  排序前：" + Arrays.toString(arr4));
        bucketSortInt(arr4);
        System.out.println("  排序后：" + Arrays.toString(arr4));
        System.out.println("  验证：" + (isSorted(arr4) ? "✓ 通过" : "✗ 失败"));

        // 测试用例 5：已有序数组
        int[] arr5 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println("\n测试 5 - 已有序整数数组：");
        System.out.println("  排序前：" + Arrays.toString(arr5));
        bucketSortInt(arr5);
        System.out.println("  排序后：" + Arrays.toString(arr5));
        System.out.println("  验证：" + (isSorted(arr5) ? "✓ 通过" : "✗ 失败"));
    }

    private static boolean isSorted(double[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
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
package com.me.play.code;

/**
 * <a href="https://leetcode.cn/problems/implement-trie-prefix-tree/?envType=study-plan-v2&envId=leetcode-75">208. 实现 Trie (前缀树)</a>
 *
 * <h2>一句话理解 Trie</h2>
 * Trie 就是一棵"按字母拆分的目录树"，专门用来快速回答两个问题：
 *   1. 这个单词存过吗？（search）
 *   2. 有没有以某个前缀开头的单词？（startsWith）
 *
 * <h2>生活类比：手机输入法联想</h2>
 * 你输入 "ap" -> 手机立刻联想出 "apple"、"app"、"apricot"...
 * 手机是怎么做到的？它把所有存过的单词，按字母一层一层拆成树：
 * <pre>
 *   第 1 层 = 单词第 1 个字母
 *   第 2 层 = 单词第 2 个字母
 *   ...
 * </pre>
 * 相同前缀的单词共享同一条路径，所以查找极快。
 *
 * <h2>图解：依次插入 "apple"、"app"、"apricot"</h2>
 * <pre>
 *   插入 "apple":  root -> a -> p -> p -> l -> e[END]
 *
 *   插入 "app":    root -> a -> p -> p[END] -> l -> e[END]
 *                                   ^ 打[END]表示 "app" 本身也是一个完整单词
 *
 *   插入 "apricot":             p[END] -> l -> e[END]        <- apple/app 的路径
 *                             /
 *                  root -> a -> p
 *                             \
 *                               r -> i -> c -> o -> t[END]  <- apricot 的路径
 * </pre>
 *   [END] 标记 = "从根走到这里，路径上的字母拼起来是一个完整存过的单词"
 *
 * <h2>三个操作的本质</h2>
 * <pre>
 *   insert("apple")    -> 沿字母一层层往下走，路不通就新建，最后打[END]
 *   search("app")      -> 沿 a->p->p 走完，看最后节点有没有[END]
 *                        有[END] = true（完整单词存在）
 *   startsWith("ap")   -> 沿 a->p 走完就行，不管有没有[END]
 *                        能走完 = true（有单词以此前缀开头）
 * </pre>
 *
 * <h2>TrieNode 为什么是 children[26]？</h2>
 * 题目保证单词只含小写字母 a-z，共 26 个。
 * 所以每个节点用一个长度 26 的数组，下标 0 对应 'a'，下标 25 对应 'z'。
 * children[i] == null 表示"这个字母的分支还不存在"。
 *
 * <h2>复杂度</h2>
 * 设单词长度为 m：
 * <ul>
 *   <li>时间复杂度：insert / search / startsWith 均为 O(m)，与单词总数无关</li>
 *   <li>空间复杂度：最坏 O(N * m)，N 为插入单词总数</li>
 * </ul>
 */
public class Solution208 {

    static class Trie {

        /**
         * Trie 节点 -- 想象成一个"路口"，每个路口最多有 26 条岔路（a-z）
         *
         * children[26]：
         *   下标 0 = 字母 'a' 的方向
         *   下标 1 = 字母 'b' 的方向
         *   ...
         *   下标 25 = 字母 'z' 的方向
         *   null = 这个方向还没走过，是死路
         *
         * isEnd：
         *   true  = "从根走到这个路口，拼出来的字母是一个完整单词"
         *   false = "只是路过这里，到这里不是一个完整单词"
         *
         * 举例：插入 "app" 后，走到第二个 p 的节点，isEnd = true
         *       插入 "apple" 后，走到 e 的节点，isEnd = true
         *       但第二个 p 的节点 isEnd 仍然是 true（不受影响）
         */
        private static class TrieNode {
            TrieNode[] children = new TrieNode[26];  // 26 条岔路，初始全是 null
            boolean isEnd = false;                    // 默认不是单词结尾
        }

        /**
         * 根节点 = 树的起点，本身不代表任何字母
         * 所有单词的第一个字母都存在 root 的子节点里
         */
        private final TrieNode root;

        public Trie() {
            root = new TrieNode();  // 创建一个空的起点
        }

        /**
         * 插入单词 -- 把单词"种"进树里
         *
         * 过程（以 insert("apple") 为例）：
         *   从 root 出发，逐个字母往下看：
         *   - 字母 'a'：root 的 children[0] 有路吗？没有 -> 新建一个节点
         *   - 字母 'p'：'a' 的 children[15] 有路吗？没有 -> 新建
         *   - 字母 'p'：继续新建...
         *   - 字母 'l'：继续新建...
         *   - 字母 'e'：继续新建...
         *   最后把 'e' 节点的 isEnd 设为 true，表示"apple 这个单词存好了"
         *
         * 如果插入 "app"（已存在 "apple"）：
         *   前 3 个字母 a->p->p 的路径已经有了，直接走下去，不用新建
         *   走到第二个 'p' 时，把它的 isEnd 设为 true
         *   后面的 l->e 不受影响
         */
        public void insert(String word) {
            TrieNode node = root;  // 从起点出发
            for (int i = 0; i < word.length(); i = i + 1) {
                int idx = word.charAt(i) - 'a';  // 把字母转成下标：'a'->0, 'b'->1, ..., 'z'->25
                if (node.children[idx] == null) {
                    // 这个方向还没路，新建一个节点（新开一条岔路）
                    node.children[idx] = new TrieNode();
                }
                // 沿这条路继续往下走
                node = node.children[idx];
            }
            // 走完了所有字母，在当前节点打[END]标记："到这里是一个完整单词"
            node.isEnd = true;
        }

        /**
         * 精确查找 -- 这个单词完整存过吗？
         *
         * 和 insert 类似，沿字母路径往下走，但有两个关键区别：
         *   1. 走到一半发现没路了（children[idx] == null）-> 直接返回 false
         *   2. 走完了所有字母，还要看最后节点的 isEnd 是不是 true
         *      isEnd == true  -> 这个单词完整存在，返回 true
         *      isEnd == false -> 路径在，但没人在这打过[END]，说明只是某个更长单词的前缀
         *
         * 举例（已插入 "apple"、"app"）：
         *   search("app")  -> 走完 a->p->p，isEnd = true  -> 返回 true
         *   search("ap")   -> 走完 a->p，isEnd = false    -> 返回 false
         *   search("banana")-> 走第一步 'b' 就没路      -> 返回 false
         */
        public boolean search(String word) {
            TrieNode node = root;
            for (int i = 0; i < word.length(); i = i + 1) {
                int idx = word.charAt(i) - 'a';
                if (node.children[idx] == null) {
                    return false;  // 路断了，这个单词不可能存在
                }
                node = node.children[idx];
            }
            // 能走完所有字母，还要检查最后是不是[END]标记
            return node.isEnd;
        }

        /**
         * 前缀查找 -- 有没有单词以这个前缀开头？
         *
         * 和 search 几乎一样，唯一区别：走完所有字母后，不需要看 isEnd
         * 因为前缀不要求是完整单词，只要路径存在就说明有单词以此开头
         *
         * 举例（已插入 "apple"、"app"、"apricot"）：
         *   startsWith("app") -> 走完 a->p->p，路径存在 -> 返回 true
         *   startsWith("apr") -> 走完 a->p->r，路径存在 -> 返回 true（apricot 以 "apr" 开头）
         *   startsWith("ban") -> 第一步 'b' 就没路     -> 返回 false
         */
        public boolean startsWith(String prefix) {
            TrieNode node = root;
            for (int i = 0; i < prefix.length(); i = i + 1) {
                int idx = prefix.charAt(i) - 'a';
                if (node.children[idx] == null) {
                    return false;  // 路断了，没有单词以此前缀开头
                }
                node = node.children[idx];
            }
            // 能走完 prefix 所有字母就够了，不需要检查 isEnd
            return true;
        }
    }

    /**
     * 测试入口：手动跑一遍，直观感受三个操作的区别
     */
    public static void main(String[] args) {
        // ---- 第一步：创建一棵空的 Trie ----
        Trie trie = new Trie();

        // ---- 第二步：插入几个单词，观察树是怎么长出来的 ----
        // 插入 "apple"：root -> a -> p -> p -> l -> e[END]
        trie.insert("apple");

        // 插入 "app"：前 3 个字母和 "apple" 共享路径，在第二个 p 打[END]
        // root -> a -> p -> p[END] -> l -> e[END]
        trie.insert("app");

        // 插入 "apricot"：从 a->p 分叉，走 r->i->c->o->t[END]
        trie.insert("apricot");

        // ---- 第三步：测试 search（精确查找，必须有[END]才算找到）----
        System.out.println("=== search ===");
        System.out.println("search(\"apple\")   = " + trie.search("apple"));    // true
        System.out.println("search(\"app\")     = " + trie.search("app"));      // true
        System.out.println("search(\"ap\")      = " + trie.search("ap"));       // false
        System.out.println("search(\"apricot\") = " + trie.search("apricot"));  // true
        System.out.println("search(\"banana\")  = " + trie.search("banana"));   // false

        // ---- 第四步：测试 startsWith（前缀查找，路径存在就行）----
        System.out.println("\n=== startsWith ===");
        System.out.println("startsWith(\"app\") = " + trie.startsWith("app"));  // true
        System.out.println("startsWith(\"ap\")  = " + trie.startsWith("ap"));   // true
        System.out.println("startsWith(\"ban\") = " + trie.startsWith("ban"));  // false
    }
}

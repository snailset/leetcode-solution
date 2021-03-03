import java.util.*;

public class Solution {
    public int threeSumClosest(int[] nums, int target) {
        int sum = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < nums.length - 2; i++) {
            int start = i + 1, end = nums.length - 1;
            while (start < end) {
                int tmp = nums[start] + nums[end] + nums[i];
                if (Math.abs(tmp - target) < Math.abs(sum - target))
                    sum = tmp;

                if (tmp > target) end --;
                else if (tmp < target) start++;
                else return 0;
            }
        }
        return sum;
    }


    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    private ListNode tailInsert(ListNode head, ListNode list, int k) {
        ListNode tmp;
        ListNode last = list;
        while (k-- != 0 && list != null) {
            tmp = list;
            list = list.next;
            tmp.next = list.next;
            list.next = tmp;
        }
        if (k != 0) {

        }
        return last;
    }

    private void printNode(ListNode head) {
        while (head != null) {
            System.out.printf("%d ", head.val);
            head = head.next;
        }
        System.out.println();
    }
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode h = new ListNode(0);
        ListNode last = h;
        ListNode cur = head;
        ListNode tmp;
        while (cur != null) {
            int n = k;
            ListNode tmplast = cur;
            System.out.println(tmplast.val);
            while (n-- != 0 && cur != null) {
                tmp = cur;
                cur = cur.next;
                tmp.next = last.next;
                last.next = tmp;
            }
            if (n != 0) {
                cur = last.next;
                while (cur != null) {
                    tmp = cur;
                    cur = cur.next;
                    tmp.next = last.next;
                    last.next = tmp;
                }
                break;
            }
            last = tmplast;
        }

        return h.next;
    }

    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        if (words.length == 0) return res;

        int len = words[0].length();
        HashMap<String, Integer> all = new HashMap<>();
        HashMap<String, Integer> cur = new HashMap<>();
        for (String str : words) {
            int num = all.getOrDefault(str, 0) + 1;
            all.put(str, num);
        }

        for (int i = 0; i < len; i++) {
            for (int j = i; j <= s.length() - len * words.length; j += len) {
                boolean sa = true;
                System.out.printf("j = %d\n", j);
                cur.clear();
                for (int k = 0; k < words.length; k++) {
                    String key = s.substring(j + k * len, j + k * len + len);
                    int num = all.getOrDefault(key, 0);
                    if (num == 0) {
                        j += k * len;
                        cur.clear();
                        sa = false;
                        break;
                    }
                    int curnum = cur.getOrDefault(key, 0) + 1;
                    if (curnum > num) {
                        cur.clear();
                        sa = false;
                        break;
                    }
                    cur.put(key, curnum);
                }
                if (sa) res.add(j);
            }
        }
        return res;
    }

    public int removeElement(int[] nums, int val) {
        int s = 0, e = nums.length - 1;
        while (s < e) {
            while (s < e && nums[s] != val) s++;
            while (s < e && nums[e] == val) e--;
            if (s < e) {
                int t = nums[s];
                nums[s] = nums[e];
                nums[e] = t;
                s++;
                e--;
            }
        }

        return nums[e] == val ? e : e +1 ;
    }

    private void swapArray(int []a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
    private void reverseArray(int []a, int i, int j) {
        while (i < j) {
            swapArray(a, i, j);
            i++; j--;
        }
    }
    public void nextPermutation(int[] nums) {
        int s, e = nums.length - 1;
        for (s = nums.length - 2; s >= 0 && nums[s] >= nums[s + 1]; s--);
        if (s > 0) {
            while (nums[e] <= nums[s]) e--;
            swapArray(nums, s, e);
        }
        System.out.println();
        reverseArray(nums, s + 1, e);
    }

    public int longestValidParentheses(String s) {
        int res = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.empty()) {
                    stack.push(i);
                } else {
                    res = Math.max(res, i - stack.peek());
                }
            }
        }
        return res;
    }

    public int searchInsert(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target)
                return mid;
            else if (nums[mid] < target)
                start = mid + 1;
            else
                end = mid;
        }
        return target <= nums[end] ? end : end + 1;
    }

    List<Set<Integer>> col = new ArrayList<>();
    List<Set<Integer>> raw = new ArrayList<>();
    List<Set<Integer>> cell = new ArrayList<>();
    List<int []> target = new ArrayList<>();
    public void solveSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            col.add(newSet());
            raw.add(newSet());
            cell.add(newSet());
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '.') {
                    target.add(new int[]{i, j});
                } else {
                    col.get(i).remove(board[i][j] - '0');
                    raw.get(j).remove(board[i][j] - '0');
                    cell.get(cellIndex(i, j)).remove(board[i][j] - '0');
                }
            }
        }
        solveDFS(0, board);
    }
    private boolean solveDFS(int n, char[][] board) {
        if (n == target.size()) return true;
        int i = target.get(n)[0];
        int j = target.get(n)[1];
        int ci = cellIndex(i, j);
        for (int k = 1; k <= 9; k++) {
            if (col.get(i).contains(k) && raw.get(j).contains(k) && cell.get(ci).contains(k)) {
                col.get(i).remove(k);
                raw.get(j).remove(k);
                cell.get(ci).remove(k);
                board[i][j] = (char) (k +'0');
                if (solveDFS(n + 1, board)) return true;
                col.get(i).add(k);
                raw.get(j).add(k);
                cell.get(ci).add(k);
            }
        }
        return false;
    }
    private Set<Integer> newSet() {
        Set<Integer> tmp = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            tmp.add(i + 1);
        }
        return tmp;
    }
    private int cellIndex(int i, int j) {
        return (i / 3) * 3 + j / 3;
    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        permuteDFS(nums, 0, res, new ArrayList<>(), new boolean[nums.length]);
        return res;
    }
    private void permuteDFS(int []nums, int n, List<List<Integer>> res, List<Integer> cur, boolean []visit) {
        if (n == nums.length) {
            res.add(new ArrayList<>(cur));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (visit[i]) continue;
            if (i > 0 && !visit[i - 1] && nums[i] == nums[i-1]) continue;
            visit[i] = true;
            cur.add(nums[i]);
            permuteDFS(nums, n + 1, res, cur, visit);
            cur.remove(cur.size() - 1);
            visit[i] = false;
        }
    }

    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int []> list = new ArrayList<>(Arrays.asList(intervals));
        list.add(newInterval);
        list.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                if (a[0] == b[0]) return a[1] - b[1];
                return a[0] - b[0];
            }
        });
        for (int i = 1; i < list.size(); i++) {
            int [] pre = list.get(i - 1);
            int [] cur = list.get(i);
            if (cur[0] <= pre[1]) {
                pre[0] = Math.min(pre[0], cur[0]);
                pre[1] = Math.max(pre[1], cur[1]);
                list.remove(i);
                i--;
            }
        }
        return list.toArray(new int[list.size()][]);
    }

    public String simplifyPath(String path) {
        Stack<String> pl = new Stack<>();
        String [] as = path.split("/+");
        for (String str : as) {
            if (str.equals(".") || str.equals("")) {
                continue;
            }
            if (str.equals("..")) {
                if (!pl.isEmpty()) pl.pop();
            } else {
                pl.push(str);
            }
        }
        System.out.println(Arrays.toString(as));
        StringBuilder sb = new StringBuilder();
        for (String str : pl) {
            sb.append("/");
            sb.append(str);
        }
        if (sb.length() == 0)
            sb.append('/');
        return sb.toString();
    }

    public int removeDuplicates(int[] nums) {
        int i = 1, index = 0;
        for (; i < nums.length; i++) if (nums[i] != nums[index]) nums[++index] = nums[i];
        return index + 1;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();

        if (root == null)
            return res;

        q.add(root);
        while (!q.isEmpty()) {
            int n = q.size();
            res.add(q.peek().val);
            while (n-- != 0) {
                root = q.poll();
                if (root.right != null)
                    q.add(root.right);
                if (root.left != null)
                    q.add(root.left);
            }
        }
        return res;
    }
}

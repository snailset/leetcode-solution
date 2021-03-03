
### 搜索 回溯
N-Queens II

### 贪心
Jump Game II
Sudoku Solver

### 排列组合

39. 组合总和
40. 组合总和 II
46. 全排列
47. 全排列 II
77. 组合
78. 子集
90. 子集 II

``` java
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
```
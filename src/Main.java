import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Solution s = new Solution();
        int []nums = new int[]{1,1,1,2,2,3};
        int n = s.removeDuplicates(nums);
        for (int i = 0; i < n; i++)
            System.out.printf("%d ", nums[n]);
    }
}


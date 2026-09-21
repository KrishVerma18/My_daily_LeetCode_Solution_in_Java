class Solution {
  public long[] resultArray(int[] nums, int k) {
    long[] ans = new long[k];
    long[] dp = new long[k];

    for (final int num : nums) {
      long[] newDp = new long[k];
      final int numMod = num % k;
      
      newDp[numMod] = 1;
      
      for (int i = 0; i < k; i++) {
        final int newMod = (int) (1L * i * numMod % k);
        newDp[newMod] += dp[i];
      }
      
      for (int i = 0; i < k; i++)
        ans[i] += newDp[i];
      dp = newDp;
    }

    return ans;
  }
}
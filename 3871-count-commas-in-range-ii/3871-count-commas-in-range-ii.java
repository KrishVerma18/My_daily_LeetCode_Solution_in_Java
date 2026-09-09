class Solution {
    public long countCommas(long n) {
        long ans = 0;
        for (long x = 1000; x <= n; x = x * 1000) {
            ans = ans + n - x + 1;
        }
        return ans;
    }
}
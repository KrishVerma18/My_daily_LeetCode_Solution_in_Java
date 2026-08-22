class Solution {
    public boolean checkDivisibility(int n) {
        int s = 0, p = 1;
        int x = n;
        while (x != 0) {
            int v = x % 10;
            x = x/10;
            s = s+v;
            p = p*v;
        }
        return n % (s + p) == 0;
    }
}
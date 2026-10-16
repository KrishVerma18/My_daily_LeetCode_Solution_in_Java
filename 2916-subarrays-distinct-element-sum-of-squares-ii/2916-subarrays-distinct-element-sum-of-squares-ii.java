class Solution {
    int mod = 1000000007;
    long[] sum, sq, lazy;
    int n;

    public int sumCounts(int[] nums) {
        n = nums.length;
        sum = new long[4 * n];
        sq = new long[4 * n];
        lazy = new long[4 * n];

        int[] last = new int[100001];
        java.util.Arrays.fill(last, -1);

        long ans = 0;

        for (int i = 0; i < n; i++) {
            update(1, 0, n - 1, last[nums[i]] + 1, i);
            last[nums[i]] = i;
            ans = (ans + sq[1]) % mod;
        }

        return (int) ans;
    }

    void push(int node, int l, int r) {
        if (lazy[node] == 0) return;

        long x = lazy[node];
        long len = r - l + 1;

        sq[node] = (sq[node] + 2 * x * sum[node] + x * x * len) % mod;
        sum[node] = (sum[node] + x * len) % mod;

        if (l != r) {
            lazy[node * 2] += x;
            lazy[node * 2 + 1] += x;
        }

        lazy[node] = 0;
    }

    void update(int node, int l, int r, int ql, int qr) {
        push(node, l, r);

        if (l > qr || r < ql) return;

        if (l >= ql && r <= qr) {
            lazy[node] = 1;
            push(node, l, r);
            return;
        }

        int mid = (l + r) / 2;

        update(node * 2, l, mid, ql, qr);
        update(node * 2 + 1, mid + 1, r, ql, qr);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % mod;
        sq[node] = (sq[node * 2] + sq[node * 2 + 1]) % mod;
    }
}
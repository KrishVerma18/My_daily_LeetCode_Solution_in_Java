public class Solution {
    private static final int MOD = 1_000_000_007;

    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] memo = new int[m][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return dfs(0, 0, grid, memo);
    }

    private int dfs(int r, int c, int[][] grid, int[][] memo) {
        int m = grid.length;
        int n = grid[0].length;

        if (r == m - 1 && c == n - 1) return 1;
        if (memo[r][c] != -1) return memo[r][c];

        long paths = 0;

        int[] rightTarget = getDestination(r, c + 1, 0, grid);
        if (rightTarget != null) {
            paths = (paths + dfs(rightTarget[0], rightTarget[1], grid, memo)) % MOD;
        }

        int[] downTarget = getDestination(r + 1, c, 1, grid);
        if (downTarget != null) {
            paths = (paths + dfs(downTarget[0], downTarget[1], grid, memo)) % MOD;
        }

        return memo[r][c] = (int) paths;
    }

    private int[] getDestination(int r, int c, int dir, int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        while (r >= 0 && r < m && c >= 0 && c < n && grid[r][c] == 1) {
            if (r == m - 1 && c == n - 1) return new int[]{r, c};
            if (dir == 0) {
                r++;
                dir = 1;
            } else {
                c++;
                dir = 0;
            }
        }

        if (r >= 0 && r < m && c >= 0 && c < n) {
            return new int[]{r, c};
        }
        return null;
    }
}
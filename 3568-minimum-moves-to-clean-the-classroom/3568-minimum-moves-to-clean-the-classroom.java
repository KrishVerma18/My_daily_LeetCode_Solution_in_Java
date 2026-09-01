class Solution {
    public int minMoves(String[] classroom, int energy) {
        int rows = classroom.length;
        int cols = classroom[0].length();
        int[][] litterId = new int[rows][cols];
        
        int startX = 0;
        int startY = 0;
        int totalLitter = 0;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char cell = classroom[i].charAt(j);
                if (cell == 'S') {
                    startX = i;
                    startY = j;
                } else if (cell == 'L') {
                    litterId[i][j] = totalLitter++;
                }
            }
        }
        
        if (totalLitter == 0) {
            return 0;
        }
        
        boolean[][][][] visited = new boolean[rows][cols][energy + 1][1 << totalLitter];
        Queue<int[]> queue = new ArrayDeque<>();
        
        int initialMask = (1 << totalLitter) - 1;
        queue.add(new int[] {startX, startY, energy, initialMask});
        visited[startX][startY][energy][initialMask] = true;
        
        int[] dRow = {-1, 0, 1, 0};
        int[] dCol = {0, 1, 0, -1};
        int moves = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int s = 0; s < size; s++) {
                int[] curr = queue.poll();
                int r = curr[0];
                int c = curr[1];
                int curEnergy = curr[2];
                int mask = curr[3];
                
                if (mask == 0) {
                    return moves;
                }
                
                if (curEnergy <= 0) {
                    continue;
                }
                
                for (int i = 0; i < 4; i++) {
                    int nextR = r + dRow[i];
                    int nextC = c + dCol[i];
                    
                    if (nextR >= 0 && nextR < rows && nextC >= 0 && nextC < cols) {
                        char nextCell = classroom[nextR].charAt(nextC);
                        if (nextCell == 'X') {
                            continue;
                        }
                        
                        int nextEnergy = (nextCell == 'R') ? energy : curEnergy - 1;
                        int nextMask = mask;
                        
                        if (nextCell == 'L') {
                            nextMask &= ~(1 << litterId[nextR][nextC]);
                        }
                        
                        if (!visited[nextR][nextC][nextEnergy][nextMask]) {
                            visited[nextR][nextC][nextEnergy][nextMask] = true;
                            queue.add(new int[] {nextR, nextC, nextEnergy, nextMask});
                        }
                    }
                }
            }
            moves++;
        }
        
        return -1;
    }
}
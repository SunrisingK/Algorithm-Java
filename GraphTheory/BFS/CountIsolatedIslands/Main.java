import java.util.Scanner;
import java.util.Queue;
import java.util.ArrayDeque;


class Solution {
    private int n, m;
    private int[] dx = {1, -1, 0, 0};
    private int[] dy = {0, 0, 1, -1};
    private boolean[][] visited;

    public int countIsolatedIslands(int[][] grid) {
        n = grid.length;
        m = grid[0].length;
        visited = new boolean[n][m];
        int count = 0;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    bfs(i, j, grid);
                    count++;
                }
            }
        }
        return count;
    }
    
    private void bfs(int x, int y, int[][] grid) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{x, y});
        visited[x][y] = true;
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int curX = curr[0];
            int curY = curr[1];
            
            for (int i = 0; i < 4; i++) {
                int nextX = curX + dx[i];
                int nextY = curY + dy[i];
                
                if (nextX >= 0 && nextX < n && nextY >= 0 && nextY < m 
                    && grid[nextX][nextY] == 1 && !visited[nextX][nextY]) {
                    queue.offer(new int[]{nextX, nextY});
                    visited[nextX][nextY] = true;
                }
            }
        }
    }
}


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] grid = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = sc.nextInt();
            }
        }
        System.out.println(new Solution().countIsolatedIslands(grid));
        sc.close();
    }
}

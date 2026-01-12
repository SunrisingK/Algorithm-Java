import java.util.Scanner;


class Solution {
    private int n, m;
    
    private int[] dx = {1, -1, 0, 0};
    private int[] dy = {0, 0, 1, -1};

    private boolean[][] visited;

    private void dfs(int x, int y, int[][] grid) {
        if (x < 0 || x >= n || y < 0 || y >= m) return;
        if (grid[x][y] == 0 || visited[x][y]) return;

        visited[x][y] = true;
        for (int i = 0; i < this.dx.length; ++i) {
            int next_x = x + dx[i];
            int next_y = y + dy[i];
            dfs(next_x, next_y, grid);
        }
    }

    public int countIsolatedIslands(int[][] grid) {
        this.n = grid.length;
        this.m = grid[0].length;
        this.visited = new boolean[n][m];

        int count = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    dfs(i, j, grid);
                    ++count;
                }
            }
        }

        return count;
    }

}


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] grid = new int[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                grid[i][j] = sc.nextInt();
            }
        }
        Solution s = new Solution();
        System.out.println(s.countIsolatedIslands(grid));
        sc.close();
    }
}

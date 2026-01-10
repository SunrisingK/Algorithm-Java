import java.util.Scanner;
import java.util.ArrayList;


class Solution {
    private ArrayList<Integer> path;
    private ArrayList<ArrayList<Integer>> result;

    private void dfs(int cur, int n, int[][] grid) {
        if (cur == n) {
            path.add(cur);
            result.add(new ArrayList<>(path));
            path.remove(path.size() - 1);
            return;
        }

        path.add(cur);
        int[] vertices = grid[cur];
        for (int i = 1; i <= n; ++i) {
            if (vertices[i] == 0) continue;
            dfs(i, n, grid);
        }
        path.remove(path.size() - 1);
    }

    public ArrayList<ArrayList<Integer>> findAllPath(int[][] grid) {
        path = new ArrayList<>();
        result = new ArrayList<>();
        int n = grid.length - 1;
        dfs(1, n, grid);
        return result;
    }
}


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        // 建立邻接矩阵
        int[][] grid = new int[1 + n][1 + n];
        for (int i = 1; i <= m; ++i) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            grid[a][b] = 1;     // a -> b
        }
        Solution s = new Solution();
        ArrayList<ArrayList<Integer>> result = s.findAllPath(grid);
        for (ArrayList<Integer> p : result) {
            for (int x: p) {
                System.out.print(x);
                if (x < n) System.out.print(" ");
            }
            System.out.println();
        }
        sc.close();
    }
}
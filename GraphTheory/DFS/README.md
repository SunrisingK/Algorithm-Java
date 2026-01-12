# DFS（深度优先搜索）算法 - Java实现

## 概述

深度优先搜索（Depth-First Search，DFS）是一种用于遍历或搜索树或图的算法。该算法会尽可能深地探索分支，当节点v的所有边都已被探寻过，搜索将回溯到发现节点v的那条边的起始节点。

## 算法特性

### 时间复杂度

- **邻接表表示**：O(V + E)，其中V是顶点数，E是边数
- **邻接矩阵表示**：O(V²)

### 空间复杂度

- O(V)，主要用于递归栈或显式栈

## 实现方式

### 1. 邻接表表示的图类

```java
import java.util.*;

public class Graph {
    private int V; // 顶点数
    private LinkedList<Integer>[] adj; // 邻接表
  
    // 构造函数
    @SuppressWarnings("unchecked")
    public Graph(int vertices) {
        this.V = vertices;
        adj = new LinkedList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new LinkedList<>();
        }
    }
  
    // 添加边（无向图）
    public void addEdge(int u, int v) {
        adj[u].add(v);
        adj[v].add(u); // 对于有向图，删除此行
    }
  
    // 添加有向边
    public void addDirectedEdge(int u, int v) {
        adj[u].add(v);
    }
  
    // 递归DFS实现
    public void dfsRecursive(int start) {
        boolean[] visited = new boolean[V];
        System.out.print("递归DFS遍历顺序: ");
        dfsRecursiveUtil(start, visited);
        System.out.println();
    }
  
    // 递归辅助函数
    private void dfsRecursiveUtil(int v, boolean[] visited) {
        visited[v] = true;
        System.out.print(v + " ");
    
        for (int neighbor : adj[v]) {
            if (!visited[neighbor]) {
                dfsRecursiveUtil(neighbor, visited);
            }
        }
    }
  
    // 迭代DFS实现（使用栈）
    public void dfsIterative(int start) {
        boolean[] visited = new boolean[V];
        Stack<Integer> stack = new Stack<>();
    
        stack.push(start);
        System.out.print("迭代DFS遍历顺序: ");
    
        while (!stack.isEmpty()) {
            int v = stack.pop();
        
            if (!visited[v]) {
                visited[v] = true;
                System.out.print(v + " ");
            
                // 逆序压入栈，以保持与递归相同的遍历顺序
                List<Integer> neighbors = adj[v];
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    int neighbor = neighbors.get(i);
                    if (!visited[neighbor]) {
                        stack.push(neighbor);
                    }
                }
            }
        }
        System.out.println();
    }
  
    // 查找从起点到终点的路径
    public List<Integer> findPath(int start, int end) {
        boolean[] visited = new boolean[V];
        int[] parent = new int[V];
        Arrays.fill(parent, -1);
        Stack<Integer> stack = new Stack<>();
    
        stack.push(start);
        visited[start] = true;
    
        while (!stack.isEmpty()) {
            int v = stack.pop();
        
            if (v == end) {
                break;
            }
        
            for (int neighbor : adj[v]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent[neighbor] = v;
                    stack.push(neighbor);
                }
            }
        }
    
        // 重构路径
        List<Integer> path = new ArrayList<>();
        if (visited[end]) {
            for (int v = end; v != -1; v = parent[v]) {
                path.add(v);
            }
            Collections.reverse(path);
        }
    
        return path;
    }
  
    // 检测图中是否有环（无向图版本）
    public boolean hasCycle() {
        boolean[] visited = new boolean[V];
    
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                if (hasCycleUtil(i, visited, -1)) {
                    return true;
                }
            }
        }
        return false;
    }
  
    // 环检测辅助函数
    private boolean hasCycleUtil(int v, boolean[] visited, int parent) {
        visited[v] = true;
    
        for (int neighbor : adj[v]) {
            if (!visited[neighbor]) {
                if (hasCycleUtil(neighbor, visited, v)) {
                    return true;
                }
            } else if (neighbor != parent) {
                return true;
            }
        }
    
        return false;
    }
  
    // 计算连通分量数量
    public int connectedComponents() {
        boolean[] visited = new boolean[V];
        int count = 0;
    
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                count++;
                dfsComponentsUtil(i, visited);
            }
        }
    
        return count;
    }
  
    private void dfsComponentsUtil(int v, boolean[] visited) {
        visited[v] = true;
    
        for (int neighbor : adj[v]) {
            if (!visited[neighbor]) {
                dfsComponentsUtil(neighbor, visited);
            }
        }
    }
  
    // 拓扑排序（仅适用于有向无环图）
    public List<Integer> topologicalSort() {
        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[V];
        Stack<Integer> stack = new Stack<>();
    
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }
    
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
    
        return result;
    }
  
    private void topologicalSortUtil(int v, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;
    
        for (int neighbor : adj[v]) {
            if (!visited[neighbor]) {
                topologicalSortUtil(neighbor, visited, stack);
            }
        }
    
        stack.push(v);
    }
  
    // 获取图的表示（用于调试）
    public void printGraph() {
        System.out.println("图的邻接表表示:");
        for (int i = 0; i < V; i++) {
            System.out.print(i + ": ");
            for (int neighbor : adj[i]) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }
}
```

### 2. 使用示例

```java
public class DFSDemo {
    public static void main(String[] args) {
        // 创建图
        Graph g = new Graph(6);
    
        // 添加边
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 5);
        g.addEdge(4, 5);
    
        System.out.println("========== DFS算法演示 ==========");
        g.printGraph();
        System.out.println();
    
        // 递归DFS
        g.dfsRecursive(0);
    
        // 迭代DFS
        g.dfsIterative(0);
    
        // 查找路径
        List<Integer> path = g.findPath(0, 5);
        if (!path.isEmpty()) {
            System.out.print("从0到5的路径: ");
            for (int node : path) {
                System.out.print(node + " ");
            }
            System.out.println();
        } else {
            System.out.println("路径不存在");
        }
    
        // 检测环
        if (g.hasCycle()) {
            System.out.println("图中存在环");
        } else {
            System.out.println("图中不存在环");
        }
    
        // 计算连通分量
        int components = g.connectedComponents();
        System.out.println("连通分量数量: " + components);
    }
}
```

## 应用场景实现

### 1. 迷宫求解

```java
class MazeSolver {
    private char[][] maze;
    private boolean[][] visited;
    private int rows, cols;
    private List<int[]> path;
  
    // 方向数组：上、右、下、左
    private final int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
  
    public MazeSolver(char[][] maze) {
        this.maze = maze;
        this.rows = maze.length;
        this.cols = maze[0].length;
        this.visited = new boolean[rows][cols];
        this.path = new ArrayList<>();
    }
  
    // DFS解决迷宫问题
    public boolean solveMazeDFS(int startX, int startY, int endX, int endY) {
        return dfs(startX, startY, endX, endY);
    }
  
    private boolean dfs(int x, int y, int endX, int endY) {
        // 检查边界和障碍物
        if (x < 0 || x >= rows || y < 0 || y >= cols || 
            maze[x][y] == '#' || visited[x][y]) {
            return false;
        }
    
        // 标记为已访问
        visited[x][y] = true;
        path.add(new int[]{x, y});
    
        // 到达终点
        if (x == endX && y == endY) {
            return true;
        }
    
        // 向四个方向探索
        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
        
            if (dfs(newX, newY, endX, endY)) {
                return true;
            }
        }
    
        // 回溯
        path.remove(path.size() - 1);
        return false;
    }
  
    public List<int[]> getPath() {
        return path;
    }
  
    public void printMazeWithPath() {
        char[][] mazeCopy = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(maze[i], 0, mazeCopy[i], 0, cols);
        }
    
        // 标记路径
        for (int[] point : path) {
            mazeCopy[point[0]][point[1]] = '*';
        }
    
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(mazeCopy[i][j] + " ");
            }
            System.out.println();
        }
    }
}
```

### 2. 岛屿数量问题

```java
class IslandCounter {
    private int[][] grid;
    private int rows, cols;
  
    public IslandCounter(int[][] grid) {
        this.grid = grid;
        this.rows = grid.length;
        this.cols = grid[0].length;
    }
  
    // 计算岛屿数量
    public int countIslands() {
        int count = 0;
        boolean[][] visited = new boolean[rows][cols];
    
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    count++;
                    dfsIsland(i, j, visited);
                }
            }
        }
    
        return count;
    }
  
    private void dfsIsland(int x, int y, boolean[][] visited) {
        if (x < 0 || x >= rows || y < 0 || y >= cols || 
            grid[x][y] == 0 || visited[x][y]) {
            return;
        }
    
        visited[x][y] = true;
    
        // 四个方向
        dfsIsland(x + 1, y, visited);
        dfsIsland(x - 1, y, visited);
        dfsIsland(x, y + 1, visited);
        dfsIsland(x, y - 1, visited);
    
        // 八个方向（包含对角线）
        // dfsIsland(x + 1, y + 1, visited);
        // dfsIsland(x + 1, y - 1, visited);
        // dfsIsland(x - 1, y + 1, visited);
        // dfsIsland(x - 1, y - 1, visited);
    }
}
```

## 高级实现

### 1. 带时间戳的DFS

```java
class TimestampDFS {
    private List<List<Integer>> adj;
    private int V;
    private int[] discoveryTime;
    private int[] finishTime;
    private int time;
  
    public TimestampDFS(int vertices) {
        this.V = vertices;
        this.adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
        this.discoveryTime = new int[V];
        this.finishTime = new int[V];
        this.time = 0;
    }
  
    public void addEdge(int u, int v) {
        adj.get(u).add(v);
    }
  
    public void execute() {
        boolean[] visited = new boolean[V];
    
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                dfs(i, visited);
            }
        }
    
        printTimestamps();
    }
  
    private void dfs(int v, boolean[] visited) {
        visited[v] = true;
        discoveryTime[v] = ++time;
    
        System.out.println("发现节点 " + v + " 在时间 " + time);
    
        for (int neighbor : adj.get(v)) {
            if (!visited[neighbor]) {
                dfs(neighbor, visited);
            }
        }
    
        finishTime[v] = ++time;
        System.out.println("完成节点 " + v + " 在时间 " + time);
    }
  
    private void printTimestamps() {
        System.out.println("\n节点时间戳:");
        System.out.println("节点\t发现时间\t完成时间");
        for (int i = 0; i < V; i++) {
            System.out.println(i + "\t" + discoveryTime[i] + "\t\t" + finishTime[i]);
        }
    }
}
```

### 2. 迭代加深深度优先搜索（IDDFS）

```java
class IDDFS {
    private List<List<Integer>> adj;
    private int V;
  
    public IDDFS(int vertices) {
        this.V = vertices;
        this.adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }
  
    public void addEdge(int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }
  
    // IDDFS主函数
    public boolean iddfs(int start, int target, int maxDepth) {
        for (int depth = 0; depth <= maxDepth; depth++) {
            boolean[] visited = new boolean[V];
            if (dls(start, target, depth, visited)) {
                System.out.println("在深度 " + depth + " 找到目标");
                return true;
            }
        }
        return false;
    }
  
    // 深度受限搜索
    private boolean dls(int current, int target, int depth, boolean[] visited) {
        if (depth == 0 && current == target) {
            return true;
        }
    
        if (depth > 0) {
            visited[current] = true;
        
            for (int neighbor : adj.get(current)) {
                if (!visited[neighbor]) {
                    if (dls(neighbor, target, depth - 1, visited)) {
                        return true;
                    }
                }
            }
        }
    
        return false;
    }
}
```

### 3. 使用回溯的排列组合问题

```java
class PermutationGenerator {
    private List<List<Integer>> permutations;
  
    public List<List<Integer>> generatePermutations(int[] nums) {
        permutations = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        backtrack(nums, current, used);
        return permutations;
    }
  
    private void backtrack(int[] nums, List<Integer> current, boolean[] used) {
        if (current.size() == nums.length) {
            permutations.add(new ArrayList<>(current));
            return;
        }
    
        for (int i = 0; i < nums.length; i++) {
            if (!used[i]) {
                used[i] = true;
                current.add(nums[i]);
            
                backtrack(nums, current, used);
            
                // 回溯
                current.remove(current.size() - 1);
                used[i] = false;
            }
        }
    }
}
```

## 测试用例

```java
public class TestDFS {
    public static void main(String[] args) {
        System.out.println("========== 测试用例1：基本图 ==========");
        testBasicGraph();
    
        System.out.println("\n========== 测试用例2：岛屿数量 ==========");
        testIslandCounter();
    
        System.out.println("\n========== 测试用例3：迷宫求解 ==========");
        testMazeSolver();
    
        System.out.println("\n========== 测试用例4：排列生成 ==========");
        testPermutations();
    }
  
    private static void testBasicGraph() {
        Graph g = new Graph(6);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 5);
        g.addEdge(4, 5);
    
        g.dfsRecursive(0);
        g.dfsIterative(0);
    }
  
    private static void testIslandCounter() {
        int[][] grid = {
            {1, 1, 0, 0, 0},
            {1, 1, 0, 0, 0},
            {0, 0, 1, 0, 0},
            {0, 0, 0, 1, 1}
        };
    
        IslandCounter ic = new IslandCounter(grid);
        System.out.println("岛屿数量: " + ic.countIslands());
    }
  
    private static void testMazeSolver() {
        char[][] maze = {
            {'S', '.', '.', '.', '#'},
            {'#', '#', '.', '#', '.'},
            {'.', '.', '.', '.', '.'},
            {'.', '#', '#', '#', '.'},
            {'.', '.', '.', '.', 'E'}
        };
    
        MazeSolver solver = new MazeSolver(maze);
        boolean found = solver.solveMazeDFS(0, 0, 4, 4);
    
        if (found) {
            System.out.println("找到路径!");
            solver.printMazeWithPath();
        } else {
            System.out.println("未找到路径");
        }
    }
  
    private static void testPermutations() {
        int[] nums = {1, 2, 3};
        PermutationGenerator pg = new PermutationGenerator();
        List<List<Integer>> permutations = pg.generatePermutations(nums);
    
        System.out.println("排列结果:");
        for (List<Integer> perm : permutations) {
            System.out.println(perm);
        }
    }
}
```

## 编译与运行

### 编译命令

```bash
javac Graph.java
java DFSDemo
```

### 运行所有测试

```bash
javac TestDFS.java
java TestDFS
```

## 性能优化建议

1. **使用迭代代替递归**：对于深度很大的图，避免递归栈溢出
2. **使用位运算存储访问状态**：当顶点数很多时，使用BitSet代替boolean数组
3. **提前剪枝**：根据问题特点，提前终止不可能的分支
4. **缓存计算结果**：对于重复计算，使用记忆化技术

## 常见问题解答

### Q1: DFS和BFS有什么区别？

- DFS使用栈（LIFO），BFS使用队列（FIFO）
- DFS更适合寻找是否存在路径，BFS更适合寻找最短路径
- DFS空间复杂度通常较低

### Q2: 如何避免递归栈溢出？

- 使用迭代版本的DFS
- 增加JVM栈大小：`-Xss`参数
- 使用尾递归优化（Java本身不支持，但可手动转换）

### Q3: DFS能否找到最短路径？

- 在未加权的图中，DFS不能保证找到最短路径
- 在加权图中，需要使用其他算法如Dijkstra

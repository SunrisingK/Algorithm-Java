# BFS（广度优先搜索）算法 - Java实现

## 概述

广度优先搜索（Breadth-First Search，BFS）是一种用于遍历或搜索树或图的算法。该算法从根节点开始，逐层遍历所有相邻节点，确保在进入下一层之前完全探索当前层的所有节点。

## 算法特性

### 时间复杂度

- **邻接表表示**：O(V + E)，其中V是顶点数，E是边数
- **邻接矩阵表示**：O(V²)

### 空间复杂度

- O(V)，主要用于队列存储

## 核心特点

- **逐层遍历**：先访问离起点最近的节点
- **最短路径**：在未加权图中能找到最短路径
- **队列结构**：使用FIFO队列实现

## 实现方式

### 1. 基础BFS实现

```java
import java.util.*;

public class Graph {
    private int V; // 顶点数
    private LinkedList<Integer>[] adj; // 邻接表
  
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
        adj[v].add(u);
    }
  
    // 添加有向边
    public void addDirectedEdge(int u, int v) {
        adj[u].add(v);
    }
  
    // 基础BFS实现
    public void bfs(int start) {
        boolean[] visited = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();
    
        visited[start] = true;
        queue.add(start);
    
        System.out.print("BFS遍历顺序: ");
    
        while (!queue.isEmpty()) {
            int v = queue.poll();
            System.out.print(v + " ");
        
            for (int neighbor : adj[v]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
        System.out.println();
    }
  
    // 带层级的BFS
    public void bfsWithLevels(int start) {
        boolean[] visited = new boolean[V];
        int[] level = new int[V];
        Queue<Integer> queue = new LinkedList<>();
    
        visited[start] = true;
        level[start] = 0;
        queue.add(start);
    
        System.out.println("节点层级信息:");
        System.out.println("节点\t层级");
    
        while (!queue.isEmpty()) {
            int v = queue.poll();
            System.out.println(v + "\t" + level[v]);
        
            for (int neighbor : adj[v]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    level[neighbor] = level[v] + 1;
                    queue.add(neighbor);
                }
            }
        }
    }
  
    // 查找最短路径（未加权图）
    public List<Integer> shortestPath(int start, int end) {
        if (start == end) {
            List<Integer> path = new ArrayList<>();
            path.add(start);
            return path;
        }
    
        boolean[] visited = new boolean[V];
        int[] parent = new int[V];
        Arrays.fill(parent, -1);
        Queue<Integer> queue = new LinkedList<>();
    
        visited[start] = true;
        queue.add(start);
    
        while (!queue.isEmpty()) {
            int v = queue.poll();
        
            if (v == end) {
                break;
            }
        
            for (int neighbor : adj[v]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent[neighbor] = v;
                    queue.add(neighbor);
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
  
    // 计算从起点到所有节点的最短距离
    public int[] shortestDistances(int start) {
        int[] distance = new int[V];
        Arrays.fill(distance, -1);
        Queue<Integer> queue = new LinkedList<>();
    
        distance[start] = 0;
        queue.add(start);
    
        while (!queue.isEmpty()) {
            int v = queue.poll();
        
            for (int neighbor : adj[v]) {
                if (distance[neighbor] == -1) {
                    distance[neighbor] = distance[v] + 1;
                    queue.add(neighbor);
                }
            }
        }
    
        return distance;
    }
  
    // 检查图是否连通
    public boolean isConnected() {
        if (V == 0) return true;
    
        boolean[] visited = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();
    
        visited[0] = true;
        queue.add(0);
    
        while (!queue.isEmpty()) {
            int v = queue.poll();
        
            for (int neighbor : adj[v]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
    
        for (boolean v : visited) {
            if (!v) return false;
        }
        return true;
    }
  
    // 寻找连通分量
    public List<List<Integer>> connectedComponents() {
        boolean[] visited = new boolean[V];
        List<List<Integer>> components = new ArrayList<>();
    
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                List<Integer> component = new ArrayList<>();
                Queue<Integer> queue = new LinkedList<>();
            
                visited[i] = true;
                queue.add(i);
            
                while (!queue.isEmpty()) {
                    int v = queue.poll();
                    component.add(v);
                
                    for (int neighbor : adj[v]) {
                        if (!visited[neighbor]) {
                            visited[neighbor] = true;
                            queue.add(neighbor);
                        }
                    }
                }
            
                components.add(component);
            }
        }
    
        return components;
    }
  
    // 检查图是否为二分图
    public boolean isBipartite() {
        int[] color = new int[V];
        Arrays.fill(color, -1); // -1: 未染色, 0: 红色, 1: 蓝色
    
        for (int i = 0; i < V; i++) {
            if (color[i] == -1) {
                if (!bfsCheckBipartite(i, color)) {
                    return false;
                }
            }
        }
    
        return true;
    }
  
    private boolean bfsCheckBipartite(int start, int[] color) {
        Queue<Integer> queue = new LinkedList<>();
        color[start] = 0;
        queue.add(start);
    
        while (!queue.isEmpty()) {
            int v = queue.poll();
        
            for (int neighbor : adj[v]) {
                if (color[neighbor] == -1) {
                    color[neighbor] = 1 - color[v];
                    queue.add(neighbor);
                } else if (color[neighbor] == color[v]) {
                    return false;
                }
            }
        }
    
        return true;
    }
  
    // 打印图
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

### 2. 多源BFS

```java
class MultiSourceBFS {
    private int[][] grid;
    private int rows, cols;
  
    public MultiSourceBFS(int[][] grid) {
        this.grid = grid;
        this.rows = grid.length;
        this.cols = grid[0].length;
    }
  
    public int[][] multiSourceBFS(int[][] sources) {
        int[][] distance = new int[rows][cols];
        for (int[] row : distance) {
            Arrays.fill(row, -1);
        }
    
        Queue<int[]> queue = new LinkedList<>();
    
        // 将所有起点加入队列
        for (int[] source : sources) {
            int x = source[0];
            int y = source[1];
            distance[x][y] = 0;
            queue.add(new int[]{x, y});
        }
    
        // 方向数组
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
        
            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];
            
                if (isValid(newX, newY) && distance[newX][newY] == -1) {
                    distance[newX][newY] = distance[x][y] + 1;
                    queue.add(new int[]{newX, newY});
                }
            }
        }
    
        return distance;
    }
  
    private boolean isValid(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols && grid[x][y] != -1;
    }
}
```

### 3. 0-1 BFS

```java
class ZeroOneBFS {
    static class Edge {
        int to;
        int weight;
    
        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }
  
    private List<List<Edge>> adj;
    private int V;
  
    public ZeroOneBFS(int vertices) {
        this.V = vertices;
        adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }
  
    public void addEdge(int u, int v, int weight) {
        adj.get(u).add(new Edge(v, weight));
        adj.get(v).add(new Edge(u, weight));
    }
  
    public int[] zeroOneBFS(int start) {
        int[] distance = new int[V];
        Arrays.fill(distance, Integer.MAX_VALUE);
        Deque<Integer> deque = new ArrayDeque<>();
    
        distance[start] = 0;
        deque.addFirst(start);
    
        while (!deque.isEmpty()) {
            int v = deque.pollFirst();
        
            for (Edge edge : adj.get(v)) {
                if (distance[edge.to] > distance[v] + edge.weight) {
                    distance[edge.to] = distance[v] + edge.weight;
                
                    if (edge.weight == 0) {
                        deque.addFirst(edge.to);
                    } else {
                        deque.addLast(edge.to);
                    }
                }
            }
        }
    
        return distance;
    }
}
```

## 应用场景实现

### 1. 迷宫求解

```java
class MazeBFS {
    private char[][] maze;
    private int rows, cols;
  
    private final int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
  
    public MazeBFS(char[][] maze) {
        this.maze = maze;
        this.rows = maze.length;
        this.cols = maze[0].length;
    }
  
    public List<int[]> findShortestPath(int[] start, int[] end) {
        boolean[][] visited = new boolean[rows][cols];
        int[][][] parent = new int[rows][cols][2];
    
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                parent[i][j][0] = -1;
                parent[i][j][1] = -1;
            }
        }
    
        Queue<int[]> queue = new LinkedList<>();
        visited[start[0]][start[1]] = true;
        queue.add(start);
    
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
        
            if (x == end[0] && y == end[1]) {
                break;
            }
        
            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];
            
                if (isValid(newX, newY) && !visited[newX][newY]) {
                    visited[newX][newY] = true;
                    parent[newX][newY][0] = x;
                    parent[newX][newY][1] = y;
                    queue.add(new int[]{newX, newY});
                }
            }
        }
    
        // 重构路径
        List<int[]> path = new ArrayList<>();
        if (visited[end[0]][end[1]]) {
            int[] current = end;
            while (current[0] != -1) {
                path.add(current);
                int x = parent[current[0]][current[1]][0];
                int y = parent[current[0]][current[1]][1];
                current = new int[]{x, y};
            }
            Collections.reverse(path);
        }
    
        return path;
    }
  
    public int[][] computeAllDistances(int[] start) {
        int[][] distance = new int[rows][cols];
        for (int[] row : distance) {
            Arrays.fill(row, -1);
        }
    
        Queue<int[]> queue = new LinkedList<>();
        distance[start[0]][start[1]] = 0;
        queue.add(start);
    
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
        
            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];
            
                if (isValid(newX, newY) && distance[newX][newY] == -1) {
                    distance[newX][newY] = distance[x][y] + 1;
                    queue.add(new int[]{newX, newY});
                }
            }
        }
    
        return distance;
    }
  
    private boolean isValid(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols && maze[x][y] != '#';
    }
}
```

### 2. 单词接龙

```java
class WordLadder {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
    
        if (!wordSet.contains(endWord)) {
            return 0;
        }
    
        Queue<String> queue = new LinkedList<>();
        queue.add(beginWord);
        int level = 1;
    
        while (!queue.isEmpty()) {
            int size = queue.size();
        
            for (int i = 0; i < size; i++) {
                String current = queue.poll();
            
                if (current.equals(endWord)) {
                    return level;
                }
            
                char[] chars = current.toCharArray();
                for (int j = 0; j < chars.length; j++) {
                    char original = chars[j];
                
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) continue;
                    
                        chars[j] = c;
                        String newWord = new String(chars);
                    
                        if (wordSet.contains(newWord)) {
                            queue.add(newWord);
                            wordSet.remove(newWord);
                        }
                    }
                
                    chars[j] = original;
                }
            }
        
            level++;
        }
    
        return 0;
    }
  
    // 返回所有最短转换序列
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> result = new ArrayList<>();
        Set<String> dict = new HashSet<>(wordList);
    
        if (!dict.contains(endWord)) {
            return result;
        }
    
        Map<String, List<String>> neighbors = new HashMap<>();
        Map<String, Integer> distance = new HashMap<>();
    
        bfs(beginWord, endWord, dict, neighbors, distance);
    
        List<String> path = new ArrayList<>();
        path.add(beginWord);
        dfs(beginWord, endWord, neighbors, distance, path, result);
    
        return result;
    }
  
    private void bfs(String start, String end, Set<String> dict, 
                    Map<String, List<String>> neighbors, Map<String, Integer> distance) {
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        distance.put(start, 0);
    
        boolean found = false;
    
        while (!queue.isEmpty()) {
            int size = queue.size();
        
            for (int i = 0; i < size; i++) {
                String current = queue.poll();
                int curDistance = distance.get(current);
                List<String> currentNeighbors = getNeighbors(current, dict);
                neighbors.put(current, currentNeighbors);
            
                for (String neighbor : currentNeighbors) {
                    if (!distance.containsKey(neighbor)) {
                        distance.put(neighbor, curDistance + 1);
                        if (neighbor.equals(end)) {
                            found = true;
                        } else {
                            queue.add(neighbor);
                        }
                    }
                }
            }
        
            if (found) break;
        }
    }
  
    private List<String> getNeighbors(String word, Set<String> dict) {
        List<String> neighbors = new ArrayList<>();
        char[] chars = word.toCharArray();
    
        for (int i = 0; i < chars.length; i++) {
            char original = chars[i];
        
            for (char c = 'a'; c <= 'z'; c++) {
                if (c == original) continue;
            
                chars[i] = c;
                String newWord = new String(chars);
                if (dict.contains(newWord)) {
                    neighbors.add(newWord);
                }
            }
        
            chars[i] = original;
        }
    
        return neighbors;
    }
  
    private void dfs(String current, String end, Map<String, List<String>> neighbors,
                    Map<String, Integer> distance, List<String> path, List<List<String>> result) {
        if (current.equals(end)) {
            result.add(new ArrayList<>(path));
            return;
        }
    
        for (String neighbor : neighbors.get(current)) {
            if (distance.get(neighbor) == distance.get(current) + 1) {
                path.add(neighbor);
                dfs(neighbor, end, neighbors, distance, path, result);
                path.remove(path.size() - 1);
            }
        }
    }
}
```

### 3. 腐烂的橘子

```java
class RottingOranges {
    public int orangesRotting(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
    
        Queue<int[]> queue = new LinkedList<>();
        int freshCount = 0;
    
        // 初始化
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 2) {
                    queue.add(new int[]{i, j});
                } else if (grid[i][j] == 1) {
                    freshCount++;
                }
            }
        }
    
        if (freshCount == 0) return 0;
    
        int minutes = 0;
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    
        while (!queue.isEmpty()) {
            int size = queue.size();
            boolean infected = false;
        
            for (int i = 0; i < size; i++) {
                int[] current = queue.poll();
                int x = current[0];
                int y = current[1];
            
                for (int[] dir : directions) {
                    int newX = x + dir[0];
                    int newY = y + dir[1];
                
                    if (newX >= 0 && newX < rows && newY >= 0 && newY < cols 
                        && grid[newX][newY] == 1) {
                        grid[newX][newY] = 2;
                        queue.add(new int[]{newX, newY});
                        freshCount--;
                        infected = true;
                    }
                }
            }
        
            if (infected) {
                minutes++;
            }
        }
    
        return freshCount == 0 ? minutes : -1;
    }
}
```

### 4. 岛屿数量

```java
class NumberOfIslands {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
    
        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;
    
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    bfs(grid, i, j);
                }
            }
        }
    
        return count;
    }
  
    private void bfs(char[][] grid, int x, int y) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});
        grid[x][y] = '0'; // 标记为已访问
    
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int curX = current[0];
            int curY = current[1];
        
            for (int[] dir : directions) {
                int newX = curX + dir[0];
                int newY = curY + dir[1];
            
                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols 
                    && grid[newX][newY] == '1') {
                    grid[newX][newY] = '0';
                    queue.add(new int[]{newX, newY});
                }
            }
        }
    }
}
```

## 测试用例

```java
public class BFSTest {
    public static void main(String[] args) {
        System.out.println("========== 测试用例1：基本BFS ==========");
        testBasicGraph();
    
        System.out.println("\n========== 测试用例2：迷宫求解 ==========");
        testMazeSolver();
    
        System.out.println("\n========== 测试用例3：单词接龙 ==========");
        testWordLadder();
    
        System.out.println("\n========== 测试用例4：腐烂的橘子 ==========");
        testRottingOranges();
    
        System.out.println("\n========== 测试用例5：岛屿数量 ==========");
        testNumberOfIslands();
    
        System.out.println("\n========== 测试用例6：0-1 BFS ==========");
        testZeroOneBFS();
    }
  
    private static void testBasicGraph() {
        Graph g = new Graph(6);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 5);
        g.addEdge(4, 5);
    
        g.printGraph();
        System.out.println();
    
        g.bfs(0);
        System.out.println();
    
        g.bfsWithLevels(0);
        System.out.println();
    
        List<Integer> path = g.shortestPath(0, 5);
        System.out.print("从0到5的最短路径: ");
        for (int node : path) {
            System.out.print(node + " ");
        }
        System.out.println();
    
        int[] distances = g.shortestDistances(0);
        System.out.println("从0到所有节点的距离:");
        for (int i = 0; i < distances.length; i++) {
            System.out.println("到节点" + i + "的距离: " + distances[i]);
        }
    
        System.out.println("图是否连通: " + g.isConnected());
        System.out.println("图是否为二分图: " + g.isBipartite());
    }
  
    private static void testMazeSolver() {
        char[][] maze = {
            {'S', '.', '.', '.', '#'},
            {'#', '#', '.', '#', '.'},
            {'.', '.', '.', '.', '.'},
            {'.', '#', '#', '#', '.'},
            {'.', '.', '.', '.', 'E'}
        };
    
        MazeBFS mazeSolver = new MazeBFS(maze);
        int[] start = {0, 0};
        int[] end = {4, 4};
    
        List<int[]> path = mazeSolver.findShortestPath(start, end);
        System.out.println("最短路径长度: " + path.size());
        System.out.print("路径: ");
        for (int[] point : path) {
            System.out.print("(" + point[0] + "," + point[1] + ") ");
        }
        System.out.println();
    }
  
    private static void testWordLadder() {
        WordLadder wl = new WordLadder();
        String beginWord = "hit";
        String endWord = "cog";
        List<String> wordList = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");
    
        int length = wl.ladderLength(beginWord, endWord, wordList);
        System.out.println("最短转换序列长度: " + length);
    
        List<List<String>> allPaths = wl.findLadders(beginWord, endWord, wordList);
        System.out.println("所有最短转换序列:");
        for (List<String> path : allPaths) {
            System.out.println(path);
        }
    }
  
    private static void testRottingOranges() {
        RottingOranges ro = new RottingOranges();
        int[][] grid = {
            {2, 1, 1},
            {1, 1, 0},
            {0, 1, 1}
        };
    
        int minutes = ro.orangesRotting(grid);
        System.out.println("所有橘子腐烂所需时间: " + minutes + " 分钟");
    }
  
    private static void testNumberOfIslands() {
        NumberOfIslands noi = new NumberOfIslands();
        char[][] grid = {
            {'1', '1', '0', '0', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '1', '0', '0'},
            {'0', '0', '0', '1', '1'}
        };
    
        int count = noi.numIslands(grid);
        System.out.println("岛屿数量: " + count);
    }
  
    private static void testZeroOneBFS() {
        ZeroOneBFS zobfs = new ZeroOneBFS(5);
        zobfs.addEdge(0, 1, 0);
        zobfs.addEdge(0, 2, 1);
        zobfs.addEdge(1, 2, 1);
        zobfs.addEdge(1, 3, 0);
        zobfs.addEdge(2, 4, 0);
        zobfs.addEdge(3, 4, 1);
    
        int[] distances = zobfs.zeroOneBFS(0);
        System.out.println("从节点0到所有节点的最短距离:");
        for (int i = 0; i < distances.length; i++) {
            System.out.println("到节点" + i + "的距离: " + distances[i]);
        }
    }
}
```

## 性能优化技巧

### 1. 使用ArrayDeque代替LinkedList

```java
// 更高效的队列实现
Queue<Integer> queue = new ArrayDeque<>();
Deque<Integer> deque = new ArrayDeque<>();
```

### 2. 预分配集合大小

```java
// 预分配大小以减少扩容开销
List<Integer> list = new ArrayList<>(expectedSize);
Map<String, Integer> map = new HashMap<>(expectedSize);
```

### 3. 使用位运算优化访问状态

```java
// 使用BitSet替代boolean数组
BitSet visited = new BitSet(V);
visited.set(start);
```

### 4. 批量操作减少方法调用

```java
// 批量处理同一层的节点
while (!queue.isEmpty()) {
    int size = queue.size();
    for (int i = 0; i < size; i++) {
        // 处理当前层节点
    }
    // 进入下一层
}
```

## BFS与DFS对比表

| 特性       | BFS                    | DFS                      |
| ---------- | ---------------------- | ------------------------ |
| 数据结构   | 队列 (Queue)           | 栈 (Stack)               |
| 遍历顺序   | 层级遍历 (Level Order) | 深度优先 (Depth First)   |
| 空间复杂度 | O(V)                   | O(V)                     |
| 最短路径   | 保证（未加权图）       | 不保证                   |
| 实现方式   | 迭代（队列）           | 递归/迭代（栈）          |
| 适用场景   | 最短路径、层级关系     | 拓扑排序、路径存在性     |
| 内存使用   | 通常较多（存储当前层） | 通常较少（存储当前路径） |

## 常见问题解答

### Q1: BFS为什么使用队列而不是栈？

队列的FIFO特性保证了按层级遍历，栈的LIFO特性会导致深度优先遍历。

### Q2: BFS如何处理循环引用？

使用visited集合或数组记录已访问节点，避免重复访问。

### Q3: BFS能否在有向图中使用？

可以，BFS同样适用于有向图和无向图。

### Q4: 如何选择BFS和DFS？

- 需要最短路径：选择BFS
- 需要检查路径是否存在：都可以
- 需要拓扑排序：选择DFS
- 内存有限：考虑DFS

### Q5: BFS的时间复杂度是多少？

- 邻接表：O(V + E)
- 邻接矩阵：O(V²)

## 扩展应用

### 1. 社交网络分析

- 查找两个人之间的最短关系链
- 计算社交影响力范围

### 2. 网络爬虫

- 控制爬取深度
- 广度优先抓取网页

### 3. 游戏开发

- 寻找最短路径（迷宫、棋类）
- AI决策树搜索

### 4. 图像处理

- 区域填充（洪水填充）
- 图像分割

## 进阶话题

### 1. 双向BFS

从起点和终点同时开始搜索，相遇时结束。

### 2. 启发式BFS（A*算法）

结合启发式函数估计成本，优化搜索方向。

### 3. 并行BFS

使用多线程或分布式系统加速BFS。

### 4. 增量BFS

动态图上的增量搜索。

## LeetCode经典题目

1. **102. 二叉树的层序遍历** - 基础的BFS应用
2. **200. 岛屿数量** - 网格BFS
3. **127. 单词接龙** - 字符串BFS
4. **994. 腐烂的橘子** - 多源BFS
5. **433. 最小基因变化** - 类似单词接龙
6. **752. 打开转盘锁** - 状态空间搜索
7. **1091. 二进制矩阵中的最短路径** - 网格最短路径
8. **130. 被围绕的区域** - 边界BFS

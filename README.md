# Java中常用的数据结构及使用方法

## 1. 动态数组- `ArrayList<T>`

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 1. 创建
List<Integer> list = new ArrayList<>(); // 推荐使用List接口
ArrayList<String> list2 = new ArrayList<>();

// 2. 初始化（带初始容量）
List<Integer> listWithCapacity = new ArrayList<>(20); // 初始容量20

// 3. 初始化（带初始元素）
List<Integer> initializedList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

// 4. 基本操作
list.add(10);                    // 添加到末尾，O(1) 平摊
list.add(0, 5);                  // 插入到指定位置，O(n)
list.addAll(Arrays.asList(6, 7)); // 批量添加

list.set(0, 100);                // 修改指定位置元素，O(1)
int element = list.get(0);       // 访问指定位置，O(1)

list.remove(0);                  // 删除指定位置元素，O(n)
list.remove(Integer.valueOf(10)); // 删除第一个匹配的元素，O(n)
list.removeAll(Arrays.asList(2, 3)); // 删除所有匹配的元素

list.clear();                    // 清空所有元素
boolean isEmpty = list.isEmpty(); // 是否为空
int size = list.size();          // 元素个数

// 5. 查找
boolean contains = list.contains(5); // 是否包含，O(n)
int index = list.indexOf(5);      // 第一次出现的索引，O(n)
int lastIndex = list.lastIndexOf(5); // 最后一次出现的索引，O(n)
```

## 2. 栈（Stack） - `Deque<T>`

```java
// 创建
Deque<Integer> stack = new ArrayDeque<>();

// 基本操作
stack.push(1);            // 压栈
stack.peek();             // 查看栈顶（不删除），空栈返回null
stack.pop();              // 弹栈，空栈抛出NoSuchElementException
stack.isEmpty();          // 是否为空
stack.size();             // 元素个数

// 遍历（会清空栈）
while (!stack.isEmpty()) {
    int item = stack.pop();
    System.out.println(item);
}

// 安全操作
Integer top = stack.peek();
if (top != null) {
    // 处理栈顶元素
}
```

## 3. 队列（Queue） - `Queue<T>`

```java
// 创建
Queue<Integer> queue = new LinkedList<>(); // 或 ArrayDeque

// 基本操作
queue.offer(1);           // 入队（推荐），成功返回true
queue.add(2);             // 入队，失败抛异常
queue.poll();             // 出队（推荐），空队列返回null
queue.remove();           // 出队，空队列抛异常
queue.peek();             // 查看队首，空队列返回null
queue.element();          // 查看队首，空队列抛异常
queue.isEmpty();
queue.size();
```

## 4. 小根堆 - `PriorityQueue<T>`

```java
// 创建（默认小根堆）
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// 基本操作
minHeap.offer(3);         // 插入元素
minHeap.add(1);           // 插入元素（抛异常版本）
minHeap.poll();           // 取出最小元素，空堆返回null
minHeap.remove();         // 取出最小元素，空堆抛异常
minHeap.peek();           // 查看最小元素，不删除
minHeap.isEmpty();
minHeap.size();
```

## 5. 大根堆 - `PriorityQueue<T>`

```java
// 创建大根堆
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
// 或
PriorityQueue<Integer> maxHeap2 = new PriorityQueue<>(Collections.reverseOrder());

// 操作同小根堆，但poll/peek返回最大元素
```

## 6. 哈希表 - `Map<K, V>`

```java
// 创建
Map<String, Integer> map = new HashMap<>();

// 增/改
map.put("apple", 1);            // 插入或更新
map.putIfAbsent("banana", 2);   // 只有不存在时才插入

// 查
int value = map.get("apple");             // 不存在返回null
int value2 = map.getOrDefault("orange", 0);  // 不存在返回默认值
boolean hasKey = map.containsKey("apple");
boolean hasValue = map.containsValue(1);

// 删
map.remove("apple");                     // 删除指定key
map.remove("banana", 2);                 // 只有key-value匹配时才删除

// 遍历（最常用）
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    String key = entry.getKey();
    Integer value = entry.getValue();
}

// 遍历key
for (String key : map.keySet()) {
    Integer value = map.get(key);
}

// 遍历value
for (Integer value : map.values()) {
    System.out.println(value);
}

// 常用模式：频率统计
Map<Character, Integer> freq = new HashMap<>();
for (char c : s.toCharArray()) {
    freq.put(c, freq.getOrDefault(c, 0) + 1);
}

// 常用模式：两数之和
Map<Integer, Integer> seen = new HashMap<>();
for (int i = 0; i < nums.length; i++) {
    int complement = target - nums[i];
    if (seen.containsKey(complement)) {
        return new int[]{seen.get(complement), i};
    }
    seen.put(nums[i], i);
}
```

## 7. 哈希集合 - `Set<T>`

```java
// 创建
Set<Integer> set = new HashSet<>();

// 增
set.add(1);              // 添加元素，返回是否成功
set.addAll(Arrays.asList(2, 3, 4));  // 批量添加

// 查
boolean contains = set.contains(1);  // 是否包含
set.isEmpty();
set.size();

// 删
set.remove(1);           // 删除元素，返回是否成功
set.clear();             // 清空集合

// 遍历
for (int num : set) {
    System.out.println(num);
}

// 集合运算
Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3));
Set<Integer> set2 = new HashSet<>(Arrays.asList(3, 4, 5));

set1.addAll(set2);       // 并集
set1.retainAll(set2);    // 交集
set1.removeAll(set2);    // 差集

// 数组与集合转换
int[] arr = {1, 2, 3};
Set<Integer> setFromArray = new HashSet<>();
for (int num : arr) setFromArray.add(num);

// 集合转数组
Integer[] array = set.toArray(new Integer[0]);

// 常用模式：去重
Set<Integer> unique = new HashSet<>(Arrays.asList(1, 2, 2, 3, 3));
// unique = {1, 2, 3}
```

## 8. 有序集合 - `TreeSet<T>`

```java
// 创建
TreeSet<Integer> treeSet = new TreeSet<>();

// 基本操作（同HashSet）
treeSet.add(5);
treeSet.add(2);
treeSet.add(8);
treeSet.remove(2);
treeSet.contains(5);
treeSet.size();

// 特有操作（边界查询）
treeSet.first();           // 最小元素：2
treeSet.last();            // 最大元素：8
treeSet.ceiling(3);        // >=3的最小元素：5（不存在返回null）
treeSet.floor(3);          // <=3的最大元素：2（不存在返回null）
treeSet.higher(3);         // >3的最小元素：5
treeSet.lower(3);          // <3的最大元素：2

// 范围查询
SortedSet<Integer> subset = treeSet.subSet(2, 5);  // [2, 5) 左闭右开
SortedSet<Integer> headSet = treeSet.headSet(5);   // <5的元素
SortedSet<Integer> tailSet = treeSet.tailSet(5);   // >=5的元素

// 包含边界
NavigableSet<Integer> range = treeSet.subSet(2, true, 5, true);  // [2, 5]

// 遍历（有序）
for (int num : treeSet) {
    System.out.println(num);  // 升序输出
}

// 反向遍历
for (int num : treeSet.descendingSet()) {
    System.out.println(num);  // 降序输出
}

// 弹出最小/最大
Integer min = treeSet.pollFirst();  // 移除并返回最小值
Integer max = treeSet.pollLast();   // 移除并返回最大值

// 自定义排序
TreeSet<Integer> customSet = new TreeSet<>((a, b) -> b - a);  // 降序
```

## 9. 综合使用示例

```java
// 示例：字符频率统计并排序
public List<Character> frequencySort(String s) {
    // 1. 统计频率
    Map<Character, Integer> freq = new HashMap<>();
    for (char c : s.toCharArray()) {
        freq.put(c, freq.getOrDefault(c, 0) + 1);
    }
  
    // 2. 按频率降序排序
    PriorityQueue<Map.Entry<Character, Integer>> maxHeap = 
        new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
    maxHeap.addAll(freq.entrySet());
  
    // 3. 构建结果
    StringBuilder result = new StringBuilder();
    while (!maxHeap.isEmpty()) {
        Map.Entry<Character, Integer> entry = maxHeap.poll();
        for (int i = 0; i < entry.getValue(); i++) {
            result.append(entry.getKey());
        }
    }
  
    return result.toString();
}

// 示例：BFS + 哈希表记录距离
public int bfsShortestPath(int start, int target) {
    Queue<Integer> queue = new LinkedList<>();
    Map<Integer, Integer> distance = new HashMap<>();  // 记录距离
  
    queue.offer(start);
    distance.put(start, 0);
  
    while (!queue.isEmpty()) {
        int current = queue.poll();
        int currentDist = distance.get(current);
      
        if (current == target) {
            return currentDist;
        }
      
        for (int neighbor : getNeighbors(current)) {
            if (!distance.containsKey(neighbor)) {
                distance.put(neighbor, currentDist + 1);
                queue.offer(neighbor);
            }
        }
    }
  
    return -1;  // 未找到
}
```

## 10. 速查表

| 数据结构         | 创建                       | 添加           | 删除              | 查询                  | 遍历                             |
| ---------------- | -------------------------- | -------------- | ----------------- | --------------------- | -------------------------------- |
| **动态数组**     | `ArrayList<T>`             | `add()`        | `remove(index)`   | `get(index)`          | `for/get()` 或 `for-each`        |
| **栈**           | `Deque<T> stack`           | `push()`       | `pop()`           | `peek()`              | `while(!isEmpty()) pop()`        |
| **队列**         | `Queue<T> queue`           | `offer()`      | `poll()`          | `peek()`              | `for(T item : queue)`            |
| **小根堆**       | `PriorityQueue<T>`         | `offer()`      | `poll()`          | `peek()`              | `while(!isEmpty()) poll()`       |
| **大根堆**       | `PriorityQueue<T>(逆序)`   | `offer()`      | `poll()`          | `peek()`              | 同上                             |
| **哈希表**       | `Map<K,V> map`             | `put(k,v)`     | `remove(k)`       | `get(k)`              | `entrySet()/keySet()/values()`   |
| **哈希集**       | `Set<T> set`               | `add()`        | `remove()`        | `contains()`          | `for(T item : set)`              |
| **有序集**       | `TreeSet<T>`               | `add()`        | `remove()`        | `ceiling()/floor()`   | 有序遍历                         |

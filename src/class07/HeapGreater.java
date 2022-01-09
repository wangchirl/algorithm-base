package class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * 1、加强堆
 * 	1）堆结构
 * 	2）反向索引表
 * 	3）堆大小
 * 	4）比较器
 * 	堆调整时，反向索引表同步调整
 */
public class HeapGreater<T> {

	private ArrayList<T> heap; // 堆结构
	private HashMap<T, Integer> indexMap; // 反向索引表
	private int heapSize; // 堆大小
	private Comparator<? super T> comp; // 比较器

	public HeapGreater(Comparator<T> c) {
		heap = new ArrayList<>();
		indexMap = new HashMap<>();
		heapSize = 0;
		comp = c;
	}

	public boolean isEmpty() {
		return heapSize == 0;
	}

	public int size() {
		return heapSize;
	}

	public boolean contains(T obj) {
		return indexMap.containsKey(obj);
	}

	public T peek() {
		return heap.get(0);
	}

	// 添加元素
	public void push(T obj) {
		// 加入堆
		heap.add(obj);
		// 加入反向索引表
		indexMap.put(obj, heapSize);
		// 调整堆
		heapInsert(heapSize++);
	}

	// 弹出堆顶元素
	public T pop() {
		// 获取要返回的堆顶元素
		T ans = heap.get(0);
		// 堆顶元素与堆的最后一个元素交换
		swap(0, heapSize - 1);
		// 反向索引表异常弹出的元素
		indexMap.remove(ans);
		// 堆中删除最后一个元素，堆大小减一
		heap.remove(--heapSize);
		// 堆顶的 0 位置元素重新调整堆结构
		heapify(0);
		return ans;
	}

	// 删除堆中元素
	public void remove(T obj) {
		// 拿堆中最后一个元素与要删除的元素进行交换
		T replace = heap.get(heapSize - 1);
		// 要删除元素的下标
		int index = indexMap.get(obj);
		// 索引表删除要删除的元素
		indexMap.remove(obj);
		// 堆中删除最后一个元素，堆大小减一
		heap.remove(--heapSize);
		// 如果要删除的元素不是最后一个元素的情况下才进行调整
		if (obj != replace) {
			// 将原堆中最后一个元素设置到删除的元素位置
			heap.set(index, replace);
			// 更新索引表
			indexMap.put(replace, index);
			// 重新调整堆
			resign(replace);
		}
	}

	// 修改对象后重写调整堆
	public void resign(T obj) {
		// 网上推或者往下推，二者只会走一个
		heapInsert(indexMap.get(obj));
		heapify(indexMap.get(obj));
	}

	// 请返回堆上的所有元素
	public List<T> getAllElements() {
		List<T> ans = new ArrayList<>();
		for (T c : heap) {
			ans.add(c);
		}
		return ans;
	}

	private void heapInsert(int index) {
		while (comp.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
			swap(index, (index - 1) / 2);
			index = (index - 1) / 2;
		}
	}

	private void heapify(int index) {
		int left = index * 2 + 1;
		while (left < heapSize) {
			int best = left + 1 < heapSize && comp.compare(heap.get(left + 1), heap.get(left)) < 0 ? (left + 1) : left;
			best = comp.compare(heap.get(best), heap.get(index)) < 0 ? best : index;
			if (best == index) {
				break;
			}
			swap(best, index);
			index = best;
			left = index * 2 + 1;
		}
	}

	// 交换元素，同步更新索引表
	private void swap(int i, int j) {
		T o1 = heap.get(i);
		T o2 = heap.get(j);
		heap.set(i, o2);
		heap.set(j, o1);
		indexMap.put(o2, i);
		indexMap.put(o1, j);
	}

}

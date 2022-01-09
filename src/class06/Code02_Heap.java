package class06;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 1、堆
 * 	1）完全二叉树：一个满的二叉树或从左到右依次变满的二叉树
 *
 * 2、数组来表示完全二叉树
 * 	1）任何一个 i 位置结点有以下规律
 * 		左孩子 = 2 * i + 1
 * 		右孩子 = 2 * i + 2
 * 		父节点 = (i - 1) / 2
 *
 * 3、堆：数组 + size
 * 	1）大根堆：每一颗子树的头结点都大于其孩子结点
 * 	2）小根堆：每一颗子树的头结点都小于其孩子结点
 *
 * 	heapInsert：新加进入的数
 * 			>>	新数据停在了 index 位置，依次往上看父节点
 * 				如果比父节点大则进行交换，否则不大于或达到了0位置了，不再继续
 *
 * 	heapIfy：删掉头结点（0位置的数）
 * 		  >> 拿最后一个位置的数放在 0 位置，堆 size-1
 * 		  	 此时很可能不是堆结构，需要调整堆结构
 * 		  	 0 位置看左右孩子，找到较大孩子，看一下能不能大于自己
 * 		  	 如果大于则2个位置进行交换，依次进行，直到干不掉了或堆最后了停
 *
 * 	heapInsert 与 heapIfy 二者只会发生一个，在不确定如何调整堆的情况下，二者依次调用可保万无一失（加强堆的使用）
 *
 * 4、堆排序
 * 	1）将数组调整为大根堆
 * 	2）将数组 0 位置的元素与 N-1 位置的元素进行交换
 * 	3）heapSize--
 * 	4）数组 0 位置的元素 heapIfy 调整堆结构，继续保持大根堆
 * 	5）重新将 0 位置的元素与 N-2 位置的元素进行交换，周而复始，直到 heapSize = 1
 */
public class Code02_Heap {

	public static class MyMaxHeap {
		private int[] heap;
		private final int limit;
		private int heapSize;

		public MyMaxHeap(int limit) {
			heap = new int[limit];
			this.limit = limit;
			heapSize = 0;
		}

		public boolean isEmpty() {
			return heapSize == 0;
		}

		public boolean isFull() {
			return heapSize == limit;
		}

		public void push(int value) {
			if (heapSize == limit) {
				throw new RuntimeException("heap is full");
			}
			heap[heapSize] = value;
			// value  heapSize
			heapInsert(heap, heapSize++);
		}

		// 用户此时，让你返回最大值，并且在大根堆中，把最大值删掉
		// 剩下的数，依然保持大根堆组织
		public int pop() {
			int ans = heap[0];
			swap(heap, 0, --heapSize);
			heapify(heap, 0, heapSize);
			return ans;
		}


		// 新加进来的数，现在停在了index位置，请依次往上移动，
		// 移动到0位置，或者干不掉自己的父亲了，停！
		private void heapInsert(int[] arr, int index) {
			// [index]    [index-1]/2
			// index == 0
			while (arr[index] > arr[(index - 1) / 2]) {
				swap(arr, index, (index - 1) / 2);
				index = (index - 1) / 2;
			}
		}

		// 从index位置，往下看，不断的下沉
		// 停：较大的孩子都不再比index位置的数大；已经没孩子了
		private void heapify(int[] arr, int index, int heapSize) {
			// 左孩子下标
			int left = index * 2 + 1;
			// 还有孩子则继续
			while (left < heapSize) {
				// 如果有左孩子，有没有右孩子，可能有可能没有！
				// 把较大孩子的下标，给largest
				int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
				largest = arr[largest] > arr[index] ? largest : index;
				// 干不掉父节点了
				if (largest == index) {
					break;
				}
				// index和较大孩子，要互换
				swap(arr, largest, index);
				index = largest;
				left = index * 2 + 1;
			}
		}

		private void swap(int[] arr, int i, int j) {
			int tmp = arr[i];
			arr[i] = arr[j];
			arr[j] = tmp;
		}

	}

	public static class RightMaxHeap {
		private int[] arr;
		private final int limit;
		private int size;

		public RightMaxHeap(int limit) {
			arr = new int[limit];
			this.limit = limit;
			size = 0;
		}

		public boolean isEmpty() {
			return size == 0;
		}

		public boolean isFull() {
			return size == limit;
		}

		public void push(int value) {
			if (size == limit) {
				throw new RuntimeException("heap is full");
			}
			arr[size++] = value;
		}

		public int pop() {
			int maxIndex = 0;
			for (int i = 1; i < size; i++) {
				if (arr[i] > arr[maxIndex]) {
					maxIndex = i;
				}
			}
			int ans = arr[maxIndex];
			arr[maxIndex] = arr[--size];
			return ans;
		}

	}

	
	public static class MyComparator implements Comparator<Integer>{

		@Override
		public int compare(Integer o1, Integer o2) {
			return o2 - o1;
		}
		
	}
	
	public static void main(String[] args) {
		// 小根堆
		PriorityQueue<Integer> heap = new PriorityQueue<>(new MyComparator());
		heap.add(5);
		heap.add(5);
		heap.add(5);
		heap.add(3);
		//  5 , 3
		System.out.println(heap.peek());
		heap.add(7);
		heap.add(0);
		heap.add(7);
		heap.add(0);
		heap.add(7);
		heap.add(0);
		System.out.println(heap.peek());
		while(!heap.isEmpty()) {
			System.out.println(heap.poll());
		}
		
		
		
		
		
		
		
		int value = 1000;
		int limit = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			int curLimit = (int) (Math.random() * limit) + 1;
			MyMaxHeap my = new MyMaxHeap(curLimit);
			RightMaxHeap test = new RightMaxHeap(curLimit);
			int curOpTimes = (int) (Math.random() * limit);
			for (int j = 0; j < curOpTimes; j++) {
				if (my.isEmpty() != test.isEmpty()) {
					System.out.println("Oops!");
				}
				if (my.isFull() != test.isFull()) {
					System.out.println("Oops!");
				}
				if (my.isEmpty()) {
					int curValue = (int) (Math.random() * value);
					my.push(curValue);
					test.push(curValue);
				} else if (my.isFull()) {
					if (my.pop() != test.pop()) {
						System.out.println("Oops!");
					}
				} else {
					if (Math.random() < 0.5) {
						int curValue = (int) (Math.random() * value);
						my.push(curValue);
						test.push(curValue);
					} else {
						if (my.pop() != test.pop()) {
							System.out.println("Oops!");
						}
					}
				}
			}
		}
		System.out.println("finish!");

	}

}

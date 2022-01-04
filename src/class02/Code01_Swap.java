package class02;

/**
 * 1、如何不引入第三个变量交换2个数
 * 异或运算：
 * N ^ 0 = N 任何数异或0等于这个数本身
 * N ^ N = 0 任何数异或自身等于0
 * N ^ M ^ N = N ^ N ^ M = M 异或运算满足交换律与结合律
 */
public class Code01_Swap {
	
	public static void main(String[] args) {

		int a = 16;
		int b = 603;

		System.out.println(a);
		System.out.println(b);

		a = a + b;
		b = a - b;
		a = a - b;

		System.out.println(a);
		System.out.println(b);

		a = 16;
		b = 603;

		System.out.println(a);
		System.out.println(b);

		a = a ^ b;
		b = a ^ b;
		a = a ^ b;

		System.out.println(a);
		System.out.println(b);

		int[] arr = {3,1,100};
		
		int i = 0;
		int j = 0;
		
		arr[i] = arr[i] ^ arr[j];
		arr[j] = arr[i] ^ arr[j];
		arr[i] = arr[i] ^ arr[j];
		
		System.out.println(arr[i] + " , " + arr[j]);

		
		System.out.println(arr[0]);
		System.out.println(arr[2]);
		
		swap(arr, 0, 0);
		
		System.out.println(arr[0]);
		System.out.println(arr[2]);
		

	}
	
	
	public static void swap (int[] arr, int i, int j) {
		// arr[0] = arr[0] ^ arr[0];
		arr[i]  = arr[i] ^ arr[j];
		arr[j]  = arr[i] ^ arr[j];
		arr[i]  = arr[i] ^ arr[j];
	}
	
	

}

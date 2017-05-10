package feng.fubao.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Solution {

	/**
	 * solution O(n log n)
	 * 
	 * @param sequence should has more than 1 element.
	 * @return
	 */
	public static List<Integer> longest_increasing_subsequence(List<Integer> sequence) {

		int[] a = new int[sequence.size()];

		int k = 0;
		for (Integer i : sequence) {
			a[k] = i;
			k++;
		}
		int[] m = new int[a.length + 1];
		int[] p = new int[a.length];

		int h = 2; // the high level of the array m;

		for (int i = 0; i < p.length; i++) {
			p[i] = -1;
			m[i] = -1;
		}
		m[p.length] = -1;

		m[1] = 0;

		for (int i = 1; i < a.length; i++) {
			int index = locate(a, i, m, h);
			if (index == 0) {
				m[index + 1] = i;
				p[i] = -1;
				continue;
			} else {
				m[index] = i;
				p[i] = m[index - 1];
			}
			h = Math.max(index + 1, h);
		}

		int max = 0;
		for (int i = 1; i < m.length; i++) {
			if (i == m.length - 1 || m[i + 1] < 0) {
				max = i;
				break;
			}
		}
		ArrayList<Integer> r = new ArrayList<Integer>(max);

		r.add(a[m[max]]);
		int t = p[m[max]];

		while (t >= 0) {
			r.add(a[t]);
			t = p[t];
		}

		Collections.reverse(r);
		return r;

	}

	/**
	 * binary search
	 * 
	 * @param a
	 * @param i
	 * @param m
	 * @param h
	 * @return
	 */
	private static int locate(int[] a, int i, int[] m, int h) {

		int l = 1;
		int hi = h;
		int k = a[i];
		int mi = (l + hi) / 2;
		while (((l < hi) && (k != a[m[mi]]))) {

			if (k > a[m[mi]]) {
				l = mi + 1;

			} else if (k < a[m[mi]]) {
				hi = mi - 1;
			}
			mi = (l + hi) / 2;
		}

		if (mi == h) {
			return mi;
		} else if (mi == 0 || k > a[m[mi]]) {
			return mi + 1;
		}
		return mi;
	}

	public static void main(String[] ar) {
		List<Integer> a = new ArrayList<Integer>(Arrays.asList(1, 3, 2, 4));
		List<Integer> r = Solution.longest_increasing_subsequence(a);

		for (Integer i : r) {
			System.out.println(i);
		}
	}
}

import java.util.*;
import java.io.*;

class B14003 {
    static int N;
    static int[] arr, indexDP, valueDP;
    static Stack<Integer> stack;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        int lis = 0;
        Arrays.fill(indexDP, -2);
        Arrays.fill(valueDP, -1000000001);

        for (int i = 0; i < N; i++) {
            int idx = binarySearch(0, lis, arr[i]);

            if (idx == -1) {
                valueDP[lis] = arr[i];
                indexDP[i] = lis++;
            }
            else {
                valueDP[idx] = arr[i];
                indexDP[i] = idx;
            }
        }

        for (int i = N-1; i >= 0; i--) {
            if (lis - 1 == indexDP[i]) {
                stack.add(arr[i]);
                lis--;
            }
        }
    }

    public static int binarySearch(int s, int e, int target) {
        int res = -1;

        while (s <= e) {
            int mid = (s + e) / 2;

            if (target <= valueDP[mid]) {
                res = mid;
                e = mid - 1;
            }
            else {
                s = mid + 1;
            }
        }

        return res;
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(stack.size()+"\n");

        while (!stack.isEmpty()) {
            bw.write(stack.pop()+" ");
        }

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        arr = new int[N];
        indexDP = new int[N];
        valueDP = new int[N];
        stack = new Stack<>();

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        br.close();
    }
}
import java.util.*;
import java.io.*;

class B6549 {
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken());

            if (N == 0) break;

            Stack<Integer> stack = new Stack<>();
            int[] arr = new int[N];
            long result = 0;

            for (int n = 0; n < N; n++) {
                arr[n] = Integer.parseInt(st.nextToken());

                while (!stack.isEmpty() && arr[stack.peek()] >= arr[n]) {
                    long height = arr[stack.pop()];
                    long width = stack.isEmpty() ? n - 1 : n - stack.peek() - 1;

                    result = Math.max(result, height * width);
                }

                stack.add(n);
            }

            while (!stack.isEmpty()) {
                long height = arr[stack.pop()];
                long width = stack.isEmpty() ? N : N - stack.peek() - 1;

                result = Math.max(result, height * width);
            }

            bw.write(result+"\n");
        }

        br.close();
        bw.flush();
        bw.close();
    }
}
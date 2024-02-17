import java.util.*;
import java.io.*;

class B2812 {
    static int N, K;
    static String num;
    static StringBuilder result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        Stack<Character> stack = new Stack<>();
        int remain = N;
        int fill = N - K;

        for (int i = 0; i < N; i++) {
            char n = num.charAt(i);

            while (!stack.isEmpty() && remain > fill && stack.peek() < n) {
                stack.pop();
                fill++;
            }

            remain--;
            if (stack.size() < N - K) {
                fill--;
                stack.push(n);
            }
        }

        while (!stack.isEmpty()) {
            result.insert(0, stack.pop());
        }
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result.toString()+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        num = br.readLine();
        result = new StringBuilder();

        br.close();
    }
}
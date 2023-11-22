import java.util.*;
import java.io.*;

class B1655 {
    static PriorityQueue<Integer> bigger;
    static PriorityQueue<Integer> smaller;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        bigger = new PriorityQueue<>(Collections.reverseOrder()); // 내림차순, 최대 값을 가지고 있음
        smaller = new PriorityQueue<>(); // 오름차순, 최소 값을 가지고 있음

        for (int n = 0; n < N; n++) {
            int num = Integer.parseInt(br.readLine());

            if (bigger.size() == smaller.size()) bigger.add(num);
            else smaller.add(num);

            if (!bigger.isEmpty() && !smaller.isEmpty()) {
                if (bigger.peek() > smaller.peek()) {
                    smaller.add(bigger.poll());
                    bigger.add(smaller.poll());
                }
            }

            bw.write(bigger.peek()+"\n");
        }

        br.close();
        bw.flush();
        bw.close();
    }
}
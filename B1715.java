import java.util.*;
import java.io.*;

public class B1715 {
    static int N;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        for (int i = 0; i < N; i++) {
            queue.add(Integer.parseInt(br.readLine()));
        }

        int sum = 0;
        while (queue.size() > 1) {
            int a = queue.poll();
            int b = queue.poll();
            sum += (a + b);

            queue.add(a+b);
        }

        if (sum == 0) bw.write(0+"");
        else bw.write(sum +"");

        br.close();
        bw.flush();
        bw.close();
    }
}
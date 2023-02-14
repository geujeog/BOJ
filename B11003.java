import java.util.*;
import java.io.*;

class B11003 {
    static BufferedWriter bw;
    static int N;
    static int L;
    static int[] arr;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        solution();

        br.close();
        bw.flush();
        bw.close();
    }

    public static void solution () throws IOException {
        LinkedList<Tuple> queue = new LinkedList<>();

        // 초기값 설정
        queue.add(new Tuple(0, arr[0]));
        int count = 1;
        bw.write(arr[0]+" ");

        while (count != N) {
            // 배열 조정 - 1) 맨 앞 값에 대한 범위 확인
            if (queue.getFirst().idx < count + 1 - L) queue.removeFirst();

            // 배열 조정 - 2) 새로 들어온 값에 대한 오름차순
            while (!queue.isEmpty() && queue.getLast().num > arr[count]) {
                queue.removeLast();
            }
            queue.add(new Tuple(count, arr[count]));
            count++;

            bw.write(queue.getFirst().num+" ");
        }
    }

    public static class Tuple {
        int idx;
        int num;

        public Tuple (int idx, int num) {
            this.idx = idx;
            this.num = num;
        }
    }
}
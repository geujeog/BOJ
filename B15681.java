import java.util.*;
import java.io.*;

public class B15681 {
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int R = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        List<Integer>[] list = new ArrayList[N+1];

        for (int i = 1; i <= N; i++) {
            list[i] = new ArrayList<>();
        }

        for (int i = 1; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());

            list[v1].add(v2);
            list[v2].add(v1);
        }

        int[] parent = new int[N+1];
        parentDFS(list, R, parent);

        for (int i = 0; i < Q; i++) {
            int search = Integer.parseInt(br.readLine());
            bw.write(parent[search]+"\n");
        }

        br.close();
        bw.flush();
        bw.close();
    }

    public static int parentDFS (List<Integer>[] list, int v, int[] parent) {
        parent[v] = 1;

        for (Integer i : list[v]) {
            if (parent[i] == 0) {
                parent[v] += parentDFS(list, i, parent);
            }
        }

        return parent[v];
    }
}
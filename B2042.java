import java.util.*;
import java.io.*;

class B2042 {
    static long[] arr;
    static long[] tree;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken()) + Integer.parseInt(st.nextToken());

        arr = new long[N];
        tree = new long[4*N];

        for (int i = 0; i < N; i++) {
            arr[i] = Long.parseLong(br.readLine());
        }

        initTree(0, N-1, 1);

        for (int k = 0; k < K; k++) {
            st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken()) - 1;

            if (command == 1) {
                long b = Long.parseLong(st.nextToken());
                updateTree(0, N-1, 1, a, b - arr[a]);
                arr[a] = b;
            }
            else if (command == 2) {
                int b = Integer.parseInt(st.nextToken()) - 1;
                bw.write(sumTree(0, N-1, 1, a, b)+"\n");
            }
        }

        br.close();
        bw.flush();
        bw.close();
    }

    public static void updateTree(int s, int e, int node, int targetIdx, long diff) {
        if (targetIdx < s || targetIdx > e) return;

        tree[node] += diff;

        if (s == e) return;

        int mid = (s + e) / 2;

        updateTree(s, mid, 2*node, targetIdx, diff);
        updateTree(mid+1, e, 2*node+1, targetIdx, diff);
    }

    public static long sumTree(int s, int e, int node, int a, int b) {
        if (e < a || s > b) return 0;
        if (a <= s && e <= b) return tree[node];

        int mid = (s + e) / 2;

        return sumTree(s, mid, 2*node, a, b) + sumTree(mid+1, e, 2*node+1, a, b);
    }

    public static long initTree(int s, int e, int node) {
        if (s == e) return tree[node] = arr[s];

        int mid = (s + e) / 2;

        return tree[node] = (initTree(s, mid, 2*node) + initTree(mid+1, e, 2*node + 1));
    }
}
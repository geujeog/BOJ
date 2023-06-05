import java.util.*;
import java.io.*;

public class B3584 {
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());

        for (int t = 0; t < T; t++) {
            int N = Integer.parseInt(br.readLine());

            boolean[] isChild = new boolean[N+1];
            List<Integer>[] list = new ArrayList[N+1];

            for (int i = 1; i <= N; i++) {
                list[i] = new ArrayList<>();
            }

            for (int i = 1; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                isChild[b] = true;
                list[a].add(b);
            }

            int root = 0;
            for (int i = 1; i <= N; i++) {
                if (!isChild[i]) {
                    root = i;
                    break;
                }
            }

            int[] parent = new int[N+1];
            int[] depth = new int[N+1];

            init(list, parent, depth, root, 0);

            String line = br.readLine();
            int v1 = Integer.parseInt(line.split(" ")[0]);
            int v2 = Integer.parseInt(line.split(" ")[1]);

            bw.write(commonParent(parent, depth, v1, v2)+"\n");
        }

        br.close();
        bw.flush();
        bw.close();
    }

    public static void init (List<Integer>[] list, int[] parent, int[] depth, int root, int depthCnt) {
        depth[root] = depthCnt;

        for (Integer i : list[root]) {
            parent[i] = root;
            init(list, parent, depth, i, depthCnt+1);
        }
    }

    public static int commonParent(int[] parent, int[] depth, int a, int b) {
        if (a == b) return a;
        if (parent[a] == parent[b]) return parent[a];

        int result = 0;

        if (depth[a] > depth[b]) {
            result = commonParent(parent, depth, parent[a], b);
        }
        else if (depth[a] < depth[b]) {
            result = commonParent(parent, depth, a, parent[b]);
        }
        else {
            result = commonParent(parent, depth, parent[a], parent[b]);
        }

        return result;
    }
}
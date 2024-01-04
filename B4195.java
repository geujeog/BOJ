import java.util.*;
import java.io.*;

class B4195 {
    static BufferedReader br;
    static BufferedWriter bw;
    static int F;
    static int[] parent, level;
    static Map<String, Integer> map;

    public static void main (String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());

        for (int t = 0; t < T; t++) {
            F = Integer.parseInt(br.readLine());
            parent = new int[2 * F];
            level = new int[2 * F];
            map = new HashMap<>();

            for (int i = 0; i < 2 * F; i++) {
                parent[i] = i;
                level[i] = 1;
            }

            int cnt = 0;
            for (int i = 0; i < F; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                String name1 = st.nextToken();
                String name2 = st.nextToken();

                if (!map.containsKey(name1)) {
                    map.put(name1, cnt++);
                }
                if (!map.containsKey(name2)) {
                    map.put(name2, cnt++);
                }

                bw.write(union(map.get(name1), map.get(name2))+"\n");
            }
        }

        br.close();
        bw.flush();
        bw.close();
    }

    public static int union(int a, int b) {
        a = getParent(a);
        b = getParent(b);

        if (a > b) {
            parent[b] = a;
            level[a] += level[b];
            return level[a];
        }
        else if (a < b) {
            parent[a] = b;
            level[b] += level[a];
            return level[b];
        }

        return level[a];
    }

    public static int getParent(int x) {
        if (x == parent[x]) return x;
        return getParent(parent[x]);
    }
}
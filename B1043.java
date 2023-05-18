import java.util.*;
import java.io.*;

public class B1043 {
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] parent = new int[N+1];
        boolean[] know = new boolean[N+1];
        List<Integer>[] party = new List[M];

        // parent 초기화
        for (int i = 1; i <= N; i++) {
            parent[i] = i;
        }

        // know 초기화
        st = new StringTokenizer(br.readLine());
        int knowPeople = Integer.parseInt(st.nextToken());
        for (int i = 0; i < knowPeople; i++) {
            know[Integer.parseInt(st.nextToken())] = true;
        }

        // party 초기화
        for (int m = 0; m < M; m++) {
            st = new StringTokenizer(br.readLine());
            int human = Integer.parseInt(st.nextToken());

            int a = Integer.parseInt(st.nextToken());
            party[m] = new ArrayList<>();
            party[m].add(a);

            for (int i = 1; i < human; i++) {
                int b = Integer.parseInt(st.nextToken());

                party[m].add(b);
                union(parent, a, b);
                a = b;
            }
        }

        // know 추가
        for (int i = 1; i <= N; i++) {
            if (know[i]) {
                know[find(parent, i)] = true;
            }
        }

        int result = 0;
        for (List<Integer> p : party) {
            if (!know[find(parent, p.get(0))]) result++;
        }

        bw.write(result+"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static void union (int[] parent, int a, int b) {
        a = find(parent, a);
        b = find(parent, b);

        if (a != b) {
            if (a < b) parent[b] = a;
            else parent[a] = b;
        }
    }

    public static int find (int[] parent, int x) {
        if (parent[x] == x) return x;
        return find(parent, parent[x]);
    }
}
import java.util.*;
import java.io.*;

class B1017 {
    static int N;
    static int[] arr;
    static List<Integer> first;
    static List<Integer> second;
    static List<Integer>[] link;
    static List<Integer> firstLink;
    static List<Integer> result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // 짝-홀
        // 이분매칭
        getGraph();
        match();
    }

    public static void match() {
        int[] choice = new int[first.size()]; // first 선택한 second
        boolean[] visit = new boolean[first.size()];
        int cnt;

        // 첫번째꺼 선 매칭
        for (int sec: firstLink) {
            Arrays.fill(choice, -1);
            choice[0] = sec;
            cnt = 1;

            for (int j = 0; j < second.size(); j++) {
                if (j == sec) continue;

                Arrays.fill(visit, false);

                if (dfs(j, choice, visit)) {
                    cnt++;
                }
            }

            if (cnt == first.size() && cnt == second.size()) {
                result.add(second.get(sec));
            }
        }
    }

    public static boolean dfs(int v, int[] choice, boolean[] visit) {
        for (int next: link[v]) {
            if (next == 0 || visit[next]) continue;
            visit[next] = true;

            if (choice[next] == -1 || dfs(choice[next], choice, visit)) {
                choice[next] = v;
                return true;
            }
        }
        return false;
    }

    public static void getGraph() {
        first = new ArrayList<>();
        second = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            if (arr[i] % 2 == arr[0] % 2) {
                first.add(arr[i]);
            }
            else {
                second.add(arr[i]);
            }
        }

        link = new ArrayList[second.size()];
        firstLink = new ArrayList<>();

        for (int i = 0; i < second.size(); i++) {
            link[i] = new ArrayList<>();
            for (int j = 0; j < first.size(); j++) {
                if (isDemical(second.get(i) + first.get(j))) {
                    link[i].add(j);

                    if (j == 0) {
                        firstLink.add(i);
                    }
                }
            }
        }
    }

    public static boolean isDemical(int v) {
        for (int i = 2; i*i <= v; i++) {
            if (v % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        if (result.size() == 0) {
            bw.write(-1+"");
        }
        else {
            Collections.sort(result);
            for (int res: result) {
                bw.write(res+" ");
            }
        }

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        arr = new int[N];
        result = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        br.close();
    }
}
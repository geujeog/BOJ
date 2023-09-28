import java.util.*;
import java.io.*;

class B17135 {
    static int N;
    static int M;
    static int D;
    static LinkedList<Tuple>[] arr;
    static List<Integer>[][] dieTime; // dieTime[x][y] : x가 y시간에 적을 죽일 때 우선순위
    static int result;

    public static void main (String[] args) throws IOException {
        init();
        solution();
        print();
    }

    public static void solution() {
        result = 0;

        moveEnemy();
        attackEnemy();

        dfs(0, 0, new boolean[M]);
    }

    public static void dfs(int idx, int cnt, boolean[] choice) {
        if (cnt == 3) {
            List<Integer> tmp = new ArrayList<>();
            for (int i = 0; i < choice.length; i++) {
                if (choice[i]) tmp.add(i);
            }

            result = Math.max(result, getMaxAttack(tmp));

            return;
        }
        if (idx == M) return;

        dfs(idx+1, cnt, choice);

        choice[idx] = true;
        dfs(idx+1, cnt+1, choice);
        choice[idx] = false;
    }

    public static int getMaxAttack(List<Integer> list) {
        int cnt = 0;
        boolean[] isDie = new boolean[arr[0].size()];

        for (int i = 0; i <= N; i++) {
            Set<Integer> tmp = new HashSet<>();

            for (Integer idx : list) {
                for (Integer enemy : dieTime[idx][i]) {
                    if (!isDie[enemy]) {
                        tmp.add(enemy);
                        break;
                    }
                }
            }

            for (Integer enemy : tmp) {
                cnt++;
                isDie[enemy] = true;
            }
        }

        return cnt;
    }

    public static void attackEnemy() {
        for (int i = 0; i < M; i++) {
            // [N][i]에서 공격했을 때, 각 적들의 dieTime 구하기
            bfs(i);
        }
    }

    public static void bfs(int idx) {
        for (int x = 0; x < N; x++) {
            PriorityQueue<Tuple> queue = new PriorityQueue<>();

            for (Tuple t : arr[x]) {
                int far = Math.abs(t.x - N) + Math.abs(t.y - idx);

                if (far <= D) queue.add(new Tuple(t.x, t.y, t.num, far));
            }

            while (!queue.isEmpty()) {
                Tuple tuple = queue.poll();
                dieTime[idx][x+1].add(tuple.num);
            }
        }
    }

    public static class Tuple implements Comparable<Tuple>{
        int x;
        int y;
        int num;
        int far;

        public Tuple(int x, int y, int num) {
            this.x = x;
            this.y = y;
            this.num = num;
        }

        public Tuple(int x, int y, int num, int far) {
            this.x = x;
            this.y = y;
            this.num = num;
            this.far = far;
        }

        @Override
        public int compareTo(Tuple t) {
            if (t.far == this.far) return this.y - t.y;
            return this.far - t.far;
        }
    }

    public static void moveEnemy() {
        for (int z = 1; z < N; z++) {
            for (Tuple t : arr[z-1]) {
                if (t.x + 1 < N) {
                    arr[z].add(new Tuple(t.x + 1, t.y, t.num));
                }
            }
        }
    }

    public static void print() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        arr = new LinkedList[N];
        dieTime = new LinkedList[M][N+1];

        for (int i = 0; i < N; i++) {
            arr[i] = new LinkedList<>();
        }

        for (int i = 0; i < M; i++) {
            for (int j = 0; j <= N; j++) {
                dieTime[i][j] = new LinkedList<>();
            }
        }

        int cnt = 0;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < M; j++) {
                if (Integer.parseInt(st.nextToken()) == 1) arr[0].add(new Tuple(i, j, cnt++));
            }
        }

        br.close();
    }
}
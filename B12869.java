import java.util.*;
import java.io.*;

class B12869 {
    static int[][] attack = {{9, 3, 1}, {9, 1, 3}, {3, 9, 1}, {3, 1, 9}, {1, 3, 9}, {1, 9, 3}};

    static int N;
    static int[] blood;
    static int[][][] visit;
    static int result;

    public static void main (String[] args) throws IOException {
        init();
        solution();
        print();
    }

    public static void solution() {
        result = Integer.MAX_VALUE;

        backtracking(blood[0], blood[1], blood[2], 0);
    }

    public static void backtracking(int hp1, int hp2, int hp3, int cnt) {
        if (cnt >= result) return;
        if (hp1 <= 0 && hp2 <= 0 && hp3 <= 0) {
            result = Math.min(result, cnt);
            return;
        }

        Integer[] hp = {hp1, hp2, hp3};
        Arrays.sort(hp, Collections.reverseOrder());

        hp1 = Math.max(0, hp[0]);
        hp2 = Math.max(0, hp[1]);
        hp3 = Math.max(0, hp[2]);

        if (visit[hp1][hp2][hp3] == 1) return;
        else visit[hp1][hp2][hp3] = 1;

        for (int i = 0; i < attack.length; i++) {
            backtracking(hp1-attack[i][0], hp2-attack[i][1], hp3-attack[i][2], cnt+1);
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

        N = Integer.parseInt(br.readLine());
        blood = new int[3];
        visit = new int[61][61][61];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            blood[i] = Integer.parseInt(st.nextToken());
        }
        for (int i = N; i < 3; i++) {
            blood[i] = 0;
        }

        br.close();
    }
}
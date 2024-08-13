import java.util.*;
import java.io.*;

class B1760 {
    static int N, M;
    static int[][] arr;
    static List<Integer>[] list;
    static int[][] col, row;
    static int colCnt, rowCnt;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        getNumber();
        getLink();
        getResult();
    }

    public static void getResult() {
        int[] owner = new int[rowCnt];
        boolean[] visit = new boolean[rowCnt];

        for (int i = 1; i < colCnt; i++) {
            Arrays.fill(visit, false);
            if (dfs(i, owner, visit)) {
                result++;
            }
        }
    }

    public static boolean dfs(int v, int[] owner, boolean[] visit) {
        for (int next: list[v]) {
            if (visit[next]) continue;

            visit[next] = true;
            if (owner[next] == 0 || dfs(owner[next], owner, visit)) {
                owner[next] = v;
                return true;
            }
        }

        return false;
    }

    public static void getLink() {
        list = new ArrayList[colCnt];
        for (int i = 1; i < colCnt; i++) {
            list[i] = new ArrayList<>();
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (col[i][j] == 0) continue;
                list[col[i][j]].add(row[i][j]);
            }
        }
    }

    public static void getNumber() {
        boolean flag = false;

        colCnt++;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (arr[i][j] == 2) {
                    if (flag) {
                        flag = false;
                        colCnt++;
                    }
                }
                if (arr[i][j] == 0) {
                    if (!flag) flag = true;
                    col[i][j] = colCnt;
                }
            }
            if (flag) {
                flag = false;
                colCnt++;
            }
        }

        flag = false;
        rowCnt++;
        for (int j = 0; j < M; j++) {
            for (int i = 0; i < N; i++) {
                if (arr[i][j] == 2) {
                    if (flag) {
                        flag = false;
                        rowCnt++;
                    }
                }
                if (arr[i][j] == 0) {
                    if (!flag) flag = true;
                    row[i][j] = rowCnt;
                }
            }
            if (flag) {
                flag = false;
                rowCnt++;
            }
        }
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[N][M];
        col = new int[N][M];
        row = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        br.close();
    }
}
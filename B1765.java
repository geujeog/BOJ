import java.util.*;
import java.io.*;

public class B1765 {
    static int n;
    static int[] team;
    static int teamMax;
    static List<Integer>[] friend;
    static List<Integer>[] enemy;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());

        friend = new ArrayList[n+1];
        enemy = new ArrayList[n+1];

        for (int i = 1; i <= n; i++) {
            friend[i] = new ArrayList<>();
        }
        for (int i = 1; i <= n; i++) {
            enemy[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            char conn = st.nextToken().charAt(0);
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if (conn == 'E') {
                enemy[a].add(b);
                enemy[b].add(a);
            }
            else {
                friend[a].add(b);
                friend[b].add(a);
            }
        }

        team = new int[n+1];

        for (int i = 1; i <= n; i++) {
            if (team[i] == 0) {
                dfs(i, ++teamMax);
            }
        }

        bw.write(teamMax+"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static void dfs (int idx, int teamNumber) {
        friendDFS(idx, teamNumber);
        enemyDFS(new boolean[n+1], idx, 0, teamNumber);
    }

    public static void enemyDFS (boolean[] check, int idx, int depth, int teamNumber) {
        check[idx] = true;
        if (depth % 2 == 0) {
            friendDFS(idx, teamNumber);
        }

        for (Integer i: enemy[idx]) {
            if (!check[i]) {
                enemyDFS(check, i, depth + 1, teamNumber);
            }
        }
    }

    public static void friendDFS (int idx, int teamNumber) {
        team[idx] = teamNumber;

        for (Integer i : friend[idx]) {
            if (team[i] == 0) {
                friendDFS(i, teamNumber);
            }
        }
    }
}
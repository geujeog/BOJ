import java.util.*;
import java.io.*;

class B16988 {
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};
    static int N, M;
    static int[][] arr;
    static Map<Integer, Integer> islandSize; // 3 이상부터 상대바둑돌
    static List<int[]> empty;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // 상대돌과 붙어있는 empty 경우의 수

        init();

        if (empty.size() == 1) {
            getResult(new ArrayList<>(){{add(0);}});
        }
        else {
            combinate(0, new ArrayList<>());
        }
    }

    public static void combinate(int idx, List<Integer> fillEmpty) {
        if (fillEmpty.size() == 2) {
            fillOrNot(fillEmpty, 1);
            getResult(fillEmpty);
            fillOrNot(fillEmpty, 0);
            return;
        }
        if (idx == empty.size()) return;

        combinate(idx+1, fillEmpty);

        fillEmpty.add(idx);
        combinate(idx+1, fillEmpty);
        fillEmpty.remove((Integer) idx);
    }

    public static void getResult(List<Integer> fillEmpty) {
        Set<Integer> set = new HashSet<>();
        for (Integer i : fillEmpty) {
            int[] e = empty.get(i);
            set.addAll(bfs(e[0], e[1]));
        }

        int cnt = 0;
        for (Integer s : set) {
            cnt += islandSize.get(s);
        }
        result = Math.max(result, cnt);
    }

    public static Set<Integer> bfs(int i, int j) {
        Set<Integer> dont = new HashSet<>();
        Set<Integer> set = new HashSet<>();
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visit = new boolean[N][M];

        queue.add(new int[]{i, j});
        visit[i][j] = true;

        while (!queue.isEmpty()) {
            int[] q = queue.poll();

            for (int d = 0; d < dx.length; d++) {
                int nx = q[0] + dx[d];
                int ny = q[1] + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
                if (arr[nx][ny] == 1 || visit[nx][ny]) continue;

                if (arr[nx][ny] == 0) {
                    dont.add(arr[q[0]][q[1]]);
                }
                else {
                    visit[nx][ny] = true;
                    set.add(arr[nx][ny]);
                    queue.add(new int[]{nx, ny});
                }
            }
        }

        set.removeAll(dont);
        return set;
    }

    public static void fillOrNot(List<Integer> fillEmpty, int orNot) {
        for (Integer i : fillEmpty) {
            int[] e = empty.get(i);
            arr[e[0]][e[1]] = orNot;
        }
    }

    public static void init() {
        int islandNumber = 3;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (arr[i][j] == 0) {
                    for (int d = 0; d < dx.length; d++) {
                        int nx = i + dx[d];
                        int ny = j + dy[d];

                        if (nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
                        if (arr[nx][ny] >= 2) {
                            empty.add(new int[]{i, j});
                        }
                    }
                }
                else if (arr[i][j] == 2) {
                    islandBFS(i, j, islandNumber++);
                }
            }
        }
    }

    public static void islandBFS(int i, int j, int islandNumber) {
        Queue<int[]> queue = new ArrayDeque<>();
        int cnt = 1;

        arr[i][j] = islandNumber;
        queue.add(new int[]{i, j});

        while (!queue.isEmpty()) {
            int[] q = queue.poll();

            for (int d = 0; d < dx.length; d++) {
                int nx = q[0] + dx[d];
                int ny = q[1] + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
                if (arr[nx][ny] == 2) {
                    arr[nx][ny] = islandNumber;
                    queue.add(new int[]{nx, ny});
                    cnt++;
                }
            }
        }

        islandSize.put(islandNumber, cnt);
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
        empty = new ArrayList<>();
        islandSize = new HashMap<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        br.close();
    }
}
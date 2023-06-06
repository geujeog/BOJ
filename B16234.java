import java.util.*;
import java.io.*;

public class B16234 {
    static int N;
    static int L;
    static int R;
    static int[][] arr;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        arr = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        bw.write(bfs()+"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static int bfs () {
        int dateCnt = 0;

        while (true) {
            if (!isOpen()) break;
            dateCnt++;
        }

        return dateCnt;
    }

    public static boolean isOpen () {
        boolean open = false;

        List<List<Tuple>> country = new ArrayList<>();
        boolean[][] check = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!check[i][j]) {
                    checkCountry(i, j, check, country);
                }
            }
        }

        // 연합이 있을 경우
        if (country.size() != N*N) {
            for (List<Tuple> countryGroup : country) {
                int countryCnt = countryGroup.size();
                int populationCnt = countryGroup.stream().mapToInt(o -> o.population).sum();
                for (Tuple cg : countryGroup) {
                    int calculate = (int) (populationCnt / countryCnt);

                    if (arr[cg.x][cg.y] != calculate) open = true;

                    arr[cg.x][cg.y] = calculate;
                }
            }
        }

        return open;
    }

    public static void checkCountry (int i, int j, boolean[][] check, List<List<Tuple>> country) {
        List<Tuple> countryGroup = new ArrayList<>();
        countryGroup.add(new Tuple(i, j, arr[i][j]));

        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(i, j, 0));

        while (!queue.isEmpty()) {
            Tuple tuple = queue.poll();
            int x = tuple.x;
            int y = tuple.y;
            int beforePopulation = tuple.population;

            if (x < 0 || y < 0 || x >= N || y >= N) continue;
            if (check[x][y]) continue;

            if (beforePopulation != 0) {
                int diff = Math.abs(beforePopulation - arr[x][y]);

                if (diff >= L && diff <= R) {
                    countryGroup.add(new Tuple(x, y, arr[x][y]));
                }
                else continue;
            }

            check[x][y] = true;

            queue.add(new Tuple(x-1, y, arr[x][y]));
            queue.add(new Tuple(x+1, y, arr[x][y]));
            queue.add(new Tuple(x, y-1, arr[x][y]));
            queue.add(new Tuple(x, y+1, arr[x][y]));
        }

        country.add(countryGroup);
    }

    public static class Tuple {
        int x;
        int y;
        int population;

        public Tuple (int x, int y, int population) {
            this.x = x;
            this.y = y;
            this.population = population;
        }
    }
}
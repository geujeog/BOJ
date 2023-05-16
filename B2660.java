import java.util.*;
import java.io.*;

public class B2660 {
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        int[][] arr = new int[N+1][N+1];


        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());

            if (v1 == -1 && v2 == -1) break;

            arr[v1][v2] = 1;
            arr[v2][v1] = 1;
        }

        for (int k = 1; k <= N; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (i == j) continue;
                    if (arr[i][k] == 0 || arr[k][j] == 0) continue;

                    if (arr[i][j] == 0 || arr[i][j] > arr[i][k] + arr[k][j]) {
                        arr[i][j] = arr[i][k] + arr[k][j];
                    }
                }
            }
        }

        TreeMap<Integer, List<Integer>> map = new TreeMap<>();

        for (int i = 1; i <= N; i++) {
            int score = 0;

            for (int j = 1; j <= N; j++) {
                score = Math.max(score, arr[i][j]);
            }

            List<Integer> tmp = new ArrayList<>();
            if (map.containsKey(score)) tmp = map.get(score);

            tmp.add(i);
            map.put(score, tmp);
        }

        bw.write(map.firstKey()+" ");
        bw.write(map.get(map.firstKey()).size()+"\n");

        for (Integer i : map.get(map.firstKey())) {
            bw.write(i+" ");
        }

        br.close();
        bw.flush();
        bw.close();
    }
}

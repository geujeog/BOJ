
import java.util.*;
import java.io.*;

public class B1707 {
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int K = Integer.parseInt(br.readLine());
        for (int k = 0; k < K; k++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int V = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());

            List<Integer>[] list = new ArrayList[V+1];
            for (int i = 1; i <= V; i++) {
                list[i] = new ArrayList<>();
            }

            for (int e = 0; e < E; e++) {
                st = new StringTokenizer(br.readLine());
                int v1 = Integer.parseInt(st.nextToken());
                int v2 = Integer.parseInt(st.nextToken());

                list[v1].add(v2);
                list[v2].add(v1);
            }

            boolean result = true;

            Integer[] draw = new Integer[V+1];

            for (int i = 1; i <= V; i++) {
                if (!result) break;

                if (draw[i] == null) result = trace(list, draw, i, 0);
                else trace(list, draw, i, draw[i]);
            }

            if (result) bw.write("YES"+"\n");
            else bw.write("NO"+"\n");
        }

        br.close();
        bw.flush();
        bw.close();
    }

    public static boolean trace (List<Integer>[] list, Integer[] draw, int v, int color) {
        if (draw[v] != null && draw[v] == color) return true;
        if (draw[v] != null && draw[v] != color) return false;

        boolean isTwoGraph = true;

        draw[v] = color;

        for (Integer i : list[v]) {
            if (!isTwoGraph) break;

            if (color == 0) isTwoGraph = trace(list, draw, i, 1);
            else isTwoGraph = trace(list, draw, i, 0);
        }

        return isTwoGraph;
    }
}
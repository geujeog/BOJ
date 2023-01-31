import java.util.*;
import java.io.*;

class B11967 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        List<String> lines = new ArrayList<>();
        Map<String, List<Tuple>> map = new TreeMap<>();
        for (int i = 0; i < M; i++) {
            lines.add(br.readLine());
        }
        Collections.sort(lines);

        for (String line : lines) {
            int fromX = Integer.parseInt(line.split(" ")[0]);
            int fromY = Integer.parseInt(line.split(" ")[1]);
            int toX = Integer.parseInt(line.split(" ")[2]);
            int toY = Integer.parseInt(line.split(" ")[3]);

            String key = fromX +" "+ fromY;
            if (map.containsKey(key)) {
                List<Tuple> tmp = new ArrayList<>();
                tmp.addAll(map.get(key));
                tmp.add(new Tuple(toX, toY));
                map.put(key, tmp);
            }
            else map.put(key, Arrays.asList(new Tuple(toX, toY)));
        }

        boolean[][] light = new boolean[N+1][N+1];
        boolean[][] moveHistory = new boolean[N+1][N+1];

        // default
        light[1][1] = true;

        bfs(light, moveHistory, map);

        int result = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (light[i][j]) result++;
            }
        }

        bw.write(result+"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static void bfs(boolean[][] light, boolean[][] moveHistory, Map<String, List<Tuple>> map) {
        Queue<Tuple> fresh = new LinkedList<>();
        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(1, 1));

        while (!queue.isEmpty() || !fresh.isEmpty()) {
            while (queue.isEmpty()) {
                Tuple fTuple = fresh.peek();
                int fTx = fTuple.x;
                int fTy = fTuple.y;

                if (fTx > 1 && moveHistory[fTx-1][fTy]) queue.add(fresh.poll());
                else if (fTx < moveHistory.length-2 && moveHistory[fTx-1][fTy]) queue.add(fresh.poll());
                else if (fTy > 1 && moveHistory[fTx][fTy-1]) queue.add(fresh.poll());
                else if (fTy < moveHistory[0].length-2 && moveHistory[fTx][fTy-1]) queue.add(fresh.poll());
                else fresh.remove();

                if (fresh.size() == 0) break;
            }
            if (queue.size() == 0) break;

            Tuple tuple = queue.poll();
            int tx = tuple.x;
            int ty = tuple.y;

            if (tx < 1 || ty < 1 || tx > moveHistory.length-1 || ty > moveHistory[0].length-1) continue;
            if (!light[tx][ty] || moveHistory[tx][ty]) continue;

            moveHistory[tx][ty] = true;

            String key = tuple.x+" "+tuple.y;
            if (map.containsKey(key)) {
                for (Tuple value : map.get(key)) {
                    int x = value.x;
                    int y = value.y;

                    // turn on
                    light[x][y] = true;

                    // input queue
                    fresh.add(new Tuple(x, y));
                }
            }

            queue.add(new Tuple(tx - 1, ty));
            queue.add(new Tuple(tx + 1, ty));
            queue.add(new Tuple(tx, ty - 1));
            queue.add(new Tuple(tx, ty + 1));
        }
    }

    public static class Tuple {
        int x;
        int y;

        public Tuple (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
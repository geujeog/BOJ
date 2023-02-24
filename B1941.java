import java.util.*;
import java.io.*;

class B1941 {
    static char[] seat;
    static int[] index;
    static int result = 0;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        seat = new char[25];
        for (int i = 0; i < 5; i++) {
            String line = br.readLine();
            for (int j = 0; j < 5; j++) {
                seat[5*i+j] = line.charAt(j);
            }
        }

        index = new int[7];
        // 25C7
        getIndex(0, 0, 0, 0);

        bw.write(result+"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static void getIndex (int idx, int count, int yCount, int sCount) {
        if (idx == 25 || count == 7) {
            if (count == 7 && sCount > 3) {
                if (aroundBFS()) result++;
            }
            return;
        }

        index[count] = idx;

        if (seat[idx] == 'Y') getIndex(idx+1, count+1, yCount+1, sCount);
        else getIndex(idx+1, count+1, yCount, sCount+1);

        getIndex(idx+1, count, yCount, sCount);
    }

    public static boolean aroundBFS () {
        boolean[][] check = new boolean[5][5];
        boolean[][] go = new boolean[5][5];
        for (int i = 0; i < 7; i++) {
            // System.out.print(index[i]+" ");
            go[index[i]/5][index[i]%5] = true;
        }
        // System.out.println();

        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(index[0]%5, index[0]/5));

        int cnt = 0;

        while (!queue.isEmpty()) {
            Tuple tuple = queue.poll();
            int x = tuple.x;
            int y = tuple.y;

            if (x < 0 || y < 0 || x == 5 || y == 5) continue;
            if (!go[y][x] || check[y][x]) continue;

            check[y][x] = true;
            cnt++;

            queue.add(new Tuple(x-1, y));
            queue.add(new Tuple(x+1, y));
            queue.add(new Tuple(x, y-1));
            queue.add(new Tuple(x, y+1));
        }
        if (cnt == 7) return true;
        return false;
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
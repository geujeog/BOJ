import java.util.*;
import java.io.*;

public class B9019 {

    static int MAX = 10000;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());

        for (int t = 0; t < T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());

            bw.write(dfs(from, to)+"\n");
        }

        br.close();
        bw.flush();
        bw.close();
    }

    public static String dfs (int from, int to) {
        String result = "";

        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(from, ""));

        boolean[] check = new boolean[MAX];

        while (!queue.isEmpty()) {
            Tuple tuple = queue.poll();
            int num = tuple.num;
            String command = tuple.command;

            if (check[num]) continue;
            if (num == to) {
                result = command;
                break;
            }

            check[num] = true;

            StringBuilder sb = new StringBuilder(command);
            queue.add(new Tuple(commandD(num), sb.append('D').toString()));
            queue.add(new Tuple(commandS(num), sb.deleteCharAt(sb.length()-1).append('S').toString()));
            queue.add(new Tuple(commandL(num), sb.deleteCharAt(sb.length()-1).append('L').toString()));
            queue.add(new Tuple(commandR(num), sb.deleteCharAt(sb.length()-1).append('R').toString()));
        }

        return result;
    }

    public static class Tuple {
        int num;
        String command;

        public Tuple (int num, String command) {
            this.num = num;
            this.command = command;
        }
    }

    public static int commandD (int from) {
        from *= 2;

        if (from >= MAX) from %= MAX;

        return from;
    }

    public static int commandS (int from) {
        from -= 1;

        if (from < 0) from = MAX-1;

        return from;
    }

    public static int commandL (int from) {
        StringBuilder sb = new StringBuilder(String.valueOf(from));
        while (sb.length() < 4) {
            sb.insert(0, 0);
        }

        sb.append(sb.charAt(0)).deleteCharAt(0);

        return Integer.parseInt(sb.toString());
    }

    public static int commandR (int from) {
        StringBuilder sb = new StringBuilder(String.valueOf(from));

        while (sb.length() < 4) {
            sb.insert(0, 0);
        }

        sb.insert(0, sb.charAt(sb.length()-1));
        sb.deleteCharAt(sb.length()-1);

        return Integer.parseInt(sb.toString());
    }
}
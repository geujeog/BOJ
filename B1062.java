import java.util.*;
import java.io.*;

class B1062 {
    static int N;
    static int K;
    static String[] arr;
    static int result;

    public static void main (String[] args) throws IOException {
        init();
        solution();
        print();
    }

    public static void solution() {
        if (K < 5) {
            return;
        }
        if (K == 26) {
            result = N;
            return;
        }

        boolean[] learn = new boolean['z' - 'a' + 1];
        learn['a' - 'a'] = true;
        learn['n' - 'a'] = true;
        learn['t' - 'a'] = true;
        learn['i' - 'a'] = true;
        learn['c' - 'a'] = true;

        choice(learn, 0, 5);
    }

    public static void choice(boolean[] learn, int alphaIdx, int cnt) {
        if (alphaIdx == 'z' - 'a' + 1 || cnt == K) {
            result = Math.max(result, getCanLearn(learn));
            return;
        }

        choice(learn, alphaIdx+1, cnt);

        if (!learn[alphaIdx]) {
            learn[alphaIdx] = true;
            choice(learn, alphaIdx+1, cnt+1);
            learn[alphaIdx] = false;
        }
    }

    public static int getCanLearn(boolean[] learn) {
        int cnt = 0;

        for (int i = 0; i < N; i++) {
            String str = arr[i];

            boolean canLearn = true;
            for (int j = 0; j < str.length(); j++) {
                if (!learn[str.charAt(j) - 'a']) {
                    canLearn = false;
                    break;
                }
            }

            if (canLearn) cnt++;
        }

        return cnt;
    }

    public static void print() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        arr = new String[N];

        for (int i = 0; i < N; i++) {
            arr[i] = br.readLine().replaceAll("anta", "").replaceAll("tica", "");
        }

        br.close();
    }
}
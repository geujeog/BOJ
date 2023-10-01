import java.util.*;
import java.io.*;

class B5525 {
    static int N;
    static int M;
    static String line;
    static int result;

    public static void main (String[] args) throws IOException {
        init();
        solution();
        print();
    }

    public static void solution() {
        int cnt = 0;

        for (int i = 0; i < M-2; i++) {
            if (line.charAt(i) == 'I' && line.charAt(i+1) == 'O' && line.charAt(i+2) == 'I') {
                i++;
                cnt++;

                if (cnt == N) {
                    cnt--;
                    result++;
                }
            }
            else cnt = 0;
        }
    }

    public static void print() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());
        line = br.readLine();

        br.close();
    }
}
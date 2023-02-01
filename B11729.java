import java.util.*;
import java.io.*;

public class B11729 {

        static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        public static void main(String[] args) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            int N = Integer.parseInt(br.readLine());
            bw.write((int) Math.pow(2, N)-1 + "\n");

            hanoi(N, 1, 2, 3);

            br.close();
            bw.flush();
            bw.close();
        }

        public static void hanoi(int N, int from, int mid, int to) throws IOException {
            if (N == 1) {
                bw.write(from+" "+to+"\n");
                return;
            }

            hanoi(N-1, from, to, mid);

            bw.write(from+" "+to+"\n");

            hanoi(N-1, mid, from, to);
        }
    }
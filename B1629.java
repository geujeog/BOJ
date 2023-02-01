import java.util.*;
import java.io.*;

class B1629 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        Long A = Long.parseLong(st.nextToken());
        Long B = Long.parseLong(st.nextToken());
        Long C = Long.parseLong(st.nextToken());

        bw.write(loop(A, B, C) +"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static Long loop (Long A, Long B, Long C) {
        if (B == 1) return A % C;

        Long calcul = loop (A, B/2, C);

        if (B % 2 == 1) return ((calcul * calcul % C) * A) % C;
        return calcul * calcul % C;
    }
}
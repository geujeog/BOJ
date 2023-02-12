import java.util.*;
import java.io.*;

class B9663 {
    static int N;
    static int result = 0;
    static int[] check;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        check = new int[N];  // check[행] = 열

        back(0);

        bw.write(result+"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static void back(int count) {
        if (count == N) {
            result++;
            return;
        }

        for (int i = 0; i < N; i++) {
            check[count] = i;

            // 기존 퀸 확인 필요
            if (canMove(count, i)) {
                back(count+1);
            }
        }
    }

    public static boolean canMove(int count, int i) {
        for (int j = 0; j < count; j++) {
            // 열이 동일할 경우 (행은 무조건적으로 이전값을 가짐.)
            if (check[j] == i) return false;
            // 대각선
            if (count-j == Math.abs(check[j]-i)) return false;
        }
        return true;
    }
}
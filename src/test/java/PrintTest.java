import com.hzq.threadDemo.Print;

/**
 * description
 *
 * @author hzq
 * @date 2022/7/14 10:12
 */
public class PrintTest {

    public static void main(String[] args) {
        Print print = new Print();
        new Thread(() -> {
            for (int i = 0; i < 26; i++) {
                print.printNum();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 26; i++) {
                print.printChar();
            }
        }).start();
    }
}

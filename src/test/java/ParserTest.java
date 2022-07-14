import com.hzq.threadDemo.ymp.FileParser;


/**
 * description
 *
 * @author hzq
 * @date 2022/7/15 0:04
 */
public class ParserTest {

    public static void main(String[] args) throws InterruptedException {
        FileParser  fileParser = FileParser.getSingleton();

        Boolean result = fileParser.getResult();
        System.out.println("zip检测结果:"+result);
    }
}

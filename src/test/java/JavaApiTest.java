import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 *
 * @author hzq
 * @date 2021/11/25 16:19
 */
public class JavaApiTest {



    @Test
    public void test1(){
        float presentSpeed = 0f;
        presentSpeed = 8 * (1.0f + new Random().nextFloat());
        if(presentSpeed > 14f) {
            presentSpeed = 14f;
        }
        BigDecimal time = new BigDecimal(100).divide(new BigDecimal(presentSpeed), 3, RoundingMode.HALF_UP);
        System.out.println(time.floatValue());
    }
}

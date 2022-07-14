import com.hzq.algorithm.slb.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * description: 测试
 *
 * @author hzq
 * @date 2021/10/11 11:16
 */
public class SlbTest {

    @Test
    public void randomTest(){
        String server = RandomGet.getServer();
        System.out.println(server);
    }

    @Test
    public void pollingTest(){
        for (int i = 0; i < 10; i++) {
            String server = PollingGet.getServer(i);
            System.out.println(server);
        }
    }

    @Test
    public void weightRoundRobinTest(){
        for (int i = 0; i < 20; i++) {
            String server = WeightedRoundRobin.getServer(i);
            System.out.println(server);
        }
    }

    @Test
    public void weightSmoothTest(){
        for (int i = 0; i < 10; i++) {
            String server = WeightSmooth.getServer();
            System.out.println(server);
        }
    }

    @Test
    public void hashTest(){
        List<String> clients = Arrays.asList("10.16.32.1","192.168.188.1","192.168.137.1");
        for (int i = 0; i < 20; i++) {
            Random random = new Random();
            int index = random.nextInt(clients.size());
            String client = clients.get(index);
            System.out.println(client+"映射到server:"+ HashGet.getServer(client));
        }
    }

    @Test
    public void hashRingTest(){
        String[] clients = new String[]
                {"10.78.12.3","113.25.63.1","126.12.3.8"};
        for (int i = 0; i < 20; i++) {
            Random random = new Random();
            int index = random.nextInt(clients.length);
            String client = clients[index];
            System.out.println(client+"被路由到服务器:"+ HashRingGet.getServer(client));
        }
    }

    @Test
    public void hashVirtualTest(){
        String[] clients = new String[]
                {"10.78.12.3","113.25.63.1","126.12.3.8","198.162.123.1"};
        for (String client:clients) {
            System.out.println(client+"被路由到服务器:"+ HashVirtualGet.getServer(client));
        }
    }
}

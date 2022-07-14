import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * description
 *
 * @author hzq
 * @date 2021/11/17 15:53
 */
public class GetIpTest {

    static public HashMap<String,Boolean> ping; //ping 后的结果集

    public HashMap<String,Boolean> getPing() { //用来得到ping后的结果集
        return ping;
    }

    //当前线程的数量, 防止过多线程摧毁电脑
    static volatile AtomicInteger threadCount = new AtomicInteger(0);

    public GetIpTest() {
        ping = new HashMap<>();
    }

    public void Ping(String ip) throws Exception {
        //最多30个线程
        while (threadCount.get() > 30)
            Thread.sleep(50);
        threadCount.incrementAndGet();
        PingIp p = new PingIp(ip);
        p.start();
    }

    public void PingAll() throws Exception {
        //首先得到本机的IP，得到网段
        /*InetAddress host = InetAddress.getLocalHost();
        String hostAddress = host.getHostAddress();
        int k = 0;
        k = hostAddress.lastIndexOf(".");
        String ss = hostAddress.substring(0, k + 1);*/
        String ss = "172.16.2.";
        for (int i = 1; i <= 255; i++) {  //对所有局域网Ip
            String iip = ss + i;
            Ping(iip);
        }

        //等着所有Ping结束
        while (threadCount.get() > 0)
            Thread.sleep(50);
    }

    public static void main(String[] args) throws Exception {
        GetIpTest ip = new GetIpTest();
        ip.PingAll();
        Set<Map.Entry<String, Boolean>> entries = ping.entrySet();
        List<Map.Entry<String,Boolean>> list = new ArrayList<>(entries);
        list.sort((o1, o2) -> {
            Integer v1 = Integer.parseInt(o1.getKey().substring(o1.getKey().lastIndexOf(".") + 1));
            Integer v2 = Integer.parseInt(o2.getKey().substring(o2.getKey().lastIndexOf(".") + 1));
            return v1 - v2;
        });

        int count = 0;
        for (Map.Entry<String, Boolean> entry : list) {
            String key =entry.getKey();
            String value = entry.getValue().toString();
            if (value.equals("true")){
                count++;
                System.out.println(key + "-->" + value);
            }
        }
        System.out.println("发现网段机器数量:"+count);
    }

   static class PingIp extends Thread {
        public String ip;          // IP

        public PingIp(String ip) {
            this.ip = ip;
        }

        public void run() {
            try {
                Process p = Runtime.getRuntime().exec("ping " + ip + " -w 300 -n 2");
                InputStreamReader ir = new InputStreamReader(p.getInputStream(), Charset.forName("gbk"));
                LineNumberReader input = new LineNumberReader(ir);
                //读取结果行
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = input.readLine())!=null){
                    sb.append(line);
                }
                //System.out.println("返回值:"+sb.toString());
                ir.close();
                input.close();
                if(sb.toString().indexOf("TTL")>0){
                    ping.put(ip,true);
                }else{
                    ping.put(ip,false);
                }
                //线程结束
                threadCount.decrementAndGet();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

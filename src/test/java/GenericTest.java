import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * ? extends T ：限定了取出数据的类型为T。编译器无法确定 List 所持有的类型，所以无法安全的向其中添加对象。可以添加 null ,
 * 因为 null 可以表示任何类型。所以 List 的 add 方法不能添加任何有意义的元素，但是可以接受现有的子类型List< T > 赋值。
 * 作为形参, 起到参数限制的作用
 *
 * ? super T ：限定了存入数据类型为 T 或者 T 的子类。因为，List< ? super T >中的类型可能是任何 T 的超类型，
 * 所以编译器无法确定get返回的对象类型是 T ,还是 T 的子类，因此返回 Object 类型。
 *
 * @author hzq
 * @date 2021/11/18 11:03
 */
public class GenericTest {

    public static void main(String[] args) {

        List<? extends Animal> list1=new ArrayList<>();

        //list1.add(new Animal());    //错误，无法添加
        //list1.add(new Cat());       //错误，无法添加
        list1.add(null);            //正确，可以添加
        list1.get(1);               //正确，可以获取，返回值类型为Animal



        List<? super Animal> list2=new ArrayList<>();
        list2.add(new Animal());    //正确，可以添加
        list2.add(new Cat());       //正确，可以添加
        list2.get(1);               //正确，可以获取，返回值类型为Object

    }

    static class Animal{}

    static class Cat extends Animal{}

}

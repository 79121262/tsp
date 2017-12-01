import com.tc.container.StartContainer;
import com.tc.service.impl.UerServiceImpl;
import org.junit.Test;

/**
 * Created by cai.tian on 2017/11/30.
 */
public class TcTest {

    @Test
    public void demo() throws Exception{
        StartContainer startContainer = new StartContainer();
        startContainer.startUp(9999,null,TcTest.class.getClassLoader());
    }
}

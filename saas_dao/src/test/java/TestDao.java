import com.shadow.bean.cargo.Factory;
import com.shadow.bean.system.PeDept;
import com.shadow.dao.cargo.FactoryDao;
import com.shadow.dao.system.DeptDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext-dao.xml")
public class TestDao {
    @Autowired
    DeptDao deptDao;

    @Autowired
    FactoryDao factoryDao;

    @Test
    public void testDept() {
        String companyId = "1";
        List<PeDept> list = deptDao.findAll(companyId);
        System.out.println(list);
    }


    @Test
    public void testFactory() {
        Factory factory = factoryDao.selectByPrimaryKey("1d3125df-b186-4a2d-8048-df817d30e52b");
        System.out.println(factory);
    }

}

package testExcel;

import com.shadow.parse.excel.ExcelParse;
import org.junit.Test;

public class ExcelTest {

    @Test
    public void testReadExcel() {
        try {
            new ExcelParse().parse("D:\\HEIMA\\学习资料\\saas\\day10\\06 百万数据解析\\测试.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

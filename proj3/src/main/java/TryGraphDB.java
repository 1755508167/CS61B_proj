import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TryGraphDB {
    public TryGraphDB(String file){
        try {
            File inputFile = new File(file);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            //创建解析器
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            //GraphBuildingHandler会在解析过程中向你的 GraphDB 中添加节点和边
            TryBuildingHandler gbh = new TryBuildingHandler(this);
            //开始正式解析XML文件。每遇到一个标签，gbh的saertElement()就会被你调用
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        TryGraphDB db=new TryGraphDB("try.xml");
    }
}

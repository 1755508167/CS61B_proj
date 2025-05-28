import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TryBuildingHandler extends DefaultHandler {
    public TryGraphDB g;
    public String activeState="";
    public TryBuildingHandler(TryGraphDB g){
        this.g=g;
    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equals("node")){
            activeState="node";
            Long id=Long.parseLong(attributes.getValue("id"));
            System.out.println("the id of node:"+id);
            //如果没有name这个属性，则返回null
            System.out.println("the name of node:"+attributes.getValue("name"));
        } else if (qName.equals("way")) {
            activeState="way";
            Long id=Long.parseLong(attributes.getValue("id"));
            System.out.println("the id of way:"+id);
        } else if (activeState.equals("way") && qName.equals("nd")) {
            System.out.println("Id of a node in this way: " + attributes.getValue("ref"));
        } else if (activeState.equals("way") && qName.equals("tag")) {
            String k = attributes.getValue("k");
            String v = attributes.getValue("v");

            if (k.equals("highway")){
                System.out.println(k+":"+v);
            }
        } else if (activeState.equals("node") && qName.equals("tag")) {
            
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equals("node")){
            System.out.println("Node读取完毕");
        }
    }
}

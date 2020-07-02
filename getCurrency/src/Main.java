import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class Main {
    public static void main(String [] args){
        String url = "http://www.cbr.ru/scripts/XML_daily.asp?format=xml";
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new URL(url).openStream());
            doc.getDocumentElement().normalize();
            int position = -1;
            for (int i=0; i<doc.getDocumentElement().getChildNodes().getLength(); i++){
                String code = doc.getDocumentElement().getChildNodes().item(i).getChildNodes().item(1).getTextContent();
                if (code.equals("HKD")){
                    position=i;
                    break;
                }
            }
            if (position!=-1) {
                double dollars = Double.parseDouble(doc.getDocumentElement().getChildNodes().item(position).getChildNodes().item(2).getTextContent());
                String rub = doc.getDocumentElement().getChildNodes().item(position).getChildNodes().item(4).getTextContent();
                String rubNew = rub.replace(',','.');
                double rubles = Double.parseDouble(rubNew);
                System.out.println ("1 гонконгский доллар равен " + rubles/dollars + " российских рублей");
            } else System.out.println("Нет данных на сайте");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
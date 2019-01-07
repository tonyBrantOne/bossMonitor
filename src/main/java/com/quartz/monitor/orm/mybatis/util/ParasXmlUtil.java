package com.quartz.monitor.orm.mybatis.util;/**
 * @Auther: tony_jaa
 * @Date: 2019/1/7 16:28
 * @Description:
 */

import com.quartz.monitor.publisher.QuartzPostgresqlMonitor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.*;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/7 16:28
 * @Description:
 */
public class ParasXmlUtil {

    private static Logger LOG = LogManager.getLogger( ParasXmlUtil.class );
    private static DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    private static DocumentBuilder db;;
    private static DocumentBuilder getDb() throws ParserConfigurationException {
        if( db == null ){
            synchronized ( ParasXmlUtil.class ){
                if( db == null ){
                    db = dbf.newDocumentBuilder();
                }
            }
        }
        return db;
    }

    public static List<Map<String,String>> loadXml(File file ) throws Exception{
        List<Map<String,String>> list = new LinkedList<>();
        Document document = getDb().parse(file);
        LOG.info("==========================================================================dom解析成功");
        NodeList allList=document.getElementsByTagName("select");
        for(int i = 0; i < allList.getLength(); i++){
            Map<String,String> map = new LinkedHashMap<>();
            Element domainElement=(Element)allList.item(i);

            String id = domainElement.getAttribute("id");
            String parameterType = domainElement.getAttribute("parameterType");
            String resultType = domainElement.getAttribute("resultType");
            String resultCoumn = domainElement.getFirstChild().getNodeValue();

            map.put("id",id);
            map.put("parameterType",parameterType);
            map.put("resultType",resultType);
            map.put("sql", resultCoumn == null ? null : resultCoumn.trim());
            map.put("handelType","select");

            list.add(map);
        }
        return list;
    }


}

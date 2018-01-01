package qge.cn.com.qgenglish.iciba;

import com.baiyang.android.process.parser.xml.handler.ParserHandler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.ArrayList;

public class WorldHandler extends ParserHandler {
    private StringBuilder builder;
    private WorldResp resp;
    private PosBean posBean;
    private PsPronBean psPronBean;
    private SentBean sentBean;


    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        builder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String name)
            throws SAXException {
        super.endElement(uri, localName, name);
        if ("requestcode".equalsIgnoreCase(localName)) {
            resp.requestcode = trim(builder.toString());
        } else if ("message".equalsIgnoreCase(localName)) {
            resp.message = trim(builder.toString());
        } else if ("key".equalsIgnoreCase(localName)) {
            resp.key = trim(builder.toString());
        } else if ("ps".equalsIgnoreCase(localName)) {
            psPronBean.ps = trim(builder.toString());
        } else if ("pron".equalsIgnoreCase(localName)) {
            psPronBean.pron = trim(builder.toString());
            resp.psPronBeanArrayList.add(psPronBean);
            psPronBean = null;
        } else if ("pos".equalsIgnoreCase(localName)) {
            posBean.pos = trim(builder.toString());
        } else if ("acceptation".equalsIgnoreCase(localName)) {
            posBean.acceptation = trim(builder.toString());
            resp.posBeanArrayList.add(posBean);
            posBean = null;
        } else if ("orig".equalsIgnoreCase(localName)) {
            sentBean.orig = trim(builder.toString());
        } else if ("trans".equalsIgnoreCase(localName)) {
            sentBean.trans = trim(builder.toString());
        } else if ("sent".equalsIgnoreCase(localName)) {
            resp.sentBeanArrayList.add(sentBean);
            sentBean = null;
        } else if ("dict".equalsIgnoreCase(localName)) {

        }
        builder.setLength(0);
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        builder = new StringBuilder();

    }

    @Override
    public void startElement(String uri, String localName, String name,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if ("dict".equalsIgnoreCase(localName)) {
            resp = new WorldResp();
            resp.posBeanArrayList = new ArrayList<PosBean>();
            resp.psPronBeanArrayList = new ArrayList<PsPronBean>();
            resp.sentBeanArrayList = new ArrayList<SentBean>();
        } else if ("ps".equalsIgnoreCase(localName)) {
            psPronBean = new PsPronBean();

        } else if ("pos".equalsIgnoreCase(localName)) {
            posBean = new PosBean();

        } else if ("sent".equalsIgnoreCase(localName)) {
            sentBean = new SentBean();

        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    private String trim(String strToTrim) {
        if (null == strToTrim) {
            return null;
        }
        return strToTrim.trim();
    }

    @Override
    public Object getContent() {
        return resp;
    }

}

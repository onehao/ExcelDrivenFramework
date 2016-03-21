/*
 * ################################################################################
 * 
 *    Copyright (c) 2015 Baidu.com, Inc. All Rights Reserved
 *
 *  version: v1
 *  
 *  
 * ################################################################################
 */
package com.sapi.apitest.datadriven.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The utility class used to handle xml and json.
 * @author yanchen
 * 
 */
public class XmlAndJsonUtil {

    private static Logger logger = LogManager.getLogger("");

    /**
     * 判断字符串是否是json格式
     * 
     * @param input
     *            输入字符串
     * @return
     */
    public static boolean isJsonString(String input) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.readValue(input, Object.class);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否是xml格式
     * 
     * @param input
     *            输入字符换
     * @return
     */
    public static boolean isXML(String input) {
        try {
            InputStream is = new ByteArrayInputStream(input.getBytes("utf-8"));
            SAXBuilder sb = new SAXBuilder();
            sb.build(is);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 将xml字符串转换成json
     * 
     * @param xml
     *            输入的xml字符串
     * @param containRoot
     *            是否包含根节点
     * @return
     */
    public static String xml2JSON(String xml, boolean containRoot) {
        JSONObject obj = new JSONObject();
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(is);
            Element root = doc.getRootElement();
            obj.put(root.getName(), iterateElement(root));
            if (containRoot) {
                return obj.toString();
            } else {
                String result = obj.toString();
                return result.substring(result.indexOf(":") + 1,
                        result.length() - 1);
            }
        } catch (Exception e) {
            logger.info("Xml2JsonUtil.xml2JSON:" + e.toString());
            return null;
        }
    }

    /**
     * 解析xml的节点
     * 
     * @param element
     *            xml的一个父节点
     * @return
     */
    @SuppressWarnings({ "unchecked" })
    private static Map<String, Object> iterateElement(Element element) {
        List<?> node = element.getChildren();
        Element et = null;
        Map<String, Object> obj = new HashMap<String, Object>();
        List<Object> list = null;
        for (int i = 0; i < node.size(); i++) {
            list = new LinkedList<Object>();
            et = (Element) node.get(i);
            if (et.getTextTrim().equals("")) {
                if (et.getChildren().size() == 0) {
                    continue;
                }
                if (obj.containsKey(et.getName())) {
                    if (obj.get(et.getName()) instanceof List) {
                        list = (List<Object>) obj.get(et.getName());
                    } else {
                        list = new LinkedList<Object>();
                        list.add(obj.get(et.getName()));
                    }
                }
                list.add(iterateElement(et));
                if (list.size() == 1) {
                    obj.put(et.getName(), list.get(0));
                } else {
                    obj.put(et.getName(), list);
                }
            } else {
                if (obj.containsKey(et.getName())) {
                    if (obj.get(et.getName()) instanceof List) {
                        list = (List<Object>) obj.get(et.getName());
                    } else {
                        list = new LinkedList<Object>();
                        list.add(obj.get(et.getName()));
                    }
                }
                list.add(et.getTextTrim());
                if (list.size() == 1) {
                    obj.put(et.getName(), list.get(0));
                } else {
                    obj.put(et.getName(), list);
                }
            }
        }
        return obj;
    }

    @SuppressWarnings({ "unused", "unchecked" })
    public static Set<String> parseParaFromJosn(Object jsonObject, String pre,
            String type) {
        Set<String> result = new LinkedHashSet<String>();
        ObjectMapper mapper = new ObjectMapper();
        Set<String> keySet = null;
        Map<String, ?> jsonMap = null;
        if (jsonObject instanceof Map) {
            jsonMap = (Map<String, ?>) jsonObject;
        } else if (jsonObject instanceof List) {
            if (((List<?>) jsonObject).get(0) instanceof Map) {
                jsonMap = (Map<String, ?>) ((List<?>) jsonObject).get(0);
            } else if (((List<?>) jsonObject).get(0) instanceof List) {
                result.add(pre + "#:" + type);
                result.addAll(parseParaFromJosn(((List<?>) jsonObject).get(0),
                        pre + "#.", type));
                return result;
            } else {
                int i = 0;
                for (Object o : (List<?>) jsonObject) {
                    result.add(pre + i + ":" + type);
                    i++;
                }
            }

        } else {
            return null;
        }
        if (jsonMap == null) {
            result.add(pre.substring(0, pre.length() - 1) + ":" + type);
            return result;
        }
        keySet = jsonMap.keySet();
        for (String string : keySet) {
            Object value = jsonMap.get(string);
            if (value instanceof LinkedHashMap) {
                result.addAll(parseParaFromJosn((Map<?, ?>) value, pre + string
                        + ".", type));
            } else if (value instanceof List) {
                result.add(pre + string + "#:" + type);
                if (((List<?>) value).size() != 0) {
                    try {
                        result.addAll(parseParaFromJosn(
                                (Map<?, ?>) (((List<?>) value).get(0)), pre
                                        + string + "#.", type));
                    } catch (Exception e) {
                        // para.setParaName(pre.equals("") ? string + "#" : pre
                        // + string + "#");
                        // result.add(para);
                        if (((List<?>) value).get(0) instanceof List) {
                            int i = 0;
                            for (Object o : (List<?>) value) {
                                result.add(pre + string + "#." + i + "#:"
                                        + type);
                                result.addAll(parseParaFromJosn(
                                        (List<?>) (((List<?>) value).get(0)),
                                        pre + string + "#." + i + "#:", type));
                                i++;
                            }
                        } else {
                            int i = 0;
                            for (Object o : (List<?>) value) {
                                result.add(pre + string + "#." + i + ":" + type);
                                i++;
                            }
                        }
                    }
                }

            } else {
                result.add(pre.equals("") ? string + ":" + type : pre + string
                        + ":" + type);
            }
        }
        return result;
    }

    public static Set<String> parseParaFromJosn(String jsonString, String type) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<?, ?> map = mapper.readValue(jsonString, Map.class);
            return parseParaFromJosn(map, "", type);
        } catch (Exception e) {
            try {
                Set<String> paras = new LinkedHashSet<String>();
                List<?> list = mapper.readValue(jsonString, List.class);
                paras.add("#:" + type);
                paras.addAll(parseParaFromJosn(list, "#.", type));
                return paras;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public static Set<String> parseParaFromXML(String xmlString) {
        Set<String> set = parseParaFromJosn(
                XmlAndJsonUtil.xml2JSON(xmlString, true), "xml");
        return set;
    }

    public static void main(String[] args) {
        String s = "{\"location\":[[1233,23344],[123,4555]]}";
        System.out.println(parseParaFromJosn(s, "json"));
    }
}
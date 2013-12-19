/** 
* Copyright 2013 AppDynamics 
* 
* Licensed under the Apache License, Version 2.0 (the License);
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
* http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an AS IS BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.appdynamics.monitors.ehcache;

import org.apache.commons.lang.math.NumberUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class EhcacheRESTWrapper {

    private final static String CACHE_NODE = "cache";
    private final static String CACHE_STATISTICS_NODE = "statistics";
    private final static String CACHE_NODE_NAME = "name";

    private String host;
    private String port;

    public EhcacheRESTWrapper(String host, String port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Connects to the Ehcache host, then converts the response from host into a HashMap of cache metrics
     * @return	HashMap containing metrics for all the caches registered to this Ehcache host
     */
    public HashMap gatherMetrics() throws Exception{
        HttpURLConnection connection = null;
        InputStream is = null;
        HashMap metrics = new HashMap();
        try {
            String cacheServerUrl = constructURL();
            URL u = new URL(cacheServerUrl);
            connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            is = connection.getInputStream();
            metrics = convertResponseToMap(is);
            return metrics;
        } catch(Exception e) {
            throw e;
        } finally {
            try {
                is.close();
                connection.disconnect();
            } catch (NullPointerException npe) {
                throw npe;
            } catch (Exception e) {
                throw e;
            }
        }
    }

    /**
     * Converts the inputstream retrieved from the connection to Ehcache host into a HashMap of metrics
     * @param is Inputstream retrieved from the connection to Ehcache host
     * @return	HashMap containing metrics for all the caches registered to this Ehcache host
     */
    private HashMap convertResponseToMap(InputStream is) throws Exception{
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(is);

        NodeList caches = doc.getElementsByTagName(CACHE_NODE);
        HashMap<String, HashMap<String, Number>> metricsMap = new HashMap<String, HashMap<String, Number>>();
        for (int i = 0; i < caches.getLength(); i++) {
            Node cache = caches.item(i);
            NodeList cacheNodes = cache.getChildNodes();
            String cacheName = null;
            for (int j = 0; j < cacheNodes.getLength(); j++) {
                Node cacheNode = cacheNodes.item(j);
                if (cacheNode.getNodeName().equalsIgnoreCase(CACHE_NODE_NAME)) {
                    cacheName = cacheNode.getTextContent();
                    metricsMap.put(cacheNode.getTextContent(), new HashMap<String,Number>());
                }
                if (cacheNode.getNodeName().equalsIgnoreCase(CACHE_STATISTICS_NODE)) {
                    NodeList statistics = cacheNode.getChildNodes();
                    HashMap<String, Number> statisticsMap = new HashMap<String, Number>();
                    for (int k = 0; k < statistics.getLength(); k++) {
                        Node statisticsNode = statistics.item(k);
                        Number value;
                        if (NumberUtils.isNumber(statisticsNode.getTextContent())) {
                            if (statisticsNode.getTextContent().contains(".")) {
                                value = Double.parseDouble(statisticsNode.getTextContent());
                            }
                            else {
                                value = Long.parseLong(statisticsNode.getTextContent());
                            }
                            statisticsMap.put(statisticsNode.getNodeName(), value);
                        }
                    }
                    if (cacheName != null) {
                        metricsMap.put(cacheName, statisticsMap);
                    }
                }
            }
        }
        return metricsMap;
    }

    /**
     * Construct the REST URL for the Ehcache host
     * @return	The Ehcache host REST URL string
     */
    private String constructURL() {
        return new StringBuilder().append("http://").append(host).append(":").append(port).append("/ehcache/rest").toString();
    }
}

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.HashMap;

public class EhcacheJMXWrapper {

    private String host;
    private String port;

    public EhcacheJMXWrapper(String host, String port) {
        this.host = host;
        this.port = port;
    }

    private CacheManager getRemoteCacheManager() throws Exception{
        CacheManager remoteCacheManager = null;
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://"
                + host + ":" + port + "/jmxrmi");
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
        ObjectName beanName = new ObjectName("net.sf.ehcache:type=CacheManager");
        remoteCacheManager = (CacheManager) mbsc.invoke(beanName, "getInstance", new Object[0], new String[0]);
        return remoteCacheManager;
    }

    public HashMap retrieveMetrics() throws Exception{
        HashMap metrics = new HashMap<String, HashMap<String, Long>>();
        CacheManager remoteCacheManager = null;
        remoteCacheManager = getRemoteCacheManager();
        String[] cacheNames = remoteCacheManager.getCacheNames();

        for (String cacheName: cacheNames) {
            Cache cache = remoteCacheManager.getCache(cacheName);
            HashMap<String, Long> cacheMetrics = EhcacheMetricUtil.convertToMap(cache.getStatistics());
            metrics.put(cacheName, cacheMetrics);
        }
        return metrics;
    }

    public String getMetricPrefix() {
        throw new UnsupportedOperationException();
    }
}

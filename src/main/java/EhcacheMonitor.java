import com.singularity.ee.agent.systemagent.api.AManagedMonitor;
import com.singularity.ee.agent.systemagent.api.TaskExecutionContext;
import com.singularity.ee.agent.systemagent.api.TaskOutput;
import com.singularity.ee.agent.systemagent.api.exception.TaskExecutionException;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.statistics.StatisticsGateway;

import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import java.util.Map;

public class EhcacheMonitor extends AManagedMonitor{

    public static void main(String[] args) {
        try {
            CacheManager cacheManager = CacheManager.newInstance();
            CacheManager cacheManager2 = CacheManager.newInstance();

            if (cacheManager.equals(cacheManager2)) {
                System.out.println("what");
            }
            cacheManager.addCache("newCache");
            Cache newCache = cacheManager.getCache("newCache");
            StatisticsGateway statisticsGateway = newCache.getStatistics();
            CacheManager.getInstance().shutdown();
            System.out.println("Hehroh");
            MBeanServerConnection x;
        }
        catch (Exception e) {
            System.out.println("Doing something stupid");
        }


    }

    @Override
    public TaskOutput execute(Map<String, String> stringStringMap, TaskExecutionContext taskExecutionContext) throws TaskExecutionException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
import com.singularity.ee.agent.systemagent.api.AManagedMonitor;
import com.singularity.ee.agent.systemagent.api.MetricWriter;
import com.singularity.ee.agent.systemagent.api.TaskExecutionContext;
import com.singularity.ee.agent.systemagent.api.TaskOutput;
import com.singularity.ee.agent.systemagent.api.exception.TaskExecutionException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EhcacheMonitor extends AManagedMonitor{

    private static final String METRIC_PREFIX = "Custom Metrics|Ehcache|CacheId|";
    private static final String HOST_PARAM = "host";
    private static final String PORT_PARAM = "port";
    private static final String DEFAULT_HOST_VALUE = "localhost";
    private static final String DEFAULT_PORT_VALUE = "8080";

    private static final Logger logger = Logger.getLogger(EhcacheMonitor.class.getSimpleName());

    public static void main(String[] args) throws Exception{
        logger.setLevel(Level.DEBUG);
        EhcacheMonitor ehcacheMonitor = new EhcacheMonitor();
        Map<String,String> taskArguments = new HashMap<String, String>();
        taskArguments.put(HOST_PARAM, DEFAULT_HOST_VALUE);
        taskArguments.put(PORT_PARAM, DEFAULT_PORT_VALUE);
        ehcacheMonitor.execute(taskArguments, null);
    }

    @Override
    public TaskOutput execute(Map<String, String> taskArguments, TaskExecutionContext taskExecutionContext) throws TaskExecutionException {
        logger.debug("Exceuting EhcacheMonitor...");
        try {
            EhcacheRESTWrapper ehcacheRESTWrapper = new EhcacheRESTWrapper(taskArguments.get("host"), taskArguments.get("port"));

            HashMap metrics = ehcacheRESTWrapper.gatherMetrics();
            logger.debug("Gathered metrics successfully");
            printMetrics(metrics);
            logger.debug("Printed metrics successfully");
            return new TaskOutput("Task successful...");
        } catch (Exception e) {
          logger.error("Error: ", e);
          return new TaskOutput("Task failed with error : " + e.getMessage());
        }
    }

    private void printMetrics(HashMap metricsMap) throws Exception{
        HashMap<String, HashMap<String, Number>> metrics = (HashMap<String,HashMap<String,Number>>) metricsMap;
        Iterator outerIterator = metrics.keySet().iterator();

        while (outerIterator.hasNext()) {
            String id = outerIterator.next().toString();
            HashMap<String, Number> cacheStatistics = metrics.get(id);
            Iterator innerIterator = cacheStatistics.keySet().iterator();
            while (innerIterator.hasNext()) {
                String metricName = innerIterator.next().toString();
                Number metric = cacheStatistics.get(metricName);
                printMetric(id + "|" + metricName, metric,
                        MetricWriter.METRIC_AGGREGATION_TYPE_OBSERVATION,
                        MetricWriter.METRIC_TIME_ROLLUP_TYPE_AVERAGE,
                        MetricWriter.METRIC_CLUSTER_ROLLUP_TYPE_INDIVIDUAL);
            }
        }
    }
    /**
     * Returns the metric to the AppDynamics Controller.
     * @param 	metricName		Name of the Metric
     * @param 	metricValue		Value of the Metric
     * @param 	aggregation		Average OR Observation OR Sum
     * @param 	timeRollup		Average OR Current OR Sum
     * @param 	cluster			Collective OR Individual
     */
    private void printMetric(String metricName, Number metricValue, String aggregation, String timeRollup, String cluster) throws Exception
    {
        MetricWriter metricWriter = super.getMetricWriter(METRIC_PREFIX + metricName,
                aggregation,
                timeRollup,
                cluster
        );
        metricWriter.printMetric(String.valueOf((long) metricValue.doubleValue()));
    }
}
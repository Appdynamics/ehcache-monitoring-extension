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

import com.singularity.ee.agent.systemagent.api.AManagedMonitor;
import com.singularity.ee.agent.systemagent.api.MetricWriter;
import com.singularity.ee.agent.systemagent.api.TaskExecutionContext;
import com.singularity.ee.agent.systemagent.api.TaskOutput;
import com.singularity.ee.agent.systemagent.api.exception.TaskExecutionException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.net.MalformedURLException;
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

    /**
     * Main execution method that uploads the metrics to the AppDynamics Controller
     * @see com.singularity.ee.agent.systemagent.api.ITask#execute(java.util.Map, com.singularity.ee.agent.systemagent.api.TaskExecutionContext)
     */
    public TaskOutput execute(Map<String, String> taskArguments, TaskExecutionContext taskExecutionContext) throws TaskExecutionException {
        logger.info("Exceuting EhcacheMonitor...");
        try {
            EhcacheRESTWrapper ehcacheRESTWrapper = new EhcacheRESTWrapper(taskArguments.get("host"), taskArguments.get("port"));
            HashMap metrics = ehcacheRESTWrapper.gatherMetrics();
            logger.info("Gathered metrics successfully. Size of metrics: " + metrics.size());
            printMetrics(metrics);
            logger.info("Printed metrics successfully");
            return new TaskOutput("Task successful...");
        } catch(MalformedURLException e) {
            logger.error("Check the url for the host", e);
        } catch(ParserConfigurationException e) {
            logger.error("DocumentBuilderFactory likely couldn't create an instance of DocumentBuilder", e);
        } catch(SAXException e) {
            logger.error("DocumentBuilder failed to parse the inputstream", e);
        } catch(DOMException e) {
            logger.error("Failed to get the node text content from the XML response. ", e);
        } catch (Exception e) {
            logger.error("Exception: ", e);
        }
        return new TaskOutput("Task failed with errors");
    }

    /**
     * Writes the cache metrics to the controller
     * @param 	metricsMap		HashMap containing all the cache metrics
     */
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

    /**
     * Writes the cache metrics to the log file
     * @param 	metricsMap		HashMap containing all the cache metrics
     */
    private void printMetricsDebugMode(HashMap metricsMap) {
        HashMap<String, HashMap<String, Number>> metrics = (HashMap<String,HashMap<String,Number>>) metricsMap;
        Iterator outerIterator = metrics.keySet().iterator();

        while (outerIterator.hasNext()) {
            String cacheId = outerIterator.next().toString();
            logger.debug("CacheID: " + cacheId);
            HashMap<String, Number> cacheStatistics = metrics.get(cacheId);
            Iterator innerIterator = cacheStatistics.keySet().iterator();
            while (innerIterator.hasNext()) {
                String metricName = innerIterator.next().toString();
                Number metric = cacheStatistics.get(metricName);
                logger.debug("  MetricName: " + metricName + "  Value: " + metric);
            }
        }
    }
}
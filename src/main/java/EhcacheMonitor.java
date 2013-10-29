import com.singularity.ee.agent.systemagent.api.AManagedMonitor;
import com.singularity.ee.agent.systemagent.api.TaskExecutionContext;
import com.singularity.ee.agent.systemagent.api.TaskOutput;
import com.singularity.ee.agent.systemagent.api.exception.TaskExecutionException;
import java.util.Map;

public class EhcacheMonitor extends AManagedMonitor{

    public static void main(String[] args) {
        try {

        }
        catch (Exception e) {
            System.out.println("Doing something stupid");
        }
    }

    @Override
    public TaskOutput execute(Map<String, String> stringStringMap, TaskExecutionContext taskExecutionContext) throws TaskExecutionException {
        try {

            return new TaskOutput("Task completed successfully...");
        } catch(Exception e) {
            // Print error;
            return new TaskOutput("Task failed with this error: " + e.getMessage());
        }
    }

    public void printMetrics() {

    }
}
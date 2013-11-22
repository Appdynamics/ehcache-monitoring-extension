Ehcache Monitoring Extension
============================

This extension works only with the standalone machine agent.

## Use Case

Ehcache is an open source, standards-based cache for boosting performance, offloading your database, and simplifying scalability. The ehcache-monitoring extension gathers metrics for a specific ehcache server and sends them to the AppDynamics Metric Browser.

## Installation
<ol>
	<li>Type 'ant package' in the command line from the ehcache-monitoring-extension directory.
	</li>
	<li>Deploy the file EhcacheMonitor.zip found in the 'dist' directory into the &lt;machineagent install dir&gt;/monitors/ directory.
	</li>
	<li>Unzip the deployed file.
	</li>
	<li>Open &lt;machineagent install dir&gt;/monitors/EhcacheMonitor/monitor.xml and configure the Ehcache parameters.
<p></p>
<pre>
	&lt;argument name="host" is-required="true" default-value="localhost" /&gt;          
	&lt;argument name="port" is-required="true" default-value="8080" /&gt;
</pre>
	</li>	
	<li> Restart the machine agent.
	</li>
	<li>In the AppDynamics Metric Browser, look for: Application Infrastructure Performance | &lt;Tier&gt; | Custom Metrics | Ehcache
	</li>
</ol>

## Directory Structure

| Directory/File | Description |
|----------------|-------------|
|conf            | Contains the monitor.xml |
|lib             | Contains third-party project references |
|src             | Contains source code of the Ehcache monitoring extension |
|dist            | Only obtained when using ant. Run 'ant build' to get binaries. Run 'ant package' to get the distributable .zip file |
|build.xml       | Ant build script to package the project (required only if changing Java code) |

## Metrics

|Metric Name           | Description     |
|----------------------|-----------------|
|averageGetTime    	   | The average time to retrieve the requested item from the cache |
|cacheHits             | The number of times a requested item was found in the cache |
|diskStoreSize         | The size of the disk store |
|evictionCount         | The number of cache evictions, since the cache was created, or statistics were cleared |
|inMemoryHits          | Number of times a requested item was found in the memory store |
|memoryStoreSize       | The size of the memory store |
|misses                | Number of times a requested item was not found in the cache |
|onDiskHits            | Number of kepspace misses per minute |
|size                  | Size of the cache |

## Custom Dashboard

![](https://raw.github.com/Appdynamics/ehcache-monitoring-extension/master/Ehcache%20Dashboard.png)

##Contributing

Always feel free to fork and contribute any changes directly via [GitHub](https://github.com/Appdynamics/ehcache-monitoring-extension).

##Community

Find out more in the [AppSphere](http://appsphere.appdynamics.com) community.

##Support

For any questions or feature request, please contact [AppDynamics Center of Excellence](mailto:ace-request@appdynamics.com).

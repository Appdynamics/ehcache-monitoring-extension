Ehcache Monitoring Extension
============================

This eXtension works only with the Java agent.

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
	<li>Open &lt;machineagent install dir&gt;/monitors/EhcacheMonitor/conf/monitor.xml and configure the Ehcache parameters.
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





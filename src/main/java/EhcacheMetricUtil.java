import net.sf.ehcache.statistics.StatisticsGateway;

import java.util.HashMap;

public class EhcacheMetricUtil {

    public static HashMap<String, Long> convertToMap(StatisticsGateway cacheStatistics) {
        HashMap<String, Long> cacheMetrics = new HashMap<String, Long>();

        cacheMetrics.put("remoteSize", cacheStatistics.getRemoteSize());
        cacheMetrics.put("size", cacheStatistics.getSize());
        cacheMetrics.put("writerQueueLength", cacheStatistics.getWriterQueueLength());

        cacheMetrics.put("cacheEvictedCount", cacheStatistics.cacheEvictedCount());
        cacheMetrics.put("cacheExpiredCont", cacheStatistics.cacheExpiredCount());
        cacheMetrics.put("cacheHitCount", cacheStatistics.cacheHitCount());
        cacheMetrics.put("cacheMissCount", cacheStatistics.cacheMissCount());
        cacheMetrics.put("cacheMissExpiredCount", cacheStatistics.cacheMissExpiredCount());
        cacheMetrics.put("cacheMissNotFoundCount", cacheStatistics.cacheMissNotFoundCount());
        cacheMetrics.put("cachePutAddedCount", cacheStatistics.cachePutAddedCount());
        cacheMetrics.put("cachePutCount", cacheStatistics.cachePutCount());
        cacheMetrics.put("cachePutUpdatedCount", cacheStatistics.cachePutUpdatedCount());
        cacheMetrics.put("cacheRemoveCount", cacheStatistics.cacheRemoveCount());

        cacheMetrics.put("localDiskSize (Bytes)", cacheStatistics.getLocalDiskSizeInBytes());
        cacheMetrics.put("localDiskHitCount", cacheStatistics.localDiskHitCount());
        cacheMetrics.put("localDiskMissCount", cacheStatistics.localDiskMissCount());
        cacheMetrics.put("localDiskPutAddedCount", cacheStatistics.localDiskPutAddedCount());
        cacheMetrics.put("localDiskPutCount", cacheStatistics.localDiskPutCount());
        cacheMetrics.put("localDiskPutUpdatedCount", cacheStatistics.localDiskPutUpdatedCount());
        cacheMetrics.put("localDiskRemoveCount", cacheStatistics.localDiskRemoveCount());

        cacheMetrics.put("localHeapSize (Bytes)", cacheStatistics.getLocalHeapSizeInBytes());
        cacheMetrics.put("localHeapHitCount", cacheStatistics.localHeapHitCount());
        cacheMetrics.put("localHeapMissCount", cacheStatistics.localHeapMissCount());
        cacheMetrics.put("localHeapPutAddedCount", cacheStatistics.localHeapPutAddedCount());
        cacheMetrics.put("localHeapPutCount", cacheStatistics.localHeapPutCount());
        cacheMetrics.put("localHeapPutUpdatedCount", cacheStatistics.localHeapPutUpdatedCount());
        cacheMetrics.put("localHeapRemoveCount", cacheStatistics.localHeapRemoveCount());

        cacheMetrics.put("localOffHeapSize (Bytes)", cacheStatistics.getLocalOffHeapSizeInBytes());
        cacheMetrics.put("localOffHeapHitCount", cacheStatistics.localOffHeapHitCount());
        cacheMetrics.put("localOffHeapMissCount", cacheStatistics.localOffHeapMissCount());
        cacheMetrics.put("localOffHeapPutAddedCount", cacheStatistics.localOffHeapPutAddedCount());
        cacheMetrics.put("localOffHeapPutCount", cacheStatistics.localOffHeapPutCount());
        cacheMetrics.put("localOffHeapPutUpdatedCount", cacheStatistics.localOffHeapPutUpdatedCount());
        cacheMetrics.put("localOffHeapRemoveCount", cacheStatistics.localOffHeapRemoveCount());

        cacheMetrics.put("xaCommitCommittedCount", cacheStatistics.xaCommitCommittedCount());
        cacheMetrics.put("xaCommitCount", cacheStatistics.xaCommitCount());
        cacheMetrics.put("xaCommitExceptionCount", cacheStatistics.xaCommitExceptionCount());
        cacheMetrics.put("xaCommitReadOnlyCount", cacheStatistics.xaCommitReadOnlyCount());
        cacheMetrics.put("xaRecoveryCount", cacheStatistics.xaRecoveryCount());
        cacheMetrics.put("xaRecoveryNothingCount", cacheStatistics.xaRecoveryNothingCount());
        cacheMetrics.put("xaRecoveryRecoveredCount", cacheStatistics.xaRecoveryRecoveredCount());
        cacheMetrics.put("xaRollBackCount", cacheStatistics.xaRollbackCount());
        cacheMetrics.put("xaRollBackExceptionCount", cacheStatistics.xaRollbackExceptionCount());
        cacheMetrics.put("xaRollBackSuccessCount", cacheStatistics.xaRollbackSuccessCount());

        return cacheMetrics;
    }
}

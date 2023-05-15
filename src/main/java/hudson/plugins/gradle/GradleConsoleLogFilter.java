package hudson.plugins.gradle;

import hudson.console.ConsoleLogFilter;
import hudson.model.Run;
import hudson.plugins.gradle.enriched.EnrichedSummaryConfig;
import hudson.plugins.gradle.enriched.ScanDetailService;
import hudson.plugins.gradle.util.RunUtil;

import java.io.OutputStream;
import java.io.Serializable;

public class GradleConsoleLogFilter extends ConsoleLogFilter implements Serializable {

    @Override
    public OutputStream decorateLogger(Run build, OutputStream logger) {
        ScanDetailService scanDetailService = new ScanDetailService(EnrichedSummaryConfig.get());
        DefaultBuildScanPublishedListener buildScanListener = new DefaultBuildScanPublishedListener(build, scanDetailService);

        return new GradleConsoleAnnotator(
            logger,
            build.getCharset(),
            RunUtil.isFreestyleBuildWithGradle(build),
            buildScanListener
        );
    }
}

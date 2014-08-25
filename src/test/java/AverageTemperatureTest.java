import junit.framework.Assert;
import org.apache.pig.backend.executionengine.ExecJob;
import org.junit.Test;
import org.springframework.data.hadoop.fs.FsShell;
import org.springframework.data.hadoop.pig.PigRunner;
import org.springframework.data.hadoop.test.context.HadoopDelegatingSmartContextLoader;
import org.springframework.data.hadoop.test.context.MiniHadoopCluster;
import org.springframework.data.hadoop.test.junit.AbstractHadoopClusterTests;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.net.URL;
import java.util.List;

@ContextConfiguration(loader=HadoopDelegatingSmartContextLoader.class, locations = "/averageTemperatureTestContext.xml")
@MiniHadoopCluster
public class AverageTemperatureTest extends AbstractHadoopClusterTests{

    @Test
    public void testAverageTemperature() throws Exception {

        URL inputFileUrl = this.getClass().getResource("199607hourly_small.txt");
        File inputFile = new File(inputFileUrl.getPath());

        FsShell fsShell = new FsShell(getConfiguration());
        fsShell.mkdir("/weather/in/");
        fsShell.copyFromLocal(inputFile.getAbsolutePath(),"/weather/in/");
        PigRunner pigRunner = (PigRunner) getApplicationContext().getBean("pigRunner");
        List<ExecJob> jobs = pigRunner.call();

        Assert.assertEquals(jobs.get(0).getStatus(), ExecJob.JOB_STATUS.COMPLETED);


    }
}

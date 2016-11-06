package org.xored.edu.memorise;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemoCrawlerJob extends QuartzJobBean{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("MemoCrawlerJob is running");
        //TODO: implement job
    }
}


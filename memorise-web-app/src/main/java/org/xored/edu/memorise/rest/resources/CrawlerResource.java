package org.xored.edu.memorise.rest.resources;

import org.slf4j.Logger;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/crawler")
public class CrawlerResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String crawlerJobDetailName = "MemoCrawlerJobDetail";

    @Autowired
    private SchedulerFactoryBean schedulerFactory;

    @POST
    @Path("/run")
	@Produces(MediaType.APPLICATION_JSON)
    public void runCrawler() throws SchedulerException {
        this.logger.info("runCrawler()");

        Scheduler scheduler = schedulerFactory.getScheduler();

        for (String groupName : scheduler.getJobGroupNames())
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName)))
                if(crawlerJobDetailName.equals(jobKey.getName()))
                    scheduler.triggerJob(jobKey);
    }
}
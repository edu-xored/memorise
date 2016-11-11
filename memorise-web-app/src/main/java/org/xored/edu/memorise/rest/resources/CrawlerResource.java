package org.xored.edu.memorise.rest.resources;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Component
@Path("/crawler")
public class CrawlerResource {
    private final String crawlerJobDetailName = "MemoCrawlerJobDetail";

    @Autowired
    private SchedulerFactoryBean schedulerFactory;

    @POST
    @Path("/run")
//	@Produces(MediaType.APPLICATION_JSON)
    public void runCrawler() throws SchedulerException {
        Scheduler scheduler = schedulerFactory.getScheduler();

        for (String groupName : scheduler.getJobGroupNames())
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName)))
                if(crawlerJobDetailName.equals(jobKey.getName()))
                    scheduler.triggerJob(jobKey);
    }
}
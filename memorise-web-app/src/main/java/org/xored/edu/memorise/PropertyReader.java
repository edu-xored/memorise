package org.xored.edu.memorise;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PropertyReader {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${quartz.time}")
	private String quartzTriggerTime;

	@Autowired
	private CronTriggerFactoryBean oldTrigger;

	@Autowired
	private SchedulerFactoryBean scheduler;

	@PostConstruct
	public void setupQuartz() {
		logger.info("resetup quartz trigger with cron expression: " + quartzTriggerTime);

		TriggerBuilder<CronTrigger> triggerBuilder = oldTrigger.getObject().getTriggerBuilder();
		Trigger newTrigger = triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(quartzTriggerTime)).build();

		try {
			scheduler.getObject().rescheduleJob(oldTrigger.getObject().getKey(), newTrigger);
		} catch (Exception SchedulerException) {
			logger.info("ERROR: bad cron expression (quartz.time) in application properties");
		}
	}

}

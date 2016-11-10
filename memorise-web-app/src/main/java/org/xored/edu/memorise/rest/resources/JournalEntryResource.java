package org.xored.edu.memorise.rest.resources;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.xored.edu.memorise.JsonViews;
import org.xored.edu.memorise.api.journal.Memo;
import org.xored.edu.memorise.impl.journal.JournalEntryDao;
import org.xored.edu.memorise.api.user.Role;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;


@Component
@Path("/journal")
public class JournalEntryResource {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JournalEntryDao journalEntryDao;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private SchedulerFactoryBean schedulerFactory;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String list() throws JsonGenerationException, JsonMappingException, IOException {
		this.logger.info("list()");

		ObjectWriter viewWriter;
		if (this.isPublisher()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Publisher.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		List<Memo> allEntries = this.journalEntryDao.findAll();

		return viewWriter.writeValueAsString(allEntries);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Memo read(@PathParam("id") Long id) {
		this.logger.info("read(id)");

		Memo journalsEntry = this.journalEntryDao.find(id);
		if (journalsEntry == null) {
			throw new WebApplicationException(404);
		}
		return journalsEntry;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Memo create(Memo journalsEntry) {
		this.logger.info("create(): " + journalsEntry);

		return this.journalEntryDao.save(journalsEntry);
	}

	@GET
//	@Produces(MediaType.APPLICATION_JSON)
	public void runCrawler() throws SchedulerException{
		Scheduler scheduler = schedulerFactory.getScheduler();

		for (String groupName : scheduler.getJobGroupNames())
			for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName)))
				if(jobKey.getName().equals("MemoCrawlerJobDetail"))
					scheduler.triggerJob(jobKey);
	}


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Memo update(@PathParam("id") Long id, Memo journalsEntry) {
		this.logger.info("update(): " + journalsEntry);

		return this.journalEntryDao.save(journalsEntry);
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public void delete(@PathParam("id") Long id) {
		this.logger.info("delete(id)");

		this.journalEntryDao.delete(id);
	}

	private boolean isPublisher() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if ((principal instanceof String) && ((String) principal).equals("anonymousUser")) {
			return false;
		}
		UserDetails userDetails = (UserDetails) principal;

		return userDetails.getAuthorities().contains(Role.PUBLISHER);
	}

}
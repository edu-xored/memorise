package org.xored.edu.memorise.rest.resources;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.xored.edu.memorise.JsonViews;
import org.xored.edu.memorise.api.memo.Memo;
import org.xored.edu.memorise.api.user.Role;
import org.xored.edu.memorise.impl.memo.MemoEntryDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;


@Component
@Path("/memo")
public class MemoEntryResource {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private MemoEntryDao memoEntryDao;

	private ObjectMapper mapper;

	@Cacheable("memos")
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
		List<Memo> allEntries = this.memoEntryDao.findAll();

		return viewWriter.writeValueAsString(allEntries);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Memo read(@PathParam("id") Long id) {
		this.logger.info("read(id)");

		Memo journalsEntry = this.memoEntryDao.find(id);
		if (journalsEntry == null) {
			throw new WebApplicationException(404);
		}
		return journalsEntry;
	}

	@CacheEvict(cacheNames="memos", allEntries=true)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Memo create(Memo journalsEntry) {
		this.logger.info("create(): " + journalsEntry);

		return this.memoEntryDao.save(journalsEntry);
	}

	@CacheEvict(cacheNames="memos", allEntries=true)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Memo update(@PathParam("id") Long id, Memo journalsEntry) {
		this.logger.info("update(): " + journalsEntry);

		return this.memoEntryDao.save(journalsEntry);
	}

	@CacheEvict(cacheNames="memos", allEntries=true)
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public void delete(@PathParam("id") Long id) {
		this.logger.info("delete(id)");

		this.memoEntryDao.delete(id);
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

	@Autowired
	public void setMapper(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Autowired
	public void setMemoEntryDao(MemoEntryDao memoEntryDao) {
		this.memoEntryDao = memoEntryDao;
	}
}
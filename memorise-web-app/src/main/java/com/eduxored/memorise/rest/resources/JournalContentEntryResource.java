package com.eduxored.memorise.rest.resources;

import com.eduxored.memorise.api.journal.Journal;
import com.eduxored.memorise.impl.journal.JournalEntryDao;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;


@Component
@Path("/file/{journalId}")
public class JournalContentEntryResource {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JournalEntryDao journalEntryDao;

	@Autowired
	private ObjectMapper mapper;

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
		@PathParam("journalId")long journalId,
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail) {

		Journal journal = journalEntryDao.find(journalId);
		journal.setFileName(fileDetail.getFileName());
		journalEntryDao.uploadFile(journal, uploadedInputStream);

		String output = "File " + fileDetail.getFileName() + " uploaded.";
		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("/download")
	@Produces({"application/pdf"})
	public Response getPDF(@PathParam("journalId")long journalId ) {
		Journal journal = journalEntryDao.find(journalId);
		Response.ResponseBuilder response = Response.ok(journalEntryDao.readFile(journal));
		response.header("Content-Disposition", "attachment; filename=" + journal.getFileName());
		response.header("Content-Type", "application/pdf");
		response.header("Access-Control-Expose-Headers", "x-filename");
		response.header("x-filename", journal.getFileName());
		return response.build();
	}

}
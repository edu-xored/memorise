package org.xored.edu.memorise.api.journal;

import org.xored.edu.memorise.JsonViews;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonView;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;


/**
 * JPA Annotated Pojo that represents a Memo entry.
 *
 * @author Daniil Efremov <daniil.efremov@gmail.com>
 */
@javax.persistence.Entity
@Table(name="MEMOS")
public class Memo implements org.xored.edu.memorise.core.entity.Entity {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private Date date;

	@Column
	private String title;

	@Column
	private String description;

	@Column
	private Long size;

	@Column
	private String fileName;

	@Enumerated(EnumType.STRING)
	private MemoStatus status;

	@Column (length = 20000)
  @Lob
	@Basic(fetch=FetchType.LAZY)
	@JsonIgnore
  private Blob content;

  public Memo() {
		this.date = new Date();
	}

	@JsonView(JsonViews.Publisher.class)
	public Long getId() {
		return this.id;
	}

	@JsonView(JsonViews.User.class)
	public Date getDate() {
		return this.date;
	}


	public void setDate(Date date) {
		this.date = date;
	}

	@JsonView(JsonViews.User.class)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonView(JsonViews.User.class)
	public String getDescription()
	{
		return this.description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Blob getContent() {
		return content;
	}

	public void setContent(Blob content) {
		this.content = content;
	}

	public MemoStatus getStatus() {
		return status;
	}

	public void setStatus(MemoStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return String.format("Memo[%d, %s, %s]", this.id, this.title, this.description);
	}

}
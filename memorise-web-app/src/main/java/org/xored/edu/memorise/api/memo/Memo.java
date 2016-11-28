package org.xored.edu.memorise.api.memo;

import org.xored.edu.memorise.JsonViews;
import org.codehaus.jackson.map.annotate.JsonView;

import javax.persistence.*;
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
	private Long counter;

	@Enumerated(EnumType.STRING)
	private MemoStatus status;

	public Memo() {
		this.date = new Date();
		this.counter = 0L;
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

	public MemoStatus getStatus() {
		return status;
	}

	public void setStatus(MemoStatus status) {
		this.status = status;
	}

	@JsonView(JsonViews.User.class)
	public Long getCounter() {
		return counter;
	}

	public void setCounter(Long counter) {
		this.counter = counter;
	}

	@Override
	public String toString() {
		return String.format("Memo[%d, %s, %s]", this.id, this.title, this.description);
	}
}
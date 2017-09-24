package com.future.bbetter.schedule.model;
// Generated 2017/9/24 下午 02:49:40 by Hibernate Tools 5.2.3.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.future.bbetter.member.model.Member;

/**
 * ReportSchedule generated by hbm2java
 */
@Entity
@Table(name = "report_schedule", catalog = "bbetter")
public class ReportSchedule implements java.io.Serializable {

	private long reportScheduleId;
	private Member member;
	private Schedule schedule;
	private Date createdate;
	private int isReview;
	private int reportType;

	public ReportSchedule() {
	}

	public ReportSchedule(long reportScheduleId, Member member, Schedule schedule, Date createdate, int isReview,
			int reportType) {
		this.reportScheduleId = reportScheduleId;
		this.member = member;
		this.schedule = schedule;
		this.createdate = createdate;
		this.isReview = isReview;
		this.reportType = reportType;
	}

	@Id

	@Column(name = "report_schedule_id", unique = true, nullable = false)
	public long getReportScheduleId() {
		return this.reportScheduleId;
	}

	public void setReportScheduleId(long reportScheduleId) {
		this.reportScheduleId = reportScheduleId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_schedule_id", nullable = false)
	public Schedule getSchedule() {
		return this.schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdate", nullable = false, length = 19)
	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@Column(name = "is_review", nullable = false)
	public int getIsReview() {
		return this.isReview;
	}

	public void setIsReview(int isReview) {
		this.isReview = isReview;
	}

	@Column(name = "report_type", nullable = false)
	public int getReportType() {
		return this.reportType;
	}

	public void setReportType(int reportType) {
		this.reportType = reportType;
	}

}

package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;

@Component("meetingService")
public class MeetingService {

	DatabaseConnector connector;

	public MeetingService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";
		Query query = connector.getSession().createQuery(hql);
		return query.list();
	}
	
	public Meeting findById(long id) {
		Meeting meeting = (Meeting) connector.getSession().get(Meeting.class, id);
		return meeting;
	}
	
	public void addMeeting(Meeting meeting) {
		Transaction transaction = this.connector.getSession().beginTransaction();
		connector.getSession().save(meeting);
		transaction.commit();
	}

	public void addParticipantToMeeting(Meeting meeting, Participant participant) {
		Transaction transaction = this.connector.getSession().beginTransaction();
		meeting.addParticipant(participant);
		connector.getSession().save(meeting);
		transaction.commit();
	}
	
	public Collection<Participant> getParticipantsFromMeeting(long id) {
		Meeting meeting = (Meeting) connector.getSession().get(Meeting.class, id);
		return meeting.getParticipants();
	}
	
	public void deleteMeeting(Meeting meeting) {
		Transaction transaction = this.connector.getSession().beginTransaction();
		connector.getSession().delete(meeting);;
		transaction.commit();
	}
	
	public void updateMeeting(Meeting meeting) {
		Transaction transaction = this.connector.getSession().beginTransaction();
		connector.getSession().update(meeting);;
		transaction.commit();
	}
	
	public void deleteParticipantFromMeeting(Meeting meeting, Participant participant) {
		Transaction transaction = this.connector.getSession().beginTransaction();
		meeting.removeParticipant(participant);
		connector.getSession().save(meeting);
		transaction.commit();
	}
}

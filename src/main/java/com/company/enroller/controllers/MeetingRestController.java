package com.company.enroller.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;

@RestController
@RequestMapping("/meetings")
public class MeetingRestController {

	@Autowired
	MeetingService meetingService;
	
	// BASIC VERSION //
	// GET All meetings
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getMeetings() {
		Collection<Meeting> meetings = meetingService.getAll();
		return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
	}

	// GET One meeting
	@RequestMapping(value = "/{id}", method = RequestMethod.GET) 
	public ResponseEntity<?> getMeeting (@PathVariable("id") long id) {
	    Meeting meeting = meetingService.findById(id);
	    if (meeting == null) { 
	    	return new ResponseEntity(HttpStatus.NOT_FOUND);
	    } 

	    return new ResponseEntity<Meeting>(meeting, HttpStatus.OK); 
	}
	
	// POST meeting
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> addMeeting (@RequestBody Meeting meeting) {
		if (meetingService.findById(meeting.getId()) != null) {
			return new ResponseEntity<String>("Cannot create meeting with login" + meeting.getId() + "already exists", HttpStatus.CONFLICT);
		}
		
		meetingService.addMeeting(meeting);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
	}
	
	// ADVANCED VERSION //
	// POST user to meeting
	@RequestMapping(value = "/{id}/participants", method = RequestMethod.POST)
	public ResponseEntity<?> addParticipantToMeeting (@PathVariable("id") long id, @RequestBody Participant participant) {
	    Meeting meeting = meetingService.findById(id);
	    if (meeting == null) { 
	    	return new ResponseEntity(HttpStatus.NOT_FOUND);
	    } 
	    
		meetingService.addParticipantToMeeting(meeting, participant);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
	}
	
	// GET participants from meeting
	@RequestMapping(value = "/{id}/participants", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipantsFromMeeting (@PathVariable("id") long id) {
		Collection<Participant> participants = meetingService.getParticipantsFromMeeting(id);
		if (participants == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK); 
	}
	
	// GOLD VERSION //
	// DELETE meeting
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteMeeting (@PathVariable("id") long id) {
		Meeting meeting = meetingService.findById(id);
		if (meeting == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		meetingService.deleteMeeting(meeting);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	}
	
	// UPDATE meeting
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateMeeting (@PathVariable("id") long id, @RequestBody Meeting meeting) {
		Meeting foundMeeting = meetingService.findById(id);
		if (foundMeeting == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		foundMeeting.setTitle(meeting.getTitle());
		foundMeeting.setDescription(meeting.getDescription());
		foundMeeting.setDate(meeting.getDate());
		
		meetingService.updateMeeting(foundMeeting);
		return new ResponseEntity<Meeting>(foundMeeting, HttpStatus.OK);
	}
	
	// DELETE participant from meeting
	@RequestMapping(value = "/{id}/participants", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteParticipantFromMeeting (@PathVariable("id") long id, @RequestBody Participant participant) {
		Meeting meeting= meetingService.findById(id);
		if (meeting == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		meetingService.deleteParticipantFromMeeting(meeting, participant);
		Collection<Participant> participants = meetingService.getParticipantsFromMeeting(id);
		return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
	}
	
//	// PREMIUM VERSION //
//	// SORT meetings based on title
//	@RequestMapping(value = "/sort", method = RequestMethod.GET)
//	public ResponseEntity<?> sortMeetings ()
}






package com.topic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.topic.model.TopicDetails;
import com.topic.model.TopicResponse;
import com.topic.service.TopicService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="Topic Controller")
public class TopicController {
	
	@Autowired
	private TopicService topicService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiOperation("Topic Details")
	public ResponseEntity<List<TopicResponse>> topicDetails() {
        
		List<TopicResponse> topicDetails = topicService.getTopicDetails();
		return new ResponseEntity<List<TopicResponse>>(topicDetails, HttpStatus.OK);
	}
	
	@PostMapping("/topics")
	@ApiOperation("Create Topics Structure")
	public ResponseEntity<List<TopicResponse>> createTopics() {
		return new ResponseEntity<List<TopicResponse>>(topicService.createTopics(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/linkedTopicsWithThree")
	@ApiOperation("Get Topic linked with 3 topic at least")
	public ResponseEntity<List<TopicResponse>> getlinkedTopicsWithThree() {
		return new ResponseEntity<List<TopicResponse>>(topicService.linkedTopicsWithThree(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/oneParentOneChild")
	@ApiOperation("Get Topic linked with 1 topic and 1 child")
	public ResponseEntity<List<TopicResponse>> oneParentOneChild() {
		return new ResponseEntity<List<TopicResponse>>(topicService.oneParentOneChild(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/topicsAtThirdPlace")
	@ApiOperation("Get Topic linked with 1 topic and 1 child")
	public ResponseEntity<List<TopicResponse>> topicsAtThirdPlace() {
		return new ResponseEntity<List<TopicResponse>>(topicService.topicsAtThirdPlace(), HttpStatus.OK);
	}

}

/**
 * 
 */
package com.topic.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.topic.dao.TopicDao;
import com.topic.model.TopicDetails;
import com.topic.model.TopicResponse;
import com.topic.service.TopicService;

@Service
public class TopicServiceImpl implements TopicService {
	
	@Autowired
	private TopicDao topicDao;

	public List<TopicResponse> getTopicDetails() {
		List<TopicDetails> list = topicDao.getTopicDetails();
		List<TopicResponse> topics = new ArrayList<>();
		convertToTopicResponse(list, topics);
		return topics;

	}
	
	public List<TopicResponse> createTopics() {
		List<TopicDetails> list = topicDao.createTopics();
		List<TopicResponse> topics = new ArrayList<>();
		convertToTopicResponse(list, topics);
		return topics;

	}

	@Override
	public List<TopicResponse> linkedTopicsWithThree() {
		List<TopicDetails> list = topicDao.linkedTopicsWithThree();
		List<TopicResponse> topics = new ArrayList<>();
		convertToTopicResponse(list, topics);
		return topics;
	}

	private void convertToTopicResponse(List<TopicDetails> list, List<TopicResponse> topics) {
		if(list != null) {
			list.stream().forEach(parentChild -> topics.add(new TopicResponse(parentChild.getId(), parentChild.getName())));
		}
	}

	@Override
	public List<TopicResponse> oneParentOneChild() {
		List<TopicDetails> list = topicDao.oneParentOneChild();
		List<TopicResponse> topics = new ArrayList<>();
		convertToTopicResponse(list, topics);
		return topics;
	}

	@Override
	public List<TopicResponse> topicsAtThirdPlace() {
		List<TopicDetails> list = topicDao.topicsAtThirdPlace();
		List<TopicResponse> topics = new ArrayList<>();
		list.stream().forEach(parentChild -> topics.add(new TopicResponse(parentChild.getId(), parentChild.getName())));
		return topics;
	}

}

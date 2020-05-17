/**
 * 
 */
package com.devglan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devglan.dao.TopicDao;
import com.devglan.model.TopicDetails;
import com.devglan.model.TopicParentChild;
import com.devglan.model.TopicResponse;
import com.devglan.service.TopicService;

@Service
public class TopicServiceImpl implements TopicService {
	
	@Autowired
	private TopicDao topicDao;

	public List<TopicDetails> getTopicDetails() {
		return topicDao.getTopicDetails();

	}
	
	public List<TopicDetails> createTopics() {
		return topicDao.createTopics();

	}

	@Override
	public List<TopicResponse> linkedTopicsWithThree() {
		List<TopicParentChild> list = topicDao.linkedTopicsWithThree();
		List<TopicResponse> topics = new ArrayList<>();
		list.stream().forEach(parentChild -> topics.add(new TopicResponse(parentChild.getId(), parentChild.getName())));
		return topics;
	}

	@Override
	public List<TopicResponse> oneParentOneChild() {
		List<TopicParentChild> list = topicDao.oneParentOneChild();
		List<TopicResponse> topics = new ArrayList<>();
		list.stream().forEach(parentChild -> topics.add(new TopicResponse(parentChild.getId(), parentChild.getName())));
		return topics;
	}

}

package com.topic.dao;

import java.util.List;

import com.topic.model.TopicDetails;

public interface TopicDao {
	
	List<TopicDetails> getTopicDetails();

	List<TopicDetails> createTopics();

	List<TopicDetails> linkedTopicsWithThree();

	List<TopicDetails> oneParentOneChild();

	List<TopicDetails> topicsAtThirdPlace();

}

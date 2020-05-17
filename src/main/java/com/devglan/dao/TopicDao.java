package com.devglan.dao;

import java.util.List;

import com.devglan.model.TopicDetails;
import com.devglan.model.TopicParentChild;

public interface TopicDao {
	
	List<TopicDetails> getTopicDetails();

	List<TopicDetails> createTopics();

	List<TopicParentChild> linkedTopicsWithThree();

	List<TopicParentChild> oneParentOneChild();

}

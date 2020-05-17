/**
 * 
 */
package com.topic.service;

import java.util.List;

import com.topic.model.TopicDetails;
import com.topic.model.TopicResponse;

public interface TopicService {

	List<TopicResponse> createTopics();

	List<TopicResponse> getTopicDetails();

	List<TopicResponse> linkedTopicsWithThree();

	List<TopicResponse> oneParentOneChild();

	List<TopicResponse> topicsAtThirdPlace();

}

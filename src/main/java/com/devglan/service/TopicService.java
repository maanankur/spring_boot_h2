/**
 * 
 */
package com.devglan.service;

import java.util.List;

import com.devglan.model.TopicDetails;
import com.devglan.model.TopicResponse;

public interface TopicService {

	List<TopicDetails> createTopics();

	List<TopicDetails> getTopicDetails();

	List<TopicResponse> linkedTopicsWithThree();

	List<TopicResponse> oneParentOneChild();

}

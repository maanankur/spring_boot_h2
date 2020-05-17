package com.topic.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.topic.dao.TopicDao;
import com.topic.model.TopicDetails;

@Repository
public class TopicDaoImpl implements TopicDao {

	@PersistenceContext
	private EntityManager em;
	
	@Value("${topic.parents}")
	long parents;

	public List<TopicDetails> getTopicDetails() {
		Criteria criteria = em.unwrap(Session.class).createCriteria(TopicDetails.class);
		return criteria.list();
	}

	public List<TopicDetails> createTopics() {
		Session ssn = em.unwrap(Session.class);
		Transaction tx = ssn.getTransaction();
		tx.begin();
		TopicDetails r1 = new TopicDetails("first").init();
		TopicDetails r2 = new TopicDetails("second").init();
		TopicDetails r3 = new TopicDetails("third").init();
		TopicDetails r4 = new TopicDetails("fourth").init();
		TopicDetails r5 = new TopicDetails("fifth").init();
		TopicDetails r6 = new TopicDetails("sixth").init();
		TopicDetails r7 = new TopicDetails("seventh").init();
		TopicDetails r8 = new TopicDetails("eighth").init();
		TopicDetails r9 = new TopicDetails("nine").init();
		TopicDetails r10 = new TopicDetails("ten").init();
		TopicDetails r11= new TopicDetails("eleven").init();
		r1.getReferencesTo().add(r2);
		r1.getReferencesTo().add(r3);
		r4.getReferencesTo().add(r3);
		r5.getReferencesTo().add(r3);
		r3.getReferencesTo().add(r4);
		r5.getReferencesTo().add(r4);
		r1.getReferencesTo().add(r4);
		r2.getReferencesTo().add(r5);
		r5.getReferencesTo().add(r1);
		r6.getReferencesTo().add(r4);
		r5.getReferencesTo().add(r6);
		r7.getReferencesTo().add(r8);
		r8.getReferencesTo().add(r9);
		r7.getReferencesTo().add(r10);
		r10.getReferencesTo().add(r11);

		ssn.persist(r1);
		ssn.persist(r2);
		ssn.persist(r3);
		ssn.persist(r4);
		ssn.persist(r5);
		ssn.persist(r6);
		ssn.persist(r7);
		ssn.persist(r8);
		ssn.persist(r9);
		ssn.persist(r10);
		ssn.persist(r11);
		tx.commit();
		List<TopicDetails> topics = Arrays.asList(r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11);
		return topics;
	}

	@Override
	public List<TopicDetails> linkedTopicsWithThree() {
		Session session = em.unwrap(Session.class);

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		//main query
		CriteriaQuery<TopicDetails> topicQuery = criteriaBuilder.createQuery(TopicDetails.class);
		Root<TopicDetails> topic = topicQuery.from(TopicDetails.class);

		//phone count subquery
		Subquery<Long> countSubQuery = topicQuery.subquery(Long.class);

		// following two lines make 'from' clause of the subquery
		Root<TopicDetails> employee2 = countSubQuery.correlate(topic);
		Join<Object, Object> phoneNumbers = employee2.join("referencesFrom", JoinType.LEFT);
		//above two lines can be written as
		//ListJoin<Employee, String> phoneNumbers = countSubQuery.correlate(employee).join(Employee_.phoneNumbers); 

		//subquery selection
		countSubQuery.select(criteriaBuilder.count(phoneNumbers));

		//main query selection
		topicQuery.select(topic)
		.where(criteriaBuilder.greaterThan(countSubQuery, parents));

		TypedQuery<TopicDetails> typedQuery = em.createQuery(topicQuery);
		return typedQuery.getResultList();

	}

	@Override
	public List<TopicDetails> oneParentOneChild() {
		Session session = em.unwrap(Session.class);

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		//main query
		CriteriaQuery<TopicDetails> topicQuery = criteriaBuilder.createQuery(TopicDetails.class);
		Root<TopicDetails> topic = topicQuery.from(TopicDetails.class);

		//phone count subquery
		Subquery<Long> countSubQuery1 = topicQuery.subquery(Long.class);
		Subquery<Long> countSubQuery2 = topicQuery.subquery(Long.class);

		// following two lines make 'from' clause of the subquery
		Root<TopicDetails> employee2 = countSubQuery1.correlate(topic);
		Root<TopicDetails> employee3 = countSubQuery2.correlate(topic);
		
		Join<Object, Object> parents = employee2.join("referencesFrom", JoinType.LEFT);
		Join<Object, Object> childs = employee3.join("referencesTo", JoinType.LEFT);
		//above two lines can be written as
		//ListJoin<Employee, String> phoneNumbers = countSubQuery.correlate(employee).join(Employee_.phoneNumbers); 

		//subquery selection
		countSubQuery1.select(criteriaBuilder.count(parents));
		countSubQuery2.select(criteriaBuilder.count(childs));
		
		List<Predicate> predicates = new ArrayList<>();

	    //Adding predicates in case of parameter not being null
		predicates.add(criteriaBuilder.equal(countSubQuery1, 1L));
		predicates.add(criteriaBuilder.equal(countSubQuery2, 1L));

		//main query selection
		topicQuery.select(topic)
		.where(predicates.toArray(new Predicate[]{}));

		TypedQuery<TopicDetails> typedQuery = em.createQuery(topicQuery);
		return typedQuery.getResultList();
	}

	@Override
	public List<TopicDetails> topicsAtThirdPlace() {
		Session session = em.unwrap(Session.class);
		// find rootParents who don't have any parent (1st level)
		List<TopicDetails> rootTopics = fetchRootTopics(session);
		List<TopicDetails> rootChilds = new ArrayList<>();
		List<TopicDetails> thirdLevelTopics = new ArrayList<>();
		// fetch childrens of root topics (2nd level)
		if(rootTopics != null) {
			rootTopics.stream().forEach(rootTopic -> rootChilds.addAll(rootTopic.getReferencesTo()));
		}
		// fetch childrens of 2nd level topics (3rd topics)
		if(rootChilds != null) {
			rootChilds.stream().forEach(rootChild -> thirdLevelTopics.addAll(rootChild.getReferencesTo()));
		}
		return thirdLevelTopics;
	}

	private List<TopicDetails> fetchRootTopics(Session session) {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		//main query
		CriteriaQuery<TopicDetails> topicQuery = criteriaBuilder.createQuery(TopicDetails.class);
		Root<TopicDetails> topic = topicQuery.from(TopicDetails.class);

		//phone count subquery
		Subquery<Long> countSubQuery = topicQuery.subquery(Long.class);

		// following two lines make 'from' clause of the subquery
		Root<TopicDetails> employee2 = countSubQuery.correlate(topic);
		Join<Object, Object> phoneNumbers = employee2.join("referencesFrom", JoinType.LEFT);
		//above two lines can be written as
		//ListJoin<Employee, String> phoneNumbers = countSubQuery.correlate(employee).join(Employee_.phoneNumbers); 

		//subquery selection
		countSubQuery.select(criteriaBuilder.count(phoneNumbers));

		//main query selection
		topicQuery.select(topic)
		.where(criteriaBuilder.equal(countSubQuery, 0L));

		TypedQuery<TopicDetails> typedQuery = em.createQuery(topicQuery);
		return typedQuery.getResultList();
	}

}

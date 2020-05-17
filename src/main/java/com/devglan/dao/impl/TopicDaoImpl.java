package com.devglan.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.devglan.dao.TopicDao;
import com.devglan.model.TopicDetails;
import com.devglan.model.TopicParentChild;

@Repository
public class TopicDaoImpl implements TopicDao {

	@PersistenceContext
    private EntityManager em;
	
	public List<TopicDetails> getTopicDetails() {
		Criteria criteria = em.unwrap(Session.class).createCriteria(TopicDetails.class);
		return criteria.list();
	}
	
	public List<TopicDetails> createTopics() {
		Session ssn = em.unwrap(Session.class);
		Transaction tx = ssn.getTransaction();
		tx.begin();
		List<TopicDetails> topics = new ArrayList<>();
//		TopicDetails r1 = new TopicDetails("first");
//		TopicDetails r2 = new TopicDetails("second");
//		TopicDetails r3 = new TopicDetails("third");
//		TopicDetails r4 = new TopicDetails("fourth");
//		TopicDetails r5 = new TopicDetails("fifth");
//		TopicDetails r6 = new TopicDetails("sixth");
//		r1.getReferencesTo().add(r2);
//		r1.getReferencesTo().add(r3);
//		r4.getReferencesTo().add(r3);
//		r5.getReferencesTo().add(r3);
//		r3.getReferencesTo().add(r4);
//		r5.getReferencesTo().add(r4);
//		r1.getReferencesTo().add(r4);
//		r2.getReferencesTo().add(r5);
//		r5.getReferencesTo().add(r1);
//		r6.getReferencesTo().add(r4);
		
		
		TopicDetails r1 = new TopicDetails("first");
		TopicDetails r2 = new TopicDetails("second");
		TopicDetails r3 = new TopicDetails("third");
		TopicDetails r4 = new TopicDetails("fourth");
		TopicDetails r5 = new TopicDetails("fifth");
		TopicDetails r6 = new TopicDetails("sixth");
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
		
		ssn.persist(r1);
		ssn.persist(r2);
		ssn.persist(r3);
		ssn.persist(r4);
		ssn.persist(r5);
		ssn.persist(r6);
		tx.commit();
		return topics;
	}

	@Override
	public List<TopicParentChild> linkedTopicsWithThree() {
		Session session = em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(TopicDetails.class,"topic");
        criteria.createAlias("referencesFrom", "referencesFrom", Criteria.LEFT_JOIN);
        ProjectionList properties = Projections.projectionList();
        properties.add(Projections.groupProperty("topic.id").as("id"));
        properties.add(Projections.property("topic.name").as("name"));
        properties.add(Projections.count("referencesFrom.id").as("parentCount"));
        criteria.setProjection(properties);
        criteria.setResultTransformer(Transformers.aliasToBean(TopicParentChild.class));
        return criteria.list();
	}

	@Override
	public List<TopicParentChild> oneParentOneChild() {
		Session session = em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(TopicDetails.class,"topic");
        criteria.createAlias("referencesFrom", "referencesFrom", Criteria.LEFT_JOIN);
        ProjectionList properties = Projections.projectionList();
        properties.add(Projections.groupProperty("topic.id").as("id"));
        properties.add(Projections.property("topic.name").as("name"));
        properties.add(Projections.count("referencesFrom.id").as("parentCount"));
        criteria.setProjection(properties);
        criteria.add(Restrictions.eq("referencesFrom.id", 4));
        criteria.setResultTransformer(Transformers.aliasToBean(TopicParentChild.class));
        return criteria.list();
	}

}

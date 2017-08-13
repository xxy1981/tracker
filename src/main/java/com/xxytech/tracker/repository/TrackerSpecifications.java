package com.xxytech.tracker.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.xxytech.tracker.entity.Tracker;

public class TrackerSpecifications {

    public static Specification<Tracker> buildQuery(String sid, String channel, String partnerId) {
        return new Specification<Tracker>() {
            @Override
            public Predicate toPredicate(Root<Tracker> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
            	List<Predicate> list = new ArrayList<Predicate>();
            	if (StringUtils.isNotBlank(sid)) {
            		 list.add(cb.equal(root.get("sid").as(String.class), sid));
                }
            	if (StringUtils.isNotBlank(channel)) {
           		 	list.add(cb.equal(root.get("channel").as(String.class), channel));
            	}
            	if (StringUtils.isNotBlank(partnerId)) {
            		list.add(cb.equal(root.get("partnerId").as(String.class), partnerId));
            	}
                Predicate[] p = new Predicate[list.size()];  
                cq.where(cb.and(list.toArray(p)));
            	
            	return cq.getRestriction();
            }
        };
    }
}

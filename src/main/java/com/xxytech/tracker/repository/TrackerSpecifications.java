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

    public static Specification<Tracker> buildQuery(String impId, String siteId, String trackingPartner) {
        return new Specification<Tracker>() {
            @Override
            public Predicate toPredicate(Root<Tracker> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
            	List<Predicate> list = new ArrayList<Predicate>();
            	if (StringUtils.isNotBlank(impId)) {
            		 list.add(cb.equal(root.get("impId").as(String.class), impId));
                }
            	if (StringUtils.isNotBlank(siteId)) {
           		 	list.add(cb.equal(root.get("siteId").as(String.class), siteId));
            	}
            	if (StringUtils.isNotBlank(trackingPartner)) {
            		list.add(cb.equal(root.get("trackingPartner").as(String.class), trackingPartner));
            	}
                Predicate[] p = new Predicate[list.size()];  
                cq.where(cb.and(list.toArray(p)));
            	
            	return cq.getRestriction();
            }
        };
    }
}

package com.xxytech.tracker.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.xxytech.tracker.entity.Campaign;

public class CampaignSpecifications {

    public static Specification<Campaign> buildQuery(String id, String name, String partnerId) {
        return new Specification<Campaign>() {
            @Override
            public Predicate toPredicate(Root<Campaign> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
            	List<Predicate> list = new ArrayList<Predicate>();
            	if (StringUtils.isNotBlank(id)) {
            		 list.add(cb.equal(root.get("id").as(String.class), id));
                }
            	if (StringUtils.isNotBlank(name)) {
           		 	list.add(cb.equal(root.get("name").as(String.class), name));
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

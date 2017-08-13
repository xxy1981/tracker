package com.xxytech.tracker.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xxytech.tracker.entity.Partner;

public class CacheUtils {
	public static Map<String, Partner> partnerCache = new HashMap<String, Partner>();
	
	public static List<Partner> getPartners(){
		return new ArrayList<Partner>(partnerCache.values());
	}
	
	public static Partner getPartner(String partnerId){
		return partnerCache.get(partnerId);
	}
	
	public static void putPartner(Partner partner){
		partnerCache.put(partner.getId(), partner);
	}
	
	public static void removePartner(Partner partner){
		removePartner(partner.getId());
	}
	
	public static void removePartner(String partnerId){
		partnerCache.remove(partnerId);
	}
}

package com.fastcode.example.application.core.t4;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.fastcode.example.application.core.t4.dto.*;
import com.fastcode.example.domain.core.t4.IT4Repository;
import com.fastcode.example.domain.core.t4.QT4;
import com.fastcode.example.domain.core.t4.T4;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import java.time.*;
import java.util.*;

@Service("t4AppService")
@RequiredArgsConstructor
public class T4AppService implements IT4AppService {
	@Qualifier("t4Repository")
	@NonNull protected final IT4Repository _t4Repository;

	
	@Qualifier("IT4MapperImpl")
	@NonNull protected final IT4Mapper mapper;

	@NonNull protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
	public CreateT4Output create(CreateT4Input input) {

		T4 t4 = mapper.createT4InputToT4(input);

		T4 createdT4 = _t4Repository.save(t4);
		return mapper.t4ToCreateT4Output(createdT4);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public UpdateT4Output update(Integer t4Id, UpdateT4Input input) {

		T4 existing = _t4Repository.findById(t4Id).get();

		T4 t4 = mapper.updateT4InputToT4(input);
		
		T4 updatedT4 = _t4Repository.save(t4);
		return mapper.t4ToUpdateT4Output(updatedT4);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Integer t4Id) {

		T4 existing = _t4Repository.findById(t4Id).orElse(null); 
	 	
	 	_t4Repository.delete(existing);
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindT4ByIdOutput findById(Integer t4Id) {

		T4 foundT4 = _t4Repository.findById(t4Id).orElse(null);
		if (foundT4 == null)  
			return null; 
 	   
 	    return mapper.t4ToFindT4ByIdOutput(foundT4);
	}
	
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<FindT4ByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception  {

		Page<T4> foundT4 = _t4Repository.findAll(search(search), pageable);
		List<T4> t4List = foundT4.getContent();
		Iterator<T4> t4Iterator = t4List.iterator(); 
		List<FindT4ByIdOutput> output = new ArrayList<>();

		while (t4Iterator.hasNext()) {
		T4 t4 = t4Iterator.next();
 	    output.add(mapper.t4ToFindT4ByIdOutput(t4));
		}
		return output;
	}
	
	protected BooleanBuilder search(SearchCriteria search) throws Exception {

		QT4 t4= QT4.t4Entity;
		if(search != null) {
			Map<String,SearchFields> map = new HashMap<>();
			for(SearchFields fieldDetails: search.getFields())
			{
				map.put(fieldDetails.getFieldName(),fieldDetails);
			}
			List<String> keysList = new ArrayList<String>(map.keySet());
			checkProperties(keysList);
			return searchKeyValuePair(t4, map,search.getJoinColumns());
		}
		return null;
	}
	
	protected void checkProperties(List<String> list) throws Exception  {
		for (int i = 0; i < list.size(); i++) {
			if(!(
				list.get(i).replace("%20","").trim().equals("id") ||
				list.get(i).replace("%20","").trim().equals("score")
			)) 
			{
			 throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!" );
			}
		}
	}
	
	protected BooleanBuilder searchKeyValuePair(QT4 t4, Map<String,SearchFields> map,Map<String,String> joinColumns) {
		BooleanBuilder builder = new BooleanBuilder();
        
		for (Map.Entry<String, SearchFields> details : map.entrySet()) {
			if(details.getKey().replace("%20","").trim().equals("id")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(t4.id.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(t4.id.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(t4.id.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(t4.id.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(t4.id.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(t4.id.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
	    
		}
		
		return builder;
	}
	
}




package com.fastcode.example.application.core.t3;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.fastcode.example.application.core.t3.dto.*;
import com.fastcode.example.domain.core.t3.IT3Repository;
import com.fastcode.example.domain.core.t3.QT3;
import com.fastcode.example.domain.core.t3.T3;

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

@Service("t3AppService")
@RequiredArgsConstructor
public class T3AppService implements IT3AppService {
	@Qualifier("t3Repository")
	@NonNull protected final IT3Repository _t3Repository;

	
	@Qualifier("IT3MapperImpl")
	@NonNull protected final IT3Mapper mapper;

	@NonNull protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
	public CreateT3Output create(CreateT3Input input) {

		T3 t3 = mapper.createT3InputToT3(input);

		T3 createdT3 = _t3Repository.save(t3);
		return mapper.t3ToCreateT3Output(createdT3);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public UpdateT3Output update(Integer t3Id, UpdateT3Input input) {

		T3 existing = _t3Repository.findById(t3Id).get();

		T3 t3 = mapper.updateT3InputToT3(input);
		
		T3 updatedT3 = _t3Repository.save(t3);
		return mapper.t3ToUpdateT3Output(updatedT3);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Integer t3Id) {

		T3 existing = _t3Repository.findById(t3Id).orElse(null); 
	 	
	 	_t3Repository.delete(existing);
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindT3ByIdOutput findById(Integer t3Id) {

		T3 foundT3 = _t3Repository.findById(t3Id).orElse(null);
		if (foundT3 == null)  
			return null; 
 	   
 	    return mapper.t3ToFindT3ByIdOutput(foundT3);
	}
	
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<FindT3ByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception  {

		Page<T3> foundT3 = _t3Repository.findAll(search(search), pageable);
		List<T3> t3List = foundT3.getContent();
		Iterator<T3> t3Iterator = t3List.iterator(); 
		List<FindT3ByIdOutput> output = new ArrayList<>();

		while (t3Iterator.hasNext()) {
		T3 t3 = t3Iterator.next();
 	    output.add(mapper.t3ToFindT3ByIdOutput(t3));
		}
		return output;
	}
	
	protected BooleanBuilder search(SearchCriteria search) throws Exception {

		QT3 t3= QT3.t3Entity;
		if(search != null) {
			Map<String,SearchFields> map = new HashMap<>();
			for(SearchFields fieldDetails: search.getFields())
			{
				map.put(fieldDetails.getFieldName(),fieldDetails);
			}
			List<String> keysList = new ArrayList<String>(map.keySet());
			checkProperties(keysList);
			return searchKeyValuePair(t3, map,search.getJoinColumns());
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
	
	protected BooleanBuilder searchKeyValuePair(QT3 t3, Map<String,SearchFields> map,Map<String,String> joinColumns) {
		BooleanBuilder builder = new BooleanBuilder();
        
		for (Map.Entry<String, SearchFields> details : map.entrySet()) {
			if(details.getKey().replace("%20","").trim().equals("id")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(t3.id.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(t3.id.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(t3.id.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(t3.id.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(t3.id.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(t3.id.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
	    
		}
		
		return builder;
	}
	
}




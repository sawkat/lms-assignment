package com.assignment.lms.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * This is will convert DTO to model and model DTO
 * 
 * @author 703177676
 *
 */
@Component
public class ObjectMapper {

	@Autowired
    private ModelMapper modelMapper;
	
	public <S, T> T map(S input, Class<T> targetClass){
		return modelMapper.map(input, targetClass);
	}
	
	
	public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
	    return source
	      .parallelStream()
	      .map(element -> modelMapper.map(element, targetClass))
	      .collect(Collectors.toList());
	}
}

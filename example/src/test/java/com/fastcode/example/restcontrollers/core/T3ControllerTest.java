package com.fastcode.example.restcontrollers.core;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;
import java.time.*;
import java.math.BigDecimal;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import org.springframework.core.env.Environment;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.application.core.t3.T3AppService;
import com.fastcode.example.application.core.t3.dto.*;
import com.fastcode.example.domain.core.t3.IT3Repository;
import com.fastcode.example.domain.core.t3.T3;

import com.fastcode.example.DatabaseContainerConfig;
import com.fastcode.example.domain.core.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
				properties = "spring.profiles.active=test")
public class T3ControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("t3Repository") 
	protected IT3Repository t3_repository;
	

	@SpyBean
	@Qualifier("t3AppService")
	protected T3AppService t3AppService;
	
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected T3 t3;

	protected MockMvc mvc;
	
	@Autowired
	EntityManagerFactory emf;
	
    static EntityManagerFactory emfs;
    
    static int relationCount = 10;
    static int yearCount = 1971;
    static int dayCount = 10;
	private BigDecimal bigdec = new BigDecimal(1.2);
    
	@PostConstruct
	public void init() {
	emfs = emf;
	}

	@AfterClass
	public static void cleanup() {
		EntityManager em = emfs.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("truncate table s2.t3 CASCADE").executeUpdate();
		em.getTransaction().commit();
	}
	

	public T3 createEntity() {
	
		T3 t3Entity = new T3();
		t3Entity.setId(1);
		t3Entity.setVersiono(0L);
		
		return t3Entity;
	}
    public CreateT3Input createT3Input() {
	
	    CreateT3Input t3Input = new CreateT3Input();
		
		return t3Input;
	}

	public T3 createNewEntity() {
		T3 t3 = new T3();
		t3.setId(3);
		
		return t3;
	}
	
	public T3 createUpdateEntity() {
		T3 t3 = new T3();
		t3.setId(4);
		
		return t3;
	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final T3Controller t3Controller = new T3Controller(t3AppService,
	logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(t3Controller)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		t3= createEntity();
		List<T3> list= t3_repository.findAll();
		if(!list.contains(t3)) {
			t3=t3_repository.save(t3);
		}

	}
	
	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/t3/" + t3.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() {

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/t3/999")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));

	}
	@Test
	public void CreateT3_T3DoesNotExist_ReturnStatusOk() throws Exception {
		CreateT3Input t3Input = createT3Input();	
		
		
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(t3Input);

		mvc.perform(post("/t3").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}     

	@Test
	public void DeleteT3_IdIsNotValid_ThrowEntityNotFoundException() {

        doReturn(null).when(t3AppService).findById(999);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/t3/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a t3 with a id=999"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 	T3 entity =  createNewEntity();
	 	entity.setVersiono(0L);
		entity = t3_repository.save(entity);
		

		FindT3ByIdOutput output= new FindT3ByIdOutput();
		output.setId(entity.getId());
		
         Mockito.doReturn(output).when(t3AppService).findById(entity.getId());
       
    //    Mockito.when(t3AppService.findById(entity.getId())).thenReturn(output);
        
		mvc.perform(delete("/t3/" + entity.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateT3_T3DoesNotExist_ReturnStatusNotFound() throws Exception {
   
        doReturn(null).when(t3AppService).findById(999);
        
        UpdateT3Input t3 = new UpdateT3Input();
		t3.setId(999);

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(t3);

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(
		 	put("/t3/999")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content(json))
			.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Unable to update. T3 with id=999 not found."));
	}    

	@Test
	public void UpdateT3_T3Exists_ReturnStatusOk() throws Exception {
		T3 entity =  createUpdateEntity();
		entity.setVersiono(0L);
		
		entity = t3_repository.save(entity);
		FindT3ByIdOutput output= new FindT3ByIdOutput();
		output.setId(entity.getId());
		output.setScore(entity.getScore());
		output.setVersiono(entity.getVersiono());
		
        Mockito.when(t3AppService.findById(entity.getId())).thenReturn(output);
        
        
		
		UpdateT3Input t3Input = new UpdateT3Input();
		t3Input.setId(entity.getId());
		

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(t3Input);
	
		mvc.perform(put("/t3/" + entity.getId()+"/").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		T3 de = createUpdateEntity();
		de.setId(entity.getId());
		t3_repository.delete(de);
		

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/t3?search=id[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}    

	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsNotValid_ThrowException() throws Exception {

		org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/t3?search=t3id[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new Exception("Wrong URL Format: Property t3id not found!"));

	} 
		
    
}


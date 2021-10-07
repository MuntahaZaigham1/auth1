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
import com.fastcode.example.application.core.t4.T4AppService;
import com.fastcode.example.application.core.t4.dto.*;
import com.fastcode.example.domain.core.t4.IT4Repository;
import com.fastcode.example.domain.core.t4.T4;

import com.fastcode.example.DatabaseContainerConfig;
import com.fastcode.example.domain.core.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
				properties = "spring.profiles.active=test")
public class T4ControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("t4Repository") 
	protected IT4Repository t4_repository;
	

	@SpyBean
	@Qualifier("t4AppService")
	protected T4AppService t4AppService;
	
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected T4 t4;

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
		em.createNativeQuery("truncate table s2.t4 CASCADE").executeUpdate();
		em.getTransaction().commit();
	}
	

	public T4 createEntity() {
	
		T4 t4Entity = new T4();
		t4Entity.setId(1);
		t4Entity.setVersiono(0L);
		
		return t4Entity;
	}
    public CreateT4Input createT4Input() {
	
	    CreateT4Input t4Input = new CreateT4Input();
		
		return t4Input;
	}

	public T4 createNewEntity() {
		T4 t4 = new T4();
		t4.setId(3);
		
		return t4;
	}
	
	public T4 createUpdateEntity() {
		T4 t4 = new T4();
		t4.setId(4);
		
		return t4;
	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final T4Controller t4Controller = new T4Controller(t4AppService,
	logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(t4Controller)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		t4= createEntity();
		List<T4> list= t4_repository.findAll();
		if(!list.contains(t4)) {
			t4=t4_repository.save(t4);
		}

	}
	
	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/t4/" + t4.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() {

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/t4/999")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));

	}
	@Test
	public void CreateT4_T4DoesNotExist_ReturnStatusOk() throws Exception {
		CreateT4Input t4Input = createT4Input();	
		
		
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(t4Input);

		mvc.perform(post("/t4").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}     

	@Test
	public void DeleteT4_IdIsNotValid_ThrowEntityNotFoundException() {

        doReturn(null).when(t4AppService).findById(999);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/t4/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a t4 with a id=999"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 	T4 entity =  createNewEntity();
	 	entity.setVersiono(0L);
		entity = t4_repository.save(entity);
		

		FindT4ByIdOutput output= new FindT4ByIdOutput();
		output.setId(entity.getId());
		
         Mockito.doReturn(output).when(t4AppService).findById(entity.getId());
       
    //    Mockito.when(t4AppService.findById(entity.getId())).thenReturn(output);
        
		mvc.perform(delete("/t4/" + entity.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateT4_T4DoesNotExist_ReturnStatusNotFound() throws Exception {
   
        doReturn(null).when(t4AppService).findById(999);
        
        UpdateT4Input t4 = new UpdateT4Input();
		t4.setId(999);

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(t4);

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(
		 	put("/t4/999")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content(json))
			.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Unable to update. T4 with id=999 not found."));
	}    

	@Test
	public void UpdateT4_T4Exists_ReturnStatusOk() throws Exception {
		T4 entity =  createUpdateEntity();
		entity.setVersiono(0L);
		
		entity = t4_repository.save(entity);
		FindT4ByIdOutput output= new FindT4ByIdOutput();
		output.setId(entity.getId());
		output.setScore(entity.getScore());
		output.setVersiono(entity.getVersiono());
		
        Mockito.when(t4AppService.findById(entity.getId())).thenReturn(output);
        
        
		
		UpdateT4Input t4Input = new UpdateT4Input();
		t4Input.setId(entity.getId());
		

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(t4Input);
	
		mvc.perform(put("/t4/" + entity.getId()+"/").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		T4 de = createUpdateEntity();
		de.setId(entity.getId());
		t4_repository.delete(de);
		

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/t4?search=id[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}    

	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsNotValid_ThrowException() throws Exception {

		org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/t4?search=t4id[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new Exception("Wrong URL Format: Property t4id not found!"));

	} 
		
    
}


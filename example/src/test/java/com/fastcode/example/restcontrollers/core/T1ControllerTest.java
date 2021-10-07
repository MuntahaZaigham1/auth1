package com.fastcode.example.restcontrollers.core;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.*;
import java.time.*;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import com.fastcode.example.commons.search.SearchUtils;
import com.fastcode.example.application.core.t1.T1AppService;
import com.fastcode.example.application.core.t1.dto.*;
import com.fastcode.example.domain.core.t1.IT1Repository;
import com.fastcode.example.domain.core.t1.T1;

import com.fastcode.example.DatabaseContainerConfig;
import com.fastcode.example.TestConstants;
import com.fastcode.example.domain.core.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
				properties = "spring.profiles.active=test")
public class T1ControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("t1Repository") 
	protected IT1Repository t1_repository;
	

	@SpyBean
	@Qualifier("t1AppService")
	protected T1AppService t1AppService;
	
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected T1 t1;

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
		em.createNativeQuery("truncate table s2.t1 CASCADE").executeUpdate();
		em.getTransaction().commit();
	}
	

	public T1 createEntity() {
	
		T1 t1Entity = new T1();
  		char[] FreeMarker template error (DEBUG mode; use RETHROW in production!):
The following has evaluated to null or missing:
==> fieldName  [in template "backendTemplates/ControllerTest.java.ftl" at line 528, column 26]

----
Tip: If the failing expression is known to legally refer to something that's sometimes null or missing, either specify a default value like myOptionalVar!myDefault, or use <#if myOptionalVar??>when-present<#else>when-missing</#if>. (These only cover the last step of the expression; to cover the whole expression, use parenthesis: (myOptionalVar.foo)!myDefault, (myOptionalVar.foo)??
----

----
FTL stack trace ("~" means nesting-related):
	- Failed at: [=fieldName?uncap_first]  [in template "backendTemplates/ControllerTest.java.ftl" at line 528, column 24]
----

Java stack trace (for programmers):
----
freemarker.core.InvalidReferenceException: [... Exception message was already printed; see it above ...]
	at freemarker.core.InvalidReferenceException.getInstance(InvalidReferenceException.java:134)
	at freemarker.core.EvalUtil.coerceModelToTextualCommon(EvalUtil.java:479)
	at freemarker.core.EvalUtil.coerceModelToStringOrUnsupportedMarkup(EvalUtil.java:434)
	at freemarker.core.Expression.evalAndCoerceToStringOrUnsupportedMarkup(Expression.java:141)
	at freemarker.core.BuiltInForString.getTargetString(BuiltInForString.java:34)
	at freemarker.core.BuiltInForString._eval(BuiltInForString.java:29)
	at freemarker.core.Expression.eval(Expression.java:101)
	at freemarker.core.DollarVariable.calculateInterpolatedStringOrMarkup(DollarVariable.java:100)
	at freemarker.core.DollarVariable.accept(DollarVariable.java:63)
	at freemarker.core.Environment.visit(Environment.java:331)
	at freemarker.core.Environment.visit(Environment.java:337)
	at freemarker.core.Environment.visit(Environment.java:373)
	at freemarker.core.IteratorBlock$IterationContext.executedNestedContentForHashListing(IteratorBlock.java:383)
	at freemarker.core.IteratorBlock$IterationContext.executeNestedContent(IteratorBlock.java:272)
	at freemarker.core.IteratorBlock$IterationContext.accept(IteratorBlock.java:244)
	at freemarker.core.Environment.visitIteratorBlock(Environment.java:643)
	at freemarker.core.IteratorBlock.acceptWithResult(IteratorBlock.java:108)
	at freemarker.core.IteratorBlock.accept(IteratorBlock.java:94)
	at freemarker.core.Environment.visit(Environment.java:331)
	at freemarker.core.Environment.visit(Environment.java:337)
	at freemarker.core.Environment.process(Environment.java:310)
	at freemarker.template.Template.process(Template.java:383)
	at com.fastcode.codegen.CodeGeneratorUtils.generateFiles(CodeGeneratorUtils.java:100)
	at com.fastcode.codegen.CodeGenerator.generateBackendUnitAndIntegrationTestFiles(CodeGenerator.java:1107)
	at com.fastcode.codegen.CodeGenerator.generate(CodeGenerator.java:777)
	at com.fastcode.codegen.CodeGenerator.generateAllModulesForEntities(CodeGenerator.java:257)
	at com.fastcode.codegen.CodeGenerator.generateAll(CodeGenerator.java:382)
	at com.fastcode.codegen.ModulesGenerator.generateAllCode(ModulesGenerator.java:334)
	at com.fastcode.application.apps.AppsAppService.generateCode(AppsAppService.java:1257)
	at com.fastcode.application.apps.AppsAppService$$FastClassBySpringCGLIB$$4263c30c.invoke(<generated>)
	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:750)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:120)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
	at org.springframework.aop.interceptor.AsyncExecutionInterceptor.lambda$invoke$0(AsyncExecutionInterceptor.java:115)
	at java.util.concurrent.FutureTask.run(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(Unknown Source)
	at java.lang.Thread.run(Unknown Source)

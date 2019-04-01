package com.cwj.springbootTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cwj.springbootTest.controller.HelloWorldController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWorldControlerTests {
  private MockMvc mvc;
  @Before
  public void setUp() throws Exception {
      mvc = MockMvcBuilders.standaloneSetup(new HelloWorldController()).build();
  }
  
  //验证controller是否正常响应并打印返回结果
  @Test
  public void getHello() throws Exception {
  mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
              .andExpect(MockMvcResultMatchers.status().isOk())
              .andDo(MockMvcResultHandlers.print())
              .andReturn();
  }
  
//验证controller是否正常响应并判断返回结果是否正确
  @Test
  public void testHello() throws Exception {
      mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
              .andExpect(MockMvcResultMatchers.status().isOk())
              .andExpect(MockMvcResultMatchers.content().string("hello_"));
  }
  
}

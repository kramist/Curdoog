package com.haverson.common.Controller;

import com.haverson.common.core.util.RespInfo;
import com.haverson.common.entity.User;
import com.haverson.common.service.api.BaseApi;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = com.example.curdoog.CurdoogExampleApplication.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BaseApi<User, User> baseApi;

    @BeforeEach
    public void setUp() {
        // 初始化模拟对象
        Mockito.reset(baseApi);
    }

    @Test
    public void testListUsers() throws Exception {
        // 准备测试数据
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setId(1);
        user1.setName("Alice");
        user1.setAge(30);
        user1.setEmail("alice@example.com");
        user1.setCreateTime("2023-01-01 12:00:00");

        User user2 = new User();
        user2.setId(2);
        user2.setName("Bob");
        user2.setAge(25);
        user2.setEmail("bob@example.com");
        user2.setCreateTime("2023-02-01 12:00:00");

        userList.add(user1);
        userList.add(user2);

        Page<User> page = new Page<>(1, 10);
        page.setRecords(userList);

        // 模拟服务层方法
        when(baseApi.baseSelectPage(any(Integer.class), any(Integer.class), any(User.class))).thenReturn(RespInfo.ok(page.toString()));

        // 发送请求
        MvcResult result = mockMvc.perform(post("/users/list")
                        .param("pageNum", "1")
                        .param("pageSize", "10")
                        .content("{\"name\": \"A\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // 验证结果
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testSaveOrUpdateUser() throws Exception {
        // 准备测试数据
        User user = new User();
        user.setId(1);
        user.setName("Charlie");
        user.setAge(28);
        user.setEmail("charlie@example.com");
        user.setCreateTime("2023-03-01 12:00:00");

        // 模拟服务层方法
        when(baseApi.baseSaveOrUpDate(any(User.class))).thenReturn(RespInfo.ok(user));

        // 发送请求
        MvcResult result = mockMvc.perform(post("/users/saveOrUpdate")
                        .content("{\"name\": \"Charlie\", \"age\": 28, \"email\": \"charlie@example.com\", \"createTime\": \"2023-03-01 12:00:00\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // 验证结果
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testGetUserById() throws Exception {
        // 准备测试数据
        User user = new User();
        user.setId(1);
        user.setName("Alice");
        user.setAge(30);
        user.setEmail("alice@example.com");
        user.setCreateTime("2023-01-01 12:00:00");

        // 模拟服务层方法
        when(baseApi.baseGetById(any(Integer.class))).thenReturn(RespInfo.ok(user));

        // 发送请求
        MvcResult result = mockMvc.perform(post("/users/getById")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andReturn();

        // 验证结果
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testDeleteUserById() throws Exception {
        // 模拟服务层方法
        when(baseApi.baseDeleteById(any(Integer.class))).thenReturn(RespInfo.ok());

        // 发送请求
        MvcResult result = mockMvc.perform(post("/users/deleteById")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andReturn();

        // 验证结果
        System.out.println(result.getResponse().getContentAsString());
    }
}
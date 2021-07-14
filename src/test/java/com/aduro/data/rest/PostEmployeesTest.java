package com.aduro.data.rest;

import com.aduro.data.rest.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class PostEmployeesTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void whenStartingApplication_thenCorrectStatusCode() throws Exception {
        mockMvc.perform(get("/employees")).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void whenAddingNewEmployeeWithoutName_thenErrorStatusCodeAndResponse() throws Exception {
        Employee employee = new Employee();
        employee.setEmail("john.doe@oscorp.com");
        employee.setPhone("415.111.1111");
        employee.setOffice("322F");
        employee.setRole("Sushi Monster");

        mockMvc.perform(post("/employees", employee)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(employee)))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().string("Please provide full name"))
                .andExpect(redirectedUrl(null));
    }

    @Test
    public void whenAddingNewEmployeeNameTooLarge_thenErrorStatusCodeAndResponse() throws Exception {
        Employee employee = new Employee();

        String name = String.format("%1$" + 101 + "s", "John Doe").replace(' ', '0');

        employee.setName(name);
        employee.setEmail("john.doe@oscorp.com");
        employee.setPhone("415.111.1111");
        employee.setOffice("322F");
        employee.setRole("Sushi Monster");

        mockMvc.perform(post("/employees", employee)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(employee)))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().string("Full name cannot exceed 100 characters"))
                .andExpect(redirectedUrl(null));
    }

    @Test
    public void whenAddingNewEmployeeWithoutEmail_thenErrorStatusCodeAndResponse() throws Exception {
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setPhone("415.111.1111");
        employee.setOffice("322F");
        employee.setRole("Sushi Monster");

        mockMvc.perform(post("/employees", employee)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(employee)))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().string("Please provide email address"))
                .andExpect(redirectedUrl(null));
    }

    @Test
    public void whenAddingNewEmployeeWithInvalidEmail_thenErrorStatusCodeAndResponse() throws Exception {
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setEmail("john.doedoesemail");
        employee.setPhone("415.111.1111");
        employee.setOffice("322F");
        employee.setRole("Sushi Monster");

        mockMvc.perform(post("/employees", employee)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(employee)))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().string("Must be a valid email with oscorp.com domain"))
                .andExpect(redirectedUrl(null));
    }

    @Test
    public void whenAddingNewEmployeeWithEmailAtIncorrectDomain_thenErrorStatusCodeAndResponse() throws Exception {
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setEmail("john.doe@gmail.com");
        employee.setPhone("415.111.1111");
        employee.setOffice("322F");
        employee.setRole("Sushi Monster");

        mockMvc.perform(post("/employees", employee)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(employee)))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().string("Must be a valid email with oscorp.com domain"))
                .andExpect(redirectedUrl(null));
    }

    @Test
    public void whenAddingNewEmployeeWithoutPhone_thenErrorStatusCodeAndResponse() throws Exception {
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setEmail("john.doe@oscorp.com");
        employee.setOffice("322F");
        employee.setRole("Sushi Monster");

        mockMvc.perform(post("/employees", employee)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(employee)))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().string("Please provide telephone number"))
                .andExpect(redirectedUrl(null));
    }

    @Test
    public void whenAddingNewEmployeeWitInvalidPhone_thenErrorStatusCodeAndResponse() throws Exception {
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setEmail("john.doe@oscorp.com");
        employee.setPhone("415-111-1111");
        employee.setOffice("322F");
        employee.setRole("Sushi Monster");

        mockMvc.perform(post("/employees", employee)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(employee)))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().string("Must be a valid North American phone number with area code 415, in the format 415.456.7890"))
                .andExpect(redirectedUrl(null));
    }

    @Test
    public void whenAddingNewEmployeeWithPhoneWithIncorrectAreaCode_thenErrorStatusCodeAndResponse() throws Exception {
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setEmail("john.doe@oscorp.com");
        employee.setPhone("666.111.1111");
        employee.setOffice("322F");
        employee.setRole("Sushi Monster");

        mockMvc.perform(post("/employees", employee)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(employee)))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().string("Must be a valid North American phone number with area code 415, in the format 415.456.7890"))
                .andExpect(redirectedUrl(null));
    }

    @Test
    public void whenAddingNewEmployeeWithoutOffice_thenErrorStatusCodeAndResponse() throws Exception {
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setEmail("john.doe@oscorp.com");
        employee.setPhone("415.111.1111");
        employee.setRole("Sushi Monster");

        mockMvc.perform(post("/employees", employee)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(employee)))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().string("Please provide office"))
                .andExpect(redirectedUrl(null));
    }

    @Test
    public void whenAddingNewEmployeeWithInvalidOfficeRange_thenErrorStatusCodeAndResponse() throws Exception {
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setEmail("john.doe@oscorp.com");
        employee.setPhone("415.111.1111");
        employee.setOffice("100Z");
        employee.setRole("Sushi Monster");

        mockMvc.perform(post("/employees", employee)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(employee)))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().string("Office must be in the range 100A - 599F"))
                .andExpect(redirectedUrl(null));
    }

    @Test
    public void whenAddingNewEmployeeWithoutRole_thenErrorStatusCodeAndResponse() throws Exception {
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setPhone("415.111.1111");
        employee.setEmail("john.doe@oscorp.com");
        employee.setOffice("322F");

        mockMvc.perform(post("/employees", employee)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(employee)))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().string("Please provide role"))
                .andExpect(redirectedUrl(null));
    }
}

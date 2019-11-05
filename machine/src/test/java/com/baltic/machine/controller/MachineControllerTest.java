package com.baltic.machine.controller;

import com.baltic.machine.repository.MachineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MachineController.class)
class MachineControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private MachineRepository repository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void machineStatus() throws Exception {

        String uri = "machine/computation/1";
        mvc.perform(get(uri)).andExpect(status().isNotFound());
    }

    @Test
    void getMachine() {
    }

    @Test
    void activateMachine() {
    }

    @Test
    void abortMachine() {
    }
}
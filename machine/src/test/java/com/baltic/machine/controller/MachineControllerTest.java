package com.baltic.machine.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(MachineController.class)
class MachineControllerTest {
    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void machineStatus() throws Exception {
        String uri = "/machine/status/1";
        mvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON));

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
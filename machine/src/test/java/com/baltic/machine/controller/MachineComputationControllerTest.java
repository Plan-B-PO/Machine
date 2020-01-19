package com.baltic.machine.controller;

import com.baltic.machine.model.*;
import com.baltic.machine.model.Runnable;
import com.baltic.machine.repository.MachineRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.lang.reflect.Array;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MachineComputationController.class)
@AutoConfigureMockMvc
class MachineComputationControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private MachineRepository repository;

    @Test
    void getComputationTaskNotFoundTest() throws Exception {
        String uri = "machine/computation/645";
        mvc.perform(get(uri)).andExpect(status().isNotFound());
    }

    @Test
    void getComputationTask() throws Exception{
        String uri = "machine/computation/645";
        mvc.perform(get(uri)).andExpect(status().is2xxSuccessful());
    }

//    @Test
//    void activateComputationTask() throws Exception {
//        String uri = "machine/computation";
//        Machine machine = Machine.builder()
//                .appUserId("123")
//                .id("312-nowa-apka")
//                .logger(null)
//                .runnable(new Runnable("app-id", new ComputationSteps(null, List.of("docker.hub.pl/calc") , null), "version_3.2.4")).build();
//        ComputationTask computationTask = new ComputationTask(machine, "nowy-token-12312-12312", ComputationStatus.RUNNING);
//        mvc.perform(MockMvcRequestBuilders
//                .post(uri)
//                .content(asJsonString(computationTask))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated());
//    }

    @Test
    void abortComputationTaskNotFound() throws Exception{
        String uri = "machine/computation/123";
        mvc.perform(delete(uri)).andExpect(status().isNotFound());
    }

    @Test
    void abortComputationTask() throws Exception{
        String uri = "machine/computation/123";
        mvc.perform(delete(uri)).andExpect(status().isAccepted());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
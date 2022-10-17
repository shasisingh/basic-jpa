package com.shashi.takeaway.takeaway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shashi.takeaway.takeaway.domain.Voucher;
import com.shashi.takeaway.takeaway.model.Create;
import com.shashi.takeaway.takeaway.services.VoucherService;
import org.assertj.core.internal.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VoucherController.class)
class VoucherControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper mapper;

  @MockBean
  VoucherService voucherService;

  @Test
  void testCreateNewVoucher() throws Exception {

    when(voucherService.initVoucher(any()))
            .thenReturn(new Voucher(1L,LocalDateTime.now(),"UserId",100L));

    Create create= new Create("userId",1L,LocalDateTime.now());

    String requestJson = mapper.writeValueAsString(create);

    mockMvc.perform(post("/takeaway/v1/voucher/create")
                    .content(requestJson)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("UserId"));
  }

  @Test
  void testUpdateAndRedeem() throws Exception {

    when(voucherService.getAfterRedeemValue(any()))
            .thenReturn(10L);

    Create create= new Create("userId",1L,LocalDateTime.now());

    String requestJson = mapper.writeValueAsString(create);

    mockMvc.perform(post("/takeaway/v1/voucher/redeem")
                    .content(requestJson)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$").value("10"));
  }
}

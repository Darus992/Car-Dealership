package pl.zajavka.api.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.zajavka.api.dto.CarPurchaseDTO;
import pl.zajavka.api.dto.InvoiceDTO;
import pl.zajavka.api.dto.mapper.CarPurchaseMapper;
import pl.zajavka.api.dto.mapper.InvoiceMapper;
import pl.zajavka.business.CarPurchaseService;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

@WebMvcTest(controllers = PurchaseRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PurchaseRestControllerWebMvcTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @MockBean
    @SuppressWarnings("unused")
    private CarPurchaseService carPurchaseService;

    @MockBean
    @SuppressWarnings("unused")
    private CarPurchaseMapper carPurchaseMapper;

    @MockBean
    @SuppressWarnings("unused")
    private InvoiceMapper invoiceMapper;

    @Test
    void carPurchaseWorksCorrectly() throws Exception {
        //  given
        CarPurchaseDTO carPurchaseBody = CarPurchaseDTO.buildDefaultData();
        String requestBody = objectMapper.writeValueAsString(carPurchaseBody);
        String responseBody = objectMapper.writeValueAsString(someInvoiceDTO());

        Mockito.when(invoiceMapper.map(Mockito.any())).thenReturn(someInvoiceDTO());

        //  when, then
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(PurchaseRestController.API_PURCHASE)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceNumber", Matchers.is(someInvoiceDTO().getInvoiceNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateTime", Matchers.is(someInvoiceDTO().getDateTime().format(DateTimeFormatter.ISO_DATE_TIME))))
                .andReturn();

        Assertions.assertThat(result.getResponse().getContentAsString())
                .isEqualTo(responseBody);
    }

    @Test
    void thatEmailValidationWorksCorrectly() throws Exception {
        //  given
        CarPurchaseDTO carPurchaseBody = CarPurchaseDTO.buildDefaultData().withCustomerEmail("badEmail");
        String requestBody = objectMapper.writeValueAsString(carPurchaseBody);

        //  when, then
        mockMvc.perform(MockMvcRequestBuilders.post(PurchaseRestController.API_PURCHASE)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorId", Matchers.notNullValue()));
    }

    @ParameterizedTest
    @MethodSource
    void thatPhoneValidationWorksCorrectly(Boolean correctPhone, String phone) throws Exception {
        //  given
        CarPurchaseDTO carPurchaseBody = CarPurchaseDTO.buildDefaultData().withCustomerPhone(phone);
        String requestBody = objectMapper.writeValueAsString(carPurchaseBody);

        //  when, then
        if (correctPhone) {
            InvoiceDTO someInvoiceDTO = someInvoiceDTO();
            String responseBody = objectMapper.writeValueAsString(someInvoiceDTO);
            Mockito.when(invoiceMapper.map(Mockito.any())).thenReturn(someInvoiceDTO);

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(PurchaseRestController.API_PURCHASE)
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceNumber", Matchers.is(someInvoiceDTO.getInvoiceNumber())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.dateTime", Matchers.is(someInvoiceDTO.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME))))
                    .andReturn();

            Assertions.assertThat(result.getResponse().getContentAsString())
                    .isEqualTo(responseBody);
        } else {
            mockMvc.perform(MockMvcRequestBuilders.post(PurchaseRestController.API_PURCHASE)
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.errorId", Matchers.notNullValue()));
        }
    }

    public static Stream<Arguments> thatPhoneValidationWorksCorrectly() {
        return Stream.of(
                Arguments.of(false, ""),
                Arguments.of(false, "+48 504 203 260@@"),
                Arguments.of(false, "+48.504.203.260"),
                Arguments.of(false, "+55(123) 456-78-90-"),
                Arguments.of(false, "+55(123) - 456-78-90"),
                Arguments.of(false, "504.203.260"),
                Arguments.of(false, " "),
                Arguments.of(false, "-"),
                Arguments.of(false, "()"),
                Arguments.of(false, "() + ()"),
                Arguments.of(false, "(21 7777"),
                Arguments.of(false, "+48 (21)"),
                Arguments.of(false, "+"),
                Arguments.of(false, " 1"),
                Arguments.of(false, "1"),
                Arguments.of(false, "+48 (12) 504 203 260"),
                Arguments.of(false, "+48 (12) 504-203-260"),
                Arguments.of(false, "+48(12)504203260"),
                Arguments.of(false, "555-5555-555"),
                Arguments.of(true, "+48 504 203 260")
        );
    }

    private static InvoiceDTO someInvoiceDTO() {
        return InvoiceDTO.builder()
                .invoiceNumber("invNumb")
                .dateTime(OffsetDateTime.of(2020, 10, 10, 10, 30, 0, 0, ZoneOffset.UTC))
                .build();
    }
}

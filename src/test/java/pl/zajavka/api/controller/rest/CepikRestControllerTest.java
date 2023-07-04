package pl.zajavka.api.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import pl.zajavka.api.dto.CarPurchaseDTO;
import pl.zajavka.api.dto.CepikVehicleDTO;
import pl.zajavka.api.dto.mapper.CepikVehicleMapper;
import pl.zajavka.business.CepikService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebMvcTest(controllers = CepikRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CepikRestControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @MockBean
    @SuppressWarnings("unused")
    private CepikService cepikService;

    @MockBean
    @SuppressWarnings("unused")
    private CepikVehicleMapper cepikVehicleMapper;

    @Test
    void carPurchaseWorksCorrectly() throws Exception {
        //  given
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("firstRegistrationDateFrom", LocalDate.of(2020, 1, 1).toString());
        parameters.add("firstRegistrationDateTo", LocalDate.of(2022, 12, 31).toString());
        CepikVehicleDTO cepikVehicle = someCepikVehicle();
        String responseBody = objectMapper.writeValueAsString(cepikVehicle);

        Mockito.when(cepikVehicleMapper.map(Mockito.any())).thenReturn(cepikVehicle);

        //  when, then
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(CepikRestController.API_CEPIK + CepikRestController.CEPIK_RANDOM)
                        .params(parameters))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cepikId", Matchers.is(cepikVehicle.getCepikId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand", Matchers.is(cepikVehicle.getBrand())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Matchers.is(cepikVehicle.getModel())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", Matchers.is(cepikVehicle.getType())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.engineCapacity", Matchers.is(cepikVehicle.getEngineCapacity()), BigDecimal.class))
                .andExpect(MockMvcResultMatchers.jsonPath("$.weight", Matchers.is(cepikVehicle.getWeight())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fuel", Matchers.is(cepikVehicle.getFuel())))
                .andReturn();

        Assertions.assertThat(result.getResponse().getContentAsString())
                .isEqualTo(responseBody);
    }

    private static CepikVehicleDTO someCepikVehicle() {
        return CepikVehicleDTO.builder()
                .cepikId("cepikId")
                .brand("brand")
                .model("model")
                .type("type")
                .engineCapacity(new BigDecimal("1000"))
                .weight(1200)
                .fuel("fuel")
                .build();
    }
}

package pl.zajavka.integration.support;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Map;

public interface WiremockTestSupport {

    Map<String, String> VEHICLE_IDS = Map.of(
            "6146580331568834", "pojazd-1.json",
            "604539927931971", "pojazd-2.json",
            "5825455199861751", "pojazd-3.json",
            "3484104437690863", "pojazd-4.json",
            "2497690509638911", "pojazd-5.json"
    );

    default void stubForSlowniki(WireMockServer wireMockServer) {
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathMatching("/slowniki/wojewodztwa"))
                .willReturn(WireMock.aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("wiremock/slowniki-wojewodztwa.json"))
        );
    }

    default void stubForPojazdy(WireMockServer wireMockServer, String dateFrom, String dateTo) {
        Map<String, StringValuePattern> queryParamsPattern = Map.of(
                "data-od", WireMock.equalTo(dateFrom.replace("-", "")),
                "data-do", WireMock.equalTo(dateTo.replace("-", ""))
        );
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/pojazdy"))
                .withQueryParams(queryParamsPattern)
                .willReturn(WireMock.aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("wiremock/pojazdy.json")));
    }

    default void stubForPojazd(WireMockServer wireMockServer) {
        VEHICLE_IDS.forEach((vehicleId, fileName) ->
                wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/pojazdy/%s".formatted(vehicleId)))
                        .willReturn(WireMock.aResponse()
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("wiremock/%s".formatted(fileName)))));
    }
}

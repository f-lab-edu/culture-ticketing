package com.culture.ticketing.booking.api

import com.culture.ticketing.booking.application.BookingFacadeService
import com.culture.ticketing.booking.application.dto.BookingSaveRequest
import com.culture.ticketing.booking.application.dto.BookingShowFloorSaveRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import spock.lang.Specification

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(BookingController.class)
@MockBean(JpaMetamodelMappingContext.class)
class BookingControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpringBean
    private BookingFacadeService bookingFacadeService = Mock();

    def "예약_생성_성공"() {

        given:
        List<Long> showSeatIds = List.of(1L);
        List<BookingShowFloorSaveRequest> bookingShowFloors = List.of(
                new BookingShowFloorSaveRequest(1L, 100)
        );
        BookingSaveRequest request = BookingSaveRequest.builder()
                .userId(1L)
                .roundId(1L)
                .totalPrice(260000)
                .showSeatIds(showSeatIds)
                .showFloors(bookingShowFloors)
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bookings")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
    }
}

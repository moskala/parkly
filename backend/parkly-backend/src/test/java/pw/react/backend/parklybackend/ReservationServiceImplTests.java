package pw.react.backend.parklybackend;

import org.apache.tomcat.jni.Local;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import pw.react.backend.parklybackend.dao.ParkingOwnerRepository;
import pw.react.backend.parklybackend.dao.ParkingRepository;
import pw.react.backend.parklybackend.dao.ParkingSpotRepository;
import pw.react.backend.parklybackend.dao.ReservationRepository;
import pw.react.backend.parklybackend.dto.AvailableParkingDto;
import pw.react.backend.parklybackend.model.Parking;
import pw.react.backend.parklybackend.model.ParkingSpot;
import pw.react.backend.parklybackend.service.ReservationService;
import pw.react.backend.parklybackend.service.ReservationServiceImpl;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceImplTests {

    @Mock
    private ParkingRepository parkingRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ParkingOwnerRepository ownerRepository;
    @Mock
    private ParkingSpotRepository spotRepository;

    @Spy
    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Test
    public void givenCityAndDates_whenFindingAvailableParkings_thenReturnEmptyList(){
        String city = "city";
        LocalDateTime dFrom = LocalDateTime.now();
        LocalDateTime dTo = LocalDateTime.now().plusHours(1);

        when(parkingRepository.findAllByCityAndWorkingHoursFromIsLessThanEqualAndWorkingHoursToIsGreaterThanEqual(anyString(), anyInt(), anyInt()))
                .thenReturn(Lists.emptyList());

        Collection<AvailableParkingDto> result = reservationService.findAvailableParkings(city, dFrom, dTo);

        assertThat(result).isEmpty();

    }

    @Test
    public void givenCityAndDates_whenFindingAvailableParkings_thenReturnListOfSearchedParkings(){
        String city = "city";
        LocalDateTime dFrom = LocalDateTime.now();
        LocalDateTime dTo = LocalDateTime.now().plusDays(1);
        Parking parking = new Parking();
        parking.setId(1L);
        parking.setCity("city");
        parking.setStreet("street");
        parking.setStreetNumber(1);
        parking.setCostPerHour(1);

        when(parkingRepository.findAllByCityAndWorkingHoursFromAndWorkingHoursTo(anyString(), anyInt(), anyInt()))
                .thenReturn(Lists.newArrayList(parking));
        when(reservationRepository.findCrossedReservationsParkingSpots(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Lists.emptyList());
        when(reservationRepository.findInternalReservationsParkingSpots(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Lists.emptyList());
        when(spotRepository.countAllByParkingId(1L)).thenReturn(1);

        Collection<AvailableParkingDto> result = reservationService.findAvailableParkings(city, dFrom, dTo);

        AvailableParkingDto expected = new AvailableParkingDto(1L,"city", "street", 1, 24);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.stream().findFirst().get()).isEqualToComparingFieldByField(expected);
        verify(parkingRepository, times(0)).findAllByCityAndWorkingHoursFromIsLessThanEqualAndWorkingHoursToIsGreaterThanEqual(anyString(), anyInt(), anyInt());

    }

    @Test
    public void givenCityAndDates_whenFindingAvailableParkingsOnFullParking_thenReturnEmptyList(){
        String city = "city";
        LocalDateTime dFrom = LocalDateTime.now();
        LocalDateTime dTo = LocalDateTime.now().plusDays(1);
        Parking parking = new Parking();

        when(parkingRepository.findAllByCityAndWorkingHoursFromAndWorkingHoursTo(anyString(), anyInt(), anyInt()))
                .thenReturn(Lists.newArrayList(parking));
        when(reservationRepository.findCrossedReservationsParkingSpots(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Lists.newArrayList( new ParkingSpot()));
        when(reservationRepository.findInternalReservationsParkingSpots(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Lists.emptyList());
        when(spotRepository.countAllByParkingId(anyLong())).thenReturn(1);

        Collection<AvailableParkingDto> result = reservationService.findAvailableParkings(city, dFrom, dTo);

        assertThat(result).isEmpty();

    }



}

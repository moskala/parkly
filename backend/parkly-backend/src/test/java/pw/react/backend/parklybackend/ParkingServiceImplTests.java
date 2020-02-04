package pw.react.backend.parklybackend;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import pw.react.backend.parklybackend.appException.ForbiddenActionException;
import pw.react.backend.parklybackend.appException.InvalidArgumentException;
import pw.react.backend.parklybackend.appException.ResourceNotFoundException;
import pw.react.backend.parklybackend.dao.ParkingOwnerRepository;
import pw.react.backend.parklybackend.dao.ParkingRepository;
import pw.react.backend.parklybackend.dao.ParkingSpotRepository;
import pw.react.backend.parklybackend.dao.ReservationRepository;
import pw.react.backend.parklybackend.dto.ParkingDto;
import pw.react.backend.parklybackend.model.Parking;
import pw.react.backend.parklybackend.model.ParkingOwner;
import pw.react.backend.parklybackend.model.Reservation;
import pw.react.backend.parklybackend.service.*;

import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ParkingServiceImplTests {

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
    private ParkingServiceImpl parkingService;


    @Test(expected = ResourceNotFoundException.class)
    public void givenNotExistingId_whenUpdateParking_thenThrowResourceNotFoundException() {

        when(parkingRepository.existsById(anyLong())).thenReturn(false);

        ParkingDto actual = parkingService.updateParking(1L, new ParkingDto());

        verify(parkingRepository, times(1)).existsById(eq(1L));
        verify(parkingRepository, times(0)).save(any(Parking.class));
    }

    @Test(expected = InvalidArgumentException.class)
    public void givenInvalidParameters_whenUpdateParking_thenThrowInvalidArgumentException() {
        ParkingDto updatedParkingDto = new ParkingDto();
        when(parkingRepository.existsById(anyLong())).thenReturn(true);

        ParkingDto actual = parkingService.updateParking(1L, updatedParkingDto);

    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void givenParkingWithFutureReservations_whenDeleteParking_thenThrowForbiddenActionException() throws Exception {

        expectedEx.expect(ForbiddenActionException.class);
        expectedEx.expectMessage(String.format("Parking with id 1 has future reservations."));

        Reservation reservation = new Reservation();
        when(reservationRepository.findAllByParkingSpotParkingIdAndDateToGreaterThanEqual(anyLong(), any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(reservation));

        boolean success = parkingService.deleteParking(1L);

    }





}

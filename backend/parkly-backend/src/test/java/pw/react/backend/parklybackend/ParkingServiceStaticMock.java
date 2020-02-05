package pw.react.backend.parklybackend;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import pw.react.backend.parklybackend.dao.ParkingOwnerRepository;
import pw.react.backend.parklybackend.dao.ParkingRepository;
import pw.react.backend.parklybackend.dao.ParkingSpotRepository;
import pw.react.backend.parklybackend.dao.ReservationRepository;
import pw.react.backend.parklybackend.dto.ParkingDto;
import pw.react.backend.parklybackend.model.Parking;
import pw.react.backend.parklybackend.model.ParkingOwner;
import pw.react.backend.parklybackend.service.ParkingServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(PowerMockRunner.class)

public class ParkingServiceStaticMock {
    @Mock
    private ParkingRepository parkingRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ParkingOwnerRepository ownerRepository;
    @Mock
    private ParkingSpotRepository spotRepository;

    private ParkingServiceImpl parkingService;

    @Before
    public void setup(){
        parkingService = Mockito.spy(new ParkingServiceImpl(parkingRepository, reservationRepository,
                ownerRepository, spotRepository));
    }

    @Test
    @PrepareForTest({Parking.class, ParkingDto.class})
    public void givenValidData_whenCreateParking_thenReturnNewParking() {

        ParkingDto parkingRequest = new ParkingDto();
        parkingRequest.setSpotsNumber(1);

        PowerMockito.mockStatic(Parking.class);
        PowerMockito.mockStatic(ParkingDto.class);
        BDDMockito.given(Parking.valueFrom(any(ParkingDto.class))).willReturn(new Parking());
        BDDMockito.given(ParkingDto.valueFrom(any(Parking.class))).willReturn(new ParkingDto());
        BDDMockito.given(Parking.isNumberValid(anyInt())).willReturn(true);

        ParkingOwner owner = new ParkingOwner();

        Mockito.doReturn(true).when(parkingService).isParkingValid(any(Parking.class));
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));
        when(spotRepository.countAllByParkingId(anyLong())).thenReturn(1);
        when(parkingRepository.save(any(Parking.class))).thenReturn(new Parking());

        ParkingDto actual = parkingService.addParking(parkingRequest);

        assertThat(actual).isEqualToIgnoringGivenFields(parkingRequest, "id");
        verify(ownerRepository, times(1)).findById(anyLong());
        verify(parkingRepository, times(1)).save(any(Parking.class));
    }
}

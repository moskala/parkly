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
import pw.react.backend.parklybackend.appException.ResourceNotFoundException;
import pw.react.backend.parklybackend.dao.ParkingOwnerRepository;
import pw.react.backend.parklybackend.model.ParkingOwner;
import pw.react.backend.parklybackend.service.ParkingOwnerServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ParkingOwnerServiceImplTests {

    @Mock
    private ParkingOwnerRepository repository;

    @Spy
    @InjectMocks
    private ParkingOwnerServiceImpl service;

    @Test
    public void givenExistingEmail_whenCheckIfEmailExists_thenReturnTrue() {

        String email = "email@email.com";
        ParkingOwner owner = new ParkingOwner();
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(owner));

        boolean checkEmail = service.checkIfEmailExists(email);

        assertThat(checkEmail).isTrue();
        verify(repository, times(1)).findByEmail(eq(email));
    }

    @Test
    public void givenInvalidParkingOwner_whenCreateParkingOwner_thenReturnEmptyParkingOwner() {
        ParkingOwner owner = new ParkingOwner();
        owner.setEmail("email@email.com");
        Mockito.doReturn(false).when(service).checkIfEmailExists(anyString());
        Mockito.doReturn(false).when(service).validateParkingOwner(any(ParkingOwner.class));

        ParkingOwner ownerSaved = service.addParkingOwner(owner);

        assertThat(ownerSaved).isEqualTo(ParkingOwner.EMPTY);
        verify(repository, times(0)).save(any(ParkingOwner.class));
    }

    @Test
    public void givenValidParkingOwner_whenCreateParkingOwner_thenReturnParkingOwner() {
        ParkingOwner owner = new ParkingOwner();
        owner.setEmail("email@email.com");
        Mockito.doReturn(false).when(service).checkIfEmailExists(anyString());
        Mockito.doReturn(true).when(service).validateParkingOwner(any(ParkingOwner.class));
        when(repository.save(any(ParkingOwner.class))).thenReturn(owner);

        ParkingOwner ownerSaved = service.addParkingOwner(owner);

        assertThat(ownerSaved).isEqualToComparingOnlyGivenFields(owner, "email");
        verify(repository, times(1)).save(any(ParkingOwner.class));
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void givenNotExistingId_whenGetParkingOwner_thenThrowException() throws Exception {

        expectedEx.expect(ResourceNotFoundException.class);
        expectedEx.expectMessage(String.format("Parking owner with id 1 does not exists."));
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        ParkingOwner owner = service.getParkingOwner(1L);

    }




}

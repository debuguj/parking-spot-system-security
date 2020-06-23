package pl.debuguj.system.spot;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;

class ArchivedSpotRepoInMemoryTest {

    private final ArchivedSpotRepo sut = new ArchivedSpotRepoInMemory();

    private static final LocalDateTime defBeginDateTime = LocalDateTime.now();
    private static final LocalDateTime defEndDate = LocalDateTime.now().plusHours(2L);
    private static final String defRegistrationNumber = "WZE12345";
    private static ArchivedSpot archivedSpot;

    private final String basicDateString = "2020-06-21";

    @BeforeAll
    static void init() {
        archivedSpot = new ArchivedSpot(defRegistrationNumber, DriverType.REGULAR, defBeginDateTime, defEndDate);
    }

    @Test
    public void shouldReturnEmptyOptional() {
        Optional<ArchivedSpot> opt = sut.save(null);
        assertFalse(opt.isPresent());
    }

    @Test
    public void shouldSaveNewArchivedSpot() {

        Optional<ArchivedSpot> returned = sut.save(archivedSpot);

        assertTrue(returned.isPresent());

        returned.ifPresent(as -> {
            assertEquals(archivedSpot.getUuid(), as.getUuid());
            assertEquals(archivedSpot.getBeginLocalDateTime(), as.getBeginLocalDateTime());
            assertEquals(archivedSpot.getEndLocalDateTime(), as.getEndLocalDateTime());
            assertEquals(archivedSpot.getDriverType(), as.getDriverType());
        });
    }

    @Test
    public void shouldFindAllByDate() throws Exception {
        List<ArchivedSpot> loadings = createArchiveSpotsList();
        loadings.forEach(sut::save);

        LocalDate date = LocalDate.parse(basicDateString);
        List<ArchivedSpot> spotStream = sut.getAllByDay(date);
        assertEquals(2, spotStream.size());

        date = date.plusDays(1L);
        spotStream = sut.getAllByDay(date);
        assertEquals(3, spotStream.size());

        date = LocalDate.now().plusDays(2L);
        spotStream = sut.getAllByDay(date);

        assertEquals(0, spotStream.size());
    }

    private List<ArchivedSpot> createArchiveSpotsList() throws Exception {

        List<ArchivedSpot> list = new ArrayList<>();
        LocalDateTime ldt = LocalDateTime.parse(basicDateString);
        list.add(new ArchivedSpot("WWW66666", DriverType.REGULAR, ldt, ldt.plusHours(2L)));
        list.add(new ArchivedSpot("WSQ77777", DriverType.REGULAR, ldt, ldt.plusHours(3L)));
        list.add(new ArchivedSpot("QAZ88888", DriverType.REGULAR, ldt.plusDays(1L), ldt.plusHours(4L)));
        list.add(new ArchivedSpot("EDC99999", DriverType.REGULAR, ldt.plusDays(1L), ldt.plusHours(2L)));
        list.add(new ArchivedSpot("FDR99998", DriverType.REGULAR, ldt.plusDays(1L), ldt.plusHours(1L)));

        return list;
    }
}

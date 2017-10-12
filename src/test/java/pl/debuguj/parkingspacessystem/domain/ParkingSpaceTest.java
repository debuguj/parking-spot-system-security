package pl.debuguj.parkingspacessystem.domain;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by grzesiek on 12.10.17.
 */
public class ParkingSpaceTest {

    private ParkingSpace parkingSpace;

    private static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat simpleDateFormat;

    @BeforeClass
    public static void set(){

        simpleDateFormat = new SimpleDateFormat(TIME_PATTERN);
    }

    @Before
    public void setUp() throws ParseException, IncorrectEndDateException {
        parkingSpace = new ParkingSpace("12345", "2017-10-12 10:15:10", "2017-10-12 14:15:10");

    }

    @Test
    public void testCorrectCarRegistrationNumber(){
        assertEquals("Car registration No. should be the same", "12345", parkingSpace.getCarRegistrationNumber());
        assertNotEquals("Car registration No. should not be the same", "12346", parkingSpace.getCarRegistrationNumber());
    }

    @Test
    public void testCorrectDriverType() throws Exception {

        assertEquals("Type of driver should be the same", DriverType.REGULAR, parkingSpace.getDriverType());
        assertNotEquals("Type of driver should not be the same", DriverType.VIP, parkingSpace.getDriverType());

        parkingSpace.setDriverType(null);
        assertEquals("Type of driver should be the same", DriverType.REGULAR, parkingSpace.getDriverType());

        parkingSpace.setDriverType(DriverType.VIP);
        assertEquals("Type of driver should be the same", DriverType.VIP, parkingSpace.getDriverType());
        assertNotEquals("Type of driver should not be the same", DriverType.REGULAR, parkingSpace.getDriverType());
    }

    @Test
    public void testCorrectBeginTime() throws ParseException {

        Date date = simpleDateFormat.parse("2017-10-12 10:15:10");
        assertEquals("Begin date should be the same", date, parkingSpace.getBeginTime());

        date = simpleDateFormat.parse("2017-10-12 10:15:11");
        assertNotEquals("Begin date should not be the same", date, parkingSpace.getBeginTime());
    }

    @Test
    public void testCorrectSetOfEndTime() throws ParseException {

        Date date = simpleDateFormat.parse("2017-10-12 14:15:10");
        assertEquals("End date should be the same", date, parkingSpace.getEndTime());

        date = simpleDateFormat.parse("2017-10-12 14:15:12");
        assertNotEquals("End date not should be the same", date, parkingSpace.getEndTime());
    }

    @Test(expected = IncorrectEndDateException.class )
    public void testIncorrectSetOfEndTime() throws ParseException, IncorrectEndDateException {

        parkingSpace.setEndTime("2017-10-12 10:00:00");
    }

    @Test(expected = ParseException.class )
    public void testIncorrectEndTimeFormat() throws ParseException, IncorrectEndDateException {

        parkingSpace.setEndTime("201710-12 10:10");
    }

}
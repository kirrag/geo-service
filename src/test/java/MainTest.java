import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;

import org.mockito.*;

import java.util.HashMap;
import java.util.Map;

import ru.netology.entity.Location;
import ru.netology.entity.Country;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import org.junit.Test;

public class MainTest {

	@Test
	public void messageSenderImplTest_Russian() {
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
		Mockito.when(geoService.byIp(GeoServiceImpl.MOSCOW_IP))
			.thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
		Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<String, String>();

        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, GeoServiceImpl.MOSCOW_IP);

        String actual = messageSender.send(headers);

		String expected = "Добро пожаловать";

		Assertions.assertEquals(expected, actual);
	}
	@Test
	public void messageSenderImplTest_NotRussian() {
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
		Mockito.when(geoService.byIp(GeoServiceImpl.NEW_YORK_IP))
			.thenReturn(new Location("New York", Country.USA, " 10th Avenue", 32));

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
		Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<String, String>();

        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, GeoServiceImpl.NEW_YORK_IP);

        String actual = messageSender.send(headers);

		String expected = "Welcome";

		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void geoServiceImplTest() {
		GeoService geoService = new GeoServiceImpl();

      	Country actualCountry = geoService.byIp(GeoServiceImpl.MOSCOW_IP).getCountry();
		String actualCountryName = actualCountry.toString();
		String expectedCountryName = "RUSSIA";
		Assertions.assertEquals(expectedCountryName, actualCountryName);
		String actualCity = geoService.byIp(GeoServiceImpl.MOSCOW_IP).getCity();
		String expectedCity = "Moscow";
		Assertions.assertEquals(expectedCity, actualCity);
		String actualStreet = geoService.byIp(GeoServiceImpl.MOSCOW_IP).getStreet();
		String expectedStreet = "Lenina";
		Assertions.assertEquals(expectedStreet, actualStreet);
		int actualBuiling = geoService.byIp(GeoServiceImpl.MOSCOW_IP).getBuiling();
		int expectedBuiling = 15;
		Assertions.assertEquals(expectedBuiling, actualBuiling);
	}

	@Test
	public void LocalizationServiceImpl() {
		LocalizationService localizationService = new LocalizationServiceImpl();

		String actualLocale = localizationService.locale(Country.RUSSIA);
		String expectedLocale = "Добро пожаловать";

		Assertions.assertEquals(expectedLocale, actualLocale);
	}
}


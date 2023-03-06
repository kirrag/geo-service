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

//import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
	@ParameterizedTest
	@ValueSource(strings = { "127.0.0.1", "172.0.32.11", "96.44.183.149", "172.10.10.1", "96.10.10.1" })
	public void geoServiceImplTestByIP(String argument) {
		
		GeoService geoService = new GeoServiceImpl();

		Location actualCountryLocation = geoService.byIp(argument);

		Location expectedCountryLocation = null;

		if (argument.equals("127.0.0.1")) {
			expectedCountryLocation = new Location(null, null, null, 0);
		} else if (argument.equals("172.0.32.11")) {
			expectedCountryLocation = new Location("Moscow", Country.RUSSIA, "Lenina", 15);
		} else if (argument.equals("96.44.183.149")) {
			expectedCountryLocation = new Location("New York", Country.USA, " 10th Avenue", 32);
		} else if (argument.startsWith("172.")) {
			expectedCountryLocation = new Location("Moscow", Country.RUSSIA, null, 0);
		} else if (argument.startsWith("96.")) {
			expectedCountryLocation = new Location("New York", Country.USA, null, 0);
		}
		Assertions.assertEquals(expectedCountryLocation.getCity(), actualCountryLocation.getCity());
		Assertions.assertEquals(expectedCountryLocation.getCountry(), actualCountryLocation.getCountry());
		Assertions.assertEquals(expectedCountryLocation.getStreet(), actualCountryLocation.getStreet());
		Assertions.assertEquals(expectedCountryLocation.getBuiling(), actualCountryLocation.getBuiling());

	}

	@Test
	public void geoServiceImplTestByCoordinateThrowsException() {
		GeoService geoService = new GeoServiceImpl();
		Assertions.assertThrows(Exception.class, () -> {
			geoService.byCoordinates(49.5, 39.6);
		});
	}

	@Test
	public void localizationServiceImplTestLocale() {
		LocalizationService localizationService = new LocalizationServiceImpl();

		String actualLocale = localizationService.locale(Country.RUSSIA);
		String expectedLocale = "Добро пожаловать";

		Assertions.assertEquals(expectedLocale, actualLocale);
	}
}

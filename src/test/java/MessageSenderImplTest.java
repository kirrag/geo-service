package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Set;
import org.mockito.*;

import java.util.HashMap;
import java.util.Map;

import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

public class MessageSenderImplTest {

	@Test
	static void messageSenderImplTest() {
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
		//Mockito.when(geoService.byIp("172.0.32.11"));

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);

	//	System.out.println(geoService);

        //MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

		//Map<String, String> headers = new HashMap<String, String>();
        //headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19");
        //messageSender.send(headers);
	//		Assertions.assertEquals(expected, preferences);
	//	        GeoService geoService = new GeoServiceImpl();
    //    LocalizationService localizationService = new LocalizationServiceImpl();

		//System.out.println(GeoServiceImpl.MOSCOW_IP);

		//System.out.println(geoService.byIp(GeoServiceImpl.MOSCOW_IP).getCountry());

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<String, String>();

        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, GeoServiceImpl.MOSCOW_IP);

        String result = messageSender.send(headers);

		String expected = "Добро пожаловать";
		System.out.println(expected);
		System.out.println(result);

		Assertions.assertEquals(expected, result);
		//System.out.println(result);

	}
}


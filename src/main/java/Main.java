import java.util.HashMap;
import java.util.Map;

import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

public class Main {
    //Тестовый пример
    public static void main(String[] args) {
        GeoService geoService = new GeoServiceImpl();
        LocalizationService localizationService = new LocalizationServiceImpl();

		//System.out.println(GeoServiceImpl.MOSCOW_IP);

		//System.out.println(geoService.byIp(GeoServiceImpl.MOSCOW_IP).getCountry());

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<String, String>();

        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19");

        messageSender.send(headers);

		//System.out.println(result);
    }
}

package com.focusstart.miller777.focusstartandroidrssreader.net;

import android.content.Context;
import android.content.Intent;

import com.focusstart.miller777.focusstartandroidrssreader.MainActivity;
import com.focusstart.miller777.focusstartandroidrssreader.model.ItemModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NetHelper {
    //Объект класса загружает RSS ленту

    private String baseRssUrl;
    private Context context;


    public NetHelper(String baseRssUrl, Context context) {

        this.baseRssUrl = baseRssUrl;
        this.context = context;


    }


    public String getRss() {
        //пока замокаем вывод

        Intent intentDownloadService = new Intent(context, DownloadService.class);
        intentDownloadService.putExtra("baseUrl", baseRssUrl);
        context.startService(intentDownloadService);

        return "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
                "<rss version=\"2.0\">\n" +
                "<channel>\n" +
                "<title>Яндекс.Новости: Интернет</title>\n" +
                "<link>https://news.yandex.ru/internet.html?from=rss</link>\n" +
                "<description>Первая в России служба автоматической обработки и систематизации новостей. Сообщения ведущих российских и мировых СМИ. Обновление в режиме реального времени 24 часа в сутки.</description>\n" +
                "<image>\n" +
                "<url>https://company.yandex.ru/i/50x23.gif</url>\n" +
                "<link>https://news.yandex.ru/internet.html?from=rss</link>\n" +
                "<title>Яндекс.Новости: Интернет</title>\n" +
                "</image>\n" +
                "<lastBuildDate>24 Apr 2019 22:11:00 +0000</lastBuildDate>\n" +
                "<item>\n" +
                "<title>Суд в Индии снял запрет на приложение TikTok</title>\n" +
                "<link>https://news.yandex.ru/story/Sud_v_Indii_snyal_zapret_na_prilozhenie_TikTok--6a8794c0cacd58e8cb705f710b5a092f?lang=ru&amp;from=rss&amp;stid=F6wJM7D0U8ky</link>\n" +
                "<guid>https://news.yandex.ru/story/Sud_v_Indii_snyal_zapret_na_prilozhenie_TikTok--6a8794c0cacd58e8cb705f710b5a092f?lang=ru&amp;from=rss&amp;stid=F6wJM7D0U8ky</guid>\n" +
                "<description>Верховный суд Мадраса снял запрет на китайскую видеоплатформу для создания коротких видео TikTok при условии, что его не будут использовать для размещения «непристойного» контента. Об этом 24 апреля сообщает агентство AP.</description>\n" +
                "<pubDate>24 Apr 2019 19:40:55 +0000</pubDate>\n" +
                "</item>\n" +
                "<item>\n" +
                "<title>СМИ сообщили о блокировке в Таджикистане социальных сетей</title>\n" +
                "<link>https://news.yandex.ru/story/SMI_soobshhili_o_blokirovke_v_Tadzhikistane_socialnykh_setej--0f6d059268e18ba9b424609dee76044b?lang=ru&amp;from=rss&amp;stid=CwCN</link>\n" +
                "<guid>https://news.yandex.ru/story/SMI_soobshhili_o_blokirovke_v_Tadzhikistane_socialnykh_setej--0f6d059268e18ba9b424609dee76044b?lang=ru&amp;from=rss&amp;stid=CwCN</guid>\n" +
                "<description>Пользователи таджикского сегмента интернета сообщили о блокировке социальных сетей, ряда новостных сайтов, а также портала Youtube. Первыми об этом заявили пользователи Facebook, которые гадали о причинах перебоев с доступом к интернет-ресурсам.</description>\n" +
                "<pubDate>24 Apr 2019 13:32:00 +0000</pubDate>\n" +
                "</item>\n" +
                "<item>\n" +
                "<title>Бесплатный Wi-Fi появился между станциями «Приморская» и «Беговая»</title>\n" +
                "<link>https://news.yandex.ru/story/Besplatnyj_Wi-Fi_poyavilsya_mezhdu_stanciyami_Primorskaya_i_Begovaya--9f08a7bdb435a7a469a26236c8221a6d?lang=ru&amp;from=rss&amp;stid=GxWorSBBQ33VkToQJbhl</link>\n" +
                "<guid>https://news.yandex.ru/story/Besplatnyj_Wi-Fi_poyavilsya_mezhdu_stanciyami_Primorskaya_i_Begovaya--9f08a7bdb435a7a469a26236c8221a6d?lang=ru&amp;from=rss&amp;stid=GxWorSBBQ33VkToQJbhl</guid>\n" +
                "<description>Средняя скорость подключения поезда к сети составляет 250 Мбит / с. С сегодняшнего дня петербуржцы и гости города могут воспользоваться бесплатным Интернетом на перегонах Невско-Василеостровской линии петербургского метрополитена — между станциями &amp;quot;Приморская&amp;quot; и &amp;quot;Беговая&amp;quot;.</description>\n" +
                "<pubDate>24 Apr 2019 10:17:13 +0000</pubDate>\n" +
                "</item>\n" +
                "<item>\n" +
                "<title>Новый Chrome получил обновление безопасности</title>\n" +
                "<link>https://news.yandex.ru/story/Novyj_Chrome_poluchil_obnovlenie_bezopasnosti--5167e23c8a533d26788bc64fcf254de3?lang=ru&amp;from=rss&amp;stid=hzfJUu3_nDJu</link>\n" +
                "<guid>https://news.yandex.ru/story/Novyj_Chrome_poluchil_obnovlenie_bezopasnosti--5167e23c8a533d26788bc64fcf254de3?lang=ru&amp;from=rss&amp;stid=hzfJUu3_nDJu</guid>\n" +
                "<description>Браузер Chrome для Android получил функцию Data Saver, которая является новым механизмом сохранения данных. Его работа дает дополнительную защиту для пользователей приложения.</description>\n" +
                "<pubDate>24 Apr 2019 16:42:32 +0000</pubDate>\n" +
                "</item>\n" +
                "<item>\n" +
                "<title>Google и «Одноклассники» запустили проект об интернет-безопасности</title>\n" +
                "<link>https://news.yandex.ru/story/Google_i_Odnoklassniki_zapustili_proekt_ob_internet-bezopasnosti--c2964cbf18c0472d7c13a9d40b53ed7c?lang=ru&amp;from=rss&amp;stid=WjXj8EBNddnQGRx26blS</link>\n" +
                "<guid>https://news.yandex.ru/story/Google_i_Odnoklassniki_zapustili_proekt_ob_internet-bezopasnosti--c2964cbf18c0472d7c13a9d40b53ed7c?lang=ru&amp;from=rss&amp;stid=WjXj8EBNddnQGRx26blS</guid>\n" +
                "<description>&amp;quot;Одноклассники&amp;quot; совместно с компанией Google запустили игровой тест на знание правил безопасности в интернете. Об этом сообщается в блоге социальной сети. По заказу Google, аналитики из международной организации YouGov проверили, насколько безопасен российский интернет.</description>\n" +
                "<pubDate>23 Apr 2019 10:13:08 +0000</pubDate>\n" +
                "</item>\n" +
                "<item>\n" +
                "<title>Google Maps будет отображать станции зарядки автомобилей</title>\n" +
                "<link>https://news.yandex.ru/story/Google_Maps_budet_otobrazhat_stancii_zaryadki_avtomobilej--6569a67a34ffad9dd8b4a683059a6d17?lang=ru&amp;from=rss&amp;stid=PR7kN1vw</link>\n" +
                "<guid>https://news.yandex.ru/story/Google_Maps_budet_otobrazhat_stancii_zaryadki_avtomobilej--6569a67a34ffad9dd8b4a683059a6d17?lang=ru&amp;from=rss&amp;stid=PR7kN1vw</guid>\n" +
                "<description>Google Maps теперь могут отображать на карте в режиме реального времени ближайшие станции зарядки электрокаров. Доступна эта функция в США и Великобритании. Любой водитель сможет отследить не только наличие станций рядом с ним, но и узнать есть ли там место для зарядки.</description>\n" +
                "<pubDate>24 Apr 2019 05:38:41 +0000</pubDate>\n" +
                "</item>\n" +
                "<item>\n" +
                "<title>Yota обновила приложение и добавила функцию вложений в чате поддержки</title>\n" +
                "<link>https://news.yandex.ru/story/Yota_obnovila_prilozhenie_i_dobavila_funkciyu_vlozhenij_v_chate_podderzhki--6aa209e77fe8b8d57336d52bee8f8630?lang=ru&amp;from=rss&amp;stid=hslTqE8a</link>\n" +
                "<guid>https://news.yandex.ru/story/Yota_obnovila_prilozhenie_i_dobavila_funkciyu_vlozhenij_v_chate_podderzhki--6aa209e77fe8b8d57336d52bee8f8630?lang=ru&amp;from=rss&amp;stid=hslTqE8a</guid>\n" +
                "<description>Пользователи Yota на Ставрополье теперь могут отправлять вложенные файлы в чате поддержки мобильного приложения, сообщает пресс-служба оператора. Мобильный оператор Yota выпустил обновление своего приложения. Теперь в чате поддержки появилась функция «Приложить файл».</description>\n" +
                "<pubDate>24 Apr 2019 12:41:10 +0000</pubDate>\n" +
                "</item>\n" +
                "<item>\n" +
                "<title>Подписка на сервис YouTube TV стала доступна абонентам Verizon</title>\n" +
                "<link>https://news.yandex.ru/story/Podpiska_na_servis_YouTube_TV_stala_dostupna_abonentam_Verizon--9ac4e59c48b56f44a4cd810b0c4e3987?lang=ru&amp;from=rss&amp;stid=SGk0</link>\n" +
                "<guid>https://news.yandex.ru/story/Podpiska_na_servis_YouTube_TV_stala_dostupna_abonentam_Verizon--9ac4e59c48b56f44a4cd810b0c4e3987?lang=ru&amp;from=rss&amp;stid=SGk0</guid>\n" +
                "<description>Подписка на YouTube TV включает шесть учетных записей на семью с персональными рекомендациями и историей просмотров. Как поясняет издание, сделка с Google позволит клиентам Verizon получать стриминг YouTube TV в мобильной сети через фиксированное 5G-подключение.</description>\n" +
                "<pubDate>24 Apr 2019 08:07:45 +0000</pubDate>\n" +
                "</item>\n" +
                "<item>\n" +
                "<title>Вышла обновленная версия «Гарда БД»</title>\n" +
                "<link>https://news.yandex.ru/story/Vyshla_obnovlennaya_versiya_Garda_BD--e82dfa74586b31d9be954b8449f360ca?lang=ru&amp;from=rss</link>\n" +
                "<guid>https://news.yandex.ru/story/Vyshla_obnovlennaya_versiya_Garda_BD--e82dfa74586b31d9be954b8449f360ca?lang=ru&amp;from=rss</guid>\n" +
                "<description>Российский разработчик систем информационной безопасности «Гарда Технологии» представил обновленную версию системы защиты баз данных и веб-приложений «Гарда БД». Решение представляет собой аппаратно-программный комплекс для аудита сетевого доступа к базам данных и веб-приложениям.</description>\n" +
                "<pubDate>23 Apr 2019 13:52:24 +0000</pubDate>\n" +
                "</item>\n" +
                "<item>\n" +
                "<title>Французский защищенный госмессенджер взломан сразу после запуска</title>\n" +
                "<link>https://news.yandex.ru/story/Francuzskij_zashhishhennyj_gosmessendzher_vzloman_srazu_posle_zapuska--7b2503f637b0038ca7ba750d3d79044a?lang=ru&amp;from=rss</link>\n" +
                "<guid>https://news.yandex.ru/story/Francuzskij_zashhishhennyj_gosmessendzher_vzloman_srazu_posle_zapuska--7b2503f637b0038ca7ba750d3d79044a?lang=ru&amp;from=rss</guid>\n" +
                "<description>Взлом французского Telegram Французское правительство запустило собственный мессенджер под названием Tchap, который должен защитить переписку госслужащих от взлома иностранными хакерами, а также от манипуляций с данными со стороны технологических компаний.</description>\n" +
                "<pubDate>23 Apr 2019 15:24:11 +0000</pubDate>\n" +
                "</item>\n" +
                "<item>\n" +
                "<title>Обновление Windows сломало популярные антивирусы</title>\n" +
                "<link>https://news.yandex.ru/story/Obnovlenie_Windows_slomalo_populyarnye_antivirusy--d0434a4498f37a62895b5ed2426db71f?lang=ru&amp;from=rss&amp;stid=F8THmoWKOF0m</link>\n" +
                "<guid>https://news.yandex.ru/story/Obnovlenie_Windows_slomalo_populyarnye_antivirusy--d0434a4498f37a62895b5ed2426db71f?lang=ru&amp;from=rss&amp;stid=F8THmoWKOF0m</guid>\n" +
                "<description>Причина всех бед кроется как минимум в двух пакетах обновлений – KB4493446 под Windows 8.1 и KB4493472 для Windows 7. Специалисты McAfee и Avast уже установили, что они вносят определенные изменения в сервис Windows Client Server Runtime Subsystem (CRSS.exe), которые и конфликтуют со сторонними антивирусами.</description>\n" +
                "<pubDate>24 Apr 2019 09:22:10 +0000</pubDate>\n" +
                "</item>\n" +
                "</channel>\n" +
                "</rss>";
    }
}

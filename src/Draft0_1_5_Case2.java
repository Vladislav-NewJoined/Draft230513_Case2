import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

/* 11.	Сохраните снимки NASA за январь 2022 года*/
// /Фото NASA здесь: https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY

// Задание здесь: https://lms.synergy.ru/learning/view/68552/?groupPeriodId=1045153&disciplineVersionId=25858&start=1
// Видеоурок здесь: https://lms.synergy.ru/learning/view/68494

public class Draft0_1_5_Case2 {
    public static void main(String[] args) throws IOException {
        System.out.println("Задание: \n11.\tСохраните снимки NASA за январь 2022 года\n");
        System.out.println("Решение: ");
        System.out.println("Создаем новую директорию, 'NASA_Photos_Of_January_2022', куда будем сохранять фотографии,\n" +
                "по адресу: 'C:\\Users\\PC\\IdeaProjects\\Java230429_Module2_Urok2\\NASA_Photos_Of_January_2022,\n" +
                "т.е. в корневом каталоге проекта.\n");

        // Создаем список дат января 2022
        LocalDate ld = LocalDate.of(2022, 1, 1);
        List<String> datesOfJan2022 = new ArrayList<>();
        do {
            datesOfJan2022.add(ld.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            ld = ld.plusDays(1);
        } while (ld.getDayOfMonth() > 1);
        System.out.println(); // Добавляем пустую строку, как разделитель

// Далее перебираем массив ArrayList с датами января 2022 г.
        System.out.println(); // Добавляем пустую строку, как разделитель
        for (int i = 1; i <= datesOfJan2022.size(); i++) {
            String currentDate = datesOfJan2022.get(i-1);

//        Чтобы получить url страницы с нужным нам кодом, берем нужную нам дату, например 2022-01-12 перед ней дописываем '&date='
//        и склеиваем с https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY , т.е.
            String PageWithCodeOfCurrentDate = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY" + "&date=" + currentDate;
            String currentCodeItself = downloadWebPage(PageWithCodeOfCurrentDate);

            int urlBegin = currentCodeItself.lastIndexOf(",\"url");
            int urlEnd = currentCodeItself.lastIndexOf("}");
            String urlOfCurrentPhoto = currentCodeItself.substring(urlBegin + 8, urlEnd - 1);
            /*System.out.println(urlOfCurrentPhoto);*/
            try (InputStream in = new URL(urlOfCurrentPhoto).openStream()) {

                Files.copy(in, Paths.get("NASA_Photos_Of_January_2022\\" + "new" + i + ".jpg"), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException exception) {
                System.out.println("Input/Output error");
            }
        }
        System.out.println("Фотографии NASA за январь 2022 г. сохранены в папке NASA_Photos_Of_January_2022.");
    }

    private static String downloadWebPage (String url) throws IOException {
        StringBuilder result = new StringBuilder();
        String line;
        URLConnection urlConnection = new URL(url).openConnection();
        try (InputStream is = urlConnection.getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        }
        return result.toString();
    }
}

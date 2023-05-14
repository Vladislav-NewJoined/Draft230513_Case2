import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.lang.System.out;

//    Задание здесь: https://lms.synergy.ru/practicums/attempt/3820593/1?groupPeriodId=1045153

public class Draft0_1_Case2 {
//        System.out.println("Задание: \nПользователь вводит месяц и год.\n" +
//                "Скачать все снимки за месяц в папку.\n" +
//                "Сгенерировать html страницу в этой папке, которая отобразит все скачанные снимки на одной странице. Пример:\n" +
//                "<img src=“1.png”/>\n" +
//                "<img src=“2.png”/>\n\nРешение: ");

//  Пример решения здесь: Модуль 2. Урок 2. Задание 11. Сохраните снимки NASA за январь 2022 года
//Я на api.nasa.gov
//230507
//    NASA
//    https://api.nasa.gov/
//    вход здесь:	https://api.nasa.gov/
//    логин:
//    пароль:
//    почта:	sozin.vladislav@gmail.com
//    Account Email: sozin.vladislav@gmail.com
//    Account ID: 99764091-0934-4636-a9fe-1a8342e58b22
//    Мой API key
//    Your API key for sozin.vladislav@gmail.com is:
//    jydXMU7fbHsLGVK6AN47isg2XJQUitZUHc29bRZR
//    How to use the NASA API



    //        Пример 3 ППППППППППППППППППППППППППППППППППП  Из Модуль 2, Урок 2, Задание 11 + Case 3_1_1
    public static void main(String[] args) throws IOException, ParseException {

        // Создаем список дат за введённый месяц (из задания Курс валют за месяц Case3_1)
        BufferedReader buffered = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введите исходные месяц и год с разделителем '/', пример: 03/2023: ");
        String origMonth = buffered.readLine();  // Start month
        System.out.println();

        // Делаем парсинг введённой строки методом Split.
        String[] items = origMonth.split("/");
        String mon = items[0];
        String yea = items[1];

        int monI = Integer.parseInt(mon);
        int yeaI = Integer.parseInt(yea);

        // Преобразовываем ввод через переменную YearMonth.
        YearMonth ym = YearMonth.of(yeaI, monI);

        int lastDay = ym.lengthOfMonth();

        //    loop 1 through the days
        List<String> list_Of_Dates_of_Entered_Month = new ArrayList<>();
        for (int day = 1; day <= lastDay; day++) {
            // create the day
            LocalDate dt = ym.atDay(day);

            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dtStr = dt.format(f);
            // set to midnight at JVM default timezone
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(dtStr));
            String currentDate;
            currentDate = sdf.format(c.getTime());  // entering current Date

            // Приводим currentDate к формату LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(currentDate, formatter);
            list_Of_Dates_of_Entered_Month.add(String.valueOf(localDate));

            // Меняем в адресе исходной страницы дату на текующую.


        }
        System.out.println(list_Of_Dates_of_Entered_Month);
        System.out.println(); /* Добавляем пустую строку, как разделитель*/

// Создали список дат введенного месяца. Теперь делаем запросы на скачивание фотографий NASA за указанные даты месяца
/* Создаем новую директорию, 'NASA_Photos_Of_Month', куда будем сохранять фотографии,
   по адресу: 'C:\Users\PC\IdeaProjectsDrafts\Draft230513_Case2\NASA_Photos_Of_Month',
   т.е. в корневом каталоге проекта. */

// Далее перебираем массив ArrayList, list_Of_Dates_of_Entered_Month с датами введенного месяца
        for (int i = 1; i <= 2 /*list_Of_Dates_of_Entered_Month.size()*/; i++) {
            String currentDate = list_Of_Dates_of_Entered_Month.get(i - 1);
            System.out.println(currentDate);

//        Чтобы получить url страницы с нужным нам кодом, берем нужную нам дату, например 2022-01-12 перед ней дописываем '&date='
//        и склеиваем с https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY , т.е.
            String PageWithCodeOfCurrentDate = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY" + "&date=" + currentDate;
            String currentCodeItself = downloadWebPage(PageWithCodeOfCurrentDate);
            System.out.println(PageWithCodeOfCurrentDate);
            System.out.println(currentCodeItself);

            int urlBegin = currentCodeItself.lastIndexOf(",\"url");
            int urlEnd = currentCodeItself.lastIndexOf("}");
            String urlOfCurrentPhoto = currentCodeItself.substring(urlBegin + 8, urlEnd - 1);
            System.out.println(urlOfCurrentPhoto);
            try (InputStream in = new URL(urlOfCurrentPhoto).openStream()) {
                /*try *//*(InputStream in = (InputStream) Paths.get("NASA_Input\\input.jpg"))*//* {*/


                Files.copy(in, Paths.get("NASA_Photos_Of_Month\\" + "new" + i + ".jpg"), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException exception) {
                out.println("Input/Output error");
            }



        }

    /*Далее как записать все фото в один файл, полезное инфо брать отсюда:
    33.33.3
    И ЗДЕСЬ ( ЗДЕСЬ САМОЕ ЛУЧШЕЕ , САНЯ ) :
    https://youtu.be/WU0BXXZP3WE	- здесь с записью на другой строке, ( т.е. без перезаписи строк и с перезаписью строк ) , Саня
    https://www.youtube.com/watch?v=WU0BXXZP3WE&t=11s*/




//        // Создаем список дат января 2022 (из задания Скачать все фото NASA за январь 2022)
//        LocalDate ld = LocalDate.of(2022, 1, 1);
//        List<String> datesOfJan2022 = new ArrayList<>();
//        do {
//            System.out.println(ld.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//            datesOfJan2022.add(ld.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//            ld = ld.plusDays(1);
//        } while (ld.getDayOfMonth() > 1);  // arrive at 1st of next month
//        System.out.println(); /* Добавляем пустую строку как разделитель*/
//        System.out.println(datesOfJan2022);
//// Список дат января 2022 г. сформировали. Далее брать инфо из Примера 8.
//
///* Создаем новую директорию, 'NASA_Photos_Of_January_2022', куда будем сохранять фотографии,
//   по адресу: 'C:\Users\PC\IdeaProjectsDrafts\Draft230429_Module2_Urok2\NASA_Photos_Of_January_2022',
//   т.е. в корневом каталоге проекта. */



// Далее перебираем массив ArrayList с датами января 2022 г.
        System.out.println();
//        for (int i = 1; i <= 2 /*datesOfJan2022.size()*/; i++) {
//            String currentDate = datesOfJan2022.get(i-1);
//            System.out.println(currentDate);
//
////        Чтобы получить url страницы с нужным нам кодом, берем нужную нам дату, например 2022-01-12 перед ней дописываем '&date='
////        и склеиваем с https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY , т.е.
//            String PageWithCodeOfCurrentDate = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY" + "&date=" + currentDate;
//            String currentCodeItself = downloadWebPage(PageWithCodeOfCurrentDate);
//            System.out.println(PageWithCodeOfCurrentDate);
//            System.out.println(currentCodeItself);
//
//
//
//            int urlBegin = currentCodeItself.lastIndexOf(",\"url");
//            int urlEnd = currentCodeItself.lastIndexOf("}");
//            String urlOfCurrentPhoto = currentCodeItself.substring(urlBegin + 8, urlEnd - 1);
//            System.out.println(urlOfCurrentPhoto);
//            try (InputStream in = new URL(urlOfCurrentPhoto).openStream()) {
//                /*try *//*(InputStream in = (InputStream) Paths.get("NASA_Input\\input.jpg"))*//* {*/
//
//
//                Files.copy(in, Paths.get("NASA_Photos_Of_Month\\" + "new" + i + ".jpg"), StandardCopyOption.COPY_ATTRIBUTES);
//            } catch (IOException exception) {
//                out.println("Input/Output error");
//            }
//        }
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


//        Конец Примера 3 КККККККККККККККК










//    //        Пример 2 ППППППППППППППППППППППППППППППППППП  Из Модуль 2, Урок 2, Задание 11.
//    public static void main(String[] args) throws IOException {
////        System.out.println("Задание: \n11.\tСохраните снимки NASA за январь 2022 года\n");
////        System.out.println("Решение: ");
////        System.out.println("Создаем новую директорию, 'NASA_Photos_Of_January_2022', куда будем сохранять фотографии,\n" +
////                "по адресу: 'C:\\Users\\PC\\IdeaProjectsDrafts\\Draft230429_Module2_Urok2\\NASA_Photos_Of_January_2022',\n" +
////                "т.е. в корневом каталоге проекта.\n");
//
//        // Создаем список дат января 2022
//        LocalDate ld = LocalDate.of(2022, 1, 1);
//        List<String> datesOfJan2022 = new ArrayList<>();
//        do {
//            System.out.println(ld.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//            datesOfJan2022.add(ld.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//            ld = ld.plusDays(1);
//        } while (ld.getDayOfMonth() > 1);  // arrive at 1st of next month
//        System.out.println(); // Добавляем пустую строку, как разделитель
//        System.out.println(datesOfJan2022);
//// Список дат января 2022 г. сформировали. Далее брать инфо из Примера 8.
//
///* Создаем новую директорию, 'NASA_Photos_Of_January_2022', куда будем сохранять фотографии,
//   по адресу: 'C:\Users\PC\IdeaProjectsDrafts\Draft230429_Module2_Urok2\NASA_Photos_Of_January_2022',
//   т.е. в корневом каталоге проекта. */
//
//
//
//// Далее перебираем массив ArrayList с датами января 2022 г.
//        System.out.println();
//        for (int i = 1; i <= datesOfJan2022.size(); i++) {
//            String currentDate = datesOfJan2022.get(i-1);
//            System.out.println(currentDate);
//
////        Чтобы получить url страницы с нужным нам кодом, берем нужную нам дату, например 2022-01-12 перед ней дописываем '&date='
////        и склеиваем с https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY , т.е.
//            String PageWithCodeOfCurrentDate = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY" + "&date=" + currentDate;
//            String currentCodeItself = downloadWebPage(PageWithCodeOfCurrentDate);
//            System.out.println(PageWithCodeOfCurrentDate);
//            System.out.println(currentCodeItself);
//
//
//
//            int urlBegin = currentCodeItself.lastIndexOf(",\"url");
//            int urlEnd = currentCodeItself.lastIndexOf("}");
//            String urlOfCurrentPhoto = currentCodeItself.substring(urlBegin + 8, urlEnd - 1);
//            System.out.println(urlOfCurrentPhoto);
//            try (InputStream in = new URL(urlOfCurrentPhoto).openStream()) {
//                /*try *//*(InputStream in = (InputStream) Paths.get("NASA_Input\\input.jpg"))*//* {*/
//
//
//                Files.copy(in, Paths.get("NASA_Photos_Of_January_2022\\" + "new" + i + ".jpg"), StandardCopyOption.COPY_ATTRIBUTES);
//            } catch (IOException exception) {
//                out.println("Input/Output error");
//            }
//        }
//    }
//
//    private static String downloadWebPage (String url) throws IOException {
//        StringBuilder result = new StringBuilder();
//        String line;
//        URLConnection urlConnection = new URL(url).openConnection();
//        try (InputStream is = urlConnection.getInputStream();
//             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
//            while ((line = br.readLine()) != null) {
//                result.append(line);
//            }
//        }
//        return result.toString();
//    }
//
//
////        Конец Примера 2 КККККККККККККККК






////        Пример 1 ППППППППППППППППППППППППППППППППППП
//    public static void main(String[] args) throws IOException {
//
//        String pageNasa = downloadWebPage("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY");
//        int urlBegin = pageNasa.lastIndexOf("url");
//        int urlEnd = pageNasa.lastIndexOf("}");
//        String urlPhoto = pageNasa.substring(urlBegin + 6, urlEnd - 1);
//        try (InputStream in = new URL(urlPhoto).openStream()) {
//            Files.copy(in, Paths.get("new.jpg"));
//        }
//
//        System.out.println("\n" + "Картинка сохранена!");
//
//        int explanationBegin = pageNasa.lastIndexOf("explanation");
//        int explanationEnd = pageNasa.lastIndexOf("hdurl");
//        String explanation = pageNasa.substring(explanationBegin + 14, explanationEnd - 3);
//        System.out.println(explanation);
//
//    }
//
//    private static String downloadWebPage(String url) throws IOException {
//        StringBuilder result = new StringBuilder();
//        String line;
//        URLConnection urlConnection = new URL(url).openConnection();
//        try (InputStream is = urlConnection.getInputStream();
//             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
//            while ((line = br.readLine()) != null) {
//                result.append(line);
//            }
//        }
//        return result.toString();
//    }
//
////        Конец Примера 1 КККККККККККККККК









}

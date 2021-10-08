package ru.alpha.example.gifcurrencygradle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.alpha.example.gifcurrencygradle.entites.CurrencyInfoMap;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public class Test1 {
    public static void main(String[] args) throws JsonProcessingException {
        String json = "{\"base\":\"USD\",\"rates\":{\"AED\":3.67295,\"AFN\":90.649999,\"ALL\":104.925,\"AMD\":484.70578,\"ANG\":1.795269,\"AOA\":598.775,\"ARS\":98.854867,\"AUD\":1.368466,\"AWG\":1.8005,\"AZN\":1.700805,\"BAM\":1.691675,\"BBD\":2,\"BDT\":85.576095,\"BGN\":1.689971,\"BHD\":0.377011,\"BIF\":1996.5,\"BMD\":1,\"BND\":1.356139,\"BOB\":6.910868,\"BRL\":5.5117,\"BSD\":1,\"BTC\":0.000018443455,\"BTN\":75.057212,\"BWP\":11.27544,\"BYN\":2.491478,\"BZD\":2.016045,\"CAD\":1.246529,\"CDF\":2015,\"CHF\":0.927053,\"CLF\":0.029899,\"CLP\":825.35,\"CNH\":6.446954,\"CNY\":6.4433,\"COP\":3767.33,\"CRC\":626.273038,\"CUC\":1,\"CUP\":25.75,\"CVE\":95.65,\"CZK\":22.0018,\"DJF\":178.05,\"DKK\":6.432413,\"DOP\":56.45,\"DZD\":137.409761,\"EGP\":15.6965,\"ERN\":15.002477,\"ETB\":46.48,\"EUR\":0.864474,\"FJD\":2.1055,\"FKP\":0.734216,\"GBP\":0.734216,\"GEL\":3.125,\"GGP\":0.734216,\"GHS\":6.045,\"GIP\":0.734216,\"GMD\":51.5,\"GNF\":9750,\"GTQ\":7.741037,\"GYD\":209.073187,\"HKD\":7.78402,\"HNL\":24.21,\"HRK\":6.4992,\"HTG\":99.017211,\"HUF\":311.581687,\"IDR\":14219.988837,\"ILS\":3.23301,\"IMP\":0.734216,\"INR\":75.190944,\"IQD\":1459.5,\"IRR\":42193.749994,\"ISK\":129.17,\"JEP\":0.734216,\"JMD\":148.514474,\"JOD\":0.709,\"JPY\":112.171,\"KES\":110.7,\"KGS\":84.802173,\"KHR\":4097,\"KMF\":426.600099,\"KPW\":900,\"KRW\":1195.929948,\"KWD\":0.301546,\"KYD\":0.833456,\"KZT\":425.516167,\"LAK\":10080,\"LBP\":1526,\"LKR\":200.025884,\"LRD\":170.499997,\"LSL\":14.94,\"LYD\":4.56,\"MAD\":9.07,\"MDL\":17.365729,\"MGA\":3912.5,\"MKD\":53.294407,\"MMK\":1961.75621,\"MNT\":2847.028893,\"MOP\":8.01998,\"MRO\":356.999828,\"MRU\":36.3,\"MUR\":42.6,\"MVR\":15.425,\"MWK\":817.5,\"MXN\":20.72,\"MYR\":4.178,\"MZN\":63.799995,\"NAD\":14.96,\"NGN\":410.81,\"NIO\":35.225,\"NOK\":8.550655,\"NPR\":120.091682,\"NZD\":1.442918,\"OMR\":0.384998,\"PAB\":1,\"PEN\":4.09,\"PGK\":3.535,\"PHP\":50.602001,\"PKR\":170.5,\"PLN\":3.985008,\"PYG\":6903.524293,\"QAR\":3.64075,\"RON\":4.2789,\"RSD\":101.675865,\"RUB\":73.8766,\"RWF\":1000,\"SAR\":3.750449,\"SBD\":8.067802,\"SCR\":13.33858,\"SDG\":441.5,\"SEK\":8.736984,\"SGD\":1.3548,\"SHP\":0.734216,\"SLL\":10575.34999,\"SOS\":585,\"SRD\":21.399,\"SSP\":130.26,\"STD\":20944.990504,\"STN\":21.4,\"SVC\":8.751649,\"SYP\":1257.39274,\"SZL\":14.94,\"THB\":33.917496,\"TJS\":11.324208,\"TMT\":3.505,\"TND\":2.832,\"TOP\":2.2638,\"TRY\":8.9606,\"TTD\":6.787732,\"TWD\":28.076498,\"TZS\":2304,\"UAH\":26.349695,\"UGX\":3581.520465,\"USD\":1,\"UYU\":43.305117,\"UZS\":10685,\"VES\":4.15,\"VND\":22748.060957,\"VUV\":112.074866,\"WST\":2.571435,\"XAF\":567.057512,\"XAG\":0.04396571,\"XAU\":0.00056829,\"XCD\":2.70255,\"XDR\":0.709187,\"XOF\":567.057512,\"XPD\":0.00047998,\"XPF\":103.159141,\"XPT\":0.00097372,\"YER\":250.249998,\"ZAR\":14.9422,\"ZMW\":17.12756,\"ZWL\":322}}";
        String json2 = "{\"base\":\"USD\",\"rates\":{\"AED\":3.47295,\"AFN\":90.649999,\"ALL\":104.925,\"AMD\":484.70578,\"ANG\":1.795269,\"AOA\":598.775,\"ARS\":98.854867,\"AUD\":1.368466,\"AWG\":1.8005,\"AZN\":1.700805,\"BAM\":1.691675,\"BBD\":2,\"BDT\":85.576095,\"BGN\":1.689971,\"BHD\":0.377011,\"BIF\":1996.5,\"BMD\":1,\"BND\":1.356139,\"BOB\":6.910868,\"BRL\":5.5117,\"BSD\":1,\"BTC\":0.000018443455,\"BTN\":75.057212,\"BWP\":11.27544,\"BYN\":2.491478,\"BZD\":2.016045,\"CAD\":1.246529,\"CDF\":2015,\"CHF\":0.927053,\"CLF\":0.029899,\"CLP\":825.35,\"CNH\":6.446954,\"CNY\":6.4433,\"COP\":3767.33,\"CRC\":626.273038,\"CUC\":1,\"CUP\":25.75,\"CVE\":95.65,\"CZK\":22.0018,\"DJF\":178.05,\"DKK\":6.432413,\"DOP\":56.45,\"DZD\":137.409761,\"EGP\":15.6965,\"ERN\":15.002477,\"ETB\":46.48,\"EUR\":0.864474,\"FJD\":2.1055,\"FKP\":0.734216,\"GBP\":0.734216,\"GEL\":3.125,\"GGP\":0.734216,\"GHS\":6.045,\"GIP\":0.734216,\"GMD\":51.5,\"GNF\":9750,\"GTQ\":7.741037,\"GYD\":209.073187,\"HKD\":7.78402,\"HNL\":24.21,\"HRK\":6.4992,\"HTG\":99.017211,\"HUF\":311.581687,\"IDR\":14219.988837,\"ILS\":3.23301,\"IMP\":0.734216,\"INR\":75.190944,\"IQD\":1459.5,\"IRR\":42193.749994,\"ISK\":129.17,\"JEP\":0.734216,\"JMD\":148.514474,\"JOD\":0.709,\"JPY\":112.171,\"KES\":110.7,\"KGS\":84.802173,\"KHR\":4097,\"KMF\":426.600099,\"KPW\":900,\"KRW\":1195.929948,\"KWD\":0.301546,\"KYD\":0.833456,\"KZT\":425.516167,\"LAK\":10080,\"LBP\":1526,\"LKR\":200.025884,\"LRD\":170.499997,\"LSL\":14.94,\"LYD\":4.56,\"MAD\":9.07,\"MDL\":17.365729,\"MGA\":3912.5,\"MKD\":53.294407,\"MMK\":1961.75621,\"MNT\":2847.028893,\"MOP\":8.01998,\"MRO\":356.999828,\"MRU\":36.3,\"MUR\":42.6,\"MVR\":15.425,\"MWK\":817.5,\"MXN\":20.72,\"MYR\":4.178,\"MZN\":63.799995,\"NAD\":14.96,\"NGN\":410.81,\"NIO\":35.225,\"NOK\":8.550655,\"NPR\":120.091682,\"NZD\":1.442918,\"OMR\":0.384998,\"PAB\":1,\"PEN\":4.09,\"PGK\":3.535,\"PHP\":50.602001,\"PKR\":170.5,\"PLN\":3.985008,\"PYG\":6903.524293,\"QAR\":3.64075,\"RON\":4.2789,\"RSD\":101.675865,\"RUB\":71.8766,\"RWF\":1000,\"SAR\":3.750449,\"SBD\":8.067802,\"SCR\":13.33858,\"SDG\":441.5,\"SEK\":8.736984,\"SGD\":1.3548,\"SHP\":0.734216,\"SLL\":10575.34999,\"SOS\":585,\"SRD\":21.399,\"SSP\":130.26,\"STD\":20944.990504,\"STN\":21.4,\"SVC\":8.751649,\"SYP\":1257.39274,\"SZL\":14.94,\"THB\":33.917496,\"TJS\":11.324208,\"TMT\":3.505,\"TND\":2.832,\"TOP\":2.2638,\"TRY\":8.9606,\"TTD\":6.787732,\"TWD\":28.076498,\"TZS\":2304,\"UAH\":26.349695,\"UGX\":3581.520465,\"USD\":1,\"UYU\":43.305117,\"UZS\":10685,\"VES\":4.15,\"VND\":22748.060957,\"VUV\":112.074866,\"WST\":2.571435,\"XAF\":567.057512,\"XAG\":0.04396571,\"XAU\":0.00056829,\"XCD\":2.70255,\"XDR\":0.709187,\"XOF\":567.057512,\"XPD\":0.00047998,\"XPF\":103.159141,\"XPT\":0.00097372,\"YER\":250.249998,\"ZAR\":14.9422,\"ZMW\":17.12756,\"ZWL\":322}}";

        ObjectMapper mapper = new ObjectMapper();

        CurrencyInfoMap map1 = mapper.readValue(json, CurrencyInfoMap.class);
        CurrencyInfoMap map2 = mapper.readValue(json2, CurrencyInfoMap.class);

        BigDecimal rubValue1 = map1.getValueForCurrencyName("RUB");
        BigDecimal rubValue2 = map2.getValueForCurrencyName("RUB");

        System.out.println(result(rubValue2, rubValue1));
        System.out.println(result(map1.getValueForCurrencyName("AED"), map2.getValueForCurrencyName("AED")));
    }

    static BigDecimal result(BigDecimal oldValue, BigDecimal newValue) {
        if (oldValue.compareTo(newValue) < 0) {
            return newValue
                    .subtract(oldValue)
                    .divide(oldValue, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100L))
                    .negate();
        } else {
            return oldValue
                    .subtract(newValue)
                    .divide(oldValue, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100L));
        }
    }
}

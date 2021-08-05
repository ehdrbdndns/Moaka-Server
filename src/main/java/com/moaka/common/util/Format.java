package com.moaka.common.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * 형 변환에 관련된 클래스
 */
public class Format {
    /**
     * Example() 참조
     * 패턴 -> 의미
     * 예
     * *********
     * 0 -> 10진수, 빈자리는 0으로 채운다.
     * 0
     * 0.0
     * 000.000
     * *********
     * # -> 10진주, 빈자리는 채우지 않는다.
     * #
     * #.##
     * ####.###
     * *********
     * . -> 소수점 표시
     * ###.##
     * ###.00
     * 00.00
     * *********
     * , -> 단위 구분 기호 표시
     * #,##.#
     * *********
     * +, - -> 음수, 양수 표시
     * +#.##
     * -#.##
     * *********
     * E -> 지수 문자
     * 0.0#00
     * *********
     * ; -> 양수와 음수 패턴을 모두 사용할 경우
     * 패턴 구분자
     * +#.##;-#.##
     * *********
     * % -> 100을 곱하고 %를 붙인다.
     * #.###%
     * *********
     * \u00A4 -> 통화 표시 \을 붙인다.
     * \u00A4####.##
     **/

    public static float DecimalFormat(float float_number, String form) {
        float f = float_number;
        DecimalFormat format = new DecimalFormat(form);
        String formatString = format.format(f);
        return Float.parseFloat(formatString);
    }


    /**
     * 10진수, 빈자리는 채우지 않는다.
     *
     * @param float_number : 1.24
     * @return 1
     */
    public static float FloatToOnePoint(float float_number) {
        float f = float_number;
        DecimalFormat format = new DecimalFormat("#");
        String changed_str_float = format.format(f);
        return Float.parseFloat(changed_str_float);
    }

    /**
     * 10진수, 빈자리는 채우지 않는다.
     *
     * @param float_number : 1.24
     * @return 1
     */
    public static float FloatToDoublePoint(float float_number) {
        float f = float_number;
        DecimalFormat format = new DecimalFormat(".##");
        String changed_str_float = format.format(f);
        return Float.parseFloat(changed_str_float);
    }

    /**
     * 10진수, 빈자리는 채우지 않는다.
     *
     * @param float_number : 1.24
     * @return 1
     */
    public static float FloatToTriplePoint(float float_number) {
        float f = float_number;
        DecimalFormat format = new DecimalFormat(".###");
        String changed_str_float = format.format(f);
        return Float.parseFloat(changed_str_float);
    }

    public void example() {
        // 변환할 값
        double n = 12345.6789;

        System.out.println("\n ======= 숫자형식 변환의 DecimalFormat 클래스 ======= \n");

        // 10진수 - 빈자리는 0으로 채움
        DecimalFormat df = new DecimalFormat("0");
        System.out.println(df.format(n));// 출력값 : 12346

        // 10진수 - 빈자리는 0으로 채움
        df = new DecimalFormat("0.0");
        System.out.println(df.format(n)); // 출력값 : 12345.7

        // 10진수 - 빈자리는 0으로 채움
        df = new DecimalFormat("000000.00000");
        System.out.println(df.format(n)); // 출력값 : 012345.67890

        // 10진수 - 빈자리는 채우지 않음
        df = new DecimalFormat("#");
        System.out.println(df.format(n)); // 출력값 : 12346

        // 10진수 - 빈자리는 채우지 않음
        df = new DecimalFormat("######.###");
        System.out.println(df.format(n)); // 출력값 : 12345.679

        // 10진수 - # 0 조합 / 단위구분을 위한 , 추가
        df = new DecimalFormat("#,###.00000");
        System.out.println(df.format(n)); // 출력값 : 12,345.67890

        // 100을 곱한 후 문자에 % 붙임
        df = new DecimalFormat("#.##%");
        System.out.println(df.format(n)); // 출력값 : 1234567.89%

        // 통화표시 \u00A4
        df = new DecimalFormat("\u00A4####.##");
        System.out.println(df.format(n)); // 출력값 : ￦12345.68

        // NumberFormat을 이용하여 통화표시를 지정할 수 있다.
        // Locale을 사용하여 국가 지정시 해당 국가의 통화기호 및 자리수 구분까지 된다.
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.KOREA);
        // 소수점 2자리까지 지정
        nf.setMaximumFractionDigits(2);
        System.out.println(nf.format(n)); // 출력값 : ￦12,345.68

    }
}

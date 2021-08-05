package com.moaka.common.util;

import java.util.Random;

/**String 생성에 관련된 Class*/
public class TokenGenerator {

    /**
     * @param tokenSize : Token을 구성할 사이즈
     * @return Token : 해당 파라미터 자리수의 Token 반환
     */
    public static String RandomToken(int tokenSize) {
        if (tokenSize >= 1) {
            Random rnd = new Random();
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < tokenSize; i++) {
                // rnd.nextBoolean() 는 랜덤으로 true, false 를 리턴. true일 시 랜덤 한 소문자를, false 일 시 랜덤 한 숫자를 StringBuffer 에 append 한다.
                if (rnd.nextBoolean()) {
                    buf.append((char) ((int) (rnd.nextInt(26)) + 97));
                } else {
                    buf.append((rnd.nextInt(10)));
                }
            }
            return buf.toString();
        } else {
            return null;
        }
    }
}

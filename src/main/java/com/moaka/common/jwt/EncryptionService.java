package com.moaka.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.moaka.common.exception.ErrorCode;
import com.moaka.common.exception.NotFoundException;
import com.moaka.common.util.Time;
import com.moaka.dto.User;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class EncryptionService {

    public JWTToken decodeJWT(String encryptedJWT) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(encryptedJWT);
            int no = jwt.getClaim("no").asInt();
            String id = jwt.getClaim("id").asString();
            String pwd = jwt.getClaim("pwd").asString();
            String sub = jwt.getClaim("sub").asString();
            int age = jwt.getClaim("age").asInt();
            String name = jwt.getClaim("name").asString();
            String profile = jwt.getClaim("profile").asString();
            String auth_type = jwt.getClaim("auth_type").asString();
            String regdate = jwt.getClaim("regdate").asString();
            String version = jwt.getClaim("version").asString();

            JWTToken jwtToken = new JWTToken();
            jwtToken.setNo(no);
            jwtToken.setId(id);
            jwtToken.setPwd(pwd);
            jwtToken.setSub(sub);
            jwtToken.setAge(age);
            jwtToken.setName(name);
            jwtToken.setProfile(profile);
            jwtToken.setAuth_type(auth_type);
            jwtToken.setRegdate(regdate);
            jwtToken.setVersion(version);

            return jwtToken;
        } catch (NullPointerException e) {
            throw new NotFoundException(ErrorCode.JWT_NOT_FOUND.getErrorCode(), ErrorCode.JWT_NOT_FOUND.getErrorMessage());
        }
    }

    public String encryptionJWT(User user) throws NoSuchAlgorithmException {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withExpiresAt(Time.LongTimeStamp())
                .withClaim("no", user.getNo())
                .withClaim("id", user.getId())
                .withClaim("pwd", user.getPwd())
                .withClaim("sub", user.getSub())
                .withClaim("age", user.getAge())
                .withClaim("name", user.getName())
                .withClaim("profile", user.getProfile())
                .withClaim("auth_type", user.getAuth_type())
                .withClaim("regdate", user.getRegdate())
                .withClaim("version", JWTConstant.VERSION)
                .withIssuer("auth0")
                .sign(algorithm);
    }

    /**
     * SHA-256으로 해싱하는 메소드
     *
     * @param msg
     * @return bytes
     * @throws NoSuchAlgorithmException
     */
    public String encryptionSHA256(String msg) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(msg.getBytes());
        return bytesToHex(md.digest());
    }

    /**
     * 바이트를 헥스값으로 변환한다.
     *
     * @param bytes
     * @return
     */
    public String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

}

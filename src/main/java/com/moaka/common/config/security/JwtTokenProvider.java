package com.moaka.common.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    // 토큰 유효시간 5시간
    private long tokenValidTime = 5 * 60 * 60 * 1000L;

    private final UserDetailsService userDetailsService;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String userPk, List<String> roles, int no, String name, String profile, ArrayList<String> categoryList) {
        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
        claims.put("no", no);
        claims.put("id", userPk);
        claims.put("name", name);
        claims.put("profile", profile);
        claims.put("category", categoryList);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        System.out.println("toeken body");
        System.out.println(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody());
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // 토큰에서 회원 번호 추출
    public int getUserNo(String token) {
        return (int) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("no");
    }

    // 토큰에서 회원 이름 추출
    public String getUserName(String token) {
        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("name");
    }

    // 토큰에서 회원 프로필 이미지(CDN URL) 추출
    public String getUserProfile(String token) {
        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("profile");
    }

    public JSONObject setUser(String token) {
        JSONObject result = new JSONObject();
        result.put("no", Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("no"));
        result.put("id", Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("id"));
        result.put("name", Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("name"));
        result.put("profile", Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("profile"));
        result.put("category", Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("category"));
        return result;
    }

    // Request의 Header에서 token 값을 가져옵니다. "Bearer" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        System.out.println("토큰 확인 중...");
        return request.getHeader("Bearer");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
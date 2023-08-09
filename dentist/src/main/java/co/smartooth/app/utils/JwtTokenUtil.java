package co.smartooth.app.utils;

import java.util.Base64;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import co.smartooth.app.vo.AuthVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
    
@Component
public class JwtTokenUtil{ 

	@Value("${spring.jwt.secretKey}")
	private static String secretKey;
	

	//암복호화에 사용되는 키 값 입니다.
    private static String base64secretKey = Base64.getEncoder().encodeToString("smartooth".getBytes());


	//인증 토큰 생성 (authVO)
    public String createToken(AuthVO authVO) {
        Claims claims = Jwts.claims();  //나중에 서버에서 파싱해서 볼 데이터 입니다.
        claims.put("userId", authVO.getUserId());  //아이디
        // claims.put("roles", new Object() );  //객체도 가능합니다 ^^
        Date now = new Date();
        
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) 
                .setClaims(claims) // 데이터를 넣어 줍니다
                .setIssuedAt(now)   // 토큰 발행 일자
                .setExpiration(new Date(now.getTime() + (1000L * 60 * 240) + 321)) // 만료 기간 입니다 :: 해당 시간은 4시간 :: 시간단위로 쪼갤 경우 보안의 위험이 있어 +321 추가
                .signWith(SignatureAlgorithm.HS256, base64secretKey) // 암호화 알고리즘과 암복호화에 사용할 키를 넣어줍니다
                .compact(); // Token 생성
    }
    
    
    
    //이메일 기간 만료 토큰 생성
    public String createToken(String userId) {
        Claims claims = Jwts.claims();  //나중에 서버에서 파싱해서 볼 데이터 입니다.
        claims.put("userId", userId);  //아이디
        // claims.put("roles", new Object() );  //객체도 가능합니다 ^^
        Date now = new Date();
        
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) 
                .setClaims(claims) // 데이터를 넣어 줍니다
                .setIssuedAt(now)   // 토큰 발행 일자
                .setExpiration(new Date(now.getTime() + (1000L * 60 * 1440))) // 만료 기간 입니다 :: 해당 시간은 4시간 :: 시간단위로 쪼갤 경우 보안의 위험이 있어 +321 추가
                .signWith(SignatureAlgorithm.HS256, base64secretKey) // 암호화 알고리즘과 암복호화에 사용할 키를 넣어줍니다
                .compact(); // Token 생성
    }   


    // Jwt Token의 유효성 및 만료 기간 검사합니다
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(base64secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    
    // Jwt Token에서 데이터를 전달 합니다.
    public Claims getInformation(String token) {
        Claims claims =Jwts.parser().setSigningKey(base64secretKey).parseClaimsJws(token).getBody();
        return claims;
    }

    
}
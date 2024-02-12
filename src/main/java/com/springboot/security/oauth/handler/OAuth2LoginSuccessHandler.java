package com.springboot.security.oauth.handler;


import com.springboot.domain.member.entity.Member;
import com.springboot.domain.member.entity.MemberRepository;
import com.springboot.domain.member.entity.Role;
import com.springboot.security.jwt.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;



import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");
        try {
            OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
            String email = oAuth2User.getAttribute("email");

            boolean isExist = oAuth2User.getAttribute("exist");

            // isExist이 false일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
            if(!isExist) {
                String accessToken = jwtService.createAccessToken(email);
                response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
                response.sendRedirect("/login"); // 프론트의 회원가입 추가 정보 입력 폼으로 리다이렉트

                jwtService.sendAccessAndRefreshToken(response, accessToken, null);
                Member member = memberRepository.findByEmail(email)
                                .orElseThrow(() -> new IllegalArgumentException("이메일에 해당하는 유저가 없습니다."));
                member.authorizeUser();
                memberRepository.save(member);
                log.info("회원가입");
            } else {
                // 로그인에 성공한 경우 access, refresh 토큰 생성
                loginSuccess(response, oAuth2User);
                log.info("로그인");
            }
        } catch (Exception e) {
            throw e;
        }

    }

    // TODO : 소셜 로그인 시에도 무조건 토큰 생성하지 말고 JWT 인증 필터처럼 RefreshToken 유/무에 따라 다르게 처리해보기
    private void loginSuccess(HttpServletResponse response, OAuth2User oAuth2User) throws IOException {
        String email = oAuth2User.getAttribute("email");

        String accessToken = jwtService.createAccessToken(email);
        String refreshToken = jwtService.createRefreshToken();
        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(email, refreshToken);
    }
}

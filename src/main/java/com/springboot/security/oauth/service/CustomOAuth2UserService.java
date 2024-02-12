package com.springboot.security.oauth.service;

import com.springboot.domain.member.entity.Member;
import com.springboot.domain.member.entity.MemberRepository;
import com.springboot.security.oauth.dto.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Member user = memberRepository.findByEmail(attributes.getEmail()).orElse(null);

        // OAuth2Attribute의 속성값들을 Map으로 반환 받는다.
        Map<String, Object> memberAttribute = attributes.convertToMap();

        System.out.println(user);
        if (user == null) {
            user = saveUser(attributes);
            memberAttribute.put("exist", false);
        } else {
            memberAttribute.put("exist", true);
        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                memberAttribute,
                "email");
    }

    private Member saveUser(OAuthAttributes attributes) {
        Member user = attributes.toEntity();
        return memberRepository.save(user);
    }
}

package com.techstore.techstore_backend.modules.auth.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.techstore.techstore_backend.modules.auth.dto.request.AuthenticationRequest;
import com.techstore.techstore_backend.modules.auth.dto.request.IntrospectRequest;
import com.techstore.techstore_backend.modules.auth.dto.request.LogoutRequest;
import com.techstore.techstore_backend.modules.auth.dto.response.AuthenticationResponse;
import com.techstore.techstore_backend.modules.auth.dto.response.IntrospectResponse;
import com.techstore.techstore_backend.modules.auth.entity.InvalidatedToken;
import com.techstore.techstore_backend.modules.user.entity.User;
import com.techstore.techstore_backend.shared.exception.AppException;
import com.techstore.techstore_backend.shared.exception.ErrorCode;
import com.techstore.techstore_backend.modules.auth.repository.InvalidatedTokenRepository;
import com.techstore.techstore_backend.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final InvalidatedTokenRepository invalidatedTokenRepository;

    @Value("${jwt.secret}")
    protected String SECRET;

    @Value("${jwt.refresh_secret}")
    protected String REFRESH_SECRET;

    @Value("${jwt.valid_duration}")
    protected Long VALID_DURATION;

    @Value("${jwt.refresh_duration}")
    protected Long REFRESH_DURATION;

    public AuthenticationResponse login(AuthenticationRequest request){
        log.info("secret_key: {}",SECRET);
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean checkPass = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!checkPass){
            throw new AppException(ErrorCode.USER_NOT_EXIST);
        }
        String token = generateToken(user,false);
        String refreshToken = generateToken(user,true);
        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        boolean isValid = true;
        try {
            verifyToken(request.getToken(), false);
        } catch (AppException e) {
            isValid = false;
        }
        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    public void logout(LogoutRequest request) throws JOSEException, ParseException {
        try {
            SignedJWT signedJWT = verifyToken(request.getToken(), true);
            String jti = signedJWT.getJWTClaimsSet().getJWTID();
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jti)
                    .expirationTime(expirationTime)
                    .build();
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException e) {
            log.info("Token is expired");
        }

        try {
            SignedJWT signedJWT = verifyToken(request.getRefreshToken(), true);
            String jti = signedJWT.getJWTClaimsSet().getJWTID();
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            InvalidatedToken invalidatedRefreshToken = InvalidatedToken.builder()
                    .id(jti)
                    .expirationTime(expirationTime)
                    .build();
            invalidatedTokenRepository.save(invalidatedRefreshToken);
        } catch (AppException e) {
            log.info("Token is expired");
        }
    }

    public AuthenticationResponse refreshToken(String refreshToken) throws ParseException, JOSEException {
        SignedJWT signedJwt = verifyToken(refreshToken, true);
        String jti = signedJwt.getJWTClaimsSet().getJWTID();
        Date expirationTime = signedJwt.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jti)
                .expirationTime(expirationTime)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);
        String username = signedJwt.getJWTClaimsSet().getSubject();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        String token = generateToken(user, false);
        String newRefreshToken = generateToken(user, true);
        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .refreshToken(newRefreshToken)
                .build();
    }

    public SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = isRefresh
                ? new MACVerifier(REFRESH_SECRET.getBytes())
                : new MACVerifier(SECRET.getBytes());
        SignedJWT signedJwt = SignedJWT.parse(token);


        System.out.println(signedJwt.getHeader());
        System.out.println(signedJwt.getJWTClaimsSet());
        System.out.println(signedJwt.getSignature());

        boolean valid = signedJwt.verify(verifier);
        if (!valid) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        Date expirationTime = signedJwt.getJWTClaimsSet().getExpirationTime();
        if(!expirationTime.after(new Date())){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        if(invalidatedTokenRepository.existsById(signedJwt.getJWTClaimsSet().getJWTID())){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJwt;
    }


    public String generateToken(User user, boolean isRefresh) {
        Long time = isRefresh ? REFRESH_DURATION : VALID_DURATION;
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("ahuy.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(time, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            if (isRefresh) {
                jwsObject.sign(new MACSigner(REFRESH_SECRET.getBytes()));
            } else {
                jwsObject.sign(new MACSigner(SECRET.getBytes()));
            }
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> {
                        stringJoiner.add(permission.getName());
                    });
                }
            });
        }
        return stringJoiner.toString();
    }

}

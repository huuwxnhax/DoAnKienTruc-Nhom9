package vn.edu.iuh.fit.userservice.service;

import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.userservice.entity.Token;

@Service
public interface TokenService {
    Token createToken(Token token);

    Token findByToken(String token);
}

package movie.app.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    private final Map<String, String> tokenCache = new HashMap<>();

    public void cacheToken(String account, String token) {
        tokenCache.put(account, token);
    }

    public boolean verifyToken(String token) {
        return token != null && tokenCache.containsValue(token);
    }

    public String getAccountFromToken(String token) {
        return tokenCache.entrySet().stream()
                .filter(entry -> entry.getValue().equals(token))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    public void removeTokenFromCache(String account) {
        tokenCache.remove(account);
    }

    public boolean containsValue(String token) {
        return tokenCache.containsValue(token);
    }
}

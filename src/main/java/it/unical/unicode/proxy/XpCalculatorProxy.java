package it.unical.unicode.proxy;

import it.unical.unicode.model.Esercizio;
import org.springframework.stereotype.Component;

@Component
public class XpCalculatorProxy {
    public int calculateXp(Esercizio ex, long timeSeconds) {
        int base = switch (ex.getDifficulty()) {
            case "Easy" -> 10;
            case "Medium" -> 20;
            case "Hard" -> 40;
            default -> 5;
        };
        int penalty = (int) (timeSeconds / 30);
        return Math.max(5, base - penalty);
    }
}

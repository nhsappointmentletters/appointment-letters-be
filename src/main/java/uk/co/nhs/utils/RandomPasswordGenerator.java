package uk.co.nhs.utils;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RandomPasswordGenerator {
    private char[] special = new char[]{'!', '"', '#', '$', '%', '&', '\'', '(', ')', '*',
            '+', ',', '-', '.', '/', ':', '<', '=', '>', '?', '@', '[', '\\', ']',
            '^', '_', '`', '{', '|', '}', '~'};

    public String generate() {
        CharacterRule myCharacterRule = new CharacterRule(new CharacterData() {
            @Override
            public String getErrorCode() {
                return "INSUFFICIENT_SPECIAL";
            }

            @Override
            public String getCharacters() {
                return new String(special);
            }
        }, 2);
        List rules = Arrays.asList(
                new CharacterRule(EnglishCharacterData.UpperCase, 2),
                new CharacterRule(EnglishCharacterData.LowerCase, 2),
                new CharacterRule(EnglishCharacterData.Digit, 2),
                myCharacterRule);
        return new PasswordGenerator().generatePassword(8, rules);
    }
}

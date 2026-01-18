package org.inatel.cdg.utils;

import java.util.regex.Pattern;

public final class PetNameValidator
{
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 20;

    // letras, números, espaço, _ e -
    private static final Pattern VALID_NAME = Pattern.compile("^[a-zA-Z0-9 _-]+$");

    private PetNameValidator() {}

    public static String validate(String input)
    {
        if (input == null) throw new IllegalArgumentException("Nome inválido.");

        String name = input.trim().replaceAll("\\s+", " ");

        if (name.length() < MIN_LENGTH || name.length() > MAX_LENGTH) throw new IllegalArgumentException("O nome deve ter entre " + MIN_LENGTH + " e " + MAX_LENGTH + " caracteres.");

        if (!VALID_NAME.matcher(name).matches()) throw new IllegalArgumentException("O nome contém caracteres inválidos.");

        if (containsMentions(name)) throw new IllegalArgumentException("Menções não são permitidas no nome do pet.");

        return name;
    }

    private static boolean containsMentions(String name)
    {
        return name.contains("@") || name.contains("<@") || name.contains("@everyone") || name.contains("@here");
    }
}

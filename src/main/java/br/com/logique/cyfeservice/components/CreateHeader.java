package br.com.logique.cyfeservice.components;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuri on 14/06/2016.
 */
public class CreateHeader {

    /**
     * Create header to be added in the controller response.
     * @param strings Header text
     * @return List with the header pieces.
     */
    public static List<String> from(String... strings) {
        List<String> header = new ArrayList<>();
        for (String string : strings) {
            header.add(string);
        }
        return header;
    }
}

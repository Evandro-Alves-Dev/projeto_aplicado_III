package com.senai.pa3.config;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ConvertTimestamp {

    public static String formatarTimestamp(Timestamp timestamp) {
        // Criar um SimpleDateFormat com o formato desejado
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        // Formatar o Timestamp
        String formattedTimestamp = dateFormat.format(timestamp);

        return formattedTimestamp;
    }
}

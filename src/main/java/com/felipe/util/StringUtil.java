package com.felipe.util;

import java.util.UUID;

public class StringUtil {

    public static UUID convertStringToUUID(String stringUUID) {
    	return UUID.fromString(stringUUID);
    }
}

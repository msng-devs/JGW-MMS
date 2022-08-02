package com.jaramgroupware.mms.utils.key;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Random;

@Component
public class KeyGenerator {

    //ref : https://www.delftstack.com/ko/howto/java/random-alphanumeric-string-in-java/
    public String getKey(Integer length){

        byte[] bytearray;
        String mystring;
        StringBuffer thebuffer;
        bytearray = new byte[256];
        new Random().nextBytes(bytearray);

        mystring = new String(bytearray, StandardCharsets.UTF_8);

        // Create the StringBuffer
        thebuffer = new StringBuffer();

        for (int m = 0; m < mystring.length(); m++) {

            char n = mystring.charAt(m);

            if (((n >= 'A' && n <= 'Z')
                    || (n >= '0' && n <= '9'))
                    && (length > 0)) {

                thebuffer.append(n);
                length--;
            }
        }
        return thebuffer.toString();
    }
}

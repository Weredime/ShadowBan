/**
 * ShadowBan - Ban players secretly without letting them know.
 * Copyright (C) 2022 Weredime
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
package io.github.weredime.shadowban;

import java.util.HashMap;

public class ShadowBanMessages {
    public static String CONNECTION_REFUSED = "Connection refused: no further information";
    public static String UNKNOWN_HOST = "Unknown host";
    public static String OUTDATED_SERVER = "Outdated server! I'm still on {0}";
    public static String BAD_LOGIN = "Failed to login: Bad Login";
    public static String CONNECTION_RESET = "java.net.SocketException: Connection reset";
    public static String OUTDATED_CLIENT = "Outdated client!";
    public static String INVALID_SERVER_KEY = "This server responded with an invalid server key";
    public static String[] KEYS = {"CONNECTION_REFUSED", "UNKNOWN_HOST", "OUTDATED_SERVER", "BAD_LOGIN", "CONNECTION_RESET", "OUTDATED_CLIENT", "INVALID_SERVER_KEY"};
    public static HashMap<String, String> getMessages() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("CONNECTION_REFUSED", CONNECTION_RESET);
        map.put("UNKNOWN_HOST", UNKNOWN_HOST);
        map.put("OUTDATED_SERVER", OUTDATED_SERVER);
        map.put("BAD_LOGIN", BAD_LOGIN);
        map.put("CONNECTION_REFUSED", CONNECTION_REFUSED);
        map.put("OUTDATED_CLIENT", OUTDATED_CLIENT);
        map.put("INVALID_SERVER_KEY", INVALID_SERVER_KEY);

        return map;
    }
}

package com.gufli.bricknametags.api;

import org.jetbrains.annotations.ApiStatus;

public class NametagAPI {

    private static NametagManager nametagManager;

    @ApiStatus.Internal
    public static void setNametagManager(NametagManager manager) {
        nametagManager = manager;
    }

    //

    public static NametagManager get() {
        return nametagManager;
    }

}

package io.github.rukins.gkeepapi.model.node;

import java.util.Arrays;
import java.util.List;

public enum PlatformType {
    ANDROID,
    WEB,
    CRX,
    IOS,
    CRX_BA;

    public static final PlatformType DEFAULT_PLATFORM_TYPE = PlatformType.ANDROID;

    public static final List<PlatformType> DEFAULT_APPLICABLE_PLATFORMS = Arrays.asList(
            PlatformType.ANDROID,
            PlatformType.WEB,
            PlatformType.CRX,
            PlatformType.IOS,
            PlatformType.CRX_BA
    );
}

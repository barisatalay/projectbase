package com.brsatalay.projectbase.library.core.data.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by barisatalay on 10.04.2018.
 */

public enum LoadingType {
    BallPulseIndicator,
    BallGridPulseIndicator,
    BallClipRotateIndicator,
    BallClipRotatePulseIndicator,
    SquareSpinIndicator,
    BallClipRotateMultipleIndicator,
    BallPulseRiseIndicator,
    BallRotateIndicator,
    CubeTransitionIndicator,
    BallZigZagIndicator,
    BallZigZagDeflectIndicator,
    BallTrianglePathIndicator,
    BallScaleIndicator,
    LineScaleIndicator,
    LineScalePartyIndicator,
    BallScaleMultipleIndicator,
    BallPulseSyncIndicator,
    BallBeatIndicator,
    LineScalePulseOutIndicator,
    LineScalePulseOutRapidIndicator,
    BallScaleRippleIndicator,
    BallScaleRippleMultipleIndicator,
    BallSpinFadeLoaderIndicator,
    LineSpinFadeLoaderIndicator,
    TriangleSkewSpinIndicator,
    PacmanIndicator,
    BallGridBeatIndicator,
    SemiCircleSpinIndicator;

    private static final List<LoadingType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static LoadingType randomType(){
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}

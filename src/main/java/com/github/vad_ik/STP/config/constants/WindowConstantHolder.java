package com.github.vad_ik.STP.config.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WindowConstantHolder {

    @Value("${fx.WIDTH}")
    public int WIDTH;

    @Value("${fx.HEIGHT}")
    public int HEIGHT;

    @Value("${fx.SIZE_ROUTER}")
    public int SIZE_ROUTER;

    @Value("${fx.TITLE}")
    public String TITLE;
}

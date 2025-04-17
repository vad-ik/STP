package com.github.vad_ik.STP.config.constants;

import javafx.scene.paint.Color;
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

    @Value("${fx.TIME_ANIMATION}")
    public int TIME_ANIMATION;

    @Value("${int.MAX}")
    public int MAX_INT;

    @Value("${text.ADD_NODE}")
    public String ADD_NODE;

    @Value("${text.NEXT}")
    public String NEXT;

    @Value("${text.NEXT_STEP}")
    public String NEXT_STEP;

    @Value("${text.LEN_TO_ROOT}")
    public String LEN_TO_ROOT;

    @Value("${text.IS_ROOT}")
    public String IS_ROOT;

    @Value("${text.IS_NOT_ROOT}")
    public String IS_NOT_ROOT;

    @Value("${fx.WIDTH_LINE}")
    public int WIDTH_LINE;

    @Value("${fx.WIDTH_OFF_LINE}")
    public int WIDTH_OFF_LINE;

    @Value("${fx.WIDTH_DOTTED_LINE}")
    public int WIDTH_DOTTED_LINE;


    //@Value("${fx.COLOR_LINE}")
    public Color COLOR_LINE = Color.DARKRED;
    //@Value("${fx.COLOR_DOTTED_LINE}")
    public Color COLOR_DOTTED_LINE = Color.ANTIQUEWHITE;
    //@Value("${fx.COLOR_OFF_LINE}")
    public Color COLOR_OFF_LINE = Color.ORANGERED;
    //@Value("${fx.COLOR_NODE}")
    public Color COLOR_NODE = Color.BLUE;
    // @Value("${fx.COLOR_NODE_ACTIVE}")
    public Color COLOR_NODE_ACTIVE = Color.GREEN;


}
package com.demo.controller;

import com.jfinal.core.Controller;
import org.apache.log4j.Logger;

/**
 * @author: guoyongkui
 * @date:  2018/11/9 22:26
 * @projectName:  demo
 * @description:
 */
public class IndexController  extends Controller {
    private static final Logger logger = Logger.getLogger(IndexController.class);

    public void index(){
        render("index.html");
    }
}

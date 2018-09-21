package com.click.interview.contract.impl;

import com.click.interview.contract.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Source code generated by Gerardo Pucheta Figueroa to Click Mexico
 * Twitter: @ledzedev
 * http://ledze.mx
 * 9/21/2018
 */
public class Sum implements Operation {
    private static final Logger LOG = LoggerFactory.getLogger(Sum.class);

    private int userId = -1;

    public Sum(int uId) {
        this.userId = uId;
    }

    public String execute() {
        LOG.info("sum operation");


        return null;
    }
}

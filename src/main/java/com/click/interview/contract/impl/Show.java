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
public class Show implements Operation {
    private static final Logger LOG = LoggerFactory.getLogger(Show.class);

    private int userId = -1;
    private String transactionId = null;

    public Show(int uId, String tId) {
        this.userId = uId;
        this.transactionId = tId;
    }

    public String execute() {
        LOG.info("show operation");

        return null;
    }
}
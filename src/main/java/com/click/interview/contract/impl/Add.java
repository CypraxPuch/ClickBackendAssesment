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
public class Add implements Operation {
    private static final Logger LOG = LoggerFactory.getLogger(Add.class);

    private int userId = -1;
    private String transactionJson = null;

    public Add(int uId, String tranJson) {
        this.userId = uId;
        this.transactionJson = tranJson;
    }

    public String execute() {
        LOG.info("add operation");

        return null;
    }
}
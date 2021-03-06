package com.click.interview.contract.impl;

import com.click.interview.contract.Operation;
import com.click.interview.data.dao.DAO;
import com.click.interview.data.dao.TransactionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Source code generated by Gerardo Pucheta Figueroa to Clip Mexico
 * Twitter: @ledzedev
 * http://ledze.mx
 * 9/21/2018
 */
public class ListTransaction implements Operation {
    private static final Logger LOG = LoggerFactory.getLogger(ListTransaction.class);

    private int userId = -1;

    public ListTransaction(int uId) {
        this.userId = uId;
    }

    public String execute() {
        LOG.info("list transactions. userId:{}",userId);
        DAO<String> dao = new TransactionDAO();
        return dao.getAllFormatted(this.userId);
    }
}


package com.click.interview.data.dao;

import com.click.interview.data.to.TransactionTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

/**
 * Source code generated by Gerardo Pucheta Figueroa to Clip Mexico
 * Twitter: @ledzedev
 * http://ledze.mx
 * 9/24/2018
 */
public class TransactionDAO implements DAO<String> {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionDAO.class);

    @Override
    public String add(Integer userId, String transaction) {
        String result = null;

        Path currentRelativePath = Paths.get("");
        String currDir = currentRelativePath.toAbsolutePath().toString();
        LOG.debug("Current relative path is: " + currDir);

        Path transactionPath = Paths.get(currDir, "json");
        boolean fileExists = false;
        try {
            transactionPath = Files.createDirectories(transactionPath);
            currDir = transactionPath.toAbsolutePath().toString();
            transactionPath = Paths.get(currDir, userId + ".json");
            if (Files.notExists(transactionPath)) {
                transactionPath = Files.createFile(transactionPath);
            } else {
                fileExists = true;
            }
        } catch (IOException e) {
            LOG.error("Exception creating json file to store transactions.", e);
        }
        Charset charset = Charset.forName("UTF-8");
        byte[] t = null;

        try {
            result = addUniqueIdToJson(transaction);
        } catch (JsonProcessingException e) {
            LOG.error("Error parsing transaction.", e);
        }

        if (!fileExists) {
            result = "[" + result + "]";
            t = result.getBytes(charset);
            try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(transactionPath, CREATE, APPEND))) {
                out.write(t, 0, t.length);
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
                LOG.error("Error saving transaction.", x);
                System.exit(700);
            }
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            List<TransactionTO> lstTrans = null;
            try {
                lstTrans = objectMapper.readValue(transactionPath.toFile(), new TypeReference<List<TransactionTO>>() {
                });
                TransactionTO to = getTransactionTOFromJsonString(transaction);
                lstTrans.add(to);

                objectMapper.writeValue(transactionPath.toFile(), lstTrans);
            } catch (IOException e) {
                LOG.error("Error readding json from file.", e);
            }
        }

        return result;
    }

    private String addUniqueIdToJson(String transactionJson) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        TransactionTO to = getTransactionTOFromJsonString(transactionJson);
        return om.writeValueAsString(to);
    }

    private TransactionTO getTransactionTOFromJsonString(String transactionJson) {
        ObjectMapper om = new ObjectMapper();
        TransactionTO to = null;
        try {
            to = om.readValue(transactionJson, TransactionTO.class);
            to.setTransaction_id(getUniqueID());
        } catch (IOException e) {
            LOG.error("Error mapping json to TO", e);
        }
        return to;
    }

    private String getUniqueID() {
        Random r = new Random();
        String pastId = "e3b202a8-fcb3-4492-a442-c717fdac" +
                (r.nextInt((9 - 0) + 1) + 0) + "" +
                (r.nextInt((9 - 0) + 1) + 0) + "" +
                (r.nextInt((9 - 0) + 1) + 0) + "" +
                (r.nextInt((9 - 0) + 1) + 0);
        //System.out.println(pastId);
        return pastId;
    }

    @Override
    public String getById(Integer userId, String transactionId) {
        String result = "";
        List<TransactionTO> lst = null;

        lst = this.getAll(userId);
        if (lst != null && !lst.isEmpty()) {
            TransactionTO to = lst.stream()
                    .filter(t -> t.getTransaction_id().equalsIgnoreCase(transactionId))
                    .findAny()
                    .orElse(null);
            if (to == null) {
                result = "Transaction not found";
            } else {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    result = objectMapper.writeValueAsString(to);
                } catch (JsonProcessingException e) {
                    LOG.error("Error getting info from transaction. userId:{}", userId, e);
                    result = "Transaction not found";
                }
            }
        } else {
            result = "Transaction not found";
        }

        return result;
    }

    @Override
    public String getAllFormatted(Integer userId) {
        List<TransactionTO> lst = null;

        lst = this.getAll(userId);

        lst.sort((t1, t2)->{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate d1 = LocalDate.parse(t1.getDate(),formatter);
            LocalDate d2 = LocalDate.parse(t2.getDate(),formatter);
            return d1.compareTo(d2);
        });
        ObjectMapper objectMapper = new ObjectMapper();
        String result = null;
        try {
            result = objectMapper.writeValueAsString(lst);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<TransactionTO> getAll(Integer userId) {
        List<TransactionTO> lstTrans = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            lstTrans = objectMapper.readValue(getPathByUserId(userId).toFile(), new TypeReference<List<TransactionTO>>() {
            });

        } catch (IOException e) {
            LOG.error("Error readding json from file.", e);
        }
        return lstTrans;
    }

    private Path getPathByUserId(Integer userId) {
        Path currentRelativePath = Paths.get("");
        String currDir = currentRelativePath.toAbsolutePath().toString();
        //LOG.debug("Current relative path is: " + currDir);

        Path transactionPath = Paths.get(currDir, "json", userId + ".json");
        return transactionPath;
    }

    @Override
    public String sumByUserId(Integer userId) {
        String result = "";
        List<TransactionTO> lst = null;

        lst = this.getAll(userId);
        if (lst != null && !lst.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            Double amount = lst
                    .stream()
                    .mapToDouble(t -> Double.valueOf(t.getAmount()))
                    .sum();
            if (amount == null) {
                result = "Transaction not found";
            } else {
                result = "{\"user_id\":" + userId + ", \"sum\":" + amount.toString() + "}";
            }
        } else {
            result = "Transaction not found";
        }

        return result;
    }
}

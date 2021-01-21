package com.example.scanner;

import com.example.scanner.logic.datatypes.responseTypes.ShortRequestDescription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class Test {
    static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args){
        ShortRequestDescription obj = null;
        try {
            obj =  mapper.readValue("{\n" +
                            "    \"collection_date\": \"2020-02-22T00:00:00\",\n" +
                            "    \"comment\": \"коммент\",\n" +
                            "    \"ext_id\": \"123\",\n" +
                            "    \"id\": 1,\n" +
                            "    \"name_view\": \"First Product\",\n" +
                            "    \"product_request_lines\": [\n" +
                            "        {\n" +
                            "            \"id\": 1,\n" +
                            "            \"product_id\": 1,\n" +
                            "            \"product_request_id\": 1,\n" +
                            "            \"quantity\": 1\n" +
                            "        },\n" +
                            "        {\n" +
                            "            \"id\": 2,\n" +
                            "            \"product_id\": 2,\n" +
                            "            \"product_request_id\": 1,\n" +
                            "            \"quantity\": 21\n" +
                            "        },\n" +
                            "        {\n" +
                            "            \"id\": 3,\n" +
                            "            \"product_id\": 4,\n" +
                            "            \"product_request_id\": 1,\n" +
                            "            \"quantity\": 1\n" +
                            "        }\n" +
                            "    ],\n" +
                            "    \"self_life_preference\": 0,\n" +
                            "    \"status\": 0,\n" +
                            "    \"warehouse_in\": {\n" +
                            "        \"corporation_id\": 1,\n" +
                            "        \"id\": 2,\n" +
                            "        \"is_delete\": false,\n" +
                            "        \"name_view\": \"Клиент 1\"\n" +
                            "    },\n" +
                            "    \"warehouse_out\": {\n" +
                            "        \"corporation_id\": 1,\n" +
                            "        \"id\": 1,\n" +
                            "        \"is_delete\": false,\n" +
                            "        \"name_view\": \"1c Основной склад\"\n" +
                            "    }\n" +
                            "}",
                    mapper.getTypeFactory().constructCollectionType(List.class,
                            ShortRequestDescription.class));
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(obj);
    }
}

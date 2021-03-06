package com.example.scanner.logic;

import com.example.scanner.logic.datatypes.responseTypes.Product;
import com.example.scanner.logic.datatypes.responseTypes.RequestData;
import com.example.scanner.logic.datatypes.responseTypes.ShortRequestDescription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class ServerResponseProcessor {
    static ObjectMapper mapper = new ObjectMapper();

    static List<ShortRequestDescription> parseReqListResponse(Response response) {
        try {
            if (response.code() == 200) return mapper.readValue(response.body().string(),
                    mapper.getTypeFactory().constructCollectionType(List.class,
                            ShortRequestDescription.class));
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //List<MstCode> mstCodes = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, MstCode.class));

    static RequestData parseReqDataResponse(Response response) {
        try {
            if (response.code() == 200) return mapper.readValue(response.body().string(), RequestData.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static Product parseScanResult(Response response) {
        try {
            if (response.code() == 200) return mapper.readValue(response.body().string(), Product.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    interface Result {
        boolean getStatus();
        void setStatus(boolean result);
    }

    static boolean parseStartResponse(Response response) {
        try {
            if (response.code() == 200)
                return mapper.readValue(response.body().string(), (new Result() {
                    private boolean result;

                    @Override
                    public boolean getStatus() {
                        return result;
                    }

                    @Override
                    public void setStatus(boolean result) {
                        this.result = result;
                    }
                }).getClass()).getStatus();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    static boolean parseCancelResponse(Response response) {
        return parseStartResponse(response);
    }

    static boolean parseFinishResponse(Response response) {
        return parseStartResponse(response);
    }
}

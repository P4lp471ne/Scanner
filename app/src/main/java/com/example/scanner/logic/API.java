package com.example.scanner.logic;


import com.example.scanner.logic.datatypes.requestTypes.Report;

import okhttp3.Callback;

interface API {
    void getRequestsList(Callback callback);

    void getRequestData(String requestID, Callback callback);

    void startCollecting(String requestID, Callback callback);

    void cancelCollecting(String requestID, Callback callback);

    void finishCollecting(Report report, Callback callback);

    void scan(String barcode, Callback callback);
}

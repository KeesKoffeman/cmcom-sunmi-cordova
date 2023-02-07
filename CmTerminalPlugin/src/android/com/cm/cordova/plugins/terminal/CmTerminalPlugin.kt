package com.cm.cordova.plugins.terminal

import org.apache.cordova.CordovaPlugin
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;

import com.cm.androidposintegration.initializer.AndroidPosIntegration;
import com.cm.androidposintegration.beans.TransactionData;
import com.cm.androidposintegration.enums.TransactionType;
import com.cm.androidposintegration.enums.TransactionResult
import com.cm.androidposintegration.service.callback.TransactionCallback
import com.cm.androidposintegration.service.callback.beans.ErrorCode
import com.cm.androidposintegration.service.callback.beans.TransactionResultData
import java.lang.System

import java.math.BigDecimal;
import java.util.Currency;

class CmTerminalPlugin : CordovaPlugin() {

    @Throws(JSONException::class)
    override fun execute(action: String, args: JSONArray, callbackContext: CallbackContext): Boolean {
        if (action.equals("transaction")) {
            val currency: String = args.getString(0)
            val amount: String = args.getString(1)
            val reference: String = args.getString(2)
            performTransaction(currency, amount, reference, callbackContext)
            return true
        }
        return false
    }

    private fun performTransaction(
        currency: String,
        amount: String,
        reference: String,
        callbackContext: CallbackContext
    ) {

        AndroidPosIntegration.init(this.cordova.getActivity().getApplicationContext())
        val instance = AndroidPosIntegration.getInstance() ?: TODO("Handle NPE.")

        val data = TransactionData(
            TransactionType.PURCHASE,
            BigDecimal(amount),
            Currency.getInstance(currency),
            reference
        )

        val callback = object : TransactionCallback {
            override fun onCrash() {
                callbackContext.error("Crash!")
            }

            override fun onError(error: ErrorCode) {
                System.out.println(error)
                callbackContext.error(error.desc)
            }

            override fun onResult(data: TransactionResultData) {
                System.out.println(data)
                callbackContext.success(data.toString())
            }
        }

        instance.doTransaction(data, callback)
    }
}
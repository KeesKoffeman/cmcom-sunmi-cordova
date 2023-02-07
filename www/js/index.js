// Wait for the deviceready event before using any of Cordova's device APIs.
// See https://cordova.apache.org/docs/en/latest/cordova/events/events.html#deviceready
document.addEventListener('deviceready', onDeviceReady, false);

function onDeviceReady() {

    console.log('Running cordova-' + cordova.platformId + '@' + cordova.version);
    document.getElementById('deviceready').classList.add('ready');
    document.getElementById("btnPaymentViaShim").addEventListener("click", executePaymentViaShim);

    window.plugins.intentShim.onIntent(function (intent) {
        console.log('Received Intent: ' + JSON.stringify(intent.extras));
    });
}

function executePaymentViaShim() {

    let currency = document.getElementById("currency").value
    let amount = document.getElementById("amount").value
    let reference = document.getElementById("reference").value

    console.log(`Starting Payment ${reference}; ${currency} ${amount}.`)

    window.plugins.intentShim.startActivity(
        {
            action: "com.payplaza.dev.action.TRANSACTION",
            extras: {
                'com.payplaza.extra.TRANSACTION_TYPE' : 'purchase',
                'com.payplaza.extra.CURRENCY_CODE' : currency,
                'com.payplaza.extra.AMOUNT' : amount * 100,
                'com.payplaza.extra.ORDERREF' : reference,
                'com.payplaza.extra.USE_PROC_STYLE_PROTOCOL' : true
            }
        },
        function(intent) {
            console.log(intent)
            navigator.notification.confirm(
                JSON.stringify(intent.extras),
                null,
                'Payment OK!'
            );
        },
        function() {
            navigator.notification.alert(
                'Failed to execute payment via Intent.',
                null,
                'Failure'
            );
        }
    );
}
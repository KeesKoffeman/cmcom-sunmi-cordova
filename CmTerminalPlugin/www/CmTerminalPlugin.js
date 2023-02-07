var exec = require('cordova/exec');

exports.transaction = function (arg0, success, error) {
    exec(success, error, 'CmTerminalPlugin', 'transaction', [arg0]);
};
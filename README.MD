# Cordova ECR
Simple example project howto integrate CM.com Sunmi terminals with Cordova apps. See https://developers.cm.com/payments-platform/v1.0.2/docs/app-2-app-integration.

## Run this example.
Assumes a Sunmi P2 pro/lite/mini is hooked up via usb and has a debug terminal app on it.
```
cordova build
cordova run android
```

## Re-apply plugin.
When making changes to the plugin it needs to be removed and re-added.
```
cordova plugin rm com.cm.cordova.plugins.terminal && cordova plugin add CmTerminalPlugin --link 
```
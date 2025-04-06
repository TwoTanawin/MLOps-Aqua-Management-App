#ifndef SECRETS_H
#define SECRETS_H

// #define WIFI_SSID "SKYNET_2.4G"
// #define WIFI_PASSWORD "Tanawin42869"


#define WIFI_SSID "PUsasi_2.4G"
#define WIFI_PASSWORD "0816315964"

// from setting
const char AWS_IOT_ENDPOINT[] = "a1ffx9w8z4hget-ats.iot.ap-southeast-1.amazonaws.com";       //change this
 
// Amazon Root CA 1
static const char AWS_CERT_CA[] PROGMEM = R"EOF(

)EOF";
 
// Device Certificate                                               //change this
static const char AWS_CERT_CRT[] PROGMEM = R"KEY(

)KEY";
 
// Device Private Key                                               //change this
static const char AWS_CERT_PRIVATE[] PROGMEM = R"KEY(

)KEY";

#endif

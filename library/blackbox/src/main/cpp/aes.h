#ifndef _AES_H_
#define _AES_H_

#include <stdint.h>


// #define the macros below to 1/0 to enable/disable the mode of operation.
//
// CBC enables AES encryption in CBC-mode of operation.
// CTR enables encryption in counter-mode.
// ECB enables the basic ECB 16-byte block algorithm. All can be enabled simultaneously.

// The #ifndef-guard allows it to be configured before #include'ing or at compile time.
#ifndef CBC
#define CBC 1
#endif

#ifndef ECB
#define ECB 1
#endif

#ifndef CTR
#define CTR 1
#endif


#define AES_ECB_encrypt         qqppqp
#define AES_ECB_decrypt         qqppqq
#define AES_CBC_encrypt_buffer  qpppqp
#define AES_CBC_decrypt_buffer  qqqpqp
#define AES_CTR_xcrypt_buffer   qppqqp


#define AES128 1
//#define AES192 1
//#define AES256 1

#if defined(ECB) && (ECB == 1)

void AES_ECB_encrypt(const uint8_t* input, const uint8_t* key, uint8_t *output, const uint32_t length);
void AES_ECB_decrypt(const uint8_t* input, const uint8_t* key, uint8_t *output, const uint32_t length);

#endif // #if defined(ECB) && (ECB == !)


#if defined(CBC) && (CBC == 1)

void AES_CBC_encrypt_buffer(uint8_t* output, uint8_t* input, uint32_t length, const uint8_t* key, const uint8_t* iv);
void AES_CBC_decrypt_buffer(uint8_t* output, uint8_t* input, uint32_t length, const uint8_t* key, const uint8_t* iv);

#endif // #if defined(CBC) && (CBC == 1)


#if defined(CTR) && (CTR == 1)

/* Same function for encrypting as for decrypting. Note no IV/nonce should ever be reused with the same key */
void AES_CTR_xcrypt_buffer(uint8_t* output, uint8_t* input, uint32_t length, const uint8_t* key, const uint8_t* nonce);

#endif // #if defined(CTR) && (CTR == 1)


#endif //_AES_H_

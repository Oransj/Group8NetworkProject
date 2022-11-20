from cryptography.hazmat.primitives import serialization as crypto_serialization
from cryptography.hazmat.primitives.asymmetric import rsa
from cryptography.hazmat.backends import default_backend as crypto_default_backend
from cryptography.hazmat.primitives import serialization
import timeit
import os
from cryptography.x509 import load_pem_x509_certificate

start = timeit.default_timer()

key = rsa.generate_private_key(
    backend=crypto_default_backend(),
    public_exponent=65537,
    key_size=4096
)

private_key = key.private_bytes(
    crypto_serialization.Encoding.PEM,
    crypto_serialization.PrivateFormat.PKCS8,
    crypto_serialization.NoEncryption()
)

public_key = key.public_key().public_bytes(
    serialization.Encoding.OpenSSH,
   serialization.PublicFormat.OpenSSH
)
stop = timeit.default_timer()

print(private_key.decode('utf-8'))
print(public_key.decode('utf-8'))
print("-------------------")
print("Time: ", stop - start)

def saveKeys(key):
    private_key = key.private_bytes(
        crypto_serialization.Encoding.PEM,
        crypto_serialization.PrivateFormat.PKCS8,
        crypto_serialization.NoEncryption()
    )

    public_key = key.public_key().public_bytes(
        serialization.Encoding.OpenSSH,
        serialization.PublicFormat.OpenSSH
    )
    
    with open("PRK.ppk", 'wb') as pem_out:
            pem_out.write(private_key)
            
    with open("PUK.pem", 'wb') as pem_out:
        pem_out.write(public_key)

with open('test.pem', 'rb') as key_file:
    a = serialization.load_pem_public_key(
        key_file.read()
    )
    print(a)

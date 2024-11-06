#!/bin/sh

# --batch to prevent interactive command
# --yes to assume "yes" for questions

gpg --quiet --batch --yes --decrypt --passphrase="$LARGE_SECRET_PASSPHRASE" \
--output src/main/resources/google_keys/client_secret_oauth.json \
src/main/resources/google_keys/client_secret_oauth.json.gpg

gpg --quiet --batch --yes --decrypt --passphrase="$LARGE_SECRET_PASSPHRASE" \
--output src/main/resources/google_keys/client_secret_service.json \
src/main/resources/google_keys/client_secret_service.json.gpg
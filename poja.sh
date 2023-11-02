#!/bin/sh

export LC_CTYPE=C
export LANG=C
find ./ -type f -exec sed -i '' -e "s/poja-base/$1/g" {} \;
find ./ -type f -exec sed -i '' -e "s/eu-west-3/$2/g" {} \;

ESCAPED_SG_ID=$(printf '%s\n' "$3" | sed -e 's/\//\\&/g');
find ./ -type f -exec sed -i '' -e "s//poja/sg/id/$ESCAPED_SG_ID/g" {} \;

ESCAPED_SUBNET1_ID=$(printf '%s\n' "$4" | sed -e 's/\//\\&/g');
find ./ -type f -exec sed -i '' -e "s//poja/subnet/public1/id/$ESCAPED_SUBNET1_ID/g" {} \;

ESCAPED_SUBNET2_ID=$(printf '%s\n' "$5" | sed -e 's/\//\\&/g');
find ./ -type f -exec sed -i '' -e "s//poja/subnet/public2/id/$ESCAPED_SUBNET2_ID/g" {} \;
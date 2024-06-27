sudo apt-get install jq
export API_URL_SSM="`aws ssm get-parameter --name /poja-base/$1/api/url`"
export API_URL=`echo $API_URL_SSM | jq -r '.Parameter.Value'`
created_uuids=$(curl --fail -X GET "$API_URL$2")
curl --fail -s -X POST -H "Content-Type: application/json" -d "$created_uuids" "$API_URL/health/event/uuids"
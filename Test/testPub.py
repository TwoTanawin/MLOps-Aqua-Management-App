import boto3
import json

# Publish a test message
session = boto3.Session(profile_name='mlops', region_name='ap-southeast-1')
iot_client = session.client('iot-data')
response = iot_client.publish(
    topic='esp32/pub',
    qos=1,
    payload=json.dumps({
        "station": "1",
        "timestamp": "2025-03-28 00:42:45",
        "data": {
            "x": -3.10, 
            "y": -3.10, 
            "z": 7.10
        }
    })
)
print("Message published:", response)